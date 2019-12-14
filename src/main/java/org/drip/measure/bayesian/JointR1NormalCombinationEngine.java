
package org.drip.measure.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>JointR1NormalCombinationEngine</i> implements the Engine that generates the Combined/Posterior
 * Distribution from the Prior and the Conditional Joint R<sup>1</sup> Multivariate Normal Distributions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/README.md">Prior, Conditional, Posterior Theil Bayesian</a></li>
 *  </ul>
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

		double[] adblPrecisionWeightedPriorMean = org.drip.numerical.linearalgebra.Matrix.Product
			(aadblPriorPrecision, r1mnPrior.mean());

		if (null == adblPrecisionWeightedPriorMean) return null;

		double[] adblPrecisionWeightedConditionalMean = org.drip.numerical.linearalgebra.Matrix.Product
			(aadblConditionalPrecision, r1mnConditional.mean());

		if (null == adblPrecisionWeightedConditionalMean) return null;

		for (int i = 0; i < iNumVariate; ++i) {
			adblJointMean[i] = adblPrecisionWeightedPriorMean[i] + adblPrecisionWeightedConditionalMean[i];

			for (int j = 0; j < iNumVariate; ++j)
				aadblJointPrecision[i][j] = aadblPriorPrecision[i][j] + aadblConditionalPrecision[i][j];
		}

		double[][] aadblJointCovariance = org.drip.numerical.linearalgebra.Matrix.InvertUsingGaussianElimination
			(aadblJointPrecision);

		double[] adblJointPosteriorMean = org.drip.numerical.linearalgebra.Matrix.Product (aadblJointCovariance,
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
