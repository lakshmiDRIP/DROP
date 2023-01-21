
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
import org.drip.numerical.linearalgebra.Matrix;
import org.drip.service.common.FormatUtil;
import org.drip.service.common.StringUtil;
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
 * <i>SetOffBaselProxy</i> simulates for various Latent States and Exposures for an Fix Float Swap and
 * 	computes the XVA Metrics using the Basel Proxy-Style Exposure Generator using Burgard Kjaer Set Off CSA
 * 	Vertexes. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option <b>eSSRN</b>
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  	</li>
 *  	<li>
 *  		Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management,
 *  			and Collateral Trading <b>eSSRN</b>
 *  			https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting <b>eSSRN</b>
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xvafixfloat/README.md">Cross Product XVA Simulation Digest</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SetOffBaselProxy
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

	private static final TerminalLatentState DealerSeniorRecovery (
		final String currency,
		final String dealer,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double dealerSeniorRecoveryDrift = 0.0002;
		double dealerSeniorRecoveryVolatility = 0.02;

		LatentStateLabel dealerSeniorRecoveryLabel = EntityRecoveryLabel.Senior (
			dealer,
			currency
		);

		latentStateLabelList.add (dealerSeniorRecoveryLabel);

		return new TerminalLatentState (
			dealerSeniorRecoveryLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerSeniorRecoveryDrift,
					dealerSeniorRecoveryVolatility
				)
			)
		);
	}

	private static final TerminalLatentState DealerSubordinateRecovery (
		final String currency,
		final String dealer,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double dealerSubordinateRecoveryDrift = 0.0002;
		double dealerSubordinateRecoveryVolatility = 0.02;

		LatentStateLabel dealerSubordinateRecoveryLabel = EntityRecoveryLabel.Subordinate (
			dealer,
			currency
		);

		latentStateLabelList.add (dealerSubordinateRecoveryLabel);

		return new TerminalLatentState (
			dealerSubordinateRecoveryLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dealerSubordinateRecoveryDrift,
					dealerSubordinateRecoveryVolatility
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
			DealerSeniorRecovery (
				currency,
				dealer,
				latentStateLabelList
			),
			DealerSubordinateRecovery (
				currency,
				dealer,
				latentStateLabelList
			),
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
			{1.00, 0.00, 0.00, 0.20, 0.15, 0.05, 0.00, 0.00, 0.00, 0.00, 0.00}, // #0  DEALER HAZARD
			{0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #1  DEALER SENIOR RECOVERY
			{0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #2  DEALER SUBORDINATE RECOVERY
			{0.20, 0.00, 0.00, 1.00, 0.13, 0.25, 0.00, 0.00, 0.00, 0.00, 0.00}, // #3  CLIENT HAZARD
			{0.15, 0.00, 0.00, 0.13, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #4  CLIENT RECOVERY
			{0.05, 0.00, 0.25, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #5  OVERNIGHT REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00, 0.00}, // #6  CSA REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00, 0.00}, // #7  DEALER SENIOR FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00, 0.00}, // #8  DEALER SUBORDINATE FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00}, // #9  CLIENT FUNDING REPLICATOR
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00}, // #10 OTC FIX FLOAT REPLICATOR
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
							PositionReplicationScheme.BURGARD_KJAER_SET_OFF_VERTEX,
							BrokenDateScheme.SQUARE_ROOT_OF_TIME,
							0.,
							CloseOutScheme.BILATERAL
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
							EntityRecoveryLabel.Subordinate (
								dealer,
								currency
							),
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
			0.250, 				// dblBankRecoveryRate
			0.015 / (1 - 0.25), // dblBankFundingSpread
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
