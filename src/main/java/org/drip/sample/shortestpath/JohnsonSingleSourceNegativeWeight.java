
package org.drip.sample.shortestpath;

import java.util.List;

import org.drip.graph.bellmanford.JohnsonPathGenerator;
import org.drip.graph.core.Directed;
import org.drip.graph.core.Edge;
import org.drip.graph.core.Path;
import org.drip.graph.shortestpath.OptimalPathGenerator;
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
 * <i>JohnsonSingleSourceNegativeWeight</i> illustrates the Shortest Path Generation for a Directed Graph
 * 	using the Johnson Algorithm for a given Source with Negative Weight. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Black, P. E. (2004): Johnson's Algorithm https://xlinux.nist.gov/dads/HTML/johnsonsAlgorithm.html
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms</i>
 *  			3<sup>rd</sup> Edition <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Johnson, D. B. (1977): Efficient Algorithms for Shortest Paths in Sparse Networks <i>Journal of
 *  			the ACM</i> <b>24 (1)</b> 1-13
 *  	</li>
 *  	<li>
 *  		Suurballe, J. W. (1974): Disjoint Paths in a Network <i>Networks</i> <b>14 (2)</b> 125-145
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Johnson's Algorithm https://en.wikipedia.org/wiki/Johnson%27s_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/shortestpath/README.md">Source Destination Shortest Path Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JohnsonSingleSourceNegativeWeight
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		Directed<?> graph = new Directed<Double>();

		graph.addEdge (
			new Edge (
				"w",
				"z",
				 2.
			)
		);

		graph.addEdge (
			new Edge (
				"x",
				"w",
				 6.
			)
		);

		graph.addEdge (
			new Edge (
				"x",
				"y",
				 3.
			)
		);

		graph.addEdge (
			new Edge (
				"y",
				"w",
				 4.
			)
		);

		graph.addEdge (
			new Edge (
				"y",
				"z",
				 5.
			)
		);

		graph.addEdge (
			new Edge (
				"z",
				"x",
				-7.
			)
		);

		graph.addEdge (
			new Edge (
				"z",
				"y",
				-3.
			)
		);

		System.out.println (
			"\t|-----------------------------------------------------------------------------------------------------"
		);

		OptimalPathGenerator optimalPathGenerator = new JohnsonPathGenerator<Double> (
			graph,
			true,
			null
		);

		for (String sourceVertexName : graph.vertexNameSet())
		{
			List<Path> pathArray = optimalPathGenerator.singleSource (
				sourceVertexName
			);

			for (Path path : pathArray)
			{
				System.out.println (
					"\t| {" + path.sourceVertexName() + " -> " + path.destinationVertexName() + "} => " + 
					path.vertexList() + " | " +
					FormatUtil.FormatDouble (path.totalLength(), 4, 0, 1.)
				);
			}

			System.out.println (
				"\t|-----------------------------------------------------------------------------------------------------"
			);
		}

		EnvManager.TerminateEnv();
	}
}
