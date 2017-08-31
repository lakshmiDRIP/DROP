
package org.drip.sample.netting;

import org.drip.analytics.date.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
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
 * PortfolioPathAggregationUncorrelated generates the Aggregation of the Portfolio Paths evolved using
 * 	Uncorrelated Market Parameters. The References are:
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

public class PortfolioPathAggregationUncorrelated {

	private static final double[] NumeraireValueRealization (
		final DiffusionEvolver deNumeraireValue,
		final double dblNumeraireValueInitial,
		final double dblTime,
		final double dblTimeWidth,
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
				SequenceGenerator.Gaussian (iNumStep)
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
				SequenceGenerator.Gaussian (iNumStep)
			),
			adblTimeWidth
		);

		for (int j = 0; j <= iNumStep; ++j)
			adblNumeraireValue[j] = aJDV[j].value();

		return adblNumeraireValue;
	}

	private static final double[][] CollateralPortfolioValueRealization (
		final DiffusionEvolver deCollateralPortfolioValue,
		final double dblCollateralPortfolioValueInitial,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep,
		final int iNumPath)
		throws Exception
	{
		double[][] aablCollateralPortfolioValue = new double[iNumPath][iNumStep + 1];
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		for (int i = 0; i < iNumPath; ++i) {
			JumpDiffusionEdge[] aJDE = deCollateralPortfolioValue.incrementSequence (
				new JumpDiffusionVertex (
					dblTime,
					dblCollateralPortfolioValueInitial,
					0.,
					false
				),
				JumpDiffusionEdgeUnit.Diffusion (
					adblTimeWidth,
					SequenceGenerator.Gaussian (iNumStep)
				),
				dblTimeWidth
			);

			aablCollateralPortfolioValue[i][0] = dblCollateralPortfolioValueInitial;

			for (int j = 1; j <= iNumStep; ++j)
				aablCollateralPortfolioValue[i][j] = aJDE[j - 1].finish();
		}

		return aablCollateralPortfolioValue;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		double dblTime = 5.;
		int iNumPath = 100000;
		double dblAssetDrift = 0.06;
		double dblAssetVolatility = 0.15;
		double dblAssetInitial = 1.;
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

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		double[][] aadblCollateralBalance = new double[iNumPath][iNumStep + 1];
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblBankFundingSpreadInitial = dblBankHazardRateInitial / (1. - dblBankRecoveryRateInitial);
		double dblCounterPartyFundingSpreadInitial = dblCounterPartyHazardRateInitial / (1. - dblCounterPartyRecoveryRateInitial);

		JulianDate dtSpot = DateUtil.Today();

		double[][] aadblCollateralPortfolioValue = CollateralPortfolioValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblAssetDrift,
					dblAssetVolatility
				)
			),
			dblAssetInitial,
			dblTime,
			dblTimeWidth,
			iNumStep,
			iNumPath
		);

		double[] adblOvernightNumeraire = VertexNumeraireRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblOvernightDrift,
					dblOvernightVolatility
				)
			),
			dblOvernightInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblCSA = VertexNumeraireRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCSADrift,
					dblCSAVolatility
				)
			),
			dblCSAInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblBankHazardRate = NumeraireValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblBankHazardRateDrift,
					dblBankHazardRateVolatility
				)
			),
			dblBankHazardRateInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblBankRecoveryRate = NumeraireValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblBankRecoveryRateDrift,
					dblBankRecoveryRateVolatility
				)
			),
			dblBankRecoveryRateInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblCounterPartyHazardRate = NumeraireValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCounterPartyHazardRateDrift,
					dblCounterPartyHazardRateVolatility
				)
			),
			dblCounterPartyHazardRateInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblCounterPartyRecoveryRate = NumeraireValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCounterPartyRecoveryRateDrift,
					dblCounterPartyRecoveryRateVolatility
				)
			),
			dblCounterPartyRecoveryRateInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblBankFundingSpread = NumeraireValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblBankFundingSpreadDrift,
					dblBankFundingSpreadVolatility
				)
			),
			dblBankFundingSpreadInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblCounterPartyFundingSpread = NumeraireValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblCounterPartyFundingSpreadDrift,
					dblCounterPartyFundingSpreadVolatility
				)
			),
			dblCounterPartyFundingSpreadInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		for (int i = 0; i <= iNumStep; ++i) {
			aMV[i] = new MarketVertex (
				adtVertex[i] = dtSpot.addMonths (6 * i),
				Double.NaN,
				dblOvernightDrift,
				new NumeraireMarketVertex (
					adblOvernightNumeraire[0],
					adblOvernightNumeraire[i]
				),
				dblCSADrift,
				new NumeraireMarketVertex (
					adblCSA[0],
					adblCSA[i]
				),
				new EntityMarketVertex (
					Math.exp (-0.5 * adblBankHazardRate[i] * i),
					adblBankHazardRate[i],
					adblBankRecoveryRate[i],
					adblBankFundingSpread[i],
					new NumeraireMarketVertex (
						Math.exp (-0.5 * adblBankHazardRate[i] * (1. - adblBankRecoveryRate[i]) * iNumStep),
						Math.exp (-0.5 * adblBankHazardRate[i] * (1. - adblBankRecoveryRate[i]) * (iNumStep - i))
					),
					Double.NaN,
					Double.NaN,
					null
				),
				new EntityMarketVertex (
					Math.exp (-0.5 * adblCounterPartyHazardRate[i] * i),
					adblCounterPartyHazardRate[i],
					adblCounterPartyRecoveryRate[i],
					adblCounterPartyFundingSpread[i],
					new NumeraireMarketVertex (
						Math.exp (-0.5 * adblCounterPartyHazardRate[i] * (1. - adblCounterPartyRecoveryRate[i]) * iNumStep),
						Math.exp (-0.5 * adblCounterPartyHazardRate[i] * (1. - adblCounterPartyRecoveryRate[i]) * (iNumStep - i))
					),
					Double.NaN,
					Double.NaN,
					null
				)
			);

			for (int j = 0; j < iNumPath; ++j)
				aadblCollateralBalance[j][i] = 0.;
		}

		MarketPath mp = new MarketPath (aMV);

		for (int i = 0; i < iNumPath; ++i) {
			AlbaneseAndersenVertex[] aHGVR = new AlbaneseAndersenVertex[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				aHGVR[j] = new AlbaneseAndersenVertex (
					adtVertex[j],
					aadblCollateralPortfolioValue[i][j],
					0.,
					0.
				);
			}

			CollateralGroupPath[] aHGP = new CollateralGroupPath[] {new CollateralGroupPath (aHGVR)};

			aMPEA[i] = new MonoPathExposureAdjustment (
				new AlbaneseAndersenNettingGroupPath[] {
					new AlbaneseAndersenNettingGroupPath (
						aHGP,
						mp
					)
				},
				new AlbaneseAndersenFundingGroupPath[] {
					new AlbaneseAndersenFundingGroupPath (
						aHGP,
						mp
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
