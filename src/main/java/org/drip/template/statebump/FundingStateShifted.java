
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>FundingStateShifted</i> generates a Sequence of Tenor Bumped Funding Curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md">Pricing/Risk Templates for Fixed Income Component Products</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/statebump/README.md">Shifted Latent State Construction Template</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingStateShifted {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "USD";
		double dblBump = 0.0001;
		boolean bIsBumpProportional = false;

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		String[] astrDepositMaturityTenor = new String[] {
			"01D",
			"02D",
			"07D",
			"14D",
			"30D",
			"60D"
		};

		double[] adblDepositQuote = new double[] {
			0.0013,		//  1D
			0.0017,		//  2D
			0.0017,		//  7D
			0.0018,		// 14D
			0.0020,		// 30D
			0.0023		// 60D
		};

		double[] adblFuturesQuote = new double[] {
			0.0027,
			0.0032,
			0.0041,
			0.0054,
			0.0077,
			0.0104,
			0.0134,
			0.0160
		};

		String[] astrFixFloatMaturityTenor = new String[] {
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

		double[] adblFixFloatQuote = new double[] {
			0.0166,		//   4Y
			0.0206,		//   5Y
			0.0241,		//   6Y
			0.0269,		//   7Y
			0.0292,		//   8Y
			0.0311,		//   9Y
			0.0326,		//  10Y
			0.0340,		//  11Y
			0.0351,		//  12Y
			0.0375,		//  15Y
			0.0393,		//  20Y
			0.0402,		//  25Y
			0.0407,		//  30Y
			0.0409,		//  40Y
			0.0409		//  50Y
		};

		Map<String, MergedDiscountForwardCurve> mapFundingCurve = LatentMarketStateBuilder.BumpedFundingCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			adblFuturesQuote,
			"ForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate",
			LatentMarketStateBuilder.SMOOTH,
			dblBump,
			bIsBumpProportional
		);

		Component[] aDepositComp = OTCInstrumentBuilder.FundingDeposit (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor
		);

		Component[] aFuturesComp = ExchangeInstrumentBuilder.ForwardRateFuturesPack (
			dtSpot,
			adblFuturesQuote.length,
			strCurrency
		);

		Component[] aFixFloatComp = OTCInstrumentBuilder.FixFloatStandard (
			dtSpot,
			strCurrency,
			"ALL",
			astrFixFloatMaturityTenor,
			"MAIN",
			0.
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		System.out.println ("\n\t|-------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : mapFundingCurve.entrySet()) {
			String strKey = meFunding.getKey();

			if (!strKey.startsWith ("deposit")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (meFunding.getValue());

			System.out.print ("\t|  [" + meFunding.getKey() + "] => ");

			for (Component comp : aDepositComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"ForwardRate"
					), 1, 4, 1.) + " |");

			System.out.print ("|\n");
		}

		System.out.println ("\t|-------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : mapFundingCurve.entrySet()) {
			String strKey = meFunding.getKey();

			if (!strKey.startsWith ("futures")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (meFunding.getValue());

			System.out.print ("\t|  [" + meFunding.getKey() + "] => ");

			for (Component comp : aFuturesComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"ForwardRate"
					), 1, 4, 1.) + " |");

			System.out.print ("|\n");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|--------------------------------------------------------------------------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : mapFundingCurve.entrySet()) {
			String strKey = meFunding.getKey();

			if (!strKey.startsWith ("fixfloat")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (meFunding.getValue());

			System.out.print ("\t|  [" + meFunding.getKey() + "] => ");

			for (Component comp : aFixFloatComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"SwapRate"
					), 1, 4, 1.) + " |");

			System.out.print ("|\n");
		}

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------||");

		CurveSurfaceQuoteContainer csqcBase = new CurveSurfaceQuoteContainer();

		csqcBase.setFundingState (mapFundingCurve.get ("Base"));

		CurveSurfaceQuoteContainer csqcBump = new CurveSurfaceQuoteContainer();

		csqcBump.setFundingState (mapFundingCurve.get ("Bump"));

		for (Component comp : aDepositComp)
			System.out.println (
				"\t| FORWARD RATE  => " +
				comp.maturityDate() + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"ForwardRate"
				), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBump,
					null,
					"ForwardRate"
				), 1, 6, 1.) + " ||"
			);

		for (Component comp : aFuturesComp)
			System.out.println (
				"\t| FORWARD RATE  => " +
				comp.maturityDate() + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"ForwardRate"
				), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBump,
					null,
					"ForwardRate"
				), 1, 6, 1.) + " ||"
			);

		for (Component comp : aFixFloatComp)
			System.out.println (
				"\t|  SWAP   RATE  => " +
				comp.maturityDate() + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"SwapRate"
				), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBump,
					null,
					"SwapRate"
				), 1, 6, 1.) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------||");
	}
}
