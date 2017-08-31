
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.credit.BondComponent;
import org.drip.product.govvie.TreasuryComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
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
 * GovvieStateShifted demonstrates the Construction and Usage of Tenor Bumped Govvie Curves.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GovvieStateShifted {

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
		double dblBump = 0.0001;
		boolean bIsBumpProportional = false;

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

		Map<String, GovvieCurve> mapBumpedGovvieCurve = LatentMarketStateBuilder.BumpedGovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING,
			dblBump,
			bIsBumpProportional
		);

		TreasuryComponent[] aTreasury = TreasuryBuilder.FromCode (
			strCode,
			adtEffective,
			adtMaturity,
			adblCoupon
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		System.out.println ("\n\t|-------------------------------------------------------------------||");

		for (Map.Entry<String, GovvieCurve> meGovvie : mapBumpedGovvieCurve.entrySet()) {
			String strKey = meGovvie.getKey();

			if (!strKey.startsWith ("tsy")) continue;

			System.out.print ("\t|  [" + meGovvie.getKey() + "] => ");

			GovvieCurve gc = meGovvie.getValue();

			for (BondComponent treasury : aTreasury)
				System.out.print (FormatUtil.FormatDouble (treasury.yieldFromPrice (
					valParams,
					null,
					null,
					treasury.maturityDate().julian(),
					1.,
					treasury.priceFromYield (
						valParams,
						null,
						null,
						gc.yield (treasury.maturityDate().julian())
					)
				), 1, 4, 1.) + " |"
			);

			System.out.print ("|\n");
		}

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\n\t|-------------------------------------||");

		GovvieCurve gcBase = mapBumpedGovvieCurve.get ("Base");

		GovvieCurve gcBump = mapBumpedGovvieCurve.get ("Bump");

		for (TreasuryComponent treasury : aTreasury)
			System.out.println (
				"\t| YIELD => " +
				treasury.maturityDate() + " |" +
				FormatUtil.FormatDouble (treasury.yieldFromPrice (
					valParams,
					null,
					null,
					treasury.maturityDate().julian(),
					1.,
					treasury.priceFromYield (
						valParams,
						null,
						null,
						gcBase.yield (treasury.maturityDate().julian())
					)
				), 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (treasury.yieldFromPrice (
					valParams,
					null,
					null,
					treasury.maturityDate().julian(),
					1.,
					treasury.priceFromYield (
						valParams,
						null,
						null,
						gcBump.yield (treasury.maturityDate().julian())
					)
				), 1, 2, 100.) + "% ||"
			);

		System.out.println ("\t|-------------------------------------||");
	}
}
