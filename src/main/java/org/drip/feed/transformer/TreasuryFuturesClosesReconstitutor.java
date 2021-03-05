
package org.drip.feed.transformer;

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
 * <i>TreasuryFuturesClosesReconstitutor</i> transforms the Treasury Futures Closes- Feed Inputs into Formats
 * suitable for Valuation Metrics and Sensitivities Generation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/README.md">Market Data Reconstitutive Feed Transformer</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesClosesReconstitutor {

	/**
	 * Regularize the Treasury Feed Closes
	 * 
	 * @param strClosesLocation The Closes Location
	 * @param iSpotDateIndex Spot Date Column Index
	 * @param iConversionFactorIndex Conversion Factor Column Index
	 * @param iCleanPriceIndex Clean Price Column Index
	 * @param iCTDCouponIndex CTD Coupon Column Index
	 * @param iEffectiveDateIndex Effective Date Column Index
	 * @param iMaturityDateIndex Maturity Date Column Index
	 * @param iExpiryProxyIndex Expiry Proxy Column Index
	 * 
	 * @return TRUE - The Regularization Successful
	 */

	public static final boolean RegularizeCloses (
		final java.lang.String strClosesLocation,
		final int iSpotDateIndex,
		final int iConversionFactorIndex,
		final int iCleanPriceIndex,
		final int iCTDCouponIndex,
		final int iEffectiveDateIndex,
		final int iMaturityDateIndex,
		final int iExpiryProxyIndex)
	{
		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid (strClosesLocation,
			true);

		if (null == csvGrid) return false;

		org.drip.analytics.date.JulianDate[] adtSpot = csvGrid.dateArrayAtColumn (iSpotDateIndex);

		if (null == adtSpot) return false;

		int iNumClose = adtSpot.length;
		org.drip.analytics.date.JulianDate[] adtExpiry = new org.drip.analytics.date.JulianDate[iNumClose];

		if (0 == iNumClose) return false;

		double[] adblConversionFactor = csvGrid.doubleArrayAtColumn (iConversionFactorIndex);

		if (null == adblConversionFactor || iNumClose != adblConversionFactor.length) return false;

		double[] adblCleanPrice = csvGrid.doubleArrayAtColumn (iCleanPriceIndex, 0.01);

		if (null == adblCleanPrice || iNumClose != adblCleanPrice.length) return false;

		double[] adblCoupon = csvGrid.doubleArrayAtColumn (iCTDCouponIndex, 0.01);

		if (null == adblCoupon || iNumClose != adblCoupon.length) return false;

		org.drip.analytics.date.JulianDate[] adtEffective = csvGrid.dateArrayAtColumn (iEffectiveDateIndex);

		if (null == adtEffective || iNumClose != adtEffective.length) return false;

		org.drip.analytics.date.JulianDate[] adtMaturity = csvGrid.dateArrayAtColumn (iMaturityDateIndex);

		if (null == adtMaturity || iNumClose != adtMaturity.length) return false;

		org.drip.analytics.date.JulianDate[] adtExpiryProxy = csvGrid.dateArrayAtColumn (iExpiryProxyIndex);

		if (null == adtExpiryProxy || iNumClose != adtExpiryProxy.length) return false;

		try {
			for (int i = 0; i < iNumClose; ++i) {
				if (null == adtExpiryProxy[i]) return false;

				int iExpiryProxyDate = adtExpiryProxy[i].julian();

				int iMonth = org.drip.analytics.date.DateUtil.Month (iExpiryProxyDate);

				int iYear = 2000 + org.drip.analytics.date.DateUtil.Date (iExpiryProxyDate);

				if (null == (adtExpiry[i] = org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth,
					org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear))))
					return false;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		System.out.println
			("CloseDate,ConversionFactor,CTDPrice,CTDCoupon,CTDEffective,CTDMaturity,FuturesExpiry");

		for (int i = 0; i < iNumClose; ++i)
			System.out.println (adtSpot[i] + "," + adblConversionFactor[i] + "," + adblCleanPrice[i] + "," +
				adblCoupon[i] + "," + adtEffective[i] + "," + adtMaturity[i] + "," + adtExpiry[i]);

		return true;
	}

	/**
	 * Regularize the UST Futures Closes Feed
	 * 
	 * @param strClosesLocation The UST Futures Closes Feed Location
	 * 
	 * @return TRUE - Regularization Successful
	 */

	public static final boolean USTRegularizeCloses (
		final java.lang.String strClosesLocation)
	{
		return RegularizeCloses (strClosesLocation, 0, 1, 4, 9, 10, 8, 13);
	}
}
