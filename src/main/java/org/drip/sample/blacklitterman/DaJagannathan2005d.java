
package org.drip.sample.blacklitterman;

import org.drip.measure.statistics.MultivariateMoments;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.linearalgebra.Matrix;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.AssetComponent;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>DaJagannathan2005d</i> reconciles the Outputs of the Black-Litterman Model Process. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Da, Z., and R. Jagannathan (2005): https://www3.nd.edu/~zda/TeachingNote_Black-Litterman.pdf
 *  	</li>
 *  	<li>
 *  		He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model Portfolios</i>
 *  			<b>Goldman Sachs Asset Management</b>
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/blacklitterman/README.md">Canonical Black Litterman and Extensions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DaJagannathan2005d {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String[] astrID = new String[] {
			"CORPORATE BOND     ",
			"LONG TERM GOVVIE   ",
			"MEDIUM TERM GOVVIE ",
			"STRONG BUY EQUITY  ",
			"BUY EQUITY         ",
			"NEUTRAL EQUITY     ",
			"SELL EQUITY        ",
			"STRONG SELL EQUITY "
		};

		double[][] aadblHistoricalCovariance = new double[][] {
			{0.0050, 0.0047, 0.0024, 0.0036, 0.0023, 0.0031, 0.0032, 0.0030},
			{0.0047, 0.0062, 0.0030, 0.0033, 0.0016, 0.0024, 0.0026, 0.0020},
			{0.0024, 0.0030, 0.0020, 0.0015, 0.0006, 0.0009, 0.0012, 0.0008},
			{0.0036, 0.0033, 0.0015, 0.0468, 0.0354, 0.0371, 0.0379, 0.0414},
			{0.0023, 0.0016, 0.0006, 0.0354, 0.0354, 0.0323, 0.0317, 0.0371},
			{0.0031, 0.0024, 0.0009, 0.0371, 0.0323, 0.0349, 0.0342, 0.0364},
			{0.0032, 0.0026, 0.0012, 0.0379, 0.0317, 0.0342, 0.0432, 0.0384},
			{0.0030, 0.0020, 0.0008, 0.0414, 0.0371, 0.0364, 0.0384, 0.0498}
		};

		double[] adblHistoricalReturn = new double[] {
			0.0595,
			0.0553,
			0.0545,
			0.1302,
			0.1114,
			0.1116,
			0.1217,
			0.1220
		};

		double[] adblHistoricalOptimalWeight = new double[] {
			 0.2154,
			-0.5434,
			 1.1976,
			 0.0624,
			 0.0808,
			-0.0450,
			 0.0472,
			-0.0149
		};

		double[] adblMarketWeight = new double[] {
			0.1667,
			0.0833,
			0.0833,
			0.2206,
			0.1184,
			0.1065,
			0.0591,
			0.1622
		};

		double[] adblMarketImpliedReturnReconciler = new double[] {
			0.0335,
			0.0332,
			0.0315,
			0.0584,
			0.0539,
			0.0544,
			0.0554,
			0.0585
		};

		double dblRiskFreeRate = 0.03;
		double[] adblAdjustedHistoricalReturn = new double[adblHistoricalReturn.length];

		for (int i = 0; i < adblHistoricalReturn.length; ++i)
			adblAdjustedHistoricalReturn[i] = adblHistoricalReturn[i] - dblRiskFreeRate;

		OptimizationOutput op = new QuadraticMeanVarianceOptimizer().allocate (
			new PortfolioConstructionParameters (
				astrID,
				CustomRiskUtilitySettings.RiskTolerant (0.078),
				EqualityConstraintSettings.FullyInvested()
			),
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					astrID,
					adblAdjustedHistoricalReturn,
					aadblHistoricalCovariance
				)
			)
		);

		AssetComponent[] aAC = op.optimalPortfolio().assetComponentArray();

		double[] adblMarketImpliedReturn = Matrix.Product (
			aadblHistoricalCovariance,
			adblMarketWeight
		);

		System.out.println ("\n\t|---------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                HISTORICAL COVARIANCE MATRIX                                 ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrID.length; ++i) {
			String strDump = "\t| " + astrID[i] + " ";

			for (int j = 0; j < astrID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblHistoricalCovariance[i][j], 1, 4, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||         MARKET WEIGHT           ||");

		System.out.println ("\t||---------------------------------||");

		for (int i = 0; i < adblMarketWeight.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblMarketWeight[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t||---------------------------------||\n");

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||       HISTORICAL RETURNS        ||");

		System.out.println ("\t||---------------------------------||");

		for (int i = 0; i < adblHistoricalReturn.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblHistoricalReturn[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\n\t||---------------------------------------------||");

		System.out.println ("\t||      HISTORICAL PARAM OPTIMAL WEIGHTS       ||");

		System.out.println ("\t||---------------------------------------------||");

		System.out.println ("\t||         ASSET        =>   CALC   |   PAPER  ||");

		System.out.println ("\t||---------------------------------------------||");

		for (int i = 0; i < aAC.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblHistoricalOptimalWeight[i], 3, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aAC[i].amount(), 3, 2, 100.) + "% ||"
			);

		System.out.println ("\t||---------------------------------------------||");

		System.out.println ("\n\t||----------------------------------------||");

		System.out.println ("\t||         MARKET IMPLIED RETURNS         ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||         ASSET        =>  CALC  | PAPER ||");

		System.out.println ("\t||----------------------------------------||");

		for (int i = 0; i < aAC.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblMarketImpliedReturn[i] + dblRiskFreeRate, 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (adblMarketImpliedReturnReconciler[i], 1, 2, 100.) + "% ||"
			);

		System.out.println ("\t||----------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
