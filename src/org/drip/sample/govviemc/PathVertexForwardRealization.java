
package org.drip.sample.govviemc;

import java.util.List;

import org.drip.analytics.date.*;
import org.drip.analytics.support.Helper;
import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.*;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.curve.BasisSplineGovvieYield;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.nonlinear.FlatForwardGovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * PathVertexForwardRealization demonstrates the Simulations of the Per-Path Forward Vertex Govvie Yield
 *  Nodes.
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
			strDump = strDump + " " + FormatUtil.FormatDouble (bsgyGround.yield (astrTenor[iDimension]), 1, 3, 100.) + "% |";

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
					strDump = strDump + " " + FormatUtil.FormatDouble (ffgc.yield (astrTenor[iDimension]), 1, 3, 100.) + "% |";

				System.out.println (strDump + "|");
			}
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------------------------------------------------------------------------------------||\n");
	}
}
