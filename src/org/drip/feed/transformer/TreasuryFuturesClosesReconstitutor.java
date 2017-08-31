
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * TreasuryFuturesClosesReconstitutor transforms the Treasury Futures Closes- Feed Inputs into Formats
 *  suitable for Valuation Metrics and Sensitivities Generation.
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
