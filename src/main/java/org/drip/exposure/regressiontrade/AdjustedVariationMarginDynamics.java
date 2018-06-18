
package org.drip.exposure.regressiontrade;

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
 * AdjustedVariationMarginDynamics builds the Dynamics of the Sparse Path Adjusted Variation Margin. The
 *  References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin Agreements,
 *  	http://www.risk-europe.com/protected/michael-pykhtin.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdjustedVariationMarginDynamics
{
	private org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
		_pathAdjustedVariationMarginEstimate = null;

	/**
	 * AdjustedVariationMarginDynamics Constructor
	 * 
	 * @param pathAdjustedVariationMarginEstimate The Path-wise Adjusted Variation Margin Estimate Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AdjustedVariationMarginDynamics (
		final org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
			pathAdjustedVariationMarginEstimate)
		throws java.lang.Exception
	{
		if (null == (_pathAdjustedVariationMarginEstimate = pathAdjustedVariationMarginEstimate))
		{
			throw new java.lang.Exception ("AdjustedVariationMarginDynamics Constructor");
		}

		int pathCount = _pathAdjustedVariationMarginEstimate.length;

		if (0 == pathCount)
		{
			throw new java.lang.Exception ("AdjustedVariationMarginDynamics Constructor");
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			if (null == _pathAdjustedVariationMarginEstimate[pathIndex])
			{
				throw new java.lang.Exception ("AdjustedVariationMarginDynamics Constructor");
			}
		}
	}

	/**
	 * Retrieve the Adjusted Variation Margin Estimate Array
	 * 
	 * @return The Adjusted Variation Margin Estimate Array
	 */

	public org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
		adjustedVariationMarginEstimateArray()
	{
		return _pathAdjustedVariationMarginEstimate;
	}

	/**
	 * Generate the Dynamics of the Sparse Pillar a.k.a Pykhtin (2009)
	 * 
	 * @return The Pykhtin Pillar Dynamics Array
	 */

	public org.drip.exposure.regression.PykhtinPillarDynamics[] pillarDynamics()
	{
		int exposureDateCount =
			_pathAdjustedVariationMarginEstimate[0].adjustedVariationMarginEstimateArray().length;

		int pathCount = _pathAdjustedVariationMarginEstimate.length;
		double[][] pathAdjustedVariationMargin = new double[exposureDateCount][pathCount];
		org.drip.exposure.regression.PykhtinPillarDynamics[] pykhtinPillarDynamicsArray = new
			org.drip.exposure.regression.PykhtinPillarDynamics[exposureDateCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			double[] pathAdjustedVariationMarginEstimateArray =
				_pathAdjustedVariationMarginEstimate[pathIndex].adjustedVariationMarginEstimateArray();

			for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
			{
				pathAdjustedVariationMargin[exposureDateIndex][pathIndex] =
					pathAdjustedVariationMarginEstimateArray[exposureDateIndex];
			}
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			if (null == (pykhtinPillarDynamicsArray[exposureDateIndex] =
				org.drip.exposure.regression.PykhtinPillarDynamics.Standard
					(pathAdjustedVariationMargin[exposureDateIndex])))
			{
				return null;
			}
		}

		return pykhtinPillarDynamicsArray;
	}
}
