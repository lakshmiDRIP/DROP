
package org.drip.param.quote;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>TickerPriceStatistics</i> maintains the Running "Thin" Price Statistics for a Single Ticker.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quote/README.md">Multi-sided Multi-Measure Ticks Quotes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TickerPriceStatistics
{
	private int _count = 0;
	private double _maximum = java.lang.Double.NaN;
	private double _minimum = java.lang.Double.NaN;
	private double _aggregate = java.lang.Double.NaN;

	/**
	 * TickerPriceStatistics Constructor
	 * 
	 * @param price The Instance Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TickerPriceStatistics (
		final double price)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (price) || 0. >= price)
		{
			throw new java.lang.Exception ("TickerPriceStatistics Constructor => Invalid Inputs");
		}

		_count = 1;
		_minimum = price;
		_maximum = price;
		_aggregate = price;
	}

	/**
	 * Retrieve the Ticker Instance Count
	 * 
	 * @return The Ticker Instance Count
	 */

	public int count()
	{
		return _count;
	}

	/**
	 * Retrieve the Minimum Ticker Price
	 * 
	 * @return The Minimum Ticker Price
	 */

	public double minimum()
	{
		return _minimum;
	}

	/**
	 * Retrieve the Maximum Ticker Price
	 * 
	 * @return The Maximum Ticker Price
	 */

	public double maximum()
	{
		return _maximum;
	}

	/**
	 * Retrieve the Aggregate Ticker Price
	 * 
	 * @return The Aggregate Ticker Price
	 */

	public double aggregate()
	{
		return _aggregate;
	}

	/**
	 * Retrieve the Average Ticker Price
	 * 
	 * @return The Average Ticker Price
	 */

	public double average()
	{
		return _aggregate / _count;
	}

	/**
	 * Add a Single Price Instance
	 * 
	 * @param price The Instance Price
	 * 
	 * @return TRUE - The Price Instance successfully added
	 */

	public boolean addInstance (
		final double price)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (price) || 0. >= price)
		{
			return false;
		}

		++_count;
		_aggregate += price;

		if (_minimum > price)
		{
			_minimum = price;
		}
		else if (_maximum < price)
		{
			_maximum = price;
		}

		return true;
	}

	@Override public java.lang.String toString()
	{
		return "" + _maximum + " " + _minimum + " " + average();
	}
}
