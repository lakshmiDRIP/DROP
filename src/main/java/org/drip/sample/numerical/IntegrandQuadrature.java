
package org.drip.sample.numerical;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1.*;
import org.drip.numerical.integration.R1ToR1Integrator;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>IntegrandQuadrature</i> shows samples for the following routines for integrating the objective function:
 * 	- Mid-Point Scheme
 * 	- Trapezoidal Scheme
 * 	- Simpson/Simpson38 schemes
 * 	- Boole Scheme
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/numerical/README.md">Search, Quadratures, Fourier Phase Tracker</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IntegrandQuadrature {

	/*
	 * Compute the Integrand Quadrature for the specified Univariate Function using the various methods.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static void ComputeQuadrature (
		final R1ToR1 au,
		final double dblActual,
		final double dblStart,
		final double dblEnd)
		throws Exception
	{
		int iRightDecimal = 8;

		System.out.println ("\t\tActual      : " +
			FormatUtil.FormatDouble (dblActual, 1, iRightDecimal, 1.)
		);

		System.out.println ("\t\tLinear      : " +
			FormatUtil.FormatDouble (
				R1ToR1Integrator.LinearQuadrature (
					au,
					dblStart,
					dblEnd
				),
				1,
				iRightDecimal,
				1.
			)
		);

		System.out.println ("\t\tMidPoint     : " +
			FormatUtil.FormatDouble (
				R1ToR1Integrator.MidPoint (
					au,
					dblStart,
					dblEnd
				),
				1,
				iRightDecimal,
				1.
			)
		);

		System.out.println ("\t\tTrapezoidal  : " +
			FormatUtil.FormatDouble (
				R1ToR1Integrator.Trapezoidal (
					au,
					dblStart,
					dblEnd
				),
				1,
				iRightDecimal,
				1.
			)
		);

		System.out.println ("\t\tSimpson      : " +
			FormatUtil.FormatDouble (
				R1ToR1Integrator.Simpson (
					au,
					dblStart,
					dblEnd
				),
				1,
				iRightDecimal,
				1.
			)
		);

		System.out.println ("\t\tSimpson 38   : " +
			FormatUtil.FormatDouble (
				R1ToR1Integrator.Simpson38 (
					au,
					dblStart,
					dblEnd
				),
				1,
				iRightDecimal,
				1.
			)
		);

		System.out.println ("\t\tBoole        : " +
			FormatUtil.FormatDouble (
				R1ToR1Integrator.Boole (
					au,
					dblStart,
					dblEnd
				),
				1,
				iRightDecimal,
				1.
			)
		);
	}

	/*
	 * Compute the Integrand Quadrature for the various Univariate Functions using the different methods.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static void IntegrandQuadratureSample()
		throws Exception
	{
		double dblStart = 0.;
		double dblEnd = 1.;

		R1ToR1 auExp = new ExponentialTension (
			Math.E,
			1.
		);

		System.out.println ("\n\t-------------------------------------\n");

		ComputeQuadrature (
			auExp,
			auExp.evaluate (dblEnd) - auExp.evaluate (dblStart),
			dblStart,
			dblEnd
		);

		System.out.println ("\n\t-------------------------------------\n");

		R1ToR1 au1 = new R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVariate)
				throws Exception
			{
				return Math.cos (dblVariate) - dblVariate * dblVariate * dblVariate;
			}
		};

		ComputeQuadrature (
			au1,
			Math.sin (dblEnd) - Math.sin (dblStart) - 0.25 * (dblEnd * dblEnd * dblEnd * dblEnd - dblStart * dblStart * dblStart * dblStart),
			dblStart,
			dblEnd
		);

		System.out.println ("\n\t-------------------------------------\n");

		R1ToR1 au2 = new R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVariate)
				throws Exception
			{
				return dblVariate * dblVariate * dblVariate - 3. * dblVariate * dblVariate + 2. * dblVariate;
			}
		};

		ComputeQuadrature (
			au2,
			0.25 * (dblEnd * dblEnd * dblEnd * dblEnd - dblStart * dblStart * dblStart * dblStart) -
				(dblEnd * dblEnd * dblEnd - dblStart * dblStart * dblStart) +
				(dblEnd * dblEnd - dblStart * dblStart),
			dblStart,
			dblEnd
		);

		System.out.println ("\n\t-------------------------------------\n");
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static void main (
		final String astrArgs[])
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		IntegrandQuadratureSample();

		EnvManager.TerminateEnv();
	}
}
