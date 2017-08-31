
package org.drip.sample.govvie;

/*
 * Credit Product imports
 */

import org.drip.analytics.date.*;
import org.drip.param.valuation.*;
import org.drip.product.definition.*;
import org.drip.param.creator.*;
import org.drip.product.creator.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * NonlinearGovvieCurve contains a demo of construction and usage of the non-linear treasury discount curve
 *  from government bond inputs. It shows the following:
 * 	- Create on-the-run TSY bond set.
 * 	- Calibrate a discount curve off of the on-the-run yields and calculate the implied zeroes and DF's.
 * 	- Price an off-the-run TSY.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonlinearGovvieCurve {

	/*
	 * Sample demonstrating creation of simple fixed coupon treasury bond
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final Bond CreateTSYBond (
		final String strName,
		final double dblCoupon,
		final JulianDate dt,
		int iNumYears)
		throws Exception
	{
		return BondBuilder.CreateSimpleFixed (	// Simple Fixed Rate Bond
			strName,					// Name
			"USD",						// Fictitious Treasury Curve Name
			"",							// Empty Credit Curve
			dblCoupon,					// Bond Coupon
			2, 							// Frequency
			"Act/Act",					// Day Count
			dt, 						// Effective
			dt.addYears (iNumYears),	// Maturity
			null,						// Principal Schedule
			null
		);
	}

	/*
	 * Sample demonstrating creation of a set of the on-the-run treasury bonds
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final Bond[] CreateOnTheRunTSYBondSet (
		final JulianDate dt,
		final String[] astrTSYBondName,
		final int[] aiMaturityYear,
		final double[] adblCoupon)
		throws Exception
	{
		Bond aTSYBond[] = new Bond[astrTSYBondName.length];

		for (int i = 0; i < astrTSYBondName.length; ++i)
			aTSYBond[i] = CreateTSYBond (
				astrTSYBondName[i],
				adblCoupon[i],
				dt,
				aiMaturityYear[i]
			);

		return aTSYBond;
	}

	/*
	 * Sample demonstrating building of the treasury discount curve based off the on-the run instruments and their yields
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MergedDiscountForwardCurve BuildOnTheRunTSYDiscountCurve (
		final JulianDate dt,
		final Bond[] aTSYBond,
		final double[] adblCalibYield)
		throws Exception
	{
		String astrCalibMeasure[] = new String[aTSYBond.length];

		for (int i = 0; i < aTSYBond.length; ++i)
			astrCalibMeasure[i] = "Yield";

		return ScenarioDiscountCurveBuilder.NonlinearBuild (
			dt,
			"USD",
			aTSYBond,
			adblCalibYield,
			astrCalibMeasure,
			null
		);
	}

	/*
	 * Sample demonstrating calculation of the yields of the input on the run treasury instruments
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final double[] GetOnTheRunYield (
		final JulianDate dt,
		final MergedDiscountForwardCurve dcTSY,
		final Bond[] aTSYBond)
		throws Exception
	{
		double adblYield[] = new double[aTSYBond.length];

		for (int i = 0; i < aTSYBond.length; ++i) {
			double dblPrice = aTSYBond[i].priceFromFundingCurve (
				new ValuationParams (
					DateUtil.Today(),
					DateUtil.Today(),
					"USD"
				),
				MarketParamsBuilder.Discount (dcTSY),
				aTSYBond[i].maturityDate().julian(),
				1.,
				0.
			);

			System.out.println ("\tPrice[" + aTSYBond[i].name() + "]: " +
				FormatUtil.FormatDouble (dblPrice, 2, 3, 100.));

			double dblYield = aTSYBond[i].yieldFromPrice (
				new ValuationParams (
					DateUtil.Today(),
					DateUtil.Today(),
					"USD"
				),
				MarketParamsBuilder.Discount (dcTSY),
				null,
				dblPrice
			);

			System.out.println ("\tYield[" + aTSYBond[i].name() + "]: " +
				FormatUtil.FormatDouble (dblYield, 1, 3, 100.));
		}

		return adblYield;
	}

	/*
	 * This sample illustrates the construction and validation of the Treasury Curve API. It demonstrates the
	 * 	following:
	 * 	- Create the on-the-run treasury bonds.
	 * 	- Create the on-the-run treasury discount curve from the treasury bonds.
	 * 	- Compare the implied and the input yields for the on-the-run's.
	 * 	- Calculate the yield of an off-the-run instrument off of the on-the-run yield discount curve and
	 * 		cross verify it with the price.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void TreasuryCurveSample()
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		String strConfig = "";

		EnvManager.InitEnv (strConfig);

		/*
		 * Define name, maturity, coupon, and the market yield of the input on-the-run treasuries  
		 */

		final String[] astrTSYName = new String[] {"TSY2YON", "TSY3YON", "TSY5YON", "TSY7YON", "TSY10YON", "TSY30YON"};
		final int[] aiMaturityYear = new int[] {2, 3, 5, 7, 10, 30};
		final double[] adblCoupon = new double[] {0.0200, 0.0250, 0.0300, 0.0325, 0.0375, 0.0400};
		final double[] adblCalibYield = new double[] {0.0200, 0.0250, 0.0300, 0.0325, 0.0375, 0.0400};

		/*
		 * Create the on-the-run treasury bonds
		 */

		long lTime = System.nanoTime();

		Bond[] aTSYBond = CreateOnTheRunTSYBondSet (
			DateUtil.Today(),
			astrTSYName,
			aiMaturityYear,
			adblCoupon
		);

		/*
		 * Create the on-the-run treasury discount curve
		 */

		MergedDiscountForwardCurve dcTSY = BuildOnTheRunTSYDiscountCurve (
			DateUtil.Today(),
			aTSYBond,
			adblCalibYield
		);

		/*
		 * Compare the implied discount rate and input yields - in general they DO NOT match 
		 */

		for (int i = 0; i < astrTSYName.length; ++i) {
			String strTenor = aiMaturityYear[i] + "Y";

			System.out.println ("Zero[" + strTenor + "]: " + dcTSY.zero (strTenor) +
				"; Yield[" + strTenor + "]: " + adblCalibYield[i]);
		}

		System.out.println ("\n----\n");

		double[] adblYield = GetOnTheRunYield (
			DateUtil.Today(),
			dcTSY,
			aTSYBond
		);

		/*
		 * Compare the implied and the input yields for the on-the-run's - they DO match 
		 */

		for (int i = 0; i < astrTSYName.length; ++i) {
			String strTenor = aiMaturityYear[i] + "Y";

			System.out.println ("CalcYield[" + strTenor + "]: " + adblYield[i] + "; Input[" + strTenor + "]: " + adblCalibYield[i]);
		}

		/*
		 * Finally calculate the yield of an off-the-run instrument off of the on-the-run yield discount curve 
		 */

		/*
		 * Construct off-the-run
		 */

		int iOffTheRunMaturityYears = 10;

		Bond bondOffTheRun = BondBuilder.CreateSimpleFixed (	// Simple Fixed Rate Bond
			"USD" + iOffTheRunMaturityYears + "YOFF",
			"USD",
			"",
			0.0375,
			2,
			"Act/Act",
			DateUtil.Today(),
			DateUtil.Today().addYears (iOffTheRunMaturityYears),	// off-the-run
			null,
			null
		);

		/*
		 * Calculate price for off-the-run
		 */

		double dblPrice = bondOffTheRun.priceFromFundingCurve (
			new ValuationParams (
				DateUtil.Today(),
				DateUtil.Today(),
				"USD"
			),
			MarketParamsBuilder.Discount (dcTSY),
			bondOffTheRun.maturityDate().julian(),
			1.,
			0.
		);

		System.out.println ("\nOff-The-Run Price[" + iOffTheRunMaturityYears + "Y]: " + dblPrice);

		/*
		 * Calculate yield for off-the-run
		 */

		double dblYieldOffTheRun = bondOffTheRun.yieldFromPrice (
			new ValuationParams (
				DateUtil.Today(),
				DateUtil.Today(),
				"USD"
			),
			MarketParamsBuilder.Discount (dcTSY),
			null,
			dblPrice
		);

		System.out.println ("\nOff-The-Run Yield[" + iOffTheRunMaturityYears + "Y]: " + dblYieldOffTheRun);

		System.out.println ("\tTime => " + (System.nanoTime() - lTime) * 1.e-06 + " ms");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		TreasuryCurveSample();
	}
}
