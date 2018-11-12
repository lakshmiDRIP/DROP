
package org.drip.measure.gaussian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>Covariance</i> holds the Standard Covariance Matrix, and provides functions to manipulate it.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian">Gaussian</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Covariance {
	private double[][] _aadblPrecision = null;
	private double[][] _aadblCovariance = null;

	/**
	 * Covariance Constructor
	 * 
	 * @param aadblCovariance Double Array of the Covariance Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Covariance (
		final double[][] aadblCovariance)
		throws java.lang.Exception
	{
		if (null == (_aadblCovariance = aadblCovariance))
			throw new java.lang.Exception ("Covariance Constructor => Invalid Inputs!");

		int iNumVariate = _aadblCovariance.length;

		if (0 == iNumVariate)
			throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == _aadblCovariance[i] || iNumVariate != _aadblCovariance[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblCovariance[i]))
				throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
		}

		if (null == (_aadblPrecision = org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination
			(_aadblCovariance)))
			throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Number of Variates
	 * 
	 * @return The Number of Variates
	 */

	public int numVariate()
	{
		return _aadblCovariance.length;
	}

	/**
	 * Retrieve the Covariance Matrix
	 * 
	 * @return The Covariance Matrix
	 */

	public double[][] covarianceMatrix()
	{
		return _aadblCovariance;
	}

	/**
	 * Retrieve the Precision Matrix
	 * 
	 * @return The Precision Matrix
	 */

	public double[][] precisionMatrix()
	{
		return _aadblPrecision;
	}

	/**
	 * Retrieve the Variance Array
	 * 
	 * @return The Variance Array
	 */

	public double[] variance()
	{
		int iNumVariate = _aadblCovariance.length;
		double[] adblVariance = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVariance[i] = _aadblCovariance[i][i];

		return adblVariance;
	}

	/**
	 * Retrieve the Volatility Array
	 * 
	 * @return The Volatility Array
	 */

	public double[] volatility()
	{
		int iNumVariate = _aadblCovariance.length;
		double[] adblVolatility = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVolatility[i] = java.lang.Math.sqrt (_aadblCovariance[i][i]);

		return adblVolatility;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		int iNumVariate = _aadblCovariance.length;
		double[][] aadblCorrelation = new double[iNumVariate][iNumVariate];

		double[] adblVolatility = volatility();

		for (int i = 0; i < iNumVariate; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				aadblCorrelation[i][j] = _aadblCovariance[i][j] / (adblVolatility[i] * adblVolatility[j]);
		}

		return aadblCorrelation;
	}
}
