
package org.drip.sample.piterbarg2012;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.*;
import org.drip.product.params.CurrencyPair;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.curve.*;
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
 * DeterministicCollateralChoiceZeroCoupon contains an analysis of the impact on the single cash flow
 * 	discount factor of a Zero Coupon collateralized using a deterministic choice of collateral.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DeterministicCollateralChoiceZeroCoupon {
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
		double dblDomesticCollateralRate = 0.03;
		double dblForeignCollateralRate = 0.02;
		double dblCollateralizedFXRate = 1.03;
		double dblForeignRatesVolatility = 0.20;
		double dblFXVolatility = 0.10;
		double dblFXForeignRatesCorrelation = 0.30;
		int iDiscreteCollateralizationIncrement = 30; // 30 Days
		String strCollateralizationCheckTenor = "5Y";

		MergedDiscountForwardCurve dcCcyDomesticCollatDomestic = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			strDomesticCurrency,
			dblDomesticCollateralRate
		);

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

		ForeignCollateralizedDiscountCurve dcCcyDomesticCollatForeign = new ForeignCollateralizedDiscountCurve (
			strDomesticCurrency,
			dcCcyForeignCollatForeign,
			fxCurve,
			new FlatForwardVolatilityCurve (
				dtToday.julian(),
				VolatilityLabel.Standard (CollateralLabel.Standard (strForeignCurrency)),
				cp.denomCcy(),
				new int[] {dtToday.julian()},
				new double[] {dblForeignRatesVolatility}
			),
			new FlatForwardVolatilityCurve (
				dtToday.julian(),
				VolatilityLabel.Standard (FXLabel.Standard (cp)),
				cp.denomCcy(),
				new int[] {dtToday.julian()},
				new double[] {dblFXVolatility}
			),
			new FlatUnivariate (dblFXForeignRatesCorrelation)
		);

		DeterministicCollateralChoiceDiscountCurve dccdc = new DeterministicCollateralChoiceDiscountCurve (
			dcCcyDomesticCollatDomestic,
			new org.drip.state.curve.ForeignCollateralizedDiscountCurve[] {dcCcyDomesticCollatForeign},
			iDiscreteCollateralizationIncrement
		);

		int iStart = dtToday.julian() + iDiscreteCollateralizationIncrement;

		double dblCollateralizationCheckDate = dtToday.addTenor (strCollateralizationCheckTenor).julian();

		System.out.println ("\tPrinting the Zero Coupon Bond Price in Order (Left -> Right):");

		System.out.println ("\t\tDate");

		System.out.println ("\t\tDomestic Collateral Price (Par = 100)");

		System.out.println ("\t\tForeign Collateral Price (Par = 100)");

		System.out.println ("\t\tChoice Collateral Price (Par = 100)");

		System.out.println ("\t-------------------------------------------------------------");

		System.out.println ("\t-------------------------------------------------------------");

		for (int iDate = iStart; iDate <= dblCollateralizationCheckDate; iDate += iDiscreteCollateralizationIncrement) {
			double dblDomesticCollateralDF = dcCcyDomesticCollatDomestic.df (iDate);

			double dblForeignCollateralDF = dcCcyDomesticCollatForeign.df (iDate);

			double dblChoiceCollateralDF = dccdc.df (iDate);

			System.out.println (
				new JulianDate (iDate) + " => " +
				FormatUtil.FormatDouble (dblDomesticCollateralDF, 2, 2, 100.) + " | " +
				FormatUtil.FormatDouble (dblForeignCollateralDF, 2, 2, 100.) + " | " +
				FormatUtil.FormatDouble (dblChoiceCollateralDF, 2, 2, 100.)
			);
		}
	}
}
