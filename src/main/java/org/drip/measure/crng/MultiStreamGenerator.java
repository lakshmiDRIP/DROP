
package org.drip.measure.crng;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>MultiStreamGenerator</i> helps generate Multiple Independent (i.e., Non-Overlapping) Streams of Random
 *  Numbers.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng">Continuous Random Number Generator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
