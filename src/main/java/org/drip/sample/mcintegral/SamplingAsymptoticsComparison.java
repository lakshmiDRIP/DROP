
package org.drip.sample.mcintegral;

import org.drip.function.definition.RdToR1;
import org.drip.measure.distribution.RdContinuousUniform;
import org.drip.numerical.rdintegration.MonteCarloRun;
import org.drip.numerical.rdintegration.QuadratureSetting;
import org.drip.numerical.rdintegration.RectangularManifold;
import org.drip.numerical.rdintegration.RecursiveStratifiedSamplingIntegrator;
import org.drip.numerical.rdintegration.UniformSamplingIntegrator;
import org.drip.numerical.rdintegration.VarianceSamplingSetting;
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
 * <i>SamplingAsymptoticsComparison</i> Compares the Asymptotics of Stratified vs. Uniform Sampling
 * 	Monte-Carlo Integration of R<sup>d</sup> to R<sup>1</sup> Objective Function. The References are:
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

public class SamplingAsymptoticsComparison
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

		int zoneIterationCount = 5;
		int inDimensionEstimationPointCount = 5;
		int outOfDimensionEstimationPointCount = 5;

		double[] leftBoundArray = new double[] {
			0.,
			0.,
			0.
		};

		double[] rightBoundArray = new double[] {
			1.,
			9.,
			4.
		};

		RdToR1 integrand = new RdToR1 (null)
		{
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] *
					variateArray[1] * variateArray[1] * variateArray[1] *
					variateArray[2] * variateArray[2];
			}
		};

		int[] samplingPointCountArray = {
			100,
			1000,
			10000,
			100000,
			1000000
		};

		QuadratureSetting quadratureSetting = new QuadratureSetting (
			integrand,
			new RectangularManifold (leftBoundArray, rightBoundArray)
		);

		RdContinuousUniform rdContinuousUniform = new RdContinuousUniform (leftBoundArray, rightBoundArray);

		VarianceSamplingSetting equiVarianceSamplingSetting = new VarianceSamplingSetting (
			zoneIterationCount,
			inDimensionEstimationPointCount,
			outOfDimensionEstimationPointCount,
			VarianceSamplingSetting.EQUI_QUADRATURE_ZONE_SAMPLING
		);

		VarianceSamplingSetting miserVarianceSamplingSetting = new VarianceSamplingSetting (
			zoneIterationCount,
			inDimensionEstimationPointCount,
			outOfDimensionEstimationPointCount,
			VarianceSamplingSetting.MISER_QUADRATURE_ZONE_SAMPLING
		);

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\t|             UNIFORM VS. STRATIFIED SAMPLE ASYMPTOTE               ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\t| L -> R:                                                           ||");

		System.out.println ("\t|   - Sample Size                                                   ||");

		System.out.println ("\t|   - Uniform Integrand Value                                       ||");

		System.out.println ("\t|   - EQUI Stratified Integrand Value                               ||");

		System.out.println ("\t|   - MISER Stratified Integrand Value                              ||");

		System.out.println ("\t|   - Uniform Integrand Error                                       ||");

		System.out.println ("\t|   - EQUI Stratified Integrand Error                               ||");

		System.out.println ("\t|   - MISER Stratified Integrand Error                              ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		for (int samplingPointCount : samplingPointCountArray) {
			MonteCarloRun uniformMonteCarloRun = new UniformSamplingIntegrator (
				quadratureSetting,
				samplingPointCount,
				rdContinuousUniform,
				false
			).quadratureRun();

			MonteCarloRun equiStratifiedMonteCarloRun = new RecursiveStratifiedSamplingIntegrator (
				quadratureSetting,
				equiVarianceSamplingSetting,
				samplingPointCount,
				rdContinuousUniform,
				false
			).quadratureRun();

			MonteCarloRun miserStratifiedMonteCarloRun = new RecursiveStratifiedSamplingIntegrator (
				quadratureSetting,
				miserVarianceSamplingSetting,
				samplingPointCount,
				rdContinuousUniform,
				false
			).quadratureRun();

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (samplingPointCount, 7, 0, 1.) + " =>" +
					FormatUtil.FormatDouble (uniformMonteCarloRun.quadratureValue(), 5, 1, 1.) + " |" +
					FormatUtil.FormatDouble (equiStratifiedMonteCarloRun.quadratureValue(), 5, 1, 1.) + " |" +
					FormatUtil.FormatDouble (miserStratifiedMonteCarloRun.quadratureValue(), 5, 1, 1.) + " |" +
					FormatUtil.FormatDouble (uniformMonteCarloRun.quadratureErrorPercent(), 2, 2, 100) +
						"% |" +
					FormatUtil.FormatDouble (equiStratifiedMonteCarloRun.quadratureErrorPercent(), 2, 2, 100) +
						"% |" +
					FormatUtil.FormatDouble (miserStratifiedMonteCarloRun.quadratureErrorPercent(), 2, 2, 100) +
						"% ||"
			);
		}

		System.out.println ("\t|-------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
