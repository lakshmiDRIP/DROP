
package org.drip.sample.athl;

import org.drip.execution.athl.*;
import org.drip.execution.parameters.AssetFlowSettings;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>EquityMarketImpactDRI</i> demonstrates the Reconciliation of the Equity Market Impact with that
 * determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 * (2003) for DRI. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 *  	</li>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 *  	</li>
 *  	<li>
 * 			Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 *  	</li>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 *  	</li>
 *  	<li>
 * 			Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18
 * 				(7)</b> 57-62
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/athl/README.md">Almgren, Thum, Hauptmann, and Li (2005) Calibration</a></li>
 *  </ul>
 * <br><br>
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
			dblTradeSize / (ti.assetFlowSettings().averageDailyVolume() * dblTime)
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

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

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

		EnvManager.TerminateEnv();
	}
}
