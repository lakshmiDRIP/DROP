
package org.drip.sample.assetallocationexcel;

import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.InteriorPointBarrierControl;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
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
 * CMV Reconciler demonstrates the Execution and Reconciliation of the Dual Constrained Mean Variance against
 *  an XL-based Implementation for Portfolio Design Returns #6.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CMVReconciler6 {

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
			0.05,
			0.04,
			0.06,
			0.03,
			0.03,
			0.03,
			0.13
		};

		double[] adblAssetUpperBound = new double[] {
			0.43,
			0.27,
			0.44,
			0.32,
			0.66,
			0.32,
			0.88
		};

		double[] adblAssetExpectedReturns = new double[] {
			0.1300,
			0.0700,
			0.0400,
			0.0300,
			0.0800,
			0.1000,
			0.0100
		};

		double dblPortfolioDesignReturn = 0.07000;

		double[][] aadblAssetReturnsCovariance = new double[][] {
			{0.002733 * 12, 0.002083 * 12, 0.001593 * 12, 0.000488 * 12, 0.001172 * 12, 0.002312 * 12, 0.000710 * 12},
			{0.002083 * 12, 0.002768 * 12, 0.001302 * 12, 0.000457 * 12, 0.001105 * 12, 0.001647 * 12, 0.000563 * 12},
			{0.001593 * 12, 0.001302 * 12, 0.001463 * 12, 0.000639 * 12, 0.001050 * 12, 0.001110 * 12, 0.000519 * 12},
			{0.000488 * 12, 0.000457 * 12, 0.000639 * 12, 0.000608 * 12, 0.000663 * 12, 0.000042 * 12, 0.000370 * 12},
			{0.001172 * 12, 0.001105 * 12, 0.001050 * 12, 0.000663 * 12, 0.001389 * 12, 0.000825 * 12, 0.000661 * 12},
			{0.002312 * 12, 0.001647 * 12, 0.001110 * 12, 0.000042 * 12, 0.000825 * 12, 0.005211 * 12, 0.000749 * 12},
			{0.000710 * 12, 0.000563 * 12, 0.000519 * 12, 0.000370 * 12, 0.000661 * 12, 0.000749 * 12, 0.000703 * 12}
		};

		double[] adblReconcilerVariate = new double[] {
			0.2752,
			0.0400,
			0.0600,
			0.2327,
			0.2322,
			0.0300,
			0.1300
		};

		AssetComponent[] aACReconciler = new AssetComponent[adblReconcilerVariate.length];

		for (int i = 0; i < adblReconcilerVariate.length; ++i)
			aACReconciler[i] = new AssetComponent (
				astrAssetName[i],
				adblReconcilerVariate[i]
			);

		Portfolio pfReconciler = new Portfolio (aACReconciler);

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

		BoundedPortfolioConstructionParameters pdp = new BoundedPortfolioConstructionParameters (
			astrAssetName,
			CustomRiskUtilitySettings.VarianceMinimizer(),
			new PortfolioEqualityConstraintSettings (
				PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT | PortfolioEqualityConstraintSettings.RETURNS_CONSTRAINT,
				dblPortfolioDesignReturn
			)
		);

		for (int i = 0; i < astrAssetName.length; ++i)
			pdp.addBound (
				astrAssetName[i],
				adblAssetLowerBound[i],
				adblAssetUpperBound[i]
			);

		OptimizationOutput pfOptimal = cmva.allocate (
			pdp,
			ausp
		);

		AssetComponent[] aACOptimal = pfOptimal.optimalPortfolio().assets();

		System.out.println ("\t|--------------------------||");

		System.out.println ("\t|   OPTIMAL ASSET WEIGHTS  ||");

		System.out.println ("\t|--------------------------||");

		System.out.println ("\t| ASSET |  DRIP  |  EXCEL  ||");

		System.out.println ("\t|--------------------------||");

		for (int i = 0; i < aACOptimal.length; ++i)
			System.out.println (
				"\t|  " + aACOptimal[i].id() + "  |" +
				FormatUtil.FormatDouble (aACOptimal[i].amount(), 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aACReconciler[i].amount(), 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t|--------------------------||\n\n");

		System.out.println ("\t|------------------------------------------------||");

		System.out.println ("\t| Optimal Portfolio Normalize          : " + FormatUtil.FormatDouble (pfOptimal.optimalPortfolio().notional(), 1, 2, 1.) + "   ||");

		System.out.println ("\t| Optimal Portfolio Input Return       : " + FormatUtil.FormatDouble (dblPortfolioDesignReturn, 1, 2, 100.) + "%  ||");

		System.out.println ("\t| Optimal Portfolio Expected Return    : " + FormatUtil.FormatDouble (pfOptimal.optimalMetrics().excessReturnsMean(), 1, 2, 100.) + "%  ||");

		System.out.println ("\t| Optimal Portfolio Standard Deviation : " + FormatUtil.FormatDouble (pfOptimal.optimalMetrics().excessReturnsStandardDeviation(), 2, 2, 100.) + "% ||");

		System.out.println ("\t| Excel Portfolio Standard Deviation   : " + FormatUtil.FormatDouble (Math.sqrt (pfReconciler.variance (ausp)), 2, 2, 100.) + "% ||");

		System.out.println ("\t|------------------------------------------------||\n");
	}
}
