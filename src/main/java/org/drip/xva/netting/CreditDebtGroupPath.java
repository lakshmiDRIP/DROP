
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
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double unilateralCreditAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposure = creditExposure();

		int vertexCount = creditExposure.length;
		double unilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			unilateralCreditAdjustment -=
				0.5 * (creditExposure[vertexIndex - 1] + creditExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return unilateralCreditAdjustment;
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
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposure = creditExposure();

		int vertexCount = creditExposure.length;
		double bilateralCreditAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			bilateralCreditAdjustment -=
				0.5 * (creditExposure[vertexIndex - 1] + creditExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
				0.5 * (marketVertexArray[vertexIndex - 1].dealer().survivalProbability() +
					marketVertexArray[vertexIndex].dealer().survivalProbability()) *
				(marketVertexArray[vertexIndex - 1].client().survivalProbability() -
					marketVertexArray[vertexIndex].client().survivalProbability());
		}

		return bilateralCreditAdjustment;
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

		double[] debtExposure = debtExposure();

		int vertexCount = debtExposure.length;
		double unilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			unilateralDebtAdjustment -=
				0.5 * (debtExposure[vertexIndex - 1] + debtExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return unilateralDebtAdjustment;
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
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] debtExposure = debtExposure();

		int vertexCount = debtExposure.length;
		double bilateralDebtAdjustment = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			bilateralDebtAdjustment -=
				0.5 * (debtExposure[vertexIndex - 1] + debtExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
				0.5 * (marketVertexArray[vertexIndex - 1].client().survivalProbability() +
					marketVertexArray[vertexIndex].client().survivalProbability()) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return bilateralDebtAdjustment;
	}

	/**
	 * Compute Period-wise Unilateral Credit Adjustment
	 * 
	 * @return The Period-wise Unilateral Credit Adjustment
	 */

	public double[] periodUnilateralCreditAdjustment()
	{
		org.drip.xva.universe.MarketVertex[] marketVertexArray = marketPath().vertexes();

		double[] creditExposure = creditExposure();

		int vertexCount = creditExposure.length;
		double[] periodUnilateralCreditAdjustment = new double[vertexCount];
		periodUnilateralCreditAdjustment[0] = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodUnilateralCreditAdjustment[vertexIndex] =
				-0.5 * (creditExposure[vertexIndex - 1] + creditExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
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

		double[] creditExposure = creditExposure();

		int vertexCount = creditExposure.length;
		double[] periodBilateralCreditAdjustment = new double[vertexCount];
		periodBilateralCreditAdjustment[0] = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodBilateralCreditAdjustment[vertexIndex] =
				-0.5 * (creditExposure[vertexIndex - 1] + creditExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].client().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].client().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
				0.5 * (marketVertexArray[vertexIndex - 1].dealer().survivalProbability() +
					marketVertexArray[vertexIndex].dealer().survivalProbability()) *
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
				periodUnilateralCreditAdjustment[vertexIndex] - periodBilateralCreditAdjustment[vertexIndex];
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

		double[] debtExposure = debtExposure();

		int vertexCount = debtExposure.length;
		double[] periodUnilateralDebtAdjustment = new double[vertexCount];
		periodUnilateralDebtAdjustment[0] = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodUnilateralDebtAdjustment[vertexCount] =
				-0.5 * (debtExposure[vertexIndex - 1] + debtExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
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

		double[] debtExposure = debtExposure();

		int vertexCount = debtExposure.length;
		double[] periodBilateralDebtAdjustment = new double[vertexCount];
		periodBilateralDebtAdjustment[0] = 0.;

		for (int vertexIndex = 1; vertexIndex < vertexCount; ++vertexIndex)
		{
			periodBilateralDebtAdjustment[vertexIndex] =
				-0.5 * (debtExposure[vertexIndex - 1] + debtExposure[vertexIndex]) *
				0.5 * (marketVertexArray[vertexIndex - 1].dealer().seniorRecoveryRate() +
					marketVertexArray[vertexIndex].dealer().seniorRecoveryRate()) *
				0.5 * (marketVertexArray[vertexIndex - 1].overnightReplicator() +
					marketVertexArray[vertexIndex].overnightReplicator()) *
				0.5 * (marketVertexArray[vertexIndex - 1].client().survivalProbability() +
					marketVertexArray[vertexIndex].client().survivalProbability()) *
				(marketVertexArray[vertexIndex - 1].dealer().survivalProbability() -
					marketVertexArray[vertexIndex].dealer().survivalProbability());
		}

		return periodBilateralDebtAdjustment;
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
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double debtAdjustment()
		throws java.lang.Exception;

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
