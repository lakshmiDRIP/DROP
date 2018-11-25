
package org.drip.state.sequence;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>PathGovvie</i> exposes the Functionality to generate a Sequence of Govvie Curve Realizations across
 * Multiple Paths.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence">Sequence</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathGovvie extends org.drip.state.sequence.PathRd {
	private org.drip.state.sequence.GovvieBuilderSettings _gbs = null;

	/**
	 * PathGovvie Constructor
	 * 
	 * @param gbs Govvie Builder Settings Instance
	 * @param dblVolatility Volatility
	 * @param bLogNormal TRUE - The Generated Random Numbers are Log Normal
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathGovvie (
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final double dblVolatility,
		final boolean bLogNormal)
		throws java.lang.Exception
	{
		super (gbs.groundForwardYield(), dblVolatility, bLogNormal);

		if (null == (_gbs = gbs)) throw new java.lang.Exception ("PathGovvie Constructor => Invalid Inputs");
	}

	/**
	 * Generate the Govvie Builder Settings Instance
	 * 
	 * @return The Govvie Builder Settings Instance
	 */

	public org.drip.state.sequence.GovvieBuilderSettings govvieBuilderSettings()
	{
		return _gbs;
	}

	/**
	 * Generate the R^d Path Govvie Curves using the Initial R^d and the Evolution Time Width
	 * 
	 * @param iNumPath Number of Paths
	 * 
	 * @return The R^d Path//Vertex Govvie Curves
	 */

	public org.drip.state.govvie.GovvieCurve[] curveSequence (
		final int iNumPath)
	{
		java.lang.String strCurrency = _gbs.groundState().currency();

		org.drip.analytics.date.JulianDate dtSpot = _gbs.spot();

		double[][] aadblPathSequence = sequence (iNumPath);

		java.lang.String strTreasuryCode = _gbs.code();

		java.lang.String[] astrTenor = _gbs.tenors();

		if (null == aadblPathSequence) return null;

		int iEpochDate = dtSpot.julian();

		int iNumTenor = astrTenor.length;
		int[] aiDate = new int[iNumTenor];
		org.drip.state.nonlinear.FlatForwardGovvieCurve[] aFFGC = new
			org.drip.state.nonlinear.FlatForwardGovvieCurve[iNumPath];

		for (int iTenor = 0; iTenor < iNumTenor; ++iTenor) {
			org.drip.analytics.date.JulianDate dtTenor = dtSpot.addTenor (astrTenor[iTenor]);

			if (null == dtTenor) return null;

			aiDate[iTenor] = dtTenor.julian();
		}

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			try {
				if (null == (aFFGC[iPath] = new org.drip.state.nonlinear.FlatForwardGovvieCurve (iEpochDate,
					strTreasuryCode, strCurrency, aiDate, aadblPathSequence[iPath])))
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aFFGC;
	}
}
