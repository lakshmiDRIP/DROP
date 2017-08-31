
package org.drip.service.state;

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
 * FundingCurveAPI computes the Metrics associated the Funding Curve State.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FundingCurveAPI {

	/**
	 * Generate the Funding Curve Daily Metrics
	 * 
	 * @param dtSpot The Spot Date
	 * @param astrFixFloatMaturityTenor Array of Fix Float Maturity Tenors
	 * @param adblFixFloatQuote Array of Fix Float Swap Rates
	 * @param astrInTenor Array of "In" Tenors
	 * @param astrForTenor Array of "For" Tenors
	 * @param strCurrency Funding Currency
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Funding Curve Daily Metrics
	 */

	public static final org.drip.historical.state.FundingCurveMetrics DailyMetrics (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrFixFloatMaturityTenor,
		final double[] adblFixFloatQuote,
		final java.lang.String[] astrInTenor,
		final java.lang.String[] astrForTenor,
		final java.lang.String strCurrency,
		final int iLatentStateType)
	{
		if (null == dtSpot || null == astrFixFloatMaturityTenor || null == adblFixFloatQuote || null ==
			astrInTenor || null == astrForTenor)
			return null;

		int iNumInTenor = astrInTenor.length;
		int iNumForTenor = astrForTenor.length;
		int iNumFixFloatQuote = adblFixFloatQuote.length;
		double[] adblForTenorDCF = new double[iNumForTenor];
		int iNumCalibrationInstrument = astrFixFloatMaturityTenor.length;

		if (0 == iNumCalibrationInstrument || iNumFixFloatQuote != iNumCalibrationInstrument || 0 ==
			iNumInTenor || 0 == iNumForTenor)
			return null;

		for (int i = 0; i < iNumForTenor; ++i) {
			try {
				adblForTenorDCF[i] = org.drip.analytics.support.Helper.TenorToYearFraction (astrForTenor[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.template.LatentMarketStateBuilder.FundingCurve (dtSpot, strCurrency, null, null,
				"ForwardRate", null, "ForwardRate", astrFixFloatMaturityTenor, adblFixFloatQuote, "SwapRate",
					iLatentStateType);

		if (null == dcFunding) return null;

		try {
			org.drip.historical.state.FundingCurveMetrics fcm = new
				org.drip.historical.state.FundingCurveMetrics (dtSpot);

			for (java.lang.String strInTenor : astrInTenor) {
				org.drip.analytics.date.JulianDate dtIn = dtSpot.addTenor (strInTenor);

				for (int j = 0; j < iNumForTenor; ++j) {
					if (!fcm.addNativeForwardRate (strInTenor, astrForTenor[j], java.lang.Math.pow
						(dcFunding.df (dtIn) / dcFunding.df (dtIn.addTenor (astrForTenor[j])), 1. /
							adblForTenorDCF[j]) - 1.))
						continue;
				}
			}

			return fcm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Curve Horizon Metrics
	 * 
	 * @param adtSpot Array of Spot
	 * @param astrFixFloatMaturityTenor Array of Fix Float Maturity Tenors
	 * @param aadblFixFloatQuote Array of Fix Float Swap Rates
	 * @param astrInTenor Array of "In" Tenors
	 * @param astrForTenor Array of "For" Tenors
	 * @param strCurrency Funding Currency
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Funding Curve Horizon Metrics
	 */

	public static final java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.historical.state.FundingCurveMetrics> HorizonMetrics (
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final java.lang.String[] astrFixFloatMaturityTenor,
			final double[][] aadblFixFloatQuote,
			final java.lang.String[] astrInTenor,
			final java.lang.String[] astrForTenor,
			final java.lang.String strCurrency,
			final int iLatentStateType)
	{
		if (null == adtSpot || null == astrFixFloatMaturityTenor || null == aadblFixFloatQuote || null ==
			astrInTenor || null == astrForTenor)
			return null;

		int iNumClose = adtSpot.length;
		int iNumInTenor = astrInTenor.length;
		int iNumForTenor = astrForTenor.length;
		double[] adblForTenorDCF = new double[iNumForTenor];
		int iNumCalibrationInstrument = astrFixFloatMaturityTenor.length;

		if (0 == iNumClose || 0 == iNumCalibrationInstrument || 0 == iNumInTenor || 0 == iNumForTenor)
			return null;

		for (int i = 0; i < iNumForTenor; ++i) {
			try {
				adblForTenorDCF[i] = org.drip.analytics.support.Helper.TenorToYearFraction (astrForTenor[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		java.util.Map<org.drip.analytics.date.JulianDate, org.drip.historical.state.FundingCurveMetrics>
			mapFCM = new java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.historical.state.FundingCurveMetrics>();

		for (int i = 0; i < iNumClose; ++i) {
			org.drip.historical.state.FundingCurveMetrics fcm = null;
			int iNumFixFloatQuote = null == aadblFixFloatQuote[i] ? 0 : aadblFixFloatQuote[i].length;

			if (0 == iNumFixFloatQuote || iNumFixFloatQuote != iNumCalibrationInstrument) continue;

			org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i], strCurrency,
					null, null, "ForwardRate", null, "ForwardRate", astrFixFloatMaturityTenor,
						aadblFixFloatQuote[i], "SwapRate", iLatentStateType);

			if (null == dcFunding) continue;

			try {
				fcm = new org.drip.historical.state.FundingCurveMetrics (adtSpot[i]);

				for (java.lang.String strInTenor : astrInTenor) {
					org.drip.analytics.date.JulianDate dtIn = adtSpot[i].addTenor (strInTenor);

					for (int j = 0; j < iNumForTenor; ++j) {
						if (!fcm.addNativeForwardRate (strInTenor, astrForTenor[j], java.lang.Math.pow
							(dcFunding.df (dtIn) / dcFunding.df (dtIn.addTenor (astrForTenor[j])), 1. /
								adblForTenorDCF[j]) - 1.))
							continue;
					}
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				continue;
			}

			mapFCM.put (adtSpot[i], fcm);
		}

		return mapFCM;
	}

	/**
	 * Generate the Funding Curve Map
	 * 
	 * @param adtSpot Array of Spot
	 * @param astrFixFloatMaturityTenor Array of Fix Float Maturity Tenors
	 * @param aadblFixFloatQuote Array of Fix Float Swap Rates
	 * @param strCurrency Funding Currency
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Funding Curve Map
	 */

	public static final java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.state.discount.MergedDiscountForwardCurve> HistoricalMap (
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final java.lang.String[] astrFixFloatMaturityTenor,
			final double[][] aadblFixFloatQuote,
			final java.lang.String strCurrency,
			final int iLatentStateType)
	{
		if (null == adtSpot || null == astrFixFloatMaturityTenor || null == aadblFixFloatQuote) return null;

		int iNumClose = adtSpot.length;
		int iNumCalibrationInstrument = astrFixFloatMaturityTenor.length;

		if (0 == iNumClose || 0 == iNumCalibrationInstrument) return null;

		java.util.Map<org.drip.analytics.date.JulianDate, org.drip.state.discount.MergedDiscountForwardCurve>
			mapFundingCurve = new java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.state.discount.MergedDiscountForwardCurve>();

		for (int i = 0; i < iNumClose; ++i) {
			int iNumFixFloatQuote = null == aadblFixFloatQuote[i] ? 0 : aadblFixFloatQuote[i].length;

			if (0 == iNumFixFloatQuote || iNumFixFloatQuote != iNumCalibrationInstrument) continue;

			org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i], strCurrency,
					null, null, "ForwardRate", null, "ForwardRate", astrFixFloatMaturityTenor,
						aadblFixFloatQuote[i], "SwapRate", iLatentStateType);

			if (null == dcFunding) continue;

			mapFundingCurve.put (adtSpot[i], dcFunding);
		}

		return mapFundingCurve;
	}
}
