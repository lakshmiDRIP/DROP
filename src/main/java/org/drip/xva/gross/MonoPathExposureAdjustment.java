
package org.drip.xva.gross;

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
 * MonoPathExposureAdjustment aggregates the Exposures and the Adjustments across Multiple Netting/Funding
 *  Groups on a Single Path Projection Run along the Granularity of a Counter Party Group. The References
 *  are:
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

public class MonoPathExposureAdjustment implements org.drip.xva.gross.PathExposureAdjustment
{
	private org.drip.xva.netting.FundingGroupPath[] _fundingGroupPathArray = null;
	private org.drip.xva.netting.CreditDebtGroupPath[] _creditDebtGroupPathArray = null;

	/**
	 * MonoPathExposureAdjustment Constructor
	 * 
	 * @param creditDebtGroupPathArray The Array of Credit/Debt Netting Group Paths
	 * @param fundingGroupPathArray The Array of Funding Group Paths
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MonoPathExposureAdjustment (
		final org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray,
		final org.drip.xva.netting.FundingGroupPath[] fundingGroupPathArray)
		throws java.lang.Exception
	{
		if (null == (_creditDebtGroupPathArray = creditDebtGroupPathArray) ||
			0 == _creditDebtGroupPathArray.length ||
			null == (_fundingGroupPathArray = fundingGroupPathArray) ||
			0 == _fundingGroupPathArray.length)
		{
			throw new java.lang.Exception ("MonoPathExposureAdjustment Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Funding Group Trajectory Paths
	 * 
	 * @return The Array of the Funding Group Trajectory Paths
	 */

	public org.drip.xva.netting.FundingGroupPath[] fundingGroupTrajectoryPaths()
	{
		return _fundingGroupPathArray;
	}

	/**
	 * Retrieve the Array of Credit/Debt Netting Group Trajectory Paths
	 * 
	 * @return The Array of Credit/Debt Netting Group Trajectory Paths
	 */

	public org.drip.xva.netting.CreditDebtGroupPath[] nettingGroupTrajectoryPaths()
	{
		return _creditDebtGroupPathArray;
	}

	@Override public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		return _creditDebtGroupPathArray[0].anchorDates();
	}

	@Override public double[] collateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] collateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupCollateralizedExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposure[vertexIndex] += creditDebtGroupCollateralizedExposure[vertexIndex];
			}
		}

		return collateralizedExposure;
	}

	@Override public double[] collateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] collateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupCollateralizedExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].collateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposurePV[vertexIndex] +=
					creditDebtGroupCollateralizedExposurePV[vertexIndex];
			}
		}

		return collateralizedExposurePV;
	}

	@Override public double[] collateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] collateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] adblCreditDebtGroupCollateralizedPositiveExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].collateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedPositiveExposure[vertexIndex] +=
					adblCreditDebtGroupCollateralizedPositiveExposure[vertexIndex];
			}
		}

		return collateralizedPositiveExposure;
	}

	@Override public double[] collateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] collateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupCollateralizedPositiveExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].collateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedPositiveExposurePV[vertexIndex] +=
					creditDebtGroupCollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return collateralizedPositiveExposurePV;
	}

	@Override public double[] collateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] collateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupCollateralizedNegativeExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].collateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedNegativeExposure[vertexIndex] +=
					creditDebtGroupCollateralizedNegativeExposure[vertexIndex];
			}
		}

		return collateralizedNegativeExposure;
	}

	@Override public double[] collateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] collateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupCollateralizedNegativeExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].collateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedNegativeExposurePV[vertexIndex] +=
					creditDebtGroupCollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return collateralizedNegativeExposurePV;
	}

	@Override public double[] uncollateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] uncollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupUncollateralizedExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposure[vertexIndex] +=
					creditDebtGroupUncollateralizedExposure[vertexIndex];
			}
		}

		return uncollateralizedExposure;
	}

	@Override public double[] uncollateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] uncollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupUncollateralizedExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].uncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposurePV[vertexIndex] +=
					creditDebtGroupUncollateralizedExposurePV[vertexIndex];
			}
		}

		return uncollateralizedExposurePV;
	}

	@Override public double[] uncollateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] adblUncollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
			adblUncollateralizedPositiveExposure[j] = 0.;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex) {
			double[] creditDebtGroupUncollateralizedPositiveExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].uncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
				adblUncollateralizedPositiveExposure[vertexIndex] +=
					creditDebtGroupUncollateralizedPositiveExposure[vertexIndex];
		}

		return adblUncollateralizedPositiveExposure;
	}

	@Override public double[] uncollateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] uncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedPositiveExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupUncollateralizedPositiveExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].uncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedPositiveExposurePV[vertexIndex] +=
					creditDebtGroupUncollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposurePV;
	}

	@Override public double[] uncollateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] uncollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupUncollateralizedNegativeExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].uncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedNegativeExposure[vertexIndex] +=
					creditDebtGroupUncollateralizedNegativeExposure[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposure;
	}

	@Override public double[] uncollateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int creditDebtGroupCount = _creditDebtGroupPathArray.length;
		double[] uncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupUncollateralizedNegativeExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].uncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedNegativeExposurePV[vertexIndex] +=
					creditDebtGroupUncollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposurePV;
	}

	@Override public double[] creditExposure()
	{
		int vertexCount = anchorDates().length;

		double[] creditExposure = new double[vertexCount];
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].creditExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				creditExposure[vertexIndex] += creditDebtGroupExposure[vertexIndex];
			}
		}

		return creditExposure;
	}

	@Override public double[] creditExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] creditExposurePV = new double[vertexCount];
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].creditExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				creditExposurePV[vertexIndex] += creditDebtGroupExposurePV[vertexIndex];
			}
		}

		return creditExposurePV;
	}

	@Override public double[] debtExposure()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposure = new double[vertexCount];
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposure[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupExposure =
				_creditDebtGroupPathArray[creditDebtGroupIndex].debtExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposure[vertexIndex] += creditDebtGroupExposure[vertexIndex];
			}
		}

		return debtExposure;
	}

	@Override public double[] debtExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposurePV = new double[vertexCount];
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposurePV[j] = 0.;
		}

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			double[] creditDebtGroupExposurePV =
				_creditDebtGroupPathArray[creditDebtGroupIndex].debtExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposurePV[vertexIndex] += creditDebtGroupExposurePV[vertexIndex];
			}
		}

		return debtExposurePV;
	}

	@Override public double[] fundingExposure()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposure = new double[vertexCount];
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			fundingExposure[j] = 0.;
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			double[] fundingGroupExposure = _fundingGroupPathArray[fundingGroupIndex].fundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += fundingGroupExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	@Override public double[] fundingExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposurePV = new double[vertexCount];
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			fundingExposurePV[j] = 0.;
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			double[] fundingGroupExposurePV = _fundingGroupPathArray[fundingGroupIndex].fundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposurePV[vertexIndex] += fundingGroupExposurePV[vertexIndex];
			}
		}

		return fundingExposurePV;
	}

	@Override public double bilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		double bilateralCollateralAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			bilateralCollateralAdjustment +=
				_creditDebtGroupPathArray[creditDebtGroupIndex].bilateralCollateralAdjustment();
		}

		return bilateralCollateralAdjustment;
	}

	@Override public double collateralAdjustment()
		throws java.lang.Exception
	{
		return bilateralCollateralAdjustment();
	}

	@Override public double unilateralCreditAdjustment()
		throws java.lang.Exception
	{
		double unilateralCreditAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			unilateralCreditAdjustment +=
				_creditDebtGroupPathArray[creditDebtGroupIndex].unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
		throws java.lang.Exception
	{
		double bilateralCreditAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			bilateralCreditAdjustment +=
				_creditDebtGroupPathArray[creditDebtGroupIndex].bilateralCreditAdjustment();
		}

		return bilateralCreditAdjustment;
	}

	@Override public double creditAdjustment()
		throws java.lang.Exception
	{
		return bilateralCreditAdjustment();
	}

	@Override public double contraLiabilityCreditAdjustment()
		throws java.lang.Exception
	{
		double contraLiabilityCreditAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			contraLiabilityCreditAdjustment +=
				_creditDebtGroupPathArray[creditDebtGroupIndex].contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
		throws java.lang.Exception
	{
		double unilateralDebtAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			unilateralDebtAdjustment +=
				_creditDebtGroupPathArray[creditDebtGroupIndex].unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
		throws java.lang.Exception
	{
		double bilateralDebtAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			bilateralDebtAdjustment +=
				_creditDebtGroupPathArray[creditDebtGroupIndex].bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
		throws java.lang.Exception
	{
		double debtAdjustment = 0.;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			debtAdjustment += _creditDebtGroupPathArray[creditDebtGroupIndex].debtAdjustment();
		}

		return debtAdjustment;
	}

	@Override public double fundingValueAdjustment()
		throws java.lang.Exception
	{
		double fundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingValueAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingValueAdjustment();
		}

		return fundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
		throws java.lang.Exception
	{
		double fundingDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingDebtAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingDebtAdjustment();
		}

		return fundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		double fundingCostAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingCostAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingCostAdjustment();
		}

		return fundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		double fundingBenefitAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			fundingBenefitAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingBenefitAdjustment();
		}

		return fundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		double symmetricFundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			symmetricFundingValueAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].symmetricFundingValueAdjustment();
		}

		return symmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
		throws java.lang.Exception
	{
		double totalAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;
		int creditDebtGroupCount = _creditDebtGroupPathArray.length;

		for (int creditDebtGroupIndex = 0; creditDebtGroupIndex < creditDebtGroupCount;
			++creditDebtGroupIndex)
		{
			totalAdjustment += _creditDebtGroupPathArray[creditDebtGroupIndex].creditAdjustment() +
				_creditDebtGroupPathArray[creditDebtGroupIndex].debtAdjustment();
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			totalAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingValueAdjustment();
		}

		return totalAdjustment;
	}
}
