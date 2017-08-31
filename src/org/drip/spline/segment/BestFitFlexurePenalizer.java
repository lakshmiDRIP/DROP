
package org.drip.spline.segment;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * This Class implements the Segment's Best Fit, Curvature, and Length Penalizers. It provides the
 * 	following functionality:
 * 	- Compute the Cross-Curvature Penalty for the given Basis Pair.
 * 	- Compute the Cross-Length Penalty for the given Basis Pair.
 * 	- Compute the Best Fit Cross-Product Penalty for the given Basis Pair.
 * 	- Compute the Basis Pair Penalty Coefficient for the Best Fit and the Curvature Penalties.
 * 	- Compute the Penalty Constraint for the Basis Pair.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BestFitFlexurePenalizer {
	private org.drip.spline.segment.BasisEvaluator _lbe = null;
	private org.drip.spline.segment.LatentStateInelastic _ics = null;
	private org.drip.spline.params.SegmentBestFitResponse _sbfr = null;
	private org.drip.spline.params.SegmentFlexurePenaltyControl _sfpcLength = null;
	private org.drip.spline.params.SegmentFlexurePenaltyControl _sfpcCurvature = null;

	/**
	 * BestFitFlexurePenalizer constructor
	 * 
	 * @param ics Segment Inelastics
	 * @param sfpcCurvature Curvature Penalty Parameters
	 * @param sfpcLength Length Penalty Parameters
	 * @param sbfr Best Fit Weighted Response
	 * @param lbe The Local Basis Evaluator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BestFitFlexurePenalizer (
		final org.drip.spline.segment.LatentStateInelastic ics,
		final org.drip.spline.params.SegmentFlexurePenaltyControl sfpcCurvature,
		final org.drip.spline.params.SegmentFlexurePenaltyControl sfpcLength,
		final org.drip.spline.params.SegmentBestFitResponse sbfr,
		final org.drip.spline.segment.BasisEvaluator lbe)
		throws java.lang.Exception
	{
		if (null == (_lbe = lbe) || null == (_ics = ics))
			throw new java.lang.Exception ("BestFitFlexurePenalizer ctr: Invalid Inputs");

		_sbfr = sbfr;
		_sfpcLength = sfpcLength;
		_sfpcCurvature = sfpcCurvature;
	}

	/**
	 * Compute the Cross-Curvature Penalty for the given Basis Pair
	 * 
	 * @param iBasisIndexI I Basis Index (I is the Summation Index)
	 * @param iBasisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return The Cross-Curvature Penalty for the given Basis Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Cross-Curvature Penalty cannot be computed
	 */

	public double basisPairCurvaturePenalty (
		final int iBasisIndexI,
		final int iBasisIndexR)
		throws java.lang.Exception
	{
		if (null == _sfpcCurvature) return 0.;

		org.drip.function.definition.R1ToR1 au = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblVariate)
				throws Exception
			{
				int iOrder = _sfpcCurvature.derivativeOrder();

				return _lbe.shapedBasisFunctionDerivative (dblVariate, iOrder, iBasisIndexI) *
					_lbe.shapedBasisFunctionDerivative (dblVariate, iOrder, iBasisIndexR);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		return _sfpcCurvature.amplitude() * au.integrate (_ics.left(), _ics.right());
	}

	/**
	 * Compute the Cross-Length Penalty for the given Basis Pair
	 * 
	 * @param iBasisIndexI I Basis Index (I is the Summation Index)
	 * @param iBasisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return The Cross-Length Penalty for the given Basis Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Cross-Length Penalty cannot be computed
	 */

	public double basisPairLengthPenalty (
		final int iBasisIndexI,
		final int iBasisIndexR)
		throws java.lang.Exception
	{
		if (null == _sfpcLength) return 0.;

		org.drip.function.definition.R1ToR1 au = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblVariate)
				throws Exception
			{
				int iOrder = _sfpcLength.derivativeOrder();

				return _lbe.shapedBasisFunctionDerivative (dblVariate, iOrder, iBasisIndexI) *
					_lbe.shapedBasisFunctionDerivative (dblVariate, iOrder, iBasisIndexR);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		return _sfpcLength.amplitude() * au.integrate (_ics.left(), _ics.right());
	}

	/**
	 * Compute the Best Fit Cross-Product Penalty for the given Basis Pair
	 * 
	 * @param iBasisIndexI I Basis Index (I is the Summation Index)
	 * @param iBasisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return The Best Fit Cross-Product Penalty for the given Basis Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Best Fit Cross-Product Penalty cannot be computed
	 */

	public double basisBestFitPenalty (
		final int iBasisIndexI,
		final int iBasisIndexR)
		throws java.lang.Exception
	{
		if (null == _sbfr) return 0.;

		int iNumPoint = _sbfr.numPoint();

		if (0 == iNumPoint) return 0.;

		double dblBasisPairFitnessPenalty = 0.;

		for (int i = 0; i < iNumPoint; ++i) {
			double dblPredictorOrdinate = _sbfr.predictorOrdinate (i);

			dblBasisPairFitnessPenalty += _sbfr.weight (i) * _lbe.shapedBasisFunctionResponse
				(dblPredictorOrdinate, iBasisIndexI) * _lbe.shapedBasisFunctionResponse
					(dblPredictorOrdinate, iBasisIndexR);
		}

		return dblBasisPairFitnessPenalty / iNumPoint;
	}

	/**
	 * Compute the Basis Pair Penalty Coefficient for the Best Fit and the Curvature Penalties
	 * 
	 * @param iBasisIndexI I Basis Index (I is the Summation Index)
	 * @param iBasisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 * 
	 * @return The Basis Pair Penalty Coefficient for the Fitness and the Curvature Penalties
	 */

	public double basisPairConstraintCoefficient (
		final int iBasisIndexI,
		final int iBasisIndexR)
		throws java.lang.Exception
	{
		return basisPairCurvaturePenalty (iBasisIndexI, iBasisIndexR) + basisPairLengthPenalty (iBasisIndexI,
			iBasisIndexR) + basisBestFitPenalty (iBasisIndexI, iBasisIndexR);
	}

	/**
	 * Compute the Penalty Constraint for the Basis Pair
	 * 
	 * @param iBasisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return Penalty Constraint for the Basis Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double basisPairPenaltyConstraint (
		final int iBasisIndexR)
		throws java.lang.Exception
	{
		if (null == _sbfr) return 0.;

		int iNumPoint = _sbfr.numPoint();

		if (0 == iNumPoint) return 0.;

		double dblBasisPairPenaltyConstraint = 0.;

		for (int i = 0; i < iNumPoint; ++i) {
			double dblPredictorOrdinate = _sbfr.predictorOrdinate (i);

			dblBasisPairPenaltyConstraint += _sbfr.weight (i) * _lbe.shapedBasisFunctionResponse
				(dblPredictorOrdinate, iBasisIndexR) * _sbfr.response (i);
		}

		return dblBasisPairPenaltyConstraint / iNumPoint;
	}
}
