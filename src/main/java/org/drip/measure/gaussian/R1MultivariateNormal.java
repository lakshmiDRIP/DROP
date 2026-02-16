
package org.drip.measure.gaussian;

import org.drip.measure.state.LabelledRdContinuousDistribution;
import org.drip.measure.state.LabelledRd;
import org.drip.numerical.common.NumberUtil;
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
 * <i>R1MultivariateNormal</i> contains the Generalized Joint Multivariate R<sup>1</sup> Normal
 * 	Distributions. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct a Standard <i>R1MultivariateNormal</i> Instance #1</li>
 * 		<li>Construct a Standard <i>R1MultivariateNormal</i> Instance #2</li>
 * 		<li><i>R1MultivariateNormal</i> Constructor</li>
 * 		<li>Compute the Co-variance of the Distribution</li>
 * 		<li>Compute the Density under the Distribution at the given Variate Array</li>
 * 		<li>Compute the Mean of the Distribution</li>
 * 		<li>Compute the Variance of the Distribution</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian/README.md">R<sup>1</sup> Covariant Gaussian Quadrature</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1MultivariateNormal
	extends LabelledRdContinuousDistribution
{
	private double[] _meanArray = null;
	private JointVariance _jointVariance = null;

	/**
	 * Construct a Standard <i>R1MultivariateNormal</i> Instance #1
	 * 
	 * @param metaRd The R<sup>1</sup> Multivariate Meta Headers
	 * @param meanArray Array of the Univariate Means
	 * @param covarianceMatrix The Covariance Matrix
	 * 
	 * @return The Standard Normal Univariate Distribution
	 */

	public static final R1MultivariateNormal Standard (
		final LabelledRd metaRd,
		final double[] meanArray,
		final double[][] covarianceMatrix)
	{
		try {
			return new R1MultivariateNormal (metaRd, meanArray, new JointVariance (covarianceMatrix));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard <i>R1MultivariateNormal</i> Instance #2
	 * 
	 * @param variateIDArray Array of Variate IDs
	 * @param meanArray Array of the Univariate Means
	 * @param covarianceMatrix The Covariance Matrix
	 * 
	 * @return The Standard Normal Univariate Distribution
	 */

	public static final R1MultivariateNormal Standard (
		final String[] variateIDArray,
		final double[] meanArray,
		final double[][] covarianceMatrix)
	{
		try {
			return new R1MultivariateNormal (
				LabelledRd.FromArray (variateIDArray),
				meanArray,
				new JointVariance (covarianceMatrix)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1MultivariateNormal</i> Constructor
	 * 
	 * @param metaRd The R<sup>1</sup> Multivariate Meta Headers
	 * @param meanArray Array of the Univariate Means
	 * @param jointVariance The Multivariate Covariance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1MultivariateNormal (
		final LabelledRd metaRd,
		final double[] meanArray,
		final JointVariance jointVariance)
		throws Exception
	{
		super (metaRd);

		if (null == (_meanArray = meanArray) || null == (_jointVariance = jointVariance)) {
			throw new Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
		}

		int variateCount = metaRd.count();

		if (variateCount != _meanArray.length ||
			variateCount != _jointVariance.variateCount() ||
			!NumberUtil.IsValid (_meanArray))
		{
			throw new Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Compute the Co-variance of the Distribution
	 * 
	 * @return The Co-variance of the Distribution
	 */

	public JointVariance covariance()
	{
		return _jointVariance;
	}

	/**
	 * Lay out the Left Support of the PDF Range
	 * 
	 * @return Left Support of the PDF Range
	 */

	@Override public double[] leftSupport()
	{
		double[] leftSupportArray = new double[dimension()];

		for (int dimensionIndex = 0; dimensionIndex < leftSupportArray.length; ++dimensionIndex) {
			leftSupportArray[dimensionIndex] = Double.NEGATIVE_INFINITY;
		}

		return leftSupportArray;
	}

	/**
	 * Lay out the Right Support of the PDF Range
	 * 
	 * @return Right Support of the PDF Range
	 */

	@Override public double[] rightSupport()
	{
		double[] rightSupportArray = new double[dimension()];

		for (int dimensionIndex = 0; dimensionIndex < rightSupportArray.length; ++dimensionIndex) {
			rightSupportArray[dimensionIndex] = Double.POSITIVE_INFINITY;
		}

		return rightSupportArray;
	}

	/**
	 * Compute the Density under the Distribution at the given Variate Array
	 * 
	 * @param variateArray Variate Array at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double[] variateArray)
		throws Exception
	{
		if (null == variateArray || !NumberUtil.IsValid (variateArray)) {
			throw new Exception ("R1MultivariateNormal::density => Invalid Inputs");
		}

		double density = 0.;
		double[] variateOffsetArray = new double[_meanArray.length];

		if (_meanArray.length != variateArray.length) {
			throw new Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
		}

		for (int variateIndex = 0; variateIndex < _meanArray.length; ++variateIndex) {
			variateOffsetArray[variateIndex] = variateArray[variateIndex] - _meanArray[variateIndex];
		}

		double[][] precisionMatrix = _jointVariance.precisionMatrix();

		for (int variateIndexI = 0; variateIndexI < _meanArray.length; ++variateIndexI) {
			for (int variateIndexJ = 0; variateIndexJ < _meanArray.length; ++variateIndexJ) {
				density +=
					variateOffsetArray[variateIndexI] * precisionMatrix[variateIndexI][variateIndexJ] *
					variateOffsetArray[variateIndexJ];
			}
		}

		return Math.exp (density) * Math.pow (2. * Math.PI, -0.5 * _meanArray.length);
	}

	/**
	 * Compute the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 */

	@Override public double[] mean()
	{
		return _meanArray;
	}

	/**
	 * Compute the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 */

	@Override public double[] variance()
	{
		return _jointVariance.varianceArray();
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 */

	@Override public double[] random()
	{
		int dimension = dimension();

		double[] jointVarianceArray = _jointVariance.varianceArray();

		double[] uncorrelatedRandomArray = R1UnivariateNormal.Standard().randomArray (dimension);

		double[][] choleskyMatrix = R1MatrixUtil.CholeskyBanachiewiczFactorization
			(_jointVariance.correlationMatrix());

		if (null == choleskyMatrix) {
			return null;
		}

		double[] correlatedRandomArray = new double[dimension];

		for (int dimensionI = 0; dimensionI < dimension; ++dimensionI) {
			correlatedRandomArray[dimensionI] = 0.;

			for (int dimensionJ = 0; dimensionJ < dimension; ++dimensionJ) {
				correlatedRandomArray[dimensionI] +=
					choleskyMatrix[dimensionI][dimensionJ] * uncorrelatedRandomArray[dimensionJ];
			}
		}

		for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
			correlatedRandomArray[dimensionIndex] =
				correlatedRandomArray[dimensionIndex] * _meanArray[dimensionIndex] +
				jointVarianceArray[dimensionIndex];
		}

		return correlatedRandomArray;
	}
}
