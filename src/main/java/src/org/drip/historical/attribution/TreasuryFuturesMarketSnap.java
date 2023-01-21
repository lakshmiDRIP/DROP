
package org.drip.historical.attribution;

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
 * <i>TreasuryFuturesMarketSnap</i> contains the Metrics Snapshot associated with the relevant Manifest
 * Measures for the given Treasury Futures Position.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical State Processing Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/attribution/README.md">Position Market Change Components Attribution</a></li>
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
