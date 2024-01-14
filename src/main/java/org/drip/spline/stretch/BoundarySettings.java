
package org.drip.spline.stretch;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>BoundarySettings</i> implements the Boundary Settings that determine the full extent of description of
 * 	the regime's State. It exports functions that:
 *
 * <br>
 *  <ul>
 * 		<li>Calibration Boundary Condition: Floating Boundary Condition</li>
 * 		<li>Calibration Boundary Condition: Natural Boundary Condition</li>
 * 		<li>Calibration Boundary Condition: Financial Boundary Condition</li>
 * 		<li>Calibration Boundary Condition: Not-A-Knot Boundary Condition</li>
 * 		<li>Return the Instance of the Standard Natural Boundary Condition</li>
 * 		<li>Return the Instance of the Standard Floating Boundary Condition</li>
 * 		<li>Return the Instance of the Standard Financial Boundary Condition</li>
 * 		<li>Return the Instance of the Standard Not-A-Knot Boundary Condition</li>
 * 		<li><i>BoundarySettings</i> constructor</li>
 * 		<li>Retrieve the Order of the Left Derivative</li>
 * 		<li>Retrieve the Order of the Right Derivative</li>
 * 		<li>Retrieve the Type of the Boundary Condition</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundarySettings
{

	/**
	 * Calibration Boundary Condition: Floating Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_FLOATING = 1;

	/**
	 * Calibration Boundary Condition: Natural Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_NATURAL = 2;

	/**
	 * Calibration Boundary Condition: Financial Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_FINANCIAL = 4;

	/**
	 * Calibration Boundary Condition: Not-A-Knot Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_NOT_A_KNOT = 8;

	private int _leftDerivativeOrder = -1;
	private int _rightDerivativeOrder = -1;
	private int _boundaryConditionType = -1;

	/**
	 * Return the Instance of the Standard Natural Boundary Condition
	 * 
	 * @return Instance of the Standard Natural Boundary Condition
	 */

	public static final BoundarySettings NaturalStandard()
	{
		try {
			return new BoundarySettings (-1, 2, BOUNDARY_CONDITION_NATURAL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Floating Boundary Condition
	 * 
	 * @return Instance of the Standard Floating Boundary Condition
	 */

	public static final BoundarySettings FloatingStandard()
	{
		try {
			return new BoundarySettings (-1, -1, BOUNDARY_CONDITION_FLOATING);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Financial Boundary Condition
	 * 
	 * @return Instance of the Standard Financial Boundary Condition
	 */

	public static final BoundarySettings FinancialStandard()
	{
		try {
			return new BoundarySettings (-1, 1, BOUNDARY_CONDITION_FINANCIAL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Not-A-Knot Boundary Condition
	 * 
	 * @param leftDerivativeOrder Order of the Left Derivative
	 * @param rightDerivativeOrder Order of the Right Derivative
	 * 
	 * @return Instance of the Standard Not-A-Knot Boundary Condition
	 */

	public static final BoundarySettings NotAKnotStandard (
		final int leftDerivativeOrder,
		final int rightDerivativeOrder)
	{
		try {
			return new BoundarySettings (
				leftDerivativeOrder,
				rightDerivativeOrder,
				BOUNDARY_CONDITION_NOT_A_KNOT
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>BoundarySettings</i> constructor
	 * 
	 * @param leftDerivativeOrder Order of the Left Derivative
	 * @param rightDerivativeOrder Order of the Right Derivative
	 * @param boundaryConditionType Type of the Boundary Condition - NATURAL/FINANCIAL/NOT_A_KNOT
	 * 
	 * @throws Exception Thrown if Inputs are invalid
	 */

	public BoundarySettings (
		final int leftDerivativeOrder,
		final int rightDerivativeOrder,
		final int boundaryConditionType)
		throws Exception
	{
		if (BOUNDARY_CONDITION_FLOATING != (_boundaryConditionType = boundaryConditionType) &&
			BOUNDARY_CONDITION_NATURAL != _boundaryConditionType &&
			BOUNDARY_CONDITION_FINANCIAL != _boundaryConditionType &&
			BOUNDARY_CONDITION_NOT_A_KNOT != _boundaryConditionType) {
			throw new Exception ("BoundarySettings ct: Invalid Inputs");
		}

		_leftDerivativeOrder = leftDerivativeOrder;
		_rightDerivativeOrder = rightDerivativeOrder;
	}

	/**
	 * Retrieve the Order of the Left Derivative
	 * 
	 * @return The Order of the Left Derivative
	 */

	public int leftDerivOrder()
	{
		return _leftDerivativeOrder;
	}

	/**
	 * Retrieve the Order of the Right Derivative
	 * 
	 * @return The Order of the Right Derivative
	 */

	public int rightDerivOrder()
	{
		return _rightDerivativeOrder;
	}

	/**
	 * Retrieve the Type of the Boundary Condition
	 * 
	 * @return The Type of the Boundary Condition
	 */

	public int boundaryCondition()
	{
		return _boundaryConditionType;
	}
}
