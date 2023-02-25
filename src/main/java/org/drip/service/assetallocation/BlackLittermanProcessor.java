
package org.drip.service.assetallocation;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>BlackLittermanProcessor</i> Sets Up and Executes a JSON Based In/Out Processing Service for the Black
 * 	Litterman Bayesian View Incorporation/Parameter Estimation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/assetallocation">JSON Based In/Out Service</a></li>
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

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONObject Estimate (
		final org.drip.service.representation.JSONObject jsonParameter)
	{
		java.lang.String[] astrAssetID = org.drip.service.jsonparser.Converter.StringArrayEntry (jsonParameter,
			"AssetSet");

		double[][] aadblAssetSpaceViewProjection = org.drip.service.jsonparser.Converter.DualDoubleArrayEntry
			(jsonParameter, "AssetSpaceViewProjection");

		double dblTau = java.lang.Double.NaN;
		double dblRiskAversion = java.lang.Double.NaN;
		double dblRiskFreeRate = java.lang.Double.NaN;
		org.drip.measure.gaussian.R1MultivariateNormal viewDistribution = null;
		org.drip.portfolioconstruction.bayesian.BlackLittermanCombinationEngine blce = null;
		int iNumView = null == aadblAssetSpaceViewProjection ? 0 : aadblAssetSpaceViewProjection.length;
		java.lang.String[] astrProjectionName = 0 == iNumView? null : new java.lang.String[iNumView];

		double[] adblAssetEquilibriumWeight = org.drip.service.jsonparser.Converter.DoubleArrayEntry (jsonParameter,
			"AssetEquilibriumWeight");

		double[][] aadblAssetExcessReturnsCovariance = org.drip.service.jsonparser.Converter.DualDoubleArrayEntry
			(jsonParameter, "AssetExcessReturnsCovariance");

		double[] adblProjectionExpectedExcessReturns = org.drip.service.jsonparser.Converter.DoubleArrayEntry
			(jsonParameter, "ProjectionExpectedExcessReturns");

		double[][] aadblProjectionExcessReturnsCovariance =
			org.drip.service.jsonparser.Converter.DualDoubleArrayEntry (jsonParameter,
				"ProjectionExcessReturnsCovariance");

		for (int i = 0; i < iNumView ; ++i)
			astrProjectionName[i] = "PROJECTION #" + i;

		try {
			dblTau = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter, "Tau");

			dblRiskAversion = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter, "Delta");

			dblRiskFreeRate = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter, "RiskFreeRate");

			viewDistribution = org.drip.measure.gaussian.R1MultivariateNormal.Standard (new
				org.drip.measure.continuous.MultivariateMeta (astrProjectionName),
					adblProjectionExpectedExcessReturns, aadblProjectionExcessReturnsCovariance);

			blce = new org.drip.portfolioconstruction.bayesian.BlackLittermanCombinationEngine
				(org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation.Reverse
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

		org.drip.measure.bayesian.R1MultivariateConvolutionMetrics jpm = blo.jointPosteriorMetrics();

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

		org.drip.service.representation.JSONObject jsonResponse = new org.drip.service.representation.JSONObject();

		jsonResponse.put ("Tau", dblTau);

		jsonResponse.put ("Delta", dblRiskAversion);

		jsonResponse.put ("RiskFreeRate", dblRiskFreeRate);

		jsonResponse.put ("ScopingSet", org.drip.service.jsonparser.Converter.Array (astrAssetID));

		jsonResponse.put ("ScopingExpectedExcessReturns", org.drip.service.jsonparser.Converter.Array
			(r1mnPrior.mean()));

		jsonResponse.put ("ScopingExcessReturnsCovariance", org.drip.service.jsonparser.Converter.Array
			(r1mnPrior.covariance().covarianceMatrix()));

		jsonResponse.put ("ProjectionExpectedExcessReturns", org.drip.service.jsonparser.Converter.Array
			(adblProjectionExpectedExcessReturns));

		jsonResponse.put ("ViewScopingProjectionLoading", org.drip.service.jsonparser.Converter.Array
			(aadblAssetSpaceViewProjection));

		jsonResponse.put ("JointExcessReturnsCovariance", org.drip.service.jsonparser.Converter.Array
			(r1mnJoint.covariance().covarianceMatrix()));

		jsonResponse.put ("PosteriorExcessReturnsCovariance", org.drip.service.jsonparser.Converter.Array
			(r1mnPosterior.covariance().covarianceMatrix()));

		jsonResponse.put ("PriorExpectedExcessReturn", org.drip.service.jsonparser.Converter.Array
			(r1mPrior.mean()));

		jsonResponse.put ("PosteriorExpectedExcessReturn", org.drip.service.jsonparser.Converter.Array
			(r1mPosterior.mean()));

		return jsonResponse;
	}
}
