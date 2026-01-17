
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.R1MultivariateConvolutionMetrics;
import org.drip.measure.gaussian.*;
import org.drip.measure.state.LabelledRd;
import org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation;
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
 * <i>Yamabe2016</i> reconciles the Outputs of the Black-Litterman Model Process. The Reference is:
 *  
 * <br><br>
 *  <ul>
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

public class Yamabe2016
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

		double tau = 1.;
		double riskAversion = 2.6;
		double riskFreeRate = 0.0;

		String[] assetIDArray = new String[] {
			"ASSET A ",
			"ASSET B ",
			"ASSET C ",
			"ASSET D ",
			"ASSET E ",
			"ASSET F "
		};

		double[] assetEquilibriumWeightArray = new double[] {
			0.2535,
			0.1343,
			0.1265,
			0.1375,
			0.0733,
			0.2749
		};

		double[][] excessAssetReturnsCovarianceMatrix = new double[][] {
			{0.00273, 0.00208, 0.00159, 0.00049, 0.00117, 0.00071},
			{0.00208, 0.00277, 0.00130, 0.00046, 0.00111, 0.00056},
			{0.00159, 0.00130, 0.00146, 0.00064, 0.00105, 0.00052},
			{0.00049, 0.00046, 0.00064, 0.00061, 0.00066, 0.00037},
			{0.00117, 0.00111, 0.00105, 0.00066, 0.00139, 0.00066},
			{0.00071, 0.00056, 0.00052, 0.00037, 0.00066, 0.00070}
		};

		double[][] assetSpaceViewProjectionMatrix = new double[][] {
			{  0.00,  0.00, -1.00,  0.00,  1.00,  0.00},
			{  0.00,  1.00,  0.00,  0.00, -1.00,  0.00},
			{ -1.00,  1.00,  1.00,  0.00,  0.00, -1.00}
		};

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.0002,
			0.0003,
			0.0001
		};

		double[][] aadblProjectionExcessReturnsCovariance = new double[][] {
			{ 0.00075, -0.00053, -0.00033},
			{-0.00053,  0.00195,  0.00110},
			{-0.00033,  0.00110,  0.00217}
		};

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			LabelledRd.FromArray (
				new String[] {
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3"
				}
			),
			adblProjectionExpectedExcessReturns,
			aadblProjectionExcessReturnsCovariance
		);

		double[] assetSpacePriorReturnsReconcilerArray = new double[] {
			0.003954,
			0.003540,
			0.002782,
			0.001299,
			0.002476,
			0.001594
		};

		double[] assetSpaceJointReturnsReconcilerArray = new double[] {
			0.003755,
			0.003241,
			0.002612,
			0.001305,
			0.002559,
			0.001662
		};

		double[] expectedHistoricalReturnsArray = new double[] {
			0.003559,
			0.000469,
			0.004053,
			0.004527,
			0.000904,
			0.001581
		};

		R1MultivariateConvolutionMetrics convolutionMetrics = new BlackLittermanCombinationEngine (
			ForwardReverseHoldingsAllocation.Reverse (
				Portfolio.Standard (assetIDArray, assetEquilibriumWeightArray),
				excessAssetReturnsCovarianceMatrix,
				riskAversion
			),
			new PriorControlSpecification (false, riskFreeRate, tau),
			new ProjectionSpecification (viewDistribution, assetSpaceViewProjectionMatrix)
		).customConfidenceRun().jointPosteriorMetrics();

		R1MultivariateNormal jointDistribution =
			(R1MultivariateNormal) convolutionMetrics.jointDistribution();

		R1MultivariateNormal priorDistribution =
			(R1MultivariateNormal) convolutionMetrics.priorDistribution();

		R1MultivariateNormal posteriorDistribution =
			(R1MultivariateNormal) convolutionMetrics.posteriorDistribution();

		double[] assetSpaceJointReturnsArray = jointDistribution.mean();

		double[] assetSpacePriorReturnsArray = priorDistribution.mean();

		double[][] assetSpaceJointCovarianceMatrix = jointDistribution.covariance().covarianceMatrix();

		double[][] assetSpacePosteriorCovarianceMatrix =
			posteriorDistribution.covariance().covarianceMatrix();

		System.out.println ("\n\t|-------------------------||");

		System.out.println ("\t| TAU            =>" + FormatUtil.FormatDouble (tau, 1, 2, 1.) + "  ||");

		System.out.println (
			"\t| RISK AVERSION  =>" + FormatUtil.FormatDouble (riskAversion, 1, 2, 1.) + "  ||"
		);

		System.out.println (
			"\t| RISK FREE RATE =>" + FormatUtil.FormatDouble (riskFreeRate, 1, 2, 1.) + "% ||"
		);

		System.out.println ("\t|-------------------------||");

		System.out.println (
			"\n\t|----------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                       PRIOR CROSS ASSET COVARIANCE MATRIX                              ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		String header = "\t|    ID    |";

		for (int assetIndex = 0; assetIndex < assetIDArray.length; ++assetIndex) {
			header += "  " + assetIDArray[assetIndex] + "  |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		for (int assetIndexI = 0; assetIndexI < assetIDArray.length; ++assetIndexI) {
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0; assetIndexJ < assetIDArray.length; ++assetIndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					excessAssetReturnsCovarianceMatrix[assetIndexI][assetIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\n\t|-----------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                    VIEW SCOPING ASSET PROJECTION LOADING                          ||"
		);

		System.out.println (
			"\t|-----------------------------------------------------------------------------------||"
		);

		header = "\t|     |";

		for (int assetIndex = 0; assetIndex < assetIDArray.length; ++assetIndex) {
			header += "  " + assetIDArray[assetIndex] + "  |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|-----------------------------------------------------------------------------------||"
		);

		for (int assetSpaceViewProjectionIndex = 0;
			assetSpaceViewProjectionIndex < assetSpaceViewProjectionMatrix.length;
			++assetSpaceViewProjectionIndex)
		{
			String dump = "\t|  #" + assetSpaceViewProjectionIndex + " ";

			for (int assetIndex = 0; assetIndex < assetIDArray.length; ++assetIndex) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpaceViewProjectionMatrix[assetSpaceViewProjectionIndex][assetIndex],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|-----------------------------------------------------------------------------------||\n"
		);

		System.out.println ("\t|----------------------------------------------------||");

		for (int assetSpaceViewProjectionIndexI = 0;
			assetSpaceViewProjectionIndexI < assetSpaceViewProjectionMatrix.length;
			++assetSpaceViewProjectionIndexI)
		{
			String dump = "\t|  #" + assetSpaceViewProjectionIndexI + " ";

			for (int assetSpaceViewProjectionIndexJ = 0;
				assetSpaceViewProjectionIndexJ < assetSpaceViewProjectionMatrix.length;
				++assetSpaceViewProjectionIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					aadblProjectionExcessReturnsCovariance[assetSpaceViewProjectionIndexI][assetSpaceViewProjectionIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (
				dump + "|" + FormatUtil.FormatDouble (
					adblProjectionExpectedExcessReturns[assetSpaceViewProjectionIndexI],
					1,
					2,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println (
			"\n\t|----------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                       JOINT CROSS ASSET COVARIANCE MATRIX                              ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		header = "\t|    ID    |";

		for (int assetIndex = 0; assetIndex < assetIDArray.length; ++assetIndex) {
			header += "  " + assetIDArray[assetIndex] + "  |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		for (int assetIndexI = 0; assetIndexI < assetIDArray.length; ++assetIndexI) {
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0; assetIndexJ < assetIDArray.length; ++assetIndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpaceJointCovarianceMatrix[assetIndexI][assetIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||\n"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                     POSTERIOR CROSS ASSET COVARIANCE MATRIX                            ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		header = "\t|    ID    |";

		for (int assetIndex = 0; assetIndex < assetIDArray.length; ++assetIndex) {
			header += "  " + assetIDArray[assetIndex] + "  |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||"
		);

		for (int assetIndexI = 0; assetIndexI < assetIDArray.length; ++assetIndexI) {
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0; assetIndexJ < assetIDArray.length; ++assetIndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpacePosteriorCovarianceMatrix[assetIndexI][assetIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------------||\n"
		);

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|      IMPLIED/PRIOR RETURN       ||");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|     ID     =>  RIOCEE |  YAMABE ||");

		System.out.println ("\t|---------------------------------||");

		for (int assetIndex = 0; assetIndex < assetSpacePriorReturnsArray.length; ++assetIndex) {
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] =>" + FormatUtil.FormatDouble (
					assetSpacePriorReturnsArray[assetIndex],
					1,
					4,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					assetSpacePriorReturnsReconcilerArray[assetIndex],
					1,
					4,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------||\n");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|     JOINT/POSTERIOR RETURN      ||");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|     ID     =>  RIOCEE |  YAMABE ||");

		System.out.println ("\t|---------------------------------||");

		for (int assetIndex = 0; assetIndex < assetSpaceJointReturnsReconcilerArray.length; ++assetIndex) {
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] =>" + FormatUtil.FormatDouble (
					assetSpaceJointReturnsArray[assetIndex],
					1,
					4,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					assetSpaceJointReturnsReconcilerArray[assetIndex],
					1,
					4,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------||\n");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|     PRIOR/POSTERIOR/HISTORICAL RETURN     ||");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|     ID     =>  PRIOR  |   POST  |   HIST  ||");

		System.out.println ("\t|-------------------------------------------||");

		for (int assetIndex = 0; assetIndex < assetSpaceJointReturnsReconcilerArray.length; ++assetIndex) {
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] =>" +
				FormatUtil.FormatDouble (assetSpacePriorReturnsArray[assetIndex], 1, 4, 100.) + "% |" +
				FormatUtil.FormatDouble (assetSpaceJointReturnsArray[assetIndex], 1, 4, 100.) + "% |" +
				FormatUtil.FormatDouble (expectedHistoricalReturnsArray[assetIndex], 1, 4, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
