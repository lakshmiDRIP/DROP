
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * ValueCategory holds the Fields relevant to Classifying Value Attribution from an Accounting ViewPoint. The
 *  References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955, eSSRN.
 *  
 *  - BCBS (2012): Consultative Document: Application of Own Credit Risk Adjustments to Derivatives, Basel
 *  	Committee on Banking Supervision.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ValueCategory {
	private java.lang.String _strID = "";
	private boolean _bCET1Contributor = false;
	private java.lang.String _strDescription = "";

	/**
	 * Retrieve an Instance of the CF1 Cash Flow
	 * 
	 * @return An Instance of the CF1 Cash Flow
	 */

	public static final ValueCategory CF1()
	{
		try {
			return new ValueCategory ("CF1", "Underlying Trade Contractual Cash Flow", true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve an Instance of the CF2 Cash Flow
	 * 
	 * @return An Instance of the CF2 Cash Flow
	 */

	public static final ValueCategory CF2()
	{
		try {
			return new ValueCategory ("CF2", "Counter Party Default Cash Flow", true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve an Instance of the CF3 Cash Flow
	 * 
	 * @return An Instance of the CF3 Cash Flow
	 */

	public static final ValueCategory CF3()
	{
		try {
			return new ValueCategory ("CF3", "Bank Default Related Cash Flow", false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve an Instance of the CF4 Cash Flow
	 * 
	 * @return An Instance of the CF4 Cash Flow
	 */

	public static final ValueCategory CF4()
	{
		try {
			return new ValueCategory ("CF4", "Pre Bank Default Dynamic Flow", false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve an Instance of the CF5 Cash Flow
	 * 
	 * @return An Instance of the CF5 Cash Flow
	 */

	public static final ValueCategory CF5()
	{
		try {
			return new ValueCategory ("CF5", "Post Bank Default Dynamic Flow", false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve an Instance of the CF6 Cash Flow
	 * 
	 * @return An Instance of the CF6 Cash Flow
	 */

	public static final ValueCategory CF6()
	{
		try {
			return new ValueCategory ("CF6", "CSA Related Cash Flow", false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve an Instance of the HYBRID Cash Flow
	 * 
	 * @return An Instance of the HYBRID Cash Flow
	 */

	public static final ValueCategory HYBRID()
	{
		try {
			return new ValueCategory ("HYBRID", "Mixed Cash Flow Types", false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ValueCategory Constructor
	 * 
	 * @param strID The Category ID
	 * @param strDescription The Category Description
	 * @param bCET1Contributor TRUE - The Category is a CET1 Contributor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ValueCategory (
		final java.lang.String strID,
		final java.lang.String strDescription,
		final boolean bCET1Contributor)
		throws java.lang.Exception
	{
		if (null == (_strID = strID) || _strID.isEmpty() || null == (_strDescription = strDescription) ||
			_strDescription.isEmpty())
			throw new java.lang.Exception ("ValueCategory Constructor => Invalid Inputs");

		_bCET1Contributor = bCET1Contributor;
	}

	/**
	 * Retrieve the Category ID
	 * 
	 * @return The Category ID
	 */

	public java.lang.String id()
	{
		return _strID;
	}

	/**
	 * Retrieve the Category Description
	 * 
	 * @return The Category Description
	 */

	public java.lang.String description()
	{
		return _strDescription;
	}

	/**
	 * Indicator if the Category is a CET1 Contributor
	 * 
	 * @return TRUE - The Category is a CET1 Contributor
	 */

	public boolean isCET1Contributor()
	{
		return _bCET1Contributor;
	}
}
