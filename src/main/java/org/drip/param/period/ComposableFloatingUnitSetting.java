
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
 * <i>ComposableFloatingUnitSetting</i> contains the cash flow period composable sub period details.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period">Period</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableFloatingUnitSetting extends org.drip.param.period.ComposableUnitBuilderSetting
{
	private int _iReferencePeriodArrearsType = -1;
	private double _dblSpread = java.lang.Double.NaN;
	private org.drip.state.identifier.FloaterLabel _floaterLabel = null;

	/**
	 * ComposableFloatingUnitSetting constructor
	 * 
	 * @param strTenor Unit Tenor
	 * @param iEdgeDateSequenceScheme Edge Date Generation Scheme
	 * @param dapEdge Date Adjust Parameter Settings for the Edge Dates
	 * @param floaterLabel Floater Label
	 * @param iReferencePeriodArrearsType Reference Period Arrears Type
	 * @param dblSpread Floater Spread
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public ComposableFloatingUnitSetting (
		final java.lang.String strTenor,
		final int iEdgeDateSequenceScheme,
		final org.drip.analytics.daycount.DateAdjustParams dapEdge,
		final org.drip.state.identifier.FloaterLabel floaterLabel,
		final int iReferencePeriodArrearsType,
		final double dblSpread)
		throws java.lang.Exception
	{
		super (strTenor, iEdgeDateSequenceScheme, dapEdge);

		if (null == (_floaterLabel = floaterLabel) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblSpread = dblSpread))
			throw new java.lang.Exception ("ComposableFloatingUnitSetting ctr: Invalid Inputs");

		_iReferencePeriodArrearsType = iReferencePeriodArrearsType;
	}

	/**
	 * Retrieve the Floater Label
	 * 
	 * @return The Floater Label
	 */

	public org.drip.state.identifier.FloaterLabel floaterLabel()
	{
		return _floaterLabel;
	}

	/**
	 * Retrieve the Reference Period Arrears Type
	 * 
	 * @return The Reference Period Arrears Type
	 */

	public int referencePeriodArrearsType()
	{
		return _iReferencePeriodArrearsType;
	}

	/**
	 * Retrieve the Floating Unit Spread
	 * 
	 * @return The Floating Unit Spread
	 */

	public double spread()
	{
		return _dblSpread;
	}
}
