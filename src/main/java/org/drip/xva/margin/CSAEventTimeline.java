
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
 * CSAEventTimeline holds the BCBS/IOSCO prescribed Events Time-line occurring Margin Period. The References
 *  are:
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

public class CSAEventTimeline
{
	private int _curePeriod = -1;
	private int _marginFrequency = -1;
	private java.lang.String _calendarSet = "";
	private org.drip.xva.margin.CSAEventDate _ed = null;
	private org.drip.xva.margin.CSAEventDate _etd = null;
	private org.drip.xva.margin.CSAEventDate _ped = null;
	private org.drip.xva.margin.CSAEventDate _valuation = null;
	private org.drip.xva.margin.CSAEventDate _lastHonored = null;
	private org.drip.xva.margin.CSAEventDate _etdDesignation = null;
	private org.drip.xva.margin.CSAEventDate _firstNonHonored = null;
	private org.drip.xva.margin.CSAEventDate _edCommunication = null;
	private org.drip.xva.margin.CSAEventDate _pedCommunication = null;
	private org.drip.xva.margin.CSAEventDate _collateralTransferInitiation = null;

	/**
	 * Construct a Standard Instance of CSAEventTimeline
	 * 
	 * @param valuationDate The Valuation Date
	 * @param pedLag PED Lag
	 * @param calendarSet The Calendar Set
	 * 
	 * @return The Standard Instance of CSAEventTimeline
	 */

	public static final CSAEventTimeline Standard (
		final org.drip.analytics.date.JulianDate valuationDate,
		final int pedLag,
		final java.lang.String calendarSet)
	{
		if (null == valuationDate)
		{
			return null;
		}

		org.drip.xva.margin.CSAEventDate valuation = org.drip.xva.margin.CSAEventDateBuilder.Valuation
			(valuationDate);

		if (null == valuation)
		{
			return null;
		}

		org.drip.xva.margin.CSAEventDate lastHonored = org.drip.xva.margin.CSAEventDateBuilder.LastHonored (
			valuation,
			calendarSet
		);

		if (null == lastHonored)
		{
			return null;
		}

		org.drip.xva.margin.CSAEventDate firstNonHonored =
			org.drip.xva.margin.CSAEventDateBuilder.FirstNonHonored (
				valuation,
				calendarSet,
				org.drip.xva.margin.CSAEventDateBuilder.MARGIN_FREQUENCY_DAILY
			);

		if (null == firstNonHonored)
		{
			return null;
		}

		org.drip.xva.margin.CSAEventDate ped = org.drip.xva.margin.CSAEventDateBuilder.PED (
			firstNonHonored.date().addBusDays (
				pedLag,
				calendarSet
			)
		);

		if (null == ped)
		{
			return null;
		}

		org.drip.xva.margin.CSAEventDate ed = org.drip.xva.margin.CSAEventDateBuilder.IMA2002ED (
			ped,
			calendarSet
		);

		if (null == ed)
		{
			return null;
		}

		org.drip.xva.margin.CSAEventDate etdDesignation =
			org.drip.xva.margin.CSAEventDateBuilder.ETDDesignation (
				ped.date().addBusDays (
					org.drip.xva.margin.CSAEventDateBuilder.ETD_DESIGNATION_DELAY,
					calendarSet
				)
			);

		if (null == etdDesignation)
		{
			return null;
		}

		try
		{
			return new CSAEventTimeline (
				valuation,
				lastHonored,
				org.drip.xva.margin.CSAEventDateBuilder.RegularCollateralTransferInitiation (
					lastHonored,
					calendarSet
				),
				firstNonHonored,
				ped,
				org.drip.xva.margin.CSAEventDateBuilder.PEDCommunication (
					ped.date().addBusDays (
						org.drip.xva.margin.CSAEventDateBuilder.PED_COMMUNICATION_DELAY,
						calendarSet
					)
				),
				ed,
				org.drip.xva.margin.CSAEventDateBuilder.EDCommunication (
					ed.date().addBusDays (
						org.drip.xva.margin.CSAEventDateBuilder.ED_COMMUNICATION_DELAY,
						calendarSet
					)
				),
				etdDesignation,
				org.drip.xva.margin.CSAEventDateBuilder.ETDDesignation (
					etdDesignation.date().addBusDays (
						org.drip.xva.margin.CSAEventDateBuilder.ETD_CALL_OUT_DELAY,
						calendarSet
					)
				),
				org.drip.xva.margin.CSAEventDateBuilder.MARGIN_FREQUENCY_DAILY,
				org.drip.xva.margin.CSAEventDateBuilder.CURE_PERIOD_IMA_2002,
				calendarSet
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param valuation The CSA Valuation Event Date
	 * @param lastHonored The CSA Last Honored Event Date
	 * @param collateralTransferInitiation The CSA Collateral Transfer Initiation Event Date
	 * @param firstNonHonored The CSA First Non-Honored Event Date
	 * @param ped The Potential Event of Default
	 * @param pedCommunication The Potential Event of Default Communication Date
	 * @param ed The Event of Default
	 * @param edCommunication The Event of Default Communication Date
	 * @param etdDesignation The Early Termination Designation Date
	 * @param etd The Early Termination Date
	 * @param marginFrequency The CSA Margin Frequency
	 * @param curePeriod The Client "Failure To Pay" Cure Period
	 * @param calendarSet The CSA Calendar Set
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CSAEventTimeline (
		final org.drip.xva.margin.CSAEventDate valuation,
		final org.drip.xva.margin.CSAEventDate lastHonored,
		final org.drip.xva.margin.CSAEventDate collateralTransferInitiation,
		final org.drip.xva.margin.CSAEventDate firstNonHonored,
		final org.drip.xva.margin.CSAEventDate ped,
		final org.drip.xva.margin.CSAEventDate pedCommunication,
		final org.drip.xva.margin.CSAEventDate ed,
		final org.drip.xva.margin.CSAEventDate edCommunication,
		final org.drip.xva.margin.CSAEventDate etdDesignation,
		final org.drip.xva.margin.CSAEventDate etd,
		final int marginFrequency,
		final int curePeriod,
		final java.lang.String calendarSet)
		throws java.lang.Exception
	{
		if (null == (_valuation = valuation) ||
			null == (_lastHonored = lastHonored) ||
			null == (_collateralTransferInitiation = collateralTransferInitiation) ||
			null == (_firstNonHonored = firstNonHonored) ||
			null == (_ped = ped) ||
			null == (_pedCommunication = pedCommunication) ||
			null == (_ed = ed) ||
			null == (_edCommunication = edCommunication) ||
			null == (_etdDesignation = etdDesignation) ||
			null == (_etd = etd) ||
			0 >= (_marginFrequency = marginFrequency) ||
			0 >= (_curePeriod = curePeriod))
		{
			throw new java.lang.Exception ("CSAEventTimeline Constructor => Invalid Inputs");
		}

		_calendarSet = calendarSet;
	}

	/**
	 * Retrieve the CSA Calendar Set
	 * 
	 * @return The CSA Calendar Set
	 */

	public java.lang.String calendarSet()
	{
		return _calendarSet;
	}

	/**
	 * Retrieve the CSA Margin Frequency
	 * 
	 * @return The CSA Margin Frequency
	 */

	public int marginFrequency()
	{
		return _marginFrequency;
	}

	/**
	 * Retrieve the Client Cure Period
	 * 
	 * @return The Client Cure Period
	 */

	public int curePeriod()
	{
		return _curePeriod;
	}

	/**
	 * Retrieve the Valuation Event Date
	 * 
	 * @return The Valuation Event Date
	 */

	public org.drip.xva.margin.CSAEventDate valuation()
	{
		return _valuation;
	}

	/**
	 * Retrieve the Last Honored Event Date
	 * 
	 * @return The Last Honored Event Date
	 */

	public org.drip.xva.margin.CSAEventDate lastHonored()
	{
		return _lastHonored;
	}

	/**
	 * Retrieve the Collateral Transfer Initiation Event Date
	 * 
	 * @return The Collateral Transfer Initiation Event Date
	 */

	public org.drip.xva.margin.CSAEventDate collateralTransferInitiation()
	{
		return _collateralTransferInitiation;
	}

	/**
	 * Retrieve the First Non Honored Event Date
	 * 
	 * @return The First Non Honored Event Date
	 */

	public org.drip.xva.margin.CSAEventDate firstNonHonored()
	{
		return _firstNonHonored;
	}

	/**
	 * Retrieve the PED Event Date
	 * 
	 * @return The PED Event Date
	 */

	public org.drip.xva.margin.CSAEventDate ped()
	{
		return _ped;
	}

	/**
	 * Retrieve the PED Communication Event Date
	 * 
	 * @return The PED Communication Event Date
	 */

	public org.drip.xva.margin.CSAEventDate pedCommunication()
	{
		return _pedCommunication;
	}

	/**
	 * Retrieve the ED Event Date
	 * 
	 * @return The ED Event Date
	 */

	public org.drip.xva.margin.CSAEventDate ed()
	{
		return _ed;
	}

	/**
	 * Retrieve the ED Communication Event Date
	 * 
	 * @return The ED Communication Event Date
	 */

	public org.drip.xva.margin.CSAEventDate edCommunication()
	{
		return _edCommunication;
	}

	/**
	 * Retrieve the ETD Designation Event Date
	 * 
	 * @return The ETD Designation Event Date
	 */

	public org.drip.xva.margin.CSAEventDate etdDesignation()
	{
		return _etdDesignation;
	}

	/**
	 * Retrieve the ETD Event Date
	 * 
	 * @return The ETD Event Date
	 */

	public org.drip.xva.margin.CSAEventDate etd()
	{
		return _etd;
	}
}
