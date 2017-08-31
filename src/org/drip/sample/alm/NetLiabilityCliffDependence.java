
package org.drip.sample.alm;

import org.drip.portfolioconstruction.alm.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * NetLiabilityAgeDependence demonstrates the Dependence of the Outstanding Value on Investor Cliff/Horizon
 * 	Settings.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityCliffDependence {

	private static void LiabilityRun (
		final ExpectedNonFinancialIncome enfi,
		final ExpectedBasicConsumption ebc,
		final double dblAfterTaxIncome,
		final double dblEndAge,
		final DiscountRate dr,
		final double dblStartAge,
		final double dblRetirementAge,
		final double dblMaximumAge)
		throws Exception
	{
		NetLiabilityStream nls = new NetLiabilityStream (
			new InvestorCliffSettings (
				dblRetirementAge,
				dblMaximumAge
			),
			enfi,
			ebc,
			dblAfterTaxIncome
		);

		NetLiabilityMetrics nlm = nls.metrics (
			dblStartAge,
			dblEndAge,
			dr
		);

		System.out.println (
			"\t|| [" +
			FormatUtil.FormatDouble (dblStartAge, 2, 0, 1.) + " |" +
			FormatUtil.FormatDouble (dblRetirementAge, 2, 0, 1.) + " |" +
			FormatUtil.FormatDouble (dblMaximumAge, 2, 0, 1.) + "] => " +
			FormatUtil.FormatDouble (nlm.workingAgeIncomePV(), 5, 0, 1.) + " |" +
			FormatUtil.FormatDouble (nlm.pensionBenefitsIncomePV(), 5, 0, 1.) + " |" +
			FormatUtil.FormatDouble (nlm.basicConsumptionPV(), 5, 0, 1.) + " ||"
		);
	}

	public static void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblAfterTaxIncome = 800.;
		double dblIncomeReplacementRate  = 0.35;
		double dblWorkingAgeConsumptionRate = 0.80;
		double dblRetirementAgeConsumptionRate = 0.60;
		double dblEndAge = 105.;

		double dblYield = -0.0020;
		double dblBasicConsumptionSpread = 0.0100;
		double dblWorkingAgeIncomeSpread = 0.0100;
		double dblPensionBenefitsIncomeSpread = 0.0100;

		double[] adblStartAge = new double[] {
			40.,
			45.,
			50.
		};
		double[] adblRetirementAge = new double[] {
			58.,
			65.,
			72.
		};
		double[] adblMaximumAge = new double[] {
			77.,
			85.,
			93.
		};

		ExpectedNonFinancialIncome enfi = new ExpectedNonFinancialIncome (dblIncomeReplacementRate);

		ExpectedBasicConsumption ebc = new ExpectedBasicConsumption (
			dblWorkingAgeConsumptionRate,
			dblRetirementAgeConsumptionRate
		);

		DiscountRate dr = new DiscountRate (
			dblYield,
			dblWorkingAgeIncomeSpread,
			dblPensionBenefitsIncomeSpread,
			dblBasicConsumptionSpread
		);

		System.out.println();

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||  L -> R:                                  ||");

		System.out.println ("\t||          - Start Age                      ||");

		System.out.println ("\t||          - Retirement Age                 ||");

		System.out.println ("\t||          - Maximum Age                    ||");

		System.out.println ("\t||-------------------------------------------||");

		System.out.println ("\t||          - Working Age Income PV          ||");

		System.out.println ("\t||          - Pension Benefits Income PV     ||");

		System.out.println ("\t||          - Basic Consumption PV           ||");

		System.out.println ("\t||-------------------------------------------||");

		for (double dblStartAge : adblStartAge) {
			for (double dblRetirementAge : adblRetirementAge) {
				for (double dblMaximumAge : adblMaximumAge)
					LiabilityRun (
						enfi,
						ebc,
						dblAfterTaxIncome,
						dblEndAge,
						dr,
						dblStartAge,
						dblRetirementAge,
						dblMaximumAge
					);
			}
		}

		System.out.println ("\t||-------------------------------------------||");

		System.out.println();
	}
}
