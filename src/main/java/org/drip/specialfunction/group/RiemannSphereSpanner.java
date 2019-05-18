
package org.drip.specialfunction.group;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>RiemannSphereSpanner</i> determines the Conformality and Tile Scheme of the Schwarz Singular Triangle
 * Maps over the Riemann Sphere. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Gessel, I., and D. Stanton (1982): Strange Evaluations of Hyper-geometric Series <i>SIAM Journal
 * 				on Mathematical Analysis</i> <b>13 (2)</b> 295-308
 * 		</li>
 * 		<li>
 * 			Koepf, W (1995): Algorithms for m-fold Hyper-geometric Summation <i>Journal of Symbolic
 * 				Computation</i> <b>20 (4)</b> 399-417
 * 		</li>
 * 		<li>
 * 			Lavoie, J. L., F. Grondin, and A. K. Rathie (1996): Generalization of Whipple’s Theorem on the
 * 				Sum of a (_2^3)F(a,b;c;z) <i>Journal of Computational and Applied Mathematics</i> <b>72</b>
 * 				293-300
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Hyper-geometric Function
 * 				https://dlmf.nist.gov/15
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Hyper-geometric Function https://en.wikipedia.org/wiki/Hypergeometric_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/group/README.md">Special Function Singularity Solution Group</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RiemannSphereSpanner
{

	/**
	 * Schwarz Triangle Tiles Nothing
	 */

	public static final int SCHWARZ_TRIANGLE_TILES_NOTHING = 0;

	/**
	 * Schwarz Triangle Tiles the Riemann Sphere
	 */

	public static final int SCHWARZ_TRIANGLE_TILES_RIEMANN_SPHERE = 1;

	/**
	 * Schwarz Triangle Tiles the Complex Plane
	 */

	public static final int SCHWARZ_TRIANGLE_TILES_COMPLEX_PLANE = 2;

	/**
	 * Schwarz Triangle Tiles the Upper Half Plane
	 */

	public static final int SCHWARZ_TRIANGLE_TILES_UPPER_HALF_PLANE = 3;

	private org.drip.specialfunction.group.SchwarzTriangleMap[] _schwarzTriangleMapArray = null;

	/**
	 * RiemannSphereSpanner Constructor
	 * 
	 * @param schwarzTriangleMapArray The Schwarz Triangle Map Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiemannSphereSpanner (
		final org.drip.specialfunction.group.SchwarzTriangleMap[] schwarzTriangleMapArray)
		throws java.lang.Exception
	{
		if (null == (_schwarzTriangleMapArray = schwarzTriangleMapArray))
		{
			throw new java.lang.Exception ("RiemannSphereSpanner Constructor => Invalid Inputs");
		}

		int singularityCount = _schwarzTriangleMapArray.length;

		if (0 == singularityCount)
		{
			throw new java.lang.Exception ("RiemannSphereSpanner Constructor => Invalid Inputs");
		}

		for (int singularityIndex = 0; singularityIndex < singularityCount; ++singularityIndex)
		{
			if (null == _schwarzTriangleMapArray[singularityCount])
			{
				throw new java.lang.Exception ("RiemannSphereSpanner Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Schwarz Triangle Map Array
	 * 
	 * @return The Schwarz Triangle Map Array
	 */

	public org.drip.specialfunction.group.SchwarzTriangleMap[] schwarzTriangleMapArray()
	{
		return _schwarzTriangleMapArray;
	}

	/**
	 * Indicate if the Spanner is Conformal
	 * 
	 * @return TRUE - The Spanner is Conformal
	 */

	public boolean isConformal()
	{
		for (org.drip.specialfunction.group.SchwarzTriangleMap schwarzTriangleMap : _schwarzTriangleMapArray)
		{
			if (!schwarzTriangleMap.isConformal())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate how the Schwarz Triangle Tiles the Riemann Sphere
	 * 
	 * @return Indicator of how the Schwarz Triangle Tiles the Riemann Sphere
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int tileIndicator()
		throws java.lang.Exception
	{
		double connectionCoefficientCumulative = 0.;
		int singularityCount = _schwarzTriangleMapArray.length;

		for (int singularityIndex = 0; singularityIndex < singularityCount; ++singularityIndex)
		{
			double connectionCoefficient =
				_schwarzTriangleMapArray[singularityIndex].connectionCoefficient();

			if (!org.drip.numerical.common.NumberUtil.IsInteger (1. / connectionCoefficient))
			{
				return SCHWARZ_TRIANGLE_TILES_NOTHING;
			}

			connectionCoefficientCumulative = connectionCoefficientCumulative + connectionCoefficient;
		}

		if (0. == connectionCoefficientCumulative)
		{
			return SCHWARZ_TRIANGLE_TILES_COMPLEX_PLANE;
		}

		return 0. > connectionCoefficientCumulative ? SCHWARZ_TRIANGLE_TILES_UPPER_HALF_PLANE :
			SCHWARZ_TRIANGLE_TILES_COMPLEX_PLANE;
	}
}
