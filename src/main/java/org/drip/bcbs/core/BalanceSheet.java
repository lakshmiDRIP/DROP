
package org.drip.bcbs.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>BalanceSheet</i> holds the Quantities used to compute the Capital/Liquidity Ratios in the BCBS
 * Standards. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/bcbs/README.md">BCBS</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/bcbs/core/README.md">Core</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BalanceSheet
{
	private double _at1 = java.lang.Double.NaN;
	private double _rwa = java.lang.Double.NaN;
	private double _cet1 = java.lang.Double.NaN;
	private double _hqla = java.lang.Double.NaN;
	private double _totalExposure = java.lang.Double.NaN;
	private double _additionalCapital = java.lang.Double.NaN;
	private double _stableFundingAmount = java.lang.Double.NaN;
	private double _netLiquidityOutflow30D = java.lang.Double.NaN;
	private double _extendedStressFundingAmount = java.lang.Double.NaN;

	/**
	 * Retrieve the Common Equity Tier 1 Capital
	 * 
	 * @return The Common Equity Tier 1 Capital
	 */

	public double commonEquityTier1()
	{
		return _cet1;
	}

	/**
	 * Retrieve the Additional Tier 1 Capital
	 * 
	 * @return The Additional Tier 1 Capital
	 */

	public double additionalTier1()
	{
		return _at1;
	}

	/**
	 * Retrieve the Risk Weighted Assets
	 * 
	 * @return The Risk Weighted Assets
	 */

	public double riskWeightedAssets()
	{
		return _rwa;
	}

	/**
	 * Retrieve the Total Exposure
	 * 
	 * @return The Total Exposure
	 */

	public double totalExposure()
	{
		return _totalExposure;
	}

	/**
	 * Retrieve the High Quality Liquid Assets
	 * 
	 * @return The High Quality Liquid Assets
	 */

	public double hqla()
	{
		return _hqla;
	}

	/**
	 * Retrieve the Total Net Liquidity Outflows Over 30D
	 * 
	 * @return The Total Net Liquidity Outflows Over 30D
	 */

	public double netLiquidityOutflow30D()
	{
		return _netLiquidityOutflow30D;
	}

	/**
	 * Retrieve the Stable Funding Amount
	 * 
	 * @return The Stable Funding Amount
	 */

	public double stableFundingAmount()
	{
		return _stableFundingAmount;
	}

	/**
	 * Retrieve the Funding Amount Required Over 1Y Period of Extended Stress
	 * 
	 * @return The Funding Amount Required Over 1Y Period of Extended Stress
	 */

	public double extendedStressFundingAmount()
	{
		return _extendedStressFundingAmount;
	}

	/**
	 * Retrieve the Additional Capital
	 * 
	 * @return The Additional Capital
	 */

	public double additionalCapital()
	{
		return _additionalCapital;
	}

	/**
	 * Retrieve the Tier 1 Capital
	 * 
	 * @return The Tier 1 Capital
	 */

	public double tier1()
	{
		return _cet1 + _at1;
	}

	/**
	 * Retrieve the Total Capital
	 * 
	 * @return The Total Capital
	 */

	public double totalCapital()
	{
		return _cet1 + _at1 + _additionalCapital;
	}

	/**
	 * Retrieve the CET 1 Ratio
	 * 
	 * @return The CET 1 Ratio
	 */

	public double cet1Ratio()
	{
		return _cet1 / _rwa;
	}

	/**
	 * Retrieve the Total Capital Ratio
	 * 
	 * @return The Total Capital Ratio
	 */

	public double totalCapitalRatio()
	{
		return (_cet1 + _at1 + _additionalCapital) / _rwa;
	}

	/**
	 * Retrieve the Leverage Ratio
	 * 
	 * @return The Leverage Ratio
	 */

	public double leverageRatio()
	{
		return (_cet1 + _at1) / _totalExposure;
	}

	/**
	 * Retrieve the Liquidity Coverage Ratio
	 * 
	 * @return The Liquidity Coverage Ratio
	 */

	public double liquidityCoverageRatio()
	{
		return _hqla / _netLiquidityOutflow30D;
	}

	/**
	 * Retrieve the Net Stable Funding Ratio
	 * 
	 * @return The Net Stable Funding Ratio
	 */

	public double netStableFundingRatio()
	{
		return _stableFundingAmount / _extendedStressFundingAmount;
	}

	/**
	 * Generate the Balance Sheet Capital Metrics
	 * 
	 * @return The Balance Sheet Capital Metrics
	 */

	public org.drip.bcbs.core.CapitalMetrics capitalMetrics()
	{
		double commonEquityCapitalRatio = _cet1 / _rwa;
		double totalCapitalRatio = (_cet1 + _at1 + _additionalCapital) / _rwa;

		try
		{
			return new org.drip.bcbs.core.CapitalMetrics (
				(_cet1 + _at1) / _totalExposure,
				commonEquityCapitalRatio,
				commonEquityCapitalRatio,
				(_cet1 + _at1) / _rwa,
				totalCapitalRatio,
				totalCapitalRatio
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Balance Sheet Liquidity Metrics
	 * 
	 * @return The Balance Sheet Liquidity Metrics
	 */

	public org.drip.bcbs.core.LiquidityMetrics liquidityMetrics()
	{
		try
		{
			return new org.drip.bcbs.core.LiquidityMetrics (
				_hqla / _netLiquidityOutflow30D,
				_stableFundingAmount / _extendedStressFundingAmount
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
