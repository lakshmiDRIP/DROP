
package org.drip.sample.xvafixfloat;

import java.util.ArrayList;
import java.util.List;

import org.drip.analytics.date.*;
import org.drip.exposure.evolver.*;
import org.drip.exposure.holdings.*;
import org.drip.exposure.universe.*;
import org.drip.measure.crng.*;
import org.drip.measure.discrete.CorrelatedPathVertexDimension;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.*;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.StringUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.*;
import org.drip.xva.dynamics.*;
import org.drip.xva.gross.*;
import org.drip.xva.proto.*;
import org.drip.xva.settings.*;

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
 * AlbaneseAndersenBaselProxy simulates for various Latent States and Exposures for an Fix Float Swap and
 *  computes the XVA Metrics using the Basel Proxy-Style Exposure Generator using Albanese Andersen
 *  Vertexes. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
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

public class AlbaneseAndersenBaselProxy
{

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
		final OTCFixFloatLabel otcFixFloatLabel,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double otcFixFloatNumeraireDrift = 0.0;
		double otcFixFloatNumeraireVolatility = 0.25;

		latentStateLabelList.add (otcFixFloatLabel);

		LatentStateDynamicsContainer latentStateDynamicsContainer = new LatentStateDynamicsContainer();

		latentStateDynamicsContainer.addOTCFixFloat (
			new TerminalLatentState (
				otcFixFloatLabel,
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
		final OTCFixFloatLabel otcFixFloatLabel,
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
				otcFixFloatLabel,
				latentStateLabelList
			)
		);
	}

	private static final void ThinStatistics (
		final String header,
		final JulianDate[] vertexDateArray,
		final UnivariateDiscreteThin[] thinStatisticsArray)
		throws Exception
	{
		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (header);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String statisticsDump = "\t|       DATE      =>" ;

		for (int i = 0; i < vertexDateArray.length; ++i)
		{
			statisticsDump = statisticsDump + " " + vertexDateArray[i] + "  |";
		}

		System.out.println (statisticsDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		statisticsDump = "\t|     AVERAGE     =>";

		for (int j = 0; j < thinStatisticsArray.length; ++j)
		{
			statisticsDump = statisticsDump + "   " + FormatUtil.FormatDouble (thinStatisticsArray[j].average(), 2, 4, 1.) + "   |";
		}

		System.out.println (statisticsDump);

		statisticsDump = "\t|     MAXIMUM     =>";

		for (int j = 0; j < thinStatisticsArray.length; ++j)
		{
			statisticsDump = statisticsDump + "   " + FormatUtil.FormatDouble (thinStatisticsArray[j].maximum(), 2, 4, 1.) + "   |";
		}

		System.out.println (statisticsDump);

		statisticsDump = "\t|     MINIMUM     =>";

		for (int j = 0; j < thinStatisticsArray.length; ++j)
		{
			statisticsDump = statisticsDump + "   " + FormatUtil.FormatDouble (thinStatisticsArray[j].minimum(), 2, 4, 1.) + "   |";
		}

		System.out.println (statisticsDump);

		statisticsDump = "\t|      ERROR      =>";

		for (int j = 0; j < thinStatisticsArray.length; ++j)
		{
			statisticsDump = statisticsDump + "   " + FormatUtil.FormatDouble (thinStatisticsArray[j].error(), 2, 4, 1.) + "   |";
		}

		System.out.println (statisticsDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
	}

	private static final void ThinStatistics (
		final String header,
		final UnivariateDiscreteThin thinStatistics)
		throws Exception
	{
		System.out.println (
			header +
			FormatUtil.FormatDouble (thinStatistics.average(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (thinStatistics.maximum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (thinStatistics.minimum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (thinStatistics.error(), 3, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String dealer = "CITI";
		String client = "AIG";
		String currency = "USD";

		double dealerVMThreshold = -0.1;
		double clientVMThreshold = 0.1;

		/*
		 * Evolution Control
		 */

		int pathCount = 60000;
		int exposureSamplingNodeCount = 10;
		String exposureSamplingTenor = "6M";
		double[][] correlationMatrix = new double[][] {
			{1.00, 0.00, 0.20, 0.15, 0.05, 0.00, 0.00, 0.00, 0.00, 0.00}, // #0  DEALER HAZARD
			{0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #1  DEALER SENIOR RECOVERY
			{0.20, 0.00, 1.00, 0.13, 0.25, 0.00, 0.00, 0.00, 0.00, 0.00}, // #2  CLIENT HAZARD
			{0.15, 0.00, 0.13, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #3  CLIENT RECOVERY
			{0.05, 0.00, 0.25, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #4  OVERNIGHT REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00}, // #5  CSA REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00}, // #6  DEALER SENIOR FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00}, // #7  DEALER SUBORDINATE FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00}, // #8  CLIENT FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00}, // #9  OTC FIX FLOAT REPLICATOR
		};

		JulianDate spotDate = DateUtil.Today();

		OTCFixFloatLabel otcFixFloatLabel = OTCFixFloatLabel.Standard (currency + "-3M-10Y");

		JulianDate terminationDate = spotDate;
		PathExposureAdjustment[] pathExposureAdjustmentArray = new PathExposureAdjustment[pathCount];

		for (int i = 0; i < exposureSamplingNodeCount; ++i)
		{
			terminationDate = terminationDate.addTenor (exposureSamplingTenor);
		}

		final int maturityDate = terminationDate.julian();

		List<LatentStateLabel> latentStateLabelList = new ArrayList<LatentStateLabel>();

		PathSimulator fixFloatPathSimulator = new PathSimulator (
			pathCount,
			ConstructMarketVertexGenerator (
				spotDate,
				exposureSamplingTenor,
				exposureSamplingNodeCount,
				currency,
				dealer,
				client,
				otcFixFloatLabel,
				latentStateLabelList
			),
			AdjustmentDigestScheme.ALBANESE_ANDERSEN_METRICS_POINTER,
			PositionGroupContainer.Solo (
				new PositionGroup (
					new PositionSchemaSpecification (
						"POSGRPSPEC1",
						"POSGRPSPEC1",
						PositionGroupSpecification.FixedThreshold (
							"FIXEDTHRESHOLD",
							clientVMThreshold,
							dealerVMThreshold,
							PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX,
							BrokenDateScheme.SQUARE_ROOT_OF_TIME,
							0.,
							CloseOutScheme.ISDA_92
						),
						new CollateralGroupSpecification (
							StringUtil.GUID(),
							"FIXEDTHRESHOLD",
							OvernightLabel.Create (currency),
							CSALabel.ISDA (currency)
						),
						new CreditDebtGroupSpecification (
							"NETGRPSPEC1",
							"NETGRPSPEC1",
							EntityHazardLabel.Standard (
								dealer,
								currency
							),
							EntityHazardLabel.Standard (
								client,
								currency
							),
							EntityRecoveryLabel.Senior (
								dealer,
								currency
							),
							EntityRecoveryLabel.Senior (
								client,
								currency
							),
							null,
							true,
							true
						),
						new FundingGroupSpecification (
							"FUNDGRPSPEC1",
							"FUNDGRPSPEC1",
							EntityFundingLabel.Senior (
								dealer,
								currency
							),
							EntityFundingLabel.Senior (
								client,
								currency
							),
							EntityFundingLabel.Subordinate (
								dealer,
								currency
							)
						)
					),
					new FixFloatBaselPositionEstimator (
						maturityDate,
						otcFixFloatLabel
					)
				)
			)
		);

		LatentStateVertexContainer latentStateVertexContainer = new LatentStateVertexContainer();

		latentStateVertexContainer.add (
			otcFixFloatLabel,
			0.
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

		CorrelatedPathVertexDimension correlatedPathVertexDimension = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			correlationMatrix,
			exposureSamplingNodeCount,
			1,
			true,
			null
		);

		for (int i = 0; i < pathCount; ++i)
		{
			pathExposureAdjustmentArray[i] = fixFloatPathSimulator.singleTrajectory (
				initialMarketVertex,
				LatentStateWeiner.FromUnitRandom (
					latentStateLabelList,
					Matrix.Transpose (correlatedPathVertexDimension.straightPathVertexRd().flatform())
				)
			);
		}

		ExposureAdjustmentAggregator exposureAdjustmentAggregator = new ExposureAdjustmentAggregator
			(pathExposureAdjustmentArray);

		ExposureAdjustmentDigest exposureAdjustmentDigest = exposureAdjustmentAggregator.digest();

		System.out.println();

		ThinStatistics (
			"\t|                                                                                COLLATERALIZED EXPOSURE                                                                                |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.collateralizedExposure()
		);

		ThinStatistics (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE                                                                               |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.uncollateralizedExposure()
		);

		ThinStatistics (
			"\t|                                                                                COLLATERALIZED EXPOSURE PV                                                                             |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.collateralizedExposurePV()
		);

		ThinStatistics (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE PV                                                                            |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.uncollateralizedExposurePV()
		);

		ThinStatistics (
			"\t|                                                                            COLLATERALIZED POSITIVE EXPOSURE PV                                                                        |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.collateralizedPositiveExposurePV()
		);

		ThinStatistics (
			"\t|                                                                           UNCOLLATERALIZED POSITIVE EXPOSURE PV                                                                       |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.uncollateralizedPositiveExposurePV()
		);

		ThinStatistics (
			"\t|                                                                            COLLATERALIZED NEGATIVE EXPOSURE PV                                                                        |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.collateralizedNegativeExposurePV()
		);

		ThinStatistics (
			"\t|                                                                           UNCOLLATERALIZED NEGATIVE EXPOSURE PV                                                                       |",
			exposureAdjustmentAggregator.vertexDates(),
			exposureAdjustmentDigest.uncollateralizedNegativeExposurePV()
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

		ThinStatistics (
			"\t||  UCVA  => ",
			exposureAdjustmentDigest.ucva()
		);

		ThinStatistics (
			"\t|| FTDCVA => ",
			exposureAdjustmentDigest.ftdcva()
		);

		ThinStatistics (
			"\t||   CVA  => ",
			exposureAdjustmentDigest.cva()
		);

		ThinStatistics (
			"\t||  CVACL => ",
			exposureAdjustmentDigest.cvacl()
		);

		ThinStatistics (
			"\t||   DVA  => ",
			exposureAdjustmentDigest.dva()
		);

		ThinStatistics (
			"\t||   FVA  => ",
			exposureAdjustmentDigest.fva()
		);

		ThinStatistics (
			"\t||   FDA  => ",
			exposureAdjustmentDigest.fda()
		);

		ThinStatistics (
			"\t||   FCA  => ",
			exposureAdjustmentDigest.fca()
		);

		ThinStatistics (
			"\t||   FBA  => ",
			exposureAdjustmentDigest.fba()
		);

		ThinStatistics (
			"\t||  SFVA  => ",
			exposureAdjustmentDigest.sfva()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		ThinStatistics (
			"\t||  Total => ",
			exposureAdjustmentDigest.totalVA()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
