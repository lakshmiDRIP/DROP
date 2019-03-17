
package org.drip.function.rdtor1descent;

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
 * <i>ArmijoEvolutionVerifier</i> implements the Armijo Criterion used for the Inexact Line Search Increment
 * Generation to ascertain that the Function has reduced sufficiently. The Reference is:
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial
 * 				Derivatives <i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1descent/README.md">R<sup>d</sup> To R<sup>1</sup></a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ArmijoEvolutionVerifier extends org.drip.function.rdtor1descent.LineEvolutionVerifier {

	/**
	 * The Nocedal-Wright Armijo Parameter
	 */

	public static final double NOCEDAL_WRIGHT_ARMIJO_PARAMETER = 0.0001;

	private boolean _bMaximizerCheck = false;
	private double _dblArmijoParameter = java.lang.Double.NaN;

	/**
	 * Construct the Nocedal-Wright Armijo Evolution Verifier
	 * @param bMaximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @return The Nocedal-Wright Armijo Evolution Verifier Instance
	 */

	public static final ArmijoEvolutionVerifier NocedalWrightStandard (
		final boolean bMaximizerCheck)
	{
		try {
			return new ArmijoEvolutionVerifier (NOCEDAL_WRIGHT_ARMIJO_PARAMETER, bMaximizerCheck);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ArmijoEvolutionVerifier Constructor
	 * 
	 * @param dblArmijoParameter The Armijo Parameter
	 * @param bMaximizerCheck TRUE - Perform a Check for the Function Maxima
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArmijoEvolutionVerifier (
		final double dblArmijoParameter,
		final boolean bMaximizerCheck)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblArmijoParameter = dblArmijoParameter))
			throw new java.lang.Exception ("ArmijoEvolutionVerifier Constructor => Invalid Inputs");

		_bMaximizerCheck = bMaximizerCheck;
	}

	/**
	 * Indicate if the Check is for Minimizer/Maximizer
	 * 
	 * @return TRUE - The Check is for Maximizer
	 */

	public boolean maximizerCheck()
	{
		return _bMaximizerCheck;
	}

	/**
	 * Retrieve the Armijo Parameter
	 * 
	 * @return The Armijo Parameter
	 */

	public double armijoParameter()
	{
		return _dblArmijoParameter;
	}

	@Override public org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics metrics (
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final org.drip.function.definition.RdToR1 funcRdToR1,
		final double dblStepLength)
	{
		try {
			return null == funcRdToR1 ? null : new
				org.drip.function.rdtor1descent.ArmijoEvolutionVerifierMetrics (_dblArmijoParameter,
					_bMaximizerCheck, uvTargetDirection, adblCurrentVariate, dblStepLength,
						funcRdToR1.evaluate (adblCurrentVariate), funcRdToR1.evaluate (NextVariate
							(uvTargetDirection, adblCurrentVariate, dblStepLength)), funcRdToR1.jacobian
								(adblCurrentVariate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
