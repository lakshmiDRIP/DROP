
package org.drip.sequence.custom;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>LongestCommonSubsequence</i> contains Variance Bounds on the Critical Measures of the Longest Common
 * Subsequence between two Strings.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/custom">Custom</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LongestCommonSubsequence extends org.drip.sequence.functional.BoundedMultivariateRandom {

	/**
	 * Lower Bound of the Conjecture of the Expected Value of the LCS Length
	 * 
	 * @param adblVariate Array of Input Variates
	 * 
	 * @return Lower Bound of the Conjecture of the Expected Value of the LCS Length
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double expectationConjectureLowerBound (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate)
			throw new java.lang.Exception
				("LongestCommonSubsequence::expectationConjectureLowerBound => Invalid Inputs");

		return 0.37898;
	}

	/**
	 * Upper Bound of the Conjecture of the Expected Value of the LCS Length
	 * 
	 * @param adblVariate Array of Input Variates
	 * 
	 * @return Upper Bound of the Conjecture of the Expected Value of the LCS Length
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double expectationConjectureUpperBound (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate)
			throw new java.lang.Exception
				("LongestCommonSubsequence::expectationConjectureUpperBound => Invalid Inputs");

		return 0.418815;
	}

	/**
	 * Conjecture of the Expected Value of the LCS Length
	 * 
	 * @param adblVariate Array of Input Variates
	 * 
	 * @return Conjecture of the Expected Value of the LCS Length
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double expectationConjecture (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate)
			throw new java.lang.Exception
				("LongestCommonSubsequence::expectationConjecture => Invalid Inputs");

		return adblVariate.length / (1. + java.lang.Math.sqrt (2.));
	}

	@Override public int dimension()
	{
		return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		return 0.25 * (expectationConjectureLowerBound (adblVariate) + expectationConjectureUpperBound
			(adblVariate)) + 0.5 * expectationConjecture (adblVariate);
	}

	@Override public double targetVariateVarianceBound (
		final int iTargetVariateIndex)
		throws java.lang.Exception
	{
		return 1.0;
	}
}
