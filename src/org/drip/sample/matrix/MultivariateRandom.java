
package org.drip.sample.matrix;

import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.*;
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
 * MultivariateRandom demonstrates the Technique to generate Correlated Multivariate Random Variables using
 *  Cholesky Factorial Method.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateRandom {

	public static final void main (
		final String[] astrArg)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[][] aadblCorrelation = {
			{1.0, 0.3, 0.0},
			{0.3, 1.0, 0.1},
			{0.0, 0.1, 1.0}
		};

		UnivariateSequenceGenerator[] aUSG = new UnivariateSequenceGenerator[] {
			new BoxMullerGaussian (0., 1.),
			new BoxMullerGaussian (0., 1.),
			new BoxMullerGaussian (0., 1.)
		};

		MultivariateSequenceGenerator msg = new MultivariateSequenceGenerator (
			aUSG,
			aadblCorrelation
		);

		for (int j = 0; j < 100; ++j) {
			String strDump = "[" + j + "] => ";

			double[] adblRandom = msg.random();

			for (int i = 0; i < aadblCorrelation.length; ++i)
				strDump += FormatUtil.FormatDouble (adblRandom[i], 1, 4, 1.) + " | ";

			System.out.println ("\t" + strDump);
		}
	}
}
