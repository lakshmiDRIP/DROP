
package org.drip.sample.blacklitterman;

import org.drip.measure.statistics.MultivariateMoments;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.AssetComponent;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/blacklitterman/README.md">Canonical Black Litterman and Extensions</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DaJagannathan2005d
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("", true);

		String[] sectorIDArray = new String[] {
			"CORPORATE BOND     ",
			"LONG TERM GOVVIE   ",
			"MEDIUM TERM GOVVIE ",
			"STRONG BUY EQUITY  ",
			"BUY EQUITY         ",
			"NEUTRAL EQUITY     ",
			"SELL EQUITY        ",
			"STRONG SELL EQUITY "
		};

		double[][] historicalCovarianceMatrix = new double[][] {
			{0.0050, 0.0047, 0.0024, 0.0036, 0.0023, 0.0031, 0.0032, 0.0030},
			{0.0047, 0.0062, 0.0030, 0.0033, 0.0016, 0.0024, 0.0026, 0.0020},
			{0.0024, 0.0030, 0.0020, 0.0015, 0.0006, 0.0009, 0.0012, 0.0008},
			{0.0036, 0.0033, 0.0015, 0.0468, 0.0354, 0.0371, 0.0379, 0.0414},
			{0.0023, 0.0016, 0.0006, 0.0354, 0.0354, 0.0323, 0.0317, 0.0371},
			{0.0031, 0.0024, 0.0009, 0.0371, 0.0323, 0.0349, 0.0342, 0.0364},
			{0.0032, 0.0026, 0.0012, 0.0379, 0.0317, 0.0342, 0.0432, 0.0384},
			{0.0030, 0.0020, 0.0008, 0.0414, 0.0371, 0.0364, 0.0384, 0.0498}
		};

		double[] historicalReturnsArray = new double[] {
			0.0595,
			0.0553,
			0.0545,
			0.1302,
			0.1114,
			0.1116,
			0.1217,
			0.1220
		};

		double[] historicalOptimalWeightArray = new double[] {
			 0.2154,
			-0.5434,
			 1.1976,
			 0.0624,
			 0.0808,
			-0.0450,
			 0.0472,
			-0.0149
		};

		double[] marketWeightArray = new double[] {
			0.1667,
			0.0833,
			0.0833,
			0.2206,
			0.1184,
			0.1065,
			0.0591,
			0.1622
		};

		double[] impliedMarketReturnsReconcilerArray = new double[] {
			0.0335,
			0.0332,
			0.0315,
			0.0584,
			0.0539,
			0.0544,
			0.0554,
			0.0585
		};

		double riskFreeRate = 0.03;
		double[] adjustedHistoricalReturnsArray = new double[historicalReturnsArray.length];

		for (int historicalReturnsIndex = 0;
			historicalReturnsIndex < historicalReturnsArray.length;
			++historicalReturnsIndex)
		{
			adjustedHistoricalReturnsArray[historicalReturnsIndex] =
				historicalReturnsArray[historicalReturnsIndex] - riskFreeRate;
		}

		AssetComponent[] assetComponentArray = new QuadraticMeanVarianceOptimizer().allocate (
			new HoldingsAllocationControl (
				sectorIDArray,
				CustomRiskUtilitySettings.RiskTolerant (0.078),
				EqualityConstraintSettings.FullyInvested()
			),
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					sectorIDArray,
					adjustedHistoricalReturnsArray,
					historicalCovarianceMatrix
				)
			)
		).optimalPortfolio().assetComponentArray();

		double[] marketImpliedReturnsArray = R1MatrixUtil.Product (
			historicalCovarianceMatrix,
			marketWeightArray
		);

		System.out.println (
			"\n\t|---------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                                HISTORICAL COVARIANCE MATRIX                                 ||"
		);

		System.out.println (
			"\t|---------------------------------------------------------------------------------------------||"
		);

		for (int sectorIndexI = 0; sectorIndexI < sectorIDArray.length; ++sectorIndexI) {
			String dump = "\t| " + sectorIDArray[sectorIndexI] + " ";

			for (int sectorIndexJ = 0; sectorIndexJ < sectorIDArray.length; ++sectorIndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					historicalCovarianceMatrix[sectorIndexI][sectorIndexJ],
					1,
					4,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|---------------------------------------------------------------------------------------------||\n"
		);

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||         MARKET WEIGHT           ||");

		System.out.println ("\t||---------------------------------||");

		for (int sectorIndex = 0; sectorIndex < marketWeightArray.length; ++sectorIndex) {
			System.out.println (
				"\t||  " + sectorIDArray[sectorIndex] + " => " +
				FormatUtil.FormatDouble (marketWeightArray[sectorIndex], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t||---------------------------------||\n");

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||       HISTORICAL RETURNS        ||");

		System.out.println ("\t||---------------------------------||");

		for (int sectorIndex = 0; sectorIndex < historicalReturnsArray.length; ++sectorIndex) {
			System.out.println (
				"\t||  " + sectorIDArray[sectorIndex] + " => " +
				FormatUtil.FormatDouble (historicalReturnsArray[sectorIndex], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\n\t||---------------------------------------------||");

		System.out.println ("\t||      HISTORICAL PARAM OPTIMAL WEIGHTS       ||");

		System.out.println ("\t||---------------------------------------------||");

		System.out.println ("\t||         ASSET        =>   CALC   |   PAPER  ||");

		System.out.println ("\t||---------------------------------------------||");

		for (int assetComponentIndex = 0;
			assetComponentIndex < assetComponentArray.length;
			++assetComponentIndex)
		{
			System.out.println (
				"\t||  " + sectorIDArray[assetComponentIndex] + " => " + FormatUtil.FormatDouble (
					historicalOptimalWeightArray[assetComponentIndex],
					3,
					2,
					100.
				) + "% | " + FormatUtil.FormatDouble (
					assetComponentArray[assetComponentIndex].amount(),
					3,
					2,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t||---------------------------------------------||");

		System.out.println ("\n\t||----------------------------------------||");

		System.out.println ("\t||         MARKET IMPLIED RETURNS         ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||         ASSET        =>  CALC  | PAPER ||");

		System.out.println ("\t||----------------------------------------||");

		for (int assetComponentIndex = 0;
			assetComponentIndex < assetComponentArray.length;
			++assetComponentIndex)
		{
			System.out.println (
				"\t||  " + sectorIDArray[assetComponentIndex] + " => " + FormatUtil.FormatDouble (
					marketImpliedReturnsArray[assetComponentIndex] + riskFreeRate,
					1,
					2,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					impliedMarketReturnsReconcilerArray[assetComponentIndex],
					1,
					2,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t||----------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
