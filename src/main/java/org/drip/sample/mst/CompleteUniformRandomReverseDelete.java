
package org.drip.sample.mst;

import org.drip.graph.mst.CompleteRandomGraph;
import org.drip.graph.mst.CompleteRandomGraphEnsemble;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>CompleteUniformRandomReverseDelete</i> demonstrates the Ensemble MST Length Analysis of a Complete
 * 	Graph built using Random Weights, and the Reverse-Delete Minimum Spanning Forest Generator. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bader, D. A., and G. Cong (2006): Fast Shared Memory Algorithms for computing the Minimum
 *  			Spanning Forests of Sparse Graphs <i>Journal of Parallel and Distributed Computing</i>
 *  			<b>66 (11)</b> 1366-1378
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree ALgorithm with Inverse-Ackerman Type Complexity
 *  			<i> Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047
 *  	</li>
 *  	<li>
 *  		Karger, D. R., P. N. Klein, and R. E. Tarjan (1995): A Randomized Linear-Time Algorithm to find
 *  			Minimum Spanning Trees <i> Journal of the Association for Computing Machinery</i> <b>42
 *  			(2)</b> 321-328
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2002): An Optimal Minimum Spanning Tree <i>Algorithm Journal of
 *  			the ACM</i> <b>49 (1)</b> 16-34
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Minimum Spanning Tree https://en.wikipedia.org/wiki/Minimum_spanning_tree
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/mst/README.md">Minimum Spanning Tree and Forest Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CompleteUniformRandomReverseDelete
{

	private static final double[] MSTLengthEnsemble (
		final int vertexCount,
		final int pathCount)
		throws Exception
	{
		double[] mstLengthEnsemble = new double[pathCount];

		for (int pathIndex = 0;
			pathIndex < pathCount;
			++pathIndex)
		{
			mstLengthEnsemble[pathIndex] = CompleteRandomGraphEnsemble.ReverseDelete (
				CompleteRandomGraph.Uniform (
					vertexCount
				)
			).length();
		}

		return mstLengthEnsemble;
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		int pathCount = 1000000;
		int[] vertexCountArray =
		{
			2,
			3,
			4,
			5,
			6,
			7,
			8,
			9,
		};

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println ("\t|   COMPLETE UNIFORM RANDOM REVERSE-DELETE MST LENGTH  ||");

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                           ||");

		System.out.println ("\t|        - Vertex Count                                ||");

		System.out.println ("\t|        - MST Length Minimum                          ||");

		System.out.println ("\t|        - MST Length Maximum                          ||");

		System.out.println ("\t|        - MST Length Average +- Error                 ||");

		System.out.println ("\t|------------------------------------------------------||");

		for (int vertexCount : vertexCountArray)
		{
			double[] mstLengthArray = MSTLengthEnsemble (
				vertexCount,
				pathCount
			);

			UnivariateDiscreteThin univariateDiscreteThin = new UnivariateDiscreteThin (
				mstLengthArray
			);

			System.out.println (
				"\t| [" + vertexCount + "] => " +
				FormatUtil.FormatDouble (univariateDiscreteThin.minimum(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (univariateDiscreteThin.maximum(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (univariateDiscreteThin.average(), 1, 6, 1.) + " +-" +
				FormatUtil.FormatDouble (univariateDiscreteThin.error(), 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
