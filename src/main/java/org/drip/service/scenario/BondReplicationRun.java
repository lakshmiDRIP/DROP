
package org.drip.service.scenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>BondReplicationRun</i> holds the Results of a Full Bond Replication Run.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/scenario/README.md">Custom Scenario Service Metric Generator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BondReplicationRun {
	private java.util.Map<java.lang.String, org.drip.service.scenario.NamedField> _mapNF = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.service.scenario.NamedField>();

	private java.util.Map<java.lang.String, org.drip.service.scenario.NamedFieldMap> _mapNFM = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.service.scenario.NamedFieldMap>();

	/**
	 * Empty ReplicationRun Constructor
	 */

	public BondReplicationRun()
	{
	}

	/**
	 * Add a Named Field
	 * 
	 * @param nf The Named Field
	 * 
	 * @return TRUE - The Named Field Successfully added
	 */

	public boolean addNamedField (
		final org.drip.service.scenario.NamedField nf)
	{
		if (null == nf) return false;

		_mapNF.put (nf.name(), nf);

		return true;
	}

	/**
	 * Add a Named Field Map
	 * 
	 * @param nfm The Named Field Map
	 * 
	 * @return TRUE - The Named Field Map Successfully added
	 */

	public boolean addNamedFieldMap (
		final org.drip.service.scenario.NamedFieldMap nfm)
	{
		if (null == nfm) return false;

		_mapNFM.put (nfm.name(), nfm);

		return true;
	}

	/**
	 * Retrieve the Named Field Metrics
	 * 
	 * @return The Named Field Metrics
	 */

	public java.util.Map<java.lang.String, org.drip.service.scenario.NamedField> namedField()
	{
		return _mapNF;
	}

	/**
	 * Retrieve the Named Field Map Metrics
	 * 
	 * @return The Named Field Map Metrics
	 */

	public java.util.Map<java.lang.String, org.drip.service.scenario.NamedFieldMap> namedFieldMap()
	{
		return _mapNFM;
	}

	/**
	 * Generate The Headers
	 * 
	 * @return The Headers
	 */

	public java.lang.String header()
	{
		java.lang.String strHeader = "";

		for (java.util.Map.Entry<java.lang.String, org.drip.service.scenario.NamedField> meNF :
			_mapNF.entrySet())
			strHeader = strHeader + meNF.getKey() + ",";

		for (java.util.Map.Entry<java.lang.String, org.drip.service.scenario.NamedFieldMap> meNFM :
			_mapNFM.entrySet()) {
			java.lang.String strNFMKey = meNFM.getKey() + ",";

			org.drip.service.scenario.NamedFieldMap nfm = meNFM.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> meNFMEntry : nfm.value().entrySet())
				strHeader = strHeader + strNFMKey + "::" + meNFMEntry.getKey() + ",";
		}

		return strHeader;
	}

	/**
	 * Generate The Values
	 * 
	 * @return The Values
	 */

	public java.lang.String value()
	{
		java.lang.String strValue = "";

		for (java.util.Map.Entry<java.lang.String, org.drip.service.scenario.NamedField> meNF :
			_mapNF.entrySet())
			strValue = strValue + meNF.getValue().value() + ",";

		for (java.util.Map.Entry<java.lang.String, org.drip.service.scenario.NamedFieldMap> meNFM :
			_mapNFM.entrySet()) {
			org.drip.service.scenario.NamedFieldMap nfm = meNFM.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> meNFMEntry : nfm.value().entrySet())
				strValue = strValue + meNFMEntry.getValue() + ",";
		}

		return strValue;
	}

	/**
	 * Display the Measures
	 * 
	 * @return The Measures
	 */

	public java.lang.String display()
	{
		java.lang.String strValue = "";

		for (java.util.Map.Entry<java.lang.String, org.drip.service.scenario.NamedField> meNF :
			_mapNF.entrySet())
			strValue = strValue + meNF.getKey() + " => " + meNF.getValue().value() + "\n";

		for (java.util.Map.Entry<java.lang.String, org.drip.service.scenario.NamedFieldMap> meNFM :
			_mapNFM.entrySet()) {
			org.drip.service.scenario.NamedFieldMap nfm = meNFM.getValue();

			java.lang.String strNFMKey = meNFM.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> meNFMEntry : nfm.value().entrySet())
				strValue = strValue + strNFMKey + "::" + meNFMEntry.getKey() + " => " + meNFMEntry.getValue()
					+ "\n";
		}

		return strValue;
	}
}
