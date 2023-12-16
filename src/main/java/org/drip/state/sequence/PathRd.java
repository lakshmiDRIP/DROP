
package org.drip.state.sequence;

import org.drip.measure.discrete.SequenceGenerator;
import org.drip.numerical.common.NumberUtil;

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
 * <i>PathRd</i> exposes the Functionality to generate a Sequence of the Path Vertex Latent State
 * R<sup>d</sup> Realizations across Multiple Paths.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence/README.md">Monte Carlo Path State Realizations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathRd
{
	private boolean _logNormal = false;
	private double[] _meanArray = null;
	private double _volatility = Double.NaN;

	/**
	 * PathRd Constructor
	 * 
	 * @param meanArray Array of Means
	 * @param volatility Volatility
	 * @param logNormal TRUE - The Generated Random Numbers are Log Normal
	 * 
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public PathRd (
		final double[] meanArray,
		final double volatility,
		final boolean logNormal)
		throws Exception
	{
		if (!NumberUtil.IsValid (_meanArray = meanArray) || null == _meanArray || 0 == _meanArray.length ||
			!NumberUtil.IsValid (_volatility = volatility) || 0. >= _volatility) {
			throw new Exception ("PathRd Constructor => Invalid Inputs");
		}

		_logNormal = logNormal;
	}

	/**
	 * Indicate if the Random Numbers are Gaussian/LogNormal
	 * 
	 * @return TRUE - The Generated Random Numbers are Log Normal
	 */

	public boolean logNormal()
	{
		return _logNormal;
	}

	/**
	 * Retrieve the R<sup>d</sup> Dimension
	 * 
	 * @return The R<sup>d</sup> Dimension
	 */

	public int dimension()
	{
		return _meanArray.length;
	}

	/**
	 * Retrieve the Array of Means
	 * 
	 * @return The Array of Means
	 */

	public double[] mean()
	{
		return _meanArray;
	}

	/**
	 * Retrieve the Volatility
	 * 
	 * @return The Volatility
	 */

	public double volatility()
	{
		return _volatility;
	}

	/**
	 * Generate the Sequence of Path Realizations
	 * 
	 * @param pathCount Number of Paths
	 * 
	 * @return The Sequence of Path Realizations
	 */

	public double[][] sequence (
		final int pathCount)
	{
		if (0 >= pathCount) {
			return null;
		}

		int dimension = _meanArray.length;
		double[][] sequenceGrid = new double[pathCount][dimension];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			double[] randomArray = SequenceGenerator.Gaussian (dimension);

			if (null == randomArray || dimension != randomArray.length) {
				return null;
			}

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				double wander = _volatility * randomArray[dimensionIndex];

				sequenceGrid[pathIndex][dimensionIndex] = _logNormal ?
					_meanArray[dimensionIndex] * Math.exp (wander) : _meanArray[dimensionIndex] + wander;
			}
		}

		return sequenceGrid;
	}
}
