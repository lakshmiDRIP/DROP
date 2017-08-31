
package org.drip.sample.xva;

import org.drip.analytics.date.*;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.xva.hypothecation.*;
import org.drip.xva.set.*;

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
 * PortfolioCollateralEstimate illustrates the Estimation of the Collateral Amount on a Single Trade Collateral
 * 	Portfolio. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioCollateralEstimate {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 40;
		double dblTime = 10.;
		double dblPortfolioDrift = 0.0;
		double dblPortfolioVolatility = 0.15;
		double dblPortfolioValueStart = 0.;
		double dblBankThreshold = -0.1;
		double dblCounterPartyThreshold = 0.1;

		JulianDate dtSpot = DateUtil.Today();

		JulianDate dtStart = dtSpot;
		double dblTimeWidth = dblTime / iNumStep;
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		CollateralGroupSpecification cgs = CollateralGroupSpecification.FixedThreshold (
			"FIXEDTHRESHOLD",
			dblCounterPartyThreshold,
			dblBankThreshold
		);

		CounterPartyGroupSpecification cpgs = CounterPartyGroupSpecification.Standard ("CPGROUP");

		DiffusionEvolver dePortfolio = new DiffusionEvolver (
			DiffusionEvaluatorLinear.Standard (
				dblPortfolioDrift,
				dblPortfolioVolatility
			)
		);

		JumpDiffusionEdge[] aJDESwapRate = dePortfolio.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblPortfolioValueStart,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				SequenceGenerator.Gaussian (iNumStep)
			),
			dblTimeWidth
		);

		System.out.println();

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                       COLLATERAL AMOUNT ESTIMATION OUTPUT METRICS                                        ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                                                               ||");

		System.out.println ("\t||            - Forward Date                                                                                                ||");

		System.out.println ("\t||            - Forward Value                                                                                               ||");

		System.out.println ("\t||            - Bank Margin Date                                                                                            ||");

		System.out.println ("\t||            - Counter Party Margin Date                                                                                   ||");

		System.out.println ("\t||            - Bank Window Margin Value                                                                                    ||");

		System.out.println ("\t||            - Counter Party Window Margin Value                                                                           ||");

		System.out.println ("\t||            - Bank Collateral Threshold                                                                                   ||");

		System.out.println ("\t||            - Counter Party Collateral Threshold                                                                          ||");

		System.out.println ("\t||            - Bank Posting Requirement                                                                                    ||");

		System.out.println ("\t||            - Counter Party Posting Requirement                                                                           ||");

		System.out.println ("\t||            - Gross Posting Requirement                                                                                   ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < iNumStep; ++i) {
			JulianDate dtEnd = dtStart.addMonths (3);

			double dblPortfolioValueFinish = dblTimeWidth * (iNumStep - i) * aJDESwapRate[i].finish();

			CollateralAmountEstimator hae = new CollateralAmountEstimator (
				cgs,
				cpgs,
				new BrokenDateInterpolatorLinearT (
					dtStart.julian(),
					dtEnd.julian(),
					dblPortfolioValueStart,
					dblPortfolioValueFinish
				),
				Double.NaN
			);

			CollateralAmountEstimatorOutput haeo = hae.output (dtEnd);

			System.out.println (
				"\t|| " +
				dtEnd + " => " +
				FormatUtil.FormatDouble (dblPortfolioValueFinish, 1, 4, 1.) + " | " +
				haeo.bankMarginDate() + " | " +
				haeo.counterPartyMarginDate() + " | " +
				FormatUtil.FormatDouble (haeo.bankWindowMarginValue(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.counterPartyWindowMarginValue(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.bankCollateralThreshold(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.counterPartyCollateralThreshold(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.bankPostingRequirement(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.counterPartyPostingRequirement(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (haeo.postingRequirement(), 1, 4, 1.) + " ||"
			);

			dtStart = dtEnd;
			dblPortfolioValueStart = dblPortfolioValueFinish;
		}

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
