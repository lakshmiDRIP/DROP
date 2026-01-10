
package org.drip.measure.bayesian;

import org.drip.measure.distribution.MetaRd;
import org.drip.measure.distribution.MetaRdContinuous;
import org.drip.measure.gaussian.JointVariance;
import org.drip.measure.gaussian.R1MultivariateNormal;
import org.drip.numerical.linearalgebra.R1MatrixUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>R1MultivariateNormalConvolutionEngine</i> implements the Engine that generates the Joint/Posterior
 * 	Distribution from the Prior and the Conditional Joint R<sup>1</sup> Multivariate Normal Distributions. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li>Empty <i>R1MultivariateNormalConvolutionEngine</i> Constructor</li>
 * 		<li>Generate the Joint R<sup>1</sup> Multivariate Combined Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/README.md">Prior, Conditional, Posterior Theil Bayesian</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1MultivariateNormalConvolutionEngine
	implements R1MultivariateConvolutionEngine
{

	/**
	 * Empty <i>R1MultivariateNormalConvolutionEngine</i> Constructor
	 */

	public R1MultivariateNormalConvolutionEngine()
	{
	}

	/**
	 * Generate the Joint R<sup>1</sup> Multivariate Combined Distribution
	 * 
	 * @param priorDistribution The Prior Distribution
	 * @param unconditionalDistribution The Unconditional Distribution
	 * @param conditionalDistribution The Conditional Distribution
	 * 
	 * @return The Joint R<sup>1</sup> Multivariate Combined Distribution
	 */

	@Override public R1MultivariateConvolutionMetrics process (
		final MetaRdContinuous priorDistribution,
		final MetaRdContinuous unconditionalDistribution,
		final MetaRdContinuous conditionalDistribution)
	{
		if (null == priorDistribution || !(priorDistribution instanceof R1MultivariateNormal) ||
			null == conditionalDistribution || !(conditionalDistribution instanceof R1MultivariateNormal) ||
			null == unconditionalDistribution ||
				!(unconditionalDistribution instanceof R1MultivariateNormal))
		{
			return null;
		}

		R1MultivariateNormal multivariatePriorDistribution = (R1MultivariateNormal) priorDistribution;
		R1MultivariateNormal multivariateConditionalDistribution =
			(R1MultivariateNormal) conditionalDistribution;
		R1MultivariateNormal multivariateUnconditionalDistribution =
			(R1MultivariateNormal) unconditionalDistribution;

		double[][] priorPrecisionMatrix = multivariatePriorDistribution.covariance().precisionMatrix();

		double[][] conditionalPrecisionMatrix =
			multivariateConditionalDistribution.covariance().precisionMatrix();

		double[] jointMeanArray = new double[conditionalPrecisionMatrix.length];
		double[][] jointPrecisionMatrix =
			new double[conditionalPrecisionMatrix.length][conditionalPrecisionMatrix.length];
		double[][] posteriorCovarianceMatrix =
			new double[conditionalPrecisionMatrix.length][conditionalPrecisionMatrix.length];

		if (priorPrecisionMatrix.length != conditionalPrecisionMatrix.length) {
			return null;
		}

		double[] precisionWeightedPriorMeanArray = R1MatrixUtil.Product (
			priorPrecisionMatrix,
			multivariatePriorDistribution.mean()
		);

		if (null == precisionWeightedPriorMeanArray) {
			return null;
		}

		double[] precisionWeightedConditionalMeanArray = R1MatrixUtil.Product (
			conditionalPrecisionMatrix,
			multivariateConditionalDistribution.mean()
		);

		if (null == precisionWeightedConditionalMeanArray) {
			return null;
		}

		for (int variateIndexI = 0; variateIndexI < conditionalPrecisionMatrix.length; ++variateIndexI) {
			jointMeanArray[variateIndexI] =
				precisionWeightedPriorMeanArray[variateIndexI] +
				precisionWeightedConditionalMeanArray[variateIndexI];

			for (int variateIndexJ = 0; variateIndexJ < conditionalPrecisionMatrix.length; ++variateIndexJ) {
				jointPrecisionMatrix[variateIndexI][variateIndexJ] =
					priorPrecisionMatrix[variateIndexI][variateIndexJ] +
					conditionalPrecisionMatrix[variateIndexI][variateIndexJ];
			}
		}

		double[][] jointCovarianceMatrix =
			R1MatrixUtil.InvertUsingGaussianElimination (jointPrecisionMatrix);

		double[] jointPosteriorMeanArray = R1MatrixUtil.Product (jointCovarianceMatrix, jointMeanArray);

		double[][] unconditionalCovarianceMatrix =
			multivariateUnconditionalDistribution.covariance().covarianceMatrix();

		MetaRd meta = multivariatePriorDistribution.meta();

		for (int variateIndexI = 0; variateIndexI < conditionalPrecisionMatrix.length; ++variateIndexI) {
			for (int variateIndexJ = 0; variateIndexJ < conditionalPrecisionMatrix.length; ++variateIndexJ) {
				posteriorCovarianceMatrix[variateIndexI][variateIndexJ] =
					jointCovarianceMatrix[variateIndexI][variateIndexJ] +
					unconditionalCovarianceMatrix[variateIndexI][variateIndexJ];
			}
		}

		try {
			return new R1MultivariateConvolutionMetrics (
				priorDistribution,
				unconditionalDistribution,
				conditionalDistribution,
				new R1MultivariateNormal (
					meta,
					jointPosteriorMeanArray,
					new JointVariance (jointCovarianceMatrix)
				),
				new R1MultivariateNormal (
					meta,
					jointPosteriorMeanArray,
					new JointVariance (posteriorCovarianceMatrix)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
