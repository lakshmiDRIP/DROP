
package org.drip.sample.alm;

import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.alm.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>NetLiabilityCliffDependence</i> demonstrates the Dependence of the Outstanding Value on Investor
 * Cliff/Horizon Settings.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/alm/README.md">Sharpe-Tint-Yotsuzuka ALM Module</a></li>
 *  </ul>
 * <br><br>
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

		NetLiabilityMetrics nlm = nls.generateMetrics (
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
		EnvManager.InitEnv (
			"",
			true
		);

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

		EnvManager.TerminateEnv();
	}
}
