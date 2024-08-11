
package org.drip.portfolioconstruction.allocator;

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
 * <i>HoldingsAllocation</i> holds the Output of an Optimal Portfolio Construction Run, i.e., the Optimal
 * Asset Weights in the Portfolio and the related Portfolio Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/README.md">MVO Based Portfolio Allocation Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HoldingsAllocation
{
	private org.drip.portfolioconstruction.asset.Portfolio _optimalPortfolio = null;
	private org.drip.portfolioconstruction.asset.PortfolioMetrics _optimalPortfolioMetrics = null;

	/**
	 * Create an Instance of the Optimal Portfolio
	 * 
	 * @param optimalAssetComponentArray The Array of the Optimal Asset Components
	 * @param assetUniverseStatisticalProperties The AssetUniverseStatisticalProperties Instance
	 * 
	 * @return The Instance of the Optimal Portfolio
	 */

	public static final HoldingsAllocation Create (
		final org.drip.portfolioconstruction.asset.AssetComponent[] optimalAssetComponentArray,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties assetUniverseStatisticalProperties)
	{
		if (null == optimalAssetComponentArray || null == assetUniverseStatisticalProperties)
		{
			return null;
		}

		int iNumAsset = optimalAssetComponentArray.length;

		if (0 == iNumAsset)
		{
			return null;
		}

		try
		{
			org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio = new
				org.drip.portfolioconstruction.asset.Portfolio (optimalAssetComponentArray);

			double portfolioExcessReturnsMean = optimalPortfolio.expectedReturn
				(assetUniverseStatisticalProperties);

			double portfolioExcessReturnsVariance = optimalPortfolio.variance
				(assetUniverseStatisticalProperties);

			double portfolioExcessReturnsSigma = java.lang.Math.sqrt (portfolioExcessReturnsVariance);

			double[] impliedBetaArray = org.drip.numerical.linearalgebra.R1MatrixUtil.Product (
				assetUniverseStatisticalProperties.covariance (optimalPortfolio.assetIDArray()),
				optimalPortfolio.weightArray()
			);

			if (null == impliedBetaArray)
			{
				return null;
			}

			for (int i = 0; i < iNumAsset; ++i)
			{
				impliedBetaArray[i] = impliedBetaArray[i] / portfolioExcessReturnsVariance;
			}

			return new org.drip.portfolioconstruction.allocator.HoldingsAllocation (
				new org.drip.portfolioconstruction.asset.Portfolio (optimalAssetComponentArray),
				new org.drip.portfolioconstruction.asset.PortfolioMetrics (
					portfolioExcessReturnsMean,
					portfolioExcessReturnsVariance,
					portfolioExcessReturnsSigma,
					portfolioExcessReturnsMean / portfolioExcessReturnsSigma,
					impliedBetaArray
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * HoldingsAllocation Constructor
	 * 
	 * @param optimalPortfolio The Optimal Portfolio
	 * @param optimalPortfolioMetrics The Optimal Portfolio Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HoldingsAllocation (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics optimalPortfolioMetrics)
		throws java.lang.Exception
	{
		if (null == (_optimalPortfolio = optimalPortfolio) ||
			null == (_optimalPortfolioMetrics = optimalPortfolioMetrics))
		{
			throw new java.lang.Exception ("HoldingsAllocation Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Optimal Portfolio Metrics
	 * 
	 * @return The Optimal Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioMetrics optimalMetrics()
	{
		return _optimalPortfolioMetrics;
	}

	/**
	 * Retrieve the Optimal Portfolio Instance
	 * 
	 * @return The Optimal Portfolio Instance
	 */

	public org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio()
	{
		return _optimalPortfolio;
	}
}
