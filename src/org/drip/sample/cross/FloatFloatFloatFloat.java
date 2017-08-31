
package org.drip.sample.cross;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.fx.ComponentPair;
import org.drip.product.params.*;
import org.drip.product.rates.*;
import org.drip.quant.common.*;
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
 * FloatFloatFloatFloat demonstrates the construction, the usage, and the eventual valuation of the Cross
 *  Currency Basis Swap built out of a pair of float-float swaps.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FloatFloatFloatFloat {

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

		double dblReference3MForwardVol = 0.3;
		double dblReference6MForwardVol = 0.3;
		double dblDerived3MForwardVol = 0.3;
		double dblDerived6MForwardVol = 0.3;
		double dblReferenceFundingVol = 0.3;
		double dblReferenceDerivedFXVol = 0.3;

		double dblReference3MForwardFundingCorr = 0.15;
		double dblReference6MForwardFundingCorr = 0.15;
		double dblDerived3MForwardFundingCorr = 0.15;
		double dblDerived6MForwardFundingCorr = 0.15;

		double dblReference3MForwardFXCorr = 0.15;
		double dblReference6MForwardFXCorr = 0.15;
		double dblDerived3MForwardFXCorr = 0.15;
		double dblDerived6MForwardFXCorr = 0.15;

		double dblFundingFXCorr = 0.15;

		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = org.drip.analytics.date.DateUtil.Today();

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			strReferenceCurrency
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

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fri3MReference),
				fri3MReference.currency(),
				dblReference3MForwardVol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fri6MReference),
				fri6MReference.currency(),
				dblReference6MForwardVol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fri3MDerived),
				fri3MDerived.currency(),
				dblDerived3MForwardVol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fri6MDerived),
				fri6MDerived.currency(),
				dblDerived6MForwardVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fundingLabelReference),
				strReferenceCurrency,
				dblReferenceFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fxLabel),
				strDerivedCurrency,
				dblReferenceDerivedFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			fri3MReference,
			fundingLabelReference,
			new FlatUnivariate (dblReference3MForwardFundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			fri6MReference,
			fundingLabelReference,
			new FlatUnivariate (dblReference6MForwardFundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			fri3MDerived,
			fundingLabelReference,
			new FlatUnivariate (dblDerived3MForwardFundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			fri6MDerived,
			fundingLabelReference,
			new FlatUnivariate (dblDerived6MForwardFundingCorr)
		);

		mktParams.setForwardFXCorrelation (
			fri3MReference,
			fxLabel,
			new FlatUnivariate (dblReference3MForwardFXCorr)
		);

		mktParams.setForwardFXCorrelation (
			fri6MReference,
			fxLabel,
			new FlatUnivariate (dblReference6MForwardFXCorr)
		);

		mktParams.setForwardFXCorrelation (
			fri3MDerived,
			fxLabel,
			new FlatUnivariate (dblDerived3MForwardFXCorr)
		);

		mktParams.setForwardFXCorrelation (
			fri6MDerived,
			fxLabel,
			new FlatUnivariate (dblDerived6MForwardFXCorr)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabelReference,
			fxLabel,
			new FlatUnivariate (dblFundingFXCorr)
		);

		CaseInsensitiveTreeMap<Double> mapMTMOutput = cpMTM.value (
			valParams,
			null,
			mktParams,
			null
		);

		CaseInsensitiveTreeMap<Double> mapNonMTMOutput = cpNonMTM.value (
			valParams,
			null,
			mktParams,
			null
		);

		for (Map.Entry<String, Double> me : mapMTMOutput.entrySet()) {
			String strKey = me.getKey();

			if (null != me.getValue() && null != mapNonMTMOutput.get (strKey)) {
				double dblMTMMeasure = me.getValue();

				double dblNonMTMMeasure = mapNonMTMOutput.get (strKey);

				String strReconcile = NumberUtil.WithinTolerance (
					dblMTMMeasure,
					dblNonMTMMeasure,
					1.e-08,
					1.e-04
				) ? "RECONCILES" : "DOES NOT RECONCILE";

				System.out.println ("\t" +
					FormatUtil.FormatDouble (dblMTMMeasure, 1, 8, 1.) + " | " +
					FormatUtil.FormatDouble (dblNonMTMMeasure, 1, 8, 1.) + " | " +
					strReconcile + " <= " + strKey);
			}
		}
	}
}
