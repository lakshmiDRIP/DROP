
package org.drip.portfolioconstruction.allocator;

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
 * <i>OptimizationOutput</i> holds the Output of an Optimal Portfolio Construction Run, i.e., the Optimal
 * Asset Weights in the Portfolio and the related Portfolio Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator">Allocator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OptimizationOutput {
	private org.drip.portfolioconstruction.asset.Portfolio _pfOptimal = null;
	private org.drip.portfolioconstruction.asset.PortfolioMetrics _pmOptimal = null;

	/**
	 * Create an Instance of the Optimal Portfolio
	 * 
	 * @param aACOptimal The Array of the Optimal Asset Components
	 * @param ausp The AUSP Instance
	 * 
	 * @return The Instance of the Optimal Portfolio
	 */

	public static final OptimizationOutput Create (
		final org.drip.portfolioconstruction.asset.AssetComponent[] aACOptimal,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties ausp)
	{
		if (null == aACOptimal || null == ausp) return null;

		int iNumAsset = aACOptimal.length;

		if (0 == iNumAsset) return null;

		try {
			org.drip.portfolioconstruction.asset.Portfolio pfOptimal = new
				org.drip.portfolioconstruction.asset.Portfolio (aACOptimal);

			double dblPortfolioExcessReturnsMean = pfOptimal.expectedReturn (ausp);

			double dblPortfolioExcessReturnsVariance = pfOptimal.variance (ausp);

			double dblPortfolioExcessReturnsSigma = java.lang.Math.sqrt
				(dblPortfolioExcessReturnsVariance);

			double[] adblImpliedBeta = org.drip.numerical.linearalgebra.Matrix.Product (ausp.covariance
				(pfOptimal.id()), pfOptimal.weights());

			if (null == adblImpliedBeta) return null;

			for (int i = 0; i < iNumAsset; ++i)
				adblImpliedBeta[i] = adblImpliedBeta[i] / dblPortfolioExcessReturnsVariance;

			return new org.drip.portfolioconstruction.allocator.OptimizationOutput (new
				org.drip.portfolioconstruction.asset.Portfolio (aACOptimal), new
					org.drip.portfolioconstruction.asset.PortfolioMetrics (dblPortfolioExcessReturnsMean,
						dblPortfolioExcessReturnsVariance, dblPortfolioExcessReturnsSigma,
							dblPortfolioExcessReturnsMean / dblPortfolioExcessReturnsSigma,
								adblImpliedBeta));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * OptimizationOutput Constructor
	 * 
	 * @param pfOptimal The Optimal Portfolio
	 * @param pmOptimal The Optimal Portfolio Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OptimizationOutput (
		final org.drip.portfolioconstruction.asset.Portfolio pfOptimal,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics pmOptimal)
		throws java.lang.Exception
	{
		if (null == (_pfOptimal = pfOptimal) || null == (_pmOptimal = pmOptimal))
			throw new java.lang.Exception ("OptimizationOutput Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Optimal Portfolio Metrics
	 * 
	 * @return The Optimal Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioMetrics optimalMetrics()
	{
		return _pmOptimal;
	}

	/**
	 * Retrieve the Optimal Portfolio Instance
	 * 
	 * @return The Optimal Portfolio Instance
	 */

	public org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio()
	{
		return _pfOptimal;
	}
}
