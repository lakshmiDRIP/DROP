
package org.drip.portfolioconstruction.params;

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
 * AssetStatisticalProperties holds the Statistical Properties of a given Asset.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetStatisticalProperties {
	private java.lang.String _strID = "";
	private java.lang.String _strName = "";
	private double _dblVariance = java.lang.Double.NaN;
	private double _dblExpectedReturn = java.lang.Double.NaN;

	/**
	 * AssetStatisticalProperties Constructor
	 * 
	 * @param strName Name of the Asset
	 * @param strID ID of the Asset
	 * @param dblExpectedReturn Expected Return of the Asset
	 * @param dblVariance Variance of the Assert
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetStatisticalProperties (
		final java.lang.String strName,
		final java.lang.String strID,
		final double dblExpectedReturn,
		final double dblVariance)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_strID = strID) ||
			_strID.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (_dblExpectedReturn =
				dblExpectedReturn) || !org.drip.quant.common.NumberUtil.IsValid (_dblVariance = dblVariance))
			throw new java.lang.Exception ("AssetStatisticalProperties Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Name of the Asset
	 * 
	 * @return Name of the Asset
	 */

	public java.lang.String name()
	{
		return _strName;
	}

	/**
	 * Retrieve the ID of the Asset
	 * 
	 * @return ID of the Asset
	 */

	public java.lang.String id()
	{
		return _strID;
	}

	/**
	 * Retrieve the Expected Returns of the Asset
	 * 
	 * @return Expected Returns of the Asset
	 */

	public double expectedReturn()
	{
		return _dblExpectedReturn;
	}

	/**
	 * Retrieve the Variance of the Asset
	 * 
	 * @return Variance of the Asset
	 */

	public double variance()
	{
		return _dblVariance;
	}
}
