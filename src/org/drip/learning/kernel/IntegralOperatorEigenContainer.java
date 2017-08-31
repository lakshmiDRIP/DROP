
package org.drip.learning.kernel;

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
 * IntegralOperatorEigenContainer holds the Group of Eigen-Components that result from the Eigenization of
 *  the R^x L2 To R^x L2 Kernel Linear Integral Operator defined by:
 * 
 * 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}
 *  
 *  The References are:
 *  
 *  1) Ash, R. (1965): Information Theory, Inter-science New York.
 *  
 *  2) Konig, H. (1986): Eigenvalue Distribution of Compact Operators, Birkhauser, Basel, Switzerland. 
 *  
 *  3) Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  	Combinations and mlps, in: Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf,
 *  	and D. Schuurmans - editors, MIT Press, Cambridge, MA.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntegralOperatorEigenContainer {
	private org.drip.learning.kernel.IntegralOperatorEigenComponent[] _aIOEC = null;

	/**
	 * IntegralOperatorEigenContainer Constructor
	 * 
	 * @param aIOEC Array of the Integral Operator Eigen-Components
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IntegralOperatorEigenContainer (
		final org.drip.learning.kernel.IntegralOperatorEigenComponent[] aIOEC)
		throws java.lang.Exception
	{
		if (null == (_aIOEC = aIOEC) || 0 == _aIOEC.length)
			throw new java.lang.Exception ("IntegralOperatorEigenContainer ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Integral Operator Eigen-Components
	 * 
	 * @return The Array of the Integral Operator Eigen-Components
	 */

	public org.drip.learning.kernel.IntegralOperatorEigenComponent[] eigenComponents()
	{
		return _aIOEC;
	}

	/**
	 * Retrieve the Eigen Input Space
	 * 
	 * @return The Eigen Input Space
	 */

	public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _aIOEC[0].eigenFunction().inputMetricVectorSpace();
	}

	/**
	 * Retrieve the Eigen Output Space
	 * 
	 * @return The Eigen Output Space
	 */

	public org.drip.spaces.metric.R1Normed outputMetricVectorSpace()
	{
		return _aIOEC[0].eigenFunction().outputMetricVectorSpace();
	}

	/**
	 * Generate the Diagonally Scaled Normed Vector Space of the RKHS Feature Space Bounds that results on
	 *  applying the Diagonal Scaling Operator
	 * 
	 * @param dso The Diagonal Scaling Operator
	 * 
	 * @return The Diagonally Scaled Normed Vector Space of the RKHS Feature Space
	 */

	public org.drip.spaces.metric.R1Combinatorial diagonallyScaledFeatureSpace (
		final org.drip.learning.kernel.DiagonalScalingOperator dso)
	{
		if (null == dso) return null;

		double[] adblDiagonalScalingOperator = dso.scaler();

		int iDimension = adblDiagonalScalingOperator.length;

		if (iDimension != _aIOEC.length) return null;

		java.util.List<java.lang.Double> lsElementSpace = new java.util.ArrayList<java.lang.Double>();

		for (int i = 0; i < iDimension; ++i)
			lsElementSpace.add (0.5 * _aIOEC[i].rkhsFeatureParallelepipedLength() /
				adblDiagonalScalingOperator[i]);

		try {
			return new org.drip.spaces.metric.R1Combinatorial (lsElementSpace, null, 2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Operator Class Covering Number Bounds of the RKHS Feature Space Bounds that result on the
	 *  Application of the Diagonal Scaling Operator
	 * 
	 * @param dso The Diagonal Scaling Operator
	 * 
	 * @return The Operator Class Covering Number Bounds of the RKHS Feature Space
	 */

	public org.drip.spaces.cover.OperatorClassCoveringBounds scaledCoveringNumberBounds (
		final org.drip.learning.kernel.DiagonalScalingOperator dso)
	{
		final org.drip.spaces.metric.R1Combinatorial r1ContinuousScaled = diagonallyScaledFeatureSpace (dso);

		if (null == r1ContinuousScaled) return null;

		try {
			final double dblPopulationMetricNorm = r1ContinuousScaled.populationMetricNorm();

			org.drip.spaces.cover.OperatorClassCoveringBounds occb = new
				org.drip.spaces.cover.OperatorClassCoveringBounds() {
				@Override public double entropyNumberLowerBound()
					throws java.lang.Exception
				{
					return dso.entropyNumberLowerBound() * dblPopulationMetricNorm;
				}

				@Override public double entropyNumberUpperBound()
					throws java.lang.Exception
				{
					return dso.entropyNumberUpperBound() * dblPopulationMetricNorm;
				}

				@Override public int entropyNumberIndex()
				{
					return dso.entropyNumberIndex();
				}

				@Override public double norm()
					throws java.lang.Exception
				{
					return dso.norm() * dblPopulationMetricNorm;
				}

				@Override public org.drip.learning.bound.DiagonalOperatorCoveringBound
					entropyNumberAsymptote()
				{
					return dso.entropyNumberAsymptote();
				}
			};

			return occb;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
