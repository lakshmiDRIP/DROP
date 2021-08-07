
package org.drip.sample.idzorek;

import org.drip.measure.bayesian.*;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation;
import org.drip.portfolioconstruction.asset.*;
import org.drip.portfolioconstruction.bayesian.*;
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
 * <i>PriorPosteriorMetricsComparison</i> reconciles the Prior-Posterior Black-Litterman Model Process
 * 	Metrics generated using the Idzorek Model. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/idzorek/README.md">Idzorek (2005) User Confidence Tilt</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PriorPosteriorMetricsComparison
{

	private static final void ForwardOptimizationWeights (
		final ForwardReverseHoldingsAllocation forwardReverseOptimizationOutput,
		final double[] weightReconcilerArray,
		final int preDecimalDigits,
		final int postDecimalDigits,
		final String header)
	{
		Portfolio forwardReverseOptimizationPortfolio = forwardReverseOptimizationOutput.optimalPortfolio();

		AssetComponent highestWeightAsset = forwardReverseOptimizationPortfolio.highestWeightAsset();

		AssetComponent lowestWeightAsset = forwardReverseOptimizationPortfolio.lowestWeightAsset();

		String[] assetIDArray = forwardReverseOptimizationPortfolio.assetIDArray();

		double[] weightArray = forwardReverseOptimizationPortfolio.weightArray();

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println (header);

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println ("\t|                  ID               =>  CALC   | VERIFY  ||");

		System.out.println ("\t|--------------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < weightArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] => " +
				FormatUtil.FormatDouble (weightArray[assetIndex], preDecimalDigits, postDecimalDigits, 100.)
					+ "% | " +
				FormatUtil.FormatDouble (
					weightReconcilerArray[assetIndex], preDecimalDigits, postDecimalDigits, 100.
				) + "% ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println (
			"\t| HIGH  : " + highestWeightAsset.id() + " => " + FormatUtil.FormatDouble (
				highestWeightAsset.amount(), preDecimalDigits, postDecimalDigits, 100.
			) + "%     ||"
		);

		System.out.println (
			"\t| LOW   : " + lowestWeightAsset.id() + " => " + FormatUtil.FormatDouble (
				lowestWeightAsset.amount(), preDecimalDigits, postDecimalDigits, 100.
			) + "%     ||"
		);

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println (
			"\t| TOTAL :                                   " + FormatUtil.FormatDouble (
				forwardReverseOptimizationPortfolio.notional(), preDecimalDigits, postDecimalDigits, 100.
			) + "%     ||"
		);

		System.out.println ("\t|--------------------------------------------------------||\n");
	}

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double tau = 0.025;
		double riskAversion = 3.07;
		double riskFreeRate = 0.00;
		double[] assetSpaceJointReturnsReconcilerArray = new double[]
		{
			0.0007,
			0.0050,
			0.0650,
			0.0432,
			0.0759,
			0.0394,
			0.0493,
			0.0684
		};
		double[] posteriorPriorDeviationReconcilerArray = new double[]
		{
			-0.0002,
			-0.0017,
			 0.0008,
			 0.0024,
			 0.0016,
			 0.0023,
			 0.0013,
			 0.0024
		};
		double[] posteriorPortfolioWeightReconcilerArray = new double[]
		{
			  0.2988,
			  0.1559,
			  0.0935,
			  0.1482,
			  0.0104,
			  0.0165,
			  0.2781,
			  0.0349
		};
		double[] posteriorWeightDeviationReconcilerArray = new double[]
		{
			  0.1054,
			 -0.1054,
			 -0.0273,
			  0.0273,
			 -0.0030,
			  0.0030,
			  0.0363,
			  0.0000
		};
		String[] assetIDArray = new String[]
		{
			"US BONDS                       ",
			"INTERNATIONAL BONDS            ",
			"US LARGE GROWTH                ",
			"US LARGE VALUE                 ",
			"US SMALL GROWTH                ",
			"US SMALL VALUE                 ",
			"INTERNATIONAL DEVELOPED EQUITY ",
			"INTERNATIONAL EMERGING EQUITY  "
		};
		double[] assetEquilibriumWeightArray = new double[]
		{
			0.1934,
			0.2613,
			0.1209,
			0.1209,
			0.0134,
			0.0134,
			0.2418,
			0.0349
		};
		double[][] assetExcessReturnsCovarianceMatrix = new double[][]
		{
			{ 0.001005,  0.001328, -0.000579, -0.000675,  0.000121,  0.000128, -0.000445, -0.000437},
			{ 0.001328,  0.007277, -0.001307, -0.000610, -0.002237, -0.000989,  0.001442, -0.001535},
			{-0.000579, -0.001307,  0.059582,  0.027588,  0.063497,  0.023036,  0.032967,  0.048039},
			{-0.000675, -0.000610,  0.027588,  0.029609,  0.026572,  0.021465,  0.020697,  0.029854},
			{ 0.000121, -0.002237,  0.063497,  0.026572,  0.102488,  0.042744,  0.039943,  0.065994},
			{ 0.000128, -0.000989,  0.023036,  0.021465,  0.042744,  0.032056,  0.019881,  0.032235},
			{-0.000445,  0.001442,  0.032967,  0.020697,  0.039943,  0.019881,  0.028355,  0.035064},
			{-0.000437, -0.001535,  0.048039,  0.029854,  0.065994,  0.032235,  0.035064,  0.079958}
		};
		double[][] assetSpaceViewProjectionMatrix = new double[][]
		{
			{  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00,  0.00},
			{ -1.00,  1.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00,  0.90, -0.90,  0.10, -0.10,  0.00,  0.00}
		};
		double[] projectionExpectedExcessReturnsArray = new double[]
		{
			0.0525,
			0.0025,
			0.0200
		};

		double[][] projectionExcessReturnsCovarianceMatrix =
			ProjectionDistributionLoading.ProjectionCovariance (
				assetExcessReturnsCovarianceMatrix,
				assetSpaceViewProjectionMatrix,
				tau
			);

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			new MultivariateMeta (
				new String[]
				{
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3"
				}
			),
			projectionExpectedExcessReturnsArray,
			projectionExcessReturnsCovarianceMatrix
		);

		R1MultivariateConvolutionMetrics jointPosteriorMetrics = new BlackLittermanCombinationEngine (
			ForwardReverseHoldingsAllocation.Reverse (
				Portfolio.Standard (
					assetIDArray,
					assetEquilibriumWeightArray
				),
				assetExcessReturnsCovarianceMatrix,
				riskAversion
			),
			new PriorControlSpecification (
				true,
				riskFreeRate,
				tau
			),
			new ProjectionSpecification (
				viewDistribution,
				assetSpaceViewProjectionMatrix
			)
		).customConfidenceRun().jointPosteriorMetrics();

		R1MultivariateNormal priorDistribution = (R1MultivariateNormal) jointPosteriorMetrics.prior();

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jointPosteriorMetrics.joint();

		R1MultivariateNormal posteriorDistribution =
			(R1MultivariateNormal) jointPosteriorMetrics.posterior();

		double[] assetSpacePriorReturnsArray = priorDistribution.mean();

		double[] assetSpaceJointReturnsArray = jointDistribution.mean();

		double[][] aadblAssetSpaceJointCovariance = jointDistribution.covariance().covarianceMatrix();

		double[][] aadblAssetSpacePosteriorCovariance =
			posteriorDistribution.covariance().covarianceMatrix();

		ForwardReverseHoldingsAllocation posteriorForwardReverseOptimizationOutput =
			ForwardReverseHoldingsAllocation.Forward (
				assetIDArray,
				assetSpaceJointReturnsArray,
				assetExcessReturnsCovarianceMatrix,
				riskAversion
			);

		double[] posteriorWeightArray =
			posteriorForwardReverseOptimizationOutput.optimalPortfolio().weightArray();

		System.out.println ("\n\t|---------------------------||");

		System.out.println ("\t| TAU            => " + FormatUtil.FormatDouble (tau, 1, 4, 1.) + " ||");

		System.out.println (
			"\t| RISK AVERSION  => " + FormatUtil.FormatDouble (riskAversion, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t| RISK FREE RATE => " + FormatUtil.FormatDouble (riskFreeRate, 1, 4, 1.) + " ||"
		);

		System.out.println ("\t|---------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                               PRIOR CROSS ASSET COVARIANCE MATRIX                                                       ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		for (int assetIndexI = 0;
			assetIndexI < assetIDArray.length;
			++assetIndexI)
		{
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0;
				assetIndexJ < assetIDArray.length;
				++assetIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					assetExcessReturnsCovarianceMatrix[assetIndexI][assetIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|---------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                         VIEW SCOPING ASSET PROJECTION LOADING                               ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------||");

		for (int viewIndex = 0;
			viewIndex < assetSpaceViewProjectionMatrix.length;
			++viewIndex)
		{
			String dump = "\t|  #" + viewIndex + " ";

			for (int assetIndex = 0;
				assetIndex < assetIDArray.length;
				++assetIndex)
			{
				dump += "| " + FormatUtil.FormatDouble (
					assetSpaceViewProjectionMatrix[viewIndex][assetIndex], 1, 5, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|----------------------------------------------||");

		for (int viewIndexI = 0;
			viewIndexI < assetSpaceViewProjectionMatrix.length;
			++viewIndexI)
		{
			String dump = "\t|  #" + viewIndexI + " ";

			for (int viewIndexJ = 0;
				viewIndexJ < assetSpaceViewProjectionMatrix.length;
				++viewIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					projectionExcessReturnsCovarianceMatrix[viewIndexI][viewIndexJ], 1, 6, 1.
				) + " ";
			}

			System.out.println (
				dump + "|" + FormatUtil.FormatDouble (
					projectionExpectedExcessReturnsArray[viewIndexI], 1, 2, 100.
				) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                               JOINT CROSS ASSET COVARIANCE MATRIX                                                       ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		for (int assetIndexI = 0;
			assetIndexI < assetIDArray.length;
			++assetIndexI)
		{
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0;
				assetIndexJ < assetIDArray.length;
				++assetIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					aadblAssetSpaceJointCovariance[assetIndexI][assetIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                             POSTERIOR CROSS ASSET COVARIANCE MATRIX                                                     ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------||");

		for (int assetIndexI = 0;
			assetIndexI < assetIDArray.length;
			++assetIndexI)
		{
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0;
				assetIndexJ < assetIDArray.length;
				++assetIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					aadblAssetSpacePosteriorCovariance[assetIndexI][assetIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|               JOINT/POSTERIOR RETURN               ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|                 ID                => DROP  | IDZO  ||");

		System.out.println ("\t|----------------------------------------------------||");

		for (int viewIndex = 0;
			viewIndex < assetSpaceJointReturnsReconcilerArray.length;
			++viewIndex)
		{
			System.out.println (
				"\t| [" + assetIDArray[viewIndex] + "] =>" +
				FormatUtil.FormatDouble (assetSpaceJointReturnsArray[viewIndex], 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (assetSpaceJointReturnsReconcilerArray[viewIndex], 1, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|           RETURNS DEVIATION RECONCILER             ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|                 ID                => DROP  | IDZO  ||");

		System.out.println ("\t|----------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < assetSpacePriorReturnsArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] =>" +
				FormatUtil.FormatDouble (
					assetSpaceJointReturnsArray[assetIndex] - assetSpacePriorReturnsArray[assetIndex],
					1, 2, 100.
				) + "% |" +
				FormatUtil.FormatDouble (posteriorPriorDeviationReconcilerArray[assetIndex], 1, 2, 100.)
					+ "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------||\n");

		ForwardOptimizationWeights (
			posteriorForwardReverseOptimizationOutput,
			posteriorPortfolioWeightReconcilerArray,
			2,
			2,
			"\t|               POSTERIOR WEIGHTS RECONCILER             ||"
		);

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println ("\t|              WEIGHT DEVIATION RECONCILER               ||");

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println ("\t|                 ID                =>   DROP  |   IDZO  ||");

		System.out.println ("\t|--------------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < assetEquilibriumWeightArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetIndex] + "] => " +
				FormatUtil.FormatDouble (
					posteriorWeightArray[assetIndex] - assetEquilibriumWeightArray[assetIndex], 2, 2, 100.
				) + "% | " +
				FormatUtil.FormatDouble (posteriorWeightDeviationReconcilerArray[assetIndex], 2, 2, 100.)
					+ "% ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
