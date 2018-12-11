
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>DateProcessor</i> Sets Up and Executes a JSON Based In/Out Date Related Service.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/json">Serialized JSON Service</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DateProcessor {

	/**
	 * JSON Based in/out Date Holiday Check Thunker
	 * 
	 * @param jsonParameter JSON Date Request Parameters
	 * 
	 * @return JSON Date Holiday Check Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject IsHoliday (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"Date");

		if (null == dt) return null;

		boolean bIsHoliday = false;

		try {
			bIsHoliday = org.drip.analytics.daycount.Convention.IsHoliday (dt.julian(),
				org.drip.json.parser.Converter.StringEntry (jsonParameter, "Calendar"));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("IsHoliday", bIsHoliday);

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Date Adjustment Thunker
	 * 
	 * @param jsonParameter JSON Date Request Parameters
	 * 
	 * @return JSON Date Adjustment Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject AdjustBusinessDays (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"Date");

		if (null == dt) return null;

		java.lang.String strCalendar = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"Calendar");

		int iDaysToAdjust = 0;

		try {
			iDaysToAdjust = org.drip.json.parser.Converter.IntegerEntry (jsonParameter, "DaysToAdjust");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtOut = dt.addBusDays (iDaysToAdjust, strCalendar);

		if (null == dtOut) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("DateOut", dtOut.toString());

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Date Offset Thunker
	 * 
	 * @param jsonParameter JSON Date Request Parameters
	 * 
	 * @return JSON Date Offset Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject AddDays (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"Date");

		if (null == dt) return null;

		int iDaysToAdd = 0;

		try {
			iDaysToAdd = org.drip.json.parser.Converter.IntegerEntry (jsonParameter, "DaysToAdd");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtOut = dt.addDays (iDaysToAdd);

		if (null == dtOut) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("DateOut", dtOut.toString());

		return jsonResponse;
	}
}
