
package org.drip.sample.overnighthistorical;

import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.state.FundingCurveMetrics;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.state.OvernightCurveAPI;
import org.drip.service.template.LatentMarketStateBuilder;

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
 * GBPSmooth1MForward Generates the Historical GBP Smoothened Overnight Curve Native 1M Compounded Forward
 *  Rate.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GBPSmooth1MForward {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "GBP";
		String strClosesLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Transforms\\OvernightOISMarks\\" + strCurrency + "OISSmoothReconstitutor.csv";
		String[] astrForTenor = new String[] {
			"1M"
		};
		String[] astrInTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M",
			"6M",
			"9M",
			"1Y",
			"18M",
			"2Y",
			"3Y",
			"4Y",
			"5Y"
		};
		String[] astrOISMaturityTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M",
			"6M",
			"9M",
			"1Y",
			"18M",
			"2Y",
			"3Y",
			"4Y",
			"5Y"
		};

		CSVGrid csvGrid = CSVParser.StringGrid (
			strClosesLocation,
			true
		);

		JulianDate[] adtClose = csvGrid.dateArrayAtColumn (0);

		double[] adblOISQuote1W = csvGrid.doubleArrayAtColumn (1);

		double[] adblOISQuote2W = csvGrid.doubleArrayAtColumn (2);

		double[] adblOISQuote3W = csvGrid.doubleArrayAtColumn (3);

		double[] adblOISQuote1M = csvGrid.doubleArrayAtColumn (4);

		double[] adblOISQuote2M = csvGrid.doubleArrayAtColumn (5);

		double[] adblOISQuote3M = csvGrid.doubleArrayAtColumn (6);

		double[] adblOISQuote4M = csvGrid.doubleArrayAtColumn (7);

		double[] adblOISQuote5M = csvGrid.doubleArrayAtColumn (8);

		double[] adblOISQuote6M = csvGrid.doubleArrayAtColumn (9);

		double[] adblOISQuote9M = csvGrid.doubleArrayAtColumn (10);

		double[] adblOISQuote1Y = csvGrid.doubleArrayAtColumn (11);

		double[] adblOISQuote18M = csvGrid.doubleArrayAtColumn (12);

		double[] adblOISQuote2Y = csvGrid.doubleArrayAtColumn (13);

		double[] adblOISQuote3Y = csvGrid.doubleArrayAtColumn (14);

		double[] adblOISQuote4Y = csvGrid.doubleArrayAtColumn (15);

		double[] adblOISQuote5Y = csvGrid.doubleArrayAtColumn (16);

		int iNumClose = adtClose.length;
		JulianDate[] adtSpot = new JulianDate[iNumClose];
		double[][] aadblOISQuote = new double[iNumClose][16];

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];
			aadblOISQuote[i][0] = adblOISQuote1W[i];
			aadblOISQuote[i][1] = adblOISQuote2W[i];
			aadblOISQuote[i][2] = adblOISQuote3W[i];
			aadblOISQuote[i][3] = adblOISQuote1M[i];
			aadblOISQuote[i][4] = adblOISQuote2M[i];
			aadblOISQuote[i][5] = adblOISQuote3M[i];
			aadblOISQuote[i][6] = adblOISQuote4M[i];
			aadblOISQuote[i][7] = adblOISQuote5M[i];
			aadblOISQuote[i][8] = adblOISQuote6M[i];
			aadblOISQuote[i][9] = adblOISQuote9M[i];
			aadblOISQuote[i][10] = adblOISQuote1Y[i];
			aadblOISQuote[i][11] = adblOISQuote18M[i];
			aadblOISQuote[i][12] = adblOISQuote2Y[i];
			aadblOISQuote[i][13] = adblOISQuote3Y[i];
			aadblOISQuote[i][14] = adblOISQuote4Y[i];
			aadblOISQuote[i][15] = adblOISQuote5Y[i];
		}

		String strDump = "Date";

		for (String strInTenor : astrInTenor) {
			for (String strForTenor : astrForTenor)
				strDump += "," + strInTenor + strForTenor;
		}

		System.out.println (strDump);

		Map<JulianDate, FundingCurveMetrics> mapFCM = OvernightCurveAPI.HorizonMetrics (
			adtSpot,
			astrOISMaturityTenor,
			aadblOISQuote,
			astrInTenor,
			astrForTenor,
			strCurrency,
			LatentMarketStateBuilder.SMOOTH
		);

		for (int i = 0; i < iNumClose; ++i) {
			FundingCurveMetrics fcm = mapFCM.get (adtSpot[i]);

			strDump = adtSpot[i].toString();

			for (String strInTenor : astrInTenor) {
				for (String strForTenor : astrForTenor)
					strDump += "," + FormatUtil.FormatDouble (
						fcm.nativeForwardRate (
							strInTenor,
							strForTenor
						), 1, 5, 100.
					);
			}

			System.out.println (strDump);
		}
	}
}
