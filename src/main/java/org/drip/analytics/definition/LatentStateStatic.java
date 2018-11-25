
package org.drip.analytics.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>LatentStateStatic</i> contains the Analytics Latent State Static/Textual Identifiers.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/definition">Definition</a></li>
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
