
package org.drip.analytics.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>LatentStateStatic</i> contains the Analytics Latent State Static/Textual Identifiers.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition/README.md">Latent State Curves, Surfaces, Turns</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateStatic {

	/**
	 * Forward Latent State
	 */

	public static final java.lang.String LATENT_STATE_FORWARD = "LATENT_STATE_FORWARD";

	/**
	 * Forward Latent State Quantification Metric - Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_FORWARD_RATE = "FORWARD_QM_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - LIBOR Rate
	 */

	public static final java.lang.String FORWARD_QM_LIBOR_RATE = "FORWARD_QM_LIBOR_RATE";

	/**
	 * Forward Latent State Quantification Metric - Shifted Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_SHIFTED_FORWARD_RATE = "FORWARD_QM_SHIFTED_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Instantaneous Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_INSTANTANEOUS_FORWARD_RATE =
		"FORWARD_QM_INSTANTANEOUS_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Continuously Compounded Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE =
		"FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Instantaneous Effective Annual Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_INSTANTANEOUS_EFFECTIVE_FORWARD_RATE =
		"FORWARD_QM_EFFECTIVE_ANNUAL_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Instantaneous Nominal Annual Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_INSTANTANEOUS_NOMINAL_FORWARD_RATE =
		"FORWARD_QM_NOMINAL_ANNUAL_FORWARD_RATE";

	/**
	 * Funding Latent State
	 */

	public static final java.lang.String LATENT_STATE_FUNDING = "LATENT_STATE_FUNDING";

	/**
	 * Discount Latent State Quantification Metric - Discount Factor
	 */

	public static final java.lang.String DISCOUNT_QM_DISCOUNT_FACTOR = "DISCOUNT_QM_DISCOUNT_FACTOR";

	/**
	 * Discount Latent State Quantification Metric - Zero Rate
	 */

	public static final java.lang.String DISCOUNT_QM_ZERO_RATE = "DISCOUNT_QM_ZERO_RATE";

	/**
	 * Discount Latent State Quantification Metric - Compounded Short Rate
	 */

	public static final java.lang.String DISCOUNT_QM_COMPOUNDED_SHORT_RATE =
		"DISCOUNT_QM_COMPOUNDED_SHORT_RATE";

	/**
	 * Discount Latent State Quantification Metric - Forward Rate
	 */

	public static final java.lang.String DISCOUNT_QM_FORWARD_RATE = "DISCOUNT_QM_FORWARD_RATE";

	/**
	 * Govvie Latent State
	 */

	public static final java.lang.String LATENT_STATE_GOVVIE = "LATENT_STATE_GOVVIE";

	/**
	 * Govvie Latent State Quantification Metric - Treasury Benchmark Yield
	 */

	public static final java.lang.String GOVVIE_QM_YIELD = "GOVVIE_QM_YIELD";

	/**
	 * FX Latent State
	 */

	public static final java.lang.String LATENT_STATE_FX = "LATENT_STATE_FX";

	/**
	 * FX Latent State Quantification Metric - FX Forward Outright
	 */

	public static final java.lang.String FX_QM_FORWARD_OUTRIGHT = "FX_QM_FORWARD_OUTRIGHT";

	/**
	 * Volatility Latent State
	 */

	public static final java.lang.String LATENT_STATE_VOLATILITY = "LATENT_STATE_VOLATILITY";

	/**
	 * Volatility Latent State Quantification Metric - SABR Volatility
	 */

	public static final java.lang.String VOLATILITY_QM_SABR_VOLATILITY = "VOLATILITY_QM_SABR_VOLATILITY";

	/**
	 * Volatility Latent State Quantification Metric - Lognormal Volatility
	 */

	public static final java.lang.String VOLATILITY_QM_LOGNORMAL_VOLATILITY =
		"VOLATILITY_QM_LOGNORMAL_VOLATILITY";

	/**
	 * Volatility Latent State Quantification Metric - Normal Volatility
	 */

	public static final java.lang.String VOLATILITY_QM_NORMAL_VOLATILITY = "VOLATILITY_QM_NORMAL_VOLATILITY";
}
