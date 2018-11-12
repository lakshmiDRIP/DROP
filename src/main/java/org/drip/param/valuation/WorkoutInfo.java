
package org.drip.param.valuation;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>WorkoutInfo</i> is the place-holder for the work-out parameters. It contains the date, the factor, the
 * type, and the yield of the work-out.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation">Valuation</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class WorkoutInfo {

	/**
	 * Work out type Call
	 */

	public static final int WO_TYPE_CALL = 1;

	/**
	 * Work out type Put
	 */

	public static final int WO_TYPE_PUT = 2;

	/**
	 * Work out type Maturity
	 */

	public static final int WO_TYPE_MATURITY = 3;

	private int _iWOType = WO_TYPE_MATURITY;
	private int _iDate = java.lang.Integer.MIN_VALUE;
	private double _dblYield = java.lang.Double.NaN;
	private double _dblExerciseFactor = java.lang.Double.NaN;

	/**
	 * Constructor: Construct the class from the work-out date, yield, exercise factor, and type
	 * 
	 * @param iDate Work-out Date
	 * @param dblYield Work-out Yield
	 * @param dblExerciseFactor Work-out Factor
	 * @param iWOType Work out Type
	 * 
	 * @throws java.lang.Exception Thrown if input is invalid
	 */

	public WorkoutInfo (
		final int iDate,
		final double dblYield,
		final double dblExerciseFactor,
		final int iWOType)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblYield = dblYield) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExerciseFactor = dblExerciseFactor))
			throw new java.lang.Exception ("WorkoutInfo ctr: One of wkout dat/yld/ex factor came out NaN!");

		_iDate = iDate;
		_iWOType = iWOType;
	}

	/**
	 * Retrieve the Work-out Date
	 * 
	 * @return The Work-out Date
	 */

	public int date()
	{
		return _iDate;
	}

	/**
	 * Retrieve the Work-out Yield
	 * 
	 * @return The Work-out Yield
	 */

	public double yield()
	{
		return _dblYield;
	}

	/**
	 * Retrieve the Work-out Factor
	 * 
	 * @return The Work-out Factor
	 */

	public double factor()
	{
		return _dblExerciseFactor;
	}

	/**
	 * Retrieve the Work-out Type
	 * 
	 * @return The Work-out Type
	 */

	public int type()
	{
		return _iWOType;
	}
}
