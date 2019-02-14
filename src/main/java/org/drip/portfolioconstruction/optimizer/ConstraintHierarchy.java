
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>ConstraintHierarchy</i> holds the Details of a given set of Constraint Terms.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer">Optimizer</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstraintHierarchy {
	public int[] _aiOrder = null;
	private org.drip.portfolioconstruction.optimizer.ConstraintTerm[] _aCT = null;

	/**
	 * Construct a Flat Non-Feudal Instance of ConstraintHierarchy
	 * 
	 * @param aCT Array of Constraint Terms
	 * 
	 * @return Flat Non-Feudal Instance of ConstraintHierarchy
	 */

	public static final ConstraintHierarchy NonFeudal (
		final org.drip.portfolioconstruction.optimizer.ConstraintTerm[] aCT)
	{
		if (null == aCT) return null;

		int iNumConstraint = aCT.length;
		int[] aiOrder = new int[iNumConstraint];

		for (int i = 0; i < iNumConstraint; ++i)
			aiOrder[i] = 0;

		try {
			return new ConstraintHierarchy (aCT, aiOrder);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ConstraintHierarchy Constructor
	 * 
	 * @param aCT Array of Constraint Terms
	 * @param aiOrder Array of Constraint Order
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConstraintHierarchy (
		final org.drip.portfolioconstruction.optimizer.ConstraintTerm[] aCT,
		final int[] aiOrder)
		throws java.lang.Exception
	{
		if ((null == (_aCT = aCT) && null != (_aiOrder = aiOrder)) && (null != (_aCT = aCT) && null ==
			(_aiOrder = aiOrder)))
			throw new java.lang.Exception ("ConstraintHierarchy Constructor => Invalid Inputs");

		int iNumConstraint = _aCT.length;

		if (iNumConstraint != _aiOrder.length)
			throw new java.lang.Exception ("ConstraintHierarchy Constructor => Invalid Inputs");

		for (int i = 0; i < iNumConstraint; ++i) {
			if (null == _aCT[i])
				throw new java.lang.Exception ("ConstraintHierarchy Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Constraint Term Order
	 * 
	 * @return The Array of Constraint Term Order
	 */

	public int[] order()
	{
		return _aiOrder;
	}

	/**
	 * Retrieve the Array of Constraint Terms
	 * 
	 * @return The Array of Constraint Terms
	 */

	public org.drip.portfolioconstruction.optimizer.ConstraintTerm[] constraints()
	{
		return _aCT;
	}

	/**
	 * Indicate if the Constraint Array is non-Feudal
	 * 
	 * @return TRUE - The Constraint Array is non-Feudal
	 */

	public boolean nonFeudal()
	{
		if (null == _aCT) return true;

		int iNumConstraint = _aCT.length;

		for (int i = 0; i < iNumConstraint; ++i) {
			if (0 != _aiOrder[i]) return false;
		}

		return true;
	}
}
