
package org.drip.execution.athl;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>CalibrationEmpirics</i> contains the Universal Market Impact Exponent/Coefficients that have been
 * determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 * (2003). The References are:
 * 
 * <br><br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 * 			<b>3 (2)</b> 5-39
 * 	</li>
 * 	<li>
 * 		Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 			<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 	</li>
 * 	<li>
 * 		Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b>
 * 			57-62
 * 	</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/README.md">ATHL</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CalibrationEmpirics {

	/**
	 * Almgren, Thum, Hauptmann, and Li (2005) Universal Permanent Impact Exponent
	 */

	public static final double PERMANENT_IMPACT_EXPONENT_ATHL2005 = 0.891;

	/**
	 * Almgren, Thum, Hauptmann, and Li (2005) Universal Permanent Impact Exponent One Sigma
	 */

	public static final double PERMANENT_IMPACT_EXPONENT_ATHL2005_ONE_SIGMA = 0.10;

	/**
	 * Quasi-Arbitrage Free Universal Permanent Impact Exponent
	 */

	public static final double PERMANENT_IMPACT_EXPONENT_QUASI_ARBITRAGE_FREE = 1.00;

	/**
	 * Universal Permanent Impact Exponent
	 */

	public static final double PERMANENT_IMPACT_EXPONENT = PERMANENT_IMPACT_EXPONENT_QUASI_ARBITRAGE_FREE;

	/**
	 * Universal Permanent Impact Coefficient
	 */

	public static final double PERMANENT_IMPACT_COEFFICIENT = 0.314;

	/**
	 * Universal Permanent Impact Coefficient One Sigma
	 */

	public static final double PERMANENT_IMPACT_COEFFICIENT_ONE_SIGMA = 0.041;

	/**
	 * The ATHL2005 Permanent Impact Inverse Turnover Coefficient
	 */

	public static final double PERMANENT_IMPACT_INVERSE_TURNOVER_EXPONENT_ATHL2005 = 0.267;

	/**
	 * The ATHL2005 Permanent Impact Inverse Turnover Coefficient One Sigma Error
	 */

	public static final double PERMANENT_IMPACT_INVERSE_TURNOVER_EXPONENT_ATHL2005_ONE_SIGMA = 0.22;

	/**
	 * The Universal Permanent Impact Inverse Turnover Coefficient
	 */

	public static final double PERMANENT_IMPACT_INVERSE_TURNOVER_EXPONENT = 0.25;

	/**
	 * Universal Temporary Impact Exponent
	 */

	public static final double TEMPORARY_IMPACT_EXPONENT = 0.600;

	/**
	 * Universal Temporary Impact Exponent One Sigma
	 */

	public static final double TEMPORARY_IMPACT_EXPONENT_ONE_SIGMA = 0.038;

	/**
	 * Universal Temporary Impact Coefficient
	 */

	public static final double TEMPORARY_IMPACT_COEFFICIENT = 0.142;

	/**
	 * Universal Temporary Impact Coefficient One Sigma
	 */

	public static final double TEMPORARY_IMPACT_COEFFICIENT_ONE_SIGMA = 0.0062;
}
