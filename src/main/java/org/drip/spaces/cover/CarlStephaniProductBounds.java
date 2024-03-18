
package org.drip.spaces.cover;

import org.drip.spaces.functionclass.NormedRxToNormedRxFinite;
import org.drip.spaces.instance.GeneralizedValidatedVector;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>CarlStephaniProductBounds</i> implements the Bounds that result from the Convolution Product Product of
 * 	2 Normed R<sup>x</sup> To Normed R<sup>x</sup> Function Spaces. The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of
 *  			Operators in Banach Spaces <i>Annals of the Fourier Institute</i> <b>35 (3)</b> 79-118
 *  	</li>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  	<li>
 *  		Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function
 *  			Classes, in: <i>Proceedings of the 13th Annual Conference on Computational Learning
 *  				Theory</i> <b>ACM</b> New York
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>CarlStephaniProductBounds</i> Constructor</li>
 * 		<li>Retrieve the Function Class A</li>
 * 		<li>Retrieve the Function Class B</li>
 * 		<li>Compute the Upper Bound for the Entropy Number of the Operator Population Metric Covering Number Convolution Product Product across both the Function Classes</li>
 * 		<li>Compute the Upper Bound for the Entropy Number of the Operator Population Supremum Covering Number Convolution Product across both the Function Classes</li>
 * 		<li>Compute the Upper Bound for the Entropy Number of the Operator Sample Metric Covering Number Convolution Product across both the Function Classes</li>
 * 		<li>Compute the Upper Bound for the Entropy Number of the Operator Sample Supremum Covering Number Convolution Product across both the Function Classes</li>
 * 		<li>Compute the Normed Upper Entropy Convolution Product Bound across the Function Classes</li>
 * 		<li>Compute the Population Supremum Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum Population Norm</li>
 * 		<li>Compute the Population Metric Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum Population Norm</li>
 * 		<li>Compute the Sample Supremum Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum Population Norm</li>
 * 		<li>Compute the Sample Metric Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum Population Norm</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover/README.md">Vector Spaces Covering Number Estimator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CarlStephaniProductBounds
{
	private NormedRxToNormedRxFinite _functionClassRxRxA = null;
	private NormedRxToNormedRxFinite _functionClassRxRxB = null;

	/**
	 * <i>CarlStephaniProductBounds</i> Constructor
	 * 
	 * @param functionClassRxRxA Function Class A
	 * @param functionClassRxRxB Function Class B
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CarlStephaniProductBounds (
		final NormedRxToNormedRxFinite functionClassRxRxA,
		final NormedRxToNormedRxFinite functionClassRxRxB)
		throws Exception
	{
		if (null == (_functionClassRxRxA = functionClassRxRxA) ||
			null == (_functionClassRxRxB = functionClassRxRxB))
		{
			throw new Exception ("CarlStephaniProductBounds ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Function Class A
	 * 
	 * @return The Function Class A
	 */

	public NormedRxToNormedRxFinite funcClassA()
	{
		return _functionClassRxRxA;
	}

	/**
	 * Retrieve the Function Class B
	 * 
	 * @return The Function Class B
	 */

	public NormedRxToNormedRxFinite funcClassB()
	{
		return _functionClassRxRxB;
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Population Metric Covering Number
	 * 	Convolution Product Product across both the Function Classes
	 * 
	 * @param entropyNumberIndexA Entropy Number Index for Class A
	 * @param entropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Population Metric Covering Number
	 * 	Convolution Product Product across both the Function Classes
	 * 
	 * @throws Exception Thrown if the Convolution Product Product Population Metric Entropy Number cannot be
	 *  computed
	 */

	public double populationMetricEntropyNumber (
		final int entropyNumberIndexA,
		final int entropyNumberIndexB)
		throws Exception
	{
		return CoveringBoundsHelper.CarlStephaniProductBound (
			_functionClassRxRxA.populationMetricCoveringBounds(),
			_functionClassRxRxB.populationMetricCoveringBounds(),
			entropyNumberIndexA,
			entropyNumberIndexB
		);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Population Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @param entropyNumberIndexA Entropy Number Index for Class A
	 * @param entropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Population Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @throws Exception Thrown if the Convolution Product Population Supremum Dyadic Entropy cannot be
	 * 	Computed
	 */

	public double populationSupremumEntropyNumber (
		final int entropyNumberIndexA,
		final int entropyNumberIndexB)
		throws Exception
	{
		return CoveringBoundsHelper.CarlStephaniProductBound (
			_functionClassRxRxA.populationSupremumCoveringBounds(),
			_functionClassRxRxB.populationSupremumCoveringBounds(),
			entropyNumberIndexA,
			entropyNumberIndexB
		);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Sample Metric Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @param generalizedValidatedVectorA The Validated Input Vector Space Instance for Class A
	 * @param generalizedValidatedVectorB The Validated Input Vector Space Instance for Class B
	 * @param entropyNumberIndexA Entropy Number Index for Class A
	 * @param entropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Sample Metric Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @throws Exception Thrown if the Convolution Product Sample Metric Entropy Number cannot be
	 * 	Computed
	 */

	public double sampleMetricEntropyNumber (
		final GeneralizedValidatedVector generalizedValidatedVectorA,
		final GeneralizedValidatedVector generalizedValidatedVectorB,
		final int entropyNumberIndexA,
		final int entropyNumberIndexB)
		throws Exception
	{
		return CoveringBoundsHelper.CarlStephaniProductBound (
			_functionClassRxRxA.sampleMetricCoveringBounds (generalizedValidatedVectorA),
			_functionClassRxRxB.sampleMetricCoveringBounds (generalizedValidatedVectorB),
			entropyNumberIndexA,
			entropyNumberIndexB
		);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Sample Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @param generalizedValidatedVectorA The Validated Input Vector Space Instance for Class A
	 * @param generalizedValidatedVectorB The Validated Input Vector Space Instance for Class B
	 * @param entropyNumberIndexA Entropy Number Index for Class A
	 * @param entropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Sample Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @throws Exception Thrown if the Convolution Product Sample Supremum Entropy Number cannot be
	 * 	Computed
	 */

	public double sampleSupremumEntropyNumber (
		final GeneralizedValidatedVector generalizedValidatedVectorA,
		final GeneralizedValidatedVector generalizedValidatedVectorB,
		final int entropyNumberIndexA,
		final int entropyNumberIndexB)
		throws java.lang.Exception
	{
		return CoveringBoundsHelper.CarlStephaniProductBound (
			_functionClassRxRxA.sampleSupremumCoveringBounds (generalizedValidatedVectorA),
			_functionClassRxRxB.sampleSupremumCoveringBounds (generalizedValidatedVectorB),
			entropyNumberIndexA,
			entropyNumberIndexB
		);
	}

	/**
	 * Compute the Normed Upper Entropy Convolution Product Bound across the Function Classes
	 * 
	 * @param maureyOperatorCoveringBoundsA The Maurey Operator Covering Bounds for Class A
	 * @param maureyOperatorCoveringBoundsB The Maurey Operator Covering Bounds for Class B
	 * @param entropyNumberIndex Entropy Number Index for either Class
	 * @param useSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Normed Upper Entropy Convolution Product Bound across the Function Classes
	 */

	public CarlStephaniNormedBounds normedEntropyUpperBound (
		final MaureyOperatorCoveringBounds maureyOperatorCoveringBoundsA,
		final MaureyOperatorCoveringBounds maureyOperatorCoveringBoundsB,
		final int entropyNumberIndex,
		final boolean useSupremumNorm)
	{
		try {
			return CoveringBoundsHelper.CarlStephaniProductNorm (
				maureyOperatorCoveringBoundsA,
				maureyOperatorCoveringBoundsB,
				useSupremumNorm ? _functionClassRxRxA.operatorPopulationSupremumNorm() :
					_functionClassRxRxA.operatorPopulationMetricNorm(),
				useSupremumNorm ? _functionClassRxRxB.operatorPopulationSupremumNorm() :
					_functionClassRxRxB.operatorPopulationMetricNorm(),
				entropyNumberIndex
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Population Supremum Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 *  
	 * @param entropyNumberIndex Entropy Number Index for either Class
	 * @param useSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Population Supremum Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 */

	public CarlStephaniNormedBounds populationSupremumEntropyNorm (
		final int entropyNumberIndex,
		final boolean useSupremumNorm)
	{
		return normedEntropyUpperBound (
			_functionClassRxRxA.populationSupremumCoveringBounds(),
			_functionClassRxRxB.populationSupremumCoveringBounds(),
			entropyNumberIndex,
			useSupremumNorm
		);
	}

	/**
	 * Compute the Population Metric Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 *  
	 * @param entropyNumberIndex Entropy Number Index for either Class
	 * @param useSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Population Metric Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 */

	public CarlStephaniNormedBounds populationMetricEntropyNorm (
		final int entropyNumberIndex,
		final boolean useSupremumNorm)
	{
		return normedEntropyUpperBound (
			_functionClassRxRxA.populationMetricCoveringBounds(),
			_functionClassRxRxB.populationMetricCoveringBounds(),
			entropyNumberIndex,
			useSupremumNorm
		);
	}

	/**
	 * Compute the Sample Supremum Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 *  
	 * @param generalizedValidatedVectorA The Validated Input Vector Space Instance for Class A
	 * @param generalizedValidatedVectorB The Validated Input Vector Space Instance for Class B
	 * @param entropyNumberIndex Entropy Number Index for either Class
	 * @param useSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Sample Supremum Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 */

	public CarlStephaniNormedBounds sampleSupremumEntropyNorm (
		final GeneralizedValidatedVector generalizedValidatedVectorA,
		final GeneralizedValidatedVector generalizedValidatedVectorB,
		final int entropyNumberIndex,
		final boolean useSupremumNorm)
	{
		return normedEntropyUpperBound (
			_functionClassRxRxA.sampleSupremumCoveringBounds (generalizedValidatedVectorA),
			_functionClassRxRxB.sampleSupremumCoveringBounds (generalizedValidatedVectorB),
			entropyNumberIndex,
			useSupremumNorm
		);
	}

	/**
	 * Compute the Sample Metric Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 *  
	 * @param generalizedValidatedVectorA The Validated Input Vector Space Instance for Class A
	 * @param generalizedValidatedVectorB The Validated Input Vector Space Instance for Class B
	 * @param entropyNumberIndex Entropy Number Index for either Class
	 * @param useSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Sample Metric Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 */

	public CarlStephaniNormedBounds sampleMetricEntropyNorm (
		final GeneralizedValidatedVector generalizedValidatedVectorA,
		final GeneralizedValidatedVector generalizedValidatedVectorB,
		final int entropyNumberIndex,
		final boolean useSupremumNorm)
	{
		return normedEntropyUpperBound (
			_functionClassRxRxA.sampleMetricCoveringBounds (generalizedValidatedVectorA),
			_functionClassRxRxB.sampleMetricCoveringBounds (generalizedValidatedVectorB),
			entropyNumberIndex,
			useSupremumNorm
		);
	}
}
