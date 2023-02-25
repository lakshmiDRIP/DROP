
package org.drip.graph.decisiontree;

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
 * <i>ComplexityEstimate</i> implements the Asymptotic Size O (n) Complexity Estimates for Decision Trees
 * 	Generation and Validation. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/decisiontree/README.md">Property Estimates for Decision Trees</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComplexityEstimate
{
	private int _vertexCount = -1;

	/**
	 * ComplexityEstimate Constructor
	 * 
	 * @param vertexCount Vertex Count
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComplexityEstimate (
		final int vertexCount)
		throws java.lang.Exception
	{
		if (0 >= (_vertexCount = vertexCount))
		{
			throw new java.lang.Exception (
				"ComplexityEstimate Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Count of Vertexes
	 * 
	 * @return Count of Vertexes
	 */

	public int vertexCount()
	{
		return _vertexCount;
	}

	/**
	 * Retrieve the Number of Possible Graphs given the Vertex Count
	 * 
	 * @return Number of Possible Graphs
	 */

	public double numberOfPossibleGraphs()
	{
		return java.lang.Math.pow (
			2.,
			org.drip.numerical.common.NumberUtil.NCK (
				_vertexCount,
				2
			)
		);
	}

	/**
	 * Retrieve the Optimal Depth of the Decision Tree
	 * 
	 * @return Optimal Depth of the Decision Tree
	 */

	public double optimalDepth()
	{
		return _vertexCount * _vertexCount;
	}

	/**
	 * Retrieve the Upper Bound on the Number of Internal DT Nodes
	 * 
	 * @return Upper Bound on the Number of Internal DT Nodes
	 */

	public double internalNodesUpperBound()
	{
		return java.lang.Math.pow (
			2.,
			_vertexCount * _vertexCount
		);
	}

	/**
	 * Retrieve the Upper Bound on the Number of Edges per Graph
	 * 
	 * @return Upper Bound on the Number of Edges per Graph
	 */

	public double graphEdgeUpperBound()
	{
		return _vertexCount * _vertexCount;
	}

	/**
	 * Retrieve the Upper Bound on the Number of Decision Tree Comparisons
	 * 
	 * @return Upper Bound on the Number of Decision Tree Comparisons
	 */

	public double comparisonUpperBound()
	{
		return _vertexCount * _vertexCount * _vertexCount * _vertexCount;
	}

	/**
	 * Retrieve the Upper Bound on the Number of Generated Decision Trees
	 * 
	 * @return Upper Bound on the Number of Generated Decision Trees
	 */

	public double generatedCountUpperBound()
	{
		double vertexCountSquared = _vertexCount * _vertexCount;

		return java.lang.Math.pow (
			vertexCountSquared,
			2. + vertexCountSquared
		);
	}

	/**
	 * Compute the Decision Tree Generation Complexity Estimation Metrics
	 * 
	 * @return Decision Tree Generation Complexity Estimation Metrics
	 */

	public org.drip.graph.decisiontree.GenerationComplexity generationMetrics()
	{
		double vertexCountSquared = _vertexCount * _vertexCount;

		try
		{
			return new org.drip.graph.decisiontree.GenerationComplexity (
				java.lang.Math.pow (
					2.,
					org.drip.numerical.common.NumberUtil.NCK (
						_vertexCount,
						2
					)
				),
				vertexCountSquared,
				java.lang.Math.pow (
					2.,
					vertexCountSquared
				),
				vertexCountSquared,
				vertexCountSquared * vertexCountSquared,
				java.lang.Math.pow (
					vertexCountSquared,
					2. + vertexCountSquared
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Upper Bound on the Number of Edge Weight Permutations
	 * 
	 * @return Upper Bound on the Number of Edge Weight Permutations
	 */

	public double edgeWeightPermutationsUpperBound()
	{
		return org.drip.numerical.common.NumberUtil.Factorial (
			_vertexCount * _vertexCount
		);
	}

	/**
	 * Retrieve the Upper Bound on the MST Algorithm Runtime
	 * 
	 * @return Upper Bound on the MST Algorithm Runtime
	 */

	public double mstRuntimeUpperBound()
	{
		return _vertexCount * _vertexCount;
	}

	/**
	 * Retrieve the Upper Bound on the Time required to check all Permutations
	 * 
	 * @return Upper Bound on the Time required to check all Permutations
	 */

	public double permutationsCheckUpperBound()
	{
		return org.drip.numerical.common.NumberUtil.Factorial (
			_vertexCount * _vertexCount + 1
		);
	}

	/**
	 * Retrieve the Upper Bound on the Time for finding the Optimal DT Finding Time
	 * 
	 * @return Upper Bound on the Time for finding the Optimal DT Finding Time
	 */

	public double optimalDTFinderUpperBound()
	{
		double vertexCountSquared = _vertexCount * _vertexCount;

		return java.lang.Math.pow (
			2.,
			org.drip.numerical.common.NumberUtil.NCK (
				_vertexCount,
				2
			)
		) * java.lang.Math.pow (
			vertexCountSquared,
			2. + vertexCountSquared
		) * org.drip.numerical.common.NumberUtil.Factorial (
			(int) vertexCountSquared + 1
		);
	}

	/**
	 * Generate the Decision Tree Validation Metrics
	 * 
	 * @return The Decision Tree Validation Metrics
	 */

	public org.drip.graph.decisiontree.ValidationComplexity validationMetrics()
	{
		int vertexCountSquared = _vertexCount * _vertexCount;

		try
		{
			return new org.drip.graph.decisiontree.ValidationComplexity (
				org.drip.numerical.common.NumberUtil.Factorial (
					vertexCountSquared
				),
				vertexCountSquared,
				org.drip.numerical.common.NumberUtil.Factorial (
					vertexCountSquared + 1
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Complete Decision Tree Metrics
	 * 
	 * @return The Complete Decision Tree Metrics
	 */

	public org.drip.graph.decisiontree.ComplexityMetrics metrics()
	{
		int vertexCountSquared = _vertexCount * _vertexCount;

		try
		{
			return new org.drip.graph.decisiontree.ComplexityMetrics (
				new org.drip.graph.decisiontree.GenerationComplexity (
					java.lang.Math.pow (
						2.,
						org.drip.numerical.common.NumberUtil.NCK (
							_vertexCount,
							2
						)
					),
					vertexCountSquared,
					java.lang.Math.pow (
						2.,
						vertexCountSquared
					),
					vertexCountSquared,
					vertexCountSquared * vertexCountSquared,
					java.lang.Math.pow (
						vertexCountSquared,
						2. + vertexCountSquared
					)
				),
				new org.drip.graph.decisiontree.ValidationComplexity (
					org.drip.numerical.common.NumberUtil.Factorial (
						vertexCountSquared
					),
					vertexCountSquared,
					org.drip.numerical.common.NumberUtil.Factorial (
						vertexCountSquared + 1
					)
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
