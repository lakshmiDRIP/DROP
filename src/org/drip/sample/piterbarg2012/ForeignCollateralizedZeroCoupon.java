
package org.drip.sample.piterbarg2012;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.*;
import org.drip.product.params.CurrencyPair;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.curve.ForeignCollateralizedDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.fx.FXCurve;
import org.drip.state.identifier.*;
import org.drip.state.nonlinear.*;

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
 * ForeignCollateralizedZeroCoupon contains an analysis of the correlation and volatility impact on the
 * 	single cash flow discount factor of a Foreign Collateralized Zero Coupon.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ForeignCollateralizedZeroCoupon {
	private static final double ZeroCouponVolCorr (
		final JulianDate dtSpot,
		final CurrencyPair cp,
		final MergedDiscountForwardCurve dcCcyForeignCollatForeign,
		final FXCurve fxCurve,
		final double dblForeignRatesVolatility,
		final double dblFXVolatility,
		final double dblFXForeignRatesCorrelation,
		final JulianDate dtMaturity,
		final double dblBaselinePrice)
		throws Exception
	{
		MergedDiscountForwardCurve dcCcyDomesticCollatForeign = new ForeignCollateralizedDiscountCurve (
			cp.denomCcy(),
			dcCcyForeignCollatForeign,
			fxCurve,
			new FlatForwardVolatilityCurve (
				dtSpot.julian(),
				VolatilityLabel.Standard (CollateralLabel.Standard (cp.numCcy())),
				cp.denomCcy(),
				new int[] {dtSpot.julian()},
				new double[] {dblForeignRatesVolatility}
			),
			new FlatForwardVolatilityCurve (
				dtSpot.julian(),
				VolatilityLabel.Standard (FXLabel.Standard (cp)),
				cp.denomCcy(),
				new int[] {dtSpot.julian()},
				new double[] {dblFXVolatility}
			),
			new FlatUnivariate (dblFXForeignRatesCorrelation)
		);

		double dblPrice = dcCcyDomesticCollatForeign.df (dtMaturity);

		System.out.println ("\t[" +
			org.drip.quant.common.FormatUtil.FormatDouble (dblForeignRatesVolatility, 2, 0, 100.) + "%," +
			org.drip.quant.common.FormatUtil.FormatDouble (dblFXVolatility, 2, 0, 100.) + "%," +
			org.drip.quant.common.FormatUtil.FormatDouble (dblFXForeignRatesCorrelation, 2, 0, 100.) + "%] =" +
			org.drip.quant.common.FormatUtil.FormatDouble (dblPrice, 1, 2, 100.) + " | " +
			org.drip.quant.common.FormatUtil.FormatDouble (dblPrice - dblBaselinePrice, 1, 0, 10000.)
		);

		return dblPrice;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today();

		String strMaturityTenor = "5Y";
		String strDomesticCurrency = "USD";
		String strForeignCurrency = "JPY";
		double dblForeignCollateralRate = 0.02;
		double dblCollateralizedFXRate = 0.01;

		JulianDate dtZeroCouponMaturity = dtToday.addTenor (strMaturityTenor);

		MergedDiscountForwardCurve dcCcyForeignCollatForeign = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			strForeignCurrency,
			dblForeignCollateralRate
		);

		CurrencyPair cp = CurrencyPair.FromCode (strForeignCurrency + "/" + strDomesticCurrency);

		FXCurve fxCurve = new FlatForwardFXCurve (
			dtToday.julian(),
			cp,
			dblCollateralizedFXRate,
			new int[] {dtToday.julian()},
			new double[] {dblCollateralizedFXRate}
		);

		double dblBaselinePrice = ZeroCouponVolCorr (
			dtToday,
			cp,
			dcCcyForeignCollatForeign,
			fxCurve,
			0.,
			0.,
			0.,
			dtZeroCouponMaturity,
			0.
		);

		double[] adblForeignRatesVol = new double[] {
			0.1, 0.2, 0.3, 0.4, 0.5
		};
		double[] adblFXVol = new double[] {
			0.10, 0.15, 0.20, 0.25, 0.30
		};
		double[] adblForeignRatesFXCorr = new double[] {
			-0.99, -0.50, 0.00, 0.50, 0.99
		};

		System.out.println ("\tPrinting the Zero Coupon Bond Price in Order (Left -> Right):");

		System.out.println ("\t\tPrice (%)");

		System.out.println ("\t\tDifference from Baseline (pt)");

		System.out.println ("\t-------------------------------------------------------------");

		System.out.println ("\t-------------------------------------------------------------");

		for (double dblForeignRatesVol : adblForeignRatesVol) {
			for (double dblFXVol : adblFXVol) {
				for (double dblForeignRatesFXCorr : adblForeignRatesFXCorr)
					ZeroCouponVolCorr (
						dtToday,
						cp,
						dcCcyForeignCollatForeign,
						fxCurve,
						dblForeignRatesVol,
						dblFXVol,
						dblForeignRatesFXCorr,
						dtZeroCouponMaturity,
						dblBaselinePrice
					);
			}
		}
	}
}
