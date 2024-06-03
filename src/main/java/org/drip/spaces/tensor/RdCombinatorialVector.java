
package org.drip.spaces.tensor;

import org.drip.spaces.iterator.RdSpanningCombinatorialIterator;

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
 * <i>RdCombinatorialVector</i> exposes the Normed/Non-normed Discrete Spaces with R<sup>d</sup>
 * 	Combinatorial Vector Elements.
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

public class RdCombinatorialVector
	extends RdAggregate
{

	/**
	 * RdCombinatorialVector Constructor
	 * 
	 * @param r1CombinatorialVectorArray Array of the Underlying R<sup>1</sup> Combinatorial Vector Spaces
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdCombinatorialVector (
		final R1CombinatorialVector[] r1CombinatorialVectorArray)
		throws Exception
	{
		super (r1CombinatorialVectorArray);
	}

	/**
	 * Retrieve the Cardinality of the Vector Space
	 * 
	 * @return Cardinality of the Vector Space
	 */

	@Override public Cardinality cardinality()
	{
		R1GeneralizedVector[] r1GeneralizedVectorArray = vectorSpaces();

		double cardinalNumber = 1.;

		for (int i = 0; i < r1GeneralizedVectorArray.length; ++i) {
			cardinalNumber *= ((R1CombinatorialVector) r1GeneralizedVectorArray[i]).cardinality().number();
		}

		return Cardinality.CountablyFinite (cardinalNumber);
	}

	/**
	 * Retrieve the Multidimensional Iterator associated with the Underlying Vector Space
	 * 
	 * @return The Multidimensional Iterator associated with the Underlying Vector Space
	 */

	public RdSpanningCombinatorialIterator iterator()
	{
		R1GeneralizedVector[] r1GeneralizedVectorArray = vectorSpaces();

		R1CombinatorialVector[] r1CombinatorialVectorArray =
			new R1CombinatorialVector[r1GeneralizedVectorArray.length];

		for (int i = 0; i < r1GeneralizedVectorArray.length; ++i) {
			r1CombinatorialVectorArray[i] = (R1CombinatorialVector) r1GeneralizedVectorArray[i];
		}

		return RdSpanningCombinatorialIterator.Standard (r1CombinatorialVectorArray);
	}

	/**
	 * Retrieve the Array of the Variate Left Edges
	 * 
	 * @return The Array of the Variate Left Edges
	 */

	@Override public double[] leftDimensionEdge()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double[] adblLeftEdge = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblLeftEdge[i] = ((org.drip.spaces.tensor.R1ContinuousVector) aR1GV[i]).leftEdge();

		return adblLeftEdge;
	}

	/**
	 * Retrieve the Array of the Variate Right Edges
	 * 
	 * @return The Array of the Variate Right Edges
	 */

	@Override public double[] rightDimensionEdge()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double[] adblRightEdge = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblRightEdge[i] = ((org.drip.spaces.tensor.R1ContinuousVector) aR1GV[i]).rightEdge();

		return adblRightEdge;
	}

	/**
	 * Retrieve the Left Edge
	 * 
	 * @return The Left Edge
	 */

	@Override public double leftEdge()
	{
		double[] adblLeftEdge = leftDimensionEdge();

		int iDimension = adblLeftEdge.length;
		double dblLeftEdge = adblLeftEdge[0];

		for (int i = 1; i < iDimension; ++i) {
			if (dblLeftEdge > adblLeftEdge[i]) dblLeftEdge = adblLeftEdge[i];
		}

		return dblLeftEdge;
	}

	/**
	 * Retrieve the Right Edge
	 * 
	 * @return The Right Edge
	 */

	@Override public double rightEdge()
	{
		double[] adblRightEdge = rightDimensionEdge();

		int iDimension = adblRightEdge.length;
		double dblRightEdge = adblRightEdge[0];

		for (int i = 1; i < iDimension; ++i) {
			if (dblRightEdge < adblRightEdge[i]) dblRightEdge = adblRightEdge[i];
		}

		return dblRightEdge;
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
			throw new java.lang.Exception ("RdCombinatorialVector::hyperVolume => Space not Bounded");

		double[] adblLeftEdge = leftDimensionEdge();

		double dblHyperVolume = 1.;
		int iDimension = adblLeftEdge.length;

		double[] adblRightEdge = rightDimensionEdge();

		for (int i = 0; i < iDimension; ++i)
			dblHyperVolume *= (adblRightEdge[i] - adblLeftEdge[i]);

		return dblHyperVolume;
	}
}
