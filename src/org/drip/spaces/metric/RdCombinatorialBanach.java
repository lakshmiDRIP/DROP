
package org.drip.spaces.metric;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * RdCombinatorialBanach implements the Bounded/Unbounded Combinatorial l^p R^d Spaces.
 * 
 * The Reference we've used is:
 * 
 * 	- Carl, B., and I. Stephani (1990): Entropy, Compactness, and Approximation of Operators, Cambridge
 * 		University Press, Cambridge UK.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdCombinatorialBanach extends org.drip.spaces.tensor.RdCombinatorialVector implements
	org.drip.spaces.metric.RdNormed {
	private int _iPNorm = -1;
	private org.drip.measure.continuous.Rd _distRd = null;

	/**
	 * RdCombinatorialBanach Space Constructor
	 * 
	 * @param aR1CV Array of Combinatorial R^1 Vector Spaces
	 * @param distRd The R^d Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdCombinatorialBanach (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV,
		final org.drip.measure.continuous.Rd distRd,
		final int iPNorm)
		throws java.lang.Exception
	{
		super (aR1CV);

		if (0 > (_iPNorm = iPNorm))
			throw new java.lang.Exception ("RdCombinatorialBanach Constructor: Invalid p-norm");

		_distRd = distRd;
	}

	@Override public int pNorm()
	{
		return _iPNorm;
	}

	@Override public org.drip.measure.continuous.Rd borelSigmaMeasure()
	{
		return _distRd;
	}

	@Override public double sampleSupremumNorm (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (!validateInstance (adblX))
			throw new java.lang.Exception ("RdCombinatorialBanach::sampleSupremumNorm => Invalid Inputs");

		int iDimension = adblX.length;

		double dblNorm = java.lang.Math.abs (adblX[0]);

		for (int i = 1; i < iDimension; ++i) {
			double dblAbsoluteX = java.lang.Math.abs (adblX[i]);

			dblNorm = dblNorm > dblAbsoluteX ? dblNorm : dblAbsoluteX;
		}

		return dblNorm;
	}

	@Override public double sampleMetricNorm (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (!validateInstance (adblX))
			throw new java.lang.Exception ("RdCombinatorialBanach::sampleMetricNorm => Invalid Inputs");

		if (java.lang.Integer.MAX_VALUE == _iPNorm) return sampleSupremumNorm (adblX);

		double dblNorm = 0.;
		int iDimension = adblX.length;

		for (int i = 0; i < iDimension; ++i)
			dblNorm += java.lang.Math.pow (java.lang.Math.abs (adblX[i]), _iPNorm);

		return java.lang.Math.pow (dblNorm, 1. / _iPNorm);
	}

	@Override public double[] populationMode()
	{
		if (null == _distRd) return null;

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator crmi = iterator();

		double[] adblVariate = crmi.cursorVariates();

		int iDimension = adblVariate.length;
		double dblModeProbabilityDensity = 0.;
		double[] adblModeVariate = new double[iDimension];
		double dblProbabilityDensity = java.lang.Double.NaN;

		while (null != adblVariate) {
			try {
				dblProbabilityDensity = _distRd.density (adblVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (dblProbabilityDensity > dblModeProbabilityDensity) {
				for (int i = 0; i < iDimension; ++i)
					adblModeVariate[i] = adblVariate[i];

				dblModeProbabilityDensity = dblProbabilityDensity;
			}

			adblVariate = crmi.nextVariates();
		}

		return adblModeVariate;
	}

	@Override public double populationSupremumNorm()
		throws java.lang.Exception
	{
		if (null == _distRd)
			throw new java.lang.Exception
				("RdCombinatorialBanach::populationSupremumNorm => Invalid Inputs");

		return sampleSupremumNorm (populationMode());
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		if (java.lang.Integer.MAX_VALUE == _iPNorm) return sampleSupremumNorm (populationMode());

		if (null == _distRd)
			throw new java.lang.Exception
				("RdCombinatorialBanach::populationMetricNorm => No Multivariate Distribution");

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator crmi = iterator();

		double[] adblVariate = crmi.cursorVariates();

		double dblNormalizer = 0.;
		double dblPopulationMetricNorm  = 0.;
		int iDimension = adblVariate.length;

		while (null != adblVariate) {
			double dblProbabilityDensity = _distRd.density (adblVariate);

			dblNormalizer += dblProbabilityDensity;

			for (int i = 0; i < iDimension; ++i)
				dblPopulationMetricNorm += dblProbabilityDensity * java.lang.Math.pow (java.lang.Math.abs
					(adblVariate[i]), _iPNorm);

			adblVariate = crmi.nextVariates();
		}

		return java.lang.Math.pow (dblPopulationMetricNorm / dblNormalizer, 1. / _iPNorm);
	}

	@Override public double borelMeasureSpaceExpectation (
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		if (null == _distRd || null == funcRdToR1)
			throw new java.lang.Exception
				("RdCombinatorialBanach::borelMeasureSpaceExpectation => Invalid Inputs");

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator crmi = iterator();

		double[] adblVariate = crmi.cursorVariates();

		double dblBorelMeasureSpaceExpectation = 0.;
		double dblNormalizer = 0.;

		while (null != adblVariate) {
			double dblProbabilityDensity = _distRd.density (adblVariate);

			dblNormalizer += dblProbabilityDensity;

			dblBorelMeasureSpaceExpectation += dblProbabilityDensity * funcRdToR1.evaluate (adblVariate);

			adblVariate = crmi.nextVariates();
		}

		return dblBorelMeasureSpaceExpectation / dblNormalizer;
	}
}
