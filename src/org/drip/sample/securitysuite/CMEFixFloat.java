
package org.drip.sample.securitysuite;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.market.otc.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.FixFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * CMEFixFloat demonstrates the Analytics Calculation/Reconciliation for the CME Cleared Fix-Float IRS.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CMEFixFloat {

	private static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrDepositMaturityTenor = new String[] {
			"1D",
		};

		double[] adblDepositQuote = new double[] {
			0.0116,		// 1D
		};

		String[] astrShortEndOISMaturityTenor = new String[] {
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
			"12M",
			"18M",
			 "2Y",
			 "3Y",
			 "4Y",
			 "5Y",
			"10Y",
		};

		double[] adblShortEndOISQuote = new double[] {
			0.0117,    //   1W
			0.0115,    //   2W
			0.0116,    //   3W
			0.0116,    //   1M
			0.0120,    //   2M
			0.0125,    //   3M
			0.0128,    //   4M
			0.0131,    //   5M
			0.0133,    //   6M
			0.0139,    //   9M
			0.0146,    //  12M
			0.0154,    //  18M
			0.0161,    //   2Y
			0.0171,    //   3Y
			0.0179,    //   4Y
			0.0185,    //   5Y
			0.0206,    //  10Y
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
			null,
			null,
			null,
			"SwapRate",
			null,
			null,
			"SwapRate"
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

		String strCurrency = "USD";
		String strForwardTenor = "3M";

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.SEPTEMBER,
			1
		);

		MergedDiscountForwardCurve dcOvernight = OvernightCurve (
			dtSpot,
			strCurrency
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strForwardTenor
		);

		String[] astrDepositMaturityTenor = new String[] {
			"1D",
		};

		double[] adblDepositQuote = new double[] {
			0.013161,	// 1D
		};

		String[] astrFixFloatMaturityTenor = new String[] {
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
			"50Y",
		};

		double[] adblFixFloatQuote = new double[] {
			0.015540,	//  2Y
			0.016423,	//  3Y
			0.017209,	//  4Y			
			0.017980,	//  5Y
			0.018743,	//  6Y
			0.019455,	//  7Y
			0.020080,	//  8Y
			0.020651,	//  9Y
			0.021195,	// 10Y
			0.021651,	// 11Y
			0.022065,	// 12Y
			0.022952,	// 15Y
			0.023825,	// 20Y
			0.024175,	// 25Y
			0.024347,	// 30Y
			0.024225,	// 40Y
			0.023968,	// 50Y
		};

		ForwardCurve fc = LatentMarketStateBuilder.ShapePreservingForwardCurve (
			dtSpot,
			forwardLabel,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			null,
			null,
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

		String strMaturityTenor = "7Y";
		double dblFixedCoupon = 0.021893;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JULY,
			8
		);

		FixedFloatSwapConvention ffsc = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		FixFloatComponent ffc = ffsc.createFixFloatComponent (
			dtEffective,
			strMaturityTenor,
			dblFixedCoupon,
			0.,
			1.
		);

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setFundingState (dcOvernight);

		csqc.setForwardState (fc);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CaseInsensitiveTreeMap<Double> mapOutput = ffc.value (
			valParams,
			null,
			csqc,
			null
		);

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t\t" + me.getKey() + " => " + me.getValue());

		System.out.println();

		System.out.println ("\tClean Price       =>" +
			FormatUtil.FormatDouble (mapOutput.get ("CleanPrice"), 1, 4, 1.)
		);

		System.out.println ("\tDirty Price       =>" +
			FormatUtil.FormatDouble (mapOutput.get ("DirtyPrice"), 1, 4, 1.)
		);

		System.out.println ("\tFixed Stream PV   =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ReferencePV"), 1, 8, 1.)
		);

		System.out.println ("\tFloat Stream PV   =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("DerivedPV"), 1, 8, 1.)
		);

		System.out.println ("\tFixed Stream PV   =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ReferencePV"), 1, 8, 1.)
		);

		System.out.println ("\tFixed Stream DV01 =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ReferenceDV01"), 1, 8, 10000.)
		);

		System.out.println ("\tFloat Stream DV01 =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("DerivedDV01"), 1, 8, 10000.)
		);

		System.out.println ("\tFixing 01         =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("Fixing01"), 1, 8, 10000.)
		);

		System.out.println ("\tClean PV          =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("CleanPV"), 1, 8, 1.)
		);

		System.out.println ("\tDirty PV          =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("DirtyPV"), 1, 8, 1.)
		);

		System.out.println ("\tFixed Accrued     =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("FixedAccrued"), 1, 8, 1.)
		);

		System.out.println ("\tFloat Accrued     =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("FloatAccrued"), 1, 8, 1.)
		);

		System.out.println ("\tAccrued           =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("Accrued"), 1, 8, 1.)
		);

		System.out.println ("\tPar Swap Rate     =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ParSwapRate"), 1, 4, 100.) + "%"
		);
 	}
}
