
package org.drip.sample.xvafixfloat;

import org.drip.analytics.date.*;
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
import org.drip.xva.evolver.*;
import org.drip.xva.gross.*;
import org.drip.xva.holdings.*;
import org.drip.xva.proto.*;
import org.drip.xva.settings.*;
import org.drip.xva.universe.*;

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

	private static final PrimarySecurityDynamicsContainer GenerateTradeablesContainer (
		final String eventTenor,
		final int eventCount,
		final int iTerminationDate,
		final String bank,
		final String counterParty)
		throws Exception
	{
		String currency = "USD";

		double dblAssetNumeraireDrift = 0.0;
		double dblAssetNumeraireVolatility = 0.25;
		double dblAssetNumeraireRepo = 0.0;

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

		double dblBankHazardRateInitial = 0.03;

		double dblBankSeniorRecoveryRateInitial = 0.45;

		double dblBankSubordinateRecoveryRateInitial = 0.25;

		double dblCounterPartyHazardRateInitial = 0.05;

		double dblCounterPartyRecoveryRateInitial = 0.30;

		PrimarySecurity tAsset = new PrimarySecurity (
			"AAPL",
			EntityEquityLabel.Standard (
				"AAPL",
				currency
			),
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblAssetNumeraireDrift,
					dblAssetNumeraireVolatility
				)
			),
			dblAssetNumeraireRepo
		);

		PrimarySecurity tOvernightIndex = new PrimarySecurity (
			currency + "_OVERNIGHT",
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
			currency + "_CSA",
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
		final String bank,
		final String counterParty)
		throws Exception
	{
		JulianDate terminationDate = spotDate;
		double dblBankHazardRateDrift = 0.002;
		double dblBankHazardRateVolatility = 0.20;
		double dblBankRecoveryRateDrift = 0.002;
		double dblBankRecoveryRateVolatility = 0.02;
		double dblCounterPartyHazardRateDrift = 0.002;
		double dblCounterPartyHazardRateVolatility = 0.30;
		double dblCounterPartyRecoveryRateDrift = 0.002;
		double dblCounterPartyRecoveryRateVolatility = 0.02;

		for (int i = 0; i < periodCount; ++i)
			terminationDate = terminationDate.addTenor (periodTenor);

		return MarketVertexGenerator.PeriodHorizon (
			spotDate.julian(),
			periodTenor,
			periodCount,
			GenerateTradeablesContainer (
				periodTenor,
				periodCount,
				terminationDate.julian(),
				bank,
				counterParty
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
							dblBankRecoveryRateDrift,
							dblBankRecoveryRateVolatility
						)
					)
				),
				null,
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
	}

	private static final void UDTDump (
		final String strHeader,
		final JulianDate[] adtVertexNode,
		final UnivariateDiscreteThin[] aUDT)
		throws Exception
	{
		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (strHeader);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|       DATE      =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + "  |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		 strDump = "\t|     AVERAGE     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].average(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MAXIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].maximum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MINIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].minimum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|      ERROR      =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].error(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
	}

	private static final void UDTDump (
		final String strHeader,
		final UnivariateDiscreteThin udt)
		throws Exception
	{
		System.out.println (
			strHeader +
			FormatUtil.FormatDouble (udt.average(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.maximum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.minimum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.error(), 3, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String bank = "CITI";
		String counterParty = "AIG";
		String currency = "USD";

		/*
		 * Evolution Control
		 */

		int iNumPath = 100000;
		int eventCount = 10;
		String eventTenor = "6M";
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

		JulianDate dtSpot = DateUtil.Today();

		JulianDate terminationDate = dtSpot;

		for (int i = 0; i < eventCount; ++i)
			terminationDate = terminationDate.addTenor (eventTenor);

		final int maturityDate = terminationDate.julian();

		/*
		 * Group Settings
		 */

		double dblBankThreshold = -0.1;
		double dblCounterPartyThreshold = 0.1;

		PathSimulator fixFloatPathSimulator = new PathSimulator (
			iNumPath,
			ConstructMarketVertexGenerator (
				dtSpot,
				eventTenor,
				eventCount,
				currency,
				bank,
				counterParty
			),
			AdjustmentDigestScheme.ALBANESE_ANDERSEN_METRICS_POINTER,
			PositionGroupContainer.Solo (
				new PositionGroup (
					new PositionSchemaSpecification (
						"POSGRPSPEC1",
						"POSGRPSPEC1",
						PositionGroupSpecification.FixedThreshold (
							"FIXEDTHRESHOLD",
							dblCounterPartyThreshold,
							dblBankThreshold,
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
								bank,
								currency
							),
							EntityHazardLabel.Standard (
								counterParty,
								currency
							),
							EntityRecoveryLabel.Senior (
								bank,
								currency
							),
							EntityRecoveryLabel.Senior (
								counterParty,
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
								bank,
								currency
							),
							EntityFundingLabel.Senior (
								counterParty,
								currency
							),
							EntityFundingLabel.Subordinate (
								bank,
								currency
							)
						)
					),
					new FixFloatBaselDuration (maturityDate)
				)
			)
		);

		MarketVertex initialMarketVertex = MarketVertex.StartUp (
			dtSpot,
			0.000, 				// dblPortfolioValueInitial
			1.000, 				// dblOvernightNumeraireInitial
			1.000, 				// dblCSANumeraire
			0.015, 				// dblBankHazardRate
			0.400, 				// dblBankRecoveryRate
			0.015 / (1 - 0.40), // dblBankFundingSpread
			0.030, 				// dblCounterPartyHazardRate
			0.300, 				// dblCounterPartyRecoveryRate
			0.030 / (1 - 0.30) 	// dblCounterPartyFundingSpread
		);

		PathExposureAdjustment[] pathExposureAdjustmentArray = new PathExposureAdjustment[iNumPath];

		CorrelatedPathVertexDimension correlatedPathVertexDimension = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			correlationMatrix,
			eventCount,
			iNumPath,
			true,
			null
		);

		for (int i = 0; i < iNumPath; ++i)
		{
			pathExposureAdjustmentArray[i] = fixFloatPathSimulator.singleTrajectory (
				initialMarketVertex,
				Matrix.Transpose (correlatedPathVertexDimension.straightPathVertexRd().flatform())
			);
		}

		ExposureAdjustmentAggregator eaa = new ExposureAdjustmentAggregator (pathExposureAdjustmentArray);

		ExposureAdjustmentDigest ead = eaa.digest();

		System.out.println();

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE                                                                                |",
			eaa.vertexDates(),
			ead.collateralizedExposure()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE                                                                               |",
			eaa.vertexDates(),
			ead.uncollateralizedExposure()
		);

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE PV                                                                             |",
			eaa.vertexDates(),
			ead.collateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE PV                                                                            |",
			eaa.vertexDates(),
			ead.uncollateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED POSITIVE EXPOSURE PV                                                                        |",
			eaa.vertexDates(),
			ead.collateralizedPositiveExposurePV()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED POSITIVE EXPOSURE PV                                                                       |",
			eaa.vertexDates(),
			ead.uncollateralizedPositiveExposurePV()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED NEGATIVE EXPOSURE PV                                                                        |",
			eaa.vertexDates(),
			ead.collateralizedNegativeExposurePV()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED NEGATIVE EXPOSURE PV                                                                       |",
			eaa.vertexDates(),
			ead.uncollateralizedNegativeExposurePV()
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

		UDTDump (
			"\t||  UCVA  => ",
			ead.ucva()
		);

		UDTDump (
			"\t|| FTDCVA => ",
			ead.ftdcva()
		);

		UDTDump (
			"\t||   CVA  => ",
			ead.cva()
		);

		UDTDump (
			"\t||  CVACL => ",
			ead.cvacl()
		);

		UDTDump (
			"\t||   DVA  => ",
			ead.dva()
		);

		UDTDump (
			"\t||   FVA  => ",
			ead.fva()
		);

		UDTDump (
			"\t||   FDA  => ",
			ead.fda()
		);

		UDTDump (
			"\t||   FCA  => ",
			ead.fca()
		);

		UDTDump (
			"\t||   FBA  => ",
			ead.fba()
		);

		UDTDump (
			"\t||  SFVA  => ",
			ead.sfva()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  Total => ",
			ead.totalVA()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
