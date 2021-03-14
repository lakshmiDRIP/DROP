
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>WordDictionary</i> is a data structure that supports the following two operations: addWord and search.
 * 
 * Search can search a literal word or a regular expression string containing only letters a-z or .. A .
 * 	means it can represent any one letter.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class WordDictionary
{
	private java.util.Set<java.lang.String> _wordSet = new java.util.HashSet<java.lang.String>();

	private java.util.Set<java.lang.String> wordsBeginWith (
		final java.lang.String prefix)
	{
		java.util.Set<java.lang.String> wordsBeginWithSet = new java.util.HashSet<java.lang.String>();

		if (null == prefix || prefix.isEmpty())
		{
			return wordsBeginWithSet;
		}

		for (java.lang.String word : _wordSet)
		{
			if (word.startsWith (
				prefix
			))
			{
				wordsBeginWithSet.add (
					word
				);
			}
		}

		return wordsBeginWithSet;
	}

	private java.util.Set<java.lang.String> wordsEndWith (
		final java.lang.String suffix)
	{
		java.util.Set<java.lang.String> wordsEndWithSet = new java.util.HashSet<java.lang.String>();

		if (null == suffix || suffix.isEmpty())
		{
			return wordsEndWithSet;
		}

		for (java.lang.String word : _wordSet)
		{
			if (word.endsWith (
				suffix
			))
			{
				wordsEndWithSet.add (
					word
				);
			}
		}

		return wordsEndWithSet;
	}

	private java.util.Set<java.lang.String> wordsBeginAndEndWith (
		final java.lang.String prefix,
		final java.lang.String suffix)
	{
		java.util.Set<java.lang.String> wordsBeginEndWithSet = wordsBeginWith (
			prefix
		);

		wordsBeginEndWithSet.addAll (
			wordsEndWith (
				suffix
			)
		);

		return wordsBeginEndWithSet;
	}

	/**
	 * WordDictionary Constructor
	 */

	public WordDictionary()
	{
	}

	/**
	 * Retrieve the Set of Words
	 * 
	 * @return The Set of Words
	 */

	public java.util.Set<java.lang.String> wordSet()
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
		final java.lang.String word)
	{
		if (null == word || word.isEmpty())
		{
			return false;
		}

		_wordSet.add (
			word
		);

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
		final java.lang.String word)
	{
		if (null == word || word.isEmpty())
		{
			return false;
		}

		char[] charArray = word.toCharArray();

		int wordLength = charArray.length;
		boolean wildCardStart = '.' == charArray[0];
		boolean wildCardFinish = '.' == charArray[wordLength - 1];

		if (!wildCardStart && !wildCardFinish)
		{
			return _wordSet.contains (
				word
			);
		}

		if (wildCardStart && wildCardFinish)
		{
			int leftIndex = 0;
			int rightIndex = wordLength - 1;

			while ('.' == leftIndex && leftIndex < wordLength)
			{
				++leftIndex;
			}

			while ('.' == rightIndex && rightIndex >= 0)
			{
				--rightIndex;
			}

			if (leftIndex <= rightIndex)
			{
				for (java.lang.String dictionaryWord : _wordSet)
				{
					if (dictionaryWord.length() == wordLength)
					{
						return true;
					}
				}

				return false;
			}

			java.util.Set<java.lang.String> wordsStartAndFinishWith = wordsBeginAndEndWith (
				word.substring (
					0,
					rightIndex - 1
				),
				word.substring (
					leftIndex + 1
				)
			);

			if (0 == wordsStartAndFinishWith.size())
			{
				return false;
			}

			for (java.lang.String wildCardWord : wordsStartAndFinishWith)
			{
				if (wildCardWord.length() == wordLength)
				{
					return true;
				}
			}

			return false;
		}

		if (wildCardStart)
		{
			int leftIndex = 0;

			while ('.' == leftIndex && leftIndex < wordLength)
			{
				++leftIndex;
			}

			java.util.Set<java.lang.String> wordsFinishWith = wordsEndWith (
				word.substring (
					leftIndex + 1
				)
			);

			if (0 == wordsFinishWith.size())
			{
				return false;
			}

			for (java.lang.String wildCardWord : wordsFinishWith)
			{
				if (wildCardWord.length() == wordLength)
				{
					return true;
				}
			}

			return false;
		}

		if (wildCardFinish)
		{
			int rightIndex = wordLength - 1;

			while ('.' == rightIndex && rightIndex >= 0)
			{
				--rightIndex;
			}

			java.util.Set<java.lang.String> wordsStartWith = wordsBeginWith (
				word.substring (
					0,
					rightIndex - 1
				)
			);

			if (0 == wordsStartWith.size())
			{
				return false;
			}

			for (java.lang.String wildCardWord : wordsStartWith)
			{
				if (wildCardWord.length() == wordLength)
				{
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

	public java.util.List<java.lang.String> wordBreakSentenceList (
		final java.lang.String s)
	{
		java.util.List<java.lang.String> wordBreakSentenceList = new java.util.ArrayList<java.lang.String>();

		java.util.List<java.lang.Integer> sIndexQueue = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.String> sentenceQueue = new java.util.ArrayList<java.lang.String>();

		int wordLength = s.length();

		sIndexQueue.add (0);

		sentenceQueue.add ("");

		while (!sIndexQueue.isEmpty()) {
			int tailIndex = sIndexQueue.size() - 1;

			int beginIndex = sIndexQueue.remove (tailIndex);

			java.lang.String sentence = sentenceQueue.remove (tailIndex);

			if (beginIndex >= wordLength - 1) {
				wordBreakSentenceList.add (sentence);

				continue;
			}

			for (int endIndex = beginIndex + 1; endIndex <= wordLength; ++endIndex) {
				java.lang.String nextWord = s.substring (beginIndex, endIndex);

				if (_wordSet.contains (nextWord)) {
					sIndexQueue.add (endIndex);

					sentenceQueue.add (sentence + " " + nextWord);
				}
			}
		}

		return wordBreakSentenceList;
	}

	public static final void main (
		final String[] argumentArray)
	{
		WordDictionary wordDictionary = new WordDictionary();

		wordDictionary.addWord (
			"cat"
		);

		wordDictionary.addWord (
			"cats"
		);

		wordDictionary.addWord (
			"and"
		);

		wordDictionary.addWord (
			"sand"
		);

		wordDictionary.addWord (
			"dog"
		);

		System.out.println (
			wordDictionary.wordBreakSentenceList (
				"catsanddog"
			)
		);

		wordDictionary.addWord (
			"apple"
		);

		wordDictionary.addWord (
			"pen"
		);

		wordDictionary.addWord (
			"applepen"
		);

		wordDictionary.addWord (
			"pine"
		);

		wordDictionary.addWord (
			"pineapple"
		);

		System.out.println (
			wordDictionary.wordBreakSentenceList (
				"pineapplepenapple"
			)
		);

		System.out.println (
			wordDictionary.wordBreakSentenceList (
				"catsandog"
			)
		);
	}
}
