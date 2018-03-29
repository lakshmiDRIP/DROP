
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
 * CreditDebtGroupPath rolls up the Path Realizations of the Sequence in a Single Path Projection Run over
 *  Multiple Collateral Hypothecation Groups onto a Single Credit/Debt Netting Group - the Purpose being to
 *  calculate Credit Valuation Adjustments. The References are:
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

public abstract class CreditDebtGroupPath extends org.drip.xva.netting.CollateralGroupPath
{

	protected CreditDebtGroupPath (
		final org.drip.xva.netting.PositionGroupPath[] positionGroupPathArray,
		final org.drip.xva.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		super (
			positionGroupPathArray,
			marketPath
		);
	}

	/**
	 * Compute Path Unilateral Credit Adjustment
	 * 
	 * @return The Path Unilateral Credit Adjustment
	 */

	public double unilateralCreditAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposurePV = creditExposurePV();

		int vertexCount = creditExposurePV.length;
		double unilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = creditExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate());

			double periodIntegrandEnd = creditExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].client().seniorRecoveryRate());

			unilateralCreditAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return unilateralCreditAdjustment;
	}

	/**
	 * Compute Path Bilateral Credit Adjustment
	 * 
	 * @return The Path Bilateral Credit Adjustment
	 */

	public double bilateralCreditAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposurePV = creditExposurePV();

		int vertexCount = creditExposurePV.length;
		double bilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = creditExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = creditExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			bilateralCreditAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return bilateralCreditAdjustment;
	}

	/**
	 * Compute Path Contra-Liability Credit Adjustment
	 * 
	 * @return The Path Contra-Liability Credit Adjustment
	 */

	public double contraLiabilityCreditAdjustment()
	{
		return bilateralCreditAdjustment() - unilateralCreditAdjustment();
	}

	/**
	 * Compute Path Unilateral Debt Adjustment
	 * 
	 * @return The Path Unilateral Debt Adjustment
	 */

	public double unilateralDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double unilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			unilateralDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Debt Adjustment
	 * 
	 * @return The Path Bilateral Debt Adjustment
	 */

	public double bilateralDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double bilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			bilateralDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return bilateralDebtAdjustment;
	}

	/**
	 * Compute Path Symmetric Funding Value Spread 01
	 * 
	 * @return The Path Symmetric Funding Value Spread 01
	 */

	public double symmetricFundingValueSpread01()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] symmetricFundingExposurePV = collateralizedExposurePV();

		int vertexCount = symmetricFundingExposurePV.length;
		double symmetricFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			symmetricFundingValueSpread01 -= 0.5 * (
				symmetricFundingExposurePV[vertexIndex - 1] +
				symmetricFundingExposurePV[vertexIndex]
			) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return symmetricFundingValueSpread01;
	}

	/**
	 * Compute Path Unilateral Funding Value Spread 01
	 * 
	 * @return The Path Unilateral Funding Value Spread 01
	 */

	public double unilateralFundingValueSpread01()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		int vertexCount = fundingExposurePV.length;
		double unilateralFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = fundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = fundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability();

			unilateralFundingValueSpread01 -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return unilateralFundingValueSpread01;
	}

	/**
	 * Compute Path Bilateral Funding Value Spread 01
	 * 
	 * @return The Path Bilateral Funding Value Spread 01
	 */

	public double bilateralFundingValueSpread01()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		int vertexCount = fundingExposurePV.length;
		double bilateralFundingValueSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = fundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability() *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = fundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability() *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			bilateralFundingValueSpread01 -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
				marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()
			) / 365.25;
		}

		return bilateralFundingValueSpread01;
	}

	/**
	 * Compute Path Unilateral Funding Debt Adjustment
	 * 
	 * @return The Path Unilateral Funding Debt Adjustment
	 */

	public double unilateralFundingDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double unilateralFundingDebtSpread01 = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			unilateralFundingDebtSpread01 -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralFundingDebtSpread01;
	}

	/**
	 * Compute Path Bilateral Funding Debt Adjustment
	 * 
	 * @return The Path Bilateral Funding Debt Adjustment
	 */

	public double bilateralFundingDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double bilateralFundingDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().survivalProbability() * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = debtExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().survivalProbability() * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			bilateralFundingDebtAdjustment += 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return bilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Path Bilateral Collateral Value Adjustment
	 */

	public double bilateralCollateralAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] periodCollateralValueAdjustment = periodCollateralValueAdjustment();

		double bilateralCollateralValueAdjustment = 0.;
		int periodCount = periodCollateralValueAdjustment.length;

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			bilateralCollateralValueAdjustment += 0.5 * periodCollateralValueAdjustment[periodIndex] * (
				marketVertexArray[periodIndex].dealer().survivalProbability() *
				marketVertexArray[periodIndex].client().survivalProbability() +
				marketVertexArray[periodIndex + 1].dealer().survivalProbability() *
				marketVertexArray[periodIndex + 1].client().survivalProbability()
			);
		}

		return bilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Collateral Value Adjustment
	 * 
	 * @return The Path Collateral Value Adjustment
	 */

	public double collateralValueAdjustment()
	{
		return bilateralCollateralAdjustment();
	}

	/**
	 * Compute Period-wise Symmetric Funding Value Spread 01
	 * 
	 * @return The Period-wise Symmetric Funding Value Spread 01
	 */

	public double[] periodSymmetricFundingValueSpread01()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] symmetricFundingExposurePV = collateralizedExposurePV();

		int periodCount = symmetricFundingExposurePV.length - 1;
		double[] periodSymmetricFundingValueSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			periodSymmetricFundingValueSpread01[periodIndex] = -0.5 * (
				symmetricFundingExposurePV[periodIndex] +
				symmetricFundingExposurePV[periodIndex + 1]
			) * (
				marketVertexArray[periodIndex + 1].anchorDate().julian() -
				marketVertexArray[periodIndex].anchorDate().julian()
			) / 365.25;
		}

		return periodSymmetricFundingValueSpread01;
	}

	/**
	 * Compute Period-wise Unilateral Credit Adjustment
	 * 
	 * @return The Period-wise Unilateral Credit Adjustment
	 */

	public double[] periodUnilateralCreditAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposurePV = creditExposurePV();

		int vertexCount = creditExposurePV.length;
		double[] periodUnilateralCreditAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = creditExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate();

			double periodIntegrandEnd = creditExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().seniorRecoveryRate();

			periodUnilateralCreditAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
				marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return periodUnilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Credit Adjustment
	 * 
	 * @return The Period-wise Bilateral Credit Adjustment
	 */

	public double[] periodBilateralCreditAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposurePV = creditExposurePV();

		int vertexCount = creditExposurePV.length;
		double[] periodBilateralCreditAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = creditExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate() *
				marketVertexArray[vertexIndex - 1].dealer().survivalProbability();

			double periodIntegrandEnd = creditExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].client().seniorRecoveryRate() *
				marketVertexArray[vertexIndex].dealer().survivalProbability();

			periodBilateralCreditAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
				marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return periodBilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Contra-Liability Credit Adjustment
	 * 
	 * @return The Period-wise Contra-Liability Credit Adjustment
	 */

	public double[] periodContraLiabilityCreditAdjustment()
	{
		double[] periodUnilateralCreditAdjustment = periodUnilateralCreditAdjustment();

		double[] periodBilateralCreditAdjustment = periodBilateralCreditAdjustment();

		int vertexCount = periodUnilateralCreditAdjustment.length;
		double[] periodContraLiabilityCreditAdjustment = new double[vertexCount];

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodContraLiabilityCreditAdjustment[vertexIndex] =
				periodUnilateralCreditAdjustment[vertexIndex] -
				periodBilateralCreditAdjustment[vertexIndex];
		}

		return periodContraLiabilityCreditAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Debt Adjustment
	 * 
	 * @return The Period-wise Unilateral Debt Adjustment
	 */

	public double[] periodUnilateralDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double[] periodUnilateralDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			periodUnilateralDebtAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodUnilateralDebtAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Debt Adjustment
	 * 
	 * @return The Period-wise Bilateral Debt Adjustment
	 */

	public double[] periodBilateralDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double[] periodBilateralDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			periodBilateralDebtAdjustment[vertexIndex - 1] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodBilateralDebtAdjustment;
	}

	/**
	 * Compute Period Unilateral Funding Value Spread 01
	 * 
	 * @return The Period Unilateral Funding Value Spread 01
	 */

	public double[] periodUnilateralFundingValueSpread01()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		int periodCount = fundingExposurePV.length - 1;
		double[] periodUnilateralFundingValueSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			double periodIntegrandStart = fundingExposurePV[periodIndex] *
				marketVertexArray[periodIndex].client().survivalProbability();

			double periodIntegrandEnd = fundingExposurePV[periodIndex + 1] *
				marketVertexArray[periodIndex + 1].client().survivalProbability();

			periodUnilateralFundingValueSpread01[periodIndex] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[periodIndex + 1].anchorDate().julian() -
					marketVertexArray[periodIndex].anchorDate().julian()
				) / 365.25;
		}

		return periodUnilateralFundingValueSpread01;
	}

	/**
	 * Compute Period Bilateral Funding Value Spread 01
	 * 
	 * @return The Period Bilateral Funding Value Spread 01
	 */

	public double[] periodBilateralFundingValueSpread01()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		int periodCount = fundingExposurePV.length - 1;
		double[] periodBilateralFundingValueSpread01 = new double[periodCount];

		for (int periodIndex = 0; periodIndex < periodCount; ++periodIndex)
		{
			double periodIntegrandStart = fundingExposurePV[periodIndex] *
				marketVertexArray[periodIndex].client().survivalProbability() *
				marketVertexArray[periodIndex].dealer().survivalProbability();

			double periodIntegrandEnd = fundingExposurePV[periodIndex + 1] *
				marketVertexArray[periodIndex + 1].client().survivalProbability() *
				marketVertexArray[periodIndex + 1].dealer().survivalProbability();

			periodBilateralFundingValueSpread01[periodIndex] =
				-0.5 * (periodIntegrandStart + periodIntegrandEnd) * (
					marketVertexArray[periodIndex + 1].anchorDate().julian() -
					marketVertexArray[periodIndex].anchorDate().julian()
				) / 365.25;
		}

		return periodBilateralFundingValueSpread01;
	}

	/**
	 * Compute Period Unilateral Funding Debt Adjustment
	 * 
	 * @return The Period Unilateral Funding Debt Adjustment
	 */

	public double[] periodUnilateralFundingDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double[] periodUnilateralFundingDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate());

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate());

			periodUnilateralFundingDebtAdjustment[vertexIndex - 1] = -0.5 *
				(periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodUnilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Period Bilateral Funding Debt Adjustment
	 * 
	 * @return The Period Bilateral Funding Debt Adjustment
	 */

	public double[] periodBilateralFundingDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double[] periodBilateralFundingDebtAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] * (1. -
				marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].client().survivalProbability();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] * (1. -
				marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].client().survivalProbability();

			periodBilateralFundingDebtAdjustment[vertexIndex - 1] = 0.5 *
				(periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
				marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodBilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Credit Adjustment
	 * 
	 * @return The Path Credit Adjustment
	 */

	public abstract double creditAdjustment();

	/**
	 * Compute Path Debt Adjustment
	 * 
	 * @return The Path Debt Adjustment
	 */

	public abstract double debtAdjustment();

	/**
	 * Compute Period-wise Credit Adjustment
	 * 
	 * @return The Period-wise Credit Adjustment
	 */

	public abstract double[] periodCreditAdjustment();

	/**
	 * Compute Period-wise Debt Adjustment
	 * 
	 * @return The Period-wise Debt Adjustment
	 */

	public abstract double[] periodDebtAdjustment();
}
