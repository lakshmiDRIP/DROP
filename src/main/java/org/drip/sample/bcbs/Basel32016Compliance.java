
package org.drip.sample.bcbs;

import org.drip.capital.bcbs.BalanceSheet;
import org.drip.capital.bcbs.BalanceSheetCapital;
import org.drip.capital.bcbs.BalanceSheetFunding;
import org.drip.capital.bcbs.BalanceSheetLiquidity;
import org.drip.capital.bcbs.CapitalMetricsStandard;
import org.drip.capital.bcbs.HighQualityLiquidAsset;
import org.drip.capital.bcbs.HighQualityLiquidAssetSettings;
import org.drip.capital.bcbs.LiquidityMetrics;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>Basel32016Compliance</i> illustrates the Basel III 2016 Capital Metrics Compliance Checks along with
 * Liquidity Compliance Checks for several Liquidity Metrics Standards. Liquidity Criteria correspond to
 * Large BHC's. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bcbs/README.md">BCBS/Jurisdictional Capital/Leverage Compliance Checks</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Basel32016Compliance
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		HighQualityLiquidAssetSettings hqlaSettings =
			HighQualityLiquidAssetSettings.FederalReserveStandard();

		/*
		 * Capital Parameters
		 */

		double cet1 = 450.;
		double at1 = 150.;
		double additionalCapital = 200.;
		double rwa = 5000.;
		double totalExposure = 4000.;

		/*
		 * HQLA Parameters
		 */

		double level1 = 60.;
		double level2A = 25.;
		double level2B = 15.;

		/*
		 * Liquidity Parameters
		 */

		double netCashOutflowAmount = 75.;

		/*
		 * Stable Funding Parameters
		 */

		double stableFundingAmount = 500.;
		double extendedStressFundingAmount = 400.;

		CapitalMetricsStandard capitalMetricsStandard = CapitalMetricsStandard.Basel_III_2016();

		BalanceSheet balanceSheet = new BalanceSheet (
			new BalanceSheetCapital (
				cet1,
				at1,
				additionalCapital,
				rwa,
				totalExposure
			),
			BalanceSheetLiquidity.LargeBHC (
				new HighQualityLiquidAsset (
					level1,
					level2A,
					level2B
				),
				netCashOutflowAmount
			),
			new BalanceSheetFunding (
				stableFundingAmount,
				extendedStressFundingAmount,
				"1Y"
			)
		);

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\t|           Basel III 2016 Liquidity Standards Compliance           ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println (
			"\t| CET1 Ratio (vs. Standard)                  => " +
			FormatUtil.FormatDouble (balanceSheet.cet1Ratio(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (capitalMetricsStandard.commonEquityRatio(), 3, 2, 100.) + "%"
		);

		System.out.println (
			"\t| Tier 1 Ratio (vs. Standard)                => " +
			FormatUtil.FormatDouble (balanceSheet.tier1Ratio(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (capitalMetricsStandard.tier1Ratio(), 3, 2, 100.) + "%"
		);

		System.out.println (
			"\t| Total Capital Ratio (vs. Standard)         => " +
			FormatUtil.FormatDouble (balanceSheet.totalCapitalRatio(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (capitalMetricsStandard.totalRatio(), 3, 2, 100.) + "%"
		);

		System.out.println (
			"\t| Leverage Ratio                             => " +
			FormatUtil.FormatDouble (balanceSheet.leverageRatio(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (capitalMetricsStandard.leverageRatio(), 3, 2, 100.) + "%"
		);

		System.out.println (
			"\t| Liquidity Coverage Ratio                   => " +
			FormatUtil.FormatDouble (balanceSheet.liquidityCoverageRatio (hqlaSettings), 3, 2, 100.) + "%"
		);

		System.out.println (
			"\t| Net Stable Funding Ratio                   => " +
			FormatUtil.FormatDouble (balanceSheet.netStableFundingRatio(), 3, 2, 100.) + "%"
		);

		System.out.println (
			"\t| Capital Metrics Compliance                 =>  " +
			balanceSheet.capitalMetrics().isCompliant (capitalMetricsStandard)
		);

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\t|                 Cross Vintage Liquidity Standard                  ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\t|             Year             =>  2015 | 2016 | 2017 | 2018 | 2019 ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		String liquidityMetricsCompliance = "\t| Liquidity Metrics Compliance =>  ";

		liquidityMetricsCompliance = liquidityMetricsCompliance +
			balanceSheet.liquidityMetrics (hqlaSettings).isCompliant (LiquidityMetrics.Basel_III_2015()) + " | ";

		liquidityMetricsCompliance = liquidityMetricsCompliance +
			balanceSheet.liquidityMetrics (hqlaSettings).isCompliant (LiquidityMetrics.Basel_III_2016()) + " | ";

		liquidityMetricsCompliance = liquidityMetricsCompliance +
			balanceSheet.liquidityMetrics (hqlaSettings).isCompliant (LiquidityMetrics.Basel_III_2017()) + " | ";

		liquidityMetricsCompliance = liquidityMetricsCompliance +
			balanceSheet.liquidityMetrics (hqlaSettings).isCompliant (LiquidityMetrics.Basel_III_2018()) + " | ";

		liquidityMetricsCompliance = liquidityMetricsCompliance +
			balanceSheet.liquidityMetrics (hqlaSettings).isCompliant (LiquidityMetrics.Basel_III_2019()) + " ||";

		System.out.println (liquidityMetricsCompliance);

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
