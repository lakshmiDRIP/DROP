
package org.drip.sample.treasuryfuturesrisk;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.sensitivity.TenorDurationNodeMetrics;
import org.drip.market.exchange.TreasuryFuturesContractContainer;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.product.TreasuryFuturesAPI;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * FV1KeyRateDuration demonstrates the Computation of the Key Rate Duration for the FV1 Treasury Futures.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FV1KeyRateDuration {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strFuturesCode = "FV1";

		String strTreasuryCode = TreasuryFuturesContractContainer.TreasuryFuturesContract (strFuturesCode).code();

		String strTreasuryMarkLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Transforms\\TreasuryYieldMarks\\" + strTreasuryCode +
			"BenchmarksReconstituted.csv";
		String strPrintLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Transforms\\TreasuryFuturesCloses\\" +
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
	}
}
