
package org.drip.simm.rates;

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
 * <i>IRSystemics</i> contains the Systemic Settings of the SIMM Interest Rate Risk Factors. The References
 * are:
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
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Interest Rate Type - Regular Volatility</li>
 * 		<li>Interest Rate Type - Low Volatility</li>
 * 		<li>Interest Rate Type - High Volatility</li>
 * 		<li>Interest Rate Type - NULL Volatility</li>
 * 		<li>Interest Rate Type - Trade Frequency Type Well Traded</li>
 * 		<li>Interest Rate Type - Trade Frequency Type Less Well Traded</li>
 * 		<li>Sub Curve - OIS</li>
 * 		<li>Sub Curve - LIBOR-1M</li>
 * 		<li>Sub Curve - LIBOR-3M</li>
 * 		<li>Sub Curve - LIBOR-6M</li>
 * 		<li>Sub Curve - LIBOR-12M</li>
 * 		<li>Sub Curve - PRIME</li>
 * 		<li>Sub Curve - MUNICIPAL</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/rates/README.md">SIMM IR Risk Factor Settings</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IRSystemics
{

	/**
	 * Interest Rate Type - Regular Volatility
	 */

	public static final String VOLATILITY_TYPE_REGULAR = "REGULAR";

	/**
	 * Interest Rate Type - Low Volatility
	 */

	public static final String VOLATILITY_TYPE_LOW = "LOW";

	/**
	 * Interest Rate Type - High Volatility
	 */

	public static final String VOLATILITY_TYPE_HIGH = "HIGH";

	/**
	 * Interest Rate Type - NULL Volatility
	 */

	public static final String VOLATILITY_TYPE_NULL = "NULL";

	/**
	 * Interest Rate Type - Trade Frequency Type Well Traded
	 */

	public static final String TRADE_FREQUENCY_WELL_TRADED = "WELL_TRADED";

	/**
	 * Interest Rate Type - Trade Frequency Type Less Well Traded
	 */

	public static final String TRADE_FREQUENCY_LESS_WELL_TRADED = "LESS_WELL_TRADED";

	/**
	 * Sub Curve - OIS
	 */

	public static final String SUB_CURVE_OIS = "OIS";

	/**
	 * Sub Curve - LIBOR-1M
	 */

	public static final String SUB_CURVE_LIBOR_1M = "LIBOR_1M";

	/**
	 * Sub Curve - LIBOR-3M
	 */

	public static final String SUB_CURVE_LIBOR_3M = "LIBOR_3M";

	/**
	 * Sub Curve - LIBOR-6M
	 */

	public static final String SUB_CURVE_LIBOR_6M = "LIBOR_6M";

	/**
	 * Sub Curve - LIBOR-12M
	 */

	public static final String SUB_CURVE_LIBOR_12M = "LIBOR_12M";

	/**
	 * Sub Curve - PRIME
	 */

	public static final String SUB_CURVE_PRIME = "PRIME";

	/**
	 * Sub Curve - MUNICIPAL
	 */

	public static final String SUB_CURVE_MUNICIPAL = "MUNICIPAL";
}
