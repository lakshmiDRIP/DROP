
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
 * DecisionFunctionOperatorBounds implements the Dot Product Entropy Number Upper Bounds for the Product of
 * 	Kernel Feature Map Function and the Scaling Diagonal Operator.
 *  
 *  The References are:
 *  
 *  1) Ash, R. (1965): Information Theory, Inter-science New York.
 *  
 *  2) Konig, H. (1986): Eigenvalue Distribution of Compact Operators, Birkhauser, Basel, Switzerland. 
 *  
 *  3) Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 *  	Approximation Numbers of Operators, Journal of Approximation Theory 49 219-237.
 *  
 * 	4) Carl, B., and I. Stephani (1990): Entropy, Compactness, and Approximation of Operators, Cambridge
 * 		University Press, Cambridge UK.
 * 
 *  5) Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  	Combinations and mlps, in: Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf,
 *  	and D. Schuurmans - editors, MIT Press, Cambridge, MA.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DecisionFunctionOperatorBounds {
	private int _iFeatureSpaceDimension = -1;
	private double _dblInverseMarginNormBound = java.lang.Double.NaN;
	private double _dblFeatureSpaceMaureyConstant = java.lang.Double.NaN;
	private org.drip.learning.kernel.DiagonalScalingOperator _dsoFactorizer = null;

	/**
	 * DecisionFunctionOperatorBounds Constructor
	 * 
	 * @param dsoFactorizer The Factorizing Diagonal Scaling Operator
	 * @param dblInverseMarginNormBound The Decision Function Inverse Margin Norm Bound
	 * @param dblFeatureSpaceMaureyConstant The Kernel Feature Space Function Maurey Constant
	 * @param iFeatureSpaceDimension The Kernel Feature Space Dimension
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DecisionFunctionOperatorBounds (
		final org.drip.learning.kernel.DiagonalScalingOperator dsoFactorizer,
		final double dblInverseMarginNormBound,
		final double dblFeatureSpaceMaureyConstant,
		final int iFeatureSpaceDimension)
		throws java.lang.Exception
	{
		if (null == (_dsoFactorizer = dsoFactorizer) || !org.drip.quant.common.NumberUtil.IsValid
			(_dblInverseMarginNormBound = dblInverseMarginNormBound) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblFeatureSpaceMaureyConstant =
					dblFeatureSpaceMaureyConstant) || 0 >= (_iFeatureSpaceDimension =
						iFeatureSpaceDimension))
			throw new java.lang.Exception ("DecisionFunctionOperatorBounds ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Factorizing Diagonal Scaling Operator Instance
	 * 
	 * @return The Factorizing Diagonal Scaling Operator Instance
	 */

	public org.drip.learning.kernel.DiagonalScalingOperator factorizingOperator()
	{
		return _dsoFactorizer;
	}

	/**
	 * Retrieve the Norm Upper Bound of the Inverse Margin
	 * 
	 * @return The Norm Upper Bound of the Inverse Margin
	 */

	public double inverseMarginNormBound()
	{
		return _dblInverseMarginNormBound;
	}

	/**
	 * Retrieve the Feature Space Maurey Constant
	 * 
	 * @return The Feature Space Maurey Constant
	 */

	public double featureSpaceMaureyConstant()
	{
		return _dblFeatureSpaceMaureyConstant;
	}

	/**
	 * Retrieve the Feature Space Dimension
	 * 
	 * @return The Feature Space Dimension
	 */

	public double featureSpaceDimension()
	{
		return _iFeatureSpaceDimension;
	}

	/**
	 * Compute the Feature Space's Maurey Bound for the Entropy Number given the specified Entropy Number
	 * 
	 * @param iFeatureSpaceEntropyNumber The Feature Space Entropy Number
	 * 
	 * @return The Feature Space's Maurey Bound for the specified Entropy Number
	 * 
	 * @throws java.lang.Exception The Feature Space's Maurey Bound cannot be computed
	 */

	public double featureSpaceMaureyBound (
		final int iFeatureSpaceEntropyNumber)
		throws java.lang.Exception
	{
		if (0 >= iFeatureSpaceEntropyNumber)
			throw new java.lang.Exception
				("DecisionFunctionOperatorBounds::featureSpaceMaureyBound => Invalid Inputs");

		return java.lang.Math.sqrt (1. / (iFeatureSpaceEntropyNumber * java.lang.Math.sqrt
			(java.lang.Math.log (1. + (((double) _iFeatureSpaceDimension) / java.lang.Math.log
				(iFeatureSpaceEntropyNumber))))));
	}

	/**
	 * Compute the Decision Function Entropy Number Upper Bound using the Product of the Feature Space's
	 *  Maurey Upper Bound for the Entropy for the specified Entropy Number and the Scaling Operator Entropy
	 *  Number Upper Bound
	 * 
	 * @param iFeatureSpaceEntropyNumber The Feature Space Entropy Number
	 * 
	 * @return The Feature Space's Operator Entropy for the specified Entropy Number
	 * 
	 * @throws java.lang.Exception The Feature Space's Operator Entropy cannot be computed
	 */

	public double featureMaureyOperatorEntropy (
		final int iFeatureSpaceEntropyNumber)
		throws java.lang.Exception
	{
		return _dblInverseMarginNormBound * _dsoFactorizer.entropyNumberUpperBound() *
			featureSpaceMaureyBound (iFeatureSpaceEntropyNumber);
	}

	/**
	 * Compute the Decision Function Entropy Number Upper Bound using the Product of the Feature Space's
	 *  Maurey Upper Bound for the Entropy for the specified Entropy Number and the Scaling Operator Norm
	 * 
	 * @param iFeatureSpaceEntropyNumber The Feature Space Entropy Number
	 * 
	 * @return The Feature Space's Operator Norm for the specified Entropy Number
	 * 
	 * @throws java.lang.Exception The Feature Space's Operator Norm cannot be computed
	 */

	public double featureMaureyOperatorNorm (
		final int iFeatureSpaceEntropyNumber)
		throws java.lang.Exception
	{
		return _dblInverseMarginNormBound * _dsoFactorizer.norm() * featureSpaceMaureyBound
			(iFeatureSpaceEntropyNumber);
	}

	/**
	 * Compute the Decision Function Entropy Number Upper Bound using the Product of the Feature Space's
	 *  Norm for the Upper Bound of the Entropy Number and the Scaling Operator Norm
	 * 
	 * @return The Entropy Number Upper Bound using the Product Norm
	 * 
	 * @throws java.lang.Exception The Entropy Number Upper Bound cannot be computed
	 */

	public double productFeatureOperatorNorm()
		throws java.lang.Exception
	{
		return _dblInverseMarginNormBound * _dsoFactorizer.norm();
	}

	/**
	 * Compute the Decision Function Entropy Number Upper Bound using the Product of the Feature Space's
	 *  Norm for the Upper Bound of the Entropy Number and the Scaling Operator Entropy Number Upper Bound
	 * 
	 * @return The Entropy Number Upper Bound using the Product Norm
	 * 
	 * @throws java.lang.Exception The Entropy Number Upper Bound cannot be computed
	 */

	public double featureNormOperatorEntropy()
		throws java.lang.Exception
	{
		return _dblInverseMarginNormBound * _dsoFactorizer.entropyNumberUpperBound();
	}

	/**
	 * Compute the Infimum of the Decision Function Operator Upper Bound across all the Product Bounds for
	 *  the specified Feature Space Entropy Number
	 * 
	 * @param iFeatureSpaceEntropyNumber The specified Feature Space Entropy Number
	 * 
	 * @return Infimum of the Decision Function Operator Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Infimum of the Decision Function Operator Upper Bound cannot
	 * 	be calculated
	 */

	public double infimumUpperBound (
		final int iFeatureSpaceEntropyNumber)
		throws java.lang.Exception
	{
		double dblFactorizerNorm = _dsoFactorizer.norm();

		double dblFactorizerEntropyUpperBound = _dsoFactorizer.entropyNumberUpperBound();

		double dblFeatureSpaceMaureyBound = featureSpaceMaureyBound (iFeatureSpaceEntropyNumber);

		double dblProductFeatureOperatorNorm = _dblInverseMarginNormBound * dblFactorizerNorm;
		double dblFeatureMaureyOperatorNorm = dblProductFeatureOperatorNorm * dblFeatureSpaceMaureyBound;
		double dblFeatureNormOperatorEntropy = _dblInverseMarginNormBound * dblFactorizerEntropyUpperBound;
		double dblInfimumUpperBound = dblFeatureNormOperatorEntropy * dblFeatureSpaceMaureyBound;

		if (dblInfimumUpperBound > dblFeatureMaureyOperatorNorm)
			dblInfimumUpperBound = dblFeatureMaureyOperatorNorm;

		if (dblInfimumUpperBound > dblProductFeatureOperatorNorm)
			dblInfimumUpperBound = dblProductFeatureOperatorNorm;

		return dblInfimumUpperBound > dblFeatureNormOperatorEntropy ? dblInfimumUpperBound :
			dblFeatureNormOperatorEntropy;
	}
}
