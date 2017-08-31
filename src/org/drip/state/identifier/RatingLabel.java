
package org.drip.state.identifier;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * RatingLabel contains the Identifier Parameters referencing the Label corresponding to the Credit Rating
 * 	Latent State. Currently it holds the Ratings Agency Name and the Rated Code.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class RatingLabel implements org.drip.state.identifier.LatentStateLabel {
	private java.lang.String _strCode = "";
	private java.lang.String _strAgency = "";

	/**
	 * Make a Standard Rating Label from the Rating Agency and the Rated Code.
	 * 
	 * @param strAgency The Rating Agency
	 * @param strCode The Rated Code
	 * 
	 * @return The Rating Label
	 */

	public static final RatingLabel Standard (
		final java.lang.String strAgency,
		final java.lang.String strCode)
	{
		try {
			return new RatingLabel (strAgency, strCode);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RatingsLabel constructor
	 * 
	 * @param strAgency The Ratings Agency
	 * @param strCode The Rated Code
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public RatingLabel (
		final java.lang.String strAgency,
		final java.lang.String strCode)
		throws java.lang.Exception
	{
		if (null == (_strAgency = strAgency) || _strAgency.isEmpty() || null == (_strCode = strCode) ||
			_strCode.isEmpty())
			throw new java.lang.Exception ("RatingLabel ctr: Invalid Inputs");
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _strAgency + "::" + _strCode;
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		if (null == lslOther || !(lslOther instanceof org.drip.state.identifier.RatingLabel)) return false;

		org.drip.state.identifier.RatingLabel rlOther = (org.drip.state.identifier.RatingLabel) lslOther;

		return _strAgency.equalsIgnoreCase (rlOther.agency()) && _strCode.equalsIgnoreCase (rlOther.code());
	}

	/**
	 * Retrieve the Ratings Agency
	 * 
	 * @return The Ratings Agency
	 */

	public java.lang.String agency()
	{
		return _strAgency;
	}

	/**
	 * Retrieve the Rated Code
	 * 
	 * @return The Rated Code
	 */

	public java.lang.String code()
	{
		return _strCode;
	}
}
