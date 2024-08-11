
package org.drip.function.rdtor1descent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>ArmijoEvolutionVerifierMetrics</i> implements the Armijo Criterion used for the Inexact Line Search
 * Increment Generation to ascertain that the Function has reduced sufficiently. The Reference is:
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

public class ArmijoEvolutionVerifierMetrics
	extends org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics
{
	private boolean _maximizerCheck = false;
	private double _armijoParameter = java.lang.Double.NaN;
	private double _nextVariateFunctionValue = java.lang.Double.NaN;
	private double _currentVariateFunctionValue = java.lang.Double.NaN;

	/**
	 * ArmijoEvolutionVerifierMetrics Constructor
	 * 
	 * @param armijoParameter The Armijo Parameter
	 * @param maximizerCheck TRUE - Perform a Check for the Function Maxima
	 * @param targetDirectionUnitVector the Target Direction Unit Vector
	 * @param currentVariateArray Array of the Current Variate
	 * @param stepLength The Incremental Step Length
	 * @param currentVariateFunctionValue The Function Value at the Current Variate
	 * @param nextVariateFunctionValue The Function Value at the Next Variate
	 * @param currentVariateFunctionJacobian The Function Jacobian at the Current Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArmijoEvolutionVerifierMetrics (
		final double armijoParameter,
		final boolean maximizerCheck,
		final org.drip.function.definition.UnitVector targetDirectionUnitVector,
		final double[] currentVariateArray,
		final double stepLength,
		final double currentVariateFunctionValue,
		final double nextVariateFunctionValue,
		final double[] currentVariateFunctionJacobian)
		throws java.lang.Exception
	{
		super (
			targetDirectionUnitVector,
			currentVariateArray,
			stepLength,
			currentVariateFunctionJacobian
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_armijoParameter = armijoParameter) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_currentVariateFunctionValue =
				currentVariateFunctionValue) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_nextVariateFunctionValue =
				nextVariateFunctionValue))
		{
			throw new java.lang.Exception ("ArmijoEvolutionVerifierMetrics Constructor => Invalid Inputs");
		}

		_maximizerCheck = maximizerCheck;
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
	 * Retrieve the Function Value at the Current Variate
	 * 
	 * @return The Function Value at the Current Variate
	 */

	public double currentVariateFunctionValue()
	{
		return _currentVariateFunctionValue;
	}

	/**
	 * Retrieve the Function Value at the Next Variate
	 * 
	 * @return The Function Value at the Next Variate
	 */

	public double nextVariateFunctionValue()
	{
		return _nextVariateFunctionValue;
	}

	/**
	 * Indicate if the Armijo Criterion has been met
	 * 
	 * @return TRUE - The Armijo Criterion has been met
	 */

	public boolean verify()
	{
		try
		{
			double gradientUpdatedFunctionValue = _currentVariateFunctionValue +
				_armijoParameter * stepLength() * org.drip.numerical.linearalgebra.R1MatrixUtil.DotProduct (
					targetDirection().component(),
					currentVariateFunctionJacobian()
				);

			return _maximizerCheck ? _nextVariateFunctionValue >= gradientUpdatedFunctionValue :
				_nextVariateFunctionValue <= gradientUpdatedFunctionValue;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
