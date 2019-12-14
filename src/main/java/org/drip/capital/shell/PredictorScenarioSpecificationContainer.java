
package org.drip.capital.shell;

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
 * <i>PredictorScenarioSpecificationContainer</i> maintains the Map of Predictors and their Scenario Stress
 * 	Specification as well the Map of Predictors and their Categories. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte-Carlo Methods in Financial Engineering</i> <b>Springer</b>
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/README.md">Economic Risk Capital Parameter Contexts</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PredictorScenarioSpecificationContainer
{
	private java.util.Map<java.lang.String, java.util.List<java.lang.String>>
		_categoryPredictorListMap = null;
	private java.util.Map<java.lang.String, org.drip.capital.gsstdesign.PredictorScenarioSpecification>
		_predictorScenarioSpecificationMap = null;

	/**
	 * Empty PredictorScenarioSpecificationContainer Constructor
	 */

	public PredictorScenarioSpecificationContainer()
	{
	}

	/**
	 * Retrieve the Predictor Stress Scenario Specification Map
	 * 
	 * @return The Predictor Stress Scenario Specification Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.gsstdesign.PredictorScenarioSpecification>
		predictorScenarioSpecificationMap()
	{
		return _predictorScenarioSpecificationMap;
	}

	/**
	 * Retrieve the Map of the Categories to their corresponding Predictor Lists
	 * 
	 * @return Map of the Categories to their corresponding Predictor Lists
	 */

	public java.util.Map<java.lang.String, java.util.List<java.lang.String>> categoryPredictorListMap()
	{
		return _categoryPredictorListMap;
	}

	/**
	 * Add the specified Predictor Scenario Specification
	 * 
	 * @param predictorScenarioSpecification The Predictor Scenario Specification
	 * 
	 * @return TRUE - The Predictor Scenario Specification successfully added
	 */

	public boolean addPredictorScenarioSpecification (
		final org.drip.capital.gsstdesign.PredictorScenarioSpecification predictorScenarioSpecification)
	{
		if (null == predictorScenarioSpecification)
		{
			return false;
		}

		java.lang.String name = predictorScenarioSpecification.name();

		java.lang.String category = predictorScenarioSpecification.category();

		if (null == _predictorScenarioSpecificationMap)
		{
			_predictorScenarioSpecificationMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.gsstdesign.PredictorScenarioSpecification>();
		}

		_predictorScenarioSpecificationMap.put (
			name,
			predictorScenarioSpecification
		);

		if (null == _categoryPredictorListMap)
		{
			_categoryPredictorListMap =
				new org.drip.analytics.support.CaseInsensitiveHashMap<java.util.List<java.lang.String>>();
		}

		if (_categoryPredictorListMap.containsKey (
			category
		))
		{
			_categoryPredictorListMap.get (
				category
			).add (
				name
			);
		}
		else
		{
			java.util.List<java.lang.String> nameList = new java.util.ArrayList<java.lang.String>();

			nameList.add (
				name
			);

			_categoryPredictorListMap.put (
				category,
				nameList
			);
		}

		return true;
	}

	/**
	 * Indicate if the Predictor has a Stress Specification Available
	 * 
	 * @param predictorName The Predictor
	 * 
	 * @return TRUE - The Predictor has a Stress Specification Available
	 */

	public boolean containsPredictor (
		final java.lang.String predictorName)
	{
		return null != predictorName && !predictorName.isEmpty() &&
			null != _predictorScenarioSpecificationMap &&
			_predictorScenarioSpecificationMap.containsKey (
				predictorName
			);
	}

	/**
	 * Indicate if the Category has Predictor(s) Available
	 * 
	 * @param category The Category
	 * 
	 * @return TRUE - The Category has Predictor(s) Available
	 */

	public boolean containsCategory (
		final java.lang.String category)
	{
		return null != category && !category.isEmpty() && null != _categoryPredictorListMap &&
			_categoryPredictorListMap.containsKey (
				category
			);
	}

	/**
	 * Retrieve the Predictor Scenario Specification
	 * 
	 * @param predictorName The Predictor
	 * 
	 * @return The Predictor Scenario Specification
	 */

	public org.drip.capital.gsstdesign.PredictorScenarioSpecification predictorScenarioSpecification (
		final java.lang.String predictorName)
	{
		return containsPredictor (
			predictorName
		) ? _predictorScenarioSpecificationMap.get (
			predictorName
		) : null;
	}

	/**
	 * Retrieve the Predictors corresponding to the Category
	 * 
	 * @param category The Category
	 * 
	 * @return The Predictors corresponding to the Category
	 */

	public java.util.List<java.lang.String> predictorList (
		final java.lang.String category)
	{
		return containsCategory (
			category
		) ? _categoryPredictorListMap.get (
			category
		) : null;
	}
}
