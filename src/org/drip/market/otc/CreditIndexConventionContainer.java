
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * CreditIndexConventionContainer contains the Conventions of the Credit Index of an OTC Index CDS Contract.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditIndexConventionContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.otc.CreditIndexConvention>
		_mapIndexConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.CreditIndexConvention>();

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
