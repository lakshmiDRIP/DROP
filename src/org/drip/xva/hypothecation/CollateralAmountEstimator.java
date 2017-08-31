
package org.drip.xva.hypothecation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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

public class CollateralAmountEstimator {
	private double _dblCurrentBalance = java.lang.Double.NaN;
	private org.drip.xva.set.CollateralGroupSpecification _cgs = null;
	private org.drip.measure.bridge.BrokenDateInterpolator _bdi = null;
	private org.drip.xva.set.CounterPartyGroupSpecification _cpgs = null;

	/**
	 * CollateralAmountEstimator Constructor
	 * 
	 * @param cgs The Collateral Group Specification
	 * @param cpgs The Counter Party Group Specification
	 * @param bdi The Stochastic Value Broken Date Bridge Estimator
	 * @param dblCurrentBalance The Current Collateral Balance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralAmountEstimator (
		final org.drip.xva.set.CollateralGroupSpecification cgs,
		final org.drip.xva.set.CounterPartyGroupSpecification cpgs,
		final org.drip.measure.bridge.BrokenDateInterpolator bdi,
		final double dblCurrentBalance)
		throws java.lang.Exception
	{
		if (null == (_cgs = cgs) || null == (_cpgs = cpgs) || null == (_bdi = bdi))
			throw new java.lang.Exception ("CollateralAmountEstimator Constructor => Invalid Inputs");

		_dblCurrentBalance = dblCurrentBalance;
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _cgs;
	}

	/**
	 * Retrieve the Counter Party Group Specification
	 * 
	 * @return The Counter Party Group Specification
	 */

	public org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification()
	{
		return _cpgs;
	}

	/**
	 * Retrieve the Stochastic Value Broken Date Bridge Estimator
	 * 
	 * @return The Stochastic Value Broken Date Bridge Estimator
	 */

	public org.drip.measure.bridge.BrokenDateInterpolator brokenDateBridge()
	{
		return _bdi;
	}

	/**
	 * Retrieve the Current Collateral Balance
	 * 
	 * @return The Current Collateral Balance
	 */

	public double currentCollateralBalance()
	{
		return _dblCurrentBalance;
	}

	/**
	 * Calculate the Margin Value at the Bank Default Window
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Margin Value at the Bank Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankWindowMarginValue (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		if (null == dtValue)
			throw new java.lang.Exception
				("CollateralAmountEstimator::bankWindowMarginValue => Invalid Inputs");

		org.drip.analytics.date.JulianDate dtMargin = dtValue.subtractDays (_cpgs.bankDefaultWindow());

		if (null == dtMargin)
			throw new java.lang.Exception
				("CollateralAmountEstimator::bankWindowMarginValue => Invalid Inputs");

		return _bdi.interpolate (dtMargin.julian());
	}

	/**
	 * Calculate the Bank Collateral Threshold
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Bank Collateral Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankThreshold (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 r1ToR1BankThreshold = _cgs.bankThreshold();

		return null == r1ToR1BankThreshold ? 0. : r1ToR1BankThreshold.evaluate (dtValue.julian());
	}

	/**
	 * Calculate the Collateral Amount Required to be Posted by the Bank
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Collateral Amount Required to be Posted by the Bank
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankPostingRequirement (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		double dblBankPostingRequirement = bankWindowMarginValue (dtValue) - bankThreshold (dtValue);

		return 0. < dblBankPostingRequirement ? 0. : dblBankPostingRequirement;
	}

	/**
	 * Calculate the Margin Value at the Counter Party Default Window
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Margin Value at the Counter Party Default Window
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyWindowMarginValue (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		if (null == dtValue)
			throw new java.lang.Exception
				("CollateralAmountEstimator::counterPartyWindowMarginValue => Invalid Inputs");

		org.drip.analytics.date.JulianDate dtMargin = dtValue.subtractDays
			(_cpgs.counterPartyDefaultWindow());

		if (null == dtMargin)
			throw new java.lang.Exception
				("CollateralAmountEstimator::counterPartyWindowMarginValue => Invalid Inputs");

		return _bdi.interpolate (dtMargin.julian());
	}

	/**
	 * Calculate the Counter Party Collateral Threshold
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Counter Party Collateral Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyThreshold (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1[] aR1ToR1CounterPartyThreshold = _cgs.counterPartyThreshold();

		return null == aR1ToR1CounterPartyThreshold || null == aR1ToR1CounterPartyThreshold[0] ? 0. :
			aR1ToR1CounterPartyThreshold[0].evaluate (dtValue.julian());
	}

	/**
	 * Calculate the Collateral Amount Required to be Posted by the Counter Party
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Collateral Amount Required to be Posted by the Counter Party
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyPostingRequirement (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		double dblCounterPartyPostingRequirement = counterPartyWindowMarginValue (dtValue) -
			counterPartyThreshold (dtValue);

		return 0. > dblCounterPartyPostingRequirement ? 0. : dblCounterPartyPostingRequirement;
	}

	/**
	 * Calculate the Gross Collateral Amount Required to be Posted
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The Gross Collateral Amount Required to be Posted
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double postingRequirement (
		final org.drip.analytics.date.JulianDate dtValue)
		throws java.lang.Exception
	{
		return org.drip.quant.common.NumberUtil.IsValid (_dblCurrentBalance) ? _dblCurrentBalance :
			bankPostingRequirement (dtValue) + counterPartyPostingRequirement (dtValue);
	}

	/**
	 * Generate the CollateralAmountEstimatorOutput Instance
	 * 
	 * @param dtValue The Valuation Date
	 * 
	 * @return The CollateralAmountEstimatorOutput Instance
	 */

	public org.drip.xva.hypothecation.CollateralAmountEstimatorOutput output (
		final org.drip.analytics.date.JulianDate dtValue)
	{
		if (null == dtValue) return null;

		org.drip.analytics.date.JulianDate dtBankMargin = dtValue.subtractDays (_cpgs.bankDefaultWindow());

		org.drip.analytics.date.JulianDate dtCounterPartyMargin = dtValue.subtractDays
			(_cpgs.counterPartyDefaultWindow());

		if (null == dtBankMargin || null == dtCounterPartyMargin) return null;

		org.drip.function.definition.R1ToR1[] aR1ToR1CounterPartyThreshold = _cgs.counterPartyThreshold();

		org.drip.function.definition.R1ToR1 r1ToR1BankThreshold = _cgs.bankThreshold();

		double dblValueDate = dtValue.julian();

		try {
			double dblBankWindowMarginValue = _bdi.interpolate (dtBankMargin.julian());

			double dblCounterPartyWindowMarginValue = _bdi.interpolate (dtCounterPartyMargin.julian());

			double dblBankThresholdValue = null == r1ToR1BankThreshold ? 0. : r1ToR1BankThreshold.evaluate
				(dblValueDate);

			double dblCounterPartyThresholdValue = null == aR1ToR1CounterPartyThreshold || null ==
				aR1ToR1CounterPartyThreshold[0] ? 0. : aR1ToR1CounterPartyThreshold[0].evaluate
					(dblValueDate);

			double dblBankPostingRequirement = dblBankWindowMarginValue - dblBankThresholdValue;
			dblBankPostingRequirement = 0. < dblBankPostingRequirement ? 0. : dblBankPostingRequirement;
			double dblCounterPartyPostingRequirement = dblCounterPartyWindowMarginValue -
				dblCounterPartyThresholdValue;
			dblCounterPartyPostingRequirement = 0. > dblCounterPartyPostingRequirement ? 0. :
				dblCounterPartyPostingRequirement;

			return new org.drip.xva.hypothecation.CollateralAmountEstimatorOutput (
				dtBankMargin,
				dtCounterPartyMargin,
				dblBankWindowMarginValue,
				dblBankThresholdValue,
				dblBankPostingRequirement,
				dblCounterPartyWindowMarginValue,
				dblCounterPartyThresholdValue,
				dblCounterPartyPostingRequirement,
				org.drip.quant.common.NumberUtil.IsValid (_dblCurrentBalance) ? _dblCurrentBalance :
					dblBankPostingRequirement + dblCounterPartyPostingRequirement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
