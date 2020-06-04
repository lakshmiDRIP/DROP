
package org.drip.sample.hjm;

import org.drip.analytics.date.*;
import org.drip.dynamics.hjm.G2PlusPlus;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.sequence.random.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>G2PlusPlusDynamics</i> demonstrates the Construction and Usage of the G2++ 2-Factor HJM Model Dynamics
 * for the Evolution of the Short Rate.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hjm/README.md">HJM Multi-Factor Principal Dynamics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class G2PlusPlusDynamics {

	private static final G2PlusPlus G2PlusPlusEvolver (
		final double dblSigma,
		final double dblA,
		final double dblEta,
		final double dblB,
		final double dblRho,
		final double dblStartingForwardRate)
		throws Exception
	{
		return new G2PlusPlus (
			dblSigma,
			dblA,
			dblEta,
			dblB,
			new UnivariateSequenceGenerator[] {
				new BoxMullerGaussian (
					0.,
					1.
				),
				new BoxMullerGaussian (
					0.,
					1.
				)
			},
			dblRho,
			new FlatUnivariate (dblStartingForwardRate)
		);
	}

	private static final void ShortRateEvolution (
		final G2PlusPlus g2pp,
		final JulianDate dtStart,
		final String strCurrency,
		final String strViewTenor,
		final double dblStartingShortRate)
		throws Exception
	{
		int iDayStep = 2;
		double dblX = 0.;
		double dblY = 0.;
		JulianDate dtSpot = dtStart;
		double dblShortRate = dblStartingShortRate;

		int iStartDate = dtStart.julian();

		int iEndDate = dtStart.addTenor (strViewTenor).julian();

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|                                                                       ||");

		System.out.println ("\t|         G2++ - 2-factor HJM Model - Short Rate Evolution Run          ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|                                                                       ||");

		System.out.println ("\t|    L->R:                                                              ||");

		System.out.println ("\t|        Date                                                           ||");

		System.out.println ("\t|        X (%)                                                          ||");

		System.out.println ("\t|        X - Increment (%)                                              ||");

		System.out.println ("\t|        Y (%)                                                          ||");

		System.out.println ("\t|        Y - Increment (%)                                              ||");

		System.out.println ("\t|        Phi (%)                                                        ||");

		System.out.println ("\t|        Short Rate (%)                                                 ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		while (dtSpot.julian() < iEndDate) {
			int iSpotDate = dtSpot.julian();

			double dblDeltaX = g2pp.deltaX (
				iStartDate,
				iSpotDate,
				dblX,
				iDayStep
			);

			dblX += dblDeltaX;

			double dblDeltaY = g2pp.deltaY (
				iStartDate,
				iSpotDate,
				dblY,
				iDayStep
			);

			dblY += dblDeltaY;

			double dblPhi = g2pp.phi (
				iStartDate,
				iSpotDate
			);

			dblShortRate = dblX + dblY + dblPhi;

			System.out.println ("\t| [" + dtSpot + "] = " +
				FormatUtil.FormatDouble (dblX, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblDeltaX, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblY, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblDeltaY, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblPhi, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblShortRate, 1, 2, 100.) + "% || "
			);

			dtSpot = dtSpot.addBusDays (
				iDayStep,
				strCurrency
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		String strCurrency = "USD";
		double dblStartingShortRate = 0.05;
		double dblSigma = 0.05;
		double dblA = 0.5;
		double dblEta = 0.05;
		double dblB = 0.5;
		double dblRho = 0.5;

		G2PlusPlus g2pp = G2PlusPlusEvolver (
			dblSigma,
			dblA,
			dblEta,
			dblB,
			dblRho,
			dblStartingShortRate
		);

		ShortRateEvolution (
			g2pp,
			dtSpot,
			strCurrency,
			"4M",
			dblStartingShortRate
		);

		EnvManager.TerminateEnv();
	}
}
