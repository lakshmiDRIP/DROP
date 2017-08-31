
package org.drip.sample.blacklitterman;

import org.drip.measure.bayesian.JointPosteriorMetrics;
import org.drip.measure.continuous.MultivariateMeta;
import org.drip.measure.gaussian.*;
import org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.bayesian.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * Yamabe2016 reconciles the Outputs of the Black-Litterman Model Process. The Reference is:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *
 * @author Lakshmi Krishnamurthy
 */

public class Yamabe2016 {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

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

		JointPosteriorMetrics jpm = blce.customConfidenceRun().combinationMetrics();

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
	}
}
