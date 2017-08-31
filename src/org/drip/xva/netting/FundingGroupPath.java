
package org.drip.xva.netting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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

public abstract class FundingGroupPath extends org.drip.xva.netting.ExposureGroupPath {

	protected FundingGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupPath[] aHGP,
		final org.drip.xva.universe.MarketPath mp)
		throws java.lang.Exception
	{
		super (aHGP, mp);
	}

	/**
	 * Compute Path Unilateral Funding Value Adjustment
	 * 
	 * @return The Path Unilateral Funding Value Adjustment
	 */

	public double unilateralFundingValueAdjustment()
	{
		double[] adblFundingExposurePV = fundingExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblFundingExposurePV.length;
		double dblUnilateralFundingCostAdjustment = 0.;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblFundingExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].bank().seniorFundingSpread();

			double dblPeriodIntegrandEnd = adblFundingExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].bank().seniorFundingSpread();

			dblUnilateralFundingCostAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblUnilateralFundingCostAdjustment;
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
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblFundingExposurePV = fundingExposurePV();

		double dblBilateralFundingValueAdjustment = 0.;
		int iNumVertex = adblFundingExposurePV.length;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblFundingExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].bank().seniorFundingSpread() *
						aMV[iVertexIndex - 1].counterParty().survivalProbability();

			double dblPeriodIntegrandEnd = adblFundingExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].bank().seniorFundingSpread() *
						aMV[iVertexIndex].counterParty().survivalProbability();

			dblBilateralFundingValueAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblBilateralFundingValueAdjustment;
	}

	/**
	 * Compute Path Unilateral Funding Debt Adjustment
	 * 
	 * @return The Path Unilateral Funding Debt Adjustment
	 */

	public double unilateralFundingDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblDebtExposurePV = debtExposurePV();

		double dblUnilateralFundingDebtAdjustment = 0.;
		int iNumVertex = adblDebtExposurePV.length;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].bank().seniorFundingSpread();

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].bank().seniorFundingSpread();

			dblUnilateralFundingDebtAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblUnilateralFundingDebtAdjustment;
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
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblDebtExposurePV = debtExposurePV();

		double dblBilateralFundingDebtAdjustment = 0.;
		int iNumVertex = adblDebtExposurePV.length;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() * (1. -
					aMV[iVertexIndex - 1].bank().seniorRecoveryRate()) *
						aMV[iVertexIndex - 1].bank().hazardRate() *
							aMV[iVertexIndex - 1].counterParty().survivalProbability();

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() * (1. -
					aMV[iVertexIndex].bank().seniorRecoveryRate()) *
						aMV[iVertexIndex].bank().hazardRate() *
							aMV[iVertexIndex].counterParty().survivalProbability();

			dblBilateralFundingDebtAdjustment += 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblBilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Path Symmetric Funding Value Adjustment
	 * 
	 * @return The Path Symmetric Funding Value Adjustment
	 */

	public double symmetricFundingValueAdjustment()
	{
		double[] adblCollateralizedExposurePV = collateralizedExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblCollateralizedExposurePV.length;
		double dblSymmetricFundingValueAdjustment = 0.;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCollateralizedExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].bank().seniorFundingSpread();

			double dblPeriodIntegrandEnd = adblCollateralizedExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].bank().seniorFundingSpread();

			dblSymmetricFundingValueAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblSymmetricFundingValueAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Path Funding Value Adjustment
	 * 
	 * @return The Period-wise Unilateral Path Funding Value Adjustment
	 */

	public double[] periodUnilateralFundingValueAdjustment()
	{
		double[] adblFundingExposurePV = fundingExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblFundingExposurePV.length;
		double[] adblUnilateralFundingValueAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblFundingExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() * (1. -
					aMV[iVertexIndex - 1].bank().seniorRecoveryRate()) *
						aMV[iVertexIndex - 1].bank().hazardRate();

			double dblPeriodIntegrandEnd = adblFundingExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() * (1. -
					aMV[iVertexIndex].bank().seniorRecoveryRate()) * aMV[iVertexIndex].bank().hazardRate();

			adblUnilateralFundingValueAdjustment[iVertexIndex - 1] = 0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblUnilateralFundingValueAdjustment;
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
		double[] adblFundingExposurePV = fundingExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblFundingExposurePV.length;
		double[] adblBilateralFundingValueAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblFundingExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() * (1. -
					aMV[iVertexIndex - 1].bank().seniorRecoveryRate()) *
						aMV[iVertexIndex - 1].bank().hazardRate() *
							aMV[iVertexIndex - 1].counterParty().survivalProbability();

			double dblPeriodIntegrandEnd = adblFundingExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() * (1. -
					aMV[iVertexIndex].bank().seniorRecoveryRate()) * aMV[iVertexIndex].bank().hazardRate() *
						aMV[iVertexIndex].counterParty().survivalProbability();

			adblBilateralFundingValueAdjustment[iVertexIndex - 1] = 0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblBilateralFundingValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Unilateral Funding Debt Adjustment
	 * 
	 * @return The Period-wise Path Unilateral Funding Debt Adjustment
	 */

	public double[] periodUnilateralFundingDebtAdjustment()
	{
		double[] adblDebtExposurePV = debtExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblDebtExposurePV.length;
		double[] adblUnilateralFundingDebtAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].bank().seniorFundingSpread();

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].bank().seniorFundingSpread();

			adblUnilateralFundingDebtAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblUnilateralFundingDebtAdjustment;
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
		double[] adblDebtExposurePV = debtExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblDebtExposurePV.length;
		double[] adblBilateralFundingDebtAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() * (1. -
					aMV[iVertexIndex - 1].bank().seniorRecoveryRate()) *
						aMV[iVertexIndex - 1].bank().hazardRate() *
							aMV[iVertexIndex - 1].counterParty().survivalProbability();

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() * (1. -
					aMV[iVertexIndex].bank().seniorRecoveryRate()) * aMV[iVertexIndex].bank().hazardRate() *
						aMV[iVertexIndex].counterParty().survivalProbability();

			adblBilateralFundingDebtAdjustment[iVertexIndex - 1] = 0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblBilateralFundingDebtAdjustment;
	}

	/**
	 * Compute Period-wise Path Symmetric Funding Value Adjustment
	 * 
	 * @return The Period-wise Path Symmetric Funding Value Adjustment
	 */

	public double[] periodSymmetricFundingValueAdjustment()
	{
		double[] adblCollateralizedExposurePV = collateralizedExposurePV();

		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		int iNumVertex = adblCollateralizedExposurePV.length;
		double[] adblSymmetricFundingValueAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCollateralizedExposurePV[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].bank().seniorFundingSpread();

			double dblPeriodIntegrandEnd = adblCollateralizedExposurePV[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].bank().seniorFundingSpread();

			adblSymmetricFundingValueAdjustment[iVertexIndex - 1] = 0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblSymmetricFundingValueAdjustment;
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
