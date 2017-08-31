
package org.drip.sample.cross;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.fx.ComponentPair;
import org.drip.product.params.*;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * FloatFloatFloatFloatAnalysis demonstrates the Funding Volatility, Forward Volatility, FX Volatility,
 *  Funding/Forward Correlation, Funding/FX Correlation, and Forward/FX Correlation of the Cross Currency
 *  Basis Swap built out of a pair of float-float swaps.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FloatFloatFloatFloatAnalysis {

	private static final FloatFloatComponent MakeFloatFloatSwap (
		final JulianDate dtEffective,
		final boolean bFXMTM,
		final String strPayCurrency,
		final String strCouponCurrency,
		final String strMaturityTenor,
		final int iTenorInMonthsReference,
		final int iTenorInMonthsDerived)
		throws Exception
	{
		ComposableFloatingUnitSetting cfusReference = new ComposableFloatingUnitSetting (
			iTenorInMonthsReference + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				iTenorInMonthsReference + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFloatingUnitSetting cfusDerived = new ComposableFloatingUnitSetting (
			iTenorInMonthsDerived + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				iTenorInMonthsDerived + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cpsReference = new CompositePeriodSetting (
			12 / iTenorInMonthsReference,
			iTenorInMonthsReference + "M",
			strPayCurrency,
			null,
			-1.,
			null,
			null,
			bFXMTM ? null : new FixingSetting (
				FixingSetting.FIXING_PRESET_STATIC,
				null,
				dtEffective.julian()
			),
			null
		);

		CompositePeriodSetting cpsDerived = new CompositePeriodSetting (
			12 / iTenorInMonthsDerived,
			iTenorInMonthsDerived + "M",
			strPayCurrency,
			null,
			1.,
			null,
			null,
			bFXMTM ? null : new FixingSetting (
				FixingSetting.FIXING_PRESET_STATIC,
				null,
				dtEffective.julian()
			),
			null
		);

		List<Integer> lsReferenceStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			iTenorInMonthsReference + "M",
			strMaturityTenor,
			null
		);

		List<Integer> lsDerivedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			iTenorInMonthsDerived + "M",
			strMaturityTenor,
			null
		);

		Stream referenceStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsReferenceStreamEdgeDate,
				cpsReference,
				cfusReference
			)
		);

		Stream derivedStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsDerivedStreamEdgeDate,
				cpsDerived,
				cfusDerived
			)
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strPayCurrency,
			0
		);

		return new FloatFloatComponent (
			referenceStream,
			derivedStream,
			csp
		);
	}

	private static final void SetMarketParams (
		final int iValueDate,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardReferenceLabel1,
		final ForwardLabel forwardReferenceLabel2,
		final ForwardLabel forwardDerivedLabel1,
		final ForwardLabel forwardDerivedLabel2,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForwardReference1Vol,
		final double dblForwardReference2Vol,
		final double dblForwardDerived1Vol,
		final double dblForwardDerived2Vol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForwardReference1FundingCorr,
		final double dblForwardReference2FundingCorr,
		final double dblForwardDerived1FundingCorr,
		final double dblForwardDerived2FundingCorr,
		final double dblForwardReference1FXCorr,
		final double dblForwardReference2FXCorr,
		final double dblForwardDerived1FXCorr,
		final double dblForwardDerived2FXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardReferenceLabel1),
				forwardReferenceLabel1.currency(),
				dblForwardReference1Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardReferenceLabel2),
				forwardReferenceLabel2.currency(),
				dblForwardReference2Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardDerivedLabel1),
				forwardDerivedLabel1.currency(),
				dblForwardDerived1Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardDerivedLabel2),
				forwardDerivedLabel2.currency(),
				dblForwardDerived2Vol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fundingLabel),
				forwardDerivedLabel1.currency(),
				dblFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fxLabel),
				forwardDerivedLabel1.currency(),
				dblFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			forwardReferenceLabel1,
			fundingLabel,
			new FlatUnivariate (dblForwardReference1FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardReferenceLabel2,
			fundingLabel,
			new FlatUnivariate (dblForwardReference2FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardDerivedLabel1,
			fundingLabel,
			new FlatUnivariate (dblForwardDerived1FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardDerivedLabel2,
			fundingLabel,
			new FlatUnivariate (dblForwardDerived2FundingCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardReferenceLabel1,
			fxLabel,
			new FlatUnivariate (dblForwardReference1FXCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardReferenceLabel2,
			fxLabel,
			new FlatUnivariate (dblForwardReference2FXCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardDerivedLabel1,
			fxLabel,
			new FlatUnivariate (dblForwardDerived1FXCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardDerivedLabel2,
			fxLabel,
			new FlatUnivariate (dblForwardDerived2FXCorr)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabel,
			fxLabel,
			new FlatUnivariate (dblFundingFXCorr)
		);
	}

	private static final void VolCorrScenario (
		final ComponentPair[] aCP,
		final ValuationParams valParams,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardReferenceLabel1,
		final ForwardLabel forwardReferenceLabel2,
		final ForwardLabel forwardDerivedLabel1,
		final ForwardLabel forwardDerivedLabel2,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForwardReference1Vol,
		final double dblForwardReference2Vol,
		final double dblForwardDerived1Vol,
		final double dblForwardDerived2Vol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForwardReference1FundingCorr,
		final double dblForwardReference2FundingCorr,
		final double dblForwardDerived1FundingCorr,
		final double dblForwardDerived2FundingCorr,
		final double dblForwardReference1FXCorr,
		final double dblForwardReference2FXCorr,
		final double dblForwardDerived1FXCorr,
		final double dblForwardDerived2FXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		SetMarketParams (
			valParams.valueDate(),
			mktParams,
			forwardReferenceLabel1,
			forwardReferenceLabel2,
			forwardDerivedLabel1,
			forwardDerivedLabel2,
			fundingLabel,
			fxLabel,
			dblForwardReference1Vol,
			dblForwardReference2Vol,
			dblForwardDerived1Vol,
			dblForwardDerived2Vol,
			dblFundingVol,
			dblFXVol,
			dblForwardReference1FundingCorr,
			dblForwardReference2FundingCorr,
			dblForwardDerived1FundingCorr,
			dblForwardDerived2FundingCorr,
			dblForwardReference1FXCorr,
			dblForwardReference2FXCorr,
			dblForwardDerived1FXCorr,
			dblForwardDerived2FXCorr,
			dblFundingFXCorr
		);

		String strDump = "\t[" +
			FormatUtil.FormatDouble (dblForwardReference1Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference2Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived1Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived2Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFXVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference1FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference2FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived1FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived2FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference1FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference2FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived1FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived2FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingFXCorr, 2, 0, 100.) + "%] = ";

		for (int i = 0; i < aCP.length; ++i) {
			CaseInsensitiveTreeMap<Double> mapOutput = aCP[i].value (
				valParams,
				null,
				mktParams,
				null
			);

			if (0 != i) strDump += " || ";

			strDump +=
				FormatUtil.FormatDouble (mapOutput.get ("ReferenceCumulativeConvexityAdjustmentPremium"), 2, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (mapOutput.get ("DerivedCumulativeConvexityAdjustmentPremium"), 2, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (mapOutput.get ("CumulativeConvexityAdjustmentPremium"), 2, 0, 10000.);
		}

		System.out.println (strDump);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		String strReferenceCurrency = "USD";
		String strDerivedCurrency = "EUR";

		double dblReference3MForwardRate = 0.00750;
		double dblReference6MForwardRate = 0.01000;
		double dblDerived3MForwardRate = 0.00375;
		double dblDerived6MForwardRate = 0.00625;
		double dblReferenceFundingRate = 0.02;
		double dblReferenceDerivedFXRate = 1. / 1.28;

		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = org.drip.analytics.date.DateUtil.Today();

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			"USD"
		);

		ForwardLabel fri3MReference = ForwardLabel.Create (
			strReferenceCurrency,
			"3M"
		);

		ForwardLabel fri6MReference = ForwardLabel.Create (
			strReferenceCurrency,
			"6M"
		);

		ForwardLabel fri3MDerived = ForwardLabel.Create (
			strDerivedCurrency,
			"3M"
		);

		ForwardLabel fri6MDerived = ForwardLabel.Create (
			strDerivedCurrency,
			"6M"
		);

		FundingLabel fundingLabelReference = FundingLabel.Standard (strReferenceCurrency);

		CurrencyPair cp = CurrencyPair.FromCode (strReferenceCurrency + "/" + strDerivedCurrency);

		FXLabel fxLabel = FXLabel.Standard (cp);

		FloatFloatComponent floatFloatReference = MakeFloatFloatSwap (
			dtToday,
			false,
			strReferenceCurrency,
			strReferenceCurrency,
			"2Y",
			6,
			3
		);

		floatFloatReference.setPrimaryCode (
			"FLOAT::FLOAT::" + strReferenceCurrency + "::" + strReferenceCurrency + "_3M::" + strReferenceCurrency + "_6M::2Y"
		);

		FloatFloatComponent floatFloatDerivedMTM = MakeFloatFloatSwap (
			dtToday,
			true,
			strReferenceCurrency,
			strDerivedCurrency,
			"2Y",
			6,
			3
		);

		floatFloatDerivedMTM.setPrimaryCode (
			"FLOAT::FLOAT::MTM::" + strReferenceCurrency + "::" + strDerivedCurrency + "_3M::" + strDerivedCurrency + "_6M::2Y"
		);

		ComponentPair cpMTM = new ComponentPair (
			"FFFF_MTM",
			floatFloatReference,
			floatFloatDerivedMTM,
			null
		);

		FloatFloatComponent floatFloatDerivedNonMTM = MakeFloatFloatSwap (
			dtToday,
			false,
			strReferenceCurrency,
			strDerivedCurrency,
			"2Y",
			6,
			3
		);

		floatFloatDerivedNonMTM.setPrimaryCode (
			"FLOAT::FLOAT::NONMTM::" + strReferenceCurrency + "::" + strDerivedCurrency + "_3M::" + strDerivedCurrency + "_6M::2Y"
		);

		ComponentPair cpNonMTM = new ComponentPair (
			"FFFF_NonMTM",
			floatFloatReference,
			floatFloatDerivedNonMTM,
			null
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFixing (
			dtToday,
			fxLabel,
			dblReferenceDerivedFXRate
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri3MReference,
				dblReference3MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri6MReference,
				dblReference6MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri3MDerived,
				dblDerived3MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri6MDerived,
				dblDerived6MForwardRate
			)
		);

		mktParams.setFundingState (
			ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
				dtToday,
				strReferenceCurrency,
				dblReferenceFundingRate
			)
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				fxLabel.fullyQualifiedName(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblReferenceDerivedFXRate},
				dblReferenceDerivedFXRate
			)
		);

		double[] adblReference3MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblReference6MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblDerived3MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblDerived6MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblReferenceFundingVol = new double[] {
			0.1, 0.4
		};
		double[] adblReferenceDerivedFXVol = new double[] {
			0.1, 0.4
		};

		double[] adblReference3MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblReference6MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived3MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived6MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};

		double[] adblReference3MForwardFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblReference6MForwardFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived3MForwardFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived6MForwardFXCorr = new double[] {
			-0.1, 0.2
		};

		double[] adblFundingFXCorr = new double[] {
			-0.1, 0.2
		};

		for (double dblReference3MForwardVol : adblReference3MForwardVol) {
			for (double dblReference6MForwardVol : adblReference6MForwardVol) {
				for (double dblDerived3MForwardVol : adblDerived3MForwardVol) {
					for (double dblDerived6MForwardVol : adblDerived6MForwardVol) {
						for (double dblReferenceFundingVol : adblReferenceFundingVol) {
							for (double dblReferenceDerivedFXVol : adblReferenceDerivedFXVol) {
								for (double dblReference3MForwardFundingCorr : adblReference3MForwardFundingCorr) {
									for (double dblReference6MForwardFundingCorr : adblReference6MForwardFundingCorr) {
										for (double dblDerived3MForwardFundingCorr : adblDerived3MForwardFundingCorr) {
											for (double dblDerived6MForwardFundingCorr : adblDerived6MForwardFundingCorr) {
												for (double dblReference3MForwardFXCorr : adblReference3MForwardFXCorr) {
													for (double dblReference6MForwardFXCorr : adblReference6MForwardFXCorr) {
														for (double dblDerived3MForwardFXCorr : adblDerived3MForwardFXCorr) {
															for (double dblDerived6MForwardFXCorr : adblDerived6MForwardFXCorr) {
																for (double dblFundingFXCorr : adblFundingFXCorr)
																	VolCorrScenario (
																		new ComponentPair[] {
																			cpMTM,
																			cpNonMTM
																		},
																		valParams,
																		mktParams,
																		fri3MReference,
																		fri6MReference,
																		fri3MDerived,
																		fri6MDerived,
																		fundingLabelReference,
																		fxLabel,
																		dblReference3MForwardVol,
																		dblReference6MForwardVol,
																		dblDerived3MForwardVol,
																		dblDerived6MForwardVol,
																		dblReferenceFundingVol,
																		dblReferenceDerivedFXVol,
																		dblReference3MForwardFundingCorr,
																		dblReference6MForwardFundingCorr,
																		dblDerived3MForwardFundingCorr,
																		dblDerived6MForwardFundingCorr,
																		dblReference3MForwardFXCorr,
																		dblReference6MForwardFXCorr,
																		dblDerived3MForwardFXCorr,
																		dblDerived6MForwardFXCorr,
																		dblFundingFXCorr
																	);
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
