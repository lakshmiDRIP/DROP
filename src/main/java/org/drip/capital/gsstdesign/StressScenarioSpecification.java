
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>StressScenarioSpecification</i> specifies the Full Stress Scenario Specification for the given Market
 *	Factor/Applicability Combination. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/gsstdesign/README.md">Systemic Stress Scenario Design/Construction</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StressScenarioSpecification
{
	private org.drip.capital.gsstdesign.CapitalBaselineDefinition _capitalBaselineDefinition = null;
	private org.drip.capital.gsstdesign.HistoricalScenarioDefinition _historicalScenarioDefinition = null;
	private org.drip.capital.gsstdesign.StressScenarioQuantification _stressScenarioQuantification = null;
	private org.drip.capital.gsstdesign.HypotheticalScenarioDefinition _hypotheticalScenarioDefinition =
		null;

	/**
	 * StressScenarioSpecification Constructor
	 * 
	 * @param stressScenarioQuantification Stress Scenario Quantification
	 * @param hypotheticalScenarioDefinition Hypothetical Scenario Definition
	 * @param historicalScenarioDefinition Historical Scenario Definition
	 * @param capitalBaselineDefinition Capital Baseline Definition
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StressScenarioSpecification (
		final org.drip.capital.gsstdesign.StressScenarioQuantification stressScenarioQuantification,
		final org.drip.capital.gsstdesign.HypotheticalScenarioDefinition hypotheticalScenarioDefinition,
		final org.drip.capital.gsstdesign.HistoricalScenarioDefinition historicalScenarioDefinition,
		final org.drip.capital.gsstdesign.CapitalBaselineDefinition capitalBaselineDefinition)
		throws java.lang.Exception
	{
		if (null == (_stressScenarioQuantification = stressScenarioQuantification) ||
			null == (_hypotheticalScenarioDefinition = hypotheticalScenarioDefinition) ||
			null == (_historicalScenarioDefinition = historicalScenarioDefinition) ||
			null == (_capitalBaselineDefinition = capitalBaselineDefinition))
		{
			throw new java.lang.Exception ("StressScenarioSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Stress Scenario Quantification
	 * 
	 * @return The Stress Scenario Quantification
	 */

	public org.drip.capital.gsstdesign.StressScenarioQuantification stressScenarioQuantification()
	{
		return _stressScenarioQuantification;
	}

	/**
	 * Retrieve the Hypothetical Scenario Definition
	 * 
	 * @return The Hypothetical Scenario Definition
	 */

	public org.drip.capital.gsstdesign.HypotheticalScenarioDefinition hypotheticalScenarioDefinition()
	{
		return _hypotheticalScenarioDefinition;
	}

	/**
	 * Retrieve the Historical Scenario Definition
	 * 
	 * @return The Historical Scenario Definition
	 */

	public org.drip.capital.gsstdesign.HistoricalScenarioDefinition historicalScenarioDefinition()
	{
		return _historicalScenarioDefinition;
	}

	/**
	 * Retrieve the Capital Baseline Definition
	 * 
	 * @return The Capital Baseline Definition
	 */

	public org.drip.capital.gsstdesign.CapitalBaselineDefinition capitalBaselineDefinition()
	{
		return _capitalBaselineDefinition;
	}
}
