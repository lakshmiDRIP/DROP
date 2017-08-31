
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
 * PortfolioConstructionProcessor Sets Up and Executes a JSON Based In/Out Processing Service for Constrained
 * 	and Unconstrained Portfolio Construction.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioConstructionProcessor {

	/**
	 * JSON Based in/out Budget Constrained Mean Variance Allocation Thunker
	 * 
	 * @param jsonParameter Budget Constrained Mean Variance Allocation Parameters
	 * 
	 * @return JSON Budget Constrained Mean Variance Allocation Response
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONObject
		BudgetConstrainedAllocator (
			final org.drip.json.simple.JSONObject jsonParameter)
	{
		java.lang.String[] astrAssetID = org.drip.json.parser.Converter.StringArrayEntry (jsonParameter,
			"AssetSet");

		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters pdp = null;
		int iNumAsset = null == astrAssetID ? 0 : astrAssetID.length;
		double[] adblAssetLowerBound = new double[iNumAsset];
		double[] adblAssetUpperBound = new double[iNumAsset];

		if (0 == iNumAsset) return null;

		double[] adblAssetExpectedReturns = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"AssetExpectedReturns");

		double[][] aadblAssetReturnsCovariance = org.drip.json.parser.Converter.DualDoubleArrayEntry
			(jsonParameter, "AssetReturnsCovariance");

		for (int i = 0; i < iNumAsset; ++i) {
			try {
				adblAssetLowerBound[i] = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
					astrAssetID[i] + "::LowerBound");

				adblAssetUpperBound[i] = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
					astrAssetID[i] + "::UpperBound");
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp =
			org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties.FromMultivariateMetrics
				(org.drip.measure.statistics.MultivariateMoments.Standard (astrAssetID,
					adblAssetExpectedReturns, aadblAssetReturnsCovariance));

		if (null == ausp) return null;

		org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer cmva = new
			org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer (null,
				org.drip.function.rdtor1descent.LineStepEvolutionControl.NocedalWrightStrongWolfe (false));

		try {
			pdp = new org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
				(astrAssetID, new org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings (1.,
					0.), new org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings
						(org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
						java.lang.Double.NaN));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < astrAssetID.length; ++i) {
			if (!pdp.addBound (astrAssetID[i], adblAssetLowerBound[i], adblAssetUpperBound[i])) return null;
		}

		org.drip.portfolioconstruction.allocator.OptimizationOutput oo = cmva.allocate (pdp, ausp);

		if (null == oo) return null;

		org.drip.portfolioconstruction.asset.Portfolio pf = oo.optimalPortfolio();

		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = pf.assets();

		if (null == aAC || aAC.length != iNumAsset) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("AssetSet", org.drip.json.parser.Converter.Array (astrAssetID));

		jsonResponse.put ("AssetExpectedReturns", org.drip.json.parser.Converter.Array
			(adblAssetExpectedReturns));

		jsonResponse.put ("AssetReturnsCovariance", org.drip.json.parser.Converter.Array
			(aadblAssetReturnsCovariance));

		for (int i = 0; i < adblAssetExpectedReturns.length; ++i) {
			jsonResponse.put (astrAssetID[i] + "::LowerBound", adblAssetLowerBound[i]);

			jsonResponse.put (astrAssetID[i] + "::UpperBound", adblAssetUpperBound[i]);
		}

		for (int i = 0; i < astrAssetID.length; ++i)
			jsonResponse.put (aAC[i].id() + "::WEIGHT", aAC[i].amount());

		jsonResponse.put ("PortfolioNotional", pf.notional());

		try {
			jsonResponse.put ("PortfolioExpectedReturn", pf.expectedReturn (ausp));

			jsonResponse.put ("PortfolioStandardDeviation", java.lang.Math.sqrt (pf.variance (ausp)));

			return jsonResponse;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * JSON Based in/out Returns Constrained Mean Variance Allocation Thunker
	 * 
	 * @param jsonParameter Returns Constrained Mean Variance Allocation Parameters
	 * 
	 * @return JSON Returns Constrained Mean Variance Allocation Response
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONObject
		ReturnsConstrainedAllocator (
			final org.drip.json.simple.JSONObject jsonParameter)
	{
    	java.lang.String[] astrAssetID = org.drip.json.parser.Converter.StringArrayEntry (jsonParameter,
			"AssetSet");

		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters pdp = null;
		int iNumAsset = null == astrAssetID ? 0 : astrAssetID.length;
		double dblPortfolioDesignReturn = java.lang.Double.NaN;
		double[] adblAssetLowerBound = new double[iNumAsset];
		double[] adblAssetUpperBound = new double[iNumAsset];

		if (0 == iNumAsset) return null;

		double[] adblAssetReturnsMean = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"AssetReturnsMean");

		double[][] aadblAssetReturnsCovariance = org.drip.json.parser.Converter.DualDoubleArrayEntry
			(jsonParameter, "AssetReturnsCovariance");

		try {
			dblPortfolioDesignReturn = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
				"PortfolioDesignReturn");

			for (int i = 0; i < iNumAsset; ++i) {
				adblAssetLowerBound[i] = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
					astrAssetID[i] + "::LowerBound");

				adblAssetUpperBound[i] = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
					astrAssetID[i] + "::UpperBound");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp =
			org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties.FromMultivariateMetrics
				(org.drip.measure.statistics.MultivariateMoments.Standard (astrAssetID,
					adblAssetReturnsMean, aadblAssetReturnsCovariance));

		if (null == ausp) return null;

		org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer cmva = new
			org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer (null, null);

		try {
			pdp = new org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
				(astrAssetID, new org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings (1.,
					0.), new org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings
						(org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT
						| org.drip.portfolioconstruction.allocator.PortfolioEqualityConstraintSettings.RETURNS_CONSTRAINT,
						dblPortfolioDesignReturn));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < astrAssetID.length; ++i) {
			if (!pdp.addBound (astrAssetID[i], adblAssetLowerBound[i], adblAssetUpperBound[i])) return null;
		}

		org.drip.portfolioconstruction.allocator.OptimizationOutput oo = cmva.allocate (pdp, ausp);

		if (null == oo) return null;

		org.drip.portfolioconstruction.asset.Portfolio pf = oo.optimalPortfolio();

		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = pf.assets();

		if (null == aAC || aAC.length != iNumAsset) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("AssetSet", org.drip.json.parser.Converter.Array (astrAssetID));

		jsonResponse.put ("AssetReturnsMean", org.drip.json.parser.Converter.Array (adblAssetReturnsMean));

		jsonResponse.put ("AssetReturnsCovariance", org.drip.json.parser.Converter.Array
			(aadblAssetReturnsCovariance));

		for (int i = 0; i < adblAssetReturnsMean.length; ++i) {
			jsonResponse.put (astrAssetID[i] + "::LowerBound", adblAssetLowerBound[i]);

			jsonResponse.put (astrAssetID[i] + "::UpperBound", adblAssetUpperBound[i]);
		}

		for (int i = 0; i < astrAssetID.length; ++i)
			jsonResponse.put (aAC[i].id() + "::WEIGHT", aAC[i].amount());

		jsonResponse.put ("PortfolioNotional", pf.notional());

		try {
			jsonResponse.put ("PortfolioDesignReturn", dblPortfolioDesignReturn);

			jsonResponse.put ("PortfolioExpectedReturn", pf.expectedReturn (ausp));

			jsonResponse.put ("PortfolioStandardDeviation", java.lang.Math.sqrt (pf.variance (ausp)));

			return jsonResponse;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
