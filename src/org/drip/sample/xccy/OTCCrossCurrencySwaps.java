
package org.drip.sample.xccy;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.market.otc.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.params.CurrencyPair;
import org.drip.product.rates.FloatFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * OTCCrossCurrencySwaps demonstrates the Construction and Valuation of the Cross-Currency Floating Swap of
 *  OTC contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class OTCCrossCurrencySwaps {

	private static final FloatFloatComponent OTCCrossCurrencyFloatFloat (
		final String strReferenceCurrency,
		final String strDerivedCurrency,
		final JulianDate dtSpot,
		final String strMaturityTenor,
		final double dblBasis,
		final double dblDerivedNotionalScaler)
	{
		CrossFloatSwapConvention ccfc = CrossFloatConventionContainer.ConventionFromJurisdiction (
			strReferenceCurrency,
			strDerivedCurrency
		);

		return ccfc.createFloatFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblBasis,
			1.,
			-1. * dblDerivedNotionalScaler
		);
	}

	private static final void OTCCrossCurrencyRun (
		final JulianDate dtSpot,
		final String strReferenceCurrency,
		final String strDerivedCurrency,
		final String strMaturityTenor,
		final double dblBasis,
		final double dblReferenceDerivedFXRate)
		throws Exception
	{
		double dblReferenceFundingRate = 0.02;
		double dblDerived3MForwardRate = 0.02;

		double dblReferenceFundingVol = 0.3;
		double dblDerivedForward3MVol = 0.3;
		double dblReferenceDerivedFXVol = 0.3;

		double dblDerived3MReferenceDerivedFXCorr = 0.1;
		double dblReferenceFundingDerived3MCorr = 0.1;
		double dblReferenceFundingReferenceDerivedFXCorr = 0.1;

		MergedDiscountForwardCurve dcReferenceFunding = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtSpot,
			strReferenceCurrency,
			dblReferenceFundingRate
		);

		ForwardLabel friDerived3M = ForwardLabel.Create (
			strDerivedCurrency,
			"3M"
		);

		ForwardCurve fcDerived3M = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtSpot,
			friDerived3M,
			dblDerived3MForwardRate
		);

		CurrencyPair cp = CurrencyPair.FromCode (
			strReferenceCurrency + "/" + strDerivedCurrency
		);

		FXLabel fxLabel = FXLabel.Standard (cp);

		FundingLabel fundingLabelReference = org.drip.state.identifier.FundingLabel.Standard (
			strReferenceCurrency
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setForwardState (
			fcDerived3M
		);

		mktParams.setFundingState (
			dcReferenceFunding
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				"FX::" + cp.code(),
				dtSpot,
				cp,
				new String[] {"10Y"},
				new double[] {dblReferenceDerivedFXRate},
				dblReferenceDerivedFXRate
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (friDerived3M),
				strDerivedCurrency,
				dblDerivedForward3MVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (fundingLabelReference),
				strReferenceCurrency,
				dblReferenceFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (fxLabel),
				strDerivedCurrency,
				dblReferenceDerivedFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			friDerived3M,
			fundingLabelReference,
			new FlatUnivariate (
				dblReferenceFundingDerived3MCorr
			)
		);

		mktParams.setForwardFXCorrelation (
			friDerived3M,
			fxLabel,
			new FlatUnivariate (
				dblDerived3MReferenceDerivedFXCorr
			)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabelReference,
			fxLabel,
			new FlatUnivariate (
				dblReferenceFundingReferenceDerivedFXCorr
			)
		);

		FloatFloatComponent xccySwap = OTCCrossCurrencyFloatFloat (
			strReferenceCurrency,
			strDerivedCurrency,
			dtSpot,
			strMaturityTenor,
			dblBasis,
			1. / dblReferenceDerivedFXRate
		);

		xccySwap.setPrimaryCode (
			strDerivedCurrency + "_" + strReferenceCurrency + "_OTC::FLOATFLOAT::" + strMaturityTenor
		);

		mktParams.setFixing (
			xccySwap.effectiveDate(),
			fxLabel,
			dblReferenceDerivedFXRate
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strReferenceCurrency + "," + strDerivedCurrency
		);

		CaseInsensitiveTreeMap<Double> mapXCcyOutput = xccySwap.value (
			valParams,
			null,
			mktParams,
			null
		);

		System.out.println (
			"\t| " + xccySwap.name() + "  [" + xccySwap.effectiveDate() + " -> " + xccySwap.maturityDate() + "]  =>  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("Price"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("DerivedParBasisSpread"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("ReferenceParBasisSpread"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("DerivedCleanDV01"), 1, 2, 10000.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("ReferenceCleanDV01"), 1, 2, 10000.) + "  |"
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

		JulianDate dtSpot = DateUtil.Today();

		System.out.println ("\t---------------------------------------------------------");

		System.out.println ("\t\tCROSS-CURRENCY FLOAT-FLOAT COMPONENT RUNS");

		System.out.println ("\t---------------------------------------------------------");

		System.out.println ("\tL -> R:");

		System.out.println ("\t\tCross Currency Swap Name");

		System.out.println ("\t\tFloat-Float Effective");

		System.out.println ("\t\tFloat-Float Maturity");

		System.out.println ("\t\tPrice");

		System.out.println ("\t\tDerived Stream Par Basis Spread");

		System.out.println ("\t\tReference Stream Par Basis Spread");

		System.out.println ("\t\tAnnualized Derived Stream Duration");

		System.out.println ("\t\tAnnualized Reference Stream Duration");

		System.out.println ("\t------------------------------------------------------------------------------------------------------------------");

		OTCCrossCurrencyRun (dtSpot, "USD", "AUD", "2Y", 0.0003, 0.7769);

		OTCCrossCurrencyRun (dtSpot, "USD", "CAD", "2Y", 0.0003, 0.7861);

		OTCCrossCurrencyRun (dtSpot, "USD", "CHF", "2Y", 0.0003, 1.0811);

		OTCCrossCurrencyRun (dtSpot, "USD", "CLP", "2Y", 0.0003, 0.0016);

		OTCCrossCurrencyRun (dtSpot, "USD", "DKK", "2Y", 0.0003, 0.1517);

		OTCCrossCurrencyRun (dtSpot, "USD", "EUR", "2Y", 0.0003, 1.1294);

		OTCCrossCurrencyRun (dtSpot, "USD", "GBP", "2Y", 0.0003, 1.5004);

		OTCCrossCurrencyRun (dtSpot, "USD", "JPY", "2Y", 0.0003, 0.0085);

		OTCCrossCurrencyRun (dtSpot, "USD", "MXN", "2Y", 0.0003, 0.0666);

		OTCCrossCurrencyRun (dtSpot, "USD", "NOK", "2Y", 0.0003, 0.1288);

		OTCCrossCurrencyRun (dtSpot, "USD", "PLN", "2Y", 0.0003, 0.2701);

		OTCCrossCurrencyRun (dtSpot, "USD", "SEK", "2Y", 0.0003, 0.1211);

		System.out.println ("\t------------------------------------------------------------------------------------------------------------------");
	}
}
