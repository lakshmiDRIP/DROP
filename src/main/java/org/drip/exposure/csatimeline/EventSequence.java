
package org.drip.exposure.csatimeline;

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
 * <i>EventSequence</i> holds the BCBS/IOSCO prescribed Events Time-line occurring Margin Period. The
 * References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/README.md">CSA Time Line</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EventSequence
{
	private int _curePeriod = -1;
	private int _marginFrequency = -1;
	private java.lang.String _calendarSet = "";
	private org.drip.exposure.csatimeline.EventDate _ed = null;
	private org.drip.exposure.csatimeline.EventDate _etd = null;
	private org.drip.exposure.csatimeline.EventDate _ped = null;
	private org.drip.exposure.csatimeline.EventDate _honored = null;
	private org.drip.exposure.csatimeline.EventDate _valuation = null;
	private org.drip.exposure.csatimeline.EventDate _nonHonored = null;
	private org.drip.exposure.csatimeline.EventDate _etdDesignation = null;
	private org.drip.exposure.csatimeline.EventDate _edCommunication = null;
	private org.drip.exposure.csatimeline.EventDate _pedCommunication = null;
	private org.drip.exposure.csatimeline.EventDate _collateralTransferInitiation = null;

	/**
	 * Construct an Instance of Aggressive EventSequence
	 * 
	 * @param valuationDate The Valuation Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return Instance of Aggressive EventSequence
	 */

	public static final EventSequence Aggressive (
		final org.drip.analytics.date.JulianDate valuationDate,
		final java.lang.String calendarSet)
	{
		if (null == valuationDate)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate valuation =
			org.drip.exposure.csatimeline.EventDateBuilder.Valuation (valuationDate);

		if (null == valuation)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate honored =
			org.drip.exposure.csatimeline.EventDateBuilder.Honored (
				valuation,
				calendarSet
			);

		if (null == honored)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate nonHonored =
			org.drip.exposure.csatimeline.EventDateBuilder.NonHonored (
				org.drip.exposure.csatimeline.EventDateBuilder.Valuation (
					valuation.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.MARGIN_FREQUENCY_DAILY,
						calendarSet
					)
				),
				calendarSet
			);

		if (null == nonHonored)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate ped = org.drip.exposure.csatimeline.EventDateBuilder.PED (
			nonHonored.date().addBusDays (
				org.drip.exposure.csatimeline.EventDateBuilder.PED_CALL_OUT_DELAY_AGGRESSIVE,
				calendarSet
			)
		);

		if (null == ped)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate ed =
			org.drip.exposure.csatimeline.EventDateBuilder.IMA2002ED (
				ped,
				calendarSet
			);

		if (null == ed)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate etdDesignation =
			org.drip.exposure.csatimeline.EventDateBuilder.ETDDesignation (
				ped.date().addBusDays (
					org.drip.exposure.csatimeline.EventDateBuilder.ETD_DESIGNATION_DELAY_AGGRESSIVE,
					calendarSet
				)
			);

		if (null == etdDesignation)
		{
			return null;
		}

		try
		{
			return new EventSequence (
				valuation,
				honored,
				org.drip.exposure.csatimeline.EventDateBuilder.RegularCollateralTransferInitiation (
					honored,
					calendarSet
				),
				nonHonored,
				ped,
				org.drip.exposure.csatimeline.EventDateBuilder.PEDCommunication (
					ped.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.PED_COMMUNICATION_DELAY_AGGRESSIVE,
						calendarSet
					)
				),
				ed,
				org.drip.exposure.csatimeline.EventDateBuilder.EDCommunication (
					ed.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.ED_COMMUNICATION_DELAY_AGGRESSIVE,
						calendarSet
					)
				),
				etdDesignation,
				org.drip.exposure.csatimeline.EventDateBuilder.ETD (
					etdDesignation.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.ETD_CALL_OUT_DELAY_AGGRESSIVE,
						calendarSet
					)
				),
				org.drip.exposure.csatimeline.EventDateBuilder.MARGIN_FREQUENCY_DAILY,
				org.drip.exposure.csatimeline.EventDateBuilder.CURE_PERIOD_IMA_2002,
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
	 * Construct an Instance of Conservative EventSequence
	 * 
	 * @param valuationDate The Valuation Date
	 * @param calendarSet The Calendar Set
	 * 
	 * @return Instance of Conservative EventSequence
	 */

	public static final EventSequence Conservative (
		final org.drip.analytics.date.JulianDate valuationDate,
		final java.lang.String calendarSet)
	{
		if (null == valuationDate)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate valuation =
			org.drip.exposure.csatimeline.EventDateBuilder.Valuation (valuationDate);

		if (null == valuation)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate honored =
			org.drip.exposure.csatimeline.EventDateBuilder.Honored (
				valuation,
				calendarSet
			);

		if (null == honored)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate nonHonored =
			org.drip.exposure.csatimeline.EventDateBuilder.NonHonored (
				org.drip.exposure.csatimeline.EventDateBuilder.Valuation (
					valuation.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.MARGIN_FREQUENCY_DAILY,
						calendarSet
					)
				),
				calendarSet
			);

		if (null == nonHonored)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate ped = org.drip.exposure.csatimeline.EventDateBuilder.PED (
			nonHonored.date().addBusDays (
				org.drip.exposure.csatimeline.EventDateBuilder.PED_CALL_OUT_DELAY_CONSERVATIVE,
				calendarSet
			)
		);

		if (null == ped)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate ed =
			org.drip.exposure.csatimeline.EventDateBuilder.IMA2002ED (
				ped,
				calendarSet
			);

		if (null == ed)
		{
			return null;
		}

		org.drip.exposure.csatimeline.EventDate etdDesignation =
			org.drip.exposure.csatimeline.EventDateBuilder.ETDDesignation (
				ped.date().addBusDays (
					org.drip.exposure.csatimeline.EventDateBuilder.ETD_DESIGNATION_DELAY_CONSERVATIVE,
					calendarSet
				)
			);

		if (null == etdDesignation)
		{
			return null;
		}

		try
		{
			return new EventSequence (
				valuation,
				honored,
				org.drip.exposure.csatimeline.EventDateBuilder.RegularCollateralTransferInitiation (
					honored,
					calendarSet
				),
				nonHonored,
				ped,
				org.drip.exposure.csatimeline.EventDateBuilder.PEDCommunication (
					ped.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.PED_COMMUNICATION_DELAY_CONSERVATIVE,
						calendarSet
					)
				),
				ed,
				org.drip.exposure.csatimeline.EventDateBuilder.EDCommunication (
					ed.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.ED_COMMUNICATION_DELAY_CONSERVATIVE,
						calendarSet
					)
				),
				etdDesignation,
				org.drip.exposure.csatimeline.EventDateBuilder.ETD (
					etdDesignation.date().addBusDays (
						org.drip.exposure.csatimeline.EventDateBuilder.ETD_CALL_OUT_DELAY_CONSERVATIVE,
						calendarSet
					)
				),
				org.drip.exposure.csatimeline.EventDateBuilder.MARGIN_FREQUENCY_DAILY,
				org.drip.exposure.csatimeline.EventDateBuilder.CURE_PERIOD_IMA_2002,
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
	 * @param honored The CSA Honored Event Date
	 * @param collateralTransferInitiation The CSA Collateral Transfer Initiation Event Date
	 * @param nonHonored The CSA Non-Honored Event Date
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

	public EventSequence (
		final org.drip.exposure.csatimeline.EventDate valuation,
		final org.drip.exposure.csatimeline.EventDate honored,
		final org.drip.exposure.csatimeline.EventDate collateralTransferInitiation,
		final org.drip.exposure.csatimeline.EventDate nonHonored,
		final org.drip.exposure.csatimeline.EventDate ped,
		final org.drip.exposure.csatimeline.EventDate pedCommunication,
		final org.drip.exposure.csatimeline.EventDate ed,
		final org.drip.exposure.csatimeline.EventDate edCommunication,
		final org.drip.exposure.csatimeline.EventDate etdDesignation,
		final org.drip.exposure.csatimeline.EventDate etd,
		final int marginFrequency,
		final int curePeriod,
		final java.lang.String calendarSet)
		throws java.lang.Exception
	{
		if (null == (_valuation = valuation) ||
			null == (_honored = honored) ||
			null == (_collateralTransferInitiation = collateralTransferInitiation) ||
			null == (_nonHonored = nonHonored) ||
			null == (_ped = ped) ||
			null == (_pedCommunication = pedCommunication) ||
			null == (_ed = ed) ||
			null == (_edCommunication = edCommunication) ||
			null == (_etdDesignation = etdDesignation) ||
			null == (_etd = etd) ||
			0 >= (_marginFrequency = marginFrequency) ||
			0 >= (_curePeriod = curePeriod))
		{
			throw new java.lang.Exception ("EventSequence Constructor => Invalid Inputs");
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

	public org.drip.exposure.csatimeline.EventDate valuation()
	{
		return _valuation;
	}

	/**
	 * Retrieve the Honored Event Date
	 * 
	 * @return The Honored Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate honored()
	{
		return _honored;
	}

	/**
	 * Retrieve the Collateral Transfer Initiation Event Date
	 * 
	 * @return The Collateral Transfer Initiation Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate collateralTransferInitiation()
	{
		return _collateralTransferInitiation;
	}

	/**
	 * Retrieve the Non Honored Event Date
	 * 
	 * @return The Non Honored Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate nonHonored()
	{
		return _nonHonored;
	}

	/**
	 * Retrieve the PED Event Date
	 * 
	 * @return The PED Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate ped()
	{
		return _ped;
	}

	/**
	 * Retrieve the PED Communication Event Date
	 * 
	 * @return The PED Communication Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate pedCommunication()
	{
		return _pedCommunication;
	}

	/**
	 * Retrieve the ED Event Date
	 * 
	 * @return The ED Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate ed()
	{
		return _ed;
	}

	/**
	 * Retrieve the ED Communication Event Date
	 * 
	 * @return The ED Communication Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate edCommunication()
	{
		return _edCommunication;
	}

	/**
	 * Retrieve the ETD Designation Event Date
	 * 
	 * @return The ETD Designation Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate etdDesignation()
	{
		return _etdDesignation;
	}

	/**
	 * Retrieve the ETD Event Date
	 * 
	 * @return The ETD Event Date
	 */

	public org.drip.exposure.csatimeline.EventDate etd()
	{
		return _etd;
	}

	/**
	 * Retrieve the Margin Period Start Date
	 * 
	 * @return The Margin Period Start Date
	 */

	public org.drip.analytics.date.JulianDate marginPeriodStart()
	{
		return _honored.date();
	}

	/**
	 * Retrieve the Margin Period End Date
	 * 
	 * @return The Margin Period End Date
	 */

	public org.drip.analytics.date.JulianDate marginPeriodEnd()
	{
		return _etd.date();
	}

	/**
	 * Retrieve the Margin Duration
	 * 
	 * @return The Margin Duration
	 */

	public int marginDuration()
	{
		return _etd.date().julian() - _honored.date().julian();
	}
}
