
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
 * <i>LinearCongruentialGenerator</i> implements a RNG based on Recurrence Based on Modular Integer
 * 	Arithmetic. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct an Instance of <i>LinearCongruentialGenerator</i> with the MRG of Type MRG32k3a</li>
 * 		<li>Construct a NumericalRecipes Version of <i>LinearCongruentialGenerator</i></li>
 * 		<li><i>LinearCongruentialGenerator</i> Constructor</li>
 * 		<li>Retrieve A</li>
 * 		<li>Retrieve B</li>
 * 		<li>Retrieve M</li>
 * 		<li>Retrieve the Recursive Generator Instance</li>
 * 		<li>Retrieve the Next Pseudo-random Long</li>
 * 		<li>Retrieve a Random Number between -1 and 1</li>
 * 		<li>Retrieve a Random Number between 0 and 1</li>
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
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearCongruentialGenerator
	extends RandomNumberGenerator
{
	private long _a = Long.MIN_VALUE;
	private long _b = Long.MIN_VALUE;
	private long _m = Long.MIN_VALUE;
	private RecursiveGenerator _recursiveGenerator = null;

	/**
	 * Construct an Instance of <i>LinearCongruentialGenerator</i> with the MRG of Type MRG32k3a
	 * 
	 * @param a A
	 * @param b B
	 * @param m M
	 * 
	 * @return Instance of <i>LinearCongruentialGenerator</i> with the MRG of Type MRG32k3a
	 */

	public static final LinearCongruentialGenerator MRG32k3a (
		final long a,
		final long b,
		final long m)
	{
		try {
			return new LinearCongruentialGenerator (a, b, m, MultipleRecursiveGeneratorLEcuyer.MRG32k3a());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Numerical Recipes Version of <i>LinearCongruentialGenerator</i>
	 * 
	 * @param recursiveGenerator The Recursive Generator Instance
	 * 
	 * @return Numerical Recipes Version of <i>LinearCongruentialGenerator</i>
	 */

	public static final LinearCongruentialGenerator NumericalRecipes (
		final RecursiveGenerator recursiveGenerator)
	{
		long twoPower32 = 1L;

		for (int i = 0; i < 32; ++i) {
			twoPower32 *= 2L;
		}

		try {
			return new LinearCongruentialGenerator (1664525L, 1013904223L, twoPower32, recursiveGenerator);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>LinearCongruentialGenerator</i> Constructor
	 * 
	 * @param a A
	 * @param b B
	 * @param m M
	 * @param recursiveGenerator  Recursive Generator Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public LinearCongruentialGenerator (
		final long a,
		final long b,
		final long m,
		final RecursiveGenerator recursiveGenerator)
		throws Exception
	{
		if (!NumberUtil.IsValid (_a = a) ||
			!NumberUtil.IsValid (_b = b) ||
			!NumberUtil.IsValid (_m = m) ||
			null == (_recursiveGenerator = recursiveGenerator))
		{
			throw new Exception ("LinearCongruentialGenerator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve A
	 * 
	 * @return A
	 */

	public long a()
	{
		return _a;
	}

	/**
	 * Retrieve B
	 * 
	 * @return B
	 */

	public long b()
	{
		return _b;
	}

	/**
	 * Retrieve M
	 * 
	 * @return M
	 */

	public long m()
	{
		return _m;
	}

	/**
	 * Retrieve the Recursive Generator Instance
	 * 
	 * @return The Recursive Generator Instance
	 */

	public RecursiveGenerator recursiveGenerator()
	{
		return _recursiveGenerator;
	}

	/**
	 * Retrieve the Next Pseudo-random Long
	 * 
	 * @return The Next Pseudo-random Long
	 */

	public long nextLong()
	{
		return (_a * _recursiveGenerator.next() + _b) % _m;
	}

	/**
	 * Retrieve a Random Number between -1 and 1
	 * 
	 * @return Random Number between -1 and 1
	 */

	public double nextDouble()
	{
		return ((double) nextLong()) / ((double) _m);
	}

	/**
	 * Retrieve a Random Number between 0 and 1
	 * 
	 * @return Random Number between 0 and 1
	 */

	@Override public double nextDouble01()
	{
		return 0.5 * (1. + nextDouble());
	}
}
