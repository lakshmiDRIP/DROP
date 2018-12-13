
package org.drip.function.definition;

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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>SizedVector</i> holds the R<sup>d</sup> Unit Direction Vector along with its Magnitude.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/definition/README.md">Definition</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SizedVector {
	private double _dblMagnitude = java.lang.Double.NaN;
	private org.drip.function.definition.UnitVector _uv = null;

	/**
	 * Construct an Instance of the Sized Vector from the Input Array
	 * 
	 * @param adbl The Input Double Array
	 * 
	 * @return The Sized Vector Instance
	 */

	public static final SizedVector Standard (
		final double[] adbl)
	{
		if (null == adbl) return null;

		double dblModulus = 0.;
		int iDimension = adbl.length;
		double[] adblComponent = 0 == iDimension ? null : new double[iDimension];

		if (0 == iDimension) return null;

		for (int i = 0; i < iDimension; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adbl[i])) return null;

			dblModulus += adbl[i] * adbl[i];
		}

		if (0. == dblModulus) return null;

		dblModulus = java.lang.Math.sqrt (dblModulus);

		for (int i = 0; i < iDimension; ++i)
			adblComponent[i] = adbl[i] / dblModulus;

		try {
			return new SizedVector (new org.drip.function.definition.UnitVector (adblComponent), dblModulus);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SizedVector Constructor
	 * 
	 * @param uv The Unit Vector
	 * @param dblMagnitude Magnitude of the Vector
	 * 
	 * @throws java.lang.Exception Thriwn if the Inputs are Invalid
	 */

	public SizedVector (
		final org.drip.function.definition.UnitVector uv,
		final double dblMagnitude)
		throws java.lang.Exception
	{
		if (null == (_uv = uv) || !org.drip.quant.common.NumberUtil.IsValid (_dblMagnitude = dblMagnitude))
			throw new java.lang.Exception ("SizedVector Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Unit Direction Vector
	 * 
	 * @return The Unit Vector Direction Instance
	 */

	public org.drip.function.definition.UnitVector direction()
	{
		return _uv;
	}

	/**
	 * Retrieve the Vector Magnitude
	 * 
	 * @return The Vector Magnitude
	 */

	public double magnitude()
	{
		return _dblMagnitude;
	}
}
