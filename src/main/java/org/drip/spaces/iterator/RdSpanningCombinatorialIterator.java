
package org.drip.spaces.iterator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>RdSpanningCombinatorialIterator</i> contains the Functionality to conduct a Spanning Iteration through
 * an R<sup>d</sup> Combinatorial Space.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/iterator">Iterator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RdSpanningCombinatorialIterator extends
	org.drip.spaces.iterator.RdExhaustiveStateSpaceScan {
	private org.drip.spaces.tensor.R1CombinatorialVector[] _aR1CV = null;

	/**
	 * Retrieve the RdSpanningCombinatorialIterator Instance associated with the Underlying Vector Space
	 * 
	 * @param aR1CV Array of R^1 Combinatorial Vectors
	 * 
	 * @return The RdSpanningCombinatorialIterator Instance associated with the Underlying Vector Space
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
	 * RdSpanningCombinatorialIterator Constructor
	 * 
	 * @param aR1CV Array of the R^1 Combinatorial Vectors
	 * @param aiMax The Array of Dimension Maximum
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdSpanningCombinatorialIterator (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV,
		final int[] aiMax)
		throws java.lang.Exception
	{
		super (aiMax, false);

		if (null == (_aR1CV = aR1CV) || _aR1CV.length != aiMax.length)
			throw new java.lang.Exception ("RdCombinatorialIterator ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the R^1 Combinatorial Vectors
	 * 
	 * @return The Array of the R^1 Combinatorial Vectors
	 */

	public org.drip.spaces.tensor.R1CombinatorialVector[] r1()
	{
		return _aR1CV;
	}

	/**
	 * Convert the Vector Space Index Array to the Variate Array
	 * 
	 * @param aiIndex Vector Space Index Array
	 * 
	 * @return Variate Array
	 */

	public double[] vectorSpaceIndexToVariate (
		final int[] aiIndex)
	{
		if (null == aiIndex) return null;

		int iDimension = _aR1CV.length;
		double[] adblVariate = new double[iDimension];

		if (iDimension != aiIndex.length) return null;

		for (int i = 0; i < iDimension; ++i)
			adblVariate[i] = _aR1CV[i].elementSpace().get (aiIndex[i]);

		return adblVariate;
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
