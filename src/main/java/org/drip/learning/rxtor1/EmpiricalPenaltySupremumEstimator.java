
package org.drip.learning.rxtor1;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>EmpiricalPenaltySupremumEstimator</i> contains the Implementation of the Empirical Penalty Supremum
 * Estimator dependent on Multivariate Random Variables where the Multivariate Function is a Linear
 * Combination of Bounded Univariate Functions acting on each Random Variate.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1">Statistical Learning Empirical Loss Penalizer</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EmpiricalPenaltySupremumEstimator extends org.drip.sequence.functional.BoundedMultivariateRandom {

	/**
	 * Supremum Penalty computed off of Empirical Loss
	 */

	public static final int SUPREMUM_PENALTY_EMPIRICAL_LOSS = 1;

	/**
	 * Supremum Penalty computed off of Structural Loss
	 */

	public static final int SUPREMUM_PENALTY_STRUCTURAL_LOSS = 2;

	/**
	 * Supremum Penalty computed off of Regularized Loss
	 */

	public static final int SUPREMUM_PENALTY_REGULARIZED_LOSS = 4;

	/**
	 * Supremum Penalty computed off of Empirical Risk
	 */

	public static final int SUPREMUM_PENALTY_EMPIRICAL_RISK = 8;

	/**
	 * Supremum Penalty computed off of Structural Risk
	 */

	public static final int SUPREMUM_PENALTY_STRUCTURAL_RISK = 16;

	/**
	 * Supremum Penalty computed off of Regularized Risk
	 */

	public static final int SUPREMUM_PENALTY_REGULARIZED_RISK = 32;

	private int _iSupremumPenaltyLossMode = -1;
	private org.drip.measure.distribution.R1R1Continuous _distR1R1 = null;
	private org.drip.measure.distribution.RdR1Continuous _distRdR1 = null;
	private org.drip.spaces.rxtor1.NormedR1ToNormedR1[] _aR1ToR1 = null;
	private org.drip.spaces.rxtor1.NormedRdToNormedR1[] _aRdToR1 = null;
	private org.drip.spaces.instance.GeneralizedValidatedVector _gvviY = null;
	private org.drip.learning.rxtor1.EmpiricalLearningMetricEstimator _elme = null;

	/**
	 * EmpiricalPenaltySupremumEstimator Constructor
	 * 
	 * @param iSupremumPenaltyLossMode Supremum Loss Penalty Mode
	 * @param elme The Empirical Learning Metric Estimator Instance
	 * @param gvviY The Validated Outcome Instance
	 * @param distR1R1 R^1 R^1 Multivariate Measure
	 * @param distRdR1 R^d R^1 Multivariate Measure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EmpiricalPenaltySupremumEstimator (
		final int iSupremumPenaltyLossMode,
		final org.drip.learning.rxtor1.EmpiricalLearningMetricEstimator elme,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY,
		final org.drip.measure.distribution.R1R1Continuous distR1R1,
		final org.drip.measure.distribution.RdR1Continuous distRdR1)
		throws java.lang.Exception
	{
		if (null == (_elme = elme))
			throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator ctr: Invalid Inputs");

		org.drip.spaces.rxtor1.NormedRxToNormedR1[] aRxToR1 = _elme.functionClass().functionSpaces();

		if (null == aRxToR1)
			throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator ctr: Invalid Inputs");

		if (aRxToR1 instanceof org.drip.spaces.rxtor1.NormedR1ToNormedR1[])
			_aR1ToR1 = (org.drip.spaces.rxtor1.NormedR1ToNormedR1[]) aRxToR1;
		else
			_aRdToR1 = (org.drip.spaces.rxtor1.NormedRdToNormedR1[]) aRxToR1;

		_gvviY = gvviY;
		_distR1R1 = distR1R1;
		_distRdR1 = distRdR1;
		int iNumRxToR1 = aRxToR1.length;
		_iSupremumPenaltyLossMode = iSupremumPenaltyLossMode;

		if (SUPREMUM_PENALTY_EMPIRICAL_LOSS == _iSupremumPenaltyLossMode && null == _gvviY)
			throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator ctr: Invalid Inputs");

		for (int i = 0; i < iNumRxToR1; ++i) {
			if (null == aRxToR1[i])
				throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator ctr: Invalid Inputs");
		}
	}

	/**
	 * The Supremum Penalty Loss Mode Flag
	 * 
	 * @return The Supremum Penalty Loss Mode Flag
	 */

	public int supremumPenaltyLossMode()
	{
		return _iSupremumPenaltyLossMode;
	}

	/**
	 * Retrieve the Empirical Learning Metric Estimator Instance
	 * 
	 * @return The Empirical Learning Metric Estimator Instance
	 */

	public org.drip.learning.rxtor1.EmpiricalLearningMetricEstimator elme()
	{
		return _elme;
	}

	/**
	 * Retrieve the Validated Outcome Instance
	 * 
	 * @return The Validated Outcome Instance
	 */

	public org.drip.spaces.instance.GeneralizedValidatedVector empiricalOutcomes()
	{
		return _gvviY;
	}

	/**
	 * Compute the Empirical Penalty Supremum for the specified R^1 Input Space
	 * 
	 * @param gvviX The R^1 Input Space
	 * 
	 * @return The Empirical Penalty Supremum for the specified R^1 Input Space
	 */

	public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumR1 (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX)
	{
		if (null == _aR1ToR1) return null;

		int iSupremumIndex  = 0;
		int iNumR1ToR1 = _aR1ToR1.length;
		double dblSupremumPenaltyLoss = 0.;

		for (int i = 0 ; i < iNumR1ToR1; ++i) {
			org.drip.function.definition.R1ToR1 funcR1ToR1 = _aR1ToR1[i].function();

			if (null == funcR1ToR1) return null;

			double dblPenaltyLoss = 0.;

			try {
				if (SUPREMUM_PENALTY_EMPIRICAL_LOSS == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.empiricalLoss (funcR1ToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_STRUCTURAL_LOSS == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.structuralLoss (funcR1ToR1, gvviX);
				else if (SUPREMUM_PENALTY_REGULARIZED_LOSS == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.regularizedLoss (funcR1ToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_EMPIRICAL_RISK == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.empiricalRisk (_distR1R1, funcR1ToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_STRUCTURAL_RISK == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.structuralRisk (_distR1R1, funcR1ToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_REGULARIZED_RISK == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.regularizedRisk (_distR1R1, funcR1ToR1, gvviX, _gvviY);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (dblPenaltyLoss > dblSupremumPenaltyLoss) {
				iSupremumIndex = i;
				dblSupremumPenaltyLoss = dblPenaltyLoss;
			}
		}

		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremum (iSupremumIndex,
				dblSupremumPenaltyLoss / gvviX.sampleSize());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Empirical Penalty Supremum for the specified R^d Input Space
	 * 
	 * @param gvviX The R^d Input Space
	 * 
	 * @return The Empirical Penalty Supremum for the specified R^d Input Space
	 */

	public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremumRd (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX)
	{
		if (null == _aRdToR1) return null;

		int iSupremumIndex  = 0;
		int iNumRdToR1 = _aRdToR1.length;
		double dblSupremumPenaltyLoss = 0.;

		for (int i = 0 ; i < iNumRdToR1; ++i) {
			org.drip.function.definition.RdToR1 funcRdToR1 = _aRdToR1[i].function();

			if (null == funcRdToR1) return null;

			double dblPenaltyLoss = 0.;

			try {
				if (SUPREMUM_PENALTY_EMPIRICAL_LOSS == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.empiricalLoss (funcRdToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_STRUCTURAL_LOSS == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.structuralLoss (funcRdToR1, gvviX);
				else if (SUPREMUM_PENALTY_REGULARIZED_LOSS == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.regularizedLoss (funcRdToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_EMPIRICAL_RISK == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.empiricalRisk (_distRdR1, funcRdToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_STRUCTURAL_RISK == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.structuralRisk (_distRdR1, funcRdToR1, gvviX, _gvviY);
				else if (SUPREMUM_PENALTY_REGULARIZED_RISK == _iSupremumPenaltyLossMode)
					dblPenaltyLoss += _elme.regularizedRisk (_distRdR1, funcRdToR1, gvviX, _gvviY);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (dblPenaltyLoss > dblSupremumPenaltyLoss) {
				iSupremumIndex = i;
				dblSupremumPenaltyLoss = dblPenaltyLoss;
			}
		}

		try {
			return new org.drip.learning.rxtor1.EmpiricalPenaltySupremum (iSupremumIndex,
				dblSupremumPenaltyLoss / gvviX.sampleSize());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Empirical Penalty Supremum for the specified R^1/R^d Input Space
	 * 
	 * @param gvviX The R^1/R^d Input Space
	 * 
	 * @return The Empirical Penalty Supremum for the specified R^1/R^d Input Space
	 */

	public org.drip.learning.rxtor1.EmpiricalPenaltySupremum supremum (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX)
	{
		org.drip.learning.rxtor1.EmpiricalPenaltySupremum epsR1 = supremumR1 (gvviX);

		return null == epsR1 ? supremumRd (gvviX) : epsR1;
	}

	/**
	 * Retrieve the Supremum R^1 To R^1 Function Instance for the specified Variate Sequence
	 * 
	 * @param adblX The Predictor Instance
	 * 
	 * @return The Supremum R^1 To R^1 Function Instance
	 */

	public org.drip.function.definition.R1ToR1 supremumR1ToR1 (
		final double[] adblX)
	{
		org.drip.learning.rxtor1.EmpiricalPenaltySupremum eps = null;

		try {
			eps = supremumR1 (new org.drip.spaces.instance.ValidatedR1
				(org.drip.spaces.tensor.R1ContinuousVector.Standard(), adblX));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return _aR1ToR1[eps.index()].function();
	}

	/**
	 * Retrieve the Supremum R^d To R^1 Function Instance for the specified Variate Sequence
	 * 
	 * @param aadblX The Predictor Instance
	 * 
	 * @return The Supremum R^d To R^1 Function Instance
	 */

	public org.drip.function.definition.RdToR1 supremumRdToR1 (
		final double[][] aadblX)
	{
		org.drip.learning.rxtor1.EmpiricalPenaltySupremum eps = null;

		try {
			eps = supremumRd (new org.drip.spaces.instance.ValidatedRd
				(org.drip.spaces.tensor.RdContinuousVector.Standard (aadblX.length), aadblX));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return _aRdToR1[eps.index()].function();
	}

	@Override public int dimension()
	{
		return -1;
	}

	@Override public double evaluate (
		final double[] adblX)
		throws java.lang.Exception
	{
		org.drip.learning.rxtor1.EmpiricalPenaltySupremum eps = supremumR1 (new
			org.drip.spaces.instance.ValidatedR1 (org.drip.spaces.tensor.R1ContinuousVector.Standard(),
				adblX));

		if (null == eps)
			throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator::evaluate => Invalid Inputs");

		return eps.value();
	}

	/**
	 * Retrieve the Worst-case Loss over the Multivariate Sequence
	 * 
	 * @param aadblX The Multivariate Array
	 * 
	 * @return The Worst-case Loss over the Multivariate Sequence
	 * 
	 * @throws java.lang.Exception Thrown if the Worst-Case Loss cannot be computed
	 */

	public double evaluate (
		final double[][] aadblX)
		throws java.lang.Exception
	{
		if (null == aadblX)
			throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator::evaluate => Invalid Inputs");

		org.drip.learning.rxtor1.EmpiricalPenaltySupremum eps = supremumRd (new
			org.drip.spaces.instance.ValidatedRd (org.drip.spaces.tensor.RdContinuousVector.Standard
				(aadblX.length), aadblX));

		if (null == eps)
			throw new java.lang.Exception ("EmpiricalPenaltySupremumEstimator::evaluate => Invalid Inputs");

		return eps.value();
	}

	@Override public double targetVariateVarianceBound (
		final int iTargetVariateIndex)
		throws java.lang.Exception
	{
		return 1. / (_gvviY.sampleSize() * _gvviY.sampleSize());
	}
}
