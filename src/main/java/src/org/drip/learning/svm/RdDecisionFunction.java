
package org.drip.learning.svm;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>RdDecisionFunction</i> exposes the R<sup>d</sup> Decision-Function Based SVM Functionality for
 * Classification and Regression.
 * 
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 * 		Shawe-Taylor, J., P. L. Bartlett, R. C. Williamson, and M. Anthony (1996): A Framework for Structural
 * 			Risk Minimization, in: <i>Proceedings of the 9th Annual Conference on Computational Learning
 * 			Theory</i> <b>ACM</b> New York 68-76
 * 	</li>
 * 	<li>
 * 		Vapnik, V., and A. Chervonenkis (1974): <i>Theory of Pattern Recognition (in Russian)</i>
 * 			<b>Nauka</b> Moscow USSR
 * 	</li>
 * 	<li>
 * 		Vapnik, V. (1995): <i>The Nature of Statistical Learning</i> <b>Springer-Verlag</b> New York
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/svm">Kernel SVM Decision Function Operator</a></li>
 *  </ul>
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
			(_adblInverseMarginWeight = adblInverseMarginWeight) || !org.drip.numerical.common.NumberUtil.IsValid
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
