
package org.drip.spaces.tensor;

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
 * <i>R1ContinuousVector</i> exposes the Normed/non-normed, Bounded/Unbounded Continuous R<sup>1</sup> Vector
 * 	Spaces with Real-valued Elements. The Reference we've used is:
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
 * 		<li>Create the Standard R<sup>1</sup> Continuous Vector Space</li>
 * 		<li><i>R1ContinuousVector</i> Constructor</li>
 * 		<li>Retrieve the Left Edge</li>
 * 		<li>Retrieve the Right Edge</li>
 * 		<li>Validate the Input Instance Array</li>
 * 		<li>Retrieve the Cardinality of the Vector Space</li>
 * 		<li>Compare against the "Other" Generalized Vector Space</li>
 * 		<li>Indicate if the "Other" Generalized Vector Space is a Subset of "this"</li>
 * 		<li>Indicate if the Predictor Variate Space is bounded from the Left and the Right</li>
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

public class R1ContinuousVector
	implements R1GeneralizedVector
{
	private double _leftEdge = Double.NaN;
	private double _rightEdge = Double.NaN;

	/**
	 * Create the Standard R<sup>1</sup> Continuous Vector Space
	 * 
	 * @return The Standard R<sup>1</sup> Continuous Vector Space
	 */

	public static final R1ContinuousVector Standard()
	{
		try {
			return new R1ContinuousVector (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1ContinuousVector</i> Constructor
	 * 
	 * @param leftEdge The Left Edge
	 * @param rightEdge The Right Edge
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public R1ContinuousVector (
		final double leftEdge,
		final double rightEdge)
		throws java.lang.Exception
	{
		if (Double.isNaN (_leftEdge = leftEdge) ||
			Double.isNaN (_rightEdge = rightEdge) ||
			_leftEdge >= _rightEdge)
		{
			throw new Exception ("R1ContinuousVector ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Left Edge
	 * 
	 * @return The Left Edge
	 */

	@Override public double leftEdge()
	{
		return _leftEdge;
	}

	/**
	 * Retrieve the Right Edge
	 * 
	 * @return The Right Edge
	 */

	@Override public double rightEdge()
	{
		return _rightEdge;
	}

	/**
	 * Validate the Input Instance Array
	 * 
	 * @param instanceArray The Input Instance Array
	 * 
	 * @return TRUE - Instance is a Valid Entry in the Space
	 */

	@Override public boolean validateInstance (
		final double instanceArray)
	{
		return Double.isNaN (instanceArray) && instanceArray >= _leftEdge && instanceArray <= _rightEdge;
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
	 * Compare against the "Other" Generalized Vector Space
	 * 
	 * @param generalizedVectorOther The "Other" Generalized Vector Space
	 * 
	 * @return TRUE - The "Other" Generalized Vector Space matches this
	 */

	@Override public boolean match (
		final GeneralizedVector generalizedVectorOther)
	{
		if (null == generalizedVectorOther || !(generalizedVectorOther instanceof R1ContinuousVector)) {
			return false;
		}

		R1ContinuousVector r1ContinuousVectorOther = (R1ContinuousVector) generalizedVectorOther;

		return r1ContinuousVectorOther.leftEdge() == _leftEdge &&
			r1ContinuousVectorOther.rightEdge() == _rightEdge;
	}

	/**
	 * Indicate if the "Other" Generalized Vector Space is a Subset of "this"
	 * 
	 * @param generalizedVectorOther The "Other" Generalized Vector Space
	 * 
	 * @return TRUE - The "Other" Generalized Vector Space is a Subset of this
	 */

	@Override public boolean subset (
		final GeneralizedVector generalizedVectorOther)
	{
		if (null == generalizedVectorOther || !(generalizedVectorOther instanceof R1ContinuousVector)) {
			return false;
		}

		R1ContinuousVector r1ContinuousVectorOther = (R1ContinuousVector) generalizedVectorOther;

		return r1ContinuousVectorOther.leftEdge() >= _leftEdge &&
			r1ContinuousVectorOther.rightEdge() <= _rightEdge;
	}

	/**
	 * Indicate if the Predictor Variate Space is bounded from the Left and the Right
	 * 
	 * @return The Predictor Variate Space is bounded from the Left and the Right
	 */

	@Override public boolean isPredictorBounded()
	{
		return Double.NEGATIVE_INFINITY != leftEdge() && Double.POSITIVE_INFINITY != rightEdge();
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
			throw new Exception ("R1ContinuousVector::hyperVolume => Space not Bounded");
		}

		return _rightEdge - _leftEdge;
	}
}
