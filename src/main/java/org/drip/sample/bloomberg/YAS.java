
package org.drip.sample.bloomberg;

/*
 * Credit Product imports
 */

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.definition.*;
import org.drip.param.valuation.*;
import org.drip.product.definition.*;
import org.drip.product.govvie.TreasuryComponent;
import org.drip.product.params.EmbeddedOptionSchedule;

/*
 * Credit Analytics API imports
 */

import org.drip.product.rates.*;
import org.drip.param.creator.*;
import org.drip.param.market.*;
import org.drip.param.quote.*;
import org.drip.product.creator.*;
import org.drip.product.credit.BondComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.TreasuryBuilder;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.ForwardLabel;

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>YAS</i> contains the sample demonstrating the replication of Bloomberg's YAS functionality.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bloomberg/README.md">Bloomberg CDSO CDSW SWPM YAS</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class YAS {
	private static final String FIELD_SEPARATOR = "    ";

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
	 * Sample demonstrating building of rates curve from cash/future/swaps
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
	 * Sample demonstrating creation of simple fixed coupon treasury bond
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final TreasuryComponent Treasury (
		final String strName,
		final double dblCoupon,
		final JulianDate dt,
		final String strTenor)
		throws Exception
	{
		return TreasuryBuilder.FromCode (
			"UST",
			dt,
			dt.addTenor (strTenor),
			dblCoupon
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
			aTreasury[i] = Treasury (
				"TSY" + astrTenor[i] + "ON",
				adblCoupon[i],
				dt,
				astrTenor[i]
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
		final TreasuryComponent[] aTreasury,
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
	 * Sample demonstrating creation of treasury quotes map
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
	 * Sample demonstrating generation of all the YAS measures
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BondPricerSample()
		throws Exception
	{
		JulianDate dtCurve = DateUtil.Today();

		JulianDate dtSettle = dtCurve.addBusDays (
			3,
			"USD"
		);

		double dblNotional = 1000000.;
		String[] astrCashTenor = new String[] {"3M"};
		double[] adblCashRate = new double[] {0.00276};
		String[] astrIRSTenor = new String[] {   "1Y",    "2Y",    "3Y",    "4Y",    "5Y",    "6Y",    "7Y",
			   "8Y",    "9Y",   "10Y",   "11Y",   "12Y",   "15Y",   "20Y",   "25Y",   "30Y",   "40Y",   "50Y"};
		double[] adblIRSRate = new double[]  {0.00367, 0.00533, 0.00843, 0.01238, 0.01609, 0.01926, 0.02191,
			0.02406, 0.02588, 0.02741, 0.02870, 0.02982, 0.03208, 0.03372, 0.03445, 0.03484, 0.03501, 0.03484};
		String[] astrTSYTenor = new String[] {
			"1Y", "2Y", "3Y", "5Y", "7Y", "10Y", "30Y"
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
		 * Create the on-the-run treasury discount curve
		 */

		GovvieCurve gc = BuildOnTheRunGovvieCurve (
			dtCurve,
			aTSYBond,
			adblTSYYield
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (	// Simple Fixed Rate Bond
			"TEST",			// Name
			"USD",			// Currency
			"",				// Empty Credit Curve
			0.054,			// Bond Coupon
			2, 				// Frequency
			"30/360",		// Day Count
			DateUtil.CreateFromYMD (
				2011,
				4,
				21
			), // Effective
			DateUtil.CreateFromYMD (
				2021,
				4,
				15
			),	// Maturity
			null,		// Principal Schedule
			null
		);

		int[] aiDate = new int[] {
			DateUtil.CreateFromYMD (2016, 3, 1).julian(),
			DateUtil.CreateFromYMD (2017, 3, 1).julian(),
			DateUtil.CreateFromYMD (2018, 3, 1).julian(),
			DateUtil.CreateFromYMD (2019, 3, 1).julian(),
			DateUtil.CreateFromYMD (2020, 3, 1).julian()
		};

		double[] adblFactor = new double[] {
			1.045, 1.03, 1.015, 1., 1.
		};

		EmbeddedOptionSchedule eos = new EmbeddedOptionSchedule (
			aiDate,
			adblFactor,
			false,
			30,
			false,
			Double.NaN,
			"",
			Double.NaN
		);

		bond.setEmbeddedCallSchedule (eos);

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

		System.out.println ("\n---- Valuation Details ----");

		System.out.println ("Trade Date   : " + dtCurve);

		System.out.println ("Cash Settle  : " + dtSettle);

		System.out.println ("\n--------");

		ValuationParams valParams = ValuationParams.Spot (
			dtSettle,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		double dblPrice = 0.97828;

		double dblAccrued = bond.accrued (
			valParams.valueDate(),
			mktParams
		);

		WorkoutInfo wi = bond.exerciseYieldFromPrice (valParams, mktParams, null, dblPrice);

		double dblTSYSpread = bond.tsySpreadFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblGSpread = bond.gSpreadFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblISpread = bond.iSpreadFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblZSpread = bond.zSpreadFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblASW = bond.aswFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblOAS = bond.oasFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblModDur = bond.modifiedDurationFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblMacDur = bond.macaulayDurationFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblYield01 = bond.yield01FromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		double dblConvexity = bond.convexityFromPrice (valParams, mktParams, null, wi.date(), wi.factor(), dblPrice);

		System.out.println ("Price          : " + FormatUtil.FormatDouble (dblPrice, 1, 3, 100.));

		System.out.println ("Yield          : " + FormatUtil.FormatDouble (wi.yield(), 1, 3, 100.));

		System.out.println ("Workout Date   : " + new JulianDate (wi.date()));

		System.out.println ("Workout Factor : " + FormatUtil.FormatDouble (wi.factor(), 1, 2, 100.));

		System.out.println ("\n--SPREAD AND YIELD CALCULATIONS--\n");

		System.out.println ("TSY Spread : " + FormatUtil.FormatDouble (dblTSYSpread, 1, 0, 10000.));

		System.out.println ("G Spread   : " + FormatUtil.FormatDouble (dblGSpread, 1, 0, 10000.));

		System.out.println ("I Spread   : " + FormatUtil.FormatDouble (dblISpread, 1, 0, 10000.));

		System.out.println ("Z Spread   : " + FormatUtil.FormatDouble (dblZSpread, 1, 0, 10000.));

		System.out.println ("ASW        : " + FormatUtil.FormatDouble (dblASW, 1, 0, 10000.));

		System.out.println ("OAS        : " + FormatUtil.FormatDouble (dblOAS, 1, 0, 10000.));

		System.out.println ("\n--RISK--\n");

		System.out.println ("Modified Duration : " + FormatUtil.FormatDouble (dblModDur, 1, 2, 10000.));

		System.out.println ("Macaulay Duration : " + FormatUtil.FormatDouble (dblMacDur, 1, 2, 1.));

		System.out.println ("Risk              : " + FormatUtil.FormatDouble (dblYield01 * 10000., 1, 2, 1.));

		System.out.println ("Convexity         : " + FormatUtil.FormatDouble (dblConvexity, 1, 2, 1000000.));

		System.out.println ("DV01              : " + FormatUtil.FormatDouble (dblYield01 * dblNotional, 1, 0, 1.));

		System.out.println ("\n--INVOICE--\n");

		System.out.println ("Face      : " + FormatUtil.FormatDouble (dblNotional, 1, 0, 1.));

		System.out.println ("Principal : " + FormatUtil.FormatDouble (dblPrice * dblNotional, 1, 2, 1.));

		System.out.println ("Accrued   : " + FormatUtil.FormatDouble (dblAccrued * dblNotional, 1, 2, 1.));

		System.out.println ("Total     : " + FormatUtil.FormatDouble ((dblPrice + dblAccrued) * dblNotional, 1, 2, 1.));

		System.out.println ("\nCashflow\n--------");

		for (CompositePeriod p : bond.couponPeriods())
			System.out.println (
				DateUtil.YYYYMMDD (p.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.endDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.payDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 4, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dc.df (p.payDate()), 1, 4, 1.) + FIELD_SEPARATOR
			);
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		EnvManager.InitEnv (
			"",
			true
		);

		BondPricerSample();

		EnvManager.TerminateEnv();
	}
}
