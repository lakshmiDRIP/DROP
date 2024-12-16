
package org.drip.service.state;

import org.drip.analytics.date.JulianDate;
import org.drip.historical.state.CreditCurveMetrics;
import org.drip.market.otc.CreditIndexConvention;
import org.drip.market.otc.CreditIndexConventionContainer;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>CreditCurveAPI</i> computes the Metrics associated the Credit Curve State. It provides the following
 * 	Functionality:
 *
 *  <ul>
 * 		<li>Construct an Instance of the Australian Treasury AUD AGB Bond</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/state/README.md">Curve Based State Metric Generator</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditCurveAPI
{

	/**
	 * Generate the Horizon Metrics for the Specified Inputs
	 * 
	 * @param spotDate The Spot Date
	 * @param fundingFixingMaturityTenorArray Array of the Funding Fixing Curve Calibration Instrument Tenors
	 * @param fundingFixingQuoteArray Array of the Funding Fixing Curve Calibration Instrument Quotes
	 * @param fullCreditIndexName The Full Credit Index Name
	 * @param creditIndexQuotedSpread The Credit Index Quoted Spread
	 * @param forTenorArray Array of the "For" Tenors
	 * 
	 * @return Map of the Dated Credit Curve Metrics
	 */

	public static final CreditCurveMetrics DailyMetrics (
		final JulianDate spotDate,
		final String[] fundingFixingMaturityTenorArray,
		final double[] fundingFixingQuoteArray,
		final String fullCreditIndexName,
		final double creditIndexQuotedSpread,
		final String[] forTenorArray)
	{
		if (null == spotDate ||
			null == fundingFixingMaturityTenorArray || 0 == fundingFixingMaturityTenorArray.length ||
			null == fundingFixingQuoteArray ||
			null == forTenorArray || 0 == forTenorArray.length ||
			0 == fundingFixingMaturityTenorArray.length ||
			fundingFixingMaturityTenorArray.length != fundingFixingQuoteArray.length)
		{
			return null;
		}

		CreditIndexConvention creditIndexConvention = CreditIndexConventionContainer.ConventionFromFullName (
			fullCreditIndexName
		);

		if (null == creditIndexConvention) {
			return null;
		}

		MergedDiscountForwardCurve fundingFixingDiscountCurve = LatentMarketStateBuilder.FundingCurve (
			spotDate,
			creditIndexConvention.currency(),
			null,
			null,
			"ForwardRate",
			null,
			"ForwardRate",
			fundingFixingMaturityTenorArray,
			fundingFixingQuoteArray,
			"SwapRate",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);

		if (null == fundingFixingDiscountCurve) {
			return null;
		}

		CreditCurve creditCurve = LatentMarketStateBuilder.CreditCurve (
			spotDate,
			new CreditDefaultSwap[] {creditIndexConvention.indexCDS()},
			new double[] {creditIndexQuotedSpread},
			"FairPremium",
			fundingFixingDiscountCurve
		);

		if (null == creditCurve) {
			return null;
		}

		try {
			CreditCurveMetrics creditCurveMetrics = new CreditCurveMetrics (spotDate);

			for (int forTenorIndex = 0; forTenorIndex < forTenorArray.length; ++forTenorIndex) {
				JulianDate forDate = spotDate.addTenor (forTenorArray[forTenorIndex]);

				if (null == forDate ||
					!creditCurveMetrics.addSurvivalProbability (forDate, creditCurve.survival (forDate)) ||
					!creditCurveMetrics.addRecoveryRate (forDate, creditCurve.recovery (forDate)))
				{
					continue;
				}
			}

			return creditCurveMetrics;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Horizon Metrics for the Specified Inputs
	 * 
	 * @param aspotDate Array of Horizon Dates
	 * @param fundingFixingMaturityTenorArray Array of the Funding Fixing Curve Calibration Instrument Tenors
	 * @param afundingFixingQuoteArray Array of the Funding Fixing Curve Calibration Instrument Quotes
	 * @param afullCreditIndexName Array of the Full Credit Index Names
	 * @param acreditIndexQuotedSpread Array of the Credit Index Quoted Spreads
	 * @param forTenorArray Array of the "For" Tenors
	 * 
	 * @return Map of the Dated Credit Curve Metrics
	 */

	public static final java.util.TreeMap<JulianDate, CreditCurveMetrics> HorizonMetrics (
		final JulianDate[] aspotDate,
		final String[] fundingFixingMaturityTenorArray,
		final double[][] afundingFixingQuoteArray,
		final String[] afullCreditIndexName,
		final double[] acreditIndexQuotedSpread,
		final String[] forTenorArray)
	{
		if (null == aspotDate || null == fundingFixingMaturityTenorArray || null == afundingFixingQuoteArray ||
			null == afullCreditIndexName || null == acreditIndexQuotedSpread || null == forTenorArray)
			return null;

		int iNumSpot = aspotDate.length;
		int iNumForTenor = forTenorArray.length;
		int iNumFundingFixingInstrument = fundingFixingMaturityTenorArray.length;

		if (0 == iNumSpot || iNumSpot != afundingFixingQuoteArray.length || iNumSpot !=
			afullCreditIndexName.length || iNumSpot != acreditIndexQuotedSpread.length || 0 ==
				iNumFundingFixingInstrument || 0 == iNumForTenor)
			return null;

		java.util.TreeMap<JulianDate, CreditCurveMetrics>
			mapCCM = new java.util.TreeMap<JulianDate,
				CreditCurveMetrics>();

		for (int i = 0; i < iNumSpot; ++i) {
			CreditIndexConvention creditIndexConvention =
				CreditIndexConventionContainer.ConventionFromFullName (afullCreditIndexName[i]);

			if (null == creditIndexConvention) continue;

			MergedDiscountForwardCurve dcFundingFixing =
				LatentMarketStateBuilder.FundingCurve (aspotDate[i], creditIndexConvention.currency(),
					null, null, "ForwardRate", null, "ForwardRate", fundingFixingMaturityTenorArray,
						afundingFixingQuoteArray[i], "SwapRate",
							LatentMarketStateBuilder.SHAPE_PRESERVING);

			if (null == dcFundingFixing) continue;

			CreditCurve cc =
				LatentMarketStateBuilder.CreditCurve (aspotDate[i], new
					CreditDefaultSwap[] {creditIndexConvention.indexCDS()}, new double[]
						{acreditIndexQuotedSpread[i]}, "FairPremium", dcFundingFixing);

			if (null == cc) continue;

			try {
				CreditCurveMetrics ccm = new
					CreditCurveMetrics (aspotDate[i]);

				for (int j = 0; j < iNumForTenor; ++j) {
					JulianDate dtFor = aspotDate[i].addTenor (forTenorArray[j]);

					if (null == dtFor) continue;

					if (!ccm.addSurvivalProbability (dtFor, cc.survival (dtFor)) || !ccm.addRecoveryRate
						(dtFor, cc.recovery (dtFor)))
						continue;
				}

				mapCCM.put (aspotDate[i], ccm);
			} catch (Exception e) {
				e.printStackTrace();

				continue;
			}
		}

		return mapCCM;
	}
}
