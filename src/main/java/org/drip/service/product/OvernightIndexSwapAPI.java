
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>OvernightIndexSwapAPI</i> exposes the Pricing and the Scenario Runs for an Overnight Index Swap.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product/README.md">Product Horizon PnL Attribution Decomposition</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OvernightIndexSwapAPI {

	/**
	 * Generate Full Set of Metrics for the Specified OIS
	 * 
	 * @param strOISCurrency OIS Currency
	 * @param strOISTenor OIS Tenor
	 * @param dblOISCoupon OIS Coupon
	 * @param iSpotDate Spot Date
	 * @param astrOvernightCurveDepositTenor Overnight Curve Calibration Deposit Tenor
	 * @param adblOvernightCurveDepositQuote Overnight Tenor Calibration Deposit Quote
	 * @param astrOvernightCurveOISTenor Overnight Curve Calibration OIS Tenor
	 * @param adblOvernightCurveOISQuote Overnight Curve Calibration OIS Quote
	 * @param bFund TRUE - Floater Based off of Fund
	 * 
	 * @return Map of Valuation Metrics
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> ValuationMetrics (
		final java.lang.String strOISCurrency,
		final java.lang.String strOISTenor,
		final double dblOISCoupon,
		final int iSpotDate,
		final java.lang.String[] astrOvernightCurveDepositTenor,
		final double[] adblOvernightCurveDepositQuote,
		final java.lang.String[] astrOvernightCurveOISTenor,
		final double[] adblOvernightCurveOISQuote,
		final boolean bFund)
	{
		org.drip.service.env.EnvManager.InitEnv ("");

		org.drip.analytics.date.JulianDate dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

		org.drip.state.discount.MergedDiscountForwardCurve dcOvernight =
			org.drip.service.template.LatentMarketStateBuilder.SmoothOvernightCurve (dtSpot, strOISCurrency,
				astrOvernightCurveDepositTenor, adblOvernightCurveDepositQuote, "Rate",
					astrOvernightCurveOISTenor, adblOvernightCurveOISQuote, "SwapRate", null, null, null,
						"SwapRate", null, null, "SwapRate");

		if (null == dcOvernight) return null;

		org.drip.product.rates.FixFloatComponent oisFixFloat =
			org.drip.service.template.OTCInstrumentBuilder.OISFixFloat (dtSpot, strOISCurrency, strOISTenor,
				dblOISCoupon, bFund);

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		return csqc.setFundingState (dcOvernight) ? oisFixFloat.value
			(org.drip.param.valuation.ValuationParams.Spot (iSpotDate), null, csqc, null) : null;
	}
}
