
package org.drip.xva.netting;

import org.drip.analytics.date.JulianDate;
import org.drip.exposure.universe.MarketPath;
import org.drip.exposure.universe.MarketVertex;

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
 * <i>CreditDebtGroupPath</i> rolls up the Path Realizations of the Sequence in a Single Path Projection Run
 * over Multiple Collateral Hypothecation Groups onto a Single Credit/Debt Netting Group - the Purpose being
 * to calculate Credit Valuation Adjustments. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/netting/README.md">Credit/Debt/Funding Netting Groups</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class CreditDebtGroupPath
{
	private MarketPath _marketPath = null;
	private CollateralGroupPath[] _collateralGroupPathArray = null;

	protected CreditDebtGroupPath (
		final CollateralGroupPath[] collateralGroupPathArray,
		final MarketPath marketPath)
		throws Exception
	{
		if (null == (_collateralGroupPathArray = collateralGroupPathArray) ||
			null == (_marketPath = marketPath)) {
			throw new Exception ("CreditDebtGroupPath Constructor => Invalid Inputs");
		}

		int collateralGroupCount = _collateralGroupPathArray.length;

		if (0 == collateralGroupCount) {
			throw new Exception ("CreditDebtGroupPath Constructor => Invalid Inputs");
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			if (null == _collateralGroupPathArray[collateralGroupIndex]) {
				throw new Exception ("CreditDebtGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of the Position Hypothecation Group Trajectory Paths
	 * 
	 * @return Array of the Position Hypothecation Group Trajectory Paths
	 */

	public CollateralGroupPath[] collateralGroupPaths()
	{
		return _collateralGroupPathArray;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public JulianDate[] vertexDates()
	{
		return _collateralGroupPathArray[0].vertexDates();
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposures
	 * 
	 * @return The Array of Vertex Collateralized Exposures
	 */

	public double[] vertexCollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralizedExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexCollateralizedExposureArray[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralGroupVertexCollateralizedExposureArray =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				vertexCollateralizedExposureArray[vertexIndex] +=
					collateralGroupVertexCollateralizedExposureArray[vertexIndex];
			}
		}

		return vertexCollateralizedExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Exposure PV
	 */

	public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralizedExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexCollateralizedExposurePVArray[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralGroupVertexCollateralizedExposurePVArray =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				vertexCollateralizedExposurePVArray[vertexIndex] +=
					collateralGroupVertexCollateralizedExposurePVArray[vertexIndex];
			}
		}

		return vertexCollateralizedExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Positive Exposures
	 * 
	 * @return The Array of Vertex Collateralized Positive Exposures
	 */

	public double[] vertexCollateralizedPositiveExposure()
	{
		double[] vertexCollateralizedPositiveExposureArray = vertexCollateralizedExposure();

		int vertexCount = vertexCollateralizedPositiveExposureArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. > vertexCollateralizedPositiveExposureArray[vertexIndex])
				vertexCollateralizedPositiveExposureArray[vertexIndex] = 0.;
		}

		return vertexCollateralizedPositiveExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Positive Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Positive Exposures PV
	 */

	public double[] vertexCollateralizedPositiveExposurePV()
	{
		double[] vertexCollateralizedPositiveExposurePVArray = vertexCollateralizedExposurePV();

		int vertexCount = vertexCollateralizedPositiveExposurePVArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. > vertexCollateralizedPositiveExposurePVArray[vertexIndex])
				vertexCollateralizedPositiveExposurePVArray[vertexIndex] = 0.;
		}

		return vertexCollateralizedPositiveExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Negative Exposures
	 * 
	 * @return The Array of Vertex Collateralized Negative Exposures
	 */

	public double[] vertexCollateralizedNegativeExposure()
	{
		double[] vertexCollateralizedNegativeExposureArray = vertexCollateralizedExposure();

		int vertexCount = vertexCollateralizedNegativeExposureArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. < vertexCollateralizedNegativeExposureArray[vertexIndex])
				vertexCollateralizedNegativeExposureArray[vertexIndex] = 0.;
		}

		return vertexCollateralizedNegativeExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Negative Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Negative Exposure PV
	 */

	public double[] vertexCollateralizedNegativeExposurePV()
	{
		double[] vertexCollateralizedNegativeExposurePVArray = vertexCollateralizedExposurePV();

		int vertexCount = vertexCollateralizedNegativeExposurePVArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. < vertexCollateralizedNegativeExposurePVArray[vertexIndex])
				vertexCollateralizedNegativeExposurePVArray[vertexIndex] = 0.;
		}

		return vertexCollateralizedNegativeExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Exposures
	 */

	public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexUncollateralizedExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexUncollateralizedExposureArray[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralGroupVertexUncollateralizedExposureArray =
				_collateralGroupPathArray[collateralGroupIndex].vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				vertexUncollateralizedExposureArray[vertexIndex] +=
					collateralGroupVertexUncollateralizedExposureArray[vertexIndex];
			}
		}

		return vertexUncollateralizedExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Exposure PV
	 */

	public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexUncollateralizedExposurePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexUncollateralizedExposurePVArray[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralGroupVertexUncollateralizedExposurePVArray =
				_collateralGroupPathArray[collateralGroupIndex].vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				vertexUncollateralizedExposurePVArray[vertexIndex] +=
					collateralGroupVertexUncollateralizedExposurePVArray[vertexIndex];
			}
		}

		return vertexUncollateralizedExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Positive Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Positive Exposures
	 */

	public double[] vertexUncollateralizedPositiveExposure()
	{
		double[] vertexUncollateralizedPositiveExposureArray = vertexUncollateralizedExposure();

		int vertexCount = vertexUncollateralizedPositiveExposureArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. > vertexUncollateralizedPositiveExposureArray[vertexIndex])
				vertexUncollateralizedPositiveExposureArray[vertexIndex] = 0.;
		}

		return vertexUncollateralizedPositiveExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Positive Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Positive Exposure PV
	 */

	public double[] vertexUncollateralizedPositiveExposurePV()
	{
		double[] vertexUncollateralizedPositiveExposurePVArray = vertexUncollateralizedExposurePV();

		int vertexCount = vertexUncollateralizedPositiveExposurePVArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. > vertexUncollateralizedPositiveExposurePVArray[vertexIndex])
				vertexUncollateralizedPositiveExposurePVArray[vertexIndex] = 0.;
		}

		return vertexUncollateralizedPositiveExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Negative Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Negative Exposures
	 */

	public double[] vertexUncollateralizedNegativeExposure()
	{
		double[] vertexUncollateralizedNegativeExposureArray = vertexUncollateralizedExposure();

		int vertexCount = vertexUncollateralizedNegativeExposureArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. < vertexUncollateralizedNegativeExposureArray[vertexIndex])
				vertexUncollateralizedNegativeExposureArray[vertexIndex] = 0.;
		}

		return vertexUncollateralizedNegativeExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Negative Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Negative Exposure PV
	 */

	public double[] vertexUncollateralizedNegativeExposurePV()
	{
		double[] vertexUncollateralizedNegativeExposurePVArray = vertexUncollateralizedExposurePV();

		int vertexCount = vertexUncollateralizedNegativeExposurePVArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (0. < vertexUncollateralizedNegativeExposurePVArray[vertexIndex])
				vertexUncollateralizedNegativeExposurePVArray[vertexIndex] = 0.;
		}

		return vertexUncollateralizedNegativeExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure
	 * 
	 * @return The Array of Vertex Credit Exposure
	 */

	public double[] vertexCreditExposure()
	{
		return vertexCollateralizedPositiveExposure();
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure PV
	 * 
	 * @return The Array of Vertex Credit Exposure PV
	 */

	public double[] vertexCreditExposurePV()
	{
		return vertexCollateralizedPositiveExposurePV();
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposure
	 * 
	 * @return The Array of Vertex Debt Exposure
	 */

	public double[] vertexDebtExposure()
	{
		return vertexCollateralizedNegativeExposure();
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposure PV
	 * 
	 * @return The Array of Vertex Debt Exposure PV
	 */

	public double[] vertexDebtExposurePV()
	{
		return vertexCollateralizedNegativeExposurePV();
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposure
	 * 
	 * @return The Array of Vertex Funding Exposure
	 */

	public double[] vertexFundingExposure()
	{
		return vertexCollateralizedExposure();
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposure PV
	 * 
	 * @return The Array of Vertex Funding Exposure PV
	 */

	public double[] vertexFundingExposurePV()
	{
		return vertexCollateralizedExposurePV();
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances
	 * 
	 * @return The Array of Vertex Collateral Balances
	 */

	public double[] vertexCollateralBalance()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralBalanceArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexCollateralBalanceArray[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralVertexGroupCollateralBalanceArray =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralBalance();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				vertexCollateralBalanceArray[vertexIndex] +=
					collateralVertexGroupCollateralBalanceArray[vertexIndex];
			}
		}

		return vertexCollateralBalanceArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances PV
	 * 
	 * @return The Array of Vertex Collateral Balances PV
	 */

	public double[] vertexCollateralBalancePV()
	{
		int vertexCount = vertexDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] vertexCollateralBalancePVArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexCollateralBalancePVArray[vertexIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralVertexGroupCollateralBalancePVArray =
				_collateralGroupPathArray[collateralGroupIndex].vertexCollateralBalancePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				vertexCollateralBalancePVArray[vertexIndex] +=
					collateralVertexGroupCollateralBalancePVArray[vertexIndex];
			}
		}

		return vertexCollateralBalancePVArray;
	}

	/**
	 * Compute Period-wise Path Collateral Spread 01
	 * 
	 * @return The Period-wise Path Collateral Spread 01
	 */

	public double[] periodCollateralSpread01()
	{
		int vertexCount = vertexDates().length;

		int periodCount = vertexCount - 1;
		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] periodCollateralSpread01Array = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
			periodCollateralSpread01Array[periodIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralPeriodCollateralSpread01Array =
				_collateralGroupPathArray[collateralGroupIndex].periodCollateralSpread01();

			for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
				periodCollateralSpread01Array[periodIndex] +=
					collateralPeriodCollateralSpread01Array[periodIndex];
			}
		}

		return periodCollateralSpread01Array;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 */

	public double[] periodCollateralValueAdjustment()
	{
		int vertexCount = vertexDates().length;

		int periodCount = vertexCount - 1;
		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] periodCollateralValueAdjustmentArray = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
			periodCollateralValueAdjustmentArray[periodIndex] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex) {
			double[] collateralPeriodCollateralValueAdjustmentArray =
				_collateralGroupPathArray[collateralGroupIndex].periodCollateralValueAdjustment();

			for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
				periodCollateralValueAdjustmentArray[periodIndex] +=
					collateralPeriodCollateralValueAdjustmentArray[periodIndex];
			}
		}

		return periodCollateralValueAdjustmentArray;
	}

	/**
	 * Compute Path Unilateral Credit Adjustment
	 * 
	 * @return The Path Unilateral Credit Adjustment
	 */

	public double unilateralCreditAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePVArray = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePVArray.length;
		double unilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexCreditExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate());

			double periodIntegrandEnd = vertexCreditExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].client().seniorRecoveryRate());

			unilateralCreditAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return unilateralCreditAdjustment;
	}

	/**
	 * Compute Path Bilateral Credit Adjustment
	 * 
	 * @return The Path Bilateral Credit Adjustment
	 */

	public double bilateralCreditAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePVArray = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePVArray.length;
		double bilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexCreditExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = vertexCreditExposurePVArray[vertexIndex] *
				(1. -marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			bilateralCreditAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return bilateralCreditAdjustment;
	}

	/**
	 * Compute Path Contra-Liability Credit Adjustment
	 * 
	 * @return The Path Contra-Liability Credit Adjustment
	 */

	public double contraLiabilityCreditAdjustment()
	{
		return bilateralCreditAdjustment() - unilateralCreditAdjustment();
	}

	/**
	 * Compute Path Unilateral Debt Adjustment
	 * 
	 * @return The Path Unilateral Debt Adjustment
	 */

	public double unilateralDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double unilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. -marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. -marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			unilateralDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Debt Adjustment
	 * 
	 * @return The Path Bilateral Debt Adjustment
	 */

	public double bilateralDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double bilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. -marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. -marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			bilateralDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability()
			);
		}

		return bilateralDebtAdjustment;
	}

	/**
	 * Compute Path Contra-Asset Debt Adjustment
	 * 
	 * @return The Path Contra-Asset Debt Adjustment
	 */

	public double contraAssetDebtAdjustment()
	{
		return bilateralDebtAdjustment() - unilateralDebtAdjustment();
	}

	/**
	 * Compute Path Symmetric Funding Value Spread 01
	 * 
	 * @return The Path Symmetric Funding Value Spread 01
	 */

	public double symmetricFundingValueSpread01()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCollateralizedExposurePVArray = vertexCollateralizedExposurePV();

		int vertexCount = vertexCollateralizedExposurePVArray.length;
		double symmetricFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			symmetricFundingValueSpread01 = 0.5 * (
				vertexCollateralizedExposurePVArray[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability() +
				vertexCollateralizedExposurePVArray[vertexIndex] *
				marketVertexArray[vertexIndex].dealer().survivalProbability()
			) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return symmetricFundingValueSpread01;
	}

	/**
	 * Compute Path Unilateral Funding Value Spread 01
	 * 
	 * @return The Path Unilateral Funding Value Spread 01
	 */

	public double unilateralFundingValueSpread01()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePVArray = vertexFundingExposurePV();

		int vertexCount = vertexFundingExposurePVArray.length;
		double unilateralFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexFundingExposurePVArray[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePVArray[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability();

			unilateralFundingValueSpread01 = 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return unilateralFundingValueSpread01;
	}

	/**
	 * Compute Path Bilateral Funding Value Spread 01
	 * 
	 * @return The Path Bilateral Funding Value Spread 01
	 */

	public double bilateralFundingValueSpread01()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePVArray = vertexFundingExposurePV();

		int vertexCount = vertexFundingExposurePVArray.length;
		double bilateralFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexFundingExposurePVArray[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability() *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePVArray[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability() *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			bilateralFundingValueSpread01 = 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return bilateralFundingValueSpread01;
	}

	/**
	 * Compute Path Unilateral Funding Debt Adjustment
	 * 
	 * @return The Path Unilateral Funding Debt Adjustment
	 */

	public double unilateralFundingDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double unilateralFundingDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			unilateralFundingDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Funding Debt Adjustment
	 * 
	 * @return The Path Bilateral Funding Debt Adjustment
	 */

	public double bilateralFundingDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double bilateralFundingDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability() *
				(1. - marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability() *
				(1. - marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			bilateralFundingDebtAdjustment += 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return bilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Unilateral Collateral Value Adjustment
	 * 
	 * @return The Path Unilateral Collateral Value Adjustment
	 */

	public double unilateralCollateralAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] periodCollateralValueAdjustmentArray = periodCollateralValueAdjustment();

		double unilateralCollateralValueAdjustment = 0.;
		int periodCount = periodCollateralValueAdjustmentArray.length;

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
			unilateralCollateralValueAdjustment += 0.5 * periodCollateralValueAdjustmentArray[periodIndex] *
			(
				marketVertexArray[periodIndex].client().survivalProbability() +
				marketVertexArray[periodIndex + 1].client().survivalProbability()
			);
		}

		return unilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Path Bilateral Collateral Value Adjustment
	 */

	public double bilateralCollateralAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] periodCollateralValueAdjustmentArray = periodCollateralValueAdjustment();

		double bilateralCollateralValueAdjustment = 0.;
		int periodCount = periodCollateralValueAdjustmentArray.length;

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
			bilateralCollateralValueAdjustment += 0.5 * periodCollateralValueAdjustmentArray[periodIndex] * (
				marketVertexArray[periodIndex].dealer().survivalProbability() *
				marketVertexArray[periodIndex].client().survivalProbability() +
				marketVertexArray[periodIndex + 1].dealer().survivalProbability() *
				marketVertexArray[periodIndex + 1].client().survivalProbability()
			);
		}

		return bilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Collateral Value Adjustment
	 * 
	 * @return The Path Collateral Value Adjustment
	 */

	public double collateralValueAdjustment()
	{
		return unilateralCollateralAdjustment();
	}

	/**
	 * Compute Period-wise Symmetric Funding Value Spread 01
	 * 
	 * @return The Period-wise Symmetric Funding Value Spread 01
	 */

	public double[] periodSymmetricFundingValueSpread01()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCollateralizedExposurePVArray = vertexCollateralizedExposurePV();

		int periodCount = vertexCollateralizedExposurePVArray.length - 1;
		double[] periodSymmetricFundingValueSpread01Array = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
			periodSymmetricFundingValueSpread01Array[periodIndex] = -0.5 * (
				vertexCollateralizedExposurePVArray[periodIndex] +
				vertexCollateralizedExposurePVArray[periodIndex + 1]
			) * (
				marketVertexArray[periodIndex + 1].anchorDate().julian() -
				marketVertexArray[periodIndex].anchorDate().julian()
			) / 365.25;
		}

		return periodSymmetricFundingValueSpread01Array;
	}

	/**
	 * Compute Period-wise Unilateral Credit Adjustment
	 * 
	 * @return The Period-wise Unilateral Credit Adjustment
	 */

	public double[] periodUnilateralCreditAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePVArray = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePVArray.length;
		double[] periodUnilateralCreditAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexCreditExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate());

			double periodIntegrandEnd = vertexCreditExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].client().seniorRecoveryRate());

			periodUnilateralCreditAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability()
				);
		}

		return periodUnilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Credit Adjustment
	 * 
	 * @return The Period-wise Bilateral Credit Adjustment
	 */

	public double[] periodBilateralCreditAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexCreditExposurePVArray = vertexCreditExposurePV();

		int vertexCount = vertexCreditExposurePVArray.length;
		double[] periodBilateralCreditAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexCreditExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = vertexCreditExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			periodBilateralCreditAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
				marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return periodBilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Contra-Liability Credit Adjustment
	 * 
	 * @return The Period-wise Contra-Liability Credit Adjustment
	 */

	public double[] periodContraLiabilityCreditAdjustment()
	{
		double[] periodUnilateralCreditAdjustmentArray = periodUnilateralCreditAdjustment();

		double[] periodBilateralCreditAdjustmentArray = periodBilateralCreditAdjustment();

		int vertexCount = periodUnilateralCreditAdjustmentArray.length;
		double[] periodContraLiabilityCreditAdjustmentArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			periodContraLiabilityCreditAdjustmentArray[vertexIndex] =
				periodUnilateralCreditAdjustmentArray[vertexIndex] -
				periodBilateralCreditAdjustmentArray[vertexIndex];
		}

		return periodContraLiabilityCreditAdjustmentArray;
	}

	/**
	 * Compute Period-wise Unilateral Debt Adjustment
	 * 
	 * @return The Period-wise Unilateral Debt Adjustment
	 */

	public double[] periodUnilateralDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double[] periodUnilateralDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			periodUnilateralDebtAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodUnilateralDebtAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Debt Adjustment
	 * 
	 * @return The Period-wise Bilateral Debt Adjustment
	 */

	public double[] periodBilateralDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double[] periodBilateralDebtAdjustmentArray = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			periodBilateralDebtAdjustmentArray[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodBilateralDebtAdjustmentArray;
	}

	/**
	 * Compute Period Unilateral Funding Value Spread 01
	 * 
	 * @return The Period Unilateral Funding Value Spread 01
	 */

	public double[] periodUnilateralFundingValueSpread01()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePVArray = vertexFundingExposurePV();

		int periodCount = vertexFundingExposurePVArray.length - 1;
		double[] periodUnilateralFundingValueSpread01Array = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			double periodIntegrandStart = vertexFundingExposurePVArray[periodIndex] *
				marketVertexArray[periodIndex].client().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePVArray[periodIndex + 1] *
				marketVertexArray[periodIndex + 1].client().survivalProbability();

			periodUnilateralFundingValueSpread01Array[periodIndex] =
				0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[periodIndex + 1].anchorDate().julian() -
					marketVertexArray[periodIndex].anchorDate().julian()
				) / 365.25;
		}

		return periodUnilateralFundingValueSpread01Array;
	}

	/**
	 * Compute Period Bilateral Funding Value Spread 01
	 * 
	 * @return The Period Bilateral Funding Value Spread 01
	 */

	public double[] periodBilateralFundingValueSpread01()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexFundingExposurePVArray = vertexFundingExposurePV();

		int periodCount = vertexFundingExposurePVArray.length - 1;
		double[] periodBilateralFundingValueSpread01Array = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex) {
			double periodIntegrandStart = vertexFundingExposurePVArray[periodIndex] *
				marketVertexArray[periodIndex].client().survivalProbability() *
				marketVertexArray[periodIndex].dealer().survivalProbability();

			double periodIntegrandEnd = vertexFundingExposurePVArray[periodIndex + 1] *
				marketVertexArray[periodIndex + 1].client().survivalProbability() *
				marketVertexArray[periodIndex + 1].dealer().survivalProbability();

			periodBilateralFundingValueSpread01Array[periodIndex] =
				0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[periodIndex + 1].anchorDate().julian() -
					marketVertexArray[periodIndex].anchorDate().julian()
				) / 365.25;
		}

		return periodBilateralFundingValueSpread01Array;
	}

	/**
	 * Compute Period Unilateral Funding Debt Adjustment
	 * 
	 * @return The Period Unilateral Funding Debt Adjustment
	 */

	public double[] periodUnilateralFundingDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double[] periodUnilateralFundingDebtAdjustmentArray = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			periodUnilateralFundingDebtAdjustmentArray[vertexIndex - 1] = -0.5 *
				(periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability()
				);
		}

		return periodUnilateralFundingDebtAdjustmentArray;
	}

	/**
	 * Compute Period Bilateral Funding Debt Adjustment
	 * 
	 * @return The Period Bilateral Funding Debt Adjustment
	 */

	public double[] periodBilateralFundingDebtAdjustment()
	{
		MarketVertex[] marketVertexArray = _marketPath.marketVertexArray();

		double[] vertexDebtExposurePVArray = vertexDebtExposurePV();

		int vertexCount = vertexDebtExposurePVArray.length;
		double[] periodBilateralFundingDebtAdjustmentArray = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			double periodIntegrandStart = vertexDebtExposurePVArray[vertexIndex - 1] *
				(1. - marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = vertexDebtExposurePVArray[vertexIndex] *
				(1. - marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			periodBilateralFundingDebtAdjustmentArray[vertexIndex - 1] = -0.5 *
				(periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability()
				);
		}

		return periodBilateralFundingDebtAdjustmentArray;
	}

	/**
	 * Compute Path Credit Adjustment
	 * 
	 * @return The Path Credit Adjustment
	 */

	public abstract double creditAdjustment();

	/**
	 * Compute Path Debt Adjustment
	 * 
	 * @return The Path Debt Adjustment
	 */

	public abstract double debtAdjustment();

	/**
	 * Compute Period-wise Credit Adjustment
	 * 
	 * @return The Period-wise Credit Adjustment
	 */

	public abstract double[] periodCreditAdjustment();

	/**
	 * Compute Period-wise Debt Adjustment
	 * 
	 * @return The Period-wise Debt Adjustment
	 */

	public abstract double[] periodDebtAdjustment();
}
