
package org.drip.feed.loader;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * InstrumentSetTenorQuote holds the Instrument Set Tenor and Closing Quote Group.
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
