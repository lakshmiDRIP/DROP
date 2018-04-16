
package org.drip.xva.csatimeline;


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
 * EventDateBuilder builds the CSA BCBS/IOSCO Dates prescribed Events Time-line occurring Margin Period. The
 *  References are:
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

	public static final org.drip.xva.csatimeline.EventDate Honored (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new org.drip.xva.csatimeline.EventDate (
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

	public static final org.drip.xva.csatimeline.EventDate Honored (
		final org.drip.xva.csatimeline.EventDate valuation,
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
		final org.drip.xva.csatimeline.EventDate honored,
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
		final org.drip.xva.csatimeline.EventDate honored,
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

	public static final org.drip.xva.csatimeline.EventDate NonHonored (
		final org.drip.xva.csatimeline.EventDate valuation,
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
		final org.drip.xva.csatimeline.EventDate ped,
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
		final org.drip.xva.csatimeline.EventDate ped,
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
		final org.drip.xva.csatimeline.EventDate ped,
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
