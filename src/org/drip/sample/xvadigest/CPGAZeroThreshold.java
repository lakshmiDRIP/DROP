
package org.drip.sample.xvadigest;

import org.drip.analytics.date.*;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.quant.common.FormatUtil;
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
 * CPGAZeroThreshold illustrates the Counter Party Aggregation over Netting Groups based Collateralized
 *  Collateral Groups with several Fix-Float Swaps under Zero Collateral Threshold. The References are:
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

public class CPGAZeroThreshold {

	private static final double[] ATMSwapRateOffsetRealization (
		final DiffusionEvolver deATMSwapRateOffset,
		final double dblATMSwapRateOffsetInitial,
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
				SequenceGenerator.Gaussian (iNumStep)
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
				dblTime,
				dblTimeWidth,
				iNumStep
			);

			for (int j = 0; j <= iNumStep; ++j)
				adblSwapPortfolioValueRealization[j] += dblTimeWidth * (iNumStep - j) * adblATMSwapRateOffsetRealization[j];
		}

		return adblSwapPortfolioValueRealization;
	}

	private static final double[][] SwapPortfolioValueRealization (
		final DiffusionEvolver deATMSwapRate,
		final double dblSwapPortfolioValueStart,
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumSwap,
		final int iNumSimulation)
		throws Exception
	{
		double[][] aadblSwapPortfolioValueRealization = new double[iNumSimulation][];

		for (int i = 0; i < iNumSimulation; ++i)
			aadblSwapPortfolioValueRealization[i] = SwapPortfolioValueRealization (
				deATMSwapRate,
				dblSwapPortfolioValueStart,
				iNumStep,
				dblTime,
				dblTimeWidth,
				iNumSwap
			);

		return aadblSwapPortfolioValueRealization;
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
		double dblATMSwapRateStart = 0.;
		double dblATMSwapRateDrift = 0.0;
		double dblATMSwapRateVolatility = 0.25;
		double dblOvernightNumeraireDrift = 0.01;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankRecoveryRate = 0.40;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;

		JulianDate dtSpot = DateUtil.Today();

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		double dblBankFundingSpread = dblBankHazardRate / (1. - dblBankRecoveryRate);
		MonoPathExposureAdjustment[] aMPEA = new MonoPathExposureAdjustment[iNumPath];
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		CollateralGroupSpecification cgs = CollateralGroupSpecification.FixedThreshold (
			"FIXEDTHRESHOLD",
			0.,
			0.
		);

		CounterPartyGroupSpecification cpgs = CounterPartyGroupSpecification.Standard ("CPGROUP");

		double[][] aadblSwapPortfolioValueRealization = SwapPortfolioValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblATMSwapRateDrift,
					dblATMSwapRateVolatility
				)
			),
			dblATMSwapRateStart,
			iNumStep,
			dblTime,
			dblTimeWidth,
			iNumSwap,
			iNumPath
		);

		for (int i = 0; i <= iNumStep; ++i)
			aMV[i] = new MarketVertex (
				adtVertex[i] = dtSpot.addMonths (6 * i),
				Double.NaN,
				dblOvernightNumeraireDrift,
				new NumeraireMarketVertex (
					Math.exp (-0.5 * dblOvernightNumeraireDrift * iNumStep),
					Math.exp (-0.5 * dblOvernightNumeraireDrift * (iNumStep - i))
				),
				dblCSADrift,
				new NumeraireMarketVertex (
					Math.exp (-0.5 * dblCSADrift * iNumStep),
					Math.exp (-0.5 * dblCSADrift * (iNumStep - i))
				),
				new EntityMarketVertex (
					Math.exp (-0.5 * dblBankHazardRate * i),
					dblBankHazardRate,
					dblBankRecoveryRate,
					dblBankFundingSpread,
					new NumeraireMarketVertex (
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankRecoveryRate) * iNumStep),
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankRecoveryRate) * (iNumStep - i))
					),
					Double.NaN,
					Double.NaN,
					null
				),
				new EntityMarketVertex (
					Math.exp (-0.5 * dblCounterPartyHazardRate * i),
					dblCounterPartyHazardRate,
					dblCounterPartyRecoveryRate,
					dblCounterPartyFundingSpread,
					new NumeraireMarketVertex (
						Math.exp (-0.5 * dblCounterPartyHazardRate * (1. - dblCounterPartyRecoveryRate) * iNumStep),
						Math.exp (-0.5 * dblCounterPartyHazardRate * (1. - dblCounterPartyRecoveryRate) * (iNumStep - i))
					),
					Double.NaN,
					Double.NaN,
					null
				)
			);

		MarketPath mp = new MarketPath (aMV);

		for (int i = 0; i < iNumPath; ++i) {
			JulianDate dtStart = dtSpot;
			double dblValueStart = dblTime * dblATMSwapRateStart;
			AlbaneseAndersenVertex[] aHGVR = new AlbaneseAndersenVertex[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				JulianDate dtEnd = adtVertex[j];
				double dblCollateralBalance = 0.;
				double dblValueEnd = aadblSwapPortfolioValueRealization[i][j];

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
					aadblSwapPortfolioValueRealization[i][j],
					0.,
					dblCollateralBalance
				);

				dtStart = dtEnd;
				dblValueStart = dblValueEnd;
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
