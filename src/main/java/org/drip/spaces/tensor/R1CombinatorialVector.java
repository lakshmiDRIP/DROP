
package org.drip.spaces.tensor;

import java.util.ArrayList;
import java.util.List;

import org.drip.numerical.common.NumberUtil;

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
 * <i>R1CombinatorialVector</i> exposes the normed/non-normed Discrete Spaces with R<sup>1</sup>
 * 	Combinatorial Vector Elements.
 *
 *  <ul>
 * 		<li>Validate the Input Instance Ordinate</li>
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

public class R1CombinatorialVector
	implements R1GeneralizedVector
{
	private List<Double> _spaceElementList = new ArrayList<Double>();

	/**
	 * <i>R1CombinatorialVector</i> Constructor
	 * 
	 * @param spaceElementList The List Space of Elements
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1CombinatorialVector (
		final List<Double> spaceElementList)
		throws Exception
	{
		if (null == (_spaceElementList = spaceElementList) || 0 == _spaceElementList.size()) {
			throw new Exception ("R1CombinatorialVector ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Full Candidate List of Elements
	 * 
	 * @return The Full Candidate List of Elements
	 */

	public List<Double> elementSpace()
	{
		return _spaceElementList;
	}

	/**
	 * Retrieve the Left Edge
	 * 
	 * @return The Left Edge
	 */

	@Override public double leftEdge()
	{
		double leftEdge = Double.NaN;

		for (double element : _spaceElementList) {
			if (Double.NEGATIVE_INFINITY == element) {
				return element;
			}

			if (!NumberUtil.IsValid (leftEdge))
				leftEdge = element;
			else {
				if (leftEdge > element) {
					leftEdge = element;
				}
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
		double dblRightEdge = java.lang.Double.NaN;

		for (double dblElement : _spaceElementList) {
			if (java.lang.Double.POSITIVE_INFINITY == dblElement) return dblElement;

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblRightEdge))
				dblRightEdge = dblElement;
			else {
				if (dblRightEdge < dblElement) dblRightEdge = dblElement;
			}
		}

		return dblRightEdge;
	}

	/**
	 * Validate the Input Instance Ordinate
	 * 
	 * @param instance The Input Instance Ordinate
	 * 
	 * @return TRUE - Instance Ordinate is a Valid Entry in the Space
	 */

	@Override public boolean validateInstance (
		final double instance)
	{
		return _spaceElementList.contains (instance);
	}

	/**
	 * Retrieve the Cardinality of the Vector Space
	 * 
	 * @return Cardinality of the Vector Space
	 */

	@Override public org.drip.spaces.tensor.Cardinality cardinality()
	{
		return org.drip.spaces.tensor.Cardinality.CountablyFinite (_spaceElementList.size());
	}

	/**
	 * Compare against the "Other" Generalized Vector Space
	 * 
	 * @param generalizedVectorOther The "Other" Generalized Vector Space
	 * 
	 * @return TRUE - The "Other" Generalized Vector Space matches this
	 */

	@Override public boolean match (
		final org.drip.spaces.tensor.GeneralizedVector gvOther)
	{
		if (null == gvOther || !(gvOther instanceof R1CombinatorialVector)) return false;

		R1CombinatorialVector r1cvOther = (R1CombinatorialVector) gvOther;

		if (!cardinality().match (r1cvOther.cardinality())) return false;

		java.util.List<java.lang.Double> lsElementSpaceOther = r1cvOther.elementSpace();

		for (double dblElement : _spaceElementList) {
			if (!lsElementSpaceOther.contains (dblElement)) return false;
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
		final org.drip.spaces.tensor.GeneralizedVector gvOther)
	{
		if (null == gvOther || !(gvOther instanceof R1CombinatorialVector)) return false;

		R1CombinatorialVector r1cvOther = (R1CombinatorialVector) gvOther;

		if (cardinality().number() < r1cvOther.cardinality().number()) return false;

		java.util.List<java.lang.Double> lsElementSpaceOther = r1cvOther.elementSpace();

		for (double dblElement : _spaceElementList) {
			if (!lsElementSpaceOther.contains (dblElement)) return false;
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
		return leftEdge() != java.lang.Double.NEGATIVE_INFINITY && rightEdge() !=
			java.lang.Double.POSITIVE_INFINITY;
	}

	/**
	 * Retrieve the "Hyper" Volume of the Vector Space
	 * 
	 * @return The "Hyper" Volume of the Vector Space
	 * 
	 * @throws Exception Thrown if the Hyper Volume cannot be computed
	 */

	@Override public double hyperVolume()
		throws java.lang.Exception
	{
		if (!isPredictorBounded())
			throw new java.lang.Exception ("R1CombinatorialVector::hyperVolume => Space not Bounded");

		return rightEdge() - leftEdge();
	}
}
