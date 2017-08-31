
package org.drip.sample.xvadigest;

import org.drip.analytics.date.*;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;
import org.drip.xva.cpty.*;
import org.drip.xva.hypothecation.*;
import org.drip.xva.set.*;
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
 * CPGAZeroThresholdCorrelated illustrates the Counter Party Aggregation over Netting Groups based
 *  Collateralized Collateral Groups with several Fix-Float Swaps under Zero Collateral Threshold, and with
 *  built in Factor Correlations across the Numeraires. The References are:
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

public class CPGAZeroThresholdCorrelated {

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

	private static final double[] ATMSwapRateOffsetRealization (
		final DiffusionEvolver deATMSwapRateOffset,
		final double dblATMSwapRateOffsetInitial,
		final double[] adblRandom,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep)
		throws Exception
	{
		double[] adblATMSwapRateOffset = new double[iNumStep + 1];
		adblATMSwapRateOffset[0] = dblATMSwapRateOffsetInitial;
		double[] adblTimeWidth = new double[iNumStep];

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEdge[] aJDE = deATMSwapRateOffset.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblATMSwapRateOffsetInitial,
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
			adblATMSwapRateOffset[j] = aJDE[j - 1].finish();

		return adblATMSwapRateOffset;
	}

	private static final double[] SwapPortfolioValueRealization (
		final DiffusionEvolver deATMSwapRate,
		final double dblATMSwapRateStart,
		final double[] adblRandom,
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumSwap)
		throws Exception
	{
		double[] adblSwapPortfolioValueRealization = new double[iNumStep + 1];

		for (int i = 0; i < iNumStep; ++i)
			adblSwapPortfolioValueRealization[i] = 0.;

		for (int i = 0; i < iNumSwap; ++i) {
			double[] adblATMSwapRateOffsetRealization = ATMSwapRateOffsetRealization (
				deATMSwapRate,
				dblATMSwapRateStart,
				adblRandom,
				dblTime,
				dblTimeWidth,
				iNumStep
			);

			for (int j = 0; j <= iNumStep; ++j)
				adblSwapPortfolioValueRealization[j] += dblTimeWidth * (iNumStep - j) * adblATMSwapRateOffsetRealization[j];
		}

		return adblSwapPortfolioValueRealization;
	}

	private static final void UDTDump (
		final String strHeader,
		final JulianDate[] adtVertexNode,
		final UnivariateDiscreteThin[] aUDT)
		throws Exception
	{
		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (strHeader);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|       DATE      =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + "  |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		 strDump = "\t|     AVERAGE     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].average(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MAXIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].maximum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MINIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].minimum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|      ERROR      =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].error(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
	}

	private static final void UDTDump (
		final String strHeader,
		final UnivariateDiscreteThin udt)
		throws Exception
	{
		System.out.println (
			strHeader +
			FormatUtil.FormatDouble (udt.average(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.maximum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.minimum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.error(), 3, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		int iNumSwap = 10;
		double dblTime = 5.;
		int iNumPath = 10000;
		double dblATMSwapRateOffsetDrift = 0.0;
		double dblATMSwapRateOffsetVolatility = 0.25;
		double dblATMSwapRateOffsetStart = 0.;
		double dblOvernightNumeraireDrift = 0.004;
		double dblOvernightNumeraireVolatility = 0.02;
		double dblOvernightNumeraireInitial = 1.;
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

		JulianDate dtSpot = DateUtil.Today();

		CollateralGroupSpecification cgs = CollateralGroupSpecification.FixedThreshold (
			"FIXEDTHRESHOLD",
			0.,
			0.
		);

		CounterPartyGroupSpecification cpgs = CounterPartyGroupSpecification.Standard ("CPGROUP");

		double dblTimeWidth = dblTime / iNumStep;
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		double[][] aadblPortfolioValue = new double[iNumPath][iNumStep + 1];
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblBankFundingSpreadInitial = dblBankHazardRateInitial / (1. - dblBankRecoveryRateInitial);
		double dblCounterPartyFundingSpreadInitial = dblCounterPartyHazardRateInitial / (1. - dblCounterPartyRecoveryRateInitial);

		DiffusionEvolver deATMSwapRateOffset = new DiffusionEvolver (
			DiffusionEvaluatorLinear.Standard (
				dblATMSwapRateOffsetDrift,
				dblATMSwapRateOffsetVolatility
			)
		);

		DiffusionEvolver deOvernightNumeraire = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblOvernightNumeraireDrift,
				dblOvernightNumeraireVolatility
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

			aadblPortfolioValue[i] = SwapPortfolioValueRealization (
				deATMSwapRateOffset,
				dblATMSwapRateOffsetStart,
				aadblNumeraire[0],
				iNumStep,
				dblTime,
				dblTimeWidth,
				iNumSwap
			);

			double[] adblOvernightNumeraire = VertexNumeraireRealization (
				deOvernightNumeraire,
				dblOvernightNumeraireInitial,
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

			JulianDate dtStart = dtSpot;
			MarketVertex[] aMV = new MarketVertex [iNumStep + 1];
			double dblValueStart = dblTime * dblATMSwapRateOffsetStart;
			AlbaneseAndersenVertex[] aHGVR = new AlbaneseAndersenVertex[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				aMV[j] = new MarketVertex (
					adtVertex[j] = dtSpot.addMonths (6 * j),
					Double.NaN,
					dblOvernightNumeraireDrift,
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

				JulianDate dtEnd = adtVertex[j];
				double dblCollateralBalance = 0.;
				double dblValueEnd = aadblPortfolioValue[i][j];

				if (0 != j) {
					CollateralAmountEstimator hae = new CollateralAmountEstimator (
						cgs,
						cpgs,
						new BrokenDateInterpolatorLinearT (
							dtStart.julian(),
							dtEnd.julian(),
							dblValueStart,
							dblValueEnd
						),
						Double.NaN
					);

					dblCollateralBalance = hae.postingRequirement (dtEnd);
				}

				aHGVR[j] = new AlbaneseAndersenVertex (
					adtVertex[j],
					aadblPortfolioValue[i][j],
					0.,
					dblCollateralBalance
				);

				dtStart = dtEnd;
				dblValueStart = dblValueEnd;
			}

			MarketPath mp = new MarketPath (aMV);

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

		ExposureAdjustmentDigest ead = eaa.digest();

		System.out.println();

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE                                                                                |",
			eaa.anchors(),
			ead.collateralizedExposure()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE                                                                               |",
			eaa.anchors(),
			ead.uncollateralizedExposure()
		);

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE PV                                                                             |",
			eaa.anchors(),
			ead.collateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE PV                                                                            |",
			eaa.anchors(),
			ead.uncollateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED POSITIVE EXPOSURE PV                                                                        |",
			eaa.anchors(),
			ead.collateralizedPositiveExposure()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED POSITIVE EXPOSURE PV                                                                       |",
			eaa.anchors(),
			ead.uncollateralizedPositiveExposure()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED NEGATIVE EXPOSURE PV                                                                        |",
			eaa.anchors(),
			ead.collateralizedNegativeExposure()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED NEGATIVE EXPOSURE PV                                                                       |",
			eaa.anchors(),
			ead.uncollateralizedNegativeExposure()
		);

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||  UCVA CVA FTDCVA DVA FCA UNIVARIATE THIN STATISTICS ||");

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                          ||");

		System.out.println ("\t||            - Path Average                           ||");

		System.out.println ("\t||            - Path Maximum                           ||");

		System.out.println ("\t||            - Path Minimum                           ||");

		System.out.println ("\t||            - Monte Carlo Error                      ||");

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  UCVA  => ",
			ead.ucva()
		);

		UDTDump (
			"\t|| FTDCVA => ",
			ead.ftdcva()
		);

		UDTDump (
			"\t||   CVA  => ",
			ead.cva()
		);

		UDTDump (
			"\t||  CVACL => ",
			ead.cvacl()
		);

		UDTDump (
			"\t||   DVA  => ",
			ead.dva()
		);

		UDTDump (
			"\t||   FVA  => ",
			ead.fva()
		);

		UDTDump (
			"\t||   FDA  => ",
			ead.fda()
		);

		UDTDump (
			"\t||   FCA  => ",
			ead.fca()
		);

		UDTDump (
			"\t||   FBA  => ",
			ead.fba()
		);

		UDTDump (
			"\t||  SFVA  => ",
			ead.sfva()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  Total => ",
			ead.totalVA()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println();
	}
}
