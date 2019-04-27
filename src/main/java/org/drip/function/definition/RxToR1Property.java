
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>RxToR1Property</i> evaluates the Specified Pair of R<sup>x</sup> To R<sup>1</sup> Functions, and
 * verifies the Properties.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/README.md">Definition</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RxToR1Property
{

	/**
	 * Mismatch Tolerance
	 */

	public static final double MISMATCH_TOLERANCE = 0.01;

	/**
	 * EQUAL To Comparison
	 */

	public static final java.lang.String EQ = "EQ";

	/**
	 * LESS THAN OR EQUAL To Comparison
	 */

	public static final java.lang.String LTE = "LTE";

	/**
	 * GREATER THAN OR EQUAL To Comparison
	 */

	public static final java.lang.String GTE = "GTE";

	/**
	 * LESS THAN To Comparison
	 */

	public static final java.lang.String LT = "LT";

	/**
	 * GREATER THAN To Comparison
	 */

	public static final java.lang.String GT = "GT";

	private java.lang.String _type = "";
	private double _mismatchTolerance = java.lang.Double.NaN;

	protected RxToR1Property (
		final java.lang.String type,
		final double mismatchTolerance)
		throws java.lang.Exception
	{
		if (null == (_type = type) || _type.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_mismatchTolerance = mismatchTolerance))
		{
			throw new java.lang.Exception ("RxToR1Property Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Type of the Comparison
	 * 
	 * @return The Type of the Comparison
	 */

	public java.lang.String type()
	{
		return _type;
	}

	/**
	 * Retrieve the Mismatch Tolerance
	 * 
	 * @return The Mismatch Tolerance
	 */

	public double mismatchTolerance()
	{
		return _mismatchTolerance;
	}

	/**
	 * Verify the specified Left and Right Function Values
	 * 
	 * @param leftValue Left Function Value
	 * @param rightValue Right Function Value
	 * 
	 * @return Results of the Verification
	 */

	public org.drip.function.definition.R1PropertyVerification verify (
		final double leftValue,
		final double rightValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (leftValue) ||
			!org.drip.numerical.common.NumberUtil.IsValid (rightValue))
		{
			return null;
		}

		java.lang.String type = type();

		try
		{
			if (LT.equalsIgnoreCase (type))
			{
				return new org.drip.function.definition.R1PropertyVerification (
					leftValue,
					rightValue,
					leftValue < rightValue
				);
			}

			if (GT.equalsIgnoreCase (type))
			{
				return new org.drip.function.definition.R1PropertyVerification (
					leftValue,
					rightValue,
					leftValue > rightValue
				);
			}

			double mismatchTolerance = mismatchTolerance();

			double leftTolerance = java.lang.Math.abs (leftValue * mismatchTolerance);

			double rightTolerance = java.lang.Math.abs (rightValue * mismatchTolerance);

			double tolerance = leftTolerance < rightTolerance ? leftTolerance: rightTolerance;
			tolerance = tolerance < mismatchTolerance ? mismatchTolerance : tolerance;

			if (EQ.equalsIgnoreCase (type))
			{
				return new org.drip.function.definition.R1PropertyVerification (
					leftValue,
					rightValue,
					java.lang.Math.abs (leftValue - rightValue) < tolerance
				);
			}

			if (LTE.equalsIgnoreCase (type))
			{
				return new org.drip.function.definition.R1PropertyVerification (
					leftValue,
					rightValue,
					leftValue < rightValue || java.lang.Math.abs (leftValue - rightValue) < tolerance
				);
			}

			if (GTE.equalsIgnoreCase (type))
			{
				return new org.drip.function.definition.R1PropertyVerification (
					leftValue,
					rightValue,
					leftValue > rightValue || java.lang.Math.abs (leftValue - rightValue) < tolerance
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
