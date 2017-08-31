
package org.drip.sample.sequence;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1.*;
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
 * SingleRandomSequenceBound demonstrates the Computation of the Probabilistic Bounds for a Sample Random
 * 	Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SingleRandomSequenceBound {

	private static final void MarkovBound (
		final SingleSequenceAgnosticMetrics sm,
		final double dblLevel,
		final R1ToR1 au)
		throws Exception
	{
		System.out.println (
			(null == au ? "\tMarkov Base Bound        [" :  "\tMarkov Function Bound    [") +
			FormatUtil.FormatDouble (dblLevel, 1, 2, 1.) + "] : " +
			FormatUtil.FormatDouble (sm.markovUpperProbabilityBound (dblLevel, au), 1, 4, 1.) + " |" +
			FormatUtil.FormatDouble (1. - dblLevel, 1, 4, 1.)
		);
	}

	private static final void ChebyshevBound (
		final SingleSequenceAgnosticMetrics sm,
		final double dblLevel)
		throws Exception
	{
		System.out.println (
			"\tChebyshev Bound          [" +
			FormatUtil.FormatDouble (dblLevel, 1, 2, 1.) + "] : " +
			FormatUtil.FormatDouble (sm.chebyshevBound (dblLevel).lower(), 1, 4, 1.) + " |" +
			FormatUtil.FormatDouble (sm.chebyshevBound (dblLevel).upper(), 1, 4, 1.) + " |" +
			FormatUtil.FormatDouble (1. - 2. * dblLevel, 1, 4, 1.)
		);
	}

	private static final void ChebyshevCantelliBound (
		final SingleSequenceAgnosticMetrics sm,
		final double dblLevel)
		throws Exception
	{
		System.out.println (
			"\tChebyshev Cantelli Bound [" +
			FormatUtil.FormatDouble (dblLevel, 1, 2, 1.) + "] : " +
			"        |" +
			FormatUtil.FormatDouble (sm.chebyshevCantelliBound (dblLevel).upper(), 1, 4, 1.) + " |" +
			FormatUtil.FormatDouble (1. - dblLevel, 1, 4, 1.)
		);
	}

	private static final void CentralMomentBound (
		final SingleSequenceAgnosticMetrics sm,
		final double dblLevel,
		final int iMoment)
		throws Exception
	{
		System.out.println (
			"\tMoment #" + iMoment + " Bound          [" +
			FormatUtil.FormatDouble (dblLevel, 1, 2, 1.) + "] : " +
			FormatUtil.FormatDouble (sm.centralMomentBound (dblLevel, iMoment).lower(), 1, 4, 1.) + " |" +
			FormatUtil.FormatDouble (sm.centralMomentBound (dblLevel, iMoment).upper(), 1, 4, 1.) + " |" +
			FormatUtil.FormatDouble (1. - 2. * dblLevel, 1, 4, 1.)
		);
	}

	private static final void MarkovBound (
		final SingleSequenceAgnosticMetrics sm,
		final R1ToR1 au)
		throws Exception
	{
		MarkovBound (sm, 0.20, au);

		MarkovBound (sm, 0.40, au);

		MarkovBound (sm, 0.59, au);

		MarkovBound (sm, 0.79, au);

		MarkovBound (sm, 0.99, au);
	}

	private static final void ChebyshevBound (
		final SingleSequenceAgnosticMetrics sm)
		throws Exception
	{
		ChebyshevBound (sm, 0.20);

		ChebyshevBound (sm, 0.25);

		ChebyshevBound (sm, 0.30);

		ChebyshevBound (sm, 0.35);

		ChebyshevBound (sm, 0.40);
	}

	private static final void ChebyshevCantelliBound (
		final SingleSequenceAgnosticMetrics sm)
		throws Exception
	{
		ChebyshevCantelliBound (sm, 0.20);

		ChebyshevCantelliBound (sm, 0.25);

		ChebyshevCantelliBound (sm, 0.30);

		ChebyshevCantelliBound (sm, 0.35);

		ChebyshevCantelliBound (sm, 0.40);
	}

	private static final void CentralMomentBound (
		final SingleSequenceAgnosticMetrics sm,
		final int iMoment)
		throws Exception
	{
		CentralMomentBound (sm, 0.20, iMoment);

		CentralMomentBound (sm, 0.25, iMoment);

		CentralMomentBound (sm, 0.30, iMoment);

		CentralMomentBound (sm, 0.35, iMoment);

		CentralMomentBound (sm, 0.40, iMoment);
	}

	private static final void SequenceGenerationRun (
		final SingleSequenceAgnosticMetrics sm)
		throws Exception
	{
		System.out.println ("\tExpectation                      : " + FormatUtil.FormatDouble (sm.empiricalExpectation(), 1, 4, 1.));

		System.out.println ("\tVariance                         : " + FormatUtil.FormatDouble (sm.empiricalVariance(), 1, 4, 1.));

		System.out.println ("\t---------------------------------------------------");

		MarkovBound (sm, new ExponentialTension (Math.E, 0.1));

		System.out.println ("\t---------------------------------------------------");

		MarkovBound (sm, new ExponentialTension (Math.E, 1.0));

		System.out.println ("\t---------------------------------------------------");

		MarkovBound (sm, new ExponentialTension (Math.E, 5.0));

		System.out.println ("\t---------------------------------------------------");

		MarkovBound (sm, null);

		System.out.println ("\t---------------------------------------------------");

		ChebyshevBound (sm);

		System.out.println ("\t---------------------------------------------------");

		ChebyshevCantelliBound (sm);

		System.out.println ("\t---------------------------------------------------");

		CentralMomentBound (sm, 3);

		System.out.println ("\t---------------------------------------------------");

		CentralMomentBound (sm, 4);

		System.out.println ("\t---------------------------------------------------");

		CentralMomentBound (sm, 5);
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		/* System.out.println ("\t---------------------------------------------------");

		System.out.println ("\t|              BOUNDED GAUSSIAN RUN               |");

		System.out.println ("\t---------------------------------------------------");

		SequenceGenerationRun (new BoundedGaussian (0.5, 1., 0., 1.).sequence (50000, null));

		System.out.println ("\t---------------------------------------------------");

		System.out.println(); */

		System.out.println ("\t---------------------------------------------------");

		System.out.println ("\t|              BOUNDED UNIFORM RUN                |");

		System.out.println ("\t---------------------------------------------------");

		SequenceGenerationRun (new BoundedUniform (0., 1.).sequence (50000, null));

		System.out.println ("\t---------------------------------------------------");
	}
}
