
package org.drip.param.quote;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>MultiSided</i> implements the Quote interface, which contains the stubs corresponding to a product
 * quote. It contains the quote value, quote instant for the different quote sides (bid/ask/mid).
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote">Quote</a></li>
 *  </ul>
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
			if (!org.drip.numerical.common.NumberUtil.IsValid (_dblQuote = dblQuote))
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
			if (!org.drip.numerical.common.NumberUtil.IsValid (dblQuote)) return false;

			_dblQuote = dblQuote;
			return true;
		}

		boolean setSize (
			final double dblSize)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (dblSize)) return false;

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
		if (null == strSide || strSide.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid (dblQuote))
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
		if (null == strSide || strSide.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid (dblQuote))
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
		if (null != strSide && !strSide.isEmpty() && !org.drip.numerical.common.NumberUtil.IsValid (dblQuote))
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
