
package org.drip.xva.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * PathSimulator drives the Simulation for various Latent States and Exposures. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathSimulator
{
	private int _iCount = -1;
	private org.drip.xva.dynamics.GroupSettings _groupSettings = null;
	private org.drip.xva.universe.MarketVertexGenerator _marketVertexGenerator = null;

	private org.drip.xva.hypothecation.CollateralGroupPath[] generateCollateralGroupVertexes (
		final org.drip.xva.universe.MarketPath marketPath)
	{
		int[] dateVertexArray = _marketVertexGenerator.vertexDates();

		org.drip.xva.universe.MarketVertex[] marketVertexes = marketPath.vertexes();

		org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification =
			_groupSettings.collateralGroupSpecification();

		org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification =
			_groupSettings.counterPartyGroupSpecification();

		int iNumTimeStep = dateVertexArray.length;
		org.drip.xva.hypothecation.CollateralGroupVertex[] collateralGroupVertexArray = new
			org.drip.xva.hypothecation.CollateralGroupVertex[iNumTimeStep];

		for (int j = 0; j < iNumTimeStep; ++j)
		{
			try
			{
				org.drip.analytics.date.JulianDate vertexDate = new org.drip.analytics.date.JulianDate
					(dateVertexArray[j]);

				collateralGroupVertexArray[j] = new org.drip.xva.hypothecation.AlbaneseAndersenVertex (
					vertexDate,
					marketVertexes[j].portfolioValue(),
					0.,
					0 == j ? 0. : new org.drip.xva.hypothecation.CollateralAmountEstimator (
						collateralGroupSpecification,
						counterPartyGroupSpecification,
						new org.drip.measure.bridge.BrokenDateInterpolatorLinearT (
							dateVertexArray[j - 1],
							dateVertexArray[j],
							marketVertexes[j - 1].portfolioValue(),
							marketVertexes[j].portfolioValue()
						),
						java.lang.Double.NaN
					).postingRequirement (vertexDate)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new org.drip.xva.hypothecation.CollateralGroupPath[]
			{
				new org.drip.xva.hypothecation.CollateralGroupPath (collateralGroupVertexArray)
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.xva.cpty.MonoPathExposureAdjustment singleTrajectory (
		final org.drip.xva.universe.MarketVertex initialMarketVertex)
	{
		try
		{
			org.drip.xva.universe.MarketPath marketPath = new org.drip.xva.universe.MarketPath
				(_marketVertexGenerator.marketVertex (initialMarketVertex));

			org.drip.xva.hypothecation.CollateralGroupPath[] collateralGroupPathArray =
				generateCollateralGroupVertexes (marketPath);

			return new org.drip.xva.cpty.MonoPathExposureAdjustment (
				new org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath[]
				{
					new org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath (
						collateralGroupPathArray,
						marketPath
					)
				},
				new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath[]
				{
					new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath (
						collateralGroupPathArray,
						marketPath
					)
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathSimulator Constructor
	 * 
	 * @param iCount Path Count
	 * @param marketVertexGenerator Market Vertex Generator
	 * @param groupSettings Group Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathSimulator (
		final int iCount,
		final org.drip.xva.universe.MarketVertexGenerator marketVertexGenerator,
		final org.drip.xva.dynamics.GroupSettings groupSettings)
		throws java.lang.Exception
	{
		if (0 >= (_iCount = iCount) ||
			null == (_marketVertexGenerator = marketVertexGenerator) ||
			null == (_groupSettings = groupSettings))
			throw new java.lang.Exception ("PathSimulator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Path Count
	 * 
	 * @return The Path Count
	 */

	public int count()
	{
		return _iCount;
	}

	/**
	 * Retrieve the Market Vertex Generator
	 * 
	 * @return The Market Vertex Generator
	 */

	public org.drip.xva.universe.MarketVertexGenerator marketVertexGenerator()
	{
		return _marketVertexGenerator;
	}

	/**
	 * Simulate the Realized State/Entity Values and their Aggregates over the Paths
	 * 
	 * @param initialMarketVertex The Initial Market Vertex
	 * 
	 * @return The Exposure Adjustment Aggregator - Simulation Result
	 */

	public org.drip.xva.cpty.ExposureAdjustmentAggregator simulate (
		final org.drip.xva.universe.MarketVertex initialMarketVertex)
	{
		org.drip.xva.cpty.PathExposureAdjustment[] pathExposureAdjustmentArray = new
			org.drip.xva.cpty.PathExposureAdjustment[_iCount];

		for (int i = 0; i < _iCount; ++i)
		{
			if (null == (pathExposureAdjustmentArray[i] = singleTrajectory (initialMarketVertex)))
				return null;
		}

		try
		{
			return new org.drip.xva.cpty.ExposureAdjustmentAggregator (pathExposureAdjustmentArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
