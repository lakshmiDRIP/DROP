
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
	private org.drip.measure.statistics.UnivariateDiscreteThin _expectedExposureThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _exposureAtDefaultThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _expectedPositiveExposureThinStatistics =
		null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _effectiveExpectedExposureThinStatistics =
		null;
	private org.drip.measure.statistics.UnivariateDiscreteThin
		_effectiveExpectedPositiveExposureThinStatistics = null;

	/**
	 * BaselExposureDigest Constructor
	 * 
	 * @param expectedExposureThinStatistics  The Univariate Thin Statistics for the Expected Exposure
	 * @param expectedPositiveExposureThinStatistics The Univariate Thin Statistics for the Expected Positive
	 * 		Exposure
	 * @param effectiveExpectedExposureThinStatistics The Univariate Thin Statistics for the Effective
	 * 		Expected Exposure
	 * @param effectiveExpectedPositiveExposureThinStatistics The Univariate Thin Statistics for the
	 * 		Effective Expected Positive Exposure
	 * @param exposureAtDefaultThinStatistics The Univariate Thin Statistics for the Exposure At Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BaselExposureDigest (
		final org.drip.measure.statistics.UnivariateDiscreteThin expectedExposureThinStatistics,
		final org.drip.measure.statistics.UnivariateDiscreteThin expectedPositiveExposureThinStatistics,
		final org.drip.measure.statistics.UnivariateDiscreteThin effectiveExpectedExposureThinStatistics,
		final org.drip.measure.statistics.UnivariateDiscreteThin
			effectiveExpectedPositiveExposureThinStatistics,
		final org.drip.measure.statistics.UnivariateDiscreteThin exposureAtDefaultThinStatistics)
		throws java.lang.Exception
	{
		if (null == (_expectedExposureThinStatistics = expectedExposureThinStatistics) ||
			null == (_expectedPositiveExposureThinStatistics = expectedPositiveExposureThinStatistics) ||
			null == (_effectiveExpectedExposureThinStatistics = effectiveExpectedExposureThinStatistics) ||
			null == (_effectiveExpectedPositiveExposureThinStatistics =
				effectiveExpectedPositiveExposureThinStatistics) ||
			null == (_exposureAtDefaultThinStatistics = exposureAtDefaultThinStatistics))
		{
			throw new java.lang.Exception ("BaselExposureDigest Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Expected Exposure
	 * 
	 * @return The Univariate Thin Statistics for the Expected Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin expectedExposure()
	{
		return _expectedExposureThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Expected Positive Exposure
	 * 
	 * @return The Univariate Thin Statistics for the Expected Positive Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin expectedPositiveExposure()
	{
		return _expectedPositiveExposureThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Effective Expected Exposure
	 * 
	 * @return The Univariate Thin Statistics for the Effective Expected Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin effectiveExpectedExposure()
	{
		return _effectiveExpectedExposureThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Effective Expected Positive Exposure
	 * 
	 * @return The Univariate Thin Statistics for the Effective Expected Positive Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin effectiveExpectedPositiveExposure()
	{
		return _effectiveExpectedPositiveExposureThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Exposure At Default
	 * 
	 * @return The Univariate Thin Statistics for the Exposure At Default
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin exposureAtDefault()
	{
		return _exposureAtDefaultThinStatistics;
	}
}
