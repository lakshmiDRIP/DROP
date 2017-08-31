
package org.drip.sample.piterbarg2010;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.measure.dynamics.*;
import org.drip.param.valuation.ValuationParams;
import org.drip.pricer.option.BlackScholesAlgorithm;
import org.drip.product.option.EuropeanCallPut;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.xva.csa.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * CSAImpliedMeasureDifference compares the Differences between the CSA and the non-CSA Implied Distribution,
 * 	expressed in Implied Volatilities across Strikes, and across Correlations. The References are:
 *  
 *  - Barden, P. (2009): Equity Forward Prices in the Presence of Funding Spreads, ICBI Conference, Rome.
 *  
 *  - Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk of
 *  	Derivative Portfolios, ICBI Conference, Rome.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps, Journal of Finance 62 383-410.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CSAImpliedMeasureDifference {

	private static final MergedDiscountForwardCurve OvernightCurve (
		final String strCurrency,
		final JulianDate dtSpot)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			// "2D",
			"3D"
		};

		double[] adblDepositQuote = new double[] {
			0.0004,		// 1D
			// 0.0004,		// 2D
			0.0004		// 3D
		};

		String[] astrShortEndOISMaturityTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M"
		};

		double[] adblShortEndOISQuote = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		String[] astrOISFuturesEffectiveTenor = new String[] {
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		String[] astrOISFuturesMaturityTenor = new String[] {
			"1M",
			"1M",
			"1M",
			"1M",
			"1M"
		};

		double[] adblOISFuturesQuote = new double[] {
			 0.00046,    //   1M x 1M
			 0.00016,    //   2M x 1M
			-0.00007,    //   3M x 1M
			-0.00013,    //   4M x 1M
			-0.00014     //   5M x 1M
		};

		String[] astrLongEndOISMaturityTenor = new String[] {
			"15M",
			"18M",
			"21M",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y"
		};

		double[] adblLongEndOISQuote = new double[] {
			0.00002,    //  15M
			0.00008,    //  18M
			0.00021,    //  21M
			0.00036,    //   2Y
			0.00127,    //   3Y
			0.00274,    //   4Y
			0.00456,    //   5Y
			0.00647,    //   6Y
			0.00827,    //   7Y
			0.00996,    //   8Y
			0.01147,    //   9Y
			0.01280,    //  10Y
			0.01404,    //  11Y
			0.01516,    //  12Y
			0.01764,    //  15Y
			0.01939,    //  20Y
			0.02003,    //  25Y
			0.02038     //  30Y
		};

		return LatentMarketStateBuilder.SmoothOvernightCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"Rate",
			astrShortEndOISMaturityTenor,
			adblShortEndOISQuote,
			"SwapRate",
			astrOISFuturesEffectiveTenor,
			astrOISFuturesMaturityTenor,
			adblOISFuturesQuote,
			"SwapRate",
			astrLongEndOISMaturityTenor,
			adblLongEndOISQuote,
			"SwapRate"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strTenor = "10Y";
		double dblCSAForward = 100.;
		double dblFundingSpreadVolatility = 0.015;
		double dblFundingSpreadMeanReversionRate = 0.05;
		String strCurrency = "USD";

		JulianDate dtSpot = DateUtil.Today().addBusDays (
			0,
			strCurrency
		);

		double[] adblCorrelation = new double[] {
			-0.30,
			-0.10,
			 0.00,
			 0.10
		};

		double[] adblStrike = new double[] {
			 50.,
			 60.,
			 70.,
			 80.,
			 90.,
			100.,
			110.,
			120.,
			130.,
			140.,
			150.
		};

		double[] adblCSAImpliedVolatility = new double[] {
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30,
			 0.30
		};

		double[][] aadblNoCSAForward = new double[adblCorrelation.length][adblStrike.length];
		double[][] aadblMeasureShiftScale = new double[adblCorrelation.length][adblStrike.length];

		DiffusionEvaluatorMeanReversion demrFundingSpread = DiffusionEvaluatorMeanReversion.Standard (
			dblFundingSpreadMeanReversionRate,
			0.,
			dblFundingSpreadVolatility
		);

		System.out.println ();

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                        PROBABILITY MEASURE DISTRIBUTION SHIFT                                        ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||  L -> R :                                                                                                            ||");

		System.out.println ("\t||           - Correlation                                                                                              ||");

		System.out.println ("\t||           - Adjustments for Strikes in unit of 10, from 50 to 150                                                    ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		for (int j = 0; j < adblCorrelation.length; ++j) {
			String strDump = "\t|| " + FormatUtil.FormatDouble (adblCorrelation[j], 2, 0, 100.) + "% => ";

			for (int i = 0; i < adblStrike.length; ++i) {
				DiffusionEvaluatorLogarithmic delUnderlying = DiffusionEvaluatorLogarithmic.Standard (
					0.,
					adblCSAImpliedVolatility[i]
				);

				FundingBasisEvolver fbe = new FundingBasisEvolver (
					delUnderlying,
					demrFundingSpread,
					adblCorrelation[j]
				);

				aadblNoCSAForward[j][i] = dblCSAForward * fbe.CSANoCSARatio (strTenor);

				NumeraireInducedMeasureShift nims = new NumeraireInducedMeasureShift (
					dblCSAForward,
					aadblNoCSAForward[j][i],
					dblCSAForward * dblCSAForward * adblCSAImpliedVolatility[i] * adblCSAImpliedVolatility[i]
				);

				aadblMeasureShiftScale[j][i] = nims.densityRescale (adblStrike[i]);

				strDump = strDump + " " + FormatUtil.FormatDouble (aadblMeasureShiftScale[j][i], 1, 4, 1.) + " |";
			}

			System.out.println (strDump + "|");;
		}

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ();

		MergedDiscountForwardCurve dcOvernight = OvernightCurve (
			strCurrency,
			dtSpot
		);

		JulianDate dtMaturity = dtSpot.addTenor (strTenor);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		double[] adblPrice = new double[adblStrike.length];
		EuropeanCallPut[] aECP = new EuropeanCallPut[adblStrike.length];

		System.out.println ("\t||---------------------------------------------------------------------------------------------------------------------------||");

		String strDump = "\t|| CSA ATM Option Price => ";

		for (int i = 0; i < adblStrike.length; ++i) {
			aECP[i] = new EuropeanCallPut (
				dtMaturity,
				adblStrike[i]
			);

			Map<String, Double> mapOptionCalc = aECP[i].value (
				valParams,
				dblCSAForward,
				true,
				dcOvernight,
				new FlatUnivariate (adblCSAImpliedVolatility[i]),
				new BlackScholesAlgorithm()
			);

			adblPrice[i] = mapOptionCalc.get ("CallPrice");

			strDump = strDump + FormatUtil.FormatDouble (adblPrice[i], 2, 2, 1.) + "  |";
		}

		System.out.println (strDump + "|");

		System.out.println ("\t||---------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ();

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                CSA CONVEXITY ADJUSTMENT IMPLIED VOLATILITY                                ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||  L -> R :                                                                                                 ||");

		System.out.println ("\t||           - Implied Volatility (%)                                                                        ||");

		System.out.println ("\t||           - Adjustments for Strikes in unit of 10, from 50 to 150                                         ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------||");

		for (int j = 0; j < adblCorrelation.length; ++j) {
			strDump = "\t|| " + FormatUtil.FormatDouble (adblCorrelation[j], 2, 0, 100.) + "% => ";

			for (int i = 0; i < adblStrike.length; ++i) {
				double dblReimpliedVolatility = aECP[i].implyVolatilityFromCallPrice (
					valParams,
					aadblNoCSAForward[j][i],
					true,
					dcOvernight,
					adblPrice[i]
				);

				strDump = strDump + FormatUtil.FormatDouble (dblReimpliedVolatility, 2, 2, 100.) + "% |";
			}

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------||");

		System.out.println ();
	}
}
