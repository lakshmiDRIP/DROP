
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
	private java.util.List<org.drip.xva.netting.CreditDebtGroupPath> _creditDebtGroupPathList = null;

	/**
	 * MonoPathExposureAdjustment Constructor
	 * 
	 * @param fundingGroupPathArray The Array of Funding Group Paths
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MonoPathExposureAdjustment (
		final org.drip.xva.netting.FundingGroupPath[] fundingGroupPathArray)
		throws java.lang.Exception
	{
		if (null == (_fundingGroupPathArray = fundingGroupPathArray))
		{
			throw new java.lang.Exception ("MonoPathExposureAdjustment Constructor => Invalid Inputs");
		}

		int fundingGroupCount = _fundingGroupPathArray.length;

		_creditDebtGroupPathList = new java.util.ArrayList<org.drip.xva.netting.CreditDebtGroupPath>();

		if (0 == fundingGroupCount)
		{
			throw new java.lang.Exception ("MonoPathExposureAdjustment Constructor => Invalid Inputs");
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray =
				_fundingGroupPathArray[fundingGroupIndex].creditDebtGroupPathArray();

			for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : creditDebtGroupPathArray)
			{
				_creditDebtGroupPathList.add (creditDebtGroupPath);
			}
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
		int creditDebtGroupPathIndex = 0;

		org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray = new
			org.drip.xva.netting.CreditDebtGroupPath[_creditDebtGroupPathList.size()];

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			creditDebtGroupPathArray[creditDebtGroupPathIndex++] = creditDebtGroupPath;
		}

		return creditDebtGroupPathArray;
	}

	@Override public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		return _creditDebtGroupPathList.get (0).anchorDates();
	}

	@Override public double[] collateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupCollateralizedExposure = creditDebtGroupPath.collateralizedExposure();

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

		double[] collateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupCollateralizedExposurePV =
				creditDebtGroupPath.collateralizedExposurePV();

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

		double[] collateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] adblCreditDebtGroupCollateralizedPositiveExposure =
				creditDebtGroupPath.collateralizedPositiveExposure();

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

		double[] collateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedPositiveExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupCollateralizedPositiveExposurePV =
				creditDebtGroupPath.collateralizedPositiveExposurePV();

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

		double[] collateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupCollateralizedNegativeExposure =
				creditDebtGroupPath.collateralizedNegativeExposure();

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

		double[] collateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedNegativeExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupCollateralizedNegativeExposurePV =
				creditDebtGroupPath.collateralizedNegativeExposurePV();

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

		double[] uncollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupUncollateralizedExposure =
				creditDebtGroupPath.uncollateralizedExposure();

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

		double[] uncollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupUncollateralizedExposurePV =
				creditDebtGroupPath.uncollateralizedExposurePV();

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

		double[] adblUncollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
			adblUncollateralizedPositiveExposure[j] = 0.;

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupUncollateralizedPositiveExposure =
				creditDebtGroupPath.uncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
				adblUncollateralizedPositiveExposure[vertexIndex] +=
					creditDebtGroupUncollateralizedPositiveExposure[vertexIndex];
		}

		return adblUncollateralizedPositiveExposure;
	}

	@Override public double[] uncollateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] uncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedPositiveExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupUncollateralizedPositiveExposurePV =
				creditDebtGroupPath.uncollateralizedPositiveExposurePV();

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

		double[] uncollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupUncollateralizedNegativeExposure =
				creditDebtGroupPath.uncollateralizedNegativeExposure();

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

		double[] uncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			uncollateralizedNegativeExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupUncollateralizedNegativeExposurePV =
				creditDebtGroupPath.uncollateralizedNegativeExposurePV();

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

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupExposure = creditDebtGroupPath.creditExposure();

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

		for (int j = 0; j < vertexCount; ++j)
		{
			creditExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupExposurePV = creditDebtGroupPath.creditExposurePV();

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

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposure[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupExposure = creditDebtGroupPath.debtExposure();

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

		for (int j = 0; j < vertexCount; ++j)
		{
			debtExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			double[] creditDebtGroupExposurePV = creditDebtGroupPath.debtExposurePV();

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

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			bilateralCollateralAdjustment += creditDebtGroupPath.bilateralCollateralAdjustment();
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

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			unilateralCreditAdjustment += creditDebtGroupPath.unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
		throws java.lang.Exception
	{
		double bilateralCreditAdjustment = 0.;

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			bilateralCreditAdjustment += creditDebtGroupPath.bilateralCreditAdjustment();
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

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			contraLiabilityCreditAdjustment += creditDebtGroupPath.contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
		throws java.lang.Exception
	{
		double unilateralDebtAdjustment = 0.;

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			unilateralDebtAdjustment += creditDebtGroupPath.unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
		throws java.lang.Exception
	{
		double bilateralDebtAdjustment = 0.;

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			bilateralDebtAdjustment += creditDebtGroupPath.bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
		throws java.lang.Exception
	{
		double debtAdjustment = 0.;

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			debtAdjustment += creditDebtGroupPath.debtAdjustment();
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

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : _creditDebtGroupPathList)
		{
			totalAdjustment += creditDebtGroupPath.creditAdjustment() + creditDebtGroupPath.debtAdjustment();
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			totalAdjustment += _fundingGroupPathArray[fundingGroupIndex].fundingValueAdjustment();
		}

		return totalAdjustment;
	}
}
