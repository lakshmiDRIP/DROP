
package org.drip.sample.sequence;

import org.drip.quant.common.FormatUtil;
import org.drip.sequence.metrics.*;
import org.drip.sequence.random.BoundedUniform;
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
 * DualRandomSequenceBound demonstrates the Computation of the Probabilistic Bounds for a Joint Realizations
 * 	of a Sample Random Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DualRandomSequenceBound {

	private static final void CauchySchwartzBound (
		final double dblLeft1,
		final double dblRight1,
		final double dblLeft2,
		final double dblRight2)
		throws Exception
	{
		SingleSequenceAgnosticMetrics ssam1 = new BoundedUniform (
			dblLeft1,
			dblRight1
		).sequence (
			50000,
			null
		);

		SingleSequenceAgnosticMetrics ssam2 = new BoundedUniform (
			dblLeft2,
			dblRight2
		).sequence (
			50000,
			null
		);

		DualSequenceAgnosticMetrics dsam = new DualSequenceAgnosticMetrics (
			ssam1,
			ssam2
		);

		System.out.println ("\t| " +
			FormatUtil.FormatDouble (ssam1.empiricalExpectation(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ssam2.empiricalExpectation(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (dsam.cauchySchwarzAbsoluteBound(), 1, 4, 1.) + " |"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println();

		System.out.println ("\t|-----------------------------|");

		System.out.println ("\t| MEAN #1 | MEAN #2 |  JOINT  |");

		System.out.println ("\t|-----------------------------|");

		CauchySchwartzBound (0., 1., 0., 1.);

		CauchySchwartzBound (0., 1., 1., 2.);

		CauchySchwartzBound (0., 1., 2., 3.);

		CauchySchwartzBound (0., 1., 3., 4.);

		CauchySchwartzBound (0., 1., 4., 5.);

		System.out.println ("\t|-----------------------------|");
	}
}
