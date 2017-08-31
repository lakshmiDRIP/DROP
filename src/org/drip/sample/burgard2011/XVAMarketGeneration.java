
package org.drip.sample.burgard2011;

import org.drip.analytics.date.*;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
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
 * XVAMarketGeneration generates the Asset, the Bank, and the Counter Party Credit/Funding Metrics used in an
 * 	XVA Run. The References are:
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

public class XVAMarketGeneration {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumVertex = 24;
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

		Tradeable tAsset = new Tradeable (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblAssetNumeraireDrift - dblAssetNumeraireDividend,
					dblAssetNumeraireVolatility
				)
			),
			dblAssetNumeraireRepo
		);

		Tradeable tOvernightIndex = new Tradeable (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblOvernightIndexNumeraireDrift,
					dblOvernightIndexNumeraireVolatility
				)
			),
			dblOvernightIndexNumeraireRepo
		);

		Tradeable tCollateralScheme = new Tradeable (
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblCollateralSchemeNumeraireDrift,
					dblCollateralSchemeNumeraireVolatility
				)
			),
			dblCollateralSchemeNumeraireRepo
		);

		Tradeable tBankSeniorFunding = new Tradeable (
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

		Tradeable tBankSubordinateFunding = new Tradeable (
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

		Tradeable tCounterPartyFunding = new Tradeable (
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

		DiffusionEvolver deBankHazardRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblBankHazardRateDrift,
				dblBankHazardRateVolatility
			)
		);

		DiffusionEvolver deBankSeniorRecoveryRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblBankSeniorRecoveryRateDrift,
				dblBankSeniorRecoveryRateVolatility
			)
		);

		DiffusionEvolver deBankSubordinateRecoveryRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblBankSubordinateRecoveryRateDrift,
				dblBankSubordinateRecoveryRateVolatility
			)
		);

		DiffusionEvolver deCounterPartyHazardRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblCounterPartyHazardRateDrift,
				dblCounterPartyHazardRateVolatility
			)
		);

		DiffusionEvolver deCounterPartyRecoveryRate = new DiffusionEvolver (
			DiffusionEvaluatorLogarithmic.Standard (
				dblCounterPartyRecoveryRateDrift,
				dblCounterPartyRecoveryRateVolatility
			)
		);

		JulianDate dtSpot = DateUtil.Today();

		int iSpotDate = dtSpot.julian();

		int aiVertexDate[] = VertexDateBuilder.EqualWidth (
			iSpotDate,
			iSpotDate + iSimulationDuration,
			iNumVertex
		);

		MarketVertexGenerator mvg = new MarketVertexGenerator (
			iSpotDate,
			aiVertexDate,
			aadblCorrelationMatrix,
			new TradeablesContainer (
				tAsset,
				tOvernightIndex,
				tCollateralScheme,
				tBankSeniorFunding,
				tBankSubordinateFunding,
				tCounterPartyFunding
			),
			deBankHazardRate,
			deBankSeniorRecoveryRate,
			deBankSubordinateRecoveryRate,
			deCounterPartyHazardRate,
			deCounterPartyRecoveryRate
		);

		MarketVertex mvInitial = new MarketVertex (
			dtSpot,
			dblAssetNumeraireInitial,
			dblOvernightIndexNumeraireDrift,
			new NumeraireMarketVertex (
				1.,
				1.
			),
			dblCollateralSchemeNumeraireDrift,
			new NumeraireMarketVertex (
				1.,
				1.
			),
			new EntityMarketVertex (
				1.,
				dblBankHazardRateInitial,
				dblBankSeniorRecoveryRateInitial,
				dblBankSeniorFundingNumeraireDrift,
				new NumeraireMarketVertex (
					1.,
					1.
				),
				dblBankSubordinateRecoveryRateInitial,
				dblBankSubordinateFundingNumeraireDrift,
				new NumeraireMarketVertex (
					1.,
					1.
				)
			),
			new EntityMarketVertex (
				1.,
				dblCounterPartyHazardRateInitial,
				dblCounterPartyRecoveryRateInitial,
				dblCounterPartyFundingNumeraireDrift,
				new NumeraireMarketVertex (
					1.,
					1.
				),
				Double.NaN,
				Double.NaN,
				null
			)
		);

		MarketVertex[] aMV = mvg.marketVertex (mvInitial);

		System.out.println();

		System.out.println ("\t||--------------------------------------------------------------------||");

		System.out.println ("\t||          ASSET, OVERNIGHT INDEX, AND COLLATERAL NUMERAIRE          ||");

		System.out.println ("\t||--------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                         ||");

		System.out.println ("\t||            - Date                                                  ||");

		System.out.println ("\t||            - Overnight Index Rate                                  ||");

		System.out.println ("\t||            - Overnight Index Numeraire                             ||");

		System.out.println ("\t||            - Collateral Scheme Rate                                ||");

		System.out.println ("\t||            - Collateral Scheme Numeraire                           ||");

		System.out.println ("\t||--------------------------------------------------------------------||");

		for (int i = 0; i < aMV.length; ++i)
			System.out.println (
				"\t|| " + aMV[i].anchor() + " => " +
				FormatUtil.FormatDouble (aMV[i].assetNumeraire(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].overnightIndexRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aMV[i].overnightIndexNumeraire().forward(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].collateralSchemeRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aMV[i].collateralSchemeNumeraire().forward(), 1, 6, 1.) + " ||"
			);

		System.out.println ("\t||--------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println ("\t||             BANK REALIZATION VERTEX => SURVIVAL, SENIOR/SUBORDINATE NODE METRICS             ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                                   ||");

		System.out.println ("\t||            - Date                                                                            ||");

		System.out.println ("\t||            - Hazard Rate                                                                     ||");

		System.out.println ("\t||            - Survival Probability                                                            ||");

		System.out.println ("\t||            - Senior Recovery Rate                                                            ||");

		System.out.println ("\t||            - Senior Funding Spread                                                           ||");

		System.out.println ("\t||            - Senior Funding Numeraire                                                        ||");

		System.out.println ("\t||            - Subordinate Recovery Rate                                                       ||");

		System.out.println ("\t||            - Subordinate Funding Spread                                                      ||");

		System.out.println ("\t||            - Subordinate Funding Numeraire                                                   ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		for (int i = 0; i < aMV.length; ++i) {
			EntityMarketVertex emvBank = aMV[i].bank();

			System.out.println (
				"\t|| " + aMV[i].anchor() + " => " +
				FormatUtil.FormatDouble (emvBank.hazardRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvBank.survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvBank.seniorRecoveryRate(), 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.seniorFundingSpread(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.seniorFundingNumeraire().forward(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvBank.subordinateRecoveryRate(), 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.subordinateFundingSpread(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.subordinateFundingNumeraire().forward(), 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println ("\t||         COUNTER PARTY REALIZATION VERTEX => SURVIVAL, SENIOR/SUBORDINATE NODE METRICS        ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                                   ||");

		System.out.println ("\t||            - Date                                                                            ||");

		System.out.println ("\t||            - Hazard Rate                                                                     ||");

		System.out.println ("\t||            - Survival Probability                                                            ||");

		System.out.println ("\t||            - Senior Recovery Rate                                                            ||");

		System.out.println ("\t||            - Senior Funding Spread                                                           ||");

		System.out.println ("\t||            - Senior Funding Numeraire                                                        ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		for (int i = 0; i < aMV.length; ++i) {
			EntityMarketVertex emvCounterParty = aMV[i].counterParty();

			System.out.println (
				"\t|| " + aMV[i].anchor() + " => " +
				FormatUtil.FormatDouble (emvCounterParty.hazardRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvCounterParty.survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvCounterParty.seniorRecoveryRate(), 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (emvCounterParty.seniorFundingSpread(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (emvCounterParty.seniorFundingNumeraire().forward(), 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
