
package org.drip.measure.crng;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * MultiStreamGenerator helps generate Multiple Independent (i.e., Non-Overlapping) Streams of Random
 *  Numbers.
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
