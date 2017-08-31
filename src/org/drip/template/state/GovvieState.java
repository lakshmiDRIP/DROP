
package org.drip.template.state;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * GovvieState sets up the Calibration and the Construction of the Govvie Latent State and examine the
 *  Emitted Metrics.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GovvieState {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today().addBusDays (
			0,
			"USD"
		);

		String strCode = "UST";

		JulianDate[] adtEffective = new JulianDate[] {
			DateUtil.CreateFromYMD (2010, DateUtil.SEPTEMBER, 21),
			DateUtil.CreateFromYMD (2009, DateUtil.JULY, 14),
			DateUtil.CreateFromYMD (2011, DateUtil.MARCH, 8),
			DateUtil.CreateFromYMD (2010, DateUtil.AUGUST, 25),
			DateUtil.CreateFromYMD (2010, DateUtil.DECEMBER, 3)
		};

		JulianDate[] adtMaturity = new JulianDate[] {
			dtSpot.addTenor ("2Y"),
			dtSpot.addTenor ("4Y"),
			dtSpot.addTenor ("5Y"),
			dtSpot.addTenor ("7Y"),
			dtSpot.addTenor ("10Y")
		};

		double[] adblCoupon = new double[] {
			0.0200,
			0.0250,
			0.0300,
			0.0325,
			0.0375
		};

		double[] adblYield = new double[] {
			0.0200,
			0.0250,
			0.0300,
			0.0325,
			0.0375
		};

		GovvieCurve gc = LatentMarketStateBuilder.ShapePreservingGovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield"
		);

		String strLatentStateLabel = gc.label().fullyQualifiedName();

		System.out.println ("\n\n\t||---------------------------------------------------------------------------------------||");

		for (int i = 0; i < adtEffective.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  TREASURY  | " +
				adtMaturity[i] + " | " + FormatUtil.FormatDouble (adblYield[i], 1, 2, 100.) +
				"% | Yield | " +
				FormatUtil.FormatDouble (gc.yield (adtMaturity[i]), 1, 2, 100.) +
				"% | Discount Factor | " +
				FormatUtil.FormatDouble (gc.df (adtMaturity[i]), 1, 4, 1.) +
				"  ||"
			);

		System.out.println ("\t||---------------------------------------------------------------------------------------||\n");
	}
}
