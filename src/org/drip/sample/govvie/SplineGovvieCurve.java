
package org.drip.sample.govvie;

import org.drip.analytics.date.*;
import org.drip.product.credit.BondComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.TreasuryBuilder;
import org.drip.state.creator.ScenarioGovvieCurveBuilder;
import org.drip.state.govvie.GovvieCurve;

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
 * SplineGovvieCurve demonstrates the Construction and Usage of the Spline-based Govvie Curve.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SplineGovvieCurve {

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		String strTreasuryCode = "UST";
		String strCurrency = "USD";

		int[] aiMaturityDate = {
			dtSpot.addTenor ("01Y").julian(),
			dtSpot.addTenor ("02Y").julian(),
			dtSpot.addTenor ("03Y").julian(),
			dtSpot.addTenor ("05Y").julian(),
			dtSpot.addTenor ("07Y").julian(),
			dtSpot.addTenor ("10Y").julian(),
			dtSpot.addTenor ("30Y").julian()
		};

		double[] adblYield = {
			0.0113, // "01Y",
			0.0121, // "02Y",
			0.0127, // "03Y",
			0.0137, // "05Y",
			0.0145, // "07Y",
			0.0154, // "10Y"
			0.0198  // "30Y"
		};

		GovvieCurve gc = ScenarioGovvieCurveBuilder.CubicPolynomialCurve (
			strTreasuryCode,
			dtSpot,
			strTreasuryCode,
			strCurrency,
			aiMaturityDate,
			adblYield
		);

		JulianDate[] adtEffective = new JulianDate[] {
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot
		};

		JulianDate[] adtMaturity = new JulianDate[] {
			dtSpot.addTenor ("1Y"),
			dtSpot.addTenor ("2Y"),
			dtSpot.addTenor ("3Y"),
			dtSpot.addTenor ("5Y"),
			dtSpot.addTenor ("7Y"),
			dtSpot.addTenor ("10Y"),
			dtSpot.addTenor ("30Y")
		};

		BondComponent[] aTreasury = TreasuryBuilder.FromCode (
			strTreasuryCode,
			adtEffective,
			adtMaturity,
			adblYield
		);

		GovvieCurve gcCalib = ScenarioGovvieCurveBuilder.CubicPolyShapePreserver (
			strTreasuryCode,
			strTreasuryCode,
			strCurrency,
			dtSpot.julian(),
			aTreasury,
			adblYield,
			"Yield"
		);

		System.out.println();

		for (int i = 0; i < adblYield.length; ++i)
			System.out.println (
				"\t[" + new JulianDate (aiMaturityDate[i]) + "] => " +
				FormatUtil.FormatDouble (adblYield[i], 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (gc.yield (aiMaturityDate[i]), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (gcCalib.yield (aiMaturityDate[i]), 1, 2, 100.) + "% || "
			);
	}
}
