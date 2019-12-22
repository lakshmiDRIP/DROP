
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.R1MultivariateConvolutionMetrics;
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

public class Yamabe2016 {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblTau = 1.0000;
		double dblRiskAversion = 2.6;
		double dblRiskFreeRate = 0.00;

		String[] astrAssetID = new String[] {
			"ASSET A ",
			"ASSET B ",
			"ASSET C ",
			"ASSET D ",
			"ASSET E ",
			"ASSET F "
		};

		double[] adblAssetEquilibriumWeight = new double[] {
			0.2535,
			0.1343,
			0.1265,
			0.1375,
			0.0733,
			0.2749
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
			{0.00273, 0.00208, 0.00159, 0.00049, 0.00117, 0.00071},
			{0.00208, 0.00277, 0.00130, 0.00046, 0.00111, 0.00056},
			{0.00159, 0.00130, 0.00146, 0.00064, 0.00105, 0.00052},
			{0.00049, 0.00046, 0.00064, 0.00061, 0.00066, 0.00037},
			{0.00117, 0.00111, 0.00105, 0.00066, 0.00139, 0.00066},
			{0.00071, 0.00056, 0.00052, 0.00037, 0.00066, 0.00070}
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
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

		double[] adblAssetSpacePriorReturnsReconciler = new double[] {
			0.003954,
			0.003540,
			0.002782,
			0.001299,
			0.002476,
			0.001594
		};

		double[] adblAssetSpaceJointReturnsReconciler = new double[] {
			0.003755,
			0.003241,
			0.002612,
			0.001305,
			0.002559,
			0.001662
		};

		double[] adblExpectedHistoricalReturns = new double[] {
			0.003559,
			0.000469,
			0.004053,
			0.004527,
			0.000904,
			0.001581
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

		R1MultivariateConvolutionMetrics jpm = blce.customConfidenceRun().jointPosteriorMetrics();

		R1MultivariateNormal priorDistribution = (R1MultivariateNormal) jpm.prior();

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jpm.joint();

		R1MultivariateNormal posteriorDistribution = (R1MultivariateNormal) jpm.posterior();

		double[] adblAssetSpacePriorReturns = priorDistribution.mean();

		double[] adblAssetSpaceJointReturns = jointDistribution.mean();

		double[][] aadblAssetSpaceJointCovariance = jointDistribution.covariance().covarianceMatrix();

		double[][] aadblAssetSpacePosteriorCovariance = posteriorDistribution.covariance().covarianceMatrix();

		System.out.println ("\n\t|-------------------------||");

		System.out.println ("\t| TAU            =>" + FormatUtil.FormatDouble (dblTau, 1, 2, 1.) + "  ||");

		System.out.println ("\t| RISK AVERSION  =>" + FormatUtil.FormatDouble (dblRiskAversion, 1, 2, 1.) + "  ||");

		System.out.println ("\t| RISK FREE RATE =>" + FormatUtil.FormatDouble (dblRiskFreeRate, 1, 2, 1.) + "% ||");

		System.out.println ("\t|-------------------------||");

		System.out.println ("\n\t|----------------------------------------------------------------------------------------||");

		System.out.println ("\t|                       PRIOR CROSS ASSET COVARIANCE MATRIX                              ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		String strHeader = "\t|    ID    |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "  " + astrAssetID[i] + "  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetExcessReturnsCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------------||");

		System.out.println ("\t|                    VIEW SCOPING ASSET PROJECTION LOADING                          ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		strHeader = "\t|     |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "  " + astrAssetID[i] + "  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		for (int i = 0; i < aadblAssetSpaceViewProjection.length; ++i) {
			String strDump = "\t|  #" + i + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpaceViewProjection[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------||");

		for (int i = 0; i < aadblAssetSpaceViewProjection.length; ++i) {
			String strDump = "\t|  #" + i + " ";

			for (int j = 0; j < aadblAssetSpaceViewProjection.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblProjectionExcessReturnsCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "|" + FormatUtil.FormatDouble (adblProjectionExpectedExcessReturns[i], 1, 2, 100.) + "% ||");
		}

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\n\t|----------------------------------------------------------------------------------------||");

		System.out.println ("\t|                       JOINT CROSS ASSET COVARIANCE MATRIX                              ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		strHeader = "\t|    ID    |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "  " + astrAssetID[i] + "  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpaceJointCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     POSTERIOR CROSS ASSET COVARIANCE MATRIX                            ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		strHeader = "\t|    ID    |";

		for (int i = 0; i < astrAssetID.length; ++i)
			strHeader += "  " + astrAssetID[i] + "  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|----------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetID.length; ++i) {
			String strDump = "\t| " + astrAssetID[i] + " ";

			for (int j = 0; j < astrAssetID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblAssetSpacePosteriorCovariance[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|      IMPLIED/PRIOR RETURN       ||");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|     ID     =>  RIOCEE |  YAMABE ||");

		System.out.println ("\t|---------------------------------||");

		for (int i = 0; i < adblAssetSpacePriorReturns.length; ++i) {
			System.out.println (
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (adblAssetSpacePriorReturns[i], 1, 4, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpacePriorReturnsReconciler[i], 1, 4, 100.) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------||\n");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|     JOINT/POSTERIOR RETURN      ||");

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\t|     ID     =>  RIOCEE |  YAMABE ||");

		System.out.println ("\t|---------------------------------||");

		for (int i = 0; i < adblAssetSpaceJointReturnsReconciler.length; ++i) {
			System.out.println (
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturns[i], 1, 4, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturnsReconciler[i], 1, 4, 100.) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------||\n");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|     PRIOR/POSTERIOR/HISTORICAL RETURN     ||");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|     ID     =>  PRIOR  |   POST  |   HIST  ||");

		System.out.println ("\t|-------------------------------------------||");

		for (int i = 0; i < adblAssetSpaceJointReturnsReconciler.length; ++i) {
			System.out.println (
				"\t| [" + astrAssetID[i] + "] =>" +
				FormatUtil.FormatDouble (adblAssetSpacePriorReturns[i], 1, 4, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturns[i], 1, 4, 100.) + "% |" +
				FormatUtil.FormatDouble (adblExpectedHistoricalReturns[i], 1, 4, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
