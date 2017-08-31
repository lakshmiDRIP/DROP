
package org.drip.sample.piterbarg2012;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.fx.ForeignCollateralizedDomesticForward;
import org.drip.product.params.CurrencyPair;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.curve.ForeignCollateralizedDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.fx.FXCurve;
import org.drip.state.identifier.*;
import org.drip.state.nonlinear.*;
import org.drip.state.volatility.VolatilityCurve;

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
 * ForeignCollateralDomesticForex demonstrates the construction and the usage of Foreign Currency
 * 	Collateralized Domestic Pay-out FX forward product, and generation of its measures.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class ForeignCollateralDomesticForex {
	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today();

		String strDomesticCurrency = "USD";
		String strForeignCurrency = "EUR";
		String strMaturity = "1Y";
		double dblFXFwdStrike = 1.016;
		double dblForeignCollateralRate = 0.02;
		double dblCollateralizedFXRate = 1.010;
		double dblForeignRatesVolatility = 0.30;
		double dblFXVolatility = 0.10;
		double dblFXForeignRatesCorrelation = 0.20;

		CurrencyPair cp = CurrencyPair.FromCode (strForeignCurrency + "/" + strDomesticCurrency);

		MergedDiscountForwardCurve dcCcyForeignCollatForeign = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			strForeignCurrency,
			dblForeignCollateralRate
		);

		FXCurve fxCurve = new FlatForwardFXCurve (
			dtToday.julian(),
			cp,
			dblCollateralizedFXRate,
			new int[] {dtToday.julian()},
			new double[] {dblCollateralizedFXRate}
		);

		VolatilityCurve vcForeignFunding = new FlatForwardVolatilityCurve (
			dtToday.julian(),
			VolatilityLabel.Standard (CollateralLabel.Standard (strForeignCurrency)),
			strDomesticCurrency,
			new int[] {dtToday.julian()},
			new double[] {dblForeignRatesVolatility}
		);

		VolatilityCurve vcFX = new FlatForwardVolatilityCurve (
			dtToday.julian(),
			VolatilityLabel.Standard (FXLabel.Standard (cp)),
			strDomesticCurrency,
			new int[] {dtToday.julian()},
			new double[] {dblFXVolatility}
		);

		MergedDiscountForwardCurve dcCcyDomesticCollatForeign = new ForeignCollateralizedDiscountCurve (
			strDomesticCurrency,
			dcCcyForeignCollatForeign,
			fxCurve,
			vcForeignFunding,
			vcFX,
			new FlatUnivariate (dblFXForeignRatesCorrelation)
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			null,
			null,
			null,
			null,
			null,
			null,
			null
		);

		mktParams.setPayCurrencyCollateralCurrencyCurve (
			strDomesticCurrency,
			strForeignCurrency,
			dcCcyDomesticCollatForeign
		);

		mktParams.setPayCurrencyCollateralCurrencyCurve (
			strForeignCurrency,
			strForeignCurrency,
			dcCcyForeignCollatForeign
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				"FX::" + cp.code(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblCollateralizedFXRate / 365.25},
				dblCollateralizedFXRate / 365.25
			)
		);

		ForeignCollateralizedDomesticForward fcff = new ForeignCollateralizedDomesticForward (
			cp,
			dblFXFwdStrike,
			dtToday.addTenor (strMaturity)
		);

		CaseInsensitiveTreeMap<Double> mapFCFF = fcff.value (
			new ValuationParams (
				dtToday,
				dtToday,
				strDomesticCurrency
			),
			null,
			mktParams,
			null
		);

		for (Map.Entry<String, Double> me : mapFCFF.entrySet())
			System.out.println ("\t" + me.getKey() + " => " + me.getValue());
	}
}
