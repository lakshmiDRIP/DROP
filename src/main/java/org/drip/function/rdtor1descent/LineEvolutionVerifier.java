
package org.drip.function.rdtor1descent;

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
 * <i>LineEvolutionVerifier</i> implements the Step Length Verification Criterion used for the Inexact Line
 * Search Increment Generation. The References are:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial
 * 				Derivatives <i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3
 * 		</li>
 * 		<li>
 * 			Nocedal, J., and S. Wright (1999): <i>Numerical Optimization</i> <b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1969): Convergence Conditions for Ascent Methods <i>SIAM Review</i> <b>11 (2)</b>
 * 				226-235
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections <i>SIAM
 * 				Review</i> <b>13 (2)</b> 185-188
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1descent/README.md">R<sup>d</sup> To R<sup>1</sup></a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class LineEvolutionVerifier
{

	protected static final double[] NextVariateArray (
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final double stepLength)
	{
		if (null == currentVariateArray ||
			!org.drip.numerical.common.NumberUtil.IsValid (stepLength))
		{
			return null;
		}

		int dimension = currentVariateArray.length;
		double[] nextVariateArray = 0 == dimension ? null : new double[dimension];

		if (null == nextVariateArray || null == targetDirectionUnitVector)
		{
			return null;
		}

		double[] targetDirectionVector = targetDirectionUnitVector.component();

		if (null == targetDirectionVector || dimension != targetDirectionVector.length)
		{
			return null;
		}

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (currentVariateArray[dimensionIndex]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (targetDirectionVector[dimensionIndex]))
			{
				return null;
			}

			nextVariateArray[dimensionIndex] = currentVariateArray[dimensionIndex] +
				stepLength * targetDirectionVector[dimensionIndex];
		}

		return nextVariateArray;
	}

	/**
	 * Verify if the specified Inputs satisfy the Criterion
	 * 
	 * @param targetDirectionUnitVector The Target Direction Unit Vector
	 * @param currentVariateArray The Current Variate
	 * @param multivariateFunction The R<sup>d</sup> To R<sup>1</sup> Function
	 * @param dblStepLength The Incremental Step Length
	 * 
	 * @return TRUE - The Specified Inputs satisfy the Criterion
	 * 
	 * @throws java.lang.Exception Thrown if the Verification cannot be performed
	 */

	public boolean verify (
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final org.drip.function.definition.RdToR1 multivariateFunction,
		final double stepLength)
		throws java.lang.Exception
	{
		org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics lineEvolutionVerifierMetrics = metrics (
			targetDirectionUnitVector,
			currentVariateArray,
			multivariateFunction,
			stepLength
		);

		if (null == lineEvolutionVerifierMetrics)
		{
			throw new java.lang.Exception ("LineEvolutionVerifier::verify => Cannot Verify");
		}

		return lineEvolutionVerifierMetrics.verify();
	}

	/**
	 * Generate the Verifier Metrics for the Specified Inputs
	 * 
	 * @param targetDirectionUnitVector The Target Direction Unit Vector
	 * @param currentVariateArray The Current Variate
	 * @param multivariateFunction The R<sup>d</sup> To R<sup>1</sup> Function
	 * @param stepLength The Incremental Step Length
	 * 
	 * @return The Verifier Metrics
	 */

	public abstract org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics metrics (
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final org.drip.function.definition.RdToR1 multivariateFunction,
		final double stepLength);
}
