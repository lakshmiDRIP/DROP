
package org.drip.sample.xvatopology;

import java.util.Map;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.StringUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.EntityFundingLabel;
import org.drip.state.identifier.EntityHazardLabel;
import org.drip.state.identifier.EntityRecoveryLabel;
import org.drip.state.identifier.OvernightLabel;
import org.drip.xva.proto.CollateralGroupSpecification;
import org.drip.xva.proto.CreditDebtGroupSpecification;
import org.drip.xva.proto.FundingGroupSpecification;
import org.drip.xva.proto.PositionGroupSpecification;
import org.drip.xva.settings.BrokenDateScheme;
import org.drip.xva.settings.CloseOutScheme;
import org.drip.xva.settings.PositionReplicationScheme;
import org.drip.xva.topology.Adiabat;
import org.drip.xva.topology.AdiabatMarketParams;
import org.drip.xva.topology.CollateralGroup;
import org.drip.xva.topology.CreditDebtGroup;
import org.drip.xva.topology.FundingGroup;
import org.drip.xva.topology.PositionGroup;

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
 * BookLatentStateMap represents the Latent State Map across all the Book Groups. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 *  	Collateral Trading <b>https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301</b><br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BookLatentStateMap
{

	private static final String ThreeDigitRandom()
	{
		return FormatUtil.FormatDouble (Math.random(), 3, 0, 1000.);
	}

	private static final PositionGroup[] PositionGroupArray (
		final PositionGroupSpecification positionGroupSpecification,
		final int count)
		throws Exception
	{
		PositionGroup[] positionGroupArray = new PositionGroup[count];

		for (int i = 0; i < count; ++i)
		{
			positionGroupArray[i] = new PositionGroup (
				StringUtil.GUID(),
				"POSITIONGROUP" + ThreeDigitRandom(),
				positionGroupSpecification
			);
		}

		return positionGroupArray;
	}

	private static final CollateralGroup[] CollateralGroupArray (
		final PositionGroupSpecification positionGroupSpecification,
		final CollateralGroupSpecification collateralGroupSpecification,
		final String currency,
		final int collateralGroupCount,
		final int positionGroupPerCollateralGroup)
		throws Exception
	{
		CollateralGroup[] collateralGroupArray = new CollateralGroup[collateralGroupCount];

		for (int i = 0; i < collateralGroupCount; ++i)
		{
			collateralGroupArray[i] = new CollateralGroup (
				StringUtil.GUID(),
				"COLLATERALGROUP" + ThreeDigitRandom(),
				collateralGroupSpecification
			);

			PositionGroup[] positionGroupArray = PositionGroupArray (
				positionGroupSpecification,
				positionGroupPerCollateralGroup
			);

			for (PositionGroup positionGroup : positionGroupArray)
				collateralGroupArray[i].addPositionGroup (positionGroup);
		}

		return collateralGroupArray;
	}

	private static final CreditDebtGroup[] CreditDebtGroupArray (
		final PositionGroupSpecification positionGroupSpecification,
		final CollateralGroupSpecification collateralGroupSpecification,
		final CreditDebtGroupSpecification creditDebtGroupSpecification,
		final int creditDebtGroupCount,
		final String currency,
		final String bank,
		final String counterParty,
		final int collateralGroupPerCreditDebtGroup,
		final int positionGroupPerCollateralGroup)
		throws Exception
	{
		CreditDebtGroup[] creditDebtGroupArray = new CreditDebtGroup[creditDebtGroupCount];

		for (int i = 0; i < creditDebtGroupCount; ++i)
		{
			creditDebtGroupArray[i] = new CreditDebtGroup (
				StringUtil.GUID(),
				"CREDITDEBTGROUP" + ThreeDigitRandom(),
				creditDebtGroupSpecification
			);

			CollateralGroup[] collateralGroupArray = CollateralGroupArray (
				positionGroupSpecification,
				collateralGroupSpecification,
				currency,
				collateralGroupPerCreditDebtGroup,
				positionGroupPerCollateralGroup
			);

			for (int j = 0; j < collateralGroupArray.length; ++j)
			{
				creditDebtGroupArray[i].addCollateralGroup (collateralGroupArray[j]);
			}
		}

		return creditDebtGroupArray;
	}

	private static final Adiabat BookTopology (
		final PositionGroupSpecification positionGroupSpecification,
		final CollateralGroupSpecification collateralGroupSpecification,
		final CreditDebtGroupSpecification creditDebtGroupSpecification,
		final FundingGroupSpecification fundingGroupSpecification,
		final int fundingGroupCount,
		final String currency,
		final String bank,
		final String counterParty,
		final int creditDebtGroupPerFundingGroup,
		final int collateralGroupPerCreditDebtGroup,
		final int positionGroupPerCollateralGroup)
		throws Exception
	{
		Adiabat bookGraph = new Adiabat (
			StringUtil.GUID(),
			"BOOKGRAPH"
		);

		FundingGroup[] fundingGroupArray = new FundingGroup[fundingGroupCount];

		for (int i = 0; i < fundingGroupCount; ++i)
		{
			fundingGroupArray[i] = new FundingGroup (
				StringUtil.GUID(),
				"FUNDINGGROUP" + ThreeDigitRandom(),
				fundingGroupSpecification
			);

			CreditDebtGroup[] creditDebtGroupArray = CreditDebtGroupArray (
				positionGroupSpecification,
				collateralGroupSpecification,
				creditDebtGroupSpecification,
				creditDebtGroupPerFundingGroup,
				currency,
				bank,
				counterParty,
				collateralGroupPerCreditDebtGroup,
				positionGroupPerCollateralGroup
			);

			for (int j = 0; j < creditDebtGroupArray.length; ++j)
			{
				fundingGroupArray[i].addCreditDebtGroup (creditDebtGroupArray[j]);
			}
		}

		for (int k = 0; k < fundingGroupArray.length; ++k)
		{
			bookGraph.addFundingGroup (fundingGroupArray[k]);
		}

		return bookGraph;
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String bank = "NOM";
		String currency = "USD";
		String counterParty = "BAC";
		int positionGroupPerCollateralGroup = 3;
		int collateralGroupPerCreditDebtGroup = 3;
		int creditDebtGroupCountPerFundingGroup = 3;
		int fundingGroupCount = 3;

		Adiabat bookGraph = BookTopology (
			PositionGroupSpecification.ZeroThreshold (
				"POSITIONGROUPSPEC",
				PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX,
				BrokenDateScheme.SQUARE_ROOT_OF_TIME,
				0.,
				CloseOutScheme.ISDA_92
			),
			new CollateralGroupSpecification (
				StringUtil.GUID(),
				"COLLATERALGROUPSPEC",
				OvernightLabel.Create (currency),
				CSALabel.ISDA (currency)
			),
			new CreditDebtGroupSpecification (
				StringUtil.GUID(),
				"CREDITDEBTGROUPSPEC",
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
				EntityRecoveryLabel.Subordinate (
					bank,
					currency
				),
				true,
				true
			),
			new FundingGroupSpecification (
				StringUtil.GUID(),
				"FUNDINGGROUPSPEC",
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
			),
			fundingGroupCount,
			currency,
			bank,
			counterParty,
			creditDebtGroupCountPerFundingGroup,
			collateralGroupPerCreditDebtGroup,
			positionGroupPerCollateralGroup
		);

		AdiabatMarketParams bookMarketParams = bookGraph.marketParams();

		System.out.println ("\t||----------------||");

		System.out.println ("\t||   OVERNIGHT    ||");

		System.out.println ("\t||----------------||");

		for (Map.Entry<String, OvernightLabel> overnightLabelEntry :
			bookMarketParams.overnightLabelMap().entrySet())
		{
			System.out.println ("\t|| " + overnightLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||----------------||");

		System.out.println();

		System.out.println ("\t||-----------||");

		System.out.println ("\t||    CSA    ||");

		System.out.println ("\t||-----------||");

		for (Map.Entry<String, CSALabel> csaLabelEntry : bookMarketParams.csaLabelMap().entrySet())
		{
			System.out.println ("\t|| " + csaLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||-----------||");

		System.out.println();

		System.out.println ("\t||----------||");

		System.out.println ("\t|| BANK HAZ ||");

		System.out.println ("\t||----------||");

		for (Map.Entry<String, EntityHazardLabel> bankHazardLabelEntry :
			bookMarketParams.dealerHazardLabelMap().entrySet())
		{
			System.out.println ("\t|| " + bankHazardLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||----------||");

		System.out.println();

		System.out.println ("\t||----------||");

		System.out.println ("\t|| CPTY HAZ ||");

		System.out.println ("\t||----------||");

		for (Map.Entry<String, EntityHazardLabel> counterPartyHazardLabelEntry :
			bookMarketParams.clientHazardLabelMap().entrySet())
		{
			System.out.println ("\t|| " + counterPartyHazardLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||----------||");

		System.out.println();

		System.out.println ("\t||------------------||");

		System.out.println ("\t|| BANK SR RECOVERY ||");

		System.out.println ("\t||------------------||");

		for (Map.Entry<String, EntityRecoveryLabel> bankSeniorRecoveryLabelEntry :
			bookMarketParams.dealerSeniorRecoveryLabelMap().entrySet())
		{
			System.out.println ("\t|| " + bankSeniorRecoveryLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||------------------||");

		System.out.println();

		System.out.println ("\t||------------------||");

		System.out.println ("\t||  CPTY RECOVERY   ||");

		System.out.println ("\t||------------------||");

		for (Map.Entry<String, EntityRecoveryLabel> counterPartyRecoveryLabelEntry :
			bookMarketParams.clientRecoveryLabelMap().entrySet())
		{
			System.out.println ("\t|| " + counterPartyRecoveryLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||------------------||");

		System.out.println();

		System.out.println ("\t||-----------------------||");

		System.out.println ("\t|| BANK SUBORD RECOVERY  ||");

		System.out.println ("\t||-----------------------||");

		for (Map.Entry<String, EntityRecoveryLabel> bankSubordinateRecoveryLabelEntry :
			bookMarketParams.dealerSubordinateRecoveryLabelMap().entrySet())
		{
			System.out.println ("\t|| " + bankSubordinateRecoveryLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||-----------------------||");

		System.out.println();

		System.out.println ("\t||------------------||");

		System.out.println ("\t|| BANK SR FUNDING  ||");

		System.out.println ("\t||------------------||");

		for (Map.Entry<String, EntityFundingLabel> bankSeniorFundingLabelEntry :
			bookMarketParams.dealerSeniorFundingLabelMap().entrySet())
		{
			System.out.println ("\t|| " + bankSeniorFundingLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||------------------||");

		System.out.println();

		System.out.println ("\t||------------------||");

		System.out.println ("\t||   CPTY FUNDING   ||");

		System.out.println ("\t||------------------||");

		for (Map.Entry<String, EntityFundingLabel> counterPartyFundingLabelEntry :
			bookMarketParams.clientFundingLabelMap().entrySet())
		{
			System.out.println ("\t|| " + counterPartyFundingLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||------------------||");

		System.out.println();

		System.out.println ("\t||-----------------------||");

		System.out.println ("\t||  BANK SUBORD FUNDING  ||");

		System.out.println ("\t||-----------------------||");

		for (Map.Entry<String, EntityFundingLabel> bankSubordinateFundingLabelEntry :
			bookMarketParams.dealerSubordinateFundingLabelMap().entrySet())
		{
			System.out.println ("\t|| " + bankSubordinateFundingLabelEntry.getValue().fullyQualifiedName() + " ||");
		}

		System.out.println ("\t||-----------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
