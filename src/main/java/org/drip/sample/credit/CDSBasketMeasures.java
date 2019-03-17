
package org.drip.sample.credit;

/*
 * Credit Product Imports
 */

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.credit.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.credit.CreditCurve;
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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>CDSBasketMeasures</i> contains a demo of the CDS Basket Measures Generation Sample. It shows the
 * following:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Build the IR Curve from the Rates' instruments.
 *  	</li>
 *  	<li>
 * 			Build the Component Credit Curve from the CDS instruments.
 *  	</li>
 *  	<li>
 * 			Create the basket market parameters and add the named discount curve and the credit curves to it.
 *  	</li>
 *  	<li>
 * 			Create the CDS basket from the component CDS and their weights.
 *  	</li>
 *  	<li>
 * 			Construct the Valuation and the Pricing Parameters.
 *  	</li>
 *  	<li>
 * 			Generate the CDS basket measures from the valuation, the pricer, and the market parameters.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credit/README.md">Credit Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSBasketMeasures {

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

		JulianDate dtCashEffective = dtStart.addBusDays (1, strCurrency);

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

		JulianDate dtIRSEffective = dtStart.addBusDays (
			2,
			strCurrency
		);

		for (int i = 0; i < astrIRSTenor.length; ++i) {
			astrCalibMeasure[i + astrCashTenor.length] = "Rate";
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
	 * Sample demonstrating the creation/usage of the Credit Curve from CDS Instruments
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static CreditCurve CreateCreditCurveFromCDS (
		final JulianDate dtStart,
		final double[] adblQuote,
		final String[] astrTenor,
		final MergedDiscountForwardCurve dc,
		final double dblRecovery,
		final String strCCName)
		throws Exception
	{
		String[] astrCalibMeasure = new String[adblQuote.length];
		CreditDefaultSwap[] aCDS = new CreditDefaultSwap[adblQuote.length];

		for (int i = 0; i < astrTenor.length; ++i) {
			aCDS[i] = CDSBuilder.CreateSNAC (
				dtStart,
				astrTenor[i],
				0.01,
				strCCName
			);

			astrCalibMeasure[i] = "FairPremium";
		}

		/*
		 * Build the credit curve from the CDS instruments and the fair premium
		 */

		return ScenarioCreditCurveBuilder.Custom (
			strCCName,
			dtStart,
			aCDS,
			dc,
			adblQuote,
			astrCalibMeasure,
			dblRecovery,
			false
		);
	}

	/*
	 * Sample demonstrating the creation/usage of the bond basket API
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	public static final void BasketBondAPISample()
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
		 * Build the IR Curve from the Rates' instruments
		 */

		String[] astrCashTenor = new String[] {
			"3M"
		};
		double[] adblCashRate = new double[] {
			0.00276
		};
		String[] astrIRSTenor = new String[] {   "1Y",    "2Y",    "3Y",    "4Y",    "5Y",    "6Y",    "7Y",
			   "8Y",    "9Y",   "10Y",   "11Y",   "12Y",   "15Y",   "20Y",   "25Y",   "30Y",   "40Y",   "50Y"};
		double[] adblIRSRate = new double[]  {0.00367, 0.00533, 0.00843, 0.01238, 0.01609, 0.01926, 0.02191,
			0.02406, 0.02588, 0.02741, 0.02870, 0.02982, 0.03208, 0.03372, 0.03445, 0.03484, 0.03501, 0.03484};

		MergedDiscountForwardCurve dc = BuildRatesCurveFromInstruments (
			dtCurve,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.,
			"USD"
		);

		/*
		 * Build the Component Credit Curve from the CDS instruments
		 */

		CreditCurve ccCHN = CreateCreditCurveFromCDS (dtCurve,
			new double[] {100., 100., 100., 100., 100., 100., 100., 100.},
			new String[] {"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"}, dc, 0.4, "CHN");

		CreditCurve ccIND = CreateCreditCurveFromCDS (dtCurve,
			new double[] {100., 100., 100., 100., 100., 100., 100., 100.},
			new String[] {"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"}, dc, 0.4, "IND");

		CreditCurve ccBRA = CreateCreditCurveFromCDS (dtCurve,
			new double[] {100., 100., 100., 100., 100., 100., 100., 100.},
			new String[] {"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"}, dc, 0.4, "BRA");

		CreditCurve ccRUS = CreateCreditCurveFromCDS (dtCurve,
			new double[] {100., 100., 100., 100., 100., 100., 100., 100.},
			new String[] {"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"}, dc, 0.4, "RUS");

		CreditCurve ccKOR = CreateCreditCurveFromCDS (dtCurve,
			new double[] {100., 100., 100., 100., 100., 100., 100., 100.},
			new String[] {"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"}, dc, 0.4, "KOR");

		CreditCurve ccTUR = CreateCreditCurveFromCDS (dtCurve,
			new double[] {100., 100., 100., 100., 100., 100., 100., 100.},
			new String[] {"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"}, dc, 0.4, "TUR");

		/*
		 * Create the basket market parameters and add the named discount curve and the credit curves to it.
		 */

		CurveSurfaceQuoteContainer mktParams =  new CurveSurfaceQuoteContainer();

		mktParams.setFundingState (dc);

		mktParams.setCreditState (ccCHN);

		mktParams.setCreditState (ccIND);

		mktParams.setCreditState (ccBRA);

		mktParams.setCreditState (ccRUS);

		mktParams.setCreditState (ccKOR);

		mktParams.setCreditState (ccTUR);

		/*
		 * Create the CDS basket from the component CDS and their weights
		 */

		CreditDefaultSwap aCDS[] = new CreditDefaultSwap[6];

		aCDS[0] = CDSBuilder.CreateSNAC (dtCurve, "5Y", 0.01, "CHN");

		aCDS[1] = CDSBuilder.CreateSNAC (dtCurve, "5Y", 0.01, "IND");

		aCDS[2] = CDSBuilder.CreateSNAC (dtCurve, "5Y", 0.01, "BRA");

		aCDS[3] = CDSBuilder.CreateSNAC (dtCurve, "5Y", 0.01, "RUS");

		aCDS[4] = CDSBuilder.CreateSNAC (dtCurve, "5Y", 0.01, "KOR");

		aCDS[5] = CDSBuilder.CreateSNAC (dtCurve, "5Y", 0.01, "TUR");

		BasketProduct bds = new CDSBasket (aCDS, new double[] {1., 2., 3., 4., 5., 6.}, "BRIC");

		/*
		 * Construct the Valuation and the Pricing Parameters
		 */

		ValuationParams valParams = ValuationParams.Spot (
			dtSettle,
			0,
			"USD",
			Convention.DATE_ROLL_ACTUAL
		);

		CreditPricerParams pricerParams = new CreditPricerParams (
			7,
			null,
			false,
			CreditPricerParams.PERIOD_DISCRETIZATION_FULL_COUPON
		);

		/*
		 * Generate the CDS basket measures from the valuation, the pricer, and the market parameters
		 */

		CaseInsensitiveTreeMap<Double> mapResult = bds.value (
			valParams,
			pricerParams,
			mktParams,
			null
		);

		System.out.println ("Accrued:      " + FormatUtil.FormatDouble (mapResult.get ("Accrued"), 0, 2, 100.));

		System.out.println ("Clean PV:     " + FormatUtil.FormatDouble (mapResult.get ("CleanPV"), 0, 2, 1.));

		System.out.println ("Fair Premium: " + FormatUtil.FormatDouble (mapResult.get ("FairPremium"), 0, 2, 1.));

		System.out.println ("Fair Upfront: " + FormatUtil.FormatDouble (mapResult.get ("FairUpfront"), 0, 2, 1.));
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		BasketBondAPISample();

		EnvManager.TerminateEnv();
	}
}
