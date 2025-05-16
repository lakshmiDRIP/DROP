
package org.drip.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
 * <i>PhoneLetterCombinationGenerator</i> generates the Phone Letter Combinations. It implements the
 * 	following Functions:
 * <br>
 * <ul>
 * 		<li>Generate the Standard <i>PhoneLetterCombinationGenerator</i></li>
 * 		<li><i>PhoneLetterCombinationGenerator</i> Constructor</li>
 * 		<li>Retrieve the Digit to Character Array Map</li>
 * 		<li>Generate the Set of Candidate Characters from the specified Digit and its Count</li>
 * 		<li>Generate all the Candidate Sequence Sets, given the Phone Number</li>
 * </ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PhoneLetterCombinationGenerator
{
	private Map<Character, char[]> _digitCharacterMap = new HashMap<Character, char[]>();

	/**
	 * Generate the Standard <i>PhoneLetterCombinationGenerator</i>
	 * 
	 * @return The Standard <i>PhoneLetterCombinationGenerator</i>
	 */

	public static final PhoneLetterCombinationGenerator Standard()
	{
		Map<Character, char[]> digitCharacterMap = new HashMap<Character, char[]>();

		digitCharacterMap.put ('1', new char[] {'A', 'B', 'C'});

		digitCharacterMap.put ('2', new char[] {'D', 'E', 'F'});

		digitCharacterMap.put ('3', new char[] {'G', 'H', 'I'});

		digitCharacterMap.put ('4', new char[] {'J', 'K', 'L'});

		digitCharacterMap.put ('5', new char[] {'M', 'N', 'O'});

		digitCharacterMap.put ('6', new char[] {'P', 'Q', 'R'});

		digitCharacterMap.put ('7', new char[] {'S', 'T'});

		digitCharacterMap.put ('8', new char[] {'U', 'V', 'W'});

		digitCharacterMap.put ('9', new char[] {'X', 'Y'});

		digitCharacterMap.put ('0', new char[] {'Z'});

		try {
			return new PhoneLetterCombinationGenerator (digitCharacterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>PhoneLetterCombinationGenerator</i> Constructor
	 * 
	 * @param digitCharacterMap The Digit To Character Array Map
	 * 
	 * @throws Exception Thrown if the Input is Invalid
	 */

	public PhoneLetterCombinationGenerator (
		final Map<Character, char[]> digitCharacterMap)
		throws Exception
	{
		if (null == (_digitCharacterMap = digitCharacterMap) || 0 == _digitCharacterMap.size()) {
			throw new Exception ("PhoneLetterCombinationGenerator Constructor => Invalid Input");
		}
	}

	/**
	 * Retrieve the Digit to Character Array Map
	 * 
	 * @return Digit to Character Array Map
	 */

	public Map<Character, char[]> digitCharacterMap()
	{
		return _digitCharacterMap;
	}

	/**
	 * Generate the Set of Candidate Characters from the specified Digit and its Count
	 * 
	 * @param digit Digit
	 * @param count Count of the Digit
	 * 
	 * @return Set of Candidate Characters
	 */

	public Set<String> candidateCharacterSet (
		final char digit,
		final int count)
	{
		if (!_digitCharacterMap.containsKey (digit)) {
			return null;
		}

		char[] charArray = _digitCharacterMap.get (digit);

		if (count > charArray.length) {
			return null;
		}

		Set<String> candidateCharacterSet = new HashSet<String>();

		if (1 == count) {
			candidateCharacterSet.add ("" + charArray[0]);
		} else if (2 == count) {
			candidateCharacterSet.add (charArray[0] + "" + charArray[0]);

			candidateCharacterSet.add ("" + charArray[1]);
		} else if (3 == count) {
			candidateCharacterSet.add (charArray[0] + "" + charArray[0] + "" + charArray[0]);

			candidateCharacterSet.add (charArray[1] + "" + charArray[0]);

			candidateCharacterSet.add (charArray[0] + "" + charArray[1]);

			candidateCharacterSet.add ("" + charArray[2]);
		}

		return candidateCharacterSet;
	}

	/**
	 * Generate all the Candidate Sequence Sets, given the Phone Number.
	 * 
	 * @param number The Input Phone Number
	 * 
	 * @return The Candidate Sequence Sets
	 */

	public Set<String> sequenceSet (
		final String number)
	{
		char[] digitArray = number.toCharArray();

		Set<String> sequenceSet = new HashSet<String>();

		List<Integer> numberIndexQueue = new ArrayList<Integer>();

		List<String> numberCombinationQueue = new ArrayList<String>();

		numberCombinationQueue.add ("");

		numberIndexQueue.add (0);

		while (!numberIndexQueue.isEmpty()) {
			int tailIndex = numberIndexQueue.size() - 1;

			int leftIndex = numberIndexQueue.remove (tailIndex);

			String combination = numberCombinationQueue.remove (tailIndex);

			if (leftIndex >= digitArray.length) {
				sequenceSet.add (combination);

				continue;
			}

			int rightIndex = leftIndex;

			while (rightIndex < digitArray.length && digitArray[leftIndex] == digitArray[rightIndex]) {
				++rightIndex;
			}

			for (String candidate : candidateCharacterSet (digitArray[leftIndex], rightIndex - leftIndex)) {
				numberIndexQueue.add (rightIndex);

				numberCombinationQueue.add (combination + candidate);
			}
		}

		return sequenceSet;
	}
}
