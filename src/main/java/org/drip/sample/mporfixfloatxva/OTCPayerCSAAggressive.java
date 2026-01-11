
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
import org.drip.exposure.mpor.PathVariationMarginTrajectoryEstimator;
import org.drip.exposure.mpor.VariationMarginTradeVertexExposure;
import org.drip.exposure.universe.LatentStateWeiner;
import org.drip.exposure.universe.MarketPath;
import org.drip.exposure.universe.MarketVertex;
import org.drip.exposure.universe.MarketVertexGenerator;
import org.drip.market.otc.FixedFloatSwapConvention;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.measure.crng.CorrelatedFactorsPathVertexRealization;
import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.dynamics.HazardJumpEvaluator;
import org.drip.measure.realization.DiffusionEvolver;
import org.drip.measure.realization.JumpDiffusionEvolver;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.product.rates.FixFloatComponent;
import org.drip.service.common.FormatUtil;
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
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>OTCPayerCSAAggressive</i> displays the MPoR-related XVA Metrics Suite for the given OTC Payer Swap on a
 * 	Daily Grid using the "Aggressive" CSA Timeline of Andersen, Pykhtin, and Sokol (2017). The References
 * 	are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017a): Re-thinking Margin Period of Risk
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017b): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/mporfixfloatxva/README.md">OTC Fix-Float MPoR XVA</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OTCPayerCSAAggressive
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

	/**
	 * Entry Point
	 * 
	 * @param args Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

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

		AndersenPykhtinSokolLag andersenPykhtinSokolLag = AndersenPykhtinSokolLag.Aggressive();

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

		CorrelatedFactorsPathVertexRealization correlatedPathVertexDimension = new CorrelatedFactorsPathVertexRealization (
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
						R1MatrixUtil.Transpose (correlatedPathVertexDimension.trajectoryStraightNodeRd().flatform())
					)
				)
			);

			PathVariationMarginTrajectoryEstimator variationMarginTradeTrajectoryEstimator =
				PathVariationMarginTrajectoryEstimator.Standard (
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
			(StandardizedExposureGeneratorScheme.Basel (eadMultiplier)));

		System.out.println ("\t|-------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
