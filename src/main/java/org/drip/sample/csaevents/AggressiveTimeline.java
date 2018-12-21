
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
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>AggressiveTimeline</i> describes CSA mandated Events Time-line occurring Margin Period, as enforced by
 * an "Aggressive" Dealer. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/csaevents/README.md">CSA Event Time-lines</a></li>
 *  </ul>
 * <br><br>
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
