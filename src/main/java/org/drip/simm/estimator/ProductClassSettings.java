
package org.drip.simm.estimator;

import java.util.List;

import org.drip.measure.identifier.LabelledVertexCorrelation;
import org.drip.simm.common.CrossRiskClassCorrelation20;
import org.drip.simm.common.CrossRiskClassCorrelation21;
import org.drip.simm.parameters.RiskClassSensitivitySettings;
import org.drip.simm.parameters.RiskClassSensitivitySettingsCR;
import org.drip.simm.parameters.RiskClassSensitivitySettingsIR;

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
 * <i>ProductClassSettings</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket
 * 	Sensitivities across Individual Product Classes. The References are:
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
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
 *  	</li>
 *  </ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct an ISDA SIMM 2.0 Version of <i>ProductClassSettings</i></li>
 * 		<li>Construct an ISDA SIMM 2.1 Version of <i>ProductClassSettings</i></li>
 * 		<li>Construct an ISDA SIMM 2.4 Version of <i>ProductClassSettings</i></li>
 * 		<li><i>ProductClassSettings</i> Constructor</li>
 * 		<li>Retrieve the Equity Risk Class Sensitivity Settings</li>
 * 		<li>Retrieve the Commodity Risk Class Sensitivity Settings</li>
 * 		<li>Retrieve the FX Risk Class Sensitivity Settings</li>
 * 		<li>Retrieve the IR Risk Class Sensitivity Settings</li>
 * 		<li>Retrieve the Credit Qualifying Risk Class Sensitivity Settings</li>
 * 		<li>Retrieve the Credit Non-Qualifying Risk Class Sensitivity Settings</li>
 * 		<li>Retrieve the Cross Risk Class Label Correlation</li>
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

public class ProductClassSettings
{
	private LabelledVertexCorrelation _labelCorrelation = null;
	private RiskClassSensitivitySettings _fxRiskClassSensitivitySettings = null;
	private RiskClassSensitivitySettingsIR _irRiskClassSensitivitySettings = null;
	private RiskClassSensitivitySettings _equityRiskClassSensitivitySettings = null;
	private RiskClassSensitivitySettings _commodityRiskClassSensitivitySettings = null;
	private RiskClassSensitivitySettingsCR _creditQualifyingRiskClassSensitivitySettings = null;
	private RiskClassSensitivitySettingsCR _creditNonQualifyingRiskClassSensitivitySettings = null;

	/**
	 * Construct an ISDA SIMM 2.0 Version of <i>ProductClassSettings</i>
	 * 
	 * @param currencyList Currency List
	 * @param vegaDurationDays The Volatility Duration in Days
	 * 
	 * @return ISDA SIMM 2.0 Version of <i>ProductClassSettings</i>
	 */

	public static final ProductClassSettings ISDA_20 (
		final List<String> currencyList,
		final int vegaDurationDays)
	{
		try {
			return new ProductClassSettings (
				RiskClassSensitivitySettings.ISDA_EQ_20 (vegaDurationDays),
				RiskClassSensitivitySettings.ISDA_CT_20 (vegaDurationDays),
				RiskClassSensitivitySettings.ISDA_FX_20 (vegaDurationDays),
				RiskClassSensitivitySettingsIR.ISDA_20 (currencyList),
				RiskClassSensitivitySettingsCR.ISDA_CRQ_20(),
				RiskClassSensitivitySettingsCR.ISDA_CRNQ_20(),
				CrossRiskClassCorrelation20.Matrix()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA SIMM 2.1 Version of <i>ProductClassSettings</i>
	 * 
	 * @param currencyList Currency List
	 * @param vegaDurationDays The Volatility Duration in Days
	 * 
	 * @return ISDA SIMM 2.1 Version of <i>ProductClassSettings</i>
	 */

	public static final ProductClassSettings ISDA_21 (
		final List<String> currencyList,
		final int vegaDurationDays)
	{
		try {
			return new ProductClassSettings (
				RiskClassSensitivitySettings.ISDA_EQ_21 (vegaDurationDays),
				RiskClassSensitivitySettings.ISDA_CT_21 (vegaDurationDays),
				RiskClassSensitivitySettings.ISDA_FX_21 (vegaDurationDays),
				RiskClassSensitivitySettingsIR.ISDA_21 (currencyList),
				RiskClassSensitivitySettingsCR.ISDA_CRQ_21(),
				RiskClassSensitivitySettingsCR.ISDA_CRNQ_21(),
				CrossRiskClassCorrelation21.Matrix()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA SIMM 2.4 Version of <i>ProductClassSettings</i>
	 * 
	 * @param currencyList Currency List
	 * @param vegaDurationDays The Volatility Duration in Days
	 * @param givenCurrency Given Currency
	 * @param calculationCurrency Calculation Currency
	 * 
	 * @return ISDA SIMM 2.4 Version of <i>ProductClassSettings</i>
	 */

	public static final ProductClassSettings ISDA_24 (
		final List<String> currencyList,
		final int vegaDurationDays,
		final String givenCurrency,
		final String calculationCurrency)
	{
		try {
			return new ProductClassSettings (
				RiskClassSensitivitySettings.ISDA_EQ_24 (vegaDurationDays),
				RiskClassSensitivitySettings.ISDA_CT_24 (vegaDurationDays),
				RiskClassSensitivitySettings.ISDA_FX_24 (
					vegaDurationDays,
					givenCurrency,
					calculationCurrency
				),
				RiskClassSensitivitySettingsIR.ISDA_24 (currencyList),
				RiskClassSensitivitySettingsCR.ISDA_CRQ_24(),
				RiskClassSensitivitySettingsCR.ISDA_CRNQ_24(),
				CrossRiskClassCorrelation21.Matrix()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>ProductClassSettings</i> Constructor
	 * 
	 * @param equityRiskClassSensitivitySettings Equity Risk Class Sensitivity Settings
	 * @param commodityRiskClassSensitivitySettings Commodity Risk Class Sensitivity Settings
	 * @param fxRiskClassSensitivitySettings FX Risk Class Sensitivity Settings
	 * @param irRiskClassSensitivitySettings IR Risk Class Sensitivity Settings
	 * @param creditQualifyingRiskClassSensitivitySettings Credit Qualifying Risk Class Sensitivity Settings
	 * @param creditNonQualifyingRiskClassSensitivitySettings Credit Non-Qualifying Risk Class Sensitivity
	 * 	Settings
	 * @param labelCorrelation Cross Risk Class Label Correlation
	 * 
	 * @throws Exception Throw if the Inputs are Invalid
	 */

	public ProductClassSettings (
		final RiskClassSensitivitySettings equityRiskClassSensitivitySettings,
		final RiskClassSensitivitySettings commodityRiskClassSensitivitySettings,
		final RiskClassSensitivitySettings fxRiskClassSensitivitySettings,
		final RiskClassSensitivitySettingsIR irRiskClassSensitivitySettings,
		final RiskClassSensitivitySettingsCR creditQualifyingRiskClassSensitivitySettings,
		final RiskClassSensitivitySettingsCR creditNonQualifyingRiskClassSensitivitySettings,
		final LabelledVertexCorrelation labelCorrelation)
		throws Exception
	{
		if (
			(
				null == (_equityRiskClassSensitivitySettings = equityRiskClassSensitivitySettings) &&
				null == (_commodityRiskClassSensitivitySettings = commodityRiskClassSensitivitySettings) &&
				null == (_fxRiskClassSensitivitySettings = fxRiskClassSensitivitySettings) &&
				null == (_irRiskClassSensitivitySettings = irRiskClassSensitivitySettings) &&
				null == (_creditQualifyingRiskClassSensitivitySettings =
					creditQualifyingRiskClassSensitivitySettings) &&
				null == (_creditNonQualifyingRiskClassSensitivitySettings =
					creditNonQualifyingRiskClassSensitivitySettings
				)
			) || null == (_labelCorrelation = labelCorrelation))
		{
			throw new Exception ("ProductClassSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Equity Risk Class Sensitivity Settings
	 * 
	 * @return The Equity Risk Class Sensitivity Settings
	 */

	public RiskClassSensitivitySettings equityRiskClassSensitivitySettings()
	{
		return _equityRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Commodity Risk Class Sensitivity Settings
	 * 
	 * @return The Commodity Risk Class Sensitivity Settings
	 */

	public RiskClassSensitivitySettings commodityRiskClassSensitivitySettings()
	{
		return _commodityRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the FX Risk Class Sensitivity Settings
	 * 
	 * @return The FX Risk Class Sensitivity Settings
	 */

	public RiskClassSensitivitySettings fxRiskClassSensitivitySettings()
	{
		return _fxRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the IR Risk Class Sensitivity Settings
	 * 
	 * @return The IR Risk Class Sensitivity Settings
	 */

	public RiskClassSensitivitySettingsIR irRiskClassSensitivitySettings()
	{
		return _irRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Sensitivity Settings
	 * 
	 * @return The Credit Qualifying Risk Class Sensitivity Settings
	 */

	public RiskClassSensitivitySettingsCR creditQualifyingRiskClassSensitivitySettings()
	{
		return _creditQualifyingRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Sensitivity Settings
	 * 
	 * @return The Credit Non-Qualifying Risk Class Sensitivity Settings
	 */

	public RiskClassSensitivitySettingsCR creditNonQualifyingRiskClassSensitivitySettings()
	{
		return _creditNonQualifyingRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Cross Risk Class Label Correlation
	 * 
	 * @return The Cross Risk Class Label Correlation
	 */

	public LabelledVertexCorrelation labelCorrelation()
	{
		return _labelCorrelation;
	}
}
