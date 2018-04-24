
package org.drip.sample.csaevents;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.exposure.csatimeline.AndersenPykhtinSokolLag;
import org.drip.exposure.csatimeline.LastFlowDates;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * AndersenPykhtinSokolDates generates the Intra-Period Dates inside a Margin. Flow Dates are generated for
 * 	the Classical+, Classical-, "Aggressive", and "Conservative" Timeline Schemes. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing Framework
 *  	for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives,
 *  	https://www.bis.org/bcbs/publ/d317.pdf.
 *  
 *  - Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties, Journal of Credit
 *  	Risk, 5 (4) 3-27.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolDates
{

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate spot = DateUtil.CreateFromYMD (
			2018,
			DateUtil.APRIL,
			16
		);

		String calendar = "USD";

		LastFlowDates csaFlowDatesClassicalPlus = LastFlowDates.SpotStandard (
			spot,
			AndersenPykhtinSokolLag.ClassicalPlus(),
			calendar
		);

		LastFlowDates csaFlowDatesClassicalMinus = LastFlowDates.SpotStandard (
			spot,
			AndersenPykhtinSokolLag.ClassicalMinus(),
			calendar
		);

		LastFlowDates csaFlowDatesAggressive = LastFlowDates.SpotStandard (
			spot,
			AndersenPykhtinSokolLag.Aggressive(),
			calendar
		);

		LastFlowDates csaFlowDatesConservative = LastFlowDates.SpotStandard (
			spot,
			AndersenPykhtinSokolLag.Conservative(),
			calendar
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|      INTRA-MARGIN CSA EVENT DATE GENERATION USING ANDERSEN PYKHTIN SOKOL PARAMETRIZATION       ||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                                                                                                ||"
		);

		System.out.println (
			"\t|    L -> R:                                                                                     ||"
		);

		System.out.println (
			"\t|                                                                                                ||"
		);

		System.out.println (
			"\t|        - CSA Event Date                                                                        ||"
		);

		System.out.println (
			"\t|        - CSA Event Type                                                                        ||"
		);

		System.out.println (
			"\t|        - Classical- Scheme                                                                     ||"
		);

		System.out.println (
			"\t|        - Classical+ Scheme                                                                     ||"
		);

		System.out.println (
			"\t|        - Aggressive Scheme                                                                     ||"
		);

		System.out.println (
			"\t|        - Conservative Scheme                                                                   ||"
		);

		System.out.println (
			"\t|                                                                                                ||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t| Margin Valuation Date   => SETTLEMENT  | " +
			csaFlowDatesClassicalPlus.valuation() + " | " +
			csaFlowDatesClassicalMinus.valuation() + " | " +
			csaFlowDatesAggressive.valuation() + " | " +
			csaFlowDatesConservative.valuation() + " ||"
		);

		System.out.println (
			"\t| Client Margin Flow Date => OBSERVATION | " +
			csaFlowDatesClassicalPlus.clientVariationMargin() + " | " +
			csaFlowDatesClassicalMinus.clientVariationMargin() + " | " +
			csaFlowDatesAggressive.clientVariationMargin() + " | " +
			csaFlowDatesConservative.clientVariationMargin() + " ||"
		);

		System.out.println (
			"\t| Dealer Margin Flow Date => OBSERVATION | " +
			csaFlowDatesClassicalPlus.dealerVariationMargin() + " | " +
			csaFlowDatesClassicalMinus.dealerVariationMargin() + " | " +
			csaFlowDatesAggressive.dealerVariationMargin() + " | " +
			csaFlowDatesConservative.dealerVariationMargin() + " ||"
		);

		System.out.println (
			"\t| Client Trade Flow Date  => SETTLEMENT  | " +
			csaFlowDatesClassicalPlus.clientTrade() + " | " +
			csaFlowDatesClassicalMinus.clientTrade() + " | " +
			csaFlowDatesAggressive.clientTrade() + " | " +
			csaFlowDatesConservative.clientTrade() + " ||"
		);

		System.out.println (
			"\t| Dealer Trade Flow Date  => SETTLEMENT  | " +
			csaFlowDatesClassicalPlus.dealerTrade() + " | " +
			csaFlowDatesClassicalMinus.dealerTrade() + " | " +
			csaFlowDatesAggressive.dealerTrade() + " | " +
			csaFlowDatesConservative.dealerTrade() + " ||"
		);

		System.out.println (
			"\t| Early Termination Date  => OBSERVATION | " +
			csaFlowDatesClassicalPlus.spot() + " | " +
			csaFlowDatesClassicalMinus.spot() + " | " +
			csaFlowDatesAggressive.spot() + " | " +
			csaFlowDatesConservative.spot() + " ||"
		);

		System.out.println (
			"\t| Spot Date               => OBSERVATION | " +
			csaFlowDatesClassicalPlus.spot() + " | " +
			csaFlowDatesClassicalMinus.spot() + " | " +
			csaFlowDatesAggressive.spot() + " | " +
			csaFlowDatesConservative.spot() + " ||"
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------------------||"
		);

		EnvManager.TerminateEnv();
	}
}
