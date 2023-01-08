
package org.drip.sample.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
 * <i>PartitionLabels</i> partitions a string of given of lower-case English letters into as many parts as
 *  possible so that each letter appears in at most one part, and return a list of integers representing the
 *  size of these parts.
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

public class PartitionLabels
{
	static class CharStretch
	{
		public int _end = -1;
		public int _start = -1;

		@Override public String toString()
		{
			return "[" + _start + ", " + _end + "]";
		}
	}

	private static final Map<Character, CharStretch> CharacterStretchMap (
		final String word)
	{
		Map<Character, CharStretch> characterStretchMap = new HashMap<Character, CharStretch>();

		int wordLength = word.length();

		for (int charIndex = 0;
			charIndex < wordLength;
			++charIndex)
		{
			char c = word.charAt (
				charIndex
			);

			if (!characterStretchMap.containsKey (
				c
			))
			{
				CharStretch charStretch = new CharStretch();

				charStretch._start = charIndex;

				characterStretchMap.put (
					c,
					charStretch
				);
			}
			else
			{
				characterStretchMap.get (
					c
				)._end = charIndex;
			}
		}

		Set<Character> characterKeySet = new HashSet<Character>();

		characterKeySet.addAll (
			characterStretchMap.keySet()
		);

		for (char c : characterKeySet)
		{
			CharStretch charStretch = characterStretchMap.get (
				c
			);

			if (-1 == charStretch._end)
			{
				characterStretchMap.remove (
					c,
					charStretch
				);
			}
		}

		return characterStretchMap;
	}

	private static final boolean StretchesOverlap (
		final CharStretch charStretch1,
		final CharStretch charStretch2)
	{
		return charStretch1._start < charStretch2._start && charStretch1._end > charStretch2._start;
	}

	private static final CharStretch MergeStretchPair (
		final CharStretch charStretch1,
		final CharStretch charStretch2)
	{
		CharStretch mergedStretch = new CharStretch();

		mergedStretch._end = charStretch1._end > charStretch2._end ? charStretch1._end : charStretch2._end;
		mergedStretch._start = charStretch1._start;
		return mergedStretch;
	}

	private static final TreeMap<Integer, CharStretch> SortedCharacterStretchMap (
		final Map<Character, CharStretch> characterStretchMap)
	{
		TreeMap<Integer, CharStretch> sortedCharacterStretchMap = new TreeMap<Integer, CharStretch>();

		for (Map.Entry<Character, CharStretch> characterStretchMapEntry : characterStretchMap.entrySet())
		{
			CharStretch charStretch = characterStretchMapEntry.getValue();

			sortedCharacterStretchMap.put (
				charStretch._start,
				charStretch
			);
		}

		return sortedCharacterStretchMap;
	}

	private static final List<Integer> SegmentIndexList (
		final String word)
	{
		Map<Character, CharStretch> characterStretchMap = CharacterStretchMap (
			word
		);

		TreeMap<Integer, CharStretch> sortedCharacterStretchMap = SortedCharacterStretchMap (
			characterStretchMap
		);

		System.out.println (sortedCharacterStretchMap);

		CharStretch currentCharStretch = null;
		CharStretch previousCharStretch = null;

		TreeMap<Integer, CharStretch> mergedCharacterStretchMap = new TreeMap<Integer, CharStretch>();

		for (Map.Entry<Integer, CharStretch> sortedCharacterStretchMapEntry :
			sortedCharacterStretchMap.entrySet())
		{
			currentCharStretch = sortedCharacterStretchMapEntry.getValue();

			if (null != previousCharStretch)
			{
				if (StretchesOverlap (
					previousCharStretch,
					currentCharStretch
				))
				{
					currentCharStretch = MergeStretchPair (
						previousCharStretch,
						currentCharStretch
					);

					mergedCharacterStretchMap.remove (
						previousCharStretch._start,
						previousCharStretch
					);
				}
			}

			mergedCharacterStretchMap.put (
				currentCharStretch._start,
				currentCharStretch
			);

			previousCharStretch = currentCharStretch;
		}

		System.out.println (mergedCharacterStretchMap);

		List<Integer> segmentIndexList = new ArrayList<Integer>();

		return segmentIndexList;
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
		SegmentIndexList ("ababcbacadefegdehijhklij");
	}
}
