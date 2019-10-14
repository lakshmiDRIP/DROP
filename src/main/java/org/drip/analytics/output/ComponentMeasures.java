
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>ComponentMeasures</i> is the place holder for analytical single component output measures, optionally
 * across scenarios. It contains measure maps for the following scenarios:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			Unadjusted Base IR/credit curves
 * 		</li>
 *		<li>
 *			Flat delta/gamma bump measure maps for IR/credit bump curves
 *		</li>
 *		<li>
 *			Tenor bump double maps for IR/credit curves
 *		</li>
 *		<li>
 *			Flat/recovery bumped measure maps for recovery bumped credit curves
 *		</li>
 *		<li>
 *			Measure Maps generated for Custom Scenarios
 *		</li>
 *		<li>
 *			Accessor Functions for the above fields
 *		</li>
 *		<li>
 *			Serialize into and de-serialize out of byte arrays
 *		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Period Product Targeted Valuation Measures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComponentMeasures {
	private double _dblCalcTime = java.lang.Double.NaN;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapBaseMeasures = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatIRDeltaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatIRGammaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatRRDeltaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatRRGammaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatCreditDeltaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatCreditGammaMeasures =
		null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorIRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorIRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorCreditDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorCreditGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorRRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorRRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmCustomMeasures = null;

	/**
	 * Empty constructor - all members initialized to NaN or null
	 */

	public ComponentMeasures()
	{
	}

	/**
	 * Retrieve the Calculation Time
	 * 
	 * @return The Calculation Time
	 */

	public double calcTime()
	{
		return _dblCalcTime;
	}

	/**
	 * Set the Calculation Time
	 * 
	 * @param dblCalcTime The Calculation Time
	 * 
	 * @return TRUE - The Calculation Time Successfully Set
	 */

	public boolean setCalcTime (
		final double dblCalcTime)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCalcTime)) return false;

		_dblCalcTime = dblCalcTime;
		return true;
	}

	/**
	 * Retrieve the Base Measure Map
	 * 
	 * @return The Base Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> baseMeasures()
	{
		return _mapBaseMeasures;
	}

	/**
	 * Set the Base Measures Map
	 * 
	 * @param mapBaseMeasures The Base Measures Map
	 * 
	 * @return TRUE - The Base Measures Map Successfully Set
	 */

	public boolean setBaseMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapBaseMeasures)
	{
		if (null == mapBaseMeasures || 0 == mapBaseMeasures.size()) return false;

		_mapBaseMeasures = mapBaseMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat IR Delta Measure Map
	 * 
	 * @return The Flat IR Delta Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatIRDeltaMeasures()
	{
		return _mapFlatIRDeltaMeasures;
	}

	/**
	 * Set the Flat IR Delta Measures Map
	 * 
	 * @param mapFlatIRDeltaMeasures The Flat IR Delta Measures Map
	 * 
	 * @return TRUE - The Flat IR Delta Measures Map Successfully Set
	 */

	public boolean setFlatIRDeltaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatIRDeltaMeasures)
	{
		if (null == mapFlatIRDeltaMeasures || 0 == mapFlatIRDeltaMeasures.size()) return false;

		_mapFlatIRDeltaMeasures = mapFlatIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat IR Gamma Measure Map
	 * 
	 * @return The Flat IR Gamma Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatIRGammaMeasures()
	{
		return _mapFlatIRGammaMeasures;
	}

	/**
	 * Set the Flat IR Gamma Measures Map
	 * 
	 * @param mapFlatIRGammaMeasures The Flat IR Gamma Measures Map
	 * 
	 * @return TRUE - The Flat IR Gamma Measures Map Successfully Set
	 */

	public boolean setFlatIRGammaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatIRGammaMeasures)
	{
		if (null == mapFlatIRGammaMeasures || 0 == mapFlatIRGammaMeasures.size()) return false;

		_mapFlatIRGammaMeasures = mapFlatIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat RR Delta Measure Map
	 * 
	 * @return The Flat RR Delta Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatRRDeltaMeasures()
	{
		return _mapFlatRRDeltaMeasures;
	}

	/**
	 * Set the Flat RR Delta Measures Map
	 * 
	 * @param mapFlatRRDeltaMeasures The Flat RR Delta Measures Map
	 * 
	 * @return TRUE - The Flat RR Delta Measures Map Successfully Set
	 */

	public boolean setFlatRRDeltaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatRRDeltaMeasures)
	{
		if (null == mapFlatRRDeltaMeasures || 0 == mapFlatRRDeltaMeasures.size()) return false;

		_mapFlatRRDeltaMeasures = mapFlatRRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat RR Gamma Measure Map
	 * 
	 * @return The Flat RR Gamma Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatRRGammaMeasures()
	{
		return _mapFlatRRGammaMeasures;
	}

	/**
	 * Set the Flat RR Gamma Measures Map
	 * 
	 * @param mapFlatRRGammaMeasures The Flat RR Gamma Measures Map
	 * 
	 * @return TRUE - The Flat RR Gamma Measures Map Successfully Set
	 */

	public boolean setFlatRRGammaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatRRGammaMeasures)
	{
		if (null == mapFlatRRGammaMeasures || 0 == mapFlatRRGammaMeasures.size()) return false;

		_mapFlatRRGammaMeasures = mapFlatRRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat Credit Delta Measure Map
	 * 
	 * @return The Flat Credit Delta Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatCreditDeltaMeasures()
	{
		return _mapFlatCreditDeltaMeasures;
	}

	/**
	 * Set the Flat Credit Delta Measures Map
	 * 
	 * @param mapFlatCreditDeltaMeasures The Flat Credit Delta Measures Map
	 * 
	 * @return TRUE - The Flat Credit Delta Measures Map Successfully Set
	 */

	public boolean setFlatCreditDeltaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatCreditDeltaMeasures)
	{
		if (null == mapFlatCreditDeltaMeasures || 0 == mapFlatCreditDeltaMeasures.size()) return false;

		_mapFlatCreditDeltaMeasures = mapFlatCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat Credit Gamma Measure Map
	 * 
	 * @return The Flat Credit Gamma Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatCreditGammaMeasures()
	{
		return _mapFlatCreditGammaMeasures;
	}

	/**
	 * Set the Flat Credit Gamma Measures Map
	 * 
	 * @param mapFlatCreditGammaMeasures The Flat Credit Gamma Measures Map
	 * 
	 * @return TRUE - The Flat Credit Gamma Measures Map Successfully Set
	 */

	public boolean setFlatCreditGammaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatCreditGammaMeasures)
	{
		if (null == mapFlatCreditGammaMeasures || 0 == mapFlatCreditGammaMeasures.size()) return false;

		_mapFlatCreditGammaMeasures = mapFlatCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor IR Delta Double Measure Map
	 * 
	 * @return The Tenor IR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorIRDeltaMeasures()
	{
		return _mmTenorIRDeltaMeasures;
	}

	/**
	 * Set the Tenor IR Delta Double Measures Map
	 * 
	 * @param mmTenorIRDeltaMeasures The Tenor IR Delta Double Measures Map
	 * 
	 * @return TRUE - The Tenor IR Delta Double Measures Map Successfully Set
	 */

	public boolean setTenorIRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorIRDeltaMeasures)
	{
		if (null == mmTenorIRDeltaMeasures || 0 == mmTenorIRDeltaMeasures.size()) return false;

		_mmTenorIRDeltaMeasures = mmTenorIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor IR Gamma Double Measure Map
	 * 
	 * @return The Tenor IR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorIRGammaMeasures()
	{
		return _mmTenorIRGammaMeasures;
	}

	/**
	 * Set the Tenor IR Gamma Double Measures Map
	 * 
	 * @param mmTenorIRGammaMeasures The Tenor IR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Tenor IR Gamma Double Measures Map Successfully Set
	 */

	public boolean setTenorIRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorIRGammaMeasures)
	{
		if (null == mmTenorIRGammaMeasures || 0 == mmTenorIRGammaMeasures.size()) return false;

		_mmTenorIRGammaMeasures = mmTenorIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor Credit Delta Double Measure Map
	 * 
	 * @return The Tenor Credit Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorCreditDeltaMeasures()
	{
		return _mmTenorCreditDeltaMeasures;
	}

	/**
	 * Set the Tenor Credit Delta Double Measures Map
	 * 
	 * @param mmTenorCreditDeltaMeasures The Tenor Credit Delta Double Measures Map
	 * 
	 * @return TRUE - The Tenor Credit Delta Double Measures Map Successfully Set
	 */

	public boolean setTenorCreditDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorCreditDeltaMeasures)
	{
		if (null == mmTenorCreditDeltaMeasures || 0 == mmTenorCreditDeltaMeasures.size()) return false;

		_mmTenorCreditDeltaMeasures = mmTenorCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor Credit Gamma Double Measure Map
	 * 
	 * @return The Tenor Credit Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorCreditGammaMeasures()
	{
		return _mmTenorCreditGammaMeasures;
	}

	/**
	 * Set the Tenor Credit Gamma Double Measures Map
	 * 
	 * @param mmTenorCreditGammaMeasures The Tenor Credit Gamma Double Measures Map
	 * 
	 * @return TRUE - The Tenor Credit Gamma Double Measures Map Successfully Set
	 */

	public boolean setTenorCreditGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorCreditGammaMeasures)
	{
		if (null == mmTenorCreditGammaMeasures || 0 == mmTenorCreditGammaMeasures.size()) return false;

		_mmTenorCreditGammaMeasures = mmTenorCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor RR Delta Double Measure Map
	 * 
	 * @return The Tenor RR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorRRDeltaMeasures()
	{
		return _mmTenorRRDeltaMeasures;
	}

	/**
	 * Set the Tenor RR Delta Double Measures Map
	 * 
	 * @param mmTenorRRDeltaMeasures The Tenor RR Delta Double Measures Map
	 * 
	 * @return TRUE - The Tenor RR Delta Double Measures Map Successfully Set
	 */

	public boolean setTenorRRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorRRDeltaMeasures)
	{
		if (null == mmTenorRRDeltaMeasures || 0 == mmTenorRRDeltaMeasures.size()) return false;

		_mmTenorRRDeltaMeasures = mmTenorRRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor RR Gamma Double Measure Map
	 * 
	 * @return The Tenor RR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorRRGammaMeasures()
	{
		return _mmTenorRRGammaMeasures;
	}

	/**
	 * Set the Tenor RR Gamma Double Measures Map
	 * 
	 * @param mmTenorRRGammaMeasures The Tenor IR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Tenor RR Gamma Double Measures Map Successfully Set
	 */

	public boolean setTenorRRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorRRGammaMeasures)
	{
		if (null == mmTenorRRGammaMeasures || 0 == mmTenorRRGammaMeasures.size()) return false;

		_mmTenorRRGammaMeasures = mmTenorRRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Custom Double Measure Map
	 * 
	 * @return The Custom Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			customMeasures()
	{
		return _mmCustomMeasures;
	}

	/**
	 * Set the Custom Double Measures Map
	 * 
	 * @param mmCustomMeasures The Custom Double Measures Map
	 * 
	 * @return TRUE - The Custom Double Measures Map Successfully Set
	 */

	public boolean setCustomMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmCustomMeasures)
	{
		if (null == mmCustomMeasures || 0 == mmCustomMeasures.size()) return false;

		_mmCustomMeasures = mmCustomMeasures;
		return true;
	}
}
