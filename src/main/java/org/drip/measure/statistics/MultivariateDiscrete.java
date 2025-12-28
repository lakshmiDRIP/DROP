
package org.drip.measure.statistics;

import org.drip.numerical.common.NumberUtil;

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
 * <i>MultivariateDiscrete</i> analyzes and computes the Moment and Metric Statistics for the Realized
 * 	Multivariate Sequence. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>MultivariateDiscrete</i> Constructor</li>
 * 		<li>Retrieve the Multivariate Means</li>
 * 		<li>Retrieve the Multivariate Sequence "Error"</li>
 * 		<li>Retrieve the Multivariate Covariance</li>
 * 		<li>Retrieve the Multivariate Correlation</li>
 * 		<li>Retrieve the Multivariate Variance</li>
 * 		<li>Retrieve the Multivariate Standard Deviation</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics/README.md">R<sup>1</sup> R<sup>d</sup> Thin Thick Moments</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateDiscrete
{
	private double[] _meanArray = null;
	private double[] _errorArray = null;
	private double[] _varianceArray = null;
	private double[][] _covarianceMatrix = null;
	private double[][] _correlationMatrix = null;
	private double[] _standardDeviationArray = null;

	/**
	 * <i>MultivariateDiscrete</i> Constructor
	 * 
	 * @param sequence The Array of Multivariate Realizations
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MultivariateDiscrete (
		final double[][] sequence)
		throws Exception
	{
		if (null == sequence) {
			throw new Exception ("MultivariateDiscrete Constructor => Invalid Inputs");
		}

		int variateCount = -1;
		int sequenceSize = sequence.length;

		if (0 == sequenceSize) {
			throw new Exception ("MultivariateDiscrete Constructor => Invalid Inputs");
		}

		for (int sequenceIndex = 0; sequenceIndex < sequenceSize; ++sequenceIndex) {
			if (null == sequence[sequenceIndex] || !NumberUtil.IsValid (sequence[sequenceIndex])) {
				throw new Exception ("MultivariateDiscrete Constructor => Invalid Inputs");
			}

			if (0 == sequenceIndex) {
				if (0 == (variateCount = sequence[0].length)) {
					throw new Exception ("MultivariateDiscrete Constructor => Invalid Inputs");
				}

				_meanArray = new double[variateCount];
				_errorArray = new double[variateCount];
				_varianceArray = new double[variateCount];
				_standardDeviationArray = new double[variateCount];
				_covarianceMatrix = new double[variateCount][variateCount];
				_correlationMatrix = new double[variateCount][variateCount];

				for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
					_meanArray[variateIndex] = 0.;
					_errorArray[variateIndex] = 0.;

					for (int variateIndexOther = 0; variateIndexOther < variateCount; ++variateIndexOther) {
						_covarianceMatrix[variateIndex][variateIndexOther] = 0.;
					}
				}
			} else if (variateCount != sequence[sequenceIndex].length) {
				throw new Exception ("MultivariateDiscrete Constructor => Invalid Inputs");
			}

			for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
				_meanArray[variateIndex] += sequence[sequenceIndex][variateIndex];
			}
		}

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			_meanArray[variateIndex] /= sequenceSize;
		}

		for (int sequenceIndex = 0; sequenceIndex < sequenceSize; ++sequenceIndex) {
			for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
				double offsetFromMean = sequence[sequenceIndex][variateIndex] - _meanArray[variateIndex];

				_errorArray[variateIndex] += Math.abs (offsetFromMean);

				for (int variateIndexOther = 0; variateIndexOther < variateCount; ++variateIndexOther) {
					_covarianceMatrix[variateIndex][variateIndexOther] += offsetFromMean *
						(sequence[sequenceIndex][variateIndexOther] - _meanArray[variateIndexOther]);
				}
			}
		}

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			_errorArray[variateIndex] /= sequenceSize;

			for (int variateIndexOther = 0; variateIndexOther < variateCount; ++variateIndexOther) {
				_covarianceMatrix[variateIndex][variateIndexOther] /= sequenceSize;
			}

			_standardDeviationArray[variateIndex] = Math.sqrt (
				_varianceArray[variateIndex] = _covarianceMatrix[variateIndex][variateIndex]
			);
		}

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			for (int variateIndexOther = 0; variateIndexOther < variateCount; ++variateIndexOther) {
				_correlationMatrix[variateIndex][variateIndexOther] =
					_covarianceMatrix[variateIndex][variateIndexOther] / (
						_standardDeviationArray[variateIndex] * _standardDeviationArray[variateIndexOther]
					);
			}
		}
	}

	/**
	 * Retrieve the Multivariate Means
	 * 
	 * @return The Multivariate Means
	 */

	public double[] mean()
	{
		return _meanArray;
	}

	/**
	 * Retrieve the Multivariate Sequence "Error"
	 * 
	 * @return The Multivariate Sequence "Error"
	 */

	public double[] error()
	{
		return _errorArray;
	}

	/**
	 * Retrieve the Multivariate Covariance
	 * 
	 * @return The Multivariate Covariance
	 */

	public double[][] covariance()
	{
		return _covarianceMatrix;
	}

	/**
	 * Retrieve the Multivariate Correlation
	 * 
	 * @return The Multivariate Correlation
	 */

	public double[][] correlation()
	{
		return _correlationMatrix;
	}

	/**
	 * Retrieve the Multivariate Variance
	 * 
	 * @return The Multivariate Variance
	 */

	public double[] variance()
	{
		return _varianceArray;
	}

	/**
	 * Retrieve the Multivariate Standard Deviation
	 * 
	 * @return The Multivariate Standard Deviation
	 */

	public double[] standardDeviation()
	{
		return _standardDeviationArray;
	}
}
