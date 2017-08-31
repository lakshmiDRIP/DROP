
package org.drip.sample.xvastrategy;

import org.drip.analytics.date.*;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.xva.cpty.*;
import org.drip.xva.definition.*;
import org.drip.xva.derivative.ReplicationPortfolioVertexBank;
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
 * FundingGroupSetOff demonstrates the Simulation Run of the Funding Group Exposure using the "Set Off"
 * 	Funding Strategy laid out in Burgard and Kjaer (2013). The References are:
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

public class FundingGroupSetOff {

	private static final double[] AssetValueRealization (
		final DiffusionEvolver deAssetValue,
		final double dblAssetValueInitial,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep)
		throws Exception
	{
		double[] ablAssetValue = new double[iNumStep + 1];
		double[] adblTimeWidth = new double[iNumStep];
		ablAssetValue[0] = dblAssetValueInitial;

		for (int i = 0; i < iNumStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEdge[] aJDE = deAssetValue.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblAssetValueInitial,
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
			ablAssetValue[j] = aJDE[j - 1].finish();

		return ablAssetValue;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		double dblTime = 5.;
		double dblAssetDrift = 0.06;
		double dblAssetVolatility = 0.15;
		double dblAssetValueInitial = 1.;
		double dblOISRate = 0.004;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankSeniorRecoveryRate = 0.40;
		double dblBankSubordinateRecoveryRate = 0.15;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;

		double dblTimeWidth = dblTime / iNumStep;
		MarketVertex[] aMV = new MarketVertex[iNumStep + 1];
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		BurgardKjaerVertex[] aBKV1 = new BurgardKjaerVertex[iNumStep + 1];
		BurgardKjaerVertex[] aBKV2 = new BurgardKjaerVertex[iNumStep + 1];
		double dblBankSeniorFundingSpread = dblBankHazardRate / (1. - dblBankSeniorRecoveryRate);
		double dblBankSubordinateFundingSpread = dblBankHazardRate / (1. - dblBankSubordinateRecoveryRate);
		double dblCounterPartyFundingSpread = dblCounterPartyHazardRate / (1. - dblCounterPartyRecoveryRate);

		JulianDate dtSpot = DateUtil.Today();

		CloseOutGeneral cog = new CloseOutBilateral (
			dblBankSeniorRecoveryRate,
			dblCounterPartyRecoveryRate
		);

		DiffusionEvolver deAssetValue = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblAssetDrift,
				dblAssetVolatility
			)
		);

		double[] adblAssetValuePath1 = AssetValueRealization (
			deAssetValue,
			dblAssetValueInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		double[] adblAssetValuePath2 = AssetValueRealization (
			deAssetValue,
			dblAssetValueInitial,
			dblTime,
			dblTimeWidth,
			iNumStep
		);

		System.out.println();

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                       PATH VERTEX EXPOSURES AND NUMERAIRE REALIZATIONS                                                       ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                                                                                                                                   ||");

		System.out.println ("\t|            - Path #1 Gross Exposure                                                                                                                          ||");

		System.out.println ("\t|            - Path #1 Positive Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Path #1 Negative Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Path #2 Gross Exposure                                                                                                                          ||");

		System.out.println ("\t|            - Path #2 Positive Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Path #2 Negative Exposure                                                                                                                       ||");

		System.out.println ("\t|            - Collateral Numeraire                                                                                                                            ||");

		System.out.println ("\t|            - Bank Survival Probability                                                                                                                       ||");

		System.out.println ("\t|            - Bank Recovery Rate                                                                                                                              ||");

		System.out.println ("\t|            - Bank Funding Spread                                                                                                                             ||");

		System.out.println ("\t|            - Counter Party Survival Probability                                                                                                              ||");

		System.out.println ("\t|            - Counter Party Recovery Rate                                                                                                                     ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i <= iNumStep; ++i) {
			aMV[i] = new MarketVertex (
				adtVertex[i] = dtSpot.addMonths (6 * i),
				Double.NaN,
				0.,
				new NumeraireMarketVertex (
					Math.exp (-0.5 * dblOISRate * iNumStep),
					Math.exp (-0.5 * dblOISRate * (iNumStep - i))
				),
				dblCSADrift,
				new NumeraireMarketVertex (
					Math.exp (-0.5 * dblCSADrift * iNumStep),
					Math.exp (-0.5 * dblCSADrift * (iNumStep - i))
				),
				new EntityMarketVertex (
					Math.exp (-0.5 * dblBankHazardRate * i),
					dblBankHazardRate,
					dblBankSeniorRecoveryRate,
					dblBankSeniorFundingSpread,
					new NumeraireMarketVertex (
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSeniorRecoveryRate) * iNumStep),
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSeniorRecoveryRate) * (iNumStep - i))
					),
					dblBankSubordinateRecoveryRate,
					dblBankSubordinateFundingSpread,
					new NumeraireMarketVertex (
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSubordinateRecoveryRate) * iNumStep),
						Math.exp (-0.5 * dblBankHazardRate * (1. - dblBankSubordinateRecoveryRate) * (iNumStep - i))
					)
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


			if (0 != i) {
				aBKV1[i] = BurgardKjaerVertexBuilder.SetOff (
					adtVertex[i],
					adblAssetValuePath1[i],
					0.,
					0.,
					new MarketEdge (
						aMV[i - 1],
						aMV[i]
					)
				);

				aBKV2[i] = BurgardKjaerVertexBuilder.SetOff (
					adtVertex[i],
					adblAssetValuePath2[i],
					0.,
					0.,
					new MarketEdge (
						aMV[i - 1],
						aMV[i]
					)
				);
			} else {
				aBKV1[i] = BurgardKjaerVertexBuilder.Initial (
					adtVertex[i],
					adblAssetValuePath1[i],
					aMV[i],
					cog
				);

				aBKV2[i] = BurgardKjaerVertexBuilder.Initial (
					adtVertex[i],
					adblAssetValuePath2[i],
					aMV[i],
					cog
				);
			}

			System.out.println (
				"\t| " + adtVertex[i] + " => " +
				FormatUtil.FormatDouble (aBKV1[i].collateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV1[i].uncollateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV1[i].collateralBalance(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV2[i].collateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV2[i].uncollateralized(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aBKV2[i].collateralBalance(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].overnightIndexRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].bank().survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].bank().seniorRecoveryRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].bank().seniorFundingSpread(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].counterParty().survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].counterParty().seniorRecoveryRate(), 1, 6, 1.) + " ||"
			);
		}

		MarketPath mp = new MarketPath (aMV);

		CollateralGroupPath[] aCGP1 = new CollateralGroupPath[] {new CollateralGroupPath (aBKV1)};

		CollateralGroupPath[] aCGP2 = new CollateralGroupPath[] {new CollateralGroupPath (aBKV2)};

		AlbaneseAndersenNettingGroupPath ngpaa2014_1 = new AlbaneseAndersenNettingGroupPath (
			aCGP1,
			mp
		);

		AlbaneseAndersenFundingGroupPath fgpaa2014_1 = new AlbaneseAndersenFundingGroupPath (
			aCGP1,
			mp
		);

		AlbaneseAndersenNettingGroupPath ngpaa2014_2 = new AlbaneseAndersenNettingGroupPath (
			aCGP2,
			mp
		);

		AlbaneseAndersenFundingGroupPath fgpaa2014_2 = new AlbaneseAndersenFundingGroupPath (
			aCGP2,
			mp
		);

		double[] adblPeriodUnilateralCreditAdjustment1 = ngpaa2014_1.periodUnilateralCreditAdjustment();

		double[] adblPeriodBilateralCreditAdjustment1 = ngpaa2014_1.periodBilateralCreditAdjustment();

		double[] adblPeriodCreditAdjustment1 = ngpaa2014_1.periodCreditAdjustment();

		double[] adblPeriodContraLiabilityCreditAdjustment1 = ngpaa2014_1.periodContraLiabilityCreditAdjustment();

		double[] adblPeriodUnilateralCreditAdjustment2 = ngpaa2014_2.periodUnilateralCreditAdjustment();

		double[] adblPeriodBilateralCreditAdjustment2 = ngpaa2014_2.periodBilateralCreditAdjustment();

		double[] adblPeriodCreditAdjustment2 = ngpaa2014_2.periodCreditAdjustment();

		double[] adblPeriodContraLiabilityCreditAdjustment2 = ngpaa2014_2.periodContraLiabilityCreditAdjustment();

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|               PERIOD UNILATERAL CREDIT, BILATERAL CREDIT, CREDIT, & CONTRA LIABILITY CREDIT VALUATION ADJUSTMENTS               ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|    - Forward Period                                                                                                             ||");

		System.out.println ("\t|    - Path #1 Period Unilateral Credit Adjustments                                                                               ||");

		System.out.println ("\t|    - Path #1 Period Bilateral Credit Adjustments                                                                                ||");

		System.out.println ("\t|    - Path #1 Period Credit Adjustments                                                                                          ||");

		System.out.println ("\t|    - Path #1 Period Contra-Liability Credit Adjustments                                                                         ||");

		System.out.println ("\t|    - Path #2 Period Unilateral Credit Adjustments                                                                               ||");

		System.out.println ("\t|    - Path #2 Period Bilateral Credit Adjustments                                                                                ||");

		System.out.println ("\t|    - Path #2 Period Credit Adjustments                                                                                          ||");

		System.out.println ("\t|    - Path #2 Period Contra-Liability Credit Adjustments                                                                         ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 1; i <= iNumStep; ++i) {
			System.out.println ("\t| [" +
				adtVertex[i - 1] + " -> " + adtVertex[i] + "] => " +
				FormatUtil.FormatDouble (adblPeriodUnilateralCreditAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodBilateralCreditAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodCreditAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodContraLiabilityCreditAdjustment1[i - 1], 1, 6, 1.) + " ||| " +
				FormatUtil.FormatDouble (adblPeriodUnilateralCreditAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodBilateralCreditAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodCreditAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodContraLiabilityCreditAdjustment2[i - 1], 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		double[] adblPeriodDebtAdjustment1 = ngpaa2014_1.periodDebtAdjustment();

		double[] adblPeriodFundingValueAdjustment1 = fgpaa2014_1.periodFundingValueAdjustment();

		double[] adblPeriodFundingDebtAdjustment1 = fgpaa2014_1.periodFundingDebtAdjustment();

		double[] adblPeriodFundingCostAdjustment1 = fgpaa2014_1.periodFundingCostAdjustment();

		double[] adblPeriodFundingBenefitAdjustment1 = fgpaa2014_1.periodFundingBenefitAdjustment();

		double[] adblPeriodSymmetricFundingValueAdjustment1 = fgpaa2014_1.periodSymmetricFundingValueAdjustment();

		double[] adblPeriodDebtAdjustment2 = ngpaa2014_2.periodDebtAdjustment();

		double[] adblPeriodFundingValueAdjustment2 = fgpaa2014_2.periodFundingValueAdjustment();

		double[] adblPeriodFundingDebtAdjustment2 = fgpaa2014_2.periodFundingDebtAdjustment();

		double[] adblPeriodFundingCostAdjustment2 = fgpaa2014_2.periodFundingCostAdjustment();

		double[] adblPeriodFundingBenefitAdjustment2 = fgpaa2014_2.periodFundingBenefitAdjustment();

		double[] adblPeriodSymmetricFundingValueAdjustment2 = fgpaa2014_2.periodSymmetricFundingValueAdjustment();

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                             DEBT VALUATION, FUNDING VALUATION, FUNDING DEBT, FUNDING COST, FUNDING BENEFIT, & SYMMETRIC FUNDING VALUATION ADJUSTMENTS                              ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|  L -> R:                                                                                                                                                                           ||");

		System.out.println ("\t|          - Path #1 Debt Valuation Adjustment                                                                                                                                       ||");

		System.out.println ("\t|          - Path #1 Funding Valuation Adjustment                                                                                                                                    ||");

		System.out.println ("\t|          - Path #1 Funding Debt Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #1 Funding Cost Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #1 Funding Benefit Adjustment                                                                                                                                      ||");

		System.out.println ("\t|          - Path #1 Symmatric Funding Valuation Adjustment                                                                                                                          ||");

		System.out.println ("\t|          - Path #2 Debt Valuation Adjustment                                                                                                                                       ||");

		System.out.println ("\t|          - Path #2 Funding Valuation Adjustment                                                                                                                                    ||");

		System.out.println ("\t|          - Path #2 Funding Debt Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #2 Funding Cost Adjustment                                                                                                                                         ||");

		System.out.println ("\t|          - Path #2 Funding Benefit Adjustment                                                                                                                                      ||");

		System.out.println ("\t|          - Path #2 Symmatric Funding Valuation Adjustment                                                                                                                          ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 1; i <= iNumStep; ++i) {
			System.out.println ("\t| [" +
				adtVertex[i - 1] + " -> " + adtVertex[i] + "] => " +
				FormatUtil.FormatDouble (adblPeriodDebtAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingValueAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingDebtAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingCostAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingBenefitAdjustment1[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodSymmetricFundingValueAdjustment1[i - 1], 1, 6, 1.) + " || " +
				FormatUtil.FormatDouble (adblPeriodDebtAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingValueAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingDebtAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingCostAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodFundingBenefitAdjustment2[i - 1], 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (adblPeriodSymmetricFundingValueAdjustment2[i - 1], 1, 6, 1.) + " || "
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		ExposureAdjustmentAggregator eaa = new ExposureAdjustmentAggregator (
			new MonoPathExposureAdjustment[] {
				new MonoPathExposureAdjustment (
					new AlbaneseAndersenNettingGroupPath[] {ngpaa2014_1},
					new AlbaneseAndersenFundingGroupPath[] {fgpaa2014_1}
				),
				new MonoPathExposureAdjustment (
					new AlbaneseAndersenNettingGroupPath[] {ngpaa2014_2},
					new AlbaneseAndersenFundingGroupPath[] {fgpaa2014_2}
				)
			}
		);

		JulianDate[] adtVertexNode = eaa.anchors();

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

		System.out.println();

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t|| BURGARD KJAER REPLICATION PORTFOLIO #1 ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||    L -> R:                             ||");

		System.out.println ("\t||           - Bank Bond Units            ||");

		System.out.println ("\t||           - Bank Subordinate Units     ||");

		System.out.println ("\t||----------------------------------------||");

		for (int i = 0; i <= iNumStep; ++i) {
			ReplicationPortfolioVertexBank rpvb = aBKV1[i].bankReplicationPortfolio();

			System.out.println ("\t|| [" + adtVertex[i] + "] =>   " +
				FormatUtil.FormatDouble (rpvb.seniorNumeraireUnits(), 1, 3, 1.) + "  |  " +
				FormatUtil.FormatDouble (rpvb.subordinateNumeraireUnits(), 1, 3, 1.) + "   || "
			);
		}

		System.out.println ("\t||----------------------------------------||");

		System.out.println();

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t|| BURGARD KJAER REPLICATION PORTFOLIO #2 ||");

		System.out.println ("\t||----------------------------------------||");

		System.out.println ("\t||    L -> R:                             ||");

		System.out.println ("\t||           - Bank Bond Units            ||");

		System.out.println ("\t||           - Bank Subordinate Units     ||");

		System.out.println ("\t||----------------------------------------||");

		for (int i = 0; i <= iNumStep; ++i) {
			ReplicationPortfolioVertexBank rpvb = aBKV2[i].bankReplicationPortfolio();

			System.out.println ("\t|| [" + adtVertex[i] + "] =>   " +
				FormatUtil.FormatDouble (rpvb.seniorNumeraireUnits(), 1, 3, 1.) + "  |  " +
				FormatUtil.FormatDouble (rpvb.subordinateNumeraireUnits(), 1, 3, 1.) + "   || "
			);
		}

		System.out.println ("\t||----------------------------------------||");

		System.out.println();
	}
}
