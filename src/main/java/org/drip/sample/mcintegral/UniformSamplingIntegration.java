
package org.drip.sample.mcintegral;

import org.drip.function.definition.RdToR1;
import org.drip.measure.distribution.RdContinuousUniform;
import org.drip.numerical.rdintegration.QuadratureSetting;
import org.drip.numerical.rdintegration.RectangularManifold;
import org.drip.numerical.rdintegration.MonteCarloRunUniformDiagnostics;
import org.drip.numerical.rdintegration.UniformSamplingIntegrator;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
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
 * <i>UniformSamplingIntegration</i> illustrates Uniformly Sampled Monte-Carlo Integration of R<sup>d</sup>
 *  To R<sup>1</sup> Objective Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Kroese, D. P., T. Taimre, and Z. I. Botev (2011): <i>Handbook of Monte Carlo Methods</i>
 * 				<b>John Wiley and Sons</b> Hoboken NJ
 * 		</li>
 * 		<li>
 * 			MacKay, D. (2003): <i>Information Theory, Inference, and Learning Algorithms</i> <b>Cambridge
 * 				University Press</b> New York NY
 * 		</li>
 * 		<li>
 * 			Newman, M. E. J., and G. T. Barkema (1999): <i>Monte Carlo Methods in Statistical Physics</i>
 * 				<b>Oxford University Press</b> Oxford UK
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, B. P. Flannery (2007): <i>Numerical Recipes: The
 * 				Art of Scientific Computing 3<sup>rd</sup> Edition</i> <b>Cambridge University Press</b> New
 * 				York NY
 * 		</li>
 * 		<li>
 * 			Wikipedia (2025): Monte Carlo Integration https://en.wikipedia.org/wiki/Monte_Carlo_integration
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/mcintegral/README.md">R<sup>d</sup> to R<sup>1</sup> Numerical Monte-Carlo Integration</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UniformSamplingIntegration
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int samplingPointCount = 10000;

		double[] leftBoundArray = new double[] {
			0.,
			0.
		};

		double[] rightBoundArray = new double[] {
			1.,
			1.
		};

		RdToR1 integrand = new RdToR1 (null)
		{
			@Override public int dimension()
			{
				return 2;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] * variateArray[1];
			}
		};

		System.out.println ("\n\t|----------------------------------------------------|");

		System.out.println ("\n\t| 2D MONTE CARLO UNIFORM SAMPLING INTEGRATION INPUTS |");

		System.out.println ("\n\t|----------------------------------------------------|");

		System.out.println ("\t| Sampling Point Count => " + samplingPointCount);

		System.out.println (
			"\t| Variate #1 Bound     => {" +
				FormatUtil.FormatDouble (leftBoundArray[0], 1, 0, 1.) + " -"  +
				FormatUtil.FormatDouble (rightBoundArray[0], 1, 0, 1.) + "}"
		);

		System.out.println (
			"\t| Variate #2 Bound     => {" +
				FormatUtil.FormatDouble (leftBoundArray[1], 1, 0, 1.) + " -"  +
				FormatUtil.FormatDouble (rightBoundArray[1], 1, 0, 1.) + "}"
		);

		System.out.println ("\t|----------------------------------------------------|");

		UniformSamplingIntegrator uniformSamplingIntegrator = new UniformSamplingIntegrator (
			new QuadratureSetting (integrand, new RectangularManifold (leftBoundArray, rightBoundArray)),
			samplingPointCount,
			new RdContinuousUniform (leftBoundArray, rightBoundArray),
			true
		);

		MonteCarloRunUniformDiagnostics rdToR1MonteCarloRunDiagnostics =
			(MonteCarloRunUniformDiagnostics) uniformSamplingIntegrator.quadratureRun();

		System.out.println ("\n\t|-------------------------------------------------|");

		System.out.println ("\t| 2D MONTE CARLO UNIFORM SAMPLING INTEGRATION RUN |");

		System.out.println ("\t|-------------------------------------------------|");

		System.out.println (
			"\t| Integrand Mean              => " + FormatUtil.FormatDouble (
				rdToR1MonteCarloRunDiagnostics.integrandMean(),
				1,
				4,
				1.
			)
		);

		System.out.println (
			"\t| Integrand Volume            => " + FormatUtil.FormatDouble (
				rdToR1MonteCarloRunDiagnostics.integrandVolume(),
				1,
				4,
				1.
			)
		);

		System.out.println (
			"\t| Unbiased Integrand Variance => " + FormatUtil.FormatDouble (
				rdToR1MonteCarloRunDiagnostics.integrandVolume(),
				1,
				4,
				1.
			)
		);

		System.out.println (
			"\t| Quadrature Value (Estimate) => " + FormatUtil.FormatDouble (
				rdToR1MonteCarloRunDiagnostics.quadratureValue(),
				1,
				4,
				1.
			)
		);

		System.out.println ("\t| Quadrature Value (Actual)   =>  0.2500");

		System.out.println (
			"\t| Quadrature Variance         => " + FormatUtil.FormatDouble (
				rdToR1MonteCarloRunDiagnostics.quadratureVariance(),
				1,
				9,
				1.
			)
		);

		System.out.println (
			"\t| Quadrature Error            => " + FormatUtil.FormatDouble (
				rdToR1MonteCarloRunDiagnostics.quadratureError(),
				1,
				9,
				1.
			)
		);

		System.out.println (
			"\t| Integrand Sample Array Size =>  " +
				rdToR1MonteCarloRunDiagnostics.integrandSampleArray().length
		);

		System.out.println ("\t|-------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
