
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>CreditIndexConventionContainer</i> contains the Conventions of the Credit Index of an OTC Index CDS
 * Contract.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC Dual Stream Option Container</a></li>
 *  </ul>
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
