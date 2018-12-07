
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
 * <i>CreditCurveAPI</i> computes the Metrics associated the Credit Curve State.
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

public class CreditCurveAPI {

	/**
	 * Generate the Horizon Metrics for the Specified Inputs
	 * 
	 * @param dtSpot The Spot Date
	 * @param astrFundingFixingMaturityTenor Array of the Funding Fixing Curve Calibration Instrument Tenors
	 * @param adblFundingFixingQuote Array of the Funding Fixing Curve Calibration Instrument Quotes
	 * @param strFullCreditIndexName The Full Credit Index Name
	 * @param dblCreditIndexQuotedSpread The Credit Index Quoted Spread
	 * @param astrForTenor Array of the "For" Tenors
	 * 
	 * @return Map of the Dated Credit Curve Metrics
	 */

	public static final org.drip.historical.state.CreditCurveMetrics DailyMetrics (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrFundingFixingMaturityTenor,
		final double[] adblFundingFixingQuote,
		final java.lang.String strFullCreditIndexName,
		final double dblCreditIndexQuotedSpread,
		final java.lang.String[] astrForTenor)
	{
		if (null == dtSpot || null == astrFundingFixingMaturityTenor || null == adblFundingFixingQuote ||
			null == astrForTenor)
			return null;

		int iNumForTenor = astrForTenor.length;
		int iNumFundingFixingInstrument = astrFundingFixingMaturityTenor.length;

		if (0 == iNumFundingFixingInstrument || iNumFundingFixingInstrument != adblFundingFixingQuote.length
			|| 0 == iNumForTenor)
			return null;

		org.drip.market.otc.CreditIndexConvention cic =
			org.drip.market.otc.CreditIndexConventionContainer.ConventionFromFullName
				(strFullCreditIndexName);

		if (null == cic) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFundingFixing =
			org.drip.service.template.LatentMarketStateBuilder.FundingCurve (dtSpot, cic.currency(), null,
				null, "ForwardRate", null, "ForwardRate", astrFundingFixingMaturityTenor,
					adblFundingFixingQuote, "SwapRate",
						org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

		if (null == dcFundingFixing) return null;

		org.drip.state.credit.CreditCurve cc = org.drip.service.template.LatentMarketStateBuilder.CreditCurve
			(dtSpot, new org.drip.product.definition.CreditDefaultSwap[] {cic.indexCDS()}, new double[]
				{dblCreditIndexQuotedSpread}, "FairPremium", dcFundingFixing);

		if (null == cc) return null;

		try {
			org.drip.historical.state.CreditCurveMetrics ccm = new
				org.drip.historical.state.CreditCurveMetrics (dtSpot);

			for (int j = 0; j < iNumForTenor; ++j) {
				org.drip.analytics.date.JulianDate dtFor = dtSpot.addTenor (astrForTenor[j]);

				if (null == dtFor || !ccm.addSurvivalProbability (dtFor, cc.survival (dtFor)) ||
					!ccm.addRecoveryRate (dtFor, cc.recovery (dtFor)))
					continue;
			}

			return ccm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Horizon Metrics for the Specified Inputs
	 * 
	 * @param adtSpot Array of Horizon Dates
	 * @param astrFundingFixingMaturityTenor Array of the Funding Fixing Curve Calibration Instrument Tenors
	 * @param aadblFundingFixingQuote Array of the Funding Fixing Curve Calibration Instrument Quotes
	 * @param astrFullCreditIndexName Array of the Full Credit Index Names
	 * @param adblCreditIndexQuotedSpread Array of the Credit Index Quoted Spreads
	 * @param astrForTenor Array of the "For" Tenors
	 * 
	 * @return Map of the Dated Credit Curve Metrics
	 */

	public static final java.util.TreeMap<org.drip.analytics.date.JulianDate,
		org.drip.historical.state.CreditCurveMetrics> HorizonMetrics (
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final java.lang.String[] astrFundingFixingMaturityTenor,
			final double[][] aadblFundingFixingQuote,
			final java.lang.String[] astrFullCreditIndexName,
			final double[] adblCreditIndexQuotedSpread,
			final java.lang.String[] astrForTenor)
	{
		if (null == adtSpot || null == astrFundingFixingMaturityTenor || null == aadblFundingFixingQuote ||
			null == astrFullCreditIndexName || null == adblCreditIndexQuotedSpread || null == astrForTenor)
			return null;

		int iNumSpot = adtSpot.length;
		int iNumForTenor = astrForTenor.length;
		int iNumFundingFixingInstrument = astrFundingFixingMaturityTenor.length;

		if (0 == iNumSpot || iNumSpot != aadblFundingFixingQuote.length || iNumSpot !=
			astrFullCreditIndexName.length || iNumSpot != adblCreditIndexQuotedSpread.length || 0 ==
				iNumFundingFixingInstrument || 0 == iNumForTenor)
			return null;

		java.util.TreeMap<org.drip.analytics.date.JulianDate, org.drip.historical.state.CreditCurveMetrics>
			mapCCM = new java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.historical.state.CreditCurveMetrics>();

		for (int i = 0; i < iNumSpot; ++i) {
			org.drip.market.otc.CreditIndexConvention cic =
				org.drip.market.otc.CreditIndexConventionContainer.ConventionFromFullName
					(astrFullCreditIndexName[i]);

			if (null == cic) continue;

			org.drip.state.discount.MergedDiscountForwardCurve dcFundingFixing =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i], cic.currency(),
					null, null, "ForwardRate", null, "ForwardRate", astrFundingFixingMaturityTenor,
						aadblFundingFixingQuote[i], "SwapRate",
							org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

			if (null == dcFundingFixing) continue;

			org.drip.state.credit.CreditCurve cc =
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (adtSpot[i], new
					org.drip.product.definition.CreditDefaultSwap[] {cic.indexCDS()}, new double[]
						{adblCreditIndexQuotedSpread[i]}, "FairPremium", dcFundingFixing);

			if (null == cc) continue;

			try {
				org.drip.historical.state.CreditCurveMetrics ccm = new
					org.drip.historical.state.CreditCurveMetrics (adtSpot[i]);

				for (int j = 0; j < iNumForTenor; ++j) {
					org.drip.analytics.date.JulianDate dtFor = adtSpot[i].addTenor (astrForTenor[j]);

					if (null == dtFor) continue;

					if (!ccm.addSurvivalProbability (dtFor, cc.survival (dtFor)) || !ccm.addRecoveryRate
						(dtFor, cc.recovery (dtFor)))
						continue;
				}

				mapCCM.put (adtSpot[i], ccm);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				continue;
			}
		}

		return mapCCM;
	}
}
