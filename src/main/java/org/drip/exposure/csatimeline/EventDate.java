
package org.drip.exposure.csatimeline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>EventDate</i> holds a specific Date composing BCBS/IOSCO prescribed Events Time-line occurring Margin
 * Period. The References are:
 *  
 *  <br>
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
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline">CSA Timeline</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Exposure">Exposure Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EventDate
{
	private java.lang.String _bcbsDesignation = "";
	private java.lang.String _aps2017Designation = "";
	private org.drip.analytics.date.JulianDate _date = null;

	/**
	 * EventDate Constructor
	 * 
	 * @param date The CSA Event Julian Date
	 * @param bcbsDesignation The BCBS IOSCO CSA Event Designation
	 * @param aps2017Designation The Andersen Pykhtin Sokol (2017) CSA Event Designation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EventDate (
		final org.drip.analytics.date.JulianDate date,
		final java.lang.String bcbsDesignation,
		final java.lang.String aps2017Designation)
		throws java.lang.Exception
	{
		if (null == (_date = date))
		{
			throw new java.lang.Exception ("EventDate Constructor => Invalid Inputs");
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
