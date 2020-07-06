
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>PhoneLetterCombinationGenerator</i> generates the Phone Letter Combinations.
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

public class PhoneLetterCombinationGenerator
{
	private java.util.Map<java.lang.Integer, char[]> _digitCharacterMap =
		new java.util.HashMap<java.lang.Integer, char[]>();

	/**
	 * PhoneLetterCombinationGenerator Constructor
	 * 
	 * @param digitCharacterMap The Digit To Character Array Map
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public PhoneLetterCombinationGenerator (final java.util.Map<java.lang.Integer, char[]> digitCharacterMap)
		throws java.lang.Exception
	{
		if (null == (_digitCharacterMap = digitCharacterMap) || 0 == _digitCharacterMap.size())
			throw new java.lang.Exception ("PhoneLetterCombinationGenerator Constructor => Invalid Input");
	}

	/**
	 * Retrieve the Digit to Character Array Map
	 * 
	 * @return Digit to Character Array Map
	 */

	public java.util.Map<java.lang.Integer, char[]> digitCharacterMap()
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

	public java.util.Set<java.lang.String> candidateCharacterSet (
		final int digit,
		final int count)
	{
		if (_digitCharacterMap.containsKey (digit)) return null;

		char[] charArray = _digitCharacterMap.get (digit);

		if (count > charArray.length) return null;

		java.util.Set<java.lang.String> candidateCharacterSet = new java.util.HashSet<java.lang.String>();

		if (1 == count)
			candidateCharacterSet.add ("" + charArray[0]);
		else if (2 == count)
		{
			candidateCharacterSet.add (charArray[0] + "" + charArray[0]);

			candidateCharacterSet.add ("" + charArray[1]);
		}
		else if (3 == count)
		{
			candidateCharacterSet.add (charArray[0] + "" + charArray[0] + "" + charArray[0]);

			candidateCharacterSet.add (charArray[1] + "" + charArray[0]);

			candidateCharacterSet.add (charArray[0] + "" + charArray[1]);

			candidateCharacterSet.add ("" + charArray[2]);
		}

		return candidateCharacterSet;
	}
}
