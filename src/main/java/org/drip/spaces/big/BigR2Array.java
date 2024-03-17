
package org.drip.spaces.big;

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
 * <i>BigR2Array</i> contains an Implementation Navigation and Processing Algorithms for Big Double
 * 	R<sup>2</sup> Arrays. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>BigR2Array</i> Constructor</li>
 * 		<li>Compute the Path Response Associated with all the Nodes in the Path up to the Current One</li>
 * 		<li>Compute the Maximum Response Associated with all the Left/Right Adjacent Paths starting from the Top Left Node</li>
 * 		<li>Retrieve the Length of the X R<sup>1</sup> Array</li>
 * 		<li>Retrieve the Length of the Y R<sup>1</sup> Array</li>
 * 		<li>Retrieve the R<sup>2</sup> Instance Array</li>
 * 		<li>Validate the Specified Index Pair</li>
 * 		<li>Compute the Maximum Response Associated with all the Left/Right Adjacent Paths starting from the Current Node</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big/README.md">Big-data In-place Manipulator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class BigR2Array
{
	private int _xLength = -1;
	private int _yLength = -1;
	private double[][] _r2Grid = null;

	/**
	 * <i>BigR2Array</i> Constructor
	 * 
	 * @param r2Grid 2D Big Array Grid Input
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BigR2Array (
		final double[][] r2Grid)
		throws Exception
	{
		if (null == (_r2Grid = r2Grid)) {
			throw new Exception ("BigR2Array Constructor => Invalid Inputs");
		}

		if (0 == (_xLength = _r2Grid.length) || 0 == (_yLength = _r2Grid[0].length)) {
			throw new Exception ("BigR2Array Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Length of the X R<sup>1</sup> Array
	 * 
	 * @return The Length of the X R<sup>1</sup> Array
	 */

	public int xLength()
	{
		return _xLength;
	}

	/**
	 * Retrieve the Length of the Y R<sup>1</sup> Array
	 * 
	 * @return The Length of the Y R<sup>1</sup> Array
	 */

	public int yLength()
	{
		return _yLength;
	}

	/**
	 * Retrieve the R<sup>2</sup> Instance Array
	 * 
	 * @return The R<sup>2</sup> Instance Array
	 */

	public double[][] instance()
	{
		return _r2Grid;
	}

	/**
	 * Compute the Path Response Associated with all the Nodes in the Path up to the Current One.
	 *  
	 * @param x The Current X Node
	 * @param y The Current Y Node
	 * @param priorPathResponse The Path Product Associated with the Given Prior Navigation Sequence
	 * 
	 * @return The Path Response
	 *  
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	abstract public double pathResponse (
		final int x,
		final int y,
		final double priorPathResponse
	) throws Exception;

	/**
	 * Compute the Maximum Response Associated with all the Left/Right Adjacent Paths starting from the Top
	 *  Left Node.
	 *  
	 * @return The Maximum Response Associated with all the Left/Right Adjacent Paths starting from the
	 *  Current Node
	 *  
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	abstract public double maxPathResponse()
		throws Exception;

	/**
	 * Validate the Specified Index Pair.
	 *  
	 * @param x The Current X Node
	 * @param y The Current Y Node
	 * 
	 * @return TRUE - The Index Pair is Valid
	 */

	public boolean validateIndex (
		final int x,
		final int y)
	{
		return x < 0 || x >= _xLength || y < 0 || y >= _yLength ? false : true;
	}

	/**
	 * Compute the Maximum Response Associated with all the Left/Right Adjacent Paths starting from the
	 *  Current Node.
	 *  
	 * @param x The Current X Node
	 * @param y The Current Y Node
	 * @param priorPathResponse The Path Response Associated with the Given Prior Navigation Sequence
	 * 
	 * @return The Maximum Response Associated with all the Left/Right Adjacent Paths starting from the
	 *  Current Node
	 *  
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public double maxPathResponse (
		final int x,
		final int y,
		final double priorPathResponse)
		throws Exception
	{
		double currentPathResponse = pathResponse (x, y, priorPathResponse);

		if (y == _yLength - 1 && x == _xLength - 1) {
			return currentPathResponse;
		}

		double xShiftMaxPathResponse = Double.NaN;
		double yShiftMaxPathResponse = Double.NaN;

		if (x < _xLength - 1) {
			xShiftMaxPathResponse = maxPathResponse (x + 1, y, currentPathResponse);
		}

		if (y < _yLength - 1) {
			yShiftMaxPathResponse = maxPathResponse (x, y + 1, currentPathResponse);
		}

		if (y == _yLength - 1) {
			return xShiftMaxPathResponse;
		}

		if (x == _xLength - 1) {
			return yShiftMaxPathResponse;
		}

		return xShiftMaxPathResponse > yShiftMaxPathResponse ? xShiftMaxPathResponse : yShiftMaxPathResponse;
	}
}
