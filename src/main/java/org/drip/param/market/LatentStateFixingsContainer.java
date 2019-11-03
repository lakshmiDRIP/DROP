
package org.drip.param.market;

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
 * <i>LatentStateFixingsContainer</i> holds the explicit fixings for a specified Latent State Quantification
 * along the date ordinate.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/market/README.md">Curves Surfaces Quotes Fixings Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateFixingsContainer {
	private java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> _mmForwardFixing = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

	private java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> _mmOTCFixFloatFixing = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

	private java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> _mmFXFixing = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

	/**
	 * Empty LatentStateFixingsContainer Instance Constructor
	 */

	public LatentStateFixingsContainer()
	{
	}

	/**
	 * Add the Fixing corresponding to the Date/Label Pair
	 * 
	 * @param dt The Fixing Date
	 * @param lsl The Latent State Label
	 * @param dblFixing The Fixing Amount
	 * 
	 * @return TRUE - Entry successfully added
	 */

	public boolean add (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		if (null == dt || null == lsl || !org.drip.numerical.common.NumberUtil.IsValid (dblFixing)) return false;

		if (lsl instanceof org.drip.state.identifier.OTCFixFloatLabel) {
			if (!_mmOTCFixFloatFixing.containsKey (dt))
				_mmOTCFixFloatFixing.put (dt, new
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>());

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOTCFixFloatFixing =
				_mmOTCFixFloatFixing.get (dt);

			mapOTCFixFloatFixing.put (lsl.fullyQualifiedName(), dblFixing);

			return true;
		}

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt))
				_mmForwardFixing.put (dt, new
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>());

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapForwardFixing =
				_mmForwardFixing.get (dt);

			mapForwardFixing.put (lsl.fullyQualifiedName(), dblFixing);

			return true;
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt))
				_mmFXFixing.put (dt, new
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>());

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFXFixing = _mmFXFixing.get
				(dt);

			mapFXFixing.put (lsl.fullyQualifiedName(), dblFixing);

			return true;
		}

		return false;
	}

	/**
	 * Add the Latent State Fixing corresponding to the Date/Label Pair
	 * 
	 * @param iDate The Fixing Date
	 * @param lsl The Latent State Fixing Label
	 * @param dblFixing The Fixing Amount
	 * 
	 * @return TRUE - Entry successfully added
	 */

	public boolean add (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		return add (new org.drip.analytics.date.JulianDate (iDate), lsl, dblFixing);
	}

	/**
	 * Remove the Latent State Fixing corresponding to the Date/Label Pair it if exists
	 * 
	 * @param dt The Fixing Date
	 * @param lsl The Latent State Fixing Label
	 * 
	 * @return TRUE - Entry successfully removed if it existed
	 */

	public boolean remove (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		if (null == dt || null == lsl) return false;

		if (lsl instanceof org.drip.state.identifier.OTCFixFloatLabel) {
			if (!_mmOTCFixFloatFixing.containsKey (dt)) return true;

			_mmOTCFixFloatFixing.get (dt).remove (lsl.fullyQualifiedName());

			return true;
		}

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt)) return true;

			_mmForwardFixing.get (dt).remove (lsl.fullyQualifiedName());

			return true;
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt)) return true;

			_mmFXFixing.get (dt).remove (lsl.fullyQualifiedName());

			return true;
		}

		return false;
	}

	/**
	 * Remove the Latent State Fixing corresponding to the Date/Label Pair it if exists
	 * 
	 * @param iDate The Fixing Date
	 * @param lsl The Latent State Fixing Label
	 * 
	 * @return TRUE - Entry successfully removed if it existed
	 */

	public boolean remove (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return remove (new org.drip.analytics.date.JulianDate (iDate), lsl);
	}

	/**
	 * Retrieve the Latent State Fixing for the Specified Date/LSL Combination
	 * 
	 * @param dt Date
	 * @param lsl The Latent State Latent State Label
	 * 
	 * @return The Latent State Fixing for the Specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Fixing cannot be found
	 */

	public double fixing (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		if (null == dt || null == lsl)
			throw new java.lang.Exception
				("LatentStateFixingsContainer::fixing => Cannot locate Latent State Fixing for the Date");

		if (lsl instanceof org.drip.state.identifier.OTCFixFloatLabel) {
			if (!_mmOTCFixFloatFixing.containsKey (dt))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate OTC Fix/Float Fixing for the Date");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOTCFixFloatFixing =
				_mmOTCFixFloatFixing.get (dt);

			java.lang.String strLabel = lsl.fullyQualifiedName();

			if (!mapOTCFixFloatFixing.containsKey (strLabel))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate the OTC Fix/Float Label Entry for the Date!");

			return mapOTCFixFloatFixing.get (strLabel);
		}

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate Forward Fixing for the Date");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapForwardFixing =
				_mmForwardFixing.get (dt);

			java.lang.String strLabel = lsl.fullyQualifiedName();

			if (!mapForwardFixing.containsKey (strLabel))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate the Forward Label Entry for the Date!");

			return mapForwardFixing.get (strLabel);
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate FX Fixing for the Date");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFXFixing = _mmFXFixing.get
				(dt);

			java.lang.String strLabel = lsl.fullyQualifiedName();

			if (!mapFXFixing.containsKey (strLabel))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate the FX Label Entry for the Date!");

			return mapFXFixing.get (strLabel);
		}

		throw new java.lang.Exception
			("LatentStateFixingsContainer::fixing => No Fixings available for the Latent State");
	}

	/**
	 * Retrieve the Latent State Fixing for the Specified Date
	 * 
	 * @param iDate Date
	 * @param lsl The Latent State Label
	 * 
	 * @return The Fixing for the Specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Fixing cannot be found
	 */

	public double fixing (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		return fixing (new org.drip.analytics.date.JulianDate (iDate), lsl);
	}

	/**
	 * Indicate the Availability of the Fixing for the Specified LSL Label on the specified Date
	 * 
	 * @param dt The Date
	 * @param lsl The Label
	 * 
	 * @return TRUE - The Fixing for the Specified LSL Label on the specified Date 
	 */

	public boolean available (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		if (null == dt || null == lsl) return false;

		if (lsl instanceof org.drip.state.identifier.OTCFixFloatLabel) {
			if (!_mmOTCFixFloatFixing.containsKey (dt)) return false;

			return _mmOTCFixFloatFixing.get (dt).containsKey (lsl.fullyQualifiedName());
		}

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt)) return false;

			return _mmForwardFixing.get (dt).containsKey (lsl.fullyQualifiedName());
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt)) return false;

			return _mmFXFixing.get (dt).containsKey (lsl.fullyQualifiedName());
		}

		return false;
	}

	/**
	 * Indicate the Availability of the Fixing for the Specified LSL on the specified Date
	 * 
	 * @param iDate The Date
	 * @param lsl The Label
	 * 
	 * @return TRUE - The Fixing for the Specified LSL on the specified Date 
	 */

	public boolean available (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return available (new org.drip.analytics.date.JulianDate (iDate), lsl);
	}
}
