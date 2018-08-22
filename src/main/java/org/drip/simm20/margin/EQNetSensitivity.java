
package org.drip.simm20.margin;

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
 * EQNetSensitivity holds the ISDA SIMM 2.0 Net Sensitivity and Concentration Factor within a across EQ Risk
 *  Factor Buckets. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EQNetSensitivity
{
	private java.util.Map<java.lang.Integer, org.drip.simm20.margin.EQBucketNetSensitivity> _bucketMap = new
		java.util.HashMap<java.lang.Integer, org.drip.simm20.margin.EQBucketNetSensitivity>();

	/**
	 * EQNetSensitivity Constructor
	 * 
	 * @param bucketMap The Bucket Map of the Net Sensitivities
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EQNetSensitivity (
		final java.util.Map<java.lang.Integer, org.drip.simm20.margin.EQBucketNetSensitivity> bucketMap)
		throws java.lang.Exception
	{
		if (null == (_bucketMap = bucketMap) || 0 == _bucketMap.size())
		{
			throw new java.lang.Exception ("EQNetSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bucket Map of the Net Sensitivities
	 * 
	 * @return The Bucket Map of the Net Sensitivities
	 */

	public java.util.Map<java.lang.Integer, org.drip.simm20.margin.EQBucketNetSensitivity> bucketMap()
	{
		return _bucketMap;
	}

	/**
	 * Compute the Equity Delta Margin
	 * 
	 * @param eqSensitivitySettings The Equity Sensitivity Settings
	 * 
	 * @return The Equity Delta Margin
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public double deltaMargin (
		final org.drip.simm20.parameters.EQSensitivitySettings eqSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == eqSensitivitySettings)
		{
			throw new java.lang.Exception ("EQNetSensitivity::deltaMargin => Invalid Inputs");
		}

		double deltaMargin = 0.;

		int bucketSize = _bucketMap.size() - 1;

		org.drip.measure.stochastic.LabelCorrelation bucketCorrelation =
			eqSensitivitySettings.bucketCorrelation();

		for (int bucketIndexI = 1; bucketIndexI <= bucketSize; ++bucketIndexI)
		{
			double netSensitivityI = _bucketMap.get (bucketIndexI).weightedAndAdjusted();

			for (int bucketIndexJ = 1; bucketIndexJ <= bucketSize; ++bucketIndexJ)
			{
				deltaMargin = deltaMargin + netSensitivityI *
					_bucketMap.get (bucketIndexJ).weightedAndAdjusted() * bucketCorrelation.entry (
						"" + bucketIndexI,
						"" + bucketIndexJ
					);
			}
		}

		return java.lang.Math.sqrt (deltaMargin) + _bucketMap.get (-1).weighted();
	}
}
