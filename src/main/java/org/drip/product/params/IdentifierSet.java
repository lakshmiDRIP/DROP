
package org.drip.product.params;

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
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>IdentifierSet</i> contains the component identifier parameters - ISIN, CUSIP, ID, and ticker. It
 * exports serialization into and de-serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params">Params</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IdentifierSet implements org.drip.product.params.Validatable {
	private java.lang.String _strID = "";
	private java.lang.String _strISIN = "";
	private java.lang.String _strCUSIP = "";
	private java.lang.String _strTicker = "";

	/**
	 * Construct the IdentifierSet from ISIN, CUSIP, ID, and ticker.
	 * 
	 * @param strISIN ISIN
	 * @param strCUSIP CUSIP
	 * @param strID component ID
	 * @param strTicker Ticker
	 */

	public IdentifierSet (
		final java.lang.String strISIN,
		final java.lang.String strCUSIP,
		final java.lang.String strID,
		final java.lang.String strTicker)
	{
		_strISIN = strISIN;
		_strCUSIP = strCUSIP;
		_strID = strID;
		_strTicker = strTicker;
	}

	@Override public boolean validate()
	{
		if ((null == _strISIN || _strISIN.isEmpty()) && (null == _strCUSIP || _strCUSIP.isEmpty()))
			return false;

		if (null == _strID || _strID.isEmpty()) {
			if (null == (_strID = _strISIN) || _strID.isEmpty()) _strID = _strCUSIP;
		}

		return true;
	}

	/**
	 * Retrieve the ID
	 * 
	 * @return The ID
	 */

	public java.lang.String id()
	{
		return _strID;
	}

	/**
	 * Retrieve the ISIN
	 * 
	 * @return The ISIN
	 */

	public java.lang.String isin()
	{
		return _strISIN;
	}

	/**
	 * Retrieve the CUSIP
	 * 
	 * @return The CUSIP
	 */

	public java.lang.String cusip()
	{
		return _strCUSIP;
	}

	/**
	 * Retrieve the Ticker
	 * 
	 * @return The Ticker
	 */

	public java.lang.String ticker()
	{
		return _strTicker;
	}
}
