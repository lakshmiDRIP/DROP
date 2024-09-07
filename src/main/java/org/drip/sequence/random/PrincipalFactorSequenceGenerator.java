
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
 * <i>PrincipalFactorSequenceGenerator</i> implements the Principal Factors Based Multivariate Random
 * Sequence Generator Functionality.
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

public class PrincipalFactorSequenceGenerator extends org.drip.sequence.random.MultivariateSequenceGenerator
{
	private double[][] _aadblFactor = null;
	private double[] _adblFactorWeight = null;

	/**
	 * PrincipalFactorSequenceGenerator Constructor
	 * 
	 * @param aUSG Array of Univariate Sequence Generators
	 * @param aadblCorrelation The Correlation Matrix
	 * @param iNumFactor Number of Factors
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public PrincipalFactorSequenceGenerator (
		final org.drip.sequence.random.UnivariateSequenceGenerator[] aUSG,
		final double[][] aadblCorrelation,
		final int iNumFactor)
		throws java.lang.Exception
	{
		super (aUSG, aadblCorrelation);

		int iNumVariate = aUSG.length;

		if (0 >= iNumFactor || iNumFactor > iNumVariate)
			throw new java.lang.Exception ("PrincipalFactorSequenceGenerator ctr: Invalid Inputs");

		org.drip.numerical.eigenization.QREigenComponentExtractor qrece = new
			org.drip.numerical.eigenization.QREigenComponentExtractor (80);

		org.drip.numerical.eigenization.EigenComponent[] aEC = qrece.orderedEigenComponentArray (aadblCorrelation);

		if (null == aEC || 0 == aEC.length)
			throw new java.lang.Exception ("PrincipalFactorSequenceGenerator ctr: Invalid Inputs");

		double dblNormalizer = 0.;
		_adblFactorWeight = new double[iNumFactor];
		_aadblFactor = new double[iNumFactor][iNumVariate];

		for (int i = 0; i < iNumFactor; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				_aadblFactor[i] = aEC[i].eigenVector();

			_adblFactorWeight[i] = aEC[i].eigenValue();

			dblNormalizer += _adblFactorWeight[i] * _adblFactorWeight[i];
		}

		dblNormalizer = java.lang.Math.sqrt (dblNormalizer);

		for (int i = 0; i < iNumFactor; ++i)
			_adblFactorWeight[i] /= dblNormalizer;
	}

	/**
	 * Retrieve the Number of Factors
	 * 
	 * @return The Number of Factors
	 */

	public int numFactor()
	{
		return _adblFactorWeight.length;
	}

	/**
	 * Retrieve the Principal Component Factor Array
	 * 
	 * @return The Principal Component Factor Array
	 */

	public double[][] factors()
	{
		return _aadblFactor;
	}

	/**
	 * Retrieve the Array of Factor Weights
	 * 
	 * @return The Array of Factor Weights
	 */

	public double[] factorWeight()
	{
		return _adblFactorWeight;
	}

	@Override public double[] random()
	{
		double[] adblBaseRandom = super.random();

		int iNumVariate = _aadblFactor[0].length;
		int iNumFactor = _adblFactorWeight.length;
		double[] adblRandom = new double[iNumFactor];

		if (iNumFactor == iNumVariate) return adblBaseRandom;

		for (int i = 0; i < iNumFactor; ++i) {
			adblRandom[i] = 0.;

			for (int j = 0; j < iNumVariate; ++j)
				adblRandom[i] += _aadblFactor[i][j] * adblBaseRandom[j];
		}

		return adblRandom;
	}
}
