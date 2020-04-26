
package org.drip.sample.service;

import org.drip.analytics.date.*;
import org.drip.json.parser.Converter;
import org.drip.json.simple.*;
import org.drip.service.env.EnvManager;
import org.drip.service.json.KeyHoleSkeleton;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>DateManipulationClient</i> demonstrates the Invocation and Examination of the JSON-based Date
 * 	Manipulation Service Client.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/service/README.md">Curve Product Portfolio Valuation Services</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DateManipulationClient {

	@SuppressWarnings ("unchecked") static String IsHoliday (
		final JulianDate dt,
		final String strCalendar)
	{
		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put ("Date", dt.toString());

		jsonParameters.put ("Calendar", strCalendar);

		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put ("API", "DATE::ISHOLIDAY");

		jsonRequest.put ("Parameters", jsonParameters);

		return KeyHoleSkeleton.Thunker (jsonRequest.toJSONString());
	}

	@SuppressWarnings ("unchecked") static String Adjust (
		final JulianDate dt,
		final String strCalendar,
		final int iNumDaysToAdjust)
	{
		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put ("Date", dt.toString());

		jsonParameters.put ("Calendar", strCalendar);

		jsonParameters.put ("DaysToAdjust", iNumDaysToAdjust);

		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put ("API", "DATE::ADJUSTBUSINESSDAYS");

		jsonRequest.put ("Parameters", jsonParameters);

		return KeyHoleSkeleton.Thunker (jsonRequest.toJSONString());
	}

	@SuppressWarnings ("unchecked") static String Add (
		final JulianDate dt,
		final int iNumDaysToAdd)
	{
		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put ("Date", dt.toString());

		jsonParameters.put ("DaysToAdd", iNumDaysToAdd);

		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put ("API", "DATE::ADDDAYS");

		jsonRequest.put ("Parameters", jsonParameters);

		return KeyHoleSkeleton.Thunker (jsonRequest.toJSONString());
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2016,
			DateUtil.MARCH,
			27
		);

		int iNumDays = 10;
		String strCalendar = "MXN";

		System.out.println ("\n\t|-----------------------------------------|");

		for (int i = 0; i < iNumDays; ++i) {
			JSONObject jsonResponse = (JSONObject) JSONValue.parse (
				Adjust (
					dtSpot,
					strCalendar,
					i
				)
			);

			System.out.println (
				"\t| Adjusted[" + dtSpot + " + " + i + "] = " +
				Converter.DateEntry (
					jsonResponse,
					"DateOut"
				) + " |"
			);
		}

		System.out.println ("\t|-----------------------------------------|");

		System.out.println ("\n\n\t|-------------------------------------------|");

		for (int i = 0; i < iNumDays; ++i) {
			JSONObject jsonResponse = (JSONObject) JSONValue.parse (
				Add (
					dtSpot,
					i
				)
			);

			System.out.println (
				"\t| Unadjusted[" + dtSpot + " + " + i + "] = " +
				Converter.DateEntry (
					jsonResponse,
					"DateOut"
				) + " |"
			);
		}

		System.out.println ("\t|-------------------------------------------|");

		System.out.println ("\n\n\t|---------------------------------|");

		for (int i = 0; i < iNumDays; ++i) {
			JulianDate dt = dtSpot.addDays (i);

			JSONObject jsonResponse = (JSONObject) JSONValue.parse (
				IsHoliday (
					dt,
					strCalendar
				)
			);

			System.out.println (
				"\t| Is " + dt + " a Holiday? " +
				Converter.BooleanEntry (
					jsonResponse,
					"IsHoliday"
				) + " |"
			);
		}

		System.out.println ("\t|---------------------------------|");

		EnvManager.TerminateEnv();
	}
}
