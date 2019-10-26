
package org.drip.learning.svm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>DecisionFunctionOperatorBounds</i> implements the Dot Product Entropy Number Upper Bounds for the
 * Product of Kernel Feature Map Function and the Scaling Diagonal Operator.
 *  
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
 * 	</li>
 * 	<li>
 * 		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and Approximation of Operators</i>
 * 			<b>Cambridge University Press</b> Cambridge UK
 * 	</li>
 * 	<li>
 *  	Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 *  		Approximation Numbers of Operators <i>Journal of Approximation Theory</i> <b>49</b> 219-237
 * 	</li>
 * 	<li>
 *  	Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel,
 *  		Switzerland
 * 	</li>
 * 	<li>
 *  	Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  		Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B.
 *  		Scholkopf, and D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA
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
		if (null == (_dsoFactorizer = dsoFactorizer) || !org.drip.numerical.common.NumberUtil.IsValid
			(_dblInverseMarginNormBound = dblInverseMarginNormBound) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblFeatureSpaceMaureyConstant =
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
