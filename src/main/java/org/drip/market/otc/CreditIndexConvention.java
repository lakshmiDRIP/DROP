
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
 * <i>CreditIndexConvention</i> contains the details of the Credit Index of an OTC Index CDS Contract.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditIndexConvention {
	private int _iFreq = -1;
	private int _iNumConstituent = -1;
	private java.lang.String _strTenor = "";
	private java.lang.String _strCurrency = "";
	private java.lang.String _strDayCount = "";
	private java.lang.String _strIndexType = "";
	private java.lang.String _strSeriesName = "";
	private java.lang.String _strIndexSubType = "";
	private double _dblFixedCoupon = java.lang.Double.NaN;
	private double _dblRecoveryRate = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtMaturity = null;
	private org.drip.analytics.date.JulianDate _dtEffective = null;

	/**
	 * CreditIndexConvention Constructor
	 * 
	 * @param strIndexType Index Type
	 * @param strIndexSubType Index Sub-Type
	 * @param strSeriesName Series Name
	 * @param strTenor Index Tenor
	 * @param strCurrency Index Currency
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param iFreq Coupon/Pay Frequency
	 * @param strDayCount Index Day Count
	 * @param dblFixedCoupon Index Fixed Coupon
	 * @param dblRecoveryRate Fixed Recovery Rate
	 * @param iNumConstituent Number of Constituents
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public CreditIndexConvention (
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
		throws java.lang.Exception
	{
		if (null == (_strIndexType = strIndexType) || _strIndexType.isEmpty() || null == (_strIndexSubType =
			strIndexSubType) || _strIndexType.isEmpty() || null == (_strSeriesName = strSeriesName) ||
				_strSeriesName.isEmpty() || null == (_strTenor = strTenor) || _strTenor.isEmpty() || null ==
					(_strCurrency = strCurrency) || _strCurrency.isEmpty() || null == (_dtEffective =
						dtEffective) || null == (_dtMaturity = dtMaturity) || 0 >= (_iFreq = iFreq) || null
							== (_strDayCount = strDayCount) || _strDayCount.isEmpty() ||
								!org.drip.numerical.common.NumberUtil.IsValid (_dblFixedCoupon = dblFixedCoupon)
									|| !org.drip.numerical.common.NumberUtil.IsValid (_dblRecoveryRate =
										dblRecoveryRate) || 0 >= (_iNumConstituent = iNumConstituent))
			throw new java.lang.Exception ("CreditIndexConvention Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Index Type
	 * 
	 * @return The Index Type
	 */

	public java.lang.String indexType()
	{
		return _strIndexType;
	}

	/**
	 * Retrieve the Index Sub-Type
	 * 
	 * @return The Index Sub-Type
	 */

	public java.lang.String indexSubType()
	{
		return _strIndexSubType;
	}

	/**
	 * Retrieve the Series Name
	 * 
	 * @return The Series Name
	 */

	public java.lang.String seriesName()
	{
		return _strSeriesName;
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
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Effective Date
	 * 
	 * @return The Effective Date
	 */

	public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return _dtEffective;
	}

	/**
	 * Retrieve the Maturity Date
	 * 
	 * @return The Maturity Date
	 */

	public org.drip.analytics.date.JulianDate maturityDate()
	{
		return _dtMaturity;
	}

	/**
	 * Retrieve the Coupon Frequency
	 * 
	 * @return The Coupon Frequency
	 */

	public int frequency()
	{
		return _iFreq;
	}

	/**
	 * Retrieve the Day Count
	 * 
	 * @return The Day Count
	 */

	public java.lang.String dayCount()
	{
		return _strDayCount;
	}

	/**
	 * Retrieve the Fixed Coupon
	 * 
	 * @return The Fixed Coupon
	 */

	public double fixedCoupon()
	{
		return _dblFixedCoupon;
	}

	/**
	 * Retrieve the Recovery Rate
	 * 
	 * @return The Recovery Rate
	 */

	public double recoveryRate()
	{
		return _dblRecoveryRate;
	}

	/**
	 * Retrieve the Number of Constituents
	 * 
	 * @return The Number of Constituents
	 */

	public int numberOfConstituents()
	{
		return _iNumConstituent;
	}

	/**
	 * Retrieve the Full Name of the Credit Index
	 * 
	 * @return The Full Name of the Credit Index
	 */

	public java.lang.String fullName()
	{
		return _strIndexType + "." + _strIndexSubType + "." + _strSeriesName + "." + _strTenor;
	}

	/**
	 * Create an Instance of the Specified Index CDS Product
	 * 
	 * @return Instance of the Specified Index CDS Product
	 */

	public org.drip.product.definition.CreditDefaultSwap indexCDS()
	{
		org.drip.product.params.CreditSetting cs = new org.drip.product.params.CreditSetting (30,
			_dblRecoveryRate, false, _strIndexType + "." + _strIndexSubType + "." + _strSeriesName, true);

		return cs.validate() ? org.drip.product.creator.CDSBuilder.CreateCDS (_dtEffective, _dtMaturity,
			_dblFixedCoupon, _strCurrency, cs, _strCurrency, true) : null;
	}
}
