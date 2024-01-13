
package org.drip.spline.segment;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>Monotonocity</i> contains the monotonicity details related to the given segment. Indicates whether the
 * 	segment is monotonic, and if not, whether it contains a maximum, a minimum, or an inflection.
 *
 * <br>
 *  <ul>
 *  	<li>MONOTONIC</li>
 *  	<li>NON-MONOTONIC</li>
 *  	<li>NON MONOTONE - MINIMA</li>
 *  	<li>NON MONOTONE - MAXIMA</li>
 *  	<li>NON MONOTONE - INFLECTION</li>
 *  	<li><i>Monotonocity</i> constructor</li>
 *  	<li>Retrieve the Monotone Type</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Monotonocity
{

	/**
	 * MONOTONIC
	 */

	public static final int MONOTONIC = 2;

	/**
	 * NON-MONOTONIC
	 */

	public static final int NON_MONOTONIC = 4;

	/**
	 * NON MONOTONE - MINIMA
	 */

	public static final int MINIMA = 5;

	/**
	 * NON MONOTONE - MAXIMA
	 */

	public static final int MAXIMA = 6;

	/**
	 * NON MONOTONE - INFLECTION
	 */

	public static final int INFLECTION = 7;

	private int _monotoneType = -1;

	/**
	 * <i>Monotonocity</i> constructor
	 * 
	 * @param monotoneType One of the valid monotone types
	 * 
	 * @throws Exception Thrown if the input monotone type is invalid
	 */

	public Monotonocity (
		final int monotoneType)
		throws Exception
	{
		if (MONOTONIC != (_monotoneType = monotoneType) &&
			NON_MONOTONIC != _monotoneType &&
			MINIMA != _monotoneType &&
			MAXIMA != _monotoneType &&
			INFLECTION != _monotoneType) {
			throw new Exception ("Monotonocity ctr: Unknown monotone type " + _monotoneType);
		}
	}

	/**
	 * Retrieve the Monotone Type
	 * 
	 * @return The Monotone Type
	 */

	public int type()
	{
		return _monotoneType;
	}

	@Override public String toString()
	{
		if (NON_MONOTONIC == _monotoneType) {
			return "NON_MONOTONIC";
		}

		if (MONOTONIC == _monotoneType) {
			return "MONOTONIC    ";
		}

		if (MINIMA == _monotoneType) {
			return "MINIMA       ";
		}

		if (MAXIMA == _monotoneType) {
			return "MAXIMA       ";
		}

		return "INFLECTION   ";
	}
}
