
package org.drip.graph.adjacencymatrix;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.graph.core.Edge;
import org.drip.graph.core.Network;
import org.drip.graph.core.Vertex;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2024 Lakshmi Krishnamurthy
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
 * <i>GkOperator</i> implements the G<sup>k</sup> Adjacency Linear Operator and its Norm/Spectral Radius. The
 *  References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dunford, N., and J. Schwartz (1963): <i>Linear Operators II: Spectral Theory: Self-adjoint
 *  			Operators in the Hilbert Space</i> <b>Wiley Interscience</b> Hoboken NJ
 *  	</li>
 *  	<li>
 *  		Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 *  			<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b> Cambridge MA
 *  	</li>
 *  	<li>
 *  		Guo, J. M., Z. W. Wang, and X. Li (2019): Sharp Upper Bounds of the Spectral Radius of a Graph
 *  			<i>Discrete Mathematics</i> <b>342 (9)</b> 2559-2563
 *  	</li>
 *  	<li>
 *  		Lax, P. D. (2002): <i>Functional Analysis</i> <b>Wiley Interscience</b> Hoboken NJ
 *  	</li>
 *  	<li>
 *  		Wikipedia (2024): Spectral Radius https://en.wikipedia.org/wiki/Spectral_radius
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/adjacencymatrix/README.md">Adjacency Matrix Representation of Graph</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class GkLinearOperator extends GkToR1
{

	/**
	 * <i>GkLinearOperator</i> Constructor
	 * 
	 * @param vertexFunction R<sup>1</sup> to R<sup>1</sup> Vertex Function
	 * @param k Norm Exponent
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public GkLinearOperator (
		final R1ToR1 vertexFunction,
		final int k)
		throws Exception
	{
		super (vertexFunction, k);
	}

	/**
	 * Evaluate the Value across the Specified Graph Vertex
	 * 
	 * @param graphNetwork Graph Network
	 * @param vertexName Vertex Name
	 * 
	 * @return Value across all the Graph Vertexes
	 * 
	 * @throws Exception Thrown if the Graph is Invalid
	 */

	public double evaluate (
		final Network<Double> graphNetwork,
		final String vertexName)
		throws Exception
	{
		if (null == graphNetwork || null == vertexName || graphNetwork.containsVertex (vertexName)) {
			throw new Exception ("GkLinearOperator::evaluate => Invalid Inputs");
		}

		double value = 0.;

		R1ToR1 vertexFunction = vertexFunction();

		Map<String, Vertex<?>> vertexMap = graphNetwork.vertexMap();

		for (Edge edge : vertexMap.get (vertexName).edgeMap().values()) {
			value += vertexFunction.evaluate ((double) vertexMap.get (edge.destinationVertexName()).value());
		}

		return value;
	}

	/**
	 * Compute the <i>GuoWangLi2019Bound</i> Instance
	 * 
	 * @param k Guo, Wang, and Li (2019) <code>k</code> Parameter
	 * 
	 * @return <i>GuoWangLi2019Bound</i> Instance
	 */

	public GuoWangLi2019Bound guoWangLi2019SpectralRadiusUpperBound (
		final Network<Double> graphNetwork,
		final int k)
	{
		if (null == graphNetwork) {
			return GuoWangLi2019Bound.Unset (k);
		}

		int m = graphNetwork.vertexCount();

		int n = graphNetwork.edgeCount();

		double kMinus3 = k - 3;
		double nMinusM = n - m;
		boolean validK = 3 <= k && k <= n;
		boolean validEdgeVertexDifferential =
			0.5 * (k - 2) * kMinus3 <= nMinusM && nMinusM <= 0.5 * (k - 2) * k;

		return new GuoWangLi2019Bound (
			k,
			validK,
			validEdgeVertexDifferential,
			validK && validEdgeVertexDifferential ?
				2. * m - n - k + 2.5 + Math.sqrt (2. * m - n - k + 2.5 + Math.sqrt (2. * m - 2. * n + 2.25))
				: Double.NaN
		);
	}

	/**
	 * Compute the Spectral Radius
	 * 
	 * @return Spectral Radius
	 */

	public abstract double spectralRadius();
}
