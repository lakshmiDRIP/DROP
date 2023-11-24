
package org.drip.oms.specification;

import java.time.LocalTime;
import java.time.Period;
import java.time.ZonedDateTime;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>TimeInForce</i> holds the Setting for Time-in-Force (TIF) Parameters. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Berkowitz, S. A., D. E. Logue, and E. A. J. Noser (1988): The Total Cost of Transactions on the
 * 				NYSE <i>Journal of Finance</i> <b>43 (1)</b> 97-112
 * 		</li>
 * 		<li>
 * 			Chen, J. (2021): Time in Force: Definition, Types, and Examples
 * 				https://www.investopedia.com/terms/t/timeinforce.asp
 * 		</li>
 * 		<li>
 * 			Cont, R., and A. Kukanov (2017): Optimal Order Placement in Limit Order Markets <i>Quantitative
 * 				Finance</i> <b>17 (1)</b> 21-39
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005b): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
 * 				(6)</b> 576-593
 * 		</li>
 * 		<li>
 * 			Weiss, D. (2006): <i>After the Trade is Made: Processing Securities Transactions</i> <b>Portfolio
 * 				Publishing</b> London UK
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/README.md">R<sup>d</sup> Order Specification, Handling, and Management</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/specification/README.md">Order Specification and Session Metrics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TimeInForce
{

	/**
	 * TIF Type DAY
	 */

	public static final int TIF_DAY = 1;

	/**
	 * TIF Type EXTENDED
	 */

	public static final int TIF_EXTENDED = 2;

	/**
	 * TIF Type IMMEDIATE
	 */

	public static final int TIF_IMMEDIATE = 3;

	/**
	 * TIF Type ON MARKET OPEN
	 */

	public static final int TIF_ON_MARKET_OPEN = 4;

	/**
	 * TIF Type ON MARKET CLOSE
	 */

	public static final int TIF_ON_MARKET_CLOSE = 5;

	private String _code = "";
	private int _tifType = Integer.MIN_VALUE;
	private int _durationDays = Integer.MIN_VALUE;
	private ZonedDateTime _setupZonedDateTime = null;

	/**
	 * Create an Immediate Version of TIF
	 * 
	 * @return Immediate Version of TIF
	 */

	public static final TimeInForce CreateImmediate()
	{
		try
		{
			return new TimeInForce (
				"IOC",
				TIF_IMMEDIATE,
				ZonedDateTime.now(),
				0
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an DAY Version of TIF
	 * 
	 * @return DAY Version of TIF
	 */

	public static final TimeInForce CreateDay()
	{
		try
		{
			return new TimeInForce (
				"DAY",
				TIF_DAY,
				ZonedDateTime.now(),
				0
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * TimeInForce Constructor
	 * 
	 * @param code TIF Code
	 * @param tifType TIF Type
	 * @param setupZonedDateTime Setup Instant
	 * @param durationDays Duration Tenor in Days
	 * 
	 * @throws Exception Thrown if TimeInForce cannot be constructed
	 */

	public TimeInForce (
		final String code,
		final int tifType,
		final ZonedDateTime setupZonedDateTime,
		final int durationDays)
		throws Exception
	{
		if (null == (_setupZonedDateTime = setupZonedDateTime))
		{
			throw new Exception (
				"TimeInForce Constructor => Invalid Inputs"
			);
		}

		_code = code;
		_tifType = tifType;
		_durationDays = durationDays;
	}

	/**
	 * Retrieve the TIF Code
	 * 
	 * @return The TIF Code
	 */

	public String code()
	{
		return _code;
	}

	/**
	 * Retrieve the TIF Type
	 * 
	 * @return The TIF Type
	 */

	public int tifType()
	{
		return _tifType;
	}

	/**
	 * Retrieve the Setup Instant
	 * 
	 * @return The Setup Instant
	 */

	public ZonedDateTime setupZonedDateTime()
	{
		return _setupZonedDateTime;
	}

	/**
	 * Retrieve the Duration Tenor in Days
	 * 
	 * @return The Duration Tenor in Days
	 */

	public int durationDays()
	{
		return _durationDays;
	}

	/**
	 * Indicate if the Input Time represents a TIF Lapse
	 * 
	 * @param currentZonedDateTime Input Time
	 * 
	 * @return TRUE - Input Time represents a TIF Lapse
	 */

	public boolean hasLapsed (
		final ZonedDateTime currentZonedDateTime)
	{
		if (null == currentZonedDateTime ||
			currentZonedDateTime.isBefore (
				_setupZonedDateTime
			)
		)
		{
			return false;
		}

		if (TIF_IMMEDIATE == _tifType)
		{
			return currentZonedDateTime.isAfter (
				_setupZonedDateTime
			);
		}

		if (TIF_DAY == _tifType)
		{
			return currentZonedDateTime.getDayOfWeek() != _setupZonedDateTime.getDayOfWeek() &&
				currentZonedDateTime.getMonth() != _setupZonedDateTime.getMonth() &&
				currentZonedDateTime.getYear() != _setupZonedDateTime.getYear();
		}

		if (TIF_EXTENDED == _tifType)
		{
			return Period.between (
				_setupZonedDateTime.toLocalDate(),
				currentZonedDateTime.toLocalDate()
			).getDays() > _durationDays;
		}

		if (TIF_ON_MARKET_OPEN == _tifType)
		{
			return currentZonedDateTime.isAfter (
				ZonedDateTime.of (
					_setupZonedDateTime.toLocalDate(),
					LocalTime.of (
						9,
						30,
						0
					),
					currentZonedDateTime.getZone()
				)
			);
		}

		if (TIF_ON_MARKET_CLOSE == _tifType)
		{
			return currentZonedDateTime.isAfter (
				ZonedDateTime.of (
					_setupZonedDateTime.toLocalDate(),
					LocalTime.of (
						16,
						0,
						0
					),
					currentZonedDateTime.getZone()
				)
			);
		}

		return false;
	}
}
