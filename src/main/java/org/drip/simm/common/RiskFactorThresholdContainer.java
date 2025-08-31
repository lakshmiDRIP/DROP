
package org.drip.simm.common;

import org.drip.simm.commodity.CTRiskThresholdContainer20;
import org.drip.simm.commodity.CTRiskThresholdContainer21;
import org.drip.simm.commodity.CTRiskThresholdContainer24;
import org.drip.simm.credit.CRThresholdContainer20;
import org.drip.simm.credit.CRThresholdContainer21;
import org.drip.simm.credit.CRThresholdContainer24;
import org.drip.simm.equity.EQRiskThresholdContainer20;
import org.drip.simm.equity.EQRiskThresholdContainer21;
import org.drip.simm.equity.EQRiskThresholdContainer24;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.fx.FXRiskThresholdContainer21;
import org.drip.simm.fx.FXRiskThresholdContainer24;
import org.drip.simm.rates.IRConcentrationThresholdContainer20;
import org.drip.simm.rates.IRConcentrationThresholdContainer21;
import org.drip.simm.rates.IRConcentrationThresholdContainer24;

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
 * <i>RiskFactorThresholdContainer</i> holds the ISDA SIMM 2.0 Risk Factor Thresholds - the Concentration
 * 	Limits for Interest Rate, Credit Spread, Equity, Commodity, and FX Risk Factors. The References are:
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
 * 		<li>The RatesFX Multiplicative Factor Default (1.0)</li>
 * 		<li>The Credit Qualifying Multiplicative Factor Default (1.0)</li>
 * 		<li>The Credit Non-Qualifying Multiplicative Factor Default (1.0)</li>
 * 		<li>The Equity Multiplicative Factor Default (1.0)</li>
 * 		<li>The Commodity Multiplicative Factor Default (1.0)</li>
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

public class RiskFactorThresholdContainer
{

	/**
	 * Initialize the Risk Factor Threshold Container
	 * 
	 * @return TRUE - The Risk Factor Threshold Container successfully initialized
	 */

	public static final boolean Init()
	{
		if (!IRConcentrationThresholdContainer20.Init()) {
			return false;
		}

		if (!IRConcentrationThresholdContainer21.Init()) {
			return false;
		}

		if (!IRConcentrationThresholdContainer24.Init()) {
			return false;
		}

		if (!CRThresholdContainer20.Init()) {
			return false;
		}

		if (!CRThresholdContainer21.Init()) {
			return false;
		}

		if (!CRThresholdContainer24.Init()) {
			return false;
		}

		if (!EQRiskThresholdContainer20.Init()) {
			return false;
		}

		if (!EQRiskThresholdContainer21.Init()) {
			return false;
		}

		if (!EQRiskThresholdContainer24.Init()) {
			return false;
		}

		if (!CTRiskThresholdContainer20.Init()) {
			return false;
		}

		if (!CTRiskThresholdContainer21.Init()) {
			return false;
		}

		if (!CTRiskThresholdContainer24.Init()) {
			return false;
		}

		if (!FXRiskThresholdContainer20.Init()) {
			return false;
		}

		if (!FXRiskThresholdContainer21.Init()) {
			return false;
		}

		if (!FXRiskThresholdContainer24.Init()) {
			return false;
		}

		return true;
	}
}
