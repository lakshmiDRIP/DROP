
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
 * <i>NetLiabilityStreamEstimator</i> demonstrates the Generation of an ALM Net Liability Cash Flow.
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

public class NetLiabilityStreamEstimator {

	public static final void main (
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
		double dblIncomeReplacementRate = 0.35;
		double dblWorkingAgeConsumptionRate = 0.80;
		double dblRetirementAgeConsumptionRate = 0.60;
		double dblStartAge = 45.;
		double dblEndAge = 105.;

		double dblYield = -0.0020;
		double dblBasicConsumptionSpread = 0.0100;
		double dblWorkingAgeIncomeSpread = 0.0100;
		double dblPensionBenefitsIncomeSpread = 0.0100;

		double dblWorkingAgeIncomePVReconciler = 14726.60;
		double dblPensionBenefitsIncomePVReconciler = 4392.20;
		double dblBasicConsumptionPVReconciler = 19310.70;

		NetLiabilityStream nls = new NetLiabilityStream (
			new InvestorCliffSettings (
				dblRetirementAge,
				dblMaximumAge
			),
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
			new DiscountRate (
				dblYield,
				dblWorkingAgeIncomeSpread,
				dblPensionBenefitsIncomeSpread,
				dblBasicConsumptionSpread
			)
		);

		System.out.println ("\n\t||---------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                    ||");

		System.out.println ("\t||        Age (Years)                                            ||");

		System.out.println ("\t||        Retired?                                               ||");

		System.out.println ("\t||        Alive?                                                 ||");

		System.out.println ("\t||        Horizon (Years)                                        ||");

		System.out.println ("\t||        Working Age Income DF                                  ||");

		System.out.println ("\t||        Pension Benefits Income DF                             ||");

		System.out.println ("\t||        Basic Consumption DF                                   ||");

		System.out.println ("\t||        Working Age Income                                     ||");

		System.out.println ("\t||        Pension Benefits Income                                ||");

		System.out.println ("\t||        Basic Consumption                                      ||");

		System.out.println ("\t||---------------------------------------------------------------||");

		for (NetLiabilityCashFlow nlcf : nlm.cashFlowList())
			System.out.println (
				"\t||" +
				FormatUtil.FormatDouble (nlcf.age(), 3, 0, 1.) + " | " +
				(nlcf.isRetired() ? "N" : "Y") + " | " +
				(nlcf.isAlive() ? "Y" : "N") + " |" +
				FormatUtil.FormatDouble (nlcf.horizon(), 2, 0, 1.) + " |" +
				FormatUtil.FormatDouble (nlcf.workingAgeIncomeDF(), 1, 4, 1.) + " |" +
				FormatUtil.FormatDouble (nlcf.pensionBenefitsDF(), 1, 4, 1.) + " |" +
				FormatUtil.FormatDouble (nlcf.basicConsumptionDF(), 1, 4, 1.) + " |" +
				FormatUtil.FormatDouble (nlcf.workingAgeIncome(), 3, 0, 1.) + " |" +
				FormatUtil.FormatDouble (nlcf.pensionBenefits(), 3, 0, 1.) + " |" +
				FormatUtil.FormatDouble (nlcf.basicConsumption(), 3, 0, 1.) + " ||"
			);

		System.out.println ("\t||---------------------------------------------------------------||\n");

		System.out.println ("\t||-------------------------------------------------||");

		System.out.println (
			"\t|| Working Age Income PV      : " +
			FormatUtil.FormatDouble (nlm.workingAgeIncomePV(), 5, 1, 1) + " |" +
			FormatUtil.FormatDouble (dblWorkingAgeIncomePVReconciler, 5, 1, 1) + " ||"
		);

		System.out.println (
			"\t|| Pension Benefits Income PV : " +
			FormatUtil.FormatDouble (nlm.pensionBenefitsIncomePV(), 5, 1, 1) + " |" +
			FormatUtil.FormatDouble (dblPensionBenefitsIncomePVReconciler, 5, 1, 1) + " ||"
		);

		System.out.println (
			"\t|| Basic Consumption PV       : " +
			FormatUtil.FormatDouble (nlm.basicConsumptionPV(), 5, 1, 1) + " |" +
			FormatUtil.FormatDouble (dblBasicConsumptionPVReconciler, 5, 1, 1) + " ||"
		);

		System.out.println ("\t||-------------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
