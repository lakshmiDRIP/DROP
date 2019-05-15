
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
 * <i>R3ToR1Property</i> evaluates the Specified Pair of R<sup>3</sup> To R<sup>1</sup> Functions, and
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

public class R3ToR1Property extends org.drip.function.definition.RxToR1Property
{
	private org.drip.function.definition.R3ToR1 _r3ToR1Left = null;
	private org.drip.function.definition.R3ToR1 _r3ToR1Right = null;

	/**
	 * R3ToR1Property Constructor
	 * 
	 * @param type The Comparator Type
	 * @param r3ToR1Left The Left R<sup>3</sup> To R<sup>1</sup> Function
	 * @param r3ToR1Right The Right R<sup>3</sup> To R<sup>1</sup> Function
	 * @param mismatchTolerance The Mismatch Tolerance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R3ToR1Property (
		final java.lang.String type,
		final org.drip.function.definition.R3ToR1 r3ToR1Left,
		final org.drip.function.definition.R3ToR1 r3ToR1Right,
		final double mismatchTolerance)
		throws java.lang.Exception
	{
		super (
			type,
			mismatchTolerance
		);

		if (null == (_r3ToR1Left = r3ToR1Left) ||
			null == (_r3ToR1Right = r3ToR1Right))
		{
			throw new java.lang.Exception ("R3ToR1Property Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Left R<sup>3</sup> To R<sup>1</sup> Function
	 * 
	 * @return The Left R<sup>3</sup> To R<sup>1</sup> Function
	 */

	public org.drip.function.definition.R3ToR1 r3ToR1Left()
	{
		return _r3ToR1Left;
	}

	/**
	 * Retrieve the Right R<sup>3</sup> To R<sup>1</sup> Function
	 * 
	 * @return The Right R<sup>3</sup> To R<sup>1</sup> Function
	 */

	public org.drip.function.definition.R3ToR1 r3ToR1Right()
	{
		return _r3ToR1Right;
	}

	/**
	 * Verify the specified R<sup>2</sup> To R<sup>1</sup> Functions
	 * 
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * 
	 * @return Results of the Verification
	 */

	public org.drip.function.definition.R1PropertyVerification verify (
		final double x,
		final double y,
		final double z)
	{
		try
		{
			return super.verify (
				_r3ToR1Left.evaluate (
					x,
					y,
					z
				),
				_r3ToR1Right.evaluate (
					x,
					y,
					z
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
