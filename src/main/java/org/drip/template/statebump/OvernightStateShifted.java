
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>OvernightStateShifted</i> demonstrates the Generation of the Tenor Bumped Overnight Curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/statebump">State Bump</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OvernightStateShifted {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "EUR";
		double dblBump = 0.0001;
		boolean bIsBumpProportional = false;

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		String[] astrDepositMaturityTenor = new String[] {
			"01D",
			// "02D",
			"03D"
		};

		double[] adblDepositQuote = new double[] {
			0.0004,		// 1D
			// 0.0004,		// 2D
			0.0004		// 3D
		};

		String[] astrShortEndOISMaturityTenor = new String[] {
			"01W",
			"02W",
			"03W",
			"01M"
		};

		double[] adblShortEndOISQuote = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		String[] astrOISFuturesEffectiveTenor = new String[] {
			"01M",
			"02M",
			"03M",
			"04M",
			"05M"
		};

		String[] astrOISFuturesMaturityTenor = new String[] {
			"1M",
			"1M",
			"1M",
			"1M",
			"1M"
		};

		double[] adblOISFuturesQuote = new double[] {
			 0.00046,    //   1M x 1M
			 0.00016,    //   2M x 1M
			-0.00007,    //   3M x 1M
			-0.00013,    //   4M x 1M
			-0.00014     //   5M x 1M
		};

		String[] astrLongEndOISMaturityTenor = new String[] {
			"15M",
			"18M",
			"21M",
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
			"30Y"
		};

		double[] adblLongEndOISQuote = new double[] {
			0.00002,    //  15M
			0.00008,    //  18M
			0.00021,    //  21M
			0.00036,    //   2Y
			0.00127,    //   3Y
			0.00274,    //   4Y
			0.00456,    //   5Y
			0.00647,    //   6Y
			0.00827,    //   7Y
			0.00996,    //   8Y
			0.01147,    //   9Y
			0.01280,    //  10Y
			0.01404,    //  11Y
			0.01516,    //  12Y
			0.01764,    //  15Y
			0.01939,    //  20Y
			0.02003,    //  25Y
			0.02038     //  30Y
		};

		Map<String, MergedDiscountForwardCurve> bumpedOvernightCurve = LatentMarketStateBuilder.BumpedOvernightCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			astrShortEndOISMaturityTenor,
			adblShortEndOISQuote,
			"SwapRate",
			astrOISFuturesEffectiveTenor,
			astrOISFuturesMaturityTenor,
			adblOISFuturesQuote,
			"SwapRate",
			astrLongEndOISMaturityTenor,
			adblLongEndOISQuote,
			"SwapRate",
			LatentMarketStateBuilder.SMOOTH,
			dblBump,
			bIsBumpProportional
		);

		Component[] aDepositComp = OTCInstrumentBuilder.OvernightDeposit (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor
		);

		Component[] aShortEndOISComp = OTCInstrumentBuilder.OISFixFloat (
			dtSpot,
			strCurrency,
			astrShortEndOISMaturityTenor,
			adblShortEndOISQuote,
			false
		);

		Component[] aOISFuturesComp = OTCInstrumentBuilder.OISFixFloatFutures (
			dtSpot,
			strCurrency,
			astrOISFuturesEffectiveTenor,
			astrOISFuturesMaturityTenor,
			adblOISFuturesQuote,
			false
		);

		Component[] aLongEndOISComp = OTCInstrumentBuilder.OISFixFloat (
			dtSpot,
			strCurrency,
			astrLongEndOISMaturityTenor,
			adblLongEndOISQuote,
			false
		);

		System.out.println ("\n\t|----------------------------------------------||");

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : bumpedOvernightCurve.entrySet()) {
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

		System.out.println ("\t|----------------------------------------------||");

		System.out.println ("\n\t|---------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : bumpedOvernightCurve.entrySet()) {
			String strKey = meFunding.getKey();

			if (!strKey.startsWith ("shortendois")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (meFunding.getValue());

			System.out.print ("\t|  [" + meFunding.getKey() + "] => ");

			for (Component comp : aShortEndOISComp)
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

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : bumpedOvernightCurve.entrySet()) {
			String strKey = meFunding.getKey();

			if (!strKey.startsWith ("oisfutures")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (meFunding.getValue());

			System.out.print ("\t|  [" + meFunding.getKey() + "] => ");

			for (Component comp : aOISFuturesComp)
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

		System.out.println ("\t|-----------------------------------------------------------------------------||");

		System.out.println ("\n\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : bumpedOvernightCurve.entrySet()) {
			String strKey = meFunding.getKey();

			if (!strKey.startsWith ("longendois")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (meFunding.getValue());

			System.out.print ("\t|  [" + meFunding.getKey() + "] => ");

			for (Component comp : aLongEndOISComp)
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

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------||");

		CurveSurfaceQuoteContainer csqcBase = new CurveSurfaceQuoteContainer();

		csqcBase.setFundingState (bumpedOvernightCurve.get ("Base"));

		CurveSurfaceQuoteContainer csqcBump = new CurveSurfaceQuoteContainer();

		csqcBump.setFundingState (bumpedOvernightCurve.get ("Bump"));

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

		for (Component comp : aShortEndOISComp)
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

		for (Component comp : aOISFuturesComp)
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

		for (Component comp : aLongEndOISComp)
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
