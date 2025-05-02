
package org.drip.service.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>WordDictionary</i> is a data structure that supports the following two operations: <code>addWord</code>
 * 	and <code>search</code>. Search can search a literal word or a regular expression string containing only
 * 	letters a-z or .. A . means it can represent any one letter. It exposes the following Functions:
 * 	<br>
 * 	<ul>
 * 
 * 	</ul>
 * 
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class WordDictionary
{
	private Set<String> _wordSet = new HashSet<String>();

	private Set<String> wordsBeginWith (
		final String prefix)
	{
		Set<String> wordsBeginWithSet = new HashSet<String>();

		if (null == prefix || prefix.isEmpty()) {
			return wordsBeginWithSet;
		}

		for (String word : _wordSet) {
			if (word.startsWith (prefix)) {
				wordsBeginWithSet.add (word);
			}
		}

		return wordsBeginWithSet;
	}

	private Set<String> wordsEndWith (
		final String suffix)
	{
		Set<String> wordsEndWithSet = new HashSet<String>();

		if (null == suffix || suffix.isEmpty()) {
			return wordsEndWithSet;
		}

		for (String word : _wordSet) {
			if (word.endsWith (suffix)) {
				wordsEndWithSet.add (word);
			}
		}

		return wordsEndWithSet;
	}

	private Set<String> wordsBeginAndEndWith (
		final String prefix,
		final String suffix)
	{
		Set<String> wordsBeginEndWithSet = wordsBeginWith (prefix);

		wordsBeginEndWithSet.addAll (wordsEndWith (suffix));

		return wordsBeginEndWithSet;
	}

	/**
	 * <i>WordDictionary</i> Constructor
	 */

	public WordDictionary()
	{
	}

	/**
	 * Retrieve the Set of Words
	 * 
	 * @return The Set of Words
	 */

	public Set<String> wordSet()
	{
		return _wordSet;
	}

	/**
	 * Add a Word to the Set
	 * 
	 * @param word The Word
	 * 
	 * @return TRUE - The Word successfully added
	 */

	public boolean addWord (
		final String word)
	{
		if (null == word || word.isEmpty()) {
			return false;
		}

		_wordSet.add (word);

		return true;
	}

	/**
	 * Return if the word is in the data structure. A word could contain the dot character '.' to represent
	 *  any one letter.
	 * 
	 * @param word The Word
	 * 
	 * @return TRUE - The Word Exists
	 */

	public boolean search (
		final String word)
	{
		if (null == word || word.isEmpty()) {
			return false;
		}

		char[] charArray = word.toCharArray();

		int wordLength = charArray.length;
		boolean wildCardStart = '.' == charArray[0];
		boolean wildCardFinish = '.' == charArray[wordLength - 1];

		if (!wildCardStart && !wildCardFinish) {
			return _wordSet.contains (word);
		}

		if (wildCardStart && wildCardFinish) {
			int leftIndex = 0;
			int rightIndex = wordLength - 1;

			while ('.' == leftIndex && leftIndex < wordLength) {
				++leftIndex;
			}

			while ('.' == rightIndex && 0 <= rightIndex) {
				--rightIndex;
			}

			if (leftIndex <= rightIndex) {
				for (String dictionaryWord : _wordSet) {
					if (dictionaryWord.length() == wordLength) {
						return true;
					}
				}

				return false;
			}

			Set<String> wordsStartAndFinishWith = wordsBeginAndEndWith (
				word.substring (0, rightIndex - 1),
				word.substring (leftIndex + 1)
			);

			if (0 == wordsStartAndFinishWith.size()) {
				return false;
			}

			for (String wildCardWord : wordsStartAndFinishWith) {
				if (wildCardWord.length() == wordLength) {
					return true;
				}
			}

			return false;
		}

		if (wildCardStart) {
			int leftIndex = 0;

			while ('.' == leftIndex && leftIndex < wordLength) {
				++leftIndex;
			}

			Set<String> wordsFinishWith = wordsEndWith (word.substring (leftIndex + 1));

			if (0 == wordsFinishWith.size()) {
				return false;
			}

			for (String wildCardWord : wordsFinishWith) {
				if (wildCardWord.length() == wordLength) {
					return true;
				}
			}

			return false;
		}

		if (wildCardFinish) {
			int rightIndex = wordLength - 1;

			while ('.' == rightIndex && 0 <= rightIndex) {
				--rightIndex;
			}

			Set<String> wordsStartWith = wordsBeginWith (word.substring (0, rightIndex - 1));

			if (0 == wordsStartWith.size()) {
				return false;
			}

			for (String wildCardWord : wordsStartWith) {
				if (wildCardWord.length() == wordLength) {
					return true;
				}
			}

			return false;
		}

		return true;
	}

	/**
	 * Given a <b>non-empty</b> string and a dictionary containing a list of <b>non-empty</b> words, add
	 * 	spaces in the string to construct a sentence where each word is a valid dictionary word. Return all
	 * 	such possible sentences.
	 * 
	 * <b>Note</b>:
	 * 
	 * 	The same word in the dictionary may be reused multiple times in the segmentation.
	 * 
	 * 	You may assume the dictionary does not contain duplicate words.
	 * 
	 * @param s Input String
	 * 
	 * @return List of all Possible Sentences
	 */

	public List<String> wordBreakSentenceList (
		final String s)
	{
		List<String> wordBreakSentenceList = new ArrayList<String>();

		List<String> sentenceQueue = new ArrayList<String>();

		List<Integer> indexQueue = new ArrayList<Integer>();

		int wordLength = s.length();

		sentenceQueue.add ("");

		indexQueue.add (0);

		while (!indexQueue.isEmpty()) {
			int tailIndex = indexQueue.size() - 1;

			int beginIndex = indexQueue.remove (tailIndex);

			String sentence = sentenceQueue.remove (tailIndex);

			if (beginIndex >= wordLength - 1) {
				wordBreakSentenceList.add (sentence);

				continue;
			}

			for (int endIndex = beginIndex + 1; endIndex <= wordLength; ++endIndex) {
				String nextWord = s.substring (beginIndex, endIndex);

				if (_wordSet.contains (nextWord)) {
					indexQueue.add (endIndex);

					sentenceQueue.add (sentence + " " + nextWord);
				}
			}
		}

		return wordBreakSentenceList;
	}
}
