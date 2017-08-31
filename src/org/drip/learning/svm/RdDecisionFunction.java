
package org.drip.learning.svm;

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
 * RdDecisionFunction exposes the R^d Decision-Function Based SVM Functionality for Classification and
 *  Regression.
 * 
 * The References are:
 * 
 * 	1) Vapnik, V., and A. Chervonenkis (1974): Theory of Pattern Recognition (in Russian), Nauka, Moscow
 * 		USSR.
 * 
 * 	2) Vapnik, V. (1995): The Nature of Statistical Learning, Springer-Verlag, New York.
 * 
 * 	3) Shawe-Taylor, J., P. L. Bartlett, R. C. Williamson, and M. Anthony (1996): A Framework for Structural
 * 		Risk Minimization, in: Proceedings of the 9th Annual Conference on Computational Learning Theory, ACM
 * 		New York 68-76.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdDecisionFunction extends org.drip.function.definition.RdToR1 {
	private double _dblB = java.lang.Double.NaN;
	private double[] _adblInverseMarginWeight = null;
	private org.drip.spaces.metric.RdNormed _rdInverseMargin = null;
	private org.drip.spaces.tensor.RdGeneralizedVector _rdPredictor = null;

	/**
	 * RdDecisionFunction Constructor
	 * 
	 * @param rdPredictor The R^d Metric Input Predictor Space
	 * @param rdInverseMargin The Inverse Margin Weights R^d Space
	 * @param adblInverseMarginWeight Array of Inverse Margin Weights
	 * @param dblB The Kernel Offset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdDecisionFunction (
		final org.drip.spaces.tensor.RdGeneralizedVector rdPredictor,
		final org.drip.spaces.metric.RdNormed rdInverseMargin,
		final double[] adblInverseMarginWeight,
		final double dblB)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_rdPredictor = rdPredictor) || null == (_rdInverseMargin = rdInverseMargin) || null ==
			(_adblInverseMarginWeight = adblInverseMarginWeight) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblB = dblB))
			throw new java.lang.Exception ("RdDecisionFunction ctr: Invalid Inputs");

		int iNumMarginWeight = _adblInverseMarginWeight.length;

		if (0 == iNumMarginWeight || iNumMarginWeight != _rdPredictor.dimension())
			throw new java.lang.Exception ("RdDecisionFunction ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Input Predictor Metric Vector Space
	 * 
	 * @return The Input Predictor Metric Vector Space
	 */

	public org.drip.spaces.tensor.RdGeneralizedVector predictorSpace()
	{
		return _rdPredictor;
	}

	/**
	 * Retrieve the Inverse Margin Weight Metric Vector Space
	 * 
	 * @return The Inverse Margin Weight Metric Vector Space
	 */

	public org.drip.spaces.metric.RdNormed inverseMarginSpace()
	{
		return _rdInverseMargin;
	}

	/**
	 * Retrieve the Decision Kernel Weights
	 * 
	 * @return The Decision Kernel Weights
	 */

	public double[] inverseMarginWeights()
	{
		return _adblInverseMarginWeight;
	}

	/**
	 * Retrieve the Offset
	 * 
	 * @return The Offset
	 */

	public double offset()
	{
		return _dblB;
	}

	/**
	 * Classify the Specified Multi-dimensional Point
	 * 
	 * @param adblX The Multi-dimensional Input Point
	 * 
	 * @return +1/-1 Boolean Space Output Equivalents
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public short classify (
		final double[] adblX)
		throws java.lang.Exception
	{
		return evaluate (adblX) > 0. ? org.drip.spaces.tensor.BinaryBooleanVector.BBV_UP :
			org.drip.spaces.tensor.BinaryBooleanVector.BBV_DOWN;
	}

	/**
	 * Regress on the Specified Multi-dimensional Point
	 * 
	 * @param adblX The Multi-dimensional Input Point
	 * 
	 * @return The Regression Output
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double regress (
		final double[] adblX)
		throws java.lang.Exception
	{
		return evaluate (adblX);
	}

	/**
	 * Compute the Entropy Number Upper Bounds Instance for the Specified Inputs
	 * 
	 * @param dsoFactorizer The Factorizing Diagonal Scaling Operator
	 * @param dblFeatureSpaceMaureyConstant The Feature Space Maurey Constant
	 * 
	 * @return The Entropy Number Upper Bounds Instance
	 */

	public org.drip.learning.svm.DecisionFunctionOperatorBounds entropyNumberUpperBounds (
		final org.drip.learning.kernel.DiagonalScalingOperator dsoFactorizer,
		final double dblFeatureSpaceMaureyConstant)
	{
		try {
			return new org.drip.learning.svm.DecisionFunctionOperatorBounds (dsoFactorizer,
				inverseMarginSpace().populationMetricNorm(), dblFeatureSpaceMaureyConstant,
					predictorSpace().dimension());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Decision Function's Asymptotic Exponent for the Entropy Number
	 * 
	 * @param dsoFactorizer The Factorizing Diagonal Scaling Operator
	 * 
	 * @return The Decision Function's Asymptotic Exponent for the Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Asymptotoc Exponent cannot be computed
	 */

	public double logEntropyNumberAsymptote (
		final org.drip.learning.kernel.DiagonalScalingOperator dsoFactorizer)
		throws java.lang.Exception
	{
		if (null == dsoFactorizer)
			throw new java.lang.Exception
				("RdDecisionFunction::logEntropyNumberAsymptote => Invalid Inputs");

		org.drip.learning.bound.DiagonalOperatorCoveringBound docb = dsoFactorizer.entropyNumberAsymptote();

		if (null == docb)
			throw new java.lang.Exception
				("RdDecisionFunction::logEntropyNumberAsymptote => Cannot get Diagonal Operator Covering Bounds");

		return org.drip.learning.bound.DiagonalOperatorCoveringBound.BASE_DIAGONAL_ENTROPY_ASYMPTOTE_EXPONENT
			== docb.entropyNumberAsymptoteType() ? -1. * docb.entropyNumberAsymptoteExponent() - 0.5 :
				-1. * docb.entropyNumberAsymptoteExponent();
	}

	/**
	 * Optimize the Hyper-plane for the Purposes of Regression
	 * 
	 * @param adblEmpirical The Empirical Observation Array
	 * @param dblMargin The Optimization Margin
	 * @param dblInverseWidthNormConstraint The Inverse Width Norm Constraint
	 * 
	 * @return TRUE - The Hyper-plane has been successfully Optimized for Regression
	 */

	public abstract boolean optimizeRegressionHyperplane (
		final double[] adblEmpirical,
		final double dblMargin,
		final double dblInverseWidthNormConstraint
	);

	/**
	 * Optimize the Hyper-plane for the Purposes of Classification
	 * 
	 * @param asEmpirical The Empirical Observation Array
	 * @param dblMargin The Optimization Margin
	 * @param dblInverseWidthNormConstraint The Inverse Width Norm Constraint
	 * 
	 * @return TRUE - The Hyper-plane has been successfully Optimized for Classification
	 */

	public abstract boolean optimizeClassificationHyperplane (
		final short[] asEmpirical,
		final double dblMargin,
		final double dblInverseWidthNormConstraint
	);
}
