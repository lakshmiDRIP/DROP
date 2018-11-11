
package org.drip.market.otc;

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
 * <i>CreditIndexConventionContainer</i> contains the Conventions of the Credit Index of an OTC Index CDS
 * Contract.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">Over-the-Counter</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditIndexConventionContainer {
	private static java.util.Map<java.lang.String, org.drip.market.otc.CreditIndexConvention>
		_mapIndexConvention = null;

	private static final boolean AddIndexConvention (
		final java.lang.String strIndexType,
		final java.lang.String strIndexSubType,
		final java.lang.String strSeriesName,
		final java.lang.String strTenor,
		final java.lang.String strCurrency,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFreq,
		final java.lang.String strDayCount,
		final double dblFixedCoupon,
		final double dblRecoveryRate,
		final int iNumConstituent)
	{
		try {
			org.drip.market.otc.CreditIndexConvention cic = new org.drip.market.otc.CreditIndexConvention
				(strIndexType, strIndexSubType, strSeriesName, strTenor, strCurrency, dtEffective,
					dtMaturity, iFreq, strDayCount, dblFixedCoupon, dblRecoveryRate, iNumConstituent);

			_mapIndexConvention.put (cic.fullName(), cic);

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static final boolean AddCDXNAIG5YSeries (
		final java.lang.String strSeriesName,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity)
	{
		return AddIndexConvention ("CDX.NA", "IG", strSeriesName, "5Y", "USD", dtEffective, dtMaturity, 4,
			"Act/360", 0.01, 0.4, 125);
	}

	/**
	 * Initialize the Credit Index Conventions Container with the pre-set CDX Contract Settings
	 * 
	 * @return TRUE - The Credit Index Conventions Container successfully initialized
	 */

	public static final boolean Init()
	{
		if (null != _mapIndexConvention) return true;

		_mapIndexConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.CreditIndexConvention>();

		if (!AddCDXNAIG5YSeries ("S15", org.drip.analytics.date.DateUtil.CreateFromYMD (2010,
			org.drip.analytics.date.DateUtil.SEPTEMBER, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2015, org.drip.analytics.date.DateUtil.DECEMBER, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S16", org.drip.analytics.date.DateUtil.CreateFromYMD (2011,
			org.drip.analytics.date.DateUtil.MARCH, 21), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2016, org.drip.analytics.date.DateUtil.JUNE, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S17", org.drip.analytics.date.DateUtil.CreateFromYMD (2011,
			org.drip.analytics.date.DateUtil.SEPTEMBER, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2016, org.drip.analytics.date.DateUtil.DECEMBER, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S18", org.drip.analytics.date.DateUtil.CreateFromYMD (2012,
			org.drip.analytics.date.DateUtil.MARCH, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2017, org.drip.analytics.date.DateUtil.JUNE, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S19", org.drip.analytics.date.DateUtil.CreateFromYMD (2012,
			org.drip.analytics.date.DateUtil.SEPTEMBER, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2017, org.drip.analytics.date.DateUtil.DECEMBER, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S20", org.drip.analytics.date.DateUtil.CreateFromYMD (2013,
			org.drip.analytics.date.DateUtil.MARCH, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2018, org.drip.analytics.date.DateUtil.JUNE, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S21", org.drip.analytics.date.DateUtil.CreateFromYMD (2013,
			org.drip.analytics.date.DateUtil.SEPTEMBER, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2018, org.drip.analytics.date.DateUtil.DECEMBER, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S22", org.drip.analytics.date.DateUtil.CreateFromYMD (2014,
			org.drip.analytics.date.DateUtil.MARCH, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2019, org.drip.analytics.date.DateUtil.JUNE, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S23", org.drip.analytics.date.DateUtil.CreateFromYMD (2014,
			org.drip.analytics.date.DateUtil.SEPTEMBER, 22), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2019, org.drip.analytics.date.DateUtil.DECEMBER, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S24", org.drip.analytics.date.DateUtil.CreateFromYMD (2015,
			org.drip.analytics.date.DateUtil.MARCH, 20), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2020, org.drip.analytics.date.DateUtil.JUNE, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S25", org.drip.analytics.date.DateUtil.CreateFromYMD (2015,
			org.drip.analytics.date.DateUtil.SEPTEMBER, 21), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2020, org.drip.analytics.date.DateUtil.DECEMBER, 20)))
			return false;

		if (!AddCDXNAIG5YSeries ("S26", org.drip.analytics.date.DateUtil.CreateFromYMD (2016,
			org.drip.analytics.date.DateUtil.MARCH, 21), org.drip.analytics.date.DateUtil.CreateFromYMD
				(2021, org.drip.analytics.date.DateUtil.JUNE, 20)))
			return false;

		return true;
	}

	/**
	 * Retrieve the OTC Credit Index Convention Instance from the Full Index Name
	 * 
	 * @param strCreditIndexFullName The Credit index Full Name
	 * 
	 * @return The OTC Credit Index Convention Instance
	 */

	public static final org.drip.market.otc.CreditIndexConvention ConventionFromFullName (
		final java.lang.String strCreditIndexFullName)
	{
		return null == strCreditIndexFullName || !_mapIndexConvention.containsKey (strCreditIndexFullName) ?
			null : _mapIndexConvention.get (strCreditIndexFullName);
	}
}
