
package org.drip.sample.statistics;

import org.drip.feed.loader.*;
import org.drip.measure.statistics.UnivariateMoments;
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
 * <i>UnivariateSequence</i> demonstrates the Generation of the Statistical Measures for the Input Series of
 * 	Univariate Sequences.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/statistics/README.md">Correlated R<sup>d</sup> Random Sequence Statistics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateSequence {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strSeriesLocation = "C:\\DROP\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

		CSVGrid csvGrid = CSVParser.NamedStringGrid (strSeriesLocation);

		UnivariateMoments mvTOK = UnivariateMoments.Standard (
			csvGrid.header (1),
			csvGrid.doubleArrayAtColumn (1)
		);

		UnivariateMoments mvEWJ = UnivariateMoments.Standard (
			csvGrid.header (2),
			csvGrid.doubleArrayAtColumn (2)
		);

		UnivariateMoments mvHYG = UnivariateMoments.Standard (
			csvGrid.header (3),
			csvGrid.doubleArrayAtColumn (3)
		);

		UnivariateMoments mvLQD = UnivariateMoments.Standard (
			csvGrid.header (4),
			csvGrid.doubleArrayAtColumn (4)
		);

		UnivariateMoments mvEMD = UnivariateMoments.Standard (
			csvGrid.header (5),
			csvGrid.doubleArrayAtColumn (5)
		);

		UnivariateMoments mvGSG = UnivariateMoments.Standard (
			csvGrid.header (6),
			csvGrid.doubleArrayAtColumn (6)
		);

		UnivariateMoments mvBWX = UnivariateMoments.Standard (
			csvGrid.header (7),
			csvGrid.doubleArrayAtColumn (7)
		);

		System.out.println ("\n\t|----------------------------||");

		System.out.println (
			"\t| " + mvTOK.name() + " | " +
			FormatUtil.FormatDouble (mvTOK.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvTOK.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvTOK.numSample() + " ||"
		);

		System.out.println (
			"\t| " + mvEWJ.name() + " | " +
			FormatUtil.FormatDouble (mvEWJ.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvEWJ.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvEWJ.numSample() + " ||"
		);

		System.out.println (
			"\t| " + mvHYG.name() + " | " +
			FormatUtil.FormatDouble (mvHYG.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvHYG.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvHYG.numSample() + " ||"
		);

		System.out.println (
			"\t| " + mvLQD.name() + " | " +
			FormatUtil.FormatDouble (mvLQD.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvLQD.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvLQD.numSample() + " ||"
		);

		System.out.println (
			"\t| " + mvEMD.name() + " | " +
			FormatUtil.FormatDouble (mvEMD.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvEMD.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvEMD.numSample() + " ||"
		);

		System.out.println (
			"\t| " + mvGSG.name() + " | " +
			FormatUtil.FormatDouble (mvGSG.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvGSG.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvGSG.numSample() + " ||"
		);

		System.out.println (
			"\t| " + mvBWX.name() + " | " +
			FormatUtil.FormatDouble (mvBWX.mean(), 1, 2, 1200) + "% | " +
			FormatUtil.FormatDouble (mvBWX.stdDev(), 2, 1, 100 * Math.sqrt (12)) + "% | " +
			mvBWX.numSample() + " ||"
		);

		System.out.println ("\t|----------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
