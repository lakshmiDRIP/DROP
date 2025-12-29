
package org.drip.sample.burgard2011;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.support.VertexDateBuilder;
import org.drip.exposure.evolver.*;
import org.drip.exposure.universe.*;
import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discontinuous.CorrelatedFactorsPathVertexRealization;
import org.drip.measure.dynamics.*;
import org.drip.measure.process.*;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>XVAMarketGeneration</i> generates the Asset, the Bank, and the Counter Party Credit/Funding Metrics
 * used in an XVA Run. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
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
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/burgard2011/README.md">Burgard Kjaer (2011) PDE Evolver</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class XVAMarketGeneration {

	private static final PrimarySecurity AssetValueReplicator (
		final String currency)
		throws Exception
	{
		double assetValueReplicatorDrift = 0.0025;
		double assetValueReplicatorVolatility = 0.10;
		double assetValueReplicatorRepo = 0.03;
		double assetValueReplicatorDividend = 0.02;

		EntityEquityLabel equityLabel = EntityEquityLabel.Standard (
			"AAPL",
			currency
		);

		return new PrimarySecurity (
			"AAPL",
			equityLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					assetValueReplicatorDrift - assetValueReplicatorDividend,
					assetValueReplicatorVolatility
				)
			),
			assetValueReplicatorRepo
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

	private static final PrimarySecurityDynamicsContainer PrimarySecurityEvolver (
		final String currency,
		final String dealer,
		final String client,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		List<PrimarySecurity> assetList = new ArrayList<PrimarySecurity>();

		assetList.add (AssetValueReplicator (currency));

		return new PrimarySecurityDynamicsContainer (
			assetList,
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

	private static final LatentStateDynamicsContainer LatentStateEvolver (
		final EntityEquityLabel equityLabel,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
		double assetValueReplicatorDrift = 0.0025;
		double assetValueReplicatorVolatility = 0.10;

		latentStateLabelList.add (equityLabel);

		LatentStateDynamicsContainer latentStateDynamicsContainer = new LatentStateDynamicsContainer();

		latentStateDynamicsContainer.addEntityEquity (
			new TerminalLatentState (
				equityLabel,
				new DiffusionEvolver (
					DiffusionEvaluatorLinear.Standard (
						assetValueReplicatorDrift,
						assetValueReplicatorVolatility
					)
				)
			)
		);

		return latentStateDynamicsContainer;
	}

	private static final MarketVertexGenerator ConstructMarketVertexGenerator (
		final JulianDate spotDate,
		final int[] eventVertexArray,
		final String currency,
		final String dealer,
		final String client,
		final EntityEquityLabel equityLabel,
		final List<LatentStateLabel> latentStateLabelList)
		throws Exception
	{
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
				equityLabel,
				latentStateLabelList
			)
		);
	}

	private static final MarketVertex[] MarketVertexArray (
		final Map<Integer, MarketVertex> marketVertexMap)
		throws Exception
	{
		int marketVertexCount = marketVertexMap.size();

		int marketVertexIndex = 0;
		MarketVertex[] marketVertexArray = new MarketVertex[marketVertexCount];

		for (Map.Entry<Integer, MarketVertex> marketVertexMapEntry : marketVertexMap.entrySet())
		{
			marketVertexArray[marketVertexIndex++] = marketVertexMapEntry.getValue();
		}

		return marketVertexArray;
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/* EnvManager.InitEnv ("");

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
		double dblAssetNumeraireVolatility = 0.010;
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

		EntityEquityLabel equityLabel = EntityEquityLabel.Standard (
			"AAPL",
			currency
		);

		PrimarySecurity tAsset = new PrimarySecurity (
			"AAPL",
			equityLabel,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					dblAssetNumeraireDrift - dblAssetNumeraireDividend,
					dblAssetNumeraireVolatility
				)
			),
			dblAssetNumeraireRepo
		);

		PrimarySecurity tOvernightIndex = new PrimarySecurity (
			currency + "_OVERNIGHT_ZERO",
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
			currency + "_CSA_ZERO",
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
			bank + "_" + currency + "_SENIOR_ZERO",
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
			bank + "_" + currency + "_SUBORDINATE_ZERO",
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
			counterParty + "_" + currency + "_SENIOR_ZERO",
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

		List<PrimarySecurity> assetList = new ArrayList<PrimarySecurity>();

		assetList.add (tAsset);

		MarketVertexGeneratorDeprecatione mvg = new MarketVertexGeneratorDeprecatione (
			iSpotDate,
			aiVertexDate,
			new PrimarySecurityDynamicsContainer (
				assetList,
				tOvernightIndex,
				tCollateralScheme,
				tBankSeniorFunding,
				tBankSubordinateFunding,
				tCounterPartyFunding
			),
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

		LatentStateVertexContainer latentStateVertexContainer = new LatentStateVertexContainer();

		latentStateVertexContainer.add (
			equityLabel,
			dblAssetNumeraireInitial
		);

		MarketVertex mvInitial = MarketVertex.Nodal (
			dtSpot,
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
			),
			latentStateVertexContainer
		);

		MarketVertex[] aMV = MarketVertexArray (
			mvg.marketVertex (
				mvInitial,
				Matrix.Transpose (
					SequenceGenerator.GaussianJoint (
						iNumVertex,
						aadblCorrelationMatrix
					)
				)
			)
		); */

		EnvManager.InitEnv ("");

		String dealer = "WFC";
		String client = "BAC";
		int vertexCount = 24;
		String currency = "USD";
		int simulationDuration = 365;

		double dealerHazardRateInitial = 0.03;
		double clientHazardRateInitial = 0.05;
		double dealerSeniorRecoveryRateInitial = 0.40;
		double clientRecoveryRateInitial = 0.40;

		double[][] latentStateCorrelationMatrix = new double[][]
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
			{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00}, // #9  EQUITY REPLICATOR
		};

		JulianDate spotDateJulian = DateUtil.Today();

		int spotDate = spotDateJulian.julian();

		int[] eventVertexArray = VertexDateBuilder.EqualWidth (
			spotDate,
			spotDate + simulationDuration,
			vertexCount
		);

		List<LatentStateLabel> latentStateLabelList = new ArrayList<LatentStateLabel>();

		EntityEquityLabel equityLabel = EntityEquityLabel.Standard (
			"AAPL",
			currency
		);

		MarketVertexGenerator marketVertexGenerator = ConstructMarketVertexGenerator (
			spotDateJulian,
			eventVertexArray,
			currency,
			dealer,
			client,
			equityLabel,
			latentStateLabelList
		);

		System.out.println ("marketVertexGenerator = " + marketVertexGenerator);

		LatentStateVertexContainer latentStateVertexContainer = new LatentStateVertexContainer();

		latentStateVertexContainer.add (
			equityLabel,
			1.
		);

		MarketVertex initialMarketVertex = MarketVertex.Epochal (
			spotDateJulian,
			1.000,
			1.000,
			dealerHazardRateInitial,
			dealerSeniorRecoveryRateInitial,
			dealerHazardRateInitial / (1 - dealerSeniorRecoveryRateInitial),
			clientHazardRateInitial,
			clientRecoveryRateInitial,
			clientHazardRateInitial / (1 - clientRecoveryRateInitial),
			latentStateVertexContainer
		);

		CorrelatedFactorsPathVertexRealization correlatedPathVertexDimension = new CorrelatedFactorsPathVertexRealization (
			new RandomNumberGenerator(),
			latentStateCorrelationMatrix,
			vertexCount,
			1,
			true,
			null
		);

		MarketVertex[] aMV = MarketVertexArray (
			marketVertexGenerator.marketVertex (
				initialMarketVertex,
				LatentStateWeiner.FromUnitRandom (
					latentStateLabelList,
					R1MatrixUtil.Transpose (correlatedPathVertexDimension.straightPathVertexRd().flatform())
				)
			)
		);

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
				"\t|| " + aMV[i].anchorDate() + " => " +
				FormatUtil.FormatDouble (aMV[i].latentStateValue (equityLabel), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].overnightRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aMV[i].overnightReplicator(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (aMV[i].csaRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aMV[i].csaReplicator(), 1, 6, 1.) + " ||"
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
			MarketVertexEntity emvBank = aMV[i].dealer();

			System.out.println (
				"\t|| " + aMV[i].anchorDate() + " => " +
				FormatUtil.FormatDouble (emvBank.hazardRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvBank.survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvBank.seniorRecoveryRate(), 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.seniorFundingSpread(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.seniorFundingReplicator(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvBank.subordinateRecoveryRate(), 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.subordinateFundingSpread(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (emvBank.subordinateFundingReplicator(), 1, 6, 1.) + " ||"
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
			MarketVertexEntity emvCounterParty = aMV[i].client();

			System.out.println (
				"\t|| " + aMV[i].anchorDate() + " => " +
				FormatUtil.FormatDouble (emvCounterParty.hazardRate(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvCounterParty.survivalProbability(), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (emvCounterParty.seniorRecoveryRate(), 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (emvCounterParty.seniorFundingSpread(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (emvCounterParty.seniorFundingReplicator(), 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t||----------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
