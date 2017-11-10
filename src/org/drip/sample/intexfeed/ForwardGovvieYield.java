
package org.drip.sample.intexfeed;

import org.drip.analytics.date.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.govvie.GovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * ForwardGovvieYield generates the Forward Govvie Yields over Monthly Increments with Maturity up to 60Y for
 *  different Govvie Tenors.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ForwardGovvieYield {

	private static final GovvieCurve GovvieCurve (
		final JulianDate dtSpot,
		final String strCode)
		throws Exception
	{
		return LatentMarketStateBuilder.GovvieCurve (
			strCode,
			dtSpot,
			new JulianDate[] {
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot
			},
			new JulianDate[] {
				dtSpot.addTenor ("1Y"),
				dtSpot.addTenor ("2Y"),
				dtSpot.addTenor ("3Y"),
				dtSpot.addTenor ("5Y"),
				dtSpot.addTenor ("7Y"),
				dtSpot.addTenor ("10Y"),
				dtSpot.addTenor ("20Y"),
				dtSpot.addTenor ("30Y")
			},
			new double[] {
				0.01219, //  1Y
				0.01391, //  2Y
				0.01590, //  3Y
				0.01937, //  5Y
				0.02200, //  7Y
				0.02378, // 10Y
				0.02677, // 20Y
				0.02927  // 30Y
			},
			new double[] {
				0.01219, //  1Y
				0.01391, //  2Y
				0.01590, //  3Y
				0.01937, //  5Y
				0.02200, //  7Y
				0.02378, // 10Y
				0.02677, // 20Y
				0.02927  // 30Y
			},
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		int iNumMonth = 720;
		String strCode = "UST";
		String[] astrGovvieTenor = new String[] {
			 "1M",
			 "3M",
			 "6M",
			 "1Y",
			 "2Y",
			 "3Y",
			 "5Y",
			 "7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.OCTOBER,
			5
		);

		GovvieCurve gc = GovvieCurve (
			dtSpot,
			strCode
		);

		System.out.println ("SpotDate,ForwardGap,ForwardTenor,ForwardStartDate,ForwardEndDate,ForwardYield");

		for (int i = 0; i <= iNumMonth; ++i) {
			JulianDate dtForward = dtSpot.addMonths (i);

			for (int j = 0; j < astrGovvieTenor.length; ++j) {
				JulianDate dtMaturity = dtForward.addTenor (astrGovvieTenor[j]);

				System.out.println (
					dtSpot + "," +
					j + "," +
					astrGovvieTenor[j] + "," +
					dtForward + "," +
					dtMaturity + "," +
					FormatUtil.FormatDouble (
						gc.forwardYield (
							dtForward.julian(),
							dtMaturity.julian()
						), 1, 8, 100.)
					+ "%"
				);
			}
		}
	}
}
