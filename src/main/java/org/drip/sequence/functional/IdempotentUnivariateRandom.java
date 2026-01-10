
package org.drip.sequence.functional;

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
 * <i>IdempotentUnivariateRandom</i> contains the Implementation of the OffsetIdempotent Objective Function
 * dependent on Univariate Random Variable.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/functional">Functional</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IdempotentUnivariateRandom extends org.drip.function.r1tor1operator.OffsetIdempotent {
	private org.drip.measure.distribution.R1Continuous _dist = null;

	/**
	 * IdempotentUnivariateRandom Constructor
	 * 
	 * @param dblOffset The Idempotent Offset
	 * @param dist The Underlying Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public IdempotentUnivariateRandom (
		final double dblOffset,
		final org.drip.measure.distribution.R1Continuous dist)
		throws java.lang.Exception
	{
		super (dblOffset);

		_dist = dist;
	}

	/**
	 * Generate the Function Metrics for the specified Variate Sequence and its corresponding Weight
	 * 
	 * @param adblVariateSequence The specified Variate Sequence
	 * @param adblVariateWeight The specified Variate Weight
	 * 
	 * @return The Function Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequenceMetrics (
		final double[] adblVariateSequence,
		final double[] adblVariateWeight)
	{
		if (null == adblVariateSequence || null == adblVariateWeight) return null;

		int iNumVariate = adblVariateSequence.length;
		double[] adblFunctionSequence = new double[iNumVariate];

		if (0 == iNumVariate || iNumVariate != adblVariateWeight.length) return null;

		try {
			for (int i = 0; i < iNumVariate; ++i)
				adblFunctionSequence[i] = adblVariateWeight[i] * evaluate (adblVariateSequence[i]);

			return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics (adblFunctionSequence, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Function Metrics for the specified Variate Sequence
	 * 
	 * @param adblVariateSequence The specified Variate Sequence
	 * 
	 * @return The Function Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequenceMetrics (
		final double[] adblVariateSequence)
	{
		if (null == adblVariateSequence) return null;

		int iNumVariate = adblVariateSequence.length;
		double[] adblVariateWeight = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVariateWeight[i] = 1.;

		return sequenceMetrics (adblVariateSequence, adblVariateWeight);
	}

	/**
	 * Generate the Function Metrics using the Underlying Variate Distribution
	 * 
	 * @return The Function Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequenceMetrics()
	{
		if (null == _dist) return null;

		org.drip.numerical.common.Array2D a2DHistogram = _dist.histogram();

		return null == a2DHistogram ? null : sequenceMetrics (a2DHistogram.x(), a2DHistogram.y());
	}

	/**
	 * Retrieve the Underlying Distribution
	 * 
	 * @return The Underlying Distribution
	 */

	public org.drip.measure.distribution.R1Continuous underlyingDistribution()
	{
		return _dist;
	}
}
