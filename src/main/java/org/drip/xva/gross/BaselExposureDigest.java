
package org.drip.xva.gross;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * BaselExposureDigest holds the Conservative Exposure Measures generated using the Standardized Basel
 * 	Approach. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 *  	for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives,
 *  	https://www.bis.org/bcbs/publ/d317.pdf.
 *  
 *  - Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties, Journal of Credit
 *  	Risk, 5 (4) 3-27.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BaselExposureDigest
{
	private double _expectedExposure = java.lang.Double.NaN;
	private double _exposureAtDefault = java.lang.Double.NaN;
	private double _expectedPositiveExposure = java.lang.Double.NaN;
	private double _effectiveExpectedExposure = java.lang.Double.NaN;
	private double _effectiveExpectedPositiveExposure = java.lang.Double.NaN;

	/**
	 * BaselExposureDigest Constructor
	 * 
	 * @param expectedExposure The Expected Exposure
	 * @param expectedPositiveExposure The Expected Positive Exposure
	 * @param effectiveExpectedExposure The Effective Expected Exposure
	 * @param effectiveExpectedPositiveExposure The Effective Expected Positive Exposure
	 * @param exposureAtDefault The Exposure At Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BaselExposureDigest (
		final double expectedExposure,
		final double expectedPositiveExposure,
		final double effectiveExpectedExposure,
		final double effectiveExpectedPositiveExposure,
		final double exposureAtDefault)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_expectedExposure = expectedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_expectedPositiveExposure = expectedPositiveExposure)
				||
			!org.drip.quant.common.NumberUtil.IsValid (_effectiveExpectedExposure =
				effectiveExpectedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_effectiveExpectedPositiveExposure =
				effectiveExpectedPositiveExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_exposureAtDefault = exposureAtDefault))
		{
			throw new java.lang.Exception ("BaselExposureDigest Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Expected Exposure
	 * 
	 * @return The Expected Exposure
	 */

	public double expectedExposure()
	{
		return _expectedExposure;
	}

	/**
	 * Retrieve the Expected Positive Exposure
	 * 
	 * @return The Expected Positive Exposure
	 */

	public double expectedPositiveExposure()
	{
		return _expectedPositiveExposure;
	}

	/**
	 * Retrieve the Effective Expected Exposure
	 * 
	 * @return The Effective Expected Exposure
	 */

	public double effectiveExpectedExposure()
	{
		return _effectiveExpectedExposure;
	}

	/**
	 * Retrieve the Effective Expected Positive Exposure
	 * 
	 * @return The Effective Expected Positive Exposure
	 */

	public double effectiveExpectedPositiveExposure()
	{
		return _effectiveExpectedPositiveExposure;
	}

	/**
	 * Retrieve the Exposure At Default
	 * 
	 * @return The Exposure At Default
	 */

	public double exposureAtDefault()
	{
		return _exposureAtDefault;
	}
}
