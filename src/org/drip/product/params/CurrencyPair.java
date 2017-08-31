
package org.drip.product.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * CurrencyPair class contains the numerator currency, the denominator currency, the quote currency, and the
 * 	PIP Factor. It exports serialization into and de-serialization out of byte arrays.
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
					!org.drip.quant.common.NumberUtil.IsValid (dblPIPFactor))
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
