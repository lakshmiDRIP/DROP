
package org.drip.json.assetallocation;

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
 * <i>BlackLittermanProcessor</i> Sets Up and Executes a JSON Based In/Out Processing Service for the Black
 * Litterman Bayesian View Incorporation/Parameter Estimation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json">JSON</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/assetallocation">Asset Allocation</a></li>
 *  </ul>
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
