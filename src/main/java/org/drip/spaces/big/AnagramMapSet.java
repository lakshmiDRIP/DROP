
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>AnagramMapSet</i> makes a Set of all the Anagram Groups in the Word Group.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big">Big</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AnagramMapSet
{
	private java.util.Map<java.lang.String, java.util.Set<java.lang.String>> _anagramMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.util.Set<java.lang.String>>();

	/**
	 * Construct an AnagramMapSet Instance from the specified Sentence
	 * 
	 * @param sentence The Sentence
	 * 
	 * @return The AnagramMapSet Instance
	 */

	public static final AnagramMapSet FromSentence (
		final java.lang.String sentence)
	{
		java.lang.String[] wordArray = sentence.split (" ");

		if (null == wordArray || 0 == wordArray.length)
		{
			return null;
		}

		AnagramMapSet anagramMapSet = new AnagramMapSet();

		for (java.lang.String word : wordArray)
		{
			if (!anagramMapSet.update (word))
			{
				return null;
			}
		}

		return anagramMapSet;
	}

	/**
	 * Empty AnagramMapSet Constructor
	 */

	public AnagramMapSet()
	{
	}

	/**
	 * Update the Anagram Map Set One Word at a Time
	 * 
	 * @param word The Word to Update
	 * 
	 * @return TRUE - Update Successful
	 */

	public boolean update (
		final java.lang.String word)
	{
		if (null == word || word.isEmpty())
		{
			return false;
		}

		char[] anagramCharArray = word.toLowerCase().toCharArray();

		java.util.Arrays.sort (anagramCharArray);

		java.lang.String anagramKey = new java.lang.String (anagramCharArray);

		if (_anagramMap.containsKey (anagramKey))
		{
			_anagramMap.get (anagramKey).add (word);
		}
		else
		{
			java.util.Set<java.lang.String> anagramSet = new java.util.HashSet<java.lang.String>();

			anagramSet.add (word);

			_anagramMap.put (
				anagramKey,
				anagramSet
			);
		}

		return true;
	}

	/**
	 * Return the Map of Anagrams
	 * 
	 * @return The Map of Anagrams
	 */

	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> anagramMap()
	{
		return _anagramMap;
	}
}
