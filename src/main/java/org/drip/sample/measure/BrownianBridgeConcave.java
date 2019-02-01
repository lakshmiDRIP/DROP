
package org.drip.sample.measure;

import org.drip.analytics.date.*;
import org.drip.measure.bridge.BrokenDateInterpolatorBrownian3P;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>BrownianBridgeConcave</i> demonstrates using the Brownian Bridge Scheme to Interpolate Three Concave
 * Value Points.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalSupportLibrary.md">Numerical Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/measure/README.md">Probability Measure Generators</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BrownianBridgeConcave {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dt1 = DateUtil.CreateFromYMD (
			2015,
			DateUtil.JULY,
			1
		);

		JulianDate dt2 = DateUtil.CreateFromYMD (
			2015,
			DateUtil.AUGUST,
			1
		);

		JulianDate dt3 = DateUtil.CreateFromYMD (
			2015,
			DateUtil.SEPTEMBER,
			1
		);

		double dblV1 = 10.;
		double dblV2 = 30.;
		double dblV3 = 20.;

		int iDaysStep = 2;

		BrokenDateInterpolatorBrownian3P tpbb = new BrokenDateInterpolatorBrownian3P (
			dt1.julian(),
			dt2.julian(),
			dt3.julian(),
			dblV1,
			dblV2,
			dblV3
		);

		System.out.println();

		System.out.println ("\t||--------------------------||");

		System.out.println ("\t||  BROWNIAN BRIDGE CONCAVE ||");

		System.out.println ("\t||--------------------------||");

		System.out.println (
			"\t|| [" + dt1 + "] => " +
			FormatUtil.FormatDouble (tpbb.interpolate (dt1.julian()), 2, 3, 1.) + " ||"
		);

		JulianDate dt = dt1.addDays (iDaysStep);

		while (dt.julian() < dt3.julian()) {
			System.out.println (
				"\t|| [" + dt + "] => " +
				FormatUtil.FormatDouble (tpbb.interpolate (dt.julian()), 2, 3, 1.) + " ||"
			);

			dt = dt.addDays (iDaysStep);
		}

		System.out.println (
			"\t|| [" + dt3 + "] => " +
			FormatUtil.FormatDouble (tpbb.interpolate (dt3.julian()), 2, 3, 1.) + " ||"
		);

		System.out.println ("\t||--------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
