
package org.drip.sample.idzorek;

import org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation;
import org.drip.portfolioconstruction.asset.*;
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
 * <i>ExpectedExcessReturnsWeights</i> reconciles the Expected Returns and the corresponding Weights for
 * 	different Input Asset Distributions using the Black-Litterman Model Process. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios,
 *  			Goldman Sachs Asset Management
 *  	</li>
 *  	<li>
 *  		Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User
 *  			Specified Confidence Levels, Ibbotson Associates, Chicago
 *  	</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/idzorek/README.md">Idzorek (2005) User Confidence Tilt</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExpectedExcessReturnsWeights
{

	private static final void ForwardOptimizationWeights (
		final ForwardReverseHoldingsAllocation forwardReverseHoldingsAllocation,
		final double[] weightReconcilerArray,
		final int preDecimalDigits,
		final int postDecimalDigits,
		final String header)
	{
		Portfolio optimalPortfolio = forwardReverseHoldingsAllocation.optimalPortfolio();

		AssetComponent highestWeightAsset = optimalPortfolio.highestWeightAsset();

		AssetComponent lowestWeightAsset = optimalPortfolio.lowestWeightAsset();

		String[] assetIDArray = optimalPortfolio.assetIDArray();

		double[] weightArray = optimalPortfolio.weightArray();

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println (header);

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println ("\t|                  ID               =>  CALC  | VERIFY ||");

		System.out.println ("\t|------------------------------------------------------||");

		for (int assetIndex = 0; assetIndex < weightArray.length; ++assetIndex) {
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] => " + FormatUtil.FormatDouble (
					weightArray[assetIndex],
					preDecimalDigits,
					postDecimalDigits,
					100.
				) + "% | " + FormatUtil.FormatDouble (
					weightReconcilerArray[assetIndex],
					preDecimalDigits,
					postDecimalDigits,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println (
			"\t| HIGH : " + highestWeightAsset.id() + " => " + FormatUtil.FormatDouble (
				highestWeightAsset.amount(),
				preDecimalDigits,
				postDecimalDigits,
				100.
			) + "%     ||"
		);

		System.out.println (
			"\t| LOW  : " + lowestWeightAsset.id() + " => "+ FormatUtil.FormatDouble (
				lowestWeightAsset.amount(),
				preDecimalDigits,
				postDecimalDigits,
				100.
			) + "%     ||"
		);

		System.out.println ("\t|------------------------------------------------------||\n");
	}

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
		EnvManager.InitEnv ("");

		double riskAversion = 3.07;
		String[] assetIDArray = new String[] {
			"US BONDS                       ",
			"INTERNATIONAL BONDS            ",
			"US LARGE GROWTH                ",
			"US LARGE VALUE                 ",
			"US SMALL GROWTH                ",
			"US SMALL VALUE                 ",
			"INTERNATIONAL DEVELOPED EQUITY ",
			"INTERNATIONAL EMERGING EQUITY  "
		};
		double[] assetEquilibriumWeightArray = new double[] {
			0.1934,
			0.2613,
			0.1209,
			0.1209,
			0.0134,
			0.0134,
			0.2418,
			0.0349
		};
		double[][] assetExcessReturnsCovarianceMatrix = new double[][] {
			{ 0.001005,  0.001328, -0.000579, -0.000675,  0.000121,  0.000128, -0.000445, -0.000437},
			{ 0.001328,  0.007277, -0.001307, -0.000610, -0.002237, -0.000989,  0.001442, -0.001535},
			{-0.000579, -0.001307,  0.059582,  0.027588,  0.063497,  0.023036,  0.032967,  0.048039},
			{-0.000675, -0.000610,  0.027588,  0.029609,  0.026572,  0.021465,  0.020697,  0.029854},
			{ 0.000121, -0.002237,  0.063497,  0.026572,  0.102488,  0.042744,  0.039943,  0.065994},
			{ 0.000128, -0.000989,  0.023036,  0.021465,  0.042744,  0.032056,  0.019881,  0.032235},
			{-0.000445,  0.001442,  0.032967,  0.020697,  0.039943,  0.019881,  0.028355,  0.035064},
			{-0.000437, -0.001535,  0.048039,  0.029854,  0.065994,  0.032235,  0.035064,  0.079958}
		};
		double[] assetSpaceHistoricalReturnsArray = new double[] {
			 0.0315,
			 0.0175,
			-0.0639,
			-0.0286,
			-0.0675,
			-0.0054,
			-0.0675,
			-0.0526
		};
		double[] assetSpaceCAPMReturnsArray = new double[] {
			0.0008,
			0.0067,
			0.0641,
			0.0408,
			0.0743,
			0.0370,
			0.0480,
			0.0660
		};
		double[] assetSpaceGSMIReturnsArray = new double[] {
			 0.0002,
			 0.0018,
			 0.0557,
			 0.0339,
			 0.0659,
			 0.0316,
			 0.0392,
			 0.0560
		};
		double[] historicalPortfolioWeightReconcilerArray = new double[] {
			 11.4432,
			 -1.0459,
			  0.5459,
			 -0.0529,
			 -0.6052,
			  0.8147,
			 -1.0436,
			  0.1459
		};
		double[] capmGSMIPortfolioWeightReconcilerArray = new double[] {
			  0.2133,
			  0.0519,
			  0.1080,
			  0.1082,
			  0.0373,
			 -0.0049,
			  0.1710,
			  0.0214
		};

		double[] impliedEquilibriumExcessReturnsArray = ForwardReverseHoldingsAllocation.Reverse (
			Portfolio.Standard (assetIDArray, assetEquilibriumWeightArray),
			assetExcessReturnsCovarianceMatrix,
			riskAversion
		).expectedAssetExcessReturnsArray();

		System.out.println ("\n\t|---------------------------------------------------------------------||");

		System.out.println ("\t|               STARTING RETURNS SOURCES RECONCILIATION               ||");

		System.out.println ("\t|---------------------------------------------------------------------||");

		System.out.println ("\t|                ID                 =>  HIST  | GSMI  | CAPM  | IMPL  ||");

		System.out.println ("\t|---------------------------------------------------------------------||");

		for (int assetIndex = 0; assetIndex < impliedEquilibriumExcessReturnsArray.length; ++assetIndex) {
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] => " + FormatUtil.FormatDouble (
					assetSpaceHistoricalReturnsArray[assetIndex],
					1,
					2,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					assetSpaceGSMIReturnsArray[assetIndex],
					1,
					2,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					assetSpaceCAPMReturnsArray[assetIndex],
					1,
					2,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					riskAversion * impliedEquilibriumExcessReturnsArray[assetIndex],
					1,
					2,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------------------------------------------||\n");

		ForwardOptimizationWeights (
			ForwardReverseHoldingsAllocation.Forward (
				assetIDArray,
				assetSpaceHistoricalReturnsArray,
				assetExcessReturnsCovarianceMatrix,
				riskAversion
			),
			historicalPortfolioWeightReconcilerArray,
			4,
			0,
			"\t|             HISTORICAL WEIGHTS RECONCILER            ||"
		);

		ForwardOptimizationWeights (
			ForwardReverseHoldingsAllocation.Forward (
				assetIDArray,
				assetSpaceGSMIReturnsArray,
				assetExcessReturnsCovarianceMatrix,
				riskAversion
			),
			capmGSMIPortfolioWeightReconcilerArray,
			2,
			1,
			"\t|              CAPM GSMI WEIGHTS RECONCILER            ||"
		);

		ForwardOptimizationWeights (
			ForwardReverseHoldingsAllocation.Forward (
				assetIDArray,
				assetSpaceCAPMReturnsArray,
				assetExcessReturnsCovarianceMatrix,
				riskAversion
			),
			assetEquilibriumWeightArray,
			2,
			1,
			"\t|             EQUILIBRIUM WEIGHTS RECONCILER           ||"
		);

		EnvManager.TerminateEnv();
	}
}
