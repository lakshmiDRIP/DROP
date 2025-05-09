
package org.drip.simm.common;

import org.drip.simm.commodity.CTSettingsContainer20;
import org.drip.simm.commodity.CTSettingsContainer21;
import org.drip.simm.commodity.CTSettingsContainer24;
import org.drip.simm.credit.CRNQSettingsContainer20;
import org.drip.simm.credit.CRNQSettingsContainer21;
import org.drip.simm.credit.CRNQSettingsContainer24;
import org.drip.simm.credit.CRQSettingsContainer20;
import org.drip.simm.credit.CRQSettingsContainer21;
import org.drip.simm.credit.CRQSettingsContainer24;
import org.drip.simm.equity.EQSettingsContainer20;
import org.drip.simm.equity.EQSettingsContainer21;
import org.drip.simm.equity.EQSettingsContainer24;
import org.drip.simm.fx.FXVolatilityGroupContainer24;
import org.drip.simm.rates.IRSettingsContainer20;
import org.drip.simm.rates.IRSettingsContainer21;
import org.drip.simm.rates.IRSettingsContainer24;

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
 * <i>ISDASettingsContainer</i> holds the ISDA SIMM Risk Weights/Correlations for Interest Rates, Qualifying
 * 	and Non-qualifying Credit, Equity, Commodity, and Foreign Exchange. The corresponding Concentration
 * 	Thresholds are also contained. The References are:
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
 * 		<li>Initial the ISDA Settings Container</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/common/README.md">Common Cross Risk Factor Utilities</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ISDASettingsContainer
{

	/**
	 * Initial the ISDA Settings Container
	 * 
	 * @return TRUE - The ISDA Settings Container successfully initialized
	 */

	public static final boolean Init()
	{
		if (!IRSettingsContainer20.Init()) {
			return false;
		}

		if (!IRSettingsContainer21.Init()) {
			return false;
		}

		if (!IRSettingsContainer24.Init()) {
			return false;
		}

		if (!CRQSettingsContainer20.Init()) {
			return false;
		}

		if (!CRQSettingsContainer21.Init()) {
			return false;
		}

		if (!CRQSettingsContainer24.Init()) {
			return false;
		}

		if (!CRNQSettingsContainer20.Init()) {
			return false;
		}

		if (!CRNQSettingsContainer21.Init()) {
			return false;
		}

		if (!CRNQSettingsContainer24.Init()) {
			return false;
		}

		if (!EQSettingsContainer20.Init()) {
			return false;
		}

		if (!EQSettingsContainer21.Init()) {
			return false;
		}

		if (!EQSettingsContainer24.Init()) {
			return false;
		}

		if (!CTSettingsContainer20.Init()) {
			return false;
		}

		if (!CTSettingsContainer21.Init()) {
			return false;
		}

		if (!CTSettingsContainer24.Init()) {
			return false;
		}

		if (!FXVolatilityGroupContainer24.Init()) {
			return false;
		}

		if (!RiskFactorThresholdContainer.Init()) {
			return false;
		}

		return true;
	}
}
