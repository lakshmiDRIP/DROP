
package org.drip.sample.idzorek;

import org.drip.measure.bayesian.ProjectionDistributionLoading;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.allocator.ForwardReverseHoldingsAllocation;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.bayesian.*;
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
 * <i>ProjectionImpliedConfidenceLevel</i> reconciles the Implied Confidence Black-Litterman Model Process
 * 	Levels generated using the Idzorek Model. The References are:
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

public class ProjectionImpliedConfidenceLevel
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double tau = 0.025;
		double riskAversion = 3.07;
		double riskFreeRate = 0.00;
		double[] customConfidenceWeightReconcilerArray = new double[]
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
		double[] fullConfidenceWeightReconcilerArray = new double[]
		{
			0.4382,
			0.0165,
			0.0381,
			0.2037,
			0.0042,
			0.0226,
			0.3521,
			0.0349
		};
		double[] customWeightDeviationReconcilerArray = new double[]
		{
			 0.1054,
			-0.1054,
			-0.0273,
			 0.0273,
			-0.0030,
			 0.0030,
			 0.0363
		};
		double[] fullWeightDeviationReconcilerArray = new double[]
		{
			 0.2448,
			-0.2448,
			-0.0828,
			 0.0828,
			-0.0092,
			 0.0092,
			 0.1103
		};
		double[] impliedConfidenceLevelReconcilerArray = new double[]
		{
			 0.4306,
			 0.4306,
			 0.3302,
			 0.3302,
			 0.3302,
			 0.3302,
			 0.3294
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
		double[][] aAssetSpaceViewProjectionMatrix = new double[][]
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

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			new MultivariateMeta (
				new String[] {
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3"
				}
			),
			projectionExpectedExcessReturnsArray,
			ProjectionDistributionLoading.ProjectionCovariance (
				assetExcessReturnsCovarianceMatrix,
				aAssetSpaceViewProjectionMatrix,
				tau
			)
		);

		ProjectionImpliedConfidenceOutput projectionImpliedConfidenceOutput =
			new BlackLittermanCombinationEngine (
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
					aAssetSpaceViewProjectionMatrix
				)
			).impliedConfidenceRun();

		double[] customConfidenceReturnsArray =
			projectionImpliedConfidenceOutput.customConfidenceOutput().adjustedOptimizationOutput().expectedAssetExcessReturnsArray();

		double[] fullConfidenceReturnsArray =
			projectionImpliedConfidenceOutput.fullConfidenceOutput().adjustedOptimizationOutput().expectedAssetExcessReturnsArray();

		double[] fullConfidenceWeightArray =
			projectionImpliedConfidenceOutput.fullProjectionConfidenceWeight();

		double[] customConfidenceWeightArray =
			projectionImpliedConfidenceOutput.customProjectionConfidenceWeight();

		double[] fullWeightDeviationArray =
			projectionImpliedConfidenceOutput.fullProjectionConfidenceDeviation();

		double[] customWeightDeviationArray =
			projectionImpliedConfidenceOutput.customProjectionConfidenceDeviation();

		double[] impliedConfidenceLevelArray =
			projectionImpliedConfidenceOutput.impliedConfidenceLevelArray();

		System.out.println ("\n\n\t|----------------------------------------------------||");

		System.out.println ("\t|    CUSTOM vs. FULL CONFIDENCE RETURNS COMPARISON   ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>  FULL  | CUSTOM ||");

		System.out.println ("\t|----------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < fullConfidenceWeightArray.length;
			++assetIndex)
		{
			System.out.println ("\t| " +
				assetIDArray[assetIndex] + " => " +
				FormatUtil.FormatDouble (fullConfidenceReturnsArray[assetIndex], 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (customConfidenceReturnsArray[assetIndex], 1, 2, 100.) + "% || "
			);
		}

		System.out.println ("\t|----------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t| {IDZO} EQUILIBRIUM WEIGHTS COMPARISON ACROSS CONFIDENCE LEVELS ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |   NONE  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < fullConfidenceWeightArray.length;
			++assetIndex)
		{
			System.out.println ("\t| " +
				assetIDArray[assetIndex] + " => " +
				FormatUtil.FormatDouble (fullConfidenceWeightReconcilerArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (customConfidenceWeightReconcilerArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (assetEquilibriumWeightArray[assetIndex], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t| {DROP} EQUILIBRIUM WEIGHTS COMPARISON ACROSS CONFIDENCE LEVELS ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |   NONE  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < fullConfidenceWeightArray.length;
			++assetIndex)
		{
			System.out.println ("\t| " +
				assetIDArray[assetIndex] + " => " +
				FormatUtil.FormatDouble (fullConfidenceWeightArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (customConfidenceWeightArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (assetEquilibriumWeightArray[assetIndex], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|  {IDZO} WEIGHTS DEVIATION COMPARISON ACROSS CONFIDENCE LEVELS  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |   NONE  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < fullConfidenceWeightArray.length - 1;
			++assetIndex)
		{
			System.out.println ("\t| " +
				assetIDArray[assetIndex] + " => " +
				FormatUtil.FormatDouble (fullWeightDeviationReconcilerArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (customWeightDeviationReconcilerArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (impliedConfidenceLevelReconcilerArray[assetIndex], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|  {DROP} WEIGHTS DEVIATION COMPARISON ACROSS CONFIDENCE LEVELS  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |  LEVEL  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int assetIndex = 0;
			assetIndex < fullConfidenceWeightArray.length - 1;
			++assetIndex)
		{
			System.out.println ("\t| " +
				assetIDArray[assetIndex] + " => " +
				FormatUtil.FormatDouble (fullWeightDeviationArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (customWeightDeviationArray[assetIndex], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (impliedConfidenceLevelArray[assetIndex], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
