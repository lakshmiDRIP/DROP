
package org.drip.sample.burgard2011;

import org.drip.analytics.date.*;
import org.drip.analytics.support.VertexDateBuilder;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.*;
import org.drip.measure.realization.*;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.*;
import org.drip.xva.definition.*;
import org.drip.xva.derivative.*;
import org.drip.xva.evolver.*;
import org.drip.xva.pde.*;
import org.drip.xva.universe.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * CorrelatedNumeraireXVAAttribution constructs the XVA PnL Attribution arising out of the Joint Evolution of
 * 	Numeraires - the Continuous Asset, the Collateral, the Bank, and the Counter-Party Numeraires involved in
 *  the Dynamic XVA Replication Portfolio of the Burgard and Kjaer (2011) Methodology. The References are:
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

public class CorrelatedNumeraireXVAAttribution {

	private static final EvolutionTrajectoryVertex RunStep (
		final TrajectoryEvolutionScheme tes,
		final BurgardKjaerOperator bko,
		final EvolutionTrajectoryVertex etvStart,
		final MarketVertex mvStart,
		final MarketVertex mvFinish)
		throws Exception
	{
		PositionGreekVertex agvStart = etvStart.positionGreekVertex();

		ReplicationPortfolioVertex rpvStart = etvStart.replicationPortfolioVertex();

		double dblDerivativeXVAValueStart = agvStart.derivativeXVAValue();

		double dblTimeWidth = (mvFinish.anchorDate().julian() - mvStart.anchorDate().julian()) / 365.;

		double dblTimeStart = etvStart.time();

		double dblTime = dblTimeStart + dblTimeWidth;

		double dblCollateralSchemeNumeraire = mvStart.csaReplicator();

		BurgardKjaerEdgeAttribution bkea = bko.edgeRunAttribution (
			new MarketEdge (
				mvStart,
				mvFinish
			),
			etvStart,
			0.
		);

		double dblTheta = bkea.theta();

		double dblAssetNumeraireBump = bkea.positionValueBump();

		double dblThetaAssetNumeraireUp = bkea.thetaPositionValueUp();

		double dblThetaAssetNumeraireDown = bkea.thetaPositionValueDown();

		double dblDerivativeXVAValueDeltaFinish = agvStart.derivativeXVAValueDelta() -
			0.5 * (dblThetaAssetNumeraireUp - dblThetaAssetNumeraireDown) * dblTimeWidth / dblAssetNumeraireBump;

		double dblDerivativeXVAValueGammaFinish = agvStart.derivativeXVAValueGamma() -
			(dblThetaAssetNumeraireUp + dblThetaAssetNumeraireDown - 2. * dblTheta) * dblTimeWidth /
				(dblAssetNumeraireBump * dblAssetNumeraireBump);

		double dblDerivativeXVAValueFinish = dblDerivativeXVAValueStart + dblTheta * dblTimeWidth;

		CloseOut cog = new CloseOutBilateral (
			mvStart.dealer().seniorRecoveryRate(),
			mvStart.client().seniorRecoveryRate()
		);

		double dblGainOnBankDefaultFinish = -1. * (dblDerivativeXVAValueFinish - cog.bankDefault
			(dblDerivativeXVAValueFinish));

		double dblGainOnCounterPartyDefaultFinish = -1. * (dblDerivativeXVAValueFinish - cog.counterPartyDefault
			(dblDerivativeXVAValueFinish));

		org.drip.xva.derivative.CashAccountEdge cae = tes.rebalanceCash (
			etvStart,
			new MarketEdge (
				mvStart,
				mvFinish
			)
		).cashAccountEdge();

		double dblCashAccountAccumulationFinish = cae.accumulation();

		double dblAssetNumeraireFinish = mvFinish.positionManifestValue();

		double dblBankSeniorFundingNumeraireFinish = mvFinish.dealer().seniorFundingReplicator();

		double dblCounterPartyFundingNumeraireFinish = mvFinish.client().seniorFundingReplicator();

		ReplicationPortfolioVertex rpvFinish = ReplicationPortfolioVertex.Standard (
			-1. * dblDerivativeXVAValueDeltaFinish,
			dblGainOnBankDefaultFinish / dblBankSeniorFundingNumeraireFinish,
			dblGainOnCounterPartyDefaultFinish / dblCounterPartyFundingNumeraireFinish,
			rpvStart.cashAccount() + dblCashAccountAccumulationFinish
		);

		System.out.println ("\t||" +
			FormatUtil.FormatDouble (dblTime, 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (dblDerivativeXVAValueFinish, 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (dblAssetNumeraireFinish, 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (dblBankSeniorFundingNumeraireFinish, 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (dblCounterPartyFundingNumeraireFinish, 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (mvFinish.csaReplicator(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvFinish.positionHoldings(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvFinish.bankSeniorNumeraireHoldings(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvFinish.counterPartyNumeraireHoldings(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvFinish.cashAccount(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (bkea.derivativeXVAFundingGrowth(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (bkea.derivativeXVABankDefaultGrowth(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (bkea.derivativeXVACounterPartyDefaultGrowth(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (bkea.derivativeXVAEarlyTerminationGrowth(), 1, 6, 1.) + " ||"
		);

		return new EvolutionTrajectoryVertex (
			dblTime,
			rpvFinish,
			new PositionGreekVertex (
				dblDerivativeXVAValueFinish,
				dblDerivativeXVAValueDeltaFinish,
				dblDerivativeXVAValueGammaFinish,
				agvStart.derivativeFairValue() * Math.exp (
					-1. * dblTimeWidth * tes.tradeablesContainer().csa().evolver().evaluator().drift().value (
						new JumpDiffusionVertex (
							dblTime,
							dblCollateralSchemeNumeraire,
							0.,
							false
						)
					)
				)
			),
			dblGainOnBankDefaultFinish,
			dblGainOnCounterPartyDefaultFinish,
			0.,
			0.
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String bank = "WFC";
		int iNumVertex = 24;
		String currency = "USD";
		String counterParty = "BAC";
		int iSimulationDuration = 365;

		double[][] aadblCorrelationMatrix = new double[][] {
			{1.00, 0.00, 0.20, 0.15, 0.05, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #0 ASSET NUMERAIRE
			{0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #1 OVERNIGHT POLICY INDEX NUMERAIRE
			{0.20, 0.00, 1.00, 0.13, 0.25, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #2 COLLATERAL SCHEME NUMERAIRE
			{0.15, 0.00, 0.13, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #3 BANK HAZARD RATE
			{0.05, 0.00, 0.25, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #4 BANK SENIOR FUNDING NUMERAIRE
			{0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #5 BANK SENIOR RECOVERY RATE
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00}, // #6 BANK SUBORDINATE FUNDING NUMERAIRE
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00}, // #7 BANK SUBORDINATE RECOVERY RATE
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00}, // #8 COUNTER PARTY HAZARD RATE
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00}, // #9 COUNTER PARTY FUNDING NUMERAIRE
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00}  // #10 COUNTER PARTY RECOVERY RATE
		};

		double dblAssetNumeraireDrift = 0.06;
		double dblAssetNumeraireVolatility = 0.10;
		double dblAssetNumeraireRepo = 0.03;
		double dblAssetNumeraireDividend = 0.02;
		double dblAssetNumeraireInitial = 1.;

		double dblOvernightIndexNumeraireDrift = 0.0025;
		double dblOvernightIndexNumeraireVolatility = 0.001;
		double dblOvernightIndexNumeraireRepo = 0.0;

		double dblCollateralSchemeNumeraireDrift = 0.01;
		double dblCollateralSchemeNumeraireVolatility = 0.002;
		double dblCollateralSchemeNumeraireRepo = 0.005;

		double dblBankSeniorFundingNumeraireDrift = 0.03;
		double dblBankSeniorFundingNumeraireVolatility = 0.002;
		double dblBankSeniorFundingNumeraireRepo = 0.028;

		double dblBankSubordinateFundingNumeraireDrift = 0.045;
		double dblBankSubordinateFundingNumeraireVolatility = 0.002;
		double dblBankSubordinateFundingNumeraireRepo = 0.028;

		double dblCounterPartyFundingNumeraireDrift = 0.03;
		double dblCounterPartyFundingNumeraireVolatility = 0.003;
		double dblCounterPartyFundingNumeraireRepo = 0.028;

		double dblBankHazardRateDrift = 0.00;
		double dblBankHazardRateVolatility = 0.005;
		double dblBankHazardRateInitial = 0.03;

		double dblBankSeniorRecoveryRateDrift = 0.0;
		double dblBankSeniorRecoveryRateVolatility = 0.0;
		double dblBankSeniorRecoveryRateInitial = 0.45;

		double dblBankSubordinateRecoveryRateDrift = 0.0;
		double dblBankSubordinateRecoveryRateVolatility = 0.0;
		double dblBankSubordinateRecoveryRateInitial = 0.25;

		double dblCounterPartyHazardRateDrift = 0.00;
		double dblCounterPartyHazardRateVolatility = 0.005;
		double dblCounterPartyHazardRateInitial = 0.05;

		double dblCounterPartyRecoveryRateDrift = 0.0;
		double dblCounterPartyRecoveryRateVolatility = 0.0;
		double dblCounterPartyRecoveryRateInitial = 0.30;

		double dblTerminalXVADerivativeValue = 1.;

		double dblSensitivityShiftFactor = 0.001;

		PrimarySecurity tAsset = new PrimarySecurity (
			"AAPL",
			EntityEquityLabel.Standard (
				"AAPL",
				currency
			),
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblAssetNumeraireDrift - dblAssetNumeraireDividend,
					dblAssetNumeraireVolatility
				)
			),
			dblAssetNumeraireRepo
		);

		PrimarySecurity tOvernightIndex = new PrimarySecurity (
			currency + "_" + "_OVERNIGHT_ZERO",
			OvernightLabel.Create (currency),
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblOvernightIndexNumeraireDrift,
					dblOvernightIndexNumeraireVolatility
				)
			),
			dblOvernightIndexNumeraireRepo
		);

		PrimarySecurity tCollateralScheme = new PrimarySecurity (
			currency + "_" + "CSA_ZERO",
			CSALabel.ISDA (currency),
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCollateralSchemeNumeraireDrift,
					dblCollateralSchemeNumeraireVolatility
				)
			),
			dblCollateralSchemeNumeraireRepo
		);

		PrimarySecurity tBankSeniorFunding = new PrimarySecurity (
			bank + "_" + currency + "_" + "_SENIOR_ZERO",
			EntityFundingLabel.Senior (
				bank,
				currency
			),
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblBankSeniorFundingNumeraireDrift,
					dblBankSeniorFundingNumeraireVolatility
				),
				HazardJumpEvaluator.Standard (
					dblBankHazardRateInitial,
					dblBankSeniorRecoveryRateInitial
				)
			),
			dblBankSeniorFundingNumeraireRepo
		);

		PrimarySecurity tBankSubordinateFunding = new PrimarySecurity (
			bank + "_" + currency + "_" + "_SUBORDINATE_ZERO",
			EntityFundingLabel.Subordinate (
				bank,
				currency
			),
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblBankSubordinateFundingNumeraireDrift,
					dblBankSubordinateFundingNumeraireVolatility
				),
				HazardJumpEvaluator.Standard (
					dblBankHazardRateInitial,
					dblBankSubordinateRecoveryRateInitial
				)
			),
			dblBankSubordinateFundingNumeraireRepo
		);

		PrimarySecurity tCounterPartyFunding = new PrimarySecurity (
			counterParty + "_" + currency + "_" + "_SENIOR_ZERO",
			EntityFundingLabel.Senior (
				counterParty,
				currency
			),
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCounterPartyFundingNumeraireDrift,
					dblCounterPartyFundingNumeraireVolatility
				),
				HazardJumpEvaluator.Standard (
					dblCounterPartyHazardRateInitial,
					dblCounterPartyRecoveryRateInitial
				)
			),
			dblCounterPartyFundingNumeraireRepo
		);

		JulianDate dtSpot = DateUtil.Today();

		int iSpotDate = dtSpot.julian();

		int aiVertexDate[] = VertexDateBuilder.EqualWidth (
			iSpotDate,
			iSpotDate + iSimulationDuration,
			iNumVertex
		);

		PrimarySecurityDynamicsContainer tc = new PrimarySecurityDynamicsContainer (
			tAsset,
			tOvernightIndex,
			tCollateralScheme,
			tBankSeniorFunding,
			tBankSubordinateFunding,
			tCounterPartyFunding
		);

		MarketVertexGenerator mvg = new MarketVertexGenerator (
			iSpotDate,
			aiVertexDate,
			tc,
			new EntityDynamicsContainer (
				new TerminalLatentState (
					EntityHazardLabel.Standard (
						bank,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dblBankHazardRateDrift,
							dblBankHazardRateVolatility
						)
					)
				),
				new TerminalLatentState (
					EntityRecoveryLabel.Senior (
						bank,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dblBankSeniorRecoveryRateDrift,
							dblBankSeniorRecoveryRateVolatility
						)
					)
				),
				new TerminalLatentState (
					EntityRecoveryLabel.Subordinate (
						bank,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dblBankSubordinateRecoveryRateDrift,
							dblBankSubordinateRecoveryRateVolatility
						)
					)
				),
				new TerminalLatentState (
					EntityHazardLabel.Standard (
						counterParty,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dblCounterPartyHazardRateDrift,
							dblCounterPartyHazardRateVolatility
						)
					)
				),
				new TerminalLatentState (
					EntityRecoveryLabel.Senior (
						counterParty,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dblCounterPartyRecoveryRateDrift,
							dblCounterPartyRecoveryRateVolatility
						)
					)
				)
			)
		);

		MarketVertex mvInitial = MarketVertex.SingleManifestMeasure (
			dtSpot,
			dblAssetNumeraireInitial,
			dblOvernightIndexNumeraireDrift,
			1.,
			dblCollateralSchemeNumeraireDrift,
			1.,
			new MarketVertexEntity (
				1.,
				dblBankHazardRateInitial,
				dblBankSeniorRecoveryRateInitial,
				dblBankSeniorFundingNumeraireDrift,
				1.,
				dblBankSubordinateRecoveryRateInitial,
				dblBankSubordinateFundingNumeraireDrift,
				1.
			),
			new MarketVertexEntity (
				1.,
				dblCounterPartyHazardRateInitial,
				dblCounterPartyRecoveryRateInitial,
				dblCounterPartyFundingNumeraireDrift,
				1.,
				Double.NaN,
				Double.NaN,
				Double.NaN
			)
		);

		System.out.println ("mvInitial = " + mvInitial);

		MarketVertex[] aMV = mvg.marketVertex (
			mvInitial,
			Matrix.Transpose (
				SequenceGenerator.GaussianJoint (
					iNumVertex,
					aadblCorrelationMatrix
				)
			)
		);

		double dblDerivativeValue = dblTerminalXVADerivativeValue;
		double dblDerivativeXVAValue = dblTerminalXVADerivativeValue;

		PDEEvolutionControl pdeec = new PDEEvolutionControl (
			PDEEvolutionControl.CLOSEOUT_GREGORY_LI_TANG,
			dblSensitivityShiftFactor
		);

		CloseOutBilateral cob = new CloseOutBilateral (
			dblBankSeniorRecoveryRateInitial,
			dblCounterPartyRecoveryRateInitial
		);

		TrajectoryEvolutionScheme tes = new TrajectoryEvolutionScheme (
			tc,
			pdeec
		);

		BurgardKjaerOperator bko = new BurgardKjaerOperator (
			tc,
			pdeec
		);

		PositionGreekVertex agvInitial = new PositionGreekVertex (
			dblDerivativeXVAValue,
			-1.,
			0.,
			dblDerivativeValue
		);

		double dblGainOnBankDefaultInitial = -1. * (dblDerivativeXVAValue - cob.bankDefault (dblDerivativeXVAValue));

		double dblGainOnCounterPartyDefaultInitial = -1. * (dblDerivativeXVAValue -
			cob.counterPartyDefault (dblDerivativeXVAValue));

		ReplicationPortfolioVertex rpvInitial = ReplicationPortfolioVertex.Standard (
			1.,
			dblGainOnBankDefaultInitial,
			dblGainOnCounterPartyDefaultInitial,
			0.
		);

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                            BILATERAL XVA EVOLVER - BURGARD & KJAER (2011) REPLICATION PORTFOLIO EVOLUTION                                             ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                                                                                                            ||");

		System.out.println ("\t||            - Time                                                                                                                                                     ||");

		System.out.println ("\t||            - Derivative XVA Value                                                                                                                                     ||");

		System.out.println ("\t||            - Asset Price Realization                                                                                                                                  ||");

		System.out.println ("\t||            - Realization of the Zero Coupon Bank Bond Price                                                                                                           ||");

		System.out.println ("\t||            - Realization of the Zero Coupon Counter Party Bond Price                                                                                                  ||");

		System.out.println ("\t||            - Realization of the Zero Coupon Collateral Bond Price                                                                                                     ||");

		System.out.println ("\t||            - Derivative XVA Asset Replication Units                                                                                                                   ||");

		System.out.println ("\t||            - Derivative XVA Value Bank Bond Replication Units                                                                                                         ||");

		System.out.println ("\t||            - Derivative XVA Value Counter Party Bond Replication Units                                                                                                ||");

		System.out.println ("\t||            - Derivative XVA Value Cash Account Replication Units                                                                                                      ||");

		System.out.println ("\t||            - Derivative XVA Funding Growth                                                                                                                            ||");

		System.out.println ("\t||            - Derivative XVA Bank Default Growth                                                                                                                       ||");

		System.out.println ("\t||            - Derivative XVA Counter Party Default Growth                                                                                                              ||");

		System.out.println ("\t||            - Derivative XVA Early Termination Growth                                                                                                                  ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||" +
			FormatUtil.FormatDouble (1., 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (agvInitial.derivativeXVAValue(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (aMV[iNumVertex].positionManifestValue(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (aMV[iNumVertex].dealer().seniorFundingReplicator(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (aMV[iNumVertex].client().seniorFundingReplicator(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (aMV[iNumVertex].csaReplicator(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvInitial.positionHoldings(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvInitial.bankSeniorNumeraireHoldings(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvInitial.counterPartyNumeraireHoldings(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (rpvInitial.cashAccount(), 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 6, 1.) + " ||"
		);

		EvolutionTrajectoryVertex etv = new EvolutionTrajectoryVertex (
			1.,
			ReplicationPortfolioVertex.Standard (
				1.,
				0.,
				0.,
				0.
			),
			agvInitial,
			dblGainOnBankDefaultInitial,
			dblGainOnCounterPartyDefaultInitial,
			0.,
			0.
		);

		for (int i = iNumVertex - 1; i >= 0; --i)
			etv = RunStep (
				tes,
				bko,
				etv,
				aMV[i + 1],
				aMV[i]
			);

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
