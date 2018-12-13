
package org.drip.sample.athl;

import org.drip.execution.athl.*;
import org.drip.execution.parameters.AssetFlowSettings;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>EquityMarketImpactIBM</i> demonstrates the Reconciliation of the Equity Market Impact with that
 * determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 * (2003) for IBM. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/athl/README.md">Almgren, Thum, Hauptmann, and Li (2005) Calibration</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EquityMarketImpactIBM {

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
		EnvManager.InitEnv (
			"",
			true
		);

		String strAssetName = "IBM";
		double dblAverageDailyVolume = 6561000.;
		double dblSharesOutstanding = 1728000000.;
		double dblDailyVolatility = 1.57;
		double dblTradeSize = 656100.;
		double dblTradeTime = 1.;

		double dblInverseTurnoverReconciler = 263.374;
		double dblNormalizedTradeSizeReconciler = 0.1;
		double dblNormalizedPermanentImpactReconciler = 0.126;
		double dblDenormalizedPermanentImpactReconciler = 19.86;

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
			22,
			15,
			8
		};

		double[] adblRealizedImpactReconciler = new double[] {
			32,
			25,
			18
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

		double dblNormalizedPermanentImpact = pina.evaluate (dblTradeSize / (afs.averageDailyVolume() * dblTradeTime));

		System.out.println();

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|  Asset                   =>  " + strAssetName);

		System.out.println ("\t|  Average Daily Volume    => " + FormatUtil.FormatDouble (dblAverageDailyVolume, 1, 0, 1.));

		System.out.println ("\t|  Shares Outstanding      => " + FormatUtil.FormatDouble (dblSharesOutstanding, 1, 0, 1.));

		System.out.println ("\t|  Daily Volatility        => " + FormatUtil.FormatDouble (dblDailyVolatility, 1, 2, 1.) + "%");

		System.out.println ("\t|  Trade Size              => " + FormatUtil.FormatDouble (dblTradeSize, 1, 0, 1.));

		System.out.println ("\t|-------------------------------------------||\n");

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println ("\t|  ALMGREN, THUM, HAUPTMANN, and LI (2005) PERM. RECON   ||");

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println (
			"\t|  Inverse Turn-over              => " +
			FormatUtil.FormatDouble (afs.inverseTurnover(), 3, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblInverseTurnoverReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  Normalized Trade Size          => " +
			FormatUtil.FormatDouble (afs.normalizeTradeSize (dblTradeSize, dblTradeTime), 3, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTradeSizeReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  Normalized Permanent Impact    => " +
			FormatUtil.FormatDouble (2. * dblNormalizedPermanentImpact, 3, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedPermanentImpactReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  De-normalized Permanent Impact => " +
			FormatUtil.FormatDouble (2. * dblDenormalizedPermanentImpact, 3, 3, 100.) + " | " +
			FormatUtil.FormatDouble (dblDenormalizedPermanentImpactReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println ("\t|--------------------------------------------------------||\n");

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
