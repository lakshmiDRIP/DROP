
package org.drip.sequence.metrics;

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
 * <i>PoissonSequenceAgnosticMetrics</i> contains the Sample Distribution Metrics and Agnostic Bounds related
 * to the specified Poisson Sequence.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/metrics">Metrics</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PoissonSequenceAgnosticMetrics extends org.drip.sequence.metrics.SingleSequenceAgnosticMetrics {
	private double _dblPopulationMean = java.lang.Double.NaN;

	/**
	 * PoissonSequenceAgnosticMetrics Constructor
	 * 
	 * @param adblSequence The Random Sequence
	 * @param dblPopulationMean The Mean of the Underlying Distribution
	 * 
	 * @throws java.lang.Exception Thrown if PoissonSequenceAgnosticMetrics cannot be constructed
	 */

	public PoissonSequenceAgnosticMetrics (
		final double[] adblSequence,
		final double dblPopulationMean)
		throws java.lang.Exception
	{
		super (adblSequence, null);

		_dblPopulationMean = dblPopulationMean;
	}

	/**
	 * Retrieve the Mean of the Underlying Distribution
	 * 
	 * @return The Mean of the Underlying Distribution
	 */

	public double populationMean()
	{
		return _dblPopulationMean;
	}

	/**
	 * Compute the Chernoff-Stirling Upper Bound
	 * 
	 * @param dblLevel The Level at which the Bound is sought
	 * 
	 * @return The Chernoff-Stirling Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Chernoff-Stirling Upper Bound cannot be computed
	 */

	public double chernoffStirlingUpperBound (
		final double dblLevel)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel))
			throw new java.lang.Exception
				("PoissonSequenceAgnosticMetrics::chernoffStirlingUpperBound => Invalid Inputs");

		int iNumEntry = sequence().length;

		double dblPopulationMean = org.drip.quant.common.NumberUtil.IsValid (_dblPopulationMean) ?
			_dblPopulationMean : empiricalExpectation();

		double dblBound = (java.lang.Math.pow (dblPopulationMean / dblLevel, iNumEntry * dblLevel) *
			java.lang.Math.exp (iNumEntry * (dblLevel - dblPopulationMean) - (1. / (12. * iNumEntry *
				dblLevel + 1.)))) / java.lang.Math.sqrt (2. * java.lang.Math.PI * iNumEntry * dblLevel);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblBound)) return 0.;

		return dblBound > 1. ? 1. : dblBound;
	}
}
