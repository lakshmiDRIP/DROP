
package org.drip.sample.forwardvolatility;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.market.otc.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.ValuationParams;
import org.drip.pricer.option.BlackScholesAlgorithm;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.fra.FRAStandardCapFloor;
import org.drip.product.params.LastTradingDateSetting;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.volatility.VolatilityCurve;

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
 * <i>CustomFRAVolatilityCurve</i> demonstrates the Construction of the FRA Volatility Curve from the FRACap
 * Quotes. The Marks and the Valuation References are sourced from:
 * 
 * <br><br>
 *  <ul>
 * 		<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics
 * 				<i>Mathematical Finance</i> <b>7 (2)</b> 127-155
 * 		</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/forwardvolatility/README.md">Forward Volatility Surface Builder</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CustomFRAVolatilityCurve {

	private static final FixFloatComponent OTCFixFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

	private static final CalibratableComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final int[] aiDay,
		final int iNumFuture,
		final String strCurrency)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[aiDay.length + iNumFuture];

		for (int i = 0; i < aiDay.length; ++i)
			aCalibComp[i] = SingleStreamComponentBuilder.Deposit (
				dtEffective,
				dtEffective.addBusDays (
					aiDay[i],
					strCurrency
				),
				ForwardLabel.Create (
					strCurrency,
					"3M"
				)
			);

		CalibratableComponent[] aEDF = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtEffective,
			iNumFuture,
			strCurrency
		);

		for (int i = aiDay.length; i < aiDay.length + iNumFuture; ++i)
			aCalibComp[i] = aEDF[i - aiDay.length];

		return aCalibComp;
	}

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aIRS[i] = OTCFixFloat (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

		return aIRS;
	}

	private static final MergedDiscountForwardCurve MakeDC (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		/*
		 * Construct the array of Deposit instruments and their quotes.
		 */

		CalibratableComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			new int[] {
				30,
				60,
				91,
				182,
				273
			},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {
			0.0668750,	//  30D
			0.0675000,	//  60D
			0.0678125,	//  91D
			0.0712500,	// 182D
			0.0750000	// 273D
		};

		String[] astrDepositManifestMeasure = new String[] {
			"ForwardRate", //  30D
			"ForwardRate", //  60D
			"ForwardRate", //  91D
			"ForwardRate", // 182D
			"ForwardRate"  // 273D
		};

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			0.08265,    //  2Y
			0.08550,    //  3Y
			0.08655,    //  4Y
			0.08770,    //  5Y
			0.08910,    //  7Y
			0.08920     // 10Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			"SwapRate",    //  2Y
			"SwapRate",    //  3Y
			"SwapRate",    //  4Y
			"SwapRate",    //  5Y
			"SwapRate",    //  7Y
			"SwapRate"     // 10Y
		};

		CalibratableComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new String[] {
				"2Y",
				"3Y",
				"4Y",
				"5Y",
				"7Y",
				"10Y"
			},
			adblSwapQuote
		);

		/*
		 * Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
		 */

		return ScenarioDiscountCurveBuilder.CubicKLKHyperbolicDFRateShapePreserver (
			"KLK_HYPERBOLIC_SHAPE_TEMPLATE",
			new ValuationParams (
				dtSpot,
				dtSpot,
				strCurrency
			),
			aDepositComp,
			adblDepositQuote,
			astrDepositManifestMeasure,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			false
		);
	}

	private static final FRAStandardCapFloor MakeCap (
		final JulianDate dtEffective,
		final ForwardLabel fri,
		final String strMaturityTenor,
		final String strManifestMeasure,
		final double dblStrike)
		throws Exception
	{
		ComposableFloatingUnitSetting cfus = new ComposableFloatingUnitSetting (
			fri.tenor(),
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			fri,
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cps = new CompositePeriodSetting (
			4,
			fri.tenor(),
			fri.currency(),
			null,
			1.,
			null,
			null,
			null,
			null
		);

		Stream floatStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective.julian(),
					fri.tenor(),
					strMaturityTenor,
					null
				),
				cps,
				cfus
			)
		);

		return new FRAStandardCapFloor (
			"FRA_CAP",
			floatStream,
			strManifestMeasure,
			true,
			dblStrike,
			new LastTradingDateSetting (
				LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY,
				"",
				Integer.MIN_VALUE
			),
			null,
			new BlackScholesAlgorithm()
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			1995,
			DateUtil.FEBRUARY,
			3
		);

		String strFRATenor = "3M";
		String strCurrency = "GBP";
		String strManifestMeasure = "ParForward";

		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strFRATenor
		);

		MergedDiscountForwardCurve dc = MakeDC (
			dtSpot,
			strCurrency
		);

		String[] astrMaturityTenor = new String[] {
			 "1Y",
			 "2Y",
			 "3Y",
			 "4Y",
			 "5Y",
			 "7Y",
			"10Y"
		};

		double[] adblATMStrike = new double[] {
			0.0788, //  "1Y",
			0.0839, // 	"2Y",
			0.0864, //  "3Y",
			0.0869, //  "4Y",
			0.0879, //  "5Y",
			0.0890, //  "7Y",
			0.0889  // "10Y"
		};

		double[] adblATMPrice = new double[] {
			0.0027, //  "1Y",
			0.0152, // 	"2Y",
			0.0267, //  "3Y",
			0.0400, //  "4Y",
			0.0546, //  "5Y",
			0.0835, //  "7Y"
			0.1078  // "10Y"
		};

		String[] astrCalibMeasure = new String[astrMaturityTenor.length];
		FRAStandardCapFloor[] aCap = new FRAStandardCapFloor[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			astrCalibMeasure[i] = "Price";

			aCap[i] = MakeCap (
				dtSpot,
				fri,
				astrMaturityTenor[i],
				strManifestMeasure,
				adblATMStrike[i]
			);
		}

		VolatilityCurve volCurve = ScenarioLocalVolatilityBuilder.NonlinearBuild (
			fri.fullyQualifiedName() + "::VOL",
			dtSpot,
			fri,
			aCap,
			adblATMPrice,
			astrCalibMeasure,
			dc,
			dc.nativeForwardCurve (strFRATenor),
			null
		);

		ForwardCurve fcNative = dc.nativeForwardCurve (strFRATenor);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			fcNative,
			null,
			null,
			null,
			null,
			null,
			null
		);

		mktParams.setForwardVolatility (volCurve);

		System.out.println ("\n\n\t|---------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                 ||");

		System.out.println ("\t|  GBP LIBOR Volatility Calibrations                                              ||");

		System.out.println ("\t|        - Forward Label                                                          ||");

		System.out.println ("\t|        - Cap Stream Start                                                       ||");

		System.out.println ("\t|        - Cap Stream End                                                         ||");

		System.out.println ("\t|        - Market Price                                                           ||");

		System.out.println ("\t|        - Recon Price                                                            ||");

		System.out.println ("\t|        - Flat Cap Volatility                                                    ||");

		System.out.println ("\t|        - Flat Forward Volatility                                                ||");

		System.out.println ("\t|                                                                                 ||");

		System.out.println ("\t|---------------------------------------------------------------------------------||");

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			Map<String, Double> mapMeasures = aCap[i].value (
				valParams,
				null,
				mktParams,
				null
			);

			System.out.println (
				"\t| " + aCap[i].forwardLabel().get ("DERIVED").fullyQualifiedName() +
				" [" + aCap[i].stream().effective() + " - " + aCap[i].stream().maturity() + "] => " +
				FormatUtil.FormatDouble (mapMeasures.get ("Price"), 1, 4, 1.) + " {" +
				FormatUtil.FormatDouble (adblATMPrice[i], 1, 4, 1.) + "} | " +
				FormatUtil.FormatDouble (mapMeasures.get ("FlatVolatility"), 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (volCurve.impliedVol (aCap[i].stream().maturity()), 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
