
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * DerivedForwardStateShifted demonstrates the Generation of Tenor-bumped Derived Forward State.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DerivedForwardStateShifted {

	private static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			"2D",
			"3D"
		};

		double[] adblDepositQuote = new double[] {
			0.0004,		// 1D
			0.0004,		// 2D
			0.0004		// 3D
		};

		String[] astrShortEndOISMaturityTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M"
		};

		double[] adblShortEndOISQuote = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		String[] astrOISFuturesEffectiveTenor = new String[] {
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
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

		return LatentMarketStateBuilder.SmoothOvernightCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"Rate",
			astrShortEndOISMaturityTenor,
			adblShortEndOISQuote,
			"SwapRate",
			astrOISFuturesEffectiveTenor,
			astrOISFuturesMaturityTenor,
			adblOISFuturesQuote,
			"SwapRate",
			astrLongEndOISMaturityTenor,
			adblLongEndOISQuote,
			"SwapRate"
		);
	}

	private static final ForwardCurve Reference (
		final JulianDate dtSpot,
		final MergedDiscountForwardCurve dcOvernight,
		final String strReferenceForwardTenor)
		throws Exception
	{
		ForwardLabel forwardLabel = ForwardLabel.Create (
			dcOvernight.currency(),
			strReferenceForwardTenor
		);

		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		double[] adblDepositQuote = new double[] {
			0.003565,	// 1D
			0.003858,	// 1W
			0.003840,	// 2W
			0.003922,	// 3W
			0.003869,	// 1M
			0.003698,	// 2M
			0.003527,	// 3M
			0.003342,	// 4M
			0.003225	// 5M
		};

		String[] astrFRAMaturityTenor = new String[] {
			"00D",
			"01M",
			"02M",
			"03M",
			"04M",
			"05M",
			"06M",
			"07M",
			"08M",
			"09M",
			"10M",
			"11M",
			"12M",
			"13M",
			"14M",
			"15M",
			"16M",
			"17M",
			"18M"
		};

		double[] adblFRAQuote = new double[] {
			0.003120,	//  0D
			0.002930,	//  1M
			0.002720,	//  2M
			0.002600,	//  3M
			0.002560,	//  4M
			0.002520,	//  5M
			0.002480,	//  6M
			0.002540,	//  7M
			0.002610,	//  8M
			0.002670,	//  9M
			0.002790,	// 10M
			0.002910,	// 11M
			0.003030,	// 12M
			0.003180,	// 13M
			0.003350,	// 14M
			0.003520,	// 15M
			0.003710,	// 16M
			0.003890,	// 17M
			0.004090	// 18M
		};

		String[] astrFixFloatMaturityTenor = new String[] {
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"35Y",
			"40Y",
			"50Y",
			"60Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.004240,	//  3Y
			0.005760,	//  4Y			
			0.007620,	//  5Y
			0.009540,	//  6Y
			0.011350,	//  7Y
			0.013030,	//  8Y
			0.014520,	//  9Y
			0.015840,	// 10Y
			0.018090,	// 12Y
			0.020370,	// 15Y
			0.021870,	// 20Y
			0.022340,	// 25Y
			0.022560,	// 30Y
			0.022950,	// 35Y
			0.023480,	// 40Y
			0.024210,	// 50Y
			0.024630	// 60Y
		};

		return LatentMarketStateBuilder.SmoothForwardCurve (
			dtSpot,
			forwardLabel,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			astrFRAMaturityTenor,
			adblFRAQuote,
			"ParForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			null,
			null,
			"DerivedParBasisSpread",
			dcOvernight,
			null
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		String strCurrency = "GBP";
		String strDerivedForwardTenor = "3M";
		String strReferenceForwardTenor = "6M";
		double dblBump = 0.0001;
		boolean bIsProportional = false;

		JulianDate dtSpot = DateUtil.Today();

		MergedDiscountForwardCurve dcOvernight = OvernightCurve (
			dtSpot,
			strCurrency
		);

		ForwardCurve fcReference = Reference (
			dtSpot,
			dcOvernight,
			strReferenceForwardTenor
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strDerivedForwardTenor
		);

		String[] astrDepositMaturityTenor = new String[] {
			"2W",
			"3W",
			"1M",
			"2M"
		};

		double[] adblDepositQuote = new double[] {
			0.001865,	// 2W
			0.001969,	// 3W
			0.001951,	// 1M
			0.001874	// 2M
		};

		String[] astrFRAMaturityTenor = new String[] {
			"00D",
			"01M",
			"03M",
			"06M",
			"09M",
			"12M",
			"15M",
			"18M",
			"21M"
		};

		double[] adblFRAQuote = new double[] {
			0.001790,	//  0D
			0.001775,	//  1M
			0.001274,	//  3M
			0.001222,	//  6M
			0.001269,	//  9M
			0.001565,	// 12M
			0.001961,	// 15M
			0.002556,	// 18M
			0.003101	// 21M
		};

		String[] astrFixFloatMaturityTenor = new String[] {
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.002850,	//  3Y
			0.004370,	//  4Y
			0.006230,	//  5Y
			0.008170,	//  6Y
			0.010000,	//  7Y
			0.011710,	//  8Y
			0.013240,	//  9Y
			0.014590,	// 10Y
			0.016920,	// 12Y
			0.019330,	// 15Y
			0.020990,	// 20Y
			0.021560,	// 25Y
			0.021860 	// 30Y
		};

		String[] astrSyntheticFloatFloatMaturityTenor = new String[] {
			"35Y",
			"40Y",
			"50Y",
			"60Y"
		};

		double[] adblSyntheticFloatFloatQuote = new double[] {
			0.00065,	// 35Y
			0.00060,	// 40Y
			0.00054,	// 50Y
			0.00050		// 60Y
		};

		Map<String, ForwardCurve> mapDerivedForwardCurve = LatentMarketStateBuilder.BumpedForwardCurve (
			dtSpot,
			forwardLabel,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			astrFRAMaturityTenor,
			adblFRAQuote,
			"ParForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			astrSyntheticFloatFloatMaturityTenor,
			adblSyntheticFloatFloatQuote,
			"DerivedParBasisSpread",
			dcOvernight,
			fcReference,
			LatentMarketStateBuilder.SMOOTH,
			dblBump,
			bIsProportional
		);

		Component[] aDepositComp = OTCInstrumentBuilder.ForwardRateDeposit (
			dtSpot,
			astrDepositMaturityTenor,
			forwardLabel
		);

		Component[] aFRAComp = OTCInstrumentBuilder.FRAStandard (
			dtSpot,
			forwardLabel,
			astrFRAMaturityTenor,
			adblFRAQuote
		);

		Component[] aFixFloatComp = OTCInstrumentBuilder.FixFloatCustom (
			dtSpot,
			forwardLabel,
			astrFixFloatMaturityTenor
		);

		Component[] aSyntheticFloatFloatComp = OTCInstrumentBuilder.FloatFloat (
			dtSpot,
			strCurrency,
			strDerivedForwardTenor,
			astrSyntheticFloatFloatMaturityTenor,
			0.
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		System.out.println ("\n\t|--------------------------------------------------------------||");

		for (Map.Entry<String, ForwardCurve> meForward : mapDerivedForwardCurve.entrySet()) {
			String strKey = meForward.getKey();

			if (!strKey.startsWith ("deposit")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (dcOvernight);

			csqc.setForwardState (fcReference);

			csqc.setForwardState (meForward.getValue());

			System.out.print ("\t|  [" + meForward.getKey() + "] => ");

			for (Component comp : aDepositComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"ForwardRate"
					), 1, 6, 1.) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|--------------------------------------------------------------||");

		System.out.println ("\n\t|---------------------------------------------------------------------------------------||");

		for (Map.Entry<String, ForwardCurve> meForward : mapDerivedForwardCurve.entrySet()) {
			String strKey = meForward.getKey();

			if (!strKey.startsWith ("fra")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (dcOvernight);

			csqc.setForwardState (fcReference);

			csqc.setForwardState (meForward.getValue());

			System.out.print ("\t|  [" + meForward.getKey() + "] => ");

			for (Component comp : aFRAComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"ParForwardRate"
					), 1, 4, 1.) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|----------------------------------------------------------------------------------------------------------------------------||");

		for (Map.Entry<String, ForwardCurve> meForward : mapDerivedForwardCurve.entrySet()) {
			String strKey = meForward.getKey();

			if (!strKey.startsWith ("fixfloat")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (dcOvernight);

			csqc.setForwardState (fcReference);

			csqc.setForwardState (meForward.getValue());

			System.out.print ("\t|  [" + meForward.getKey() + "] => ");

			for (Component comp : aFixFloatComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"SwapRate"
					), 1, 4, 1.) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------||");

		for (Map.Entry<String, ForwardCurve> meForward : mapDerivedForwardCurve.entrySet()) {
			String strKey = meForward.getKey();

			if (!strKey.startsWith ("syntheticfloatfloat")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFundingState (dcOvernight);

			csqc.setForwardState (fcReference);

			csqc.setForwardState (meForward.getValue());

			System.out.print ("\t|  [" + meForward.getKey() + "] => ");

			for (Component comp : aSyntheticFloatFloatComp)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"DerivedParBasisSpread"
					), 1, 4, 1.) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------||");

		CurveSurfaceQuoteContainer csqcBase = new CurveSurfaceQuoteContainer();

		csqcBase.setFundingState (dcOvernight);

		csqcBase.setForwardState (fcReference);

		csqcBase.setForwardState (mapDerivedForwardCurve.get ("Base"));

		CurveSurfaceQuoteContainer csqcBump = new CurveSurfaceQuoteContainer();

		csqcBump.setFundingState (dcOvernight);

		csqcBump.setForwardState (fcReference);

		csqcBump.setForwardState (mapDerivedForwardCurve.get ("Bump"));

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

		for (Component comp : aFRAComp)
			System.out.println (
				"\t| FORWARD RATE  => " +
				comp.maturityDate() + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"ParForwardRate"
				), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"ParForwardRate"
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

		for (Component comp : aSyntheticFloatFloatComp)
			System.out.println (
				"\t| DERIVED BASIS => " +
				comp.maturityDate() + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"DerivedParBasisSpread"
				), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBump,
					null,
					"DerivedParBasisSpread"
				), 1, 6, 1.) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------||");
	}
}
