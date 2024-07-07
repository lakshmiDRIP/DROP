
package org.drip.measure.gaussian;

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
 * <i>Covariance</i> holds the Standard Covariance Matrix, and provides functions to manipulate it.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian/README.md">R<sup>1</sup> R<sup>d</sup> Covariant Gaussian Quadrature</a></li>
 *  </ul>
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
				!org.drip.numerical.common.NumberUtil.IsValid (_aadblCovariance[i]))
				throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
		}

		if (null == (_aadblPrecision = org.drip.numerical.linearalgebra.MatrixUtil.InvertUsingGaussianElimination
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
