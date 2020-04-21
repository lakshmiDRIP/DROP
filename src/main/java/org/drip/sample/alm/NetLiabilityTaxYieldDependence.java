
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
 * <i>NetLiabilityTaxYieldDependence</i> demonstrates the Dependence of the Outstanding Value on the Tax and
 * the Discount Yield Settings.
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

public class NetLiabilityTaxYieldDependence {

	private static final void LiabilityRun (
		final InvestorCliffSettings ics,
		final ExpectedNonFinancialIncome enfi,
		final ExpectedBasicConsumption ebc,
		final double dblStartAge,
		final double dblEndAge,
		final double dblWorkingAgeIncomeSpread,
		final double dblPensionBenefitsIncomeSpread,
		final double dblBasicConsumptionSpread,
		final double dblYield,
		final double dblAfterTaxIncome)
		throws Exception
	{
		NetLiabilityStream nls = new NetLiabilityStream (
			ics,
			enfi,
			ebc,
			dblAfterTaxIncome
		);

		NetLiabilityMetrics nlm = nls.generateMetrics (
			dblStartAge,
			dblEndAge,
			new DiscountRate (
				dblYield,
				dblWorkingAgeIncomeSpread,
				dblPensionBenefitsIncomeSpread,
				dblBasicConsumptionSpread
			)
		);

		System.out.println (
			"\t|| [" +
			FormatUtil.FormatDouble (dblYield, 1, 1, 100.) + "% |" +
			FormatUtil.FormatDouble (dblAfterTaxIncome, 3, 0, 1.) + "] => " +
			FormatUtil.FormatDouble (nlm.workingAgeIncomePV(), 5, 0, 1.) + " |" +
			FormatUtil.FormatDouble (nlm.pensionBenefitsIncomePV(), 5, 0, 1.) + " |" +
			FormatUtil.FormatDouble (nlm.basicConsumptionPV(), 5, 0, 1.) + " ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblRetirementAge = 65.;
		double dblMaximumAge = 85.;
		double dblIncomeReplacementRate = 0.35;
		double dblWorkingAgeConsumptionRate = 0.80;
		double dblRetirementAgeConsumptionRate = 0.60;
		double dblStartAge = 45.;
		double dblEndAge = 105.;

		double dblBasicConsumptionSpread = 0.0100;
		double dblWorkingAgeIncomeSpread = 0.0100;
		double dblPensionBenefitsIncomeSpread = 0.0100;

		double[] adblYield = new double[] {
			-0.004,
			-0.002,
			 0.000,
			 0.002,
			 0.004,
			 0.006
		};
		double[] adblAfterTaxIncome = new double[] {
			600.,
			700.,
			800.,
			900.,
			999.
		};

		InvestorCliffSettings ics = new InvestorCliffSettings (
			dblRetirementAge,
			dblMaximumAge
		);

		ExpectedNonFinancialIncome enfi = new ExpectedNonFinancialIncome (dblIncomeReplacementRate);

		ExpectedBasicConsumption ebc = new ExpectedBasicConsumption (
			dblWorkingAgeConsumptionRate,
			dblRetirementAgeConsumptionRate
		);

		System.out.println();

		System.out.println ("\t||-----------------------------------------||");

		System.out.println ("\t||  L -> R:                                ||");

		System.out.println ("\t||          - Base Discounting Yield       ||");

		System.out.println ("\t||          - After Tax Income             ||");

		System.out.println ("\t||-----------------------------------------||");

		System.out.println ("\t||          - Working Age Income PV        ||");

		System.out.println ("\t||          - Pension Benefits Income PV   ||");

		System.out.println ("\t||          - Basic Consumption PV         ||");

		System.out.println ("\t||-----------------------------------------||");

		for (double dblYield : adblYield) {
			for (double dblAfterTaxIncome : adblAfterTaxIncome)
				LiabilityRun (
					ics,
					enfi,
					ebc,
					dblStartAge,
					dblEndAge,
					dblWorkingAgeIncomeSpread,
					dblPensionBenefitsIncomeSpread,
					dblBasicConsumptionSpread,
					dblYield,
					dblAfterTaxIncome
				);
		}

		System.out.println ("\t||-----------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
