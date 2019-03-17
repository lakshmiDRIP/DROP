
package org.drip.state.sequence;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>PathRd</i> exposes the Functionality to generate a Sequence of the Path Vertex Latent State
 * R<sup>d</sup> Realizations across Multiple Paths.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence">Sequence</a></li>
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
