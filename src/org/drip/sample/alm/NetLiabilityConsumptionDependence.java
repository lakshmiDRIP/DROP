
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
 * NetLiabilityConsumptionDependence demonstrates the Dependence of the Outstanding Values on the Investor
 *  Consumption Settings.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityConsumptionDependence {

	private static final void LiabilityRun (
		final InvestorCliffSettings ics,
		final double dblAfterTaxIncome,
		final double dblStartAge,
		final double dblEndAge,
		final DiscountRate dr,
		final double dblIncomeReplacementRate,
		final double dblWorkingAgeConsumptionRate,
		final double dblRetirementAgeConsumptionRate)
		throws Exception
	{
		NetLiabilityStream nls = new NetLiabilityStream (
			ics,
			new ExpectedNonFinancialIncome (dblIncomeReplacementRate),
			new ExpectedBasicConsumption (
				dblWorkingAgeConsumptionRate,
				dblRetirementAgeConsumptionRate
			),
			dblAfterTaxIncome
		);

		NetLiabilityMetrics nlm = nls.metrics (
			dblStartAge,
			dblEndAge,
			dr
		);

		System.out.println (
			"\t|| [" +
			FormatUtil.FormatDouble (dblIncomeReplacementRate, 2, 0, 100.) + "% |" +
			FormatUtil.FormatDouble (dblWorkingAgeConsumptionRate, 2, 0, 100.) + "% |" +
			FormatUtil.FormatDouble (dblRetirementAgeConsumptionRate, 2, 0, 100.) + "%] => " +
			FormatUtil.FormatDouble (nlm.workingAgeIncomePV(), 5, 0, 1.) + " |" +
			FormatUtil.FormatDouble (nlm.pensionBenefitsIncomePV(), 5, 0, 1.) + " |" +
			FormatUtil.FormatDouble (nlm.basicConsumptionPV(), 5, 0, 1.) + " ||"
		);
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblAfterTaxIncome = 800.;
		double dblRetirementAge = 65.;
		double dblMaximumAge = 85.;
		double dblStartAge = 45.;
		double dblEndAge = 105.;

		double dblYield = -0.0020;
		double dblBasicConsumptionSpread = 0.0100;
		double dblWorkingAgeIncomeSpread = 0.0100;
		double dblPensionBenefitsIncomeSpread = 0.0100;

		double[] adblIncomeReplacementRate = new double[] {
			0.25,
			0.35,
			0.45
		};
		double[] adblWorkingAgeConsumptionRate = new double[] {
			0.70,
			0.80,
			0.90
		};
		double[] adblRetirementAgeConsumptionRate = new double[] {
			0.50,
			0.60,
			0.70
		};

		InvestorCliffSettings ics = new InvestorCliffSettings (
			dblRetirementAge,
			dblMaximumAge
		);

		DiscountRate dr = new DiscountRate (
			dblYield,
			dblWorkingAgeIncomeSpread,
			dblPensionBenefitsIncomeSpread,
			dblBasicConsumptionSpread
		);

		System.out.println();

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||  L -> R:                                     ||");

		System.out.println ("\t||          - Income Replacement Rate           ||");

		System.out.println ("\t||          - Working Age Consumption Rate      ||");

		System.out.println ("\t||          - Retirement Age Consumption Rate   ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||          - Working Age Income PV             ||");

		System.out.println ("\t||          - Pension Benefits Income PV        ||");

		System.out.println ("\t||          - Basic Consumption PV              ||");

		System.out.println ("\t||----------------------------------------------||");

		for (double dblIncomeReplacementRate : adblIncomeReplacementRate) {
			for (double dblWorkingAgeConsumptionRate : adblWorkingAgeConsumptionRate) {
				for (double dblRetirementAgeConsumptionRate : adblRetirementAgeConsumptionRate)
					LiabilityRun (
						ics,
						dblAfterTaxIncome,
						dblStartAge,
						dblEndAge,
						dr,
						dblIncomeReplacementRate,
						dblWorkingAgeConsumptionRate,
						dblRetirementAgeConsumptionRate
					);
			}
		}

		System.out.println ("\t||----------------------------------------------||");

		System.out.println();
	}
}
