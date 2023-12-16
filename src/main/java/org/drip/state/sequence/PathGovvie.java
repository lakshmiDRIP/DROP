
package org.drip.state.sequence;

import org.drip.analytics.date.JulianDate;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.nonlinear.FlatForwardGovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>PathGovvie</i> exposes the Functionality to generate a Sequence of Govvie Curve Realizations across
 * Multiple Paths. It exposes the following functionality:
 *  
 *  <br><br>
 *  <ul>
 *		<li>PathGovvie Constructor</li>
 *		<li>Retrieve the Govvie Builder Settings Instance</li>
 *		<li>Generate the R<sup>d</sup> Path Govvie Curves using the Ground State Yield</li>
 *  </ul>
 *  <br><br>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence/README.md">Monte Carlo Path State Realizations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathGovvie extends PathRd
{
	private GovvieBuilderSettings _govvieBuilderSettings = null;

	/**
	 * PathGovvie Constructor
	 * 
	 * @param govvieBuilderSettings Govvie Builder Settings Instance
	 * @param volatility Volatility
	 * @param logNormal TRUE - The Generated Random Numbers are Log Normal
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PathGovvie (
		final GovvieBuilderSettings govvieBuilderSettings,
		final double volatility,
		final boolean logNormal)
		throws Exception
	{
		super (govvieBuilderSettings.groundForwardYield(), volatility, logNormal);

		if (null == (_govvieBuilderSettings = govvieBuilderSettings)) {
			throw new Exception ("PathGovvie Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Govvie Builder Settings Instance
	 * 
	 * @return The Govvie Builder Settings Instance
	 */

	public GovvieBuilderSettings govvieBuilderSettings()
	{
		return _govvieBuilderSettings;
	}

	/**
	 * Generate the R<sup>d</sup> Path Govvie Curves using the Ground State Yield
	 * 
	 * @param pathCount Number of Paths
	 * 
	 * @return The R<sup>d</sup> Path/Vertex Govvie Curves
	 */

	public GovvieCurve[] curveSequence (
		final int pathCount)
	{
		String currency = _govvieBuilderSettings.groundState().currency();

		JulianDate spotDate = _govvieBuilderSettings.spot();

		double[][] pathSequenceGrid = sequence (pathCount);

		String treasuryCode = _govvieBuilderSettings.code();

		String[] tenorArray = _govvieBuilderSettings.tenors();

		if (null == pathSequenceGrid) {
			return null;
		}

		int epochDate = spotDate.julian();

		int tenorCount = tenorArray.length;
		int[] tenorDateArray = new int[tenorCount];
		FlatForwardGovvieCurve[] flatForwardGovvieCurveArray = new FlatForwardGovvieCurve[pathCount];

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			JulianDate tenorDate = spotDate.addTenor (tenorArray[tenorIndex]);

			if (null == tenorDate) {
				return null;
			}

			tenorDateArray[tenorIndex] = tenorDate.julian();
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			try {
				if (null == (
					flatForwardGovvieCurveArray[pathIndex] = new FlatForwardGovvieCurve (
						epochDate,
						treasuryCode,
						currency,
						tenorDateArray,
						pathSequenceGrid[pathIndex]
					)
				)) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return flatForwardGovvieCurveArray;
	}
}
