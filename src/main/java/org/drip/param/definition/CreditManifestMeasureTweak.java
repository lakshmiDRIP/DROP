
package org.drip.param.definition;


/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>CreditManifestMeasureTweak</i> contains the place holder for the credit curve scenario tweak
 * parameters: in addition to the ResponseValueTweakParams fields, this exposes the calibration manifest
 * measure, the curve node, and the nodal calibration type (entire curve/flat or a given tenor point).
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition">Definition</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
