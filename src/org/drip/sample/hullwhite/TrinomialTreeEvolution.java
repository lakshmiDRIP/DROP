
package org.drip.sample.hullwhite;

import org.drip.analytics.date.*;
import org.drip.dynamics.hullwhite.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.FundingLabel;

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
 * TrinomialTreeEvolution demonstrates the Construction and Usage of the Hull-White Trinomial Tree and the
 *  Eventual Evolution of the Short Rate on it.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TrinomialTreeEvolution {

	private static final SingleFactorStateEvolver HullWhiteEvolver (
		final String strCurrency,
		final double dblSigma,
		final double dblA,
		final double dblStartingForwardRate)
		throws Exception
	{
		return new SingleFactorStateEvolver (
			FundingLabel.Standard (strCurrency),
			dblSigma,
			dblA,
			new FlatUnivariate (dblStartingForwardRate),
			new BoxMullerGaussian (
				0.,
				1.
			)
		);
	}

	private static final void DumpMetrics (
		final TrinomialTreeTransitionMetrics hwtm)
		throws Exception
	{
		System.out.println ("\t| [" + new JulianDate (hwtm.initialDate()) + " -> " +
			new JulianDate (hwtm.terminalDate()) + "] => " +
			FormatUtil.FormatDouble (hwtm.expectedTerminalX(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (Math.sqrt (hwtm.xVariance()), 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (hwtm.xStochasticShift(), 1, 4, 1.) + " || " +
			FormatUtil.FormatDouble (hwtm.probabilityUp(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (hwtm.upNodeMetrics().x(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (hwtm.upNodeMetrics().shortRate(), 1, 2, 100.) + "% || " +
			FormatUtil.FormatDouble (hwtm.probabilityDown(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (hwtm.downNodeMetrics().x(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (hwtm.downNodeMetrics().shortRate(), 1, 2, 100.) + "% || " +
			FormatUtil.FormatDouble (hwtm.probabilityStay(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (hwtm.stayNodeMetrics().x(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (hwtm.stayNodeMetrics().shortRate(), 1, 2, 100.) + "% ||"
		);
	}

	private static final void TreeHeader (
		final String strEvolutionComment)
		throws Exception
	{
		System.out.println ("\n\n\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                                                                    ||");

		System.out.println (strEvolutionComment);

		System.out.println ("\t|    ---------------------------------------------------                                                                                             ||");

		System.out.println ("\t|                                                                                                                                                    ||");

		System.out.println ("\t|    L->R:                                                                                                                                           ||");

		System.out.println ("\t|                                                                                                                                                    ||");

		System.out.println ("\t|        Initial Date                                                                                                                                ||");

		System.out.println ("\t|        Final Date                                                                                                                                  ||");

		System.out.println ("\t|        Expected Final X                                                                                                                            ||");

		System.out.println ("\t|        X Volatility (%)                                                                                                                            ||");

		System.out.println ("\t|        X Stochastic Shift                                                                                                                          ||");

		System.out.println ("\t|        Move-Up Probability                                                                                                                         ||");

		System.out.println ("\t|        Move-Up X Node Value                                                                                                                        ||");

		System.out.println ("\t|        Move-Up Short Rate                                                                                                                          ||");

		System.out.println ("\t|        Move-Down Probability                                                                                                                       ||");

		System.out.println ("\t|        Move-Down X Node Value                                                                                                                      ||");

		System.out.println ("\t|        Move-Down Short Rate                                                                                                                        ||");

		System.out.println ("\t|        Stay Probability                                                                                                                            ||");

		System.out.println ("\t|        Stay X Node Value                                                                                                                           ||");

		System.out.println ("\t|        Stay Short Rate                                                                                                                             ||");

		System.out.println ("\t|                                                                                                                                                    ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		String strCurrency = "USD";
		double dblStartingShortRate = 0.05;
		double dblSigma = 0.01;
		double dblA = 1.;

		SingleFactorStateEvolver hw = HullWhiteEvolver (
			strCurrency,
			dblSigma,
			dblA,
			dblStartingShortRate
		);

		int iSpotDate = dtSpot.julian();

		int iFinalDate = dtSpot.addMonths (6).julian();

		int iInitialDate = iSpotDate;
		TrinomialTreeTransitionMetrics hwtm = null;

		TreeHeader ("\t|    Hull-White Trinomial Tree Upwards Evolution Metrics                                                                                             ||");

		while (iInitialDate < iFinalDate) {
			DumpMetrics (hwtm =
				hw.evolveTrinomialTree (
					iSpotDate,
					iInitialDate,
					iFinalDate,
					null == hwtm ? null : hwtm.upNodeMetrics()
				)
			);

			iInitialDate += 10;
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");

		hwtm = null;
		iInitialDate = iSpotDate;

		TreeHeader ("\t|    Hull-White Trinomial Tree Downwards Evolution Metrics                                                                                           ||");

		while (iInitialDate < iFinalDate) {
			DumpMetrics (hwtm =
				hw.evolveTrinomialTree (
					iSpotDate,
					iInitialDate,
					iFinalDate,
					null == hwtm ? null : hwtm.downNodeMetrics()
				)
			);

			iInitialDate += 10;
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");

		hwtm = null;
		iInitialDate = iSpotDate;

		TreeHeader ("\t|    Hull-White Trinomial Tree Stay-Put Evolution Metrics                                                                                            ||");

		while (iInitialDate < iFinalDate) {
			DumpMetrics (hwtm =
				hw.evolveTrinomialTree (
					iSpotDate,
					iInitialDate,
					iFinalDate,
					null == hwtm ? null : hwtm.stayNodeMetrics()
				)
			);

			iInitialDate += 10;
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");
	}
}
