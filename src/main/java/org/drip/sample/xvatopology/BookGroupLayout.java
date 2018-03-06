
package org.drip.sample.xvatopology;

import java.util.Map;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.StringUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.OvernightLabel;
import org.drip.xva.proto.CollateralGroupSpecification;
import org.drip.xva.proto.PositionGroupSpecification;
import org.drip.xva.settings.BrokenDateScheme;
import org.drip.xva.settings.CloseOutScheme;
import org.drip.xva.settings.PositionReplicationScheme;
import org.drip.xva.topology.CollateralGroup;
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
 * BookGroupLayout represents the Directed Graph of all the Encompassing Book Groups. The References are:
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

public class BookGroupLayout
{

	private static final String ThreeDigitRandom()
	{
		return FormatUtil.FormatDouble (Math.random(), 3, 0, 1000.);
	}

	private static final PositionGroup[] PositionGroupArray (
		final int count)
		throws Exception
	{
		PositionGroup[] positionGroupArray = new PositionGroup[count];

		for (int i = 0; i < count; ++i)
		{
			positionGroupArray[i] = new PositionGroup (
				StringUtil.GUID(),
				"POSITIONGROUP" + ThreeDigitRandom(),
				new PositionGroupSpecification (
					StringUtil.GUID(),
					"POSITIONGROUPSPEC"
				)
			);
		}

		return positionGroupArray;
	}

	private static final CollateralGroup CollGroup (
		final String currency,
		final int count)
		throws Exception
	{
		CollateralGroup collateralGroup = new CollateralGroup (
			StringUtil.GUID(),
			"COLLATERALGROUP" + ThreeDigitRandom(),
			CollateralGroupSpecification.ZeroThreshold (
				"COLLATERALGROUPSPEC",
				OvernightLabel.Create (currency),
				CSALabel.ISDA (currency),
				PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX,
				BrokenDateScheme.SQUARE_ROOT_OF_TIME,
				0.,
				CloseOutScheme.ISDA_92
			)
		);

		PositionGroup[] positionGroupArray = PositionGroupArray (count);

		for (PositionGroup positionGroup : positionGroupArray)
			collateralGroup.addPositionGroup (positionGroup);

		return collateralGroup;
	}

	private static final void DumpCollateralGroup (
		final CollateralGroup collateralGroup)
	{
		System.out.println (
			"\t" +
			collateralGroup.name() + " | " +
			collateralGroup.id() + " ||"
		);

		for (Map.Entry<String, PositionGroup> positionGroupEntry :
			collateralGroup.positionGroupMap().entrySet())
		{
			PositionGroup positionGroup = positionGroupEntry.getValue();

			System.out.println (
				"\t\t" +
				positionGroup.name() + " | " +
				positionGroup.id() + " ||"
			);
		}
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String currency = "USD";
		int positionGroupCount = 3;

		CollateralGroup collateralGroup = CollGroup (
			currency,
			positionGroupCount
		);

		DumpCollateralGroup (collateralGroup);

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
