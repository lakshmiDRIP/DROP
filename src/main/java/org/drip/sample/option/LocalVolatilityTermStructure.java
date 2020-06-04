
package org.drip.sample.option;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.ScenarioMarketSurfaceBuilder;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>LocalVolatilityTermStructure</i> contains an illustration of the Calibration and Extraction of the
 * 	Implied and the Local Volatility Surfaces and their eventual Strike and Maturity Anchor Term Structures.
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

public class LocalVolatilityTermStructure {
	private static final SegmentCustomBuilderControl scbc()
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (4),
			SegmentInelasticDesignControl.Create (
				2,
				2
			),
			null,
			null
		);
	}

	private static final void EvaluateLocalVolSurface (
		final MarketSurface volSurface,
		final double[] adblStrikeATMFactor,
		final String[] astrMaturityTenor)
		throws Exception
	{
		System.out.println ("\n\t  " + volSurface.label());

		System.out.println ("\t|------------------------------------------------------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		NodeStructure[] aTSMaturityAnchor = new NodeStructure[astrMaturityTenor.length];

		for (int j = 0; j < astrMaturityTenor.length; ++j) {
			aTSMaturityAnchor[j] = volSurface.maturityAnchorTermStructure (astrMaturityTenor[j]);

			System.out.print ("    " + astrMaturityTenor[j] + "  ");
		}

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (int i = 0; i < adblStrikeATMFactor.length; ++i) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (adblStrikeATMFactor[i], 1, 2, 1.) + "    =>");

			NodeStructure tsStrikeAnchor = volSurface.xAnchorTermStructure (adblStrikeATMFactor[i]);

			for (int j = 0; j < astrMaturityTenor.length; ++j) {
				double dblLocalVol = Math.sqrt (2. * (tsStrikeAnchor.nodeDerivative (astrMaturityTenor[j], 1) +
					0.0 * adblStrikeATMFactor[i] * aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 1)) /
						(adblStrikeATMFactor[i] * adblStrikeATMFactor[i] *
							aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 2)));

				System.out.print ("  " + FormatUtil.FormatDouble (dblLocalVol, 2, 2, 100.) + "%");
			}

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtStart = DateUtil.Today();

		double[] adblStrikeATMFactorCalib = new double[] {
			0.8, 0.9, 1.0, 1.1, 1.2
		};
		String[] astrMaturityTenorCalib = new String[] {
			"12M", "24M", "36M", "48M", "60M"
		};
		double[][] aadblVol = new double[][] {
			{0.171, 0.169, 0.168, 0.168, 0.168},
			{0.159, 0.161, 0.161, 0.162, 0.164},
			{0.138, 0.145, 0.149, 0.152, 0.154},
			{0.115, 0.130, 0.137, 0.143, 0.148},
			{0.103, 0.119, 0.128, 0.135, 0.140}
		};

		MarketSurface priceSurfCubicPoly = ScenarioMarketSurfaceBuilder.CustomWireSurface (
			"HESTON1993_CUBICPOLY_CALLPRICE_SURFACE",
			dtStart,
			"USD",
			adblStrikeATMFactorCalib,
			astrMaturityTenorCalib,
			aadblVol,
			scbc(),
			scbc()
		);

		double[] adblStrikeATMFactor = new double[] {
			0.850, 0.925, 1.000, 1.075, 1.150
		};
		String[] astrMaturityTenor = new String[] {
			"18M", "27M", "36M", "45M", "54M"
		};

		NodeStructure[] aTSMaturityAnchor = new NodeStructure[astrMaturityTenor.length];

		for (int j = 0; j < astrMaturityTenor.length; ++j)
			aTSMaturityAnchor[j] = priceSurfCubicPoly.maturityAnchorTermStructure (astrMaturityTenor[j]);

		for (int i = 0; i < adblStrikeATMFactor.length; ++i) {
			NodeStructure tsStrikeAnchor = priceSurfCubicPoly.xAnchorTermStructure (adblStrikeATMFactor[i]);

			for (int j = 0; j < astrMaturityTenor.length; ++j) {
				System.out.println (Math.sqrt (2. * (tsStrikeAnchor.nodeDerivative (astrMaturityTenor[j], 1) +
					0.0 * adblStrikeATMFactor[i] * aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 1)) /
						(adblStrikeATMFactor[i] * adblStrikeATMFactor[i] *
							aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 2))) + " | " +
								aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 2));
			}
		}

		EvaluateLocalVolSurface (
			priceSurfCubicPoly,
			adblStrikeATMFactor,
			astrMaturityTenor
		);

		EnvManager.TerminateEnv();
	}
}
