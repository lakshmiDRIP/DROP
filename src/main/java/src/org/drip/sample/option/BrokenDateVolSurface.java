
package org.drip.sample.option;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.MarketSurface;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioMarketSurfaceBuilder;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>BrokenDateVolSurface</i> contains an illustration of the Construction and Usage of the Option
 * 	Volatility Surface, and the Evaluation at the supplied Broken Dates.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/option/README.md">Deterministic (Black) / Stochastic (Heston) Options</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateVolSurface {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtStart = DateUtil.Today();

		double[] adblStrikeATMFactor = new double[] {
			0.8, 0.9, 1.0, 1.1, 1.2
		};
		String[] astrMaturityTenor = new String[] {
			"1Y", "2Y", "3Y", "4Y", "5Y"
		};

		double[][] aadblImpliedVolatility = new double[][] {
			{0.44, 0.38, 0.33, 0.27, 0.25},
			{0.41, 0.34, 0.30, 0.22, 0.27},
			{0.36, 0.31, 0.28, 0.30, 0.37},
			{0.38, 0.31, 0.34, 0.40, 0.47},
			{0.43, 0.46, 0.48, 0.52, 0.57}
		};

		MarketSurface volSurface = ScenarioMarketSurfaceBuilder.CubicPolynomialWireSurface (
			"SAMPLE_VOL_SURFACE",
			dtStart,
			"USD",
			adblStrikeATMFactor,
			astrMaturityTenor,
			aadblImpliedVolatility
		);

		System.out.println ("\n\t|------------------------------------------------------------|");

		System.out.println ("\t|----------------- INPUT  SURFACE  RECOVERY -----------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		for (String strMaturity : astrMaturityTenor)
			System.out.print ("     " + strMaturity + "  ");

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (double dblStrike : adblStrikeATMFactor) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (dblStrike, 1, 2, 1.) + "    =>");

			for (String strMaturity : astrMaturityTenor)
				System.out.print ("  " + FormatUtil.FormatDouble (volSurface.node (dblStrike, strMaturity), 2, 2, 100.) + "%");

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");

		adblStrikeATMFactor = new double[] {
			0.850, 0.925, 1.000, 1.075, 1.15
		};
		astrMaturityTenor = new String[] {
			"18M", "27M", "36M", "45M", "54M"
		};

		System.out.println ("\n\t|------------------------------------------------------------|");

		System.out.println ("\t|------------- INTERIM  SURFACE  RECALCULATION --------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		for (String strMaturity : astrMaturityTenor)
			System.out.print ("    " + strMaturity + "  ");

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (double dblStrike : adblStrikeATMFactor) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (dblStrike, 1, 2, 1.) + "    =>");

			for (String strMaturity : astrMaturityTenor)
				System.out.print ("  " + FormatUtil.FormatDouble (volSurface.node (dblStrike, strMaturity), 2, 2, 100.) + "%");

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");

		adblStrikeATMFactor = new double[] {
			0.700, 0.850, 1.000, 1.150, 1.300
		};
		astrMaturityTenor = new String[] {
			"06M", "21M", "36M", "51M", "66M"
		};

		System.out.println ("\n\t|------------------------------------------------------------|");

		System.out.println ("\t|------------- INTERIM  SURFACE  RECALCULATION --------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		for (String strMaturity : astrMaturityTenor)
			System.out.print ("    " + strMaturity + "  ");

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (double dblStrike : adblStrikeATMFactor) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (dblStrike, 1, 2, 1.) + "    =>");

			for (String strMaturity : astrMaturityTenor)
				System.out.print ("  " + FormatUtil.FormatDouble (volSurface.node (dblStrike, strMaturity), 2, 2, 100.) + "%");

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
