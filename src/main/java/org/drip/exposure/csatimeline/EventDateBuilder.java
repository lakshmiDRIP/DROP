
package org.drip.exposure.csatimeline;

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
 * <i>EventDateBuilder</i> builds the CSA BCBS/IOSCO Dates prescribed Events Time-line occurring Margin
 * Period. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing
 *  				Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives
 *  				https://www.bis.org/bcbs/publ/d317.pdf
 *  		</li>
 *  		<li>
 *  			Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of
 *  				Credit Risk</i> <b>5 (4)</b> 3-27
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/README.md">Time-line of IMA/CSA Event Dates</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EventDateBuilder
{

	/**
	 * 1992 ISDA IMA Cure Period of 3 Business Days
	 */

	public static final int CURE_PERIOD_IMA_1992 = 3;

	/**
	 * 2002 ISDA IMA Cure Period of 1 Business Day
	 */

	public static final int CURE_PERIOD_IMA_2002 = 1;

	/**
	 * Daily Margining Frequency
	 */

	public static final int MARGIN_FREQUENCY_DAILY = 1;

	/**
	 * PED Call Out Delay - Aggressive
	 */

	public static final int PED_CALL_OUT_DELAY_AGGRESSIVE = 1;

	/**
	 * PED Call Out Delay - Conservative
	 */

	public static final int PED_CALL_OUT_DELAY_CONSERVATIVE = 3;

	/**
	 * PED Communication Delay - Aggressive
	 */

	public static final int PED_COMMUNICATION_DELAY_AGGRESSIVE = 1;

	/**
	 * PED Communication Delay - Conservative
	 */

	public static final int PED_COMMUNICATION_DELAY_CONSERVATIVE = 2;

	/**
	 * ED Communication Delay - Aggressive
	 */

	public static final int ED_COMMUNICATION_DELAY_AGGRESSIVE = 1;

	/**
	 * ED Communication Delay - Conservative
	 */

	public static final int ED_COMMUNICATION_DELAY_CONSERVATIVE = 2;

	/**
	 * ETD Designation Delay - Aggressive
	 */

	public static final int ETD_DESIGNATION_DELAY_AGGRESSIVE = 1;

	/**
	 * ETD Designation Delay - Conservative
	 */

	public static final int ETD_DESIGNATION_DELAY_CONSERVATIVE = 3;

	/**
	 * ETD Call-out Delay - Aggressive
	 */

	public static final int ETD_CALL_OUT_DELAY_AGGRESSIVE = 1;

	/**
	 * ETD Call-out Delay - Conservative
	 */

	public static final int ETD_CALL_OUT_DELAY_CONSERVATIVE = 12;

	/**
	 * Construct the CSA Valuation Event Date
	 * 
	 * @param date The Valuation Date
	 * 
	 * @return The CSA Valuation Event Date
	 */

	public static final EventDate Valuation (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Valuation Date",
				"T0"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Undisputed and Respected CSA Event Date
	 * 
	 * @param date The CSA Honored Date
	 * 
	 * @return The Undisputed and Respected CSA Event Date
	 */

	public static final org.drip.exposure.csatimeline.EventDate Honored (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new org.drip.exposure.csatimeline.EventDate (
				date,
				"Honored Date",
				"T1"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Undisputed and Respected CSA Event Date from the CSA Valuation Date
	 * 
	 * @param valuation The CSA Valuation Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Undisputed and Respected CSA Event Date from the CSA Valuation Date
	 */

	public static final org.drip.exposure.csatimeline.EventDate Honored (
		final org.drip.exposure.csatimeline.EventDate valuation,
		final java.lang.String calendarSet)
	{
		return null == valuation ? null : Honored (
			valuation.date().addBusDays (
				1,
				calendarSet
			)
		);
	}

	/**
	 * Construct the Collateral Transfer Initiation CSA Event Date
	 * 
	 * @param date The Collateral Transfer Initiation Date
	 * 
	 * @return The Collateral Transfer Initiation CSA Event Date
	 */

	public static final EventDate CollateralTransferInitiation (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Collateral Transfer Initiation Date",
				"T2"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Regular Collateral Transfer Initiation CSA Event Date
	 * 
	 * @param honored The CSA Last Honored Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Regular Collateral Transfer Initiation CSA Event Date
	 */

	public static final EventDate RegularCollateralTransferInitiation (
		final org.drip.exposure.csatimeline.EventDate honored,
		final java.lang.String calendarSet)
	{
		return null == honored ? null : CollateralTransferInitiation (
			honored.date().addBusDays (
				1,
				calendarSet
			)
		);
	}

	/**
	 * Construct the Delayed Collateral Transfer Initiation CSA Event Date
	 * 
	 * @param honored The CSA Last Honored Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Delayed Collateral Transfer Initiation CSA Event Date
	 */

	public static final EventDate DelayedCollateralTransferInitiation (
		final org.drip.exposure.csatimeline.EventDate honored,
		final java.lang.String calendarSet)
	{
		return null == honored ? null : CollateralTransferInitiation (
			honored.date().addBusDays (
				2,
				calendarSet
			)
		);
	}

	/**
	 * Construct the Non-Honored CSA Event Date
	 * 
	 * @param date The CSA Non-Honored Date
	 * 
	 * @return The Non-Honored CSA Event Date
	 */

	public static final EventDate NonHonored (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Non-Honored Date",
				"T3"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Non-Honored CSA Event Date
	 * 
	 * @param valuation The CSA Valuation Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Non-Honored CSA Event Date
	 */

	public static final org.drip.exposure.csatimeline.EventDate NonHonored (
		final org.drip.exposure.csatimeline.EventDate valuation,
		final java.lang.String calendarSet)
	{
		return null == valuation ? null : NonHonored (
			valuation.date().addBusDays (
				1,
				calendarSet
			)
		);
	}

	/**
	 * Construct the Potential Event of Default CSA Event Date
	 * 
	 * @param date The Potential Event of Default Date
	 * 
	 * @return The Potential Event of Default CSA Event Date
	 */

	public static final EventDate PotentialEventOfDefault (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Potential Event of Default Date",
				"Tau"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Potential Event of Default CSA Event Date
	 * 
	 * @param date The Potential Event of Default Date
	 * 
	 * @return The Potential Event of Default CSA Event Date
	 */

	public static final EventDate PED (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Potential Event of Default Date",
				"Tau"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the PED Communication CSA Event Date
	 * 
	 * @param date The PED Communication Date
	 * 
	 * @return The PED Communication CSA Event Date
	 */

	public static final EventDate PEDCommunication (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"PED Communication Date",
				"T4"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Event of Default CSA Event Date
	 * 
	 * @param date The Event of Default Date
	 * 
	 * @return The Event of Default CSA Event Date
	 */

	public static final EventDate EventOfDefault (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Event of Default Date",
				"T5"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Event of Default CSA Event Date
	 * 
	 * @param date The Event of Default Date
	 * 
	 * @return The Event of Default CSA Event Date
	 */

	public static final EventDate ED (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Event of Default Date",
				"T5"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Cure Period Adjusted ED
	 * 
	 * @param ped The PED CSA Event Date
	 * @param calendarSet The Calendar Set
	 * @param curePeriod The Cure Period
	 * 
	 * @return The Cure Period Adjusted ED
	 */

	public static final EventDate EventOfDefault (
		final org.drip.exposure.csatimeline.EventDate ped,
		final java.lang.String calendarSet,
		final int curePeriod)
	{
		return null == ped ? null : EventOfDefault (
			ped.date().addBusDays (
				curePeriod,
				calendarSet
			)
		);
	}

	/**
	 * Construct the IMA 1992 Cure Period Adjusted ED
	 * 
	 * @param ped The PED CSA Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The IMA 1992 Cure Period Adjusted ED
	 */

	public static final EventDate IMA1992ED (
		final org.drip.exposure.csatimeline.EventDate ped,
		final java.lang.String calendarSet)
	{
		return null == ped ? null : EventOfDefault (
			ped.date().addBusDays (
				CURE_PERIOD_IMA_1992,
				calendarSet
			)
		);
	}

	/**
	 * Construct the IMA 2002 Cure Period Adjusted ED
	 * 
	 * @param ped The PED CSA Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The IMA 2002 Cure Period Adjusted ED
	 */

	public static final EventDate IMA2002ED (
		final org.drip.exposure.csatimeline.EventDate ped,
		final java.lang.String calendarSet)
	{
		return null == ped ? null : EventOfDefault (
			ped.date().addBusDays (
				CURE_PERIOD_IMA_2002,
				calendarSet
			)
		);
	}

	/**
	 * Construct the ED Communication CSA Event Date
	 * 
	 * @param date The ED Communication Date
	 * 
	 * @return The ED Communication CSA Event Date
	 */

	public static final EventDate EDCommunication (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"ED Communication Date",
				"T6"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ETD Designation CSA Event Date
	 * 
	 * @param date The ETD Designation Date
	 * 
	 * @return The ETD Designation CSA Event Date
	 */

	public static final EventDate ETDDesignation (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"ETD Designation Date",
				"T7"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Early Termination Date (ETD) CSA Event Date
	 * 
	 * @param date The Early Termination Date (ETD)
	 * 
	 * @return The Early Termination Date (ETD) CSA Event Date
	 */

	public static final EventDate ETD (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Early Termination Date",
				"T8"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Early Termination Date (ETD) CSA Event Date
	 * 
	 * @param date The Early Termination Date (ETD)
	 * 
	 * @return The Early Termination Date (ETD) CSA Event Date
	 */

	public static final EventDate EarlyTerminationDate (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new EventDate (
				date,
				"Early Termination Date",
				"T8"
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
