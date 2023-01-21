
package org.drip.sample.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>TopNCompetitors</i> returns a list of strings representing a company's top N competitors in order of
 * most frequently mentioned to least frequent.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/algo/README.md">C<sup>x</sup> R<sup>x</sup> In-Place Manipulation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TopNCompetitors
{

	private static final String ContainedCompetitor (
		final List<String> competitorList,
		final String review)
	{
		for (String competitor : competitorList)
		{
			if (review.contains (
				competitor.toLowerCase()
			))
			{
				return competitor;
			}
		}

		return "";
	}

	private static final Map<String, Integer> CompetitorCountMap (
		final List<String> competitorList,
		final List<String> reviewList)
	{
		Map<String, Integer> competitorCountMap = new HashMap<String, Integer>();

		for (String review : reviewList)
		{
			String containedCompetitor = ContainedCompetitor (
				competitorList,
				review.toLowerCase()
			);

			competitorCountMap.put (
				containedCompetitor,
				competitorCountMap.containsKey (
					containedCompetitor
				) ? competitorCountMap.get (
					containedCompetitor
				) + 1 : 1
			);
		}

		return competitorCountMap;
	}

	private static final List<String> OrderByDecreasingFrequency (
		final int topNCompetitors,
		final List<String> competitorList,
		final List<String> reviewList)
	{
		Map<String, Integer> competitorCountMap = CompetitorCountMap (
			competitorList,
			reviewList
		);

		TreeMap<Integer, List<String>> orderedCompetitorCountMap = new TreeMap<Integer, List<String>>();

		for (Map.Entry<String, Integer> competitorCountEntry : competitorCountMap.entrySet())
		{
			int competitorCount = competitorCountEntry.getValue();

			if (orderedCompetitorCountMap.containsKey (
				competitorCount
			))
			{
				orderedCompetitorCountMap.get (
					competitorCount
				).add (
					competitorCountEntry.getKey()
				);
			}
			else
			{
				List<String> orderedCompetitorList = new ArrayList<String>();

				orderedCompetitorList.add (
					competitorCountEntry.getKey()
				);

				orderedCompetitorCountMap.put (
					competitorCount,
					orderedCompetitorList
				);
			}
		}

		Set<Integer> orderedCompetitorCountKeySet = orderedCompetitorCountMap.descendingKeySet();

		int topNCompetitorListSize = 0;

		List<String> topNCompetitorList = new ArrayList<String>();

		for (Integer orderedCompetitorCountKey : orderedCompetitorCountKeySet)
		{
			if (topNCompetitorListSize == topNCompetitors)
			{
				break;
			}

			List<String> orderedCompetitorList = orderedCompetitorCountMap.get (
				orderedCompetitorCountKey
			);

			for (String orderedCompetitor :orderedCompetitorList)
			{
				topNCompetitorList.add (
					orderedCompetitor
				);

				if (++topNCompetitorListSize == topNCompetitors)
				{
					break;
				}
			}
		}

		return topNCompetitorList;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		int topNCompetitors = 2;

		List<String> competitorList = new ArrayList<String>();

		competitorList.add (
			"newshop"
		);

		competitorList.add (
			"shopnow"
		);

		competitorList.add (
			"afashion"
		);

		competitorList.add (
			"fashionbeats"
		);

		competitorList.add (
			"mymarket"
		);

		competitorList.add (
			"tcellular"
		);

		List<String> reviewList = new ArrayList<String>();

		reviewList.add (
			"newshop is providing good services in the city; everyone should use newshop"
		);

		reviewList.add (
			"best services by newshop"
		);

		reviewList.add (
			"fashionbeats has great services in the city"
		);

		reviewList.add (
			"I am proud to have fashionbeats"
		);

		reviewList.add (
			"mymarket has awesome services"
		);

		reviewList.add (
			"Thanks Newshop for the quick delivery"
		);

		List<String> decreasingFrequencyCompetitorOrder = OrderByDecreasingFrequency (
			topNCompetitors,
			competitorList,
			reviewList
		);

		System.out.println (
			decreasingFrequencyCompetitorOrder
		);
	}
}
