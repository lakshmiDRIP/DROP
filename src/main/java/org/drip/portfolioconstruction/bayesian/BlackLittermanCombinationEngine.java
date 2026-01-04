
package org.drip.portfolioconstruction.bayesian;

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
 * <i>BlackLittermanCombinationEngine</i> implements the Engine that generates the Combined/Posterior
 * Distributions from the Prior and the Conditional Joint R<sup>1</sup> Multivariate Normal Distributions.
 * The References are:
 *  
 *  <br><br>
 *  	<ul>
 *  		<li>
 *  			He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model
 *  				Portfolios</i> <b>Goldman Sachs Asset Management</b>
 *  		</li>
 *  		<li>
 *  			Idzorek, T. (2005): <i>A Step-by-Step Guide to the Black-Litterman Model: Incorporating
 *  				User-Specified Confidence Levels</i> <b>Ibbotson Associates</b> Chicago, IL
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian/README.md">Black Litterman Bayesian Portfolio Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanCombinationEngine
{
	private org.drip.portfolioconstruction.bayesian.ProjectionSpecification _projectionSpecification = null;
	private org.drip.portfolioconstruction.bayesian.PriorControlSpecification _priorControlSpecification =
		null;
	private org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
		_forwardReverseOptimizationOutputUnadjusted = null;

	private org.drip.measure.bayesian.ScopingContainer
		scopingProjectionVariateDistribution()
	{
		double[][] assetExcessReturnsCovarianceMatrix =
			_forwardReverseOptimizationOutputUnadjusted.assetExcessReturnsCovarianceMatrix();

		double[] priorExpectedAssetReturnsArray =
			_forwardReverseOptimizationOutputUnadjusted.expectedAssetExcessReturnsArray();

		int assetCount = assetExcessReturnsCovarianceMatrix.length;
		double[][] priorCovarianceMatrix = new double[assetCount][assetCount];

		double riskFreeRate = _priorControlSpecification.riskFreeRate();

		double tau = _priorControlSpecification.tau();

		for (int assetIndexI = 0; assetIndexI < assetCount; ++assetIndexI)
		{
			priorExpectedAssetReturnsArray[assetIndexI] = priorExpectedAssetReturnsArray[assetIndexI] +
				riskFreeRate;

			for (int assetIndexJ = 0; assetIndexJ < assetCount; ++assetIndexJ)
			{
				priorCovarianceMatrix[assetIndexI][assetIndexJ] =
					assetExcessReturnsCovarianceMatrix[assetIndexI][assetIndexJ] * tau;
			}
		}

		try
		{
			org.drip.measure.bayesian.ScopingContainer
				scopingProjectionVariateDistribution =
					new org.drip.measure.bayesian.ScopingContainer (
						org.drip.measure.gaussian.R1MultivariateNormal.Standard (
							_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio().meta(),
							priorExpectedAssetReturnsArray,
							priorCovarianceMatrix
						)
					);

			return scopingProjectionVariateDistribution.addViewLoading (
				"VIEW",
				new org.drip.measure.bayesian.ViewLoading (
					_projectionSpecification.excessReturnsDistribution(),
					_projectionSpecification.assetSpaceLoadingMatrix()
				)
			) ? scopingProjectionVariateDistribution : null;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private double[] allocationTiltArray (
		final org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputAdjusted)
	{
		double[] adjustedWeightArray =
			forwardReverseOptimizationOutputAdjusted.optimalPortfolio().weightArray();

		double[] unAdjustedWeightArray =
			_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio().weightArray();

		int assetCount = unAdjustedWeightArray.length;
		double[] allocationTiltArray = new double[assetCount];

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			allocationTiltArray[assetIndex] = adjustedWeightArray[assetIndex] -
				unAdjustedWeightArray[assetIndex];
		}

		return allocationTiltArray;
	}

	/**
	 * BlackLittermanCombinationEngine Construction
	 * 
	 * @param forwardReverseOptimizationOutputUnadjusted The Unadjusted Instance of
	 * 	ForwardReverseOptimizationOutput
	 * @param priorControlSpecification The Prior Control Specification Instance
	 * @param projectionSpecification The View Projection Specification Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlackLittermanCombinationEngine (
		final org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputUnadjusted,
		final org.drip.portfolioconstruction.bayesian.PriorControlSpecification priorControlSpecification,
		final org.drip.portfolioconstruction.bayesian.ProjectionSpecification projectionSpecification)
		throws java.lang.Exception
	{
		if (null == (_forwardReverseOptimizationOutputUnadjusted =
				forwardReverseOptimizationOutputUnadjusted) ||
			null == (_priorControlSpecification = priorControlSpecification) ||
			null == (_projectionSpecification = projectionSpecification))
		{
			throw new java.lang.Exception ("BlackLittermanCombinationEngine Constructor => Invalid Inputs");
		}
	}

	/**
	 * Conduct a Black Litterman Run using a Theil-like Mixed Model Estimator for 0% Confidence in the
	 * 	Projection
	 * 
	 * @return Output of the Black Litterman Run
	 */

	public org.drip.portfolioconstruction.bayesian.BlackLittermanOutput noConfidenceRun()
	{
		double tau = _priorControlSpecification.tau();

		double[][] assetExcessReturnsCovarianceMatrix =
			_forwardReverseOptimizationOutputUnadjusted.assetExcessReturnsCovarianceMatrix();

		int assetCount = assetExcessReturnsCovarianceMatrix.length;
		double[][] assetBayesianExcessReturnsCovarianceMatrix = new double[assetCount][assetCount];

		for (int i = 0; i < assetCount; ++i)
		{
			for (int j = 0; j < assetCount; ++j)
			{
				assetBayesianExcessReturnsCovarianceMatrix[i][j] = assetExcessReturnsCovarianceMatrix[i][j] *
					tau;
			}
		}

		org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputAdjusted =
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio().meta().names(),
					_forwardReverseOptimizationOutputUnadjusted.expectedAssetExcessReturnsArray(),
					assetBayesianExcessReturnsCovarianceMatrix,
					_forwardReverseOptimizationOutputUnadjusted.riskAversion()
				);

		try
		{
			return null == forwardReverseOptimizationOutputAdjusted ? null :
				new org.drip.portfolioconstruction.bayesian.BlackLittermanOutput (
					forwardReverseOptimizationOutputAdjusted,
					allocationTiltArray (
						forwardReverseOptimizationOutputAdjusted
					)
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Conduct a Black Litterman Run using a Theil-like Mixed Model Estimator Using the specified Confidence
	 * 	Level
	 * 
	 * @return Output of the Black Litterman Run
	 */

	public org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput customConfidenceRun()
	{
		double[][] assetExcessReturnsCovarianceMatrix =
			_forwardReverseOptimizationOutputUnadjusted.assetExcessReturnsCovarianceMatrix();

		org.drip.measure.continuous.MetaRd portfolioMeta =
			_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio().meta();

		org.drip.measure.bayesian.ScopingContainer scopingProjectionVariateDistribution =
			scopingProjectionVariateDistribution();

		if (null == scopingProjectionVariateDistribution)
		{
			return null;
		}

		org.drip.measure.bayesian.R1MultivariateConvolutionMetrics jointPosteriorMetrics =
			org.drip.measure.bayesian.TheilMixedEstimationModel.GenerateComposite (
				scopingProjectionVariateDistribution,
				"VIEW",
				org.drip.measure.gaussian.R1MultivariateNormal.Standard (
					portfolioMeta,
					scopingProjectionVariateDistribution.projectionDistribution().mean(),
					assetExcessReturnsCovarianceMatrix
				)
			);

		if (null == jointPosteriorMetrics)
		{
			return null;
		}

		org.drip.measure.continuous.MetaRdDistribution r1mPosterior = jointPosteriorMetrics.posteriorDistribution();

		org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputAdjusted =
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					portfolioMeta.names(),
					r1mPosterior.mean(),
					_priorControlSpecification.useAlternateReferenceModel() ?
						assetExcessReturnsCovarianceMatrix :
						((org.drip.measure.gaussian.R1MultivariateNormal)
							r1mPosterior).covariance().covarianceMatrix(),
					_forwardReverseOptimizationOutputUnadjusted.riskAversion()
				);

		if (null == forwardReverseOptimizationOutputAdjusted)
		{
			return null;
		}

		try {
			return new org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput (
				forwardReverseOptimizationOutputAdjusted,
				allocationTiltArray (
					forwardReverseOptimizationOutputAdjusted
				),
				jointPosteriorMetrics
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Conduct a Black Litterman Run using a Theil-like Mixed Model Estimator For 100% Confidence in the
	 * 	Projection
	 * 
	 * @return Output of the Black Litterman Run
	 */

	public org.drip.portfolioconstruction.bayesian.BlackLittermanOutput fullConfidenceRun()
	{
		org.drip.measure.continuous.MetaRd portfolioMeta =
			_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio().meta();

		double[][] assetExcessReturnsCovarianceMatrix =
			_forwardReverseOptimizationOutputUnadjusted.assetExcessReturnsCovarianceMatrix();

		double riskAversion = _forwardReverseOptimizationOutputUnadjusted.riskAversion();

		java.lang.String[] assetIDArray = portfolioMeta.names();

		org.drip.measure.bayesian.ScopingContainer scopingProjectionVariateDistribution =
			scopingProjectionVariateDistribution();

		if (null == scopingProjectionVariateDistribution)
		{
			return null;
		}

		org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputAdjusted = null;

		if (_priorControlSpecification.useAlternateReferenceModel())
		{
			forwardReverseOptimizationOutputAdjusted =
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					assetIDArray,
					org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingMean (
						scopingProjectionVariateDistribution,
						"VIEW"
					),
					assetExcessReturnsCovarianceMatrix,
					riskAversion
				);
		}
		else
		{
			org.drip.measure.gaussian.R1MultivariateNormal combinedDistribution =
				org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingDistribution (
					scopingProjectionVariateDistribution,
					"VIEW",
					org.drip.measure.gaussian.R1MultivariateNormal.Standard (
						portfolioMeta,
						scopingProjectionVariateDistribution.projectionDistribution().mean(),
						assetExcessReturnsCovarianceMatrix
					)
				);

			forwardReverseOptimizationOutputAdjusted = null == combinedDistribution ? null :
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					assetIDArray,
					combinedDistribution.mean(),
					assetExcessReturnsCovarianceMatrix,
					riskAversion
				);
		}

		try
		{
			return null == forwardReverseOptimizationOutputAdjusted ? null :
				new org.drip.portfolioconstruction.bayesian.BlackLittermanOutput (
					forwardReverseOptimizationOutputAdjusted,
					allocationTiltArray (
						forwardReverseOptimizationOutputAdjusted
					)
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Idzorek Implied Projection Confidence Level
	 * 
	 * @return The Idzorek Implied Projection Confidence Level
	 */

	public org.drip.portfolioconstruction.bayesian.ProjectionImpliedConfidenceOutput impliedConfidenceRun()
	{
		double[][] assetExcessReturnsCovarianceMatrix =
			_forwardReverseOptimizationOutputUnadjusted.assetExcessReturnsCovarianceMatrix();

		org.drip.portfolioconstruction.asset.Portfolio unadjustedPortfolio =
			_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio();

		org.drip.measure.bayesian.ScopingContainer scopingProjectionVariateDistribution =
			scopingProjectionVariateDistribution();

		boolean useAlternateReferenceModel = _priorControlSpecification.useAlternateReferenceModel();

		org.drip.measure.continuous.MetaRd portfolioMeta = unadjustedPortfolio.meta();

		double riskAversion = _forwardReverseOptimizationOutputUnadjusted.riskAversion();

		java.lang.String[] assetIDArray = portfolioMeta.names();

		if (null == scopingProjectionVariateDistribution)
		{
			return null;
		}

		org.drip.measure.bayesian.R1MultivariateConvolutionMetrics jointPosteriorMetrics =
			org.drip.measure.bayesian.TheilMixedEstimationModel.GenerateComposite (
				scopingProjectionVariateDistribution,
				"VIEW",
				org.drip.measure.gaussian.R1MultivariateNormal.Standard (
					portfolioMeta,
					scopingProjectionVariateDistribution.projectionDistribution().mean(),
					assetExcessReturnsCovarianceMatrix
				)
			);

		if (null == jointPosteriorMetrics)
		{
			return null;
		}

		org.drip.measure.continuous.MetaRdDistribution posteriorDistribution = jointPosteriorMetrics.posteriorDistribution();

		org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputCustomConfidence =
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					assetIDArray,
					posteriorDistribution.mean(),
					useAlternateReferenceModel ? assetExcessReturnsCovarianceMatrix :
						((org.drip.measure.gaussian.R1MultivariateNormal)
							posteriorDistribution).covariance().covarianceMatrix(),
					riskAversion
				);

		if (null == forwardReverseOptimizationOutputCustomConfidence)
		{
			return null;
		}

		org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation
			forwardReverseOptimizationOutputFullConfidence = null;

		if (useAlternateReferenceModel)
		{
			forwardReverseOptimizationOutputFullConfidence =
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					assetIDArray,
					org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingMean (
						scopingProjectionVariateDistribution,
						"VIEW"
					),
					assetExcessReturnsCovarianceMatrix,
					riskAversion
				);
		}
		else
		{
			org.drip.measure.gaussian.R1MultivariateNormal combinedDistribution =
				org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingDistribution (
					scopingProjectionVariateDistribution,
					"VIEW",
					org.drip.measure.gaussian.R1MultivariateNormal.Standard (
						portfolioMeta,
						scopingProjectionVariateDistribution.projectionDistribution().mean(),
						assetExcessReturnsCovarianceMatrix
					)
				);

			forwardReverseOptimizationOutputFullConfidence = null == combinedDistribution ? null :
				org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Forward (
					assetIDArray,
					combinedDistribution.mean(),
					assetExcessReturnsCovarianceMatrix,
					riskAversion
				);
		}

		try
		{
			return new org.drip.portfolioconstruction.bayesian.ProjectionImpliedConfidenceOutput (
				unadjustedPortfolio.weightArray(),
				new org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput (
					forwardReverseOptimizationOutputCustomConfidence,
					allocationTiltArray (
						forwardReverseOptimizationOutputCustomConfidence
					),
					jointPosteriorMetrics
				), new org.drip.portfolioconstruction.bayesian.BlackLittermanOutput (
					forwardReverseOptimizationOutputFullConfidence,
					allocationTiltArray (
						forwardReverseOptimizationOutputFullConfidence
					)
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Exposure Loadings Attribution on a per-Projection Basis
	 * 
	 * @return The Exposure Loadings Attribution on a per-Projection Basis
	 */

	public org.drip.portfolioconstruction.bayesian.ProjectionExposure projectionExposureAttribution()
	{
		org.drip.measure.bayesian.ScopingContainer scopingProjectionVariateDistribution =
			scopingProjectionVariateDistribution();

		if (null == scopingProjectionVariateDistribution)
		{
			return null;
		}

		double[] intraViewComponentArray =
			org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionPrecisionMeanProduct (
				scopingProjectionVariateDistribution,
				"VIEW"
			);

		if (null == intraViewComponentArray)
		{
			return null;
		}

		double tau = _priorControlSpecification.tau();

		double riskAversion = _forwardReverseOptimizationOutputUnadjusted.riskAversion();

		double[][] assetSpaceLoadingMatrix = _projectionSpecification.assetSpaceLoadingMatrix();

		double[][] assetExcessReturnsCovarianceMatrix =
			_forwardReverseOptimizationOutputUnadjusted.assetExcessReturnsCovarianceMatrix();

		double projectionConfidenceScaler = 1. / tau;
		int viewCount = intraViewComponentArray.length;
		double assetConfidenceScaler = 1. / (1. + tau);
		int assetCount = assetExcessReturnsCovarianceMatrix.length;
		double[][] compositeConfidenceCovarianceMatrix = new double[viewCount][viewCount];

		for (int viewIndex = 0; viewIndex < viewCount; ++viewIndex)
		{
			intraViewComponentArray[viewIndex] = intraViewComponentArray[viewIndex] * tau / riskAversion;
		}

		double[][] projectionSpaceAssetLoadingsMatrix = org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
			org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
				assetSpaceLoadingMatrix,
				assetExcessReturnsCovarianceMatrix
			),
			org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (
				assetSpaceLoadingMatrix
			)
		);

		if (null == projectionSpaceAssetLoadingsMatrix)
		{
			return null;
		}

		double[][] projectionCovarianceMatrix =
			_projectionSpecification.excessReturnsDistribution().covariance().covarianceMatrix();

		for (int viewIndexI = 0; viewIndexI < viewCount; ++viewIndexI)
		{
			for (int viewIndexJ = 0; viewIndexJ < viewCount; ++viewIndexJ)
			{
				compositeConfidenceCovarianceMatrix[viewIndexI][viewIndexJ] =
					projectionCovarianceMatrix[viewIndexI][viewIndexJ] * projectionConfidenceScaler +
					projectionSpaceAssetLoadingsMatrix[viewIndexI][viewIndexJ] * assetConfidenceScaler;
			}
		}

		double[][] compositePrecisionProjectionScoping = org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
			org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
				org.drip.numerical.linearalgebra.R1MatrixUtil.InvertUsingGaussianElimination (
					compositeConfidenceCovarianceMatrix
				),
				assetSpaceLoadingMatrix
			),
			assetExcessReturnsCovarianceMatrix
		);

		if (null == compositePrecisionProjectionScoping)
		{
			return null;
		}

		for (int viewIndexI = 0; viewIndexI < viewCount; ++viewIndexI)
		{
			for (int viewIndexJ = 0; viewIndexJ < assetCount; ++viewIndexJ)
			{
				compositePrecisionProjectionScoping[viewIndexI][viewIndexJ] = -1. * assetConfidenceScaler *
					compositePrecisionProjectionScoping[viewIndexI][viewIndexJ];
			}
		}

		try
		{
			return new org.drip.portfolioconstruction.bayesian.ProjectionExposure (
				intraViewComponentArray,
				org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
					compositePrecisionProjectionScoping,
					_forwardReverseOptimizationOutputUnadjusted.optimalPortfolio().weightArray()
				),
				org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
					org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
						compositePrecisionProjectionScoping,
						org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (
							assetSpaceLoadingMatrix
						)
					),
					intraViewComponentArray
				),
				compositeConfidenceCovarianceMatrix
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Idzorek Implied Tilt Matrix from the User Projection Confidence Level
	 * 
	 * @param userSpecifiedProjectionConfidenceArray Array of User-specified Projection Confidence
	 * 
	 * @return The Idzorek Implied Tilt Matric from the Projection Confidence Level
	 */

	public double[][] userConfidenceProjectionTitMatrix (
		final double[] userSpecifiedProjectionConfidenceArray)
	{
		if (null == userSpecifiedProjectionConfidenceArray)
		{
			return null;
		}

		double[][] assetSpaceLoadingMatrix = _projectionSpecification.assetSpaceLoadingMatrix();

		int assetCount = assetSpaceLoadingMatrix[0].length;
		int projectionCount = assetSpaceLoadingMatrix.length;
		double[][] userConfidenceProjectionTitMatrix = new double[projectionCount][assetCount];

		if (projectionCount != userSpecifiedProjectionConfidenceArray.length)
		{
			return null;
		}

		org.drip.portfolioconstruction.bayesian.BlackLittermanOutput fullConfidenceOutput =
			fullConfidenceRun();

		if (null == fullConfidenceOutput)
		{
			return null;
		}

		double[] fullConfidenceWeightsDeviationArray = fullConfidenceOutput.allocationAdjustmentTiltArray();

		for (int projectionIndex = 0; projectionIndex < projectionCount; ++projectionIndex)
		{
			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
			{
				userConfidenceProjectionTitMatrix[projectionIndex][assetIndex] =
					fullConfidenceWeightsDeviationArray[assetIndex] *
					assetSpaceLoadingMatrix[projectionIndex][assetIndex] *
					userSpecifiedProjectionConfidenceArray[projectionIndex];
			}
		}

		return userConfidenceProjectionTitMatrix;
	}

	/**
	 * Compute the Mismatch between the User Specified Projection and the Custom Confidence Implied Tilts
	 * 
	 * @param userConfidenceProjectionTiltArray Array of the User Confidence induced Projection Tilts
	 * @param projectionIndex The Index into the Projection Meta
	 * @param projectionVariance The Projection Variance
	 * 
	 * @return The Squared Mismatch
	 * 
	 * @throws java.lang.Exception Thrown if the Squared Mismatch cannot be calculated
	 */

	public double tiltMismatchSquared (
		final double[] userConfidenceProjectionTiltArray,
		final int projectionIndex,
		final double projectionVariance)
		throws java.lang.Exception
	{
		if (null == userConfidenceProjectionTiltArray ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				projectionVariance
			))
		{
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatchSquared => Invalid Inputs");
		}

		org.drip.measure.gaussian.R1MultivariateNormal totalExcessReturnsDistribution =
			_projectionSpecification.excessReturnsDistribution();

		org.drip.measure.gaussian.R1MultivariateNormal projectionDistribution =
			org.drip.measure.gaussian.R1MultivariateNormal.Standard (
				new java.lang.String[] {
					totalExcessReturnsDistribution.meta().names()[projectionIndex]
				},
				new double[] {
					totalExcessReturnsDistribution.mean()[projectionIndex]
				},
				new double[][]
				{
					{
						projectionVariance
					}
				}
			);

		if (null == projectionDistribution)
		{
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatchSquared => Invalid Inputs");
		}

		BlackLittermanCombinationEngine projectionEngine =
			new BlackLittermanCombinationEngine (
				_forwardReverseOptimizationOutputUnadjusted,
				_priorControlSpecification,
				new org.drip.portfolioconstruction.bayesian.ProjectionSpecification (
					projectionDistribution,
					new double[][]
					{
						_projectionSpecification.assetSpaceLoadingMatrix()[projectionIndex]
					}
				)
			);

		org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput customConfidenceOuput =
			projectionEngine.customConfidenceRun();

		if (null == customConfidenceOuput)
		{
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatchSquared => Invalid Inputs");
		}

		double[] posteriorTiltArray = customConfidenceOuput.allocationAdjustmentTiltArray();

		int assetCount = posteriorTiltArray.length;
		double tiltMismatchSquared = 0.;

		if (assetCount != userConfidenceProjectionTiltArray.length)
		{
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatchSquared => Invalid Inputs");
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (
				userConfidenceProjectionTiltArray[assetIndex]
			))
			{
				throw new java.lang.Exception
					("BlackLittermanCombinationEngine::tiltMismatchSquared => Invalid Inputs");
			}

			double dblAssetTiltGap = posteriorTiltArray[assetIndex] -
				userConfidenceProjectionTiltArray[assetIndex];
			tiltMismatchSquared = tiltMismatchSquared + dblAssetTiltGap * dblAssetTiltGap;
		}

		return tiltMismatchSquared;
	}

	/**
	 * Generate the Squared Tilt Departure R<sup>1</sup> To R<sup>1</sup>
	 * 
	 * @param userConfidenceProjectionTiltArray Array of the User Confidence induced Projection Tilts
	 * @param projectionIndex The Index into the Projection Meta
	 * @param generateDerivative TRUE - Generate the Derivative of the Tilt Departure
	 * 
	 * @return The Squared Tilt Departure R<sup>1</sup> To R<sup>1</sup>
	 */

	public org.drip.function.definition.R1ToR1 tiltDepartureR1ToR1 (
		final double[] userConfidenceProjectionTiltArray,
		final int projectionIndex,
		final boolean generateDerivative)
	{
		final org.drip.function.definition.R1ToR1 tiltDepartureFunction =
			new org.drip.function.definition.R1ToR1 (
				null
			)
		{
			@Override public double evaluate (
				final double projectionVariance)
				throws java.lang.Exception
			{
				return tiltMismatchSquared (
					userConfidenceProjectionTiltArray,
					projectionIndex,
					projectionVariance
				);
			}
		};

		if (!generateDerivative)
		{
			return tiltDepartureFunction;
		}

		return new org.drip.function.definition.R1ToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double projectionVariance)
				throws java.lang.Exception
			{
				return tiltDepartureFunction.derivative (
					projectionVariance,
					1
				);
			}
		};
	}
}
