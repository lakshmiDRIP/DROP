
package org.drip.service.scenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * BondReplicationRun holds the Results of a Full Bond Replication Run,
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
