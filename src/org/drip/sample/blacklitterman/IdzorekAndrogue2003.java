
package org.drip.sample.blacklitterman;

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
 * IdzorekAndrogue2003 reconciles the Outputs of the Black-Litterman Model Process. The References are:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *  
 *  - Idzorek, T., and J. Androgue (2003):
 *  	https://faculty.fuqua.duke.edu/~charvey/Teaching/BA453_2005/Black%20Litterman.pdf
 *
 * @author Lakshmi Krishnamurthy
 */

public class IdzorekAndrogue2003 {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrAssetClass = new String[] {
			"US Bonds            ",
			"Global Bonds xUSD   ",
			"World Equity xUS    ",
			"Emerging Equity     ",
			"US Large Cap Growth ",
			"US Large Cap Value  ",
			"US Small Cap Growth ",
			"US Small Cap Value  "
		};

		double[] adblMarketCapitalizationEstimate = new double[] {
			 8360741000000.,
			11583275710000.,
			 9212460000000.,
			  964647000000.,
			 5217844438500.,
			 5217844438500.,
			  459897061500.,
			  459897061500.
		};

		double dblDelta = 3.37;

		double[][] aadblAssetExcessReturnsCorrelation = new double[][] {
			{ 0.0014,  0.0015, -0.0008, -0.0017, -0.0010, -0.0007, -0.0015, -0.0006},
			{ 0.0015,  0.0076,  0.0026, -0.0006, -0.0013, -0.0003, -0.0002,  0.0005},
			{-0.0008,  0.0026,  0.0251,  0.0292,  0.0208,  0.0147,  0.0248,  0.0134},
			{-0.0017, -0.0006,  0.0292,  0.0663,  0.0359,  0.0244,  0.0490,  0.0268},
			{-0.0010, -0.0013,  0.0208,  0.0359,  0.0468,  0.0283,  0.0520,  0.0260},
			{-0.0007, -0.0003,  0.0147,  0.0244,  0.0283,  0.0252,  0.0314,  0.0215},
			{-0.0015, -0.0002,  0.0248,  0.0490,  0.0520,  0.0314,  0.0809,  0.0411},
			{-0.0006,  0.0005,  0.0134,  0.0268,  0.0260,  0.0215,  0.0411,  0.0276}
		};

		double[] adblMarketCapitalizationWeightReconciler = new double[] {
			0.2016,
			0.2793,
			0.2221,
			0.0233,
			0.1258,
			0.1258,
			0.0111,
			0.0111
		};

		double[] adblImpliedReturnsReconciler = new double[] {
			0.0008,
			0.0094,
			0.0395,
			0.0537,
			0.0513,
			0.0368,
			0.0612,
			0.0349
		};

		double dblTotalMarketCapitalization = 0.;
		double[] adblMarketCapitalizationWeight = new double[adblMarketCapitalizationEstimate.length];

		for (int i = 0; i < adblMarketCapitalizationEstimate.length; ++i)
			dblTotalMarketCapitalization += adblMarketCapitalizationEstimate[i];

		for (int i = 0; i < adblMarketCapitalizationEstimate.length; ++i)
			adblMarketCapitalizationWeight[i] = adblMarketCapitalizationEstimate[i] / dblTotalMarketCapitalization;

		double[] adblImpliedReturns = Matrix.Product (
			aadblAssetExcessReturnsCorrelation,
			adblMarketCapitalizationWeight
		);

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                          CO-VARIANCE MATRIX                                          ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < astrAssetClass.length; ++i) {
			String strDump = "\t| " + astrAssetClass[i] + " ";

			for (int j = 0; j < astrAssetClass.length; ++j)
				strDump += "| " + FormatUtil.FormatDouble (aadblAssetExcessReturnsCorrelation[i][j], 1, 4, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t||-------------------------------------------------------------||");

		System.out.println ("\t||         MARKET CAPITALIZATION AND EQUILIBRIUM WEIGHTS       ||");

		System.out.println ("\t||-------------------------------------------------------------||");

		System.out.println ("\t||     ASSET CLASS      =>  CAPITALIZATION |  OUTPUT |  PAPER  ||");

		System.out.println ("\t||-------------------------------------------------------------||");

		for (int i = 0; i < adblMarketCapitalizationEstimate.length; ++i)
			System.out.println (
				"\t|| " + astrAssetClass[i] + " => " +
				FormatUtil.FormatDouble (adblMarketCapitalizationEstimate[i], 14, 0, 1.) + " | " +
				FormatUtil.FormatDouble (adblMarketCapitalizationWeight[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblMarketCapitalizationWeightReconciler[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t||-------------------------------------------------------------||\n");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||         RISK PREMIUM IMPLIED RETURNS      ||");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||     ASSET CLASS      =>  OUTPUT |  PAPER  ||");

		System.out.println ("\t||-------------------------------------------||");

		for (int i = 0; i < adblImpliedReturns.length; ++i)
			System.out.println (
				"\t|| " + astrAssetClass[i] + " => " +
				FormatUtil.FormatDouble (dblDelta * adblImpliedReturns[i], 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblImpliedReturnsReconciler[i], 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t||-------------------------------------------||\n");
	}
}
