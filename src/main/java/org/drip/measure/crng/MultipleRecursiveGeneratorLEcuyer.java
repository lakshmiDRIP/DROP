
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
 * <i>MultipleRecursiveGeneratorLEcuyer</i> - L'Ecuyer's Multiple Recursive Generator - combines Multiple
 * Recursive Sequences to produce a Large State Space with good Randomness Properties. MRG32k3a is a special
 * Type of MultipleRecursiveGeneratorLEcuyer.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng">Continuous Random Number Stream Generator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultipleRecursiveGeneratorLEcuyer implements org.drip.measure.crng.RecursiveGenerator {
	private long _lM1 = java.lang.Long.MIN_VALUE;
	private long _lM2 = java.lang.Long.MIN_VALUE;
	private long _lA12 = java.lang.Long.MIN_VALUE;
	private long _lA13 = java.lang.Long.MIN_VALUE;
	private long _lA21 = java.lang.Long.MIN_VALUE;
	private long _lA23 = java.lang.Long.MIN_VALUE;
	private long _lY1Prev = java.lang.Long.MIN_VALUE;
	private long _lY2Prev = java.lang.Long.MIN_VALUE;
	private long _lY1PrevPrev = java.lang.Long.MIN_VALUE;
	private long _lY2PrevPrev = java.lang.Long.MIN_VALUE;
	private long _lY1PrevPrevPrev = java.lang.Long.MIN_VALUE;
	private long _lY2PrevPrevPrev = java.lang.Long.MIN_VALUE;

	/**
	 * Generate the MRG32k3a Variant of the L'Ecuyer's Multiple Recursive Generator
	 * 
	 * @return The MRG32k3a Variant of the L'Ecuyer's Multiple Recursive Generator
	 */

	public static final MultipleRecursiveGeneratorLEcuyer MRG32k3a()
	{
		long l2Power32 = 1;

		for (int i = 0; i < 32; ++i)
			l2Power32 *= 2;

		try {
			return new MultipleRecursiveGeneratorLEcuyer (
				System.nanoTime(),
				System.nanoTime(),
				System.nanoTime(),
				System.nanoTime(),
				System.nanoTime(),
				System.nanoTime(),
				1403580,
				-810728,
				 527612,
				-1370589,
				l2Power32 - 209,
				l2Power32 - 22853
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MultipleRecursiveGeneratorLEcuyer Constructor
	 * 
	 * @param lY1Prev Y1 Previous
	 * @param lY1PrevPrev Y1 Previous Previous
	 * @param lY1PrevPrevPrev Y1 Previous Previous Previous
	 * @param lY2Prev Y2 Previous
	 * @param lY2PrevPrev Y2 Previous Previous
	 * @param lY2PrevPrevPrev Y2 Previous Previous Previous
	 * @param lA12 A12
	 * @param lA13 A13
	 * @param lA21 A21
	 * @param lA23 A23
	 * @param lM1 M1
	 * @param lM2 M2
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MultipleRecursiveGeneratorLEcuyer (
		final long lY1Prev,
		final long lY1PrevPrev,
		final long lY1PrevPrevPrev,
		final long lY2Prev,
		final long lY2PrevPrev,
		final long lY2PrevPrevPrev,
		final long lA12,
		final long lA13,
		final long lA21,
		final long lA23,
		final long lM1,
		final long lM2)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_lY1Prev = lY1Prev) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lY1PrevPrev = lY1PrevPrev) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lY1PrevPrevPrev = lY1PrevPrevPrev) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lY2Prev = lY2Prev) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lY2PrevPrev = lY2PrevPrev) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lY2PrevPrevPrev = lY2PrevPrevPrev) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lA12 = lA12) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lA13 = lA13) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lA21 = lA21) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lA23 = lA23) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lM1 = lM1) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lM2 = lM2))
			throw new java.lang.Exception
				("MultipleRecursiveGeneratorLEcuyer Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve M1
	 * 
	 * @return M1
	 */

	public long m1()
	{
		return _lM1;
	}

	/**
	 * Retrieve M2
	 * 
	 * @return M2
	 */

	public long m2()
	{
		return _lM2;
	}

	/**
	 * Retrieve A12
	 * 
	 * @return A12
	 */

	public long a12()
	{
		return _lA12;
	}

	/**
	 * Retrieve A13
	 * 
	 * @return A13
	 */

	public long a13()
	{
		return _lA13;
	}

	/**
	 * Retrieve A21
	 * 
	 * @return A21
	 */

	public long a21()
	{
		return _lA21;
	}

	/**
	 * Retrieve A23
	 * 
	 * @return A23
	 */

	public long a23()
	{
		return _lA23;
	}

	/**
	 * Retrieve Y1 Previous
	 * 
	 * @return Y1 Previous
	 */

	public long y1Prev()
	{
		return _lY1Prev;
	}

	/**
	 * Retrieve Y1 Previous Previous
	 * 
	 * @return Y1 Previous Previous
	 */

	public long y1PrevPrev()
	{
		return _lY1PrevPrev;
	}

	/**
	 * Retrieve Y1 Previous Previous Previous
	 * 
	 * @return Y1 Previous Previous Previous
	 */

	public long y1PrevPrevPrev()
	{
		return _lY1PrevPrevPrev;
	}

	/**
	 * Retrieve Y2 Previous
	 * 
	 * @return Y2 Previous
	 */

	public long y2Prev()
	{
		return _lY2Prev;
	}

	/**
	 * Retrieve Y2 Previous Previous
	 * 
	 * @return Y2 Previous Previous
	 */

	public long y2PrevPrev()
	{
		return _lY2PrevPrev;
	}

	/**
	 * Retrieve Y2 Previous Previous Previous
	 * 
	 * @return Y2 Previous Previous Previous
	 */

	public long y2PrevPrevPrev()
	{
		return _lY2PrevPrevPrev;
	}

	@Override public long next()
	{
		long lY1 = (_lA12 * _lY1PrevPrev + _lA13 * _lY1PrevPrevPrev) % _lM1;
		long lY2 = (_lA21 * _lY2Prev     + _lA23 * _lY2PrevPrevPrev) % _lM2;
		_lY2PrevPrevPrev = _lY2PrevPrev;
		_lY1PrevPrevPrev = _lY1PrevPrev;
		_lY2PrevPrev = _lY2Prev;
		_lY1PrevPrev = _lY1Prev;
		_lY2Prev = lY2;
		_lY1Prev = lY1;
		return lY1 + lY2;
	}
}
