
package org.drip.feed.loader;

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
 * <i>InstrumentSetTenorQuote</i> holds the Instrument Set Tenor and Closing Quote Group.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/README.md">Reference/Market Data Feed Loader</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class InstrumentSetTenorQuote {
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.util.Map<java.lang.Integer,
		org.drip.feed.loader.TenorQuote>> _mapOrderedInstrumentTenorQuote = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.util.Map<java.lang.Integer,
				org.drip.feed.loader.TenorQuote>>();

	/**
	 * Empty InstrumentSetTenorQuote Constructor
	 */

	public InstrumentSetTenorQuote()
	{
	}

	/**
	 * Add the Instrument/Tenor/Quote/Scale Field Set
	 * 
	 * @param strInstrument Quote Instrument
	 * @param strTenor Quote Tenor
	 * @param dblQuote Quote Value
	 * @param dblScale Quote Scale
	 * 
	 * @return TRUE - Group Set successfully added
	 */

	public boolean add (
		final java.lang.String strInstrument,
		final java.lang.String strTenor,
		final double dblQuote,
		final double dblScale)
	{
		int iTenorDays = -1;
		org.drip.feed.loader.TenorQuote tq = null;

		try {
			tq = new org.drip.feed.loader.TenorQuote (strTenor, dblQuote * dblScale);

			if (0 >= (iTenorDays = org.drip.analytics.support.Helper.TenorToDays (strTenor))) return false;
		} catch (java.lang.Exception e) {
			return false;
		}

		java.util.Map<java.lang.Integer, org.drip.feed.loader.TenorQuote> mapOrderedTenorQuote =
			_mapOrderedInstrumentTenorQuote.containsKey (strInstrument) ? _mapOrderedInstrumentTenorQuote.get
				(strInstrument) : new java.util.TreeMap<java.lang.Integer,
					org.drip.feed.loader.TenorQuote>();

		mapOrderedTenorQuote.put (iTenorDays, tq);

		_mapOrderedInstrumentTenorQuote.put (strInstrument, mapOrderedTenorQuote);

		return true;
	}

	/**
	 * Retrieve the Named Instrument Group Quote Map
	 * 
	 * @param strInstrument The Instrument Group Name
	 * 
	 * @return The Named Instrument Group Quote Map
	 */

	public java.util.Map<java.lang.Integer, org.drip.feed.loader.TenorQuote> instrumentTenorQuote (
		final java.lang.String strInstrument)
	{
		return null == strInstrument || !_mapOrderedInstrumentTenorQuote.containsKey (strInstrument) ? null :
			_mapOrderedInstrumentTenorQuote.get (strInstrument);
	}

	/**
	 * Retrieve the Named Instrument Tenors
	 * 
	 * @param strInstrument The Instrument Group Name
	 * 
	 * @return The Named Instrument Tenors
	 */

	public java.lang.String[] instrumentTenor (
		final java.lang.String strInstrument)
	{
		java.util.Map<java.lang.Integer, org.drip.feed.loader.TenorQuote> mapTenorQuote =
			instrumentTenorQuote (strInstrument);

		if (null == mapTenorQuote) return null;

		int iNumTenor = mapTenorQuote.size();

		if (0 == iNumTenor) return null;

		int i = 0;
		java.lang.String[] astrTenor = new java.lang.String[iNumTenor];

		for (java.util.Map.Entry<java.lang.Integer, org.drip.feed.loader.TenorQuote> meTQ :
			mapTenorQuote.entrySet())
			astrTenor[i++] = meTQ.getValue().tenor();

		return astrTenor;
	}

	/**
	 * Retrieve the Named Instrument Quotes
	 * 
	 * @param strInstrument The Instrument Group Name
	 * 
	 * @return The Named Instrument Quotes
	 */

	public double[] instrumentQuote (
		final java.lang.String strInstrument)
	{
		java.util.Map<java.lang.Integer, org.drip.feed.loader.TenorQuote> mapTenorQuote =
			instrumentTenorQuote (strInstrument);

		if (null == mapTenorQuote) return null;

		int iNumTenor = mapTenorQuote.size();

		if (0 == iNumTenor) return null;

		int i = 0;
		double[] adblQuote = new double[iNumTenor];

		for (java.util.Map.Entry<java.lang.Integer, org.drip.feed.loader.TenorQuote> meTQ :
			mapTenorQuote.entrySet())
			adblQuote[i++] = meTQ.getValue().quote();

		return adblQuote;
	}

	/**
	 * Indicates whether the ISTQ is Empty or not
	 * 
	 * @return TRUE - ISTQ is Empty
	 */

	public boolean isEmpty()
	{
		return 0 == _mapOrderedInstrumentTenorQuote.size();
	}
}
