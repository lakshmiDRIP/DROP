
package org.drip.sample.athl;

import org.drip.execution.athl.*;
import org.drip.execution.parameters.AssetFlowSettings;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * EquityMarketImpactDRI demonstrates the Reconciliation of the Equity Market Impact with that determined
 *  empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren (2003) for
 *  DRI. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 *
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 * 
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EquityMarketImpactDRI {

	private static final void TemporaryImpactReconciler (
		final TemporaryImpact ti,
		final double dblTradeSize,
		final double dblTime,
		final double dblNormalizedTemporaryImpactReconciler,
		final double dblDenormalizedTemporaryImpactReconciler,
		final double dblDenormalizedPermanentImpact,
		final double dblRealizedImpactReconciler)
		throws Exception
	{
		double dblNormalizedTemporaryImpact = ti.evaluate (
			dblTradeSize / (ti.assetFlowParameters().averageDailyVolume() * dblTime)
		);

		double dblDenormalizedTemporaryImpact = ti.evaluate (
			dblTradeSize,
			dblTime
		);

		System.out.println (
			"\t| " +
			FormatUtil.FormatDouble (dblTime, 1, 1, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTemporaryImpact, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTemporaryImpactReconciler, 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (dblDenormalizedTemporaryImpact, 2, 0, 100.) + " | " +
			FormatUtil.FormatDouble (dblDenormalizedTemporaryImpactReconciler, 2, 0, 1.) + " ||" +
			FormatUtil.FormatDouble (dblDenormalizedPermanentImpact + dblDenormalizedTemporaryImpact, 2, 0, 100.) + " | " +
			FormatUtil.FormatDouble (dblRealizedImpactReconciler, 2, 0, 1.) + " ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strAssetName = "DRI";
		double dblAverageDailyVolume = 1929000.;
		double dblSharesOutstanding = 168000000.;
		double dblDailyVolatility = 2.26;
		double dblTradeSize = 192900.;
		double dblTradeTime = 1.;

		double dblInverseTurnoverReconciler = 87.092;
		double dblNormalizedTradeSizeReconciler = 0.1;
		double dblNormalizedPermanentImpactReconciler = 0.096;
		double dblDenormalizedPermanentImpactReconciler = 21.679;

		double[] adblTime = new double[] {
			0.1,
			0.2,
			0.5
		};

		double[] adblNormalizedTemporaryImpactReconciler = new double[] {
			0.142,
			0.094,
			0.054
		};

		double[] adblDenormalizedTemporaryImpactReconciler = new double[] {
			32,
			21,
			12
		};

		double[] adblRealizedImpactReconciler = new double[] {
			43,
			32,
			23
		};

		AssetFlowSettings afs = new AssetFlowSettings (
			strAssetName,
			dblAverageDailyVolume,
			dblSharesOutstanding,
			dblDailyVolatility
		);

		TemporaryImpact ti = new TemporaryImpact (afs);

		PermanentImpactNoArbitrage pina = new PermanentImpactNoArbitrage (afs);

		double dblDenormalizedPermanentImpact = pina.evaluate (
			dblTradeSize,
			dblTradeTime
		);

		double dblNormalizedPermanentImpact = pina.evaluate (
			dblTradeSize / (afs.averageDailyVolume() * dblTradeTime)
		);

		System.out.println();

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|  Asset                   =>  " + strAssetName);

		System.out.println ("\t|  Average Daily Volume    => " + FormatUtil.FormatDouble (dblAverageDailyVolume, 1, 0, 1.));

		System.out.println ("\t|  Shares Outstanding      => " + FormatUtil.FormatDouble (dblSharesOutstanding, 1, 0, 1.));

		System.out.println ("\t|  Daily Volatility        => " + FormatUtil.FormatDouble (dblDailyVolatility, 1, 2, 1.) + "%");

		System.out.println ("\t|  Trade Size              => " + FormatUtil.FormatDouble (dblTradeSize, 1, 0, 1.));

		System.out.println ("\t|-------------------------------------------||\n");

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println ("\t| ALMGREN, THUM, HAUPTMANN, and LI (2005) PERM. RECON  ||");

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println (
			"\t|  Inverse Turn-over              => " +
			FormatUtil.FormatDouble (afs.inverseTurnover(), 2, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblInverseTurnoverReconciler, 2, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  Normalized Trade Size          => " +
			FormatUtil.FormatDouble (afs.normalizeTradeSize (dblTradeSize, dblTradeTime), 2, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTradeSizeReconciler, 2, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  Normalized Permanent Impact    => " +
			FormatUtil.FormatDouble (2. * dblNormalizedPermanentImpact, 2, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedPermanentImpactReconciler, 2, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  De-normalized Permanent Impact => " +
			FormatUtil.FormatDouble (2. * dblDenormalizedPermanentImpact, 2, 3, 100.) + " | " +
			FormatUtil.FormatDouble (dblDenormalizedPermanentImpactReconciler, 2, 3, 1.) + " ||"
		);

		System.out.println ("\t|------------------------------------------------------||\n");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|    TEMPORARY IMPACT PARAMETERS RECONCILIATION   ||");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                  ||");

		System.out.println ("\t|                - Time                           ||");

		System.out.println ("\t|                - Normalized K (Computed)        ||");

		System.out.println ("\t|                - Normalized K (Reconciler)      ||");

		System.out.println ("\t|                - De-normalized K (Computed)     ||");

		System.out.println ("\t|                - De-normalized K (Reconciler)   ||");

		System.out.println ("\t|                - De-normalized J (Computed)     ||");

		System.out.println ("\t|                - De-normalized J (Reconciler)   ||");

		System.out.println ("\t|-------------------------------------------------||");

		for (int i = 0; i < adblTime.length; ++i)
			TemporaryImpactReconciler (
				ti,
				dblTradeSize,
				adblTime[i],
				adblNormalizedTemporaryImpactReconciler[i],
				adblDenormalizedTemporaryImpactReconciler[i],
				dblDenormalizedPermanentImpact,
				adblRealizedImpactReconciler[i]
			);

		System.out.println ("\t|-------------------------------------------------||\n");
	}
}
