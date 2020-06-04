
package org.drip.sample.funding;

/*
 * Credit Product imports
 */

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
import org.drip.param.creator.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>NonlinearCurveMeasures</i> contains a demo of the Non-linear Rates Analytics API Usage. It shows the
 * following:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Build a discount curve using: cash instruments only, EDF instruments only, IRS instruments only,
 * 				or all of them strung together.
 *  	</li>
 *  	<li>
 * 			Re-calculate the component input measure quotes from the calibrated discount curve object.
 *  	</li>
 *  	<li>
 * 			Compute the PVDF Wengert Jacobian across all the instruments used in the curve construction.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/README.md">Shape Preserving Local Funding Curve</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonlinearCurveMeasures {

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
	 * Sample API demonstrating the creation of the discount curve from the rates input instruments
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	public static void DiscountCurveFromRatesInstruments()
		throws Exception
	{
		int NUM_DC_INSTR = 30;
		double adblRate[] = new double[NUM_DC_INSTR];
		int aiMaturityDate[] = new int[NUM_DC_INSTR];
		String astrCalibMeasure[] = new String[NUM_DC_INSTR];
		double adblCompCalibValue[] = new double[NUM_DC_INSTR];
		CalibratableComponent aCompCalib[] = new CalibratableComponent[NUM_DC_INSTR];

		JulianDate dtStart = org.drip.analytics.date.DateUtil.CreateFromYMD (
			2011,
			4,
			6
		);

		// First 7 instruments - cash calibration

		JulianDate dtCashEffective = dtStart.addBusDays (1, "USD");

		aiMaturityDate[0] = dtCashEffective.addBusDays (1, "USD").julian(); // ON

		aiMaturityDate[1] = dtCashEffective.addBusDays (2, "USD").julian(); // 1D (TN)

		aiMaturityDate[2] = dtCashEffective.addBusDays (7, "USD").julian(); // 1W

		aiMaturityDate[3] = dtCashEffective.addBusDays (14, "USD").julian(); // 2W

		aiMaturityDate[4] = dtCashEffective.addBusDays (30, "USD").julian(); // 1M

		aiMaturityDate[5] = dtCashEffective.addBusDays (60, "USD").julian(); // 2M

		aiMaturityDate[6] = dtCashEffective.addBusDays (90, "USD").julian(); // 3M

		/*
		 * Cash Rate Quotes
		 */

		adblCompCalibValue[0] = .0013;
		adblCompCalibValue[1] = .0017;
		adblCompCalibValue[2] = .0017;
		adblCompCalibValue[3] = .0018;
		adblCompCalibValue[4] = .0020;
		adblCompCalibValue[5] = .0023;
		adblCompCalibValue[6] = .0026;

		ComposableFloatingUnitSetting cfus = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cps = new CompositePeriodSetting (
			4,
			"3M",
			"USD",
			null,
			1.,
			null,
			null,
			null,
			null
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			"USD",
			0
		);

		for (int i = 0; i < 7; ++i) {
			adblRate[i] = 0.01;
			astrCalibMeasure[i] = "Rate";

			aCompCalib[i] = SingleStreamComponentBuilder.Deposit (
				dtCashEffective, // Effective
				new JulianDate (aiMaturityDate[i]).addBusDays (
					2,
					"USD"
				), // Maturity
				ForwardLabel.Create (
					"USD",
					"3M"
				)
			);

			aCompCalib[i] = new SingleStreamComponent (
				"DEPOSIT_" + aiMaturityDate[i],
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.EdgePair (
							dtStart,
							new JulianDate (aiMaturityDate[i]).addBusDays (
								2,
								"USD"
							)
						),
						cps,
						cfus
					)
				),
				csp
			);

			aCompCalib[i].setPrimaryCode (aCompCalib[i].name());
		}

		// Next 8 instruments - EDF calibration

		adblCompCalibValue[7] = .0027;
		adblCompCalibValue[8] = .0032;
		adblCompCalibValue[9] = .0041;
		adblCompCalibValue[10] = .0054;
		adblCompCalibValue[11] = .0077;
		adblCompCalibValue[12] = .0104;
		adblCompCalibValue[13] = .0134;
		adblCompCalibValue[14] = .0160;

		CalibratableComponent[] aEDF = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtStart,
			8,
			"USD"
		);

		for (int i = 0; i < 8; ++i) {
			adblRate[i + 7] = 0.01;
			aCompCalib[i + 7] = aEDF[i];
			astrCalibMeasure[i + 7] = "Rate";

			aiMaturityDate[i + 7] = aEDF[i].maturityDate().julian();
		}

		// Final 15 instruments - IRS calibration

		JulianDate dtIRSEffective = dtStart.addBusDays (
			2,
			"USD"
		);

		String[] astrIRSTenor = new String[] {
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y",
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

		aiMaturityDate[15] = dtIRSEffective.addTenor (astrIRSTenor[0]).julian();

		aiMaturityDate[16] = dtIRSEffective.addTenor (astrIRSTenor[1]).julian();

		aiMaturityDate[17] = dtIRSEffective.addTenor (astrIRSTenor[2]).julian();

		aiMaturityDate[18] = dtIRSEffective.addTenor (astrIRSTenor[3]).julian();

		aiMaturityDate[19] = dtIRSEffective.addTenor (astrIRSTenor[4]).julian();

		aiMaturityDate[20] = dtIRSEffective.addTenor (astrIRSTenor[5]).julian();

		aiMaturityDate[21] = dtIRSEffective.addTenor (astrIRSTenor[6]).julian();

		aiMaturityDate[22] = dtIRSEffective.addTenor (astrIRSTenor[7]).julian();

		aiMaturityDate[23] = dtIRSEffective.addTenor (astrIRSTenor[8]).julian();

		aiMaturityDate[24] = dtIRSEffective.addTenor (astrIRSTenor[9]).julian();

		aiMaturityDate[25] = dtIRSEffective.addTenor (astrIRSTenor[10]).julian();

		aiMaturityDate[26] = dtIRSEffective.addTenor (astrIRSTenor[11]).julian();

		aiMaturityDate[27] = dtIRSEffective.addTenor (astrIRSTenor[12]).julian();

		aiMaturityDate[28] = dtIRSEffective.addTenor (astrIRSTenor[13]).julian();

		aiMaturityDate[29] = dtIRSEffective.addTenor (astrIRSTenor[14]).julian();

		adblCompCalibValue[15] = .0166;
		adblCompCalibValue[16] = .0206;
		adblCompCalibValue[17] = .0241;
		adblCompCalibValue[18] = .0269;
		adblCompCalibValue[19] = .0292;
		adblCompCalibValue[20] = .0311;
		adblCompCalibValue[21] = .0326;
		adblCompCalibValue[22] = .0340;
		adblCompCalibValue[23] = .0351;
		adblCompCalibValue[24] = .0375;
		adblCompCalibValue[25] = .0393;
		adblCompCalibValue[26] = .0402;
		adblCompCalibValue[27] = .0407;
		adblCompCalibValue[28] = .0409;
		adblCompCalibValue[29] = .0409;

		for (int i = 0; i < 15; ++i) {
			astrCalibMeasure[i + 15] = "Rate";
			adblRate[i + 15] = 0.01;

			aCompCalib[i + 15] = OTCIRS (
				dtIRSEffective,
				"USD",
				astrIRSTenor[i],
				0.
			);
		}

		/*
		 * Build the IR curve from the components, their calibration measures, and their calibration quotes.
		 */

		MergedDiscountForwardCurve dc = ScenarioDiscountCurveBuilder.NonlinearBuild (
			dtStart,
			"USD",
			aCompCalib,
			adblCompCalibValue,
			astrCalibMeasure,
			null
		);

		/*
		 * Re-calculate the component input measure quotes from the calibrated discount curve object
		 */

		for (int i = 0; i < aCompCalib.length; ++i)
			System.out.println (astrCalibMeasure[i] + "[" + i + "] = " +
				FormatUtil.FormatDouble (aCompCalib[i].measureValue (new ValuationParams (dtStart, dtStart, "USD"), null,
					MarketParamsBuilder.Create (dc, null, null, null, null, null, null),
						null, astrCalibMeasure[i]), 1, 5, 1.) + " | " + FormatUtil.FormatDouble (adblCompCalibValue[i], 1, 5, 1.));

		for (int i = 0; i < aCompCalib.length; ++i) {
			WengertJacobian wjComp = aCompCalib[i].jackDDirtyPVDManifestMeasure (
				new ValuationParams (
					dtStart,
					dtStart,
					"USD"
				),
				null,
				MarketParamsBuilder.Create (
					dc,
					null,
					null,
					null,
					null,
					null,
					null
				),
				null
			);

			System.out.println ("PV/DF Micro Jack[" + aCompCalib[i].name() + "]=" +
				(null == wjComp ? null : wjComp.displayString()));
		}
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		long lStart = System.nanoTime();

		DiscountCurveFromRatesInstruments();

		System.out.println ("Time Taken: " + ((int)(1.e-09 * (System.nanoTime() - lStart))) + " sec");

		EnvManager.TerminateEnv();
	}
}
