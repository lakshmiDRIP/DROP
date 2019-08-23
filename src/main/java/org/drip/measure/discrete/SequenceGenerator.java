
package org.drip.measure.discrete;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>SequenceGenerator</i> generates the specified Univariate Sequence of the Given Distribution Type.
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete">Discrete</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SequenceGenerator
{

	/**
	 * Generate a Sequence of Uniform Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * 
	 * @return The Sequence of Uniform Random Numbers
	 */

	public static final double[] Uniform (
		final int iCount)
	{
		if (0 >= iCount) return null;

		double[] adblRandom = new double[iCount];

		for (int i = 0; i < iCount; ++i)
			adblRandom[i] = java.lang.Math.random();

		return adblRandom;
	}

	/**
	 * Generate a Sequence of Gaussian Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * 
	 * @return The Sequence of Gaussian Random Numbers
	 */

	public static final double[] Gaussian (
		final int iCount)
	{
		if (0 >= iCount) return null;

		double[] adblRandom = new double[iCount];

		for (int i = 0; i < iCount; ++i) {
			try {
				adblRandom[i] = org.drip.measure.gaussian.NormalQuadrature.Random();
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblRandom;
	}

	/**
	 * Generate a Sequence of Log Normal Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * 
	 * @return The Sequence of Log Normal Random Numbers
	 */

	public static final double[] LogNormal (
		final int iCount)
	{
		if (0 >= iCount) return null;

		double[] adblRandom = new double[iCount];

		double dblNormalizer = 1. / java.lang.Math.sqrt (java.lang.Math.E);

		for (int i = 0; i < iCount; ++i) {
			try {
				adblRandom[i] = java.lang.Math.exp (org.drip.measure.gaussian.NormalQuadrature.Random()) *
					dblNormalizer;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblRandom;
	}

	/**
	 * Generate a Sequence of R^d Correlated Gaussian Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * @param aadblCorrelation The Correlation Matrix
	 * 
	 * @return The Sequence of R^d Correlated Gaussian Random Numbers
	 */

	public static final double[][] GaussianJoint (
		final int iCount,
		final double[][] aadblCorrelation)
	{
		if (0 >= iCount) return null;

		double[][] aadblCholesky = org.drip.numerical.linearalgebra.Matrix.CholeskyBanachiewiczFactorization
			(aadblCorrelation);

		if (null == aadblCholesky) return null;

		int iDimension = aadblCholesky.length;
		double[][] aadblRandom = new double[iCount][];

		for (int k = 0; k < iCount; ++k) {
			double[] adblUncorrelatedRandom = Gaussian (iDimension);

			if (null == adblUncorrelatedRandom || iDimension != adblUncorrelatedRandom.length) return null;

			double[] adblCorrelatedRandom = new double[iDimension];

			for (int i = 0; i < iDimension; ++i) {
				adblCorrelatedRandom[i] = 0.;

				for (int j = 0; j < iDimension; ++j)
					adblCorrelatedRandom[i] += aadblCholesky[i][j] * adblUncorrelatedRandom[j];
			}

			aadblRandom[k] = adblCorrelatedRandom;
		}

		return aadblRandom;
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
		if (0 >= degreesOfFreedom)
		{
			return null;
		}

		double[] chiSquaredArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
			double sumOfStandardNormalSquares = 0.;

			for (int drawIndex = 0; drawIndex < degreesOfFreedom; ++drawIndex)
			{
				try
				{
					double randomStandardNormal = org.drip.measure.gaussian.NormalQuadrature.InverseCDF
						(java.lang.Math.random());

					sumOfStandardNormalSquares = sumOfStandardNormalSquares +
						randomStandardNormal * randomStandardNormal;
				}
				catch (java.lang.Exception e)
				{
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (scale) || 0. >= scale)
		{
			return null;
		}

		double[] chiSquaredArray =  ChiSquared (
			count,
			degreesOfFreedom
		);

		if (null == chiSquaredArray)
		{
			return null;
		}

		double[] scaledGammaArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
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
		double[] chiSquaredArray =  ChiSquared (
			count,
			degreesOfFreedom
		);

		if (null == chiSquaredArray)
		{
			return null;
		}

		double[] chiArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
			chiArray[index] = java.lang.Math.sqrt (chiSquaredArray[index]);
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
		double[] chiSquaredArray =  ChiSquared (
			count,
			2
		);

		if (null == chiSquaredArray)
		{
			return null;
		}

		double[] unitScaleRayleighArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
			unitScaleRayleighArray[index] = java.lang.Math.sqrt (chiSquaredArray[index]);
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
		double[] chiSquaredArray =  ChiSquared (
			count,
			3
		);

		if (null == chiSquaredArray)
		{
			return null;
		}

		double[] unitScaleMaxwellArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
			unitScaleMaxwellArray[index] = java.lang.Math.sqrt (chiSquaredArray[index]);
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
		double[] chiSquaredArray =  ChiSquared (
			count,
			degreesOfFreedom
		);

		if (null == chiSquaredArray)
		{
			return null;
		}

		double[] inverseChiSquaredArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
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
		double[] chiSquaredArray1 = ChiSquared (
			count,
			degreesOfFreedom1
		);

		double[] chiSquaredArray2 = ChiSquared (
			count,
			degreesOfFreedom2
		);

		if (null == chiSquaredArray1 || null == chiSquaredArray2)
		{
			return null;
		}

		double[] betaArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
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
		double[] chiSquaredArray1 = ChiSquared (
			count,
			degreesOfFreedom1
		);

		double[] chiSquaredArray2 = ChiSquared (
			count,
			degreesOfFreedom2
		);

		if (null == chiSquaredArray1 || null == chiSquaredArray2)
		{
			return null;
		}

		double[] fArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
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

	public double[] RankReducedChiSquare (
		final int count,
		final double[][] covarianceMatrix)
	{
		double[] rankReducedChiSquare = new double[count];
		org.drip.measure.gaussian.Covariance covariance = null;

		try
		{
			covariance = new org.drip.measure.gaussian.Covariance (covarianceMatrix);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double[][] gaussianJointArray = GaussianJoint (
			count,
			covariance.correlationMatrix()
		);

		if (null == gaussianJointArray)
		{
			return null;
		}

		double[][] precisionMatrix = covariance.precisionMatrix();

		for (int index = 0; index < count; ++index)
		{
			try
			{
				rankReducedChiSquare[index] = org.drip.numerical.linearalgebra.Matrix.DotProduct (
					gaussianJointArray[index],
					org.drip.numerical.linearalgebra.Matrix.Product (
						precisionMatrix,
						gaussianJointArray[index]
					)
				);
			}
			catch (java.lang.Exception e)
			{
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

	public double[] PillaiSpecialChiSquare (
		final int count,
		final double[][] covarianceMatrix,
		final double[] weightArray)
	{
		if (!org.drip.numerical.common.NumberUtil.NormalizedPositive (weightArray))
		{
			return null;
		}

		int pillaiVectorSize = weightArray.length;
		double[] pillaiSpecialChiSquare = new double[count];
		org.drip.measure.gaussian.Covariance covariance = null;

		try
		{
			covariance = new org.drip.measure.gaussian.Covariance (covarianceMatrix);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		if (pillaiVectorSize != covarianceMatrix.length)
		{
			return null;
		}

		double[][] gaussianJointArray = GaussianJoint (
			count,
			covariance.correlationMatrix()
		);

		if (null == gaussianJointArray)
		{
			return null;
		}

		for (int index = 0; index < count; ++index)
		{
			double[] pillaiVector = new double[pillaiVectorSize];

			for (int pillaiVectorIndex = 0; pillaiVectorIndex < pillaiVectorSize; ++pillaiVectorIndex)
			{
				pillaiVector[pillaiVectorIndex] = weightArray[pillaiVectorIndex] /
					gaussianJointArray[index][pillaiVectorIndex];
			}

			try
			{
				pillaiSpecialChiSquare[index] = 1. / org.drip.numerical.linearalgebra.Matrix.DotProduct (
					pillaiVector,
					org.drip.numerical.linearalgebra.Matrix.Product (
						covarianceMatrix,
						pillaiVector
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return pillaiSpecialChiSquare;
	}
}
