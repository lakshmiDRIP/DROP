
package org.drip.numerical.r1integration;

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
 * <i>NestedQuadratureEstimator</i> extends the R<sup>1</sup> Quadrature Estimator by providing the
 * Estimation Error. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Briol, F. X., C. J. Oates, M. Girolami, and M. A. Osborne (2015): <i>Frank-Wolfe Bayesian
 * 				Quadrature: Probabilistic Integration with Theoretical Guarantees</i> <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Forsythe, G. E., M. A. Malcolm, and C. B. Moler (1977): <i>Computer Methods for Mathematical
 * 				Computation</i> <b>Prentice Hall</b> Englewood Cliffs NJ
 * 		</li>
 * 		<li>
 * 			Leader, J. J. (2004): <i>Numerical Analysis and Scientific Computation</i> <b>Addison Wesley</b>
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (1980): <i>Introduction to Numerical Analysis</i>
 * 				<b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Numerical Integration https://en.wikipedia.org/wiki/Numerical_integration
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/r1integration/README.md">R<sup>1</sup> to R<sup>1</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NestedQuadratureEstimator extends org.drip.numerical.r1integration.QuadratureEstimator
{
	private org.drip.numerical.r1integration.QuadratureEstimator _embeddedQuadratureEstimator = null;

	/**
	 * NestedQuadratureEstimator Constructor
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * @param nodeWeightArray Array of the Nodes and Weights
	 * @param embeddedQuadratureEstimator The Embedded Quadrature Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NestedQuadratureEstimator (
		final org.drip.numerical.r1integration.AbscissaTransform abscissaTransformer,
		final org.drip.numerical.common.Array2D nodeWeightArray,
		final org.drip.numerical.r1integration.QuadratureEstimator embeddedQuadratureEstimator)
		throws java.lang.Exception
	{
		super (
			abscissaTransformer,
			nodeWeightArray
		);

		if (null == (_embeddedQuadratureEstimator = embeddedQuadratureEstimator))
		{
			throw new java.lang.Exception ("NestedQuadratureEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Embedded Quadrature Estimator
	 * 
	 * @return The Embedded Quadrature Estimator
	 */

	public org.drip.numerical.r1integration.QuadratureEstimator embeddedQuadratureEstimator()
	{
		return _embeddedQuadratureEstimator;
	}

	/**
	 * Estimate the Quadrature and its Error
	 * 
	 * @param r1ToR1Integrand The R<sup>1</sup> To R<sup>1</sup> Integrand
	 * 
	 * @return The Quadrature and its Error
	 */

	public org.drip.numerical.r1integration.QuadratureEstimate estimate (
		final org.drip.function.definition.R1ToR1 r1ToR1Integrand)
	{
		try
		{
			double baseline = integrate (r1ToR1Integrand);

			return new org.drip.numerical.r1integration.QuadratureEstimate (
				baseline,
				java.lang.Math.abs (baseline - _embeddedQuadratureEstimator.integrate (r1ToR1Integrand))
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
