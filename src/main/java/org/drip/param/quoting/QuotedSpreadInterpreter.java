
package org.drip.param.quoting;

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
 * <i>QuotedSpreadInterpreter</i> holds the fields needed to interpret a Quoted Spread Quote. It contains the
 * contract type and the coupon.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/quoting">Quoting</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class QuotedSpreadInterpreter extends org.drip.param.quoting.MeasureInterpreter {

	/**
	 * SNAC CDS Contract
	 */

	public static final java.lang.String SNAC_CDS = "SNAC";

	/**
	 * Conventional CDS Contract
	 */

	public static final java.lang.String CONV_CDS = "CONV";

	/**
	 * STEM CDS Contract
	 */

	public static final java.lang.String STEM_CDS = "CONV";

	private java.lang.String _strCDSContractType = "";
	private double _dblCouponStrike = java.lang.Double.NaN;

	/**
	 * QuotedSpreadInterpreter constructor
	 * 
	 * @param strCDSContractType The CDS Contract Type
	 * @param dblCouponStrike The Coupon Strike
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public QuotedSpreadInterpreter (
		final java.lang.String strCDSContractType,
		final double dblCouponStrike)
		throws java.lang.Exception
	{
		if (null == (_strCDSContractType = strCDSContractType) || (!CONV_CDS.equalsIgnoreCase
			(_strCDSContractType) && !SNAC_CDS.equalsIgnoreCase (_strCDSContractType) &&
				!STEM_CDS.equalsIgnoreCase (_strCDSContractType)))
			throw new java.lang.Exception ("QuotedSpreadInterpreter ctr: Invalid Inputs");

		_dblCouponStrike = dblCouponStrike;
	}

	/**
	 * Retrieve the CDS Contract Type
	 * 
	 * @return The CDS Contract Type
	 */

	public java.lang.String cdsContractType()
	{
		return _strCDSContractType;
	}

	/**
	 * Retrieve the Coupon Strike
	 * 
	 * @return The Coupon Strike
	 */

	public double couponStrike()
	{
		return _dblCouponStrike;
	}
}
