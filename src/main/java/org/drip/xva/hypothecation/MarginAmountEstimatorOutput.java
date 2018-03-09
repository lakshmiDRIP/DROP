
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
 * MarginAmountEstimatorOutput contains the Estimation Output of the Hypothecation Collateral that is to be
 *  Posted during a Single Run of a Collateral Hypothecation Group Valuation. The References are:
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

public class MarginAmountEstimatorOutput
{
	private double _postingRequirement = java.lang.Double.NaN;
	private double _bankWindowMarginValue = java.lang.Double.NaN;
	private double _bankPostingRequirement = java.lang.Double.NaN;
	private double _bankCollateralThreshold = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _bankMarginDate = null;
	private double _counterPartyWindowMarginValue = java.lang.Double.NaN;
	private double _counterPartyPostingRequirement = java.lang.Double.NaN;
	private double _counterPartyCollateralThreshold = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _counterPartyMarginDate = null;

	/**
	 * MarginAmountEstimatorOutput Constructor
	 * 
	 * @param bankMarginDate The Bank Margin Date
	 * @param counterPartyMarginDate The Counter Party Margin Date
	 * @param bankWindowMarginValue The Margin Value at the Bank Default Window
	 * @param bankCollateralThreshold The Bank Collateral Threshold
	 * @param bankPostingRequirement The Bank Collateral Posting Requirement
	 * @param counterPartyWindowMarginValue The Margin Value at the Counter Party Default Window
	 * @param counterPartyCollateralThreshold The Counter Party Collateral Threshold
	 * @param counterPartyPostingRequirement The Counter Party Collateral Posting Requirement
	 * @param postingRequirement The Total Collateral Posting Requirement
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarginAmountEstimatorOutput (
		final org.drip.analytics.date.JulianDate bankMarginDate,
		final org.drip.analytics.date.JulianDate counterPartyMarginDate,
		final double bankWindowMarginValue,
		final double bankCollateralThreshold,
		final double bankPostingRequirement,
		final double counterPartyWindowMarginValue,
		final double counterPartyCollateralThreshold,
		final double counterPartyPostingRequirement,
		final double postingRequirement)
		throws java.lang.Exception
	{
		if (null == (_bankMarginDate = bankMarginDate) ||
			null == (_counterPartyMarginDate = counterPartyMarginDate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_bankWindowMarginValue = bankWindowMarginValue) ||
			!org.drip.quant.common.NumberUtil.IsValid (_bankCollateralThreshold = bankCollateralThreshold) ||
			!org.drip.quant.common.NumberUtil.IsValid (_bankPostingRequirement = bankPostingRequirement) ||
			!org.drip.quant.common.NumberUtil.IsValid (_counterPartyWindowMarginValue =
				counterPartyWindowMarginValue) ||
			!org.drip.quant.common.NumberUtil.IsValid (_counterPartyCollateralThreshold =
				counterPartyCollateralThreshold) ||
			!org.drip.quant.common.NumberUtil.IsValid (_counterPartyPostingRequirement =
				counterPartyPostingRequirement) ||
			!org.drip.quant.common.NumberUtil.IsValid (_postingRequirement = postingRequirement))
		{
			throw new java.lang.Exception ("MarginAmountEstimatorOutput Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bank Margin Date
	 * 
	 * @return The Bank Margin Date
	 */

	public org.drip.analytics.date.JulianDate bankMarginDate()
	{
		return _bankMarginDate;
	}

	/**
	 * Retrieve the Counter Party Margin Date
	 * 
	 * @return The Counter Party Margin Date
	 */

	public org.drip.analytics.date.JulianDate counterPartyMarginDate()
	{
		return _counterPartyMarginDate;
	}

	/**
	 * Retrieve the Margin Value at the Bank Default Window
	 * 
	 * @return The Margin Value at the Bank Default Window
	 */

	public double bankWindowMarginValue()
	{
		return _bankWindowMarginValue;
	}

	/**
	 * Retrieve the Bank Collateral Threshold
	 * 
	 * @return The Bank Collateral Threshold
	 */

	public double bankCollateralThreshold()
	{
		return _bankCollateralThreshold;
	}

	/**
	 * Retrieve the Bank Posting Requirement
	 * 
	 * @return The Bank Posting Requirement
	 */

	public double bankPostingRequirement()
	{
		return _bankPostingRequirement;
	}

	/**
	 * Retrieve the Margin Value at the Counter Party Default Window
	 * 
	 * @return The Margin Value at the Counter Party Default Window
	 */

	public double counterPartyWindowMarginValue()
	{
		return _counterPartyWindowMarginValue;
	}

	/**
	 * Retrieve the Counter Party Collateral Threshold
	 * 
	 * @return The Counter Party Collateral Threshold
	 */

	public double counterPartyCollateralThreshold()
	{
		return _counterPartyCollateralThreshold;
	}

	/**
	 * Retrieve the Counter Party Posting Requirement
	 * 
	 * @return The Counter Party Posting Requirement
	 */

	public double counterPartyPostingRequirement()
	{
		return _counterPartyPostingRequirement;
	}

	/**
	 * Retrieve the Total Collateral Posting Requirement
	 * 
	 * @return The Total Collateral Posting Requirement
	 */

	public double postingRequirement()
	{
		return _postingRequirement;
	}
}
