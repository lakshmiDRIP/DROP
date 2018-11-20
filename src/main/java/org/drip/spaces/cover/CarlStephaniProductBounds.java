
package org.drip.spaces.cover;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>CarlStephaniProductBounds</i> implements the Bounds that result from the Convolution Product Product of
 * 2 Normed R<sup>x</sup> To Normed R<sup>x</sup> Function Spaces. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover">Covering Number</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CarlStephaniProductBounds {
	private org.drip.spaces.functionclass.NormedRxToNormedRxFinite _funcClassRxRxA = null;
	private org.drip.spaces.functionclass.NormedRxToNormedRxFinite _funcClassRxRxB = null;

	/**
	 * CarlStephaniProductBounds Constructor
	 * 
	 * @param funcClassRxRxA Function Class A
	 * @param funcClassRxRxB Function Class B
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CarlStephaniProductBounds (
		final org.drip.spaces.functionclass.NormedRxToNormedRxFinite funcClassRxRxA,
		final org.drip.spaces.functionclass.NormedRxToNormedRxFinite funcClassRxRxB)
		throws java.lang.Exception
	{
		if (null == (_funcClassRxRxA = funcClassRxRxA) || null == (_funcClassRxRxB = funcClassRxRxB))
			throw new java.lang.Exception ("CarlStephaniProductBounds ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Function Class A
	 * 
	 * @return The Function Class A
	 */

	public org.drip.spaces.functionclass.NormedRxToNormedRxFinite funcClassA()
	{
		return _funcClassRxRxA;
	}

	/**
	 * Retrieve the Function Class B
	 * 
	 * @return The Function Class B
	 */

	public org.drip.spaces.functionclass.NormedRxToNormedRxFinite funcClassB()
	{
		return _funcClassRxRxB;
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Population Metric Covering Number
	 * 	Convolution Product Product across both the Function Classes
	 * 
	 * @param iEntropyNumberIndexA Entropy Number Index for Class A
	 * @param iEntropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Population Metric Covering Number
	 * 	Convolution Product Product across both the Function Classes
	 * 
	 * @throws java.lang.Exception Thrown if the Convolution Product Product Population Metric Entropy Number cannot be
	 * 	Computed
	 */

	public double populationMetricEntropyNumber (
		final int iEntropyNumberIndexA,
		final int iEntropyNumberIndexB)
		throws java.lang.Exception
	{
		return org.drip.spaces.cover.CoveringBoundsHelper.CarlStephaniProductBound
			(_funcClassRxRxA.populationMetricCoveringBounds(),
				_funcClassRxRxB.populationMetricCoveringBounds(), iEntropyNumberIndexA,
					iEntropyNumberIndexB);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Population Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @param iEntropyNumberIndexA Entropy Number Index for Class A
	 * @param iEntropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Population Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @throws java.lang.Exception Thrown if the Convolution Product Population Supremum Dyadic Entropy cannot be
	 * 	Computed
	 */

	public double populationSupremumEntropyNumber (
		final int iEntropyNumberIndexA,
		final int iEntropyNumberIndexB)
		throws java.lang.Exception
	{
		return org.drip.spaces.cover.CoveringBoundsHelper.CarlStephaniProductBound
			(_funcClassRxRxA.populationSupremumCoveringBounds(),
				_funcClassRxRxB.populationSupremumCoveringBounds(), iEntropyNumberIndexA,
					iEntropyNumberIndexB);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Sample Metric Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @param gvviA The Validated Input Vector Space Instance for Class A
	 * @param gvviB The Validated Input Vector Space Instance for Class B
	 * @param iEntropyNumberIndexA Entropy Number Index for Class A
	 * @param iEntropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Sample Metric Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @throws java.lang.Exception Thrown if the Convolution Product Sample Metric Entropy Number cannot be
	 * 	Computed
	 */

	public double sampleMetricEntropyNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviA,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviB,
		final int iEntropyNumberIndexA,
		final int iEntropyNumberIndexB)
		throws java.lang.Exception
	{
		return org.drip.spaces.cover.CoveringBoundsHelper.CarlStephaniProductBound
			(_funcClassRxRxA.sampleMetricCoveringBounds (gvviA),
				_funcClassRxRxB.sampleMetricCoveringBounds (gvviB), iEntropyNumberIndexA,
					iEntropyNumberIndexB);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Sample Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @param gvviA The Validated Input Vector Space Instance for Class A
	 * @param gvviB The Validated Input Vector Space Instance for Class B
	 * @param iEntropyNumberIndexA Entropy Number Index for Class A
	 * @param iEntropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Sample Supremum Covering Number
	 * 	Convolution Product across both the Function Classes
	 * 
	 * @throws java.lang.Exception Thrown if the Convolution Product Sample Supremum Entropy Number cannot be
	 * 	Computed
	 */

	public double sampleSupremumEntropyNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviA,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviB,
		final int iEntropyNumberIndexA,
		final int iEntropyNumberIndexB)
		throws java.lang.Exception
	{
		return org.drip.spaces.cover.CoveringBoundsHelper.CarlStephaniProductBound
			(_funcClassRxRxA.sampleSupremumCoveringBounds (gvviA),
				_funcClassRxRxB.sampleSupremumCoveringBounds (gvviB), iEntropyNumberIndexA,
					iEntropyNumberIndexB);
	}

	/**
	 * Compute the Normed Upper Entropy Convolution Product Bound across the Function Classes
	 * 
	 * @param mocbA The Maurey Operator Covering Bounds for Class A
	 * @param mocbB The Maurey Operator Covering Bounds for Class B
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * @param bUseSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Normed Upper Entropy Convolution Product Bound across the Function Classes
	 */

	public org.drip.spaces.cover.CarlStephaniNormedBounds normedEntropyUpperBound (
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbA,
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbB,
		final int iEntropyNumberIndex,
		final boolean bUseSupremumNorm)
	{
		try {
			return org.drip.spaces.cover.CoveringBoundsHelper.CarlStephaniProductNorm (mocbA, mocbB,
				bUseSupremumNorm ? _funcClassRxRxA.operatorPopulationSupremumNorm() :
					_funcClassRxRxA.operatorPopulationMetricNorm(), bUseSupremumNorm ?
						_funcClassRxRxB.operatorPopulationSupremumNorm() :
							_funcClassRxRxB.operatorPopulationMetricNorm(), iEntropyNumberIndex);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Population Supremum Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 *  
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * @param bUseSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Population Supremum Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 */

	public org.drip.spaces.cover.CarlStephaniNormedBounds populationSupremumEntropyNorm (
		final int iEntropyNumberIndex,
		final boolean bUseSupremumNorm)
	{
		return normedEntropyUpperBound (_funcClassRxRxA.populationSupremumCoveringBounds(),
			_funcClassRxRxB.populationSupremumCoveringBounds(), iEntropyNumberIndex, bUseSupremumNorm);
	}

	/**
	 * Compute the Population Metric Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 *  
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * @param bUseSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Population Metric Carl-Stephani Entropy Number Upper Bound using either the
	 *  Metric/Supremum Population Norm
	 */

	public org.drip.spaces.cover.CarlStephaniNormedBounds populationMetricEntropyNorm (
		final int iEntropyNumberIndex,
		final boolean bUseSupremumNorm)
	{
		return normedEntropyUpperBound (_funcClassRxRxA.populationMetricCoveringBounds(),
			_funcClassRxRxB.populationMetricCoveringBounds(), iEntropyNumberIndex, bUseSupremumNorm);
	}

	/**
	 * Compute the Sample Supremum Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 *  
	 * @param gvviA The Validated Input Vector Space Instance for Class A
	 * @param gvviB The Validated Input Vector Space Instance for Class B
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * @param bUseSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Sample Supremum Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 */

	public org.drip.spaces.cover.CarlStephaniNormedBounds sampleSupremumEntropyNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviA,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviB,
		final int iEntropyNumberIndex,
		final boolean bUseSupremumNorm)
	{
		return normedEntropyUpperBound (_funcClassRxRxA.sampleSupremumCoveringBounds (gvviA),
			_funcClassRxRxB.sampleSupremumCoveringBounds (gvviB), iEntropyNumberIndex, bUseSupremumNorm);
	}

	/**
	 * Compute the Sample Metric Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 *  
	 * @param gvviA The Validated Input Vector Space Instance for Class A
	 * @param gvviB The Validated Input Vector Space Instance for Class B
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * @param bUseSupremumNorm TRUE/FALSE - Use the Supremum/Metric Bound as the Operator Function Class
	 * 
	 * @return The Sample Metric Carl-Stephani Entropy Number Upper Bound using either the Metric/Supremum
	 *  Population Norm
	 */

	public org.drip.spaces.cover.CarlStephaniNormedBounds sampleMetricEntropyNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviA,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviB,
		final int iEntropyNumberIndex,
		final boolean bUseSupremumNorm)
	{
		return normedEntropyUpperBound (_funcClassRxRxA.sampleMetricCoveringBounds (gvviA),
			_funcClassRxRxB.sampleMetricCoveringBounds (gvviB), iEntropyNumberIndex, bUseSupremumNorm);
	}
}
