
package org.drip.measure.crng;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>MultiStreamGenerator</i> helps generate Multiple Independent (i.e., Non-Overlapping) Streams of Random
 * Numbers.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/README.md">Continuous Random Number Stream Generator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiStreamGenerator {

	/**
	 * Generate Multiple Independent Streams using the Skip Ahead Technique
	 * 
	 * @param rng The Random Number Generator
	 * @param iNumStream Number of Streams to be Generated
	 * @param iNumElementsPerStream Number of Elements Per Stream
	 * 
	 * @return Double Array of Multiple Independent Streams
	 */

	public static final double[][] SkipAhead (
		final org.drip.measure.crng.RandomNumberGenerator rng,
		final int iNumStream,
		final int iNumElementsPerStream)
	{
		if (null == rng || 0 >= iNumStream || 0 >= iNumElementsPerStream) return null;

		double[][] aadblRandomStream = new double[iNumStream][iNumElementsPerStream];

		for (int iStream = 0; iStream < iNumStream; ++iStream) {
			for (int iElement = 0; iElement < iNumElementsPerStream; ++iElement)
				aadblRandomStream[iStream][iElement] = rng.nextDouble01();
		}

		return aadblRandomStream;
	}

	/**
	 * Generate Multiple Independent Streams using the Leap Frog Technique
	 * 
	 * @param rng The Random Number Generator
	 * @param iNumStream Number of Streams to be Generated
	 * @param iNumElementsPerStream Number of Elements Per Stream
	 * 
	 * @return Double Array of Multiple Independent Streams
	 */

	public static final double[][] LeapFrog (
		final org.drip.measure.crng.RandomNumberGenerator rng,
		final int iNumStream,
		final int iNumElementsPerStream)
	{
		if (null == rng || 0 >= iNumStream || 0 >= iNumElementsPerStream) return null;

		double[][] aadblRandomStream = new double[iNumStream][iNumElementsPerStream];

		for (int iElement = 0; iElement < iNumElementsPerStream; ++iElement) {
			for (int iStream = 0; iStream < iNumStream; ++iStream)
				aadblRandomStream[iStream][iElement] = rng.nextDouble01();
		}

		return aadblRandomStream;
	}

	/**
	 * Generate Multiple Independent Streams using the Skip Ahead Technique from the Default Random Number
	 * 	Generator
	 * 
	 * @param iNumStream Number of Streams to be Generated
	 * @param iNumElementsPerStream Number of Elements Per Stream
	 * 
	 * @return Double Array of Multiple Independent Streams
	 */

	public static final double[][] SkipAhead (
		final int iNumStream,
		final int iNumElementsPerStream)
	{
		return SkipAhead (new org.drip.measure.crng.RandomNumberGenerator(), iNumStream,
			iNumElementsPerStream);
	}

	/**
	 * Generate Multiple Independent Streams using the Leap Frog Technique from the Default Random Number
	 * 	Generator
	 * 
	 * @param iNumStream Number of Streams to be Generated
	 * @param iNumElementsPerStream Number of Elements Per Stream
	 * 
	 * @return Double Array of Multiple Independent Streams
	 */

	public static final double[][] LeapFrog (
		final int iNumStream,
		final int iNumElementsPerStream)
	{
		return LeapFrog (new org.drip.measure.crng.RandomNumberGenerator(), iNumStream,
			iNumElementsPerStream);
	}
}
