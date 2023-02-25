
package org.drip.service.state;

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
 * <i>CreditCurveAPI</i> computes the Metrics associated the Credit Curve State.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/state/README.md">Curve Based State Metric Generator</a></li>
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
