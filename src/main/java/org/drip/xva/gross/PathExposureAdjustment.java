
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
 * PathExposureAdjustment aggregates the Exposures and the Adjustments across Multiple Netting/Funding Groups
 *  on a Single Path Projection Run along the Granularity of a Counter Party Group. The References are:
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

public interface PathExposureAdjustment
{

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	abstract public org.drip.analytics.date.JulianDate[] vertexDates();

	/**
	 * Retrieve the Array of Collateralized Vertex Exposures
	 * 
	 * @return The Array of Collateralized Vertex Exposures
	 */

	abstract public double[] vertexCollateralizedExposure();

	/**
	 * Retrieve the Array of Collateralized Vertex Exposure PVs
	 * 
	 * @return The Array of Collateralized Vertex Exposure PVs
	 */

	abstract public double[] vertexCollateralizedExposurePV();

	/**
	 * Retrieve the Array of Collateralized Positive Vertex Exposures
	 * 
	 * @return The Array of Collateralized Positive Vertex Exposures
	 */

	abstract public double[] vertexCollateralizedPositiveExposure();

	/**
	 * Retrieve the Array of Collateralized Positive Vertex Exposure PVs
	 * 
	 * @return The Array of Collateralized Positive Vertex Exposure PVs
	 */

	abstract public double[] vertexCollateralizedPositiveExposurePV();

	/**
	 * Retrieve the Array of Collateralized Negative Vertex Exposures
	 * 
	 * @return The Array of Collateralized Negative Vertex Exposures
	 */

	abstract public double[] vertexCollateralizedNegativeExposure();

	/**
	 * Retrieve the Array of Collateralized Negative Vertex Exposure PV
	 * 
	 * @return The Array of Collateralized Negative Vertex Exposure PV
	 */

	abstract public double[] vertexCollateralizedNegativeExposurePV();

	/**
	 * Retrieve the Array of Uncollateralized Vertex Exposures
	 * 
	 * @return The Array of Uncollateralized Vertex Exposures
	 */

	abstract public double[] vertexUncollateralizedExposure();

	/**
	 * Retrieve the Array of Uncollateralized Vertex Exposure PV
	 * 
	 * @return The Array of Uncollateralized Vertex Exposure PV
	 */

	abstract public double[] vertexUncollateralizedExposurePV();

	/**
	 * Retrieve the Array of Uncollateralized Positive Vertex Exposures
	 * 
	 * @return The Array of Uncollateralized Positive Vertex Exposures
	 */

	abstract public double[] vertexUncollateralizedPositiveExposure();

	/**
	 * Retrieve the Array of Uncollateralized Positive Vertex Exposure PV
	 * 
	 * @return The Array of Uncollateralized Positive Vertex Exposure PV
	 */

	abstract public double[] vertexUncollateralizedPositiveExposurePV();

	/**
	 * Retrieve the Array of Uncollateralized Vertex Negative Exposures
	 * 
	 * @return The Array of Uncollateralized Vertex Negative Exposures
	 */

	abstract public double[] vertexUncollateralizedNegativeExposure();

	/**
	 * Retrieve the Array of Uncollateralized Vertex Negative Exposure PV
	 * 
	 * @return The Array of Uncollateralized Vertex Negative Exposure PV
	 */

	abstract public double[] vertexUncollateralizedNegativeExposurePV();

	/**
	 * Retrieve the Array of Vertex Funding Exposures
	 * 
	 * @return The Array of Vertex Funding Exposures
	 */

	abstract public double[] vertexFundingExposure();

	/**
	 * Retrieve the Array of Vertex Funding Exposure PVs
	 * 
	 * @return The Array of Vertex Funding Exposures PVs
	 */

	abstract public double[] vertexFundingExposurePV();

	/**
	 * Compute Path Unilateral Collateral Adjustment
	 * 
	 * @return The Path Unilateral Collateral Adjustment
	 */

	abstract public double unilateralCollateralAdjustment();

	/**
	 * Compute Path Bilateral Collateral Adjustment
	 * 
	 * @return The Path Bilateral Collateral Adjustment
	 */

	abstract public double bilateralCollateralAdjustment();

	/**
	 * Compute Path Collateral Adjustment
	 * 
	 * @return The Path Collateral Adjustment
	 */

	abstract public double collateralAdjustment();

	/**
	 * Compute Path Unilateral Credit Adjustment
	 * 
	 * @return The Path Unilateral Credit Adjustment
	 */

	abstract public double unilateralCreditAdjustment();

	/**
	 * Compute Path Bilateral Credit Adjustment
	 * 
	 * @return The Path Bilateral Credit Adjustment
	 */

	abstract public double bilateralCreditAdjustment();

	/**
	 * Compute Path Credit Adjustment
	 * 
	 * @return The Path Credit Adjustment
	 */

	abstract public double creditAdjustment();

	/**
	 * Compute Path Contra-Liability Credit Adjustment
	 * 
	 * @return The Path Contra-Liability Credit Adjustment
	 */

	abstract public double contraLiabilityCreditAdjustment();

	/**
	 * Compute Path Unilateral Debt Adjustment
	 * 
	 * @return The Path Unilateral Debt Adjustment
	 */

	abstract public double unilateralDebtAdjustment();

	/**
	 * Compute Path Bilateral Debt Adjustment
	 * 
	 * @return The Path Bilateral Debt Adjustment
	 */

	abstract public double bilateralDebtAdjustment();

	/**
	 * Compute Path Debt Adjustment
	 * 
	 * @return The Path Debt Adjustment
	 */

	abstract public double debtAdjustment();

	/**
	 * Compute Path Contra-Asset Debt Adjustment
	 * 
	 * @return The Path Contra-Asset Debt Adjustment
	 */

	abstract public double contraAssetDebtAdjustment();

	/**
	 * Compute Path Unilateral Funding Value Adjustment
	 * 
	 * @return The Path Unilateral Funding Value Adjustment
	 */

	abstract public double unilateralFundingValueAdjustment();

	/**
	 * Compute Path Bilateral Funding Value Adjustment
	 * 
	 * @return The Path Bilateral Funding Value Adjustment
	 */

	abstract public double bilateralFundingValueAdjustment();

	/**
	 * Compute Path Funding Value Adjustment
	 * 
	 * @return The Path Funding Value Adjustment
	 */

	abstract public double fundingValueAdjustment();

	/**
	 * Compute Path Funding Debt Adjustment
	 * 
	 * @return The Path Funding Debt Adjustment
	 */

	abstract public double fundingDebtAdjustment();

	/**
	 * Compute Path Funding Cost Adjustment
	 * 
	 * @return The Path Funding Cost Adjustment
	 */

	abstract public double fundingCostAdjustment();

	/**
	 * Compute Path Funding Benefit Adjustment
	 * 
	 * @return The Path Funding Benefit Adjustment
	 */

	abstract public double fundingBenefitAdjustment();

	/**
	 * Compute Path Symmetric Funding Value Adjustment
	 * 
	 * @return The Path Symmetric Funding Value Adjustment
	 */

	abstract public double symmetricFundingValueAdjustment();

	/**
	 * Compute Path Total Adjustment
	 * 
	 * @return The Path Total Adjustment
	 */

	abstract public double totalAdjustment();
}
