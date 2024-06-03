
package org.drip.spaces.tensor;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>RdContinuousVector</i> implements the Normed/non-normed, Bounded/Unbounded Continuous R<sup>d</sup>
 * 	Vector Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 *  <ul>
 * 		<li>Construct the <i>RdContinuousVector</i> Instance</li>
 * 		<li><i>RdContinuousVector</i> Constructor</li>
 * 		<li>Retrieve the Array of the Variate Left Edges</li>
 * 		<li>Retrieve the Array of the Variate Right Edges</li>
 * 		<li>Retrieve the Cardinality of the Vector Space</li>
 * 		<li>Retrieve the Left Edge</li>
 * 		<li>Retrieve the Right Edge</li>
 * 		<li>Retrieve the "Hyper" Volume of the Vector Space</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/tensor/README.md">R<sup>x</sup> Continuous/Combinatorial Tensor Spaces</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdContinuousVector
	extends RdAggregate
{

	/**
	 * Construct the <i>RdContinuousVector</i> Instance
	 * 
	 * @param dimension The Space Dimension
	 * 
	 * @return The <i>RdContinuousVector</i> Instance
	 */

	public static final RdContinuousVector Standard (
		final int dimension)
	{
		try {
			return 0 >= dimension ? null : new RdContinuousVector (new R1ContinuousVector[dimension]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>RdContinuousVector</i> Constructor
	 * 
	 * @param r1ContinuousVectorArray Array of the Continuous R<sup>1</sup> Vector Spaces
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdContinuousVector (
		final R1ContinuousVector[] r1ContinuousVectorArray)
		throws Exception
	{
		super (r1ContinuousVectorArray);
	}

	/**
	 * Retrieve the Array of the Variate Left Edges
	 * 
	 * @return The Array of the Variate Left Edges
	 */

	@Override public double[] leftDimensionEdge()
	{
		R1GeneralizedVector[] r1GeneralizedVectorArray = vectorSpaces();

		int dimension = r1GeneralizedVectorArray.length;
		double[] leftEdgeArray = new double[dimension];

		for (int i = 0; i < dimension; ++i) {
			leftEdgeArray[i] = ((R1ContinuousVector) r1GeneralizedVectorArray[i]).leftEdge();
		}

		return leftEdgeArray;
	}

	/**
	 * Retrieve the Array of the Variate Right Edges
	 * 
	 * @return The Array of the Variate Right Edges
	 */

	@Override public double[] rightDimensionEdge()
	{
		R1GeneralizedVector[] r1GeneralizedVectorArray = vectorSpaces();

		int dimension = r1GeneralizedVectorArray.length;
		double[] rightEdgeArray = new double[dimension];

		for (int i = 0; i < dimension; ++i) {
			rightEdgeArray[i] = ((R1ContinuousVector) r1GeneralizedVectorArray[i]).rightEdge();
		}

		return rightEdgeArray;
	}

	/**
	 * Retrieve the Cardinality of the Vector Space
	 * 
	 * @return Cardinality of the Vector Space
	 */

	@Override public Cardinality cardinality()
	{
		return Cardinality.UncountablyInfinite();
	}

	/**
	 * Retrieve the Left Edge
	 * 
	 * @return The Left Edge
	 */

	@Override public double leftEdge()
	{
		double[] leftEdgeArray = leftDimensionEdge();

		double leftEdge = leftEdgeArray[0];

		for (int i = 1; i < leftEdgeArray.length; ++i) {
			if (leftEdge > leftEdgeArray[i]) {
				leftEdge = leftEdgeArray[i];
			}
		}

		return leftEdge;
	}

	/**
	 * Retrieve the Right Edge
	 * 
	 * @return The Right Edge
	 */

	@Override public double rightEdge()
	{
		double[] rightEdgeArray = rightDimensionEdge();

		double rightEdge = rightEdgeArray[0];

		for (int i = 1; i < rightEdgeArray.length; ++i) {
			if (rightEdge < rightEdgeArray[i]) {
				rightEdge = rightEdgeArray[i];
			}
		}

		return rightEdge;
	}

	/**
	 * Retrieve the "Hyper" Volume of the Vector Space
	 * 
	 * @return The "Hyper" Volume of the Vector Space
	 * 
	 * @throws Exception Thrown if the Hyper Volume cannot be computed
	 */

	@Override public double hyperVolume()
		throws Exception
	{
		if (!isPredictorBounded()) {
			throw new Exception ("ContinuousVectorRd::hyperVolume => Space not Bounded");
		}

		double hyperVolume = 1.;

		double[] leftEdgeArray = leftDimensionEdge();

		double[] rightEdgeArray = rightDimensionEdge();

		for (int i = 0; i < leftEdgeArray.length; ++i) {
			hyperVolume *= (rightEdgeArray[i] - leftEdgeArray[i]);
		}

		return hyperVolume;
	}
}
