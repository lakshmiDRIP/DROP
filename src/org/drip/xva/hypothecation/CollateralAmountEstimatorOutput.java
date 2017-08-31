
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
 * CollateralAmountEstimatorOutput contains the Estimation Output of the Hypothecation Collateral that is to
 *  be Posted during a Single Run of a Collateral Hypothecation Group Valuation. The References are:
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

public class CollateralAmountEstimatorOutput {
	private double _dblPostingRequirement = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtBankMargin = null;
	private double _dblBankWindowMarginValue = java.lang.Double.NaN;
	private double _dblBankPostingRequirement = java.lang.Double.NaN;
	private double _dblBankCollateralThreshold = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtCounterPartyMargin = null;
	private double _dblCounterPartyWindowMarginValue = java.lang.Double.NaN;
	private double _dblCounterPartyPostingRequirement = java.lang.Double.NaN;
	private double _dblCounterPartyCollateralThreshold = java.lang.Double.NaN;

	/**
	 * CollateralAmountEstimatorOutput Constructor
	 * 
	 * @param dtBankMargin The Bank Margin Date
	 * @param dtCounterPartyMargin The Counter Party Margin Date
	 * @param dblBankWindowMarginValue The Margin Value at the Bank Default Window
	 * @param dblBankCollateralThreshold The Bank Collateral Threshold
	 * @param dblBankPostingRequirement The Bank Collateral Posting Requirement
	 * @param dblCounterPartyWindowMarginValue The Margin Value at the Counter Party Default Window
	 * @param dblCounterPartyCollateralThreshold The Counter Party Collateral Threshold
	 * @param dblCounterPartyPostingRequirement The Counter Party Collateral Posting Requirement
	 * @param dblPostingRequirement The Total Collateral Posting Requirement
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralAmountEstimatorOutput (
		final org.drip.analytics.date.JulianDate dtBankMargin,
		final org.drip.analytics.date.JulianDate dtCounterPartyMargin,
		final double dblBankWindowMarginValue,
		final double dblBankCollateralThreshold,
		final double dblBankPostingRequirement,
		final double dblCounterPartyWindowMarginValue,
		final double dblCounterPartyCollateralThreshold,
		final double dblCounterPartyPostingRequirement,
		final double dblPostingRequirement)
		throws java.lang.Exception
	{
		if (null == (_dtBankMargin = dtBankMargin) || null == (_dtCounterPartyMargin = dtCounterPartyMargin)
			|| !org.drip.quant.common.NumberUtil.IsValid (_dblBankWindowMarginValue =
				dblBankWindowMarginValue) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblBankCollateralThreshold = dblBankCollateralThreshold) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblBankPostingRequirement =
							dblBankPostingRequirement) || !org.drip.quant.common.NumberUtil.IsValid
								(_dblCounterPartyWindowMarginValue = dblCounterPartyWindowMarginValue) ||
									!org.drip.quant.common.NumberUtil.IsValid
										(_dblCounterPartyCollateralThreshold =
											dblCounterPartyCollateralThreshold) ||
												!org.drip.quant.common.NumberUtil.IsValid
													(_dblCounterPartyPostingRequirement =
														dblCounterPartyPostingRequirement) ||
															!org.drip.quant.common.NumberUtil.IsValid
																(_dblPostingRequirement =
																	dblPostingRequirement))
			throw new java.lang.Exception ("CollateralAmountEstimatorOutput Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Bank Margin Date
	 * 
	 * @return The Bank Margin Date
	 */

	public org.drip.analytics.date.JulianDate bankMarginDate()
	{
		return _dtBankMargin;
	}

	/**
	 * Retrieve the Counter Party Margin Date
	 * 
	 * @return The Counter Party Margin Date
	 */

	public org.drip.analytics.date.JulianDate counterPartyMarginDate()
	{
		return _dtCounterPartyMargin;
	}

	/**
	 * Retrieve the Margin Value at the Bank Default Window
	 * 
	 * @return The Margin Value at the Bank Default Window
	 */

	public double bankWindowMarginValue()
	{
		return _dblBankWindowMarginValue;
	}

	/**
	 * Retrieve the Bank Collateral Threshold
	 * 
	 * @return The Bank Collateral Threshold
	 */

	public double bankCollateralThreshold()
	{
		return _dblBankCollateralThreshold;
	}

	/**
	 * Retrieve the Bank Posting Requirement
	 * 
	 * @return The Bank Posting Requirement
	 */

	public double bankPostingRequirement()
	{
		return _dblBankPostingRequirement;
	}

	/**
	 * Retrieve the Margin Value at the Counter Party Default Window
	 * 
	 * @return The Margin Value at the Counter Party Default Window
	 */

	public double counterPartyWindowMarginValue()
	{
		return _dblCounterPartyWindowMarginValue;
	}

	/**
	 * Retrieve the Counter Party Collateral Threshold
	 * 
	 * @return The Counter Party Collateral Threshold
	 */

	public double counterPartyCollateralThreshold()
	{
		return _dblCounterPartyCollateralThreshold;
	}

	/**
	 * Retrieve the Counter Party Posting Requirement
	 * 
	 * @return The Counter Party Posting Requirement
	 */

	public double counterPartyPostingRequirement()
	{
		return _dblCounterPartyPostingRequirement;
	}

	/**
	 * Retrieve the Total Collateral Posting Requirement
	 * 
	 * @return The Total Collateral Posting Requirement
	 */

	public double postingRequirement()
	{
		return _dblPostingRequirement;
	}
}
