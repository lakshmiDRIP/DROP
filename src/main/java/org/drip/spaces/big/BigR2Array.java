
package org.drip.spaces.big;

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
 * <i>BigR2Array</i> contains an Implementation Navigation and Processing Algorithms for Big Double
 * R<sup>2</sup> Arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big">Big</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class BigR2Array {
	private int _iXLength = -1;
	private int _iYLength = -1;
	private double[][] _aadblR2 = null;

	/**
	 * BigR2Array Constructor
	 * 
	 * @param aadblR2 2D Big Array Inputs
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BigR2Array (
		final double[][] aadblR2)
		throws java.lang.Exception
	{
		if (null == (_aadblR2 = aadblR2))
			throw new java.lang.Exception ("BigR2Array Constructor => Invalid Inputs");

		if (0 == (_iXLength = _aadblR2.length) || 0 == (_iYLength = _aadblR2[0].length))
			throw new java.lang.Exception ("BigR2Array Constructor => Invalid Inputs");
	}

	/**
	 * Compute the Path Response Associated with all the Nodes in the Path up to the Current One.
	 *  
	 * @param iX The Current X Node
	 * @param iY The Current Y Node
	 * @param dblPriorPathResponse The Path Product Associated with the Given Prior Navigation Sequence
	 * 
	 * @return The Path Response
	 *  
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	abstract public double pathResponse (
		final int iX,
		final int iY,
		final double dblPriorPathResponse)
		throws java.lang.Exception;

	/**
	 * Compute the Maximum Response Associated with all the Left/Right Adjacent Paths starting from the Top
	 *  Left Node.
	 *  
	 * @return The Maximum Response Associated with all the Left/Right Adjacent Paths starting from the
	 *  Current Node
	 *  
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	abstract public double maxPathResponse()
		throws java.lang.Exception;

	/**
	 * Retrieve the Length of the X R^1 Array
	 * 
	 * @return The Length of the X R^1 Array
	 */

	public int xLength()
	{
		return _iXLength;
	}

	/**
	 * Retrieve the Length of the Y R^1 Array
	 * 
	 * @return The Length of the Y R^1 Array
	 */

	public int yLength()
	{
		return _iYLength;
	}

	/**
	 * Retrieve the R^2 Instance Array
	 * 
	 * @return The R^2 Instance Array
	 */

	public double[][] instance()
	{
		return _aadblR2;
	}

	/**
	 * Validate the Specified Index Pair.
	 *  
	 * @param iX The Current X Node
	 * @param iY The Current Y Node
	 * 
	 * @return TRUE - The Index Pair is Valid
	 */

	public boolean validateIndex (
		final int iX,
		final int iY)
	{
		return iX < 0 || iX >= _iXLength || iY < 0 || iY >= _iYLength ? false : true;
	}

	/**
	 * Compute the Maximum Response Associated with all the Left/Right Adjacent Paths starting from the
	 *  Current Node.
	 *  
	 * @param iX The Current X Node
	 * @param iY The Current Y Node
	 * @param dblPriorPathResponse The Path Response Associated with the Given Prior Navigation Sequence
	 * 
	 * @return The Maximum Response Associated with all the Left/Right Adjacent Paths starting from the
	 *  Current Node
	 *  
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double maxPathResponse (
		final int iX,
		final int iY,
		final double dblPriorPathResponse)
		throws java.lang.Exception
	{
		double dblCurrentPathResponse = pathResponse (iX, iY, dblPriorPathResponse);

		if (iY == _iYLength - 1 && iX == _iXLength - 1) return dblCurrentPathResponse;

		double dblXShiftMaxPathResponse = java.lang.Double.NaN;
		double dblYShiftMaxPathResponse = java.lang.Double.NaN;

		if (iX < _iXLength - 1)
			dblXShiftMaxPathResponse = maxPathResponse (iX + 1, iY, dblCurrentPathResponse);

		if (iY < _iYLength - 1)
			dblYShiftMaxPathResponse = maxPathResponse (iX, iY + 1, dblCurrentPathResponse);

		if (iY == _iYLength - 1) return dblXShiftMaxPathResponse;

		if (iX == _iXLength - 1) return dblYShiftMaxPathResponse;

		return dblXShiftMaxPathResponse > dblYShiftMaxPathResponse ? dblXShiftMaxPathResponse :
			dblYShiftMaxPathResponse;
	}
}
