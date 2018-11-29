
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ConstraintFunctionPointMetrics</i> holds the R<sup>d</sup> Point Base and Sensitivity Metrics of the
 * Constraint Function.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1solver">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstraintFunctionPointMetrics {
	private double[] _adblValue = null;
	private double[] _adblMultiplier = null;
	private double[][] _aadblJacobian = null;

	/**
	 * ConstraintFunctionPointMetrics Constructor
	 * 
	 * @param adblValue Constraint Value Array
	 * @param aadblJacobian Constraint Jacobian Matrix
	 * @param adblMultiplier Constraint Karush-Kahn-Tucker Multiplier Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConstraintFunctionPointMetrics (
		final double[] adblValue,
		final double[][] aadblJacobian,
		final double[] adblMultiplier)
		throws java.lang.Exception
	{
		if (null == (_adblValue = adblValue) || null == (_aadblJacobian = aadblJacobian) || null ==
			(_adblMultiplier = adblMultiplier))
			throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");

		int iDimension = _aadblJacobian.length;
		int iNumConstraint = _adblValue.length;

		if (0 == iNumConstraint || iNumConstraint != adblMultiplier.length || 0 == iDimension)
			throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");

		for (int i = 0; i < iNumConstraint; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblValue[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (_adblMultiplier[i]))
				throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
		}

		for (int i = 0; i < iDimension; ++i) {
			if (null == _aadblJacobian[i] || iNumConstraint != _aadblJacobian[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblJacobian[i]))
				throw new java.lang.Exception
					("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Constraint Count
	 * 
	 * @return The Constraint Count
	 */

	public int count()
	{
		return _adblValue.length;
	}

	/**
	 * Retrieve the Constraint Dimension
	 * 
	 * @return The Constraint Dimension
	 */

	public int dimension()
	{
		return _aadblJacobian.length;
	}

	/**
	 * Retrieve the Constraint Value Array
	 * 
	 * @return The Constraint Value Array
	 */

	public double[] value()
	{
		return _adblValue;
	}

	/**
	 * Retrieve the Constraint KKR Multiplier Array
	 * 
	 * @return The Constraint KKR Multiplier Array
	 */

	public double[] multiplier()
	{
		return _adblMultiplier;
	}

	/**
	 * Retrieve the Constraint Jacobian Matrix
	 * 
	 * @return The Constraint Jacobian Matrix
	 */

	public double[][] jacobian()
	{
		return _aadblJacobian;
	}
}
