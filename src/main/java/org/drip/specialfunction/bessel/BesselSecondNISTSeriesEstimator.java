
package org.drip.specialfunction.bessel;

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
 * <i>BesselSecondNISTSeriesEstimator</i> implements the NIST Series Estimator for the Cylindrical Bessel
 * Function of the Second Kind. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BesselSecondNISTSeriesEstimator extends
	org.drip.specialfunction.definition.BesselSecondKindEstimator
{
	private org.drip.numerical.estimation.R2ToR1Series _besselSecondNISTSeries = null;
	private org.drip.specialfunction.definition.BesselFirstKindEstimator _besselFirstEstimator = null;

	/**
	 * Construct a Standard Instance of BesselSecondNISTSeriesEstimator
	 * 
	 * @param digammaEstimator The Digamma Estimator
	 * @param gammaEstimator The Gamma Estimator
	 * @param besselFirstEstimator The Bessel Function First Kind Estimator
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The Standard Instance of BesselSecondNISTSeriesEstimator
	 */

	public static final BesselSecondNISTSeriesEstimator Standard (
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstEstimator,
		final int termCount)
	{
		try
		{
			return new BesselSecondNISTSeriesEstimator (
				org.drip.specialfunction.bessel.BesselSecondNISTSeries.SecondKind (
					digammaEstimator,
					gammaEstimator,
					termCount
				),
				besselFirstEstimator
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	protected BesselSecondNISTSeriesEstimator (
		final org.drip.numerical.estimation.R2ToR1Series besselSecondNISTSeries,
		final org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstEstimator)
		throws java.lang.Exception
	{
		if (null == (_besselSecondNISTSeries = besselSecondNISTSeries) ||
			null == (_besselFirstEstimator = besselFirstEstimator))
		{
			throw new java.lang.Exception ("BesselSecondNISTSeriesEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bessel Second NIST Series
	 * 
	 * @return The Bessel Second NIST Series
	 */

	public org.drip.numerical.estimation.R2ToR1Series besselSecondNISTSeries()
	{
		return _besselSecondNISTSeries;
	}

	/**
	 * Retrieve the Bessel Function First Kind Estimator
	 * 
	 * @return The Bessel Function First Kind Estimator
	 */

	public org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstEstimator()
	{
		return _besselFirstEstimator;
	}

	@Override public double bigY (
		final double alpha,
		final double z)
		throws java.lang.Exception
	{
		return _besselSecondNISTSeries.evaluate (
			alpha,
			z
		) + 2. / java.lang.Math.PI * java.lang.Math.log (0.5 * z) * _besselFirstEstimator.bigJ (
			alpha,
			z
		);
	}
}
