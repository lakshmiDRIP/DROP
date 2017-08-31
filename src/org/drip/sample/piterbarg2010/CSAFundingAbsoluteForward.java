
package org.drip.sample.piterbarg2010;

import org.drip.measure.dynamics.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.xva.csa.FundingBasisEvolver;

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
 * CSAFundingAbsoluteForward compares the Absolute Differences between the CSA and the non-CSA Forward LIBOR
 * 	under a Stochastic Funding Model. The References are:
 *  
 *  - Barden, P. (2009): Equity Forward Prices in the Presence of Funding Spreads, ICBI Conference, Rome.
 *  
 *  - Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk of
 *  	Derivative Portfolios, ICBI Conference, Rome.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps, Journal of Finance 62 383-410.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CSAFundingAbsoluteForward {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblUnderlyingVolatility = 0.3;
		double dblFundingSpreadVolatility = 0.015;
		double dblFundingSpreadMeanReversionRate = 0.05;
		double dblCSALIBOR = 0.018;

		double[] adblCorrelation = new double[] {
			-0.20,
			 0.00,
			 0.20,
			 0.40
		};

		int[] aiTenor = new int[] {
			 1,
			 2,
			 3,
			 4,
			 5,
			 7,
			10,
			15,
			20,
			25,
			30
		};

		DiffusionEvaluatorLogarithmic delUnderlying = DiffusionEvaluatorLogarithmic.Standard (
			0.,
			dblUnderlyingVolatility
		);

		DiffusionEvaluatorMeanReversion demrFundingSpread = DiffusionEvaluatorMeanReversion.Standard (
			dblFundingSpreadMeanReversionRate,
			0.,
			dblFundingSpreadVolatility
		);

		System.out.println();

		System.out.println ("\t||--------------------------------------------||");

		System.out.println ("\t||     DRIP CSA vs Non CSA Forward Rates      ||");

		System.out.println ("\t||--------------------------------------------||");

		String strHeader = "\t|| CORR => ";

		for (double dblCorrelation : adblCorrelation)
			strHeader = strHeader + "  " + FormatUtil.FormatDouble (dblCorrelation, 2, 0, 100.) + "%  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t||--------------------------------------------||");

		for (int iTenor : aiTenor) {
			String strDump = "\t|| " + FormatUtil.FormatDouble (iTenor, 2, 0, 1.) + "Y => ";

			for (double dblCorrelation : adblCorrelation) {
				FundingBasisEvolver sftf = new FundingBasisEvolver (
					delUnderlying,
					demrFundingSpread,
					dblCorrelation
				);

				strDump = strDump + " " + FormatUtil.FormatDouble (dblCSALIBOR * (sftf.CSANoCSARatio (iTenor + "Y") - 1.), 1, 2, 100.) + "% |";
			}

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||--------------------------------------------||");

		System.out.println();
	}
}
