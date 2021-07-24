
package org.drip.sample.businessspec;

import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.shell.RiskTypeContext;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>RBCRiskTypeMapping</i> zeds the RBC to the iVAST Risk Type Mapping. It also indicates if the RBC is to
 * 	be excluded from Consideration. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/businessspec/README.md">Business Grouping and Hierarchy Specification</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RBCRiskTypeMapping
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] rbcArray =
		{
			"CGM_ACC",
			"CGM_AFS",
			"CGM_CVAA_MTM",
			"CGM_CVAL_MTM",
			"CGM_CVA_MTM",
			"CGM_HFS", 
			"CGM_MTM",
			"CITIB_ACC",
			"CITIB_AFS",
			"CITIB_CRDT_MTM",
			"CITIB_CVAA_MTM",
			"CITIB_CVAL_MTM",
			"CITIB_CVA_MTM",
			"CITIB_HFI",
			"CITIB_HTM",
			"CITIB_IVAST_ACC",
			"CITIB_IVAST_AFS",
			"CITIB_MTM",
			"CITIB_NT_MTM",
			"CITIG_ACC",
			"CITIG_AFS",
			"CITIG_CVAA_MTM",
			"CITIG_CVAL_MTM",
			"CITIG_CVA_MTM",
			"CITIG_HFS",
			"CITIG_HTM",
			"CITIG_IVAST_ACC",
			"CITIG_MTM",
			"CITIG_NT_MTM",
		};

		RiskTypeContext riskTypeContext = CapitalEstimationContextManager.ContextContainer().riskTypeContext();

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t|        RBC CODE RISK TYPE MAP         ||");

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t|    L -> R:                            ||");

		System.out.println ("\t|        - RBC                          ||");

		System.out.println ("\t|        - Exclude/Include              ||");

		System.out.println ("\t|        - Risk Type                    ||");

		System.out.println ("\t|---------------------------------------||");

		for (String rbc : rbcArray)
		{
			System.out.println (
				"\t| " + rbc + " => " +
				(riskTypeContext.rbcExclusionCheck (rbc) ? "EXCLUDE" : "INCLUDE") + " | " +
				riskTypeContext.riskType (rbc) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
