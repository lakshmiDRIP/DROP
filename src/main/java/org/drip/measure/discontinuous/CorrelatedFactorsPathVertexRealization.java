
package org.drip.measure.discontinuous;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.numerical.linearalgebra.R1MatrixUtil;

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
 * <i>CorrelatedFactorsPathVertexRealization</i> generates Correlated R<sup>d</sup> Random Numbers at the
 *  specified Vertexes, over the Specified Paths. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>QuadraticResampler</i> Constructor</li>
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

public class CorrelatedFactorsPathVertexRealization
{
	private int _nodeCount = -1;
	private int _simulationCount = -1;
	private boolean _applyAntithetic = false;
	private double[][] _choleskyMatrix = null;
	private double[][] _correlationMatrix = null;
	private QuadraticResampler _quadraticResampler = null;
	private RandomNumberGenerator _randomNumberGenerator = null;

	/**
	 * <i>CorrelatedFactorsPathVertexRealization</i> Constructor
	 * 
	 * @param randomNumberGenerator The Random Number Generator
	 * @param correlationMatrix The Correlation Matrix
	 * @param nodeCount Number of Vertex Nodes
	 * @param simulationCount Number of Simulated Paths
	 * @param applyAntithetic TRUE - Apply Antithetic Variables Based Variance Reduction
	 * @param quadraticResampler Quadratic Resampler Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CorrelatedFactorsPathVertexRealization (
		final RandomNumberGenerator randomNumberGenerator,
		final double[][] correlationMatrix,
		final int nodeCount,
		final int simulationCount,
		final boolean applyAntithetic,
		final QuadraticResampler quadraticResampler)
		throws Exception
	{
		if (null == (_randomNumberGenerator = randomNumberGenerator) ||
			0 >= (_nodeCount = nodeCount) ||
			0 >= (_simulationCount = simulationCount))
		{
			throw new Exception ("CorrelatedFactorsPathVertexRealization Constructor => Invalid Inputs");
		}

		_applyAntithetic = applyAntithetic;
		_quadraticResampler = quadraticResampler;

		if (null == (
			_choleskyMatrix = R1MatrixUtil.CholeskyBanachiewiczFactorization (
				_correlationMatrix = correlationMatrix
			)
		))
		{
			throw new Exception ("CorrelatedFactorsPathVertexRealization Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Random Number Generator
	 * 
	 * @return The Random Number Generator Instance
	 */

	public RandomNumberGenerator randomNumberGenerator()
	{
		return _randomNumberGenerator;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		return _correlationMatrix;
	}

	/**
	 * Retrieve the Cholesky Matrix
	 * 
	 * @return The Cholesky Matrix
	 */

	public double[][] choleskyMatrix()
	{
		return _choleskyMatrix;
	}

	/**
	 * Retrieve the Number of Vertex Nodes
	 * 
	 * @return The Number of Vertex Nodes
	 */

	public int nodeCount()
	{
		return _nodeCount;
	}

	/**
	 * Retrieve the Number of Simulation Paths
	 * 
	 * @return The Number of Simulation Paths
	 */

	public int simulationCount()
	{
		return _simulationCount;
	}

	/**
	 * Retrieve the Number of Factor Dimensions
	 * 
	 * @return The Number of Dimensions
	 */

	public int dimensionCount()
	{
		return _choleskyMatrix.length;
	}

	/**
	 * Indicate if the Antithetic Variable Generation is to be applied
	 * 
	 * @return TRUE - Apply Antithetic Variables Based Variance Reduction
	 */

	public boolean applyAntithetic()
	{
		return _applyAntithetic;
	}

	/**
	 * Retrieve the Quadratic Resampler Instance
	 * 
	 * @return The Quadratic Resampler Instance
	 */

	public QuadraticResampler quadraticResampler()
	{
		return _quadraticResampler;
	}

	/**
	 * Generate a Single R<sup>d</sup> Vertex Node Realization
	 * 
	 * @return Single R<sup>d</sup> Vertex Node Realization
	 */

	public double[] nodeRd()
	{
		double[] correlatedRd = new double[_choleskyMatrix.length];
		double[] uncorrelatedRd = new double[_choleskyMatrix.length];

		for (int factorIndex = 0; factorIndex < _choleskyMatrix.length; ++factorIndex) {
			try {
				uncorrelatedRd[factorIndex] =
					NormalQuadrature.InverseCDF (_randomNumberGenerator.nextDouble01());
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		for (int factorIndexI = 0; factorIndexI < _choleskyMatrix.length; ++factorIndexI) {
			correlatedRd[factorIndexI] = 0.;

			for (int factorIndexJ = 0; factorIndexJ < _choleskyMatrix.length; ++factorIndexJ) {
				correlatedRd[factorIndexI] +=
					_choleskyMatrix[factorIndexI][factorIndexJ] * uncorrelatedRd[factorIndexJ];
			}
		}

		return correlatedRd;
	}

	/**
	 * Generate an Antithetic R<sup>d</sup> Vertex Pair Realization
	 * 
	 * @return Antithetic R<sup>d</sup> Vertex Pair Realization
	 */

	public double[][] antitheticNodeRd()
	{
		double[] nodeRd = nodeRd();

		double[] antitheticNodeRd = new double[_choleskyMatrix.length];

		for (int factorIndex = 0; factorIndex < _choleskyMatrix.length; ++factorIndex) {
			antitheticNodeRd[factorIndex] = -1. * nodeRd[factorIndex];
		}

		return new double[][] {nodeRd, antitheticNodeRd};
	}

	/**
	 * Generate a Single Straight Path R<sup>d</sup> Vertex Realization
	 * 
	 * @return Single Straight Path R<sup>d</sup> Vertex Realization
	 */

	public org.drip.measure.discontinuous.VertexRd straightPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd vertexRealization = new
			org.drip.measure.discontinuous.VertexRd();

		for (int i = 0; i < _nodeCount; ++i) {
			if (!vertexRealization.add (i, nodeRd())) return null;
		}

		return null != _quadraticResampler ? org.drip.measure.discontinuous.VertexRd.FromFlatForm (_quadraticResampler.transform
			(vertexRealization.flatform())) : vertexRealization;
	}

	/**
	 * Generate an Antithetic Pair Path R<sup>d</sup> Vertex Realizations
	 * 
	 * @return Antithetic Pair Path R<sup>d</sup> Vertex Realizations
	 */

	public org.drip.measure.discontinuous.VertexRd[] antitheticPairPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd straightVertexRealization = new
			org.drip.measure.discontinuous.VertexRd();

		org.drip.measure.discontinuous.VertexRd antitheticVertexRealization = new
			org.drip.measure.discontinuous.VertexRd();

		for (int i = 0; i < _nodeCount; ++i) {
			double[][] aadblStraightAntitheticRealization = antitheticNodeRd();

			if (!straightVertexRealization.add (i, aadblStraightAntitheticRealization[0]) ||
				!antitheticVertexRealization.add (i, aadblStraightAntitheticRealization[1]))
				return null;
		}

		if (null == _quadraticResampler)
			return new org.drip.measure.discontinuous.VertexRd[] {straightVertexRealization,
				antitheticVertexRealization};

		return new org.drip.measure.discontinuous.VertexRd[] {org.drip.measure.discontinuous.VertexRd.FromFlatForm
			(_quadraticResampler.transform (straightVertexRealization.flatform())),
				org.drip.measure.discontinuous.VertexRd.FromFlatForm (_quadraticResampler.transform
					(antitheticVertexRealization.flatform()))};
	}

	/**
	 * Generate Straight Multi-Path R<sup>d</sup> Vertex Realizations Array
	 * 
	 * @return Straight Multi-Path R<sup>d</sup> Vertex Realizations Array
	 */

	public org.drip.measure.discontinuous.VertexRd[] straightMultiPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd[] aVertexRd = new
			org.drip.measure.discontinuous.VertexRd[_simulationCount];

		for (int i = 0; i < _simulationCount; ++i) {
			if (null == (aVertexRd[i] = straightPathVertexRd())) return null;
		}

		return aVertexRd;
	}

	/**
	 * Generate Antithetic Multi-Path R<sup>d</sup> Vertex Realizations Array
	 * 
	 * @return Antithetic Multi-Path R<sup>d</sup> Vertex Realizations Array
	 */

	public org.drip.measure.discontinuous.VertexRd[] antitheticMultiPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd[] aVertexRd = new
			org.drip.measure.discontinuous.VertexRd[_simulationCount];

		int iNumGeneration = _simulationCount / 2;

		for (int i = 0; i < iNumGeneration; ++i) {
			org.drip.measure.discontinuous.VertexRd[] aAntitheticVertexRd = antitheticPairPathVertexRd();

			if (null == aAntitheticVertexRd || 2 != aAntitheticVertexRd.length) return null;

			if (null == (aVertexRd[i] = aAntitheticVertexRd[0]) || null == (aVertexRd[i + iNumGeneration] =
				aAntitheticVertexRd[1]))
				return null;
		}

		if (1 == (_simulationCount % 2)) aVertexRd[_simulationCount - 1] = straightPathVertexRd();

		return aVertexRd;
	}

	/**
	 * Generate Multi-Path R<sup>d</sup> Vertex Realizations Array
	 * 
	 * @return Multi-Path R<sup>d</sup> Vertex Realizations Array
	 */

	public org.drip.measure.discontinuous.VertexRd[] multiPathVertexRd()
	{
		return _applyAntithetic ? antitheticMultiPathVertexRd() : straightMultiPathVertexRd();
	}
}
