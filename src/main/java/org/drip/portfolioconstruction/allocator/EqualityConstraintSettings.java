
package org.drip.portfolioconstruction.allocator;

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
 * <i>EqualityConstraintSettings</i> holds the Parameters required to generate the Mandatory Constraints for
 * the Portfolio.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator">Allocator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EqualityConstraintSettings
{

	/**
	 * NO_CONSTRAINT - No Constraint of any Kind
	 */

	public static final int NO_CONSTRAINT = 1;

	/**
	 * FULLY_INVESTED_CONSTRAINT - The Mandatory Completely Invested Constraint
	 */

	public static final int FULLY_INVESTED_CONSTRAINT = 2;

	/**
	 * RETURNS_CONSTRAINT - The Mandatory Returns Constraint
	 */

	public static final int RETURNS_CONSTRAINT = 4;

	private int _constraintType = -1;
	private double _returnsConstraint = java.lang.Double.NaN;

	/**
	 * Construct an Unconstrained Instance of EqualityConstraintSettings
	 * 
	 * @return Unconstrained EqualityConstraintSettings Instance
	 */

	public static final EqualityConstraintSettings Unconstrained()
	{
		try
		{
			return new EqualityConstraintSettings (
				NO_CONSTRAINT,
				java.lang.Double.NaN
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Fully Invested Instance of EqualityConstraintSettings
	 * 
	 * @return Fully Invested EqualityConstraintSettings Instance
	 */

	public static final EqualityConstraintSettings FullyInvested()
	{
		try
		{
			return new EqualityConstraintSettings (
				FULLY_INVESTED_CONSTRAINT,
				java.lang.Double.NaN
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Returns Constrained Instance of EqualityConstraintSettings
	 * 
	 * @param returnsConstraint The Returns Constraint
	 * 
	 * @return Returns Constrained EqualityConstraintSettings Instance
	 */

	public static final EqualityConstraintSettings ReturnsConstrained (
		final double returnsConstraint)
	{
		try
		{
			return new EqualityConstraintSettings (
				FULLY_INVESTED_CONSTRAINT | RETURNS_CONSTRAINT,
				returnsConstraint
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EqualityConstraintSettings Constructor
	 * 
	 * @param constraintType The Constraint Type
	 * @param returnsConstraint The Returns Constraint
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EqualityConstraintSettings (
		final int constraintType,
		final double returnsConstraint)
		throws java.lang.Exception
	{
		_constraintType = constraintType;
		_returnsConstraint = returnsConstraint;

		if (0 == (NO_CONSTRAINT & _constraintType) && 0 == (FULLY_INVESTED_CONSTRAINT & _constraintType) &&
			0 == (RETURNS_CONSTRAINT & _constraintType))
		{
			throw new java.lang.Exception ("EqualityConstraintSettings Constructor => Invalid Inputs!");
		}

		if (0 != (RETURNS_CONSTRAINT & _constraintType) && !org.drip.numerical.common.NumberUtil.IsValid
			(_returnsConstraint))
		{
			throw new java.lang.Exception ("EqualityConstraintSettings Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Constraint Type
	 * 
	 * @return The Constraint Type
	 */

	public int constraintType()
	{
		return _constraintType;
	}

	/**
	 * Retrieve the Returns Constraint
	 * 
	 * @return The Returns Constraint
	 */

	public double returnsConstraint()
	{
		return _returnsConstraint;
	}
}
