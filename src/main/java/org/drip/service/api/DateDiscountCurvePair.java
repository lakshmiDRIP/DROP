
package org.drip.service.api;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DateDiscountCurvePair</i> contains the COB/Discount Curve Pair, and the corresponding computed outputs.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api">API</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DateDiscountCurvePair {
	private org.drip.analytics.date.JulianDate _dt = null;
	private org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
	private java.util.List<java.lang.String> _lsstrDump = null;

	/**
	 * DateDiscountCurvePair constructor
	 * 
	 * @param dt The COB
	 * @param dc The COB Discount Curve
	 * @param lsstrDump List of Output String Dump
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public DateDiscountCurvePair (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final java.util.List<java.lang.String> lsstrDump)
		throws java.lang.Exception
	{
		if (null == (_dt = dt)) throw new java.lang.Exception ("DateDiscountCurvePair ctr: Invalid Inputs");

		_dc = dc;
		_lsstrDump = lsstrDump;
	}

	/**
	 * Retrieve the COB
	 * 
	 * @return The COB
	 */

	public org.drip.analytics.date.JulianDate date()
	{
		return _dt;
	}

	/**
	 * Retrieve the Discount Curve
	 * 
	 * @return The Discount Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve dc()
	{
		return _dc;
	}

	/**
	 * Retrieve the Output Dump
	 * 
	 * @return The Output Dump
	 */

	public java.util.List<java.lang.String> output()
	{
		return _lsstrDump;
	}
}
