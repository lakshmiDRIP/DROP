
package org.drip.xva.netting;

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
 * PositionGroupPath accumulates the Vertex Realizations of the Sequence in a Single Path Projection Run
 *  along the Granularity of a Regular Position Hypothecation Group. The References are:
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

public class PositionGroupPath
{
	private org.drip.xva.hypothecation.PositionGroupVertex[] _positionGroupVertexArray = null;

	/**
	 * PositionGroupPath Constructor
	 * 
	 * @param positionGroupVertexArray The Array of Collateral Hypothecation Group Trajectory Vertexes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionGroupPath (
		final org.drip.xva.hypothecation.PositionGroupVertex[] positionGroupVertexArray)
		throws java.lang.Exception
	{
		if (null == (_positionGroupVertexArray = positionGroupVertexArray))
		{
			throw new java.lang.Exception ("PositionGroupPath Constructor => Invalid Inputs");
		}

		int vertexCount = _positionGroupVertexArray.length;

		if (1 >= vertexCount)
		{
			throw new java.lang.Exception ("PositionGroupPath Constructor => Invalid Inputs");
		}

		for (int i = 0; i < vertexCount; ++i)
		{
			if (null == _positionGroupVertexArray[i])
			{
				throw new java.lang.Exception ("PositionGroupPath Constructor => Invalid Inputs");
			}

			if (0 != i && _positionGroupVertexArray[i - 1].anchor().julian() >=
				_positionGroupVertexArray[i].anchor().julian())
			{
				throw new java.lang.Exception ("PositionGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Position Group Trajectory Vertexes
	 * 
	 * @return The Array of Position Group Trajectory Vertexes
	 */

	public org.drip.xva.hypothecation.PositionGroupVertex[] vertexes()
	{
		return _positionGroupVertexArray;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		int vertexCount = _positionGroupVertexArray.length;
		org.drip.analytics.date.JulianDate[] vertexDateArray = new
			org.drip.analytics.date.JulianDate[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			vertexDateArray[i] = _positionGroupVertexArray[i].anchor();
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
		int vertexCount = _positionGroupVertexArray.length;
		double[] collateralizedExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			collateralizedExposure[i] = _positionGroupVertexArray[i].collateralized();
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
		int vertexCount = _positionGroupVertexArray.length;
		double[] uncollateralizedExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			uncollateralizedExposure[i] = _positionGroupVertexArray[i].uncollateralized();
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
		int vertexCount = _positionGroupVertexArray.length;
		double[] creditExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			creditExposure[i] = _positionGroupVertexArray[i].credit();
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
		int vertexCount = _positionGroupVertexArray.length;
		double[] debtExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			debtExposure[i] = _positionGroupVertexArray[i].debt();
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
		int vertexCount = _positionGroupVertexArray.length;
		double[] fundingExposure = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			fundingExposure[i] = _positionGroupVertexArray[i].funding();
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
		int vertexCount = _positionGroupVertexArray.length;
		double[] collateralizedBalance = new double[vertexCount];

		for (int i = 0; i < vertexCount; ++i)
		{
			collateralizedBalance[i] = _positionGroupVertexArray[i].collateralBalance();
		}

		return collateralizedBalance;
	}
}
