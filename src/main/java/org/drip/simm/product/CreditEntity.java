
package org.drip.simm.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * CreditEntity holds the SIMM specific Details of a Credit Entity. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditEntity
{
	private java.lang.String _id = "";
	private java.lang.String _name = "";
	private java.lang.String _tenor = "";
	private java.lang.String _family = "";
	private int _bucketIndex = java.lang.Integer.MIN_VALUE;

	/**
	 * CreditEntity Constructor
	 * 
	 * @param id The Credit Entity ID
	 * @param name The Credit Entity Name
	 * @param family The Credit Entity Family
	 * @param tenor The Credit Entity Exposure Tenor
	 * @param bucketIndex The Credit Entity Bucket Index
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CreditEntity (
		final java.lang.String id,
		final java.lang.String name,
		final java.lang.String family,
		final java.lang.String tenor,
		final int bucketIndex)
		throws java.lang.Exception
	{
		if (null == (_id = id) || _id.isEmpty() ||
			null == (_name = name) || _name.isEmpty() ||
			null == (_family = family) || _family.isEmpty() ||
			null == (_tenor = tenor) || _tenor.isEmpty())
		{
			throw new java.lang.Exception ("CreditEntity Constructor => Invalid Inputs");
		}

		_bucketIndex = bucketIndex;
	}

	/**
	 * Retrieve the Credit Entity ID
	 * 
	 * @return The Credit Entity ID
	 */

	public java.lang.String id()
	{
		return _id;
	}

	/**
	 * Retrieve the Credit Entity Name
	 * 
	 * @return The Credit Entity Name
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Credit Entity Family
	 * 
	 * @return The Credit Entity Family
	 */

	public java.lang.String family()
	{
		return _family;
	}

	/**
	 * Retrieve the Credit Entity Tenor
	 * 
	 * @return The Credit Entity Tenor
	 */

	public java.lang.String tenor()
	{
		return _tenor;
	}

	/**
	 * Retrieve the Credit Entity Bucket Index
	 * 
	 * @return The Credit Entity Bucket Index
	 */

	public int bucketIndex()
	{
		return _bucketIndex;
	}
}
