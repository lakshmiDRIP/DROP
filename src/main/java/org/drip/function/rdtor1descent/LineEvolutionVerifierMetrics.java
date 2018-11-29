
package org.drip.function.rdtor1descent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

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
 * <i>LineEvolutionVerifierMetrics</i> implements the Step Length Verification Criterion used for the Inexact
 * Line Search Increment Generation. The References are:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial
 * 				Derivatives <i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3
 * 		</li>
 * 		<li>
 * 			Nocedal, J., and S. Wright (1999): <i>Numerical Optimization</i> <b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1969): Convergence Conditions for Ascent Methods <i>SIAM Review</i> <b>11 (2)</b>
 * 				226-235
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections <i>SIAM
 * 				Review</i> <b>13 (2)</b> 185-188
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1descent">R<sup>d</sup> To R<sup>1</sup></a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class LineEvolutionVerifierMetrics {
	private double[] _adblCurrentVariate = null;
	private double _dblStepLength = java.lang.Double.NaN;
	private double[] _adblCurrentVariateFunctionJacobian = null;
	private org.drip.function.definition.UnitVector _uvTargetDirection = null;

	protected LineEvolutionVerifierMetrics (
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final double dblStepLength,
		final double[] adblCurrentVariateFunctionJacobian)
		throws java.lang.Exception
	{
		if (null == (_uvTargetDirection = uvTargetDirection) || null == (_adblCurrentVariate =
			adblCurrentVariate) || !org.drip.quant.common.NumberUtil.IsValid (_dblStepLength = dblStepLength)
				|| null == (_adblCurrentVariateFunctionJacobian = adblCurrentVariateFunctionJacobian) ||
					_adblCurrentVariate.length != _adblCurrentVariateFunctionJacobian.length)
			throw new java.lang.Exception ("LineEvolutionVerifierMetrics Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Current Variate Array
	 * 
	 * @return The Current Variate Array
	 */

	public double[] currentVariate()
	{
		return _adblCurrentVariate;
	}

	/**
	 * Retrieve the Target Direction Unit Vector
	 * 
	 * @return The Target Direction Unit Vector
	 */

	public org.drip.function.definition.UnitVector targetDirection()
	{
		return _uvTargetDirection;
	}

	/**
	 * Retrieve the Step Length
	 * 
	 * @return The Step Length
	 */

	public double stepLength()
	{
		return _dblStepLength;
	}

	/**
	 * Retrieve the Function Jacobian at the Current Variate
	 * 
	 * @return The Function Jacobian at the Current Variate
	 */

	public double[] currentVariateFunctionJacobian()
	{
		return _adblCurrentVariateFunctionJacobian;
	}

	@Override public java.lang.String toString()
	{
		double[] adblDirection = _uvTargetDirection.component();

		java.lang.String strDump = "\t[";
		int iNumVariate = _adblCurrentVariate.length;

		for (int i = 0; i < iNumVariate; ++i)
			strDump = strDump + org.drip.quant.common.FormatUtil.FormatDouble (_adblCurrentVariate[i], 2, 3,
				1.) + " |";

		strDump = strDump + "]" + org.drip.quant.common.FormatUtil.FormatDouble (_dblStepLength, 1, 3, 1.) +
			" || {";

		for (int i = 0; i < iNumVariate; ++i)
			strDump = strDump + org.drip.quant.common.FormatUtil.FormatDouble (adblDirection[i], 1, 2, 1.) +
				" |";

		return strDump + " }";
	}

	/**
	 * Indicate if the Evolution Criterion has been met
	 * 
	 * @return TRUE - The Evolution Criterion has been met
	 */

	public abstract boolean verify();
}
