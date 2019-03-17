
package org.drip.sample.intexfeed;

import org.drip.analytics.date.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.govvie.GovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>ForwardGovvieYield</i> generates the Forward Govvie Yields over Monthly Increments with Maturity up to
 * 60Y for different Govvie Tenors.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/intexfeed/README.md">Intex Feed Inputs</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ForwardGovvieYield {

	private static final GovvieCurve GovvieCurve (
		final JulianDate dtSpot,
		final String strCode)
		throws Exception
	{
		return LatentMarketStateBuilder.GovvieCurve (
			strCode,
			dtSpot,
			new JulianDate[] {
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot
			},
			new JulianDate[] {
				dtSpot.addTenor ("1Y"),
				dtSpot.addTenor ("2Y"),
				dtSpot.addTenor ("3Y"),
				dtSpot.addTenor ("5Y"),
				dtSpot.addTenor ("7Y"),
				dtSpot.addTenor ("10Y"),
				dtSpot.addTenor ("20Y"),
				dtSpot.addTenor ("30Y")
			},
			new double[] {
				0.01219, //  1Y
				0.01391, //  2Y
				0.01590, //  3Y
				0.01937, //  5Y
				0.02200, //  7Y
				0.02378, // 10Y
				0.02677, // 20Y
				0.02927  // 30Y
			},
			new double[] {
				0.01219, //  1Y
				0.01391, //  2Y
				0.01590, //  3Y
				0.01937, //  5Y
				0.02200, //  7Y
				0.02378, // 10Y
				0.02677, // 20Y
				0.02927  // 30Y
			},
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		int iNumMonth = 720;
		String strCode = "UST";
		String[] astrGovvieTenor = new String[] {
			 "1M",
			 "3M",
			 "6M",
			 "1Y",
			 "2Y",
			 "3Y",
			 "5Y",
			 "7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.OCTOBER,
			5
		);

		GovvieCurve gc = GovvieCurve (
			dtSpot,
			strCode
		);

		System.out.println ("SpotDate,ForwardGap,ForwardTenor,ForwardStartDate,ForwardEndDate,ForwardYield");

		for (int i = 0; i <= iNumMonth; ++i) {
			JulianDate dtForward = dtSpot.addMonths (i);

			for (int j = 0; j < astrGovvieTenor.length; ++j) {
				JulianDate dtMaturity = dtForward.addTenor (astrGovvieTenor[j]);

				System.out.println (
					dtSpot + "," +
					j + "," +
					astrGovvieTenor[j] + "," +
					dtForward + "," +
					dtMaturity + "," +
					FormatUtil.FormatDouble (
						gc.forwardYield (
							dtForward.julian(),
							dtMaturity.julian()
						), 1, 8, 100.)
					+ "%"
				);
			}
		}

		EnvManager.TerminateEnv();
	}
}
