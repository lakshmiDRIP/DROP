
package org.drip.sample.credithistorical;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.state.CreditCurveMetrics;
import org.drip.service.env.EnvManager;
import org.drip.service.state.CreditCurveAPI;

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
 * CDXNAIGS215YMetrics generates the Historical Credit Survival/Recovery Metrics for the Index Contract CDX
 *  NA IG S21 5Y.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDXNAIGS215YMetrics {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iSeries = 21;
		String strClosesLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Transforms\\CreditCDXMarks\\CDXNAIGS" + iSeries + "5YReconstitutor.csv";
		String[] astrForTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
		};
		String[] astrFundingFixingMaturityTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"40Y",
			"50Y"
		};

		CSVGrid csvGrid = CSVParser.StringGrid (
			strClosesLocation,
			true
		);

		JulianDate[] adtClose = csvGrid.dateArrayAtColumn (0);

		double[] adblFundingFixingQuote1Y = csvGrid.doubleArrayAtColumn (1);

		double[] adblFundingFixingQuote2Y = csvGrid.doubleArrayAtColumn (2);

		double[] adblFundingFixingQuote3Y = csvGrid.doubleArrayAtColumn (3);

		double[] adblFundingFixingQuote4Y = csvGrid.doubleArrayAtColumn (4);

		double[] adblFundingFixingQuote5Y = csvGrid.doubleArrayAtColumn (5);

		double[] adblFundingFixingQuote6Y = csvGrid.doubleArrayAtColumn (6);

		double[] adblFundingFixingQuote7Y = csvGrid.doubleArrayAtColumn (7);

		double[] adblFundingFixingQuote8Y = csvGrid.doubleArrayAtColumn (8);

		double[] adblFundingFixingQuote9Y = csvGrid.doubleArrayAtColumn (9);

		double[] adblFundingFixingQuote10Y = csvGrid.doubleArrayAtColumn (10);

		double[] adblFundingFixingQuote11Y = csvGrid.doubleArrayAtColumn (11);

		double[] adblFundingFixingQuote12Y = csvGrid.doubleArrayAtColumn (12);

		double[] adblFundingFixingQuote15Y = csvGrid.doubleArrayAtColumn (13);

		double[] adblFundingFixingQuote20Y = csvGrid.doubleArrayAtColumn (14);

		double[] adblFundingFixingQuote25Y = csvGrid.doubleArrayAtColumn (15);

		double[] adblFundingFixingQuote30Y = csvGrid.doubleArrayAtColumn (16);

		double[] adblFundingFixingQuote40Y = csvGrid.doubleArrayAtColumn (17);

		double[] adblFundingFixingQuote50Y = csvGrid.doubleArrayAtColumn (18);

		String[] astrFullCreditIndexName = csvGrid.stringArrayAtColumn (19);

		double[] adblCreditIndexQuotedSpread = csvGrid.doubleArrayAtColumn (20);

		int iNumClose = adtClose.length;
		JulianDate[] adtSpot = new JulianDate[iNumClose];
		double[][] aadblFundingFixingQuote = new double[iNumClose][18];

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];
			aadblFundingFixingQuote[i][0] = adblFundingFixingQuote1Y[i];
			aadblFundingFixingQuote[i][1] = adblFundingFixingQuote2Y[i];
			aadblFundingFixingQuote[i][2] = adblFundingFixingQuote3Y[i];
			aadblFundingFixingQuote[i][3] = adblFundingFixingQuote4Y[i];
			aadblFundingFixingQuote[i][4] = adblFundingFixingQuote5Y[i];
			aadblFundingFixingQuote[i][5] = adblFundingFixingQuote6Y[i];
			aadblFundingFixingQuote[i][6] = adblFundingFixingQuote7Y[i];
			aadblFundingFixingQuote[i][7] = adblFundingFixingQuote8Y[i];
			aadblFundingFixingQuote[i][8] = adblFundingFixingQuote9Y[i];
			aadblFundingFixingQuote[i][9] = adblFundingFixingQuote10Y[i];
			aadblFundingFixingQuote[i][10] = adblFundingFixingQuote11Y[i];
			aadblFundingFixingQuote[i][11] = adblFundingFixingQuote12Y[i];
			aadblFundingFixingQuote[i][12] = adblFundingFixingQuote15Y[i];
			aadblFundingFixingQuote[i][13] = adblFundingFixingQuote20Y[i];
			aadblFundingFixingQuote[i][14] = adblFundingFixingQuote25Y[i];
			aadblFundingFixingQuote[i][15] = adblFundingFixingQuote30Y[i];
			aadblFundingFixingQuote[i][16] = adblFundingFixingQuote40Y[i];
			aadblFundingFixingQuote[i][17] = adblFundingFixingQuote50Y[i];
			adblCreditIndexQuotedSpread[i] *= 10000.;
		}

		int i = 0;
		String strDump = "Date,QuotedSpread";

		for (String strForTenor : astrForTenor)
			strDump += ",SURVIVAL::" + strForTenor + ",RECOVERY::" + strForTenor;

		System.out.println (strDump);

		TreeMap<JulianDate, CreditCurveMetrics> mapCCM = CreditCurveAPI.HorizonMetrics (
			adtSpot,
			astrFundingFixingMaturityTenor,
			aadblFundingFixingQuote,
			astrFullCreditIndexName,
			adblCreditIndexQuotedSpread,
			astrForTenor
		);

		Set<JulianDate> setSpotDate = mapCCM.keySet();

		for (JulianDate dtSpot : setSpotDate) {
			strDump = dtSpot.toString() + "," + adblCreditIndexQuotedSpread[i++];

			CreditCurveMetrics ccmSpot = mapCCM.get (dtSpot);

			for (String strForTenor : astrForTenor) {
				JulianDate dtFor = dtSpot.addTenor (strForTenor);

				strDump += "," + ccmSpot.survivalProbability (dtFor) + "," + ccmSpot.recoveryRate (dtFor);
			}

			System.out.println (strDump);
		}
	}
}
