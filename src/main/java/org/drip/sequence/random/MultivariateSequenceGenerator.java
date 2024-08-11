
package org.drip.sequence.random;

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
 * <i>MultivariateSequenceGenerator</i> implements the Multivariate Random Sequence Generator Functionality.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/random">Random</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateSequenceGenerator {
	private double[][] _aadblCholesky = null;
	private double[][] _aadblCorrelation = null;
	private org.drip.sequence.random.UnivariateSequenceGenerator[] _aUSG = null;

	/**
	 * MultivariateSequenceGenerator Constructor
	 * 
	 * @param aUSG Array of Univariate Sequence Generators
	 * @param aadblCorrelation The Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public MultivariateSequenceGenerator (
		final org.drip.sequence.random.UnivariateSequenceGenerator[] aUSG,
		final double[][] aadblCorrelation)
		throws java.lang.Exception
	{
		if (null == (_aUSG = aUSG) || null == (_aadblCorrelation = aadblCorrelation))
			throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");

		_aadblCholesky = org.drip.numerical.linearalgebra.R1MatrixUtil.CholeskyBanachiewiczFactorization
			(aadblCorrelation);

		int iNumVariate = aUSG.length;

		if (null == _aadblCholesky || null == _aadblCholesky[0] || iNumVariate != _aadblCholesky.length ||
			iNumVariate != _aadblCholesky[0].length)
			throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == _aUSG[i])
				throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");

			for (int j = 0; j < iNumVariate; ++j) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (_aadblCorrelation[i][j]))
					throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Univariate Sequence Generators
	 * 
	 * @return Array of Univariate Sequence Generators
	 */

	public org.drip.sequence.random.UnivariateSequenceGenerator[] usg()
	{
		return _aUSG;
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
	 * Retrieve the Cholesky Factorial
	 * 
	 * @return The Cholesky Factorial
	 */

	public double[][] cholesky()
	{
		return _aadblCholesky;
	}

	/**
	 * Retrieve the Number of Variates
	 * 
	 * @return The Number of Variates
	 */

	public int numVariate()
	{
		return _aUSG.length;
	}

	/**
	 * Generate the Set of Multivariate Random Numbers according to the specified rule
	 * 
	 * @return The Set of Multivariate Random Numbers
	 */

	public double[] random()
	{
		int iNumVariate = _aUSG.length;
		double[] adblRandom = new double[iNumVariate];
		double[] adblUncorrelatedRandom = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblUncorrelatedRandom[i] = _aUSG[i].random();

		for (int i = 0; i < iNumVariate; ++i) {
			adblRandom[i] = 0.;

			for (int j = 0; j < iNumVariate; ++j)
				adblRandom[i] += _aadblCholesky[i][j] * adblUncorrelatedRandom[j];
		}

		return adblRandom;
	}
}
