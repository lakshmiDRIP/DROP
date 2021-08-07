
package org.drip.sample.measure;

import org.drip.analytics.date.*;
import org.drip.measure.bridge.BrokenDateInterpolatorBrownian3P;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>BrownianBridgeConcave</i> demonstrates using the Brownian Bridge Scheme to Interpolate Three Concave
 * 	Value Points.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/measure/README.md">Lebesgue Measure Brownian Bridge Interpolation</a></li>
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
