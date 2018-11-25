
package org.drip.state.volatility;

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
 * <i>VolatilityCurve</i> exposes the Stub that implements the Latent State's Deterministic Volatility Term
 * Structure Curve - by Construction, this is expected to be non-local.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/volatility">Volatility</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class VolatilityCurve extends org.drip.analytics.definition.NodeStructure {

	protected VolatilityCurve (
		final int iEpochDate,
		final org.drip.state.identifier.LatentStateLabel label,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		super (iEpochDate, label, strCurrency);
	}

	/**
	 * Compute the Deterministic Implied Volatility at the Date Node from the Volatility Term Structure
	 * 
	 * @param iDate The Date Node
	 * 
	 * @return The Deterministic Implied Volatility at the Date Node from the Volatility Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Deterministic Implied Volatility cannot be computed
	 */

	public abstract double impliedVol (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Compute the Deterministic Implied Volatility at the Date Node from the Volatility Term Structure
	 * 
	 * @param dt The Date Node
	 * 
	 * @return The Deterministic Implied Volatility at the Date Node from the Volatility Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Deterministic Implied Volatility cannot be computed
	 */

	public double impliedVol (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("VolatilityCurve::impliedVol => Invalid Inputs!");

		return impliedVol (dt.julian());
	}

	/**
	 * Compute the Deterministic Implied Volatility at the Tenor from the Volatility Term Structure
	 * 
	 * @param strTenor The Date Node
	 * 
	 * @return The Deterministic Implied Volatility at the Tenor from the Volatility Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Deterministic Implied Volatility cannot be computed
	 */

	public double impliedVol (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("VolatilityCurve::impliedVol => Invalid Inputs!");

		return impliedVol (epoch().addTenor (strTenor).julian());
	}

	/**
	 * Compute the Deterministic Implied Volatility at the Date Node from the Volatility Term Structure
	 * 
	 * @param iDate The Date Node
	 * 
	 * @return The Deterministic Implied Volatility at the Date Node from the Volatility Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Deterministic Implied Volatility cannot be computed
	 */

	public abstract double vol (
		final int iDate)
		throws java.lang.Exception;
}
