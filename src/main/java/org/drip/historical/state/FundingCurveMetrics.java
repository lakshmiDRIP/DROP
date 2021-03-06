
package org.drip.historical.state;

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
 * <i>FundingCurveMetrics</i> holds the computed Metrics associated the Funding Curve State.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical State Processing Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/state/README.md">Historical Implied Curve Node Metrics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FundingCurveMetrics {
	private org.drip.analytics.date.JulianDate _dtClose = null;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>>
			_mapInForNativeLIBOR = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>>();

	/**
	 * FundingCurveMetrics Constructor
	 * 
	 * @param dtClose The Closing Date
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public FundingCurveMetrics (
		final org.drip.analytics.date.JulianDate dtClose)
		throws java.lang.Exception
	{
		if (null == (_dtClose = dtClose))
			throw new java.lang.Exception ("FundingCurveMetrics Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Closing Date
	 * 
	 * @return The Closing Date
	 */

	public org.drip.analytics.date.JulianDate close()
	{
		return _dtClose;
	}

	/**
	 * Add the Native Forward Rate for the specified In/For Start/Forward Tenors
	 * 
	 * @param strInTenor "In" Start Tenor
	 * @param strForTenor "For" Forward Tenor
	 * @param dblForwardRate Forward Rate
	 * 
	 * @return TRUE - The Native Forward Rate successfully added
	 */

	public boolean addNativeForwardRate (
		final java.lang.String strInTenor,
		final java.lang.String strForTenor,
		final double dblForwardRate)
	{
		if (null == strInTenor || strInTenor.isEmpty() || null == strForTenor || strForTenor.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblForwardRate))
			return false;

		if (!_mapInForNativeLIBOR.containsKey (strInTenor)) {
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapForwardRate = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

			mapForwardRate.put (strForTenor, dblForwardRate);

			_mapInForNativeLIBOR.put (strInTenor, mapForwardRate);
		} else
			_mapInForNativeLIBOR.get (strInTenor).put (strForTenor, dblForwardRate);

		return true;
	}

	/**
	 * Retrieve the Native Forward Rate given the In/For Tenors
	 * 
	 * @param strInTenor "In" Start Tenor
	 * @param strForTenor "For" Forward Tenor
	 * 
	 * @return The Native Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nativeForwardRate (
		final java.lang.String strInTenor,
		final java.lang.String strForTenor)
		throws java.lang.Exception
	{
		if (null == strInTenor || strInTenor.isEmpty() || null == strForTenor || strForTenor.isEmpty() ||
			!_mapInForNativeLIBOR.containsKey (strInTenor))
			throw new java.lang.Exception ("FundingCurveMetrics::forwardRate => Invalid Inputs");

		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapForwardRate =
			_mapInForNativeLIBOR.get (strInTenor);

		if (null == mapForwardRate || !mapForwardRate.containsKey (strForTenor))
			throw new java.lang.Exception ("FundingCurveMetrics::forwardRate => Invalid Inputs");

		return mapForwardRate.get (strForTenor);
	}
}
