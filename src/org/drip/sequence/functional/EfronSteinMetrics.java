
package org.drip.sequence.functional;

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
 * EfronSteinMetrics contains the Variance-based non-exponential Sample Distribution/Bounding Metrics and
 *  Agnostic Bounds related to the Functional Transformation of the specified Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class EfronSteinMetrics {
	private org.drip.sequence.functional.MultivariateRandom _func = null;
	private org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] _aSSAM = null;

	private double[] demotedSequence (
		final double[] adblSequence,
		final int iDemoteIndex)
	{
		int iSequenceLength = adblSequence.length;
		double[] adblDemotedSequence = new double[iSequenceLength - 1];

		for (int i = 0; i < iSequenceLength; ++i) {
			if (i < iDemoteIndex)
				adblDemotedSequence[i] = adblSequence[i];
			else if (i > iDemoteIndex)
				adblDemotedSequence[i - 1] = adblSequence[i];
		}

		return adblDemotedSequence;
	}

	/**
	 * EfronSteinMetrics Constructor
	 * 
	 * @param func Multivariate Objective Function
	 * @param aSSAM Array of the individual Single Sequence Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EfronSteinMetrics (
		final org.drip.sequence.functional.MultivariateRandom func,
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM)
		throws java.lang.Exception
	{
		if (null == (_func = func) || null == (_aSSAM = aSSAM))
			throw new java.lang.Exception ("EfronSteinMetrics ctr: Invalid Inputs");

		int iNumVariable = _aSSAM.length;

		if (0 == iNumVariable)
			throw new java.lang.Exception ("EfronSteinMetrics ctr: Invalid Inputs");

		int iSequenceLength = _aSSAM[0].sequence().length;

		for (int i = 1; i < iNumVariable; ++i) {
			if (null == _aSSAM[i] || _aSSAM[i].sequence().length != iSequenceLength)
				throw new java.lang.Exception ("EfronSteinMetrics ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Multivariate Objective Function
	 * 
	 * @return The Multivariate Objective Function Instance
	 */

	public org.drip.function.definition.RdToR1 function()
	{
		return _func;
	}

	/**
	 * Retrieve the Array of the Single Sequence Agnostic Metrics
	 * 
	 * @return The Array of the Single Sequence Agnostic Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] sequenceMetrics()
	{
		return _aSSAM;
	}

	/**
	 * Extract the Full Variate Array Sequence
	 * 
	 * @param aSSAM Array of the individual Single Sequence Metrics
	 * 
	 * @return The Full Variate Array Sequence
	 */

	public double[][] variateSequence (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM)
	{
		int iNumVariate = _aSSAM.length;

		if (null == aSSAM || aSSAM.length != iNumVariate) return null;

		int iSequenceSize = aSSAM[0].sequence().length;

		double[][] aadblVariateSequence = new double[iSequenceSize][iNumVariate];

		for (int iVariateIndex = 0; iVariateIndex < iNumVariate; ++iVariateIndex) {
			double[] adblVariate = aSSAM[iVariateIndex].sequence();

			for (int iSequenceIndex = 0; iSequenceIndex < iSequenceSize; ++iSequenceIndex)
				aadblVariateSequence[iSequenceIndex][iVariateIndex] = adblVariate[iSequenceIndex];
		}

		return aadblVariateSequence;
	}

	/**
	 * Compute the Function Sequence Agnostic Metrics associated with the Variance of each Variate
	 * 
	 * @return The Array of the Associated Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] variateFunctionVarianceMetrics()
	{
		int iNumVariate = _aSSAM.length;
		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM = new
			org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == (aSSAM[i] = _func.unconditionalTargetVariateMetrics (_aSSAM, i))) return null;
		}

		return aSSAM;
	}

	/**
	 * Compute the Function Sequence Agnostic Metrics associated with the Variance of each Variate Using the
	 * 	Supplied Ghost Variate Sequence
	 * 
	 * @param aSSAMGhost Array of the Ghost Single Sequence Metrics
	 * 
	 * @return The Array of the Associated Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] ghostVariateVarianceMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAMGhost)
	{
		if (null == aSSAMGhost) return null;

		int iNumVariate = _aSSAM.length;
		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM = new
			org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == aSSAMGhost[i] || null == (aSSAM[i] = _func.ghostTargetVariateMetrics (_aSSAM, i,
				aSSAMGhost[i].sequence())))
				return null;
		}

		return aSSAM;
	}

	/**
	 * Compute the Function Sequence Agnostic Metrics associated with each Variate using the specified Ghost
	 * 	Symmetric Variable Copy
	 * 
	 * @param aSSAMGhost Array of the Ghost Single Sequence Metrics
	 * 
	 * @return The Array of the Associated Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] symmetrizedDifferenceSequenceMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAMGhost)
	{
		double[][] aadblSequenceVariate = variateSequence (_aSSAM);

		double[][] aadblGhostSequenceVariate = variateSequence (aSSAMGhost);

		if (null == aadblGhostSequenceVariate || aadblSequenceVariate.length !=
			aadblGhostSequenceVariate.length || aadblSequenceVariate[0].length !=
				aadblGhostSequenceVariate[0].length)
			return null;

		int iSequenceSize = _aSSAM[0].sequence().length;

		int iNumVariate = _aSSAM.length;
		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAMFunction = new
			org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[iNumVariate];

		try {
			for (int iVariateIndex = 0; iVariateIndex < iNumVariate; ++iVariateIndex) {
				double[] adblSymmetrizedFunctionDifference = new double[iSequenceSize];

				for (int iSequenceIndex = 0; iSequenceIndex < iSequenceSize; ++iSequenceIndex) {
					double[] adblVariate = aadblSequenceVariate[iSequenceIndex];

					adblSymmetrizedFunctionDifference[iSequenceIndex] = _func.evaluate (adblVariate);

					double dblVariateOrig = adblVariate[iVariateIndex];
					adblVariate[iVariateIndex] = aadblGhostSequenceVariate[iSequenceIndex][iVariateIndex];

					adblSymmetrizedFunctionDifference[iSequenceIndex] -= _func.evaluate (adblVariate);

					adblVariate[iVariateIndex] = dblVariateOrig;
				}

				aSSAMFunction[iVariateIndex] = new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics
					(adblSymmetrizedFunctionDifference, null);
			}

			return aSSAMFunction;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Function Sequence Agnostic Metrics associated with each Variate around the Pivot Point
	 *  provided by the Pivot Function
	 * 
	 * @param funcPivot The Pivot Function
	 * 
	 * @return The Array of the Associated Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] pivotedDifferenceSequenceMetrics (
		final org.drip.sequence.functional.MultivariateRandom funcPivot)
	{
		if (null == funcPivot) return null;

		double[][] aadblSequenceVariate = variateSequence (_aSSAM);

		int iSequenceSize = _aSSAM[0].sequence().length;

		int iNumVariate = _aSSAM.length;
		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAMFunction = new
			org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[iNumVariate];

		try {
			for (int iVariateIndex = 0; iVariateIndex < iNumVariate; ++iVariateIndex) {
				double[] adblSymmetrizedFunctionDifference = new double[iSequenceSize];

				for (int iSequenceIndex = 0; iSequenceIndex < iSequenceSize; ++iSequenceIndex) {
					double[] adblVariate = aadblSequenceVariate[iSequenceIndex];

					adblSymmetrizedFunctionDifference[iSequenceIndex] = _func.evaluate (adblVariate) -
						funcPivot.evaluate (demotedSequence (adblVariate, iVariateIndex));
				}

				aSSAMFunction[iVariateIndex] = new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics
					(adblSymmetrizedFunctionDifference, null);
			}

			return aSSAMFunction;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Multivariate Variance Upper Bound using the Martingale Differences Method
	 * 
	 * @return The Multivariate Variance Upper Bound using the Martingale Differences Method
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound cannot be calculated
	 */

	public double martingaleVarianceUpperBound()
		throws java.lang.Exception
	{
		int iNumVariate = _aSSAM.length;
		double dblVarianceUpperBound = 0.;

		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM = variateFunctionVarianceMetrics();

		if (null == aSSAM || iNumVariate != aSSAM.length)
			throw new java.lang.Exception
				("EfronSteinMetrics::martingaleVarianceUpperBound => Cannot compute Univariate Variance Metrics");

		for (int i = 0; i < iNumVariate; ++i)
			dblVarianceUpperBound += aSSAM[i].empiricalExpectation();

		return dblVarianceUpperBound;
	}

	/**
	 * Compute the Variance Upper Bound using the Ghost Variables
	 * 
	 * @param aSSAMGhost Array of the Ghost Single Sequence Metrics
	 * 
	 * @return The Variance Upper Bound using the Ghost Variables
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound cannot be calculated
	 */

	public double ghostVarianceUpperBound (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAMGhost)
		throws java.lang.Exception
	{
		int iNumVariate = _aSSAM.length;
		double dblVarianceUpperBound = 0.;

		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM = ghostVariateVarianceMetrics
			(aSSAMGhost);

		if (null == aSSAM || iNumVariate != aSSAM.length)
			throw new java.lang.Exception
				("EfronSteinMetrics::ghostVarianceUpperBound => Cannot compute Target Ghost Variance Metrics");

		for (int i = 0; i < iNumVariate; ++i)
			dblVarianceUpperBound += aSSAM[i].empiricalExpectation();

		return dblVarianceUpperBound;
	}

	/**
	 * Compute the Efron-Stein-Steele Variance Upper Bound using the Ghost Variables
	 * 
	 * @param aSSAMGhost Array of the Ghost Single Sequence Metrics
	 * 
	 * @return The Efron-Stein-Steele Variance Upper Bound using the Ghost Variables
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound cannot be calculated
	 */

	public double efronSteinSteeleBound (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAMGhost)
		throws java.lang.Exception
	{
		int iNumVariate = _aSSAM.length;
		double dblVarianceUpperBound = 0.;

		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM =
			symmetrizedDifferenceSequenceMetrics (aSSAMGhost);

		if (null == aSSAM || iNumVariate != aSSAM.length)
			throw new java.lang.Exception
				("EfronSteinMetrics::efronSteinSteeleBound => Cannot compute Symmetrized Difference Metrics");

		for (int i = 0; i < iNumVariate; ++i)
			dblVarianceUpperBound += aSSAM[i].empiricalRawMoment (2, false);

		return 0.5 * dblVarianceUpperBound;
	}

	/**
	 * Compute the Function Variance Upper Bound using the supplied Multivariate Pivoting Function
	 * 
	 * @param funcPivot The Custom Multivariate Pivoting Function
	 * 
	 * @return The Function Variance Upper Bound using the supplied Multivariate Pivot Function
	 * 
	 * @throws java.lang.Exception Thrown if the Variance Upper Bound cannot be calculated
	 */

	public double pivotVarianceUpperBound (
		final org.drip.sequence.functional.MultivariateRandom funcPivot)
		throws java.lang.Exception
	{
		int iNumVariate = _aSSAM.length;
		double dblVarianceUpperBound = 0.;

		org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM = pivotedDifferenceSequenceMetrics
			(funcPivot);

		if (null == aSSAM || iNumVariate != aSSAM.length)
			throw new java.lang.Exception
				("EfronSteinMetrics::pivotVarianceUpperBound => Cannot compute Pivoted Difference Metrics");

		for (int i = 0; i < iNumVariate; ++i)
			dblVarianceUpperBound += aSSAM[i].empiricalRawMoment (2, false);

		return 0.5 * dblVarianceUpperBound;
	}

	/**
	 * Compute the Multivariate Variance Upper Bound using the Bounded Differences Support
	 * 
	 * @return The Multivariate Variance Upper Bound using the Bounded Differences Support
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound cannot be calculated
	 */

	public double boundedVarianceUpperBound()
		throws java.lang.Exception
	{
		if (!(_func instanceof org.drip.sequence.functional.BoundedMultivariateRandom))
			throw new java.lang.Exception
				("EfronSteinMetrics::boundedVarianceUpperBound => Invalid Bounded Metrics");

		int iNumVariate = _aSSAM.length;
		double dblVarianceUpperBound = 0.;
		org.drip.sequence.functional.BoundedMultivariateRandom boundedFunc =
			(org.drip.sequence.functional.BoundedMultivariateRandom) _func;

		for (int i = 0; i < iNumVariate; ++i)
			dblVarianceUpperBound += boundedFunc.targetVariateVarianceBound (i);

		return 0.5 * dblVarianceUpperBound;
	}

	/**
	 * Compute the Multivariate Variance Upper Bound using the Separable Variance Bound
	 * 
	 * @return The Multivariate Variance Upper Bound using the Separable Variance Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound cannot be calculated
	 */

	public double separableVarianceUpperBound()
		throws java.lang.Exception
	{
		if (!(_func instanceof org.drip.sequence.functional.SeparableMultivariateRandom))
			throw new java.lang.Exception
				("EfronSteinMetrics::separableVarianceUpperBound => Invalid Bounded Metrics");

		int iNumVariate = _aSSAM.length;
		double dblVarianceUpperBound = 0.;
		org.drip.sequence.functional.SeparableMultivariateRandom separableFunc =
			(org.drip.sequence.functional.SeparableMultivariateRandom) _func;

		for (int i = 0; i < iNumVariate; ++i)
			dblVarianceUpperBound += separableFunc.targetVariateVariance (i);

		return 0.5 * dblVarianceUpperBound;
	}
}
