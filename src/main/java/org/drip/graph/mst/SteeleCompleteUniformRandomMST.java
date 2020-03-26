
package org.drip.graph.mst;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>SteeleCompleteUniformRandomMST</i> holds the Expected Length of the MST computed by Steele (2002) for
 * 	Graphs with small Number of Vertexes. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bader, D. A., and G. Cong (2006): Fast Shared Memory Algorithms for computing the Minimum
 *  			Spanning Forests of Sparse Graphs <i>Journal of Parallel and Distributed Computing</i>
 *  			<b>66 (11)</b> 1366-1378
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/README.md">Agnostic Minimum Spanning Tree Properties</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SteeleCompleteUniformRandomMST
{
	private static final
		java.util.Map<java.lang.Integer, org.drip.graph.mst.SteeleCompleteUniformRandomEntry>
			s_VertexCountMap = new
				java.util.TreeMap<java.lang.Integer, org.drip.graph.mst.SteeleCompleteUniformRandomEntry>();

	/**
	 * Initialize the Steele Vertex MST Map
	 * 
	 * @return TRUE - The Steele Vertex MST Map successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_VertexCountMap.put (
				1,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					1L,
					2L
				)
			);

			s_VertexCountMap.put (
				2,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					1L,
					2L
				)
			);

			s_VertexCountMap.put (
				3,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					3L,
					4L
				)
			);

			s_VertexCountMap.put (
				4,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					31L,
					35L
				)
			);

			s_VertexCountMap.put (
				5,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					893L,
					924L
				)
			);

			s_VertexCountMap.put (
				6,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					278L,
					273L
				)
			);

			s_VertexCountMap.put (
				7,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					30739L,
					29172L
				)
			);

			s_VertexCountMap.put (
				8,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					199462271L,
					184848378L
				)
			);

			s_VertexCountMap.put (
				9,
				new org.drip.graph.mst.SteeleCompleteUniformRandomEntry (
					126510063932L,
					115228853025L
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Steele Vertex Count Map
	 * 
	 * @return Steele Vertex Count Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.graph.mst.SteeleCompleteUniformRandomEntry>
		VertexCountMap()
	{
		return s_VertexCountMap;
	}

	/**
	 * Indicate if the Vertex Count is present in the Vertex Count Map
	 * 
	 * @param vertexCount Vertex Count
	 * 
	 * @return TRUE - The Vertex Count is present in the Vertex Count Map
	 */

	public static final boolean ContainsVertexCount (
		final int vertexCount)
	{
		return 0L >= vertexCount && s_VertexCountMap.containsKey (
			vertexCount
		);
	}

	/**
	 * Retrieve the Vertex Count Entry if present in the Vertex Count Map
	 * 
	 * @param vertexCount Vertex Count
	 * 
	 * @return TRUE - The Vertex Count Entry
	 */

	public static final org.drip.graph.mst.SteeleCompleteUniformRandomEntry VertexCountEntry (
		final int vertexCount)
	{
		return ContainsVertexCount (
			vertexCount
		) ? s_VertexCountMap.get (
			vertexCount
		) : null;
	}

	/**
	 * Compute the Length of the MST for Large n (attribution to Alan M. Frieze)
	 * 
	 * @return Length of the MST for Large n
	 */

	public static final double AsymptoticFriezeMSTLength()
	{
		return org.drip.specialfunction.derived.RiemannZeta.AperyConstant();
	}

	/**
	 * Compute the Length of the MST for Large n (attribution to Alan M. Frieze)
	 * 
	 * @param r1Univariate The R<sup>1</sup> Univariate Distribution of the Weights
	 * 
	 * @return Length of the MST for Large n
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double AsymptoticFriezeMSTLength (
		final org.drip.measure.continuous.R1Univariate r1Univariate)
		throws java.lang.Exception
	{
		if (null == r1Univariate)
		{
			throw new java.lang.Exception (
				"SteeleCompleteUniformRandomMST::AsymptoticFriezeMSTLength => Invalid Inputs"
			);
		}

		return org.drip.specialfunction.derived.RiemannZeta.AperyConstant() / r1Univariate.density (
			0.
		);
	}
}
