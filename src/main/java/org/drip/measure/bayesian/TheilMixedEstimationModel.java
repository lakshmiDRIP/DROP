
package org.drip.measure.bayesian;

import org.drip.measure.gaussian.JointVariance;
import org.drip.measure.gaussian.R1MultivariateNormal;
import org.drip.measure.state.LabelledRd;
import org.drip.measure.state.LabelledRdContinuousDistribution;
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
 * <i>TheilMixedEstimationModel</i> implements the Theil's Mixed Model for the Estimation of the Distribution
 * 	Parameters. The Reference is:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Theil, H. (1971): <i>Principles of Econometrics</i> <b>Wiley</b>
 * 		</li>
 * 	</ul>
 *
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate the Joint Mixed Estimation Model Joint/Posterior Metrics
 * 		<li>Generate the Combined R<sup>1</sup> Multivariate Normal Distribution from the Scoping Container and the Named Views</li>
 * 		<li>Generate the Combined R<sup>1</sup> Multivariate Normal Distribution from the Scoping Container, the NATIVE Projection, and the Named View</li>
 * 		<li>Generate the Projection Space Scoping Mean</li>
 * 		<li>Generate the Projection Space Projection-Scoping Mean Differential</li>
 * 		<li>Generate the Projection Space Scoping Co-variance</li>
 * 		<li>Compute the Shadow of the Scoping on Projection Transpose</li>
 * 		<li>Compute the Shadow of the Scoping on Projection</li>
 * 		<li>Compute the Projection Precision Mean Dot Product Array</li>
 * 		<li>Compute the Projection Induced Scoping Mean Deviation</li>
 * 		<li>Compute the Projection Induced Scoping Deviation Adjusted Mean</li>
 * 		<li>Compute the Asset Space Projection Co-variance</li>
 * 		<li>Compute the Projection Space Asset Co-variance</li>
 * 		<li>Compute the Projection Induced Scoping Deviation Adjusted Mean</li>
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

public class TheilMixedEstimationModel
{

	/**
	 * Generate the Joint Mixed Estimation Model Joint/Posterior Metrics
	 * 
	 * @param multivariateMeta The R<sup>1</sup> Multivariate Meta Descriptors
	 * @param viewLoading1 View Loading #1
	 * @param viewLoading2 View Loading #2
	 * @param unconditionalDistribution The R<sup>1</sup> Multivariate Normal Unconditional Distribution
	 * 
	 * @return The Joint Mixed Estimation Model Joint/Posterior Metrics
	 */

	public static final R1MultivariateConvolutionMetrics GenerateComposite (
		final LabelledRd multivariateMeta,
		final ViewLoading viewLoading1,
		final ViewLoading viewLoading2,
		final R1MultivariateNormal unconditionalDistribution)
	{
		if (null == multivariateMeta ||
			null == viewLoading1 ||
			null == viewLoading2 ||
			null == unconditionalDistribution)
		{
			return null;
		}

		int scopingVariateCount = multivariateMeta.count();

		if (scopingVariateCount != viewLoading1.scopingVariateCount() ||
			scopingVariateCount != viewLoading2.scopingVariateCount() ||
			scopingVariateCount != unconditionalDistribution.stateLabels().count())
		{
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution1 = viewLoading1.projectionDistribution();

		LabelledRdContinuousDistribution projectionDistribution2 = viewLoading2.projectionDistribution();

		if (!(projectionDistribution1 instanceof R1MultivariateNormal) ||
			!(projectionDistribution2 instanceof R1MultivariateNormal))
		{
			return null;
		}

		double[] jointPrecisionWeightedMeanArray = new double[scopingVariateCount];
		double[][] jointPrecisionMatrix = new double[scopingVariateCount][scopingVariateCount];
		double[][] posteriorCovarianceMatrix = new double[scopingVariateCount][scopingVariateCount];
		R1MultivariateNormal multivariateNormalProjectionDistribution1 =
			(R1MultivariateNormal) projectionDistribution1;
		R1MultivariateNormal multivariateNormalProjectionDistribution2 =
			(R1MultivariateNormal) projectionDistribution2;

		double[][] scopingLoadingMatrix1 = viewLoading1.projectionScopingLoadingMatrix();

		double[][] scopingLoadingMatrix2 = viewLoading2.projectionScopingLoadingMatrix();

		double[][] scopingWeightedPrecisionMatrix1 = R1MatrixUtil.Product (
			R1MatrixUtil.Transpose (scopingLoadingMatrix1),
			multivariateNormalProjectionDistribution1.covariance().precisionMatrix()
		);

		double[][] scopingWeightedPrecisionMatrix2 = R1MatrixUtil.Product (
			R1MatrixUtil.Transpose (scopingLoadingMatrix2),
			multivariateNormalProjectionDistribution2.covariance().precisionMatrix()
		);

		double[][] scopingSpacePrecisionMatrix1 = R1MatrixUtil.Product (
			scopingWeightedPrecisionMatrix1,
			scopingLoadingMatrix1
		);

		double[][] scopingSpacePrecisionMatrix2 = R1MatrixUtil.Product (
			scopingWeightedPrecisionMatrix2,
			scopingLoadingMatrix2
		);

		if (null == scopingSpacePrecisionMatrix1 || null == scopingSpacePrecisionMatrix2) {
			return null;
		}

		double[] precisionWeightedMeanArray1 = R1MatrixUtil.Product (
			scopingWeightedPrecisionMatrix1,
			multivariateNormalProjectionDistribution1.mean()
		);

		double[] precisionWeightedMeanArray2 = R1MatrixUtil.Product (
			scopingWeightedPrecisionMatrix2,
			multivariateNormalProjectionDistribution2.mean()
		);

		if (null == precisionWeightedMeanArray1 || null == precisionWeightedMeanArray2) {
			return null;
		}

		for (int scopingVariateIndexI = 0;
			scopingVariateIndexI < scopingVariateCount;
			++scopingVariateIndexI)
		{
			jointPrecisionWeightedMeanArray[scopingVariateIndexI] =
				precisionWeightedMeanArray1[scopingVariateIndexI] +
				precisionWeightedMeanArray2[scopingVariateIndexI];

			for (int scopingVariateIndexJ = 0;
				scopingVariateIndexJ < scopingVariateCount;
				++scopingVariateIndexJ)
			{
				jointPrecisionMatrix[scopingVariateIndexI][scopingVariateIndexJ] =
					scopingSpacePrecisionMatrix1[scopingVariateIndexI][scopingVariateIndexJ] +
					scopingSpacePrecisionMatrix2[scopingVariateIndexI][scopingVariateIndexJ];
			}
		}

		double[][] jointCovarianceMatrix =
			R1MatrixUtil.InvertUsingGaussianElimination (jointPrecisionMatrix);

		double[] jointPosteriorMeanArray = R1MatrixUtil.Product (
			jointCovarianceMatrix,
			jointPrecisionWeightedMeanArray
		);

		double[][] unconditionalCovarianceMatrix = unconditionalDistribution.covariance().covarianceMatrix();

		for (int scopingVariateIndexI = 0;
			scopingVariateIndexI < scopingVariateCount;
			++scopingVariateIndexI)
		{
			for (int scopingVariateIndexJ = 0;
				scopingVariateIndexJ < scopingVariateCount;
				++scopingVariateIndexJ)
			{
				posteriorCovarianceMatrix[scopingVariateIndexI][scopingVariateIndexJ] =
					jointCovarianceMatrix[scopingVariateIndexI][scopingVariateIndexJ] +
					unconditionalCovarianceMatrix[scopingVariateIndexI][scopingVariateIndexJ];
			}
		}

		try {
			return new R1MultivariateConvolutionMetrics (
				multivariateNormalProjectionDistribution1,
				unconditionalDistribution,
				multivariateNormalProjectionDistribution2,
				new R1MultivariateNormal (
					multivariateMeta,
					jointPosteriorMeanArray,
					new JointVariance (jointCovarianceMatrix)
				),
				new R1MultivariateNormal (
					multivariateMeta,
					jointPosteriorMeanArray,
					new JointVariance (posteriorCovarianceMatrix)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Combined R<sup>1</sup> Multivariate Normal Distribution from the Scoping Container and
	 * 	the Named Views
	 * 
	 * @param scopingContainer The Scoping Container
	 * @param viewName1 Name of Projection #1
	 * @param viewName2 Name of Projection #2
	 * @param unconditionalDistribution The R<sup>1</sup> Multivariate Normal Unconditional Distribution
	 * 
	 * @return The Combined R<sup>1</sup> Multivariate Normal Distribution
	 */

	public static final R1MultivariateConvolutionMetrics GenerateComposite (
		final ScopingContainer scopingContainer,
		final String viewName1,
		final String viewName2,
		final R1MultivariateNormal unconditionalDistribution)
	{
		return null == scopingContainer ? null : GenerateComposite (
			scopingContainer.projectionDistribution().stateLabels(),
			scopingContainer.viewLoading (viewName1),
			scopingContainer.viewLoading (viewName2),
			unconditionalDistribution
		);
	}

	/**
	 * Generate the Combined R<sup>1</sup> Multivariate Normal Distribution from the Scoping Container, the
	 * 	NATIVE Projection, and the Named View
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of View
	 * @param unconditionalDistribution The R<sup>1</sup> Multivariate Normal Unconditional Distribution
	 * 
	 * @return The Combined R<sup>1</sup> Multivariate Normal Distribution
	 */

	public static final R1MultivariateConvolutionMetrics GenerateComposite (
		final ScopingContainer scopingContainer,
		final String viewName,
		final R1MultivariateNormal unconditionalDistribution)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		return GenerateComposite (
			((R1MultivariateNormal) projectionDistribution).stateLabels(),
			scopingContainer.viewLoading ("NATIVE"),
			scopingContainer.viewLoading (viewName),
			unconditionalDistribution
		);
	}

	/**
	 * Generate the Projection Space Scoping Mean
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Projection Space Scoping Mean
	 */

	public static final double[] ProjectionSpaceScopingMean (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		return null == viewLoading ? null : R1MatrixUtil.Product (
			viewLoading.projectionScopingLoadingMatrix(),
			scopingContainer.projectionDistribution().mean()
		);
	}

	/**
	 * Generate the Projection Space Projection-Scoping Mean Differential
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Projection Space Projection-Scoping Mean Differential
	 */

	public static final double[] ProjectionSpaceScopingDifferential (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		double[] projectionSpaceScopingMeanArray = R1MatrixUtil.Product (
			viewLoading.projectionScopingLoadingMatrix(),
			scopingContainer.projectionDistribution().mean()
		);

		if (null == projectionSpaceScopingMeanArray) {
			return null;
		}

		double[] projectionSpaceScopingDifferentialArray =
			new double[projectionSpaceScopingMeanArray.length];

		double[] projectionMeanArray = viewLoading.projectionDistribution().mean();

		for (int projectionSpaceIndex = 0;
			projectionSpaceIndex < projectionSpaceScopingMeanArray.length;
			++projectionSpaceIndex)
		{
			projectionSpaceScopingDifferentialArray[projectionSpaceIndex] =
				projectionMeanArray[projectionSpaceIndex] -
				projectionSpaceScopingMeanArray[projectionSpaceIndex];
		}

		return projectionSpaceScopingDifferentialArray;
	}

	/**
	 * Generate the Projection Space Scoping Co-variance
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Projection Space Scoping Co-variance
	 */

	public static final JointVariance ProjectionSpaceScopingCovariance (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		R1MultivariateNormal multivariateNormalProjectionDistribution =
			(R1MultivariateNormal) projectionDistribution;

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		double[][] scopingLoadingMatrix = viewLoading.projectionScopingLoadingMatrix();

		try {
			return new JointVariance (
				R1MatrixUtil.Product (
					scopingLoadingMatrix,
					R1MatrixUtil.Product (
						multivariateNormalProjectionDistribution.covariance().covarianceMatrix(),
						R1MatrixUtil.Transpose (scopingLoadingMatrix)
					)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Shadow of the Scoping on Projection Transpose
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of View
	 * 
	 * @return The Shadow of the Scoping on Projection Transpose
	 */

	public static final double[][] ShadowScopingProjectionTranspose (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		return null == viewLoading ? null : R1MatrixUtil.Product (
			((R1MultivariateNormal) projectionDistribution).covariance().covarianceMatrix(),
			R1MatrixUtil.Transpose (viewLoading.projectionScopingLoadingMatrix())
		);
	}

	/**
	 * Compute the Shadow of the Scoping on Projection
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Shadow of the Scoping on Projection
	 */

	public static final double[][] ShadowScopingProjection (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		return !(projectionDistribution instanceof R1MultivariateNormal) || null == viewLoading ? null :
			R1MatrixUtil.Product (
				viewLoading.projectionScopingLoadingMatrix(),
				((R1MultivariateNormal) projectionDistribution).covariance().covarianceMatrix()
			);
	}

	/**
	 * Compute the Projection Precision Mean Dot Product Array
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Projection Precision Mean Dot Product Array
	 */

	public static final double[] ProjectionPrecisionMeanProduct (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = viewLoading.projectionDistribution();

		return !(projectionDistribution instanceof R1MultivariateNormal) ? null : R1MatrixUtil.Product (
			((R1MultivariateNormal) projectionDistribution).covariance().precisionMatrix(),
			projectionDistribution.mean()
		);
	}

	/**
	 * Compute the Projection Induced Scoping Mean Deviation
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Projection Induced Scoping Mean Deviation
	 */

	public static final double[] ProjectionInducedScopingDeviation (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		R1MultivariateNormal multivariateNormalProjectionDistribution =
			(R1MultivariateNormal) projectionDistribution;

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		double[][] scopingLoadingMatrix = viewLoading.projectionScopingLoadingMatrix();

		double[][] projectionScopingShadowMatrix = R1MatrixUtil.Product (
			multivariateNormalProjectionDistribution.covariance().covarianceMatrix(),
			R1MatrixUtil.Transpose (scopingLoadingMatrix)
		);

		double[] projectionSpaceScopingMeanArray = R1MatrixUtil.Product (
			scopingLoadingMatrix,
			projectionDistribution.mean()
		);

		if (null == projectionSpaceScopingMeanArray) {
			return null;
		}

		double[] projectionSpaceScopingDifferentialArray =
			new double[projectionSpaceScopingMeanArray.length];

		double[] projectionMeanArray = viewLoading.projectionDistribution().mean();

		for (int projectionSpaceIndex = 0;
			projectionSpaceIndex < projectionSpaceScopingMeanArray.length;
			++projectionSpaceIndex)
		{
			projectionSpaceScopingDifferentialArray[projectionSpaceIndex] =
				projectionMeanArray[projectionSpaceIndex] -
				projectionSpaceScopingMeanArray[projectionSpaceIndex];
		}

		return R1MatrixUtil.Product (
			projectionScopingShadowMatrix,
			R1MatrixUtil.Product (
				R1MatrixUtil.InvertUsingGaussianElimination (
					R1MatrixUtil.Product (scopingLoadingMatrix, projectionScopingShadowMatrix)
				),
			projectionSpaceScopingDifferentialArray)
		);
	}

	/**
	 * Compute the Projection Induced Scoping Deviation Adjusted Mean
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of the View
	 * 
	 * @return The Projection Induced Scoping Deviation Adjusted Mean
	 */

	public static final double[] ProjectionInducedScopingMean (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		double[] scopingMeanArray = projectionDistribution.mean();

		int scopingVariateCount = projectionDistribution.stateLabels().count();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		R1MultivariateNormal multivariateNormalProjectionDistribution =
			(R1MultivariateNormal) projectionDistribution;

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		double[][] scopingLoadingMatrix = viewLoading.projectionScopingLoadingMatrix();

		double[][] projectionScopingShadowMatrix = R1MatrixUtil.Product (
			multivariateNormalProjectionDistribution.covariance().covarianceMatrix(),
			R1MatrixUtil.Transpose (scopingLoadingMatrix)
		);

		double[] projectionSpaceScopingMeanArray = R1MatrixUtil.Product (
			scopingLoadingMatrix,
			scopingMeanArray
		);

		if (null == projectionSpaceScopingMeanArray) {
			return null;
		}

		double[] projectionInducedScopingMeanArray = new double[scopingVariateCount];
		double[] projectionSpaceScopingDifferentialArray =
			new double[projectionSpaceScopingMeanArray.length];

		double[] projectionMeanArray = viewLoading.projectionDistribution().mean();

		for (int projectionSpaceIndex = 0;
			projectionSpaceIndex < projectionSpaceScopingMeanArray.length;
			++projectionSpaceIndex)
		{
			projectionSpaceScopingDifferentialArray[projectionSpaceIndex] =
				projectionMeanArray[projectionSpaceIndex] -
				projectionSpaceScopingMeanArray[projectionSpaceIndex];
		}

		double[] projectionInducedScopingDeviationArray = R1MatrixUtil.Product (
			projectionScopingShadowMatrix,
			R1MatrixUtil.Product (
				R1MatrixUtil.InvertUsingGaussianElimination (
					R1MatrixUtil.Product (scopingLoadingMatrix, projectionScopingShadowMatrix)
				),
				projectionSpaceScopingDifferentialArray
			)
		);

		if (null == projectionInducedScopingDeviationArray) {
			return null;
		}

		for (int scopingVariateIndex = 0; scopingVariateIndex < scopingVariateCount; ++scopingVariateIndex) {
			projectionInducedScopingMeanArray[scopingVariateIndex] =
				scopingMeanArray[scopingVariateIndex] +
				projectionInducedScopingDeviationArray[scopingVariateIndex];
		}

		return projectionInducedScopingMeanArray;
	}

	/**
	 * Compute the Asset Space Projection Co-variance
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of then View
	 * 
	 * @return The Asset Space Projection Co-variance
	 */

	public static final double[][] AssetSpaceProjectionCovariance (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = viewLoading.projectionDistribution();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		double[][] scopingLoadingMatrix = viewLoading.projectionScopingLoadingMatrix();

		return R1MatrixUtil.Product (
			R1MatrixUtil.Transpose (scopingLoadingMatrix),
			R1MatrixUtil.Product (
				((R1MultivariateNormal) projectionDistribution).covariance().covarianceMatrix(),
				scopingLoadingMatrix
			)
		);
	}

	/**
	 * Compute the Projection Space Asset Co-variance
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of then View
	 * 
	 * @return The Projection Space Asset Co-variance
	 */

	public static final double[][] ProjectionSpaceAssetCovariance (
		final ScopingContainer scopingContainer,
		final String viewName)
	{
		if (null == scopingContainer) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		double[][] scopingLoadingMatrix = viewLoading.projectionScopingLoadingMatrix();

		return R1MatrixUtil.Product (
			scopingLoadingMatrix,
			R1MatrixUtil.Product (
				((R1MultivariateNormal) projectionDistribution).covariance().covarianceMatrix(),
				R1MatrixUtil.Transpose (scopingLoadingMatrix)
			)
		);
	}

	/**
	 * Compute the Projection Induced Scoping Deviation Adjusted Mean
	 * 
	 * @param scopingContainer The Scoping/Projection Distribution
	 * @param viewName Name of then View
	 * @param unconditionalDistribution The Unconditional Distribution
	 * 
	 * @return The Projection Induced Scoping Deviation Adjusted Mean
	 */

	public static final R1MultivariateNormal ProjectionInducedScopingDistribution (
		final ScopingContainer scopingContainer,
		final String viewName,
		final R1MultivariateNormal unconditionalDistribution)
	{
		if (null == scopingContainer || null == unconditionalDistribution) {
			return null;
		}

		LabelledRdContinuousDistribution projectionDistribution = scopingContainer.projectionDistribution();

		int iNumScopingVariate = projectionDistribution.stateLabels().count();

		double[] scopingMeanArray = projectionDistribution.mean();

		if (!(projectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		ViewLoading viewLoading = scopingContainer.viewLoading (viewName);

		if (null == viewLoading) {
			return null;
		}

		double[][] scopingLoadingMatrix = viewLoading.projectionScopingLoadingMatrix();

		double[][] projectionScopingShadowMatrix = R1MatrixUtil.Product (
			((R1MultivariateNormal) projectionDistribution).covariance().covarianceMatrix(),
			R1MatrixUtil.Transpose (scopingLoadingMatrix)
		);

		double[] projectionSpaceScopingMeanArray = R1MatrixUtil.Product (
			scopingLoadingMatrix,
			scopingMeanArray
		);

		if (null == projectionSpaceScopingMeanArray) {
			return null;
		}

		double[] projectionInducedScopingMeanArray = new double[iNumScopingVariate];
		double[] projectionSpaceScopingDifferentialArray =
			new double[projectionSpaceScopingMeanArray.length];

		double[] projectionMeanArray = viewLoading.projectionDistribution().mean();

		for (int projectionSpaceIndex = 0;
			projectionSpaceIndex < projectionSpaceScopingMeanArray.length;
			++projectionSpaceIndex)
		{
			projectionSpaceScopingDifferentialArray[projectionSpaceIndex] =
				projectionMeanArray[projectionSpaceIndex] -
				projectionSpaceScopingMeanArray[projectionSpaceIndex];
		}

		double[] projectionInducedScopingDeviationArray = R1MatrixUtil.Product (
			projectionScopingShadowMatrix,
			R1MatrixUtil.Product (
				R1MatrixUtil.InvertUsingGaussianElimination (
					R1MatrixUtil.Product (scopingLoadingMatrix, projectionScopingShadowMatrix)
				),
				projectionSpaceScopingDifferentialArray
			)
		);

		if (null == projectionInducedScopingDeviationArray) {
			return null;
		}

		for (int scopingVariateIndex = 0; scopingVariateIndex < iNumScopingVariate; ++scopingVariateIndex) {
			projectionInducedScopingMeanArray[scopingVariateIndex] =
				scopingMeanArray[scopingVariateIndex] +
				projectionInducedScopingDeviationArray[scopingVariateIndex];
		}

		LabelledRdContinuousDistribution viewProjectionDistribution = viewLoading.projectionDistribution();

		if (!(viewProjectionDistribution instanceof R1MultivariateNormal)) {
			return null;
		}

		try {
			return new R1MultivariateNormal (
				unconditionalDistribution.stateLabels(),
				projectionInducedScopingMeanArray,
				new JointVariance (
					R1MatrixUtil.Product (
						R1MatrixUtil.Transpose (scopingLoadingMatrix),
						R1MatrixUtil.Product (
							(
								(R1MultivariateNormal) viewProjectionDistribution
							).covariance().covarianceMatrix(),
							scopingLoadingMatrix
						)
					)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
