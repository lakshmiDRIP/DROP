
package org.drip.xva.netting;

import org.drip.analytics.date.JulianDate;
import org.drip.exposure.universe.MarketPath;
import org.drip.xva.hypothecation.CollateralGroupVertex;

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
 * <i>CollateralGroupPath</i> accumulates the Vertex Realizations of the Sequence in a Single Path Projection
 * Run along the Granularity of a Regular Collateral Hypothecation Group. The References are:
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

public class CollateralGroupPath
{
	private MarketPath _marketPath = null;
	private double _overnightReplicatorStart = Double.NaN;
	private CollateralGroupVertex[] _collateralGroupVertexArray = null;

	/**
	 * CollateralGroupPath Constructor
	 * 
	 * @param collateralGroupVertexArray The Array of Collateral Hypothecation Group Trajectory Vertexes
	 * @param marketPath The Market Path
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupPath (
		final CollateralGroupVertex[] collateralGroupVertexArray,
		final MarketPath marketPath)
		throws Exception
	{
		if (null == (_collateralGroupVertexArray = collateralGroupVertexArray) ||
			null == (_marketPath = marketPath)) {
			throw new Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		_overnightReplicatorStart = _marketPath.epochalMarketVertex().overnightReplicator();

		int vertexCount = _collateralGroupVertexArray.length;

		if (1 >= vertexCount) {
			throw new Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			if (null == _collateralGroupVertexArray[vertexIndex]) {
				throw new Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}

			if (0 != vertexIndex && _collateralGroupVertexArray[vertexIndex - 1].vertexDate().julian() >=
				_collateralGroupVertexArray[vertexIndex].vertexDate().julian()) {
				throw new Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Collateral Group Trajectory Vertexes
	 * 
	 * @return The Array of Collateral Group Trajectory Vertexes
	 */

	public CollateralGroupVertex[] collateralGroupVertex()
	{
		return _collateralGroupVertexArray;
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
		int vertexCount = _collateralGroupVertexArray.length;
		JulianDate[] vertexDateArray = new JulianDate[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			vertexDateArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].vertexDate();
		}

		return vertexDateArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposures
	 * 
	 * @return The Array of Vertex Collateralized Exposures
	 */

	public double[] vertexCollateralizedExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposureArray[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].collateralized();
		}

		return collateralizedExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Exposure PV
	 */

	public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedExposurePVArray = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedExposurePVArray[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].collateralized() * _overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return collateralizedExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Exposures
	 */

	public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] uncollateralizedExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposureArray[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].uncollateralized();
		}

		return uncollateralizedExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Exposure PV
	 */

	public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] uncollateralizedExposurePVArray = new double[vertexCount];

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			uncollateralizedExposurePVArray[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].uncollateralized() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return uncollateralizedExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposures
	 * 
	 * @return The Array of Vertex Credit Exposures
	 */

	public double[] vertexCreditExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] creditExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			creditExposureArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].credit();
		}

		return creditExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure PV
	 * 
	 * @return The Array of Vertex Credit Exposure PV
	 */

	public double[] vertexCreditExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] creditExposurePVArray = new double[vertexCount];

		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			creditExposurePVArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].credit() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return creditExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposures
	 * 
	 * @return The Array of Vertex Debt Exposures
	 */

	public double[] vertexDebtExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] debtExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			debtExposureArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].debt();
		}

		return debtExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposures PV
	 * 
	 * @return The Array of Vertex Debt Exposures PV
	 */

	public double[] vertexDebtExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] debtExposurePVArray = new double[vertexCount];

		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			debtExposurePVArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].debt() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return debtExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposures
	 * 
	 * @return The Array of Vertex Funding Exposures
	 */

	public double[] vertexFundingExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] fundingExposureArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposureArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].funding();
		}

		return fundingExposureArray;
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposures PV
	 * 
	 * @return The Array of Vertex Funding Exposures PV
	 */

	public double[] vertexFundingExposurePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] fundingExposurePVArray = new double[vertexCount];

		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			fundingExposurePVArray[vertexIndex] = _collateralGroupVertexArray[vertexIndex].funding() *
				_overnightReplicatorStart /
				_marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return fundingExposurePVArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances
	 * 
	 * @return The Array of Vertex Collateral Balances
	 */

	public double[] vertexCollateralBalance()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedBalanceArray = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedBalanceArray[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].variationMarginPosting();
		}

		return collateralizedBalanceArray;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances PV
	 * 
	 * @return The Array of Vertex Collateral Balances PV
	 */

	public double[] vertexCollateralBalancePV()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedBalancePVArray = new double[vertexCount];

		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
			collateralizedBalancePVArray[vertexIndex] =
				_collateralGroupVertexArray[vertexIndex].variationMarginPosting() * _overnightReplicatorStart
				/ _marketPath.marketVertex (vertexDateArray[vertexIndex].julian()).overnightReplicator();
		}

		return collateralizedBalancePVArray;
	}

	/**
	 * Compute Period-wise Path Collateral Spread 01
	 * 
	 * @return The Period-wise Path Collateral Spread 01
	 */

	public double[] periodCollateralSpread01()
	{
		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		double[] vertexCollateralBalancePVArray = vertexCollateralBalancePV();

		int vertexCount = vertexCollateralBalancePVArray.length;
		double[] periodCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			periodCollateralValueAdjustment[vertexIndex - 1] = -0.5 * (
				vertexCollateralBalancePVArray[vertexIndex - 1] + vertexCollateralBalancePVArray[vertexIndex]
			) * (vertexDateArray[vertexIndex].julian() - vertexDateArray[vertexIndex - 1].julian()) / 365.25;
		}

		return periodCollateralValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 */

	public double[] periodCollateralValueAdjustment()
	{
		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		double[] vertexCollateralBalancePVArray = vertexCollateralBalancePV();

		int vertexCount = vertexCollateralBalancePVArray.length;
		double[] periodCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex) {
			int previousVertexDate = vertexDateArray[vertexIndex - 1].julian();

			int currentVertexDate = vertexDateArray[vertexIndex].julian();

			double periodIntegrandStart = vertexCollateralBalancePVArray[vertexIndex - 1] *
				_marketPath.marketVertex (previousVertexDate).csaSpread();

			double periodIntegrandEnd = vertexCollateralBalancePVArray[vertexIndex] *
				_marketPath.marketVertex (currentVertexDate).csaSpread();

			periodCollateralValueAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(currentVertexDate - previousVertexDate) / 365.25;
		}

		return periodCollateralValueAdjustment;
	}
}
