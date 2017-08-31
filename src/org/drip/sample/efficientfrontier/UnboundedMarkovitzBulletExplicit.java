
package org.drip.sample.efficientfrontier;

import org.drip.feed.loader.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.AssetComponent;
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
 * UnboundedMarkovitzBulletExplicit demonstrates the Explicit Construction of the Efficient Frontier.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnboundedMarkovitzBulletExplicit {

	private static double DisplayPortfolioMetrics (
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

		return optPort.optimalMetrics().excessReturnsMean();
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iRiskReturnGranularity = 40;
		double dblRiskToleranceFactor = 0.;
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

		PortfolioConstructionParameters pcp = new PortfolioConstructionParameters (
			astrAsset,
			CustomRiskUtilitySettings.RiskTolerant (dblRiskToleranceFactor),
			new PortfolioEqualityConstraintSettings (
				PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
				Double.NaN
			)
		);

		MeanVarianceOptimizer mvo = new QuadraticMeanVarianceOptimizer();

		System.out.println ("\n\n\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     GLOBAL MINIMUM VARIANCE AND MAXIMUM RETURNS PORTFOLIOS                    ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		String strHeader = "\t| RETURNS | RISK % |";

		for (int i = 0; i < astrAsset.length; ++i)
			strHeader += "   " + astrAsset[i] + "    |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		double dblGMVReturns = DisplayPortfolioMetrics (
			mvo.globalMinimumVarianceAllocate (
				pcp,
				ausp
			)
		);

		double dblMaximumReturns = DisplayPortfolioMetrics (
			mvo.longOnlyMaximumReturnsAllocate (
				pcp,
				ausp
			)
		);

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n\n");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|         EFFICIENT FRONTIER: PORTFOLIO RISK & RETURNS + CORRESPONDING ASSET ALLOCATION         ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println (strHeader + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		double dblReturnsGrain = (dblMaximumReturns - dblGMVReturns) / iRiskReturnGranularity;

		for (int i = 0; i <= iRiskReturnGranularity; ++i) {
			PortfolioConstructionParameters pcpReturnsConstrained = new PortfolioConstructionParameters (
				astrAsset,
				CustomRiskUtilitySettings.VarianceMinimizer(),
				new PortfolioEqualityConstraintSettings (
					PortfolioEqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT | PortfolioEqualityConstraintSettings.RETURNS_CONSTRAINT,
					dblGMVReturns + i * dblReturnsGrain
				)
			);

			DisplayPortfolioMetrics (
				mvo.allocate (
					pcpReturnsConstrained,
					ausp
				)
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n");
	}
}
