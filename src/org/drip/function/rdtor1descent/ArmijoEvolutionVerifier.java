
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
 * ArmijoEvolutionVerifier implements the Armijo Criterion used for the Inexact Line Search Increment
 *  Generation to ascertain that the Function has reduced sufficiently. The Reference is:
 * 
 * 	- Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial Derivatives,
 * 		Pacific Journal of Mathematics 16 (1) 1-3.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ArmijoEvolutionVerifier extends org.drip.function.rdtor1descent.LineEvolutionVerifier {

	/**
	 * The Nocedal-Wright Armijo Parameter
	 */

	public static final double NOCEDAL_WRIGHT_ARMIJO_PARAMETER = 0.0001;

	private boolean _bMaximizerCheck = false;
	private double _dblArmijoParameter = java.lang.Double.NaN;

	/**
	 * Construct the Nocedal-Wright Armijo Evolution Verifier
	 * @param bMaximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @return The Nocedal-Wright Armijo Evolution Verifier Instance
	 */

	public static final ArmijoEvolutionVerifier NocedalWrightStandard (
		final boolean bMaximizerCheck)
	{
		try {
			return new ArmijoEvolutionVerifier (NOCEDAL_WRIGHT_ARMIJO_PARAMETER, bMaximizerCheck);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ArmijoEvolutionVerifier Constructor
	 * 
	 * @param dblArmijoParameter The Armijo Parameter
	 * @param bMaximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArmijoEvolutionVerifier (
		final double dblArmijoParameter,
		final boolean bMaximizerCheck)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblArmijoParameter = dblArmijoParameter))
			throw new java.lang.Exception ("ArmijoEvolutionVerifier Constructor => Invalid Inputs");

		_bMaximizerCheck = bMaximizerCheck;
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
	 * Retrieve the Armijo Parameter
	 * 
	 * @return The Armijo Parameter
	 */

	public double armijoParameter()
	{
		return _dblArmijoParameter;
	}

	@Override public org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics metrics (
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final org.drip.function.definition.RdToR1 funcRdToR1,
		final double dblStepLength)
	{
		try {
			return null == funcRdToR1 ? null : new
				org.drip.function.rdtor1descent.ArmijoEvolutionVerifierMetrics (_dblArmijoParameter,
					_bMaximizerCheck, uvTargetDirection, adblCurrentVariate, dblStepLength,
						funcRdToR1.evaluate (adblCurrentVariate), funcRdToR1.evaluate (NextVariate
							(uvTargetDirection, adblCurrentVariate, dblStepLength)), funcRdToR1.jacobian
								(adblCurrentVariate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
