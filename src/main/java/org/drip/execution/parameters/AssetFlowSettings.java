
package org.drip.execution.parameters;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>AssetFlowSettings</i> contains the Asset's Market Flow Parameters that are determined empirically from
 * Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren (2003). The References are:
 * 
 * <br><br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 * 			<b>3 (2)</b> 5-39
 * 	</li>
 * 	<li>
 * 		Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 			<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 	</li>
 * 	<li>
 * 		Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b>
 * 			57-62
 * 	</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/README.md">Parameters</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AssetFlowSettings {
	private java.lang.String _strAssetID = "";
	private double _dblDailyVolatility = java.lang.Double.NaN;
	private double _dblNumberOutstanding = java.lang.Double.NaN;
	private double _dblAverageDailyVolume = java.lang.Double.NaN;

	/**
	 * AssetFlowSettings Constructor
	 * 
	 * @param strAssetID The Asset ID
	 * @param dblAverageDailyVolume The Asset Average Daily Volume
	 * @param dblNumberOutstanding The Number of Trade-able Asset Units Outstanding
	 * @param dblDailyVolatility The Asset Daily Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetFlowSettings (
		final java.lang.String strAssetID,
		final double dblAverageDailyVolume,
		final double dblNumberOutstanding,
		final double dblDailyVolatility)
		throws java.lang.Exception
	{
		if (null == (_strAssetID = strAssetID) || _strAssetID.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblAverageDailyVolume = dblAverageDailyVolume) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblNumberOutstanding = dblNumberOutstanding) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblDailyVolatility = dblDailyVolatility) ||
						0. >= _dblNumberOutstanding || 0. >= _dblDailyVolatility)
			throw new java.lang.Exception ("AssetFlowSettings Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset ID
	 * 
	 * @return The Asset ID
	 */

	public java.lang.String assetID()
	{
		return _strAssetID;
	}

	/**
	 * Retrieve the Average Daily Volume
	 * 
	 * @return The Average Daily Volume
	 */

	public double averageDailyVolume()
	{
		return _dblAverageDailyVolume;
	}

	/**
	 * Retrieve the Daily Volatility
	 * 
	 * @return The Daily Volatility
	 */

	public double dailyVolatility()
	{
		return _dblDailyVolatility;
	}

	/**
	 * Retrieve the Outstanding Number of the Traded Units 
	 * 
	 * @return The Outstanding Number of the Traded Units
	 */

	public double outstandingUnits()
	{
		return _dblNumberOutstanding;
	}

	/**
	 * Retrieve the Daily Turnover 
	 * 
	 * @return The Daily Turnover
	 */

	public double turnover()
	{
		return _dblAverageDailyVolume / _dblNumberOutstanding;
	}

	/**
	 * Retrieve the Daily Inverse Turnover 
	 * 
	 * @return The Daily Inverse Turnover
	 */

	public double inverseTurnover()
	{
		return _dblNumberOutstanding / _dblAverageDailyVolume;
	}

	/**
	 * Retrieve the Normalized Trade Size
	 * 
	 * @param dblRawTradeSize The Raw Trade Size
	 * @param dblTime The Time
	 * 
	 * @return The Normalized Trade Size
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double normalizeTradeSize (
		final double dblRawTradeSize,
		final double dblTime)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblRawTradeSize) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblTime))
			throw new java.lang.Exception ("AssetFlowSettings::normalizeTradeSize => Invalid Inputs");

		return dblRawTradeSize / (_dblAverageDailyVolume * dblTime);
	}

	/**
	 * De-normalize the Specified Temporary/Permanent Impact
	 * 
	 * @param dblNormalizedImpact The Normalized Impact
	 * 
	 * @return The De-normalized Temporary/Permanent Impact
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double denormalizeImpact (
		final double dblNormalizedImpact)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblNormalizedImpact))
			throw new java.lang.Exception ("AssetFlowSettings::denormalizeImpact => Invalid Inputs");

		return dblNormalizedImpact * _dblDailyVolatility;
	}
}
