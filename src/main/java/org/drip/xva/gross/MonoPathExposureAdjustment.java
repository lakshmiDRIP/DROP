
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

		if (0 == fundingGroupCount)
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

	public org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupTrajectoryPaths()
	{
		int creditDebtGroupPathIndex = 0;
		int fundingGroupCount = _fundingGroupPathArray.length;

		java.util.List<org.drip.xva.netting.CreditDebtGroupPath> creditDebtGroupPathList = new
			java.util.ArrayList<org.drip.xva.netting.CreditDebtGroupPath>();

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray =
				_fundingGroupPathArray[fundingGroupIndex].creditDebtGroupPathArray();

			for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : creditDebtGroupPathArray)
			{
				creditDebtGroupPathList.add (creditDebtGroupPath);
			}
		}

		org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray = new
			org.drip.xva.netting.CreditDebtGroupPath[creditDebtGroupPathList.size()];

		for (org.drip.xva.netting.CreditDebtGroupPath creditDebtGroupPath : creditDebtGroupPathList)
		{
			creditDebtGroupPathArray[creditDebtGroupPathIndex++] = creditDebtGroupPath;
		}

		return creditDebtGroupPathArray;
	}

	@Override public org.drip.analytics.date.JulianDate[] vertexDates()
	{
		return _fundingGroupPathArray[0].vertexDates();
	}

	@Override public double[] vertexCollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedExposure =
				fundingGroupPath.vertexCollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedExposure[vertexIndex] +=
					fundingGroupVertexCollateralizedExposure[vertexIndex];
			}
		}

		return vertexCollateralizedExposure;
	}

	@Override public double[] vertexCollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedExposurePV =
				fundingGroupPath.vertexCollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedExposurePV[vertexIndex] +=
					fundingGroupVertexCollateralizedExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedExposurePV;
	}

	@Override public double[] vertexCollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedPositiveExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedPositiveExposure =
				fundingGroupPath.vertexCollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedPositiveExposure[vertexIndex] +=
					fundingGroupVertexCollateralizedPositiveExposure[vertexIndex];
			}
		}

		return vertexCollateralizedPositiveExposure;
	}

	@Override public double[] vertexCollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedPositiveExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedPositiveExposurePV =
				fundingGroupPath.vertexCollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedPositiveExposurePV[vertexIndex] +=
					fundingGroupVertexCollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedPositiveExposurePV;
	}

	@Override public double[] vertexCollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedNegativeExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedNegativeExposure =
				fundingGroupPath.vertexCollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedNegativeExposure[vertexIndex] +=
					fundingGroupVertexCollateralizedNegativeExposure[vertexIndex];
			}
		}

		return vertexCollateralizedNegativeExposure;
	}

	@Override public double[] vertexCollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexCollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexCollateralizedNegativeExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexCollateralizedNegativeExposurePV =
				fundingGroupPath.vertexCollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexCollateralizedNegativeExposurePV[vertexIndex] +=
					fundingGroupVertexCollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return vertexCollateralizedNegativeExposurePV;
	}

	@Override public double[] vertexUncollateralizedExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedExposure =
				fundingGroupPath.vertexUncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedExposure[vertexIndex] +=
					fundingGroupVertexUncollateralizedExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedExposure;
	}

	@Override public double[] vertexUncollateralizedExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedExposurePV =
				fundingGroupPath.vertexUncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedExposurePV[vertexIndex] +=
					fundingGroupVertexUncollateralizedExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedExposurePV;
	}

	@Override public double[] vertexUncollateralizedPositiveExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedPositiveExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedPositiveExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedPositiveExposure =
				fundingGroupPath.vertexUncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedPositiveExposure[vertexIndex] +=
					fundingGroupVertexUncollateralizedPositiveExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedPositiveExposure;
	}

	@Override public double[] vertexUncollateralizedPositiveExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedPositiveExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedPositiveExposurePV =
				fundingGroupPath.vertexUncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedPositiveExposurePV[vertexIndex] +=
					fundingGroupVertexUncollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedPositiveExposurePV;
	}

	@Override public double[] vertexUncollateralizedNegativeExposure()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedNegativeExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedNegativeExposure[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedNegativeExposure =
				fundingGroupPath.vertexUncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedNegativeExposure[vertexIndex] +=
					fundingGroupVertexUncollateralizedNegativeExposure[vertexIndex];
			}
		}

		return vertexUncollateralizedNegativeExposure;
	}

	@Override public double[] vertexUncollateralizedNegativeExposurePV()
	{
		int vertexCount = vertexDates().length;

		double[] vertexUncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexUncollateralizedNegativeExposurePV[j] = 0.;
		}

		for (org.drip.xva.netting.FundingGroupPath fundingGroupPath : _fundingGroupPathArray)
		{
			double[] fundingGroupVertexUncollateralizedNegativeExposurePV =
				fundingGroupPath.vertexUncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexUncollateralizedNegativeExposurePV[vertexIndex] +=
					fundingGroupVertexUncollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return vertexUncollateralizedNegativeExposurePV;
	}

	@Override public double[] vertexFundingExposure()
	{
		int vertexCount = vertexDates().length;

		int fundingGroupCount = _fundingGroupPathArray.length;
		double[] vertexFundingExposure = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexFundingExposure[j] = 0.;
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			double[] fundingGroupVertexExposure =
				_fundingGroupPathArray[fundingGroupIndex].vertexFundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexFundingExposure[vertexIndex] += fundingGroupVertexExposure[vertexIndex];
			}
		}

		return vertexFundingExposure;
	}

	@Override public double[] vertexFundingExposurePV()
	{
		int vertexCount = vertexDates().length;

		int fundingGroupCount = _fundingGroupPathArray.length;
		double[] vertexFundingExposurePV = new double[vertexCount];

		for (int j = 0; j < vertexCount; ++j)
		{
			vertexFundingExposurePV[j] = 0.;
		}

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			double[] fundingGroupVertexExposurePV =
				_fundingGroupPathArray[fundingGroupIndex].vertexFundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				vertexFundingExposurePV[vertexIndex] += fundingGroupVertexExposurePV[vertexIndex];
			}
		}

		return vertexFundingExposurePV;
	}

	@Override public double unilateralCreditAdjustment()
	{
		double unilateralCreditAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralCreditAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
	{
		double bilateralCreditAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralCreditAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].bilateralCreditAdjustment();
		}

		return bilateralCreditAdjustment;
	}

	@Override public double creditAdjustment()
	{
		return unilateralCreditAdjustment();
	}

	@Override public double contraLiabilityCreditAdjustment()
	{
		double contraLiabilityCreditAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			contraLiabilityCreditAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
	{
		double unilateralDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralDebtAdjustment += _fundingGroupPathArray[fundingGroupIndex].unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
	{
		double bilateralDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralDebtAdjustment += _fundingGroupPathArray[fundingGroupIndex].bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
	{
		return unilateralDebtAdjustment();
	}

	@Override public double contraAssetDebtAdjustment()
	{
		double contraAssetDebtAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			contraAssetDebtAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].contraAssetDebtAdjustment();
		}

		return contraAssetDebtAdjustment;
	}

	@Override public double unilateralCollateralAdjustment()
	{
		double unilateralCollateralAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralCollateralAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].unilateralCollateralAdjustment();
		}

		return unilateralCollateralAdjustment;
	}

	@Override public double bilateralCollateralAdjustment()
	{
		double bilateralCollateralAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralCollateralAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].bilateralCollateralAdjustment();
		}

		return bilateralCollateralAdjustment;
	}

	@Override public double collateralAdjustment()
	{
		return bilateralCollateralAdjustment();
	}

	@Override public double unilateralFundingValueAdjustment()
	{
		double unilateralFundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			unilateralFundingValueAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].unilateralFundingValueAdjustment();
		}

		return unilateralFundingValueAdjustment;
	}

	@Override public double bilateralFundingValueAdjustment()
	{
		double bilateralFundingValueAdjustment = 0.;
		int fundingGroupCount = _fundingGroupPathArray.length;

		for (int fundingGroupIndex = 0; fundingGroupIndex < fundingGroupCount; ++fundingGroupIndex)
		{
			bilateralFundingValueAdjustment +=
				_fundingGroupPathArray[fundingGroupIndex].bilateralFundingValueAdjustment();
		}

		return bilateralFundingValueAdjustment;
	}

	@Override public double fundingValueAdjustment()
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
	{
		return creditAdjustment() + debtAdjustment() + fundingValueAdjustment();
	}
}
