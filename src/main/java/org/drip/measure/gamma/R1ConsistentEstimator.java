
package org.drip.measure.gamma;

import org.drip.numerical.common.NumberUtil;
import org.drip.validation.evidence.R1Sample;

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
 * <i>R1ConsistentEstimator</i> implements the Mixed Type Log-Moment Parameter Estimator for a Sequence of
 * 	Observations. The References are:
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
 * 		<li>Construct and Instance of <i>R1ConsistentEstimator</i> from the Array of Realizations</li>
 * 		<li><i>R1ConsistentEstimator</i> Constructor</li>
 * 		<li>Infer the Shape-Scale Parameter from the Observations</li>
 * 		<li>Retrieve the Scale Bias Correction Factor</li>
 * 		<li>Compute the Shape Bias Correction Adjustment</li>
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

public class R1ConsistentEstimator
	extends R1ParameterEstimator
{

	/**
	 * Construct and Instance of <i>R1ConsistentEstimator</i> from the Array of Realizations
	 * 
	 * @param realizationArray The Realization Array
	 * 
	 * @return Instance of <i>R1ConsistentEstimator</i>
	 */

	public static final R1ConsistentEstimator FromRealizationArray (
		final double[] realizationArray)
	{
		try {
			return new R1ConsistentEstimator (new R1Sample (realizationArray));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1ConsistentEstimator</i> Constructor
	 * 
	 * @param sample The Sample
	 * 
	 * @throws Exception Thrown of the Inputs are Invalid
	 */

	public R1ConsistentEstimator (
		final R1Sample sample)
		throws Exception
	{
		super (sample);
	}

	/**
	 * Infer the Shape-Scale Parameter from the Observations
	 * 
	 * @return The Shape-Scale Parameter from the Observations
	 */

	public ShapeScaleParameters inferShapeScaleParameter()
	{
		double[] realizationArray = sample().realizationArray();

		double realizationLogRealizationSum = 0.;
		double logRealizationSum = 0.;
		double realizationSum = 0.;

		for (int realizationIndex = 0;
			realizationIndex < realizationArray.length;
			++realizationIndex)
		{
			double logRealization = Math.log (realizationArray[realizationIndex]);

			logRealizationSum = logRealizationSum + logRealization;
			realizationSum = realizationSum + realizationArray[realizationIndex];
			realizationLogRealizationSum = realizationLogRealizationSum +
				realizationArray[realizationIndex] * logRealization;
		}

		double nonNormalizedScale = realizationArray.length * realizationLogRealizationSum -
			logRealizationSum * realizationSum;

		try {
			return new ShapeScaleParameters (
				realizationArray.length * realizationSum / nonNormalizedScale,
				nonNormalizedScale / realizationArray.length / realizationArray.length
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Scale Bias Correction Factor
	 * 
	 * @return The Scale Bias Correction Factor
	 */

	public double scaleBiasCorrectionFactor()
	{
		int realizationCount = sample().realizationArray().length;

		return realizationCount / (realizationCount - 1);
	}

	/**
	 * Compute the Shape Bias Correction Adjustment
	 * 
	 * @param scaleEstimate The Scale Estimate
	 * 
	 * @return The Shape Bias Correction Adjustment
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double shapeBiasCorrectionAdjustment (
		final double scaleEstimate)
		throws Exception
	{
		if (!NumberUtil.IsValid (scaleEstimate)) {
			throw new Exception ("R1ConsistentEstimator::shapeBiasCorrectionAdjustment => Invalid Inputs");
		}

		double onePlusScaleEstimate = 1. + scaleEstimate;
		double scaleEstimateOver_onePlusScaleEstimate_ = scaleEstimate / onePlusScaleEstimate;

		return (
			3. * scaleEstimate
			- (2. / 3. * scaleEstimateOver_onePlusScaleEstimate_)
			+ (4. / 5. * scaleEstimateOver_onePlusScaleEstimate_ / onePlusScaleEstimate)
		) / sample().realizationArray().length;
	}
}
