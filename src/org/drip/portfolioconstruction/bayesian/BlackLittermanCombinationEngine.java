
package org.drip.portfolioconstruction.bayesian;

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
 * BlackLittermanCombinationEngine implements the Engine that generates the Combined/Posterior Distributions
 *  from the Prior and the Conditional Joint R^1 Multivariate Normal Distributions. The References are:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *  
 *  - Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User-Specified
 *  	Confidence Levels, Ibbotson Associates, Chicago
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanCombinationEngine {
	private org.drip.portfolioconstruction.bayesian.ProjectionSpecification _ps = null;
	private org.drip.portfolioconstruction.bayesian.PriorControlSpecification _pcs = null;
	private org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput _frooUnadjusted = null;

	private org.drip.measure.bayesian.ScopingProjectionVariateDistribution scopingProjectionDistribution()
	{
		double[][] aadblAssetSpaceExcessReturnsCovariance = _frooUnadjusted.assetExcessReturnsCovariance();

		double[] adblPriorMean = _frooUnadjusted.expectedAssetExcessReturns();

		int iNumAsset = aadblAssetSpaceExcessReturnsCovariance.length;
		double[][] aadblPriorCovariance = new double[iNumAsset][iNumAsset];

		double dblRiskFreeRate = _pcs.riskFreeRate();

		double dblTau = _pcs.tau();

		for (int i = 0; i < iNumAsset; ++i) {
			adblPriorMean[i] = adblPriorMean[i] + dblRiskFreeRate;

			for (int j = 0; j < iNumAsset; ++j)
				aadblPriorCovariance[i][j] = aadblAssetSpaceExcessReturnsCovariance[i][j] * dblTau;
		}

		try {
			org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd = new
				org.drip.measure.bayesian.ScopingProjectionVariateDistribution
					(org.drip.measure.gaussian.R1MultivariateNormal.Standard
						(_frooUnadjusted.optimalPortfolio().meta(), adblPriorMean, aadblPriorCovariance));

			return spvd.addProjectionDistributionLoading ("VIEW", new
				org.drip.measure.bayesian.ProjectionDistributionLoading (_ps.excessReturnsDistribution(),
					_ps.assetSpaceLoading())) ? spvd : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double[] allocationTilt (
		final org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooAdjusted)
	{
		double[] adblUnadjustedWeights = _frooUnadjusted.optimalPortfolio().weights();

		double[] adblAdjustedWeights = frooAdjusted.optimalPortfolio().weights();

		int iNumAsset = adblUnadjustedWeights.length;
		double[] adblAllocationTilt = new double[iNumAsset];

		for (int i = 0; i < iNumAsset; ++i)
			adblAllocationTilt[i] = adblAdjustedWeights[i] - adblUnadjustedWeights[i];

		return adblAllocationTilt;
	}

	/**
	 * BlackLittermanCombinationEngine Construction
	 * 
	 * @param frooUnadjusted The Unadjusted Instance of FROO
	 * @param pcs The Prior Control Specification Instance
	 * @param ps The View Projection Specification Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlackLittermanCombinationEngine (
		final org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooUnadjusted,
		final org.drip.portfolioconstruction.bayesian.PriorControlSpecification pcs,
		final org.drip.portfolioconstruction.bayesian.ProjectionSpecification ps)
		throws java.lang.Exception
	{
		if (null == (_frooUnadjusted = frooUnadjusted) || null == (_pcs = pcs) || null == (_ps = ps))
			throw new java.lang.Exception ("BlackLittermanCombinationEngine Constructor => Invalid Inputs");
	}

	/**
	 * Conduct a Black Litterman Run using a Theil-like Mixed Model Estimator For 0% Confidence in the
	 * 	Projection
	 * 
	 * @return Output of the Black Litterman Run
	 */

	public org.drip.portfolioconstruction.bayesian.BlackLittermanOutput noConfidenceRun()
	{
		double[][] aadblAssetSpaceExcessReturnsCovariance = _frooUnadjusted.assetExcessReturnsCovariance();

		double dblTau = _pcs.tau();

		int iNumAsset = aadblAssetSpaceExcessReturnsCovariance.length;
		double[][] aadblBayesianExcessReturnsCovariance = new double[iNumAsset][iNumAsset];

		for (int i = 0; i < iNumAsset; ++i) {
			for (int j = 0; j < iNumAsset; ++j)
				aadblBayesianExcessReturnsCovariance[i][j] = aadblAssetSpaceExcessReturnsCovariance[i][j] *
					dblTau;
		}

		org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooAdjusted =
			org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward
				(_frooUnadjusted.optimalPortfolio().meta().names(),
					_frooUnadjusted.expectedAssetExcessReturns(), aadblBayesianExcessReturnsCovariance,
						_frooUnadjusted.riskAversion());

		try {
			return null == frooAdjusted ? null : new
				org.drip.portfolioconstruction.bayesian.BlackLittermanOutput (frooAdjusted, allocationTilt
					(frooAdjusted));
		} catch (java.lang.Exception e) {
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
		double[][] aadblAssetSpaceExcessReturnsCovariance = _frooUnadjusted.assetExcessReturnsCovariance();

		org.drip.measure.continuous.MultivariateMeta meta = _frooUnadjusted.optimalPortfolio().meta();

		org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd =
			scopingProjectionDistribution();

		if (null == spvd) return null;

		org.drip.measure.bayesian.JointPosteriorMetrics jpm =
			org.drip.measure.bayesian.TheilMixedEstimationModel.GenerateComposite (spvd, "VIEW",
				org.drip.measure.gaussian.R1MultivariateNormal.Standard (meta,
					spvd.scopingDistribution().mean(), aadblAssetSpaceExcessReturnsCovariance));

		if (null == jpm) return null;

		org.drip.measure.continuous.R1Multivariate r1mPosterior = jpm.posterior();

		org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooAdjusted =
			org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward (meta.names(),
				r1mPosterior.mean(), _pcs.useAlternateReferenceModel() ?
					aadblAssetSpaceExcessReturnsCovariance :
						((org.drip.measure.gaussian.R1MultivariateNormal)
							r1mPosterior).covariance().covarianceMatrix(), _frooUnadjusted.riskAversion());

		if (null == frooAdjusted) return null;

		try {
			return new org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput
				(frooAdjusted, allocationTilt (frooAdjusted), jpm);
		} catch (java.lang.Exception e) {
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
		org.drip.measure.continuous.MultivariateMeta meta = _frooUnadjusted.optimalPortfolio().meta();

		double[][] aadblAssetSpaceExcessReturnsCovariance = _frooUnadjusted.assetExcessReturnsCovariance();

		double dblRiskAversion = _frooUnadjusted.riskAversion();

		java.lang.String[] astrAssetID = meta.names();

		org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd =
			scopingProjectionDistribution();

		if (null == spvd) return null;

		org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooAdjusted = null;

		if (_pcs.useAlternateReferenceModel())
			frooAdjusted = org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward
				(astrAssetID,
					org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingMean (spvd,
						"VIEW"), aadblAssetSpaceExcessReturnsCovariance, dblRiskAversion);
		else {
			org.drip.measure.gaussian.R1MultivariateNormal r1mnCombined =
				org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingDistribution
					(spvd, "VIEW", org.drip.measure.gaussian.R1MultivariateNormal.Standard (meta,
						spvd.scopingDistribution().mean(), aadblAssetSpaceExcessReturnsCovariance));

			frooAdjusted = null == r1mnCombined ? null :
				org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward
					(astrAssetID, r1mnCombined.mean(), aadblAssetSpaceExcessReturnsCovariance,
						dblRiskAversion);
		}

		try {
			return null == frooAdjusted ? null : new
				org.drip.portfolioconstruction.bayesian.BlackLittermanOutput (frooAdjusted, allocationTilt
					(frooAdjusted));
		} catch (java.lang.Exception e) {
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
		double[][] aadblAssetSpaceExcessReturnsCovariance = _frooUnadjusted.assetExcessReturnsCovariance();

		org.drip.portfolioconstruction.asset.Portfolio pfUnadjusted = _frooUnadjusted.optimalPortfolio();

		org.drip.measure.continuous.MultivariateMeta meta = pfUnadjusted.meta();

		boolean bUseAlternateReferenceModel = _pcs.useAlternateReferenceModel();

		double dblRiskAversion = _frooUnadjusted.riskAversion();

		java.lang.String[] astrAssetID = meta.names();

		org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd =
			scopingProjectionDistribution();

		if (null == spvd) return null;

		org.drip.measure.bayesian.JointPosteriorMetrics jpm =
			org.drip.measure.bayesian.TheilMixedEstimationModel.GenerateComposite (spvd, "VIEW",
				org.drip.measure.gaussian.R1MultivariateNormal.Standard (meta,
					spvd.scopingDistribution().mean(), aadblAssetSpaceExcessReturnsCovariance));

		if (null == jpm) return null;

		org.drip.measure.continuous.R1Multivariate r1mPosterior = jpm.posterior();

		org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooCustomConfidence =
			org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward (astrAssetID,
				r1mPosterior.mean(), bUseAlternateReferenceModel ? aadblAssetSpaceExcessReturnsCovariance :
					((org.drip.measure.gaussian.R1MultivariateNormal)
						r1mPosterior).covariance().covarianceMatrix(), dblRiskAversion);

		if (null == frooCustomConfidence) return null;

		org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooFullConfidence = null;

		if (bUseAlternateReferenceModel)
			frooFullConfidence =
				org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward
					(astrAssetID,
						org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingMean
							(spvd, "VIEW"), aadblAssetSpaceExcessReturnsCovariance, dblRiskAversion);
		else {
			org.drip.measure.gaussian.R1MultivariateNormal r1mnCombined =
				org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionInducedScopingDistribution
					(spvd, "VIEW", org.drip.measure.gaussian.R1MultivariateNormal.Standard (meta,
						spvd.scopingDistribution().mean(), aadblAssetSpaceExcessReturnsCovariance));

			frooFullConfidence = null == r1mnCombined ? null :
				org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Forward
					(astrAssetID, r1mnCombined.mean(), aadblAssetSpaceExcessReturnsCovariance,
						dblRiskAversion);
		}

		try {
			return new org.drip.portfolioconstruction.bayesian.ProjectionImpliedConfidenceOutput
				(pfUnadjusted.weights(), new
					org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput
						(frooCustomConfidence, allocationTilt (frooCustomConfidence), jpm), new
							org.drip.portfolioconstruction.bayesian.BlackLittermanOutput (frooFullConfidence,
								allocationTilt (frooFullConfidence)));
		} catch (java.lang.Exception e) {
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
		org.drip.measure.bayesian.ScopingProjectionVariateDistribution spvd =
			scopingProjectionDistribution();

		if (null == spvd) return null;

		double[] adblIntraViewComponent =
			org.drip.measure.bayesian.TheilMixedEstimationModel.ProjectionPrecisionMeanProduct (spvd,
				"VIEW");

		if (null == adblIntraViewComponent) return null;

		double dblTau = _pcs.tau();

		double dblRiskAversion = _frooUnadjusted.riskAversion();

		double[][] aadblProjectionSpaceLoading = _ps.assetSpaceLoading();

		double[][] aadblAssetExcessReturnsCovariance = _frooUnadjusted.assetExcessReturnsCovariance();

		int iNumView = adblIntraViewComponent.length;
		double dblProjectionConfidenceScaler = 1. / dblTau;
		double dblAssetConfidenceScaler = 1. / (1. + dblTau);
		int iNumAsset = aadblAssetExcessReturnsCovariance.length;
		double[][] aadblCompositeConfidenceCovariance = new double[iNumView][iNumView];

		for (int i = 0; i < iNumView; ++i)
			adblIntraViewComponent[i] = adblIntraViewComponent[i] * dblTau / dblRiskAversion;

		double[][] aadblProjectionSpaceAssetCovariance = org.drip.quant.linearalgebra.Matrix.Product
			(org.drip.quant.linearalgebra.Matrix.Product (aadblProjectionSpaceLoading,
				aadblAssetExcessReturnsCovariance), org.drip.quant.linearalgebra.Matrix.Transpose
					(aadblProjectionSpaceLoading));

		if (null == aadblProjectionSpaceAssetCovariance) return null;

		double[][] aadblProjectionCovariance =
			_ps.excessReturnsDistribution().covariance().covarianceMatrix();

		for (int i = 0; i < iNumView; ++i) {
			for (int j = 0; j < iNumView; ++j)
				aadblCompositeConfidenceCovariance[i][j] = aadblProjectionCovariance[i][j] *
					dblProjectionConfidenceScaler + aadblProjectionSpaceAssetCovariance[i][j] *
						dblAssetConfidenceScaler;
		}

		double[][] aadblCompositePrecisionProjectionScoping = org.drip.quant.linearalgebra.Matrix.Product
			(org.drip.quant.linearalgebra.Matrix.Product
				(org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination
					(aadblCompositeConfidenceCovariance), aadblProjectionSpaceLoading),
						aadblAssetExcessReturnsCovariance);

		if (null == aadblCompositePrecisionProjectionScoping) return null;

		for (int i = 0; i < iNumView; ++i) {
			for (int j = 0; j < iNumAsset; ++j)
				aadblCompositePrecisionProjectionScoping[i][j] = -1. * dblAssetConfidenceScaler *
					aadblCompositePrecisionProjectionScoping[i][j];
		}

		try {
			return new org.drip.portfolioconstruction.bayesian.ProjectionExposure (adblIntraViewComponent,
				org.drip.quant.linearalgebra.Matrix.Product (aadblCompositePrecisionProjectionScoping,
					_frooUnadjusted.optimalPortfolio().weights()),
						org.drip.quant.linearalgebra.Matrix.Product
							(org.drip.quant.linearalgebra.Matrix.Product
								(aadblCompositePrecisionProjectionScoping,
									org.drip.quant.linearalgebra.Matrix.Transpose
										(aadblProjectionSpaceLoading)), adblIntraViewComponent),
											aadblCompositeConfidenceCovariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Idzorek Implied Tilt from the User Projection Confidence Level
	 * 
	 * @param adblUserSpecifiedProjectionConfidence Array of User-specified Projection Confidence
	 * 
	 * @return The Idzorek Implied Tilt from the Projection Confidence Level
	 */

	public double[][] userConfidenceProjectionTilt (
		final double[] adblUserSpecifiedProjectionConfidence)
	{
		if (null == adblUserSpecifiedProjectionConfidence) return null;

		double[][] aadblAssetSpaceLoading = _ps.assetSpaceLoading();

		int iNumAsset = aadblAssetSpaceLoading[0].length;
		int iNumProjection = aadblAssetSpaceLoading.length;
		double[][] aadblProjectionTilt = new double[iNumProjection][iNumAsset];

		if (iNumProjection != adblUserSpecifiedProjectionConfidence.length) return null;

		org.drip.portfolioconstruction.bayesian.BlackLittermanOutput bloFullConfidence = fullConfidenceRun();

		if (null == bloFullConfidence) return null;

		double[] adblFullConfidenceWeightsDeviation = bloFullConfidence.allocationAdjustmentTilt();

		for (int i = 0; i < iNumProjection; ++i) {
			for (int j = 0; j < iNumAsset; ++j)
				aadblProjectionTilt[i][j] = adblFullConfidenceWeightsDeviation[j] *
					aadblAssetSpaceLoading[i][j] * adblUserSpecifiedProjectionConfidence[i];
		}

		return aadblProjectionTilt;
	}

	/**
	 * Compute the Mismatch between the User Specified Projection and the Custom Confidence Implied Tilts
	 * 
	 * @param adblUserConfidenceProjectionTilt Array of the User Confidence induced Projection Tilts
	 * @param iProjectionIndex The Index into the Projection Meta
	 * @param dblProjectionVariance The Projection Variance
	 * 
	 * @return The Squared Mismatch
	 * 
	 * @throws java.lang.Exception Thrown if the Squared Mismatch cannot be calculated
	 */

	public double tiltMismatch (
		final double[] adblUserConfidenceProjectionTilt,
		final int iProjectionIndex,
		final double dblProjectionVariance)
		throws java.lang.Exception
	{
		if (null == adblUserConfidenceProjectionTilt || !org.drip.quant.common.NumberUtil.IsValid
			(dblProjectionVariance))
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatch => Invalid Inputs");

		org.drip.measure.gaussian.R1MultivariateNormal r1mnTotal = _ps.excessReturnsDistribution();

		org.drip.measure.gaussian.R1MultivariateNormal r1mnProjection =
			org.drip.measure.gaussian.R1MultivariateNormal.Standard (new java.lang.String[]
				{r1mnTotal.meta().names()[iProjectionIndex]}, new double[]
					{r1mnTotal.mean()[iProjectionIndex]}, new double[][] {{dblProjectionVariance}});

		if (null == r1mnProjection)
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatch => Invalid Inputs");

		org.drip.portfolioconstruction.bayesian.ProjectionSpecification psProjection = new
			org.drip.portfolioconstruction.bayesian.ProjectionSpecification (r1mnProjection, new double[][]
				{_ps.assetSpaceLoading()[iProjectionIndex]});

		BlackLittermanCombinationEngine blceProjection = new BlackLittermanCombinationEngine
			(_frooUnadjusted, _pcs, psProjection);

		org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput blcco =
			blceProjection.customConfidenceRun();

		if (null == blcco)
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatch => Invalid Inputs");

		double[] adblPosteriorTilt = blcco.allocationAdjustmentTilt();

		int iNumAsset = adblPosteriorTilt.length;
		double dblTiltGap = 0.;

		if (iNumAsset != adblUserConfidenceProjectionTilt.length)
			throw new java.lang.Exception
				("BlackLittermanCombinationEngine::tiltMismatch => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblUserConfidenceProjectionTilt[i]))
				throw new java.lang.Exception
					("BlackLittermanCombinationEngine::tiltMismatch => Invalid Inputs");

			double dblAssetTiltGap = adblPosteriorTilt[i] - adblUserConfidenceProjectionTilt[i];
			dblTiltGap = dblTiltGap + dblAssetTiltGap * dblAssetTiltGap;
		}

		return dblTiltGap;
	}

	/**
	 * Generate the Squared Tilt Departure R^1 To R^1
	 * 
	 * @param adblUserConfidenceProjectionTilt Array of the User Confidence induced Projection Tilts
	 * @param iProjectionIndex The Index into the Projection Meta
	 * @param bDerivative TRUE - Generate the Derivative of the Tilt Departure
	 * 
	 * @return The Squared Tilt Departure R^1 To R^1
	 */

	public org.drip.function.definition.R1ToR1 tiltDepartureR1ToR1 (
		final double[] adblUserConfidenceProjectionTilt,
		final int iProjectionIndex,
		final boolean bDerivative)
	{
		final org.drip.function.definition.R1ToR1 r1ToR1TiltDeparture = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblProjectionVariance)
				throws java.lang.Exception
			{
				return tiltMismatch (adblUserConfidenceProjectionTilt, iProjectionIndex,
					dblProjectionVariance);
			}
		};

		if (!bDerivative) return r1ToR1TiltDeparture;

		return new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblProjectionVariance)
				throws java.lang.Exception
			{
				return r1ToR1TiltDeparture.derivative (dblProjectionVariance, 1);
			}
		};
	}
}
