
package org.drip.sample.measure;

import org.drip.measure.discrete.SequenceGenerator;
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
 * GaussianSequence demonstrates the Generation of R^1 and Correlated/Uncorrelated R^d Gaussian Random
 *  Number Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussianSequence {

	private static final void CorrelatedSequence (
		final int iCount,
		final double[][] aadblCorrelation,
		final String strHeader)
		throws Exception
	{
		double[][] aadblGaussianJoint = SequenceGenerator.GaussianJoint (
			iCount,
			aadblCorrelation
		);

		System.out.println();

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println (strHeader);

		System.out.println ("\t||----------------------------------------------------||");

		for (int i = 0; i < iCount; ++i) {
			String strDump = "\t||" + FormatUtil.FormatDouble (i, 2, 0, 1.) + " |";

			for (int j = 0; j < aadblCorrelation.length; ++j)
				strDump = strDump + " " + FormatUtil.FormatDouble (aadblGaussianJoint[i][j], 1, 6, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iCount = 50;
		double[][] aadblCorrelation1 = new double[][] {
			{1.00, 0.70, 0.25, 0.05},
			{0.70, 1.00, 0.13, 0.01},
			{0.25, 0.13, 1.00, 0.00},
			{0.05, 0.01, 0.00, 1.00}
		};
		double[][] aadblCorrelation2 = new double[][] {
			{1.00, 0.00, 0.00, 0.00},
			{0.00, 1.00, 0.00, 0.00},
			{0.00, 0.00, 1.00, 0.00},
			{0.00, 0.00, 0.00, 1.00}
		};

		CorrelatedSequence (
			iCount,
			aadblCorrelation1,
			"\t||            CORRELATED GAUSSIAN SEQUENCE            ||"
		);

		CorrelatedSequence (
			iCount,
			aadblCorrelation2,
			"\t||           UNCORRELATED GAUSSIAN SEQUENCE           ||"
		);
	}
}
