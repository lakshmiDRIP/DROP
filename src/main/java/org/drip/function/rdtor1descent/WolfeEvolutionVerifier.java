
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
 * <i>WolfeEvolutionVerifier</i> implements the Wolfe Criterion used for the Inexact Line Search Increment
 * Generation. The References are:
 * 
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

public class WolfeEvolutionVerifier
	extends org.drip.function.rdtor1descent.LineEvolutionVerifier
{
	private boolean _maximizerCheck = false;
	private boolean _strongCurvatureCriterion = false;
	private double _armijoParameter = java.lang.Double.NaN;
	private double _curvatureParameter = java.lang.Double.NaN;

	/**
	 * Construct the Nocedal-Wright Wolfe Evolution Verifier
	 * 
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * @param strongCurvatureCriterion TRUE - Apply the Strong Curvature Criterion
	 * 
	 * @return The Nocedal-Wright Wolfe Evolution Verifier Instance
	 */

	public static final WolfeEvolutionVerifier NocedalWrightStandard (
		final boolean maximizerCheck,
		final boolean strongCurvatureCriterion)
	{
		try
		{
			return new WolfeEvolutionVerifier (
				org.drip.function.rdtor1descent.ArmijoEvolutionVerifier.NOCEDAL_WRIGHT_ARMIJO_PARAMETER,
				maximizerCheck,
				org.drip.function.rdtor1descent.CurvatureEvolutionVerifier.NOCEDAL_WRIGHT_CURVATURE_PARAMETER,
				strongCurvatureCriterion
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * WolfeEvolutionVerifier Constructor
	 * 
	 * @param armijoParameter The Armijo Criterion Parameter
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * @param curvatureParameter The Curvature Parameter
	 * @param strongCurvatureCriterion TRUE - Apply the Strong Curvature Criterion
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public WolfeEvolutionVerifier (
		final double armijoParameter,
		final boolean maximizerCheck,
		final double curvatureParameter,
		final boolean strongCurvatureCriterion)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_armijoParameter = armijoParameter) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_curvatureParameter = curvatureParameter))
		{
			throw new java.lang.Exception ("WolfeEvolutionVerifier Constructor => Invalid Inputs");
		}

		_maximizerCheck = maximizerCheck;
		_strongCurvatureCriterion = strongCurvatureCriterion;
	}

	/**
	 * Retrieve the Armijo Parameter
	 * 
	 * @return The Armijo Parameter
	 */

	public double armijoParameter()
	{
		return _armijoParameter;
	}

	/**
	 * Indicate if the Check is for Minimizer/Maximizer
	 * 
	 * @return TRUE - The Check is for Maximizer
	 */

	public boolean maximizerCheck()
	{
		return _maximizerCheck;
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

	@Override public org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics metrics (
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final org.drip.function.definition.RdToR1 multivariateFunction,
		final double stepLength)
	{
		double[] nextVariateArray = NextVariateArray (
			targetDirectionUnitVector,
			currentVariateArray,
			stepLength
		);

		try
		{
			return null == multivariateFunction ? null :
				new org.drip.function.rdtor1descent.WolfeEvolutionVerifierMetrics (
					_armijoParameter,
					_maximizerCheck,
					_curvatureParameter,
					_strongCurvatureCriterion,
					targetDirectionUnitVector,
					currentVariateArray,
					stepLength,
					multivariateFunction.evaluate (
						currentVariateArray
					),
					multivariateFunction.evaluate (
						nextVariateArray
					),
					multivariateFunction.jacobian (
						currentVariateArray
					),
					multivariateFunction.jacobian (
						nextVariateArray
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
