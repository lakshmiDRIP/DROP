
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
 * GroupPathExposureAdjustment cumulates the Exposures and the Adjustments across Multiple Netting/Funding
 *  Groups on a Single Path Projection Run across multiple Counter Party Groups the constitute a Book. The
 *  References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies, Risk, 23 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GroupPathExposureAdjustment implements org.drip.xva.gross.PathExposureAdjustment
{
	private org.drip.xva.gross.MonoPathExposureAdjustment[] _monoPathExposureAdjustmentArray = null;

	/**
	 * GroupPathExposureAdjustment Constructor
	 * 
	 * @param monoPathExposureAdjustmentArray Array of Single Counter Party Path Exposure Adjustments
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GroupPathExposureAdjustment (
		final org.drip.xva.gross.MonoPathExposureAdjustment[] monoPathExposureAdjustmentArray)
		throws java.lang.Exception
	{
		if (null == (_monoPathExposureAdjustmentArray = monoPathExposureAdjustmentArray) ||
			0 == _monoPathExposureAdjustmentArray.length)
		{
			throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		if (0 == adiabatGroupCount)
		{
			throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			if (null == _monoPathExposureAdjustmentArray[adiabatGroupIndex])
			{
				throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Counter Party Group Paths
	 * 
	 * @return Array of Counter Party Group Paths
	 */

	public org.drip.xva.gross.MonoPathExposureAdjustment[] adiabatGroupPaths()
	{
		return _monoPathExposureAdjustmentArray;
	}

	@Override public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		return _monoPathExposureAdjustmentArray[0].anchorDates();
	}

	@Override public double[] collateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] counterPartyGroupCollateralizedExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].collateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposure[vertexIndex] += counterPartyGroupCollateralizedExposure[vertexIndex];
			}
		}

		return collateralizedExposure;
	}

	@Override public double[] collateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int j = 0; j < vertexCount; ++j)
		{
			collateralizedExposurePV[j] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] counterPartyGroupCollateralizedExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].collateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedExposurePV[vertexIndex] +=
					counterPartyGroupCollateralizedExposurePV[vertexIndex];
			}
		}

		return collateralizedExposurePV;
	}

	@Override public double[] uncollateralizedExposure()
	{
		int vertexCount = anchorDates().length;

		double[] uncollateralizedExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].uncollateralizedExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposure[vertexIndex] += pathUncollateralizedExposure[vertexIndex];
			}
		}

		return uncollateralizedExposure;
	}

	@Override public double[] uncollateralizedExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] uncollateralizedExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].uncollateralizedExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedExposurePV[vertexIndex] += pathUncollateralizedExposurePV[vertexIndex];
			}
		}

		return uncollateralizedExposurePV;
	}

	@Override public double[] collateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedPositiveExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedPositiveExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedPositiveExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].collateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedPositiveExposure[vertexIndex] +=
					pathCollateralizedPositiveExposure[vertexIndex];
			}
		}

		return collateralizedPositiveExposure;
	}

	@Override public double[] collateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedPositiveExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedPositiveExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedPositiveExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].collateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedPositiveExposurePV[vertexIndex] +=
					pathCollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return collateralizedPositiveExposurePV;
	}

	@Override public double[] uncollateralizedPositiveExposure()
	{
		int vertexCount = anchorDates().length;

		double[] uncollateralizedPositiveExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedPositiveExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedPositiveExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].uncollateralizedPositiveExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedPositiveExposure[vertexIndex] +=
					pathUncollateralizedPositiveExposure[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposure;
	}

	@Override public double[] uncollateralizedPositiveExposurePV()
	{
		int vertexCount = anchorDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedPositiveExposurePV = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedPositiveExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedPositiveExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].uncollateralizedPositiveExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedPositiveExposurePV[vertexIndex] +=
					pathUncollateralizedPositiveExposurePV[vertexIndex];
			}
		}

		return uncollateralizedPositiveExposurePV;
	}

	@Override public double[] collateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedNegativeExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedNegativeExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedNegativeExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].collateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedNegativeExposure[vertexIndex] +=
					pathCollateralizedNegativeExposure[vertexIndex];
			}
		}

		return collateralizedNegativeExposure;
	}

	@Override public double[] collateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] collateralizedNegativeExposurePV = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			collateralizedNegativeExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCollateralizedNegativeExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].collateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				collateralizedNegativeExposurePV[vertexIndex] +=
					pathCollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return collateralizedNegativeExposurePV;
	}

	@Override public double[] uncollateralizedNegativeExposure()
	{
		int vertexCount = anchorDates().length;

		double[] uncollateralizedNegativeExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedNegativeExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] adblPathUncollateralizedNegativeExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].uncollateralizedNegativeExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedNegativeExposure[vertexIndex] +=
					adblPathUncollateralizedNegativeExposure[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposure;
	}

	@Override public double[] uncollateralizedNegativeExposurePV()
	{
		int vertexCount = anchorDates().length;

		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;
		double[] uncollateralizedNegativeExposurePV = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			uncollateralizedNegativeExposurePV[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathUncollateralizedNegativeExposurePV =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].uncollateralizedNegativeExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				uncollateralizedNegativeExposurePV[vertexIndex] +=
					pathUncollateralizedNegativeExposurePV[vertexIndex];
			}
		}

		return uncollateralizedNegativeExposurePV;
	}

	@Override public double[] creditExposure()
	{
		int vertexCount = anchorDates().length;

		double[] adblCreditExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			adblCreditExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCreditExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].creditExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				adblCreditExposure[vertexIndex] += pathCreditExposure[vertexIndex];
			}
		}

		return adblCreditExposure;
	}

	@Override public double[] creditExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] adblCreditExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			adblCreditExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathCreditExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].creditExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				adblCreditExposure[vertexIndex] += pathCreditExposure[vertexIndex];
			}
		}

		return adblCreditExposure;
	}

	@Override public double[] debtExposure()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			debtExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathDebtExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].debtExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposure[vertexIndex] += pathDebtExposure[vertexIndex];
			}
		}

		return debtExposure;
	}

	@Override public double[] debtExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] debtExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			debtExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathDebtExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].debtExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				debtExposure[vertexIndex] += pathDebtExposure[vertexIndex];
			}
		}

		return debtExposure;
	}

	@Override public double[] fundingExposure()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			fundingExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathFundingExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingExposure();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += pathFundingExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	@Override public double[] fundingExposurePV()
	{
		int vertexCount = anchorDates().length;

		double[] fundingExposure = new double[vertexCount];
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			fundingExposure[vertexIndex] = 0.;
		}

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			double[] pathFundingExposure =
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingExposurePV();

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				fundingExposure[vertexIndex] += pathFundingExposure[vertexIndex];
			}
		}

		return fundingExposure;
	}

	@Override public double bilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		double bilateralCollateralAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralCollateralAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralCollateralAdjustment();
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
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			unilateralCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralCreditAdjustment();
		}

		return unilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
		throws java.lang.Exception
	{
		double bilateralCreditAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralCreditAdjustment();
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
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			contraLiabilityCreditAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].contraLiabilityCreditAdjustment();
		}

		return contraLiabilityCreditAdjustment;
	}

	@Override public double unilateralDebtAdjustment()
		throws java.lang.Exception
	{
		double unilateralDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			unilateralDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].unilateralDebtAdjustment();
		}

		return unilateralDebtAdjustment;
	}

	@Override public double bilateralDebtAdjustment()
		throws java.lang.Exception
	{
		double bilateralDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			bilateralDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].bilateralDebtAdjustment();
		}

		return bilateralDebtAdjustment;
	}

	@Override public double debtAdjustment()
		throws java.lang.Exception
	{
		double debtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			debtAdjustment += _monoPathExposureAdjustmentArray[adiabatGroupIndex].debtAdjustment();
		}

		return debtAdjustment;
	}

	@Override public double fundingValueAdjustment()
		throws java.lang.Exception
	{
		double fundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingValueAdjustment();
		}

		return fundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
		throws java.lang.Exception
	{
		double fundingDebtAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingDebtAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingDebtAdjustment();
		}

		return fundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		double fundingCostAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingCostAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingCostAdjustment();
		}

		return fundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		double fundingBenefitAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			fundingBenefitAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingBenefitAdjustment();
		}

		return fundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		double symmetricFundingValueAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			symmetricFundingValueAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].symmetricFundingValueAdjustment();
		}

		return symmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
		throws java.lang.Exception
	{
		double totalAdjustment = 0.;
		int adiabatGroupCount = _monoPathExposureAdjustmentArray.length;

		for (int adiabatGroupIndex = 0; adiabatGroupIndex < adiabatGroupCount; ++adiabatGroupIndex)
		{
			totalAdjustment +=
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].creditAdjustment() +
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].debtAdjustment() +
				_monoPathExposureAdjustmentArray[adiabatGroupIndex].fundingValueAdjustment();
		}

		return totalAdjustment;
	}
}
