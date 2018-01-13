
package org.drip.xva.hypothecation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * CollateralAmountEstimator estimates the Amount of Collateral Hypothecation that is to be Posted during a
 *  Single Run of a Collateral Hypothecation Group Valuation. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralAmountEstimator
{
	private double _currentBalance = java.lang.Double.NaN;
	private org.drip.measure.bridge.BrokenDateInterpolator _brokenDateInterpolator = null;
	private org.drip.xva.set.CollateralGroupSpecification _collateralGroupSpecification = null;
	private org.drip.xva.set.CounterPartyGroupSpecification _counterPartyGroupSpecification = null;

	/**
	 * CollateralAmountEstimator Constructor
	 * 
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * @param counterPartyGroupSpecification The Counter Party Group Specification
	 * @param brokenDateInterpolator The Stochastic Value Broken Date Bridge Estimator
	 * @param currentBalance The Current Collateral Balance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralAmountEstimator (
		final org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification,
		final org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification,
		final org.drip.measure.bridge.BrokenDateInterpolator brokenDateInterpolator,
		final double currentBalance)
		throws java.lang.Exception
	{
		if (null == (_collateralGroupSpecification = collateralGroupSpecification) ||
			null == (_counterPartyGroupSpecification = counterPartyGroupSpecification) ||
			null == (_brokenDateInterpolator = brokenDateInterpolator))
		{
			throw new java.lang.Exception ("CollateralAmountEstimator Constructor => Invalid Inputs");
		}

		_currentBalance = currentBalance;
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Counter Party Group Specification
	 * 
	 * @return The Counter Party Group Specification
	 */

	public org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification()
	{
		return _counterPartyGroupSpecification;
	}

	/**
	 * Retrieve the Stochastic Value Broken Date Bridge Estimator
	 * 
	 * @return The Stochastic Value Broken Date Bridge Estimator
	 */

	public org.drip.measure.bridge.BrokenDateInterpolator brokenDateBridge()
	{
		return _brokenDateInterpolator;
	}

	/**
	 * Retrieve the Current Collateral Balance
	 * 
	 * @return The Current Collateral Balance
	 */

	public double currentCollateralBalance()
	{
		return _currentBalance;
	}

	/**
	 * Calculate the Margin Value at the Bank Default Window
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Value at the Bank Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankWindowMarginValue (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		if (null == valuationDateJulian)
			throw new java.lang.Exception
				("CollateralAmountEstimator::bankWindowMarginValue => Invalid Inputs");

		org.drip.analytics.date.JulianDate dtMargin = valuationDateJulian.subtractDays (_counterPartyGroupSpecification.bankDefaultWindow());

		if (null == dtMargin)
			throw new java.lang.Exception
				("CollateralAmountEstimator::bankWindowMarginValue => Invalid Inputs");

		return _brokenDateInterpolator.interpolate (dtMargin.julian());
	}

	/**
	 * Calculate the Bank Collateral Threshold
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Bank Collateral Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankThreshold (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 bankThresholdFunction = _collateralGroupSpecification.bankThreshold();

		return null == bankThresholdFunction ? 0. : bankThresholdFunction.evaluate (valuationDateJulian.julian());
	}

	/**
	 * Calculate the Collateral Amount Required to be Posted by the Bank
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Collateral Amount Required to be Posted by the Bank
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankPostingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		double bankPostingRequirement = bankWindowMarginValue (valuationDateJulian) - bankThreshold (valuationDateJulian);

		return 0. < bankPostingRequirement ? 0. : bankPostingRequirement;
	}

	/**
	 * Calculate the Margin Value at the Counter Party Default Window
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Value at the Counter Party Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyWindowMarginValue (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		if (null == valuationDateJulian)
			throw new java.lang.Exception
				("CollateralAmountEstimator::counterPartyWindowMarginValue => Invalid Inputs");

		org.drip.analytics.date.JulianDate dtMargin = valuationDateJulian.subtractDays
			(_counterPartyGroupSpecification.counterPartyDefaultWindow());

		if (null == dtMargin)
			throw new java.lang.Exception
				("CollateralAmountEstimator::counterPartyWindowMarginValue => Invalid Inputs");

		return _brokenDateInterpolator.interpolate (dtMargin.julian());
	}

	/**
	 * Calculate the Counter Party Collateral Threshold
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Counter Party Collateral Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyThreshold (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1[] counterPartyThresholdFunctionArray = _collateralGroupSpecification.counterPartyThreshold();

		return null == counterPartyThresholdFunctionArray || null == counterPartyThresholdFunctionArray[0] ? 0. :
			counterPartyThresholdFunctionArray[0].evaluate (valuationDateJulian.julian());
	}

	/**
	 * Calculate the Collateral Amount Required to be Posted by the Counter Party
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Collateral Amount Required to be Posted by the Counter Party
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyPostingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		double counterPartyPostingRequirement = counterPartyWindowMarginValue (valuationDateJulian) -
			counterPartyThreshold (valuationDateJulian);

		return 0. > counterPartyPostingRequirement ? 0. : counterPartyPostingRequirement;
	}

	/**
	 * Calculate the Gross Collateral Amount Required to be Posted
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Gross Collateral Amount Required to be Posted
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double postingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		return org.drip.quant.common.NumberUtil.IsValid (_currentBalance) ? _currentBalance :
			bankPostingRequirement (valuationDateJulian) + counterPartyPostingRequirement (valuationDateJulian);
	}

	/**
	 * Generate the CollateralAmountEstimatorOutput Instance
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The CollateralAmountEstimatorOutput Instance
	 */

	public org.drip.xva.hypothecation.CollateralAmountEstimatorOutput output (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
	{
		if (null == valuationDateJulian)
		{
			return null;
		}

		org.drip.analytics.date.JulianDate bankMarginDate = valuationDateJulian.subtractDays
			(_counterPartyGroupSpecification.bankDefaultWindow());

		org.drip.analytics.date.JulianDate counterPartyMarginDate = valuationDateJulian.subtractDays
			(_counterPartyGroupSpecification.counterPartyDefaultWindow());

		if (null == bankMarginDate ||
			null == counterPartyMarginDate)
		{
			return null;
		}

		org.drip.function.definition.R1ToR1[] counterPartyThresholdFunctionArray =
			_collateralGroupSpecification.counterPartyThreshold();

		org.drip.function.definition.R1ToR1 bankThresholdFunction =
			_collateralGroupSpecification.bankThreshold();

		double valuationDate = valuationDateJulian.julian();

		try
		{
			double bankWindowMarginValue = _brokenDateInterpolator.interpolate (bankMarginDate.julian());

			double counterPartyWindowMarginValue = _brokenDateInterpolator.interpolate
				(counterPartyMarginDate.julian());

			double bankThresholdValue = null == bankThresholdFunction ? 0. : bankThresholdFunction.evaluate
				(valuationDate);

			double counterPartyThresholdValue = null == counterPartyThresholdFunctionArray || null ==
				counterPartyThresholdFunctionArray[0] ? 0. : counterPartyThresholdFunctionArray[0].evaluate
					(valuationDate);

			double bankPostingRequirement = bankWindowMarginValue - bankThresholdValue;
			bankPostingRequirement = 0. < bankPostingRequirement ? 0. : bankPostingRequirement;
			double counterPartyPostingRequirement = counterPartyWindowMarginValue -
				counterPartyThresholdValue;
			counterPartyPostingRequirement = 0. > counterPartyPostingRequirement ? 0. :
				counterPartyPostingRequirement;

			return new org.drip.xva.hypothecation.CollateralAmountEstimatorOutput (
				bankMarginDate,
				counterPartyMarginDate,
				bankWindowMarginValue,
				bankThresholdValue,
				bankPostingRequirement,
				counterPartyWindowMarginValue,
				counterPartyThresholdValue,
				counterPartyPostingRequirement,
				org.drip.quant.common.NumberUtil.IsValid (_currentBalance) ? _currentBalance :
					bankPostingRequirement + counterPartyPostingRequirement);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
