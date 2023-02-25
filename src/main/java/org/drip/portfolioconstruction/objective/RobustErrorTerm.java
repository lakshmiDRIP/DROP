
package org.drip.portfolioconstruction.objective;

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
 * <i>RobustErrorTerm</i> optimizes the Error in the Target Expected Absolute Return of the Portfolio on the
 * Absence of Benchmark, and the Error in the Benchmark-Adjusted Returns Otherwise.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/README.md">Portfolio Construction Objective Term Suite</a></li>
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
