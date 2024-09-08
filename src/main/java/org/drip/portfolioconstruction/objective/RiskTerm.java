
package org.drip.portfolioconstruction.objective;

import org.drip.portfolioconstruction.composite.Holdings;

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
 * <i>RiskTerm</i> holds the Details of the Portfolio Risk Objective Term. Risk can be Absolute or in
 * relation to a Benchmark, and can be measured as Variance or Standard Deviation.
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

public abstract class RiskTerm
	extends org.drip.portfolioconstruction.optimizer.ObjectiveTerm
{
	private double[][] _assetCovarianceMatrix = null;
	private double[] _benchmarkConstrictedHoldingsArray = null;

	protected RiskTerm (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description,
		final Holdings initialHoldings,
		final double[][] assetCovarianceMatrix,
		final double[] benchmarkConstrictedHoldingsArray)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description,
			"RISK",
			initialHoldings
		);

		int assetCount = initialHoldings.size();

		if (null == (_assetCovarianceMatrix = assetCovarianceMatrix) ||
			assetCount != _assetCovarianceMatrix.length)
		{
			throw new java.lang.Exception ("RiskTerm Constructor => Invalid Inputs");
		}

		for (int assetIndex = 0; assetIndex < assetCount; ++assetIndex)
		{
			if (null == _assetCovarianceMatrix[assetIndex] ||
				!org.drip.numerical.common.NumberUtil.IsValid (_assetCovarianceMatrix[assetIndex]) ||
				assetCount != _assetCovarianceMatrix[assetIndex].length)
			{
				throw new java.lang.Exception ("RiskTerm Constructor => Invalid Inputs");
			}
		}

		if (null != (_benchmarkConstrictedHoldingsArray = benchmarkConstrictedHoldingsArray) &&
			(assetCount != _benchmarkConstrictedHoldingsArray.length ||
				!org.drip.numerical.common.NumberUtil.IsValid (_benchmarkConstrictedHoldingsArray)))
		{
			throw new java.lang.Exception ("RiskTerm Constructor => Invalid Inputs");
		}
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
	 * Retrieve the Benchmark Constricted Holdings
	 * 
	 * @return The Benchmark Constricted Holdings
	 */

	public double[] benchmarkConstrictedHoldings()
	{
		return _benchmarkConstrictedHoldingsArray;
	}
}
