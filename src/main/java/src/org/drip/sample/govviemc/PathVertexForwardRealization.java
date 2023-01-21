
package org.drip.sample.govviemc;

import java.util.List;

import org.drip.analytics.date.*;
import org.drip.analytics.support.Helper;
import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.*;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.curve.BasisSplineGovvieYield;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.nonlinear.FlatForwardGovvieCurve;

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
 * <i>PathVertexForwardRealization</i> demonstrates the Simulations of the Per-Path Forward Vertex Govvie
 * Yield Nodes.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/govviemc/README.md">Monte Carlo Govvie Path Vertexes</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathVertexForwardRealization {

	private static final GovvieCurve GovvieCurve (
		final JulianDate dtSpot,
		final String strCode,
		final String[] astrTenor,
		final double[] adblCoupon,
		final double[] adblYield)
		throws Exception
	{
		JulianDate[] adtMaturity = new JulianDate[astrTenor.length];
		JulianDate[] adtEffective = new JulianDate[astrTenor.length];

		for (int i = 0; i < astrTenor.length; ++i) {
			adtEffective[i] = dtSpot;

			adtMaturity[i] = dtSpot.addTenor (astrTenor[i]);
		}

		return LatentMarketStateBuilder.GovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
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

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MARCH,
			24
		);

		int iNumPath = 50;
		int iNumVertex = 1;
		double dblTimeWidth = 1.0;
		double dblVolatility = 0.10;
		String strTreasuryCode = "UST";

		String[] astrTenor = new String[] {
			"01Y",
			"02Y",
			"03Y",
			"05Y",
			"07Y",
			"10Y",
			"20Y",
			"30Y"
		};

		double[] adblTreasuryCoupon = new double[] {
			0.0100,
			0.0100,
			0.0125,
			0.0150,
			0.0200,
			0.0225,
			0.0250,
			0.0300
		};

		double[] adblTreasuryYield = new double[] {
			0.0083,	//  1Y
			0.0122, //  2Y
			0.0149, //  3Y
			0.0193, //  5Y
			0.0227, //  7Y
			0.0248, // 10Y
			0.0280, // 20Y
			0.0308  // 30Y
		};

		BasisSplineGovvieYield bsgyGround = (BasisSplineGovvieYield) GovvieCurve (
			dtSpot,
			strTreasuryCode,
			astrTenor,
			adblTreasuryCoupon,
			adblTreasuryYield
		);

		double[] adblInitialForward = bsgyGround.flatForward (astrTenor).nodeValues();

		DiffusionEvolver de = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				0.,
				dblVolatility
			)
		);

		int iNumDimension = astrTenor.length;
		DiffusionEvolver[] aDE = new DiffusionEvolver[iNumDimension];
		double[][] aadblCorrelation = new double[iNumDimension][iNumDimension];
		double[][] aadblPathForwardYield = new double[iNumVertex][iNumDimension];

		for (int i = 0; i < iNumDimension; ++i) {
			aDE[i] = de;

			for (int j = 0; j < iNumDimension; ++j)
				aadblCorrelation[i][j] = i == j ? 1. : 0.;
		}

		System.out.println ("\n\t||------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		String strDump = "\t|| TENOR ";

		for (int i = 0; i < iNumDimension; ++i)
			strDump += " |   " + astrTenor[i] + "  ";

		for (int i = 0; i < iNumDimension; ++i)
			strDump += " |   " + astrTenor[i] + "  ";

		System.out.println (strDump + " ||");

		strDump = "\t|| " + FormatUtil.FormatDouble (0, 3, 0, 1.) + " => ";

		System.out.println ("\t||------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < iNumDimension; ++i)
			strDump += " " + FormatUtil.FormatDouble (adblInitialForward[i], 1, 3, 100.) + "% |";

		for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
			strDump = strDump + " " + FormatUtil.FormatDouble (bsgyGround.yld (astrTenor[iDimension]), 1, 3, 100.) + "% |";

		System.out.println (strDump + "|");

		CorrelatedPathVertexDimension cpvd = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			aadblCorrelation,
			iNumVertex,
			iNumPath,
			false,
			null
		);

		VertexRd[] aVertexRd = cpvd.multiPathVertexRd();

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			List<double[]> lsVertexRd = aVertexRd[iPath].vertexList();

			JumpDiffusionEdgeUnit[][] aaUR = new JumpDiffusionEdgeUnit[iNumDimension][iNumVertex];
			JumpDiffusionVertex[][] aaJDV = new JumpDiffusionVertex[iNumDimension][iNumVertex + 1];

			for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
				double[] adblRd = lsVertexRd.get (iTimeVertex);

				for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
					aaUR[iDimension][iTimeVertex] = new JumpDiffusionEdgeUnit (
						dblTimeWidth,							
						adblRd[iDimension],
						0.
					);
			}

			for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
				aaJDV[iDimension] = aDE[iDimension].vertexSequence (
					new JumpDiffusionVertex (
						0.,
						adblInitialForward[iDimension],
						0.,
						false
					),
					aaUR[iDimension],
					dblTimeWidth
				);

			for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
				strDump = "\t|| " + FormatUtil.FormatDouble (iTimeVertex + 1, 3, 0, 1.) + " => ";

				for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
					strDump = strDump + " " + FormatUtil.FormatDouble (
						aadblPathForwardYield[iTimeVertex][iDimension] = aaJDV[iDimension][iTimeVertex].value(), 1, 3, 100.
					) + "% |";

				JulianDate dtEvent = dtSpot.addYears (iTimeVertex + 1);

				FlatForwardGovvieCurve ffgc = new FlatForwardGovvieCurve (
					dtEvent.julian(),
					strTreasuryCode,
					bsgyGround.currency(),
					Helper.TenorToDate (
						dtEvent,
						astrTenor
					),
					aadblPathForwardYield[iTimeVertex]
				);

				for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
					strDump = strDump + " " + FormatUtil.FormatDouble (ffgc.yld (astrTenor[iDimension]), 1, 3, 100.) + "% |";

				System.out.println (strDump + "|");
			}
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------------------------------------------------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
