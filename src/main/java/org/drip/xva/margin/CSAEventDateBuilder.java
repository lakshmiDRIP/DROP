
package org.drip.xva.margin;

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
 * CSAEventDateBuilder builds the CSA BCBS/IOSCO Dates prescribed Events Time-line occurring Margin Period.
 *  The References are:
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

public class CSAEventDateBuilder
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
	 * PED Communication Delay
	 */

	public static final int PED_COMMUNICATION_DELAY = 1;

	/**
	 * ED Communication Delay
	 */

	public static final int ED_COMMUNICATION_DELAY = 1;

	/**
	 * ETD Designation Delay
	 */

	public static final int ETD_DESIGNATION_DELAY = 1;

	/**
	 * ETD Call-out Delay
	 */

	public static final int ETD_CALL_OUT_DELAY = 7;

	/**
	 * Construct the CSA Valuation Event Date
	 * 
	 * @param date The Valuation Date
	 * 
	 * @return The CSA Valuation Event Date
	 */

	public static final CSAEventDate Valuation (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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
	 * Construct the Last Undisputed and Respected CSA Event Date
	 * 
	 * @param date The Last CSA Honored Date
	 * 
	 * @return The Last Undisputed and Respected CSA Event Date
	 */

	public static final org.drip.xva.margin.CSAEventDate LastHonored (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new org.drip.xva.margin.CSAEventDate (
				date,
				"Last Honored Date",
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
	 * Construct the Last Undisputed and Respected CSA Event Date from the CSA Valuation Date
	 * 
	 * @param valuationEventDate The CSA Valuation Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Last Undisputed and Respected CSA Event Date from the CSA Valuation Date
	 */

	public static final org.drip.xva.margin.CSAEventDate LastHonored (
		final org.drip.xva.margin.CSAEventDate valuationEventDate,
		final java.lang.String calendarSet)
	{
		return null == valuationEventDate ? null : LastHonored (
			valuationEventDate.date().addBusDays (
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

	public static final CSAEventDate CollateralTransferInitiation (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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
	 * @param lastHonoredEventDate The CSA Last Honored Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Regular Collateral Transfer Initiation CSA Event Date
	 */

	public static final CSAEventDate RegularCollateralTransferInitiation (
		final org.drip.xva.margin.CSAEventDate lastHonoredEventDate,
		final java.lang.String calendarSet)
	{
		return null == lastHonoredEventDate ? null : CollateralTransferInitiation (
			lastHonoredEventDate.date().addBusDays (
				1,
				calendarSet
			)
		);
	}

	/**
	 * Construct the Delayed Collateral Transfer Initiation CSA Event Date
	 * 
	 * @param lastHonoredEventDate The CSA Last Honored Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Delayed Collateral Transfer Initiation CSA Event Date
	 */

	public static final CSAEventDate DelayedCollateralTransferInitiation (
		final org.drip.xva.margin.CSAEventDate lastHonoredEventDate,
		final java.lang.String calendarSet)
	{
		return null == lastHonoredEventDate ? null : CollateralTransferInitiation (
			lastHonoredEventDate.date().addBusDays (
				1,
				calendarSet
			).addBusDays (
				1,
				calendarSet
			)
		);
	}

	/**
	 * Construct the First Non-Honored CSA Event Date
	 * 
	 * @param date The First CSA Non-Honored Date
	 * 
	 * @return The First Non-Honored CSA Event Date
	 */

	public static final CSAEventDate FirstNonHonored (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
				date,
				"First Non-Honored Date",
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
	 * Construct the Last Undisputed and Respected CSA Event Date from the CSA Valuation Date
	 * 
	 * @param valuationEventDate The CSA Valuation Event Date
	 * @param calendarSet The Calendar Set
	 * @param marginFrequency The Margin Frequency
	 * 
	 * @return The Last Undisputed and Respected CSA Event Date from the CSA Valuation Date
	 */

	public static final org.drip.xva.margin.CSAEventDate FirstNonHonored (
		final org.drip.xva.margin.CSAEventDate valuationEventDate,
		final java.lang.String calendarSet,
		final int marginFrequency)
	{
		return null == valuationEventDate ? null : FirstNonHonored (
			valuationEventDate.date().addBusDays (
				marginFrequency,
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

	public static final CSAEventDate PotentialEventOfDefault (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate PED (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate PEDCommunication (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate EventOfDefault (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate ED (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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
	 * @param pedEventDate The PED CSA Event Date
	 * @param calendarSet The Calendar Set
	 * @param curePeriod The Cure Period
	 * 
	 * @return The Cure Period Adjusted ED
	 */

	public static final CSAEventDate EventOfDefault (
		final org.drip.xva.margin.CSAEventDate pedEventDate,
		final java.lang.String calendarSet,
		final int curePeriod)
	{
		return null == pedEventDate ? null : EventOfDefault (
			pedEventDate.date().addBusDays (
				curePeriod,
				calendarSet
			)
		);
	}

	/**
	 * Construct the IMA 1992 Cure Period Adjusted ED
	 * 
	 * @param pedEventDate The PED CSA Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The IMA 1992 Cure Period Adjusted ED
	 */

	public static final CSAEventDate IMA1992ED (
		final org.drip.xva.margin.CSAEventDate pedEventDate,
		final java.lang.String calendarSet)
	{
		return null == pedEventDate ? null : EventOfDefault (
			pedEventDate.date().addBusDays (
				CURE_PERIOD_IMA_1992,
				calendarSet
			)
		);
	}

	/**
	 * Construct the IMA 2002 Cure Period Adjusted ED
	 * 
	 * @param pedEventDate The PED CSA Event Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The IMA 2002 Cure Period Adjusted ED
	 */

	public static final CSAEventDate IMA2002ED (
		final org.drip.xva.margin.CSAEventDate pedEventDate,
		final java.lang.String calendarSet)
	{
		return null == pedEventDate ? null : EventOfDefault (
			pedEventDate.date().addBusDays (
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

	public static final CSAEventDate EDCommunication (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate ETDDesignation (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate ETD (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	public static final CSAEventDate EarlyTerminationDate (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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
