
package org.drip.function.r1tor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1UnivariateCIRPDF</i> exposes the R<sup>1</sup> Univariate Cox-Ingersoll-Ross Probability Density
 * 	Function. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Bogoliubov, N. N., and D. P. Sankevich (1994): N. N. Bogoliubov and Statistical Mechanics
 * 				<i>Russian Mathematical Surveys</i> <b>49 (5)</b> 19-49
 * 		</li>
 * 		<li>
 * 			Holubec, V., K. Kroy, and S. Steffenoni (2019): Physically Consistent Numerical Solver for
 * 				Time-dependent Fokker-Planck Equations <i>Physical Review E</i> <b>99 (4)</b> 032117
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Ottinger, H. C. (1996): <i>Stochastic Processes in Polymeric Fluids</i> <b>Springer-Verlag</b>
 * 				Berlin-Heidelberg
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Fokker-Planck Equation
 * 				https://en.wikipedia.org/wiki/Fokker%E2%80%93Planck_equation
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/kolmogorov/README.md">Fokker Planck Kolmogorov Forward/Backward</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateCIRPDF
	extends org.drip.function.definition.R1ToR1
{
	private double _beta = java.lang.Double.NaN;
	private double _alpha = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _gammaFunction = null;

	/**
	 * Construct a Standard Instance of R1UnivariateCIRPDF
	 * 
	 * @param cklsParameters The CKLS Parameters
	 * 
	 * @return Standard Instance of R1UnivariateCIRPDF
	 */

	public static final R1UnivariateCIRPDF Standard (
		final org.drip.dynamics.meanreverting.CKLSParameters cklsParameters)
	{
		if (null == cklsParameters)
		{
			return null;
		}

		double volatility = cklsParameters.volatilityCoefficient();

		double beta = 2. * cklsParameters.meanReversionSpeed() / volatility / volatility;

		try
		{
			return new R1UnivariateCIRPDF (
				beta * cklsParameters.meanReversionLevel(),
				beta,
				new org.drip.specialfunction.gamma.NemesAnalytic (
					null
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1UnivariateCIRPDF Constructor
	 * 
	 * @param alpha The Alpha
	 * @param beta The Beta
	 * @param gammaFunction The Gamma Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateCIRPDF (
		final double alpha,
		final double beta,
		final org.drip.function.definition.R1ToR1 gammaFunction)
		throws java.lang.Exception
	{
		super (
			null
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_alpha = alpha
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				_beta = beta
			) || null == (_gammaFunction = gammaFunction)
		)
		{
			throw new java.lang.Exception (
				"R1UnivariateCIRPDF CVonstructor => IOnvalid Inputs"
			);
		}
	}

	/**
	 * Retrieve Alpha
	 * 
	 * @return The Alpha
	 */

	public double alpha()
	{
		return _alpha;
	}

	/**
	 * Retrieve Beta
	 * 
	 * @return The Beta
	 */

	public double beta()
	{
		return _beta;
	}

	/**
	 * Retrieve the Gamma Function
	 * 
	 * @return The Gamma Function
	 */

	public org.drip.function.definition.R1ToR1 gammaFunction()
	{
		return _gammaFunction;
	}

	@Override public double evaluate (
		final double r)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			r
		))
		{
			throw new java.lang.Exception (
				"R1UnivariateCIRPDF::evaluate => Invalid Inputs"
			);
		}

		return java.lang.Math.pow (
			_beta,
			_alpha
		) * java.lang.Math.pow (
			r,
			_alpha - 1.
		) * java.lang.Math.exp (
			-1. * _beta * r
		) / _gammaFunction.evaluate (
			_alpha
		);
	}
}
