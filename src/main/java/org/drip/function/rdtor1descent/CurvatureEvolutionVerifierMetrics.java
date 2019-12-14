
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
 * <i>CurvatureEvolutionVerifierMetrics</i> implements the Armijo Criterion used for the Inexact Line Search
 * Increment Generation to ascertain that the Gradient of the Function has reduced sufficiently. The
 * References are:
 * <br><br>
 * 	<ul>
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

public class CurvatureEvolutionVerifierMetrics
	extends org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics
{
	private boolean _strongCurvatureCriterion = false;
	private double[] _nextVariateFunctionJacobian = null;
	private double _curvatureParameter = java.lang.Double.NaN;

	/**
	 * CurvatureEvolutionVerifierMetrics Constructor
	 * 
	 * @param curvatureParameter The Curvature Criterion Parameter
	 * @param strongCurvatureCriterion TRUE - Apply the "Strong" Curvature Criterion
	 * @param targetDirectionUnitVector The Target Direction Unit Vector
	 * @param currentVariateArray Array of Current Variate
	 * @param stepLength The Incremental Step Length
	 * @param currentVariateFunctionJacobian The Function Jacobian at the Current Variate
	 * @param nextVariateFunctionJacobian The Function Jacobian at the Next Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CurvatureEvolutionVerifierMetrics (
		final double curvatureParameter,
		final boolean strongCurvatureCriterion,
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final double stepLength,
		final double[] currentVariateFunctionJacobian,
		final double[] nextVariateFunctionJacobian)
		throws java.lang.Exception
	{
		super (
			targetDirectionUnitVector,
			currentVariateArray,
			stepLength,
			currentVariateFunctionJacobian
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_curvatureParameter = curvatureParameter) ||
			null == (_nextVariateFunctionJacobian = nextVariateFunctionJacobian) ||
			currentVariateArray.length != _nextVariateFunctionJacobian.length)
		{
			throw new java.lang.Exception
				("CurvatureEvolutionVerifierMetrics Constructor => Invalid Inputs");
		}

		_strongCurvatureCriterion = strongCurvatureCriterion;
	}

	/**
	 * Retrieve the Curvature Parameter
	 * 
	 * @return The Curvature Parameter
	 */

	public double curvatureParameter()
	{
		return _curvatureParameter;
	}

	/**
	 * Retrieve Whether of not the "Strong" Curvature Criterion needs to be met
	 * 
	 * @return TRUE - The "Strong" Curvature Criterion needs to be met
	 */

	public boolean strongCurvatureCriterion()
	{
		return _strongCurvatureCriterion;
	}

	/**
	 * Retrieve the Function Jacobian at the Next Variate
	 * 
	 * @return The Function Jacobian at the Next Variate
	 */

	public double[] nextVariateFunctionJacobian()
	{
		return _nextVariateFunctionJacobian;
	}

	/**
	 * Indicate if the Curvature Criterion has been met
	 * 
	 * @return TRUE - The Curvature Criterion has been met
	 */

	public boolean verify()
	{
		double[] targetDirectionVector = targetDirection().component();

		try
		{
			double nextFunctionIncrement = org.drip.numerical.linearalgebra.Matrix.DotProduct (
				targetDirectionVector,
				_nextVariateFunctionJacobian
			);

			double parametrizedCurrentFunctionIncrement =
				_curvatureParameter * org.drip.numerical.linearalgebra.Matrix.DotProduct (
					targetDirectionVector,
					currentVariateFunctionJacobian()
				);

			return _strongCurvatureCriterion ?
				java.lang.Math.abs (
					nextFunctionIncrement
				) <= java.lang.Math.abs (
					parametrizedCurrentFunctionIncrement
				) : nextFunctionIncrement >= parametrizedCurrentFunctionIncrement;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
