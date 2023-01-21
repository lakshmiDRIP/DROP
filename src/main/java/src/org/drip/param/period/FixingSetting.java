
package org.drip.param.period;

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
 * <i>FixingSetting</i> implements the custom setting parameters for the Latent State Fixing Settings.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period">Composite Composable Period Builder Settings</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixingSetting {

	/**
	 * Fixing Based off of the Start of the Composite Period
	 */

	public static final int FIXING_COMPOSITE_PERIOD_START = 1;

	/**
	 * Fixing Based off of the End of the Composite Period
	 */

	public static final int FIXING_COMPOSITE_PERIOD_END = 2;

	/**
	 * Fixing Based off of the Start of a Pre-determined Static Date
	 */

	public static final int FIXING_PRESET_STATIC = 4;

	private int _iType = -1;
	private int _iStaticDate = java.lang.Integer.MIN_VALUE;
	private org.drip.analytics.daycount.DateAdjustParams _dap = null;

	/**
	 * Validate the Type of FX Fixing
	 * 
	 * @param iType The FX Fixing Type
	 * 
	 * @return TRUE - FX Fixing is One of the Valid Types
	 */

	public static final boolean ValidateType (
		final int iType)
	{
		return FIXING_COMPOSITE_PERIOD_START == iType || FIXING_COMPOSITE_PERIOD_END == iType ||
			FIXING_PRESET_STATIC == iType;
	}

	/**
	 * FixingSetting Constructor
	 * 
	 * @param iType The Fixing Type
	 * @param dap The Fixing DAP
	 * @param iStaticDate Static Fixing Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public FixingSetting (
		final int iType,
		final org.drip.analytics.daycount.DateAdjustParams dap,
		final int iStaticDate)
		throws java.lang.Exception
	{
		if (!ValidateType (_iType = iType))
			throw new java.lang.Exception ("FixingSetting ctr: Invalid Inputs");

		_dap = dap;
		_iStaticDate = iStaticDate;
	}

	/**
	 * Retrieve the Fixing Type
	 * 
	 * @return The Fixing Type
	 */

	public int type()
	{
		return _iType;
	}

	/**
	 * Retrieve the Fixing DAP
	 * 
	 * @return The Fixing DAP
	 */

	public org.drip.analytics.daycount.DateAdjustParams dap()
	{
		return _dap;
	}

	/**
	 * Retrieve the Static Fixing Date
	 * 
	 * @return The Static Fixing Date
	 */

	public int staticDate()
	{
		return _iStaticDate;
	}
}
