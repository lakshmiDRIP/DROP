
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.JointPosteriorMetrics;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput;
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
 * <i>DaJagannathan2005e</i> reconciles the Outputs of the Black-Litterman Model Process. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/blacklitterman/README.md">Black Litterman</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DaJagannathan2005e {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblTau = 0.0025;
		double dblRiskAversion = 1.0;
		double dblRiskFreeRate = 0.03;

		String[] astrAssetID = new String[] {
			"CORPORATE BOND     ",
			"LONG TERM GOVVIE   ",
			"MEDIUM TERM GOVVIE ",
			"STRONG BUY EQUITY  ",
			"BUY EQUITY         ",
			"NEUTRAL EQUITY     ",
			"SELL EQUITY        ",
			"STRONG SELL EQUITY "
		};

		double[] adblAssetEquilibriumWeight = new double[] {
			0.1667,
			0.0833,
			0.0833,
			0.2206,
			0.1184,
			0.1065,
			0.0591,
			0.1622
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
			{0.0050, 0.0047, 0.0024, 0.0036, 0.0023, 0.0031, 0.0032, 0.0030},
			{0.0047, 0.0062, 0.0030, 0.0033, 0.0016, 0.0024, 0.0026, 0.0020},
			{0.0024, 0.0030, 0.0020, 0.0015, 0.0006, 0.0009, 0.0012, 0.0008},
			{0.0036, 0.0033, 0.0015, 0.0468, 0.0354, 0.0371, 0.0379, 0.0414},
			{0.0023, 0.0016, 0.0006, 0.0354, 0.0354, 0.0323, 0.0317, 0.0371},
			{0.0031, 0.0024, 0.0009, 0.0371, 0.0323, 0.0349, 0.0342, 0.0364},
			{0.0032, 0.0026, 0.0012, 0.0379, 0.0317, 0.0342, 0.0432, 0.0384},
			{0.0030, 0.0020, 0.0008, 0.0414, 0.0371, 0.0364, 0.0384, 0.0498}
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{  0.00,  0.00,  0.00,  0.33,  0.18,  0.16,  0.09,  0.24},
			{ -0.50, -0.25, -0.25,  0.33,  0.18,  0.16,  0.09,  0.24},
			{  0.00,  0.00,  0.00,  1.00,  0.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00,  0.00,  0.00,  1.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00,  0.00},
			{  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00}
		};

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.0634,
			0.0400,
			0.0834,
			0.0784,
			0.0484,
			0.0434
		};

		double[][] aadblProjectionExcessReturnsCovariance = new double[][] {
			{0.0012, 0.0000, 0.0000, 0.0000, 0.0000, 0.0000},
			{0.0000, 0.0009, 0.0000, 0.0000, 0.0000, 0.0000},
			{0.0000, 0.0000, 0.0009, 0.0000, 0.0000, 0.0000},
			{0.0000, 0.0000, 0.0000, 0.0036, 0.0000, 0.0000},
			{0.0000, 0.0000, 0.0000, 0.0000, 0.0036, 0.0000},
			{0.0000, 0.0000, 0.0000, 0.0000, 0.0000, 0.0009}
		};

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			new MultivariateMeta (
				new String[] {
					"PROJECTION #1",
					"PROJECTION #2",
					"PROJECTION #3",
					"PROJECTION #4",
					"PROJECTION #5",
					"PROJECTION #6"
				}
			),
			adblProjectionExpectedExcessReturns,
			aadblProjectionExcessReturnsCovariance
		);

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

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| TAU   => " + FormatUtil.FormatDouble (dblTau, 1, 8, 1.) + "   ||");

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (dblRiskAversion, 1, 8, 1.) + "   ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           PRIOR CROSS ASSET COVARIANCE MATRIX                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String strHeader = "\t|     |";

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
				FormatUtil.FormatDouble (adblAssetSpaceJointReturns[i], 2, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturnsReconciler[i], 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
