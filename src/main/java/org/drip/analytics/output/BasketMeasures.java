
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>BasketMeasures</i> is the place holder for the analytical basket measures, optionally across scenarios.
 * It contains the following scenario measure maps:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			Unadjusted Base Measures
 * 		</li>
 *		<li>
 *			Flat delta/gamma bump measure maps for IR/credit/RR bump curves
 *		</li>
 *		<li>
 *			Component/tenor bump double maps for IR/credit/RR curves
 *		</li>
 *		<li>
 *			Flat/component recovery bumped measure maps for recovery bumped credit curves
 *		</li>
 *		<li>
 *			Custom scenario measure map
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

public class BasketMeasures extends org.drip.analytics.output.ComponentMeasures {
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentIRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentIRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentCreditDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentCreditGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentRRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentRRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorIRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorIRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorCreditDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorCreditGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentCustomMeasures = null;

	/**
	 * Empty constructor - all members initialized to NaN or null
	 */

	public BasketMeasures()
	{
	}

	/**
	 * Retrieve the Component IR Delta Double Measure Map
	 * 
	 * @return The Component IR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentIRDeltaMeasures()
	{
		return _mmComponentIRDeltaMeasures;
	}

	/**
	 * Set the Component IR Delta Double Measures Map
	 * 
	 * @param mmComponentIRDeltaMeasures The Component IR Delta Double Measures Map
	 * 
	 * @return TRUE - The Component IR Delta Double Measures Map Successfully Set
	 */

	public boolean setComponentIRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentIRDeltaMeasures)
	{
		if (null == mmComponentIRDeltaMeasures || 0 == mmComponentIRDeltaMeasures.size()) return false;

		_mmComponentIRDeltaMeasures = mmComponentIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component IR Gamma Double Measure Map
	 * 
	 * @return The Component IR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentIRGammaMeasures()
	{
		return _mmComponentIRGammaMeasures;
	}

	/**
	 * Set the Component IR Gamma Double Measures Map
	 * 
	 * @param mmComponentIRGammaMeasures The Component IR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Component IR Gamma Double Measures Map Successfully Set
	 */

	public boolean setComponentIRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentIRGammaMeasures)
	{
		if (null == mmComponentIRGammaMeasures || 0 == mmComponentIRGammaMeasures.size()) return false;

		_mmComponentIRGammaMeasures = mmComponentIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component Credit Delta Double Measure Map
	 * 
	 * @return The ComponentCredit Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentCreditDeltaMeasures()
	{
		return _mmComponentCreditDeltaMeasures;
	}

	/**
	 * Set the Component Credit Delta Double Measures Map
	 * 
	 * @param mmComponentCreditDeltaMeasures The Component Credit Delta Double Measures Map
	 * 
	 * @return TRUE - The Component Credit Delta Double Measures Map Successfully Set
	 */

	public boolean setComponentCreditDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentCreditDeltaMeasures)
	{
		if (null == mmComponentCreditDeltaMeasures || 0 == mmComponentCreditDeltaMeasures.size())
			return false;

		_mmComponentCreditDeltaMeasures = mmComponentCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component Credit Gamma Double Measure Map
	 * 
	 * @return The Component Credit Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentCreditGammaMeasures()
	{
		return _mmComponentCreditGammaMeasures;
	}

	/**
	 * Set the Component Credit Gamma Double Measures Map
	 * 
	 * @param mmComponentCreditGammaMeasures The Component Credit Gamma Double Measures Map
	 * 
	 * @return TRUE - The Component Credit Gamma Double Measures Map Successfully Set
	 */

	public boolean setComponentCreditGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentCreditGammaMeasures)
	{
		if (null == mmComponentCreditGammaMeasures || 0 == mmComponentCreditGammaMeasures.size()) return false;

		_mmComponentCreditGammaMeasures = mmComponentCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component RR Delta Double Measure Map
	 * 
	 * @return The Component RR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentRRDeltaMeasures()
	{
		return _mmComponentRRDeltaMeasures;
	}

	/**
	 * Set the Component RR Delta Double Measures Map
	 * 
	 * @param mmComponentRRDeltaMeasures The RR Delta Double Measures Map
	 * 
	 * @return TRUE - The Component RR Delta Double Measures Map Successfully Set
	 */

	public boolean setComponentRRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentRRDeltaMeasures)
	{
		if (null == mmComponentRRDeltaMeasures || 0 == mmComponentRRDeltaMeasures.size()) return false;

		_mmComponentRRDeltaMeasures = mmComponentRRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component RR Gamma Double Measure Map
	 * 
	 * @return The Component RR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentRRGammaMeasures()
	{
		return _mmComponentRRGammaMeasures;
	}

	/**
	 * Set the Component RR Gamma Double Measures Map
	 * 
	 * @param mmComponentRRGammaMeasures The RR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Component RR Gamma Double Measures Map Successfully Set
	 */

	public boolean setComponentRRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentRRGammaMeasures)
	{
		if (null == mmComponentRRGammaMeasures || 0 == mmComponentRRGammaMeasures.size()) return false;

		_mmComponentRRGammaMeasures = mmComponentRRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor IR Delta Triple Measure Map
	 * 
	 * @return The Component/Tenor IR Delta Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorIRDeltaMeasures()
	{
		return _mmmComponentTenorIRDeltaMeasures;
	}

	/**
	 * Set the Component/Tenor IR Delta Triple Measures Map
	 * 
	 * @param mmmComponentTenorIRDeltaMeasures The Component/Tenor IR Delta Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor IR Delta Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorIRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorIRDeltaMeasures)
	{
		if (null == mmmComponentTenorIRDeltaMeasures || 0 == mmmComponentTenorIRDeltaMeasures.size())
			return false;

		_mmmComponentTenorIRDeltaMeasures = mmmComponentTenorIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor IR Gamma Triple Measure Map
	 * 
	 * @return The Component/Tenor IR Gamma Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorIRGammaMeasures()
	{
		return _mmmComponentTenorIRGammaMeasures;
	}

	/**
	 * Set the Component/Tenor IR Gamma Triple Measures Map
	 * 
	 * @param mmmComponentTenorIRGammaMeasures The Component/Tenor IR Gamma Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor IR Gamma Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorIRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorIRGammaMeasures)
	{
		if (null == mmmComponentTenorIRGammaMeasures || 0 == mmmComponentTenorIRGammaMeasures.size())
			return false;

		_mmmComponentTenorIRGammaMeasures = mmmComponentTenorIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor Credit Delta Triple Measure Map
	 * 
	 * @return The Component/Tenor Credit Delta Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorCreditDeltaMeasures()
	{
		return _mmmComponentTenorCreditDeltaMeasures;
	}

	/**
	 * Set the Component/Tenor Credit Delta Triple Measures Map
	 * 
	 * @param mmmComponentTenorCreditDeltaMeasures The Component/Tenor Credit Delta Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor Credit Delta Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorCreditDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorCreditDeltaMeasures)
	{
		if (null == mmmComponentTenorCreditDeltaMeasures || 0 == mmmComponentTenorCreditDeltaMeasures.size())
			return false;

		_mmmComponentTenorCreditDeltaMeasures = mmmComponentTenorCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor Credit Gamma Triple Measure Map
	 * 
	 * @return The Component/Tenor Credit Gamma Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorCreditGammaMeasures()
	{
		return _mmmComponentTenorCreditGammaMeasures;
	}

	/**
	 * Set the Component/Tenor Credit Gamma Triple Measures Map
	 * 
	 * @param mmmComponentTenorCreditGammaMeasures The Component/Tenor Credit Gamma Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor Credit Gamma Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorCreditGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorCreditGammaMeasures)
	{
		if (null == mmmComponentTenorCreditGammaMeasures || 0 == mmmComponentTenorCreditGammaMeasures.size())
			return false;

		_mmmComponentTenorCreditGammaMeasures = mmmComponentTenorCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component Custom Double Measure Map
	 * 
	 * @return The Component Custom Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentCustomMeasures()
	{
		return _mmComponentCustomMeasures;
	}

	/**
	 * Set the Component Custom Double Measures Map
	 * 
	 * @param mmComponentCustomMeasures The Component Custom Double Measures Map
	 * 
	 * @return TRUE - The Component Custom Double Measures Map Successfully Set
	 */

	public boolean setComponentCustomMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentCustomMeasures)
	{
		if (null == mmComponentCustomMeasures || 0 == mmComponentCustomMeasures.size()) return false;

		_mmComponentCustomMeasures = mmComponentCustomMeasures;
		return true;
	}
}
