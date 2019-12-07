
package org.drip.numerical.linearalgebra;

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
 * <i>UD</i> holds the Results of U and the D Matrices that form the Result of the UDU Transpose
 * 	Decomposition.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UD
{
	private double[][] _D = null;
	private double[][] _U = null;

	/**
	 * UD Constructor
	 * 
	 * @param u U
	 * @param d D
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public UD (
		final double[][] u,
		final double[][] d)
		throws java.lang.Exception
	{
		if (null == (_U = u) ||
			null == (_D = d)
		)
		{
			throw new java.lang.Exception (
				"UD ctr: Invalid Inputs!"
			);
		}

		int size = _U.length;

		if (0 == size || null == _U[0] || size != _U[0].length ||
			size != _D.length || null == _D[0] || size != _D[0].length ||
			!org.drip.numerical.linearalgebra.Matrix.IsDiagonal (
				_D
			)
		)
		{
			throw new java.lang.Exception (
				"UD ctr: Invalid Inputs!"
			);
		}
	}

	/**
	 * Retrieve U
	 * 
	 * @return U
	 */

	public double[][] u()
	{
		return _U;
	}

	/**
	 * Retrieve D
	 * 
	 * @return D
	 */

	public double[][] d()
	{
		return _D;
	}

	/**
	 * Compute the UDU Transpose
	 * 
	 * @return The UDU Transpose
	 */

	public double[][] uduTranspose()
	{
		double[][] ud = org.drip.numerical.linearalgebra.Matrix.Product (
			_U,
			_D
		);

		return null == ud ? null : org.drip.numerical.linearalgebra.Matrix.Product (
			ud,
			org.drip.numerical.linearalgebra.Matrix.Transpose (
				_U
			)
		);
	}
}
