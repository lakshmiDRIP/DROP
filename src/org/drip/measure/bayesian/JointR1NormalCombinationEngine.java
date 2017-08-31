
package org.drip.measure.bayesian;

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
 * JointR1NormalCombinationEngine implements the Engine that generates the Combined/Posterior Distribution
 *  from the Prior and the Conditional Joint R^1 Multivariate Normal Distributions.
 *
 * @author Lakshmi Krishnamurthy
 */

public class JointR1NormalCombinationEngine implements org.drip.measure.bayesian.JointR1CombinationEngine {

	/**
	 * Empty JointR1NormalConvolutionEngine Construction
	 */

	public JointR1NormalCombinationEngine()
	{
	}

	@Override public org.drip.measure.bayesian.JointPosteriorMetrics process (
		final org.drip.measure.continuous.R1Multivariate r1mPrior,
		final org.drip.measure.continuous.R1Multivariate r1mUnconditional,
		final org.drip.measure.continuous.R1Multivariate r1mConditional)
	{
		if (null == r1mPrior || !(r1mPrior instanceof org.drip.measure.gaussian.R1MultivariateNormal) || null
			== r1mConditional || !(r1mConditional instanceof org.drip.measure.gaussian.R1MultivariateNormal)
				|| null == r1mUnconditional || !(r1mUnconditional instanceof
					org.drip.measure.gaussian.R1MultivariateNormal))
			return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnPrior =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mPrior;
		org.drip.measure.gaussian.R1MultivariateNormal r1mnConditional =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mConditional;
		org.drip.measure.gaussian.R1MultivariateNormal r1mnUnconditional =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mUnconditional;

		double[][] aadblPriorPrecision = r1mnPrior.covariance().precisionMatrix();

		double[][] aadblConditionalPrecision = r1mnConditional.covariance().precisionMatrix();

		int iNumVariate = aadblConditionalPrecision.length;
		double[] adblJointMean = new double[iNumVariate];
		double[][] aadblJointPrecision = new double[iNumVariate][iNumVariate];
		double[][] aadblPosteriorCovariance = new double[iNumVariate][iNumVariate];

		if (aadblPriorPrecision.length != iNumVariate) return null;

		double[] adblPrecisionWeightedPriorMean = org.drip.quant.linearalgebra.Matrix.Product
			(aadblPriorPrecision, r1mnPrior.mean());

		if (null == adblPrecisionWeightedPriorMean) return null;

		double[] adblPrecisionWeightedConditionalMean = org.drip.quant.linearalgebra.Matrix.Product
			(aadblConditionalPrecision, r1mnConditional.mean());

		if (null == adblPrecisionWeightedConditionalMean) return null;

		for (int i = 0; i < iNumVariate; ++i) {
			adblJointMean[i] = adblPrecisionWeightedPriorMean[i] + adblPrecisionWeightedConditionalMean[i];

			for (int j = 0; j < iNumVariate; ++j)
				aadblJointPrecision[i][j] = aadblPriorPrecision[i][j] + aadblConditionalPrecision[i][j];
		}

		double[][] aadblJointCovariance = org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination
			(aadblJointPrecision);

		double[] adblJointPosteriorMean = org.drip.quant.linearalgebra.Matrix.Product (aadblJointCovariance,
			adblJointMean);

		double[][] aadblUnconditionalCovariance = r1mnUnconditional.covariance().covarianceMatrix();

		org.drip.measure.continuous.MultivariateMeta meta = r1mnPrior.meta();

		for (int i = 0; i < iNumVariate; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				aadblPosteriorCovariance[i][j] = aadblJointCovariance[i][j] +
					aadblUnconditionalCovariance[i][j];
		}

		try {
			return new org.drip.measure.bayesian.JointPosteriorMetrics (r1mPrior, r1mUnconditional,
				r1mConditional, new org.drip.measure.gaussian.R1MultivariateNormal (meta,
					adblJointPosteriorMean, new org.drip.measure.gaussian.Covariance (aadblJointCovariance)),
						new org.drip.measure.gaussian.R1MultivariateNormal (meta, adblJointPosteriorMean, new
							org.drip.measure.gaussian.Covariance (aadblPosteriorCovariance)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
