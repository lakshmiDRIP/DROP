
package org.drip.sample.bcbs;

import org.drip.capital.bcbs.CapitalMetricsStandard;
import org.drip.capital.bcbs.LiquidityMetrics;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>BaselPhaseInArrangements</i> illustrates the Basel III Capital/Liquidity Phase-in Arrangement Schedule.
 * 	The References are:
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

public class BaselPhaseInArrangements
{

	private static final void DisplayLeverageRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String leverageRatio =
			"\t| Leverage                                                  => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			leverageRatio = leverageRatio +
				FormatUtil.FormatDouble (capitalMetricsStandard.leverageRatio(), 1, 3, 100.) + "% |";
		}

		System.out.println (leverageRatio + "|");
	}

	private static final void MinimumCommonEquityCapitalRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String minimumCommonEquityCapitalRatio =
			"\t| Minimum Common Equity Capital Ratio                       => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			minimumCommonEquityCapitalRatio = minimumCommonEquityCapitalRatio +
				FormatUtil.FormatDouble (capitalMetricsStandard.commonEquityRatio(), 1, 3, 100.) + "% |";
		}

		System.out.println (minimumCommonEquityCapitalRatio + "|");
	}

	private static final void CapitalConservationBufferRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String capitalConservationBufferRatio =
			"\t| Capital Conservation Ratio                                => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			capitalConservationBufferRatio = capitalConservationBufferRatio +
				FormatUtil.FormatDouble (capitalMetricsStandard.conservationBufferRatio(), 1, 3, 100.) + "% |";
		}

		System.out.println (capitalConservationBufferRatio + "|");
	}

	private static final void MinimumCommonEquityPlusCapitalConservationBufferRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String minimumCommonEquityPlusCapitalConservationBufferRatio =
			"\t| Minimum Common Equity + Capital Conservation Buffer Ratio => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			minimumCommonEquityPlusCapitalConservationBufferRatio =
				minimumCommonEquityPlusCapitalConservationBufferRatio + FormatUtil.FormatDouble
					(capitalMetricsStandard.commonEquityPlusConservationBufferRatio(), 1, 3, 100.) + "% |";
		}

		System.out.println (minimumCommonEquityPlusCapitalConservationBufferRatio + "|");
	}

	private static final void PhaseInOfDeductionsFromCET1 (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String phaseInOfDeductionsFromCET1 =
			"\t| Phase-in of Deductions from CET1                          => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			phaseInOfDeductionsFromCET1 = phaseInOfDeductionsFromCET1 +
				FormatUtil.FormatDouble (capitalMetricsStandard.cet1DeductionsPhaseIn(), 3, 1, 100.) + "% |";
		}

		System.out.println (phaseInOfDeductionsFromCET1 + "|");
	}

	private static final void MinimumTier1CapitalRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String minimumTier1CapitalRatio =
			"\t| Minimum Tier 1 Capital Ratio                              => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			minimumTier1CapitalRatio = minimumTier1CapitalRatio +
				FormatUtil.FormatDouble (capitalMetricsStandard.tier1Ratio(), 1, 3, 100.) + "% |";
		}

		System.out.println (minimumTier1CapitalRatio + "|");
	}

	private static final void MinimumTotalCapitalRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String minimumTotalCapitalRatio =
			"\t| Minimum Total Capital Ratio                               => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			minimumTotalCapitalRatio = minimumTotalCapitalRatio +
				FormatUtil.FormatDouble (capitalMetricsStandard.totalRatio(), 1, 3, 100.) + "% |";
		}

		System.out.println (minimumTotalCapitalRatio + "|");
	}

	private static final void MinimumTotalCapitalPlusConservationBufferRatio (
		final CapitalMetricsStandard[] capitalMetricsStandardArray)
	{
		String minimumTotalCapitalPlusConservationBufferRatio =
			"\t| Minimum Total Capital Plus Conservation Buffer Ratio      => ";

		for (CapitalMetricsStandard capitalMetricsStandard : capitalMetricsStandardArray)
		{
			minimumTotalCapitalPlusConservationBufferRatio = minimumTotalCapitalPlusConservationBufferRatio +
				FormatUtil.FormatDouble (capitalMetricsStandard.totalPlusConservationBufferRatio(), 1, 3, 100.) + "% |";
		}

		System.out.println (minimumTotalCapitalPlusConservationBufferRatio + "|");
	}

	private static final void LiquidityCoverageRatioMinimumRequirement (
		final LiquidityMetrics[] liquidityMetricsArray)
	{
		String liquidityCoverageRatioMinimumRequirement =
			"\t| Liquidity Coverage Ratio - Minimum Requirement => ";

		for (LiquidityMetrics liquidityMetrics : liquidityMetricsArray)
		{
			liquidityCoverageRatioMinimumRequirement = liquidityCoverageRatioMinimumRequirement +
				FormatUtil.FormatDouble (liquidityMetrics.liquidityCoverageRatio(), 3, 1, 100.) + "% |";
		}

		System.out.println (liquidityCoverageRatioMinimumRequirement + "|");
	}

	private static final void NetStableFundingRatio (
		final LiquidityMetrics[] liquidityMetricsArray)
	{
		String netStableFundingRatio = "\t| Net Stable Funding Ratio                       => ";

		for (LiquidityMetrics liquidityMetrics : liquidityMetricsArray)
		{
			netStableFundingRatio = netStableFundingRatio +
				FormatUtil.FormatDouble (liquidityMetrics.netStableFundingRatio(), 3, 1, 100.) + "% |";
		}

		System.out.println (netStableFundingRatio + "|");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		CapitalMetricsStandard[] capitalMetricsStandardArray = {
			CapitalMetricsStandard.Basel_III_2013(),
			CapitalMetricsStandard.Basel_III_2014(),
			CapitalMetricsStandard.Basel_III_2015(),
			CapitalMetricsStandard.Basel_III_2016(),
			CapitalMetricsStandard.Basel_III_2017(),
			CapitalMetricsStandard.Basel_III_2018(),
			CapitalMetricsStandard.Basel_III_2019(),
			CapitalMetricsStandard.US_SIFI(),
			CapitalMetricsStandard.US_SIFI_BHC()
		};

		LiquidityMetrics[] liquidityMetricsArray = {
			LiquidityMetrics.Basel_III_2015(),
			LiquidityMetrics.Basel_III_2016(),
			LiquidityMetrics.Basel_III_2017(),
			LiquidityMetrics.Basel_III_2018(),
			LiquidityMetrics.Basel_III_2019()
		};

		System.out.println
			("\t|----------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println
			("\t|                                                            BASEL III Phase-in Arrangements - Capital                                         ||");

		System.out.println
			("\t|----------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println (
			"\t|                           Year                            =>   2013  |  2014  |  2015  |  2016  |  2017  |  2018  |  2019  |  SIFI  |  BHC   ||"
		);

		System.out.println
			("\t|----------------------------------------------------------------------------------------------------------------------------------------------||");

		DisplayLeverageRatio (capitalMetricsStandardArray);

		MinimumCommonEquityCapitalRatio (capitalMetricsStandardArray);

		CapitalConservationBufferRatio (capitalMetricsStandardArray);

		MinimumCommonEquityPlusCapitalConservationBufferRatio (capitalMetricsStandardArray);

		PhaseInOfDeductionsFromCET1 (capitalMetricsStandardArray);

		MinimumTier1CapitalRatio (capitalMetricsStandardArray);

		MinimumTotalCapitalRatio (capitalMetricsStandardArray);

		MinimumTotalCapitalPlusConservationBufferRatio (capitalMetricsStandardArray);

		System.out.println
			("\t|----------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println
			("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println
			("\t|                          BASEL III Phase-in Arrangements - Liquidity                          ||");

		System.out.println
			("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println (
			"\t|                      Year                      =>   2015  |  2016  |  2017  |  2018  |  2019  ||"
		);

		System.out.println
			("\t|-----------------------------------------------------------------------------------------------||");

		LiquidityCoverageRatioMinimumRequirement (liquidityMetricsArray);

		NetStableFundingRatio (liquidityMetricsArray);

		System.out.println
			("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
