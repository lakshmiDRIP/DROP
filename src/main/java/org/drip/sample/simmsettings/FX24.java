
package org.drip.sample.simmsettings;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.simm.fx.FXSystemics24;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
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
 * <i>FX24</i> demonstrates the Extraction and Display of ISDA SIMM 2.4 FX Bucket Risk Weights, Correlations,
 * 	and Systemics. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmsettings/README.md">ISDA SIMM Calibration Parameter Settings</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FX24
{

	private static final void Systemics()
	{
		System.out.println ("\t||----------------------------------------------------------------||");

		System.out.println ("\t||                      2.4 FX SYSTEMICS                          ||");

		System.out.println ("\t||----------------------------------------------------------------||");

		System.out.println (
			"\t|| Regular Given/Regular Calculation FX Delta Weight   => " +
			FormatUtil.FormatDouble (
				FXSystemics24.REGULAR_REGULAR_DELTA_RISK_WEIGHT, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| High Given/Regular Calculation FX Delta Weight      => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HIGH_REGULAR_DELTA_RISK_WEIGHT, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Regular Given/High Calculation FX Delta Weight      => " +
			FormatUtil.FormatDouble (
				FXSystemics24.REGULAR_HIGH_DELTA_RISK_WEIGHT, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| High Given/High Calculation FX Delta Weight         => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HIGH_HIGH_DELTA_RISK_WEIGHT, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Historical Volatility Ratio                         => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HISTORICAL_VOLATILITY_RATIO, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Vega Risk Weight                                    => " +
			FormatUtil.FormatDouble (
				FXSystemics24.VEGA_RISK_WEIGHT, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Volatility/Curvature Correlation                    => " +
			FormatUtil.FormatDouble (
				FXSystemics24.VOLATILITY_CURVATURE_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Regular Base: Regular Given/Regular Calculation Corr=> " +
			FormatUtil.FormatDouble (
				FXSystemics24.REGULAR_REGULAR_REGULAR_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Regular Base: Regular Given/High Calculation Corr   => " +
			FormatUtil.FormatDouble (
				FXSystemics24.REGULAR_REGULAR_HIGH_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Regular Base: High Given/Regular Calculation Corr   => " +
			FormatUtil.FormatDouble (
				FXSystemics24.REGULAR_HIGH_REGULAR_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| Regular Base: High Given/High Calculation Corr      => " +
			FormatUtil.FormatDouble (
				FXSystemics24.REGULAR_HIGH_HIGH_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| High Base: Regular Given/Regular Calculation Corr   => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HIGH_REGULAR_REGULAR_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| High Base: Regular Given/High Calculation Corr      => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HIGH_REGULAR_HIGH_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| High Base: High Given/Regular Calculation Corr      => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HIGH_HIGH_REGULAR_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println (
			"\t|| High Base: High Given/High Calculation Corr         => " +
			FormatUtil.FormatDouble (
				FXSystemics24.HIGH_HIGH_HIGH_CORRELATION, 3, 2, 1.
			) + " ||"
		);

		System.out.println ("\t||----------------------------------------------------------------||");

		System.out.println();
	}

	/**
	 * Entry Point
	 * 
	 * @param args Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		Systemics();

		EnvManager.TerminateEnv();
	}
}
