
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
 * FundingFuturesClosesReconstitutor transforms the Funding Futures Closes- Feed Inputs into Formats
 *  suitable for Valuation Metrics and Sensitivities Generation.
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
