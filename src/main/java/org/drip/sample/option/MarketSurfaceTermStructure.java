
package org.drip.sample.option;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.fourier.PhaseAdjuster;
import org.drip.param.pricer.HestonOptionPricerParams;
import org.drip.pricer.option.HestonStochasticVolatilityAlgorithm;
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
 * <i>MarketSurfaceTermStructure</i> contains an illustration of the Creation and Usage of the Strike
 * 	Anchored and Maturity Anchored Term Structures extracted from the given Market Surface.
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

public class MarketSurfaceTermStructure {

	private static final SegmentCustomBuilderControl CubicPolySCBC()
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

	private static final void EvaluateSplineSurface (
		final MarketSurface mktSurf,
		final double[] adblStrikeATMFactor,
		final String[] astrMaturityTenor)
		throws Exception
	{
		System.out.println ("\t|------------------------------------------------------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		for (String strMaturity : astrMaturityTenor)
			System.out.print ("    " + strMaturity + "  ");

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (double dblStrike : adblStrikeATMFactor) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (dblStrike, 1, 2, 1.) + "    =>");

			for (String strMaturity : astrMaturityTenor)
				System.out.print ("  " + FormatUtil.FormatDouble (mktSurf.node (dblStrike, strMaturity), 2, 2, 100.) + "%");

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");
	}

	private static final void EvaluateStrikeTermStructure (
		final MarketSurface mktSurf,
		final double[] adblStrikeATMFactor,
		final String[] astrMaturityTenor)
		throws Exception
	{
		System.out.println ("\n\t|--------- STRIKE TERM STRUCTURE FROM MARKET SURFACE --------|");

		System.out.println ("\t|------------------------------------------------------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		for (String strMaturity : astrMaturityTenor)
			System.out.print ("    " + strMaturity + "  ");

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (double dblStrike : adblStrikeATMFactor) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (dblStrike, 1, 2, 1.) + "    =>");

			NodeStructure tsStrike = mktSurf.xAnchorTermStructure (dblStrike);

			for (String strMaturity : astrMaturityTenor)
				System.out.print ("  " + FormatUtil.FormatDouble (tsStrike.node (strMaturity), 2, 2, 100.) + "%");

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");
	}

	private static final void EvaluateMaturityTermStructure (
		final MarketSurface mktSurf,
		final double[] adblStrikeATMFactor,
		final String[] astrMaturityTenor)
		throws Exception
	{
		System.out.println ("\n\t|-------- MATURITY TERM STRUCTURE FROM MARKET SURFACE -------|");

		System.out.println ("\t|------------------------------------------------------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		Map<String, NodeStructure> mapMaturityTS = new TreeMap<String, NodeStructure>();

		for (String strMaturity : astrMaturityTenor) {
			System.out.print ("    " + strMaturity + "  ");

			mapMaturityTS.put (strMaturity, mktSurf.maturityAnchorTermStructure (strMaturity));
		}

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (double dblStrike : adblStrikeATMFactor) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (dblStrike, 1, 2, 1.) + "    =>");

			for (String strMaturity : astrMaturityTenor) {
				NodeStructure tsMaturity = mapMaturityTS.get (strMaturity);

				System.out.print ("  " + FormatUtil.FormatDouble (tsMaturity.node ((int) dblStrike), 2, 2, 100.) + "%");
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

		double dblRho = 0.3;
		double dblKappa = 1.;
		double dblSigma = 0.5;
		double dblTheta = 0.2;
		double dblLambda = 0.;

		HestonOptionPricerParams hopp = new HestonOptionPricerParams (
			HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_AMST_2007,
			dblRho,
			dblKappa,
			dblSigma,
			dblTheta,
			dblLambda,
			PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL
		);

		MarketSurface priceSurfCubicPoly = ScenarioMarketSurfaceBuilder.HestonRunMarketSurface (
			"HESTON1993_CUBICPOLY_CALLPRICE_SURFACE",
			dtStart,
			"USD",
			0.01,
			1.,
			false,
			0.20,
			adblStrikeATMFactorCalib,
			astrMaturityTenorCalib,
			hopp,
			true,
			CubicPolySCBC(),
			CubicPolySCBC()
		);

		EvaluateSplineSurface (
			priceSurfCubicPoly,
			adblStrikeATMFactorCalib,
			astrMaturityTenorCalib
		);

		EvaluateSplineSurface (
			priceSurfCubicPoly,
			new double[] {0.500, 0.700, 0.850, 1.000, 1.150, 1.300, 1.500},
			new String[] {"06M", "21M", "36M", "51M", "66M"}
		);

		EvaluateStrikeTermStructure (
			priceSurfCubicPoly,
			new double[] {0.500, 0.700, 0.850, 1.000, 1.150, 1.300, 1.500},
			new String[] {"06M", "21M", "36M", "51M", "66M"}
		);

		EvaluateMaturityTermStructure (
			priceSurfCubicPoly,
			new double[] {0.500, 0.700, 0.850, 1.000, 1.150, 1.300, 1.500},
			new String[] {"06M", "21M", "36M", "51M", "66M"}
		);

		EnvManager.TerminateEnv();
	}
}
