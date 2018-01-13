
package org.drip.xva.hypothecation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * CollateralGroupPath accumulates the Vertex Realizations of the Sequence in a Single Path Projection Run
 *  along the Granularity of a Regular Collateral Hypothecation Group. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroupPath
{
	private org.drip.xva.hypothecation.CollateralGroupVertex[] _collateralGroupVertexArray = null;

	/**
	 * CollateralGroupPath Constructor
	 * 
	 * @param collateralGroupVertexArray The Array of Collateral Hypothecation Group Trajectory Vertexes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupVertex[] collateralGroupVertexArray)
		throws java.lang.Exception
	{
		if (null == (_collateralGroupVertexArray = collateralGroupVertexArray))
		{
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		int pathCount = _collateralGroupVertexArray.length;

		if (1 >= pathCount)
		{
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		for (int i = 0; i < pathCount; ++i)
		{
			if (null == _collateralGroupVertexArray[i])
			{
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}

			if (0 != i && _collateralGroupVertexArray[i - 1].anchor().julian() >=
				_collateralGroupVertexArray[i].anchor().julian())
			{
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Netting Group Trajectory Vertexes
	 * 
	 * @return The Array of Netting Group Trajectory Vertexes
	 */

	public org.drip.xva.hypothecation.CollateralGroupVertex[] vertexes()
	{
		return _collateralGroupVertexArray;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		org.drip.analytics.date.JulianDate[] vertexDateArray = new
			org.drip.analytics.date.JulianDate[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			vertexDateArray[i] = _collateralGroupVertexArray[i].anchor();
		}

		return vertexDateArray;
	}

	/**
	 * Retrieve the Array of Collateralized Exposures
	 * 
	 * @return The Array of Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			collateralizedExposure[i] = _collateralGroupVertexArray[i].collateralized();
		}

		return collateralizedExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposures
	 * 
	 * @return The Array of Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] uncollateralizedExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			uncollateralizedExposure[i] = _collateralGroupVertexArray[i].uncollateralized();
		}

		return uncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Credit Exposures
	 * 
	 * @return The Array of Credit Exposures
	 */

	public double[] creditExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] creditExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			creditExposure[i] = _collateralGroupVertexArray[i].credit();
		}

		return creditExposure;
	}

	/**
	 * Retrieve the Array of Debt Exposures
	 * 
	 * @return The Array of Debt Exposures
	 */

	public double[] debtExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] debtExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			debtExposure[i] = _collateralGroupVertexArray[i].debt();
		}

		return debtExposure;
	}

	/**
	 * Retrieve the Array of Funding Exposures
	 * 
	 * @return The Array of Funding Exposures
	 */

	public double[] fundingExposure()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] fundingExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			fundingExposure[i] = _collateralGroupVertexArray[i].funding();
		}

		return fundingExposure;
	}

	/**
	 * Retrieve the Array of Collateral Balances
	 * 
	 * @return The Array of Collateral Balances
	 */

	public double[] collateralBalance()
	{
		int vertexCount = _collateralGroupVertexArray.length;
		double[] collateralizedBalance = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			collateralizedBalance[i] = _collateralGroupVertexArray[i].collateralBalance();
		}

		return collateralizedBalance;
	}
}
