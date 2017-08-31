
package org.drip.spline.segment;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * This Interface implements the Segment's Basis Evaluator Functions. It exports the following functions:
 * 	- Retrieve the number of Segment's Basis Functions.
 * 	- Set the Inelastics that provides the enveloping Context the Basis Evaluation.
 * 	- Clone/Replicate the current Basis Evaluator Instance.
 * 	- Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate.
 * 	- Compute the Basis Function Value at the specified Predictor Ordinate.
 * 	- Compute the Response Value at the specified Predictor Ordinate.
 * 	- Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the
 * 		specified Predictor Ordinate.
 * 	- Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the specified
 * 		Predictor Ordinate.
 * 	- Compute the Response Value Derivative at the specified Predictor Ordinate.
 * 
 * @author Lakshmi Krishnamurthy
 */

public interface BasisEvaluator {

	/**
	 * Retrieve the number of Segment's Basis Functions
	 * 
	 * @return The Number of Segment's Basis Functions
	 */

	public abstract int numBasis();

	/**
	 * Set the Inelastics that provides the enveloping Context the Basis Evaluation
	 * 
	 * @param ics The Inelastic Settings
	 * 
	 * @return TRUE - The inelastics has been set
	 */

	public abstract boolean setContainingInelastics (
		final org.drip.spline.segment.LatentStateInelastic ics);

	/**
	 * Clone/Replicate the current Basis Evaluator Instance
	 * 
	 * @return TRUE - The Replicated Basis Evaluator Instance
	 */

	public abstract BasisEvaluator replicate();

	/**
	 * Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iBasisFunctionIndex Index representing the Basis Function in the Basis Function Set
	 *  
	 * @return The Response Value of the indexed Basis Function at the specified Predictor Ordinate
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative cannot be computed
	 */

	public abstract double shapedBasisFunctionResponse (
		final double dblPredictorOrdinate,
		final int iBasisFunctionIndex)
		throws java.lang.Exception;

	/**
	 * Compute the Basis Function Value at the specified Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * 
	 * @return The Basis Function Value
	 * 
	 * @throws java.lang.Exception Thrown if the Basis Function Value cannot be computed
	 */

	public abstract double unshapedResponseValue (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate)
		throws java.lang.Exception;

	/**
	 * Compute the Response Value at the specified Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws java.lang.Exception Thrown if the Basis Function Value cannot be computed
	 */

	public abstract double responseValue (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate)
		throws java.lang.Exception;

	/**
	 * Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the
	 *	specified Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * @param iBasisFunctionIndex Index representing the Basis Function in the Basis Function Set
	 *  
	 * @return The Ordered Derivative of the Response Value off of the Indexed Basis Function
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative cannot be computed
	 */

	public abstract double shapedBasisFunctionDerivative (
		final double dblPredictorOrdinate,
		final int iOrder,
		final int iBasisFunctionIndex)
		throws java.lang.Exception;

	/**
	 * Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the specified
	 *  Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Ordered Derivative of the Response Value off of the Basis Function Set
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative of the Basis Function Set cannot be
	 * 	computed
	 */

	public abstract double unshapedBasisFunctionDerivative (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Compute the Response Value Derivative at the specified Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Response Value Derivative
	 * 
	 * @throws java.lang.Exception Thrown if the Response Value Derivative cannot be computed
	 */

	public abstract double responseValueDerivative (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception;
}
