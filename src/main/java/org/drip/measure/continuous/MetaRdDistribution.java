
package org.drip.measure.continuous;

import org.drip.function.definition.RdToR1;

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
 * <i>MetaRdDistribution</i> contains the Generalized R<sup>1</sup> Multivariate Distributions. It provides
 * 	the following Functionality:
 *
 *  <ul>
 * 		<li>Retrieve the Multivariate Meta Instance</li>
 * 		<li>Retrieve the Left Edge Bounding Multivariate</li>
 * 		<li>Retrieve the Right Edge Bounding Multivariate</li>
 * 		<li>Convert the Multivariate Density into an R<sup>d</sup> To R<sup>1</sup> Function Instance</li>
 * 		<li>Compute the Cumulative under the Distribution to the given Variate Values</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 Multivariate Instances</li>
 * 		<li>Compute the Expectation of the Specified R<sup>d</sup> To R<sup>1</sup> Function Instance</li>
 * 		<li>Compute the Mean of the Distribution</li>
 * 		<li>Compute the Variance of the Distribution</li>
 * 		<li>Construct the Moment Generating Function</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class MetaRdDistribution
	extends RdDistribution
{
	private MultivariateMeta _multivariateMeta = null;

	protected MetaRdDistribution (
		final MultivariateMeta multivariateMeta)
		throws Exception
	{
		if (null == (_multivariateMeta = multivariateMeta)) {
			throw new Exception ("MetaRdDistribution Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Multivariate Meta Instance
	 * 
	 * @return The Multivariate Meta Instance
	 */

	public MultivariateMeta meta()
	{
		return _multivariateMeta;
	}

	/**
	 * Retrieve the Left Edge Bounding Multivariate
	 * 
	 * @return The Left Edge Bounding Multivariate
	 */

	public double[] leftEdge()
	{
		int variateCount = _multivariateMeta.numVariable();

		double[] leftEdgeArray = new double[variateCount];

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			leftEdgeArray[variateIndex] = Double.MIN_NORMAL;
		}

		return leftEdgeArray;
	}

	/**
	 * Retrieve the Right Edge Bounding Multivariate
	 * 
	 * @return The Right Edge Bounding Multivariate
	 */

	public double[] rightEdge()
	{
		int variateCount = _multivariateMeta.numVariable();

		double[] rightEdgeArray = new double[variateCount];

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			rightEdgeArray[variateIndex] = Double.MAX_VALUE;
		}

		return rightEdgeArray;
	}

	/**
	 * Convert the Multivariate Density into an R<sup>d</sup> To R<sup>1</sup> Function Instance
	 * 
	 * @return The Multivariate Density converted into an R<sup>d</sup> To R<sup>1</sup> Function Instance
	 */

	public RdToR1 densityRdToR1()
	{
		return new RdToR1 (null) {
			@Override public int dimension()
			{
				return _multivariateMeta.numVariable();
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return density (variateArray);
			}
		};
	}

	/**
	 * Compute the Cumulative under the Distribution to the given Variate Values
	 * 
	 * @param variateArray Array of Variate Values to which the Cumulative is to be computed
	 * 
	 * @return The Cumulative
	 * 
	 * @throws Exception Thrown if the Cumulative cannot be computed
	 */

	@Override public double cumulative (
		final double[] variateArray)
		throws Exception
	{
		return densityRdToR1().integrate (leftEdge(), variateArray);
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 Multivariate Instances
	 * 
	 * @param leftVariateArray Left Multivariate Instance to which the Cumulative is to be computed
	 * @param rightVariateArray Right Multivariate Instance to which the Cumulative is to be computed
	 * 
	 * @return The Incremental
	 * 
	 * @throws Exception Thrown if the Incremental cannot be computed
	 */

	@Override public double incremental (
		final double[] leftVariateArray,
		final double[] rightVariateArray)
		throws Exception
	{
		return densityRdToR1().integrate (leftVariateArray, rightVariateArray);
	}

	/**
	 * Compute the Expectation of the Specified R<sup>d</sup> To R<sup>1</sup> Function Instance
	 * 
	 * @param rdToR1 The R<sup>d</sup> To R<sup>1</sup> Function Instance
	 * 
	 * @return The Expectation of the Specified R<sup>d</sup> To R<sup>1</sup> Function Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double expectation (
		final RdToR1 rdToR1)
		throws Exception
	{
		if (null == rdToR1) {
			throw new Exception ("MetaRdDistribution::expectation => Invalid Inputs");
		}

		return new RdToR1 (null) {
			@Override public int dimension()
			{
				return _multivariateMeta.numVariable();
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return density (variateArray) * rdToR1.evaluate (variateArray);
			}
		}.integrate (leftEdge(), rightEdge());
	}

	/**
	 * Compute the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 */

	public double[] mean()
	{
		int variateCount = _multivariateMeta.numVariable();

		double[] meanArray = new double[variateCount];

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			final int variateIndexFinal = variateIndex;

			try {
				meanArray[variateIndex] = expectation (
					new RdToR1 (null) {
						@Override public int dimension()
						{
							return _multivariateMeta.numVariable();
						}

						@Override public double evaluate (
							final double[] adblVariate)
							throws Exception
						{
							return density (adblVariate) * adblVariate[variateIndexFinal];
						}
					}
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return meanArray;
	}

	/**
	 * Compute the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 */

	public double[] variance()
	{
		final double[] meanArray = mean();

		if (null == meanArray) {
			return null;
		}

		final int variateCount = meanArray.length;
		double[] varianceArray = new double[variateCount];

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			final int variateIndexFinal = variateIndex;

			try {
				varianceArray[variateIndex] = expectation (
					new RdToR1 (null) {
						@Override public int dimension()
						{
							return _multivariateMeta.numVariable();
						}

						@Override public double evaluate (
							final double[] variateArray)
							throws Exception
						{
							double secondMoment = 0.;

							for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
								double offset =
									variateArray[variateIndexFinal] - meanArray[variateIndexFinal];
								secondMoment += offset * offset;
							}

							return density (variateArray) * secondMoment;
						}
					}
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return varianceArray;
	}

	/**
	 * Construct the Moment Generating Function
	 * 
	 * @return The Moment Generating Function
	 */

	public RdToR1 momentGeneratingFunction()
	{
		return null;
	}
}
