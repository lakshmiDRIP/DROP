
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
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete">Discrete</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SequenceGenerator {

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

		double[][] aadblCholesky = org.drip.quant.linearalgebra.Matrix.CholeskyBanachiewiczFactorization
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
}
