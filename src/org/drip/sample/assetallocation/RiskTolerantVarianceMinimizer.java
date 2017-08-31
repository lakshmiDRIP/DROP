
package org.drip.sample.assetallocation;

import org.drip.feed.loader.*;
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
 * RiskTolerantVarianceMinimizer demonstrates the Construction of an Optimal Portfolio using the Variance
 * 	Minimization with a Fully Invested Constraint on a Risk Tolerance Objective Function.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RiskTolerantVarianceMinimizer {

	static final void RiskTolerancePortfolio (
		final String[] astrAsset,
		final AssetUniverseStatisticalProperties ausp,
		final double dblRiskTolerance)
		throws Exception
	{
		OptimizationOutput opf = new QuadraticMeanVarianceOptimizer().allocate (
			new PortfolioConstructionParameters (
				astrAsset,
				CustomRiskUtilitySettings.RiskTolerant (dblRiskTolerance),
				new PortfolioEqualityConstraintSettings (
					PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
					Double.NaN
				)
			),
			ausp
		);

		AssetComponent[] aAC = opf.optimalPortfolio().assets();

		System.out.println ("\t|----------------||");

		for (AssetComponent ac : aAC)
			System.out.println ("\t| " + ac.id() + " | " + FormatUtil.FormatDouble (ac.amount(), 3, 2, 100.) + "% ||");

		System.out.println ("\t|----------------||");

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t| Portfolio Notional           : " + FormatUtil.FormatDouble (opf.optimalPortfolio().notional(), 1, 3, 1.) + " ||");

		System.out.println ("\t| Portfolio Expected Return    : " + FormatUtil.FormatDouble (opf.optimalMetrics().excessReturnsMean(), 1, 2, 100.) + "% ||");

		System.out.println ("\t| Portfolio Standard Deviation : " + FormatUtil.FormatDouble (opf.optimalMetrics().excessReturnsStandardDeviation(), 1, 2, 100.) + "% ||");

		System.out.println ("\t|---------------------------------------||\n");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strSeriesLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

		CSVGrid csvGrid = CSVParser.NamedStringGrid (strSeriesLocation);

		String[] astrVariateHeader = csvGrid.headers();

		String[] astrAsset = new String[astrVariateHeader.length - 1];
		double[][] aadblVariateSample = new double[astrVariateHeader.length - 1][];

		for (int i = 0; i < astrAsset.length; ++i) {
			astrAsset[i] = astrVariateHeader[i + 1];

			aadblVariateSample[i] = csvGrid.doubleArrayAtColumn (i + 1);
		}

		AssetUniverseStatisticalProperties ausp = AssetUniverseStatisticalProperties.FromMultivariateMetrics (
			MultivariateMoments.Standard (
				astrAsset,
				aadblVariateSample
			)
		);

		double[] adblRiskTolerance = new double[] {
			0.1,
			0.2,
			0.3,
			0.5,
			1.0
		};

		for (double dblRiskTolerance : adblRiskTolerance) {
			System.out.println ("\n\t|---------------------------------------------||");

			System.out.println ("\t| Running Optimization For Risk Tolerance " + dblRiskTolerance + " ||");

			System.out.println ("\t|---------------------------------------------||");

			RiskTolerancePortfolio (
				astrAsset,
				ausp,
				dblRiskTolerance
			);
		}
	}
}
