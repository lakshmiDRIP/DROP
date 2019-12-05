
package org.drip.spline.segment;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>Monotonocity</i> contains the monotonicity details related to the given segment. Indicates whether the
 * segment is monotonic, and if not, whether it contains a maximum, a minimum, or an inflection.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Monotonocity {

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

	private int _iMonotoneType = -1;

	/**
	 * Monotonocity constructor
	 * 
	 * @param iMonotoneType One of the valid monotone types
	 * 
	 * @throws java.lang.Exception Thrown if the input monotone type is invalid
	 */

	public Monotonocity (
		final int iMonotoneType)
		throws java.lang.Exception
	{
		if (org.drip.spline.segment.Monotonocity.MONOTONIC != (_iMonotoneType = iMonotoneType) &&
			org.drip.spline.segment.Monotonocity.NON_MONOTONIC != _iMonotoneType &&
				org.drip.spline.segment.Monotonocity.MINIMA != _iMonotoneType &&
					org.drip.spline.segment.Monotonocity.MAXIMA != _iMonotoneType &&
						org.drip.spline.segment.Monotonocity.INFLECTION != _iMonotoneType)
			throw new java.lang.Exception ("Monotonocity ctr: Unknown monotone type " + _iMonotoneType);
	}

	/**
	 * Retrieve the Monotone Type
	 * 
	 * @return The Monotone Type
	 */

	public int type()
	{
		return _iMonotoneType;
	}

	@Override public java.lang.String toString()
	{
		if (org.drip.spline.segment.Monotonocity.NON_MONOTONIC == _iMonotoneType) return "NON_MONOTONIC";

		if (org.drip.spline.segment.Monotonocity.MONOTONIC == _iMonotoneType) return "MONOTONIC    ";

		if (org.drip.spline.segment.Monotonocity.MINIMA == _iMonotoneType) return "MINIMA       ";

		if (org.drip.spline.segment.Monotonocity.MAXIMA == _iMonotoneType) return "MAXIMA       ";

		return "INFLECTION   ";
	}
}
