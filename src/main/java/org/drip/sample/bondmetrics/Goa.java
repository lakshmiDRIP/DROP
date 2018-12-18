
package org.drip.sample.bondmetrics;

import org.drip.analytics.date.*;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.params.EmbeddedOptionSchedule;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>Goa</i> generates the Full Suite of Replication Metrics for Bond Goa.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondmetrics/README.md">Bond Analytics Metrics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Goa {

	private static final void SetEOS (
		final BondComponent bond,
		final EmbeddedOptionSchedule eosCall,
		final EmbeddedOptionSchedule eosPut)
		throws java.lang.Exception
	{
		if (null != eosPut) bond.setEmbeddedPutSchedule (eosPut);

		if (null != eosCall) bond.setEmbeddedCallSchedule (eosCall);
	}

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JULY,
			10
		);

		String[] astrDepositTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0130411 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01345,	// 98.655
			0.01470,	// 98.530
			0.01575,	// 98.425
			0.01660,	// 98.340
			0.01745,    // 98.255
			0.01845     // 98.155
		};

		String[] astrFixFloatTenor = new String[] {
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
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

		String[] astrGovvieTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.016410, //  2Y
			0.017863, //  3Y
			0.019030, //  4Y
			0.020035, //  5Y
			0.020902, //  6Y
			0.021660, //  7Y
			0.022307, //  8Y
			0.022879, //  9Y
			0.023363, // 10Y
			0.023820, // 11Y
			0.024172, // 12Y
			0.024934, // 15Y
			0.025581, // 20Y
			0.025906, // 25Y
			0.025973, // 30Y
			0.025838, // 40Y
			0.025560  // 50Y
		};

		double[] adblGovvieYield = new double[] {
			0.01219, //  1Y
			0.01391, //  2Y
			0.01590, //  3Y
			0.01937, //  5Y
			0.02200, //  7Y
			0.02378, // 10Y
			0.02677, // 20Y
			0.02927  // 30Y
		};

		String[] astrCreditTenor = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblCreditQuote = new double[] {
			 60.,	//  6M
			 68.,	//  1Y
			 88.,	//  2Y
			102.,	//  3Y
			121.,	//  4Y
			138.,	//  5Y
			168.,	//  7Y
			188.	// 10Y
		};

		double dblFX = 1.;
		int iSettleLag = 12;
		int iCouponFreq = 2;
		String strName = "Goa";
		double dblCleanPrice = 1.;
		double dblIssuePrice = 1.;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblCouponRate = 0.0000;
		String strTreasuryCode = "UST";
		double dblIssueAmount = 1.448e9;
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2000,
			11,
			3
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2030,
			11,
			15
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strName,
			strCurrency,
			strName,
			dblCouponRate,
			iCouponFreq,
			strCouponDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);

		SetEOS (
			bond,
			null,
			null
		);

		BondReplicator abr = BondReplicator.CorporateSenior (
			dblCleanPrice,
			dblIssuePrice,
			dblIssueAmount,
			dtSpot,
			astrDepositTenor,
			adblDepositQuote,
			adblFuturesQuote,
			astrFixFloatTenor,
			adblFixFloatQuote,
			dblSpreadBump,
			dblSpreadDurationMultiplier,
			strTreasuryCode,
			astrGovvieTenor,
			adblGovvieYield,
			astrCreditTenor,
			adblCreditQuote,
			dblFX,
			Double.NaN,
			iSettleLag,
			bond
		);

		BondReplicationRun abrr = abr.generateRun();

		System.out.println (abrr.display());

		EnvManager.TerminateEnv();
	}
}
