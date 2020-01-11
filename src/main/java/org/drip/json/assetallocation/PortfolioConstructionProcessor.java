
package org.drip.json.assetallocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>PortfolioConstructionProcessor</i> Sets Up and Executes a JSON Based In/Out Processing Service for
 * Constrained and Unconstrained Portfolio Construction.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json">RFC-4627 Compliant JSON Encoder/Decoder (Parser)</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/assetallocation">JSON Based In/Out Service</a></li>
 *  </ul>
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

		org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl pdp = null;
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
			pdp = new org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
				(astrAssetID, new org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings (1.,
					0.), new org.drip.portfolioconstruction.allocator.EqualityConstraintSettings
						(org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
						java.lang.Double.NaN));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < astrAssetID.length; ++i) {
			if (!pdp.addBound (astrAssetID[i], adblAssetLowerBound[i], adblAssetUpperBound[i])) return null;
		}

		org.drip.portfolioconstruction.allocator.HoldingsAllocation oo = cmva.allocate (pdp, ausp);

		if (null == oo) return null;

		org.drip.portfolioconstruction.asset.Portfolio pf = oo.optimalPortfolio();

		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = pf.assetComponentArray();

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

		org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl pdp = null;
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
			pdp = new org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
				(astrAssetID, new org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings (1.,
					0.), new org.drip.portfolioconstruction.allocator.EqualityConstraintSettings
						(org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT
						| org.drip.portfolioconstruction.allocator.EqualityConstraintSettings.RETURNS_CONSTRAINT,
						dblPortfolioDesignReturn));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < astrAssetID.length; ++i) {
			if (!pdp.addBound (astrAssetID[i], adblAssetLowerBound[i], adblAssetUpperBound[i])) return null;
		}

		org.drip.portfolioconstruction.allocator.HoldingsAllocation oo = cmva.allocate (pdp, ausp);

		if (null == oo) return null;

		org.drip.portfolioconstruction.asset.Portfolio pf = oo.optimalPortfolio();

		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = pf.assetComponentArray();

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
