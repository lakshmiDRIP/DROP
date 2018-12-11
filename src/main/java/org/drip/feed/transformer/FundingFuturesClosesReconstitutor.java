
package org.drip.feed.transformer;

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
 * <i>FundingFuturesClosesReconstitutor</i> transforms the Funding Futures Closes- Feed Inputs into Formats
 * suitable for Valuation Metrics and Sensitivities Generation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Feed</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer">Transformer</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingFuturesClosesReconstitutor {

	/**
	 * Regularize the Funding Futures Feed Closes
	 * 
	 * @param strClosesLocation The Closes Location
	 * @param iSpotDateIndex Spot Date Column Index
	 * @param iLastPriceIndex Last Price Column Index
	 * @param iFuturesExpiryDateIndex Futures Expiry Date Column Index
	 * 
	 * @return TRUE - The Regularization is Successful
	 */

	public static final boolean RegularizeCloses (
		final java.lang.String strClosesLocation,
		final int iSpotDateIndex,
		final int iLastPriceIndex,
		final int iFuturesExpiryDateIndex)
	{
		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid (strClosesLocation,
			true);

		if (null == csvGrid) return false;

		org.drip.analytics.date.JulianDate[] adtSpot = csvGrid.dateArrayAtColumn (iSpotDateIndex);

		if (null == adtSpot) return false;

		int iNumClose = adtSpot.length;
		double[] adblForwardRate = new double[iNumClose];

		if (0 == iNumClose) return false;

		double[] adblLastPrice = csvGrid.doubleArrayAtColumn (iLastPriceIndex);

		if (null == adblLastPrice || iNumClose != adblLastPrice.length) return false;

		for (int i = 0; i < iNumClose; ++i)
			adblForwardRate[i] = 0.01 * (100. - adblLastPrice[i]);

		org.drip.analytics.date.JulianDate[] adtFuturesExpiry = csvGrid.dateArrayAtColumn
			(iFuturesExpiryDateIndex);

		if (null == adtFuturesExpiry || iNumClose != adtFuturesExpiry.length) return false;

		System.out.println ("CloseDate,Price,ForwardRate,FuturesExpiry");

		for (int i = 0; i < iNumClose; ++i)
			System.out.println (adtSpot[i] + "," + adblLastPrice[i] + "," + adblForwardRate[i] + "," +
				adtFuturesExpiry[i]);

		return true;
	}
}
