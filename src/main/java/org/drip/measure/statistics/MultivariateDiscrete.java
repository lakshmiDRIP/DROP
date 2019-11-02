
package org.drip.measure.statistics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>MultivariateDiscrete</i> analyzes and computes the Moment and Metric Statistics for the Realized
 * Multivariate Sequence.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics">R<sup>1</sup> R<sup>d</sup> Thin Thick Moments</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateDiscrete {
	private double[] _adblMean = null;
	private double[] _adblError = null;
	private double[] _adblVariance = null;
	private double[][] _aadblCovariance = null;
	private double[][] _aadblCorrelation = null;
	private double[] _adblStandardDeviation = null;

	/**
	 * MultivariateDiscrete Constructor
	 * 
	 * @param aadblSequence The Array of Multivariate Realizations
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MultivariateDiscrete (
		final double[][] aadblSequence)
		throws java.lang.Exception
	{
		if (null == aadblSequence)
			throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

		int iNumVariate = -1;
		int iSequenceSize = aadblSequence.length;

		if (0 == iSequenceSize)
			throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

		for (int iSequence = 0; iSequence < iSequenceSize; ++iSequence) {
			if (null == aadblSequence[iSequence] || !org.drip.numerical.common.NumberUtil.IsValid
				(aadblSequence[iSequence]))
				throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

			if (0 == iSequence) {
				if (0 == (iNumVariate = aadblSequence[0].length))
					throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

				_adblMean = new double[iNumVariate];
				_adblError = new double[iNumVariate];
				_adblVariance = new double[iNumVariate];
				_adblStandardDeviation = new double[iNumVariate];
				_aadblCovariance = new double[iNumVariate][iNumVariate];
				_aadblCorrelation = new double[iNumVariate][iNumVariate];

				for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
					_adblMean[iVariate] = 0.;
					_adblError[iVariate] = 0.;

					for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
						_aadblCovariance[iVariate][iVariateOther] = 0.;
				}
			} else if (iNumVariate != aadblSequence[iSequence].length)
				throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

			for (int iVariate = 0; iVariate < iNumVariate; ++iVariate)
				_adblMean[iVariate] += aadblSequence[iSequence][iVariate];
		}

		for (int iVariate = 0; iVariate < iNumVariate; ++iVariate)
			_adblMean[iVariate] /= iSequenceSize;

		for (int iSequence = 0; iSequence < iSequenceSize; ++iSequence) {
			for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
				double dblOffsetFromMean = aadblSequence[iSequence][iVariate] - _adblMean[iVariate];

				_adblError[iVariate] += java.lang.Math.abs (dblOffsetFromMean);

				for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
					_aadblCovariance[iVariate][iVariateOther] += dblOffsetFromMean *
						(aadblSequence[iSequence][iVariateOther] - _adblMean[iVariateOther]);
			}
		}

		for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
			_adblError[iVariate] /= iSequenceSize;

			for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
				_aadblCovariance[iVariate][iVariateOther] /= iSequenceSize;

			_adblStandardDeviation[iVariate] = java.lang.Math.sqrt (_adblVariance[iVariate] =
				_aadblCovariance[iVariate][iVariate]);
		}

		for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
			for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
				_aadblCorrelation[iVariate][iVariateOther] = _aadblCovariance[iVariate][iVariateOther] /
					(_adblStandardDeviation[iVariate] * _adblStandardDeviation[iVariateOther]);
		}
	}

	/**
	 * Retrieve the Multivariate Means
	 * 
	 * @return The Multivariate Means
	 */

	public double[] mean()
	{
		return _adblMean;
	}

	/**
	 * Retrieve the Multivariate Sequence "Error"
	 * 
	 * @return The Multivariate Sequence "Error"
	 */

	public double[] error()
	{
		return _adblError;
	}

	/**
	 * Retrieve the Multivariate Covariance
	 * 
	 * @return The Multivariate Covariance
	 */

	public double[][] covariance()
	{
		return _aadblCovariance;
	}

	/**
	 * Retrieve the Multivariate Correlation
	 * 
	 * @return The Multivariate Correlation
	 */

	public double[][] correlation()
	{
		return _aadblCorrelation;
	}

	/**
	 * Retrieve the Multivariate Variance
	 * 
	 * @return The Multivariate Variance
	 */

	public double[] variance()
	{
		return _adblVariance;
	}

	/**
	 * Retrieve the Multivariate Standard Deviation
	 * 
	 * @return The Multivariate Standard Deviation
	 */

	public double[] standardDeviation()
	{
		return _adblStandardDeviation;
	}
}
