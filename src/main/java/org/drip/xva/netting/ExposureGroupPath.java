
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
 * ExposureGroupPath rolls up the Path Realizations of the Sequence in a Single Path Projection Run over
 *  Multiple Collateral Groups onto a Single Exposure Group. The References are:
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

public class ExposureGroupPath
{
	private org.drip.xva.universe.MarketPath _marketPath = null;
	private org.drip.xva.netting.CollateralGroupPath[] _collateralGroupPathArray = null;

	/**
	 * ExposureGroupPath Constructor
	 * 
	 * @param collateralGroupPathArray Array of the Collateral Group Trajectory Paths
	 * @param marketPath The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ExposureGroupPath (
		final org.drip.xva.netting.CollateralGroupPath[] collateralGroupPathArray,
		final org.drip.xva.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == (_collateralGroupPathArray = collateralGroupPathArray) ||
			null == (_marketPath = marketPath))
		{
			throw new java.lang.Exception ("ExposureGroupPath Constructor => Invalid Inputs");
		}

		int collateralGroupCount = _collateralGroupPathArray.length;

		if (0 == collateralGroupCount)
		{
			throw new java.lang.Exception ("ExposureGroupPath Constructor => Invalid Inputs");
		}

		for (int i = 0; i < collateralGroupCount; ++i)
		{
			if (null == _collateralGroupPathArray[i])
			{
				throw new java.lang.Exception ("ExposureGroupPath Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of the Collateral Hypothecation Group Trajectory Paths
	 * 
	 * @return Array of the Collateral Hypothecation Group Trajectory Paths
	 */

	public org.drip.xva.netting.CollateralGroupPath[] collateralGroupPaths()
	{
		return _collateralGroupPathArray;
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
		return _collateralGroupPathArray[0].anchorDates();
	}

	/**
	 * Retrieve the Array of Collateralized Exposures
	 * 
	 * @return The Array of Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedExposure = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposure[vertexIndex] +=
					collateralGroupCollateralizedExposure[vertexIndex];
			}
		}

		return collateralizedExposure;
	}

	/**
	 * Retrieve the Array of Collateralized Exposure PV
	 * 
	 * @return The Array of Collateralized Exposure PV
	 */

	public double[] collateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] collateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposurePV[vertexIndex] +=
					collateralGroupCollateralizedExposure[vertexIndex] *
						marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return collateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Collateralized Positive Exposures
	 * 
	 * @return The Array of Collateralized Positive Exposures
	 */

	public double[] collateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] collateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = collateralGroupCollateralizedExposure[vertexIndex];

				if (0. < collateralizedExposure)
				{
					collateralizedPositiveExposure[vertexIndex] += collateralizedExposure;
				}
			}
		}

		return collateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Collateralized Positive Exposure PV
	 * 
	 * @return The Array of Collateralized Positive Exposures PV
	 */

	public double[] collateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] collateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = collateralGroupCollateralizedExposure[vertexIndex];

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
	 * Retrieve the Array of Collateralized Negative Exposures
	 * 
	 * @return The Array of Collateralized Negative Exposures
	 */

	public double[] collateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] collateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = collateralGroupCollateralizedExposure[vertexIndex];

				if (0. > collateralizedExposure)
				{
					collateralizedNegativeExposure[vertexIndex] += collateralizedExposure;
				}
			}
		}

		return collateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Collateralized Negative Exposure PV
	 * 
	 * @return The Array of Collateralized Negative Exposure PV
	 */

	public double[] collateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] collateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double collateralizedExposure = collateralGroupCollateralizedExposure[vertexIndex];

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
	 * Retrieve the Array of Uncollateralized Exposures
	 * 
	 * @return The Array of Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] uncollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupUncollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposure[vertexIndex] +=
					collateralGroupUncollateralizedExposure[vertexIndex];
			}
		}

		return uncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposure PV
	 * 
	 * @return The Array of Uncollateralized Exposure PV
	 */

	public double[] uncollateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] uncollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupUncollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposurePV[vertexIndex] +=
					collateralGroupUncollateralizedExposure[vertexIndex] *
						marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return uncollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Uncollateralized Positive Exposures
	 * 
	 * @return The Array of Uncollateralized Positive Exposures
	 */

	public double[] uncollateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] uncollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedPositiveExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] uncollateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				double uncollateralizedExposure =
					uncollateralGroupCollateralizedExposure[vertexIndex];

				if (0. < uncollateralizedExposure)
				{
					uncollateralizedPositiveExposure[vertexIndex] += uncollateralizedExposure;
				}
			}
		}

		return uncollateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Positive Exposure PV
	 * 
	 * @return The Array of Uncollateralized Positive Exposure PV
	 */

	public double[] uncollateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] uncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedPositiveExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] uncollateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double uncollateralizedExposure = uncollateralGroupCollateralizedExposure[vertexIndex];

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
	 * Retrieve the Array of Uncollateralized Negative Exposures
	 * 
	 * @return The Array of Uncollateralized Negative Exposures
	 */

	public double[] uncollateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] uncollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupUncollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double uncollateralizedExposure = collateralGroupUncollateralizedExposure[vertexIndex];

				if (0. > uncollateralizedExposure)
				{
					uncollateralizedNegativeExposure[vertexIndex] += uncollateralizedExposure;
				}
			}
		}

		return uncollateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Negative Exposure PV
	 * 
	 * @return The Array of Uncollateralized Negative Exposure PV
	 */

	public double[] uncollateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int collateralGroupCount = _collateralGroupPathArray.length;
		double[] uncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] uncollateralGroupCollateralizedExposure =
				_collateralGroupPathArray[collateralGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				double uncollateralizedExposure = uncollateralGroupCollateralizedExposure[vertexIndex];

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
	 * Retrieve the Array of Collateral Balances
	 * 
	 * @return The Array of Collateral Balances
	 */

	public double[] collateralBalance()
	{
		int vertexCount = anchorDates().length;

		double[] collateralBalance = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralBalance[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCollateralBalance =
				_collateralGroupPathArray[collateralGroupIndex].collateralBalance();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralBalance[vertexIndex] += collateralGroupCollateralBalance[vertexIndex];
			}
		}

		return collateralBalance;
	}

	/**
	 * Compute Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Path Bilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		double[] collateralBalance = collateralBalance();

		double bilateralCollateralValueAdjustment = 0.;
		int vertexCount = collateralBalance.length;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralBalance[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex - 1].csaSpread() *
				marketVertexArray[vertexIndex - 1].overnightReplicator();

			double periodIntegrandEnd = collateralBalance[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex].csaSpread() *
				marketVertexArray[vertexIndex].overnightReplicator();

			bilateralCollateralValueAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return bilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Unilateral Collateral Value Adjustment
	 * 
	 * @return The Path Unilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double unilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		double[] collateralBalance = collateralBalance();

		double unilateralCollateralValueAdjustment = 0.;
		int vertexCount = collateralBalance.length;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralBalance[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex - 1].csaSpread() *
				marketVertexArray[vertexIndex - 1].overnightReplicator();

			double periodIntegrandEnd = collateralBalance[vertexIndex] *
				marketVertexArray[vertexIndex].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex].csaSpread() *
				marketVertexArray[vertexIndex].overnightReplicator();

			unilateralCollateralValueAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return unilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Collateral Value Adjustment
	 * 
	 * @return The Path Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double collateralAdjustment()
		throws java.lang.Exception
	{
		return bilateralCollateralAdjustment();
	}

	/**
	 * Compute Period-wise Path Unilateral Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Unilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodUnilateralCollateralValueAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		double[] collateralBalance = collateralBalance();

		int vertexCount = collateralBalance.length;
		double[] periodUnilateralCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralBalance[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex - 1].csaSpread() *
				marketVertexArray[vertexIndex - 1].overnightReplicator();

			double periodIntegrandEnd = collateralBalance[vertexIndex] *
				marketVertexArray[vertexIndex].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex].csaSpread() *
				marketVertexArray[vertexIndex].overnightReplicator();

			periodUnilateralCollateralValueAdjustment[vertexIndex - 1] = -0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
					marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodUnilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Bilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodBilateralCollateralValueAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		double[] collateralBalance = collateralBalance();

		int vertexCount = collateralBalance.length;
		double[] periodBilateralCollateralValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralBalance[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex - 1].csaSpread() *
				marketVertexArray[vertexIndex - 1].overnightReplicator();

			double periodIntegrandEnd = collateralBalance[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex].csaSpread() *
				marketVertexArray[vertexIndex].overnightReplicator();

			periodBilateralCollateralValueAdjustment[vertexIndex - 1] = -0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
					marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodBilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodCollateralValueAdjustment()
		throws java.lang.Exception
	{
		return periodBilateralCollateralValueAdjustment();
	}

	/**
	 * Retrieve the Array of Credit Exposures
	 * 
	 * @return The Array of Credit Exposures
	 */

	public double[] creditExposure()
	{
		int vertexCount = anchorDates().length;

		double[] creditExposure = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCreditExposure =
				_collateralGroupPathArray[collateralGroupIndex].creditExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				creditExposure[vertexIndex] += collateralGroupCreditExposure[vertexIndex];
			}
		}

		return creditExposure;
	}

	/**
	 * Retrieve the Array of Credit Exposure PV
	 * 
	 * @return The Array of Credit Exposure PV
	 */

	public double[] creditExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] creditExposurePV = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupCreditExposure =
				_collateralGroupPathArray[collateralGroupIndex].creditExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				creditExposurePV[vertexIndex] += collateralGroupCreditExposure[vertexIndex] *
					marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return creditExposurePV;
	}

	/**
	 * Retrieve the Array of Debt Exposures
	 * 
	 * @return The Array of Debt Exposures
	 */

	public double[] debtExposure()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposure = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupDebtExposure =
				_collateralGroupPathArray[collateralGroupIndex].debtExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposure[vertexIndex] += collateralGroupDebtExposure[vertexIndex];
			}
		}

		return debtExposure;
	}

	/**
	 * Retrieve the Array of Debt Exposure PV
	 * 
	 * @return The Array of Debt Exposure PV
	 */

	public double[] debtExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposurePV = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupDebtExposure =
				_collateralGroupPathArray[collateralGroupIndex].debtExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposurePV[vertexIndex] += collateralGroupDebtExposure[vertexIndex] *
					marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return debtExposurePV;
	}

	/**
	 * Retrieve the Array of Funding Exposures
	 * 
	 * @return The Array of Funding Exposures
	 */

	public double[] fundingExposure()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposure = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			fundingExposure[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupFundingExposure =
				_collateralGroupPathArray[collateralGroupIndex].fundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += collateralGroupFundingExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	/**
	 * Retrieve the Array of Funding Exposure PV
	 * 
	 * @return The Array of Funding Exposure PV
	 */

	public double[] fundingExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposurePV = new double[vertexCount];
		int collateralGroupCount = _collateralGroupPathArray.length;

		org.drip.xva.universe.MarketVertex[] marketVertexArray = _marketPath.vertexes();

		for (int j = 0; j < vertexCount; ++j)
		{
			fundingExposurePV[j] = 0.;
		}

		for (int collateralGroupIndex = 0; collateralGroupIndex < collateralGroupCount;
			++collateralGroupIndex)
		{
			double[] collateralGroupFundingExposure =
				_collateralGroupPathArray[collateralGroupIndex].fundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposurePV[vertexIndex] += collateralGroupFundingExposure[vertexIndex] *
					marketVertexArray[vertexIndex].overnightReplicator();
			}
		}

		return fundingExposurePV;
	}
}
