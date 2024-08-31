
package org.drip.graph.adjacencymatrix;

import org.drip.function.definition.R1ToR1;
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
 * <i>GkToR1</i> implements the Space Map induced by L<sup>k</sup> Norm on the R<sup>1</sup> Functions over
 * 	the Vertexes of a Graph. The References are:
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

public class GkToR1
{
	private int _k = Integer.MIN_VALUE;
	private R1ToR1 _vertexFunction = null;

	/**
	 * <i>GkToR1</i> Constructor
	 * 
	 * @param vertexFunction R<sup>1</sup> to R<sup>1</sup> Vertex Function
	 * @param k Norm Exponent
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public GkToR1 (
		final R1ToR1 vertexFunction,
		final int k)
		throws Exception
	{
		if (null == (_vertexFunction = vertexFunction)) {
			throw new Exception ("GkToR1 Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the R<sup>1</sup> to R<sup>1</sup> Vertex Function
	 * 
	 * @return R<sup>1</sup> to R<sup>1</sup> Vertex Function
	 */

	public R1ToR1 vertexFunction()
	{
		return _vertexFunction;
	}

	/**
	 * Evaluate the Value across all the Graph Vertexes
	 * 
	 * @param graphNetwork Graph Network
	 * 
	 * @return Value across all the Graph Vertexes
	 * 
	 * @throws Exception Thrown if the Graph is Invalid
	 */

	public double evaluate (
		final Network<Double> graphNetwork)
		throws Exception
	{
		if (null == graphNetwork) {
			throw new Exception ("GkToR1::evaluate => Invalid Inputs");
		}

		double value = 0.;

		for (Vertex<?> vertex : graphNetwork.vertexMap().values()) {
			value += _vertexFunction.evaluate ((double) vertex.value());
		}

		return value;
	}

	/**
	 * Evaluate the Norm across all the Graph Vertexes
	 * 
	 * @param graphNetwork Graph Network
	 * 
	 * @return Norm across all the Graph Vertexes
	 * 
	 * @throws Exception Thrown if the Graph is Invalid
	 */

	public double norm (
		final Network<Double> graphNetwork)
		throws Exception
	{
		if (null == graphNetwork) {
			throw new Exception ("GkToR1::norm => Invalid Inputs");
		}

		double norm = 0.;

		for (Vertex<?> vertex : graphNetwork.vertexMap().values()) {
			norm += Math.pow (Math.abs ((double) vertex.value()), _k);
		}

		return Math.pow (norm, 1. / _k);
	}

	/**
	 * Indicate if the G<sup>2</sup> Map is bounded based on the L<sup>2</sup> Metric
	 * 
	 * @param graphNetwork Graph Network
	 * 
	 * @return TRUE - G<sup>2</sup> Map is bounded
	 * 
	 * @throws Exception Thrown if the Graph is Invalid
	 */

	public boolean bounded (
		final Network<Double> graphNetwork)
		throws Exception
	{
		if (null == graphNetwork) {
			throw new Exception ("GkToR1::bounded => Invalid Inputs");
		}

		return Double.isFinite (norm (graphNetwork));
	}
}
