
package org.drip.portfolioconstruction.objective;

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
 * <i>RobustErrorTerm</i> optimizes the Error in the Target Expected Absolute Return of the Portfolio on the
 * Absence of Benchmark, and the Error in the Benchmark-Adjusted Returns Otherwise.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective">Objective</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RobustErrorTerm
	extends org.drip.portfolioconstruction.objective.ReturnsTerm
{
	private double[][] _assetCovarianceMatrix = null;
	private double[][] _alphaUncertaintyMatrix = null;
	private double _confidenceLevel = java.lang.Double.NaN;

	/**
	 * RobustErrorTerm Constructor
	 * 
	 * @param name Name of the Expected Returns Objective Term
	 * @param initialHoldingsArray Initial Holdings Array
	 * @param alphaArray Asset Alpha Array
	 * @param alphaUncertaintyMatrix Alpha Uncertainty Matrix
	 * @param assetCovarianceMatrix Asset Co-variance Matrix
	 * @param benchmarkConstrictedHoldingsArray Benchmark Constricted Holdings Array
	 * @param confidenceLevel Confidence Level (i.e., Eta)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RobustErrorTerm (
		final java.lang.String name,
		final double[] initialHoldingsArray,
		final double[] alphaArray,
		final double[][] alphaUncertaintyMatrix,
		final double[][] assetCovarianceMatrix,
		final double[] benchmarkConstrictedHoldingsArray,
		final double confidenceLevel)
		throws java.lang.Exception
	{
		super (
			name,
			"OT_ROBUST",
			"Robust Error Portfolio Returns Objective Term",
			initialHoldingsArray,
			alphaArray,
			benchmarkConstrictedHoldingsArray
		);

		int assetCount = initialHoldingsArray.length;

		if (!org.drip.numerical.common.NumberUtil.IsValid (_confidenceLevel = confidenceLevel) ||
			null == (_alphaUncertaintyMatrix = alphaUncertaintyMatrix) ||
			assetCount != _alphaUncertaintyMatrix.length)
		{
			throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (null == _alphaUncertaintyMatrix[assetIndex] ||
				!org.drip.numerical.common.NumberUtil.IsValid (_alphaUncertaintyMatrix[assetIndex]) ||
				assetCount != _alphaUncertaintyMatrix[assetIndex].length)
			{
				throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");
			}
		}

		if (null == (_assetCovarianceMatrix = assetCovarianceMatrix) ||
			assetCount != _assetCovarianceMatrix.length)
		{
			throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (null == _assetCovarianceMatrix[assetIndex] ||
				!org.drip.numerical.common.NumberUtil.IsValid (_assetCovarianceMatrix[assetIndex]) ||
				assetCount != _assetCovarianceMatrix[assetIndex].length)
			{
				throw new java.lang.Exception ("RobustErrorTerm Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Confidence Level (i.e., Eta)
	 * 
	 * @return The Confidence Level (i.e., Eta)
	 */

	public double confidenceLevel()
	{
		return _confidenceLevel;
	}

	/**
	 * Retrieve the Asset Co-variance Matrix
	 * 
	 * @return The Asset Co-variance Matrix
	 */

	public double[][] assetCovariance()
	{
		return _assetCovarianceMatrix;
	}

	/**
	 * Retrieve the Alpha Uncertainty Matrix
	 * 
	 * @return The Alpha Uncertainty Matrix
	 */

	public double[][] alphaUncertainty()
	{
		return _alphaUncertaintyMatrix;
	}
}
