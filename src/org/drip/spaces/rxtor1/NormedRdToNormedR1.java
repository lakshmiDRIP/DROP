
package org.drip.spaces.rxtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * NormedRdToNormedR1 is the Abstract Class underlying the f : Validated Normed R^d To Validated Normed R^1
 *  Function Spaces.
 * 
 * The Reference we've used is:
 * 
 * 	- Carl, B., and I. Stephani (1990): Entropy, Compactness, and Approximation of Operators, Cambridge
 * 		University Press, Cambridge UK.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedRdToNormedR1 extends org.drip.spaces.rxtor1.NormedRxToNormedR1 {
	private org.drip.spaces.metric.RdNormed _rdInput = null;
	private org.drip.spaces.metric.R1Normed _r1Output = null;
	private org.drip.function.definition.RdToR1 _funcRdToR1 = null;

	protected NormedRdToNormedR1 (
		final org.drip.spaces.metric.RdNormed rdInput,
		final org.drip.spaces.metric.R1Normed r1Output,
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		if (null == (_rdInput = rdInput) || null == (_r1Output = r1Output))
			throw new java.lang.Exception ("NormedRdToNormedR1 ctr: Invalid Inputs");

		_funcRdToR1 = funcRdToR1;
	}

	/**
	 * Retrieve the Underlying RdToR1 Function
	 * 
	 * @return The Underlying RdToR1 Function
	 */

	public org.drip.function.definition.RdToR1 function()
	{
		return _funcRdToR1;
	}

	@Override public double sampleSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		if (null == _funcRdToR1 || null == gvvi || !gvvi.tensorSpaceType().match (_rdInput))
			throw new java.lang.Exception ("NormedRdToNormedR1::sampleSupremumNorm => Invalid Input");

		double[][] aadblInstance = ((org.drip.spaces.instance.ValidatedRd) gvvi).instance();

		int iNumSample = aadblInstance.length;

		double dblSupremumNorm = java.lang.Math.abs (_funcRdToR1.evaluate (aadblInstance[0]));

		for (int i = 1; i < iNumSample; ++i) {
			double dblResponse = java.lang.Math.abs (_funcRdToR1.evaluate (aadblInstance[i]));

			if (dblResponse > dblSupremumNorm) dblSupremumNorm = dblResponse;
		}

		return dblSupremumNorm;
	}

	@Override public double sampleMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		int iPNorm = _r1Output.pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) return sampleSupremumNorm (gvvi);

		if (null == _funcRdToR1 || null == gvvi || !gvvi.tensorSpaceType().match (_rdInput))
			throw new java.lang.Exception ("NormedRdToNormedR1::sampleMetricNorm => Invalid Input");

		double[][] aadblInstance = ((org.drip.spaces.instance.ValidatedRd) gvvi).instance();

		int iNumSample = aadblInstance.length;
		double dblNorm = 0.;

		for (int i = 0; i < iNumSample; ++i)
			dblNorm += java.lang.Math.pow (java.lang.Math.abs (_funcRdToR1.evaluate (aadblInstance[i])),
				iPNorm);

		return java.lang.Math.pow (dblNorm, 1. / iPNorm);
	}

	@Override public double populationESS()
		throws java.lang.Exception
	{
		if (null == _funcRdToR1)
			throw new java.lang.Exception ("NormedRdToNormedR1::populationESS => Invalid Input");

		return _funcRdToR1.evaluate (_rdInput.populationMode());
	}

	@Override public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _rdInput;
	}

	@Override public org.drip.spaces.metric.R1Normed outputMetricVectorSpace()
	{
		return _r1Output;
	}
}
