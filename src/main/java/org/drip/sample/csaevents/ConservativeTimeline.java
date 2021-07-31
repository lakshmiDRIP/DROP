
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
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>ConservativeTimeline</i> describes CSA mandated Events Time-line occurring Margin Period, as enforced
 * by a "Conservative" Dealer. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives
 *  			https://www.bis.org/bcbs/publ/d317.pdf
 *  	</li>
 *  	<li>
 *  		Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of
 *  			Credit Risk</i> <b>5 (4)</b> 3-27
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/csaevents/README.md">Time-line of IMA/CSA Event Sequences</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ConservativeTimeline
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

		EventSequence csaTimeline = EventSequence.Conservative (
			valuationDate,
			calendar
		);

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|              CSA IMA 2002 \"Conservative\" Timeline               |");

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
