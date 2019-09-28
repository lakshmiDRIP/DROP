
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
 * <i>ConstraintTerm</i> holds the Details of a given Constraint Term.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer">Optimizer</a></li>
*		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/strategy">Strategy</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ConstraintTerm
	extends org.drip.portfolioconstruction.optimizer.FormulationTerm
{
	private double _maximum = java.lang.Double.NaN;
	private double _minimum = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.optimizer.Unit _unit = null;
	private org.drip.portfolioconstruction.optimizer.Scope _scope = null;
	private org.drip.portfolioconstruction.optimizer.SoftConstraint _softConstraint = null;

	protected ConstraintTerm (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final java.lang.String strCategory,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double minimum,
		final double maximum)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription,
			strCategory
		);

		if (null == (_scope = scope) ||
			null == (_unit = unit) ||
			(!org.drip.numerical.common.NumberUtil.IsValid (_minimum = minimum) &&
			!org.drip.numerical.common.NumberUtil.IsValid (_maximum = maximum)))
		{
			throw new java.lang.Exception ("ConstraintTerm Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Soft Constraint
	 * 
	 * @return The Soft Constraint
	 */

	public org.drip.portfolioconstruction.optimizer.SoftConstraint softContraint()
	{
		return _softConstraint;
	}

	/**
	 * Retrieve the Constraint Scope
	 * 
	 * @return The Constraint Scope
	 */

	public org.drip.portfolioconstruction.optimizer.Scope scope()
	{
		return _scope;
	}

	/**
	 * Retrieve the Constraint Unit
	 * 
	 * @return The Constraint Unit
	 */

	public org.drip.portfolioconstruction.optimizer.Unit unit()
	{
		return _unit;
	}

	/**
	 * Retrieve the Constraint Minimum
	 * 
	 * @return The Constraint Minimum
	 */

	public double minimum()
	{
		return _minimum;
	}

	/**
	 * Retrieve the Constraint Maximum
	 * 
	 * @return The Constraint Maximum
	 */

	public double maximum()
	{
		return _maximum;
	}

	/**
	 * Indicate if this is an Equality Constraint
	 * 
	 * @return TRUE - This is an Equality Constraint
	 */

	public boolean isEquality()
	{
		return _maximum == _minimum;
	}

	/**
	 * Set the Soft Constraint
	 * 
	 * @param softConstraint The Soft Constraint
	 * 
	 * @return TRUE - The Soft Constraint successfully set
	 */

	public boolean setSoftConstraint (
		final org.drip.portfolioconstruction.optimizer.SoftConstraint softConstraint)
	{
		if (null == softConstraint)
		{
			return false;
		}

		_softConstraint = softConstraint;
		return true;
	}
}
