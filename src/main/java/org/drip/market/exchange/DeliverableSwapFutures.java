
package org.drip.market.exchange;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>DeliverableSwapFutures</i> contains the details of the exchange-traded Deliverable Swap Futures
 * Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Exchange</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DeliverableSwapFutures {
	private java.lang.String _strTenor = "";
	private java.lang.String _strCurrency = "";
	private double _dblNominal = java.lang.Double.NaN;
	private double _dblRateIncrement = java.lang.Double.NaN;
	private org.drip.product.params.LastTradingDateSetting _ltds = null;

	/**
	 * DeliverableSwapFutures constructor
	 * 
	 * @param strCurrency Currency
	 * @param strTenor Tenor
	 * @param dblNominal Nominal
	 * @param dblRateIncrement Rate Increment
	 * @param ltds Late Trading Date Setting
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DeliverableSwapFutures (
		final java.lang.String strCurrency,
		final java.lang.String strTenor,
		final double dblNominal,
		final double dblRateIncrement,
		final org.drip.product.params.LastTradingDateSetting ltds)
		throws java.lang.Exception
	{
		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null == (_strTenor = strTenor)
			|| _strTenor.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid (_dblNominal = dblNominal) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblRateIncrement = dblRateIncrement))
			throw new java.lang.Exception ("DeliverableSwapFutures ctr: Invalid Inputs");

		_ltds = ltds;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Tenor
	 * 
	 * @return The Tenor
	 */

	public java.lang.String tenor()
	{
		return _strTenor;
	}

	/**
	 * Retrieve the Nominal
	 * 
	 * @return The Nominal
	 */

	public double nominal()
	{
		return _dblNominal;
	}

	/**
	 * Retrieve the Rate Increment
	 * 
	 * @return The Rate Increment
	 */

	public double rateIncrement()
	{
		return _dblRateIncrement;
	}

	/**
	 * Retrieve the Last Trading Date Setting
	 * 
	 * @return The Last Trading Date Setting
	 */

	public org.drip.product.params.LastTradingDateSetting ltds()
	{
		return _ltds;
	}

	/**
	 * Create an Instance of the Deliverable Swaps Futures
	 * 
	 * @param dtSpot Spot Date
	 * @param dblFixedCoupon Fixed Coupon
	 * 
	 * @return Instance of the Deliverable Swaps Futures
	 */

	public org.drip.product.rates.FixFloatComponent Create (
		final org.drip.analytics.date.JulianDate dtSpot,
		final double dblFixedCoupon)
	{
		org.drip.market.otc.FixedFloatSwapConvention ffConv =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdictionMaturity (_strCurrency,
				_strTenor);

		return null == ffConv ? null : ffConv.createFixFloatComponent (dtSpot, _strTenor, dblFixedCoupon, 0.,
			_dblNominal);
	}
}
