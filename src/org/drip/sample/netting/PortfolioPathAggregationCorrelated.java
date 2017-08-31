
package org.drip.sample.netting;

import org.drip.analytics.date.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;
import org.drip.xva.cpty.*;
import org.drip.xva.hypothecation.*;
import org.drip.xva.strategy.*;
import org.drip.xva.universe.*;

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
 * PortfolioPathAggregationCorrelated generates the Aggregation of the Portfolio Paths evolved using
 * 	Correlated Market Parameters. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioPathAggregationCorrelated {

	private static final double[] NumeraireValueRealization (
		final DiffusionEvolver deNumeraireValue,
		final double dblNumeraireValueInitial,
		final double dblTime,
		final double dblTimeWidth,
		final double[] adblRandom,
		final int iNumStep)
		throws Exception
	{
		double[] adblNumeraireValue = new double[iNumStep + 1];
		adblNumeraireValue[0] = dblNumeraireValueInitial;
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEdge[] aJDE = deNumeraireValue.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblNumeraireValueInitial,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				adblRandom
			),
			dblTimeWidth
		);

		for (int j = 1; j <= iNumStep; ++j)
			adblNumeraireValue[j] = aJDE[j - 1].finish();

		return adblNumeraireValue;
	}

	private static final double[] VertexNumeraireRealization (
		final DiffusionEvolver deNumeraireValue,
		final double dblNumeraireValueInitial,
		final double dblTime,
		final double dblTimeWidth,
		final double[] adblRandom,
		final int iNumStep)
		throws Exception
	{
		double[] adblNumeraireValue = new double[iNumStep + 1];
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionVertex[] aJDV = deNumeraireValue.vertexSequenceReverse (
			new JumpDiffusionVertex (
				dblTime,
				dblNumeraireValueInitial,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				adblRandom
			),
			adblTimeWidth
		);

		for (int j = 0; j <= iNumStep; ++j)
			adblNumeraireValue[j] = aJDV[j].value();

		return adblNumeraireValue;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		double dblTime = 5.;
		int iNumPath = 10000;
		double dblCollateralPortfolioDrift = 0.06;
		double dblCollateralPortfolioVolatility = 0.15;
		double dblCollateralPortfolioInitial = 1.;
		double dblOvernightDrift = 0.004;
		double dblOvernightVolatility = 0.02;
		double dblOvernightInitial = 1.;
		double dblCSADrift = 0.01;
		double dblCSAVolatility = 0.05;
		double dblCSAInitial = 1.;
		double dblBankHazardRateDrift = 0.002;
		double dblBankHazardRateVolatility = 0.20;
		double dblBankHazardRateInitial = 0.015;
		double dblBankRecoveryRateDrift = 0.002;
		double dblBankRecoveryRateVolatility = 0.02;
		double dblBankRecoveryRateInitial = 0.40;
		double dblCounterPartyHazardRateDrift = 0.002;
		double dblCounterPartyHazardRateVolatility = 0.30;
		double dblCounterPartyHazardRateInitial = 0.030;
		double dblCounterPartyRecoveryRateDrift = 0.002;
		double dblCounterPartyRecoveryRateVolatility = 0.02;
		double dblCounterPartyRecoveryRateInitial = 0.30;
		double dblBankFundingSpreadDrift = 0.00002;
		double dblBankFundingSpreadVolatility = 0.002;
		double dblCounterPartyFundingSpreadDrift = 0.000022;
		double dblCounterPartyFundingSpreadVolatility = 0.0022;

		double[][] aadblCorrelation = new double[][] {
			{1.00,  0.00,  0.03,  0.07,  0.04,  0.05,  0.08,  0.00,  0.00},  // PORTFOLIO
			{0.00,  1.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00},  // OVERNIGHT
			{0.03,  0.00,  1.00,  0.26,  0.33,  0.21,  0.35,  0.13,  0.00},  // CSA
			{0.07,  0.00,  0.26,  1.00,  0.45, -0.17,  0.07,  0.77,  0.00},  // BANK HAZARD
			{0.04,  0.00,  0.33,  0.45,  1.00, -0.22, -0.54,  0.58,  0.00},  // COUNTER PARTY HAZARD
			{0.05,  0.00,  0.21, -0.17, -0.22,  1.00,  0.47, -0.23,  0.00},  // BANK RECOVERY
			{0.08,  0.00,  0.35,  0.07, -0.54,  0.47,  1.00,  0.01,  0.00},  // COUNTER PARTY RECOVERY
			{0.00,  0.00,  0.13,  0.77,  0.58, -0.23,  0.01,  1.00,  0.00},  // BANK FUNDING SPREAD
			{0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  1.00}   // COUNTER PARTY FUNDING SPREAD
		};

		double dblTimeWidth = dblTime / iNumStep;
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		double[][] aadblCollateralBalance = new double[iNumPath][iNumStep + 1];
		double[][] aadblCollateralPortfolio = new double[iNumPath][iNumStep + 1];
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblBankFundingSpreadInitial = dblBankHazardRateInitial / (1. - dblBankRecoveryRateInitial);
		double dblCounterPartyFundingSpreadInitial = dblCounterPartyHazardRateInitial / (1. - dblCounterPartyRecoveryRateInitial);

		JulianDate dtSpot = DateUtil.Today();

		for (int j = 0; j <= iNumStep; ++j)
			adtVertex[j] = dtSpot.addMonths (6 * j + 6);

		DiffusionEvolver deCollateralPortfolio = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblCollateralPortfolioDrift,
				dblCollateralPortfolioVolatility
			)
		);

		DiffusionEvolver deOvernight = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblOvernightDrift,
				dblOvernightVolatility
			)
		);

		DiffusionEvolver deCSA = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblCSADrift,
				dblCSAVolatility
			)
		);

		DiffusionEvolver deBankHazardRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblBankHazardRateDrift,
				dblBankHazardRateVolatility
			)
		);

		DiffusionEvolver deCounterPartyHazardRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblCounterPartyHazardRateDrift,
				dblCounterPartyHazardRateVolatility
			)
		);

		DiffusionEvolver deBankRecoveryRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblBankRecoveryRateDrift,
				dblBankRecoveryRateVolatility
			)
		);

		DiffusionEvolver deCounterPartyRecoveryRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblCounterPartyRecoveryRateDrift,
				dblCounterPartyRecoveryRateVolatility
			)
		);

		DiffusionEvolver deBankFundingSpread = new DiffusionEvolver (
			DiffusionEvaluatorLinear.Standard (
				dblBankFundingSpreadDrift,
				dblBankFundingSpreadVolatility
			)
		);

		DiffusionEvolver deCounterPartyFundingSpread = new DiffusionEvolver (
			DiffusionEvaluatorLinear.Standard (
				dblCounterPartyFundingSpreadDrift,
				dblCounterPartyFundingSpreadVolatility
			)
		);

		for (int i = 0; i < iNumPath; ++i) {
			double[][] aadblNumeraire = Matrix.Transpose (
				SequenceGenerator.GaussianJoint (
					iNumStep,
					aadblCorrelation
				)
			);

			aadblCollateralPortfolio[i] = NumeraireValueRealization (
				deCollateralPortfolio,
				dblCollateralPortfolioInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[0],
				iNumStep
			);

			double[] adblOvernightNumeraire = VertexNumeraireRealization (
				deOvernight,
				dblOvernightInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[1],
				iNumStep
			);

			double[] adblCSA = VertexNumeraireRealization (
				deCSA,
				dblCSAInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[2],
				iNumStep
			);

			double[] adblBankHazardRate = NumeraireValueRealization (
				deBankHazardRate,
				dblBankHazardRateInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[3],
				iNumStep
			);

			double[] adblCounterPartyHazardRate = NumeraireValueRealization (
				deCounterPartyHazardRate,
				dblCounterPartyHazardRateInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[4],
				iNumStep
			);

			double[] adblBankRecoveryRate = NumeraireValueRealization (
				deBankRecoveryRate,
				dblBankRecoveryRateInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[5],
				iNumStep
			);

			double[] adblCounterPartyRecoveryRate = NumeraireValueRealization (
				deCounterPartyRecoveryRate,
				dblCounterPartyRecoveryRateInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[6],
				iNumStep
			);

			double[] adblBankFundingSpread = NumeraireValueRealization (
				deBankFundingSpread,
				dblBankFundingSpreadInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[7],
				iNumStep
			);

			double[] adblCounterPartyFundingSpread = NumeraireValueRealization (
				deCounterPartyFundingSpread,
				dblCounterPartyFundingSpreadInitial,
				dblTime,
				dblTimeWidth,
				aadblNumeraire[8],
				iNumStep
			);

			MarketVertex[] aMV = new MarketVertex [iNumStep + 1];
			AlbaneseAndersenVertex[] aHGVR = new AlbaneseAndersenVertex[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				aMV[j] = new MarketVertex (
					adtVertex[j] = dtSpot.addMonths (6 * j),
					Double.NaN,
					dblOvernightDrift,
					new NumeraireMarketVertex (
						adblOvernightNumeraire[0],
						adblOvernightNumeraire[j]
					),
					dblCSADrift,
					new NumeraireMarketVertex (
						adblCSA[0],
						adblCSA[j]
					),
					new EntityMarketVertex (
						Math.exp (-0.5 * adblBankHazardRate[j] * j),
						adblBankHazardRate[j],
						adblBankRecoveryRate[j],
						adblBankFundingSpread[j],
						new NumeraireMarketVertex (
							Math.exp (-0.5 * adblBankHazardRate[j] * (1. - adblBankRecoveryRate[j]) * iNumStep),
							Math.exp (-0.5 * adblBankHazardRate[j] * (1. - adblBankRecoveryRate[j]) * (iNumStep - j))
						),
						Double.NaN,
						Double.NaN,
						null
					),
					new EntityMarketVertex (
						Math.exp (-0.5 * adblCounterPartyHazardRate[j] * j),
						adblCounterPartyHazardRate[j],
						adblCounterPartyRecoveryRate[j],
						adblCounterPartyFundingSpread[j],
						new NumeraireMarketVertex (
							Math.exp (-0.5 * adblCounterPartyHazardRate[j] * (1. - adblCounterPartyRecoveryRate[j]) * iNumStep),
							Math.exp (-0.5 * adblCounterPartyHazardRate[j] * (1. - adblCounterPartyRecoveryRate[j]) * (iNumStep - j))
						),
						Double.NaN,
						Double.NaN,
						null
					)
				);

				aadblCollateralBalance[i][j] = 0.;

				aHGVR[j] = new AlbaneseAndersenVertex (
					adtVertex[j],
					aadblCollateralPortfolio[i][j],
					0.,
					0.
				);
			}

			CollateralGroupPath[] aHGP = new CollateralGroupPath[] {new CollateralGroupPath (aHGVR)};

			aMPEA[i] = new MonoPathExposureAdjustment (
				new AlbaneseAndersenNettingGroupPath[] {
					new AlbaneseAndersenNettingGroupPath (
						aHGP,
						new MarketPath (aMV)
					)
				},
				new AlbaneseAndersenFundingGroupPath[] {
					new AlbaneseAndersenFundingGroupPath (
						aHGP,
						new MarketPath (aMV)
					)
				}
			);
		}

		ExposureAdjustmentAggregator eaa = new ExposureAdjustmentAggregator (aMPEA);

		JulianDate[] adtVertexNode = eaa.anchors();

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|         DATE         =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + " |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		double[] adblExposure = eaa.collateralizedExposure();

		strDump = "\t|       EXPOSURE       =>";

		for (int j = 0; j < adblExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblPositiveExposure = eaa.collateralizedPositiveExposure();

		strDump = "\t|  POSITIVE EXPOSURE   =>";

		for (int j = 0; j < adblPositiveExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblPositiveExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblNegativeExposure = eaa.collateralizedNegativeExposure();

		strDump = "\t|  NEGATIVE EXPOSURE   =>";

		for (int j = 0; j < adblNegativeExposure.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblNegativeExposure[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblExposurePV = eaa.collateralizedExposurePV();

		strDump = "\t|      EXPOSURE PV     =>";

		for (int j = 0; j < adblExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblPositiveExposurePV = eaa.collateralizedPositiveExposurePV();

		strDump = "\t| POSITIVE EXPOSURE PV =>";

		for (int j = 0; j < adblPositiveExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblPositiveExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		double[] adblNegativeExposurePV = eaa.collateralizedNegativeExposurePV();

		strDump = "\t| NEGATIVE EXPOSURE PV =>";

		for (int j = 0; j < adblNegativeExposurePV.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (adblNegativeExposurePV[j], 1, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println();

		System.out.println ("\t||-------------------||");

		System.out.println ("\t||  UCVA  => " + FormatUtil.FormatDouble (eaa.ucva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t|| FTDCVA => " + FormatUtil.FormatDouble (eaa.ftdcva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  CVA   => " + FormatUtil.FormatDouble (eaa.cva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  CVACL => " + FormatUtil.FormatDouble (eaa.cvacl().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  DVA   => " + FormatUtil.FormatDouble (eaa.dva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FVA   => " + FormatUtil.FormatDouble (eaa.fva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FDA   => " + FormatUtil.FormatDouble (eaa.fda().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FCA   => " + FormatUtil.FormatDouble (eaa.fca().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  FBA   => " + FormatUtil.FormatDouble (eaa.fba().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||  SFVA  => " + FormatUtil.FormatDouble (eaa.sfva().amount(), 2, 2, 100.) + "% ||");

		System.out.println ("\t||-------------------||");
	}
}
