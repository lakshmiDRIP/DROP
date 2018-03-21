
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
 * MarginAmountEstimator estimates the Amount of Collateral Hypothecation that is to be Posted during a
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

public class MarginAmountEstimator
{
	private double _currentBalance = java.lang.Double.NaN;
	private org.drip.measure.bridge.BrokenDateInterpolator _brokenDateInterpolator = null;
	private org.drip.xva.proto.PositionGroupSpecification _positionGroupSpecification = null;

	/**
	 * MarginAmountEstimator Constructor
	 * 
	 * @param positionGroupSpecification The Position Group Specification
	 * @param brokenDateInterpolator The Stochastic Value Broken Date Bridge Estimator
	 * @param currentBalance The Current Collateral Balance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarginAmountEstimator (
		final org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification,
		final org.drip.measure.bridge.BrokenDateInterpolator brokenDateInterpolator,
		final double currentBalance)
		throws java.lang.Exception
	{
		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_brokenDateInterpolator = brokenDateInterpolator))
		{
			throw new java.lang.Exception ("CollateralAmountEstimator Constructor => Invalid Inputs");
		}

		_currentBalance = currentBalance;
	}

	/**
	 * Retrieve the Position Group Specification
	 * 
	 * @return The Position Group Specification
	 */

	public org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
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
	 * Calculate the Margin Value at the Dealer Default Window
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Value at the Dealer Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerWindowMarginValue (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		if (null == valuationDateJulian)
		{
			throw new java.lang.Exception
				("MarginAmountEstimator::dealerWindowMarginValue => Invalid Inputs");
		}

		org.drip.analytics.date.JulianDate marginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.dealerDefaultWindow());

		if (null == marginDate)
		{
			throw new java.lang.Exception
				("MarginAmountEstimator::dealerWindowMarginValue => Invalid Inputs");
		}

		return _brokenDateInterpolator.interpolate (marginDate.julian());
	}

	/**
	 * Calculate the Dealer Margin Threshold
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Dealer Margin Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerThreshold (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 dealerThresholdFunction =
			_positionGroupSpecification.dealerThresholdFunction();

		return null == dealerThresholdFunction ? 0. : dealerThresholdFunction.evaluate
			(valuationDateJulian.julian());
	}

	/**
	 * Calculate the Margin Amount Required to be Posted by the Dealer
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Amount Required to be Posted by the Dealer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPostingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		double dealerPostingRequirement = dealerWindowMarginValue (valuationDateJulian) - dealerThreshold
			(valuationDateJulian);

		return 0. < dealerPostingRequirement ? 0. : dealerPostingRequirement;
	}

	/**
	 * Calculate the Margin Value at the Client Default Window
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Value at the Client Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double clientWindowMarginValue (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		if (null == valuationDateJulian)
		{
			throw new java.lang.Exception
				("MarginAmountEstimator::clientWindowMarginValue => Invalid Inputs");
		}

		org.drip.analytics.date.JulianDate marginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.clientDefaultWindow());

		if (null == marginDate)
		{
			throw new java.lang.Exception
				("MarginAmountEstimator::clientWindowMarginValue => Invalid Inputs");
		}

		return _brokenDateInterpolator.interpolate (marginDate.julian());
	}

	/**
	 * Calculate the Client Margin Threshold
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Client Margin Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double clientThreshold (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1[] clientThresholdFunctionArray =
			_positionGroupSpecification.clientThresholdFunctionArray();

		return null == clientThresholdFunctionArray || null == clientThresholdFunctionArray[0] ? 0. :
			clientThresholdFunctionArray[0].evaluate (valuationDateJulian.julian());
	}

	/**
	 * Calculate the Margin Amount Required to be Posted by the Client
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Margin Amount Required to be Posted by the Client
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double clientPostingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		double clientPostingRequirement = clientWindowMarginValue (valuationDateJulian) - clientThreshold
			(valuationDateJulian);

		return 0. > clientPostingRequirement ? 0. : clientPostingRequirement;
	}

	/**
	 * Calculate the Gross Margin Amount Required to be Posted
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The Gross Margin Amount Required to be Posted
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double postingRequirement (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
		throws java.lang.Exception
	{
		return org.drip.quant.common.NumberUtil.IsValid (_currentBalance) ? _currentBalance :
			dealerPostingRequirement (valuationDateJulian) + clientPostingRequirement (valuationDateJulian);
	}

	/**
	 * Generate the MarginAmountEstimatorOutput Instance
	 * 
	 * @param valuationDateJulian The Valuation Date
	 * 
	 * @return The MarginAmountEstimatorOutput Instance
	 */

	public org.drip.xva.hypothecation.MarginAmountEstimatorOutput output (
		final org.drip.analytics.date.JulianDate valuationDateJulian)
	{
		if (null == valuationDateJulian)
		{
			return null;
		}

		org.drip.analytics.date.JulianDate dealerMarginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.dealerDefaultWindow());

		org.drip.analytics.date.JulianDate clientMarginDate = valuationDateJulian.subtractDays
			(_positionGroupSpecification.clientDefaultWindow());

		if (null == dealerMarginDate ||
			null == clientMarginDate)
		{
			return null;
		}

		org.drip.function.definition.R1ToR1[] clientThresholdFunctionArray =
			_positionGroupSpecification.clientThresholdFunctionArray();

		org.drip.function.definition.R1ToR1 dealerThresholdFunction =
			_positionGroupSpecification.dealerThresholdFunction();

		double valuationDate = valuationDateJulian.julian();

		try
		{
			double dealerWindowMarginValue = _brokenDateInterpolator.interpolate (dealerMarginDate.julian());

			double clientWindowMarginValue = _brokenDateInterpolator.interpolate (clientMarginDate.julian());

			double dealerThresholdValue = null == dealerThresholdFunction ? 0. :
				dealerThresholdFunction.evaluate (valuationDate);

			double clientThresholdValue = null == clientThresholdFunctionArray || null ==
				clientThresholdFunctionArray[0] ? 0. : clientThresholdFunctionArray[0].evaluate
					(valuationDate);

			double dealerPostingRequirement = dealerWindowMarginValue - dealerThresholdValue;
			dealerPostingRequirement = 0. < dealerPostingRequirement ? 0. : dealerPostingRequirement;
			double clientPostingRequirement = clientWindowMarginValue - clientThresholdValue;
			clientPostingRequirement = 0. > clientPostingRequirement ? 0. : clientPostingRequirement;

			return new org.drip.xva.hypothecation.MarginAmountEstimatorOutput (
				dealerMarginDate,
				clientMarginDate,
				dealerWindowMarginValue,
				dealerThresholdValue,
				dealerPostingRequirement,
				clientWindowMarginValue,
				clientThresholdValue,
				clientPostingRequirement,
				org.drip.quant.common.NumberUtil.IsValid (_currentBalance) ? _currentBalance :
					dealerPostingRequirement + clientPostingRequirement);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
