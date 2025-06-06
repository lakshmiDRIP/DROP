
package org.drip.spaces.iterator;

import org.drip.spaces.tensor.R1CombinatorialVector;

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
 * <i>RdSpanningCombinatorialIterator</i> contains the Functionality to conduct a Spanning Iteration through
 * 	an R<sup>d</sup> Combinatorial Space.
 *
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Retrieve the <i>RdSpanningCombinatorialIterator</i> Instance associated with the Underlying Vector Space</li>
 * 		<li><i>RdSpanningCombinatorialIterator</i> Constructor</li>
 * 		<li>Retrieve the Array of the R<sup>1</sup> Combinatorial Vectors</li>
 * 		<li>Convert the Vector Space Index Array to the Variate Array</li>
 * 		<li>Retrieve the Cursor Variate Array</li>
 * 		<li>Retrieve the Subsequent Variate Array</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/iterator/README.md">Iterative/Exhaustive Vector Space Scanners</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RdSpanningCombinatorialIterator extends RdExhaustiveStateSpaceScan
{
	private R1CombinatorialVector[] _r1CombinatorialVectorArray = null;

	/**
	 * Retrieve the <i>RdSpanningCombinatorialIterator</i> Instance associated with the Underlying Vector Space
	 * 
	 * @param aR1CV Array of R<sup>1</sup> Combinatorial Vectors
	 * 
	 * @return The <i>RdSpanningCombinatorialIterator</i> Instance associated with the Underlying Vector Space
	 */

	public static final RdSpanningCombinatorialIterator Standard (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV)
	{
		if (null == aR1CV) return null;

		int iDimension = aR1CV.length;
		int[] aiMax = new int[iDimension];

		if (0 == iDimension) return null;

		for (int i = 0; i < iDimension; ++i)
			aiMax[i] = (int) aR1CV[i].cardinality().number();

		try {
			return new RdSpanningCombinatorialIterator (aR1CV, aiMax);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>RdSpanningCombinatorialIterator</i> Constructor
	 * 
	 * @param r1CombinatorialVectorArray Array of the R<sup>1</sup> Combinatorial Vectors
	 * @param terminalStateIndexArray The Array of Dimension Maximum
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdSpanningCombinatorialIterator (
		final R1CombinatorialVector[] r1CombinatorialVectorArray,
		final int[] terminalStateIndexArray)
		throws Exception
	{
		super (terminalStateIndexArray, false);

		if (null == (_r1CombinatorialVectorArray = r1CombinatorialVectorArray) ||
			_r1CombinatorialVectorArray.length != terminalStateIndexArray.length)
		{
			throw new Exception ("RdCombinatorialIterator ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the R<sup>1</sup> Combinatorial Vectors
	 * 
	 * @return The Array of the R<sup>1</sup> Combinatorial Vectors
	 */

	public R1CombinatorialVector[] r1()
	{
		return _r1CombinatorialVectorArray;
	}

	/**
	 * Convert the Vector Space Index Array to the Variate Array
	 * 
	 * @param vectorSpaceIndexArray Vector Space Index Array
	 * 
	 * @return Variate Array
	 */

	public double[] vectorSpaceIndexToVariate (
		final int[] vectorSpaceIndexArray)
	{
		if (null == vectorSpaceIndexArray) {
			return null;
		}

		int dimension = _r1CombinatorialVectorArray.length;
		double[] variateArray = new double[dimension];

		if (dimension != vectorSpaceIndexArray.length) {
			return null;
		}

		for (int i = 0; i < dimension; ++i) {
			variateArray[i] = _r1CombinatorialVectorArray[i].elementSpace().get (vectorSpaceIndexArray[i]);
		}

		return variateArray;
	}

	/**
	 * Retrieve the Cursor Variate Array
	 * 
	 * @return The Cursor Variate Array
	 */

	public double[] cursorVariates()
	{
		return vectorSpaceIndexToVariate (stateIndexCursor());
	}

	/**
	 * Retrieve the Subsequent Variate Array
	 * 
	 * @return The Subsequent Variate Array
	 */

	public double[] nextVariates()
	{
		return vectorSpaceIndexToVariate (nextStateIndexCursor());
	}
}
