
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
 * CollateralGroupPath rolls up the Path Realizations of the Sequence in a Single Path Projection Run over
 *  Multiple Position Groups onto a Single Collateral Group. The References are:
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
	private org.drip.xva.universe.MarketPath _marketPath = null;
	private org.drip.xva.netting.PositionGroupPath[] _positionGroupPathArray = null;

	/**
	 * CollateralGroupPath Constructor
	 * 
	 * @param positionGroupPathArray Array of the Position Group Trajectory Paths
	 * @param marketPath The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupPath (
		final org.drip.xva.netting.PositionGroupPath[] positionGroupPathArray,
		final org.drip.xva.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == (_positionGroupPathArray = positionGroupPathArray) ||
			null == (_marketPath = marketPath))
		{
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		int positionGroupCount = _positionGroupPathArray.length;

		if (0 == positionGroupCount)
		{
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}

		for (int i = 0; i < positionGroupCount; ++i)
		{
			if (null == _positionGroupPathArray[i])
			{
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of the Position Hypothecation Group Trajectory Paths
	 * 
	 * @return Array of the Position Hypothecation Group Trajectory Paths
	 */

	public org.drip.xva.netting.PositionGroupPath[] positionGroupPaths()
	{
		return _positionGroupPathArray;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public org.drip.xva.universe.MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		return _positionGroupPathArray[0].anchorDates();
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposures
	 * 
	 * @return The Array of Vertex Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedExposure = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposure[vertexIndex] += positionGroupCollateralizedExposure[vertexIndex];
			}
		}

		return collateralizedExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Exposure PV
	 */

	public double[] collateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] collateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposurePV =
				_positionGroupPathArray[positionGroupIndex].collateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposurePV[vertexIndex] += positionGroupCollateralizedExposurePV[vertexIndex];
			}
		}

		return collateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Positive Exposures
	 * 
	 * @return The Array of Vertex Collateralized Positive Exposures
	 */

	public double[] collateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] collateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. < collateralizedExposure)
				{
					collateralizedPositiveExposure[vertexIndex] += collateralizedExposure;
				}
			}
		}

		return collateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Positive Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Positive Exposures PV
	 */

	public double[] collateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] collateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. < collateralizedExposure)
				{
					collateralizedPositiveExposurePV[vertexIndex] += collateralizedExposure *
						marketVertexArray[vertexIndex].overnightReplicator();
				}
			}
		}

		return collateralizedPositiveExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Negative Exposures
	 * 
	 * @return The Array of Vertex Collateralized Negative Exposures
	 */

	public double[] collateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] collateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. > collateralizedExposure)
				{
					collateralizedNegativeExposure[vertexIndex] += collateralizedExposure;
				}
			}
		}

		return collateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Vertex Collateralized Negative Exposure PV
	 * 
	 * @return The Array of Vertex Collateralized Negative Exposure PV
	 */

	public double[] collateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] collateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. > collateralizedExposure)
				{
					collateralizedNegativeExposurePV[vertexIndex] += collateralizedExposure *
						marketVertexArray[vertexIndex].overnightReplicator();
				}
			}
		}

		return collateralizedNegativeExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] uncollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupUncollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposure[vertexIndex] += positionGroupUncollateralizedExposure[vertexIndex];
			}
		}

		return uncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Exposure PV
	 */

	public double[] uncollateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] uncollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupUncollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposurePV[vertexIndex] += positionGroupUncollateralizedExposure[vertexIndex]
					* marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return uncollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Positive Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Positive Exposures
	 */

	public double[] uncollateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] uncollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedPositiveExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				double uncollateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. < uncollateralizedExposure)
				{
					uncollateralizedPositiveExposure[vertexIndex] += uncollateralizedExposure;
				}
			}
		}

		return uncollateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Positive Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Positive Exposure PV
	 */

	public double[] uncollateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] uncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedPositiveExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double uncollateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. < uncollateralizedExposure)
				{
					uncollateralizedPositiveExposurePV[vertexIndex] += uncollateralizedExposure *
						marketVertexArray[vertexIndex].overnightReplicator();
				}
			}
		}

		return uncollateralizedPositiveExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Negative Exposures
	 * 
	 * @return The Array of Vertex Uncollateralized Negative Exposures
	 */

	public double[] uncollateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] uncollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupUncollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double uncollateralizedExposure = positionGroupUncollateralizedExposure[vertexIndex];

				if (0. > uncollateralizedExposure)
				{
					uncollateralizedNegativeExposure[vertexIndex] += uncollateralizedExposure;
				}
			}
		}

		return uncollateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Vertex Uncollateralized Negative Exposure PV
	 * 
	 * @return The Array of Vertex Uncollateralized Negative Exposure PV
	 */

	public double[] uncollateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int positionGroupCount = _positionGroupPathArray.length;
		double[] uncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralizedExposure =
				_positionGroupPathArray[positionGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double uncollateralizedExposure = positionGroupCollateralizedExposure[vertexIndex];

				if (0. > uncollateralizedExposure)
				{
					uncollateralizedNegativeExposurePV[vertexIndex] += uncollateralizedExposure *
						marketVertexArray[vertexIndex].overnightReplicator();
				}
			}
		}

		return uncollateralizedNegativeExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Collateral Balances
	 * 
	 * @return The Array of Vertex Collateral Balances
	 */

	public double[] collateralBalance()
	{
		int vertexCount = anchorDates().length;

		double[] collateralBalance = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralBalance[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCollateralBalance =
				_positionGroupPathArray[positionGroupIndex].collateralBalance();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralBalance[vertexIndex] += positionGroupCollateralBalance[vertexIndex];
			}
		}

		return collateralBalance;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposures
	 * 
	 * @return The Array of Vertex Credit Exposures
	 */

	public double[] creditExposure()
	{
		int vertexCount = anchorDates().length;

		double[] creditExposure = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCreditExposure =
				_positionGroupPathArray[positionGroupIndex].creditExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				creditExposure[vertexIndex] += positionGroupCreditExposure[vertexIndex];
			}
		}

		return creditExposure;
	}

	/**
	 * Retrieve the Array of Vertex Credit Exposure PV
	 * 
	 * @return The Array of Vertex Credit Exposure PV
	 */

	public double[] creditExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] creditExposurePV = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupCreditExposure =
				_positionGroupPathArray[positionGroupIndex].creditExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				creditExposurePV[vertexIndex] += positionGroupCreditExposure[vertexIndex] *
					marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return creditExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposures
	 * 
	 * @return The Array of Vertex Debt Exposures
	 */

	public double[] debtExposure()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposure = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupDebtExposure = _positionGroupPathArray[positionGroupIndex].debtExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposure[vertexIndex] += positionGroupDebtExposure[vertexIndex];
			}
		}

		return debtExposure;
	}

	/**
	 * Retrieve the Array of Vertex Debt Exposure PV
	 * 
	 * @return The Array of Vertex Debt Exposure PV
	 */

	public double[] debtExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposurePV = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupDebtExposure = _positionGroupPathArray[positionGroupIndex].debtExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposurePV[vertexIndex] += positionGroupDebtExposure[vertexIndex] *
					marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return debtExposurePV;
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposures
	 * 
	 * @return The Array of Vertex Funding Exposures
	 */

	public double[] fundingExposure()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposure = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			fundingExposure[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupFundingExposure =
				_positionGroupPathArray[positionGroupIndex].fundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += positionGroupFundingExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	/**
	 * Retrieve the Array of Vertex Funding Exposure PV
	 * 
	 * @return The Array of Vertex Funding Exposure PV
	 */

	public double[] fundingExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposurePV = new double[vertexCount];
		int positionGroupCount = _positionGroupPathArray.length;

		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		for (int j = 0; j < vertexCount; ++j)
		{
			fundingExposurePV[j] = 0.;
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			double[] positionGroupFundingExposure =
				_positionGroupPathArray[positionGroupIndex].fundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposurePV[vertexIndex] += positionGroupFundingExposure[vertexIndex] *
					marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return fundingExposurePV;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 */

	public double[] periodCollateralValueAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		double[] collateralBalance = collateralBalance();

		int vertexCount = collateralBalance.length;
		double[] periodCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralBalance[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].csaSpread() *
				marketVertexArray[vertexIndex - 1].overnightReplicator();

			double periodIntegrandEnd = collateralBalance[vertexIndex] *
				marketVertexArray[vertexIndex].csaSpread() *
				marketVertexArray[vertexIndex].overnightReplicator();

			periodCollateralValueAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd)
				* (marketVertexArray[vertexIndex].anchorDate().julian() -
					marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodCollateralValueAdjustment;
	}
}
