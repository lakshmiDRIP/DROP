
package org.drip.service.assetallocation;

import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl;
import org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer;
import org.drip.portfolioconstruction.allocator.CustomRiskUtilitySettings;
import org.drip.portfolioconstruction.allocator.EqualityConstraintSettings;
import org.drip.portfolioconstruction.allocator.HoldingsAllocation;
import org.drip.portfolioconstruction.asset.AssetComponent;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
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
 * <i>PortfolioConstructionProcessor</i> Sets Up and Executes a JSON Based In/Out Processing Service for
 * 	Constrained and Unconstrained Portfolio Construction. It provides the following Functions:
 * <ul>
 * 		<li>JSON Based in/out Budget Constrained Mean Variance Allocation Thunker</li>
 * 		<li>JSON Based in/out Returns Constrained Mean Variance Allocation Thunker</li>
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

public class PortfolioConstructionProcessor
{

	/**
	 * JSON Based in/out Budget Constrained Mean Variance Allocation Thunker
	 * 
	 * @param jsonParameter Budget Constrained Mean Variance Allocation Parameters
	 * 
	 * @return JSON Budget Constrained Mean Variance Allocation Response
	 */

	@SuppressWarnings ("unchecked") public static final JSONObject BudgetConstrainedAllocator (
		final JSONObject jsonParameter)
	{
		String[] assetIdArray = Converter.StringArrayEntry (jsonParameter, "AssetSet");

		BoundedHoldingsAllocationControl boundedHoldingsAllocationControl = null;
		int assetCount = null == assetIdArray ? 0 : assetIdArray.length;
		double[] assetUpperBoundArray = new double[assetCount];
		double[] assetLowerBoundArray = new double[assetCount];

		if (0 == assetCount) {
			return null;
		}

		double[] assetExpectedReturnsArray = Converter.DoubleArrayEntry (
			jsonParameter,
			"AssetExpectedReturns"
		);

		double[][] assetReturnsCovarianceMatrix = Converter.DualDoubleArrayEntry (
			jsonParameter,
			"AssetReturnsCovariance"
		);

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex) {
			try {
				assetLowerBoundArray[assetIndex] = Converter.DoubleEntry (
					jsonParameter,
					assetIdArray[assetIndex] + "::LowerBound"
				);

				assetUpperBoundArray[assetIndex] = Converter.DoubleEntry (
					jsonParameter,
					assetIdArray[assetIndex] + "::UpperBound"
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIdArray,
					assetExpectedReturnsArray,
					assetReturnsCovarianceMatrix
				)
			);

		if (null == assetUniverseStatisticalProperties) {
			return null;
		}

		try {
			boundedHoldingsAllocationControl = new BoundedHoldingsAllocationControl (
				assetIdArray,
				new CustomRiskUtilitySettings (1., 0.),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
					Double.NaN
				)
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int assetIndex = 0; assetIndex < assetIdArray.length; ++assetIndex) {
			if (!boundedHoldingsAllocationControl.addBound (
				assetIdArray[assetIndex],
				assetLowerBoundArray[assetIndex],
				assetUpperBoundArray[assetIndex]
			))
			{
				return null;
			}
		}

		HoldingsAllocation holdingsAllocation = new ConstrainedMeanVarianceOptimizer (
			null,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (false)
		).allocate (
			boundedHoldingsAllocationControl,
			assetUniverseStatisticalProperties
		);

		if (null == holdingsAllocation) {
			return null;
		}

		Portfolio portfolio = holdingsAllocation.optimalPortfolio();

		AssetComponent[] assetComponentArray = portfolio.assetComponentArray();

		if (null == assetComponentArray || assetComponentArray.length != assetCount) {
			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("AssetSet", Converter.Array (assetIdArray));

		jsonResponse.put ("AssetExpectedReturns", Converter.Array (assetExpectedReturnsArray));

		jsonResponse.put ("AssetReturnsCovariance", Converter.Array (assetReturnsCovarianceMatrix));

		for (int assetIndex = 0; assetIndex < assetExpectedReturnsArray.length; ++assetIndex) {
			jsonResponse.put (assetIdArray[assetIndex] + "::LowerBound", assetLowerBoundArray[assetIndex]);

			jsonResponse.put (assetIdArray[assetIndex] + "::UpperBound", assetUpperBoundArray[assetIndex]);
		}

		for (int assetIndex = 0; assetIndex < assetIdArray.length; ++assetIndex) {
			jsonResponse.put (
				assetComponentArray[assetIndex].id() + "::WEIGHT",
				assetComponentArray[assetIndex].amount()
			);
		}

		jsonResponse.put ("PortfolioNotional", portfolio.notional());

		try {
			jsonResponse.put (
				"PortfolioExpectedReturn",
				portfolio.expectedReturn (assetUniverseStatisticalProperties)
			);

			jsonResponse.put (
				"PortfolioStandardDeviation",
				Math.sqrt (portfolio.variance (assetUniverseStatisticalProperties))
			);

			return jsonResponse;
		} catch (Exception e) {
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

	@SuppressWarnings ("unchecked") public static final JSONObject ReturnsConstrainedAllocator (
		final JSONObject jsonParameter)
	{
    	String[] assetIdArray = Converter.StringArrayEntry (jsonParameter, "AssetSet");

		BoundedHoldingsAllocationControl boundedHoldingsAllocationControl = null;
		int assetCount = null == assetIdArray ? 0 : assetIdArray.length;
		double[] assetUpperBoundArray = new double[assetCount];
		double[] assetLowerBoundArray = new double[assetCount];
		double portfolioDesignReturn = Double.NaN;

		if (0 == assetCount) {
			return null;
		}

		double[] assetReturnsMeanArray = Converter.DoubleArrayEntry (jsonParameter, "AssetReturnsMean");

		double[][] assetReturnsCovarianceMatrix =
			Converter.DualDoubleArrayEntry (jsonParameter, "AssetReturnsCovariance");

		try {
			portfolioDesignReturn = Converter.DoubleEntry (jsonParameter, "PortfolioDesignReturn");

			for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex) {
				assetLowerBoundArray[assetIndex] = Converter.DoubleEntry (
					jsonParameter,
					assetIdArray[assetIndex] + "::LowerBound"
				);

				assetUpperBoundArray[assetIndex] = Converter.DoubleEntry (
					jsonParameter,
					assetIdArray[assetIndex] + "::UpperBound"
				);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIdArray,
					assetReturnsMeanArray,
					assetReturnsCovarianceMatrix
				)
			);

		if (null == assetUniverseStatisticalProperties) {
			return null;
		}

		try {
			boundedHoldingsAllocationControl = new BoundedHoldingsAllocationControl (
				assetIdArray,
				new CustomRiskUtilitySettings (1., 0.),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT |
						EqualityConstraintSettings.RETURNS_CONSTRAINT,
					portfolioDesignReturn
				)
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int assetIndex = 0; assetIndex < assetIdArray.length; ++assetIndex) {
			if (!boundedHoldingsAllocationControl.addBound (
				assetIdArray[assetIndex],
				assetLowerBoundArray[assetIndex],
				assetUpperBoundArray[assetIndex]
			))
			{
				return null;
			}
		}

		HoldingsAllocation holdingsAllocation = new ConstrainedMeanVarianceOptimizer (
			null,
			null
		).allocate (
			boundedHoldingsAllocationControl,
			assetUniverseStatisticalProperties
		);

		if (null == holdingsAllocation) {
			return null;
		}

		Portfolio portfolio = holdingsAllocation.optimalPortfolio();

		AssetComponent[] assetComponentArray = portfolio.assetComponentArray();

		if (null == assetComponentArray || assetComponentArray.length != assetCount) {
			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("AssetSet", Converter.Array (assetIdArray));

		jsonResponse.put ("AssetReturnsMean", Converter.Array (assetReturnsMeanArray));

		jsonResponse.put ("AssetReturnsCovariance", Converter.Array (assetReturnsCovarianceMatrix));

		for (int assetIndex = 0; assetIndex < assetReturnsMeanArray.length; ++assetIndex) {
			jsonResponse.put (assetIdArray[assetIndex] + "::LowerBound", assetLowerBoundArray[assetIndex]);

			jsonResponse.put (assetIdArray[assetIndex] + "::UpperBound", assetUpperBoundArray[assetIndex]);
		}

		for (int assetIndex = 0; assetIndex < assetIdArray.length; ++assetIndex) {
			jsonResponse.put (
				assetComponentArray[assetIndex].id() + "::WEIGHT",
				assetComponentArray[assetIndex].amount()
			);
		}

		jsonResponse.put ("PortfolioNotional", portfolio.notional());

		try {
			jsonResponse.put ("PortfolioDesignReturn", portfolioDesignReturn);

			jsonResponse.put (
				"PortfolioExpectedReturn",
				portfolio.expectedReturn (assetUniverseStatisticalProperties)
			);

			jsonResponse.put (
				"PortfolioStandardDeviation",
				Math.sqrt (portfolio.variance (assetUniverseStatisticalProperties))
			);

			return jsonResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
