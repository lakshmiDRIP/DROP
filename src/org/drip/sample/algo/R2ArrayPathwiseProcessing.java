
package org.drip.sample.algo;

import org.drip.quant.common.FormatUtil;
import org.drip.spaces.big.BigR2Array;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * R2ArrayPathwiseProcessing demonstrates the Functionality that conducts an in-place Path-wise Processing of
 *  an Instance of Big R^2 Array.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R2ArrayPathwiseProcessing {

	private static final void MaxPathwiseProduct (
		final int iSize,
		final int iNumSim)
		throws Exception
	{
		double dblExpectedMaxPathResponse = 0.;
		final double[][] aadblA = new double[iSize][iSize];

		for (int iRun = 0; iRun < iNumSim; ++iRun) {
			for (int i = 0; i < iSize; ++i) {
				for (int j = 0; j < iSize; ++j)
					aadblA[i][j] = Math.random();
			}

			dblExpectedMaxPathResponse += new BigR2Array (aadblA) {
				@Override public double pathResponse (
					final int iX,
					final int iY,
					final double dblPriorPathResponse)
					throws Exception
				{
					return dblPriorPathResponse * aadblA[iX][iY];
				}

				@Override public double maxPathResponse()
					throws Exception
				{
					return maxPathResponse (
						0,
						0,
						1.
					);
				}
			}.maxPathResponse();
		}

		System.out.println (
			"\t|| EXPECTED MAX PATH PRODUCT => " +
			FormatUtil.FormatDouble (dblExpectedMaxPathResponse / iNumSim, 1, 4, 1.) + " ||"
		);
	}

	private static final void MaxPathwiseSum (
		final int iSize,
		final int iNumSim)
		throws Exception
	{
		double dblExpectedMaxPathResponse = 0.;
		final double[][] aadblA = new double[iSize][iSize];

		for (int iRun = 0; iRun < iNumSim; ++iRun) {
			for (int i = 0; i < iSize; ++i) {
				for (int j = 0; j < iSize; ++j)
					aadblA[i][j] = Math.random();
			}

			dblExpectedMaxPathResponse += new BigR2Array (aadblA) {
				@Override public double pathResponse (
					final int iX,
					final int iY,
					final double dblPriorPathResponse)
					throws Exception
				{
					return dblPriorPathResponse + aadblA[iX][iY];
				}

				@Override public double maxPathResponse()
					throws Exception
				{
					return maxPathResponse (
						0,
						0,
						0.
					);
				}
			}.maxPathResponse();
		}

		System.out.println (
			"\t|| EXPECTED MAX PATH SUM     => " +
			FormatUtil.FormatDouble (dblExpectedMaxPathResponse / iNumSim, 1, 4, 1.) + " ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		int iSize = 5;
		int iNumSim = 1000000;
		int iNumRunSet = 5;

		System.out.println ();

		for (int i = 0; i < iNumRunSet; ++i) {
			System.out.println ("\t||--------------------------------------||");

			MaxPathwiseProduct (
				iSize,
				iNumSim
			);

			MaxPathwiseSum (
				iSize,
				iNumSim
			);

			System.out.println ("\t||--------------------------------------||");
		}

		System.out.println ();
	}
}
