
package org.drip.service.state;

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
 * <i>OvernightCurveAPI</i> computes the Metrics associated the Overnight Curve State.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/state">Latent State API</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OvernightCurveAPI {

	/**
	 * Generate the Overnight Curve Horizon Metrics for the Specified Date
	 * 
	 * @param dtSpot The Spot Date
	 * @param astrOvernightCurveOISTenor Array of Overnight Curve Fix Float OIS Maturity Tenors
	 * @param adblOvernightCurveOISQuote Array of Overnight Curve OIS Rates
	 * @param astrInTenor Array of "In" Tenors
	 * @param astrForTenor Array of "For" Tenors
	 * @param strCurrency Overnight Currency
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Overnight Curve Horizon Metrics
	 */

	public static final org.drip.historical.state.FundingCurveMetrics DailyMetrics (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrOvernightCurveOISTenor,
		final double[] adblOvernightCurveOISQuote,
		final java.lang.String[] astrInTenor,
		final java.lang.String[] astrForTenor,
		final java.lang.String strCurrency,
		final int iLatentStateType)
	{
		if (null == dtSpot || null == astrOvernightCurveOISTenor || null == adblOvernightCurveOISQuote ||
			null == astrInTenor || null == astrForTenor)
			return null;

		int iNumInTenor = astrInTenor.length;
		int iNumForTenor = astrForTenor.length;
		double[] adblForTenorDCF = new double[iNumForTenor];
		int iNumCalibrationInstrument = astrOvernightCurveOISTenor.length;
		int iNumOISQuote = null == adblOvernightCurveOISQuote ? 0 : adblOvernightCurveOISQuote.length;

		if (0 == iNumCalibrationInstrument || 0 == iNumInTenor || 0 == iNumForTenor || iNumOISQuote !=
			iNumCalibrationInstrument)
			return null;

		for (int i = 0; i < iNumForTenor; ++i) {
			try {
				adblForTenorDCF[i] = org.drip.analytics.support.Helper.TenorToYearFraction (astrForTenor[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.state.discount.MergedDiscountForwardCurve dcOvernight =
			org.drip.service.template.LatentMarketStateBuilder.OvernightCurve (dtSpot, strCurrency, null,
				null, "Rate", astrOvernightCurveOISTenor, adblOvernightCurveOISQuote, "SwapRate", null, null,
					null, "SwapRate", null, null, "SwapRate", iLatentStateType);

		if (null == dcOvernight) return null;

		try {
			org.drip.historical.state.FundingCurveMetrics fcm = new
				org.drip.historical.state.FundingCurveMetrics (dtSpot);

			for (java.lang.String strInTenor : astrInTenor) {
				org.drip.analytics.date.JulianDate dtIn = dtSpot.addTenor (strInTenor);

				for (int j = 0; j < iNumForTenor; ++j) {
					if (!fcm.addNativeForwardRate (strInTenor, astrForTenor[j], java.lang.Math.pow
						(dcOvernight.df (dtIn) / dcOvernight.df (dtIn.addTenor (astrForTenor[j])), 1. /
							adblForTenorDCF[j]) - 1.))
						return null;
				}
			}

			return fcm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Overnight Curve Horizon Metrics For an Array of Closing Dates
	 * 
	 * @param adtSpot Array of Spot
	 * @param astrOvernightCurveOISTenor Array of Overnight Curve Fix Float OIS Maturity Tenors
	 * @param aadblOvernightCurveOISQuote Array of Overnight Curve OIS Rates
	 * @param astrInTenor Array of "In" Tenors
	 * @param astrForTenor Array of "For" Tenors
	 * @param strCurrency Overnight Currency
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Overnight Curve Horizon Metrics
	 */

	public static final java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.historical.state.FundingCurveMetrics> HorizonMetrics (
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final java.lang.String[] astrOvernightCurveOISTenor,
			final double[][] aadblOvernightCurveOISQuote,
			final java.lang.String[] astrInTenor,
			final java.lang.String[] astrForTenor,
			final java.lang.String strCurrency,
			final int iLatentStateType)
	{
		if (null == adtSpot || null == astrOvernightCurveOISTenor || null == aadblOvernightCurveOISQuote ||
			null == astrInTenor || null == astrForTenor)
			return null;

		int iNumClose = adtSpot.length;
		int iNumInTenor = astrInTenor.length;
		int iNumForTenor = astrForTenor.length;
		double[] adblForTenorDCF = new double[iNumForTenor];
		int iNumCalibrationInstrument = astrOvernightCurveOISTenor.length;

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
			int iNumOISQuote = null == aadblOvernightCurveOISQuote[i] ? 0 :
				aadblOvernightCurveOISQuote[i].length;

			if (0 == iNumOISQuote || iNumOISQuote != iNumCalibrationInstrument) continue;

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernight =
				org.drip.service.template.LatentMarketStateBuilder.OvernightCurve (adtSpot[i], strCurrency,
					null, null, "Rate", astrOvernightCurveOISTenor, aadblOvernightCurveOISQuote[i],
						"SwapRate", null, null, null, "SwapRate", null, null, "SwapRate", iLatentStateType);

			if (null == dcOvernight) continue;

			try {
				fcm = new org.drip.historical.state.FundingCurveMetrics (adtSpot[i]);

				for (java.lang.String strInTenor : astrInTenor) {
					org.drip.analytics.date.JulianDate dtIn = adtSpot[i].addTenor (strInTenor);

					for (int j = 0; j < iNumForTenor; ++j) {
						if (!fcm.addNativeForwardRate (strInTenor, astrForTenor[j], java.lang.Math.pow
							(dcOvernight.df (dtIn) / dcOvernight.df (dtIn.addTenor (astrForTenor[j])), 1. /
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
}
