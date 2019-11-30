
package org.drip.template.state;

import org.drip.analytics.date.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.govvie.GovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>GovvieState</i> sets up the Calibration and the Construction of the Govvie Latent State and examine the
 * Emitted Metrics.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md">Pricing/Risk Templates for Fixed Income Component Products</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/state/README.md">Standard Latent State Construction Template</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GovvieState {

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

		GovvieCurve gc = LatentMarketStateBuilder.ShapePreservingGovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield"
		);

		String strLatentStateLabel = gc.label().fullyQualifiedName();

		System.out.println ("\n\n\t||---------------------------------------------------------------------------------------||");

		for (int i = 0; i < adtEffective.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  TREASURY  | " +
				adtMaturity[i] + " | " + FormatUtil.FormatDouble (adblYield[i], 1, 2, 100.) +
				"% | Yield | " +
				FormatUtil.FormatDouble (gc.yield (adtMaturity[i]), 1, 2, 100.) +
				"% | Discount Factor | " +
				FormatUtil.FormatDouble (gc.df (adtMaturity[i]), 1, 4, 1.) +
				"  ||"
			);

		System.out.println ("\t||---------------------------------------------------------------------------------------||\n");
	}
}
