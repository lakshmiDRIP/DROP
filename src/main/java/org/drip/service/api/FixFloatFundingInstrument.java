
package org.drip.service.api;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>FixFloatFundingInstrument</i> contains the Fix Float Instrument Inputs for the Funding Curve
 * Construction Purposes.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api">API</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatFundingInstrument {
	private int _iLatentStateType = -1;
	private double[] _adblQuote = null;
	private java.lang.String _strCurrency = "";
	private java.lang.String[] _astrMaturityTenor = null;
	private org.drip.state.discount.DiscountCurve _dc = null;
	private org.drip.analytics.date.JulianDate _dtSpot = null;

	/**
	 * FixFloatFundingInstrument Constructor
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrMaturityTenor Array of the Maturity Tenors
	 * @param adblQuote Array of Quotes
	 * @param iLatentStateType Latent State Type
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FixFloatFundingInstrument (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblQuote,
		final int iLatentStateType)
		throws java.lang.Exception
	{
		if (null == (_dc = org.drip.service.template.LatentMarketStateBuilder.FundingCurve (_dtSpot = dtSpot,
			_strCurrency = strCurrency, null, null, "ForwardRate", null, "ForwardRate", _astrMaturityTenor =
				astrMaturityTenor, _adblQuote = adblQuote, "SwapRate", _iLatentStateType =
					iLatentStateType)))
			throw new java.lang.Exception ("FixFloatFundingInstrument Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public org.drip.analytics.date.JulianDate spotDate()
	{
		return _dtSpot;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Latent State Type
	 * 
	 * @return The Latent State Type
	 */

	public int latentStateType()
	{
		return _iLatentStateType;
	}

	/**
	 * Retrieve the Array of the Maturity Tenors
	 * 
	 * @return Array of Maturity Tenors
	 */

	public java.lang.String[] maturityTenors()
	{
		return _astrMaturityTenor;
	}

	/**
	 * Retrieve the Array of Quotes
	 * 
	 * @return Array of Quotes
	 */

	public double[] quotes()
	{
		return _adblQuote;
	}

	/**
	 * Retrieve the Funding State
	 * 
	 * @return The Funding State Instance
	 */

	public org.drip.state.discount.DiscountCurve fundingState()
	{
		return _dc;
	}
}
