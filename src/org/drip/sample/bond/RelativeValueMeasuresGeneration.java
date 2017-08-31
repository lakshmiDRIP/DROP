
package org.drip.sample.bond;

/*
 * Credit Product imports
 */

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.definition.*;
import org.drip.param.market.*;
import org.drip.param.quote.*;
import org.drip.param.valuation.*;
import org.drip.product.definition.*;
import org.drip.product.govvie.TreasuryComponent;
import org.drip.product.rates.*;
import org.drip.analytics.output.BondRVMeasures;
import org.drip.param.creator.*;
import org.drip.product.creator.*;
import org.drip.product.credit.BondComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.TreasuryBuilder;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.ForwardLabel;

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * RelativeValueMeasuresGeneration is a Bond RV Measures Generation Sample demonstrating the invocation and
 *  usage of Bond RV Measures functionality. It shows the following:
 *  
 * 	- Create the discount/treasury curve from rates/treasury instruments.
 * 	- Compute the work-out date given the price.
 * 	- Compute and display the base RV measures to the work-out date.
 * 	- Compute and display the bumped RV measures to the work-out date.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RelativeValueMeasuresGeneration {

	private static final FixFloatComponent OTCIRS (
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

	/*
	 * Sample demonstrating creation of a rates curve from instruments
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static MergedDiscountForwardCurve BuildRatesCurveFromInstruments (
		final JulianDate dtStart,
		final String[] astrCashTenor,
		final double[] adblCashRate,
		final String[] astrIRSTenor,
		final double[] adblIRSRate,
		final double dblBump,
		final String strCurrency)
		throws Exception
	{
		int iNumDCInstruments = astrCashTenor.length + adblIRSRate.length;
		int aiDate[] = new int[iNumDCInstruments];
		double adblRate[] = new double[iNumDCInstruments];
		String astrCalibMeasure[] = new String[iNumDCInstruments];
		double adblCompCalibValue[] = new double[iNumDCInstruments];
		CalibratableComponent aCompCalib[] = new CalibratableComponent[iNumDCInstruments];

		// Cash Calibration

		JulianDate dtCashEffective = dtStart.addBusDays (
			1,
			strCurrency
		);

		for (int i = 0; i < astrCashTenor.length; ++i) {
			astrCalibMeasure[i] = "Rate";
			adblRate[i] = java.lang.Double.NaN;
			adblCompCalibValue[i] = adblCashRate[i] + dblBump;

			aCompCalib[i] = SingleStreamComponentBuilder.Deposit (
				dtCashEffective,
				new JulianDate (aiDate[i] = dtCashEffective.addTenor (astrCashTenor[i]).julian()),
				ForwardLabel.Create (
					strCurrency,
					astrCashTenor[i]
				)
			);
		}

		// IRS Calibration

		JulianDate dtIRSEffective = dtStart.addBusDays (2, strCurrency);

		for (int i = 0; i < astrIRSTenor.length; ++i) {
			astrCalibMeasure[i + astrCashTenor.length] = "SwapRate";
			adblRate[i + astrCashTenor.length] = java.lang.Double.NaN;
			adblCompCalibValue[i + astrCashTenor.length] = adblIRSRate[i] + dblBump;

			aiDate[i + astrCashTenor.length] = dtIRSEffective.addTenor (astrIRSTenor[i]).julian();

			aCompCalib[i + astrCashTenor.length] = OTCIRS (
				dtIRSEffective,
				strCurrency,
				astrIRSTenor[i],
				0.
			);
		}

		/*
		 * Build the IR curve from the components, their calibration measures, and their calibration quotes.
		 */

		return ScenarioDiscountCurveBuilder.NonlinearBuild (
			dtStart,
			strCurrency,
			aCompCalib,
			adblCompCalibValue,
			astrCalibMeasure,
			null
		);
	}

	/*
	 * Sample demonstrating creation of a set of the on-the-run treasury bonds
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final TreasuryComponent[] OTRTreasurySet (
		final JulianDate dt,
		final String[] astrTenor,
		final double[] adblCoupon)
		throws Exception
	{
		TreasuryComponent aTreasury[] = new TreasuryComponent[astrTenor.length];

		for (int i = 0; i < astrTenor.length; ++i)
			aTreasury[i] = TreasuryBuilder.FromCode (
				"UST",
				dt,
				dt.addTenor (astrTenor[i]),
				adblCoupon[i]
			);

		return aTreasury;
	}

	/*
	 * Sample demonstrating building of the treasury discount curve based off the on-the run instruments and their yields
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final GovvieCurve BuildOnTheRunGovvieCurve (
		final JulianDate dt,
		final Bond[] aTreasury,
		final double[] adblYield)
		throws Exception
	{
		return ScenarioGovvieCurveBuilder.CubicPolyShapePreserver (
			"UST",
			"UST",
			aTreasury[0].currency(),
			dt.julian(),
			aTreasury,
			adblYield,
			"Yield"
		);
	}

	/*
	 * Put together a named map of treasury quotes
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CaseInsensitiveTreeMap<ProductQuote> MakeTSYQuotes (
		final String[] astrTSYTenor,
		final double[] adblTSYYield)
		throws Exception
	{
		CaseInsensitiveTreeMap<ProductQuote> mTSYQuotes = new CaseInsensitiveTreeMap<ProductQuote>();

		for (int i = 0; i < astrTSYTenor.length; ++i) {
			ProductMultiMeasure cmmq = new ProductMultiMeasure();

			cmmq.addQuote (
				"Yield",
				new MultiSided (
					"mid",
					adblTSYYield[i],
					Double.NaN
				),
				true
			);

			mTSYQuotes.put (
				astrTSYTenor[i] + "ON",
				cmmq
			);
		}

		return mTSYQuotes;
	}

	/*
	 * Print the Bond RV Measures
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final boolean PrintRVMeasures (
		final String strPrefix,
		final BondRVMeasures rv)
	{
		if (null == rv) return false;

		System.out.println (strPrefix + "ASW: " + FormatUtil.FormatDouble (rv.asw(), 0, 0, 10000.));

		System.out.println (strPrefix + "Bond Basis: " + FormatUtil.FormatDouble (rv.bondBasis(), 0, 0, 10000.));

		System.out.println (strPrefix + "Convexity: " + FormatUtil.FormatDouble (rv.convexity(), 0, 2, 1000000.));

		System.out.println (strPrefix + "Credit Basis: " + FormatUtil.FormatDouble (rv.creditBasis(), 0, 0, 10000.));

		System.out.println (strPrefix + "Discount Margin: " + FormatUtil.FormatDouble (rv.discountMargin(), 0, 0, 10000.));

		System.out.println (strPrefix + "G Spread: " + FormatUtil.FormatDouble (rv.gSpread(), 0, 0, 10000.));

		System.out.println (strPrefix + "I Spread: " + FormatUtil.FormatDouble (rv.iSpread(), 0, 0, 10000.));

		System.out.println (strPrefix + "Macaulay Duration: " + FormatUtil.FormatDouble (rv.macaulayDuration(), 0, 2, 1.));

		System.out.println (strPrefix + "Modified Duration: " + FormatUtil.FormatDouble (rv.modifiedDuration(), 0, 2, 10000.));

		System.out.println (strPrefix + "OAS: " + FormatUtil.FormatDouble (rv.oas(), 0, 0, 10000.));

		System.out.println (strPrefix + "PECS: " + FormatUtil.FormatDouble (rv.pecs(), 0, 0, 10000.));

		System.out.println (strPrefix + "Price: " + FormatUtil.FormatDouble (rv.price(), 0, 3, 100.));

		System.out.println (strPrefix + "TSY Spread: " + FormatUtil.FormatDouble (rv.tsySpread(), 0, 0, 10000.));

		try {
			System.out.println (strPrefix + "Workout Date: " + new JulianDate (rv.wi().date()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println (strPrefix + "Workout Factor: " + rv.wi().factor());

		System.out.println (strPrefix + "Workout Type: " + rv.wi().type());

		System.out.println (strPrefix + "Workout Yield: " + FormatUtil.FormatDouble (rv.wi().yield(), 0, 3, 100.));

		System.out.println (strPrefix + "Yield01: " + FormatUtil.FormatDouble (rv.yield01(), 0, 2, 10000.));

		System.out.println (strPrefix + "Yield Basis: " + FormatUtil.FormatDouble (rv.bondBasis(), 0, 0, 10000.));

		System.out.println (strPrefix + "Yield Spread: " + FormatUtil.FormatDouble (rv.bondBasis(), 0, 0, 10000.));

		System.out.println (strPrefix + "Z Spread: " + FormatUtil.FormatDouble (rv.zSpread(), 0, 0, 10000.));

		return true;
	}

	/*
	 * Sample demonstrating invocation and extraction of RV Measures from a bond
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BondRVMeasuresSample()
		throws Exception
	{
		JulianDate dtCurve = DateUtil.CreateFromYMD (
			2013,
			6,
			27
		);

		JulianDate dtSettle = DateUtil.CreateFromYMD (
			2013,
			7,
			1
		);

		/*
		 * Create the discount curve from rates instruments.
		 */

		String[] astrCashTenor = new String[] {"3M"};
		double[] adblCashRate = new double[] {0.00276};
		String[] astrIRSTenor = new String[] {   "1Y",    "2Y",    "3Y",    "4Y",    "5Y",    "6Y",    "7Y",
			   "8Y",    "9Y",   "10Y",   "11Y",   "12Y",   "15Y",   "20Y",   "25Y",   "30Y",   "40Y",   "50Y"};
		double[] adblIRSRate = new double[]  {0.00367, 0.00533, 0.00843, 0.01238, 0.01609, 0.01926, 0.02191,
			0.02406, 0.02588, 0.02741, 0.02870, 0.02982, 0.03208, 0.03372, 0.03445, 0.03484, 0.03501, 0.03484};
		String[] astrTSYTenor = new String[] {
			"1Y", "2Y", "3Y", "5Y", "7Y", "10Y",  "30Y"
		};
		final double[] adblTSYCoupon = new double[] {
			0.0000, 0.00375, 0.00500, 0.0100, 0.01375, 0.01375, 0.02875
		};
		double[] adblTSYYield = new double[] {
			0.00160, 0.00397, 0.00696, 0.01421, 0.01955, 0.02529, 0.03568
		};

		MergedDiscountForwardCurve dc = BuildRatesCurveFromInstruments (
			dtCurve,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.,
			"USD"
		);

		TreasuryComponent[] aTSYBond = OTRTreasurySet (
			dtCurve,
			astrTSYTenor,
			adblTSYCoupon
		);

		/*
		 * Create the on-the-run treasury discount curve.
		 */

		GovvieCurve gc = BuildOnTheRunGovvieCurve (
			dtCurve,
			aTSYBond,
			adblTSYYield
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (	// Simple Fixed Rate Bond
			"TEST",			// Name
			"USD",			// Currency
			"",				// Credit Curve - Empty for now
			0.0875,			// Bond Coupon
			2, 				// Frequency
			"30/360",		// Day Count
			DateUtil.CreateFromYMD (
				2010,
				3,
				17
			), // Effective
			DateUtil.CreateFromYMD (
				2015,
				4,
				1
			),	// Maturity
			null,		// Principal Schedule
			null
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			gc,
			null,
			null,
			null,
			MakeTSYQuotes (
				astrTSYTenor,
				adblTSYYield
			),
			null
		);

		ValuationParams valParams = ValuationParams.Spot (
			dtSettle,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		double dblPrice = 1.1025;

		/*
		 * Compute the work-out date given the price.
		 */

		WorkoutInfo wi = bond.exerciseYieldFromPrice (
			valParams,
			mktParams,
			null,
			dblPrice
		);

		/*
		 * Compute the base RV measures to the work-out date.
		 */

		BondRVMeasures rvm = bond.standardMeasures (
			valParams,
			null,
			mktParams,
			null,
			wi,
			dblPrice
		);

		PrintRVMeasures ("\tBase: ", rvm);

		MergedDiscountForwardCurve dcBumped = BuildRatesCurveFromInstruments (
			dtCurve,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.0001,
			"USD"
		);

		mktParams.setFundingState (dcBumped);

		/*
		 * Compute the bumped RV measures to the work-out date.
		 */

		org.drip.analytics.output.BondRVMeasures rvmBumped = bond.standardMeasures (
			valParams,
			null,
			mktParams,
			null,
			wi,
			dblPrice
		);

		PrintRVMeasures ("\tBumped: ", rvmBumped);
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		BondRVMeasuresSample();
	}
}
