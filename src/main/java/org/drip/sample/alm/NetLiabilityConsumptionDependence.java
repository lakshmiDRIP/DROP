
package org.drip.sample.alm;

import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.alm.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>NetLiabilityConsumptionDependence</i> demonstrates the Dependence of the Outstanding Values on the Investor
 * Consumption Settings.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/alm/README.md">ALM</a></li>
 *  </ul>
 * <br><br>
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

		NetLiabilityMetrics nlm = nls.generateMetrics (
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
		EnvManager.InitEnv (
			"",
			true
		);

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

		EnvManager.TerminateEnv();
	}
}
