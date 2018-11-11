
package org.drip.market.definition;

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
 * <i>FloaterIndex</i> contains the definitions of the floating rate indexes of different jurisdictions.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/definition">Definition</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

abstract public class FloaterIndex {
	private java.lang.String _strName = "";
	private java.lang.String _strFamily = "";
	private int _iAccrualCompoundingRule = -1;
	private java.lang.String _strCalendar = "";
	private java.lang.String _strCurrency = "";
	private java.lang.String _strDayCount = "";

	/**
	 * FloaterIndex Constructor
	 * 
	 * @param strName Index Name
	 * @param strFamily Index Family
	 * @param strCurrency Index Currency
	 * @param strDayCount Index Day Count
	 * @param strCalendar Index Holiday Calendar
	 * @param iAccrualCompoundingRule Accrual Compounding Rule
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FloaterIndex (
		final java.lang.String strName,
		final java.lang.String strFamily,
		final java.lang.String strCurrency,
		final java.lang.String strDayCount,
		final java.lang.String strCalendar,
		final int iAccrualCompoundingRule)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_strFamily = strFamily) ||
			_strFamily.isEmpty() || null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null ==
				(_strDayCount = strDayCount) || _strDayCount.isEmpty() ||
					!org.drip.analytics.support.CompositePeriodBuilder.ValidateCompoundingRule
						(_iAccrualCompoundingRule = iAccrualCompoundingRule))
			throw new java.lang.Exception ("FloaterIndex ctr: Invalid Inputs");

		_strCalendar = strCalendar;
	}

	/**
	 * Retrieve the Index Name
	 * 
	 * @return The Index Name
	 */

	public java.lang.String name()
	{
		return _strName;
	}

	/**
	 * Retrieve the Index Family
	 * 
	 * @return The Index Family
	 */

	public java.lang.String family()
	{
		return _strFamily;
	}

	/**
	 * Retrieve the Index Holiday Calendar
	 * 
	 * @return The Index Holiday Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}

	/**
	 * Retrieve the Index Currency
	 * 
	 * @return The Index Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Index Day Count Convention
	 * 
	 * @return The Index Day Count Convention
	 */

	public java.lang.String dayCount()
	{
		return _strDayCount;
	}

	/**
	 * Retrieve the Accrual Compounding Rule
	 * 
	 * @return The Accrual Compounding Rule
	 */

	public int accrualCompoundingRule()
	{
		return _iAccrualCompoundingRule;
	}

	/**
	 * Retrieve the Spot Lag DAP with Date Roll Previous
	 * 
	 * @return The Spot Lag DAP with Date Roll Previous
	 */

	public org.drip.analytics.daycount.DateAdjustParams spotLagDAPBackward()
	{
		return new org.drip.analytics.daycount.DateAdjustParams
			(org.drip.analytics.daycount.Convention.DATE_ROLL_PREVIOUS, spotLag(), _strCalendar);
	}

	/**
	 * Retrieve the Spot Lag DAP with Date Roll Following
	 * 
	 * @return The Spot Lag DAP with Date Roll Following
	 */

	public org.drip.analytics.daycount.DateAdjustParams spotLagDAPForward()
	{
		return new org.drip.analytics.daycount.DateAdjustParams
			(org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING, spotLag(), _strCalendar);
	}

	/**
	 * Retrieve the Index Spot Lag
	 * 
	 * @return The Index Spot Lag
	 */

	abstract public int spotLag();
}
