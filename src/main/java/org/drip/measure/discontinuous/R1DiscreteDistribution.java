
package org.drip.measure.discontinuous;

import java.util.TreeMap;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>R1DiscreteDistribution</i> implements the Discrete Distribution over the Combinatorial R<sup>1</sup>
 *  Outcomes. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate an Standard Instance of Discrete <i>R1DiscreteDistribution</i></li>
 * 		<li>Retrieve the Discrete Probability Map</li>
 * 		<li>Retrieve the Probability of the Instance Occurrence</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discontinuous/README.md">Antithetic, Quadratically Re-sampled, De-biased Distribution</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1DiscreteDistribution
{
	private TreeMap<Double, Double> _probabilityMap = null;

	/**
	 * Generate an Standard Instance of Discrete <i>R1DiscreteDistribution</i>
	 * 
	 * @param instanceArray Instance Array
	 * @param probabilityArray Probability Array
	 * 
	 * @return Standard Instance of Discrete <i>R1DiscreteDistribution</i>
	 */

	public static R1DiscreteDistribution Standard (
		final double[] instanceArray,
		final double[] probabilityArray)
	{
		if (null == instanceArray || null == probabilityArray) {
			return null;
		}

		int instanceCount = instanceArray.length;

		if (0 == instanceCount || instanceCount != probabilityArray.length) {
			return null;
		}

		TreeMap<Double, Double> probabilityMap = new TreeMap<Double, Double>();

		double cumulativeProbability = 0.;

		for (double probability : probabilityArray) {
			if (!NumberUtil.IsValid (probability) || 0. > probability) {
				return null;
			}

			cumulativeProbability += probability;
		}

		if (0. == cumulativeProbability) {
			return null;
		}

		for (int instanceIndex = 0; instanceIndex < instanceCount; ++instanceIndex) {
			if (!NumberUtil.IsValid (instanceArray[instanceIndex])) {
				return null;
			}

			probabilityMap.put (
				instanceArray[instanceIndex],
				probabilityArray[instanceIndex] / cumulativeProbability
			);
		}

		try {
			return new R1DiscreteDistribution (probabilityMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private R1DiscreteDistribution (
		final TreeMap<Double, Double> probabilityMap)
	{
		_probabilityMap = probabilityMap;
	}

	/**
	 * Retrieve the Discrete Probability Map
	 * 
	 * @return The Discrete Probability Map
	 */

	public TreeMap<Double, Double> probabilityMap()
	{
		return _probabilityMap;
	}

	/**
	 * Retrieve the Probability of the Instance Occurrence
	 * 
	 * @param x Input Instance
	 * 
	 * @return Probability of the Instance Occurrence
	 * 
	 * @throws Exception Thrown if the Input Instance is Invalid
	 */

	public double probability (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x) || !_probabilityMap.containsKey (x)) {
			throw new Exception ("R1DiscreteDistribution::probability => Input Instance is Invalid");
		}

		return _probabilityMap.get (x);
	}
}
