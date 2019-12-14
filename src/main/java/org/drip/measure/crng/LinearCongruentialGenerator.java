
package org.drip.measure.crng;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>LinearCongruentialGenerator</i> implements a RNG based on Recurrence Based on Modular Integer
 * Arithmetic.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/README.md">Continuous Random Number Stream Generator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearCongruentialGenerator extends org.drip.measure.crng.RandomNumberGenerator {
	private long _lA = java.lang.Long.MIN_VALUE;
	private long _lB = java.lang.Long.MIN_VALUE;
	private long _lM = java.lang.Long.MIN_VALUE;
	private org.drip.measure.crng.RecursiveGenerator _rg = null;

	/**
	 * Construct an Instance of LinearCongruentialGenerator with the MRG of Type MRG32k3a
	 * 
	 * @param lA A
	 * @param lB B
	 * @param lM M
	 * 
	 * @return Instance of LinearCongruentialGenerator with the MRG of Type MRG32k3a
	 */

	public static final LinearCongruentialGenerator MRG32k3a (
		final long lA,
		final long lB,
		final long lM)
	{
		try {
			return new LinearCongruentialGenerator (lA, lB, lM,
				org.drip.measure.crng.MultipleRecursiveGeneratorLEcuyer.MRG32k3a());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a NumericalRecipes Version of LinearCongruentialGenerator
	 * 
	 * @param rg The Recursive Generator Instance
	 * 
	 * @return NumericalRecipes Version of LinearCongruentialGenerator
	 */

	public static final LinearCongruentialGenerator NumericalRecipes (
		final org.drip.measure.crng.RecursiveGenerator rg)
	{
		long l2Power32 = 1;

		for (int i = 0; i < 32; ++i)
			l2Power32 *= 2;

		try {
			return new LinearCongruentialGenerator (1664525L, 1013904223L, l2Power32, rg);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LinearCongruentialGenerator Contructor
	 * 
	 * @param lA A
	 * @param lB B
	 * @param lM M
	 * @param rg The MultipleRecursiveGenerator Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LinearCongruentialGenerator (
		final long lA,
		final long lB,
		final long lM,
		final org.drip.measure.crng.RecursiveGenerator rg)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_lA = lA) || !org.drip.numerical.common.NumberUtil.IsValid
			(_lB = lB) || !org.drip.numerical.common.NumberUtil.IsValid (_lM = lM) || null == (_rg = rg))
			throw new java.lang.Exception ("LinearCongruentialGenerator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve A
	 * 
	 * @return A
	 */

	public long a()
	{
		return _lA;
	}

	/**
	 * Retrieve B
	 * 
	 * @return B
	 */

	public long b()
	{
		return _lB;
	}

	/**
	 * Retrieve M
	 * 
	 * @return M
	 */

	public long m()
	{
		return _lM;
	}

	/**
	 * Retrieve the Recursive Generator Instance
	 * 
	 * @return The Recursive Generator Instance
	 */

	public org.drip.measure.crng.RecursiveGenerator recursiveGenerator()
	{
		return _rg;
	}

	/**
	 * Retrieve the Next Pseudo-random Long
	 * 
	 * @return The Next Pseudo-random Long
	 */

	public long nextLong()
	{
		return (_lA * _rg.next() + _lB) % _lM;
	}

	/**
	 * Retrieve a Random Number between -1 and 1
	 * 
	 * @return Random Number between -1 and 1
	 */

	public double nextDouble()
	{
		return ((double) nextLong()) / ((double) _lM);
	}

	@Override public double nextDouble01()
	{
		return 0.5 * (1. + nextDouble());
	}
}
