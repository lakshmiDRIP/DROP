
package org.drip.sample.piterbarg2012;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.fx.DomesticCollateralizedForeignForward;
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
 * DomesticCollateralForeignForexAnalysis contains an analysis of the correlation and volatility impact on the
 * 	price of a Domestic Collateralized ForeignPay-out Forex Contract.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DomesticCollateralForeignForexAnalysis {
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
		double dblFXFwdStrike = 0.984;
		double dblDomesticCollateralRate = 0.02;
		double dblCollateralizedFXRate = 1.10;

		CurrencyPair cp = CurrencyPair.FromCode (strForeignCurrency + "/" + strDomesticCurrency);

		MergedDiscountForwardCurve dcCcyDomesticCollatDomestic = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			strDomesticCurrency,
			dblDomesticCollateralRate
		);

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			strDomesticCurrency
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
			new double[] {0.}
		);

		VolatilityCurve vcFX = new FlatForwardVolatilityCurve (
			dtToday.julian(),
			VolatilityLabel.Standard (FXLabel.Standard (cp)),
			strDomesticCurrency,
			new int[] {dtToday.julian()},
			new double[] {0.}
		);

		MergedDiscountForwardCurve dcCcyForeignCollatDomestic = new ForeignCollateralizedDiscountCurve (
			strForeignCurrency,
			dcCcyDomesticCollatDomestic,
			fxCurve,
			vcForeignFunding,
			vcFX,
			new FlatUnivariate (0.)
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
			strForeignCurrency,
			strDomesticCurrency,
			dcCcyForeignCollatDomestic
		);

		mktParams.setPayCurrencyCollateralCurrencyCurve (
			strDomesticCurrency,
			strDomesticCurrency,
			dcCcyDomesticCollatDomestic
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				"FX::" + cp.code(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblCollateralizedFXRate},
				dblCollateralizedFXRate
			)
		);

		DomesticCollateralizedForeignForward dcff = new DomesticCollateralizedForeignForward (
			cp,
			dblFXFwdStrike,
			dtToday.addTenor (strMaturity)
		);

		CaseInsensitiveTreeMap<Double> mapBaseValue = dcff.value (
			new ValuationParams (
				dtToday,
				dtToday,
				strDomesticCurrency
			),
			null,
			mktParams,
			null
		);

		double dblBaselinePrice = mapBaseValue.get ("Price");

		double dblBaselineParForward = mapBaseValue.get ("ParForward");

		double[] adblForeignRatesVolatility = new double[] {
			0.1, 0.2, 0.3, 0.4, 0.5
		};
		double[] adblFXVolatility = new double[] {
			0.10, 0.15, 0.20, 0.25, 0.30
		};
		double[] adblFXForeignRatesCorrelation = new double[] {
			-0.99, -0.50, 0.00, 0.50, 0.99
		};

		System.out.println ("\tPrinting the Domestic Collateralized Foreign Forex Output in Order (Left -> Right):");

		System.out.println ("\t\tPrice (%)");

		System.out.println ("\t\tPrice Difference (%)");

		System.out.println ("\t\tPar Forward (abs)");

		System.out.println ("\t\tPar Forward Difference (abs)");

		System.out.println ("\t-------------------------------------------------------------");

		System.out.println ("\t-------------------------------------------------------------");

		for (double dblForeignRatesVolatility : adblForeignRatesVolatility) {
			for (double dblFXVolatility : adblFXVolatility) {
				for (double dblFXForeignRatesCorrelation : adblFXForeignRatesCorrelation) {
					dcCcyForeignCollatDomestic = new ForeignCollateralizedDiscountCurve (
						strForeignCurrency,
						dcCcyDomesticCollatDomestic,
						fxCurve,
						new FlatForwardVolatilityCurve (
							dtToday.julian(),
							VolatilityLabel.Standard (CollateralLabel.Standard (strForeignCurrency)),
							strDomesticCurrency,
							new int[] {dtToday.julian()},
							new double[] {dblForeignRatesVolatility}
						),
						new FlatForwardVolatilityCurve (
							dtToday.julian(),
							VolatilityLabel.Standard (FXLabel.Standard (cp)),
							strDomesticCurrency,
							new int[] {dtToday.julian()},
							new double[] {dblFXVolatility}
						),
						new FlatUnivariate (dblFXForeignRatesCorrelation)
					);

					mktParams.setPayCurrencyCollateralCurrencyCurve (
						strForeignCurrency,
						strDomesticCurrency,
						dcCcyForeignCollatDomestic
					);

					CaseInsensitiveTreeMap<Double> mapDCFF = dcff.value (
						valParams,
						null,
						mktParams,
						null
					);

					double dblPrice = mapDCFF.get ("Price");

					double dblParForward = mapDCFF.get ("ParForward");

					System.out.println ("\t[" +
						org.drip.quant.common.FormatUtil.FormatDouble (dblForeignRatesVolatility, 2, 0, 100.) + "%," +
						org.drip.quant.common.FormatUtil.FormatDouble (dblFXVolatility, 2, 0, 100.) + "%," +
						org.drip.quant.common.FormatUtil.FormatDouble (dblFXForeignRatesCorrelation, 2, 0, 100.) + "%] = " +
						org.drip.quant.common.FormatUtil.FormatDouble (dblPrice, 2, 2, 100.) + " | " +
						org.drip.quant.common.FormatUtil.FormatDouble (dblPrice - dblBaselinePrice, 2, 2, 100.) + " | " +
						org.drip.quant.common.FormatUtil.FormatDouble (dblParForward, 1, 4, 1.) + " | " +
						org.drip.quant.common.FormatUtil.FormatDouble (dblParForward - dblBaselineParForward, 1, 4, 1.)
					);
				}
			}
		}
	}
}
