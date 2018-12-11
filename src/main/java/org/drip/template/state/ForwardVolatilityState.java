
package org.drip.template.state;

import org.drip.analytics.date.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.volatility.VolatilityCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ForwardVolatilityState</i> sets up the Calibration and the Construction of the Volatility Latent State
 * for the Forward Latent State and examine the Emitted Metrics.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/state">State</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ForwardVolatilityState {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strFRATenor = "3M";
		String strCurrency = "GBP";

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			19
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strFRATenor
		);

		MergedDiscountForwardCurve dcFunding = LatentMarketStateBuilder.SmoothFundingCurve (
			dtSpot,
			strCurrency,
			new String[] {
				 "30D",
				 "60D",
				 "91D",
				"182D",
				"273D"
			},
			new double[] {
				0.0668750,	//  30D
				0.0675000,	//  60D
				0.0678125,	//  91D
				0.0712500,	// 182D
				0.0750000	// 273D
			},
			"ForwardRate",
			null,
			"ForwardRate",
			new String[] {
				"2Y",
				"3Y",
				"4Y",
				"5Y",
				"7Y",
				"10Y"
			},
			new double[] {
				0.08265,    //  2Y
				0.08550,    //  3Y
				0.08655,    //  4Y
				0.08770,    //  5Y
				0.08910,    //  7Y
				0.08920     // 10Y
			},
			"SwapRate"
		);

		String[] astrMaturityTenor = new String[] {
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblStrike = new double[] {
			0.0788, //  "1Y",
			0.0839, // 	"2Y",
			0.0864, //  "3Y",
			0.0869, //  "4Y",
			0.0879, //  "5Y",
			0.0890, //  "7Y",
			0.0889  // "10Y"
		};

		double[] adblPrice = new double[] {
			0.0017, //  "1Y",
			0.0132, // 	"2Y",
			0.0234, //  "3Y",
			0.0343, //  "4Y",
			0.0491, //  "5Y",
			0.0868, //  "7Y"
			0.1175  // "10Y"
		};

		VolatilityCurve vcForward = LatentMarketStateBuilder.ForwardRateVolatilityCurve (
			dtSpot,
			forwardLabel,
			true,
			astrMaturityTenor,
			adblStrike,
			adblPrice,
			"Price",
			dcFunding,
			dcFunding.nativeForwardCurve (strFRATenor)
		);

		String strLatentStateLabel = vcForward.label().fullyQualifiedName();

		System.out.println ("\n\n\t||-----------------------------------------------------------------------------||");

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  CAP PRICE  | " +
				astrMaturityTenor[i] + " | " + FormatUtil.FormatDouble (adblPrice[i], 1, 4, 1.) +
				" | Forward Implied Vol | " +
				FormatUtil.FormatDouble (vcForward.impliedVol (astrMaturityTenor[i]), 2, 2, 100.) +
				"% ||"
			);

		System.out.println ("\t||-----------------------------------------------------------------------------||\n");
	}
}
