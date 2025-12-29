
package org.drip.sample.sequence;

import org.drip.measure.continuous.R1Distribution;
import org.drip.measure.discontinuous.*;
import org.drip.measure.exponential.PoissonDistribution;
import org.drip.sequence.metrics.*;
import org.drip.sequence.random.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*

 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>PoissonRandomSequenceBound</i> demonstrates the Computation of the Probabilistic Bounds for a Sample
 * 	Random Poisson Sequence.
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

public class PoissonRandomSequenceBound {

	private static final void Head (
		final String strHeader)
	{
		System.out.println();

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		System.out.println (strHeader);

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		System.out.println ("\t| SIZE ||               <-               TOLERANCES               ->               |");

		System.out.println ("\t|----------------------------------------------------------------------------------|");
	}

	private static final void ChernoffStirlingBounds (
		final UnivariateSequenceGenerator iidsg,
		final R1Distribution dist,
		final int[] aiSampleSize,
		final double[] adblTolerance)
		throws Exception
	{
		for (int iSampleSize : aiSampleSize) {
			PoissonSequenceAgnosticMetrics ssamDist = (PoissonSequenceAgnosticMetrics) iidsg.sequence (
				iSampleSize,
				dist
			);

			String strDump = "\t| " + FormatUtil.FormatDouble (iSampleSize, 3, 0, 1) + " => ";

			for (double dblTolerance : adblTolerance)
				strDump += FormatUtil.FormatDouble (ssamDist.chernoffStirlingUpperBound (dblTolerance), 1, 9, 1.) + " | ";

			System.out.println (strDump);
		}
	}

	/**
	 * Entry Point
	 * 
	 * @param args Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		Poisson poissonRandom = new Poisson (10.);

		PoissonDistribution poissonDistribution = new PoissonDistribution (10.);

		int[] aiSampleSize = new int[] {
			10, 20, 50, 100, 250
		};

		double[] adblTolerance = new double[] {
			5., 10., 15., 20., 25.
		};

		Head ("\t|        CHERNOFF-STIRLING BOUNDS    -     METRICS FROM UNDERLYING GENERATOR       |");

		ChernoffStirlingBounds (
			poissonRandom,
			poissonDistribution,
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		Head ("\t|      CHERNOFF-STIRLING BOUNDS    -     METRICS FROM EMPIRICAL DISTRIBUTION       |");

		ChernoffStirlingBounds (
			poissonRandom,
			null,
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|----------------------------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
