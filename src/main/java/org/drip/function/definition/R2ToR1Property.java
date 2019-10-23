
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R2ToR1Property</i> evaluates the Specified Pair of R<sup>2</sup> To R<sup>1</sup> Functions, and
 * verifies the Properties.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition">Function Implementation Ancillary Support Objects</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R2ToR1Property extends org.drip.function.definition.RxToR1Property
{
	private org.drip.function.definition.R2ToR1 _r2ToR1Left = null;
	private org.drip.function.definition.R2ToR1 _r2ToR1Right = null;

	/**
	 * R2ToR1Property Constructor
	 * 
	 * @param type The Comparator Type
	 * @param r2ToR1Left The Left R<sup>2</sup> To R<sup>1</sup> Function
	 * @param r2ToR1Right The Right R<sup>2</sup> To R<sup>1</sup> Function
	 * @param mismatchTolerance The Mismatch Tolerance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R2ToR1Property (
		final java.lang.String type,
		final org.drip.function.definition.R2ToR1 r2ToR1Left,
		final org.drip.function.definition.R2ToR1 r2ToR1Right,
		final double mismatchTolerance)
		throws java.lang.Exception
	{
		super (
			type,
			mismatchTolerance
		);

		if (null == (_r2ToR1Left = r2ToR1Left) ||
			null == (_r2ToR1Right = r2ToR1Right))
		{
			throw new java.lang.Exception ("R2ToR1Property Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Left R<sup>2</sup> To R<sup>1</sup> Function
	 * 
	 * @return The Left R<sup>2</sup> To R<sup>1</sup> Function
	 */

	public org.drip.function.definition.R2ToR1 r2ToR1Left()
	{
		return _r2ToR1Left;
	}

	/**
	 * Retrieve the Right R<sup>2</sup> To R<sup>1</sup> Function
	 * 
	 * @return The Right R<sup>2</sup> To R<sup>1</sup> Function
	 */

	public org.drip.function.definition.R2ToR1 r2ToR1Right()
	{
		return _r2ToR1Right;
	}

	/**
	 * Verify the specified R<sup>2</sup> To R<sup>1</sup> Functions
	 * 
	 * @param x X
	 * @param y Y
	 * 
	 * @return Results of the Verification
	 */

	public org.drip.function.definition.R1PropertyVerification verify (
		final double x,
		final double y)
	{
		try
		{
			return super.verify (
				_r2ToR1Left.evaluate (
					x,
					y
				),
				_r2ToR1Right.evaluate (
					x,
					y
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
