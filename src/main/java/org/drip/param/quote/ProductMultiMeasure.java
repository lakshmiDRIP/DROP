
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
 * <i>ProductMultiMeasureQuote</i> holds the different types of quotes for a given component. It contains a
 * single market field/quote pair, but multiple alternate named quotes (to accommodate quotes on different
 * measure for the component).
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote">Quote</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *   
 * @author Lakshmi Krishnamurthy
 */

public class ProductMultiMeasure extends org.drip.param.definition.ProductQuote {
	private java.lang.String _strMarketQuoteField = "";
	private org.drip.param.definition.Quote _mktQuote = null;

	org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.Quote> _mapQuote = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.Quote>();

	/**
	 * Construct an empty instance of ProductMultiMeasure
	 */

	public ProductMultiMeasure()
	{
	}

	@Override public void addQuote (
		final java.lang.String strQuoteField,
		final org.drip.param.definition.Quote q,
		final boolean bIsMarketQuote)
	{
		_mapQuote.put (strQuoteField, q);

		if (bIsMarketQuote) {
			_mktQuote = q;
			_strMarketQuoteField = strQuoteField;
		}
	}

	@Override public boolean setMarketQuote (
		final java.lang.String strMarketQuoteField,
		final org.drip.param.definition.Quote q)
	{
		if (null == strMarketQuoteField || strMarketQuoteField.isEmpty() || null == q) return false;

		_strMarketQuoteField = strMarketQuoteField;
		_mktQuote = q;
		return true;
	}

	@Override public boolean removeMarketQuote()
	{
		_strMarketQuoteField = "";
		_mktQuote = null;
		return true;
	}

	@Override public org.drip.param.definition.Quote quote (
		final java.lang.String strQuoteField)
	{
		return null == strQuoteField || strQuoteField.isEmpty() ? null : _mapQuote.get (strQuoteField);
	}

	@Override public org.drip.param.definition.Quote marketQuote()
	{
		return _mktQuote;
	}

	@Override public java.lang.String marketQuoteField()
	{
		return _strMarketQuoteField;
	}

	@Override public boolean removeQuote (
		final java.lang.String strQuoteField)
	{
		if (null == strQuoteField || strQuoteField.isEmpty()) return false;

		_mapQuote.remove (strQuoteField);

		if (!_strMarketQuoteField.equalsIgnoreCase (strQuoteField)) return true;

		return removeMarketQuote();
	}

	@Override public boolean containsQuote (
		final java.lang.String strQuoteField)
	{
		if (null == strQuoteField || strQuoteField.isEmpty()) return false;

		return _mapQuote.containsKey (strQuoteField) || (null != _strMarketQuoteField &&
			_strMarketQuoteField.equalsIgnoreCase (strQuoteField));
	}
}
