
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
	private List<Double> _lsSwapQuote = null;
	private List<String> _lsSwapTenor = null;
	private List<Double> _lsFutureQuote = null;
	private List<String> _lsFutureTenor = null;

	/**
	 * DiscountCurveInputInstrument constructor
	 * 
	 * @param dt Curve Epoch Date
	 * @param lsCashTenor List of Cash Tenors
	 * @param lsCashQuote List of Cash Quotes
	 * @param lsFutureTenor List of Future Tenors
	 * @param lsFutureQuote List of Future Quotes
	 * @param lsSwapTenor List of Swap Tenors
	 * @param lsSwapQuote List of Swap Quotes
	 * 
	 * @throws Exception Thrown if Inputs are invalid
	 */

	public DiscountCurveInputInstrument (
		final JulianDate dt,
		final List<String> lsCashTenor,
		final List<Double> lsCashQuote,
		final List<String> lsFutureTenor,
		final List<Double> lsFutureQuote,
		final List<String> lsSwapTenor,
		final List<Double> lsSwapQuote)
		throws Exception
	{
		if (null == (_epochDate = dt))
			throw new Exception ("DiscountCurveInputInstrument ctr: Invalid Inputs");

		int iNumCashQuote = null == (_cashQuoteList = lsCashQuote) ? 0 : _cashQuoteList.size();

		int iNumCashTenor = null == (_cashTenorList = lsCashTenor) ? 0 : _cashTenorList.size();

		int iNumFutureQuote = null == (_lsFutureQuote = lsFutureQuote) ? 0 : _lsFutureQuote.size();

		int iNumFutureTenor = null == (_lsFutureTenor = lsFutureTenor) ? 0 : _lsFutureTenor.size();

		int iNumSwapQuote = null == (_lsSwapQuote = lsSwapQuote) ? 0 : _lsSwapQuote.size();

		int iNumSwapTenor = null == (_lsSwapTenor = lsSwapTenor) ? 0 : _lsSwapTenor.size();

		if (iNumCashQuote != iNumCashTenor || iNumFutureQuote != iNumFutureTenor || iNumSwapQuote !=
			iNumSwapTenor || (0 == iNumCashTenor && 0 == iNumFutureTenor && 0 == iNumSwapTenor))
			throw new Exception ("DiscountCurveInputInstrument ctr: Invalid Inputs");
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
		if (null == _cashQuoteList) return null;

		int iNumQuote = _cashQuoteList.size();

		if (0 == iNumQuote) return null;

		int i = 0;
		double[] adblQuote = new double[iNumQuote];

		for (double dblQuote : _cashQuoteList)
			adblQuote[i++] = dblQuote;

		return adblQuote;
	}

	/**
	 * Retrieve the Array of Cash Tenors
	 * 
	 * @return The Array of Cash Tenors
	 */

	public String[] cashTenor()
	{
		if (null == _cashTenorList) return null;

		int iNumTenor = _cashTenorList.size();

		if (0 == iNumTenor) return null;

		int i = 0;
		String[] astrTenor = new String[iNumTenor];

		for (String strTenor : _cashTenorList)
			astrTenor[i++] = strTenor;

		return astrTenor;
	}

	/**
	 * Retrieve the Array of Future Quotes
	 * 
	 * @return The Array of Future Quotes
	 */

	public double[] futureQuote()
	{
		if (null == _lsFutureQuote) return null;

		int iNumQuote = _lsFutureQuote.size();

		if (0 == iNumQuote) return null;

		int i = 0;
		double[] adblQuote = new double[iNumQuote];

		for (double dblQuote : _lsFutureQuote)
			adblQuote[i++] = dblQuote;

		return adblQuote;
	}

	/**
	 * Retrieve the Array of Future Tenors
	 * 
	 * @return The Array of Future Tenors
	 */

	public String[] futureTenor()
	{
		if (null == _lsFutureTenor) return null;

		int iNumTenor = _lsFutureTenor.size();

		if (0 == iNumTenor) return null;

		int i = 0;
		String[] astrTenor = new String[iNumTenor];

		for (String strTenor : _lsFutureTenor)
			astrTenor[i++] = strTenor;

		return astrTenor;
	}

	/**
	 * Retrieve the Array of Swap Quotes
	 * 
	 * @return The Array of Swap Quotes
	 */

	public double[] swapQuote()
	{
		if (null == _lsSwapQuote) return null;

		int iNumQuote = _lsSwapQuote.size();

		if (0 == iNumQuote) return null;

		int i = 0;
		double[] adblQuote = new double[iNumQuote];

		for (double dblQuote : _lsSwapQuote)
			adblQuote[i++] = dblQuote;

		return adblQuote;
	}

	/**
	 * Retrieve the Array of Swap Tenors
	 * 
	 * @return The Array of Swap Tenors
	 */

	public String[] swapTenor()
	{
		if (null == _lsSwapTenor) return null;

		int iNumTenor = _lsSwapTenor.size();

		if (0 == iNumTenor) return null;

		int i = 0;
		String[] astrTenor = new String[iNumTenor];

		for (String strTenor : _lsSwapTenor)
			astrTenor[i++] = strTenor;

		return astrTenor;
	}
}
