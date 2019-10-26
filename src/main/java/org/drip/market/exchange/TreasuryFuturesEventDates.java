
package org.drip.market.exchange;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesEventDates</i> contains the actually realized Event Dates related to a Treasury Futures
 * Contract.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Deliverable Swap, STIR, Treasury Futures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesEventDates {
	private org.drip.analytics.date.JulianDate _dtExpiry = null;
	private org.drip.analytics.date.JulianDate _dtLastTrading = null;
	private org.drip.analytics.date.JulianDate _dtFirstDelivery = null;
	private org.drip.analytics.date.JulianDate _dtFinalDelivery = null;
	private org.drip.analytics.date.JulianDate _dtDeliveryNotice = null;

	/**
	 * TreasuryFuturesEventDates Constructor
	 * 
	 * @param dtExpiry The Expiry Date
	 * @param dtFirstDelivery The First Delivery Date
	 * @param dtFinalDelivery The Final Delivery Date
	 * @param dtDeliveryNotice The Delivery Notice Date
	 * @param dtLastTrading The Last Trading Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public TreasuryFuturesEventDates (
		final org.drip.analytics.date.JulianDate dtExpiry,
		final org.drip.analytics.date.JulianDate dtFirstDelivery,
		final org.drip.analytics.date.JulianDate dtFinalDelivery,
		final org.drip.analytics.date.JulianDate dtDeliveryNotice,
		final org.drip.analytics.date.JulianDate dtLastTrading)
		throws java.lang.Exception
	{
		if (null == (_dtExpiry = dtExpiry) || null == (_dtFirstDelivery = dtFirstDelivery) || null ==
			(_dtFinalDelivery = dtFinalDelivery) || null == (_dtDeliveryNotice = dtDeliveryNotice) || null ==
				(_dtLastTrading = dtLastTrading))
			throw new java.lang.Exception ("TreasuryFuturesEventDates ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Expiry Date
	 * 
	 * @return The Expiry Date
	 */

	public org.drip.analytics.date.JulianDate expiry()
	{
		return _dtExpiry;
	}

	/**
	 * Retrieve the First Delivery Date
	 * 
	 * @return The First Delivery Date
	 */

	public org.drip.analytics.date.JulianDate firstDelivery()
	{
		return _dtFirstDelivery;
	}

	/**
	 * Retrieve the Final Delivery Date
	 * 
	 * @return The Final Delivery Date
	 */

	public org.drip.analytics.date.JulianDate finalDelivery()
	{
		return _dtFinalDelivery;
	}

	/**
	 * Retrieve the Delivery Notice Date
	 * 
	 * @return The Delivery Notice Date
	 */

	public org.drip.analytics.date.JulianDate deliveryNotice()
	{
		return _dtDeliveryNotice;
	}

	/**
	 * Retrieve the Last Trading Date
	 * 
	 * @return The Last Trading Date
	 */

	public org.drip.analytics.date.JulianDate lastTrading()
	{
		return _dtLastTrading;
	}

	@Override public java.lang.String toString()
	{
		return _dtExpiry + " | " + _dtFirstDelivery + "  | " + _dtFirstDelivery + " |  " + _dtDeliveryNotice
			+ "  | " + _dtLastTrading;
	}
}
