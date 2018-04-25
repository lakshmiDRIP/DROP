
package org.drip.sample.mporstream;

import java.util.Map;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.exposure.csatimeline.AndersenPykhtinSokolLag;
import org.drip.exposure.csatimeline.LastFlowDates;
import org.drip.exposure.evolver.EntityDynamicsContainer;
import org.drip.exposure.evolver.PrimarySecurity;
import org.drip.exposure.evolver.PrimarySecurityDynamicsContainer;
import org.drip.exposure.evolver.TerminalLatentState;
import org.drip.exposure.generator.FloatStreamMPoR;
import org.drip.exposure.mpor.VariationMarginTradeVertexExposure;
import org.drip.exposure.mpor.VariationMarginTradeTrajectoryEstimator;
import org.drip.exposure.universe.MarketPath;
import org.drip.exposure.universe.MarketVertex;
import org.drip.exposure.universe.MarketVertexGenerator;
import org.drip.market.otc.FixedFloatSwapConvention;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.CorrelatedPathVertexDimension;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.dynamics.HazardJumpEvaluator;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.process.JumpDiffusionEvolver;
import org.drip.product.rates.FixFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.EntityEquityLabel;
import org.drip.state.identifier.EntityFundingLabel;
import org.drip.state.identifier.EntityHazardLabel;
import org.drip.state.identifier.EntityRecoveryLabel;
import org.drip.state.identifier.OvernightLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * ShortFloatClassicalMinusTimeline displays the MPoR-related Exposure Metrics Suite for the given Short
 *  Float Coupon Stream on a Daily Grid using the "Classical-" CSA Timeline of Andersen, Pykhtin, and Sokol
 *  (2017). The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ShortFloatClassicalMinusTimeline
{

	private static final FixFloatComponent OTCIRS (
		final JulianDate spotDate,
		final String currency,
		final String maturityTenor,
		final double coupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			currency,
			"ALL",
			maturityTenor,
			"MAIN"
		);

		return ffConv.createFixFloatComponent (
			spotDate,
			maturityTenor,
			coupon,
			0.,
			1.
		);
	}

	private static final PrimarySecurityDynamicsContainer GenerateTradeablesContainer (
		final String eventTenor,
		final int eventCount,
		final String dealer,
		final String client)
		throws Exception
	{
		String currency = "USD";

		double liborDrift = 0.0;
		double liborVolatility = 0.001;

		double overnightIndexNumeraireDrift = 0.0025;
		double overnightIndexNumeraireVolatility = 0.0005;
		double overnightIndexNumeraireRepo = 0.0;

		double collateralSchemeNumeraireDrift = 0.01;
		double collateralSchemeNumeraireVolatility = 0.002;
		double collateralSchemeNumeraireRepo = 0.005;

		double dealerSeniorFundingNumeraireDrift = 0.03;
		double dealerSeniorFundingNumeraireVolatility = 0.002;
		double dealerSeniorFundingNumeraireRepo = 0.028;

		double dealerSubordinateFundingNumeraireDrift = 0.045;
		double dealerSubordinateFundingNumeraireVolatility = 0.002;
		double dealerSubordinateFundingNumeraireRepo = 0.028;

		double clientFundingNumeraireDrift = 0.03;
		double clientFundingNumeraireVolatility = 0.003;
		double clientFundingNumeraireRepo = 0.028;

		double dealerHazardRateInitial = 0.03;

		double dealerSeniorRecoveryRateInitial = 0.45;

		double dealerSubordinateRecoveryRateInitial = 0.25;

		double clientHazardRateInitial = 0.05;

		double clientRecoveryRateInitial = 0.30;

		PrimarySecurity tAsset = new PrimarySecurity (
			"AAPL",
			EntityEquityLabel.Standard (
				"AAPL",
				currency
			),
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					liborDrift,
					liborVolatility
				)
			),
			0.
		);

		PrimarySecurity tOvernightIndex = new PrimarySecurity (
			currency + "_OVERNIGHT",
			OvernightLabel.Create (currency),
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					overnightIndexNumeraireDrift,
					overnightIndexNumeraireVolatility
				)
			),
			overnightIndexNumeraireRepo
		);

		PrimarySecurity tCollateralScheme = new PrimarySecurity (
			currency + "_CSA",
			CSALabel.ISDA (currency),
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					collateralSchemeNumeraireDrift,
					collateralSchemeNumeraireVolatility
				)
			),
			collateralSchemeNumeraireRepo
		);

		PrimarySecurity tBankSeniorFunding = new PrimarySecurity (
			dealer + "_" + currency + "_SENIOR_ZERO",
			EntityFundingLabel.Senior (
				dealer,
				currency
			),
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerSeniorFundingNumeraireDrift,
					dealerSeniorFundingNumeraireVolatility
				),
				HazardJumpEvaluator.Standard (
					dealerHazardRateInitial,
					dealerSeniorRecoveryRateInitial
				)
			),
			dealerSeniorFundingNumeraireRepo
		);

		PrimarySecurity tBankSubordinateFunding = new PrimarySecurity (
			dealer + "_" + currency + "_SUBORDINATE_ZERO",
			EntityFundingLabel.Subordinate (
				dealer,
				currency
			),
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerSubordinateFundingNumeraireDrift,
					dealerSubordinateFundingNumeraireVolatility
				),
				HazardJumpEvaluator.Standard (
					dealerHazardRateInitial,
					dealerSubordinateRecoveryRateInitial
				)
			),
			dealerSubordinateFundingNumeraireRepo
		);

		PrimarySecurity tCounterPartyFunding = new PrimarySecurity (
			client + "_" + currency + "_SENIOR_ZERO",
			EntityFundingLabel.Senior (
				client,
				currency
			),
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					clientFundingNumeraireDrift,
					clientFundingNumeraireVolatility
				),
				HazardJumpEvaluator.Standard (
					clientHazardRateInitial,
					clientRecoveryRateInitial
				)
			),
			clientFundingNumeraireRepo
		);

		return new PrimarySecurityDynamicsContainer (
			tAsset,
			tOvernightIndex,
			tCollateralScheme,
			tBankSeniorFunding,
			tBankSubordinateFunding,
			tCounterPartyFunding
		);
	}

	private static final MarketVertexGenerator ConstructMarketVertexGenerator (
		final JulianDate spotDate,
		final String periodTenor,
		final int periodCount,
		final String currency,
		final String dealer,
		final String client)
		throws Exception
	{
		double dealerHazardRateDrift = 0.002;
		double dealerHazardRateVolatility = 0.20;
		double dealerRecoveryRateDrift = 0.002;
		double dealerRecoveryRateVolatility = 0.02;
		double clientHazardRateDrift = 0.002;
		double clientHazardRateVolatility = 0.30;
		double clientRecoveryRateDrift = 0.002;
		double clientRecoveryRateVolatility = 0.02;

		return MarketVertexGenerator.PeriodHorizon (
			spotDate.julian(),
			periodTenor,
			periodCount,
			GenerateTradeablesContainer (
				periodTenor,
				periodCount,
				dealer,
				client
			),
			new EntityDynamicsContainer (
				new TerminalLatentState (
					EntityHazardLabel.Standard (
						dealer,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dealerHazardRateDrift,
							dealerHazardRateVolatility
						)
					)
				),
				new TerminalLatentState (
					EntityRecoveryLabel.Senior (
						dealer,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							dealerRecoveryRateDrift,
							dealerRecoveryRateVolatility
						)
					)
				),
				null,
				new TerminalLatentState (
					EntityHazardLabel.Standard (
						client,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							clientHazardRateDrift,
							clientHazardRateVolatility
						)
					)
				),
				new TerminalLatentState (
					EntityRecoveryLabel.Senior (
						client,
						currency
					),
					new DiffusionEvolver (
						DiffusionEvaluatorLogarithmic.Standard (
							clientRecoveryRateDrift,
							clientRecoveryRateVolatility
						)
					)
				)
			)
		);
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate spotDate = DateUtil.CreateFromYMD (
			2018,
			DateUtil.APRIL,
			19
		);

		int pathCount = 1000;
		String exposurePeriodTenor = "1D";
		int exposurePeriodCount = 390;
		int vertexGenerationPeriodCount = exposurePeriodCount + 10;
		String currency = "USD";
		String dealer = "NOM";
		String client = "SSGA";
		double[][] correlationMatrix = new double[][] {
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
		String fixFloatMaturityTenor = "1Y";
		double fixFloatCoupon = 0.03;
		double fixFloatNotional = 1.e+06;

		MarketVertexGenerator marketVertexGenerator = ConstructMarketVertexGenerator (
			spotDate.subtractTenor ("1W"),
			exposurePeriodTenor,
			vertexGenerationPeriodCount,
			currency,
			dealer,
			client
		);

		MarketVertex initialMarketVertex = MarketVertex.StartUp (
			spotDate,
			0.020, 				// Initial LIBOR
			1.000, 				// dblOvernightNumeraireInitial
			1.000, 				// dblCSANumeraire
			0.015, 				// dblBankHazardRate
			0.400, 				// dblBankRecoveryRate
			0.015 / (1 - 0.40), // dblBankFundingSpread
			0.030, 				// dblCounterPartyHazardRate
			0.300, 				// dblCounterPartyRecoveryRate
			0.030 / (1 - 0.30) 	// dblCounterPartyFundingSpread
		);

		AndersenPykhtinSokolLag andersenPykhtinSokolLag = AndersenPykhtinSokolLag.ClassicalMinus();

		FixFloatComponent fixFloatComponent = OTCIRS (
			spotDate,
			currency,
			fixFloatMaturityTenor,
			fixFloatCoupon
		);

		FloatStreamMPoR floatCouponStream = new FloatStreamMPoR (
			fixFloatComponent.derivedStream(),
			fixFloatNotional
		);

		CorrelatedPathVertexDimension correlatedPathVertexDimension = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			correlationMatrix,
			vertexGenerationPeriodCount,
			1,
			true,
			null
		);

		JulianDate exposureDate = spotDate;
		int[] exposureDateArray = new int[exposurePeriodCount + 1];
		int[] variationMarginGapEndDateArray = new int[exposurePeriodCount + 1];
		int[] variationMarginGapStartDateArray = new int[exposurePeriodCount + 1];
		double[] tradePaymentGapArray = new double[exposurePeriodCount + 1];
		double[] variationMarginGapArray = new double[exposurePeriodCount + 1];
		double[] clientTradePaymentGapArray = new double[exposurePeriodCount + 1];
		double[] collateralizedExposureArray = new double[exposurePeriodCount + 1];
		double[] variationMarginPostingArray = new double[exposurePeriodCount + 1];
		double[] variationMarginEstimateArray = new double[exposurePeriodCount + 1];
		double[] clientDealerTradePaymentGapArray = new double[exposurePeriodCount + 1];
		double[] collateralizedPositiveExposureArray = new double[exposurePeriodCount + 1];
		int[] clientTradePaymentGapEndDateArray = new int[exposurePeriodCount + 1];
		int[] clientTradePaymentGapStartDateArray = new int[exposurePeriodCount + 1];
		int[] clientDealerTradePaymentGapEndDateArray = new int[exposurePeriodCount + 1];
		int[] clientDealerTradePaymentGapStartDateArray = new int[exposurePeriodCount + 1];

		for (int i = 0; i <= exposurePeriodCount; ++i)
		{
			tradePaymentGapArray[i] = 0.;
			variationMarginGapArray[i] = 0.;
			clientTradePaymentGapArray[i] = 0.;
			collateralizedExposureArray[i] = 0.;
			variationMarginPostingArray[i] = 0.;
			variationMarginEstimateArray[i] = 0.;
			clientDealerTradePaymentGapArray[i] = 0.;
			collateralizedPositiveExposureArray[i] = 0.;

			exposureDateArray[i] = exposureDate.julian();

			exposureDate = exposureDate.addTenor (exposurePeriodTenor);
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			MarketPath marketPath = new MarketPath (
				marketVertexGenerator.marketVertex (
					initialMarketVertex,
					Matrix.Transpose (correlatedPathVertexDimension.straightPathVertexRd().flatform())
				)
			);

			VariationMarginTradeTrajectoryEstimator marginTradeFlowTrajectory =
				new VariationMarginTradeTrajectoryEstimator (
					exposureDateArray,
					currency,
					floatCouponStream,
					marketPath,
					andersenPykhtinSokolLag
				);

			Map<Integer, VariationMarginTradeVertexExposure> mapMarginTradeFlowEntry =
				marginTradeFlowTrajectory.trajectory();

			for (int i = 0; i <= exposurePeriodCount; ++i)
			{
				VariationMarginTradeVertexExposure marginTradeFlowEntry = mapMarginTradeFlowEntry.get (exposureDateArray[i]);

				LastFlowDates lastFlowDates = marginTradeFlowEntry.lastFlowDates();

				tradePaymentGapArray[i] += marginTradeFlowEntry.tradePaymentGap();

				clientTradePaymentGapArray[i] += marginTradeFlowEntry.clientTradePaymentGap();

				clientDealerTradePaymentGapArray[i] += marginTradeFlowEntry.clientDealerTradePaymentGap();

				collateralizedExposureArray[i] += marginTradeFlowEntry.collateralizedExposure();

				collateralizedPositiveExposureArray[i] += marginTradeFlowEntry.collateralizedPositiveExposure();

				variationMarginEstimateArray[i] += marginTradeFlowEntry.variationMarginEstimate();

				variationMarginPostingArray[i] += marginTradeFlowEntry.variationMarginPosting();

				variationMarginGapArray[i] += marginTradeFlowEntry.variationMarginGap();

				variationMarginGapStartDateArray[i] = lastFlowDates.clientVariationMargin().julian();

				variationMarginGapEndDateArray[i] = lastFlowDates.dealerVariationMargin().julian();

				clientTradePaymentGapStartDateArray[i] = lastFlowDates.clientTrade().julian();

				clientTradePaymentGapEndDateArray[i] = lastFlowDates.dealerTrade().julian();

				clientDealerTradePaymentGapStartDateArray[i] = lastFlowDates.dealerTrade().julian();

				clientDealerTradePaymentGapEndDateArray[i] = lastFlowDates.variationMarginPeriodEnd().julian();
			}
		}

		System.out.println();

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                            FIXED STREAM MARGIN/TRADE FLOW EXPOSURES AND DATES                                                                               ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                                                                                                                             ||");

		System.out.println ("\t|  L -> R:                                                                                                                                                                                                    ||");

		System.out.println ("\t|                                                                                                                                                                                                             ||");

		System.out.println ("\t|    - Exposure Date                                                                                                                                                                                          ||");

		System.out.println ("\t|    - Variation Margin Gap Start Date                                                                                                                                                                        ||");

		System.out.println ("\t|    - Variation Margin Gap End Date                                                                                                                                                                          ||");

		System.out.println ("\t|    - Variation Margin Estimate                                                                                                                                                                              ||");

		System.out.println ("\t|    - Variation Margin Posting                                                                                                                                                                               ||");

		System.out.println ("\t|    - Variation Margin Gap                                                                                                                                                                                   ||");

		System.out.println ("\t|    - Client Trade Payment Gap Start Date                                                                                                                                                                    ||");

		System.out.println ("\t|    - Client Trade Payment Gap End Date                                                                                                                                                                      ||");

		System.out.println ("\t|    - Client Trade Payment Gap                                                                                                                                                                               ||");

		System.out.println ("\t|    - Net Trade Payment Gap Start Date                                                                                                                                                                       ||");

		System.out.println ("\t|    - Net Trade Payment Gap End Date                                                                                                                                                                         ||");

		System.out.println ("\t|    - Net Trade Payment Gap                                                                                                                                                                                  ||");

		System.out.println ("\t|    - Trade Payment Gap                                                                                                                                                                                      ||");

		System.out.println ("\t|    - Exposure                                                                                                                                                                                               ||");

		System.out.println ("\t|    - Positive Exposure                                                                                                                                                                                      ||");

		System.out.println ("\t|                                                                                                                                                                                                             ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i <= exposurePeriodCount; ++i)
		{
			System.out.println (
				"\t| [" +
				new JulianDate (exposureDateArray[i]) + "] => [" +
				new JulianDate (variationMarginGapStartDateArray[i]) + " -> " +
				new JulianDate (variationMarginGapEndDateArray[i]) + "] | " +
				FormatUtil.FormatDouble (variationMarginEstimateArray[i] / pathCount, 5, 2, 1) + " | " +
				FormatUtil.FormatDouble (variationMarginPostingArray[i] / pathCount, 5, 2, 1) + " | " +
				FormatUtil.FormatDouble (variationMarginGapArray[i] / pathCount, 5, 2, 1) + " | [" +
				new JulianDate (clientTradePaymentGapStartDateArray[i]) + " -> " +
				new JulianDate (clientTradePaymentGapEndDateArray[i]) + "] | " +
				FormatUtil.FormatDouble (clientTradePaymentGapArray[i] / pathCount, 5, 2, 1) + " | [" +
				new JulianDate (clientDealerTradePaymentGapStartDateArray[i]) + " -> " +
				new JulianDate (clientDealerTradePaymentGapEndDateArray[i]) + "] | " +
				FormatUtil.FormatDouble (clientDealerTradePaymentGapArray[i] / pathCount, 5, 2, 1) + " | " +
				FormatUtil.FormatDouble (tradePaymentGapArray[i] / pathCount, 5, 2, 1) + " | " +
				FormatUtil.FormatDouble (collateralizedExposureArray[i] / pathCount, 5, 2, 1) + " | " +
				FormatUtil.FormatDouble (collateralizedPositiveExposureArray[i] / pathCount, 5, 2, 1) + " ||"
			);
		}

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
