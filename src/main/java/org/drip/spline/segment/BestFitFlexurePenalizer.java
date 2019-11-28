
package org.drip.spline.segment;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>BestFitFlexurePenalizer</i> implements the Segment's Best Fit, Curvature, and Length Penalizers. It
 * provides the following functionality:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Compute the Cross-Curvature Penalty for the given Basis Pair
 *  	</li>
 *  	<li>
 * 			Compute the Cross-Length Penalty for the given Basis Pair
 *  	</li>
 *  	<li>
 * 			Compute the Best Fit Cross-Product Penalty for the given Basis Pair
 *  	</li>
 *  	<li>
 * 			Compute the Basis Pair Penalty Coefficient for the Best Fit and the Curvature Penalties
 *  	</li>
 *  	<li>
 * 			Compute the Penalty Constraint for the Basis Pair
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></li>
 *  </ul>
 * <br><br>
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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
