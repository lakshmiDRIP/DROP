
package org.drip.state.sequence;

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
 * <i>PathRd</i> exposes the Functionality to generate a Sequence of the Path Vertex Latent State
 * R<sup>d</sup> Realizations across Multiple Paths.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence/README.md">Monte Carlo Path State Realizations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathRd {
	private double[] _adblMean = null;
	private boolean _bLogNormal = false;
	private double _dblVolatility = java.lang.Double.NaN;

	/**
	 * PathRd Constructor
	 * 
	 * @param adblMean Array of Mean
	 * @param dblVolatility Volatility
	 * @param bLogNormal TRUE - The Generated Random Numbers are Log Normal
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public PathRd (
		final double[] adblMean,
		final double dblVolatility,
		final boolean bLogNormal)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_adblMean = adblMean) || null == _adblMean || 0 ==
			_adblMean.length || !org.drip.numerical.common.NumberUtil.IsValid (_dblVolatility = dblVolatility) ||
				0. >= _dblVolatility)
			throw new java.lang.Exception ("PathRd Constructor => Invalid Inputs");

		_bLogNormal = bLogNormal;
	}

	/**
	 * Indicate if the Random Numbers are Gaussian/LogNormal
	 * 
	 * @return TRUE - The Generated Random Numbers are Log Normal
	 */

	public boolean logNormal()
	{
		return _bLogNormal;
	}

	/**
	 * Retrieve the R^d Dimension
	 * 
	 * @return The R^d Dimension
	 */

	public int dimension()
	{
		return _adblMean.length;
	}

	/**
	 * Retrieve the Array of Means
	 * 
	 * @return The Array of Means
	 */

	public double[] mean()
	{
		return _adblMean;
	}

	/**
	 * Retrieve the Volatility
	 * 
	 * @return The Volatility
	 */

	public double volatility()
	{
		return _dblVolatility;
	}

	/**
	 * Generate the Sequence of Path Realizations
	 * 
	 * @param iNumPath Number of Paths
	 * 
	 * @return The Sequence of Path Realizations
	 */

	public double[][] sequence (
		final int iNumPath)
	{
		if (0 >= iNumPath) return null;

		int iNumDimension = _adblMean.length;
		double[][] aadblSequence = new double[iNumPath][iNumDimension];

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			double[] adblRandom = org.drip.measure.discrete.SequenceGenerator.Gaussian (iNumDimension);

			if (null == adblRandom || iNumDimension != adblRandom.length) return null;

			for (int iDimension = 0; iDimension < iNumDimension; ++iDimension) {
				double dblWander = _dblVolatility * adblRandom[iDimension];

				aadblSequence[iPath][iDimension] = _bLogNormal ? _adblMean[iDimension] * java.lang.Math.exp
					(dblWander) : _adblMean[iDimension] + dblWander;
			}
		}

		return aadblSequence;
	}
}
