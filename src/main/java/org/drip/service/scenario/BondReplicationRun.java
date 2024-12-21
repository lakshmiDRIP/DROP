
package org.drip.service.scenario;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;

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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>BondReplicationRun</i> holds the Results of a Full Bond Replication Run. It provides the following
 *  Functionality:
 *
 *  <ul>
 * 		<li>Empty <i>BondReplicationRun</i> Constructor</li>
 * 		<li>Add a Named Field</li>
 * 		<li>Add a Named Field Map</li>
 * 		<li>Retrieve the Named Field Metrics</li>
 * 		<li>Retrieve the Named Field Map Metrics</li>
 * 		<li>Generate the Headers</li>
 * 		<li>Generate the Values</li>
 * 		<li>Display the Measures</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/scenario/README.md">Custom Scenario Service Metric Generator</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BondReplicationRun
{
	private Map<String, NamedField> _namedFieldMap = new CaseInsensitiveHashMap<NamedField>();

	private Map<String, NamedFieldMap> _namedFieldMapMap = new CaseInsensitiveHashMap<NamedFieldMap>();

	/**
	 * Empty <i>BondReplicationRun</i> Constructor
	 */

	public BondReplicationRun()
	{
	}

	/**
	 * Add a Named Field
	 * 
	 * @param namedField The Named Field
	 * 
	 * @return TRUE - The Named Field Successfully added
	 */

	public boolean addNamedField (
		final NamedField namedField)
	{
		if (null == namedField) {
			return false;
		}

		_namedFieldMap.put (namedField.name(), namedField);

		return true;
	}

	/**
	 * Add a Named Field Map
	 * 
	 * @param namedFieldMap The Named Field Map
	 * 
	 * @return TRUE - The Named Field Map Successfully added
	 */

	public boolean addNamedFieldMap (
		final NamedFieldMap namedFieldMap)
	{
		if (null == namedFieldMap) {
			return false;
		}

		_namedFieldMapMap.put (namedFieldMap.name(), namedFieldMap);

		return true;
	}

	/**
	 * Retrieve the Named Field Metrics
	 * 
	 * @return The Named Field Metrics
	 */

	public Map<String, NamedField> namedField()
	{
		return _namedFieldMap;
	}

	/**
	 * Retrieve the Named Field Map Metrics
	 * 
	 * @return The Named Field Map Metrics
	 */

	public Map<String, NamedFieldMap> namedFieldMap()
	{
		return _namedFieldMapMap;
	}

	/**
	 * Generate the Headers
	 * 
	 * @return The Headers
	 */

	public String header()
	{
		String header = "";

		for (Map.Entry<String, NamedField> namedFieldMapEntry : _namedFieldMap.entrySet()) {
			header = header + namedFieldMapEntry.getKey() + ",";
		}

		for (Map.Entry<String, NamedFieldMap> namedFieldMapMapEntry : _namedFieldMapMap.entrySet()) {
			String namedFieldMapMapEntryKey = namedFieldMapMapEntry.getKey() + ",";

			NamedFieldMap namedFieldMap = namedFieldMapMapEntry.getValue();

			for (Map.Entry<String, Double> meNFMEntry : namedFieldMap.value().entrySet()) {
				header = header + namedFieldMapMapEntryKey + "::" + meNFMEntry.getKey() + ",";
			}
		}

		return header;
	}

	/**
	 * Generate The Values
	 * 
	 * @return The Values
	 */

	public String value()
	{
		String value = "";

		for (Map.Entry<String, NamedField> namedFieldMapEntry : _namedFieldMap.entrySet()) {
			value = value + namedFieldMapEntry.getValue().value() + ",";
		}

		for (Map.Entry<String, NamedFieldMap> namedFieldMapMapEntry : _namedFieldMapMap.entrySet()) {
			for (Map.Entry<String, Double> namedFieldMapMapValueEntry :
				namedFieldMapMapEntry.getValue().value().entrySet())
			{
				value = value + namedFieldMapMapValueEntry.getValue() + ",";
			}
		}

		return value;
	}

	/**
	 * Display the Measures
	 * 
	 * @return The Measures
	 */

	public String display()
	{
		String value = "";

		for (Map.Entry<String, NamedField> namedFieldMapEntry : _namedFieldMap.entrySet()) {
			value = value + namedFieldMapEntry.getKey() + " => " + namedFieldMapEntry.getValue().value() +
				"\n";
		}

		for (Map.Entry<String, NamedFieldMap> namedFieldMapMapEntry : _namedFieldMapMap.entrySet()) {
			NamedFieldMap namedFieldMap = namedFieldMapMapEntry.getValue();

			String namedFieldMapMapEntryKey = namedFieldMapMapEntry.getKey();

			for (Map.Entry<String, Double> namedFieldMapMapEntryValue : namedFieldMap.value().entrySet()) {
				value = value + namedFieldMapMapEntryKey + "::" + namedFieldMapMapEntryValue.getKey() +
					" => " + namedFieldMapMapEntryValue.getValue() + "\n";
			}
		}

		return value;
	}
}
