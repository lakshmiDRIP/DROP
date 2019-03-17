
package org.drip.sample.credit;

/*
 * Credit Product import
 */

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.*;
import org.drip.product.definition.*;
import org.drip.param.creator.*;
import org.drip.product.creator.*;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.credit.CreditCurve;
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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>CDSCashFlowMeasures</i> contains a demo of the CDS Measures and Cash flow Generation Sample. It
 * illustrates the following:
 * 
 * <br><br>
 * <ul>
 * 	<li>
 * 		Credit Curve Creation: From flat Hazard Rate, and from an array of dates and their corresponding
 * 			survival probabilities.
 * 	</li>
 * 	<li>
 * 		Create Credit Curve from CDS instruments, and recover the input measure quotes.
 * 	</li>
 * 	<li>
 * 		Create an SNAC CDS, price it, and display the coupon/loss cash flow.
 * 	</li>
 * </ul>
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

public class CDSCashFlowMeasures {
	private static final java.lang.String FIELD_SEPARATOR = "   ";

	/*
	 * Sample API demonstrating the creation/usage of the credit curve from survival and hazard rates
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void CreditCurveAPISample()
		throws Exception
	{
		JulianDate dtStart = DateUtil.Today();

		JulianDate dt10Y = dtStart.addYears (10);

		/*
		 * Create Credit Curve from flat Hazard Rate
		 */

		CreditCurve ccFlatHazard = ScenarioCreditCurveBuilder.FlatHazard (
			dtStart.julian(),
			"CC",
			"USD",
			0.02,
			0.4
		);

		System.out.println ("CCFromFlatHazard[" + dt10Y.toString() + "]; Survival=" +
			ccFlatHazard.survival ("10Y") + "; Hazard=" + ccFlatHazard.hazard ("10Y"));

		int[] aiDate = new int[5];
		double[] adblSurvival = new double[5];

		for (int i = 0; i < 5; ++i) {
			aiDate[i] = dtStart.addYears (2 * i + 2).julian();

			adblSurvival[i] = 1. - 0.1 * (i + 1);
		}

		/*
		 * Create Credit Curve from an array of dates and their corresponding survival probabilities
		 */

		CreditCurve ccFromSurvival = ScenarioCreditCurveBuilder.Survival (
			dtStart.julian(),
			"CC",
			"USD",
			aiDate,
			adblSurvival,
			0.4
		);

		System.out.println ("CCFromSurvival[" + dt10Y.toString() + "]; Survival=" +
			ccFromSurvival.survival ("10Y") + "; Hazard=" + ccFromSurvival.hazard ("10Y"));
	}

	/*
	 * Sample API demonstrating the creation of the Credit Curve from the CDS instruments
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static void CreateCreditCurveFromCDSInstruments()
		throws Exception
	{
		JulianDate dtStart = DateUtil.Today();

		/*
		 * Populate the instruments, the calibration measures, and the calibration quotes
		 */

		double[] adblQuotes = new double[5];
		String[] astrCalibMeasure = new String[5];
		CreditDefaultSwap[] aCDS = new CreditDefaultSwap[5];

		for (int i = 0; i < 5; ++i) {
			/*
			 * The Calibration CDS
			 */

			aCDS[i] = CDSBuilder.CreateSNAC (
				dtStart,
				(i + 1) + "Y",
				0.01,
				"CORP"
			);

			/*
			 * Calibration Quote
			 */

			adblQuotes[i] = 100.;

			/*
			 * Calibration Measure
			 */

			astrCalibMeasure[i] = "FairPremium";
		}

		/*
		 * Flat Discount Curve
		 */

		MergedDiscountForwardCurve dc = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtStart,
			"USD",
			0.05
		);

		/*
		 * Create the Credit Curve from the give CDS instruments
		 */

		CreditCurve cc = ScenarioCreditCurveBuilder.Custom (
			"CORP",
			dtStart,
			aCDS,
			dc,
			adblQuotes,
			astrCalibMeasure,
			0.4,
			false
		);

		/*
		 * Valuation Parameters
		 */

		ValuationParams valParams = ValuationParams.Spot (
			dtStart,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		/*
		 * Standard Credit Pricer Parameters (check javadoc for details)
		 */

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		/*
		 * Re-calculate the input calibration measures for the input CDSes
		 */

		for (int i = 0; i < aCDS.length; ++i)
			System.out.println (
				"\t" + astrCalibMeasure[i] + "[" + i + "] = " +
				aCDS[i].measureValue (
					valParams, pricerParams, MarketParamsBuilder.Create (
						dc,
						null,
						null,
						cc,
						null,
						null,
						null,
						null
					),
					null,
					astrCalibMeasure[i]
				)
			);
	}

	/*
	 * Sample API demonstrating the display of the CDS coupon and loss cash flow
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void CDSAPISample()
		throws Exception
	{
		JulianDate dtStart = DateUtil.Today();

		/*
		 * Flat Discount Curve
		 */

		MergedDiscountForwardCurve dc = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtStart,
			"USD",
			0.05
		);

		/*
		 * Flat Credit Curve
		 */

		CreditCurve cc = ScenarioCreditCurveBuilder.FlatHazard (
			dtStart.julian(),
			"CC",
			"USD",
			0.02,
			0.4
		);

		/*
		 * Component Market Parameters built from the Discount and the Credit Curves
		 */

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Credit (
			dc,
			cc
		);

		/*
		 * Create an SNAC CDS
		 */

		CreditDefaultSwap cds = CDSBuilder.CreateSNAC (
			dtStart,
			"5Y",
			0.1,
			"CC"
		);

		/*
		 * Valuation Parameters
		 */

		ValuationParams valParams = ValuationParams.Spot (
			dtStart,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		/*
		 * Standard Credit Pricer Parameters (check javadoc for details)
		 */

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		System.out.println ("Loss Start     Loss End  Notl    Rec    EffDF    StartSurv  EndSurv");

		System.out.println ("----------     --------  ----    ---    -----    ---------  -------");

		/*
		 * CDS Loss Cash Flow
		 */

		for (LossQuadratureMetrics dp : cds.lossFlow (valParams, pricerParams, mktParams))
			System.out.println (
				DateUtil.YYYYMMDD (dp.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (dp.endDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dp.effectiveNotional(), 1, 0, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dp.effectiveRecovery(), 1, 2, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dp.effectiveDF(), 1, 4, 1.)  + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dp.startSurvival(), 1, 4, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dp.endSurvival(), 1, 4, 1.)
			);
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		EnvManager.InitEnv ("");

		CreditCurveAPISample();

		CreateCreditCurveFromCDSInstruments();

		CDSAPISample();

		EnvManager.TerminateEnv();
	}
}
