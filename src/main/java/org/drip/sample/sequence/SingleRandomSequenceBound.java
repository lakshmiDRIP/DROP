
package org.drip.sample.sequence;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.sequence.metrics.*;
import org.drip.sequence.random.BoundedUniform;
import org.drip.service.env.EnvManager;

/*

 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>SingleRandomSequenceBound</i> demonstrates the Computation of the Probabilistic Bounds for a Sample
 * 	Random Sequence.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/sequence/README.md">IID Dual Poisson Sequence Bound</a></li>
 *  </ul>
 * <br><br>
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

		EnvManager.TerminateEnv();
	}
}
