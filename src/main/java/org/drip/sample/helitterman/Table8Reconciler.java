
package org.drip.sample.helitterman;

import org.drip.measure.bayesian.JointPosteriorMetrics;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
import org.drip.portfolioconstruction.bayesian.*;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
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
 * <i>Table8Reconciler</i> reconciles the First Set of Outputs (Table #8) of the Black-Litterman Model
 * Process as illustrated in the Following Paper:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios,
 *  			Goldman Sachs Asset Management
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/helitterman/README.md">He and Litterman (1999) Reconcilers</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Table8Reconciler {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrAssetID = new String[] {
			"AUS",
			"CAD",
			"FRA",
			"GER",
			"JPN",
			"UK ",
			"USA"
		};

		double[] adblAssetEquilibriumWeight = new double[] {
			0.016,
			0.022,
			0.052,
			0.055,
			0.116,
			0.124,
			0.615
		};

		double[][] aadblAssetExcessReturnsCorrelation = new double[][] {
			{1.000, 0.488, 0.478, 0.515, 0.439, 0.512, 0.491},
			{0.488, 1.000, 0.664, 0.655, 0.310, 0.608, 0.779},
			{0.478, 0.664, 1.000, 0.861, 0.355, 0.783, 0.668},
			{0.515, 0.655, 0.861, 1.000, 0.354, 0.777, 0.653},
			{0.439, 0.310, 0.355, 0.354, 1.000, 0.405, 0.306},
			{0.512, 0.608, 0.783, 0.777, 0.405, 1.000, 0.652},
			{0.491, 0.779, 0.668, 0.653, 0.306, 0.652, 1.000}
		};

		double[] adblAssetExcessReturnsVolatility = new double[] {
			0.160,
			0.203,
			0.248,
			0.271,
			0.210,
			0.200,
			0.187
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{0.000,  0.000, -0.295,  1.000,  0.000, -0.705,  0.000},
			{0.000,  1.000,  0.000,  0.000,  0.000,  0.000, -1.000},
			{0.000,  1.000,  0.000,  0.000, -1.000,  0.000,  0.000}
		};

		double dblTau = 0.05;
		double dblRiskAversion = 2.5;
		double dblRiskFreeRate = 0.0;

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.0500,
			0.0400,
			0.0412
		};

		double[][] aadblProjectionExcessReturnsCovariance = new double[][] {
			{0.043 * dblTau, 0.000 * dblTau, 0.000 * dblTau},
			{0.000 * dblTau, 0.017 * dblTau, 0.000 * dblTau},
			{0.000 * dblTau, 0.000 * dblTau, 0.059 * dblTau}
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

		double[] adblAssetSpaceJointReturnsReconciler = new double[] {
			0.043,
			0.089,
			0.093,
			0.106,
			0.046,
			0.069,
			0.072
		};

		double[] adblPosteriorOptimalWeightsReconciler = new double[] {
			 0.015,
			 0.539,
			-0.005,
			 0.236,
			 0.110,
			-0.011,
			 0.068
		};

		double[] adblPosteriorOptimalDeviationReconciler = new double[] {
			 0.000,
			 0.518,
			-0.054,
			 0.184,
			 0.000,
			-0.130,
			-0.518
		};

		double[] adblPELoadingsReconciler = new double[] {
			0.193,
			0.544,
			0.000
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[astrAssetID.length][astrAssetID.length];

		for (int i = 0; i < astrAssetID.length; ++i) {
			for (int j = 0; j < astrAssetID.length; ++j)
				aadblAssetExcessReturnsCovariance[i][j] = aadblAssetExcessReturnsCorrelation[i][j] *
					adblAssetExcessReturnsVolatility[i] * adblAssetExcessReturnsVolatility[j];
		}

		BlackLittermanCombinationEngine blce = new BlackLittermanCombinationEngine (
			ForwardReverseOptimizationOutput.Reverse (
				Portfolio.Standard (
					astrAssetID,
					adblAssetEquilibriumWeight
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

		JointPosteriorMetrics jpm = blce.customConfidenceRun().combinationMetrics();

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jpm.joint();

		R1MultivariateNormal posteriorDistribution = (R1MultivariateNormal) jpm.posterior();

		double[] adblAssetSpaceJointReturns = jointDistribution.mean();

		double[][] aadblAssetSpaceJointCovariance = jointDistribution.covariance().covarianceMatrix();

		double[][] aadblAssetSpacePosteriorCovariance = posteriorDistribution.covariance().covarianceMatrix();

		OptimizationOutput op = new QuadraticMeanVarianceOptimizer().allocate (
			new PortfolioConstructionParameters (
				astrAssetID,
				CustomRiskUtilitySettings.RiskAversion (dblRiskAversion),
				PortfolioEqualityConstraintSettings.Unconstrained()
			),
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					astrAssetID,
					posteriorDistribution.mean(),
					aadblAssetSpacePosteriorCovariance
				)
			)
		);

		AssetComponent[] aAC = op.optimalPortfolio().assets();

		ProjectionExposure pe = blce.projectionExposureAttribution();

		double[] adblInterViewComponent = pe.interViewComponent();

		double[] adblIntraViewComponent = pe.intraViewComponent();

		double[] adblPriorViewComponent = pe.priorViewComponent();

		double[] adblCumulativeComponent = pe.cumulativeViewComponent();

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| TAU   => " + FormatUtil.FormatDouble (dblTau, 1, 8, 1.) + "   ||");

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (dblRiskAversion, 1, 8, 1.) + "   ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t|  ASSET EXCESS RETURNS  ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => EQ WT |  VOL  ||");

		System.out.println ("\t|------------------------||");

		for (int i = 0; i < adblAssetSpaceJointReturnsReconciler.length; ++i) {
			System.out.println (
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (adblAssetEquilibriumWeight[i], 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetExcessReturnsVolatility[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           PRIOR CROSS ASSET CORRELATION MATRIX                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String strHeader = "\t|     |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "    " + astrAssetID[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetExcessReturnsCorrelation[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           PRIOR CROSS ASSET COVARIANCE MATRIX                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "    " + astrAssetID[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetExcessReturnsCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                          VIEW SCOPING ASSET PROJECTION LOADING                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "    " + astrAssetID[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < aadblAssetSpaceViewProjection.length; ++i) {
			String strDump = "\t|  #" + i + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
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

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "    " + astrAssetID[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpaceJointCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                         POSTERIOR CROSS ASSET COVARIANCE MATRIX                                ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "    " + astrAssetID[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
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
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturns[i], 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturnsReconciler[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| OPTIMAL POSTERIOR WTS. ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int i = 0; i < aAC.length; ++i) {
			System.out.println (
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (aAC[i].amount(), 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (adblPosteriorOptimalWeightsReconciler[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t|   POSTERIOR DEVIATION  ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int i = 0; i < aAC.length; ++i) {
			System.out.println (
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (aAC[i].amount() - (adblAssetEquilibriumWeight[i]) / (1. + dblTau), 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (adblPosteriorOptimalDeviationReconciler[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||\n");

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println ("\t|              POSTERIOR DEVIATION WEIGHTS ATTRIBUTION            ||");

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println ("\t| VIEW NUM =>  INTRA |  INTER |  PRIOR |  CUMUL |  RECON |  BAYES ||");

		System.out.println ("\t|-----------------------------------------------------------------||");

		for (int i = 0; i < adblInterViewComponent.length; ++i)
			System.out.println (
				"\t| VIEW  #" + (i + 1) + " => " +
				FormatUtil.FormatDouble (adblInterViewComponent[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (adblIntraViewComponent[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (adblPriorViewComponent[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (adblCumulativeComponent[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (adblPELoadingsReconciler[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (adblCumulativeComponent[i] / (1. + dblTau), 1, 3, 1.) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
