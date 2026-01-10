
package org.drip.spaces.metric;

import org.drip.function.definition.RdToR1;
import org.drip.function.definition.VariateOutputPair;
import org.drip.measure.distribution.RdContinuous;
import org.drip.spaces.tensor.R1ContinuousVector;
import org.drip.spaces.tensor.RdContinuousVector;

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
 * <i>RdBanach</i> implements the Normed, Bounded/Unbounded Continuous l<sub>p</sub> R<sup>d</sup> Spaces.
 * 	The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct the Standard l<sup>p</sup> R<sup>d</sup> Continuous Banach Space Instance</li>
 * 		<li>Construct the Supremum (i.e., l<sup>Infinity</sup>) R<sup>d</sup> Continuous Banach Space Instance</li>
 * 		<li><i>RdBanach</i> Space Constructor</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric/README.md">Hilbert/Banach Normed Metric Spaces</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdBanach extends RdContinuousVector implements RdNormed
{
	private int _pNorm = -1;
	private RdContinuous _rdDistribution = null;

	/**
	 * Construct the Standard l<sup>p</sup> R<sup>d</sup> Continuous Banach Space Instance
	 * 
	 * @param iDimension The Space Dimension
	 * @param distRd The R<sup>d</sup> Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l<sup>p</sup> R<sup>d</sup> Continuous Banach Space Instance
	 */

	public static final RdBanach StandardBanach (
		final int iDimension,
		final org.drip.measure.distribution.RdContinuous distRd,
		final int iPNorm)
	{
		try {
			return 0 >= iDimension ? null : new RdBanach (new
				org.drip.spaces.tensor.R1ContinuousVector[iDimension], distRd, iPNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Supremum (i.e., l<sup>Infinity</sup>) R<sup>d</sup> Continuous Banach Space Instance
	 * 
	 * @param iDimension The Space Dimension
	 * @param distRd The R<sup>d</sup> Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l<sup>Infinity</sup>) R<sup>d</sup> Continuous Banach Space Instance
	 */

	public static final RdBanach SupremumBanach (
		final int iDimension,
		final org.drip.measure.distribution.RdContinuous distRd)
	{
		try {
			return 0 >= iDimension ? null : new RdBanach (new
				org.drip.spaces.tensor.R1ContinuousVector[iDimension], distRd, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RdBanach Space Constructor
	 * 
	 * @param r1ContinuousVectorArray Array of R<sup>1</sup> Continuous Vector
	 * @param rdDistribution The R<sup>d</sup> Borel Sigma Measure
	 * @param pNorm The p-norm of the Space
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdBanach (
		final R1ContinuousVector[] r1ContinuousVectorArray,
		final RdContinuous rdDistribution,
		final int pNorm)
		throws Exception
	{
		super (r1ContinuousVectorArray);

		if (0 > (_pNorm = pNorm)) {
			throw new Exception ("RdBanach Constructor: Invalid p-norm");
		}

		_rdDistribution = rdDistribution;
	}

	@Override public int pNorm()
	{
		return _pNorm;
	}

	@Override public RdContinuous borelSigmaMeasure()
	{
		return _rdDistribution;
	}

	@Override public double sampleSupremumNorm (
		final double[] xArray)
		throws Exception
	{
		if (!validateInstance (xArray)) {
			throw new Exception ("RdBanach::sampleSupremumNorm => Invalid Inputs");
		}

		int dimension = xArray.length;

		double norm = Math.abs (xArray[0]);

		for (int i = 1; i < dimension; ++i) {
			double absoluteX = Math.abs (xArray[i]);

			norm = norm > absoluteX ? norm : absoluteX;
		}

		return norm;
	}

	@Override public double sampleMetricNorm (
		final double[] xArray)
		throws Exception
	{
		if (!validateInstance (xArray)) {
			throw new Exception ("RdBanach::sampleMetricNorm => Invalid Inputs");
		}

		if (Integer.MAX_VALUE == _pNorm) {
			return sampleSupremumNorm (xArray);
		}

		double norm = 0.;
		int dimension = xArray.length;

		for (int i = 0; i < dimension; ++i) {
			norm += Math.pow (Math.abs (xArray[i]), _pNorm);
		}

		return Math.pow (norm, 1. / _pNorm);
	}

	@Override public double[] populationMode()
	{
		if (null == _rdDistribution) {
			return null;
		}

		VariateOutputPair modeVariateOutputPair = new RdToR1 (null) {
			@Override public int dimension()
			{
				return RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] xArray)
				throws Exception
			{
				return _rdDistribution.density (xArray);
			}
		}.maxima (leftDimensionEdge(), rightDimensionEdge());

		return null == modeVariateOutputPair ? null : modeVariateOutputPair.variates();
	}

	@Override public double populationSupremumNorm()
		throws Exception
	{
		if (null == _rdDistribution) {
			throw new Exception ("RdBanach::populationSupremumNorm => Invalid Inputs");
		}

		return sampleSupremumNorm (populationMode());
	}

	@Override public double populationMetricNorm()
		throws Exception
	{
		if (null == _rdDistribution) {
			throw new Exception ("RdBanach::populationMetricNorm => Invalid Inputs");
		}

		if (Integer.MAX_VALUE == _pNorm) {
			return sampleSupremumNorm (populationMode());
		}

		return Math.pow (
			new RdToR1 (null) {
				@Override public int dimension()
				{
					return RdToR1.DIMENSION_NOT_FIXED;
				}

				@Override public double evaluate (
					final double[] xArray)
					throws Exception
				{
					double norm = 0.;
					int dimension = xArray.length;

					for (int i = 0; i < dimension; ++i) {
						norm += Math.pow (Math.abs (xArray[i]), _pNorm);
					}

					return norm * _rdDistribution.density (xArray);
				}
			}.integrate (leftDimensionEdge(), rightDimensionEdge()), 1. / _pNorm
		);
	}

	@Override public double borelMeasureSpaceExpectation (
		final RdToR1 rdToR1Function)
		throws Exception
	{
		if (null == _rdDistribution || null == rdToR1Function) {
			throw new Exception ("RdBanach::borelMeasureSpaceExpectation => Invalid Inputs");
		}

		return new RdToR1 (null) {
			@Override public int dimension()
			{
				return RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] xArray)
				throws Exception
			{
				return rdToR1Function.evaluate (xArray) * _rdDistribution.density (xArray);
			}
		}.integrate (leftDimensionEdge(), rightDimensionEdge());
	}
}
