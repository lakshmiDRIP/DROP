
package org.drip.xva.margin;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * CSAEventDate holds a specific Date composing BCBS/IOSCO prescribed Events Time-line occurring Margin
 *  Period. The References are:
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

public class CSAEventDate
{
	private java.lang.String _bcbsDesignation = "";
	private java.lang.String _aps2017Designation = "";
	private org.drip.analytics.date.JulianDate _date = null;

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

	public static final CSAEventDate LastHonored (
		final org.drip.analytics.date.JulianDate date)
	{
		try
		{
			return new CSAEventDate (
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

	/**
	 * CSAEventDate Constructor
	 * 
	 * @param date The CSA Event Julian Date
	 * @param bcbsDesignation The BCBS IOSCO CSA Event Designation
	 * @param aps2017Designation The Andersen Pykhtin Sokol (2017) CSA Event Designation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CSAEventDate (
		final org.drip.analytics.date.JulianDate date,
		final java.lang.String bcbsDesignation,
		final java.lang.String aps2017Designation)
		throws java.lang.Exception
	{
		if (null == (_date = date))
		{
			throw new java.lang.Exception ("CSAEventDate Constructor => Invalid Inputs");
		}

		_bcbsDesignation = bcbsDesignation;
		_aps2017Designation = aps2017Designation;
	}

	/**
	 * Retrieve the CSA Event Julian Date
	 * 
	 * @return The CSA Event Julian Date
	 */

	public org.drip.analytics.date.JulianDate date()
	{
		return _date;
	}

	/**
	 * Retrieve the BCBS IOSCO CSA Event Designation
	 * 
	 * @return The BCBS IOSCO CSA Event Designation
	 */

	public java.lang.String bcbsDesignation()
	{
		return _bcbsDesignation;
	}

	/**
	 * Retrieve the Andersen Pykhtin Sokol (2017) CSA Event Designation
	 * 
	 * @return The Andersen Pykhtin Sokol (2017) CSA Event Designation
	 */

	public java.lang.String aps2017Designation()
	{
		return _aps2017Designation;
	}
}
