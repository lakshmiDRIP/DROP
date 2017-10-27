
package org.drip.sample.bondcustom;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * Repo generates the Full Suite of Replication Metrics for a Sample Repo Instrument.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Repo {

	private static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			"3D"
		};

		double[] adblDepositQuote = new double[] {
			0.0004,		// 1D
			0.0004		// 3D
		};

		String[] astrShortEndOISMaturityTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M"
		};

		double[] adblShortEndOISQuote = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		String[] astrOISFuturesEffectiveTenor = new String[] {
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		String[] astrOISFuturesMaturityTenor = new String[] {
			"1M",
			"1M",
			"1M",
			"1M",
			"1M"
		};

		double[] adblOISFuturesQuote = new double[] {
			 0.00046,    //   1M x 1M
			 0.00016,    //   2M x 1M
			-0.00007,    //   3M x 1M
			-0.00013,    //   4M x 1M
			-0.00014     //   5M x 1M
		};

		String[] astrLongEndOISMaturityTenor = new String[] {
			"15M",
			"18M",
			"21M",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y"
		};

		double[] adblLongEndOISQuote = new double[] {
			0.00002,    //  15M
			0.00008,    //  18M
			0.00021,    //  21M
			0.00036,    //   2Y
			0.00127,    //   3Y
			0.00274,    //   4Y
			0.00456,    //   5Y
			0.00647,    //   6Y
			0.00827,    //   7Y
			0.00996,    //   8Y
			0.01147,    //   9Y
			0.01280,    //  10Y
			0.01404,    //  11Y
			0.01516,    //  12Y
			0.01764,    //  15Y
			0.01939,    //  20Y
			0.02003,    //  25Y
			0.02038     //  30Y
		};

		return LatentMarketStateBuilder.SmoothOvernightCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"Rate",
			astrShortEndOISMaturityTenor,
			adblShortEndOISQuote,
			"SwapRate",
			astrOISFuturesEffectiveTenor,
			astrOISFuturesMaturityTenor,
			adblOISFuturesQuote,
			"SwapRate",
			astrLongEndOISMaturityTenor,
			adblLongEndOISQuote,
			"SwapRate"
		);
	}

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JULY,
			10
		);

		int iCouponFreq = 2;
		String strName = "Repo";
		String strCurrency = "USD";
		double dblMarketPrice = 1.;
		double dblRepoRate = 0.04; 
		double dblCouponRate = 0.0667; 
		String strCouponDayCount = "30/360";

		MergedDiscountForwardCurve dcOvernight = OvernightCurve (
			dtSpot,
			strCurrency
		);

		JulianDate dtBondEffective = DateUtil.CreateFromYMD (
			2010,
			3,
			18
		);

		JulianDate dtBondMaturity = DateUtil.CreateFromYMD (
			2030,
			4,
			7
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strName,
			strCurrency,
			strName,
			dblCouponRate,
			iCouponFreq,
			strCouponDayCount,
			dtBondEffective,
			dtBondMaturity,
			null,
			null
		);

		double dblCurrentYield = bond.yieldFromPrice (
			ValuationParams.Spot (dtSpot.julian()),
			null,
			null,
			dblMarketPrice
		);

		System.out.println ("Current Yield: " + dblCurrentYield);

		JulianDate dtRepoEffective = DateUtil.CreateFromYMD (
			2018,
			3,
			20
		);

		JulianDate dtRepoMaturity = DateUtil.CreateFromYMD (
			2018,
			9,
			20
		);

		double dblRepoEffectiveCleanPrice = bond.priceFromYield (
			ValuationParams.Spot (dtRepoEffective.julian()),
			null,
			null,
			dblCurrentYield
		);

		double dblRepoMaturityCleanPrice = bond.priceFromYield (
			ValuationParams.Spot (dtRepoMaturity.julian()),
			null,
			null,
			dblCurrentYield
		);

		System.out.println (dblRepoEffectiveCleanPrice + " | " + dblRepoMaturityCleanPrice);

		double dblRepoEffectiveDatePV = dblRepoEffectiveCleanPrice * dcOvernight.df (dtRepoEffective);

		double dblRepoMaturityDatePV = dblRepoMaturityCleanPrice * dcOvernight.df (dtRepoMaturity);

		System.out.println (dblRepoEffectiveDatePV + " | " + dblRepoMaturityDatePV);

		double dblBondAccrual = dblCouponRate * Convention.YearFraction (
			dtRepoEffective.julian(),
			dtRepoMaturity.julian(),
			strCouponDayCount,
			false,
			null,
			strCurrency
		);

		double dblBondLegValue = dblRepoEffectiveDatePV - dblRepoMaturityDatePV - dblBondAccrual;

		double dblRepoAccrual = dblRepoRate * Convention.YearFraction (
			dtRepoEffective.julian(),
			dtRepoMaturity.julian(),
			strCouponDayCount,
			false,
			null,
			strCurrency
		);

		double dblRepoValue = dblBondLegValue + dblRepoAccrual;

		System.out.println (dblRepoEffectiveDatePV + " | " + dblRepoMaturityDatePV);

		System.out.println (dblBondLegValue + " | " + dblRepoAccrual + " | " + dblRepoValue);
	}
}
