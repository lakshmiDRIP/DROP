
package org.drip.sample.numeraire;

import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.*;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;

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
 * R1JointJumpDiffusion demonstrates the Joint Evolution of R^1 Jump Diffusion Variates - the Continuous
 *  Asset, the Collateral, the Bank, and the Counter-Party Numeraires involved in the Dynamic XVA Replication
 *  Portfolio of the Burgard and Kjaer (2011) Methodology. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
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

public class R1JointJumpDiffusion {

	private static final double[][] NumeraireSequence (
		final int iCount,
		final double[][] aadblCorrelation,
		final String strHeader)
		throws Exception
	{
		double[][] aadblGaussianJoint = SequenceGenerator.GaussianJoint (
			iCount,
			aadblCorrelation
		);

		System.out.println();

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println (strHeader);

		System.out.println ("\t||----------------------------------------------------||");

		for (int i = 0; i < iCount; ++i) {
			String strDump = "\t||" + FormatUtil.FormatDouble (i, 2, 0, 1.) + " |";

			for (int j = 0; j < aadblCorrelation.length; ++j)
				strDump = strDump + " " + FormatUtil.FormatDouble (aadblGaussianJoint[i][j], 1, 6, 1.) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println();

		return Matrix.Transpose (aadblGaussianJoint);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblTimeWidth = 1. / 24.;
		double dblTime = 0.;
		double[][] aadblCorrelation = new double[][] {
			{1.00, 0.20, 0.15, 0.05}, // #0 ASSET
			{0.20, 1.00, 0.13, 0.25}, // #1 COLLATERAL
			{0.15, 0.13, 1.00, 0.00}, // #2 BANK
			{0.05, 0.25, 0.00, 1.00}  // #3 COUNTER PARTY
		};
		double dblAssetDrift = 0.06;
		double dblAssetVolatility = 0.15;
		double dblInitialAssetNumeraire = 1.;

		double dblZeroCouponBankBondDrift = 0.03;
		double dblZeroCouponBankBondVolatility = 0.10;
		double dblBankHazardRate = 0.03;
		double dblBankRecoveryRate = 0.45;
		double dblInitialBankNumeraire = 1.;

		double dblZeroCouponCollateralBondDrift = 0.01;
		double dblZeroCouponCollateralBondVolatility = 0.05;
		double dblInitialCollateralNumeraire = 1.;

		double dblZeroCouponCounterPartyBondDrift = 0.03;
		double dblZeroCouponCounterPartyBondVolatility = 0.10;
		double dblCounterPartyHazardRate = 0.05;
		double dblCounterPartyRecoveryRate = 0.30;
		double dblInitialCounterPartyNumeraire = 1.;

		int iNumTimeStep = (int) (1. / dblTimeWidth);

		DiffusionEvolver meAsset = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblAssetDrift,
				dblAssetVolatility
			)
		);

		DiffusionEvolver meZeroCouponCollateralBond = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblZeroCouponCollateralBondDrift,
				dblZeroCouponCollateralBondVolatility
			)
		);

		JumpDiffusionEvolver meZeroCouponBankBond = new JumpDiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblZeroCouponBankBondDrift,
				dblZeroCouponBankBondVolatility
			),
			HazardJumpEvaluator.Standard (
				dblBankHazardRate,
				dblBankRecoveryRate
			)
		);

		JumpDiffusionEvolver meZeroCouponCounterPartyBond = new JumpDiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblZeroCouponCounterPartyBondDrift,
				dblZeroCouponCounterPartyBondVolatility
			),
			HazardJumpEvaluator.Standard (
				dblCounterPartyHazardRate,
				dblCounterPartyRecoveryRate
			)
		);

		double[][] aadblNumeraireTimeSeries = NumeraireSequence (
			iNumTimeStep,
			aadblCorrelation,
			"\t|| ASSET, COLLATERAL, BANK, COUNTER PARTY REALIZATION ||"
		);

		double[] adblBankDefaultIndicator = SequenceGenerator.Uniform (iNumTimeStep);

		double[] adblCounterPartyDefaultIndicator = SequenceGenerator.Uniform (iNumTimeStep);

		double[] adblTimeWidth = new double[iNumTimeStep + 1];

		for (int i = 0; i < iNumTimeStep; ++i)
			adblTimeWidth[i] = dblTimeWidth;

		JumpDiffusionEdge[] aR1AssetLR = meAsset.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialAssetNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[0]
			),
			dblTimeWidth
		);

		JumpDiffusionEdge[] aR1CollateralLR = meZeroCouponCollateralBond.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialCollateralNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.Diffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[1]
			),
			dblTimeWidth
		);

		JumpDiffusionEdge[] aR1BankLR = meZeroCouponBankBond.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialBankNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.JumpDiffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[2],
				adblBankDefaultIndicator
			),
			dblTimeWidth
		);

		JumpDiffusionEdge[] aR1CounterPartyLR = meZeroCouponCounterPartyBond.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblInitialCounterPartyNumeraire,
				0.,
				false
			),
			JumpDiffusionEdgeUnit.JumpDiffusion (
				adblTimeWidth,
				aadblNumeraireTimeSeries[3],
				adblCounterPartyDefaultIndicator
			),
			dblTimeWidth
		);

		System.out.println();

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println("\t||                  BURGARD & KJAER (2011) CORRELATED JOINT ASSET/COLLATERAL/BANK/COUNTER-PARTY NUMERAIRE EVOLUTION                 ||");

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println("\t||    L -> R:                                                                                                                       ||");

		System.out.println("\t||          - Time                                                                                                                  ||");

		System.out.println("\t||          - Asset Numeraire Finish Value                                                                                          ||");

		System.out.println("\t||          - Asset Numeraire Start Value                                                                                           ||");

		System.out.println("\t||          - Asset Numeraire Continuous Wander Realization                                                                         ||");

		System.out.println("\t||          - Collateral Numeraire Finish Value                                                                                     ||");

		System.out.println("\t||          - Collateral Numeraire Start Value                                                                                      ||");

		System.out.println("\t||          - Collateral Numeraire Continuous Wander Realization                                                                    ||");

		System.out.println("\t||          - Bank Numeraire Finish Value                                                                                           ||");

		System.out.println("\t||          - Bank Numeraire Start Value                                                                                            ||");

		System.out.println("\t||          - Bank Numeraire Continuous Wander Realization                                                                          ||");

		System.out.println("\t||          - Counter-Party Numeraire Finish Value                                                                                  ||");

		System.out.println("\t||          - Counter-Party Numeraire Start Value                                                                                   ||");

		System.out.println("\t||          - Counter-Party Numeraire Continuous Wander Realization                                                                 ||");

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < iNumTimeStep; ++i) {
			dblTime = dblTime + dblTimeWidth;

			System.out.println (
				"\t|| " +
				FormatUtil.FormatDouble (dblTime, 1, 4, 1.) + " => " +
				FormatUtil.FormatDouble (aR1AssetLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1AssetLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1AssetLR[i].diffusionWander(), 1, 4, 1.) + " ||" +
				FormatUtil.FormatDouble (aR1CollateralLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CollateralLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CollateralLR[i].diffusionWander(), 1, 4, 1.) + " ||" +
				FormatUtil.FormatDouble (aR1BankLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1BankLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1BankLR[i].diffusionWander(), 1, 4, 1.) + " ||" +
				FormatUtil.FormatDouble (aR1CounterPartyLR[i].start(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CounterPartyLR[i].finish(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (aR1CounterPartyLR[i].diffusionWander(), 1, 4, 1.) + " ||"
			);
		}

		System.out.println("\t||----------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
