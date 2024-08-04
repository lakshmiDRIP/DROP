
package org.drip.sample.principal;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.ContinuousPowerImpact;
import org.drip.execution.optimum.PowerImpactContinuous;
import org.drip.execution.parameters.*;
import org.drip.execution.principal.*;
import org.drip.execution.profiletime.*;
import org.drip.function.r1tor1operator.Flat;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>OptimalMeasuresReconciler</i> reconciles the Dependence Exponents on Liquidity, Trade Size, and
 * 	Permanent Impact Adjusted Principal Discount for the Optimal Principal Horizon and the Optional
 * 	Information Ratio with Almgren and Chriss (2003). The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 *  	</li>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 *  	</li>
 *  	<li>
 * 			Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 *  	</li>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 *  	</li>
 *  	<li>
 * 			Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18
 * 				(7)</b> 57-62
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/principal/README.md">Information Ratio Based Principal Trading</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimalMeasuresReconciler {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblS0 = 50.;
		double dblDailyVolume = 1000000.;
		double dblBidAskSpread = 0.;
		double dblPermanentImpactFactor = 0.;
		double dblTemporaryImpactFactor = 0.01;
		double dblDailyVolumeExecutionFactor = 0.1;
		double dblDrift = 0.;
		double dblVolatility = 1.;
		double dblSerialCorrelation = 0.;
		double dblX = 100000.;
		double dblFinishTime = 1.;
		double dblLambda = 5.e-06;

		double[] adblK = new double[] {
			0.5,
			1.0,
			2.0
		};

		double[][] aadblOptimalHorizonDependenceReconciler = new double[][] {
			{0.810, 2.0, 1.0, 0.0, -2.0},
			{1.000, 1.0, 1.0, 0.0, -1.0},
			{1.134, 0.5, 1.0, 0.0, -0.5}
		};

		double[][] aadblOptimalInformationRatioDependenceReconciler = new double[][] {
			{1.063, -2.0, -1.0, -1.0, 3.0},
			{0.707, -1.0, -1.0, -1.0, 2.0},
			{0.449, -0.5, -1.0, -1.0, 1.5}
		};

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|    Optimal Market Parameters Horizon Dependence     ||");

		System.out.println ("\t|-----------------------------------------------------||");

		for (double dblK : adblK) {
			PriceMarketImpactPower pmip = new PriceMarketImpactPower (
				new AssetTransactionSettings (
					dblS0,
					dblDailyVolume,
					dblBidAskSpread
				),
				dblPermanentImpactFactor,
				dblTemporaryImpactFactor,
				dblDailyVolumeExecutionFactor,
				dblK
			);

			LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
				new ArithmeticPriceDynamicsSettings (
					dblDrift,
					new Flat (dblVolatility),
					dblSerialCorrelation
				),
				new UniformParticipationRateLinear ((ParticipationRateLinear) pmip.permanentTransactionFunction()),
				new UniformParticipationRate ((ParticipationRatePower) pmip.temporaryTransactionFunction())
			);

			Almgren2003Estimator a2003e = new Almgren2003Estimator (
				(PowerImpactContinuous) ContinuousPowerImpact.Standard (
					dblX,
					dblFinishTime,
					lpep,
					dblLambda
				).generate(),
				lpep
			);

			OptimalMeasureDependence omd = a2003e.optimalMeasures().omdHorizon();

			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (dblK, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.constant(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.liquidityExponent(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.blockSizeExponent(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.volatilityExponent(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.adjustedPrincipalDiscountExponent(), 1, 3, 1.) + " ||"
			);
		}

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|-----------------------------------------------------||");

		for (int i = 0; i < adblK.length; ++i)
			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (adblK[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalHorizonDependenceReconciler[i][0], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalHorizonDependenceReconciler[i][1], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalHorizonDependenceReconciler[i][2], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalHorizonDependenceReconciler[i][3], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalHorizonDependenceReconciler[i][4], 1, 3, 1.) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|     Optimal Market Parameters Information Ratio     ||");

		System.out.println ("\t|-----------------------------------------------------||");

		for (double dblK : adblK) {
			PriceMarketImpactPower pmip = new PriceMarketImpactPower (
				new AssetTransactionSettings (
					dblS0,
					dblDailyVolume,
					dblBidAskSpread
				),
				dblPermanentImpactFactor,
				dblTemporaryImpactFactor,
				dblDailyVolumeExecutionFactor,
				dblK
			);

			LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
				new ArithmeticPriceDynamicsSettings (
					dblDrift,
					new Flat (dblVolatility),
					dblSerialCorrelation
				),
				new UniformParticipationRateLinear ((ParticipationRateLinear) pmip.permanentTransactionFunction()),
				new UniformParticipationRate ((ParticipationRatePower) pmip.temporaryTransactionFunction())
			);

			Almgren2003Estimator a2003e = new Almgren2003Estimator (
				(PowerImpactContinuous) ContinuousPowerImpact.Standard (
					dblX,
					dblFinishTime,
					lpep,
					dblLambda
				).generate(),
				lpep
			);

			OptimalMeasureDependence omd = a2003e.optimalMeasures().omdInformationRatio();

			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (dblK, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.constant(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.liquidityExponent(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.blockSizeExponent(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.volatilityExponent(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (omd.adjustedPrincipalDiscountExponent(), 1, 3, 1.) + " ||"
			);
		}

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println ("\t|-----------------------------------------------------||");

		for (int i = 0; i < adblK.length; ++i)
			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (adblK[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalInformationRatioDependenceReconciler[i][0], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalInformationRatioDependenceReconciler[i][1], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalInformationRatioDependenceReconciler[i][2], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalInformationRatioDependenceReconciler[i][3], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (aadblOptimalInformationRatioDependenceReconciler[i][4], 1, 3, 1.) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
