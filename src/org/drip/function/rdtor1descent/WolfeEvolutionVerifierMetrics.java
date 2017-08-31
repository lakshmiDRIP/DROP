
package org.drip.function.rdtor1descent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * WolfeEvolutionVerifierMetrics implements the Wolfe Criterion used for the Inexact Line Search Increment
 *  Generation. The References are:
 * 
 * 	- Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial Derivatives,
 * 		Pacific Journal of Mathematics 16 (1) 1-3.
 * 
 * 	- Wolfe, P. (1969): Convergence Conditions for Ascent Methods, SIAM Review 11 (2) 226-235.
 * 
 * 	- Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections, SIAM Review 13 (2)
 * 		185-188.
 * 
 * 	- Nocedal, J., and S. Wright (1999): Numerical Optimization, Wiley.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WolfeEvolutionVerifierMetrics extends
	org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics {
	private boolean _bMaximizerCheck = false;
	private boolean _bStrongCurvatureCriterion = false;
	private double[] _adblNextVariateFunctionJacobian = null;
	private double _dblArmijoParameter = java.lang.Double.NaN;
	private double _dblCurvatureParameter = java.lang.Double.NaN;
	private double _dblNextVariateFunctionValue = java.lang.Double.NaN;
	private double _dblCurrentVariateFunctionValue = java.lang.Double.NaN;

	/**
	 * WolfeEvolutionVerifierMetrics Constructor
	 * 
	 * @param dblArmijoParameter The Armijo Criterion Parameter
	 * @param bMaximizerCheck TRUE - Perform a Check for the Function Maxima
	 * @param dblCurvatureParameter The Curvature Criterion Parameter
	 * @param bStrongCurvatureCriterion TRUE - Apply the "Strong" Curvature Criterion
	 * @param uvTargetDirection The Target Direction Unit Vector
	 * @param adblCurrentVariate Array of the Current Variate
	 * @param dblStepLength The Incremental Step Length
	 * @param dblCurrentVariateFunctionValue The Function Value at the Current Variate
	 * @param dblNextVariateFunctionValue The Function Value at the Next Variate
	 * @param adblCurrentVariateFunctionJacobian The Function Jacobian at the Current Variate
	 * @param adblNextVariateFunctionJacobian The Function Jacobian at the Next Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public WolfeEvolutionVerifierMetrics (
		final double dblArmijoParameter,
		final boolean bMaximizerCheck,
		final double dblCurvatureParameter,
		final boolean bStrongCurvatureCriterion,
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final double dblStepLength,
		final double dblCurrentVariateFunctionValue,
		final double dblNextVariateFunctionValue,
		final double[] adblCurrentVariateFunctionJacobian,
		final double[] adblNextVariateFunctionJacobian)
		throws java.lang.Exception
	{
		super (uvTargetDirection, adblCurrentVariate, dblStepLength, adblCurrentVariateFunctionJacobian);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblArmijoParameter = dblArmijoParameter) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCurvatureParameter = dblCurvatureParameter) ||
				null == (_adblNextVariateFunctionJacobian = adblNextVariateFunctionJacobian) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblCurrentVariateFunctionValue =
						dblCurrentVariateFunctionValue) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblNextVariateFunctionValue = dblNextVariateFunctionValue) ||
								adblCurrentVariate.length != _adblNextVariateFunctionJacobian.length)
			throw new java.lang.Exception ("WolfeEvolutionVerifierMetrics Constructor => Invalid Inputs");

		_bMaximizerCheck = bMaximizerCheck;
		_bStrongCurvatureCriterion = bStrongCurvatureCriterion;
	}

	/**
	 * Retrieve the Armijo Parameter
	 * 
	 * @return The Armijo Parameter
	 */

	public double armijoParameter()
	{
		return _dblArmijoParameter;
	}

	/**
	 * Indicate if the Check is for Minimizer/Maximizer
	 * 
	 * @return TRUE - The Check is for Maximizer
	 */

	public boolean maximizerCheck()
	{
		return _bMaximizerCheck;
	}

	/**
	 * Retrieve the Curvature Parameter
	 * 
	 * @return The Curvature Parameter
	 */

	public double curvatureParameter()
	{
		return _dblCurvatureParameter;
	}

	/**
	 * Retrieve Whether of not the "Strong" Curvature Criterion needs to be met
	 * 
	 * @return TRUE - The "Strong" Curvature Criterion needs to be met
	 */

	public boolean strongCurvatureCriterion()
	{
		return _bStrongCurvatureCriterion;
	}

	/**
	 * Retrieve the Function Value at the Current Variate
	 * 
	 * @return The Function Value at the Current Variate
	 */

	public double currentVariateFunctionValue()
	{
		return _dblCurrentVariateFunctionValue;
	}

	/**
	 * Retrieve the Function Value at the Next Variate
	 * 
	 * @return The Function Value at the Next Variate
	 */

	public double nextVariateFunctionValue()
	{
		return _dblNextVariateFunctionValue;
	}

	/**
	 * Retrieve the Function Jacobian at the Next Variate
	 * 
	 * @return The Function Jacobian at the Next Variate
	 */

	public double[] nextVariateFunctionJacobian()
	{
		return _adblNextVariateFunctionJacobian;
	}

	/**
	 * Indicate if the Wolfe Criterion has been met
	 * 
	 * @return TRUE - The Wolfe Criterion has been met
	 */

	public boolean verify()
	{
		double[] adblDirectionVector = targetDirection().component();

		double[] adblCurrentVariateFunctionJacobian = currentVariateFunctionJacobian();

		try {
			double dblGradientUpdatedFunctionValue = _dblCurrentVariateFunctionValue + _dblArmijoParameter *
				stepLength() * org.drip.quant.linearalgebra.Matrix.DotProduct (adblDirectionVector,
					adblCurrentVariateFunctionJacobian);

			if ((_bMaximizerCheck && _dblNextVariateFunctionValue < dblGradientUpdatedFunctionValue) ||
				(!_bMaximizerCheck && _dblNextVariateFunctionValue > dblGradientUpdatedFunctionValue))
				return false;

			double dblNextFunctionIncrement = org.drip.quant.linearalgebra.Matrix.DotProduct
				(adblDirectionVector, _adblNextVariateFunctionJacobian);

			double dblParametrizedCurrentFunctionIncrement = _dblCurvatureParameter *
				org.drip.quant.linearalgebra.Matrix.DotProduct (adblDirectionVector,
					adblCurrentVariateFunctionJacobian);

			return _bStrongCurvatureCriterion ? java.lang.Math.abs (dblNextFunctionIncrement) <=
				java.lang.Math.abs (dblParametrizedCurrentFunctionIncrement) : dblNextFunctionIncrement >=
					dblParametrizedCurrentFunctionIncrement;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
