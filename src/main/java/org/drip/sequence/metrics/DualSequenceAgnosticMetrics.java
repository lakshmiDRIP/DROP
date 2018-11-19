
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
 * <i>DualSequenceAgnosticMetrics</i> contains the Joint Distribution Metrics and Agnostic Bounds related to
 * the specified Sequence Pair.
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

public class DualSequenceAgnosticMetrics {
	private org.drip.sequence.metrics.SingleSequenceAgnosticMetrics _ssam1 = null;
	private org.drip.sequence.metrics.SingleSequenceAgnosticMetrics _ssam2 = null;

	/**
	 * DualSequenceAgnosticMetrics Constructor
	 * 
	 * @param ssam1 First Sequence Metrics
	 * @param ssam2 Second Sequence Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DualSequenceAgnosticMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssam1,
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssam2)
		throws java.lang.Exception
	{
		if (null == (_ssam1 = ssam1) || null == (_ssam2 = ssam2) || _ssam1.sequence().length !=
			_ssam2.sequence().length)
			throw new java.lang.Exception ("DualSequenceAgnosticMetrics ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Component Single Sequences
	 * 
	 * @return The Array of the Component Single Sequences
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] components()
	{
		return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] {_ssam1, _ssam2};
	}

	/**
	 * Retrieve the Cauchy-Schwarz Joint Expectation Bound
	 * 
	 * @return The Cauchy-Schwarz Joint Expectation Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Cauchy-Schwarz Joint Expectation Bound cannot be computed
	 */

	public double cauchySchwarzAbsoluteBound()
		throws java.lang.Exception
	{
		return java.lang.Math.sqrt (_ssam1.empiricalRawMoment (2, true) * _ssam2.empiricalRawMoment (2,
			true));
	}
}
