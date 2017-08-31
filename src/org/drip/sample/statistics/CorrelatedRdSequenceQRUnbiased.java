
package org.drip.sample.statistics;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.*;
import org.drip.measure.statistics.MultivariateDiscrete;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * CorrelatedRdSequenceQRUnbiased demonstrates the Generation of the Statistical Measures for the Input
 *  Correlated Sequence Set created using the Multi-Path Correlated Random Variable Generator using Unbiased
 *  Quadratic Re-sampling but without Antithetic Variables.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CorrelatedRdSequenceQRUnbiased {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumPath = 1;
		int iNumVertex = 50000;
		boolean bDebias = true;
		boolean bMeanCenter = false;
		boolean bApplyAntithetic = false;

		double[][] aadblCorrelationInput = new double[][] {
			{1.000, 0.161, 0.245, 0.352, 0.259, 0.166, 0.003, 0.038, 0.114},	// USD_LIBOR_3M
			{0.161, 1.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000},	// EUR_LIBOR_3M
			{0.245, 0.000, 1.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000},	// JPY_LIBOR_3M
			{0.352, 0.000, 0.000, 1.000, 0.000, 0.000, 0.000, 0.000, 0.000},	// CHF_LIBOR_3M
			{0.259, 0.000, 0.000, 0.000, 1.000, 0.000, 0.000, 0.000, 0.000},	// GBP_LIBOR_3M
			{0.166, 0.000, 0.000, 0.000, 0.000, 1.000, 0.000, 0.000, 0.000},	// EURUSD
			{0.003, 0.000, 0.000, 0.000, 0.000, 0.000, 1.000, 0.000, 0.000},	// JPYUSD
			{0.038, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 1.000, 0.000},	// CHFUSD
			{0.114, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 1.000},	// GBPUSD
		};

		CorrelatedPathVertexDimension cpvd = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			aadblCorrelationInput,
			iNumVertex,
			iNumPath,
			bApplyAntithetic,
			new QuadraticResampler (
				bMeanCenter,
				bDebias
			)
		);

		VertexRd vertexRd = cpvd.multiPathVertexRd()[0];

		MultivariateDiscrete mds = new MultivariateDiscrete (vertexRd.flatform());

		double[] adblMeanOutput = mds.mean();

		double[] adblErrorOutput = mds.error();

		double[] adblVarianceOutput = mds.variance();

		double[][] aadblCovarianceOutput = mds.covariance();

		double[][] aadblCorrelationOutput = mds.correlation();

		double[] adblStandardDeviationOutput = mds.standardDeviation();

		System.out.println();

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||                R^1 METRICS                ||");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||    L -> R:                                ||");

		System.out.println ("\t||            - Mean                         ||");

		System.out.println ("\t||            - Error                        ||");

		System.out.println ("\t||            - Variance                     ||");

		System.out.println ("\t||            - Standard Deviation           ||");

		System.out.println ("\t||-------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i)
			System.out.println ("\t|| " +
				FormatUtil.FormatDouble (adblMeanOutput[i], 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (adblErrorOutput[i], 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (adblVarianceOutput[i], 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (adblStandardDeviationOutput[i], 1, 5, 1.) + " ||"
			);

		System.out.println ("\t||-------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                     INPUT CORRELATION                                    ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i) {
			String strDump = "\t|| ";

			for (int j = 0; j < adblMeanOutput.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (aadblCorrelationInput[i][j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                    OUTPUT CORRELATION                                    ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i) {
			String strDump = "\t|| ";

			for (int j = 0; j < adblMeanOutput.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (aadblCorrelationOutput[i][j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                    OUTPUT COVARIANCE                                     ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblMeanOutput.length; ++i) {
			String strDump = "\t|| ";

			for (int j = 0; j < adblMeanOutput.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (aadblCovarianceOutput[i][j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
