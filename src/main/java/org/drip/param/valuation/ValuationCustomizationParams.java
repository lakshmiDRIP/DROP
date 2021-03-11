
package org.drip.param.valuation;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>ValuationCustomizationParams</i> holds the parameters needed to interpret the input quotes. It contains
 * the quote day count, the quote frequency, the quote EOM Adjustment, the quote Act/Act parameters, the
 * quote Calendar, the Core Collateralization Parameters, and the Switchable Alternate Collateralization
 * Parameters. It also indicates if the native quote is spread based.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation">Valuation Settlement and Valuation Customization Parameters</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ValuationCustomizationParams {
	private int _iYieldFrequency = 0;
	private boolean _bSpreadQuoted = false;
	private java.lang.String _strYieldDC = "";
	private boolean _bYieldApplyEOMAdj = false;
	private boolean _bApplyFlatForwardRate = false;
	private java.lang.String _strYieldCalendar = "";
	private org.drip.analytics.daycount.ActActDCParams _aapYield = null;

	/**
	 * Construct the BondEquivalent Instance of ValuationCustomizationParams
	 * 
	 * @param strCalendar The Calendar
	 * 
	 * @return The BondEquivalent Instance of ValuationCustomizationParams
	 */

	public static final ValuationCustomizationParams BondEquivalent (
		final java.lang.String strCalendar)
	{
		try {
			return new ValuationCustomizationParams ("30/360", 2, false, null, strCalendar, false, false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct ValuationCustomizationParams from the Day Count and the Frequency parameters
	 * 
	 * @param strDC Quoting Day Count
	 * @param iFrequency Quoting Frequency
	 * @param bApplyEOMAdj TRUE - Apply the EOM Adjustment
	 * @param aap - Quoting Act/Act Parameters
	 * @param strCalendar - Quoting Calendar
	 * @param bSpreadQuoted - TRUE - Market Quotes are Spread Quoted
	 * @param bApplyFlatForwardRate - TRUE - Apply Flat Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ValuationCustomizationParams (
		final java.lang.String strDC,
		final int iFrequency,
		final boolean bApplyEOMAdj,
		final org.drip.analytics.daycount.ActActDCParams aap,
		final java.lang.String strCalendar,
		final boolean bSpreadQuoted,
		final boolean bApplyFlatForwardRate)
		throws java.lang.Exception
	{
		if (null == strDC || strDC.isEmpty() || 0 == iFrequency)
			throw new java.lang.Exception ("ValuationCustomizationParams ctr: Invalid quoting params!");

		_aapYield = aap;
		_strYieldDC = strDC;
		_iYieldFrequency = iFrequency;
		_bSpreadQuoted = bSpreadQuoted;
		_strYieldCalendar = strCalendar;
		_bYieldApplyEOMAdj = bApplyEOMAdj;
		_bApplyFlatForwardRate = bApplyFlatForwardRate;
	}

	/**
	 * Retrieve the Yield Act Act Day Count Parameters
	 * 
	 * @return The Yield Act Act Day Count Parameters
	 */

	public org.drip.analytics.daycount.ActActDCParams yieldAAP()
	{
		return _aapYield;
	}

	/**
	 * Retrieve the Yield Day Count
	 * 
	 * @return The Yield Day Count
	 */

	public java.lang.String yieldDayCount()
	{
		return _strYieldDC;
	}

	/**
	 * Retrieve the Yield Frequency
	 * 
	 * @return The Yield Frequency
	 */

	public int yieldFreq()
	{
		return _iYieldFrequency;
	}

	/**
	 * Indicate if spread Quoted
	 * 
	 * @return TRUE - Spread Quoted
	 */

	public boolean spreadQuoted()
	{
		return _bSpreadQuoted;
	}

	/**
	 * Retrieve the Yield Calendar
	 * 
	 * @return The Yield Calendar
	 */

	public java.lang.String yieldCalendar()
	{
		return _strYieldCalendar;
	}

	/**
	 * Indicate if EOM Adjustment is to be made for the Yield Calculation
	 * 
	 * @return TRUE - EOM Adjustment is to be made for the Yield Calculation
	 */

	public boolean applyYieldEOMAdj()
	{
		return _bYieldApplyEOMAdj;
	}

	/**
	 * Indicate if Forward Rate is to be Projected using its Current Value
	 * 
	 * @return TRUE - Forward Rate is to be Projected using its Current Value
	 */

	public boolean applyFlatForwardRate()
	{
		return _bApplyFlatForwardRate;
	}
}
