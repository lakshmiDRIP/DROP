
package org.drip.specialfunction.bessel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>SphericalSecondEstimator</i> implements the Integral Estimator for the Spherical Bessel Function of the
 * Second Kind. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/bessel/README.md">Ordered Bessel Function Variant Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SphericalSecondEstimator extends
	org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator
{
	private org.drip.specialfunction.definition.BesselSecondKindEstimator _besselSecondKindEstimator = null;

	/**
	 * Retrieve the Order 0 Spherical Bessel Second Kind Estimator
	 * 
	 * @return The Order 0 Spherical Bessel Second Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator OrderZero()
	{
		return new org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator()
		{
			@Override public double smallY (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (0. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderZero::smallY => Invalid Inputs");
				}

				return java.lang.Math.cos (z) / z;
			}
		};
	}

	/**
	 * Retrieve the Order +1 Spherical Bessel Second Kind Estimator
	 * 
	 * @return The Order +1 Spherical Bessel Second Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator OrderPlusOne()
	{
		return new org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator()
		{
			@Override public double smallY (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (1. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderPlusOne::smallY => Invalid Inputs");
				}

				double oneOverZ = 1. / z;

				return -1. * java.lang.Math.cos (z) * oneOverZ * oneOverZ -
					java.lang.Math.sin (z) * oneOverZ;
			}
		};
	}

	/**
	 * Retrieve the Order +2 Spherical Bessel Second Kind Estimator
	 * 
	 * @return The Order +2 Spherical Bessel Second Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator OrderPlusTwo()
	{
		return new org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator()
		{
			@Override public double smallY (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (2. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderPlusTwo::smallY => Invalid Inputs");
				}

				double oneOverZ = 1. / z;
				double oneOverZ2 = oneOverZ * oneOverZ;

				return (1. - 3. * oneOverZ2) * java.lang.Math.cos (z) * oneOverZ -
					3. * java.lang.Math.sin (z) * oneOverZ2;
			}
		};
	}

	/**
	 * Retrieve the Order +3 Spherical Bessel Second Kind Estimator
	 * 
	 * @return The Order +3 Spherical Bessel Second Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator
		OrderPlusThree()
	{
		return new org.drip.specialfunction.definition.SphericalBesselSecondKindEstimator()
		{
			@Override public double smallY (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (3. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderOrderPlusThree::smallY => Invalid Inputs");
				}

				double oneOverZ = 1. / z;
				double oneOverZ2 = oneOverZ * oneOverZ;

				return (6. * oneOverZ - 15. * oneOverZ2 * oneOverZ) * java.lang.Math.cos (z) * oneOverZ -
					(15. * oneOverZ2 - 1.) * java.lang.Math.sin (z) * oneOverZ;
			}
		};
	}

	/**
	 * SphericalSecondEstimator Constructor
	 * 
	 * @param besselSecondKindEstimator Bessel Function Second Kind Estimator
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public SphericalSecondEstimator (
		final org.drip.specialfunction.definition.BesselSecondKindEstimator besselSecondKindEstimator)
		throws java.lang.Exception
	{
		if (null == (_besselSecondKindEstimator = besselSecondKindEstimator))
		{
			throw new java.lang.Exception ("SphericalSecondEstimator Constructor => Invalid Inputs");
		}
	}

	@Override public double smallY (
		final double alpha,
		final double z)
		throws java.lang.Exception
	{
		return java.lang.Math.sqrt (0.5 * java.lang.Math.PI / z) * _besselSecondKindEstimator.bigY (
			alpha + 0.5,
			z
		);
	}

	/**
	 * Retrieve the Bessel Function Second Kind Estimator
	 * 
	 * @return Bessel Function Second Kind Estimator
	 */

	public org.drip.specialfunction.definition.BesselSecondKindEstimator besselSecondKindEstimator()
	{
		return _besselSecondKindEstimator;
	}
}
