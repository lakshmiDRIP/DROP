
package org.drip.sample.hullwhite;

import org.drip.analytics.date.*;
import org.drip.dynamics.hullwhite.SingleFactorStateEvolver;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.FundingLabel;

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
 * <i>ShortRateDynamics</i> demonstrates the Construction and Usage of the Hull-White 1F Model Dynamics for
 * the Evolution of the Short Rate.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hullwhite/README.md">Hull White Trinomial Tree Dynamics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortRateDynamics {

	private static final SingleFactorStateEvolver HullWhiteEvolver (
		final String strCurrency,
		final double dblSigma,
		final double dblA,
		final double dblStartingForwardRate)
		throws Exception
	{
		return new SingleFactorStateEvolver (
			FundingLabel.Standard (strCurrency),
			dblSigma,
			dblA,
			new FlatUnivariate (dblStartingForwardRate),
			new BoxMullerGaussian (
				0.,
				1.
			)
		);
	}

	private static final void ShortRateEvolution (
		final SingleFactorStateEvolver hw,
		final JulianDate dtSpot,
		final String strCurrency,
		final String strViewTenor,
		final double dblStartingShortRate)
		throws Exception
	{
		int iDayStep = 2;
		JulianDate dtView = dtSpot;
		double dblShortRate = dblStartingShortRate;

		int iSpotDate = dtSpot.julian();

		int iEndDate = dtSpot.addTenor (strViewTenor).julian();

		System.out.println ("\n\n\t|------------------------------------------------------||");

		System.out.println ("\t|                                                      ||");

		System.out.println ("\t|    Hull-White Evolution Run                          ||");

		System.out.println ("\t|    ------------------------                          ||");

		System.out.println ("\t|                                                      ||");

		System.out.println ("\t|    L->R:                                             ||");

		System.out.println ("\t|        Date                                          ||");

		System.out.println ("\t|        Short Rate (%)                                ||");

		System.out.println ("\t|        Short Rate - Change (%)                       ||");

		System.out.println ("\t|        Alpha (%)                                     ||");

		System.out.println ("\t|        Theta (%)                                     ||");

		System.out.println ("\t|------------------------------------------------------||");

		while (dtView.julian() < iEndDate) {
			int iViewDate = dtView.julian();

			double dblAlpha = hw.alpha (
				iSpotDate,
				iViewDate
			);

			double dblTheta = hw.theta (
				iSpotDate,
				iViewDate
			);

			double dblShortRateIncrement = hw.shortRateIncrement (
				iSpotDate,
				iViewDate,
				dblShortRate,
				iDayStep
			);

			dblShortRate += dblShortRateIncrement;

			System.out.println ("\t| [" + dtView + "] = " +
				FormatUtil.FormatDouble (dblShortRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblShortRateIncrement, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblAlpha, 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (dblTheta, 1, 4, 100.) + "% || "
			);

			dtView = dtView.addBusDays (
				iDayStep,
				strCurrency
			);
		}

		System.out.println ("\t|------------------------------------------------------||");
	}

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

		JulianDate dtSpot = DateUtil.Today();

		String strCurrency = "USD";
		double dblStartingShortRate = 0.05;
		double dblSigma = 0.05;
		double dblA = 1.;

		SingleFactorStateEvolver hw = HullWhiteEvolver (
			strCurrency,
			dblSigma,
			dblA,
			dblStartingShortRate
		);

		ShortRateEvolution (
			hw,
			dtSpot,
			strCurrency,
			"4M",
			dblStartingShortRate
		);

		EnvManager.TerminateEnv();
	}
}
