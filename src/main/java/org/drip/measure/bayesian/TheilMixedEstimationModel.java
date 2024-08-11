
package org.drip.measure.bayesian;

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
 * <i>TheilMixedEstimationModel</i> implements the Theil's Mixed Model for the Estimation of the Distribution
 * Parameters. The Reference is:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Theil, H. (1971): <i>Principles of Econometrics</i> <b>Wiley</b>
 * 		</li>
 * 	</ul>
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

public class TheilMixedEstimationModel {

	/**
	 * Generate the Joint Mixed Estimation Model Joint/Posterior Metrics
	 * 
	 * @param meta The R^1 Multivariate Meta Descriptors
	 * @param pdl1 Projection Distribution and Loading #1
	 * @param pdl2 Projection Distribution and Loading #2
	 * @param r1mnUnconditional The R^1 Multivariate Normal Unconditional Distribution
	 * 
	 * @return The Joint Mixed Estimation Model Joint/Posterior Metrics
	 */

	public static final org.drip.measure.bayesian.R1MultivariateConvolutionMetrics GenerateComposite (
		final org.drip.measure.continuous.MultivariateMeta meta,
		final org.drip.measure.bayesian.ProjectionDistributionLoading pdl1,
		final org.drip.measure.bayesian.ProjectionDistributionLoading pdl2,
		final org.drip.measure.gaussian.R1MultivariateNormal r1mnUnconditional)
	{
		if (null == meta || null == pdl1 || null == pdl2 || null == r1mnUnconditional) return null;

		int iNumScopingVariate = meta.numVariable();

		if (iNumScopingVariate != pdl1.numberOfScopingVariate() || iNumScopingVariate !=
			pdl2.numberOfScopingVariate() || iNumScopingVariate != r1mnUnconditional.meta().numVariable())
			return null;

		org.drip.measure.continuous.R1Multivariate r1m1 = pdl1.distribution();

		org.drip.measure.continuous.R1Multivariate r1m2 = pdl2.distribution();

		if (!(r1m1 instanceof org.drip.measure.gaussian.R1MultivariateNormal) || !(r1m2 instanceof
			org.drip.measure.gaussian.R1MultivariateNormal))
			return null;

		double[] adblJointPrecisionWeightedMean = new double[iNumScopingVariate];
		double[][] aadblJointPrecision = new double[iNumScopingVariate][iNumScopingVariate];
		double[][] aadblPosteriorCovariance = new double[iNumScopingVariate][iNumScopingVariate];
		org.drip.measure.gaussian.R1MultivariateNormal r1mn1 =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1m1;
		org.drip.measure.gaussian.R1MultivariateNormal r1mn2 =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1m2;

		double[][] aadblScopingLoading1 = pdl1.scopingLoading();

		double[][] aadblScopingLoading2 = pdl2.scopingLoading();

		double[][] aadblScopingWeightedPrecision1 = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (aadblScopingLoading1),
				r1mn1.covariance().precisionMatrix());

		double[][] aadblScopingWeightedPrecision2 = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (aadblScopingLoading2),
				r1mn2.covariance().precisionMatrix());

		double[][] aadblScopingSpacePrecision1 = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingWeightedPrecision1, aadblScopingLoading1);

		double[][] aadblScopingSpacePrecision2 = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingWeightedPrecision2, aadblScopingLoading2);

		if (null == aadblScopingSpacePrecision1 || null == aadblScopingSpacePrecision2) return null;

		double[] adblPrecisionWeightedMean1 = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingWeightedPrecision1, r1mn1.mean());

		double[] adblPrecisionWeightedMean2 = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingWeightedPrecision2, r1mn2.mean());

		if (null == adblPrecisionWeightedMean1 || null == adblPrecisionWeightedMean2) return null;

		for (int i = 0; i < iNumScopingVariate; ++i) {
			adblJointPrecisionWeightedMean[i] = adblPrecisionWeightedMean1[i] +
				adblPrecisionWeightedMean2[i];

			for (int j = 0; j < iNumScopingVariate; ++j)
				aadblJointPrecision[i][j] = aadblScopingSpacePrecision1[i][j] +
					aadblScopingSpacePrecision2[i][j];
		}

		double[][] aadblJointCovariance = org.drip.numerical.linearalgebra.R1MatrixUtil.InvertUsingGaussianElimination
			(aadblJointPrecision);

		double[] adblJointPosteriorMean = org.drip.numerical.linearalgebra.R1MatrixUtil.Product (aadblJointCovariance,
			adblJointPrecisionWeightedMean);

		double[][] aadblUnconditionalCovariance = r1mnUnconditional.covariance().covarianceMatrix();

		for (int i = 0; i < iNumScopingVariate; ++i) {
			for (int j = 0; j < iNumScopingVariate; ++j)
				aadblPosteriorCovariance[i][j] = aadblJointCovariance[i][j] +
					aadblUnconditionalCovariance[i][j];
		}

		try {
			return new org.drip.measure.bayesian.R1MultivariateConvolutionMetrics (r1mn1, r1mnUnconditional, r1mn2, new
				org.drip.measure.gaussian.R1MultivariateNormal (meta, adblJointPosteriorMean, new
					org.drip.measure.gaussian.Covariance (aadblJointCovariance)), new
						org.drip.measure.gaussian.R1MultivariateNormal (meta, adblJointPosteriorMean, new
							org.drip.measure.gaussian.Covariance (aadblPosteriorCovariance)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Combined R^1 Multivariate Normal Distribution from the SPVD and the Named Projections
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection1 Name of Projection #1
	 * @param strProjection2 Name of Projection #2
	 * @param r1mnUnconditional The R^1 Multivariate Normal Unconditional Distribution
	 * 
	 * @return The Combined R^1 Multivariate Normal Distribution
	 */

	public static final org.drip.measure.bayesian.R1MultivariateConvolutionMetrics GenerateComposite (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection1,
		final java.lang.String strProjection2,
		final org.drip.measure.gaussian.R1MultivariateNormal r1mnUnconditional)
	{
		return null == spvd ? null : GenerateComposite (spvd.scopingDistribution().meta(),
			spvd.projectionDistributionLoading (strProjection1), spvd.projectionDistributionLoading
				(strProjection2), r1mnUnconditional);
	}

	/**
	 * Generate the Combined R^1 Multivariate Normal Distribution from the SPVD, the NATIVE Projection, and
	 *  the Named Projection
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * @param r1mnUnconditional The R^1 Multivariate Normal Unconditional Distribution
	 * 
	 * @return The Combined R^1 Multivariate Normal Distribution
	 */

	public static final org.drip.measure.bayesian.R1MultivariateConvolutionMetrics GenerateComposite (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection,
		final org.drip.measure.gaussian.R1MultivariateNormal r1mnUnconditional)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1m = spvd.scopingDistribution();

		if (!(r1m instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnScoping =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1m;

		return GenerateComposite (r1mnScoping.meta(), spvd.projectionDistributionLoading ("NATIVE"),
			spvd.projectionDistributionLoading (strProjection), r1mnUnconditional);
	}

	/**
	 * Generate the Projection Space Scoping Mean
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Space Scoping Mean
	 */

	public static final double[] ProjectionSpaceScopingMean (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		return null == pdl ? null : org.drip.numerical.linearalgebra.R1MatrixUtil.Product (pdl.scopingLoading(),
			spvd.scopingDistribution().mean());
	}

	/**
	 * Generate the Projection Space Projection-Scoping Mean Differential
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Space Projection-Scoping Mean Differential
	 */

	public static final double[] ProjectionSpaceScopingDifferential (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		double[] adblProjectionSpaceScopingMean = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(pdl.scopingLoading(), spvd.scopingDistribution().mean());

		if (null == adblProjectionSpaceScopingMean) return null;

		int iNumProjection = adblProjectionSpaceScopingMean.length;
		double[] adblProjectionSpaceScopingDifferential = new double[iNumProjection];

		double[] adblProjectionMean = pdl.distribution().mean();

		for (int i = 0; i < iNumProjection; ++i)
			adblProjectionSpaceScopingDifferential[i] = adblProjectionMean[i] -
				adblProjectionSpaceScopingMean[i];

		return adblProjectionSpaceScopingDifferential;
	}

	/**
	 * Generate the Projection Space Scoping Co-variance
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Space Scoping Co-variance
	 */

	public static final org.drip.measure.gaussian.Covariance ProjectionSpaceScopingCovariance (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		if (!(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnScoping =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mScoping;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		double[][] aadblScopingLoading = pdl.scopingLoading();

		try {
			return new org.drip.measure.gaussian.Covariance (org.drip.numerical.linearalgebra.R1MatrixUtil.Product
				(aadblScopingLoading, org.drip.numerical.linearalgebra.R1MatrixUtil.Product
					(r1mnScoping.covariance().covarianceMatrix(),
						org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (aadblScopingLoading))));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Shadow of the Scoping on Projection Transpose
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Shadow of the Scoping on Projection Transpose
	 */

	public static final double[][] ShadowScopingProjectionTranspose (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		if (!(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		return null == pdl ? null : org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(((org.drip.measure.gaussian.R1MultivariateNormal) r1mScoping).covariance().covarianceMatrix(),
				org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (pdl.scopingLoading()));
	}

	/**
	 * Compute the Shadow of the Scoping on Projection
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Shadow of the Scoping on Projection
	 */

	public static final double[][] ShadowScopingProjection (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		return !(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal) || null == pdl ? null
			: org.drip.numerical.linearalgebra.R1MatrixUtil.Product (pdl.scopingLoading(),
				((org.drip.measure.gaussian.R1MultivariateNormal)
					r1mScoping).covariance().covarianceMatrix());
	}

	/**
	 * Compute the Projection Precision Mean Dot Product Array
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Precision Mean Dot Product Array
	 */

	public static final double[] ProjectionPrecisionMeanProduct (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		org.drip.measure.continuous.R1Multivariate r1mProjection = pdl.distribution();

		return !(r1mProjection instanceof org.drip.measure.gaussian.R1MultivariateNormal) ? null :
			org.drip.numerical.linearalgebra.R1MatrixUtil.Product (((org.drip.measure.gaussian.R1MultivariateNormal)
				r1mProjection).covariance().precisionMatrix(), r1mProjection.mean());
	}

	/**
	 * Compute the Projection Induced Scoping Mean Deviation
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Induced Scoping Mean Deviation
	 */

	public static final double[] ProjectionInducedScopingDeviation (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		if (!(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnScoping =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mScoping;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		double[][] aadblScopingLoading = pdl.scopingLoading();

		double[][] aadblProjectionScopingShadow = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(r1mnScoping.covariance().covarianceMatrix(), org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose
				(aadblScopingLoading));

		double[] adblProjectionSpaceScopingMean = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingLoading, r1mScoping.mean());

		if (null == adblProjectionSpaceScopingMean) return null;

		int iNumProjection = adblProjectionSpaceScopingMean.length;
		double[] adblProjectionSpaceScopingDifferential = new double[iNumProjection];

		double[] adblProjectionMean = pdl.distribution().mean();

		for (int i = 0; i < iNumProjection; ++i)
			adblProjectionSpaceScopingDifferential[i] = adblProjectionMean[i] -
				adblProjectionSpaceScopingMean[i];

		return org.drip.numerical.linearalgebra.R1MatrixUtil.Product (aadblProjectionScopingShadow,
			org.drip.numerical.linearalgebra.R1MatrixUtil.Product
				(org.drip.numerical.linearalgebra.R1MatrixUtil.InvertUsingGaussianElimination
					(org.drip.numerical.linearalgebra.R1MatrixUtil.Product (aadblScopingLoading,
						aadblProjectionScopingShadow)), adblProjectionSpaceScopingDifferential));
	}

	/**
	 * Compute the Projection Induced Scoping Deviation Adjusted Mean
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Induced Scoping Deviation Adjusted Mean
	 */

	public static final double[] ProjectionInducedScopingMean (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		double[] adblScopingMean = r1mScoping.mean();

		int iNumScopingVariate = r1mScoping.meta().numVariable();

		if (!(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnScoping =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mScoping;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		double[][] aadblScopingLoading = pdl.scopingLoading();

		double[][] aadblProjectionScopingShadow = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(r1mnScoping.covariance().covarianceMatrix(), org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose
				(aadblScopingLoading));

		double[] adblProjectionSpaceScopingMean = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingLoading, adblScopingMean);

		if (null == adblProjectionSpaceScopingMean) return null;

		int iNumProjection = adblProjectionSpaceScopingMean.length;
		double[] adblProjectionInducedScopingMean = new double[iNumScopingVariate];
		double[] adblProjectionSpaceScopingDifferential = new double[iNumProjection];

		double[] adblProjectionMean = pdl.distribution().mean();

		for (int i = 0; i < iNumProjection; ++i)
			adblProjectionSpaceScopingDifferential[i] = adblProjectionMean[i] -
				adblProjectionSpaceScopingMean[i];

		double[] adblProjectionInducedScopingDeviation = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblProjectionScopingShadow, org.drip.numerical.linearalgebra.R1MatrixUtil.Product
				(org.drip.numerical.linearalgebra.R1MatrixUtil.InvertUsingGaussianElimination
					(org.drip.numerical.linearalgebra.R1MatrixUtil.Product (aadblScopingLoading,
						aadblProjectionScopingShadow)), adblProjectionSpaceScopingDifferential));

		if (null == adblProjectionInducedScopingDeviation) return null;

		for (int i = 0; i < iNumScopingVariate; ++i)
			adblProjectionInducedScopingMean[i] = adblScopingMean[i] +
				adblProjectionInducedScopingDeviation[i];

		return adblProjectionInducedScopingMean;
	}

	/**
	 * Compute the Asset Space Projection Co-variance
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Asset Space Projection Co-variance
	 */

	public static final double[][] AssetSpaceProjectionCovariance (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		org.drip.measure.continuous.R1Multivariate r1mProjection = pdl.distribution();

		if (!(r1mProjection instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		double[][] aadblScopingLoading = pdl.scopingLoading();

		return org.drip.numerical.linearalgebra.R1MatrixUtil.Product (org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose
			(aadblScopingLoading), org.drip.numerical.linearalgebra.R1MatrixUtil.Product
				(((org.drip.measure.gaussian.R1MultivariateNormal)
					r1mProjection).covariance().covarianceMatrix(), aadblScopingLoading));
	}

	/**
	 * Compute the Projection Space Asset Co-variance
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * 
	 * @return The Projection Space Asset Co-variance
	 */

	public static final double[][] ProjectionSpaceAssetCovariance (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection)
	{
		if (null == spvd) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		if (!(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		double[][] aadblScopingLoading = pdl.scopingLoading();

		return org.drip.numerical.linearalgebra.R1MatrixUtil.Product (aadblScopingLoading,
			org.drip.numerical.linearalgebra.R1MatrixUtil.Product (((org.drip.measure.gaussian.R1MultivariateNormal)
				r1mScoping).covariance().covarianceMatrix(), org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose
					(aadblScopingLoading)));
	}

	/**
	 * Compute the Projection Induced Scoping Deviation Adjusted Mean
	 * 
	 * @param spvd The Scoping/Projection Distribution
	 * @param strProjection Name of Projection
	 * @param r1mnUnconditional The Unconditional Distribution
	 * 
	 * @return The Projection Induced Scoping Deviation Adjusted Mean
	 */

	public static final org.drip.measure.gaussian.R1MultivariateNormal ProjectionInducedScopingDistribution (
		final org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd,
		final java.lang.String strProjection,
		final org.drip.measure.gaussian.R1MultivariateNormal r1mnUnconditional)
	{
		if (null == spvd || null == r1mnUnconditional) return null;

		org.drip.measure.continuous.R1Multivariate r1mScoping = spvd.scopingDistribution();

		double[] adblScopingMean = r1mScoping.mean();

		int iNumScopingVariate = r1mScoping.meta().numVariable();

		if (!(r1mScoping instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnScoping =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mScoping;

		org.drip.measure.bayesian.ProjectionDistributionLoading pdl = spvd.projectionDistributionLoading
			(strProjection);

		if (null == pdl) return null;

		double[][] aadblScopingLoading = pdl.scopingLoading();

		double[][] aadblProjectionScopingShadow = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(r1mnScoping.covariance().covarianceMatrix(), org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose
				(aadblScopingLoading));

		double[] adblProjectionSpaceScopingMean = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblScopingLoading, adblScopingMean);

		if (null == adblProjectionSpaceScopingMean) return null;

		int iNumProjection = adblProjectionSpaceScopingMean.length;
		double[] adblProjectionInducedScopingMean = new double[iNumScopingVariate];
		double[] adblProjectionSpaceScopingDifferential = new double[iNumProjection];

		double[] adblProjectionMean = pdl.distribution().mean();

		for (int i = 0; i < iNumProjection; ++i)
			adblProjectionSpaceScopingDifferential[i] = adblProjectionMean[i] -
				adblProjectionSpaceScopingMean[i];

		double[] adblProjectionInducedScopingDeviation = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(aadblProjectionScopingShadow, org.drip.numerical.linearalgebra.R1MatrixUtil.Product
				(org.drip.numerical.linearalgebra.R1MatrixUtil.InvertUsingGaussianElimination
					(org.drip.numerical.linearalgebra.R1MatrixUtil.Product (aadblScopingLoading,
						aadblProjectionScopingShadow)), adblProjectionSpaceScopingDifferential));

		if (null == adblProjectionInducedScopingDeviation) return null;

		for (int i = 0; i < iNumScopingVariate; ++i)
			adblProjectionInducedScopingMean[i] = adblScopingMean[i] +
				adblProjectionInducedScopingDeviation[i];

		org.drip.measure.continuous.R1Multivariate r1mProjection = pdl.distribution();

		if (!(r1mProjection instanceof org.drip.measure.gaussian.R1MultivariateNormal)) return null;

		try {
			return new org.drip.measure.gaussian.R1MultivariateNormal (r1mnUnconditional.meta(),
				adblProjectionInducedScopingMean, new org.drip.measure.gaussian.Covariance
					(org.drip.numerical.linearalgebra.R1MatrixUtil.Product
						(org.drip.numerical.linearalgebra.R1MatrixUtil.Transpose (aadblScopingLoading),
							org.drip.numerical.linearalgebra.R1MatrixUtil.Product
								(((org.drip.measure.gaussian.R1MultivariateNormal)
									r1mProjection).covariance().covarianceMatrix(), aadblScopingLoading))));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
