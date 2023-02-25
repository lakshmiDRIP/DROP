
package org.drip.market.exchange;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesEligibility</i> contains the Eligibility Criterion for a Bond in the Futures Basket of
 * the Exchange-Traded Treasury Futures Contracts.
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
				!org.drip.numerical.common.NumberUtil.IsValid (_dblMinimumOutstandingNotional =
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

		if (0. != _dblMinimumOutstandingNotional && org.drip.numerical.common.NumberUtil.IsValid
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
