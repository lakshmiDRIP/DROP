
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
 * FundingGroupPath holds up the Strategy Abstract Realizations of the Sequence in a Single Path Projection
 *  Run over Multiple Collateral Groups onto a Single Funding Group - the Purpose being to calculate Funding
 *  Valuation Adjustments. The References are:
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

public abstract class FundingGroupPath extends org.drip.xva.netting.ExposureGroupPath
{

	protected FundingGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupPath[] collateralGroupPathArray,
		final org.drip.xva.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		super (
			collateralGroupPathArray,
			marketPath
		);
	}

	/**
	 * Compute Path Unilateral Funding Value Adjustment
	 * 
	 * @return The Path Unilateral Funding Value Adjustment
	 */

	public double unilateralFundingValueAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		double unilateralFundingValueAdjustment = 0.;
		int vertexCount = fundingExposurePV.length;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = fundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].bank().seniorFundingSpread();

			double periodIntegrandEnd = fundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].bank().seniorFundingSpread();

			unilateralFundingValueAdjustment -=
				0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return unilateralFundingValueAdjustment;
	}

	/**
	 * Compute Path Bilateral Funding Value Adjustment
	 * 
	 * @return The Path Bilateral Funding Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bilateralFundingValueAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		double bilateralFundingValueAdjustment = 0.;
		int vertexCount = fundingExposurePV.length;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = fundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].bank().seniorFundingSpread() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability();

			double periodIntegrandEnd = fundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].bank().seniorFundingSpread() *
				marketVertexArray[vertexIndex].counterParty().survivalProbability();

			bilateralFundingValueAdjustment -=
				0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return bilateralFundingValueAdjustment;
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

		double unilateralFundingDebtAdjustment = 0.;
		int vertexCount = debtExposurePV.length;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().seniorFundingSpread();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().seniorFundingSpread();

			unilateralFundingDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return unilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Funding Debt Adjustment
	 * 
	 * @return The Path Bilateral Funding Debt Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bilateralFundingDebtAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposurePV = debtExposurePV();

		double bilateralFundingDebtAdjustment = 0.;
		int vertexCount = debtExposurePV.length;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().seniorFundingSpread();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().seniorFundingSpread();

			bilateralFundingDebtAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return bilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Symmetric Funding Value Adjustment
	 * 
	 * @return The Path Symmetric Funding Value Adjustment
	 */

	public double symmetricFundingValueAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] collateralizedExposurePV = collateralizedExposurePV();

		int vertexCount = collateralizedExposurePV.length;
		double symmetricFundingValueAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralizedExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].bank().seniorFundingSpread();

			double periodIntegrandEnd = collateralizedExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].bank().seniorFundingSpread();

			symmetricFundingValueAdjustment -= 0.5 * (periodIntegrandStart + periodIntegrandEnd) *
				(marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return symmetricFundingValueAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Path Funding Value Adjustment
	 * 
	 * @return The Period-wise Unilateral Path Funding Value Adjustment
	 */

	public double[] periodUnilateralFundingValueAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] fundingExposurePV = fundingExposurePV();

		int vertexCount = fundingExposurePV.length;
		double[] periodUnilateralFundingValueAdjustment = new double[vertexCount - 1];

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = fundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() * (1. -
				marketVertexArray[vertexIndex - 1].bank().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].bank().hazardRate();

			double periodIntegrandEnd = fundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				(1. - marketVertexArray[vertexIndex].bank().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].bank().hazardRate();

			periodUnilateralFundingValueAdjustment[vertexIndex - 1] = 0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodUnilateralFundingValueAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Path Funding Value Adjustment
	 * 
	 * @return The Period-wise Bilateral Path Funding Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodBilateralFundingValueAdjustment()
		throws java.lang.Exception
	{
		double[] fundingExposurePV = fundingExposurePV();

		int vertexCount = fundingExposurePV.length;
		double[] periodBilateralFundingValueAdjustment = new double[vertexCount - 1];

		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = fundingExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() * (1. -
				marketVertexArray[vertexIndex - 1].bank().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex - 1].bank().hazardRate() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability();

			double periodIntegrandEnd = fundingExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				(1. - marketVertexArray[vertexIndex].bank().seniorRecoveryRate()) *
				marketVertexArray[vertexIndex].bank().hazardRate() *
				marketVertexArray[vertexIndex].counterParty().survivalProbability();

			periodBilateralFundingValueAdjustment[vertexIndex - 1] = 0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodBilateralFundingValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Unilateral Funding Debt Adjustment
	 * 
	 * @return The Period-wise Path Unilateral Funding Debt Adjustment
	 */

	public double[] periodUnilateralFundingDebtAdjustment()
	{
		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double[] periodUnilateralFundingDebtAdjustment = new double[vertexCount - 1];

		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().seniorFundingSpread();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().seniorFundingSpread();

			periodUnilateralFundingDebtAdjustment[vertexIndex - 1] = -0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodUnilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Period-wise Path Bilateral Funding Debt Adjustment
	 * 
	 * @return The Period-wise Path Bilateral Funding Debt Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodBilateralFundingDebtAdjustment()
		throws java.lang.Exception
	{
		double[] debtExposurePV = debtExposurePV();

		int vertexCount = debtExposurePV.length;
		double[] periodBilateralFundingDebtAdjustment = new double[vertexCount - 1];

		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = debtExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex - 1].counterParty().seniorFundingSpread();

			double periodIntegrandEnd = debtExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().survivalProbability() *
				marketVertexArray[vertexIndex].counterParty().seniorFundingSpread();

			periodBilateralFundingDebtAdjustment[vertexIndex - 1] = 0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodBilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Period-wise Path Symmetric Funding Value Adjustment
	 * 
	 * @return The Period-wise Path Symmetric Funding Value Adjustment
	 */

	public double[] periodSymmetricFundingValueAdjustment()
	{
		double[] collateralizedExposurePV = collateralizedExposurePV();

		int vertexCount = collateralizedExposurePV.length;
		double[] periodSymmetricFundingValueAdjustment = new double[vertexCount - 1];

		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			double periodIntegrandStart = collateralizedExposurePV[vertexIndex - 1] *
				marketVertexArray[vertexIndex - 1].bank().survivalProbability() *
				marketVertexArray[vertexIndex - 1].bank().seniorFundingSpread();

			double periodIntegrandEnd = collateralizedExposurePV[vertexIndex] *
				marketVertexArray[vertexIndex].bank().survivalProbability() *
				marketVertexArray[vertexIndex].bank().seniorFundingSpread();

			periodSymmetricFundingValueAdjustment[vertexIndex - 1] = 0.5 * (periodIntegrandStart +
				periodIntegrandEnd) * (marketVertexArray[vertexIndex].anchorDate().julian() -
				marketVertexArray[vertexIndex - 1].anchorDate().julian()) / 365.25;
		}

		return periodSymmetricFundingValueAdjustment;
	}

	/**
	 * Compute Path Funding Value Adjustment
	 * 
	 * @return The Path Funding Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double fundingValueAdjustment()
		throws java.lang.Exception;

	/**
	 * Compute Path Funding Debt Adjustment
	 * 
	 * @return The Path Funding Debt Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double fundingDebtAdjustment()
		throws java.lang.Exception;

	/**
	 * Compute Path Funding Cost Adjustment
	 * 
	 * @return The Path Funding Cost Adjustment
	 */

	public abstract double fundingCostAdjustment();

	/**
	 * Compute Path Funding Benefit Adjustment
	 * 
	 * @return The Path Funding Benefit Adjustment
	 */

	public abstract double fundingBenefitAdjustment();

	/**
	 * Compute Period-wise Path Funding Value Adjustment
	 * 
	 * @return The Period-wise Path Funding Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double[] periodFundingValueAdjustment()
		throws java.lang.Exception;

	/**
	 * Compute Period-wise Path Funding Debt Adjustment
	 * 
	 * @return The Period-wise Path Funding Debt Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double[] periodFundingDebtAdjustment()
		throws java.lang.Exception;

	/**
	 * Compute Period-wise Path Funding Cost Adjustment
	 * 
	 * @return The Period-wise Path Funding Cost Adjustment
	 */

	public abstract double[] periodFundingCostAdjustment();

	/**
	 * Compute Period-wise Path Funding Benefit Adjustment
	 * 
	 * @return The Period-wise Path Funding Benefit Adjustment
	 */

	public abstract double[] periodFundingBenefitAdjustment();
}
