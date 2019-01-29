
package org.drip.param.quote;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>TickerPriceStatisticsContainer</i> maintains the Running "Thin" Price Statistics for all Tickers.
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

public class TickerPriceStatisticsContainer
{
	private java.util.Map<java.lang.String, org.drip.param.quote.TickerPriceStatistics>
		_tickerPriceStatisticsMap = new
			java.util.TreeMap<java.lang.String, org.drip.param.quote.TickerPriceStatistics>();

	/**
	 * Empty TickerPriceStatisticsContainer
	 */

	public TickerPriceStatisticsContainer()
	{
	}

	/**
	 * Retrieve the Ticker Price Statistics Map
	 * 
	 * @return The Ticker Price Statistics Map
	 */

	public java.util.Map<java.lang.String, org.drip.param.quote.TickerPriceStatistics>
		tickerPriceStatisticsMap()
	{
		return _tickerPriceStatisticsMap;
	}

	/**
	 * Add an Instance of the Ticker/Price
	 * 
	 * @param ticker Ticker
	 * @param price Price
	 * 
	 * @return TRUE - The Ticker/Price Instance successfully added
	 */

	public boolean addInstance (
		final java.lang.String ticker,
		final double price)
	{
		if (null == ticker || ticker.isEmpty())
		{
			return false;
		}

		if (_tickerPriceStatisticsMap.containsKey (ticker))
		{
			return _tickerPriceStatisticsMap.get (ticker).addInstance (price);
		}

		try
		{
			_tickerPriceStatisticsMap.put (
				ticker,
				new org.drip.param.quote.TickerPriceStatistics (price)
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override public java.lang.String toString()
	{
		java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.quote.TickerPriceStatistics>
			tickerPriceStatisticsEntry : _tickerPriceStatisticsMap.entrySet())
		{
			stringBuilder.append (tickerPriceStatisticsEntry.getKey() + " " +
				tickerPriceStatisticsEntry.getValue() + "\n");
		}

		return stringBuilder.toString();
	}
}
