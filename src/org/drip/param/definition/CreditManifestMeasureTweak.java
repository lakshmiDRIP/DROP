
package org.drip.param.definition;


/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * CreditManifestMeasureTweak contains the place holder for the credit curve scenario tweak parameters: in
 *  addition to the ResponseValueTweakParams fields, this exposes the calibration manifest measure, the curve
 *  node, and the nodal calibration type (entire curve/flat or a given tenor point).
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditManifestMeasureTweak extends ManifestMeasureTweak {

	/**
	 * Tweak Parameter Type of Quote
	 */

	public static final java.lang.String CREDIT_TWEAK_NODE_PARAM_QUOTE = "Quote";

	/**
	 * Tweak Parameter Type of Recovery
	 */

	public static final java.lang.String CREDIT_TWEAK_NODE_PARAM_RECOVERY = "Recovery";

	/**
	 * Tweak Measure Type of Quote
	 */

	public static final java.lang.String CREDIT_TWEAK_NODE_MEASURE_QUOTE = "Quote";

	/**
	 * Tweak Measure Type of Hazard
	 */

	public static final java.lang.String CREDIT_TWEAK_NODE_MEASURE_HAZARD = "Hazard";

	private boolean _bSingleNodeCalib = false;
	private java.lang.String _strParamType = "";
	private java.lang.String _strMeasureType = "";

	/**
	 * CreditManifestMeasureTweak constructor
	 * 
	 * @param strParamType Node Tweak Parameter Type
	 * @param strMeasureType Node Tweak Measure Type
	 * @param iNode Node to be tweaked - Set to NODE_FLAT_TWEAK for flat curve tweak
	 * @param bIsProportional True - Tweak is proportional, False - parallel
	 * @param dblAmount Amount to be tweaked - proportional tweaks are represented as percent, parallel
	 * 			tweaks are absolute numbers
	 * @param bSingleNodeCalib Flat Calibration using a single node?
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CreditManifestMeasureTweak (
		final java.lang.String strParamType,
		final java.lang.String strMeasureType,
		final int iNode,
		final boolean bIsProportional,
		final double dblAmount,
		final boolean bSingleNodeCalib)
		throws java.lang.Exception
	{
		super (iNode, bIsProportional, dblAmount);

		if (null == (_strParamType = strParamType))
			throw new java.lang.Exception
				("CreditManifestMeasureTweak ctr => Invalid Tweak Parameter Type!");

		if (null == (_strMeasureType = strMeasureType))
			throw new java.lang.Exception ("CreditManifestMeasureTweak ctr => Invalid Tweak Measure Type!");

		_bSingleNodeCalib = bSingleNodeCalib;
	}

	/**
	 * Single Node Calibration Flag
	 * 
	 * @return TRUE - Turn on Single Node Calibration
	 */

	public boolean singleNodeCalib()
	{
		return _bSingleNodeCalib;
	}

	/**
	 * Retrieve the Tweak Parameter Type
	 * 
	 * @return The Tweak Parameter Type
	 */

	public java.lang.String paramType()
	{
		return _strParamType;
	}

	/**
	 * Retrieve the Tweak Measure Type
	 * 
	 * @return The Tweak Measure Type
	 */

	public java.lang.String measureType()
	{
		return _strMeasureType;
	}
}
