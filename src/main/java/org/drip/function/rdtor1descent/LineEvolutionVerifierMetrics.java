
package org.drip.function.rdtor1descent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>LineEvolutionVerifierMetrics</i> implements the Step Length Verification Criterion used for the Inexact
 * Line Search Increment Generation. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1descent/README.md">R<sup>d</sup> To R<sup>1</sup> Gradient Descent Techniques</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class LineEvolutionVerifierMetrics
{
	private double[] _currentVariateArray = null;
	private double _stepLength = java.lang.Double.NaN;
	private double[] _currentVariateFunctionJacobian = null;
	private org.drip.function.definition.UnitVector _targetDirection = null;

	protected LineEvolutionVerifierMetrics (
		final org.drip.function.definition.UnitVector targetDirection,
		final double[] currentVariateArray,
		final double stepLength,
		final double[] currentVariateFunctionJacobian)
		throws java.lang.Exception
	{
		if (null == (_targetDirection = targetDirection) ||
			null == (_currentVariateArray = currentVariateArray) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_stepLength = stepLength) ||
			null == (_currentVariateFunctionJacobian = currentVariateFunctionJacobian) ||
			_currentVariateArray.length != _currentVariateFunctionJacobian.length)
		{
			throw new java.lang.Exception ("LineEvolutionVerifierMetrics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Current Variate Array
	 * 
	 * @return The Current Variate Array
	 */

	public double[] currentVariateArray()
	{
		return _currentVariateArray;
	}

	/**
	 * Retrieve the Target Direction Unit Vector
	 * 
	 * @return The Target Direction Unit Vector
	 */

	public org.drip.function.definition.UnitVector targetDirection()
	{
		return _targetDirection;
	}

	/**
	 * Retrieve the Step Length
	 * 
	 * @return The Step Length
	 */

	public double stepLength()
	{
		return _stepLength;
	}

	/**
	 * Retrieve the Function Jacobian at the Current Variate
	 * 
	 * @return The Function Jacobian at the Current Variate
	 */

	public double[] currentVariateFunctionJacobian()
	{
		return _currentVariateFunctionJacobian;
	}

	@Override public java.lang.String toString()
	{
		double[] targetDirectionVector = _targetDirection.component();

		java.lang.String string = "\t[";
		int variateCount = _currentVariateArray.length;

		for (int variateIndex = 0;
			variateIndex < variateCount;
			++variateIndex)
		{
			string = string + org.drip.service.common.FormatUtil.FormatDouble (
				_currentVariateArray[variateIndex],
				2,
				3,
				1.
			) + " |";
		}

		string = string + "]" + org.drip.service.common.FormatUtil.FormatDouble (
			_stepLength,
			1,
			3,
			1.
		) + " || {";

		for (int variateIndex = 0;
			variateIndex < variateCount;
			++variateIndex)
		{
			string = string + org.drip.service.common.FormatUtil.FormatDouble (
				targetDirectionVector[variateIndex],
				1,
				2,
				1.
			) + " |";
		}

		return string + " }";
	}

	/**
	 * Indicate if the Evolution Criterion has been met
	 * 
	 * @return TRUE - The Evolution Criterion has been met
	 */

	public abstract boolean verify();
}
