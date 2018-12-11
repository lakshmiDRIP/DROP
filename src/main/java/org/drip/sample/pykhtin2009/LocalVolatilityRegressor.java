
package org.drip.sample.pykhtin2009;

import org.drip.exposure.regression.LocalVolatilityGenerationControl;
import org.drip.exposure.regression.PykhtinPillar;
import org.drip.exposure.regression.PykhtinPillarDynamics;
import org.drip.function.definition.R1ToR1;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * LocalVolatilityRegressor is a Demonstration of the Exposure Regression Local Volatility Methodology of
 * 	Pykhtin (2009). The References are:
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
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LocalVolatilityRegressor
{

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int exposureCount = 1000;
		double exposureLow = 70.;
		double exposureHigh = 130.;
		double[] exposureArray = new double[exposureCount];

		LocalVolatilityGenerationControl localVolatilityGenerationControl =
			LocalVolatilityGenerationControl.Standard (exposureCount);

		for (int exposureIndex = 0; exposureIndex < exposureCount; ++exposureIndex)
		{
			exposureArray[exposureIndex] = exposureLow + (exposureHigh - exposureLow) * Math.random();
		}

		PykhtinPillarDynamics vertexRealization = PykhtinPillarDynamics.Standard (exposureArray);

		PykhtinPillar[] pillarVertexArray = vertexRealization.pillarVertexArray
			(localVolatilityGenerationControl);

		R1ToR1 localVolatilityR1ToR1 = vertexRealization.localVolatilityR1ToR1 (
			localVolatilityGenerationControl,
			pillarVertexArray
		);

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||       Pykhtin (2009) Terminal Brownian Bridge       ||");

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||                                                     ||");

		System.out.println ("\t||  L -> R:                                            ||");

		System.out.println ("\t||                                                     ||");

		System.out.println ("\t||      Terminal Numeraire                             ||");

		System.out.println ("\t||      Ranking Ordinal                                ||");

		System.out.println ("\t||      Uniform CDF                                    ||");

		System.out.println ("\t||      Gaussian Predictor Variate                     ||");

		System.out.println ("\t||      Local Volatility Estimate                      ||");

		System.out.println ("\t||-----------------------------------------------------||");

		for (PykhtinPillar pillarVertex : pillarVertexArray)
		{
			double exposure = pillarVertex.exposure();

			System.out.println (
				"\t|| " +
				FormatUtil.FormatDouble (exposure, 3, 2, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.order(), 3, 0, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.cdf(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.variate(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.localVolatility(), 2, 2, 1.) + " | " +
				FormatUtil.FormatDouble (localVolatilityR1ToR1.evaluate (exposure), 2, 2, 1.) + " ||"
			);
		}

		System.out.println ("\t||-----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
