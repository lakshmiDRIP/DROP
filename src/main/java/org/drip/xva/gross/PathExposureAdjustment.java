
package org.drip.xva.gross;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>PathExposureAdjustment</i> aggregates the Exposures and the Adjustments across Multiple Netting/Funding
 * Groups on a Single Path Projection Run along the Granularity of a Counter Party Group. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/README.md">XVA Gross Adiabat Exposure Aggregation</a></li>
 *  </ul>
 * <br><br>
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
