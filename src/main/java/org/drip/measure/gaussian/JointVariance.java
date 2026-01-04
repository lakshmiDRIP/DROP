
package org.drip.measure.gaussian;

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
 * <i>JointVariance</i> holds the Standard Covariance Matrix, and provides functions to manipulate it. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>JointVariance</i> Constructor</li>
 * 		<li>Retrieve the Number of Variates</li>
 * 		<li>Retrieve the Covariance Matrix</li>
 * 		<li>Retrieve the Precision Matrix</li>
 * 		<li>Retrieve the Variance Array</li>
 * 		<li>Retrieve the Volatility Array</li>
 * 		<li>Retrieve the Correlation Matrix</li>
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

public class JointVariance
{
	private double[][] _precisionMatrix = null;
	private double[][] _covarianceMatrix = null;

	/**
	 * <i>JointVariance</i> Constructor
	 * 
	 * @param covarianceMatrix Double Array of the Covariance Matrix
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public JointVariance (
		final double[][] covarianceMatrix)
		throws Exception
	{
		if (null == (_covarianceMatrix = covarianceMatrix)) {
			throw new Exception ("JointVariance Constructor => Invalid Inputs!");
		}

		if (0 == _covarianceMatrix.length) {
			throw new Exception ("JointVariance Constructor => Invalid Inputs!");
		}

		for (int variateIndex = 0; variateIndex < _covarianceMatrix.length; ++variateIndex) {
			if (null == _covarianceMatrix[variateIndex] ||
				_covarianceMatrix.length != _covarianceMatrix[variateIndex].length ||
				!NumberUtil.IsValid (_covarianceMatrix[variateIndex]))
			{
				throw new Exception ("JointVariance Constructor => Invalid Inputs!");
			}
		}

		if (null == (_precisionMatrix = R1MatrixUtil.InvertUsingGaussianElimination (_covarianceMatrix))) {
			throw new Exception ("JointVariance Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Number of Variates
	 * 
	 * @return The Number of Variates
	 */

	public int variateCount()
	{
		return _covarianceMatrix.length;
	}

	/**
	 * Retrieve the Covariance Matrix
	 * 
	 * @return The Covariance Matrix
	 */

	public double[][] covarianceMatrix()
	{
		return _covarianceMatrix;
	}

	/**
	 * Retrieve the Precision Matrix
	 * 
	 * @return The Precision Matrix
	 */

	public double[][] precisionMatrix()
	{
		return _precisionMatrix;
	}

	/**
	 * Retrieve the Variance Array
	 * 
	 * @return The Variance Array
	 */

	public double[] varianceArray()
	{
		double[] varianceArray = new double[_covarianceMatrix.length];

		for (int variateIndex = 0; variateIndex < _covarianceMatrix.length; ++variateIndex) {
			varianceArray[variateIndex] = _covarianceMatrix[variateIndex][variateIndex];
		}

		return varianceArray;
	}

	/**
	 * Retrieve the Volatility Array
	 * 
	 * @return The Volatility Array
	 */

	public double[] volatilityArray()
	{
		double[] volatilityArray = new double[_covarianceMatrix.length];

		for (int variateIndex = 0; variateIndex < _covarianceMatrix.length; ++variateIndex) {
			volatilityArray[variateIndex] = Math.sqrt (_covarianceMatrix[variateIndex][variateIndex]);
		}

		return volatilityArray;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		double[][] correlationMatrix = new double[_covarianceMatrix.length][_covarianceMatrix.length];

		double[] volatilityArray = volatilityArray();

		for (int variateIndexI = 0; variateIndexI < _covarianceMatrix.length; ++variateIndexI) {
			for (int variateIndexJ = 0; variateIndexJ < _covarianceMatrix.length; ++variateIndexJ) {
				correlationMatrix[variateIndexI][variateIndexJ] =
					_covarianceMatrix[variateIndexI][variateIndexJ] / (
						volatilityArray[variateIndexI] * volatilityArray[variateIndexJ]
					);
			}
		}

		return correlationMatrix;
	}
}
