
package org.drip.measure.gamma;

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
 * <i>R1ShapeScaleComposite</i> implements the Scale-Scale Composite Measures. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gamma/README.md">R<sup>1</sup> Gamma Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ShapeScaleComposite
{

	/**
	 * Generate a Random Number that follows the F Distribution
	 * 
	 * @param gammaDistribution1 Gamma Distribution #1
	 * @param gammaDistribution2 Gamma Distribution #2
	 * 
	 * @return Random Number that follows the F Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double RandomF (
		final org.drip.measure.gamma.R1ShapeScaleDiscrete gammaDistribution1,
		final org.drip.measure.gamma.R1ShapeScaleDiscrete gammaDistribution2)
		throws java.lang.Exception
	{
		if (null == gammaDistribution1 ||
			null == gammaDistribution2)
		{
			throw new java.lang.Exception (
				"R1ShapeScaleComposite::RandomF => Invalid Inputs"
			);
		}

		org.drip.measure.gamma.ShapeScaleParameters shapeScaleParameters1 =
			gammaDistribution1.shapeScaleParameters();

		org.drip.measure.gamma.ShapeScaleParameters shapeScaleParameters2 =
			gammaDistribution2.shapeScaleParameters();

		return gammaDistribution1.random() / (
			shapeScaleParameters1.shape() * shapeScaleParameters1.scale()
		) / (gammaDistribution2.random() / (
			shapeScaleParameters2.shape() * shapeScaleParameters2.scale()
		));
	}

	/**
	 * Generate a Random Number that follows the Beta Prime Distribution
	 * 
	 * @param gammaDistribution1 Gamma Distribution #1
	 * @param gammaDistribution2 Gamma Distribution #2
	 * 
	 * @return Random Number that follows the Beta Prime Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double RandomBetaPrime (
		final org.drip.measure.gamma.R1ShapeScaleDiscrete gammaDistribution1,
		final org.drip.measure.gamma.R1ShapeScaleDiscrete gammaDistribution2)
		throws java.lang.Exception
	{
		if (null == gammaDistribution1 ||
			null == gammaDistribution2)
		{
			throw new java.lang.Exception (
				"R1ShapeScaleComposite::RandomBetaPrime => Invalid Inputs"
			);
		}

		return gammaDistribution1.random() / gammaDistribution2.random();
	}

	/**
	 * Generate a Random Number that follows the Beta Distribution
	 * 
	 * @param gammaDistribution1 Gamma Distribution #1
	 * @param gammaDistribution2 Gamma Distribution #2
	 * 
	 * @return Random Number that follows the Beta Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double RandomBeta (
		final org.drip.measure.gamma.R1ShapeScaleDiscrete gammaDistribution1,
		final org.drip.measure.gamma.R1ShapeScaleDiscrete gammaDistribution2)
		throws java.lang.Exception
	{
		if (null == gammaDistribution1 ||
			null == gammaDistribution2)
		{
			throw new java.lang.Exception (
				"R1ShapeScaleComposite::RandomBeta => Invalid Inputs"
			);
		}

		double scale = gammaDistribution1.shapeScaleParameters().scale();

		if (scale != gammaDistribution2.shapeScaleParameters().scale())
		{
			throw new java.lang.Exception (
				"R1ShapeScaleComposite::RandomBeta => Invalid Inputs"
			);
		}

		double gammaDistribution1Random = gammaDistribution1.random();

		return gammaDistribution1Random / (gammaDistribution1Random + gammaDistribution2.random());
	}

	/**
	 * Generate a Random Vector that follows the Dirichlet Distribution
	 * 
	 * @param gammaDistributionArray Gamma Distribution Array
	 * 
	 * @return Random Vector that follows the Dirichlet Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double[] RandomDirichletVector (
		final org.drip.measure.gamma.R1ShapeScaleDiscrete[] gammaDistributionArray)
	{
		if (null == gammaDistributionArray)
		{
			return null;
		}

		double dirichletSum = 0.;
		int dirichletVectorCount = gammaDistributionArray.length;
		double[] randomDirichletVector = new double[dirichletVectorCount];

		if (0 == dirichletVectorCount)
		{
			return null;
		}

		for (int dirichletVectorIndex = 0;
			dirichletVectorIndex < dirichletVectorCount;
			++dirichletVectorIndex)
		{
			if (null == gammaDistributionArray[dirichletVectorIndex] ||
				1 != gammaDistributionArray[dirichletVectorIndex].shapeScaleParameters().scale())
			{
				return null;
			}

			try
			{
				dirichletSum = dirichletSum + (
					randomDirichletVector[dirichletVectorIndex] =
					gammaDistributionArray[dirichletVectorIndex].random()
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		for (int dirichletVectorIndex = 0;
			dirichletVectorIndex < dirichletVectorCount;
			++dirichletVectorIndex)
		{
			randomDirichletVector[dirichletVectorIndex] = randomDirichletVector[dirichletVectorIndex] /
				dirichletSum;
		}

		return randomDirichletVector;
	}

	/**
	 * Compute the Kullback-Liebler Divergence for the Gamma Distribution Pair
	 * 
	 * @param gammaDistribution1 Gamma Distribution #1
	 * @param gammaDistribution2 Gamma Distribution #2
	 * 
	 * @return The Kullback-Liebler Divergence for the Gamma Distribution Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double KullbackLieblerDivergence (
		final org.drip.measure.gamma.R1ShapeScaleDistribution gammaDistribution1,
		final org.drip.measure.gamma.R1ShapeScaleDistribution gammaDistribution2)
		throws java.lang.Exception
	{
		if (null == gammaDistribution1 ||
			null == gammaDistribution2)
		{
			throw new java.lang.Exception (
				"R1ShapeScaleComposite::KullbackLieblerDivergence => Invalid Inputs"
			);
		}

		org.drip.measure.gamma.ShapeScaleParameters shapeScaleParameters1 =
			gammaDistribution1.shapeScaleParameters();

		org.drip.measure.gamma.ShapeScaleParameters shapeScaleParameters2 =
			gammaDistribution2.shapeScaleParameters();

		double scale1 = shapeScaleParameters1.scale();

		double scale2 = shapeScaleParameters2.scale();

		double shape1 = shapeScaleParameters1.shape();

		double shape2 = shapeScaleParameters2.shape();

		org.drip.function.definition.R1ToR1 gammaEstimator = gammaDistribution1.gammaEstimator();

		return (shape1 - shape2) * gammaDistribution1.digammaEstimator().evaluate (
			shape1
		) - gammaEstimator.evaluate (
			shape1
		) + gammaEstimator.evaluate (
			shape2
		) + shape2 * (
			java.lang.Math.log (
				scale2
			) - java.lang.Math.log (
				scale1
			)
		) + shape1 * (scale1 - scale2) / scale1;
	}
}
