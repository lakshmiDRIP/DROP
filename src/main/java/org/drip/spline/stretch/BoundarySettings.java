
package org.drip.spline.stretch;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>BoundarySettings</i> implements the Boundary Settings that determine the full extent of description of
 * the regime's State. It exports functions that:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Specify the type of the boundary condition (NATURAL/FLOATING/IS-A-KNOT)
 *  	</li>
 *  	<li>
 * 			Boundary Condition specific additional parameters (e.g., Derivative Orders and Matches)
 *  	</li>
 *  	<li>
 * 			Static methods that help construct standard boundary settings
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundarySettings {

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

	private int _iLeftDerivOrder = -1;
	private int _iRightDerivOrder = -1;
	private int _iBoundaryConditionType = -1;

	/**
	 * Return the Instance of the Standard Natural Boundary Condition
	 * 
	 * @return Instance of the Standard Natural Boundary Condition
	 */

	public static final BoundarySettings NaturalStandard()
	{
		try {
			return new BoundarySettings (-1, 2, BOUNDARY_CONDITION_NATURAL);
		} catch (java.lang.Exception e) {
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
		} catch (java.lang.Exception e) {
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
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Not-A-Knot Boundary Condition
	 * 
	 * @param iLeftDerivOrder Order of the Left Derivative
	 * @param iRightDerivOrder Order of the Right Derivative
	 * 
	 * @return Instance of the Standard Not-A-Knot Boundary Condition
	 */

	public static final BoundarySettings NotAKnotStandard (
		final int iLeftDerivOrder,
		final int iRightDerivOrder)
	{
		try {
			return new BoundarySettings (iLeftDerivOrder, iRightDerivOrder, BOUNDARY_CONDITION_NOT_A_KNOT);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BoundarySettings constructor
	 * 
	 * @param iLeftDerivOrder Order of the Left Derivative
	 * @param iRightDerivOrder Order of the Right Derivative
	 * @param iBoundaryConditionType Type of the Boundary Condition - NATURAL/FINANCIAL/NOT_A_KNOT
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public BoundarySettings (
		final int iLeftDerivOrder,
		final int iRightDerivOrder,
		final int iBoundaryConditionType)
		throws java.lang.Exception
	{
		if (BOUNDARY_CONDITION_FLOATING != (_iBoundaryConditionType = iBoundaryConditionType) &&
			BOUNDARY_CONDITION_NATURAL != _iBoundaryConditionType && BOUNDARY_CONDITION_FINANCIAL !=
				_iBoundaryConditionType && BOUNDARY_CONDITION_NOT_A_KNOT != _iBoundaryConditionType)
			throw new java.lang.Exception ("BoundarySettings ct: Invalid Inputs");

		_iLeftDerivOrder = iLeftDerivOrder;
		_iRightDerivOrder = iRightDerivOrder;
	}

	/**
	 * Retrieve the Order of the Left Derivative
	 * 
	 * @return The Order of the Left Derivative
	 */

	public int leftDerivOrder()
	{
		return _iLeftDerivOrder;
	}

	/**
	 * Retrieve the Order of the Right Derivative
	 * 
	 * @return The Order of the Right Derivative
	 */

	public int rightDerivOrder()
	{
		return _iRightDerivOrder;
	}

	/**
	 * Retrieve the Type of the Boundary Condition
	 * 
	 * @return The Type of the Boundary Condition
	 */

	public int boundaryCondition()
	{
		return _iBoundaryConditionType;
	}
}
