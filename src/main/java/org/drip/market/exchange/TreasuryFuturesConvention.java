
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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesConvention</i> contains the Details for the Futures Basket of the Exchange-Traded
 * Treasury Futures Contracts.
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

public class TreasuryFuturesConvention {
	private java.lang.String _strName = "";
	private java.lang.String _strCalendar = "";
	private java.lang.String _strCurrency = "";
	private java.lang.String[] _astrCode = null;
	private java.lang.String[] _astrExchange = null;
	private java.lang.String _strMaturityTenor = "";
	private java.lang.String _strUnderlierType = "";
	private java.lang.String _strUnderlierSubtype = "";
	private double _dblBasketNotional = java.lang.Double.NaN;
	private double _dblMinimumPriceMovement = java.lang.Double.NaN;
	private org.drip.market.exchange.TreasuryFuturesSettle _bfs = null;
	private org.drip.analytics.eventday.DateInMonth _dimExpiry = null;
	private double _dblComponentNotionalMinimum = java.lang.Double.NaN;
	private org.drip.market.exchange.TreasuryFuturesEligibility _bfe = null;

	/**
	 * TreasuryFuturesConvention Constructor
	 * 
	 * @param strName The Futures Name
	 * @param astrCode The Array of the Futures Codes
	 * @param strCurrency The Futures Currency
	 * @param strCalendar The Futures Settle Calendar
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblBasketNotional Basket Notional
	 * @param dblMinimumPriceMovement The Minimum Price Movement
	 * @param dblComponentNotionalMinimum The Minimum Component Notional
	 * @param astrExchange Exchange Array
	 * @param strUnderlierType Underlier Type
	 * @param strUnderlierSubtype Underlier Sub-Type
	 * @param dimExpiry The Expiry Date-In-Month Setting
	 * @param bfe Eligibility Settings
	 * @param bfs Settlement Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TreasuryFuturesConvention (
		final java.lang.String strName,
		final java.lang.String[] astrCode,
		final java.lang.String strCurrency,
		final java.lang.String strCalendar,
		final java.lang.String strMaturityTenor,
		final double dblBasketNotional,
		final double dblMinimumPriceMovement,
		final double dblComponentNotionalMinimum,
		final java.lang.String[] astrExchange,
		final java.lang.String strUnderlierType,
		final java.lang.String strUnderlierSubtype,
		final org.drip.analytics.eventday.DateInMonth dimExpiry,
		final org.drip.market.exchange.TreasuryFuturesEligibility bfe,
		final org.drip.market.exchange.TreasuryFuturesSettle bfs)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_astrCode = astrCode) || 0 ==
			_astrCode.length || null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null ==
				(_strMaturityTenor = strMaturityTenor) || _strMaturityTenor.isEmpty() ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblBasketNotional = dblBasketNotional) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblMinimumPriceMovement =
							dblMinimumPriceMovement) || !org.drip.numerical.common.NumberUtil.IsValid
								(_dblComponentNotionalMinimum = dblComponentNotionalMinimum) || null ==
									(_astrExchange = astrExchange) || 0 == _astrExchange.length || null ==
										(_strUnderlierType = strUnderlierType) || _strUnderlierType.isEmpty()
											|| null == (_strUnderlierSubtype = strUnderlierSubtype) ||
												_strUnderlierSubtype.isEmpty() || null == (_dimExpiry =
													dimExpiry) || null == (_bfe = bfe) || null == (_bfs =
														bfs))
			throw new java.lang.Exception ("TreasuryFuturesConvention ctr: Invalid Inputs");

		_strCalendar = strCalendar;
	}

	/**
	 * Retrieve the Treasury Futures Name
	 * 
	 * @return The Treasury Futures Name
	 */

	public java.lang.String name()
	{
		return _strName;
	}

	/**
	 * Retrieve the Treasury Futures Settle Calendar
	 * 
	 * @return The Treasury Futures Settle Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}

	/**
	 * Retrieve the Treasury Futures Code Array
	 * 
	 * @return The Treasury Futures Code Array
	 */

	public java.lang.String[] codes()
	{
		return _astrCode;
	}

	/**
	 * Retrieve the Treasury Futures Currency
	 * 
	 * @return The Treasury Futures Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Treasury Futures Maturity Tenor
	 * 
	 * @return The Treasury Futures Maturity Tenor
	 */

	public java.lang.String maturityTenor()
	{
		return _strMaturityTenor;
	}

	/**
	 * Retrieve the Treasury Futures Basket Notional
	 * 
	 * @return The Treasury Futures Basket Notional
	 */

	public double basketNotional()
	{
		return _dblBasketNotional;
	}

	/**
	 * Retrieve the Minimimum Price Movement - a.k.a Tick
	 * 
	 * @return The Minimum Price Movement
	 */

	public double minimumPriceMovement()
	{
		return _dblMinimumPriceMovement;
	}

	/**
	 * Retrieve the Minimum Treasury Futures Component Notional
	 * 
	 * @return The Minimum Treasury Futures Component Notional
	 */

	public double minimumComponentNotional()
	{
		return _dblComponentNotionalMinimum;
	}

	/**
	 * Retrieve the Bond Futures Exchanges Array
	 * 
	 * @return The Bond Futures Exchanges Array
	 */

	public java.lang.String[] exchanges()
	{
		return _astrExchange;
	}

	/**
	 * Retrieve the Treasury Futures Underlier Type
	 * 
	 * @return The Treasury Futures Underlier Type
	 */

	public java.lang.String underlierType()
	{
		return _strUnderlierType;
	}

	/**
	 * Retrieve the Treasury Futures Underlier Sub-type
	 * 
	 * @return The Treasury Futures Underlier Sub-type
	 */

	public java.lang.String underlierSubtype()
	{
		return _strUnderlierSubtype;
	}

	/**
	 * Retrieve the Date In Month Expiry Settings
	 * 
	 * @return The Date In Month Expiry Settings
	 */

	public org.drip.analytics.eventday.DateInMonth dimExpiry()
	{
		return _dimExpiry;
	}

	/**
	 * Retrieve the Treasury Futures Eligibility Settings
	 * 
	 * @return The Treasury Futures Eligibility Settings
	 */

	public org.drip.market.exchange.TreasuryFuturesEligibility eligibility()
	{
		return _bfe;
	}

	/**
	 * Retrieve the Treasury Futures Settle Settings
	 * 
	 * @return The Treasury Futures Settle Settings
	 */

	public org.drip.market.exchange.TreasuryFuturesSettle settle()
	{
		return _bfs;
	}

	/**
	 * Retrieve the TreasuryFuturesEventDates Instance corresponding to the Futures Expiry Year/Month
	 * 
	 * @param iYear Futures Year
	 * @param iMonth Futures Month
	 * 
	 * @return The TreasuryFuturesEventDates Instance
	 */

	public org.drip.market.exchange.TreasuryFuturesEventDates eventDates (
		final int iYear,
		final int iMonth)
	{
		org.drip.analytics.date.JulianDate dtExpiry = _dimExpiry.instanceDay (iYear, iMonth, _strCalendar);

		if (null == dtExpiry) return null;

		try {
			return new org.drip.market.exchange.TreasuryFuturesEventDates (dtExpiry, dtExpiry.addBusDays
				(_bfs.expiryFirstDeliveryLag(), _strCalendar), dtExpiry.addBusDays
					(_bfs.expiryFinalDeliveryLag(), _strCalendar), dtExpiry.addBusDays
						(_bfs.expiryDeliveryNoticeLag(), _strCalendar), dtExpiry.addBusDays
							(_bfs.expiryFirstDeliveryLag(), _strCalendar));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Reference Bond Price from the Quoted Futures Index Level
	 * 
	 * @param dblFuturesQuotedIndex The Quoted Futures Index Level
	 * 
	 * @return The Reference Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double referencePrice (
		final double dblFuturesQuotedIndex)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblFuturesQuotedIndex))
			throw new java.lang.Exception ("TreasuryFuturesConvention::referencePrice => Invalid Inputs!");

		double dblPeriodReferenceYield = 0.5 * (1. - dblFuturesQuotedIndex);

		double dblCompoundedDF = java.lang.Math.pow (1. / (1. + dblPeriodReferenceYield),
			org.drip.analytics.support.Helper.TenorToMonths (_strMaturityTenor) / 6);

		return dblCompoundedDF + 0.5 * _bfs.currentReferenceYield() * (1. - dblCompoundedDF) /
			dblPeriodReferenceYield;
	}

	/**
	 * Compute the Reference Bond Price from the Quoted Futures Index Level
	 * 
	 * @param dtValue The Valuation Date
	 * @param bond The Bond Instance
	 * @param dblFuturesQuotedIndex The Quoted Futures Index Level
	 * 
	 * @return The Reference Price
	 * 
	 * @throws java.lang.Exception Thrown if the Treasury Futures Price Generic cannot be computed
	 */

	public double referencePrice (
		final org.drip.analytics.date.JulianDate dtValue,
		final org.drip.product.definition.Bond bond,
		final double dblFuturesQuotedIndex)
		throws java.lang.Exception
	{
		if (null == dtValue || null == bond) return referencePrice (dblFuturesQuotedIndex);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblFuturesQuotedIndex))
			throw new java.lang.Exception ("AnalyticsHelper::referencePrice => Invalid Inputs");

		return bond.priceFromYield (new org.drip.param.valuation.ValuationParams (dtValue, dtValue, null),
			null, null, 1. - dblFuturesQuotedIndex);
	}

	/**
	 * Indicate whether the given bond is eligible to be delivered
	 * 
	 * @param dtValue The Value Date
	 * @param bond The Bond whose Eligibility is to be evaluated
	 * @param dblOutstandingNotional The Outstanding Notional
	 * @param strIssuer The Issuer
	 * 
	 * @return TRUE - The given bond is eligible to be delivered
	 */

	public boolean isEligible (
		final org.drip.analytics.date.JulianDate dtValue,
		final org.drip.product.definition.Bond bond,
		final double dblOutstandingNotional,
		final java.lang.String strIssuer)
	{
		return _bfe.isEligible (dtValue, bond, dblOutstandingNotional, strIssuer);
	}

	@Override public java.lang.String toString()
	{
		java.lang.String strDump = "Name: " + _strName + " | Currency: " + _strCurrency + " | Calendar: " +
			_strCalendar + " | Underlier Type: " + _strUnderlierType + " | Underlier Sub-type: " +
				_strUnderlierSubtype + " | Maturity Tenor: " + _strMaturityTenor + " | Basket Notional: " +
					_dblBasketNotional + " | Minimum Price Movement: " + _dblMinimumPriceMovement +
						" | Component Notional Minimum: " + _dblComponentNotionalMinimum;

		for (int i = 0; i < _astrCode.length; ++i) {
			if (0 == i)
				strDump += " | CODES => {";
			else
				strDump += ", ";

			strDump += _astrCode[i];

			if (_astrExchange.length - 1 == i) strDump += "}";
		}

		for (int i = 0; i < _astrExchange.length; ++i) {
			if (0 == i)
				strDump += " | EXCHANGES => (";
			else
				strDump += ", ";

			strDump += _astrExchange[i];

			if (_astrExchange.length - 1 == i) strDump += ") ";
		}

		return strDump + "\n\t\t" + _dimExpiry + "\n\t\t" + _bfe + "\n\t\t" + _bfs;
	}
}
