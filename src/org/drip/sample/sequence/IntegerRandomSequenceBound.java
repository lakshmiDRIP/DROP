
package org.drip.sample.sequence;

import org.drip.measure.continuous.R1;
import org.drip.measure.discrete.*;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.metrics.*;
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
 * IntegerRandomSequenceBound demonstrates the Computation of the Probabilistic Bounds for a Sample Random
 * 	Integer Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntegerRandomSequenceBound {

	private static final void IntegerBounds (
		final UnivariateSequenceGenerator iidsg,
		final R1 dist,
		final int[] aiSampleSize)
		throws Exception
	{
		for (int iSampleSize : aiSampleSize) {
			IntegerSequenceAgnosticMetrics ssamDist = (IntegerSequenceAgnosticMetrics) iidsg.sequence (
				iSampleSize,
				dist
			);

			String strDump = "\t| " + FormatUtil.FormatDouble (iSampleSize, 3, 0, 1) + " => ";

			strDump +=
				FormatUtil.FormatDouble (ssamDist.probGreaterThanZeroUpperBound(), 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (ssamDist.probEqualToZeroUpperBound(), 1, 9, 1.) + " | ";

			System.out.println (strDump);
		}
	}

	public static void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		BoundedUniformInteger buiSequence = new BoundedUniformInteger (
			0,
			100
		);

		BoundedUniformIntegerDistribution buiDistribution = new BoundedUniformIntegerDistribution (
			0,
			100
		);

		int[] aiSampleSize = new int[] {
			10, 20, 50, 100, 250
		};

		System.out.println();

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		System.out.println ("\t| Generating Integer Random Sequence Metrics");

		System.out.println ("\t| \tL -> R:");

		System.out.println ("\t| \t\tSample Size");

		System.out.println ("\t| \t\tUpper Probability Bound for X != 0");

		System.out.println ("\t| \t\tUpper Probability Bound for X = 0");

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		System.out.println ("\t| Generating Metrics off of Underlying Distribution");

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		IntegerBounds (
			buiSequence,
			buiDistribution,
			aiSampleSize
		);

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		System.out.println();

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		System.out.println ("\t| Generating Metrics off of Empirical Distribution");

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		IntegerBounds (
			buiSequence,
			null,
			aiSampleSize
		);

		System.out.println ("\t|----------------------------------------------------------------------------------|");
	}
}
