
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

public abstract class CreditDebtGroupPath extends org.drip.xva.netting.ExposureGroupPath {

	protected CreditDebtGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupPath[] aHGP,
		final org.drip.xva.universe.MarketPath mp)
		throws java.lang.Exception
	{
		super (aHGP, mp);
	}

	/**
	 * Compute Path Unilateral Credit Adjustment
	 * 
	 * @return The Path Unilateral Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double unilateralCreditAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblCreditExposurePV = creditExposurePV();

		int iNumVertex = adblCreditExposurePV.length;
		double dblUnilateralCreditAdjustment = 0.;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCreditExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].counterParty().seniorRecoveryRate());

			double dblPeriodIntegrandEnd = adblCreditExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].counterParty().seniorRecoveryRate());

			dblUnilateralCreditAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex - 1].counterParty().survivalProbability() -
					aMV[iVertexIndex].counterParty().survivalProbability());
		}

		return dblUnilateralCreditAdjustment;
	}

	/**
	 * Compute Path Bilateral Credit Adjustment
	 * 
	 * @return The Path Bilateral Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bilateralCreditAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblCreditExposurePV = creditExposurePV();

		int iNumVertex = adblCreditExposurePV.length;
		double dblBilateralCreditAdjustment = 0.;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCreditExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].counterParty().seniorRecoveryRate()) *
					aMV[iVertexIndex - 1].bank().survivalProbability();

			double dblPeriodIntegrandEnd = adblCreditExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].counterParty().seniorRecoveryRate()) *
					aMV[iVertexIndex].bank().survivalProbability();

			dblBilateralCreditAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex - 1].counterParty().survivalProbability() -
					aMV[iVertexIndex].counterParty().survivalProbability());
		}

		return dblBilateralCreditAdjustment;
	}

	/**
	 * Compute Path Contra-Liability Credit Adjustment
	 * 
	 * @return The Path Contra-Liability Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double contraLiabilityCreditAdjustment()
		throws java.lang.Exception
	{
		return unilateralCreditAdjustment() - bilateralCreditAdjustment();
	}

	/**
	 * Compute Path Unilateral Debt Adjustment
	 * 
	 * @return The Path Unilateral Debt Adjustment
	 */

	public double unilateralDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblDebtExposurePV = debtExposurePV();

		int iNumVertex = adblDebtExposurePV.length;
		double dblUnilateralDebtAdjustment = 0.;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].bank().seniorRecoveryRate());

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].bank().seniorRecoveryRate());

			dblUnilateralDebtAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex - 1].bank().survivalProbability() -
					aMV[iVertexIndex].bank().survivalProbability());
		}

		return dblUnilateralDebtAdjustment;
	}

	/**
	 * Compute Path Bilateral Debt Adjustment
	 * 
	 * @return The Path Bilateral Debt Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bilateralDebtAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblDebtExposurePV = debtExposurePV();

		int iNumVertex = adblDebtExposurePV.length;
		double dblBilateralDebtAdjustment = 0.;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].bank().seniorRecoveryRate()) *
					aMV[iVertexIndex - 1].counterParty().survivalProbability();

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].bank().seniorRecoveryRate()) *
					aMV[iVertexIndex].counterParty().survivalProbability();

			dblBilateralDebtAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd) *
				(aMV[iVertexIndex - 1].bank().survivalProbability() -
					aMV[iVertexIndex].bank().survivalProbability());
		}

		return dblBilateralDebtAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Credit Adjustment
	 * 
	 * @return The Period-wise Unilateral Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodUnilateralCreditAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblCreditExposurePV = creditExposurePV();

		int iNumVertex = aMV.length;
		double[] adblPeriodUnilateralCreditAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCreditExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].counterParty().seniorRecoveryRate());

			double dblPeriodIntegrandEnd = adblCreditExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].counterParty().seniorRecoveryRate());

			adblPeriodUnilateralCreditAdjustment[iVertexIndex - 1] = 0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex - 1].counterParty().survivalProbability() -
					aMV[iVertexIndex].counterParty().survivalProbability());
		}

		return adblPeriodUnilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Credit Adjustment
	 * 
	 * @return The Period-wise Bilateral Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodBilateralCreditAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblCreditExposurePV = creditExposurePV();

		int iNumVertex = aMV.length;
		double[] adblPeriodBilateralCreditAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCreditExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].counterParty().seniorRecoveryRate()) *
					aMV[iVertexIndex - 1].bank().survivalProbability();

			double dblPeriodIntegrandEnd = adblCreditExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].counterParty().seniorRecoveryRate()) *
					aMV[iVertexIndex].bank().survivalProbability();

			adblPeriodBilateralCreditAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex - 1].counterParty().survivalProbability() -
					aMV[iVertexIndex].counterParty().survivalProbability());
		}

		return adblPeriodBilateralCreditAdjustment;
	}

	/**
	 * Compute Period-wise Contra-Liability Credit Adjustment
	 * 
	 * @return The Period-wise Contra-Liability Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodContraLiabilityCreditAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblCreditExposurePV = creditExposurePV();

		int iNumVertex = aMV.length;
		double[] adblPeriodContraLiabilityCreditAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCreditExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].counterParty().seniorRecoveryRate()) * (1. -
					aMV[iVertexIndex - 1].bank().survivalProbability());

			double dblPeriodIntegrandEnd = adblCreditExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].counterParty().seniorRecoveryRate()) * (1. -
					aMV[iVertexIndex].bank().survivalProbability());

			adblPeriodContraLiabilityCreditAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex - 1].counterParty().survivalProbability() -
					aMV[iVertexIndex].counterParty().survivalProbability());
		}

		return adblPeriodContraLiabilityCreditAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Debt Adjustment
	 * 
	 * @return The Period-wise Unilateral Debt Adjustment
	 */

	public double[] periodUnilateralDebtAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblDebtExposurePV = debtExposurePV();

		int iNumVertex = aMV.length;
		double[] adblUnilateralDebtAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblDebtExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].bank().seniorRecoveryRate());

			double dblPeriodIntegrandEnd = adblDebtExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].bank().seniorRecoveryRate());

			adblUnilateralDebtAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex - 1].bank().survivalProbability() -
					aMV[iVertexIndex].bank().survivalProbability());
		}

		return adblUnilateralDebtAdjustment;
	}

	/**
	 * Compute Period-wise Bilateral Debt Adjustment
	 * 
	 * @return The Period-wise Bilateral Debt Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodBilateralDebtAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = marketPath().vertexes();

		double[] adblCreditExposurePV = creditExposurePV();

		int iNumVertex = adblCreditExposurePV.length;
		double[] adblBilateralDebtAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCreditExposurePV[iVertexIndex - 1] * (1. -
				aMV[iVertexIndex - 1].bank().seniorRecoveryRate()) *
					aMV[iVertexIndex - 1].counterParty().survivalProbability();

			double dblPeriodIntegrandEnd = adblCreditExposurePV[iVertexIndex] * (1. -
				aMV[iVertexIndex].bank().seniorRecoveryRate()) *
					aMV[iVertexIndex].counterParty().survivalProbability();

			adblBilateralDebtAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex - 1].bank().survivalProbability() -
					aMV[iVertexIndex].bank().survivalProbability());
		}

		return adblBilateralDebtAdjustment;
	}

	/**
	 * Compute Path Credit Adjustment
	 * 
	 * @return The Path Credit Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double creditAdjustment()
		throws java.lang.Exception;

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
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double[] periodCreditAdjustment()
		throws java.lang.Exception;

	/**
	 * Compute Period-wise Debt Adjustment
	 * 
	 * @return The Period-wise Debt Adjustment
	 */

	public abstract double[] periodDebtAdjustment();
}
