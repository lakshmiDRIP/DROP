
package org.drip.measure.distribution;

import org.drip.numerical.rdintegration.BoundedManifold;

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
 * <i>RdContinuous</i> implements the Base Abstract Class behind R<sup>d</sup> Distributions. It exports
 *  Methods for incremental, cumulative, and inverse cumulative Distribution Densities. It provides the
 * 	following Functionality:
 *
 *  <ul>
 * 		<li>RdContinuous Constructor</li>
 * 		<li>Retrieve the Bounded Manifold</li>
 * 		<li>Compute the Cumulative under the Distribution to the given Variate Array</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 Variate Arrays</li>
 * 		<li>Compute the Density under the Distribution at the given Variate Array</li>
 * 		<li>Re-zone the Domain using the specified Bounded Manifold</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution within Cartesian Bounds</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 * 		<li>Retrieve the State Dimension</li>
 * 		<li>Indicate if the Variate Array is inside the Supported Range</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdContinuous
{
	private BoundedManifold _boundedManifold = null;

	/**
	 * RdContinuous Constructor
	 * 
	 * @param boundedManifold The Manifold
	 * 
	 * @throws Exception Thrown if the Manifold is Invalid
	 */

	public RdContinuous (
		final BoundedManifold boundedManifold)
		throws Exception
	{
		if (null == (_boundedManifold = boundedManifold)) {
			throw new Exception ("RdContinuous Constructor - Invalid Manifold");
		}
	}

	/**
	 * Retrieve the Bounded Manifold
	 * 
	 * @return Bounded Manifold
	 */

	public BoundedManifold boundedManifold()
	{
		return _boundedManifold;
	}

	/**
	 * Compute the Cumulative under the Distribution to the given Variate Array
	 * 
	 * @param variateArray Variate Array to which the Cumulative is to be computed
	 * 
	 * @return The Cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public abstract double cumulative (
		final double[] variateArray)
		throws Exception;

	/**
	 * Compute the Incremental under the Distribution between the 2 Variate Arrays
	 * 
	 * @param leftVariateArray Left Variate Array to which the Cumulative is to be computed
	 * @param rightVariateArray Right Variate Array to which the Cumulative is to be computed
	 * 
	 * @return The Incremental
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public abstract double incremental (
		final double[] leftVariateArray,
		final double[] rightVariateArray)
		throws Exception;

	/**
	 * Compute the Density under the Distribution at the given Variate Array
	 * 
	 * @param variateArray Variate Array at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	public abstract double density (
		final double[] variateArray)
		throws Exception;

	/**
	 * Re-zone the Domain using the specified Bounded Manifold
	 * 
	 * @param boundedManifold Bounded Manifold
	 * 
	 * @return TRUE - The Domain successfully re-zoned with the specified Bounded Manifold
	 */

	public abstract RdContinuous rezone (
		final BoundedManifold boundedManifold
	);

	/**
	 * Generate a Random Variable corresponding to the Distribution within Cartesian Bounds
	 * 
	 * @return Random Variable corresponding to the Distribution within Cartesian Bounds
	 */

	public abstract double[] nonBoundingRandom();

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @param validVariateTrial Valid Random Variate Generation Attempts
	 * 
	 * @return Random Variable corresponding to the Distribution
	 */

	public double[] random (
		int validVariateTrial)
	{
		while (0 <= --validVariateTrial) {
			double[] randomRd = nonBoundingRandom();

			if (_boundedManifold.in11 (randomRd)) {
				return randomRd;
			}
		}

		return null;
	}

	/**
	 * Retrieve the State Dimension
	 * 
	 * @return The State Dimension
	 */

	public int dimension()
	{
		return _boundedManifold.dimension();
	}

	/**
	 * Indicate if the Variate Array is inside the Supported Range
	 * 
	 * @param variateArray Variate Array
	 * 
	 * @return TRUE - Variate Array is inside of the Supported Range
	 */

	public boolean supported (
		final double[] variateArray)
	{
		return _boundedManifold.in11 (variateArray);
	}
}
