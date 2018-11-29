
package org.drip.param.quoting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>YieldInterpreter</i> holds the fields needed to interpret a Yield Quote. It contains the quote day
 * count, quote frequency, quote EOM Adjustment, quote Act/Act parameters, and quote Calendar.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quoting">Quoting</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class YieldInterpreter extends org.drip.param.quoting.MeasureInterpreter {

	/*
	 * Quote Day Count
	 */

	private java.lang.String _strDC = "";

	/*
	 * Quote Frequency
	 */

	private int _iFreq = 0;

	/*
	 * Quote Apply EOM Adjustment?
	 */

	private boolean _bApplyEOMAdj = false;

	/*
	 * Quote Act Act DC Params
	 */

	private org.drip.analytics.daycount.ActActDCParams _aap = null;

	/*
	 * Quote Calendar
	 */

	private java.lang.String _strCalendar = "";

	/**
	 * Construct YieldInterpreter from the Day Count and the Frequency parameters
	 * 
	 * @param strDC Quoting Day Count
	 * @param iFreq Quoting Frequency
	 * @param bApplyEOMAdj TRUE - Apply the EOM Adjustment
	 * @param aap The ActActDCParams Instance
	 * @param strCalendar The Calendar
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public YieldInterpreter (
		final java.lang.String strDC,
		final int iFreq,
		final boolean bApplyEOMAdj,
		final org.drip.analytics.daycount.ActActDCParams aap,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		if (null == (_strDC = strDC) || _strDC.isEmpty() || 0 == (_iFreq = iFreq))
			throw new java.lang.Exception ("YieldInterpreter ctr: Invalid quoting params!");

		_aap = aap;
		_strCalendar = strCalendar;
		_bApplyEOMAdj = bApplyEOMAdj;
	}

	/**
	 * Retrieve the Day Count Convention
	 * 
	 * @return The Day Count Convention
	 */

	public java.lang.String dayCount()
	{
		return _strDC;
	}

	/**
	 * Retrieve the Frequency
	 * 
	 * @return The Frequency
	 */

	public int freq()
	{
		return _iFreq;
	}

	/**
	 * Retrieve the EOM Adjustment
	 * 
	 * @return The EOM Adjustment
	 */

	public boolean eomAdj()
	{
		return _bApplyEOMAdj;
	}

	/**
	 * Retrieve the Act/Act Day Count Parameters
	 * 
	 * @return The Act/Act Day Count Parameters
	 */

	public org.drip.analytics.daycount.ActActDCParams aap()
	{
		return _aap;
	}

	/**
	 * Retrieve the Calendar
	 * 
	 * @return The Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}
}
