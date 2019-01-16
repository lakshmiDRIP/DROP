
package org.drip.sample.funding;

/*
 * Credit Analytics Imports
 */

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.*;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.product.rates.*;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.*;

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
 * <i>MultiStreamSwapMeasures</i> illustrates the creation, invocation, and usage of the MultiStreamSwap. It
 * shows how to:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Create the Discount Curve from the rates instruments.
 *  	</li>
 *  	<li>
 *  		Set up the valuation and the market parameters.
 *  	</li>
 *  	<li>
 * 			Create the Rates Basket from the fixed/float streams.
 *  	</li>
 *  	<li>
 * 			Value the Rates Basket.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/README.md">Funding Curve Builder</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MultiStreamSwapMeasures {

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
	 * Sample demonstrating building of rates curve from deposit/future/swaps
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static MergedDiscountForwardCurve BuildRatesCurveFromInstruments (
		final JulianDate dtStart,
		final String[] astrDepositTenor,
		final double[] adblDepositRate,
		final String[] astrIRSTenor,
		final double[] adblIRSRate,
		final double dblBump,
		final String strCurrency)
		throws Exception
	{
		int iNumDCInstruments = astrDepositTenor.length + adblIRSRate.length;
		int aiDate[] = new int[iNumDCInstruments];
		double adblRate[] = new double[iNumDCInstruments];
		String astrCalibMeasure[] = new String[iNumDCInstruments];
		double adblCompCalibValue[] = new double[iNumDCInstruments];
		CalibratableComponent aCompCalib[] = new CalibratableComponent[iNumDCInstruments];

		// Deposit Calibration

		ComposableFloatingUnitSetting cfusDeposit = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				strCurrency,
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cpsDeposit = new CompositePeriodSetting (
			4,
			"3M",
			strCurrency,
			null,
			1.,
			null,
			null,
			null,
			null
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		for (int i = 0; i < astrDepositTenor.length; ++i) {
			astrCalibMeasure[i] = "Rate";
			adblRate[i] = java.lang.Double.NaN;
			adblCompCalibValue[i] = adblDepositRate[i] + dblBump;

			aCompCalib[i] = new SingleStreamComponent (
				"DEPOSIT_" + astrDepositTenor[i],
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.EdgePair (
							dtStart,
							new JulianDate (aiDate[i] = dtStart.addTenor (astrDepositTenor[i]).julian())
						),
						cpsDeposit,
						cfusDeposit
					)
				),
				csp
			);

			aCompCalib[i].setPrimaryCode (astrDepositTenor[i]);
		}

		// IRS Calibration

		for (int i = 0; i < astrIRSTenor.length; ++i) {
			astrCalibMeasure[i + astrDepositTenor.length] = "Rate";
			adblRate[i + astrDepositTenor.length] = java.lang.Double.NaN;
			adblCompCalibValue[i + astrDepositTenor.length] = adblIRSRate[i] + dblBump;

			FixFloatComponent irs = OTCIRS (
				dtStart,
				strCurrency,
				astrIRSTenor[i],
				adblIRSRate[i] + dblBump
			);

			irs.setPrimaryCode ("IRS." + astrIRSTenor[i] + "." + strCurrency);

			aCompCalib[i + astrDepositTenor.length] = irs;
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
	 * Sample demonstrating creation of a rates basket instance from component fixed and floating streams
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final RatesBasket MakeRatesBasket (
		final JulianDate dtEffective)
		throws Exception
	{
		/*
		 * Create a sequence of Fixed Streams
		 */

		Stream[] aFixedStream = new Stream[3];

		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			"USD",
			false,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFixedUnitSetting cfusFixed3Y = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.03,
			0.,
			"USD"
		);

		ComposableFixedUnitSetting cfusFixed5Y = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.05,
			0.,
			"USD"
		);

		ComposableFixedUnitSetting cfusFixed7Y = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.07,
			0.,
			"USD"
		);

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
			2,
			"6M",
			"USD",
			null,
			1.,
			null,
			null,
			null,
			null
		);

		aFixedStream[0] = new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective,
					"6M",
					"3Y",
					null
				),
				cpsFixed,
				ucasFixed,
				cfusFixed3Y
			)
		);

		aFixedStream[1] = new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective,
					"6M",
					"5Y",
					null
				),
				cpsFixed,
				ucasFixed,
				cfusFixed5Y
			)
		);

		aFixedStream[2] = new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective,
					"6M",
					"7Y",
					null
				),
				cpsFixed,
				ucasFixed,
				cfusFixed7Y
			)
		);

		/*
		 * Create a sequence of Float Streams
		 */

		Stream[] aFloatStream = new Stream[3];

		ComposableFloatingUnitSetting cfusFloat3Y = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.03
		);

		ComposableFloatingUnitSetting cfusFloat5Y = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.05
		);

		ComposableFloatingUnitSetting cfusFloat7Y = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.07
		);

		CompositePeriodSetting cpsFloat = new CompositePeriodSetting (
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

		aFloatStream[0] = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective,
					"6M",
					"3Y",
					null
				),
				cpsFloat,
				cfusFloat3Y
			)
		);

		aFloatStream[1] = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective,
					"6M",
					"5Y",
					null
				),
				cpsFloat,
				cfusFloat5Y
			)
		);

		aFloatStream[2] = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective,
					"6M",
					"7Y",
					null
				),
				cpsFloat,
				cfusFloat7Y
			)
		);

		/*
		 * Create a Rates Basket instance containing the fixed and floating streams
		 */

		return new RatesBasket (
			"RATESBASKET",
			aFixedStream,
			aFloatStream
		);
	}

	/*
	 * Sample demonstrating creation of discount curve from cash/futures/swaps
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void MultiLegSwapSample()
		throws Exception
	{
		JulianDate dtValue = DateUtil.Today();

		/*
		 * Create the Discount Curve from the rates instruments
		 */

		String[] astrCashTenor = new String[] {"3M"};
		double[] adblCashRate = new double[] {0.00276};
		String[] astrIRSTenor = new String[] {   "1Y",    "2Y",    "3Y",    "4Y",    "5Y",    "6Y",    "7Y",
		   "8Y",    "9Y",   "10Y",   "11Y",   "12Y",   "15Y",   "20Y",   "25Y",   "30Y",   "40Y",   "50Y"};
		double[] adblIRSRate = new double[]  {0.00367, 0.00533, 0.00843, 0.01238, 0.01609, 0.01926, 0.02191,
			0.02406, 0.02588, 0.02741, 0.02870, 0.02982, 0.03208, 0.03372, 0.03445, 0.03484, 0.03501, 0.03484};

		MergedDiscountForwardCurve dc = BuildRatesCurveFromInstruments (
			dtValue,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.,
			"USD"
		);

		/*
		 * Set up the valuation and the market parameters
		 */

		ValuationParams valParams = ValuationParams.Spot (
			dtValue,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		double dblUSDABCFXRate = 1.;

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFundingState (dc);

		CurrencyPair cp = CurrencyPair.FromCode ("USD/ABC");

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				"FX::" + cp.code(),
				dtValue,
				cp,
				new String[] {"10Y"},
				new double[] {dblUSDABCFXRate},
				dblUSDABCFXRate
			)
		);

		/*
		 * Create the Rates Basket from the streams
		 */

		RatesBasket rb = MakeRatesBasket (dtValue);

		/*
		 * Value the Rates Basket
		 */

		CaseInsensitiveTreeMap<Double> mapRBResults = rb.value (
			valParams,
			null,
			mktParams,
			null
		);

		System.out.println (mapRBResults);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		// String strConfig = "c:\\Lakshmi\\BondAnal\\Config.xml";

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		MultiLegSwapSample();

		EnvManager.TerminateEnv();
	}
}
