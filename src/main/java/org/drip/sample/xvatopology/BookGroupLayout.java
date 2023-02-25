
package org.drip.sample.xvatopology;

import java.util.Map;

import org.drip.service.common.FormatUtil;
import org.drip.service.common.StringUtil;
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
import org.drip.xva.topology.CollateralGroup;
import org.drip.xva.topology.CreditDebtGroup;
import org.drip.xva.topology.FundingGroup;
import org.drip.xva.topology.PositionGroup;

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
 * <i>BookGroupLayout</i> represents the Directed Graph of all the Encompassing Book Groups. The References
 * 	are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management,
 *  			and Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xvatopology/README.md">Aggregation Group Based XVA Topology</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BookGroupLayout
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

	private static final void DisplayBookGraph (
		final Adiabat bookGraph,
		final String displayPrefix)
	{
		System.out.println (
			displayPrefix +
			"-------------------------------------------------||"
		);

		System.out.println (
			displayPrefix +
			bookGraph.name() + " | " +
			bookGraph.id() + " ||"
		);

		System.out.println (
			displayPrefix +
			"-------------------------------------------------||"
		);

		System.out.println();

		System.out.println (
			displayPrefix +
			"----------------------------------------------------------------------------- ||"
		);

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : bookGraph.fundingGroupMap().entrySet())
		{
			FundingGroup fundingGroup = fundingGroupEntry.getValue();

			System.out.println (
				displayPrefix + "\t" +
				fundingGroup.name() + " | " +
				fundingGroup.id() + " ||"
			);

			System.out.println (
				displayPrefix +
				"----------------------------------------------------------------------------- ||"
			);

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupEntry :
				fundingGroup.creditDebtGroupMap().entrySet())
			{
				CreditDebtGroup creditDebtGroup = creditDebtGroupEntry.getValue();

				System.out.println (
					displayPrefix + "\t\t" +
					creditDebtGroup.name() + " | " +
					creditDebtGroup.id() + " ||"
				);

				for (Map.Entry<String, CollateralGroup> collateralGroupEntry :
					creditDebtGroup.collateralGroupMap().entrySet())
				{
					CollateralGroup collateralGroup = collateralGroupEntry.getValue();
	
					System.out.println (
						displayPrefix + "\t\t\t" +
						collateralGroup.name() + " | " +
						collateralGroup.id() + " ||"
					);

					for (Map.Entry<String, PositionGroup> positionGroupEntry :
						collateralGroup.positionGroupMap().entrySet())
					{
						PositionGroup positionGroup = positionGroupEntry.getValue();

						System.out.println (
							displayPrefix + "\t\t\t\t" +
							positionGroup.name() + " | " +
							positionGroup.id() + " ||"
						);
					}
				}

				System.out.println (
					displayPrefix +
					"----------------------------------------------------------------------------- ||"
				);
			}

			System.out.println (
				displayPrefix +
				"----------------------------------------------------------------------------- ||"
			);
		}
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

		String bank = "NOM";
		String currency = "USD";
		String counterParty = "BAC";
		int positionGroupPerCollateralGroup = 3;
		int collateralGroupPerCreditDebtGroup = 3;
		int creditDebtGroupCountPerFundingGroup = 3;
		int fundingGroupCount = 3;
		String displayPrefix = "\t|| ";

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

		DisplayBookGraph (
			bookGraph,
			displayPrefix
		);

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
