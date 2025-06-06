
package org.drip.simm.estimator;

import org.drip.simm.foundation.MarginEstimationSettings;
import org.drip.simm.product.RiskClassSensitivity;
import org.drip.simm.product.RiskClassSensitivityCR;
import org.drip.simm.product.RiskClassSensitivityIR;

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
 * <i>ProductClassSensitivity</i> holds the multiple Risk Class Sensitivities for a single Product Class. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>ProductClassSensitivity</i> Constructor</li>
 * 		<li>Retrieve the Equity Risk Class Sensitivity</li>
 * 		<li>Retrieve the Commodity Risk Class Sensitivity</li>
 * 		<li>Retrieve the FX Risk Class Sensitivity</li>
 * 		<li>Retrieve the IR Risk Class Sensitivity</li>
 * 		<li>Retrieve the Credit Qualifying Risk Class Sensitivity</li>
 * 		<li>Retrieve the Credit Non-Qualifying Risk Class Sensitivity</li>
 * 		<li>Generate the Margin for the Product Class</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator/README.md">ISDA SIMM Core + Add-On Estimator</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductClassSensitivity
{
	private RiskClassSensitivity _fxRiskClassSensitivity = null;
	private RiskClassSensitivityIR _irRiskClassSensitivity = null;
	private RiskClassSensitivity _equityRiskClassSensitivity = null;
	private RiskClassSensitivity _commodityRiskClassSensitivity = null;
	private RiskClassSensitivityCR _creditQualifyingRiskClassSensitivity = null;
	private RiskClassSensitivityCR _creditNonQualifyingRiskClassSensitivity = null;

	/**
	 * <i>ProductClassSensitivity</i> Constructor
	 * 
	 * @param equityRiskClassSensitivity Equity Risk Class Sensitivity
	 * @param commodityRiskClassSensitivity Commodity Risk Class Sensitivity
	 * @param fxRiskClassSensitivity FX Risk Class Sensitivity
	 * @param irRiskClassSensitivity IR Risk Class Sensitivity
	 * @param creditQualifyingRiskClassSensitivity Credit Qualifying Risk Class Sensitivity
	 * @param creditNonQualifyingRiskClassSensitivity Credit Non-Qualifying Risk Class Sensitivity
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ProductClassSensitivity (
		final RiskClassSensitivity equityRiskClassSensitivity,
		final RiskClassSensitivity commodityRiskClassSensitivity,
		final RiskClassSensitivity fxRiskClassSensitivity,
		final RiskClassSensitivityIR irRiskClassSensitivity,
		final RiskClassSensitivityCR creditQualifyingRiskClassSensitivity,
		final RiskClassSensitivityCR creditNonQualifyingRiskClassSensitivity)
		throws Exception
	{
		if (null == (_equityRiskClassSensitivity = equityRiskClassSensitivity) &&
			null == (_commodityRiskClassSensitivity = commodityRiskClassSensitivity) &&
			null == (_fxRiskClassSensitivity = fxRiskClassSensitivity) &&
			null == (_irRiskClassSensitivity = irRiskClassSensitivity) &&
			null == (_creditQualifyingRiskClassSensitivity = creditQualifyingRiskClassSensitivity) &&
			null == (_creditNonQualifyingRiskClassSensitivity = creditNonQualifyingRiskClassSensitivity))
		{
			throw new Exception ("ProductClassSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Equity Risk Class Sensitivity
	 * 
	 * @return The Equity Risk Class Sensitivity
	 */

	public RiskClassSensitivity equityRiskClassSensitivity()
	{
		return _equityRiskClassSensitivity;
	}

	/**
	 * Retrieve the Commodity Risk Class Sensitivity
	 * 
	 * @return The Commodity Risk Class Sensitivity
	 */

	public RiskClassSensitivity commodityRiskClassSensitivity()
	{
		return _commodityRiskClassSensitivity;
	}

	/**
	 * Retrieve the FX Risk Class Sensitivity
	 * 
	 * @return The FX Risk Class Sensitivity
	 */

	public RiskClassSensitivity fxRiskClassSensitivity()
	{
		return _fxRiskClassSensitivity;
	}

	/**
	 * Retrieve the IR Risk Class Sensitivity
	 * 
	 * @return The IR Risk Class Sensitivity
	 */

	public RiskClassSensitivityIR irRiskClassSensitivity()
	{
		return _irRiskClassSensitivity;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Sensitivity
	 * 
	 * @return The Credit Qualifying Risk Class Sensitivity
	 */

	public RiskClassSensitivityCR creditQualifyingRiskClassSensitivity()
	{
		return _creditQualifyingRiskClassSensitivity;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Sensitivity
	 * 
	 * @return The Credit Non-Qualifying Risk Class Sensitivity
	 */

	public RiskClassSensitivityCR creditNonQualifyingRiskClassSensitivity()
	{
		return _creditNonQualifyingRiskClassSensitivity;
	}

	/**
	 * Generate the Margin for the Product Class
	 * 
	 * @param productClassSettings The Product Class Settings
	 * @param marginEstimationSettings Margin Estimation Settings
	 * 
	 * @return The Margin for the Product Class
	 */

	public ProductClassMargin estimate (
		final ProductClassSettings productClassSettings,
		final MarginEstimationSettings marginEstimationSettings)
	{
		try {
			return null == productClassSettings ? null : new ProductClassMargin (
				null == _irRiskClassSensitivity ? null : _irRiskClassSensitivity.aggregate (
					productClassSettings.irRiskClassSensitivitySettings(),
					marginEstimationSettings
				),
				null == _creditQualifyingRiskClassSensitivity ? null :
					_creditQualifyingRiskClassSensitivity.aggregate (
						productClassSettings.creditQualifyingRiskClassSensitivitySettings(),
						marginEstimationSettings
					),
				null == _creditNonQualifyingRiskClassSensitivity ? null :
					_creditNonQualifyingRiskClassSensitivity.aggregate (
						productClassSettings.creditNonQualifyingRiskClassSensitivitySettings(),
						marginEstimationSettings
					),
				null == _equityRiskClassSensitivity ? null : _equityRiskClassSensitivity.aggregate (
					productClassSettings.equityRiskClassSensitivitySettings(),
					marginEstimationSettings
				),
				null == _fxRiskClassSensitivity ? null : _fxRiskClassSensitivity.aggregate (
					productClassSettings.fxRiskClassSensitivitySettings(),
					marginEstimationSettings
				),
				null == _commodityRiskClassSensitivity ? null : _commodityRiskClassSensitivity.aggregate (
					productClassSettings.commodityRiskClassSensitivitySettings(),
					marginEstimationSettings
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
