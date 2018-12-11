
package org.drip.spline.segment;

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
 * <i>Monotonicity</i> contains the monotonicity details related to the given segment. Indicates whether the
 * segment is monotonic, and if not, whether it contains a maximum, a minimum, or an inflection.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment">Segment</a></li>
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
