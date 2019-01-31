
package org.drip.sample.intexfeed;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Helper;
import org.drip.market.otc.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
// import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.service.template.OTCInstrumentBuilder;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>BrokenDateLIBOREUR</i> generates the EUR LIBOR Forward's over Monthly Increments with Maturity up to 60
 * Years for different Forward Tenors.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/intexfeed/README.md">Intex Feed Inputs</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateLIBOREUR {

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] depositTenorArray = new String[] {
			"1W",
			"2W",
			"1M",
			"2M",
			"3M",
			"6M",
			"9M"
		};

		double[] depositQuoteArray = new double[] {
			-0.00379, // 1W
			-0.00372, // 2W
			-0.00370, // 1M
			-0.00341, // 2M
			-0.00329, // 3M
			-0.00271, // 6M
			-0.00221  // 9M
		};

		String[] fixFloatMaturityTenorArray = new String[] {
			"18M",
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
			"35Y",
			"40Y",
			"45Y",
			"50Y"
		};

		double[] fixFloatQuoteArray = new double[] {
			-0.002040, // 18M
			-0.001190, // 02Y
			 0.000855, // 03Y
			 0.002960, // 04Y
			 0.004990, // 05Y
			 0.006630, // 06Y
			 0.008090, // 07Y
			 0.009360, // 08Y
			 0.010490, // 09Y
			 0.011300, // 10Y
			 0.012360, // 11Y
			 0.013150, // 12Y
			 0.014890, // 15Y
			 0.016300, // 20Y
			 0.016690, // 25Y
			 0.016740, // 30Y
			 0.016680, // 35Y
			 0.016530, // 40Y
			 0.015944, // 45Y
			 0.016090, // 50Y
		};

		int depositCount = depositTenorArray.length;
		int calibComponentCount = depositCount + fixFloatMaturityTenorArray.length;
		CalibratableComponent[] calibComponentArray = new CalibratableComponent[calibComponentCount];
		String[] calibMeasureArray = new String[calibComponentCount];
		double[] calibQuoteArray = new double[calibComponentCount];

		SingleStreamComponent[] depositArray = OTCInstrumentBuilder.FundingDeposit (
			dtSpot,
			strCurrency,
			depositTenorArray
		);

		FixFloatComponent[] fixFloatArray = OTCInstrumentBuilder.FixFloatStandard (
			dtSpot,
			strCurrency,
			"ALL",
			fixFloatMaturityTenorArray,
			"MAIN",
			0.
		);

		for (int i = 0; i < depositCount; ++i)
		{
			calibMeasureArray[i] = "Rate";
			calibQuoteArray[i] = depositQuoteArray[i];
			calibComponentArray[i] = depositArray[i];
		}

		for (int i = depositCount; i < calibComponentCount; ++i)
		{
			calibMeasureArray[i] = "SwapRate";
			calibComponentArray[i] = fixFloatArray[i - depositCount];
			calibQuoteArray[i] = fixFloatQuoteArray[i - depositCount];
		}

		return ScenarioDiscountCurveBuilder.NonlinearBuild (
			dtSpot,
			"USD",
			calibComponentArray,
			calibQuoteArray,
			calibMeasureArray,
			null
		);

		/* return LatentMarketStateBuilder.SingleStretchShapePreservingFundingCurve (
			dtSpot,
			strCurrency,
			depositTenorArray,
			depositQuoteArray,
			"ForwardRate",
			null, // adblFuturesQuote,
			null, // "ForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate"
		); */
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.OCTOBER,
			5
		);

		int iNumMonth = 720;
		String strCurrency = "USD";
		String[] astrForwardTenor = new String[] {
			 "1M",
			 "2M",
			 "3M",
			 "6M",
			"12M"
		};

		FixedFloatSwapConvention ffsc = IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency);

		ForwardLabel forwardLabel = ffsc.floatStreamConvention().floaterIndex();

		String strLIBORDayCount = forwardLabel.floaterIndex().dayCount();

		int iLIBORFreq = Helper.TenorToFreq (forwardLabel.tenor());

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency
		);

		System.out.println
			("SpotDate,ViewDate,ForwardTenor,ViewDiscountFactor,ViewForwardDiscountFactor, ForwardRate");

		for (int i = 0; i < iNumMonth; ++i) {
			JulianDate dtView = 0 == i ? dtSpot : dtSpot.addMonths (i);

			double dblDFView = mdfc.df (dtView);

			for (int j = 0; j < astrForwardTenor.length; ++j) {
				JulianDate dtForward = dtView.addTenor (astrForwardTenor[j]);

				double dblDFForward = mdfc.df (dtForward);

				double dblForwardRate = Helper.DF2Yield (
					iLIBORFreq,
					dblDFForward / dblDFView,
					Convention.YearFraction (
						dtView.julian(),
						dtForward.julian(),
						strLIBORDayCount,
						false,
						null,
						strCurrency
					)
				);

				System.out.println (
					dtSpot + "," +
					dtView + "," +
					astrForwardTenor[j] + "," +
					FormatUtil.FormatDouble (dblDFView, 1, 8, 1.) + "," +
					FormatUtil.FormatDouble (dblDFForward, 1, 8, 1.) + "," +
					FormatUtil.FormatDouble (dblForwardRate, 1, 8, 100.) + "%"
				);
			}
		}

		EnvManager.TerminateEnv();
	}
}
