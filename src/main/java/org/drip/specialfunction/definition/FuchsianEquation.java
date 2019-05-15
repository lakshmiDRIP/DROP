
package org.drip.specialfunction.definition;

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
 * <i>FuchsianEquation</i> holds the Isomorphic Order, Coexter Singularity Index, and the Klein-4
 * Transformations of the 2F1 Regular Hyper-geometric Function. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/definition/README.md">Definition of Special Function Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FuchsianEquation
{
	private int _isomorphyOrder = -1;
	private org.drip.function.definition.R1ToR1[] _kleinGroupFunctionArray = null;

	/**
	 * FuchsianEquation Constructor
	 * 
	 * @param kleinGroupFunctionArray Array of the Klein Group Isomorphic Functions
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FuchsianEquation (
		final org.drip.function.definition.R1ToR1[] kleinGroupFunctionArray)
		throws java.lang.Exception
	{
		if (null == (_kleinGroupFunctionArray = kleinGroupFunctionArray))
		{
			throw new java.lang.Exception ("FuchsianEquation Constructor => Invalid Inputs");
		}

		int coxeterSingularityIndex = _kleinGroupFunctionArray.length;

		if (0 == coxeterSingularityIndex)
		{
			throw new java.lang.Exception ("FuchsianEquation Constructor => Invalid Inputs");
		}

		_isomorphyOrder = org.drip.numerical.common.NumberUtil.Factorial (coxeterSingularityIndex);

		for (int kleinGroupFunctionIndex = 0;
			kleinGroupFunctionIndex < coxeterSingularityIndex;
			++kleinGroupFunctionIndex)
		{
			if (null == _kleinGroupFunctionArray[kleinGroupFunctionIndex])
			{
				throw new java.lang.Exception ("FuchsianEquation Constructor => Invalid Inputs");
			}

			if (0 != kleinGroupFunctionIndex)
			{
				_isomorphyOrder = _isomorphyOrder * 2;
			}
		}
	}

	/**
	 * Retrieve the Coxeter Singularity Index
	 * 
	 * @return The Coxeter Singularity Index
	 */

	public int coxeterSingularityIndex()
	{
		return _kleinGroupFunctionArray.length;
	}

	/**
	 * Retrieve the Isomorphy Order
	 * 
	 * @return The Isomorphy Order
	 */

	public int isomorphyOrder()
	{
		return _isomorphyOrder;
	}

	/**
	 * Retrieve the Klein Group of Isomorphic Functions
	 * 
	 * @return The Klein Group of Isomorphic Functions
	 */

	public org.drip.function.definition.R1ToR1[] kleinGroupFunctionArray()
	{
		return _kleinGroupFunctionArray;
	}
}
