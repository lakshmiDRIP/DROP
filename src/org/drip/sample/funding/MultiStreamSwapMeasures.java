
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

/**
 * MultiStreamSwapMeasures illustrates the creation, invocation, and usage of the MultiStreamSwap. It shows
 *  how to:
 *  
 * 	- Create the Discount Curve from the rates instruments.
 *  - Set up the valuation and the market parameters.
 * 	- Create the Rates Basket from the fixed/float streams.
 * 	- Value the Rates Basket.
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
	}
}
