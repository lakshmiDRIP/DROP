
package org.drip.capital.bcbs;

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
 * <i>LiquidityMetrics</i> holds the Realized Liquidity Metrics. The References are:
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

public class LiquidityMetrics
{
	private double _netStableFundingRatio = java.lang.Double.NaN;
	private double _liquidityCoverageRatio = java.lang.Double.NaN;

	/**
	 * Construct the Basel III 2015 Version of the Liquidity Metrics Standard
	 * 
	 * @return The Basel III 2015 Version of the Liquidity Metrics Standard
	 */

	public static final LiquidityMetrics Basel_III_2015()
	{
		try
		{
			return new LiquidityMetrics (
				0.60,
				0.00
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Basel III 2016 Version of the Liquidity Metrics Standard
	 * 
	 * @return The Basel III 2016 Version of the Liquidity Metrics Standard
	 */

	public static final LiquidityMetrics Basel_III_2016()
	{
		try
		{
			return new LiquidityMetrics (
				0.70,
				0.00
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Basel III 2017 Version of the Liquidity Metrics Standard
	 * 
	 * @return The Basel III 2017 Version of the Liquidity Metrics Standard
	 */

	public static final LiquidityMetrics Basel_III_2017()
	{
		try
		{
			return new LiquidityMetrics (
				0.80,
				0.00
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Basel III 2018 Version of the Liquidity Metrics Standard
	 * 
	 * @return The Basel III 2018 Version of the Liquidity Metrics Standard
	 */

	public static final LiquidityMetrics Basel_III_2018()
	{
		try
		{
			return new LiquidityMetrics (
				0.90,
				1.00
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Basel III 2019 Version of the Liquidity Metrics Standard
	 * 
	 * @return The Basel III 2019 Version of the Liquidity Metrics Standard
	 */

	public static final LiquidityMetrics Basel_III_2019()
	{
		try
		{
			return new LiquidityMetrics (
				1.00,
				1.00
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LiquidityMetrics Constructor
	 * 
	 * @param liquidityCoverageRatio The Liquidity Coverage Ratio
	 * @param netStableFundingRatio The Net Stable Funding Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LiquidityMetrics (
		final double liquidityCoverageRatio,
		final double netStableFundingRatio)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_liquidityCoverageRatio = liquidityCoverageRatio) ||
				0. > _liquidityCoverageRatio ||
			!org.drip.numerical.common.NumberUtil.IsValid (_netStableFundingRatio = netStableFundingRatio) ||
				0. > _netStableFundingRatio)
		{
			throw new java.lang.Exception ("LiquidityMetrics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Liquidity Coverage Ratio
	 * 
	 * @return The Liquidity Coverage Ratio
	 */

	public double liquidityCoverageRatio()
	{
		return _liquidityCoverageRatio;
	}

	/**
	 * Retrieve the Net Stable Funding Ratio
	 * 
	 * @return The Net Stable Funding Ratio
	 */

	public double netStableFundingRatio()
	{
		return _netStableFundingRatio;
	}

	/**
	 * Verify if the Liquidity Metrics are Compliant with the Standard
	 * 
	 * @param liquidityMetricsStandard The Liquidity Metrics Standard
	 * 
	 * @return TRUE - The Liquidity Metrics are Compliant with the Standard
	 */

	public boolean isCompliant (
		final org.drip.capital.bcbs.LiquidityMetrics liquidityMetricsStandard)
	{
		if (null == liquidityMetricsStandard)
		{
			return false;
		}

		return _liquidityCoverageRatio >= liquidityMetricsStandard.liquidityCoverageRatio() &&
			_netStableFundingRatio >= liquidityMetricsStandard.netStableFundingRatio();
	}
}
