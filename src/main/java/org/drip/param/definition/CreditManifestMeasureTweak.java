
package org.drip.param.definition;

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
 * <i>CreditManifestMeasureTweak</i> contains the place holder for the credit curve scenario tweak
 * parameters: in addition to the ResponseValueTweakParams fields, this exposes the calibration manifest
 * measure, the curve node, and the nodal calibration type (entire curve/flat or a given tenor point).
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition/README.md">Latent State Quantification Metrics Tweak</a></li>
 *  </ul>
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
