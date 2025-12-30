
package org.drip.measure.crng;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>MultipleRecursiveGeneratorLEcuyer</i> - L'Ecuyer's Multiple Recursive Generator - combines Multiple
 * 	Recursive Sequences to produce a Large State Space with good Randomness Properties. MRG32k3a is a special
 * 	Type of MultipleRecursiveGeneratorLEcuyer. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate the MRG32k3a Variant of the L'Ecuyer's Multiple Recursive Generator</li>
 * 		<li><i>MultipleRecursiveGeneratorLEcuyer</i> Constructor</li>
 * 		<li>Retrieve M1</li>
 * 		<li>Retrieve M2</li>
 * 		<li>Retrieve A12</li>
 * 		<li>Retrieve A13</li>
 * 		<li>Retrieve A21</li>
 * 		<li>Retrieve A23</li>
 * 		<li>Retrieve Y1 Previous</li>
 * 		<li>Retrieve Y1 Previous Previous</li>
 * 		<li>Retrieve Y1 Previous Previous Previous</li>
 * 		<li>Retrieve Y2 Previous</li>
 * 		<li>Retrieve Y2 Previous Previous</li>
 * 		<li>Retrieve Y2 Previous Previous Previous</li>
 * 		<li>Generate the Next Number in the Sequence</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/README.md">Continuous Random Number Stream Generator</a></td></tr>
 *  </table>
 *	<br>
 */

public class MultipleRecursiveGeneratorLEcuyer
	implements RecursiveGenerator
{
	private long _m1 = Long.MIN_VALUE;
	private long _m2 = Long.MIN_VALUE;
	private long _a12 = Long.MIN_VALUE;
	private long _a13 = Long.MIN_VALUE;
	private long _a21 = Long.MIN_VALUE;
	private long _a23 = Long.MIN_VALUE;
	private long _y1Previous = Long.MIN_VALUE;
	private long _y2Previous = Long.MIN_VALUE;
	private long _y1PreviousPrevious = Long.MIN_VALUE;
	private long _y2PreviousPrevious = Long.MIN_VALUE;
	private long _y1PreviousPreviousPrevious = Long.MIN_VALUE;
	private long _y2PreviousPreviousPrevious = Long.MIN_VALUE;

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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>MultipleRecursiveGeneratorLEcuyer</i> Constructor
	 * 
	 * @param y1Previous Y1 Previous
	 * @param y1PreviousPrevious Y1 Previous Previous
	 * @param y1PreviousPreviousPrevious Y1 Previous Previous Previous
	 * @param y2Previous Y2 Previous
	 * @param y2PreviousPrevious Y2 Previous Previous
	 * @param y2PreviousPreviousPrevious Y2 Previous Previous Previous
	 * @param a12 A12
	 * @param a13 A13
	 * @param a21 A21
	 * @param a23 A23
	 * @param m1 M1
	 * @param m2 M2
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MultipleRecursiveGeneratorLEcuyer (
		final long y1Previous,
		final long y1PreviousPrevious,
		final long y1PreviousPreviousPrevious,
		final long y2Previous,
		final long y2PreviousPrevious,
		final long y2PreviousPreviousPrevious,
		final long a12,
		final long a13,
		final long a21,
		final long a23,
		final long m1,
		final long m2)
		throws Exception
	{
		if (!NumberUtil.IsValid (_y1Previous = y1Previous) ||
			!NumberUtil.IsValid (_y1PreviousPrevious = y1PreviousPrevious) ||
			!NumberUtil.IsValid (_y1PreviousPreviousPrevious = y1PreviousPreviousPrevious) ||
			!NumberUtil.IsValid (_y2Previous = y2Previous) ||
			!NumberUtil.IsValid (_y2PreviousPrevious = y2PreviousPrevious) ||
			!NumberUtil.IsValid (_y2PreviousPreviousPrevious = y2PreviousPreviousPrevious) ||
			!NumberUtil.IsValid (_a12 = a12) ||
			!NumberUtil.IsValid (_a13 = a13) ||
			!NumberUtil.IsValid (_a21 = a21) ||
			!NumberUtil.IsValid (_a23 = a23) ||
			!NumberUtil.IsValid (_m1 = m1) ||
			!NumberUtil.IsValid (_m2 = m2))
		{
			throw new Exception ("MultipleRecursiveGeneratorLEcuyer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve M1
	 * 
	 * @return M1
	 */

	public long m1()
	{
		return _m1;
	}

	/**
	 * Retrieve M2
	 * 
	 * @return M2
	 */

	public long m2()
	{
		return _m2;
	}

	/**
	 * Retrieve A12
	 * 
	 * @return A12
	 */

	public long a12()
	{
		return _a12;
	}

	/**
	 * Retrieve A13
	 * 
	 * @return A13
	 */

	public long a13()
	{
		return _a13;
	}

	/**
	 * Retrieve A21
	 * 
	 * @return A21
	 */

	public long a21()
	{
		return _a21;
	}

	/**
	 * Retrieve A23
	 * 
	 * @return A23
	 */

	public long a23()
	{
		return _a23;
	}

	/**
	 * Retrieve Y1 Previous
	 * 
	 * @return Y1 Previous
	 */

	public long y1Prev()
	{
		return _y1Previous;
	}

	/**
	 * Retrieve Y1 Previous Previous
	 * 
	 * @return Y1 Previous Previous
	 */

	public long y1PrevPrev()
	{
		return _y1PreviousPrevious;
	}

	/**
	 * Retrieve Y1 Previous Previous Previous
	 * 
	 * @return Y1 Previous Previous Previous
	 */

	public long y1PrevPrevPrev()
	{
		return _y1PreviousPreviousPrevious;
	}

	/**
	 * Retrieve Y2 Previous
	 * 
	 * @return Y2 Previous
	 */

	public long y2Prev()
	{
		return _y2Previous;
	}

	/**
	 * Retrieve Y2 Previous Previous
	 * 
	 * @return Y2 Previous Previous
	 */

	public long y2PrevPrev()
	{
		return _y2PreviousPrevious;
	}

	/**
	 * Retrieve Y2 Previous Previous Previous
	 * 
	 * @return Y2 Previous Previous Previous
	 */

	public long y2PrevPrevPrev()
	{
		return _y2PreviousPreviousPrevious;
	}

	/**
	 * Generate the Next Number in the Sequence
	 * 
	 * @return The Next Number in the Sequence
	 */

	@Override public long next()
	{
		long y1 = (_a12 * _y1PreviousPrevious + _a13 * _y1PreviousPreviousPrevious) % _m1;
		long y2 = (_a21 * _y2Previous + _a23 * _y2PreviousPreviousPrevious) % _m2;
		_y2PreviousPreviousPrevious = _y2PreviousPrevious;
		_y1PreviousPreviousPrevious = _y1PreviousPrevious;
		_y2PreviousPrevious = _y2Previous;
		_y1PreviousPrevious = _y1Previous;
		_y2Previous = y2;
		_y1Previous = y1;
		return y1 + y2;
	}
}
