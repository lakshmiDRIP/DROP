
package org.drip.sample.csaevents;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.exposure.csatimeline.EventDate;
import org.drip.exposure.csatimeline.EventSequence;
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
 * AggressiveTimeline describes CSA mandated Events Time-line occurring Margin Period, as enforced by an
 * 	"Aggressive" Dealer. The References are:
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

public class AggressiveTimeline
{

	private static final void DumpCSAEvent (
		final EventDate csaEventDate)
		throws Exception
	{
		System.out.println (
			"\t| " +
			csaEventDate.date() + " => " +
			csaEventDate.aps2017Designation() + " | " +
			csaEventDate.bcbsDesignation()
		);
	}

	public static final void main (
		final String args[])
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate valuationDate = DateUtil.CreateFromYMD (
			2018,
			DateUtil.APRIL,
			16
		);

		String calendar = "USD";

		EventSequence csaTimeline = EventSequence.Aggressive (
			valuationDate,
			calendar
		);

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|               CSA IMA 2002 \"Aggressive\" Timeline                |");

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|  L -> R:                                                        |");

		System.out.println ("\t|                                                                 |");

		System.out.println ("\t|      - CSA Event Date                                           |");

		System.out.println ("\t|      - Andersen-Pykhtin-Sokol (2017) CSA Event Designation      |");

		System.out.println ("\t|      - BCBS-IOSCO-IMA CSA Event Designation                     |");

		System.out.println ("\t|-----------------------------------------------------------------|");

		DumpCSAEvent (csaTimeline.valuation());

		DumpCSAEvent (csaTimeline.honored());

		DumpCSAEvent (csaTimeline.collateralTransferInitiation());

		DumpCSAEvent (csaTimeline.nonHonored());

		DumpCSAEvent (csaTimeline.ped());

		DumpCSAEvent (csaTimeline.pedCommunication());

		DumpCSAEvent (csaTimeline.ed());

		DumpCSAEvent (csaTimeline.edCommunication());

		DumpCSAEvent (csaTimeline.etdDesignation());

		DumpCSAEvent (csaTimeline.etd());

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t| Margin Period Start => " + csaTimeline.marginPeriodStart());

		System.out.println ("\t| Margin Period End   => " + csaTimeline.marginPeriodEnd());

		System.out.println ("\t| Margin Duration     => " + csaTimeline.marginDuration() + " Calendar Days");

		System.out.println ("\t| Margin Frequency    => " + csaTimeline.marginFrequency() + " Business Days");

		System.out.println ("\t| PED Cure Period     => " + csaTimeline.curePeriod() + " Business Days");

		System.out.println ("\t|-----------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
