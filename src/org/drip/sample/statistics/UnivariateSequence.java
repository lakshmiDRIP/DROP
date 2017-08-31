
package org.drip.sample.statistics;

import org.drip.feed.loader.*;
import org.drip.measure.statistics.UnivariateMoments;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * UnivariateSequence demonstrates the Generation of the Statistical Measures for the Input Series of
 * 	Univariate Sequences.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateSequence {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strSeriesLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

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
	}
}
