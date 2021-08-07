
package org.drip.sample.gammadistribution;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.gamma.R1MaximumLikelihoodEstimator;
import org.drip.measure.gamma.R1ParameterEstimator;
import org.drip.measure.gamma.R1ShapeScaleDistribution;
import org.drip.measure.gamma.ShapeScaleParameters;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.digamma.CumulativeSeriesEstimator;
import org.drip.specialfunction.gamma.EulerIntegralSecondKind;
import org.drip.specialfunction.incompletegamma.LowerEulerIntegral;

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
 * <i>MaximumLikelihoodInference</i> illustrates the Estimate of the Gamma Distribution from the Observation
 * 	Array using the Maximum Likelihood Estimator. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Gamma Distribution (2019): Gamma Distribution
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Louzada, F., P. L. Ramos, and E. Ramos (2019): A Note on Bias of Closed-Form Estimators for the
 * 				Gamma Distribution Derived From Likelihood Equations <i>The American Statistician</i> <b>73
 * 				(2)</b> 195-199
 * 		</li>
 * 		<li>
 * 			Minka, T. (2002): Estimating a Gamma distribution https://tminka.github.io/papers/minka-gamma.pdf
 * 		</li>
 * 		<li>
 * 			Ye, Z. S., and N. Chen (2017): Closed-Form Estimators for the Gamma Distribution Derived from
 * 				Likelihood Equations <i>The American Statistician</i> <b>71 (2)</b> 177-181
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gammadistribution/README.md">R<sup>1</sup> Gamma Distribution Usage/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MaximumLikelihoodInference
{

	private static final R2ToR1 LowerIncompleteGamma()
		throws Exception
	{
		return new R2ToR1()
		{
			@Override public double evaluate (
				final double s,
				final double t)
				throws Exception
			{
				return new LowerEulerIntegral (
					null,
					t
				).evaluate (
					s
				);
			}
		};
	}

	private static final double[] Generate (
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final double k,
		final double theta,
		final int simulationCount)
		throws Exception
	{
		double[] marsagliaRandomArray = new double[simulationCount];

		R1ShapeScaleDistribution gammaDistribution = R1ShapeScaleDistribution.Standard (
			k,
			theta,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator
		);

		for (int simulationIndex = 0;
			simulationIndex < simulationCount;
			++simulationIndex)
		{
			marsagliaRandomArray[simulationIndex] = gammaDistribution.randomMarsaglia1977();
		}

		return marsagliaRandomArray;
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double[] kArray = {
			0.50,
			1.00,
			1.50,
			2.00,
			2.50,
			3.00,
			3.50,
		};
		double[] thetaArray = {
			0.50,
			1.00,
			1.50,
			2.00,
			2.50,
			3.00,
			3.50,
		};
		int simulationCount = 100000;
		int digammaTermCount = 1000;

		R1ToR1 gammaEstimator = new EulerIntegralSecondKind (
			null
		);

		R2ToR1 lowerIncompleteGammaEstimator = LowerIncompleteGamma();

		R1ToR1 digammaEstimator = CumulativeSeriesEstimator.AbramowitzStegun2007 (
			digammaTermCount
		);

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t|   SHAPE SCALE MLE INFERENCE    ||");

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t|    L -> R:                     ||");

		System.out.println ("\t|        IN => Shape             ||");

		System.out.println ("\t|        IN => Scale             ||");

		System.out.println ("\t|        OUT => Shape            ||");

		System.out.println ("\t|        OUT => Scale            ||");

		System.out.println ("\t|--------------------------------||");

		for (double k : kArray)
		{
			for (double theta : thetaArray)
			{
				R1ParameterEstimator parameterEstimator =
					R1MaximumLikelihoodEstimator.FromRealizationArray (
						Generate (
							gammaEstimator,
							digammaEstimator,
							lowerIncompleteGammaEstimator,
							k,
							theta,
							simulationCount
						)
					);

				ShapeScaleParameters shapeScaleParameters = parameterEstimator.inferShapeScaleParameter();

				System.out.println (
					"\t| [" + FormatUtil.FormatDouble (k, 1, 2, 1., false) + " | " +
					FormatUtil.FormatDouble (theta, 1, 2, 1., false) + "] => {" +
					FormatUtil.FormatDouble (shapeScaleParameters.shape(), 1, 2, 1., false) + " | " +
					FormatUtil.FormatDouble (shapeScaleParameters.scale(), 1, 2, 1., false) + "} ||"
				);
			}
		}

		System.out.println ("\t|--------------------------------||");

		EnvManager.TerminateEnv();
	}
}
