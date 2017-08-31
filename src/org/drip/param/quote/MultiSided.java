
package org.drip.param.quote;

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
 * MultiSided implements the Quote interface, which contains the stubs corresponding to a product quote. It
 *  contains the quote value, quote instant for the different quote sides (bid/ask/mid).
 *   
 * @author Lakshmi Krishnamurthy
 */

public class MultiSided extends org.drip.param.definition.Quote {
	class SingleSided {
		double _dblSize = java.lang.Double.NaN;
		double _dblQuote = java.lang.Double.NaN;
		org.drip.analytics.date.DateTime _dt = null;

		SingleSided (
			final double dblQuote,
			final double dblSize)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (_dblQuote = dblQuote))
				throw new java.lang.Exception ("MultiSided::SingleSided ctr: Invalid Inputs!");

			_dblSize = dblSize;

			_dt = new org.drip.analytics.date.DateTime();
		}

		double quote()
		{
			return _dblQuote;
		}

		double size()
		{
			return _dblSize;
		}

		org.drip.analytics.date.DateTime time()
		{
			return _dt;
		}

		boolean setQuote (
			final double dblQuote)
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblQuote)) return false;

			_dblQuote = dblQuote;
			return true;
		}

		boolean setSize (
			final double dblSize)
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblSize)) return false;

			_dblSize = dblSize;
			return true;
		}
	};

	private org.drip.analytics.support.CaseInsensitiveTreeMap<SingleSided> _mapSingleSidedQuote = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<SingleSided>();

	/**
	 * MultiSidedQuote Constructor: Constructs a Quote object from the quote value and the side string.
	 * 
	 * @param strSide bid/ask/mid
	 * @param dblQuote Quote Value
	 * 
	 * @throws java.lang.Exception Thrown on invalid inputs
	 */

	public MultiSided (
		final java.lang.String strSide,
		final double dblQuote)
		throws java.lang.Exception
	{
		if (null == strSide || strSide.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (dblQuote))
			throw new java.lang.Exception ("MultiSided ctr: Invalid Side/Quote/Size!");

		_mapSingleSidedQuote.put (strSide, new SingleSided (dblQuote, java.lang.Double.NaN));
	}

	/**
	 * MultiSided Constructor: Constructs a Quote object from the quote size/value and the side string.
	 * 
	 * @param strSide bid/ask/mid
	 * @param dblQuote Quote Value
	 * @param dblSize Size
	 * 
	 * @throws java.lang.Exception Thrown on invalid inputs
	 */

	public MultiSided (
		final java.lang.String strSide,
		final double dblQuote,
		final double dblSize)
		throws java.lang.Exception
	{
		if (null == strSide || strSide.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (dblQuote))
			throw new java.lang.Exception ("MultiSided ctr: Invalid Side/Quote/Size!");

		_mapSingleSidedQuote.put (strSide, new SingleSided (dblQuote, dblSize));
	}

	@Override public double value (
		final java.lang.String strSide)
	{
		if (null == strSide || strSide.isEmpty()) return java.lang.Double.NaN;

		return _mapSingleSidedQuote.get (strSide).quote();
	}

	@Override public double size (
		final java.lang.String strSide)
	{
		if (null == strSide || strSide.isEmpty()) return java.lang.Double.NaN;

		return _mapSingleSidedQuote.get (strSide).size();
	}

	@Override public org.drip.analytics.date.DateTime time (
		final java.lang.String strSide)
	{
		if (null == strSide || strSide.isEmpty()) return null;

		return _mapSingleSidedQuote.get (strSide).time();
	}

	@Override public boolean setSide (
		final java.lang.String strSide,
		final double dblQuote,
		final double dblSize)
	{
		if (null != strSide && !strSide.isEmpty() && !org.drip.quant.common.NumberUtil.IsValid (dblQuote))
			return false;

		try {
			_mapSingleSidedQuote.put (strSide, new SingleSided (dblQuote, dblSize));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}
}
