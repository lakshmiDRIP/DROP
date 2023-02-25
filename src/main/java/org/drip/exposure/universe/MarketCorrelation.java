
package org.drip.exposure.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>MarketCorrelation</i> holds the Cross Latent State Correlations needed for computing the Valuation
 * Adjustment. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/README.md">Exposure Generation - Market States Simulation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketCorrelation
{
	private double[][] _matrix = null;
	private java.util.List<org.drip.state.identifier.LatentStateLabel> _latentStateLabelList = null;

	private java.util.Map<java.lang.String, java.lang.Integer> _latentStateIndexMap = new
		java.util.HashMap<java.lang.String, java.lang.Integer>();

	/**
	 * MarketCorrelation Constructor
	 * 
	 * @param latentStateLabelList The Latent State Label List
	 * @param matrix The Square Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketCorrelation (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList,
		final double[][] matrix)
		throws java.lang.Exception
	{
		if (null == (_latentStateLabelList = latentStateLabelList) ||
			null == (_matrix = matrix))
		{
			throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
		}

		int latentStateCount = _latentStateLabelList.size();

		if (0 == latentStateCount || latentStateCount != _matrix.length)
		{
			throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
		}

		for (int latentStateIndex = 0; latentStateIndex < latentStateCount; ++latentStateIndex)
		{
			org.drip.state.identifier.LatentStateLabel latentStateLabel = _latentStateLabelList.get
				(latentStateIndex);

			if (null == latentStateLabel ||
				null == _matrix[latentStateIndex] ||
				latentStateCount != _matrix[latentStateIndex].length)
			{
				throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
			}

			for (int matrixColumnIndex = 0; matrixColumnIndex < latentStateCount; ++matrixColumnIndex)
			{
				double correlationEntry = _matrix[latentStateIndex][matrixColumnIndex];

				if (!org.drip.numerical.common.NumberUtil.IsValid (correlationEntry) ||
					(latentStateIndex == matrixColumnIndex && 1 != correlationEntry) ||
					(latentStateIndex != matrixColumnIndex && (1 < correlationEntry ||
						-1 < correlationEntry)))
				{
					throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
				}
			}

			java.lang.String latentStateLabelFQN = latentStateLabel.fullyQualifiedName();

			if (_latentStateIndexMap.containsKey (latentStateLabelFQN))
			{
				throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
			}

			_latentStateIndexMap.put (
				latentStateLabelFQN,
				latentStateIndex
			);
		}
	}

	/**
	 * Retrieve the Latent State Label List
	 * 
	 * @return The Latent State Label List
	 */

	public java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList()
	{
		return _latentStateLabelList;
	}

	/**
	 * Retrieve the Cross-Latent State Correlation Matrix
	 * 
	 * @return The Cross-Latent State Correlation Matrix
	 */

	public double[][] matrix()
	{
		return _matrix;
	}

	/**
	 * Check if the Latent State  is available in the Correlation Matrix
	 * 
	 * @param latentStateLabel The Latent State Label
	 * 
	 * @return TRUE - The Latent State  is available in the Correlation Matrix
	 */

	public boolean latentStateExists (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		return null != latentStateLabel && _latentStateIndexMap.containsKey
			(latentStateLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Cross State Correlation
	 * 
	 * @param latentStateLabel1 Latent State Label #1
	 * @param latentStateLabel2 Latent State Label #2
	 * 
	 * @return The Cross State Correlation
	 */

	public double entry (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel1,
		final org.drip.state.identifier.LatentStateLabel latentStateLabel2)
	{
		return !latentStateExists (latentStateLabel1) || !latentStateExists (latentStateLabel2) ? 0. :
			_matrix[_latentStateIndexMap.get (latentStateLabel1.fullyQualifiedName())]
				[_latentStateIndexMap.get (latentStateLabel2.fullyQualifiedName())];
	}

	/**
	 * Synthesize a MarketCorrelation Instance for the Custom Latent State List
	 * 
	 * @param customLatentStateLabelList The Custom Latent State List
	 * 
	 * @return The MarketCorrelation Instance for the Custom Latent State List
	 */

	public MarketCorrelation customMarketCorrelation (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> customLatentStateLabelList)
	{
		if (null == customLatentStateLabelList)
		{
			return null;
		}

		int customLatentStateCount = customLatentStateLabelList.size();

		if (0 == customLatentStateCount)
		{
			return null;
		}

		double[][] customMatix = new double[customLatentStateCount][customLatentStateCount];

		for (int customLatentStateIndexOuter = 0; customLatentStateIndexOuter < customLatentStateCount;
			++customLatentStateIndexOuter)
		{
			for (int customLatentStateIndexInner = 0; customLatentStateIndexInner < customLatentStateCount;
				++customLatentStateIndexInner)
			{
				customMatix[customLatentStateIndexOuter][customLatentStateIndexInner] =
					customLatentStateIndexOuter == customLatentStateIndexInner ? 1. :
					entry (
						customLatentStateLabelList.get (customLatentStateIndexOuter),
						customLatentStateLabelList.get (customLatentStateIndexInner)
					);
			}
		}

		try
		{
			return new MarketCorrelation (
				customLatentStateLabelList,
				customMatix
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
