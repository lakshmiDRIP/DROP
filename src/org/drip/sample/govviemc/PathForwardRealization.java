
package org.drip.sample.govviemc;

import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.sequence.PathRd;

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
 * PathForwardRealization demonstrates the Simulations of the Per-Path Forward Govvie Yield Nodes.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathForwardRealization {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] adblMean = new double[] {
			0.011,
			0.015,
			0.017,
			0.020,
			0.023,
			0.027,
			0.040
		};

		int iNumPath = 50;
		double dblLogNormalVolatility = 0.10;

		PathRd pRd = new PathRd (
			adblMean,
			dblLogNormalVolatility,
			true
		);

		double[][] aadblSequence = pRd.sequence (iNumPath);

		String strDump = "\t||";
		int iNumVertex = adblMean.length;

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------||");

		for (int iVertex = 0; iVertex < iNumVertex; ++iVertex)
			strDump = strDump + FormatUtil.FormatDouble (adblMean[iVertex], 1, 2, 100.) + "% |";

		System.out.println (strDump + "|");

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			strDump = "\t||";

			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex)
				strDump = strDump + FormatUtil.FormatDouble (aadblSequence[iPath][iVertex], 1, 2, 100.) + "% |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||-------------------------------------------------------||");

		System.out.println();
	}
}
