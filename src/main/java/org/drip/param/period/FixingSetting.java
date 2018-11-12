
package org.drip.param.period;

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
 * <i>FixingSetting</i> implements the custom setting parameters for the Latent State Fixing Settings.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period">Period</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
