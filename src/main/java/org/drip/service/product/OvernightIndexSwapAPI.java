
package org.drip.service.product;

import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.service.template.OTCInstrumentBuilder;
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
 * <i>OvernightIndexSwapAPI</i> exposes the Pricing and the Scenario Runs for an Overnight Index Swap. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate Full Set of Metrics for the Specified OIS</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product/README.md">Product Horizon PnL Attribution Decomposition</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OvernightIndexSwapAPI
{

	/**
	 * Generate Full Set of Metrics for the Specified OIS
	 * 
	 * @param oisCurrency OIS Currency
	 * @param oisTenor OIS Tenor
	 * @param oisCoupon OIS Coupon
	 * @param spotDate Spot Date
	 * @param overnightCurveDepositTenorArray Overnight Curve Calibration Deposit Tenor
	 * @param overnightCurveDepositQuoteArray Overnight Tenor Calibration Deposit Quote
	 * @param overnightCurveOISTenorArray Overnight Curve Calibration OIS Tenor
	 * @param overnightCurveOISQuoteArray Overnight Curve Calibration OIS Quote
	 * @param floaterBasedOffOfFunding TRUE - Floater Based off of Fund
	 * 
	 * @return Map of Valuation Metrics
	 */

	public static final Map<String, Double> ValuationMetrics (
		final String oisCurrency,
		final String oisTenor,
		final double oisCoupon,
		final int spotDate,
		final String[] overnightCurveDepositTenorArray,
		final double[] overnightCurveDepositQuoteArray,
		final String[] overnightCurveOISTenorArray,
		final double[] overnightCurveOISQuoteArray,
		final boolean floaterBasedOffOfFunding)
	{
		EnvManager.InitEnv ("");

		JulianDate julianSpotDate = new JulianDate (spotDate);

		MergedDiscountForwardCurve overnightDiscountCurve = LatentMarketStateBuilder.SmoothOvernightCurve (
			julianSpotDate,
			oisCurrency,
			overnightCurveDepositTenorArray,
			overnightCurveDepositQuoteArray,
			"Rate",
			overnightCurveOISTenorArray,
			overnightCurveOISQuoteArray,
			"SwapRate",
			null,
			null,
			null,
			"SwapRate",
			null,
			null,
			"SwapRate"
		);

		if (null == overnightDiscountCurve) {
			return null;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		return curveSurfaceQuoteContainer.setFundingState (overnightDiscountCurve) ?
			OTCInstrumentBuilder.OISFixFloat (
				julianSpotDate,
				oisCurrency,
				oisTenor,
				oisCoupon,
				floaterBasedOffOfFunding
			).value (
				ValuationParams.Spot (spotDate),
				null,
				curveSurfaceQuoteContainer,
				null
			) : null;
	}
}
