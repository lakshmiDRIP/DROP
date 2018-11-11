
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
 * <i>TreasuryFuturesEligibility</i> contains the Eligibility Criterion for a Bond in the Futures Basket of
 * the Exchange-Traded Treasury Futures Contracts.
 * 
 * <br><br>
 * 	<ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Exchange</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesEligibility {
	private java.lang.String[] _astrIssuer = null;
	private java.lang.String _strMaturityFloor = "";
	private java.lang.String _strMaturityCeiling = "";
	private double _dblMinimumOutstandingNotional = java.lang.Double.NaN;

	/**
	 * TreasuryFuturesEligibility Constructor
	 * 
	 * @param strMaturityFloor Maturity Floor
	 * @param strMaturityCeiling Maturity Floor
	 * @param astrIssuer Array of Issuers
	 * @param dblMinimumOutstandingNotional Minimum Outstanding Notional
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public TreasuryFuturesEligibility (
		final java.lang.String strMaturityFloor,
		final java.lang.String strMaturityCeiling,
		final java.lang.String[] astrIssuer,
		final double dblMinimumOutstandingNotional)
		throws java.lang.Exception
	{
		if (null == (_strMaturityFloor = strMaturityFloor) || _strMaturityFloor.isEmpty() || null ==
			(_strMaturityCeiling = strMaturityCeiling) || _strMaturityCeiling.isEmpty() ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblMinimumOutstandingNotional =
					dblMinimumOutstandingNotional))
			throw new java.lang.Exception ("TreasuryFuturesEligibility ctr: Invalid Inputs");

		if (null != (_astrIssuer = astrIssuer)) {
			int iNumIssuer = _astrIssuer.length;

			for (int i = 0; i < iNumIssuer; ++i) {
				if (null == _astrIssuer[i] || _astrIssuer[i].isEmpty())
					throw new java.lang.Exception ("TreasuryFuturesEligibility ctr: Invalid Issuer");
			}
		}
	}

	/**
	 * Retrieve the Eligible Maturity Floor
	 * 
	 * @return Array of Eligible Maturity Floor
	 */

	public java.lang.String maturityFloor()
	{
		return _strMaturityFloor;
	}

	/**
	 * Retrieve the Eligible Maturity Ceiling
	 * 
	 * @return Array of Eligible Maturity Ceiling
	 */

	public java.lang.String maturityCeiling()
	{
		return _strMaturityCeiling;
	}

	/**
	 * Retrieve the Array of Eligible Issuers
	 * 
	 * @return Array of Eligible Issuers
	 */

	public java.lang.String[] issuer()
	{
		return _astrIssuer;
	}

	/**
	 * Retrieve the Minimum Outstanding Notional
	 * 
	 * @return The Minimum Outstanding Notional
	 */

	public double minimumOutstandingNotional()
	{
		return _dblMinimumOutstandingNotional;
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
		if (null == bond || null == dtValue) return false;

		org.drip.analytics.date.JulianDate dtFloorMaturity = dtValue.addTenor (_strMaturityFloor);

		org.drip.analytics.date.JulianDate dtCeilingMaturity = dtValue.addTenor (_strMaturityCeiling);

		if (null == dtFloorMaturity || null == dtFloorMaturity) return false;

		int iValueDate = dtValue.julian();

		if (iValueDate < dtFloorMaturity.julian() || iValueDate > dtCeilingMaturity.julian()) return false;

		if (0. != _dblMinimumOutstandingNotional && org.drip.quant.common.NumberUtil.IsValid
			(dblOutstandingNotional) && dblOutstandingNotional < _dblMinimumOutstandingNotional)
			return false;

		if (null == strIssuer || strIssuer.isEmpty() || null == _astrIssuer) return true;

		int iNumIssuer = _astrIssuer.length;

		if (0 == iNumIssuer) return true;

		for (int i = 0; i < iNumIssuer; ++i) {
			if (_astrIssuer[i].equalsIgnoreCase (strIssuer)) return true;
		}

		return false;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String strDump = "[Futures Eligibility => Maturity Band: " + _strMaturityFloor + " -> " +
			_strMaturityCeiling + "] [Issuers: ";

		if (null == _astrIssuer) return strDump + "]";

		for (int i = 0; i < _astrIssuer.length; ++i) {
			if (0 != i) strDump += " | ";

			strDump += _astrIssuer[i];
		}

		return strDump + "] [Minimum Outstanding Notional: " + _dblMinimumOutstandingNotional + "]";
	}
}
