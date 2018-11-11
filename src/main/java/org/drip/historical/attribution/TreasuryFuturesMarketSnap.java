
package org.drip.historical.attribution;

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
 * <i>TreasuryFuturesMarketSnap</i> contains the Metrics Snapshot associated with the relevant Manifest
 * Measures for the given Treasury Futures Position.
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical">Historical</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/attribution">Attribution</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesMarketSnap extends org.drip.historical.attribution.PositionMarketSnap {

	/**
	 * TreasuryFuturesMarketSnap Constructor
	 * 
	 * @param dtSnap The Snapshot Date
	 * @param dblMarketValue The Snapshot Market Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TreasuryFuturesMarketSnap (
		final org.drip.analytics.date.JulianDate dtSnap,
		final double dblMarketValue)
		throws java.lang.Exception
	{
		super (dtSnap, dblMarketValue);
	}

	/**
	 * Set the Yield Level and Position Sensitivity
	 * 
	 * @param dblYield The Yield Level
	 * @param dblYieldSensitivity The Position Yield Sensitivity
	 * @param dblYieldRollDown The Position Yield Roll Down
	 * 
	 * @return TRUE - The Yield Level and the Position Sensitivity successfully set
	 */

	public boolean setYieldMarketFactor (
		final double dblYield,
		final double dblYieldSensitivity,
		final double dblYieldRollDown)
	{
		return addManifestMeasureSnap ("Yield", dblYield, dblYieldSensitivity, dblYieldRollDown);
	}

	/**
	 * Set the Expiry Date
	 * 
	 * @param dtExpiry The Expiry Date
	 * 
	 * @return TRUE - The Expiry Date successfully set
	 */

	public boolean setExpiryDate (
		final org.drip.analytics.date.JulianDate dtExpiry)
	{
		return setDate ("Expiry", dtExpiry);
	}

	/**
	 * Retrieve the Expiry Date
	 * 
	 * @return The Expiry Date
	 */

	public org.drip.analytics.date.JulianDate expiryDate()
	{
		return date ("Expiry");
	}

	/**
	 * Set the CTD Bond Name
	 * 
	 * @param strCTDName Name of the CTD Bond
	 * 
	 * @return TRUE - The CTD Bond Name successfully set
	 */

	public boolean setCTDName (
		final java.lang.String strCTDName)
	{
		return setC1 ("CTDBondName", strCTDName);
	}

	/**
	 * Retrieve the CTD Name
	 * 
	 * @return The CTD Name
	 */

	public java.lang.String ctdName()
	{
		return c1 ("CTDBondName");
	}

	/**
	 * Set the Clean Expiry Price
	 * 
	 * @param dblExpiryCleanPrice The Clean Price of the CTD at Expiry
	 * 
	 * @return TRUE - The Clean Expiry Price Successfully Set
	 */

	public boolean setCleanExpiryPrice (
		final double dblExpiryCleanPrice)
	{
		return setR1 ("ExpiryCleanPrice", dblExpiryCleanPrice);
	}

	/**
	 * Retrieve the Clean Price at Expiry
	 * 
	 * @return The Clean Price at Expiry
	 * 
	 * @throws java.lang.Exception Thrown if the Clean Price at Expiry cannot be obtained
	 */

	public double expiryCleanPrice()
		throws java.lang.Exception
	{
		return r1 ("ExpiryCleanPrice");
	}

	/**
	 * Set the CTD Conversion Factor at Expiry
	 * 
	 * @param dblConversionFactor The Conversion Factor at Expiry
	 * 
	 * @return TRUE - The CTD Conversion Factor at Expiry Successfully Set
	 */

	public boolean setConversionFactor (
		final double dblConversionFactor)
	{
		return setR1 ("ConversionFactor", dblConversionFactor);
	}

	/**
	 * Retrieve the CTD Conversion Factor at Expiry
	 * 
	 * @return The CTD Conversion Factor at Expiry
	 * 
	 * @throws java.lang.Exception Thrown if the CTD Conversion Factor cannot be obtained
	 */

	public double conversionFactor()
		throws java.lang.Exception
	{
		return r1 ("ConversionFactor");
	}
}
