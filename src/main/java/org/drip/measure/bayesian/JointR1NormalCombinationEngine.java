
package org.drip.measure.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <br><br>
 * 	<ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian">Bayesian</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
