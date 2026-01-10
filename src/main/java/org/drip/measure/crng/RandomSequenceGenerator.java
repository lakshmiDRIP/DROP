
package org.drip.measure.crng;

import org.drip.measure.gaussian.JointVariance;
import org.drip.measure.gaussian.NormalQuadrature;
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
 * <i>RandomSequenceGenerator</i> generates the specified Univariate Sequence of the Given Distribution Type.
 *
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Backstrom, T., and J. Fischer (2018): Fast Randomization for Distributed Low Bit-rate Coding of
 * 				Speech and Audio <i>IEEE/ACM Transactions on Audio, Speech, and Language Processing</i> <b>26
 * 				(1)</b> 19-30
 * 		</li>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Johnson, N. L., S. Klotz, and N. Balakrishnan (1994): <i>Continuous Univariate Distributions
 * 				<b>1</b> 2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			Lancaster, H, O. (1969): <i>The Chi-Squared Distribution</i> <b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Pillai, N. S. (1026): An Unexpected Encounter with Cauchy and Levy <i>Annals of Statistics</i>
 * 				<b>44 (5)</b> 2089-2097
 * 		</li>
 * 	</ul>
 * 
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a Sequence of Uniform Random Numbers</li>
 * 		<li>Generate a Sequence of Gaussian Random Numbers</li>
 * 		<li>Generate a Sequence of Log Normal Random Numbers</li>
 * 		<li>Generate a Sequence of R<sup>d</sup> Correlated Gaussian Random Numbers</li>
 * 		<li>Generate an Array of Chi-Squared Distributed Random Numbers</li>
 * 		<li>Generate an Array of Scaled Gamma Distributed Random Numbers</li>
 * 		<li>Generate an Array of Chi Distributed Random Numbers</li>
 * 		<li>Generate an Array of Unit Scale Rayleigh Distributed Random Numbers</li>
 * 		<li>Generate an Array of Unit Scale Maxwell Distributed Random Numbers</li>
 * 		<li>Generate an Array of Inverse Chi-Squared Distributed Random Numbers</li>
 * 		<li>Generate an Array of Beta Distributed Random Numbers</li>
 * 		<li>Generate an Array of F Distributed Random Numbers</li>
 * 		<li>Generate a Rank-reduced Chi-Squared Distributed Array</li>
 * 		<li>Generate a Pillai (2016) Special Chi-Squared Distributed Array</li>
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

public class RandomSequenceGenerator
{

	/**
	 * Generate a Sequence of Uniform Random Numbers
	 * 
	 * @param count The Count in the Sequence
	 * 
	 * @return The Sequence of Uniform Random Numbers
	 */

	public static final double[] Uniform (
		final int count)
	{
		if (0 >= count) {
			return null;
		}

		double[] randomArray = new double[count];

		for (int i = 0; i < count; ++i) {
			randomArray[i] = Math.random();
		}

		return randomArray;
	}

	/**
	 * Generate a Sequence of Gaussian Random Numbers
	 * 
	 * @param count The Count in the Sequence
	 * 
	 * @return The Sequence of Gaussian Random Numbers
	 */

	public static final double[] Gaussian (
		final int count)
	{
		if (0 >= count) {
			return null;
		}

		double[] randomArray = new double[count];

		for (int i = 0; i < count; ++i) {
			try {
				randomArray[i] = NormalQuadrature.Random();
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return randomArray;
	}

	/**
	 * Generate a Sequence of Log Normal Random Numbers
	 * 
	 * @param count The Count in the Sequence
	 * 
	 * @return The Sequence of Log Normal Random Numbers
	 */

	public static final double[] LogNormal (
		final int count)
	{
		if (0 >= count) {
			return null;
		}

		double[] randomArray = new double[count];

		double normalizer = 1. / Math.sqrt (Math.E);

		for (int i = 0; i < count; ++i) {
			try {
				randomArray[i] = Math.exp (NormalQuadrature.Random()) * normalizer;
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return randomArray;
	}

	/**
	 * Generate a Sequence of R<sup>d</sup> Correlated Gaussian Random Numbers
	 * 
	 * @param count The Count in the Sequence
	 * @param correlationMatrix The Correlation Matrix
	 * 
	 * @return The Sequence of R<sup>d</sup> Correlated Gaussian Random Numbers
	 */

	public static final double[][] GaussianJoint (
		final int count,
		final double[][] correlationMatrix)
	{
		if (0 >= count) {
			return null;
		}

		double[][] choleskyMatrix = R1MatrixUtil.CholeskyBanachiewiczFactorization (correlationMatrix);

		if (null == choleskyMatrix) {
			return null;
		}

		int dimension = choleskyMatrix.length;
		double[][] randomArraySequence = new double[count][];

		for (int k = 0; k < count; ++k) {
			double[] uncorrelatedRandomArray = Gaussian (dimension);

			if (null == uncorrelatedRandomArray || dimension != uncorrelatedRandomArray.length) {
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

			randomArraySequence[k] = correlatedRandomArray;
		}

		return randomArraySequence;
	}

	/**
	 * Generate an Array of Chi-Squared Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * @param degreesOfFreedom Degrees of Freedom
	 * 
	 * @return Array of Chi-Squared Distributed Random Numbers
	 */

	public static final double[] ChiSquared (
		final int count,
		final int degreesOfFreedom)
	{
		if (0 >= degreesOfFreedom) {
			return null;
		}

		double[] chiSquaredArray = new double[count];

		for (int index = 0; index < count; ++index) {
			double sumOfStandardNormalSquares = 0.;

			for (int drawIndex = 0; drawIndex < degreesOfFreedom; ++drawIndex) {
				try {
					double randomStandardNormal = NormalQuadrature.InverseCDF (Math.random());

					sumOfStandardNormalSquares = sumOfStandardNormalSquares +
						randomStandardNormal * randomStandardNormal;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			chiSquaredArray[index] = sumOfStandardNormalSquares;
		}

		return chiSquaredArray;
	}

	/**
	 * Generate an Array of Scaled Gamma Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param scale Scale Parameter
	 * 
	 * @return Array of Scaled Gamma Distributed Random Numbers
	 */

	public static final double[] ScaledGamma (
		final int count,
		final int degreesOfFreedom,
		final double scale)
	{
		if (!NumberUtil.IsValid (scale) || 0. >= scale) {
			return null;
		}

		double[] chiSquaredArray =  ChiSquared (count, degreesOfFreedom);

		if (null == chiSquaredArray) {
			return null;
		}

		double[] scaledGammaArray = new double[count];

		for (int index = 0; index < count; ++index) {
			scaledGammaArray[index] = scale * chiSquaredArray[index];
		}

		return scaledGammaArray;
	}

	/**
	 * Generate an Array of Chi Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * @param degreesOfFreedom Degrees of Freedom
	 * 
	 * @return Array of Chi Distributed Random Numbers
	 */

	public static final double[] Chi (
		final int count,
		final int degreesOfFreedom)
	{
		double[] chiSquaredArray =  ChiSquared (count, degreesOfFreedom);

		if (null == chiSquaredArray) {
			return null;
		}

		double[] chiArray = new double[count];

		for (int index = 0; index < count; ++index) {
			chiArray[index] = Math.sqrt (chiSquaredArray[index]);
		}

		return chiArray;
	}

	/**
	 * Generate an Array of Unit Scale Rayleigh Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * 
	 * @return Array of Unit Scale Rayleigh Distributed Random Numbers
	 */

	public static final double[] UnitScaleRayleigh (
		final int count)
	{
		double[] chiSquaredArray = ChiSquared (count, 2);

		if (null == chiSquaredArray) {
			return null;
		}

		double[] unitScaleRayleighArray = new double[count];

		for (int index = 0; index < count; ++index) {
			unitScaleRayleighArray[index] = Math.sqrt (chiSquaredArray[index]);
		}

		return unitScaleRayleighArray;
	}

	/**
	 * Generate an Array of Unit Scale Maxwell Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * 
	 * @return Array of Unit Scale Maxwell Distributed Random Numbers
	 */

	public static final double[] UnitScaleMaxwell (
		final int count)
	{
		double[] chiSquaredArray = ChiSquared (count, 3);

		if (null == chiSquaredArray) {
			return null;
		}

		double[] unitScaleMaxwellArray = new double[count];

		for (int index = 0; index < count; ++index) {
			unitScaleMaxwellArray[index] = Math.sqrt (chiSquaredArray[index]);
		}

		return unitScaleMaxwellArray;
	}

	/**
	 * Generate an Array of Inverse Chi-Squared Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * @param degreesOfFreedom Degrees of Freedom
	 * 
	 * @return Array of Inverse Chi-Squared Distributed Random Numbers
	 */

	public static final double[] InverseChiSquared (
		final int count,
		final int degreesOfFreedom)
	{
		double[] chiSquaredArray = ChiSquared (count, degreesOfFreedom);

		if (null == chiSquaredArray) {
			return null;
		}

		double[] inverseChiSquaredArray = new double[count];

		for (int index = 0; index < count; ++index) {
			inverseChiSquaredArray[index] = 1. / chiSquaredArray[index];
		}

		return inverseChiSquaredArray;
	}

	/**
	 * Generate an Array of Beta Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * @param degreesOfFreedom1 Degrees of Freedom #1
	 * @param degreesOfFreedom2 Degrees of Freedom #2
	 * 
	 * @return Array of Beta Distributed Random Numbers
	 */

	public static final double[] Beta (
		final int count,
		final int degreesOfFreedom1,
		final int degreesOfFreedom2)
	{
		double[] chiSquaredArray1 = ChiSquared (count, degreesOfFreedom1);

		double[] chiSquaredArray2 = ChiSquared (count, degreesOfFreedom2);

		if (null == chiSquaredArray1 || null == chiSquaredArray2) {
			return null;
		}

		double[] betaArray = new double[count];

		for (int index = 0; index < count; ++index) {
			betaArray[index] = chiSquaredArray1[index] / (chiSquaredArray1[index] + chiSquaredArray2[index]);
		}

		return betaArray;
	}

	/**
	 * Generate an Array of F Distributed Random Numbers
	 * 
	 * @param count Array Count
	 * @param degreesOfFreedom1 Degrees of Freedom #1
	 * @param degreesOfFreedom2 Degrees of Freedom #2
	 * 
	 * @return Array of F Distributed Random Numbers
	 */

	public static final double[] F (
		final int count,
		final int degreesOfFreedom1,
		final int degreesOfFreedom2)
	{
		double[] chiSquaredArray1 = ChiSquared (count, degreesOfFreedom1);

		double[] chiSquaredArray2 = ChiSquared (count, degreesOfFreedom2);

		if (null == chiSquaredArray1 || null == chiSquaredArray2) {
			return null;
		}

		double[] fArray = new double[count];

		for (int index = 0; index < count; ++index) {
			fArray[index] = (chiSquaredArray1[index] * degreesOfFreedom2) /
				(chiSquaredArray2[index] * degreesOfFreedom1);
		}

		return fArray;
	}

	/**
	 * Generate a Rank-reduced Chi-Squared Distributed Array
	 * 
	 * @param count Array Count
	 * @param covarianceMatrix The Covariance Matrix
	 * 
	 * @return Rank-reduced Chi-Squared Distributed Array
	 */

	public static final double[] RankReducedChiSquare (
		final int count,
		final double[][] covarianceMatrix)
	{
		JointVariance covariance = null;
		double[] rankReducedChiSquare = new double[count];

		try {
			covariance = new JointVariance (covarianceMatrix);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		double[][] gaussianJointArraySequence = GaussianJoint (count, covariance.correlationMatrix());

		if (null == gaussianJointArraySequence) {
			return null;
		}

		double[][] precisionMatrix = covariance.precisionMatrix();

		for (int index = 0; index < count; ++index) {
			try {
				rankReducedChiSquare[index] = R1MatrixUtil.DotProduct (
					gaussianJointArraySequence[index],
					R1MatrixUtil.Product (precisionMatrix, gaussianJointArraySequence[index])
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return rankReducedChiSquare;
	}

	/**
	 * Generate a Pillai (2016) Special Chi-Squared Distributed Array
	 * 
	 * @param count Array Count
	 * @param covarianceMatrix The Covariance Matrix
	 * @param weightArray Array of Weights
	 * 
	 * @return Pillai (2016) Special Chi-Squared Distributed Array
	 */

	public static final double[] PillaiSpecialChiSquare (
		final int count,
		final double[][] covarianceMatrix,
		final double[] weightArray)
	{
		if (!NumberUtil.NormalizedPositive (weightArray)) {
			return null;
		}

		JointVariance covariance = null;
		int pillaiVectorSize = weightArray.length;
		double[] pillaiSpecialChiSquare = new double[count];

		try {
			covariance = new org.drip.measure.gaussian.JointVariance (covarianceMatrix);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (pillaiVectorSize != covarianceMatrix.length) {
			return null;
		}

		double[][] gaussianJointArraySequence = GaussianJoint (count, covariance.correlationMatrix());

		if (null == gaussianJointArraySequence) {
			return null;
		}

		for (int index = 0; index < count; ++index)
		{
			double[] pillaiVector = new double[pillaiVectorSize];

			for (int pillaiVectorIndex = 0; pillaiVectorIndex < pillaiVectorSize; ++pillaiVectorIndex) {
				pillaiVector[pillaiVectorIndex] = weightArray[pillaiVectorIndex] /
					gaussianJointArraySequence[index][pillaiVectorIndex];
			}

			try {
				pillaiSpecialChiSquare[index] = 1. / R1MatrixUtil.DotProduct (
					pillaiVector,
					R1MatrixUtil.Product (covarianceMatrix, pillaiVector)
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return pillaiSpecialChiSquare;
	}
}
