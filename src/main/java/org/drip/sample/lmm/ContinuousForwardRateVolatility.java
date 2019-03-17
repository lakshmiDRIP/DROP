
package org.drip.sample.lmm;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.MarketSurface;
import org.drip.dynamics.lmm.LognormalLIBORVolatility;
import org.drip.numerical.common.FormatUtil;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ContinuousForwardRateVolatility</i> demonstrates the Implying of the Volatility of the Continuously
 * Compounded Forward Rate from the Corresponding LIBOR Forward Rate Volatility. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure
 *  			Models, The University of New South Wales.
 *  	</li>
 *  	<li>
 *  		Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of
 *  			New South Wales.
 *  	</li>
 *  	<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics,
 * 				Mathematical Finance 7 (2), 127-155.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/README.md">LIBOR Market (LMM-BGM Variant) Evolution</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ContinuousForwardRateVolatility {

	private static final MarketSurface FlatVolatilitySurface (
		final JulianDate dtStart,
		final String strCurrency,
		final double dblFlatVol)
		throws Exception
	{
		return ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (
			"VIEW_TARGET_VOLATILITY_SURFACE",
			dtStart,
			strCurrency,
			new double[] {
				dtStart.julian(),
				dtStart.addYears (2).julian(),
				dtStart.addYears (4).julian(),
				dtStart.addYears (6).julian(),
				dtStart.addYears (8).julian(),
				dtStart.addYears (10).julian()
			},
			new double[] {
				dtStart.julian(),
				dtStart.addYears (2).julian(),
				dtStart.addYears (4).julian(),
				dtStart.addYears (6).julian(),
				dtStart.addYears (8).julian(),
				dtStart.addYears (10).julian()
			},
			new double[][] {
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
			},
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				null,
				null
			),
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				null,
				null
			)
		);
	}

	private static final void DisplayVolArray (
		final String strTenor,
		final double[] adblVol)
	{
		String strDump = "\t | " + strTenor + " => ";

		for (int i = 0; i < adblVol.length; ++i)
			strDump += FormatUtil.FormatDouble (adblVol[i], 1, 2, 100.) + "% |";

		System.out.println (strDump);
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strTenor = "3M";
		String strCurrency = "USD";
		double dblFlatVol1 = 0.35;
		double dblFlatVol2 = 0.42;
		double dblFlatVol3 = 0.27;
		double dblFlatForwardRate = 0.02;

		int[] aiNumFactor = {
			1, 2, 3
		};

		String[] astrForwardTenor = {
			"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y"
		};

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		JulianDate dtSpot = org.drip.analytics.date.DateUtil.Today();

		MarketSurface mktSurfFlatVol1 = FlatVolatilitySurface (
			dtSpot,
			strCurrency,
			dblFlatVol1
		);

		MarketSurface mktSurfFlatVol2 = FlatVolatilitySurface (
			dtSpot,
			strCurrency,
			dblFlatVol2
		);

		MarketSurface mktSurfFlatVol3 = FlatVolatilitySurface (
			dtSpot,
			strCurrency,
			dblFlatVol3
		);

		ForwardCurve fc = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtSpot,
			forwardLabel,
			dblFlatForwardRate
		);

		for (int iNumFactor : aiNumFactor) {
			LognormalLIBORVolatility llv = new LognormalLIBORVolatility (
				dtSpot.julian(),
				forwardLabel,
				new MarketSurface[] {
					mktSurfFlatVol1,
					mktSurfFlatVol2,
					mktSurfFlatVol3
				},
				new PrincipalFactorSequenceGenerator (
					new UnivariateSequenceGenerator[] {
						new BoxMullerGaussian (
							0.,
							1.
						),
						new BoxMullerGaussian (
							0.,
							1.
						),
						new BoxMullerGaussian (
							0.,
							1.
						)
					},
					new double[][] {
						{1.0, 0.1, 0.2},
						{0.1, 1.0, 0.2},
						{0.2, 0.1, 1.0}
					},
					iNumFactor
				)
			);

			System.out.println ("\n\t |------------------------------|");

			System.out.println ("\t |  CONTINUOUS FORWARD RATE VOL |");

			System.out.println ("\t |    Num Factors: " + iNumFactor + "            |");

			System.out.println ("\t |------------------------------|");

			for (String strForwardTenor : astrForwardTenor)
				DisplayVolArray (
					strForwardTenor,
					llv.continuousForwardVolatility (
						dtSpot.addTenor (strForwardTenor).julian(),
						fc
					)
				);

			System.out.println ("\t |------------------------------|");
		}

		EnvManager.TerminateEnv();
	}
}
