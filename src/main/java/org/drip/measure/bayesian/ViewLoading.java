
package org.drip.measure.bayesian;

import org.drip.measure.gaussian.JointVariance;
import org.drip.measure.gaussian.R1MultivariateNormal;
import org.drip.measure.state.LabelledRd;
import org.drip.measure.state.LabelledRdContinuousDistribution;
import org.drip.numerical.common.NumberUtil;
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
 * <i>ViewLoading</i> contains the View Projection Distribution and its Loadings to the Scoping Distribution.
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate the Projection Co-variance Matrix from the Confidence Level</li>
 * 		<li>Generate the <i>ViewLoading</i> Instance from the Confidence Level</li>
 * 		<li><i>ViewLoading</i> Constructor</li>
 * 		<li>Retrieve the Projection Distribution</li>
 * 		<li>Retrieve the Matrix of the Projection Scoping Loadings</li>
 * 		<li>Retrieve the Number of the Projection Variates</li>
 * 		<li>Retrieve the Number of the Scoping Variate</li>
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

public class ViewLoading
{
	private double[][] _projectionScopingLoadingMatrix = null;
	private LabelledRdContinuousDistribution _projectionDistribution = null;

	/**
	 * Generate the Projection Co-variance Matrix from the Confidence Level
	 * 
	 * @param scopingCovarianceMatrix The Scoping Co-variance Matrix
	 * @param scopingLoadingMatrix The Projection-Scoping Variate Loadings
	 * @param tau The Tau Parameter
	 * 
	 * @return The Projection Co-variance Matrix
	 */

	public static final double[][] ProjectionCovariance (
		final double[][] scopingCovarianceMatrix,
		final double[][] scopingLoadingMatrix,
		final double tau)
	{
		if (null == scopingCovarianceMatrix || null == scopingLoadingMatrix || !NumberUtil.IsValid (tau)) {
			return null;
		}

		double[][] projectionCovarianceMatrix = 0 == scopingLoadingMatrix.length ?
			null : new double[scopingLoadingMatrix.length][scopingLoadingMatrix.length];

		if (0 == scopingLoadingMatrix.length || scopingLoadingMatrix.length != scopingLoadingMatrix.length) {
			return null;
		}

		for (int componentIndexI = 0; componentIndexI < scopingLoadingMatrix.length; ++componentIndexI) {
			for (int componentIndexJ = 0; componentIndexJ < scopingLoadingMatrix.length; ++componentIndexJ) {
				try {
					projectionCovarianceMatrix[componentIndexI][componentIndexJ] =
						componentIndexI != componentIndexJ ? 0. : tau * R1MatrixUtil.DotProduct (
							scopingLoadingMatrix[componentIndexI],
							R1MatrixUtil.Product (
								scopingCovarianceMatrix,
								scopingLoadingMatrix[componentIndexJ]
							)
						);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return projectionCovarianceMatrix;
	}

	/**
	 * Generate the <i>ViewLoading</i> Instance from the Confidence Level
	 * 
	 * @param metaRd The R<sup>1</sup> Multivariate Meta Headers
	 * @param meanArray Array of the Univariate Means
	 * @param scopingCovarianceMatrix The Scoping Co-variance Matrix
	 * @param scopingLoadingMatrix The Projection-Scoping Variate Loadings
	 * @param tau The Tau Parameter
	 * 
	 * @return The <i>ViewLoading</i> Instance
	 */

	public static final ViewLoading FromConfidence (
		final LabelledRd metaRd,
		final double[] meanArray,
		final double[][] scopingCovarianceMatrix,
		final double[][] scopingLoadingMatrix,
		final double tau)
	{
		try {
			return new ViewLoading (
				new R1MultivariateNormal (
					metaRd,
					meanArray,
					new JointVariance (
						ProjectionCovariance (scopingCovarianceMatrix, scopingLoadingMatrix, tau)
					)
				),
				scopingLoadingMatrix
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>ViewLoading</i> Constructor
	 * 
	 * @param projectionDistribution The Projection Distribution Instance
	 * @param projectionScopingLoadingMatrix The Projection-Scoping Variate Loadings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ViewLoading (
		final LabelledRdContinuousDistribution projectionDistribution,
		final double[][] projectionScopingLoadingMatrix)
		throws Exception
	{
		if (null == (_projectionDistribution = projectionDistribution) ||
			null == (_projectionScopingLoadingMatrix = projectionScopingLoadingMatrix))
		{
			throw new Exception ("ViewLoading Constructor => Invalid Inputs!");
		}

		int projectionViewCount = _projectionDistribution.stateLabels().count();

		if (projectionViewCount != _projectionScopingLoadingMatrix.length) {
			throw new Exception ("ViewLoading Constructor => Invalid Inputs!");
		}

		for (int projectionViewIndex = 0; projectionViewIndex < projectionViewCount; ++projectionViewIndex) {
			if (null == _projectionScopingLoadingMatrix[projectionViewIndex] ||
				0 == _projectionScopingLoadingMatrix[projectionViewIndex].length ||
				!NumberUtil.IsValid (_projectionScopingLoadingMatrix[projectionViewIndex]))
			{
				throw new Exception ("ViewLoading Constructor => Invalid Inputs!");
			}
		}
	}

	/**
	 * Retrieve the Projection Distribution
	 * 
	 * @return The Projection Distribution
	 */

	public LabelledRdContinuousDistribution projectionDistribution()
	{
		return _projectionDistribution;
	}

	/**
	 * Retrieve the Matrix of the Projection Scoping Loadings
	 * 
	 * @return The Matrix of the Projection Scoping Loadings
	 */

	public double[][] projectionScopingLoadingMatrix()
	{
		return _projectionScopingLoadingMatrix;
	}

	/**
	 * Retrieve the Number of the Projection Variates
	 * 
	 * @return The Number of the Projection Variates
	 */

	public int projectionVariateCount()
	{
		return _projectionScopingLoadingMatrix.length;
	}

	/**
	 * Retrieve the Number of the Scoping Variate
	 * 
	 * @return The Number of the Scoping Variate
	 */

	public int scopingVariateCount()
	{
		return _projectionScopingLoadingMatrix[0].length;
	}
}
