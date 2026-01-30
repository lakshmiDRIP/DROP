
package org.drip.numerical.integration;

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
 * <i>R1QuadratureEstimator</i> estimates an Integrand Quadrature using the Array of Transformed Quadrature
 * Abscissa and their corresponding Weights. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integration/README.md">R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1QuadratureEstimator
{
	private org.drip.numerical.common.Array2D _nodeWeightArray = null;
	private org.drip.numerical.integration.R1AbscissaTransform _abscissaTransform = null;

	/**
	 * R1QuadratureEstimator Constructor
	 * 
	 * @param abscissaTransform The Abscissa Transform
	 * @param nodeWeightArray Array of the Nodes and Weights
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1QuadratureEstimator (
		final org.drip.numerical.integration.R1AbscissaTransform abscissaTransform,
		final org.drip.numerical.common.Array2D nodeWeightArray)
		throws java.lang.Exception
	{
		if (null == (_abscissaTransform = abscissaTransform) ||
			null == (_nodeWeightArray = nodeWeightArray))
		{
			throw new java.lang.Exception ("R1QuadratureEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Abscissa Transform
	 * 
	 * @return The Abscissa Transform
	 */

	public org.drip.numerical.integration.R1AbscissaTransform abscissaTransform()
	{
		return _abscissaTransform;
	}

	/**
	 * Retrieve the 2D Array of Nodes and Weights
	 * 
	 * @return 2D Array of Nodes and Weights
	 */

	public org.drip.numerical.common.Array2D nodeWeightArray()
	{
		return _nodeWeightArray;
	}

	/**
	 * Integrate the Specified Integrand over the Nodes
	 * 
	 * @param r1ToR1Integrand The R<sup>1</sup> To R<sup>1</sup> Integrand
	 * 
	 * @return The Integrand Quadrature
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double integrate (
		final org.drip.function.definition.R1ToR1 r1ToR1Integrand)
		throws java.lang.Exception
	{
		if (null == r1ToR1Integrand)
		{
			throw new java.lang.Exception ("R1QuadratureEstimator::integrate => Invalid Inputs");
		}

		double[] weightArray = _nodeWeightArray.y();

		double[] abscissaArray = _nodeWeightArray.x();

		double quadrature = 0.;
		int nodeCount = abscissaArray.length;

		double quadratureScale = _abscissaTransform.quadratureScale();

		org.drip.function.definition.R1ToR1 r1PointValueScale = _abscissaTransform.pointValueScale();

		org.drip.function.definition.R1ToR1 r1ToR1VariateChange = _abscissaTransform.variateChange();

		for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex)
		{
			quadrature = quadrature + quadratureScale * weightArray[nodeIndex] *
				r1PointValueScale.evaluate (abscissaArray[nodeIndex]) *
				r1ToR1Integrand.evaluate (r1ToR1VariateChange.evaluate (abscissaArray[nodeIndex]));
		}

		return quadrature;
	}
}
