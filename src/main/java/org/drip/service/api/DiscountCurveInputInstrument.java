
package org.drip.service.api;

import java.util.List;

import org.drip.analytics.date.JulianDate;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DiscountCurveInputInstrument</i> contains the input instruments and their quotes. It provides the
 * 	following Functions:
 * 	<ul>
 * 		<li><i>DiscountCurveInputInstrument</i> constructor</li>
 * 		<li>Retrieve the Curve Epoch Date</li>
 * 		<li>Retrieve the Array of Cash Quotes</li>
 * 		<li>Retrieve the Array of Cash Tenors</li>
 * 		<li>Retrieve the Array of Future Quotes</li>
 * 		<li>Retrieve the Array of Future Tenors</li>
 * 		<li>Retrieve the Array of Swap Quotes</li>
 * 		<li>Retrieve the Array of Swap Tenors</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/README.md">Horizon Roll Attribution Service API</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DiscountCurveInputInstrument
{
	private JulianDate _epochDate = null;
	private List<Double> _cashQuoteList = null;
	private List<String> _cashTenorList = null;
	private List<Double> _swapQuoteList = null;
	private List<String> _swapTenorList = null;
	private List<Double> _futuresQuoteList = null;
	private List<String> _futuresTenorList = null;

	/**
	 * <i>DiscountCurveInputInstrument</i> Constructor
	 * 
	 * @param epochDate Curve Epoch Date
	 * @param cashTenorList List of Cash Tenors
	 * @param cashQuoteList List of Cash Quotes
	 * @param futuresTenorList List of Future Tenors
	 * @param futuresQuoteList List of Future Quotes
	 * @param swapTenorList List of Swap Tenors
	 * @param swapQuoteList List of Swap Quotes
	 * 
	 * @throws Exception Thrown if Inputs are invalid
	 */

	public DiscountCurveInputInstrument (
		final JulianDate epochDate,
		final List<String> cashTenorList,
		final List<Double> cashQuoteList,
		final List<String> futuresTenorList,
		final List<Double> futuresQuoteList,
		final List<String> swapTenorList,
		final List<Double> swapQuoteList)
		throws Exception
	{
		if (null == (_epochDate = epochDate)) {
			throw new Exception ("DiscountCurveInputInstrument Constructor: Invalid Inputs");
		}

		int cashQuoteCount = null == (_cashQuoteList = cashQuoteList) ? 0 : _cashQuoteList.size();

		int cashTenorCount = null == (_cashTenorList = cashTenorList) ? 0 : _cashTenorList.size();

		int swapQuoteCount = null == (_swapQuoteList = swapQuoteList) ? 0 : _swapQuoteList.size();

		int swapTenorCount = null == (_swapTenorList = swapTenorList) ? 0 : _swapTenorList.size();

		int futuresQuoteCount = null == (_futuresQuoteList = futuresQuoteList) ?
			0 : _futuresQuoteList.size();

		int futuresTenorCount = null == (_futuresTenorList = futuresTenorList) ?
			0 : _futuresTenorList.size();

		if (cashQuoteCount != cashTenorCount ||
			futuresQuoteCount != futuresTenorCount ||
			swapQuoteCount != swapTenorCount ||
			(0 == cashTenorCount && 0 == futuresTenorCount && 0 == swapTenorCount))
		{
			throw new Exception ("DiscountCurveInputInstrument ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Curve Epoch Date
	 * 
	 * @return The Curve Epoch Date
	 */

	public JulianDate date()
	{
		return _epochDate;
	}

	/**
	 * Retrieve the Array of Cash Quotes
	 * 
	 * @return The Array of Cash Quotes
	 */

	public double[] cashQuote()
	{
		if (null == _cashQuoteList) {
			return null;
		}

		int quoteCount = _cashQuoteList.size();

		if (0 == quoteCount) {
			return null;
		}

		int i = 0;
		double[] quoteArray = new double[quoteCount];

		for (double quote : _cashQuoteList) {
			quoteArray[i++] = quote;
		}

		return quoteArray;
	}

	/**
	 * Retrieve the Array of Cash Tenors
	 * 
	 * @return The Array of Cash Tenors
	 */

	public String[] cashTenor()
	{
		if (null == _cashTenorList) {
			return null;
		}

		int tenorCount = _cashTenorList.size();

		if (0 == tenorCount) return null;

		int i = 0;
		String[] tenorArray = new String[tenorCount];

		for (String tenor : _cashTenorList) {
			tenorArray[i++] = tenor;
		}

		return tenorArray;
	}

	/**
	 * Retrieve the Array of Future Quotes
	 * 
	 * @return The Array of Future Quotes
	 */

	public double[] futureQuote()
	{
		if (null == _futuresQuoteList) {
			return null;
		}

		int quoteCount = _futuresQuoteList.size();

		if (0 == quoteCount) {
			return null;
		}

		int i = 0;
		double[] quoteArray = new double[quoteCount];

		for (double quote : _futuresQuoteList) {
			quoteArray[i++] = quote;
		}

		return quoteArray;
	}

	/**
	 * Retrieve the Array of Future Tenors
	 * 
	 * @return The Array of Future Tenors
	 */

	public String[] futureTenor()
	{
		if (null == _futuresTenorList) {
			return null;
		}

		int tenorCount = _futuresTenorList.size();

		if (0 == tenorCount) {
			return null;
		}

		int i = 0;
		String[] tenorArray = new String[tenorCount];

		for (String tenor : _futuresTenorList) {
			tenorArray[i++] = tenor;
		}

		return tenorArray;
	}

	/**
	 * Retrieve the Array of Swap Quotes
	 * 
	 * @return The Array of Swap Quotes
	 */

	public double[] swapQuote()
	{
		if (null == _swapQuoteList) {
			return null;
		}

		int quoteCount = _swapQuoteList.size();

		if (0 == quoteCount) {
			return null;
		}

		int i = 0;
		double[] quoteArray = new double[quoteCount];

		for (double quote : _swapQuoteList) {
			quoteArray[i++] = quote;
		}

		return quoteArray;
	}

	/**
	 * Retrieve the Array of Swap Tenors
	 * 
	 * @return The Array of Swap Tenors
	 */

	public String[] swapTenor()
	{
		if (null == _swapTenorList) {
			return null;
		}

		int tenorCount = _swapTenorList.size();

		if (0 == tenorCount) {
			return null;
		}

		int i = 0;
		String[] tenorArray = new String[tenorCount];

		for (String tenor : _swapTenorList) {
			tenorArray[i++] = tenor;
		}

		return tenorArray;
	}
}
