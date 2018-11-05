
package org.drip.assetbacked.loan;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>Vintage</i> contains the Loan Origination Vintage Details - i.e., the Year/Month of Loan Origination.
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/assetbacked">Asset Backed</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/assetbacked/loan">Loan</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetBacked">Asset Backed Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Vintage {
	private int _iOriginationYear = -1;
	private int _iOriginationMonth = -1;

	/**
	 * Construct a Vintage Instance from the Origination Date
	 * 
	 * @param dtOrigination The Origination Date
	 * 
	 * @return Vintage Instance
	 */

	public static final Vintage Standard (
		final org.drip.analytics.date.JulianDate dtOrigination)
	{
		if (null == dtOrigination) return null;

		try {
			int iDate = dtOrigination.julian();

			return new Vintage (org.drip.analytics.date.DateUtil.Year (iDate),
				org.drip.analytics.date.DateUtil.Month (iDate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Vintage Constructor
	 * 
	 * @param iOriginationYear Loan Origination Year
	 * @param iOriginationMonth Loan Origination Month
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are not Valid
	 */

	public Vintage (
		final int iOriginationYear,
		final int iOriginationMonth)
		throws java.lang.Exception
	{
		if (0 > (_iOriginationYear = iOriginationYear) || 0 > (_iOriginationMonth = iOriginationMonth))
			throw new java.lang.Exception ("Vintage Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Origination Year
	 * 
	 * @return The Origination Year
	 */

	public int originationYear()
	{
		return _iOriginationYear;
	}

	/**
	 * Retrieve the Origination Month
	 * 
	 * @return The Origination Month
	 */

	public int originationMonth()
	{
		return _iOriginationMonth;
	}
}
