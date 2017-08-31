
package org.drip.sample.matrix;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.eigen.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * PrincipalComponent demonstrates how to generate the Principal eigenvalue and eigenvector for the Input
 *  Matrix.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PrincipalComponent {

	private static final void PrincipalComponentRun (
		final PowerIterationComponentExtractor pice)
		throws Exception
	{
		double dblCorr1 = 0.5 * Math.random();

		double dblCorr2 = 0.5 * Math.random();

		double[][] aadblA = {
			{     1.0, dblCorr1,      0.0},
			{dblCorr1,      1.0, dblCorr2},
			{     0.0, dblCorr2,      1.0}
		};

		EigenComponent ec = pice.principalComponent (aadblA);

		double[] adblEigenvector = ec.eigenvector();

		java.lang.String strDump = "[" + FormatUtil.FormatDouble (ec.eigenvalue(), 1, 4, 1.) + "] => ";

		for (int i = 0; i < adblEigenvector.length; ++i)
			strDump += FormatUtil.FormatDouble (adblEigenvector[i], 1, 4, 1.) + " | ";

		System.out.println (
			"\t{" +
			FormatUtil.FormatDouble (dblCorr1, 1, 4, 1.) + " ||" +
			FormatUtil.FormatDouble (dblCorr2, 1, 4, 1.) + "} => " + strDump
		);
	}

	public static final void main (
		final String[] astrArg)
		throws Exception
	{
		EnvManager.InitEnv ("");

		PowerIterationComponentExtractor pice = new PowerIterationComponentExtractor (
			30,
			0.000001,
			false
		);

		for (int i = 0; i < 50; ++i)
			PrincipalComponentRun (pice);
	}
}
