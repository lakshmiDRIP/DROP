
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.*;
import org.drip.measure.gaussian.*;
import org.drip.measure.state.LabelledRd;
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

public class DaJagannathan2005b
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

		String[] assetIDArray = new String[] {
			"A",
			"B",
			"C"
		};

		double[] excessAssetReturnsArray = new double[] {
			0.01,
			0.01,
			0.01
		};

		double[][] excessAssetReturnsCovarianceMatrix = new double[][] {
			{0.00091, 0.00030, 0.00060},
			{0.00030, 0.00011, 0.00020},
			{0.00060, 0.00020, 0.00041}
		};

		double[][] assetSpaceViewProjectionMatrix = new double[][] {
			{1.000, -1.000,  0.000}
		};

		double tau = 1.00;
		double delta = 1.00;

		double[] expectedExcessProjectionReturnsArray = new double[] {
			0.02
		};

		double[][] excessProjectionReturnsCovariance = new double[][] {
			{0.00000001}
		};

		double[] assetSpaceJointReturnsReconcilerArray = new double[] {
			0.0390,
			0.0190,
			0.0290
		};

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			LabelledRd.FromArray (new String[] {"DJVIEW"}),
			expectedExcessProjectionReturnsArray,
			excessProjectionReturnsCovariance
		);

		R1MultivariateNormal scopingDstribution = R1MultivariateNormal.Standard (
			LabelledRd.FromArray (assetIDArray),
			excessAssetReturnsArray,
			excessAssetReturnsCovarianceMatrix
		);

		ScopingContainer scopingContainer = new ScopingContainer (scopingDstribution);

		scopingContainer.addViewLoading (
			"VIEW",
			new ViewLoading (viewDistribution, assetSpaceViewProjectionMatrix)
		);

		R1MultivariateConvolutionMetrics convolutionMetrics = TheilMixedEstimationModel.GenerateComposite (
			scopingContainer,
			"VIEW",
			scopingDstribution
		);

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

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (delta, 1, 8, 1.) + "   ||");

		System.out.println ("\t|------------------------||");

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

		for (int assetIDIndex = 0; assetIDIndex < assetIDArray.length; ++assetIDIndex) {
			header += "    " + assetIDArray[assetIDIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int assetIDIndexI = 0; assetIDIndexI < assetIDArray.length; ++assetIDIndexI) {
			String dump = "\t| " + assetIDArray[assetIDIndexI] + " ";

			for (int assetIDIndexJ = 0; assetIDIndexJ < assetIDArray.length; ++assetIDIndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					excessAssetReturnsCovarianceMatrix[assetIDIndexI][assetIDIndexJ],
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

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		header = "\t|     |";

		for (int assetIDIndex = 0; assetIDIndex < assetIDArray.length; ++assetIDIndex) {
			header += "    " + assetIDArray[assetIDIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int assetSpaceViewProjectionIndex = 0;
			assetSpaceViewProjectionIndex < assetSpaceViewProjectionMatrix.length;
			++assetSpaceViewProjectionIndex)
		{
			String dump = "\t|  #" + assetSpaceViewProjectionIndex + " ";

			for (int assetIDIndex = 0; assetIDIndex < assetIDArray.length; ++assetIDIndex) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpaceViewProjectionMatrix[assetSpaceViewProjectionIndex][assetIDIndex],
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
			String dump = "\t|  #" + assetSpaceViewProjectionIndexI + " ";

			for (int assetSpaceViewProjectionIndexJ = 0;
				assetSpaceViewProjectionIndexJ < assetSpaceViewProjectionMatrix.length;
				++assetSpaceViewProjectionIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					excessProjectionReturnsCovariance[assetSpaceViewProjectionIndexI][assetSpaceViewProjectionIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (
				dump + "|" + FormatUtil.FormatDouble (
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

		for (int assetIDIndex = 0; assetIDIndex < assetIDArray.length; ++assetIDIndex) {
			header += "    " + assetIDArray[assetIDIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int assetIDIndexI = 0; assetIDIndexI < assetIDArray.length; ++assetIDIndexI) {
			String strDump = "\t| " + assetIDArray[assetIDIndexI] + " ";

			for (int assetIDIndexJ = 0; assetIDIndexJ < assetIDArray.length; ++assetIDIndexJ) {
				strDump += "|" + FormatUtil.FormatDouble (
					assetSpaceJointCovarianceMatrix[assetIDIndexI][assetIDIndexJ],
					1,
					8,
					1.
				) + " ";
			}

			System.out.println (strDump + "||");
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

		for (int assetIDIndex = 0; assetIDIndex < assetIDArray.length; ++assetIDIndex) {
			header += "    " + assetIDArray[assetIDIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		for (int assetIDIndexI = 0; assetIDIndexI < assetIDArray.length; ++assetIDIndexI) {
			String dump = "\t| " + assetIDArray[assetIDIndexI] + " ";

			for (int assetIDIndexJ = 0; assetIDIndexJ < assetIDArray.length; ++assetIDIndexJ) {
				dump += "|" + FormatUtil.FormatDouble (
					assetSpacePosteriorCovarianceMatrix[assetIDIndexI][assetIDIndexJ],
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

		System.out.println ("\t|  ID  =>  RIOC  | DJ05  ||");

		System.out.println ("\t|------------------------||");

		for (int assetSpaceJointReturnsReconcilerIndex = 0;
			assetSpaceJointReturnsReconcilerIndex < assetSpaceJointReturnsReconcilerArray.length;
			++assetSpaceJointReturnsReconcilerIndex)
		{
			System.out.println (
				"\t|  [" + assetIDArray[assetSpaceJointReturnsReconcilerIndex] + "] => " +
				FormatUtil.FormatDouble (
					assetSpaceJointReturnsArray[assetSpaceJointReturnsReconcilerIndex],
					1,
					2,
					100.
				) + "% |" + FormatUtil.FormatDouble (
					assetSpaceJointReturnsReconcilerArray[assetSpaceJointReturnsReconcilerIndex],
					1,
					2,
					100.
				) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
