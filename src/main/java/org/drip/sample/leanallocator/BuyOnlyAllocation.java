
package org.drip.sample.leanallocator;

import java.util.Map;

import org.drip.measure.crng.RdRandomSequence;
import org.drip.portfolioconstruction.lean.FullSequenceAllocationDiagnostics;
import org.drip.portfolioconstruction.lean.FullSequenceAllocator;
import org.drip.portfolioconstruction.lean.HoldingsContainer;
import org.drip.portfolioconstruction.lean.PostProcessorSettings;
import org.drip.portfolioconstruction.lean.RandomizedOptimizer;
import org.drip.portfolioconstruction.lean.TradesContainer;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
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
 * <i>BuyOnlyAllocation</i> generates an Optimal Buy-Only Portfolio from the Initial Holdings.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/lean/README.md">"Lean" Portfolio Construction Utilities Suite</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BuyOnlyAllocation
{

	private static final int ScaledMarketValue (
		final double scale)
	{
		return (int) (Math.random() * scale);
	}

	private static final HoldingsContainer RandomHoldings (
		final int size,
		final double scale,
		final double cash)
		throws Exception
	{
		HoldingsContainer holdingsContainer = new HoldingsContainer();

		for (int i = 0; i < size; ++i) {
			holdingsContainer.setAsset (
				RdRandomSequence.RandomCUSIP(),
				100. * ScaledMarketValue (scale)
			);
		}

		return holdingsContainer.setCashValue (cash) ? holdingsContainer : null;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int assetCount = 10;
		double scale = 1000.;
		double startingCashAssetEquivalent = 3.;

		HoldingsContainer startingHoldings = RandomHoldings (
			assetCount,
			scale,
			startingCashAssetEquivalent * scale * 100.
		);

		FullSequenceAllocator fullSequenceAllocator = new FullSequenceAllocator (
			new RandomizedOptimizer (scale),
			PostProcessorSettings.BuyOnly(),
			true
		);

		FullSequenceAllocationDiagnostics fullSequenceAllocationDiagnostics = 
			(FullSequenceAllocationDiagnostics) fullSequenceAllocator.allocate (startingHoldings);

		HoldingsContainer preFilteredHoldings = fullSequenceAllocationDiagnostics.preFilteredHoldings();

		Map<String, Double> preFilteredAssetMarketValueMap = preFilteredHoldings.assetMarketValueMap();

		Map<String, Double> startingAssetMarketValueMap = startingHoldings.assetMarketValueMap();

		HoldingsContainer endingHoldings = fullSequenceAllocationDiagnostics.endingHoldings();

		Map<String, Double> preFilteredAssetWeightMap = preFilteredHoldings.assetWeightMap();

		Map<String, Double> endingAssetMarketValueMap = endingHoldings.assetMarketValueMap();

		Map<String, Double> startingAssetWeightMap = startingHoldings.assetWeightMap();

		Map<String, Double> endingAssetWeightMap = endingHoldings.assetWeightMap();

		System.out.println (
			"\t|-------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                              BUY-ONLY ALLOCATION                              ||"
		);

		System.out.println (
			"\t|-------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t| L -> R:                                                                       ||"
		);

		System.out.println (
			"\t|   - Asset ID                                                                  ||"
		);

		System.out.println (
			"\t|   - Starting Asset Market Value                                               ||"
		);

		System.out.println (
			"\t|   - Starting Asset Weight                                                     ||"
		);

		System.out.println (
			"\t|   - Pre-filtered Asset Market Value                                           ||"
		);

		System.out.println (
			"\t|   - Pre-filtered Asset Weight                                                 ||"
		);

		System.out.println (
			"\t|   - Ending Asset Market Value                                                 ||"
		);

		System.out.println (
			"\t|   - Ending Asset Weight                                                       ||"
        );

		System.out.println (
			"\t|-------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|   Starting Holdings   ==>  Pre-filtered Holdings  ==>   Ending Holdings       ||"
		);

		System.out.println (
			"\t|-------------------------------------------------------------------------------||"
		);

		for (String assetID : endingAssetWeightMap.keySet()) {
			System.out.println (
				"\t| " + assetID.toUpperCase() + " => " +
				FormatUtil.FormatDouble (startingAssetMarketValueMap.get (assetID), 6, 0, 1.) + " |" +
				FormatUtil.FormatDouble (startingAssetWeightMap.get(assetID), 1, 5, 1.) + "  ==> " +
				FormatUtil.FormatDouble (preFilteredAssetMarketValueMap.get (assetID), 6, 0, 1.) + " |" +
				FormatUtil.FormatDouble (preFilteredAssetWeightMap.get(assetID), 1, 5, 1.) + "  ==> " +
				FormatUtil.FormatDouble (endingAssetMarketValueMap.get (assetID), 6, 0, 1.) + " |" +
				FormatUtil.FormatDouble (endingAssetWeightMap.get(assetID), 1, 5, 1.) + " ||"
			);
		}

		System.out.println (
			"\t| CASH       => " +
			FormatUtil.FormatDouble (startingHoldings.cashValue(), 6, 0, 1.) + " |" +
			FormatUtil.FormatDouble (startingHoldings.cashWeight(), 1, 5, 1.) + "  ==> " +
			FormatUtil.FormatDouble (preFilteredHoldings.cashValue(), 6, 0, 1.) + " |" +
			FormatUtil.FormatDouble (preFilteredHoldings.cashWeight(), 1, 5, 1.) + "  ==> " +
			FormatUtil.FormatDouble (endingHoldings.cashValue(), 6, 0, 1.) + " |" +
			FormatUtil.FormatDouble (endingHoldings.cashWeight(), 1, 5, 1.) + " ||"
		); 

		System.out.println (
			"\t|-------------------------------------------------------------------------------||"
		);

		System.out.println();

		System.out.println ("\t|----------------------||");

		System.out.println ("\t|      TRADE LIST      ||");

		System.out.println ("\t|----------------------||");

		System.out.println ("\t| L -> R:              ||");

		System.out.println ("\t|  - Asset ID          ||");

		System.out.println ("\t|  - Trade MV          ||");

		System.out.println ("\t|----------------------||");

		TradesContainer tradesContainer = fullSequenceAllocationDiagnostics.tradesContainer();

		Map<String, Double> assetQuantityMap = tradesContainer.assetQuantityMap();

		for (String assetID : assetQuantityMap.keySet()) {
			System.out.println (
				"\t| " + assetID.toUpperCase() + " => " +
				FormatUtil.FormatDouble (assetQuantityMap.get (assetID), 5, 0, 1.) + " ||"
			); 
		}

		System.out.println ("\t|----------------------||");

		EnvManager.TerminateEnv();
	}
}
