
package org.drip.sample.mporfixfloatxva;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.exposure.csatimeline.AndersenPykhtinSokolLag;
import org.drip.exposure.evolver.EntityDynamicsContainer;
import org.drip.exposure.evolver.LatentStateDynamicsContainer;
import org.drip.exposure.evolver.LatentStateVertexContainer;
import org.drip.exposure.evolver.PrimarySecurity;
import org.drip.exposure.evolver.PrimarySecurityDynamicsContainer;
import org.drip.exposure.evolver.TerminalLatentState;
import org.drip.exposure.generator.FixFloatMPoR;
import org.drip.exposure.mpor.VariationMarginTradeTrajectoryEstimator;
import org.drip.exposure.mpor.VariationMarginTradeVertexExposure;
import org.drip.exposure.universe.LatentStateWeiner;
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
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.product.rates.FixFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.EntityFundingLabel;
import org.drip.state.identifier.EntityHazardLabel;
import org.drip.state.identifier.EntityRecoveryLabel;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.identifier.LatentStateLabel;
import org.drip.state.identifier.OvernightLabel;
import org.drip.xva.gross.BaselExposureDigest;
import org.drip.xva.gross.ExposureAdjustmentAggregator;
import org.drip.xva.gross.ExposureAdjustmentDigest;
import org.drip.xva.gross.MonoPathExposureAdjustment;
import org.drip.xva.gross.PathExposureAdjustment;
import org.drip.xva.hypothecation.CollateralGroupVertex;
import org.drip.xva.netting.CollateralGroupPath;
import org.drip.xva.netting.CreditDebtGroupPath;
import org.drip.xva.netting.FundingGroupPath;
import org.drip.xva.settings.StandardizedExposureGeneratorScheme;
import org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath;
import org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath;
import org.drip.xva.vertex.AlbaneseAndersen;

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
 * OTCPayerCSAClassicalMinus displays the MPoR-related XVA Metrics Suite for the given OTC Payer Swap on a
 *  Daily Grid using the "Classical-" CSA Timeline of Andersen, Pykhtin, and Sokol (2017). The References
 *  are:
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

public class OTCPayerCSAClassicalMinus
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

	private static final PrimarySecurity OvernightReplicator (
		final String currency,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double overnightReplicatorDrift = 0.0025;
		double overnightReplicatorVolatility = 0.001;
		double overnightReplicatorRepo = 0.0;

		LatentStateLabel overnightLabel = OvernightLabel.Create (currency);

		latentStateLabelList.add (overnightLabel);

		return new PrimarySecurity (
			currency + "_OVERNIGHT",
			overnightLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					overnightReplicatorDrift,
					overnightReplicatorVolatility
				)
			),
			overnightReplicatorRepo
		);
	}

	private static final PrimarySecurity CSAReplicator (
		final String currency,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double csaReplicatorDrift = 0.01;
		double csaReplicatorVolatility = 0.002;
		double csaReplicatorRepo = 0.005;

		LatentStateLabel csaLabel = CSALabel.ISDA (currency);

		latentStateLabelList.add (csaLabel);

		return new PrimarySecurity (
			currency + "_CSA",
			csaLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					csaReplicatorDrift,
					csaReplicatorVolatility
				)
			),
			csaReplicatorRepo
		);
	}

	private static final PrimarySecurity DealerSeniorFundingReplicator (
		final String currency,
		final String dealer,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double dealerSeniorFundingReplicatorDrift = 0.03;
		double dealerSeniorFundingReplicatorVolatility = 0.002;
		double dealerSeniorFundingReplicatorRepo = 0.028;

		LatentStateLabel dealerSeniorFundingLabel = EntityFundingLabel.Senior (
			dealer,
			currency
		);

		latentStateLabelList.add (dealerSeniorFundingLabel);

		return new PrimarySecurity (
			dealer + "_" + currency + "_SENIOR_ZERO",
			dealerSeniorFundingLabel,
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerSeniorFundingReplicatorDrift,
					dealerSeniorFundingReplicatorVolatility
				),
				HazardJumpEvaluator.Standard (
					0.3,
					0.45
				)
			),
			dealerSeniorFundingReplicatorRepo
		);
	}

	private static final PrimarySecurity DealerSubordinateFundingReplicator (
		final String currency,
		final String dealer,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double dealerSubordinateFundingReplicatorDrift = 0.045;
		double dealerSubordinateFundingReplicatorVolatility = 0.002;
		double dealerSubordinateFundingReplicatorRepo = 0.028;

		LatentStateLabel dealerSubordinateFundingLabel = EntityFundingLabel.Subordinate (
			dealer,
			currency
		);

		latentStateLabelList.add (dealerSubordinateFundingLabel);

		return new PrimarySecurity (
			dealer + "_" + currency + "_SUBORDINATE_ZERO",
			dealerSubordinateFundingLabel,
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerSubordinateFundingReplicatorDrift,
					dealerSubordinateFundingReplicatorVolatility
				),
				HazardJumpEvaluator.Standard (
					0.3,
					0.25
				)
			),
			dealerSubordinateFundingReplicatorRepo
		);
	}

	private static final PrimarySecurity ClientFundingReplicator (
		final String currency,
		final String client,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double clientFundingReplicatorDrift = 0.03;
		double clientFundingReplicatorVolatility = 0.003;
		double clientFundingReplicatorRepo = 0.028;

		LatentStateLabel clientFundingLabel = EntityFundingLabel.Senior (
			client,
			currency
		);

		latentStateLabelList.add (clientFundingLabel);

		return new PrimarySecurity (
			client + "_" + currency + "_SENIOR_ZERO",
			clientFundingLabel,
			new JumpDiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					clientFundingReplicatorDrift,
					clientFundingReplicatorVolatility
				),
				HazardJumpEvaluator.Standard (
					0.5,
					0.30
				)
			),
			clientFundingReplicatorRepo
		);
	}

	private static final TerminalLatentState DealerHazard (
		final String currency,
		final String dealer,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double dealerHazardDrift = 0.0002;
		double dealerHazardVolatility = 0.02;

		LatentStateLabel dealerHazardLabel = EntityHazardLabel.Standard (
			dealer,
			currency
		);

		latentStateLabelList.add (dealerHazardLabel);

		return new TerminalLatentState (
			dealerHazardLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerHazardDrift,
					dealerHazardVolatility
				)
			)
		);
	}

	private static final TerminalLatentState DealerRecovery (
		final String currency,
		final String dealer,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double dealerRecoveryDrift = 0.0002;
		double dealerRecoveryVolatility = 0.02;

		LatentStateLabel dealerRecoveryLabel = EntityRecoveryLabel.Senior (
			dealer,
			currency
		);

		latentStateLabelList.add (dealerRecoveryLabel);

		return new TerminalLatentState (
			dealerRecoveryLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerRecoveryDrift,
					dealerRecoveryVolatility
				)
			)
		);
	}

	private static final TerminalLatentState ClientHazard (
		final String currency,
		final String client,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double clientHazardDrift = 0.0002;
		double clientHazardVolatility = 0.02;

		LatentStateLabel clientHazardLabel = EntityHazardLabel.Standard (
			client,
			currency
		);

		latentStateLabelList.add (clientHazardLabel);

		return new TerminalLatentState (
			clientHazardLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					clientHazardDrift,
					clientHazardVolatility
				)
			)
		);
	}

	private static final TerminalLatentState ClientRecovery (
		final String currency,
		final String client,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double clientRecoveryDrift = 0.0002;
		double clientRecoveryVolatility = 0.02;

		LatentStateLabel clientRecoveryLabel = EntityRecoveryLabel.Senior (
			client,
			currency
		);

		latentStateLabelList.add (clientRecoveryLabel);

		return new TerminalLatentState (
			clientRecoveryLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					clientRecoveryDrift,
					clientRecoveryVolatility
				)
			)
		);
	}

	private static final EntityDynamicsContainer EntityEvolver (
		final String currency,
		final String dealer,
		final String client,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		return new EntityDynamicsContainer (
			DealerHazard (
				currency,
				dealer,
				latentStateLabelList
			),
			DealerRecovery (
				currency,
				dealer,
				latentStateLabelList
			),
			null,
			ClientHazard (
				currency,
				client,
				latentStateLabelList
			),
			ClientRecovery (
				currency,
				client,
				latentStateLabelList
			)
		);
	}

	private static final PrimarySecurityDynamicsContainer PrimarySecurityEvolver (
		final String currency,
		final String dealer,
		final String client,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		return new PrimarySecurityDynamicsContainer (
			null,
			OvernightReplicator (
				currency,
				latentStateLabelList
			),
			CSAReplicator (
				currency,
				latentStateLabelList
			),
			DealerSeniorFundingReplicator (
				currency,
				dealer,
				latentStateLabelList
			),
			DealerSubordinateFundingReplicator (
				currency,
				dealer,
				latentStateLabelList
			),
			ClientFundingReplicator (
				currency,
				client,
				latentStateLabelList
			)
		);
	}

	private static final LatentStateDynamicsContainer LatentStateEvolver (
		final ForwardLabel forwardLabel,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double otcFixFloatNumeraireDrift = 0.0;
		double otcFixFloatNumeraireVolatility = 0.25;

		latentStateLabelList.add (forwardLabel);

		LatentStateDynamicsContainer latentStateDynamicsContainer = new LatentStateDynamicsContainer();

		latentStateDynamicsContainer.addForward (
			new TerminalLatentState (
				forwardLabel,
				new DiffusionEvolver (
					DiffusionEvaluatorLinear.Standard (
						otcFixFloatNumeraireDrift,
						otcFixFloatNumeraireVolatility
					)
				)
			)
		);

		return latentStateDynamicsContainer;
	}

	private static final MarketVertexGenerator ConstructMarketVertexGenerator (
		final JulianDate spotDate,
		final String exposureSamplingTenor,
		final int exposureSamplingNodeCount,
		final String currency,
		final String dealer,
		final String client,
		final ForwardLabel forwardLabel,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		JulianDate terminationDate = spotDate;
		int[] eventVertexArray = new int[exposureSamplingNodeCount];

		for (int i = 0; i < exposureSamplingNodeCount; ++i)
		{
			terminationDate = terminationDate.addTenor (exposureSamplingTenor);

			eventVertexArray[i] = terminationDate.julian();
		}

		return new MarketVertexGenerator (
			spotDate.julian(),
			eventVertexArray,
			EntityEvolver (
				currency,
				dealer,
				client,
				latentStateLabelList
			),
			PrimarySecurityEvolver (
				currency,
				dealer,
				client,
				latentStateLabelList
			),
			LatentStateEvolver (
				forwardLabel,
				latentStateLabelList
			)
		);
	}

	private static final void DisplayThinStatistics (
		final String annotation,
		final UnivariateDiscreteThin univariateDiscreteThin)
		throws Exception
	{
		System.out.println (
			annotation + " => " +
			FormatUtil.FormatDouble (univariateDiscreteThin.average(), 3, 0, 1.) + " | " +
			FormatUtil.FormatDouble (univariateDiscreteThin.minimum(), 3, 0, 1.) + " | " +
			FormatUtil.FormatDouble (univariateDiscreteThin.maximum(), 3, 0, 1.) + " | " +
			FormatUtil.FormatDouble (univariateDiscreteThin.error(), 3, 0, 1.) + " ||"
		);
	}

	private static final void DisplayBaselMeasures (
		final BaselExposureDigest baselExposureDigest)
		throws Exception
	{
		System.out.println (
			"\t| Expected Exposure                    => " +
			FormatUtil.FormatDouble (baselExposureDigest.expectedExposure(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| Expected Positive Exposure           => " +
			FormatUtil.FormatDouble (baselExposureDigest.expectedPositiveExposure(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| Effective Expected Exposure          => " +
			FormatUtil.FormatDouble (baselExposureDigest.effectiveExpectedExposure(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| Effective Expected Positive Exposure => " +
			FormatUtil.FormatDouble (baselExposureDigest.effectiveExpectedPositiveExposure(), 6, 0, 1.) + " ||"
		);

		System.out.println (
			"\t| Exposure At Default                  => " +
			FormatUtil.FormatDouble (baselExposureDigest.exposureAtDefault(), 6, 0, 1.) + " ||"
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
		int vertexGenerationPeriodCount = exposurePeriodCount + 0;
		String currency = "USD";
		String dealer = "NOM";
		String client = "SSGA";
		double[][] correlationMatrix = new double[][]
		{
			{1.00, 0.00, 0.20, 0.15, 0.05, 0.00, 0.00, 0.00, 0.00, 0.00}, // #0  DEALER HAZARD
			{0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #1  DEALER SENIOR RECOVERY
			{0.20, 0.00, 1.00, 0.13, 0.25, 0.00, 0.00, 0.00, 0.00, 0.00}, // #2  CLIENT HAZARD
			{0.15, 0.00, 0.13, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #3  CLIENT RECOVERY
			{0.05, 0.00, 0.25, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #4  OVERNIGHT REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00}, // #5  CSA REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00}, // #6  DEALER SENIOR FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00}, // #7  DEALER SUBORDINATE FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00}, // #8  CLIENT FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00}, // #9  FORWARD NUMERAIRE
		};
		String fixFloatMaturityTenor = "1Y";
		double fixFloatCoupon = 0.02;
		double fixFloatNotional = -10.e+06;

		double eadMultiplier = 1.;
		int timeIntegrand = 365;

		StandardizedExposureGeneratorScheme standardizedExposureGeneratorScheme =
			new StandardizedExposureGeneratorScheme (
				eadMultiplier,
				timeIntegrand
			);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			currency,
			"3M"
		);

		List<LatentStateLabel> latentStateLabelList = new ArrayList<LatentStateLabel>();

		MarketVertexGenerator marketVertexGenerator = ConstructMarketVertexGenerator (
			spotDate,
			exposurePeriodTenor,
			vertexGenerationPeriodCount,
			currency,
			dealer,
			client,
			forwardLabel,
			latentStateLabelList
		);

		LatentStateVertexContainer latentStateVertexContainer = new LatentStateVertexContainer();

		latentStateVertexContainer.add (
			forwardLabel,
			0.02
		);

		MarketVertex initialMarketVertex = MarketVertex.Epochal (
			spotDate,
			1.000, 				// dblOvernightNumeraireInitial
			1.000, 				// dblCSANumeraire
			0.015, 				// dblBankHazardRate
			0.400, 				// dblBankRecoveryRate
			0.015 / (1 - 0.40), // dblBankFundingSpread
			0.030, 				// dblCounterPartyHazardRate
			0.300, 				// dblCounterPartyRecoveryRate
			0.030 / (1 - 0.30),	// dblCounterPartyFundingSpread
			latentStateVertexContainer
		);

		AndersenPykhtinSokolLag andersenPykhtinSokolLag = AndersenPykhtinSokolLag.ClassicalMinus();

		FixFloatComponent fixFloatComponent = OTCIRS (
			spotDate,
			currency,
			fixFloatMaturityTenor,
			fixFloatCoupon
		);

		FixFloatMPoR fixFloatMPoR = new FixFloatMPoR (
			fixFloatComponent,
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
		PathExposureAdjustment[] pathExposureAdjustmentArray = new PathExposureAdjustment[pathCount];

		for (int exposurePeriodIndex = 0; exposurePeriodIndex <= exposurePeriodCount; ++exposurePeriodIndex)
		{
			exposureDateArray[exposurePeriodIndex] = exposureDate.julian();

			exposureDate = exposureDate.addTenor (exposurePeriodTenor);
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			MarketPath marketPath = new MarketPath (
				marketVertexGenerator.marketVertex (
					initialMarketVertex,
					LatentStateWeiner.FromUnitRandom (
						latentStateLabelList,
						Matrix.Transpose (correlatedPathVertexDimension.straightPathVertexRd().flatform())
					)
				)
			);

			VariationMarginTradeTrajectoryEstimator variationMarginTradeTrajectoryEstimator =
				new VariationMarginTradeTrajectoryEstimator (
					exposureDateArray,
					currency,
					fixFloatMPoR,
					marketPath,
					andersenPykhtinSokolLag
				);

			Map<Integer, VariationMarginTradeVertexExposure> mapMarginTradeFlowEntry =
				variationMarginTradeTrajectoryEstimator.trajectory();

			CollateralGroupVertex[] collateralGroupVertexArray = new
				CollateralGroupVertex[exposurePeriodCount + 1];

			for (int exposurePeriodIndex = 0; exposurePeriodIndex <= exposurePeriodCount; ++exposurePeriodIndex)
			{
				VariationMarginTradeVertexExposure variationMarginTradeVertexExposure =
					mapMarginTradeFlowEntry.get (exposureDateArray[exposurePeriodIndex]);

				collateralGroupVertexArray[exposurePeriodIndex] = new AlbaneseAndersen (
					new JulianDate (exposureDateArray[exposurePeriodIndex]),
					variationMarginTradeVertexExposure.variationMarginEstimate(),
					variationMarginTradeVertexExposure.tradePaymentGap(),
					variationMarginTradeVertexExposure.variationMarginPosting()
				);
			}

			CollateralGroupPath collateralGroupPath = new CollateralGroupPath (
				collateralGroupVertexArray,
				marketPath
			);

			CreditDebtGroupPath creditDebtGroupPath = new AlbaneseAndersenNettingGroupPath (
				new CollateralGroupPath[] {collateralGroupPath},
				marketPath
			);

			FundingGroupPath fundingGroupPath = new AlbaneseAndersenFundingGroupPath (
				new CreditDebtGroupPath[] {creditDebtGroupPath},
				marketPath
			);

			pathExposureAdjustmentArray[pathIndex] = new MonoPathExposureAdjustment
				(new FundingGroupPath[] {fundingGroupPath});
		}

		ExposureAdjustmentAggregator exposureAdjustmentAggregator = new ExposureAdjustmentAggregator
			(pathExposureAdjustmentArray);

		ExposureAdjustmentDigest exposureAdjustmentDigest = exposureAdjustmentAggregator.digest();

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t|    OTC FIX FLOAT MPOR XVA METRICS     ||");

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t|                                       ||");

		System.out.println ("\t|    L -> R:                            ||");

		System.out.println ("\t|                                       ||");

		System.out.println ("\t|        - Average                      ||");

		System.out.println ("\t|        - Minimum                      ||");

		System.out.println ("\t|        - Maximum                      ||");

		System.out.println ("\t|        - Error                        ||");

		System.out.println ("\t|                                       ||");

		System.out.println ("\t|---------------------------------------||");

		DisplayThinStatistics ("\t| UCOLVA  ", exposureAdjustmentDigest.ucolva());

		DisplayThinStatistics ("\t| FTDCOLVA", exposureAdjustmentDigest.ftdcolva());

		DisplayThinStatistics ("\t| UCVA    ", exposureAdjustmentDigest.ucva());

		DisplayThinStatistics ("\t| FTDCVA  ", exposureAdjustmentDigest.ftdcva());

		DisplayThinStatistics ("\t| CVA     ", exposureAdjustmentDigest.cva());

		DisplayThinStatistics ("\t| CVACL   ", exposureAdjustmentDigest.cvacl());

		DisplayThinStatistics ("\t| DVA     ", exposureAdjustmentDigest.dva());

		DisplayThinStatistics ("\t| FVA     ", exposureAdjustmentDigest.fva());

		DisplayThinStatistics ("\t| FDA     ", exposureAdjustmentDigest.fda());

		DisplayThinStatistics ("\t| DVA2    ", exposureAdjustmentDigest.dva2());

		DisplayThinStatistics ("\t| FCA     ", exposureAdjustmentDigest.fca());

		DisplayThinStatistics ("\t| FBA     ", exposureAdjustmentDigest.fba());

		DisplayThinStatistics ("\t| SFVA    ", exposureAdjustmentDigest.sfva());

		System.out.println ("\t|---------------------------------------||");

		DisplayThinStatistics ("\t| Total VA", exposureAdjustmentDigest.totalVA());

		System.out.println ("\t|---------------------------------------||");

		System.out.println();

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|             BASEL EXPOSURE MEASURES             ||");

		System.out.println ("\t|-------------------------------------------------||");

		DisplayBaselMeasures (exposureAdjustmentAggregator.baselExposureDigest
			(standardizedExposureGeneratorScheme));

		System.out.println ("\t|-------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
