
package org.drip.json.assetallocation;

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
 * BlackLittermanProcessor Sets Up and Executes a JSON Based In/Out Processing Service for the Black
 *  Litterman Bayesian View Incorporation/Parameter Estimation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanProcessor {

	/**
	 * JSON Based in/out Bayesian Co-variance/Returns Estimation Thunker
	 * 
	 * @param jsonParameter Bayesian Co-variance/Returns Estimation Parameters
	 * 
	 * @return JSON Bayesian Co-variance/Returns Estimation Response
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONObject Estimate (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		java.lang.String[] astrAssetID = org.drip.json.parser.Converter.StringArrayEntry (jsonParameter,
			"AssetSet");

		double[][] aadblAssetSpaceViewProjection = org.drip.json.parser.Converter.DualDoubleArrayEntry
			(jsonParameter, "AssetSpaceViewProjection");

		double dblTau = java.lang.Double.NaN;
		double dblRiskAversion = java.lang.Double.NaN;
		double dblRiskFreeRate = java.lang.Double.NaN;
		org.drip.measure.gaussian.R1MultivariateNormal viewDistribution = null;
		org.drip.portfolioconstruction.bayesian.BlackLittermanCombinationEngine blce = null;
		int iNumView = null == aadblAssetSpaceViewProjection ? 0 : aadblAssetSpaceViewProjection.length;
		java.lang.String[] astrProjectionName = 0 == iNumView? null : new java.lang.String[iNumView];

		double[] adblAssetEquilibriumWeight = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"AssetEquilibriumWeight");

		double[][] aadblAssetExcessReturnsCovariance = org.drip.json.parser.Converter.DualDoubleArrayEntry
			(jsonParameter, "AssetExcessReturnsCovariance");

		double[] adblProjectionExpectedExcessReturns = org.drip.json.parser.Converter.DoubleArrayEntry
			(jsonParameter, "ProjectionExpectedExcessReturns");

		double[][] aadblProjectionExcessReturnsCovariance =
			org.drip.json.parser.Converter.DualDoubleArrayEntry (jsonParameter,
				"ProjectionExcessReturnsCovariance");

		for (int i = 0; i < iNumView ; ++i)
			astrProjectionName[i] = "PROJECTION #" + i;

		try {
			dblTau = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "Tau");

			dblRiskAversion = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "Delta");

			dblRiskFreeRate = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "RiskFreeRate");

			viewDistribution = org.drip.measure.gaussian.R1MultivariateNormal.Standard (new
				org.drip.measure.continuous.MultivariateMeta (astrProjectionName),
					adblProjectionExpectedExcessReturns, aadblProjectionExcessReturnsCovariance);

			blce = new org.drip.portfolioconstruction.bayesian.BlackLittermanCombinationEngine
				(org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput.Reverse
					(org.drip.portfolioconstruction.asset.Portfolio.Standard (astrAssetID,
						adblAssetEquilibriumWeight), aadblAssetExcessReturnsCovariance, dblRiskAversion), new
							org.drip.portfolioconstruction.bayesian.PriorControlSpecification (false,
								dblRiskFreeRate, dblTau), new
									org.drip.portfolioconstruction.bayesian.ProjectionSpecification
										(viewDistribution, aadblAssetSpaceViewProjection));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput blo = blce.customConfidenceRun();

		if (null == blo) return null;

		org.drip.measure.bayesian.JointPosteriorMetrics jpm = blo.combinationMetrics();

		org.drip.measure.continuous.R1Multivariate r1mPrior = jpm.prior();

		org.drip.measure.continuous.R1Multivariate r1mJoint = jpm.joint();

		org.drip.measure.continuous.R1Multivariate r1mPosterior = jpm.posterior();

		if (null == r1mPrior || !(r1mPrior instanceof org.drip.measure.gaussian.R1MultivariateNormal) || null
			== r1mJoint || !(r1mJoint instanceof org.drip.measure.gaussian.R1MultivariateNormal) || null ==
				r1mPosterior || !(r1mPosterior instanceof org.drip.measure.gaussian.R1MultivariateNormal))
			return null;

		org.drip.measure.gaussian.R1MultivariateNormal r1mnPrior =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mPrior;
		org.drip.measure.gaussian.R1MultivariateNormal r1mnJoint =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mJoint;
		org.drip.measure.gaussian.R1MultivariateNormal r1mnPosterior =
			(org.drip.measure.gaussian.R1MultivariateNormal) r1mPosterior;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("Tau", dblTau);

		jsonResponse.put ("Delta", dblRiskAversion);

		jsonResponse.put ("RiskFreeRate", dblRiskFreeRate);

		jsonResponse.put ("ScopingSet", org.drip.json.parser.Converter.Array (astrAssetID));

		jsonResponse.put ("ScopingExpectedExcessReturns", org.drip.json.parser.Converter.Array
			(r1mnPrior.mean()));

		jsonResponse.put ("ScopingExcessReturnsCovariance", org.drip.json.parser.Converter.Array
			(r1mnPrior.covariance().covarianceMatrix()));

		jsonResponse.put ("ProjectionExpectedExcessReturns", org.drip.json.parser.Converter.Array
			(adblProjectionExpectedExcessReturns));

		jsonResponse.put ("ViewScopingProjectionLoading", org.drip.json.parser.Converter.Array
			(aadblAssetSpaceViewProjection));

		jsonResponse.put ("JointExcessReturnsCovariance", org.drip.json.parser.Converter.Array
			(r1mnJoint.covariance().covarianceMatrix()));

		jsonResponse.put ("PosteriorExcessReturnsCovariance", org.drip.json.parser.Converter.Array
			(r1mnPosterior.covariance().covarianceMatrix()));

		jsonResponse.put ("PriorExpectedExcessReturn", org.drip.json.parser.Converter.Array
			(r1mPrior.mean()));

		jsonResponse.put ("PosteriorExpectedExcessReturn", org.drip.json.parser.Converter.Array
			(r1mPosterior.mean()));

		return jsonResponse;
	}
}
