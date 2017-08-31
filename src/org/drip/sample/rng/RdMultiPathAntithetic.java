
package org.drip.sample.rng;

import java.util.List;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.*;
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
 * RdMultiPathAntithetic illustrates the Generation of the Multi-Path Correlated Random Variables with
 *  Antithetic Variables but without using Quadratic Re-sampling.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdMultiPathAntithetic {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumPath = 1;
		int iNumVertex = 50;
		boolean bApplyAntithetic = true;

		double[][] aadblCorrelation = new double[][] {
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
			aadblCorrelation,
			iNumVertex,
			iNumPath,
			bApplyAntithetic,
			null
		);

		VertexRd vertexRd = cpvd.multiPathVertexRd()[0];

		List<double[]> lsVertexRd = vertexRd.vertexList();

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < lsVertexRd.size(); ++i) {
			double[] adblRd = lsVertexRd.get (i);

			String strDump = "\t||" + FormatUtil.FormatDouble (i, 2, 0, 1.) + " => ";

			for (int j = 0; j < adblRd.length; ++j)
				strDump = strDump + FormatUtil.FormatDouble (adblRd[j], 1, 5, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
