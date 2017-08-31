
package org.drip.sample.blacklitterman;

import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.AssetComponent;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
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
 * DaJagannathan2005d reconciles the Outputs of the Black-Litterman Model Process. The References are:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *  
 *  - Da, Z., and R. Jagannathan (2005): https://www3.nd.edu/~zda/TeachingNote_Black-Litterman.pdf
 *
 * @author Lakshmi Krishnamurthy
 */

public class DaJagannathan2005d {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrID = new String[] {
			"CORPORATE BOND     ",
			"LONG TERM GOVVIE   ",
			"MEDIUM TERM GOVVIE ",
			"STRONG BUY EQUITY  ",
			"BUY EQUITY         ",
			"NEUTRAL EQUITY     ",
			"SELL EQUITY        ",
			"STRONG SELL EQUITY "
		};

		double[][] aadblHistoricalCovariance = new double[][] {
			{0.0050, 0.0047, 0.0024, 0.0036, 0.0023, 0.0031, 0.0032, 0.0030},
			{0.0047, 0.0062, 0.0030, 0.0033, 0.0016, 0.0024, 0.0026, 0.0020},
			{0.0024, 0.0030, 0.0020, 0.0015, 0.0006, 0.0009, 0.0012, 0.0008},
			{0.0036, 0.0033, 0.0015, 0.0468, 0.0354, 0.0371, 0.0379, 0.0414},
			{0.0023, 0.0016, 0.0006, 0.0354, 0.0354, 0.0323, 0.0317, 0.0371},
			{0.0031, 0.0024, 0.0009, 0.0371, 0.0323, 0.0349, 0.0342, 0.0364},
			{0.0032, 0.0026, 0.0012, 0.0379, 0.0317, 0.0342, 0.0432, 0.0384},
			{0.0030, 0.0020, 0.0008, 0.0414, 0.0371, 0.0364, 0.0384, 0.0498}
		};

		double[] adblHistoricalReturn = new double[] {
			0.0595,
			0.0553,
			0.0545,
			0.1302,
			0.1114,
			0.1116,
			0.1217,
			0.1220
		};

		double[] adblHistoricalOptimalWeight = new double[] {
			 0.2154,
			-0.5434,
			 1.1976,
			 0.0624,
			 0.0808,
			-0.0450,
			 0.0472,
			-0.0149
		};

		double[] adblMarketWeight = new double[] {
			0.1667,
			0.0833,
			0.0833,
			0.2206,
			0.1184,
			0.1065,
			0.0591,
			0.1622
		};

		double[] adblMarketImpliedReturnReconciler = new double[] {
			0.0335,
			0.0332,
			0.0315,
			0.0584,
			0.0539,
			0.0544,
			0.0554,
			0.0585
		};

		double dblRiskFreeRate = 0.03;
		double[] adblAdjustedHistoricalReturn = new double[adblHistoricalReturn.length];

		for (int i = 0; i < adblHistoricalReturn.length; ++i)
			adblAdjustedHistoricalReturn[i] = adblHistoricalReturn[i] - dblRiskFreeRate;

		OptimizationOutput op = new QuadraticMeanVarianceOptimizer().allocate (
			new PortfolioConstructionParameters (
				astrID,
				CustomRiskUtilitySettings.RiskTolerant (0.078),
				PortfolioEqualityConstraintSettings.FullyInvested()
			),
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					astrID,
					adblAdjustedHistoricalReturn,
					aadblHistoricalCovariance
				)
			)
		);

		AssetComponent[] aAC = op.optimalPortfolio().assets();

		double[] adblMarketImpliedReturn = Matrix.Product (
			aadblHistoricalCovariance,
			adblMarketWeight
		);

		System.out.println ("\n\t|---------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                HISTORICAL COVARIANCE MATRIX                                 ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrID.length; ++i) {
			String strDump = "\t| " + astrID[i] + " ";

			for (int j = 0; j < astrID.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblHistoricalCovariance[i][j], 1, 4, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||         MARKET WEIGHT           ||");

		System.out.println ("\t||---------------------------------||");

		for (int i = 0; i < adblMarketWeight.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblMarketWeight[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t||---------------------------------||\n");

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||       HISTORICAL RETURNS        ||");

		System.out.println ("\t||---------------------------------||");

		for (int i = 0; i < adblHistoricalReturn.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblHistoricalReturn[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\n\t||---------------------------------------------||");

		System.out.println ("\t||      HISTORICAL PARAM OPTIMAL WEIGHTS       ||");

		System.out.println ("\t||---------------------------------------------||");

		System.out.println ("\t||         ASSET        =>   CALC   |   PAPER  ||");

		System.out.println ("\t||---------------------------------------------||");

		for (int i = 0; i < aAC.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblHistoricalOptimalWeight[i], 3, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aAC[i].amount(), 3, 2, 100.) + "% ||"
			);

		System.out.println ("\t||---------------------------------------------||");

		System.out.println ("\n\t||----------------------------------------||");

		System.out.println ("\t||         MARKET IMPLIED RETURNS         ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||         ASSET        =>  CALC  | PAPER ||");

		System.out.println ("\t||----------------------------------------||");

		for (int i = 0; i < aAC.length; ++i)
			System.out.println (
				"\t||  " + astrID[i] + " => " +
				FormatUtil.FormatDouble (adblMarketImpliedReturn[i] + dblRiskFreeRate, 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (adblMarketImpliedReturnReconciler[i], 1, 2, 100.) + "% ||"
			);

		System.out.println ("\t||----------------------------------------||\n");
	}
}
