
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.R1MultivariateConvolutionMetrics;
import org.drip.measure.gaussian.*;
import org.drip.measure.state.LabelledRd;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.bayesian.*;
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
 * <i>Soontornkit2010</i> reconciles the Outputs of the Black-Litterman Model Process. The References are:
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
 *  	<li>
 *  		Soontornkit, S. (2010): The Black-Litterman Approach to Asset Allocation
 *   			http://www.bus.tu.ac.th/uploadPR/%E0%B9%80%E0%B8%AD%E0%B8%81%E0%B8%AA%E0%B8%B2%E0%B8%A3%209%20%E0%B8%A1%E0%B8%B4.%E0%B8%A2.%2053/Black-Litterman_Supakorn.pdf
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

public class Soontornkit2010
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

		double tau = 0.3;
		double riskFreeRate = 0.03;
		double historicalLongTermVariance = 0.0152;
		double historicalBenchmarkReturnArray = 0.049;

		String[] sector1Array = new String[] {
			"AGRO & FOOD INDUSTRY    ",
			"CONSUMER PRODUCTS       ",
			"FINANCIALS              ",
			"INDUSTRIALS             ",
			"PROPERTY & CONSTRUCTION ",
			"RESOURCES               ",
			"SERVICES                ",
			"TECHNOLOGY              "
		};

		String[] sector2Array = new String[] {
			"ZRR3Y                   ",
			"AGRO & FOOD INDUSTRY    ",
			"CONSUMER PRODUCTS       ",
			"FINANCIALS              ",
			"INDUSTRIALS             ",
			"PROPERTY & CONSTRUCTION ",
			"RESOURCES               ",
			"SERVICES                ",
			"TECHNOLOGY              "
		};

		double[] marketCapitalizationEstimateArray = new double[] {
			1118732.,
			 143798.,
			3136108.,
			1727804.,
			2096000.,
			4497231.,
			 816320.,
			1808058.
		};

		double[][] excessAssetReturnsCovarianceMatrix = new double[][] {
			{ 0.0013, -0.0010, -0.0005, -0.0009, -0.0019, -0.0004, -0.0014, -0.0008, -0.0006},
			{-0.0010,  0.0391,  0.0158,  0.0398,  0.0496,  0.0462,  0.0454,  0.0370,  0.0265},
			{-0.0005,  0.0158,  0.0118,  0.0150,  0.0203,  0.0204,  0.0191,  0.0161,  0.0111},
			{-0.0009,  0.0398,  0.0150,  0.0683,  0.0696,  0.0667,  0.0623,  0.0489,  0.0403},
			{-0.0019,  0.0496,  0.0203,  0.0696,  0.1029,  0.0809,  0.0829,  0.0629,  0.0471},
			{-0.0004,  0.0462,  0.0204,  0.0667,  0.0809,  0.0791,  0.0699,  0.0566,  0.0453},
			{-0.0014,  0.0454,  0.0191,  0.0623,  0.0829,  0.0699,  0.0943,  0.0557,  0.0481},
			{-0.0008,  0.0370,  0.0161,  0.0489,  0.0629,  0.0566,  0.0557,  0.0500,  0.0384},
			{-0.0006,  0.0265,  0.0111,  0.0403,  0.0471,  0.0453,  0.0481,  0.0384,  0.0473}
		};

		double[][] assetSpaceViewProjectionMatrix = new double[][] {
			{  1.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00, -1.00,  0.00,  0.00,  0.00,  1.00,  0.00,  0.00},
			{  0.00,  0.00,  0.00, -0.41,  0.49,  0.00, -0.59,  0.00,  0.51}
		};

		double[] expectedExcessProjectionReturnsArray = new double[] {
			0.000,
			0.020,
			0.001
		};

		double[][] excessProjectionReturnsCovarianceMatrix = new double[][] {
			{0.0013, 0.0000, 0.0000},
			{0.0000, 0.0679, 0.0000},
			{0.0000, 0.0000, 0.0132}
		};

		double[] marketCapitalizationWeight1ReconcilerArray = new double[] {
			0.07,
			0.01,
			0.20,
			0.11,
			0.14,
			0.29,
			0.05,
			0.12
		};

		double[] marketCapitalizationWeight2ReconcilerArray = new double[] {
			0.500,
			0.036,
			0.005,
			0.102,
			0.056,
			0.068,
			0.147,
			0.027,
			0.059
		};

		double[] adblExpectedExcessReturnReconciler = new double[] {
			0.0001,
			0.0221,
			0.0096,
			0.0312,
			0.0397,
			0.0361,
			0.0350,
			0.0280,
			0.0244
		};

		double[] assetSpaceJointReturnsReconcilerArray = new double[] {
			0.0336,
			0.0333,
			0.0315,
			0.0614,
			0.0562,
			0.0568,
			0.0577,
			0.0608
		};

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			LabelledRd.FromArray (
				new String[] {
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3"
				}
			),
			expectedExcessProjectionReturnsArray,
			excessProjectionReturnsCovarianceMatrix
		);

		double riskAversion = RiskUtilitySettingsEstimator.EquilibriumRiskAversion (
			historicalBenchmarkReturnArray,
			riskFreeRate,
			historicalLongTermVariance
		);

		double[] marketCapitalizationWeight2Array = new double[marketCapitalizationEstimateArray.length + 1];
		double[] marketCapitalizationWeight1Array = new double[marketCapitalizationEstimateArray.length];
		marketCapitalizationWeight2Array[0] = 0.50;
		double totalMarketCapitalization = 0.;

		for (int marketCapitalizationIndex = 0;
			marketCapitalizationIndex < marketCapitalizationEstimateArray.length;
			++marketCapitalizationIndex)
		{
			totalMarketCapitalization += marketCapitalizationEstimateArray[marketCapitalizationIndex];
		}

		for (int marketCapitalizationIndex = 0;
			marketCapitalizationIndex < marketCapitalizationEstimateArray.length;
			++marketCapitalizationIndex)
		{
			marketCapitalizationWeight2Array[marketCapitalizationIndex + 1] = 0.5 * (
				marketCapitalizationWeight1Array[marketCapitalizationIndex] =
					marketCapitalizationEstimateArray[marketCapitalizationIndex] / totalMarketCapitalization
			);
		}

		double[] expectedExcessReturnsArray = R1MatrixUtil.Product (
			excessAssetReturnsCovarianceMatrix,
			marketCapitalizationWeight2Array
		);

		for (int expectedExcessReturnsIndex = 0;
			expectedExcessReturnsIndex < expectedExcessReturnsArray.length;
			++expectedExcessReturnsIndex)
		{
			expectedExcessReturnsArray[expectedExcessReturnsIndex] *= riskAversion;
		}

		R1MultivariateConvolutionMetrics convolutionMetrics = new BlackLittermanCombinationEngine (
			ForwardReverseHoldingsAllocation.Reverse (
				Portfolio.Standard (sector2Array, marketCapitalizationWeight2Array),
				excessAssetReturnsCovarianceMatrix,
				riskAversion
			),
			new PriorControlSpecification (false, riskFreeRate, tau),
			new ProjectionSpecification (viewDistribution, assetSpaceViewProjectionMatrix)
		).customConfidenceRun().jointPosteriorMetrics();

		R1MultivariateNormal jointDistribution =
			(R1MultivariateNormal) convolutionMetrics.jointDistribution();

		R1MultivariateNormal posteriorDistribution =
			(R1MultivariateNormal) convolutionMetrics.posteriorDistribution();

		double[] assetSpaceJointReturnsArray = jointDistribution.mean();

		double[][] assetSpaceJointCovarianceMatrix = jointDistribution.covariance().covarianceMatrix();

		double[][] assetSpacePosteriorCovarianceMatrix =
			posteriorDistribution.covariance().covarianceMatrix();

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| TAU   => " + FormatUtil.FormatDouble (tau, 1, 8, 1.) + "   ||");

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (riskAversion, 1, 8, 1.) + "   ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t||-------------------------------------------||");

		System.out.println ("\t||    MARKET CAPITALIZATION RECONCILER #1    ||");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||           SECTOR         =>  WT. |  PAPER ||");

		System.out.println ("\t||-------------------------------------------||");

		for (int marketCapitalizationIndex = 0;
			marketCapitalizationIndex < marketCapitalizationEstimateArray.length;
			++marketCapitalizationIndex)
		{
			System.out.println (
				"\t|| " + sector1Array[marketCapitalizationIndex] + " => " + FormatUtil.FormatDouble (
					marketCapitalizationWeight1Array[marketCapitalizationIndex],
					2,
					0,
					100.
				) + "% |  " + FormatUtil.FormatDouble (
					marketCapitalizationWeight1ReconcilerArray[marketCapitalizationIndex],
					2,
					0,
					100.
				) + "%  ||"
			);
		}

		System.out.println ("\t||-------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||      MARKET CAPITALIZATION RECONCILER #2     ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||           SECTOR         => WEIGHT |  PAPER  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int marketCapitalizationIndex = 0;
			marketCapitalizationIndex <= marketCapitalizationEstimateArray.length;
			++marketCapitalizationIndex)
		{
			System.out.println (
				"\t|| " + sector2Array[marketCapitalizationIndex] + " => " + FormatUtil.FormatDouble (
					marketCapitalizationWeight2Array[marketCapitalizationIndex],
					2,
					1,
					100.
				) + "% | " + FormatUtil.FormatDouble (
					marketCapitalizationWeight2ReconcilerArray[marketCapitalizationIndex],
					2,
					1,
					100.
				) + "%  ||"
			);
		}

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||             IMPLIED EXCESS RETURN            ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||           SECTOR         => RETURN |  PAPER  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int expectedExcessReturnsIndex = 0;
			expectedExcessReturnsIndex < expectedExcessReturnsArray.length;
			++expectedExcessReturnsIndex)
		{
			System.out.println (
				"\t|| " + sector2Array[expectedExcessReturnsIndex] + " => " + FormatUtil.FormatDouble (
					expectedExcessReturnsArray[expectedExcessReturnsIndex],
					1,
					2,
					100.
				) + "% | " + FormatUtil.FormatDouble (
					adblExpectedExcessReturnReconciler[expectedExcessReturnsIndex],
					1,
					2,
					100.
				) + "%  ||"
			);
		}

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||             IMPLIED MARKET RETURN            ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||           SECTOR         => RETURN |  PAPER  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int expectedExcessReturnsIndex = 0;
			expectedExcessReturnsIndex < expectedExcessReturnsArray.length;
			++expectedExcessReturnsIndex)
		{
			System.out.println (
				"\t|| " + sector2Array[expectedExcessReturnsIndex] + " => " + FormatUtil.FormatDouble (
					expectedExcessReturnsArray[expectedExcessReturnsIndex] + riskFreeRate,
					1,
					2,
					100.
				) + "% | " + FormatUtil.FormatDouble (
					adblExpectedExcessReturnReconciler[expectedExcessReturnsIndex] + riskFreeRate,
					1,
					2,
					100.
				) + "%  ||"
			);
		}

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println (
			"\n\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                           PRIOR CROSS ASSET COVARIANCE MATRIX                                  ||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		String header = "\t|     |";

		for (int sector2Index = 0; sector2Index < sector2Array.length; ++sector2Index) {
			header += "    " + sector2Array[sector2Index] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int sector2IndexI = 0; sector2IndexI < sector2Array.length; ++sector2IndexI) {
			String dump = "\t| " + sector2Array[sector2IndexI] + " ";

			for (int sector2IndexJ = 0; sector2IndexJ < sector2Array.length; ++sector2IndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					excessAssetReturnsCovarianceMatrix[sector2IndexI][sector2IndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\n\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                          VIEW SCOPING ASSET PROJECTION LOADING                                 ||"
		);

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		header = "\t|     |";

		for (int sector2Index = 0; sector2Index < sector2Array.length; ++sector2Index) {
			header += "    " + sector2Array[sector2Index] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetSpaceViewProjectionIndex = 0;
			assetSpaceViewProjectionIndex < assetSpaceViewProjectionMatrix.length;
			++assetSpaceViewProjectionIndex)
		{
			String dump = "\t|  #" + assetSpaceViewProjectionIndex + " ";

			for (int sector2Index = 0; sector2Index < sector2Array.length; ++sector2Index) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpaceViewProjectionMatrix[assetSpaceViewProjectionIndex][sector2Index],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\n\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int assetSpaceViewProjectionIndexI = 0;
			assetSpaceViewProjectionIndexI < assetSpaceViewProjectionMatrix.length;
			++assetSpaceViewProjectionIndexI)
		{
			String strDump = "\t|  #" + assetSpaceViewProjectionIndexI + " ";

			for (int assetSpaceViewProjectionIndexJ = 0;
				assetSpaceViewProjectionIndexJ < assetSpaceViewProjectionMatrix.length;
				++assetSpaceViewProjectionIndexJ)
			{
				strDump += "|" + FormatUtil.FormatDouble (
					excessProjectionReturnsCovarianceMatrix[assetSpaceViewProjectionIndexI][assetSpaceViewProjectionIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (
				strDump + "|" + FormatUtil.FormatDouble (
					expectedExcessProjectionReturnsArray[assetSpaceViewProjectionIndexI],
					1,
					2,
					100.
				) + "%"
			);
		}

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\n\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                           JOINT CROSS ASSET COVARIANCE MATRIX                                  ||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		header = "\t|     |";

		for (int sector2Index = 0; sector2Index < sector2Array.length; ++sector2Index) {
			header += "    " + sector2Array[sector2Index] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int sector2IndexI = 0; sector2IndexI < sector2Array.length; ++sector2IndexI) {
			String dump = "\t| " + sector2Array[sector2IndexI] + " ";

			for (int sector2IndexJ = 0; sector2IndexJ < sector2Array.length; ++sector2IndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpaceJointCovarianceMatrix[sector2IndexI][sector2IndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\n\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                         POSTERIOR CROSS ASSET COVARIANCE MATRIX                                ||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		header = "\t|     |";

		for (int sector2Index = 0; sector2Index < sector2Array.length; ++sector2Index) {
			header += "    " + sector2Array[sector2Index] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int sector2IndexI = 0; sector2IndexI < sector2Array.length; ++sector2IndexI) {
			String dump = "\t| " + sector2Array[sector2IndexI] + " ";

			for (int sector2IndexJ = 0; sector2IndexJ < sector2Array.length; ++sector2IndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpacePosteriorCovarianceMatrix[sector2IndexI][sector2IndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||\n"
		);

		System.out.println ("\t|------------------------||");

		System.out.println ("\t| JOINT/POSTERIOR RETURN ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int assetSpaceJointReturnsReconcilerIndex = 0;
			assetSpaceJointReturnsReconcilerIndex < assetSpaceJointReturnsReconcilerArray.length;
			++assetSpaceJointReturnsReconcilerIndex)
		{
			System.out.println (
				"\t| [" + sector2Array[assetSpaceJointReturnsReconcilerIndex] + "] =>" +
				FormatUtil.FormatDouble (
					assetSpaceJointReturnsArray[assetSpaceJointReturnsReconcilerIndex],
					2,
					2,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					assetSpaceJointReturnsReconcilerArray[assetSpaceJointReturnsReconcilerIndex],
					2,
					2,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
