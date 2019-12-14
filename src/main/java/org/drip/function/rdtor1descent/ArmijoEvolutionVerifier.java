
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
 * <i>ArmijoEvolutionVerifier</i> implements the Armijo Criterion used for the Inexact Line Search Increment
 * Generation to ascertain that the Function has reduced sufficiently. The Reference is:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial
 * 				Derivatives <i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3
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

public class ArmijoEvolutionVerifier
	extends org.drip.function.rdtor1descent.LineEvolutionVerifier
{

	/**
	 * The Nocedal-Wright Armijo Parameter
	 */

	public static final double NOCEDAL_WRIGHT_ARMIJO_PARAMETER = 0.0001;

	private boolean _maximizerCheck = false;
	private double _armijoParameter = java.lang.Double.NaN;

	/**
	 * Construct the Nocedal-Wright Armijo Evolution Verifier
	 * 
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @return The Nocedal-Wright Armijo Evolution Verifier Instance
	 */

	public static final ArmijoEvolutionVerifier NocedalWrightStandard (
		final boolean maximizerCheck)
	{
		try
		{
			return new ArmijoEvolutionVerifier (
				NOCEDAL_WRIGHT_ARMIJO_PARAMETER,
				maximizerCheck
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ArmijoEvolutionVerifier Constructor
	 * 
	 * @param armijoParameter The Armijo Parameter
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArmijoEvolutionVerifier (
		final double armijoParameter,
		final boolean maximizerCheck)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_armijoParameter = armijoParameter))
		{
			throw new java.lang.Exception ("ArmijoEvolutionVerifier Constructor => Invalid Inputs");
		}

		_maximizerCheck = maximizerCheck;
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
	 * Retrieve the Armijo Parameter
	 * 
	 * @return The Armijo Parameter
	 */

	public double armijoParameter()
	{
		return _armijoParameter;
	}

	@Override public org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics metrics (
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final org.drip.function.definition.RdToR1 multivariateFunction,
		final double stepLength)
	{
		try
		{
			return null == multivariateFunction ? null :
				new org.drip.function.rdtor1descent.ArmijoEvolutionVerifierMetrics (
					_armijoParameter,
					_maximizerCheck,
					targetDirectionUnitVector,
					currentVariateArray,
					stepLength,
					multivariateFunction.evaluate (
						currentVariateArray
					),
					multivariateFunction.evaluate (
						NextVariateArray (
							targetDirectionUnitVector,
							currentVariateArray,
							stepLength
						)
					),
					multivariateFunction.jacobian (
						currentVariateArray
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
