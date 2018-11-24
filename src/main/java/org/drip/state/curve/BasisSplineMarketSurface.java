
package org.drip.state.curve;

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
 * <i>BasisSplineMarketSurface</i> implements the Market surface that holds the latent state Dynamics
 * parameters.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve">Curve</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisSplineMarketSurface extends org.drip.analytics.definition.MarketSurface {
	private org.drip.spline.multidimensional.WireSurfaceStretch _wss = null;

	/**
	 * BasisSplineMarketSurface Constructor
	 * 
	 * @param iEpochDate The Starting Date
	 * @param label The Spline Market Surface Latent State Label
	 * @param strCurrency The Currency
	 * @param wss Wire Surface Stretch Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BasisSplineMarketSurface (
		final int iEpochDate,
		final org.drip.state.identifier.CustomLabel label,
		final java.lang.String strCurrency,
		final org.drip.spline.multidimensional.WireSurfaceStretch wss)
		throws java.lang.Exception
	{
		super (iEpochDate, label, strCurrency);

		_wss = wss;
	}

	@Override public double node (
		final double dblStrike,
		final double dblDate)
		throws java.lang.Exception
	{
		return _wss.responseValue (dblStrike, dblDate);
	}

	@Override public org.drip.analytics.definition.NodeStructure xAnchorTermStructure (
		final double dblStrikeAnchor)
	{
		try {
			return new BasisSplineTermStructure (epoch().julian(),
				org.drip.state.identifier.CustomLabel.Standard (label() + "_" + dblStrikeAnchor),
					currency(), _wss.wireSpanXAnchor (dblStrikeAnchor));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.definition.NodeStructure yAnchorTermStructure (
		final double dblMaturityDateAnchor)
	{
		try {
			return new BasisSplineTermStructure (epoch().julian(),
				org.drip.state.identifier.CustomLabel.Standard (label() + "_" + new
					org.drip.analytics.date.JulianDate ((int) dblMaturityDateAnchor)), currency(),
						_wss.wireSpanYAnchor (dblMaturityDateAnchor));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
