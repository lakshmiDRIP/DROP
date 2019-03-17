
package org.drip.spline.bspline;

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
 * <i>SegmentBasisFunctionSet</i> class implements per-segment function set for B Splines and tension
 * splines. Derived implementations expose explicit targeted basis functions.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline">B Spline</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentBasisFunctionSet extends org.drip.spline.basis.FunctionSet {
	protected double _dblTension = java.lang.Double.NaN;

	private static final org.drip.function.definition.R1ToR1[] responseBasis (
		final int iNumBasisToUse,
		final org.drip.function.definition.R1ToR1[] aAUHat)
	{
		if (null == aAUHat || iNumBasisToUse > aAUHat.length) return null;

		try {
			org.drip.function.definition.R1ToR1[] aAU = new
				org.drip.function.definition.R1ToR1[iNumBasisToUse + 2];

			aAU[0] = new org.drip.function.r1tor1.Polynomial (0);

			aAU[1] = new org.drip.function.r1tor1.UnivariateReflection (new
				org.drip.function.r1tor1.Polynomial (1));

			for (int i = 0; i < iNumBasisToUse; ++i)
				aAU[2 + i] = aAUHat[i];

			return aAU;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SegmentBasisFunctionSet constructor
	 * 
	 * @param iNumBasisToUse Number of Basis in the Hat Basis Set to Use
	 * @param dblTension Tension Parameter
	 * @param aAUHat The Hat Representation Function Set
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public SegmentBasisFunctionSet (
		final int iNumBasisToUse,
		final double dblTension,
		final org.drip.function.definition.R1ToR1[] aAUHat)
		throws java.lang.Exception
	{
		super (responseBasis (iNumBasisToUse, aAUHat));

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblTension = dblTension))
			throw new java.lang.Exception ("SegmentBasisFunctionSet ctr: Invalid Inputs!");
	}

	/**
	 * Retrieve the Tension Parameter
	 * 
	 * @return The Tension Parameter
	 */

	public double tension()
	{
		return _dblTension;
	}
}
