
package org.drip.measure.generators;

import org.drip.function.definition.RdToR1;
import org.drip.measure.distribution.RdContinuous;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>RdMomentGeneratingFunction</i> implements the <i>RdToR1</i> Moment Generating Function. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bulmer, M. G. (1979): <i>Principles of Statistics</i> <b>Dover</b> Garden City NY
 * 		</li>
 * 		<li>
 * 			Casella, G., and R. L. Berger (2002): <i>Statistical Inference 2<sup>nd</sup> Edition</i>
 * 				<b>Thompson Learning</b> Novato CA
 * 		</li>
 * 		<li>
 * 			Wikipedia (2025): Moment Generating Function
 * 				https://en.wikipedia.org/wiki/Moment-generating_function
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>RdMomentGeneratingFunction</i> Constructor</li>
 * 		<li>Retrieve the Underlying R<sup>d</sup> Distribution</li>
 * 		<li>Retrieve the State Dimension</li>
 * 		<li>Evaluate the Moment Generating Function at t</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/generators/README.md">R<sup>1</sup>/R<sup>d</sup> Moment/Probability Generating Functions</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdMomentGeneratingFunction
	extends RdToR1
{
	private RdContinuous _distribution = null;

	/**
	 * <i>RdMomentGeneratingFunction</i> Constructor
	 * 
	 * @param distribution Underlying R<sup>1</sup> Distribution
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdMomentGeneratingFunction (
		final RdContinuous distribution)
		throws Exception
	{
		super (null);

		if (null == (_distribution = distribution)) {
			throw new Exception ("RdMomentGeneratingFunction Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Underlying R<sup>d</sup> Distribution
	 * 
	 * @return Underlying R<sup>d</sup> Distribution
	 */

	public RdContinuous distribution()
	{
		return _distribution;
	}

	/**
	 * Retrieve the State Dimension
	 * 
	 * @return The State Dimension
	 */

	@Override public int dimension()
	{
		return _distribution.dimension();
	}

	/**
	 * Evaluate the Moment Generating Function at t
	 * 
	 * @param tArray MGF "t" Array
	 * 
	 * @return Moment Generating Function evaluated at t
	 * 
	 * @throws Exception Thrown if the Moment Generating Function cannot be evaluated
	 */

	@Override public double evaluate (
		final double[] tArray)
		throws Exception
	{
		if (null == tArray || dimension() != tArray.length || !NumberUtil.IsValid (tArray)) {
			throw new Exception ("R1MomentGeneratingFunction::evaluate => t is Invalid");
		}

		double tAbsolute = 0.;

		for (int i = 0; i < tArray.length; ++i) {
			tAbsolute += tArray[i] * tArray[i];
		}

		if (0. == tAbsolute) {
			return 1.;
		}

		throw new Exception ("R1MomentGeneratingFunction::evaluate => Not implemented");

		/* return _distribution.quadratureEstimator().integrate (
			new RdToR1 (null)
			{
				@Override public int dimension()
				{
					return _distribution.dimension();
				}

				@Override public double evaluate (
					final double[] xArray)
					throws Exception
				{
					double tDotX = 0.;

					for (int i = 0; i < tArray.length; ++i) {
						tDotX += tArray[i] * xArray[i];
					}

					return _distribution.density (xArray) * Math.exp (tDotX);
				}
			}
		); */
	}
}
