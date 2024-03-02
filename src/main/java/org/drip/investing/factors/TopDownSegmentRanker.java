
package org.drip.investing.factors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>TopDownSegmentRanker</i> implements the Top-Down Sliced Ranking the Factor Portfolio Constituents. The
 *  References are:
 *
 *	<br><br>
 * <ul>
 * 	<li>
 *  	Blitz, D., M. X. Hanauer, M. Vidojevic, and P. van Vliet (2018): Five-Factors with the Five-Factor
 *  		Model <i>Journal of Portfolio Management</i> <b>44 (4)</b> 71-78
 * 	</li>
 * 	<li>
 *  	Fama, E. F., and K. R. French (1992): The Cross-section of Expected Stock Returns <i>Journal of
 *  		Finance</i> <b>47 (2)</b> 427-465
 * 	</li>
 * 	<li>
 *  	Fama, E. F., and K. R. French (2015): A Five-Factor Asset Pricing Model <i>Journal of Financial
 *  		Economics</i> <b>116 (1)</b> 1-22
 * 	</li>
 * 	<li>
 *  	Foye, J. (2018): Testing Alternative Versions of the Fama-French Five-Factor Model in the UK <i>Risk
 *  		Management</i> <b>20 (2)</b> 167-183
 * 	</li>
 * 	<li>
 *  	Wikipedia (2024): Fama–French three-factor model
 *  		<i>https://en.wikipedia.org/wiki/Fama%E2%80%93French_three-factor_model</i>
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/README.md">Factor/Style Based Quantitative Investing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/factors/README.md">Factor Types, Characteristics, and Constitution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TopDownSegmentRanker implements FactorPortfolioRanker
{
	private boolean _flipBottomSign = false;
	private int _topComponentCutoffCount = Integer.MIN_VALUE;
	private int _bottomComponentCutoffCount = Integer.MIN_VALUE;

	/**
	 * Build a Full-Suite Top/Bottom Portfolio Ranker
	 * 
	 * @param flipBottomSign TRUE - Signs of the Bottom Components must be Flipped
	 * 
	 * @return Full-Suite Top/Bottom Portfolio Ranker
	 */

	public static final TopDownSegmentRanker FullSuite (
		final boolean flipBottomSign)
	{
		try {
			return new TopDownSegmentRanker (Integer.MIN_VALUE, Integer.MIN_VALUE, flipBottomSign);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * TopDownSegmentRanker Constructor
	 * 
	 * @param topComponentCutoffCount The Top Component Cutoff Count
	 * @param bottomComponentCutoffCount The Bottom Component Cutoff Count
	 * @param flipBottomSign TRUE - Signs of the Bottom Components must be Flipped
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public TopDownSegmentRanker (
		final int topComponentCutoffCount,
		final int bottomComponentCutoffCount,
		final boolean flipBottomSign)
		throws Exception
	{
		_flipBottomSign = flipBottomSign;
		_topComponentCutoffCount = topComponentCutoffCount;
		_bottomComponentCutoffCount = bottomComponentCutoffCount;
	}

	/**
	 * Retrieve the Top Component Cutoff Count
	 * 
	 * @return The Top Component Cutoff Count
	 */

	public int topComponentCutoffCount()
	{
		return _topComponentCutoffCount;
	}

	/**
	 * Retrieve the Bottom Component Cutoff Count
	 * 
	 * @return The Bottom Component Cutoff Count
	 */

	public int bottomComponentCutoffCount()
	{
		return _bottomComponentCutoffCount;
	}

	/**
	 * Indicate if the Signs of the Bottom Components must be Flipped
	 * 
	 * @return TRUE - Signs of the Bottom Components must be Flipped
	 */

	public boolean flipBottomSign()
	{
		return _flipBottomSign;
	}

	/**
	 * Generate the Map of Ranked Factor Components from the Input Factor Component Loading Map
	 * 
	 * @param factorComponentLoadingMap Input Factor Component Loading Map
	 * 
	 * @return The Map of Ranked Factor Components
	 */

	@Override public Map<String, FactorComponentLoading> rank (
		final Map<String, FactorComponentLoading> factorComponentLoadingMap)
	{
		if (null == factorComponentLoadingMap) {
			return factorComponentLoadingMap;
		}

		int factorComponentCount = factorComponentLoadingMap.size();

		if (0 == factorComponentCount) {
			return factorComponentLoadingMap;
		}

		int topComponentCount = 0;
		int bottomComponentCount = 0;
		boolean topComponentLimitReached = false;
		boolean bottomComponentLimitReached = false;
		int topComponentCutoffCount = Integer.MIN_VALUE == _topComponentCutoffCount ?
			factorComponentCount / 2 : _topComponentCutoffCount;
		int bottomComponentCutoffCount = Integer.MIN_VALUE == _bottomComponentCutoffCount ?
			factorComponentCount / 2 : _bottomComponentCutoffCount;

		if (topComponentCutoffCount + bottomComponentCutoffCount > factorComponentCount) {
			return factorComponentLoadingMap;
		}

		TreeMap<Double, List<FactorComponentLoading>> sortedFactorComponentLoadingMap =
			new TreeMap<Double, List<FactorComponentLoading>>();

		for (FactorComponentLoading factorComponentLoading : factorComponentLoadingMap.values()) {
			double factorScore = factorComponentLoading.score();

			if (sortedFactorComponentLoadingMap.containsKey (factorScore)) {
				sortedFactorComponentLoadingMap.get (factorScore).add (factorComponentLoading);
			} else {
				List<FactorComponentLoading> factorComponentLoadingList =
					new ArrayList<FactorComponentLoading>();

				factorComponentLoadingList.add (factorComponentLoading);

				sortedFactorComponentLoadingMap.put (factorScore, factorComponentLoadingList);
			}
		}

		List<FactorComponentLoading> topFactorComponentLoadingList = new ArrayList<FactorComponentLoading>();

		for (double factorScore : sortedFactorComponentLoadingMap.descendingKeySet()) {
			if (!topComponentLimitReached) {
				for (FactorComponentLoading factorComponentLoading :
					sortedFactorComponentLoadingMap.get (factorScore))
				{
					while (!topComponentLimitReached) {
						topFactorComponentLoadingList.add (factorComponentLoading);

						topComponentLimitReached = ++topComponentCount > topComponentCutoffCount;
					}
				}
			}
		}

		List<FactorComponentLoading> bottomFactorComponentLoadingList =
			new ArrayList<FactorComponentLoading>();

		for (double factorScore : sortedFactorComponentLoadingMap.keySet()) {
			if (!bottomComponentLimitReached) {
				for (FactorComponentLoading factorComponentLoading :
					sortedFactorComponentLoadingMap.get (factorScore))
				{
					while (!bottomComponentLimitReached) {
						bottomFactorComponentLoadingList.add (factorComponentLoading);

						bottomComponentLimitReached = ++bottomComponentCount > bottomComponentCutoffCount;
					}
				}
			}
		}

		Map<String, FactorComponentLoading> rankedFactorComponentLoadingMap =
			new HashMap<String, FactorComponentLoading>();

		for (FactorComponentLoading factorComponentLoading : topFactorComponentLoadingList) {
			rankedFactorComponentLoadingMap.put (factorComponentLoading.assetID(), factorComponentLoading);
		}

		for (FactorComponentLoading factorComponentLoading : bottomFactorComponentLoadingList) {
			if (_flipBottomSign && !factorComponentLoading.flipWeightSign()) {
				return null;
			}

			rankedFactorComponentLoadingMap.put (factorComponentLoading.assetID(), factorComponentLoading);
		}

		return rankedFactorComponentLoadingMap;
	}
}
