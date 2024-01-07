
package org.drip.spline.multidimensional;

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
 * <i>WireSurfacePiecewiseConstant</i> implements the piecewise Constant version of the 2D Spline Response
 * 	Surface. It synthesizes this from an array of 1D Span Instances, each of which is referred to as wire
 * 	spline in this case.
 *
 * <br>
 *  <ul>
 * 		<li><i>WireSurfacePiecewiseConstant</i> Constructor</li>
 * 		<li>Enclosing X Index</li>
 * 		<li>Enclosing Y Index</li>
 * 		<li>Compute the Bivariate Surface Response Value</li>
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
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/multidimensional/README.md">Multi-dimensional Wire Surface Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class WireSurfacePiecewiseConstant
{
	private double[] _xArray = null;
	private double[] _yArray = null;
	private double[][] _responseGrid = null;

	/**
	 * <i>WireSurfacePiecewiseConstant</i> Constructor
	 * 
	 * @param xArray Array of the X Ordinates
	 * @param yArray Array of the Y Ordinates
	 * @param responseGrid Double Array of the Responses corresponding to {X, Y}
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public WireSurfacePiecewiseConstant (
		final double[] xArray,
		final double[] yArray,
		final double[][] responseGrid)
		throws Exception
	{
		if (null == (_xArray = xArray) || null == (_yArray = yArray)) {
			throw new Exception ("WireSurfacePiecewiseConstant ctr: Invalid Inputs");
		}

		int xLength = _xArray.length;
		int yLength = _yArray.length;

		if (0 == xLength || 0 == yLength || null == (_responseGrid = responseGrid) ||
			xLength != _responseGrid.length || yLength != _responseGrid[0].length) {
			throw new Exception ("WireSurfacePiecewiseConstant ctr: Invalid Inputs");
		}
	}

	/**
	 * Enclosing X Index
	 * 
	 * @param x The X Ordinate
	 * 
	 * @return The Corresponding Index
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public int enclosingXIndex (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
		}

		if (x < _xArray[0]) {
			return Integer.MIN_VALUE;
		}

		int terminalXIndex = _xArray.length - 1;

		if (x > _xArray[terminalXIndex]) {
			return Integer.MAX_VALUE;
		}

		for (int xIndex = 1; xIndex <= terminalXIndex; ++xIndex) {
			if (x >= _xArray[xIndex - 1] && x >= _xArray[xIndex]) {
				return xIndex;
			}
		}

		throw new Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
	}

	/**
	 * Enclosing Y Index
	 * 
	 * @param y The Y Ordinate
	 * 
	 * @return The Corresponding Index
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public int enclosingYIndex (
		final double y)
		throws Exception
	{
		if (!NumberUtil.IsValid (y)) {
			throw new Exception ("WireSurfacePiecewiseConstant::enclosingYIndex => Invalid Inputs");
		}

		if (y < _yArray[0]) {
			return Integer.MIN_VALUE;
		}

		int terminalYIndex = _yArray.length - 1;

		if (y > _yArray[terminalYIndex]) {
			return Integer.MAX_VALUE;
		}

		for (int yIndex = 1; yIndex <= terminalYIndex; ++yIndex) {
			if (y >= _yArray[yIndex - 1] && y >= _yArray[yIndex]) {
				return yIndex;
			}
		}

		throw new Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
	}

	/**
	 * Compute the Bivariate Surface Response Value
	 * 
	 * @param x X
	 * @param y Y
	 * 
	 * @return The Bivariate Surface Response Value
	 * 
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public double responseValue (
		final double x,
		final double y)
		throws Exception
	{
		int terminalXIndex = _xArray.length - 1;
		int terminalYIndex = _yArray.length - 1;

		int enclosingXIndex = enclosingXIndex (x);

		int enclosingYIndex = enclosingYIndex (y);

		if (Integer.MIN_VALUE == enclosingXIndex) {
			enclosingXIndex = 0;
		} else if (Integer.MAX_VALUE == enclosingXIndex) {
			enclosingXIndex = terminalXIndex;
		} else {
			for (int xIndex = 1; xIndex <= terminalXIndex; ++xIndex) {
				if (x >= _xArray[xIndex - 1] && x >= _xArray[xIndex]) {
					enclosingXIndex = xIndex;
					break;
				}
			}
		}

		if (Integer.MIN_VALUE == enclosingYIndex) {
			enclosingYIndex = 0;
		} else if (Integer.MAX_VALUE == enclosingYIndex) {
			enclosingYIndex = terminalYIndex;
		} else {
			for (int yIndex = 1; yIndex <= terminalYIndex; ++yIndex) {
				if (y >= _yArray[yIndex - 1] && y >= _yArray[yIndex]) {
					enclosingYIndex = yIndex;
					break;
				}
			}
		}

		return _responseGrid[enclosingXIndex][enclosingYIndex];
	}
}
