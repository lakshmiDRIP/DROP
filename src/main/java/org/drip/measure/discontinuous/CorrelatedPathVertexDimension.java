
package org.drip.measure.discontinuous;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>CorrelatedPathVertexDimension</i> generates Correlated R^d Random Numbers at the specified Vertexes,
 * over the Specified Paths.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete/README.md">Antithetic, Quadratically Re-sampled, De-biased Distribution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CorrelatedPathVertexDimension {
	private int _iNumPath = -1;
	private int _iNumVertex = -1;
	private double[][] _aadblCholesky = null;
	private boolean _bApplyAntithetic = false;
	private double[][] _aadblCorrelation = null;
	private org.drip.measure.crng.RandomNumberGenerator _rng = null;
	private org.drip.measure.discontinuous.QuadraticResampler _qr = null;

	/**
	 * CorrelatedPathVertexDimension Constructor
	 * 
	 * @param rng The Random Number Generator
	 * @param aadblCorrelation The Correlation Matrix
	 * @param iNumVertex Number of Vertexes
	 * @param iNumPath Number of Paths
	 * @param bApplyAntithetic TRUE - Apply Antithetic Variables Based Variance Reduction
	 * @param qr Quadratic Resampler Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CorrelatedPathVertexDimension (
		final org.drip.measure.crng.RandomNumberGenerator rng,
		final double[][] aadblCorrelation,
		final int iNumVertex,
		final int iNumPath,
		final boolean bApplyAntithetic,
		final org.drip.measure.discontinuous.QuadraticResampler qr)
		throws java.lang.Exception
	{
		if (null == (_rng = rng) || 0 >= (_iNumVertex = iNumVertex) || 0 >= (_iNumPath = iNumPath))
			throw new java.lang.Exception ("CorrelatedPathVertexDimension Constructor => Invalid Inputs");

		_qr = qr;
		_bApplyAntithetic = bApplyAntithetic;

		if (null == (_aadblCholesky = org.drip.numerical.linearalgebra.R1MatrixUtil.CholeskyBanachiewiczFactorization
			(_aadblCorrelation = aadblCorrelation)))
			throw new java.lang.Exception ("CorrelatedPathVertexDimension Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Random Number Generator
	 * 
	 * @return The Random Number Generator Instance
	 */

	public org.drip.measure.crng.RandomNumberGenerator randomNumberGenerator()
	{
		return _rng;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlation()
	{
		return _aadblCorrelation;
	}

	/**
	 * Retrieve the Cholesky Matrix
	 * 
	 * @return The Cholesky Matrix
	 */

	public double[][] cholesky()
	{
		return _aadblCholesky;
	}

	/**
	 * Retrieve the Number of Vertexes
	 * 
	 * @return The Number of Vertexes
	 */

	public int numVertex()
	{
		return _iNumVertex;
	}

	/**
	 * Retrieve the Number of Paths
	 * 
	 * @return The Number of Paths
	 */

	public int numPath()
	{
		return _iNumPath;
	}

	/**
	 * Retrieve the Number of Dimensions
	 * 
	 * @return The Number of Dimensions
	 */

	public int numDimension()
	{
		return _aadblCholesky.length;
	}

	/**
	 * Indicate if the Antithetic Variable Generation is to be applied
	 * 
	 * @return TRUE - Apply Antithetic Variables Based Variance Reduction
	 */

	public boolean applyAntithetic()
	{
		return _bApplyAntithetic;
	}

	/**
	 * Retrieve the Quadratic Resampler Instance
	 * 
	 * @return The Quadratic Resampler Instance
	 */

	public org.drip.measure.discontinuous.QuadraticResampler quadraticResampler()
	{
		return _qr;
	}

	/**
	 * Generate a Straight Single R^d Vertex Realization
	 * 
	 * @return Straight Single R^d Vertex Realization
	 */

	public double[] straightVertexRealization()
	{
		int iDimension = _aadblCholesky.length;
		double[] adblRdCorrelated = new double[iDimension];
		double[] adblRdUncorrelated = new double[iDimension];

		for (int i = 0; i < iDimension; ++i) {
			try {
				adblRdUncorrelated[i] = org.drip.measure.gaussian.NormalQuadrature.InverseCDF
					(_rng.nextDouble01());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		for (int i = 0; i < iDimension; ++i) {
			adblRdCorrelated[i] = 0.;

			for (int j = 0; j < iDimension; ++j)
				adblRdCorrelated[i] += _aadblCholesky[i][j] * adblRdUncorrelated[j];
		}

		return adblRdCorrelated;
	}

	/**
	 * Generate an Antithetic R^d Vertex Pair Realization
	 * 
	 * @return Antithetic R^d Vertex Pair Realization
	 */

	public double[][] antitheticVertexPairRealization()
	{
		double[] adblStraightVertexRealization = straightVertexRealization();

		int iDimension = _aadblCholesky.length;
		double[] adblAntitheticVertexRealization = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblAntitheticVertexRealization[i] = -1. * adblStraightVertexRealization[i];

		return new double[][] {adblStraightVertexRealization, adblAntitheticVertexRealization};
	}

	/**
	 * Generate a Single Straight Path R^d Vertex Realization
	 * 
	 * @return Single Straight Path R^d Vertex Realization
	 */

	public org.drip.measure.discontinuous.VertexRd straightPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd vertexRealization = new
			org.drip.measure.discontinuous.VertexRd();

		for (int i = 0; i < _iNumVertex; ++i) {
			if (!vertexRealization.add (i, straightVertexRealization())) return null;
		}

		return null != _qr ? org.drip.measure.discontinuous.VertexRd.FromFlatForm (_qr.transform
			(vertexRealization.flatform())) : vertexRealization;
	}

	/**
	 * Generate an Antithetic Pair Path R^d Vertex Realizations
	 * 
	 * @return Antithetic Pair Path R^d Vertex Realizations
	 */

	public org.drip.measure.discontinuous.VertexRd[] antitheticPairPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd straightVertexRealization = new
			org.drip.measure.discontinuous.VertexRd();

		org.drip.measure.discontinuous.VertexRd antitheticVertexRealization = new
			org.drip.measure.discontinuous.VertexRd();

		for (int i = 0; i < _iNumVertex; ++i) {
			double[][] aadblStraightAntitheticRealization = antitheticVertexPairRealization();

			if (!straightVertexRealization.add (i, aadblStraightAntitheticRealization[0]) ||
				!antitheticVertexRealization.add (i, aadblStraightAntitheticRealization[1]))
				return null;
		}

		if (null == _qr)
			return new org.drip.measure.discontinuous.VertexRd[] {straightVertexRealization,
				antitheticVertexRealization};

		return new org.drip.measure.discontinuous.VertexRd[] {org.drip.measure.discontinuous.VertexRd.FromFlatForm
			(_qr.transform (straightVertexRealization.flatform())),
				org.drip.measure.discontinuous.VertexRd.FromFlatForm (_qr.transform
					(antitheticVertexRealization.flatform()))};
	}

	/**
	 * Generate Straight Multi-Path R^d Vertex Realizations Array
	 * 
	 * @return Straight Multi-Path R^d Vertex Realizations Array
	 */

	public org.drip.measure.discontinuous.VertexRd[] straightMultiPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd[] aVertexRd = new
			org.drip.measure.discontinuous.VertexRd[_iNumPath];

		for (int i = 0; i < _iNumPath; ++i) {
			if (null == (aVertexRd[i] = straightPathVertexRd())) return null;
		}

		return aVertexRd;
	}

	/**
	 * Generate Antithetic Multi-Path R^d Vertex Realizations Array
	 * 
	 * @return Antithetic Multi-Path R^d Vertex Realizations Array
	 */

	public org.drip.measure.discontinuous.VertexRd[] antitheticMultiPathVertexRd()
	{
		org.drip.measure.discontinuous.VertexRd[] aVertexRd = new
			org.drip.measure.discontinuous.VertexRd[_iNumPath];

		int iNumGeneration = _iNumPath / 2;

		for (int i = 0; i < iNumGeneration; ++i) {
			org.drip.measure.discontinuous.VertexRd[] aAntitheticVertexRd = antitheticPairPathVertexRd();

			if (null == aAntitheticVertexRd || 2 != aAntitheticVertexRd.length) return null;

			if (null == (aVertexRd[i] = aAntitheticVertexRd[0]) || null == (aVertexRd[i + iNumGeneration] =
				aAntitheticVertexRd[1]))
				return null;
		}

		if (1 == (_iNumPath % 2)) aVertexRd[_iNumPath - 1] = straightPathVertexRd();

		return aVertexRd;
	}

	/**
	 * Generate Multi-Path R^d Vertex Realizations Array
	 * 
	 * @return Multi-Path R^d Vertex Realizations Array
	 */

	public org.drip.measure.discontinuous.VertexRd[] multiPathVertexRd()
	{
		return _bApplyAntithetic ? antitheticMultiPathVertexRd() : straightMultiPathVertexRd();
	}
}
