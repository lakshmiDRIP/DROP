
package org.drip.sequence.random;

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
 * <i>BoundedUniformInteger</i> implements the Bounded Uniform Distribution, with a Uniform Integer being
 * generated between a lower and an upper Bound.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/random">Random</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedUniformInteger extends org.drip.sequence.random.UnivariateSequenceGenerator {
	private int _iStart = -1;
	private int _iFinish = -1;

	private java.util.Random _rng = new java.util.Random();

	/**
	 * BoundedUniformInteger Distribution Constructor
	 * 
	 * @param iStart The Lower Bound
	 * @param iFinish The Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public BoundedUniformInteger (
		final int iStart,
		final int iFinish)
		throws java.lang.Exception
	{
		if ((_iFinish = iFinish) <= (_iStart = iStart))
			throw new java.lang.Exception ("BoundedUniformInteger constructor: Invalid inputs");
	}

	/**
	 * Retrieve the Start
	 * 
	 * @return The Start
	 */

	public int start()
	{
		return _iStart;
	}

	/**
	 * Retrieve the Finish
	 * 
	 * @return The Finish
	 */

	public int finish()
	{
		return _iFinish;
	}

	@Override public double random()
	{
		return (int) (_iStart + _rng.nextDouble() * (_iFinish - _iStart) + 0.5);
	}

	@Override public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequence (
		final int iNumEntry,
		final org.drip.measure.continuous.R1 distPopulation)
	{
		double[] adblSequence = new double[iNumEntry];

		for (int i = 0; i < iNumEntry; ++i)
			adblSequence[i] = random();

		try {
			return new org.drip.sequence.metrics.IntegerSequenceAgnosticMetrics (adblSequence, distPopulation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
