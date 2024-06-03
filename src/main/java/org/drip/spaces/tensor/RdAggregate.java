
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
 * <i>RdAggregate</i> exposes the basic Properties of the R<sup>d</sup> as a Sectional Super-position of
 * 	R<sup>1</sup> Vector Spaces.
 *
 *  <ul>
 * 		<li><i>RdAggregate</i> Constructor</li>
 * 		<li>Retrieve the Dimension of the Space</li>
 * 		<li>Retrieve the Array of the Underlying R<sup>1</sup> Vector Spaces</li>
 * 		<li>Validate the Input Instance Array</li>
 * 		<li>Compare against the "Other" Generalized Vector Space</li>
 * 		<li>Indicate if the "Other" Generalized Vector Space is a Subset of "this"</li>
 * 		<li>Indicate if the Predictor Variate Space is bounded from the Left and the Right</li>
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

public abstract class RdAggregate
	implements RdGeneralizedVector
{
	private R1GeneralizedVector[] _r1GeneralizedVectorArray = null;

	/**
	 * <i>RdAggregate</i> Constructor
	 * 
	 * @param r1GeneralizedVectorArray Array of Generalized R<sup>1</sup> Vectors
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	protected RdAggregate (
		final R1GeneralizedVector[] r1GeneralizedVectorArray)
		throws Exception
	{
		if (null == (_r1GeneralizedVectorArray = r1GeneralizedVectorArray)) {
			throw new Exception ("RdAggregate Constructor => Invalid Inputs");
		}

		int dimension = _r1GeneralizedVectorArray.length;

		if (0 == dimension) {
			throw new Exception ("RdAggregate Constructor => Invalid Inputs");
		}

		for (int i = 0; i < dimension; ++i) {
			if (null == _r1GeneralizedVectorArray[i]) {
				throw new Exception ("RdAggregate Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Dimension of the Space
	 *  
	 * @return The Dimension of the Space
	 */

	@Override public int dimension()
	{
		return _r1GeneralizedVectorArray.length;
	}

	/**
	 * Retrieve the Array of the Underlying R<sup>1</sup> Vector Spaces
	 * 
	 * @return The Array of the Underlying R<sup>1</sup> Vector Spaces
	 */

	@Override public R1GeneralizedVector[] vectorSpaces()
	{
		return _r1GeneralizedVectorArray;
	}

	/**
	 * Validate the Input Instance Array
	 * 
	 * @param instanceArray The Input Instance Array
	 * 
	 * @return TRUE - Instance is a Valid Entry in the Space
	 */

	@Override public boolean validateInstance (
		final double[] instanceArray)
	{
		if (null == instanceArray) {
			return false;
		}

		int dimension = _r1GeneralizedVectorArray.length;

		if (instanceArray.length != dimension) {
			return false;
		}

		for (int i = 0; i < dimension; ++i) {
			if (!_r1GeneralizedVectorArray[i].validateInstance (instanceArray[i])) {
				return false;
			}
		}

		return true;
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
		if (null == generalizedVectorOther || !(generalizedVectorOther instanceof RdAggregate)) {
			return false;
		}

		RdAggregate rdAggregateOther = (RdAggregate) generalizedVectorOther;

		int dimensionOther = rdAggregateOther.dimension();

		if (dimensionOther != dimension()) {
			return false;
		}

		R1GeneralizedVector[] r1GeneralizedVectorArrayOther = rdAggregateOther.vectorSpaces();

		for (int i = 0; i < dimensionOther; ++i) {
			if (!r1GeneralizedVectorArrayOther[i].match (_r1GeneralizedVectorArray[i])) {
				return false;
			}
		}

		return true;
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
		if (null == generalizedVectorOther || !(generalizedVectorOther instanceof RdAggregate)) {
			return false;
		}

		R1GeneralizedVector[] r1GeneralizedVectorArrayOther =
			((RdAggregate) generalizedVectorOther).vectorSpaces();

		for (int i = 0; i < _r1GeneralizedVectorArray.length; ++i) {
			if (!r1GeneralizedVectorArrayOther[i].match (_r1GeneralizedVectorArray[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate if the Predictor Variate Space is bounded from the Left and the Right
	 * 
	 * @return The Predictor Variate Space is bounded from the Left and the Right
	 */

	@Override public boolean isPredictorBounded()
	{
		for (int i = 0; i < _r1GeneralizedVectorArray.length; ++i) {
			if (!_r1GeneralizedVectorArray[i].isPredictorBounded()) {
				return false;
			}
		}

		return true;
	}
}
