
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.*;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.numerical.common.FormatUtil;
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
 * <i>DaJagannathan2005b</i> reconciles the Outputs of the Black-Litterman Model Process. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/blacklitterman/README.md">Canonical Black Litterman and Extensions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DaJagannathan2005b {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String[] astrAssetID = new String[] {
			"A",
			"B",
			"C"
		};

		double[] adblAssetExcessReturns = new double[] {
			0.01,
			0.01,
			0.01
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
			{0.00091, 0.00030, 0.00060},
			{0.00030, 0.00011, 0.00020},
			{0.00060, 0.00020, 0.00041}
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{1.000, -1.000,  0.000}
		};

		double dblTau = 1.00;
		double dblDelta = 1.00;

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.02
		};

		double[][] aadblProjectionExcessReturnsCovariance = new double[][] {
			{0.00000001}
		};

		double[] adblAssetSpaceJointReturnsReconciler = new double[] {
			0.0390,
			0.0190,
			0.0290
		};

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			new MultivariateMeta (new String[] {"DJVIEW"}),
			adblProjectionExpectedExcessReturns,
			aadblProjectionExcessReturnsCovariance
		);

		R1MultivariateNormal r1mnScoping = R1MultivariateNormal.Standard (
			new MultivariateMeta (astrAssetID),
			adblAssetExcessReturns,
			aadblAssetExcessReturnsCovariance
		);

		ScopingProjectionVariateDistribution spvd = new ScopingProjectionVariateDistribution (r1mnScoping);

		spvd.addProjectionDistributionLoading (
			"VIEW",
			new ProjectionDistributionLoading (
				viewDistribution,
				aadblAssetSpaceViewProjection
			)
		);

		R1MultivariateConvolutionMetrics jpm = TheilMixedEstimationModel.GenerateComposite (
			spvd,
			"VIEW",
			r1mnScoping
		);

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jpm.joint();

		R1MultivariateNormal posteriorDistribution = (R1MultivariateNormal) jpm.posterior();

		double[] adblAssetSpaceJointReturns = jointDistribution.mean();

		double[][] aadblAssetSpaceJointCovariance = jointDistribution.covariance().covarianceMatrix();

		double[][] aadblAssetSpacePosteriorCovariance = posteriorDistribution.covariance().covarianceMatrix();

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| TAU   => " + FormatUtil.FormatDouble (dblTau, 1, 8, 1.) + "   ||");

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (dblDelta, 1, 8, 1.) + "   ||");

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

		System.out.println ("\t|  ID  =>  RIOC  | DJ05  ||");

		System.out.println ("\t|------------------------||");

		for (int i = 0; i < adblAssetSpaceJointReturnsReconciler.length; ++i) {
			System.out.println (
				"\t|  [" + astrAssetID[i] + "] => " +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturns[i], 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (adblAssetSpaceJointReturnsReconciler[i], 1, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
