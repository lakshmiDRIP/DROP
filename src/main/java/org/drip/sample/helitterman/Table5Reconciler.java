
package org.drip.sample.helitterman;

import org.drip.measure.bayesian.R1MultivariateConvolutionMetrics;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
import org.drip.portfolioconstruction.bayesian.*;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.service.common.FormatUtil;
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
 * <i>Table5Reconciler</i> reconciles the First Set of Outputs (Table #5) of the Black-Litterman Model
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
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/helitterman/README.md">He Litterman (1999) Projection Loadings</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Table5Reconciler
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] assetIDArray = new String[]
		{
			"AUS",
			"CAD",
			"FRA",
			"GER",
			"JPN",
			"UK ",
			"USA"
		};
		double[] assetEquilibriumWeightArray = new double[]
		{
			0.016,
			0.022,
			0.052,
			0.055,
			0.116,
			0.124,
			0.615
		};
		double[][] assetExcessReturnsCorrelationMatrix = new double[][]
		{
			{1.000, 0.488, 0.478, 0.515, 0.439, 0.512, 0.491},
			{0.488, 1.000, 0.664, 0.655, 0.310, 0.608, 0.779},
			{0.478, 0.664, 1.000, 0.861, 0.355, 0.783, 0.668},
			{0.515, 0.655, 0.861, 1.000, 0.354, 0.777, 0.653},
			{0.439, 0.310, 0.355, 0.354, 1.000, 0.405, 0.306},
			{0.512, 0.608, 0.783, 0.777, 0.405, 1.000, 0.652},
			{0.491, 0.779, 0.668, 0.653, 0.306, 0.652, 1.000}
		};
		double[] assetExcessReturnsVolatilityArray = new double[]
		{
			0.160,
			0.203,
			0.248,
			0.271,
			0.210,
			0.200,
			0.187
		};
		double[][] assetSpaceViewProjectionMatrix = new double[][]
		{
			{0.000,  0.000, -0.295,  1.000,  0.000, -0.705,  0.000},
			{0.000,  1.000,  0.000,  0.000,  0.000,  0.000, -1.000}
		};
		double tau = 0.05;
		double riskAversion = 2.5;
		double riskFreeRate = 0.0;
		double[] projectionExpectedExcessReturnsArray = new double[]
		{
			0.05,
			0.03
		};
		double[][] projectionExcessReturnsCovarianceMatrix = new double[][]
		{
			{0.021 * tau, 0.000 * tau},
			{0.000 * tau, 0.017 * tau}
		};
		double[] assetSpaceJointReturnsReconcilerArray = new double[]
		{
			0.044,
			0.087,
			0.095,
			0.112,
			0.046,
			0.070,
			0.075
		};
		double[] posteriorOptimalWeightsReconcilerArray = new double[]
		{
			 0.015,
			 0.419,
			-0.034,
			 0.336,
			 0.110,
			-0.082,
			 0.188
		};
		double[] posteriorOptimalDeviationReconcilerArray = new double[]
		{
			 0.000,
			 0.398,
			-0.084,
			 0.284,
			 0.000,
			-0.200,
			-0.398
		};
		double[] peLoadingsReconcilerArray = new double[]
		{
			0.298,
			0.418
		};

		R1MultivariateNormal viewDistribution = R1MultivariateNormal.Standard (
			new MultivariateMeta (
				new String[]
				{
					"PROJECTION #1",
					"PROJECTION #2"
				}
			),
			projectionExpectedExcessReturnsArray,
			projectionExcessReturnsCovarianceMatrix
		);

		double[][] assetExcessReturnsCovarianceMatrix = new double[assetIDArray.length][assetIDArray.length];

		for (int assetID1 = 0; assetID1 < assetIDArray.length; ++assetID1)
		{
			for (int assetIDJ = 0; assetIDJ < assetIDArray.length; ++assetIDJ)
			{
				assetExcessReturnsCovarianceMatrix[assetID1][assetIDJ] = assetExcessReturnsCorrelationMatrix[assetID1][assetIDJ] *
					assetExcessReturnsVolatilityArray[assetID1] * assetExcessReturnsVolatilityArray[assetIDJ];
			}
		}

		BlackLittermanCombinationEngine blackLittermanCombinationEngine =
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
					false,
					riskFreeRate,
					tau
				),
				new ProjectionSpecification (
					viewDistribution,
					assetSpaceViewProjectionMatrix
				)
			);

		R1MultivariateConvolutionMetrics jointPosteriorMetrics =
			blackLittermanCombinationEngine.customConfidenceRun().jointPosteriorMetrics();

		R1MultivariateNormal jointDistribution = (R1MultivariateNormal) jointPosteriorMetrics.joint();

		R1MultivariateNormal posteriorDistribution =
			(R1MultivariateNormal) jointPosteriorMetrics.posterior();

		double[] assetSpaceJointReturnsArray = jointDistribution.mean();

		double[][] assetSpaceJointCovarianceMatrix = jointDistribution.covariance().covarianceMatrix();

		double[][] assetSpacePosteriorCovarianceMatrix =
			posteriorDistribution.covariance().covarianceMatrix();

		HoldingsAllocation optimizationOutput = new QuadraticMeanVarianceOptimizer().allocate (
			new HoldingsAllocationControl (
				assetIDArray,
				CustomRiskUtilitySettings.RiskAversion (
					riskAversion
				),
				EqualityConstraintSettings.Unconstrained()
			),
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIDArray,
					posteriorDistribution.mean(),
					assetSpacePosteriorCovarianceMatrix
				)
			)
		);

		AssetComponent[] assetComponentArray = optimizationOutput.optimalPortfolio().assetComponentArray();

		ProjectionExposure projectionExposure =
			blackLittermanCombinationEngine.projectionExposureAttribution();

		double[] interViewComponentArray = projectionExposure.interViewComponentArray();

		double[] intraViewComponentArray = projectionExposure.intraViewComponentArray();

		double[] priorViewComponentArray = projectionExposure.priorViewComponentArray();

		double[] cumulativeViewComponentLoadingArray =
			projectionExposure.cumulativeViewComponentLoadingArray();

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| TAU   => " + FormatUtil.FormatDouble (tau, 1, 8, 1.) + "   ||");

		System.out.println ("\t| DELTA => " + FormatUtil.FormatDouble (riskAversion, 1, 8, 1.) + "   ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t|  ASSET EXCESS RETURNS  ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => EQ WT |  VOL  ||");

		System.out.println ("\t|------------------------||");

		for (int assetID = 0; assetID < assetSpaceJointReturnsReconcilerArray.length; ++assetID)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetID] + "] =>" +
				FormatUtil.FormatDouble (assetEquilibriumWeightArray[assetID], 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (assetExcessReturnsVolatilityArray[assetID], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           PRIOR CROSS ASSET CORRELATION MATRIX                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String header = "\t|     |";

		for (int assetID = 0; assetID < assetIDArray.length; ++assetID)
		{
			header += "    " + assetIDArray[assetID] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetID1 = 0; assetID1 < assetIDArray.length; ++assetID1)
		{
			String dump = "\t| " + assetIDArray[assetID1] + " ";

			for (int assetIDJ = 0; assetIDJ < assetIDArray.length; ++assetIDJ)
			{
				dump += "|" + FormatUtil.FormatDouble (assetExcessReturnsCorrelationMatrix[assetID1][assetIDJ], 1, 8, 1.) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           PRIOR CROSS ASSET COVARIANCE MATRIX                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		header = "\t|     |";

		for (int assetID = 0; assetID < assetIDArray.length; ++assetID)
		{
			header += "    " + assetIDArray[assetID] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetID1 = 0; assetID1 < assetIDArray.length; ++assetID1)
		{
			String dump = "\t| " + assetIDArray[assetID1] + " ";

			for (int assetIDJ = 0; assetIDJ < assetIDArray.length; ++assetIDJ)
			{
				dump += "|" + FormatUtil.FormatDouble (assetExcessReturnsCovarianceMatrix[assetID1][assetIDJ], 1, 8, 1.) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                          VIEW SCOPING ASSET PROJECTION LOADING                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		header = "\t|     |";

		for (int assetID = 0; assetID < assetIDArray.length; ++assetID)
		{
			header += "    " + assetIDArray[assetID] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int viewIndex = 0; viewIndex < assetSpaceViewProjectionMatrix.length; ++viewIndex)
		{
			String dump = "\t|  #" + viewIndex + " ";

			for (int assetID = 0; assetID < assetIDArray.length; ++assetID)
			{
				dump += "|" + FormatUtil.FormatDouble (
					assetSpaceViewProjectionMatrix[viewIndex][assetID], 1, 8, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int viewIndexI = 0; viewIndexI < assetSpaceViewProjectionMatrix.length; ++viewIndexI)
		{
			String dump = "\t|  #" + viewIndexI + " ";

			for (int viewIndexJ = 0; viewIndexJ < assetSpaceViewProjectionMatrix.length; ++viewIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					projectionExcessReturnsCovarianceMatrix[viewIndexI][viewIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (dump + "|" + FormatUtil.FormatDouble (
				projectionExpectedExcessReturnsArray[viewIndexI], 1, 2, 100.) + "%"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           JOINT CROSS ASSET COVARIANCE MATRIX                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		header = "\t|     |";

		for (int assetID = 0; assetID < assetIDArray.length; ++assetID)
		{
			header += "    " + assetIDArray[assetID] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetID1 = 0; assetID1 < assetIDArray.length; ++assetID1)
		{
			String dump = "\t| " + assetIDArray[assetID1] + " ";

			for (int assetIDJ = 0; assetIDJ < assetIDArray.length; ++assetIDJ)
			{
				dump += "|" + FormatUtil.FormatDouble (assetSpaceJointCovarianceMatrix[assetID1][assetIDJ], 1, 8, 1.) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                         POSTERIOR CROSS ASSET COVARIANCE MATRIX                                ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		header = "\t|     |";

		for (int assetID = 0; assetID < assetIDArray.length; ++assetID)
		{
			header += "    " + assetIDArray[assetID] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetID1 = 0; assetID1 < assetIDArray.length; ++assetID1)
		{
			String dump = "\t| " + assetIDArray[assetID1] + " ";

			for (int assetIDJ = 0; assetIDJ < assetIDArray.length; ++assetIDJ)
			{
				dump += "|" + FormatUtil.FormatDouble (assetSpacePosteriorCovarianceMatrix[assetID1][assetIDJ], 1, 8, 1.) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t| JOINT/POSTERIOR RETURN ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int assetID = 0; assetID < assetSpaceJointReturnsReconcilerArray.length; ++assetID)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetID] + "] =>" +
				FormatUtil.FormatDouble (assetSpaceJointReturnsArray[assetID], 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (assetSpaceJointReturnsReconcilerArray[assetID], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t| OPTIMAL POSTERIOR WTS. ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int assetID = 0; assetID < assetComponentArray.length; ++assetID)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetID] + "] =>" +
				FormatUtil.FormatDouble (assetComponentArray[assetID].amount(), 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (posteriorOptimalWeightsReconcilerArray[assetID], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\t|------------------------||");

		System.out.println ("\t|   POSTERIOR DEVIATION  ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   ID  => RIOC  | HL99  ||");

		System.out.println ("\t|------------------------||");

		for (int assetID = 0; assetID < assetComponentArray.length; ++assetID)
		{
			System.out.println (
				"\t| [" + assetIDArray[assetID] + "] =>" +
				FormatUtil.FormatDouble (assetComponentArray[assetID].amount() - (assetEquilibriumWeightArray[assetID]) / (1. + tau), 2, 1, 100.) + "% |" +
				FormatUtil.FormatDouble (posteriorOptimalDeviationReconcilerArray[assetID], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------------||\n");

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println ("\t|              POSTERIOR DEVIATION WEIGHTS ATTRIBUTION            ||");

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println ("\t| VIEW NUM =>  INTRA |  INTER |  PRIOR |  CUMUL |  RECON |  BAYES ||");

		System.out.println ("\t|-----------------------------------------------------------------||");

		for (int viewIndex = 0; viewIndex < interViewComponentArray.length; ++viewIndex)
		{
			System.out.println (
				"\t| VIEW  #" + (viewIndex + 1) + " => " +
				FormatUtil.FormatDouble (interViewComponentArray[viewIndex], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (intraViewComponentArray[viewIndex], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (priorViewComponentArray[viewIndex], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (cumulativeViewComponentLoadingArray[viewIndex], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (peLoadingsReconcilerArray[viewIndex], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (cumulativeViewComponentLoadingArray[viewIndex] / (1. + tau), 1, 3, 1.) + " ||"
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
