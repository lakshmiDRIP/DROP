
package org.drip.measure.gamma;

import org.drip.function.definition.R1ToR1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>ConjugateShapeScalePrior</i> implements the Determinants of the Parameters of the Conjugate Prior for
 * 	the Shape and the Scale Parameters. The References are:
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
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>ConjugateShapePrior</i> Constructor</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gamma/README.md">R<sup>1</sup> Gamma Distribution Implementation/Properties</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConjugateShapeScalePrior
{
	private R1ToR1 _gammaEstimator = null;
	private ConjugateScalePrior _conjugateScalePrior = null;
	private ConjugateShapePrior _conjugateShapePrior = null;

	/**
	 * ConjugateShapeScalePrior Constructor
	 * 
	 * @param conjugateShapePrior The Conjugate Shape Prior
	 * @param conjugateScalePrior The Conjugate Scale Prior
	 * @param gammaEstimator The Gamma Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConjugateShapeScalePrior (
		final org.drip.measure.gamma.ConjugateShapePrior conjugateShapePrior,
		final org.drip.measure.gamma.ConjugateScalePrior conjugateScalePrior,
		final org.drip.function.definition.R1ToR1 gammaEstimator)
		throws java.lang.Exception
	{
		if (null == (_conjugateShapePrior = conjugateShapePrior) ||
			null == (_conjugateScalePrior = conjugateScalePrior) ||
			null == (_gammaEstimator = gammaEstimator)
		)
		{
			throw new java.lang.Exception (
				"ConjugateShapeScalePrior Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Conjugate Shape Prior
	 * 
	 * @return The Conjugate Shape Prior
	 */

	public ConjugateShapePrior conjugateShapePrior()
	{
		return _conjugateShapePrior;
	}

	/**
	 * Retrieve the Conjugate Scale Prior
	 * 
	 * @return The Conjugate Scale Prior
	 */

	public ConjugateScalePrior conjugateScalePrior()
	{
		return _conjugateScalePrior;
	}

	/**
	 * Retrieve the Gamma Estimator
	 * 
	 * @return Gamma Estimator
	 */

	public R1ToR1 gammaEstimator()
	{
		return _gammaEstimator;
	}

	/**
	 * Compute the Conjugate Shape-Scale Unnormalized Prior Probability
	 * 
	 * @return The Conjugate Shape-Scale Unnormalized Prior Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double unnormalizedPriorProbability()
		throws java.lang.Exception
	{
		double scaleEstimate = _conjugateScalePrior.parameterEstimate();

		double shapeEstimate = _conjugateShapePrior.parameterEstimate();

		return java.lang.Math.pow (
			_conjugateShapePrior.observationProduct(),
			shapeEstimate - 1.
		) * java.lang.Math.exp (
			-1. * _conjugateScalePrior.observationSum() / scaleEstimate
		) * java.lang.Math.pow (
			_gammaEstimator.evaluate (
				shapeEstimate
			),
			-1. * _conjugateShapePrior.observationCount()
		) * java.lang.Math.pow (
			scaleEstimate,
			-1. * shapeEstimate * _conjugateScalePrior.observationCount()
		);
	}

	/**
	 * Perform an Bayes' Update of the Conjugate Prior from the Sample
	 * 
	 * @param sample The Sample
	 * 
	 * @return TRUE - Bayes' Update of the Conjugate Prior from the Sample completed successfully
	 */

	public boolean bayesUpdate (
		final org.drip.validation.evidence.R1Sample sample)
	{
		return _conjugateScalePrior.bayesUpdate (
			sample
		) && _conjugateShapePrior.bayesUpdate (
			sample
		);
	}
}
