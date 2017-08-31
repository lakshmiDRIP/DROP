
package org.drip.sample.efficientfrontier;

import java.util.*;

import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.InteriorPointBarrierControl;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
import org.drip.portfolioconstruction.mpt.MarkovitzBullet;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
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
 * LongOnlyMarkovitzBullet demonstrates the Construction of the Efficient Frontier using the Constrained Mean
 *  Variance Optimizer for a Long-Only Portfolio.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LongOnlyMarkovitzBullet {

	private static void DisplayPortfolioMetrics (
		final OptimizationOutput optPort)
		throws Exception
	{
		AssetComponent[] aACGlobalMinimum = optPort.optimalPortfolio().assets();

		String strDump = "\t|" +
			FormatUtil.FormatDouble (optPort.optimalMetrics().excessReturnsMean(), 1, 4, 100.) + "% |" +
			FormatUtil.FormatDouble (optPort.optimalMetrics().excessReturnsStandardDeviation(), 1, 4, 100.) + " |";

		for (AssetComponent ac : aACGlobalMinimum)
			strDump += " " + FormatUtil.FormatDouble (ac.amount(), 3, 2, 100.) + "% |";

		System.out.println (strDump + "|");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrAssetName = new String[] {
			"TOK",
			"EWJ",
			"HYG",
			"LQD",
			"EMD",
			"GSG",
			"BWX"
		};

		double[] adblAssetLowerBound = new double[] {
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00
		};

		double[] adblAssetUpperBound = new double[] {
			1.00,
			1.00,
			1.00,
			1.00,
			1.00,
			1.00,
			1.00
		};

		double[] adblAssetExpectedReturns = new double[] {
			0.008430,
			0.007230,
			0.006450,
			0.002560,
			0.004480,
			0.006840,
			0.001670
		};

		double[][] aadblAssetReturnsCovariance = new double[][] {
			{0.002733, 0.002083, 0.001593, 0.000488, 0.001172, 0.002312, 0.000710},
			{0.002083, 0.002768, 0.001302, 0.000457, 0.001105, 0.001647, 0.000563},
			{0.001593, 0.001302, 0.001463, 0.000639, 0.001050, 0.001110, 0.000519},
			{0.000488, 0.000457, 0.000639, 0.000608, 0.000663, 0.000042, 0.000370},
			{0.001172, 0.001105, 0.001050, 0.000663, 0.001389, 0.000825, 0.000661},
			{0.002312, 0.001647, 0.001110, 0.000042, 0.000825, 0.005211, 0.000749},
			{0.000710, 0.000563, 0.000519, 0.000370, 0.000661, 0.000749, 0.000703}
		};

		AssetUniverseStatisticalProperties ausp = AssetUniverseStatisticalProperties.FromMultivariateMetrics (
			MultivariateMoments.Standard (
				astrAssetName,
				adblAssetExpectedReturns,
				aadblAssetReturnsCovariance
			)
		);

		double[][] aadblCovarianceMatrix = ausp.covariance (astrAssetName);

		System.out.println ("\n\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                  CROSS ASSET COVARIANCE MATRIX                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String strHeader = "\t|     |";

		for (int i = 0; i < astrAssetName.length; ++i)
			strHeader += "    " + astrAssetName[i] + "     |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetName.length; ++i) {
			String strDump = "\t| " + astrAssetName[i] + " ";

			for (int j = 0; j < astrAssetName.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblCovarianceMatrix[i][j], 1, 8, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||\n\n");

		System.out.println ("\t|-------------------||");

		System.out.println ("\t|   ASSET BOUNDS    ||");

		System.out.println ("\t|-------------------||");

		for (int i = 0; i < astrAssetName.length; ++i)
			System.out.println (
				"\t| " + astrAssetName[i] + " | " +
				FormatUtil.FormatDouble (adblAssetLowerBound[i], 2, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (adblAssetUpperBound[i], 2, 0, 100.) + "% ||"
			);

		System.out.println ("\t|-------------------||\n\n");

		InteriorPointBarrierControl ipbc = InteriorPointBarrierControl.Standard();

		System.out.println ("\t|--------------------------------------------||");

		System.out.println ("\t|  INTERIOR POINT METHOD BARRIER PARAMETERS  ||");

		System.out.println ("\t|--------------------------------------------||");

		System.out.println ("\t|    Barrier Decay Velocity        : " + 1. / ipbc.decayVelocity());

		System.out.println ("\t|    Barrier Decay Steps           : " + ipbc.numDecaySteps());

		System.out.println ("\t|    Initial Barrier Strength      : " + ipbc.initialStrength());

		System.out.println ("\t|    Barrier Convergence Tolerance : " + ipbc.relativeTolerance());

		System.out.println ("\t|--------------------------------------------||\n\n");

		ConstrainedMeanVarianceOptimizer cmva = new ConstrainedMeanVarianceOptimizer (
			ipbc,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (false)
		);

		BoundedPortfolioConstructionParameters bpcp = new BoundedPortfolioConstructionParameters (
			astrAssetName,
			CustomRiskUtilitySettings.VarianceMinimizer(),
			new PortfolioEqualityConstraintSettings (
				PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
				Double.NaN
			)
		);

		for (int i = 0; i < astrAssetName.length; ++i)
			bpcp.addBound (
				astrAssetName[i],
				adblAssetLowerBound[i],
				adblAssetUpperBound[i]
			);

		int iFrontierSampleUnits = 20;

		MarkovitzBullet mb = cmva.efficientFrontier (
			bpcp,
			ausp,
			iFrontierSampleUnits
		);

		System.out.println ("\n\n\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     GLOBAL MINIMUM VARIANCE AND MAXIMUM RETURNS PORTFOLIOS                    ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		strHeader = "\t| RETURNS | RISK % |";

		for (int i = 0; i < astrAssetName.length; ++i)
			strHeader += "   " + astrAssetName[i] + "    |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		DisplayPortfolioMetrics (mb.globalMinimumVariance());

		DisplayPortfolioMetrics (mb.longOnlyMaximumReturns());

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n\n");

		TreeMap<Double, OptimizationOutput> mapFrontierPortfolio = mb.optimalPortfolios();

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|         EFFICIENT FRONTIER: PORTFOLIO RISK & RETURNS + CORRESPONDING ASSET ALLOCATION         ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println (strHeader + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		for (Map.Entry<Double, OptimizationOutput> me : mapFrontierPortfolio.entrySet())
			DisplayPortfolioMetrics (me.getValue());

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n");
	}
}
