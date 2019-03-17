
package org.drip.sample.govvie;

import org.drip.analytics.date.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.product.credit.BondComponent;
import org.drip.service.env.EnvManager;
import org.drip.service.template.TreasuryBuilder;
import org.drip.state.creator.ScenarioGovvieCurveBuilder;
import org.drip.state.govvie.GovvieCurve;

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
 * <i>SplineGovvieCurve</i> demonstrates the Construction and Usage of the Spline-based Govvie Curve.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/govvie/README.md">Govvie Curve Builder</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SplineGovvieCurve {

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		String strTreasuryCode = "UST";
		String strCurrency = "USD";

		int[] aiMaturityDate = {
			dtSpot.addTenor ("01Y").julian(),
			dtSpot.addTenor ("02Y").julian(),
			dtSpot.addTenor ("03Y").julian(),
			dtSpot.addTenor ("05Y").julian(),
			dtSpot.addTenor ("07Y").julian(),
			dtSpot.addTenor ("10Y").julian(),
			dtSpot.addTenor ("30Y").julian()
		};

		double[] adblYield = {
			0.0113, // "01Y",
			0.0121, // "02Y",
			0.0127, // "03Y",
			0.0137, // "05Y",
			0.0145, // "07Y",
			0.0154, // "10Y"
			0.0198  // "30Y"
		};

		GovvieCurve gc = ScenarioGovvieCurveBuilder.CubicPolynomialCurve (
			strTreasuryCode,
			dtSpot,
			strTreasuryCode,
			strCurrency,
			aiMaturityDate,
			adblYield
		);

		JulianDate[] adtEffective = new JulianDate[] {
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot
		};

		JulianDate[] adtMaturity = new JulianDate[] {
			dtSpot.addTenor ("1Y"),
			dtSpot.addTenor ("2Y"),
			dtSpot.addTenor ("3Y"),
			dtSpot.addTenor ("5Y"),
			dtSpot.addTenor ("7Y"),
			dtSpot.addTenor ("10Y"),
			dtSpot.addTenor ("30Y")
		};

		BondComponent[] aTreasury = TreasuryBuilder.FromCode (
			strTreasuryCode,
			adtEffective,
			adtMaturity,
			adblYield
		);

		GovvieCurve gcCalib = ScenarioGovvieCurveBuilder.CubicPolyShapePreserver (
			strTreasuryCode,
			strTreasuryCode,
			strCurrency,
			dtSpot.julian(),
			aTreasury,
			adblYield,
			"Yield"
		);

		System.out.println();

		for (int i = 0; i < adblYield.length; ++i)
			System.out.println (
				"\t[" + new JulianDate (aiMaturityDate[i]) + "] => " +
				FormatUtil.FormatDouble (adblYield[i], 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (gc.yield (aiMaturityDate[i]), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (gcCalib.yield (aiMaturityDate[i]), 1, 2, 100.) + "% || "
			);

		EnvManager.TerminateEnv();
	}
}
