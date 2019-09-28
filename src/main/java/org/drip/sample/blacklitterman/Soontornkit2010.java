
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.JointPosteriorMetrics;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.linearalgebra.Matrix;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.bayesian.*;
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/blacklitterman/README.md">Black Litterman</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Soontornkit2010 {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblTau = 0.3;
		double dblRiskFreeRate = 0.03;
		double adblHistoricalBenchmarkReturn = 0.049;
		double dblHistoricalLongTermVariance = 0.0152;

		String[] astrID1 = new String[] {
			"AGRO & FOOD INDUSTRY    ",
			"CONSUMER PRODUCTS       ",
			"FINANCIALS              ",
			"INDUSTRIALS             ",
			"PROPERTY & CONSTRUCTION ",
			"RESOURCES               ",
			"SERVICES                ",
			"TECHNOLOGY              "
		};

		String[] astrID2 = new String[] {
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

		double[] adblMarketCapitalizationEstimate = new double[] {
			1118732.,
			 143798.,
			3136108.,
			1727804.,
			2096000.,
			4497231.,
			 816320.,
			1808058.
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
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

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{  1.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00, -1.00,  0.00,  0.00,  0.00,  1.00,  0.00,  0.00},
			{  0.00,  0.00,  0.00, -0.41,  0.49,  0.00, -0.59,  0.00,  0.51}
		};

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.000,
			0.020,
			0.001
		};

		double[][] aadblProjectionExcessReturnsCovariance = new double[][] {
			{0.0013, 0.0000, 0.0000},
			{0.0000, 0.0679, 0.0000},
			{0.0000, 0.0000, 0.0132}
		};

		double[] adblMarketCapitalizationWeight1Reconciler = new double[] {
			0.07,
			0.01,
			0.20,
			0.11,
			0.14,
			0.29,
			0.05,
			0.12
		};

		double[] adblMarketCapitalizationWeight2Reconciler = new double[] {
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

		double[] adblAssetSpaceJointReturnsReconciler = new double[] {
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
			new MultivariateMeta (
				new String[] {
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3"
				}
			),
			adblProjectionExpectedExcessReturns,
			aadblProjectionExcessReturnsCovariance
		);

		double dblRiskAversion = RiskUtilitySettingsEstimator.EquilibriumRiskAversion (
			adblHistoricalBenchmarkReturn,
			dblRiskFreeRate,
			dblHistoricalLongTermVariance
		);

		double[] adblMarketCapitalizationWeight2 = new double[adblMarketCapitalizationEstimate.length + 1];
		double[] adblMarketCapitalizationWeight1 = new double[adblMarketCapitalizationEstimate.length];
		adblMarketCapitalizationWeight2[0] = 0.50;
		double dblTotalMarketCapitalization = 0.;

		for (int i = 0; i < adblMarketCapitalizationEstimate.length; ++i)
			dblTotalMarketCapitalization += adblMarketCapitalizationEstimate[i];

		for (int i = 0; i < adblMarketCapitalizationEstimate.length; ++i) {
			adblMarketCapitalizationWeight1[i] = adblMarketCapitalizationEstimate[i] / dblTotalMarketCapitalization;
			adblMarketCapitalizationWeight2[i + 1] = 0.5 * adblMarketCapitalizationWeight1[i];
		}

		double[] adblExpectedExcessReturn = Matrix.Product (
			aadblAssetExcessReturnsCovariance,
			adblMarketCapitalizationWeight2
		);

		for (int i = 0; i < adblExpectedExcessReturn.length; ++i)
			adblExpectedExcessReturn[i] *= dblRiskAversion;

		BlackLittermanCombinationEngine blce = new BlackLittermanCombinationEngine (
			ForwardReverseOptimizationOutput.Reverse (
				Portfolio.Standard (
					astrID2,
					adblMarketCapitalizationWeight2
				),
				aadblAssetExcessReturnsCovariance,
				dblRiskAversion
			),
			new PriorControlSpecification (
				false,
				dblRiskFreeRate,
				dblTau
			),
			new ProjectionSpecification (
				viewDistribution,
				aadblAssetSpaceViewProjection
			)
		);

		JointPosteriorMetrics jpm = blce.customConfidenceRun().jointPosteriorMetrics();

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jpm.joint();

		R1MultivariateNormal posteriorDistribution = (R1MultivariateNormal) jpm.posterior();

		double[] adblAssetSpaceJointReturns = jointDistribution.mean();

		double[][] aadblAssetSpaceJointCovariance = jointDistribution.covariance().covarianceMatrix();

		double[][] aadblAssetSpacePosteriorCovariance = posteriorDistribution.covariance().covarianceMatrix();

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| TAU   => " + FormatUtil.FormatDouble (dblTau, 1, 8, 1.) + "   ||");

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (dblRiskAversion, 1, 8, 1.) + "   ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t||-------------------------------------------||");

		System.out.println ("\t||    MARKET CAPITALIZATION RECONCILER #1    ||");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||           SECTOR         =>  WT. |  PAPER ||");

		System.out.println ("\t||-------------------------------------------||");

		for (int i = 0; i < adblMarketCapitalizationEstimate.length; ++i)
			System.out.println (
				"\t|| " + astrID1[i] + " => " +
					FormatUtil.FormatDouble (adblMarketCapitalizationWeight1[i], 2, 0, 100.) + "% |  " +
					FormatUtil.FormatDouble (adblMarketCapitalizationWeight1Reconciler[i], 2, 0, 100.) + "%  ||"
			);

		System.out.println ("\t||-------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||      MARKET CAPITALIZATION RECONCILER #2     ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||           SECTOR         => WEIGHT |  PAPER  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int i = 0; i <= adblMarketCapitalizationEstimate.length; ++i)
			System.out.println (
				"\t|| " + astrID2[i] + " => " +
					FormatUtil.FormatDouble (adblMarketCapitalizationWeight2[i], 2, 1, 100.) + "% | " +
					FormatUtil.FormatDouble (adblMarketCapitalizationWeight2Reconciler[i], 2, 1, 100.) + "%  ||"
			);

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||             IMPLIED EXCESS RETURN            ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||           SECTOR         => RETURN |  PAPER  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int i = 0; i < adblExpectedExcessReturn.length; ++i)
			System.out.println (
				"\t|| " + astrID2[i] + " => " +
					FormatUtil.FormatDouble (adblExpectedExcessReturn[i], 1, 2, 100.) + "% | " +
					FormatUtil.FormatDouble (adblExpectedExcessReturnReconciler[i], 1, 2, 100.) + "%  ||"
			);

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||             IMPLIED MARKET RETURN            ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||           SECTOR         => RETURN |  PAPER  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int i = 0; i < adblExpectedExcessReturn.length; ++i)
			System.out.println (
				"\t|| " + astrID2[i] + " => " +
					FormatUtil.FormatDouble (adblExpectedExcessReturn[i] + dblRiskFreeRate, 1, 2, 100.) + "% | " +
					FormatUtil.FormatDouble (adblExpectedExcessReturnReconciler[i] + dblRiskFreeRate, 1, 2, 100.) + "%  ||"
			);

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           PRIOR CROSS ASSET COVARIANCE MATRIX                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String strHeader = "\t|     |";

		for (int i = 0; i < astrID2.length; ++i)
			strHeader += "    " + astrID2[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrID2.length; ++i) {
			String strDump = "\t| " + astrID2[i] + " ";

			for (int j = 0; j < astrID2.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetExcessReturnsCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                          VIEW SCOPING ASSET PROJECTION LOADING                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrID2.length; ++i)
			strHeader += "    " + astrID2[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < aadblAssetSpaceViewProjection.length; ++i) {
			String strDump = "\t|  #" + i + " ";

			for (int j = 0; j < astrID2.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpaceViewProjection[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < aadblAssetSpaceViewProjection.length; ++i) {
			String strDump = "\t|  #" + i + " ";

			for (int j = 0; j < aadblAssetSpaceViewProjection.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblProjectionExcessReturnsCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "|" + FormatUtil.FormatDouble (adblProjectionExpectedExcessReturns[i], 1, 2, 100.) + "%");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           JOINT CROSS ASSET COVARIANCE MATRIX                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrID2.length; ++i)
			strHeader += "    " + astrID2[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrID2.length; ++i) {
			String strDump = "\t| " + astrID2[i] + " ";

			for (int j = 0; j < astrID2.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpaceJointCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                         POSTERIOR CROSS ASSET COVARIANCE MATRIX                                ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrID2.length; ++i)
			strHeader += "    " + astrID2[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrID2.length; ++i) {
			String strDump = "\t| " + astrID2[i] + " ";

			for (int j = 0; j < astrID2.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpacePosteriorCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t| JOINT/POSTERIOR RETURN ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int i = 0; i < adblAssetSpaceJointReturnsReconciler.length; ++i) {
			System.out.println (
				"\t| [" + astrID2[i] + "] =>" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturns[i], 2, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturnsReconciler[i], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
