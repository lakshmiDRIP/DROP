
package org.drip.sample.idzorek;

import org.drip.measure.bayesian.ProjectionDistributionLoading;
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
 * ProjectionImpliedConfidenceLevel reconciles the Implied Confidence Black-Litterman Model Process Levels
 * 	generated using the Idzorek Model. The References are:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *  
 *  - Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User-Specified
 *  	Confidence Levels, Ibbotson Associates, Chicago
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionImpliedConfidenceLevel {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblTau = 0.025;
		double dblRiskAversion = 3.07;
		double dblRiskFreeRate = 0.00;

		double[] adblCustomConfidenceWeightReconciler = new double[] {
			0.2988,
			0.1559,
			0.0935,
			0.1482,
			0.0104,
			0.0165,
			0.2781,
			0.0349
		};

		double[] adblFullConfidenceWeightReconciler = new double[] {
			0.4382,
			0.0165,
			0.0381,
			0.2037,
			0.0042,
			0.0226,
			0.3521,
			0.0349
		};

		double[] adblCustomWeightDeviationReconciler = new double[] {
			 0.1054,
			-0.1054,
			-0.0273,
			 0.0273,
			-0.0030,
			 0.0030,
			 0.0363
		};

		double[] adblFullWeightDeviationReconciler = new double[] {
			 0.2448,
			-0.2448,
			-0.0828,
			 0.0828,
			-0.0092,
			 0.0092,
			 0.1103
		};

		double[] adblImpliedConfidenceLevelReconciler = new double[] {
			 0.4306,
			 0.4306,
			 0.3302,
			 0.3302,
			 0.3302,
			 0.3302,
			 0.3294
		};

		String[] astrAssetID = new String[] {
			"US BONDS                       ",
			"INTERNATIONAL BONDS            ",
			"US LARGE GROWTH                ",
			"US LARGE VALUE                 ",
			"US SMALL GROWTH                ",
			"US SMALL VALUE                 ",
			"INTERNATIONAL DEVELOPED EQUITY ",
			"INTERNATIONAL EMERGING EQUITY  "
		};

		double[] adblAssetEquilibriumWeight = new double[] {
			0.1934,
			0.2613,
			0.1209,
			0.1209,
			0.0134,
			0.0134,
			0.2418,
			0.0349
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
			{ 0.001005,  0.001328, -0.000579, -0.000675,  0.000121,  0.000128, -0.000445, -0.000437},
			{ 0.001328,  0.007277, -0.001307, -0.000610, -0.002237, -0.000989,  0.001442, -0.001535},
			{-0.000579, -0.001307,  0.059582,  0.027588,  0.063497,  0.023036,  0.032967,  0.048039},
			{-0.000675, -0.000610,  0.027588,  0.029609,  0.026572,  0.021465,  0.020697,  0.029854},
			{ 0.000121, -0.002237,  0.063497,  0.026572,  0.102488,  0.042744,  0.039943,  0.065994},
			{ 0.000128, -0.000989,  0.023036,  0.021465,  0.042744,  0.032056,  0.019881,  0.032235},
			{-0.000445,  0.001442,  0.032967,  0.020697,  0.039943,  0.019881,  0.028355,  0.035064},
			{-0.000437, -0.001535,  0.048039,  0.029854,  0.065994,  0.032235,  0.035064,  0.079958}
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00,  0.00},
			{ -1.00,  1.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00},
			{  0.00,  0.00,  0.90, -0.90,  0.10, -0.10,  0.00,  0.00}
		};

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.0525,
			0.0025,
			0.0200
		};

		double[][] aadblProjectionExcessReturnsCovariance = ProjectionDistributionLoading.ProjectionCovariance (
			aadblAssetExcessReturnsCovariance,
			aadblAssetSpaceViewProjection,
			dblTau
		);

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
				true,
				dblRiskFreeRate,
				dblTau
			),
			new ProjectionSpecification (
				viewDistribution,
				aadblAssetSpaceViewProjection
			)
		);

		ProjectionImpliedConfidenceOutput pico = blce.impliedConfidenceRun();

		ForwardReverseOptimizationOutput frooCustomProjectionConfidence = pico.customConfidenceOutput().adjustedMetrics();

		double[] adblCustomConfidenceReturns = frooCustomProjectionConfidence.expectedAssetExcessReturns();

		ForwardReverseOptimizationOutput frooFullProjectionConfidence = pico.fullConfidenceOutput().adjustedMetrics();

		double[] adblFullConfidenceReturns = frooFullProjectionConfidence.expectedAssetExcessReturns();

		double[] adblFullConfidenceWeight = pico.fullProjectionConfidenceWeight();

		double[] adblCustomConfidenceWeight = pico.customProjectionConfidenceWeight();

		double[] adblFullWeightDeviation = pico.fullProjectionConfidenceDeviation();

		double[] adblCustomWeightDeviation = pico.customProjectionConfidenceDeviation();

		double[] adblImpliedConfidenceLevel = pico.level();

		System.out.println ("\n\n\t|----------------------------------------------------||");

		System.out.println ("\t|    CUSTOM vs. FULL CONFIDENCE RETURNS COMPARISON   ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>  FULL  | CUSTOM ||");

		System.out.println ("\t|----------------------------------------------------||");

		for (int i = 0; i < adblFullConfidenceWeight.length; ++i)
			System.out.println ("\t| " +
				astrAssetID[i] + " => " +
				FormatUtil.FormatDouble (adblFullConfidenceReturns[i], 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblCustomConfidenceReturns[i], 1, 2, 100.) + "% || "
			);

		System.out.println ("\t|----------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t| {IDZO} EQUILIBRIUM WEIGHTS COMPARISON ACROSS CONFIDENCE LEVELS ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |   NONE  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int i = 0; i < adblFullConfidenceWeight.length; ++i)
			System.out.println ("\t| " +
				astrAssetID[i] + " => " +
				FormatUtil.FormatDouble (adblFullConfidenceWeightReconciler[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblCustomConfidenceWeightReconciler[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblAssetEquilibriumWeight[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t|----------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t| {DRIP} EQUILIBRIUM WEIGHTS COMPARISON ACROSS CONFIDENCE LEVELS ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |   NONE  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int i = 0; i < adblFullConfidenceWeight.length; ++i)
			System.out.println ("\t| " +
				astrAssetID[i] + " => " +
				FormatUtil.FormatDouble (adblFullConfidenceWeight[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblCustomConfidenceWeight[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblAssetEquilibriumWeight[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t|----------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|  {IDZO} WEIGHTS DEVIATION COMPARISON ACROSS CONFIDENCE LEVELS  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |   NONE  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int i = 0; i < adblFullConfidenceWeight.length - 1; ++i)
			System.out.println ("\t| " +
				astrAssetID[i] + " => " +
				FormatUtil.FormatDouble (adblFullWeightDeviationReconciler[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblCustomWeightDeviationReconciler[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblImpliedConfidenceLevelReconciler[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t|----------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|  {DRIP} WEIGHTS DEVIATION COMPARISON ACROSS CONFIDENCE LEVELS  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println ("\t|             ASSET               =>   FULL  |  CUSTOM |  LEVEL  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		for (int i = 0; i < adblFullConfidenceWeight.length - 1; ++i)
			System.out.println ("\t| " +
				astrAssetID[i] + " => " +
				FormatUtil.FormatDouble (adblFullWeightDeviation[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblCustomWeightDeviation[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblImpliedConfidenceLevel[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t|----------------------------------------------------------------||\n");
	}
}
