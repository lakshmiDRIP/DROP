
package org.drip.sample.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * <i>FreshPromotion</i> an integer 1 if the customer is a winner else return 0.
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

public class FreshPromotion
{

	private static final boolean CriteriaPatternMatch (
		final Integer[] codedCriteriaArray,
		final Integer[] codedShoppingArray,
		final int codedShoppingStartIndex)
	{
		int codedCriteriaIndex = 0;
		int codedShoppingIndex = codedShoppingStartIndex;

		while (codedCriteriaIndex < codedCriteriaArray.length &&
			codedShoppingIndex < codedShoppingArray.length)
		{
			if (0 != codedCriteriaArray[codedCriteriaIndex] &&
				codedCriteriaArray[codedCriteriaIndex] != codedShoppingArray[codedShoppingIndex])
			{
				return false;
			}

			++codedCriteriaIndex;
			++codedShoppingIndex;
		}

		System.out.println ("\t\t\tGOT Here");

		return codedCriteriaArray.length <= codedShoppingArray.length - codedShoppingStartIndex;
	}

	private static final boolean CriteriaPatternMatch (
		final Integer[] codedCriteriaArray,
		final Integer[] codedShoppingArray)
	{
		List<Integer> codedShoppingStartIndexList = new ArrayList<Integer>();

		for (int codedShoppingIndex : codedShoppingArray)
		{
			if (0 == codedCriteriaArray[0] ||
				codedCriteriaArray[0] == codedShoppingArray[codedShoppingIndex]
			)
			{
				codedShoppingStartIndexList.add (
					codedShoppingIndex
				);
			}
		}

		for (int codedShoppingStartIndex : codedShoppingStartIndexList)
		{
			if (CriteriaPatternMatch (
				codedCriteriaArray,
				codedShoppingArray,
				codedShoppingStartIndex
			))
			{
				System.out.println ("\t\tMatch!");

				return true;
			}
		}

		return false;
	}

	private static final int UpdateWordCodeMap (
		final Map<String, Integer> wordCodeMap,
		final String[] wordArray,
		final int startingCode)
	{
		int code = startingCode;

		for (String word : wordArray)
		{
			if (!wordCodeMap.containsKey (
				word
			))
			{
				wordCodeMap.put (
					word,
					code++
				);
			}
		}

		return code;
	}

	private static final Integer[] Encode (
		final Map<String, Integer> wordCodeMap,
		final String[] wordArray)
	{
		int encodedWordIndex = 0;
		Integer[] encodedWordArray = new Integer[wordArray.length];

		for (String word : wordArray)
		{
			encodedWordArray[encodedWordIndex] = wordCodeMap.get (
				word
			);

			++encodedWordIndex;
		}

		return encodedWordArray;
	}

	public static final boolean RewardCriteriaMatch (
		final List<String[]> criteriaArrayList,
		final String[] shoppingArray)
	{
		Map<String, Integer> wordCodeMap = new HashMap<String, Integer>();

		wordCodeMap.put (
			"anything",
			0
		);

		int code = 1;

		for (String[] criteriaArray : criteriaArrayList)
		{
			code = UpdateWordCodeMap (
				wordCodeMap,
				criteriaArray,
				code
			);
		}

		List<Integer[]> codedCriteriaArrayList = new ArrayList<Integer[]>();

		for (String[] criteriaArray : criteriaArrayList)
		{
			codedCriteriaArrayList.add (
				Encode (
					wordCodeMap,
					criteriaArray
				)
			);
		}

		code = UpdateWordCodeMap (
			wordCodeMap,
			shoppingArray,
			code
		);

		System.out.println (wordCodeMap);

		for (Integer[] codedCriteriaArray : codedCriteriaArrayList)
		{
			String criteriaCodeLine = "\t";

			for (int codedCriteriaArrayIndex = 0; codedCriteriaArrayIndex < codedCriteriaArray.length; ++codedCriteriaArrayIndex)
			{
				criteriaCodeLine = criteriaCodeLine + codedCriteriaArray[codedCriteriaArrayIndex] + " ";
			}

			System.out.println (criteriaCodeLine);
		}

		Integer[] codedShoppingArray = Encode (
			wordCodeMap,
			shoppingArray
		);

		String codedShoppingLine = "\t";

		for (int codedShoppingArrayIndex = 0; codedShoppingArrayIndex < codedShoppingArray.length; ++codedShoppingArrayIndex)
		{
			codedShoppingLine = codedShoppingLine + codedShoppingArray[codedShoppingArrayIndex] + " ";
		}

		System.out.println (codedShoppingLine);

		for (Integer[] codedCriteriaArray : codedCriteriaArrayList)
		{
			if (!CriteriaPatternMatch (
				codedCriteriaArray,
				codedShoppingArray
			))
			{
				return false;
			}
		}

		return true;
	}

	public static final void Test1()
		throws Exception
	{
		List<String[]> criteriaArrayList = new ArrayList<String[]>();

		criteriaArrayList.add (
			new String[]
			{
				"apple", "apple"
			}
		);

		criteriaArrayList.add (
			new String[]
			{
				"banana", "anything", "banana"
			}
		);

		String[] shoppingArray = new String[]
		{
			"orange", "apple", "apple", "banana", "orange", "banana"
		};

		System.out.println (
			RewardCriteriaMatch (
				criteriaArrayList,
				shoppingArray
			)
		);
	}

	public static final void Test2()
		throws Exception
	{
		List<String[]> criteriaArrayList = new ArrayList<String[]>();

		criteriaArrayList.add (
			new String[]
			{
				"apple", "apple"
			}
		);

		criteriaArrayList.add (
			new String[]
			{
				"banana", "anything", "banana"
			}
		);

		String[] shoppingArray = new String[]
		{
			"banana", "orange", "banana", "apple", "apple"
		};

		System.out.println (
			RewardCriteriaMatch (
				criteriaArrayList,
				shoppingArray
			)
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		Test2();
	}
}
