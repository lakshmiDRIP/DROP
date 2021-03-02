
package org.drip.capital.bcbs;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>BalanceSheetLiquidity</i> holds the Liquidity Related Fields needed for computing the Compliance
 * Ratios. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Basel Committee on Banking Supervision (2017): Basel III Leverage Ratio Framework and Disclosure
 * 				Requirements https://www.bis.org/publ/bcbs270.pdf
 * 		</li>
 * 		<li>
 * 			Central Banking (2013): Fed and FDIC agree 6% Leverage Ratio for US SIFIs
 * 				https://www.centralbanking.com/central-banking/news/2280726/fed-and-fdic-agree-6-leverage-ratio-for-us-sifis
 * 		</li>
 * 		<li>
 * 			European Banking Agency (2013): Implementing Basel III in Europe: CRD IV Package
 * 				https://eba.europa.eu/regulation-and-policy/implementing-basel-iii-europe
 * 		</li>
 * 		<li>
 * 			Federal Reserve (2013): Liquidity Coverage Ratio – Liquidity Risk Measurements, Standards, and
 * 				Monitoring
 * 				https://web.archive.org/web/20131102074614/http:/www.federalreserve.gov/FR_notice_lcr_20131024.pdf
 * 		</li>
 * 		<li>
 * 			Wikipedia (2018): Basel III https://en.wikipedia.org/wiki/Basel_III
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/bcbs/README.md">BCBS and Jurisdictional Capital Ratios</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BalanceSheetLiquidity
{
	private boolean _usePeakCumulative = false;
	private java.lang.String _netCashOutflowPeriod = "";
	private double _netCashOutflowAmount = java.lang.Double.NaN;
	private org.drip.capital.bcbs.HighQualityLiquidAsset _highQualityLiquidAsset = null;

	/**
	 * Construct the Basel III Standard Version of Balance Sheet Liquidity
	 * 
	 * @param highQualityLiquidAsset High Quality Liquid Asset Instance
	 * @param netCashOutflowAmount Net Cash Outflow Amount
	 * @param usePeakCumulative TRUE - The Net Outflow is to be determined off of the Peak Cumulative Period
	 * 
	 * @return Basel III Standard Version of Balance Sheet Liquidity
	 */

	public static final BalanceSheetLiquidity Basel_III (
		final org.drip.capital.bcbs.HighQualityLiquidAsset highQualityLiquidAsset,
		final double netCashOutflowAmount,
		final boolean usePeakCumulative)
	{
		try
		{
			return new BalanceSheetLiquidity (
				highQualityLiquidAsset,
				netCashOutflowAmount,
				"30D",
				usePeakCumulative
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Basel III Standard Version of Balance Sheet Liquidity for Large BHC's
	 * 
	 * @param highQualityLiquidAsset High Quality Liquid Asset Instance
	 * @param netCashOutflowAmount Net Cash Outflow Amount
	 * 
	 * @return Basel III Standard Version of Balance Sheet Liquidity for Large BHC's
	 */

	public static final BalanceSheetLiquidity LargeBHC (
		final org.drip.capital.bcbs.HighQualityLiquidAsset highQualityLiquidAsset,
		final double netCashOutflowAmount)
	{
		return Basel_III (
			highQualityLiquidAsset,
			netCashOutflowAmount,
			true
		);
	}

	/**
	 * Construct the Basel III Standard Version of Balance Sheet Liquidity for Regional BHC's
	 * 
	 * @param highQualityLiquidAsset High Quality Liquid Asset Instance
	 * @param netCashOutflowAmount Net Cash Outflow Amount
	 * 
	 * @return Basel III Standard Version of Balance Sheet Liquidity for Regional BHC's
	 */

	public static final BalanceSheetLiquidity RegionalBHC (
		final org.drip.capital.bcbs.HighQualityLiquidAsset highQualityLiquidAsset,
		final double netCashOutflowAmount)
	{
		try
		{
			return new BalanceSheetLiquidity (
				highQualityLiquidAsset,
				netCashOutflowAmount,
				"21D",
				false
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BalanceSheetLiquidity Constructor
	 * 
	 * @param highQualityLiquidAsset High Quality Liquid Asset Instance
	 * @param netCashOutflowAmount Net Cash Outflow Amount
	 * @param netCashOutflowPeriod Net Cash Outflow Period
	 * @param usePeakCumulative TRUE - The Net Outflow is to be determined off of the Peak Cumulative Period
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BalanceSheetLiquidity (
		final org.drip.capital.bcbs.HighQualityLiquidAsset highQualityLiquidAsset,
		final double netCashOutflowAmount,
		final java.lang.String netCashOutflowPeriod,
		final boolean usePeakCumulative)
		throws java.lang.Exception
	{
		if (null == (_highQualityLiquidAsset = highQualityLiquidAsset) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_netCashOutflowAmount = netCashOutflowAmount) ||
				0. > _netCashOutflowAmount ||
			null == (_netCashOutflowPeriod = netCashOutflowPeriod) || _netCashOutflowPeriod.isEmpty())
		{
			throw new java.lang.Exception ("BalanceSheetLiquidity Constructor => Invalid Inputs");
		}

		_usePeakCumulative = usePeakCumulative;
	}

	/**
	 * Retrieve the High Quality Liquid Asset Instance
	 * 
	 * @return The High Quality Liquid Asset Instance
	 */

	public org.drip.capital.bcbs.HighQualityLiquidAsset highQualityLiquidAsset()
	{
		return _highQualityLiquidAsset;
	}

	/**
	 * Retrieve the Net Cash Outflow Amount
	 * 
	 * @return The Net Cash Outflow Amount
	 */

	public double netCashOutflowAmount()
	{
		return _netCashOutflowAmount;
	}

	/**
	 * Retrieve the Net Cash Outflow Period
	 * 
	 * @return The Net Cash Outflow Period
	 */

	public java.lang.String netCashOutflowPeriod()
	{
		return _netCashOutflowPeriod;
	}

	/**
	 * Indicate if the Net Outflow is to be determined off of the Peak Cumulative Period
	 * 
	 * @return TRUE - The Net Outflow is to be determined off of the Peak Cumulative Period
	 */

	public boolean usePeakCumulative()
	{
		return _usePeakCumulative;
	}

	/**
	 * Compute the Liquidity Coverage Ratio
	 *  
	 * @param hqlaSettings THe HQLA Settings
	 * 
	 * @return The Liquidity Coverage Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double liquidityCoverageRatio (
		final org.drip.capital.bcbs.HighQualityLiquidAssetSettings hqlaSettings)
		throws java.lang.Exception
	{
		return _highQualityLiquidAsset.totalRiskWeightAndHaircut (hqlaSettings) / _netCashOutflowAmount;
	}
}
