
package org.drip.service.assetallocation;

import org.drip.measure.bayesian.R1MultivariateConvolutionMetrics;
import org.drip.measure.distribution.MetaRd;
import org.drip.measure.distribution.MetaRdContinuous;
import org.drip.measure.gaussian.R1MultivariateNormal;
import org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.bayesian.BlackLittermanCombinationEngine;
import org.drip.portfolioconstruction.bayesian.BlackLittermanCustomConfidenceOutput;
import org.drip.portfolioconstruction.bayesian.PriorControlSpecification;
import org.drip.portfolioconstruction.bayesian.ProjectionSpecification;
import org.drip.service.jsonparser.Converter;
import org.drip.service.representation.JSONObject;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>BlackLittermanProcessor</i> Sets Up and Executes a JSON Based In/Out Processing Service for the Black
 * 	Litterman Bayesian View Incorporation/Parameter Estimation. It provides the following Functions:
 * <ul>
 * 		<li>JSON Based in/out Bayesian Co-variance/Returns Estimation Thunker</li>
 * </ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/assetallocation/README.md">JSON Based In/Out Service</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanProcessor
{

	/**
	 * JSON Based in/out Bayesian Co-variance/Returns Estimation Thunker
	 * 
	 * @param jsonParameter Bayesian Co-variance/Returns Estimation Parameters
	 * 
	 * @return JSON Bayesian Co-variance/Returns Estimation Response
	 */

	@SuppressWarnings ("unchecked") public static final JSONObject Estimate (
		final JSONObject jsonParameter)
	{
		String[] assetId = Converter.StringArrayEntry (jsonParameter, "AssetSet");

		double[][] assetSpaceViewProjectionMatrix = Converter.DualDoubleArrayEntry (
			jsonParameter,
			"AssetSpaceViewProjection"
		);

		double tau = Double.NaN;
		double riskAversion = Double.NaN;
		double riskFreeRate = Double.NaN;
		R1MultivariateNormal viewDistribution = null;
		BlackLittermanCombinationEngine blackLittermanCombinationEngine = null;
		int viewCount = null == assetSpaceViewProjectionMatrix ? 0 : assetSpaceViewProjectionMatrix.length;
		String[] projectionNameArray = 0 == viewCount ? null : new String[viewCount];

		double[] projectionExpectedExcessReturnsArray = Converter.DoubleArrayEntry (
			jsonParameter,
			"ProjectionExpectedExcessReturns"
		);

		for (int i = 0; i < viewCount ; ++i) {
			projectionNameArray[i] = "PROJECTION #" + i;
		}

		try {
			tau = Converter.DoubleEntry (jsonParameter, "Tau");

			riskAversion = Converter.DoubleEntry (jsonParameter, "Delta");

			riskFreeRate = Converter.DoubleEntry (jsonParameter, "RiskFreeRate");

			viewDistribution = R1MultivariateNormal.Standard (
				new MetaRd (projectionNameArray),
				projectionExpectedExcessReturnsArray,
				Converter.DualDoubleArrayEntry (jsonParameter, "ProjectionExcessReturnsCovariance")
			);

			blackLittermanCombinationEngine = new BlackLittermanCombinationEngine (
				ForwardReverseHoldingsAllocation.Reverse (
					Portfolio.Standard (
						assetId,
						Converter.DoubleArrayEntry (jsonParameter, "AssetEquilibriumWeight")
					),
					Converter.DualDoubleArrayEntry (jsonParameter, "AssetExcessReturnsCovariance"),
					riskAversion
				),
				new PriorControlSpecification (false, riskFreeRate, tau),
				new ProjectionSpecification (viewDistribution, assetSpaceViewProjectionMatrix)
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		BlackLittermanCustomConfidenceOutput blackLittermanCustomConfidenceOutput =
			blackLittermanCombinationEngine.customConfidenceRun();

		if (null == blackLittermanCustomConfidenceOutput) {
			return null;
		}

		R1MultivariateConvolutionMetrics jointPosteriorConvolutionMetrics =
			blackLittermanCustomConfidenceOutput.jointPosteriorMetrics();

		MetaRdContinuous priorMultivariate = jointPosteriorConvolutionMetrics.priorDistribution();

		MetaRdContinuous posteriorMultivariate = jointPosteriorConvolutionMetrics.posteriorDistribution();

		MetaRdContinuous jointPosteriorMultivariate = jointPosteriorConvolutionMetrics.jointDistribution();

		if (null == priorMultivariate || !(priorMultivariate instanceof R1MultivariateNormal) ||
			null == jointPosteriorMultivariate ||
				!(jointPosteriorMultivariate instanceof R1MultivariateNormal) ||
			null == posteriorMultivariate || !(posteriorMultivariate instanceof R1MultivariateNormal))
		{
			return null;
		}

		R1MultivariateNormal priorMultivariateNormal = (R1MultivariateNormal) priorMultivariate;

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("Tau", tau);

		jsonResponse.put ("Delta", riskAversion);

		jsonResponse.put ("RiskFreeRate", riskFreeRate);

		jsonResponse.put ("ScopingSet", Converter.Array (assetId));

		jsonResponse.put ("ScopingExpectedExcessReturns", Converter.Array (priorMultivariateNormal.mean()));

		jsonResponse.put (
			"ScopingExcessReturnsCovariance",
			Converter.Array (priorMultivariateNormal.covariance().covarianceMatrix())
		);

		jsonResponse.put (
			"ProjectionExpectedExcessReturns",
			Converter.Array (projectionExpectedExcessReturnsArray)
		);

		jsonResponse.put ("ViewScopingProjectionLoading", Converter.Array (assetSpaceViewProjectionMatrix));

		jsonResponse.put (
			"JointExcessReturnsCovariance",
			Converter.Array (
				((R1MultivariateNormal) jointPosteriorMultivariate).covariance().covarianceMatrix()
			)
		);

		jsonResponse.put (
			"PosteriorExcessReturnsCovariance",
			Converter.Array (((R1MultivariateNormal) posteriorMultivariate).covariance().covarianceMatrix())
		);

		jsonResponse.put ("PriorExpectedExcessReturn", Converter.Array (priorMultivariate.mean()));

		jsonResponse.put ("PosteriorExpectedExcessReturn", Converter.Array (posteriorMultivariate.mean()));

		return jsonResponse;
	}
}
