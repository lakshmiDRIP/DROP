
package org.drip.sample.coveringnumber;

import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spaces.cover.L1R1CoveringBounds;

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
 * BoundedFunction demonstrates Computation of the Lower and the Upper Bounds for Functions that are
 *  absolutely Bounded.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedFunction {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		L1R1CoveringBounds bfcnVariation = new L1R1CoveringBounds (
			1.,
			1.,
			Double.NaN
		);

		L1R1CoveringBounds bfcnBounded = new L1R1CoveringBounds (
			1.,
			1.,
			1.
		);

		double[] adblCover = new double[] {
			0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08
		};

		System.out.println ("\n\t||------------------------------------------||");

		System.out.println ("\t||    Bounded  Function  Covering Number    ||");

		System.out.println ("\t||    -------  --------  -------- ------    ||");

		System.out.println ("\t|| L -> R:                                  ||");

		System.out.println ("\t||   Variation Bound Covering Number Lower  ||");

		System.out.println ("\t||   Variation Bound Covering Number Upper  ||");

		System.out.println ("\t||    Absolute Bound Covering Number Lower  ||");

		System.out.println ("\t||    Absolute Bound Covering Number Upper  ||");

		System.out.println ("\t||------------------------------------------||");

		for (double dblCover : adblCover)
			System.out.println ("\t|| [" + FormatUtil.FormatDouble (dblCover, 1, 2, 1.) + "] => " +
				FormatUtil.FormatDouble (Math.log (bfcnVariation.logLowerBound (dblCover)), 1, 2, 1.) + " ->" +
				FormatUtil.FormatDouble (Math.log (bfcnVariation.logUpperBound (dblCover)), 1, 2, 1.) + " | " +
				FormatUtil.FormatDouble (Math.log (bfcnBounded.logLowerBound (dblCover)), 1, 2, 1.) + " ->" +
				FormatUtil.FormatDouble (Math.log (bfcnBounded.logUpperBound (dblCover)), 1, 2, 1.) + " ||"
			);

		System.out.println ("\t||------------------------------------------||");
	}
}
