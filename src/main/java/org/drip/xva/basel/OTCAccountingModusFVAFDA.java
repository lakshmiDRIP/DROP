
package org.drip.xva.basel;

import org.drip.xva.gross.ExposureAdjustmentAggregator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>OTCAccountingModusFVAFDA</i> implements the Basel Accounting Scheme using the FVA/FDA Specification of
 * the Streamlined Accounting Framework for OTC Derivatives, as described in Albanese and Andersen (2014).
 * The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2012): <i>Consultative Document: Application of Own Credit Risk Adjustments to
 *  			Derivatives</i> <b>Basel Committee on Banking Supervision</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel/README.md">XVA Based Basel Accounting Measures</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OTCAccountingModusFVAFDA extends OTCAccountingModus
{

	/**
	 * OTCAccountingModusFVAFDA Constructor
	 * 
	 * @param exposureAdjustmentAggregator The Counter Party Group Aggregator Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public OTCAccountingModusFVAFDA (
		final ExposureAdjustmentAggregator exposureAdjustmentAggregator)
		throws Exception
	{
		super (exposureAdjustmentAggregator);
	}

	@Override public double contraAssetAdjustment()
	{
		ExposureAdjustmentAggregator exposureAdjustmentAggregator = aggregator();

		return exposureAdjustmentAggregator.ucva().amount() + exposureAdjustmentAggregator.fva().amount();
	}

	@Override public double contraLiabilityAdjustment()
	{
		ExposureAdjustmentAggregator exposureAdjustmentAggregator = aggregator();

		return exposureAdjustmentAggregator.cvacl().amount() + exposureAdjustmentAggregator.ftddva().amount()
			+ exposureAdjustmentAggregator.fda().amount();
	}

	@Override public OTCAccountingPolicy feePolicy (
		final ExposureAdjustmentAggregator exposureAdjustmentAggregatorNext)
	{
		if (null == exposureAdjustmentAggregatorNext) {
			return null;
		}

		ExposureAdjustmentAggregator exposureAdjustmentAggregator = aggregator();

		double collateralVAChange = exposureAdjustmentAggregatorNext.colva().amount() -
			exposureAdjustmentAggregator.colva().amount();

		double contraLiabilityChange = exposureAdjustmentAggregatorNext.ftddva().amount() +
			exposureAdjustmentAggregatorNext.fda().amount() +
			exposureAdjustmentAggregatorNext.cvacl().amount() -
			exposureAdjustmentAggregator.ftddva().amount() - exposureAdjustmentAggregator.fda().amount() -
			exposureAdjustmentAggregator.cvacl().amount();

		try {
			return new OTCAccountingPolicy (
				exposureAdjustmentAggregatorNext.ucva().amount() +
				exposureAdjustmentAggregatorNext.fva().amount() -
				exposureAdjustmentAggregator.ucva().amount() -
				exposureAdjustmentAggregator.fva().amount() + collateralVAChange,
				0.,
				contraLiabilityChange,
				contraLiabilityChange
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
