
package org.drip.sample.treasuryfuturesrisk;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.sensitivity.TenorDurationNodeMetrics;
import org.drip.market.exchange.*;
import org.drip.market.issue.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.product.TreasuryFuturesAPI;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>IK1KeyRateDuration</i> demonstrates the Computation of the Key Rate Duration for the IK1 Treasury
 * 	Futures.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/treasuryfuturesrisk/README.md">Treasury Futures Key Rate Duration</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IK1KeyRateDuration {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strFuturesCode = "IK1";

		TreasuryFuturesContract tfc = TreasuryFuturesContractContainer.TreasuryFuturesContract (strFuturesCode);

		String strTreasuryCode = tfc.code();

		TreasurySetting ts = TreasurySettingContainer.TreasurySetting (strTreasuryCode);

		String strTreasuryMarkLocation = "C:\\DROP\\Daemons\\Transforms\\TreasuryYieldMarks\\" +
			TreasurySettingContainer.CurrencyBenchmarkCode (ts.currency()) + "BenchmarksReconstituted.csv";
		String strPrintLocation = "C:\\DROP\\Daemons\\Transforms\\TreasuryFuturesCloses\\" +
			strFuturesCode + "ClosesReconstitutor.csv";

		CSVGrid csvGridTreasuryMark = CSVParser.StringGrid (
			strTreasuryMarkLocation,
			true
		);

		JulianDate[] adtTreasuryMark = csvGridTreasuryMark.dateArrayAtColumn (0);

		double[] adblYield02Y = csvGridTreasuryMark.doubleArrayAtColumn (1);

		double[] adblYield03Y = csvGridTreasuryMark.doubleArrayAtColumn (2);

		double[] adblYield04Y = csvGridTreasuryMark.doubleArrayAtColumn (3);

		double[] adblYield05Y = csvGridTreasuryMark.doubleArrayAtColumn (4);

		double[] adblYield07Y = csvGridTreasuryMark.doubleArrayAtColumn (5);

		double[] adblYield10Y = csvGridTreasuryMark.doubleArrayAtColumn (6);

		double[] adblYield20Y = csvGridTreasuryMark.doubleArrayAtColumn (7);

		double[] adblYield30Y = csvGridTreasuryMark.doubleArrayAtColumn (8);

		Map<JulianDate, Double> mapTreasuryMark02Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark03Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark04Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark05Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark07Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark10Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark20Y = new TreeMap<JulianDate, Double>();

		Map<JulianDate, Double> mapTreasuryMark30Y = new TreeMap<JulianDate, Double>();

		for (int i = 0; i < adtTreasuryMark.length; ++i) {
			mapTreasuryMark02Y.put (adtTreasuryMark[i], adblYield02Y[i]);

			mapTreasuryMark03Y.put (adtTreasuryMark[i], adblYield03Y[i]);

			mapTreasuryMark04Y.put (adtTreasuryMark[i], adblYield04Y[i]);

			mapTreasuryMark05Y.put (adtTreasuryMark[i], adblYield05Y[i]);

			mapTreasuryMark07Y.put (adtTreasuryMark[i], adblYield07Y[i]);

			mapTreasuryMark10Y.put (adtTreasuryMark[i], adblYield10Y[i]);

			mapTreasuryMark20Y.put (adtTreasuryMark[i], adblYield20Y[i]);

			mapTreasuryMark30Y.put (adtTreasuryMark[i], adblYield30Y[i]);
		}

		CSVGrid csvGrid = CSVParser.StringGrid (
			strPrintLocation,
			true
		);

		JulianDate[] adtSpot = csvGrid.dateArrayAtColumn (0);

		double[] adblCleanPrice = csvGrid.doubleArrayAtColumn (2);

		double[] adblCoupon = csvGrid.doubleArrayAtColumn (3);

		JulianDate[] adtEffective = csvGrid.dateArrayAtColumn (4);

		JulianDate[] adtMaturity = csvGrid.dateArrayAtColumn (5);

		JulianDate[] adtExpiry = csvGrid.dateArrayAtColumn (6);

		int iNumCompute = adtSpot.length;
		JulianDate[] adtEffectiveCompute = new JulianDate[iNumCompute];
		JulianDate[] adtMaturityCompute = new JulianDate[iNumCompute];
		double[] adblCouponCompute = new double[iNumCompute];
		JulianDate[] adtExpiryCompute = new JulianDate[iNumCompute];
		JulianDate[] adtSpotCompute = new JulianDate[iNumCompute];
		double[] adblCleanPriceCompute = new double[iNumCompute];
		double[][] aadblComputeYield = new double[iNumCompute][8];
		String[] astrBenchmarkTenor = new String[] {
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		for (int i = 0; i < iNumCompute; ++i) {
			adtEffectiveCompute[i] = adtEffective[i];
			adtMaturityCompute[i] = adtMaturity[i];
			adblCouponCompute[i] = adblCoupon[i];
			adtExpiryCompute[i] = adtExpiry[i];
			adtSpotCompute[i] = adtSpot[i];
			adblCleanPriceCompute[i] = adblCleanPrice[i];

			aadblComputeYield[i][0] = mapTreasuryMark02Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][1] = mapTreasuryMark03Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][2] = mapTreasuryMark04Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][3] = mapTreasuryMark05Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][4] = mapTreasuryMark07Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][5] = mapTreasuryMark10Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][6] = mapTreasuryMark20Y.get (adtSpotCompute[i]);

			aadblComputeYield[i][7] = mapTreasuryMark30Y.get (adtSpotCompute[i]);
		}

		List<TenorDurationNodeMetrics> lsTDNM = TreasuryFuturesAPI.HorizonKeyRateDuration (
			strTreasuryCode,
			adtEffectiveCompute,
			adtMaturityCompute,
			adblCouponCompute,
			adtExpiryCompute,
			adtSpotCompute,
			adblCleanPriceCompute,
			astrBenchmarkTenor,
			aadblComputeYield
		);

		System.out.println ("SpotDate,ExpiryDate,CTDName,SpotCTDCleanPrice,ExpiryCTDCleanPrice,SpotGSpread,ExpiryGSpread,SpotYield,ExpiryYield,Parallel,2Y,3Y,4Y,5Y,7Y,10Y,20Y,30Y");

		for (TenorDurationNodeMetrics tdnm : lsTDNM) {
			String strTDNM =
				tdnm.dateSnap() + "," +
				tdnm.date ("ExpiryDate") + "," +
				tdnm.c1 ("CTDName") + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("SpotCTDCleanPrice"), 1, 5, 100.) + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("ExpiryCTDCleanPrice"), 1, 5, 100.) + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("SpotGSpread"), 1, 1, 10000.) + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("ExpiryGSpread"), 1, 1, 10000.) + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("SpotYield"), 1, 4, 100.) + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("ExpiryYield"), 1, 4, 100.) + "," +
				FormatUtil.FormatDouble (tdnm.r1 ("ParallelKRD"), 1, 4, 1.);

			for (Map.Entry<String, Double> meTDNM : tdnm.krdMap().entrySet())
				strTDNM += "," + FormatUtil.FormatDouble (meTDNM.getValue(), 1, 4, 1.);

			System.out.println (strTDNM);
		}

		EnvManager.TerminateEnv();
	}
}
