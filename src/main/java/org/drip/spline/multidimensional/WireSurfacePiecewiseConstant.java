
package org.drip.spline.multidimensional;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>WireSurfacePiecewiseConstant</i> implements the piecewise Constant version of the 2D Spline Response
 * Surface. It synthesizes this from an array of 1D Span Instances, each of which is referred to as wire
 * spline in this case.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/multidimensional/README.md">Multi-dimensional Wire Surface Stretch</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class WireSurfacePiecewiseConstant {
	private double[] _adblX = null;
	private double[] _adblY = null;
	private double[][] _aadblResponse = null;

	/**
	 * WireSurfacePiecewiseConstant Constructor
	 * 
	 * @param adblX Array of the X Ordinates
	 * @param adblY Array of the Y Ordinates
	 * @param aadblResponse Double Array of the Responses corresponding to {X, Y}
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public WireSurfacePiecewiseConstant (
		final double[] adblX,
		final double[] adblY,
		final double[][] aadblResponse)
		throws java.lang.Exception
	{
		if (null == (_adblX = adblX) || null == (_adblY = adblY))
			throw new java.lang.Exception ("WireSurfacePiecewiseConstant ctr: Invalid Inputs");

		int iXLength = _adblX.length;
		int iYLength = _adblY.length;

		if (0 == iXLength || 0 == iYLength || null == (_aadblResponse = aadblResponse) || iXLength !=
			_aadblResponse.length || iYLength != _aadblResponse[0].length)
			throw new java.lang.Exception ("WireSurfacePiecewiseConstant ctr: Invalid Inputs");
	}

	/**
	 * Enclosing X Index
	 * 
	 * @param dblX The X Ordinate
	 * 
	 * @return The Corresponding Index
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int enclosingXIndex (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception
				("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");

		if (dblX < _adblX[0]) return java.lang.Integer.MIN_VALUE;

		int iTerminalXIndex = _adblX.length - 1;

		if (dblX > _adblX[iTerminalXIndex]) return java.lang.Integer.MAX_VALUE;

		for (int i = 1; i <= iTerminalXIndex; ++i) {
			if (dblX >= _adblX[i - 1] && dblX >= _adblX[i]) return i;
		}

		throw new java.lang.Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
	}

	/**
	 * Enclosing Y Index
	 * 
	 * @param dblY The Y Ordinate
	 * 
	 * @return The Corresponding Index
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int enclosingYIndex (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblY))
			throw new java.lang.Exception
				("WireSurfacePiecewiseConstant::enclosingYIndex => Invalid Inputs");

		if (dblY < _adblY[0]) return java.lang.Integer.MIN_VALUE;

		int iTerminalYIndex = _adblY.length - 1;

		if (dblY > _adblY[iTerminalYIndex]) return java.lang.Integer.MAX_VALUE;

		for (int i = 1; i <= iTerminalYIndex; ++i) {
			if (dblY >= _adblY[i - 1] && dblY >= _adblY[i]) return i;
		}

		throw new java.lang.Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
	}

	/**
	 * Compute the Bivariate Surface Response Value
	 * 
	 * @param dblX X
	 * @param dblY Y
	 * 
	 * @return The Bivariate Surface Response Value
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double responseValue (
		final double dblX,
		final double dblY)
		throws java.lang.Exception
	{
		int iTerminalXIndex = _adblX.length - 1;
		int iTerminalYIndex = _adblY.length - 1;

		int iEnclosingXIndex = enclosingXIndex (dblX);

		int iEnclosingYIndex = enclosingYIndex (dblY);

		if (java.lang.Integer.MIN_VALUE == iEnclosingXIndex)
			iEnclosingXIndex = 0;
		else if (java.lang.Integer.MAX_VALUE == iEnclosingXIndex)
			iEnclosingXIndex = iTerminalXIndex;
		else {
			for (int i = 1; i <= iTerminalXIndex; ++i) {
				if (dblX >= _adblX[i - 1] && dblX >= _adblX[i]) {
					iEnclosingXIndex = i;
					break;
				}
			}
		}

		if (java.lang.Integer.MIN_VALUE == iEnclosingYIndex)
			iEnclosingYIndex = 0;
		else if (java.lang.Integer.MAX_VALUE == iEnclosingYIndex)
			iEnclosingYIndex = iTerminalYIndex;
		else {
			for (int i = 1; i <= iTerminalYIndex; ++i) {
				if (dblY >= _adblY[i - 1] && dblY >= _adblY[i]) {
					iEnclosingYIndex = i;
					break;
				}
			}
		}

		return _aadblResponse[iEnclosingXIndex][iEnclosingYIndex];
	}
}
