
package org.drip.product.params;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>CurrencyPair</i> class contains the numerator currency, the denominator currency, the quote currency,
 * and the PIP Factor. It exports serialization into and de-serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/README.md">Fixed Income Product Customization Parameters</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurrencyPair {
	private java.lang.String _strNumCcy = "";
	private java.lang.String _strDenomCcy = "";
	private java.lang.String _strQuoteCcy = "";
	private double _dblPIPFactor = java.lang.Double.NaN;

	/**
	 * Construct the Currency Pair from the Code
	 * 
	 * @param strCode Currency Pair Code
	 * 
	 * @return The Currency Pair
	 */

	public static final CurrencyPair FromCode (
		final java.lang.String strCode)
	{
		if (null == strCode || strCode.isEmpty()) return null;

		java.lang.String[] astrCcy = strCode.split ("/");

		if (null == astrCcy || 2 != astrCcy.length || null == astrCcy[0] || astrCcy[0].isEmpty() || null ==
			astrCcy[1] || astrCcy[1].isEmpty())
			return null;

		try {
			return new CurrencyPair (astrCcy[0], astrCcy[1], astrCcy[0], 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the currency pair from the numerator currency, the denominator currency, the quote
	 * 	currency, and the PIP Factor
	 * 
	 * @param strNumCcy Numerator currency
	 * @param strDenomCcy Denominator currency
	 * @param strQuoteCcy Quote Currency
	 * @param dblPIPFactor PIP Factor
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CurrencyPair (
		final java.lang.String strNumCcy,
		final java.lang.String strDenomCcy,
		final java.lang.String strQuoteCcy,
		final double dblPIPFactor)
		throws java.lang.Exception
	{
		if (null == strNumCcy || strNumCcy.isEmpty() || null == strDenomCcy || strDenomCcy.isEmpty() || null
			== strQuoteCcy || strNumCcy.equalsIgnoreCase (strDenomCcy) || (!strQuoteCcy.equalsIgnoreCase
				(strNumCcy) && !strQuoteCcy.equalsIgnoreCase (strDenomCcy)) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblPIPFactor))
			throw new java.lang.Exception ("CurrencyPair ctr: Invalid parameters");

		_strNumCcy = strNumCcy;
		_strDenomCcy = strDenomCcy;
		_strQuoteCcy = strQuoteCcy;
		_dblPIPFactor = dblPIPFactor;
	}

	/**
	 * Get the numerator currency
	 * 
	 * @return Numerator currency
	 */

	public java.lang.String numCcy()
	{
		return _strNumCcy;
	}

	/**
	 * Get the denominator currency
	 * 
	 * @return Denominator currency
	 */

	public java.lang.String denomCcy()
	{
		return _strDenomCcy;
	}

	/**
	 * Get the quote currency
	 * 
	 * @return Quote currency
	 */

	public java.lang.String quoteCcy()
	{
		return _strQuoteCcy;
	}

	/**
	 * Get the currency pair code
	 * 
	 * @return Currency pair code
	 */

	public java.lang.String code()
	{
		return _strNumCcy + "/" + _strDenomCcy;
	}

	/**
	 * Get the inverse currency pair code
	 * 
	 * @return The Inverse Currency pair code
	 */

	public java.lang.String inverseCode()
	{
		return _strDenomCcy + "/" + _strNumCcy;
	}

	/**
	 * Get the PIP Factor
	 * 
	 * @return PIP Factor
	 */

	public double pipFactor()
	{
		return _dblPIPFactor;
	}

	@Override public java.lang.String toString()
	{
		return _strNumCcy + " | " + _strDenomCcy + " | " + _strQuoteCcy + " | " + _dblPIPFactor;
	}
}
