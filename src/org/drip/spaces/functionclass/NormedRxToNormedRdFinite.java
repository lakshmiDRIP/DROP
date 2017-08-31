
package org.drip.spaces.functionclass;

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
 * NormedRxToNormedRdFinite implements the Class F with f E f : Normed R^x To Normed R^d Space of Finite
 * 	Functions.
 * 
 *  The References are:
 *  
 *  1) Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of Operators
 *  	in Banach Spaces, Annals of the Fourier Institute 35 (3) 79-118.
 *  
 *  2) Carl, B., and I. Stephani (1990): Entropy, Compactness, and the Approximation of Operators, Cambridge
 *  	University Press, Cambridge UK. 
 *  
 *  3) Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function Classes,
 *  	in: Proceedings of the 13th Annual Conference on Computational Learning Theory, ACM New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedRxToNormedRdFinite extends org.drip.spaces.functionclass.NormedRxToNormedRxFinite {
	private org.drip.spaces.rxtord.NormedRxToNormedRd[] _aNormedRxToNormedRd = null;

	/**
	 * NormedRxToNormedRdFinite Constructor
	 * 
	 * @param dblMaureyConstant Maurey Constant
	 * @param aNormedRxToNormedRd Array of the Normed R^x To Normed R^d Spaces
	 *  
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NormedRxToNormedRdFinite (
		final double dblMaureyConstant,
		final org.drip.spaces.rxtord.NormedRxToNormedRd[] aNormedRxToNormedRd)
		throws java.lang.Exception
	{
		super (dblMaureyConstant);

		int iClassSize = null == (_aNormedRxToNormedRd = aNormedRxToNormedRd) ? 0 :
			_aNormedRxToNormedRd.length;

		if (null != _aNormedRxToNormedRd && 0 == iClassSize)
			throw new java.lang.Exception ("NormedRxToNormedRdFinite ctr: Invalid Inputs");

		for (int i = 0; i < iClassSize; ++i) {
			if (null == _aNormedRxToNormedRd[i])
				throw new java.lang.Exception ("NormedRxToNormedRdFinite ctr: Invalid Inputs");
		}
	}

	@Override public org.drip.spaces.cover.FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		return null;
	}

	@Override public org.drip.spaces.metric.GeneralizedMetricVectorSpace inputMetricVectorSpace()
	{
		return null == _aNormedRxToNormedRd ? null : _aNormedRxToNormedRd[0].inputMetricVectorSpace();
	}

	@Override public org.drip.spaces.metric.RdNormed outputMetricVectorSpace()
	{
		return null == _aNormedRxToNormedRd ? null : _aNormedRxToNormedRd[0].outputMetricVectorSpace();
	}

	/**
	 * Retrieve the Array of Function Spaces in the Class
	 * 
	 * @return The Array of Function Spaces in the Class
	 */

	public org.drip.spaces.rxtord.NormedRxToNormedRd[] functionSpaces()
	{
		return _aNormedRxToNormedRd;
	}

	/**
	 * Estimate for the Function Class Population Covering Number Array, one for each dimension
	 * 
	 * @param adblCover The Size of the Cover Array
	 * 
	 * @return Function Class Population Covering Number Estimate Array, one for each dimension
	 */

	public double[] populationCoveringNumber (
		final double[] adblCover)
	{
		if (null == _aNormedRxToNormedRd || null == adblCover) return null;

		int iFunctionSpaceSize = _aNormedRxToNormedRd.length;

		if (iFunctionSpaceSize != adblCover.length) return null;

		double[] adblPopulationCoveringNumber = _aNormedRxToNormedRd[0].populationCoveringNumber
			(adblCover[0]);

		if (!org.drip.quant.common.NumberUtil.IsValid (adblPopulationCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionPopulationCoveringNumber = _aNormedRxToNormedRd[i].populationCoveringNumber
				(adblCover[i]);

			if (!org.drip.quant.common.NumberUtil.IsValid (adblFunctionPopulationCoveringNumber))
				return null;

			int iDimension = adblFunctionPopulationCoveringNumber.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblPopulationCoveringNumber[j] < adblFunctionPopulationCoveringNumber[j])
					adblPopulationCoveringNumber[j] = adblFunctionPopulationCoveringNumber[j];
			}
		}

		return adblPopulationCoveringNumber;
	}

	/**
	 * Estimate for the Function Class Population Covering Number Array, one for each dimension
	 * 
	 * @param dblCover The Cover
	 * 
	 * @return Function Class Population Covering Number Estimate Array, one for each dimension
	 */

	public double[] populationCoveringNumber (
		final double dblCover)
	{
		int iDimension = outputMetricVectorSpace().dimension();

		double[] adblCover = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblCover[i] = dblCover;

		return populationCoveringNumber (adblCover);
	}

	/**
	 * Estimate for the Function Class Population Supremum Covering Number Array, one for each dimension
	 * 
	 * @param adblCover The Size of the Cover Array
	 * 
	 * @return Function Class Population Supremum Covering Number Estimate Array, one for each dimension
	 */

	public double[] populationSupremumCoveringNumber (
		final double[] adblCover)
	{
		if (null == _aNormedRxToNormedRd || null == adblCover) return null;

		int iFunctionSpaceSize = _aNormedRxToNormedRd.length;

		if (iFunctionSpaceSize != adblCover.length) return null;

		double[] adblPopulationSupremumCoveringNumber =
			_aNormedRxToNormedRd[0].populationSupremumCoveringNumber (adblCover[0]);

		if (!org.drip.quant.common.NumberUtil.IsValid (adblPopulationSupremumCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionPopulationSupremumCoveringNumber =
				_aNormedRxToNormedRd[i].populationSupremumCoveringNumber (adblCover[i]);

			if (!org.drip.quant.common.NumberUtil.IsValid (adblFunctionPopulationSupremumCoveringNumber))
				return null;

			int iDimension = adblFunctionPopulationSupremumCoveringNumber.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblPopulationSupremumCoveringNumber[j] <
					adblFunctionPopulationSupremumCoveringNumber[j])
					adblPopulationSupremumCoveringNumber[j] =
						adblFunctionPopulationSupremumCoveringNumber[j];
			}
		}

		return adblPopulationSupremumCoveringNumber;
	}

	/**
	 * Estimate for the Function Class Population Supremum Covering Number Array, one for each dimension
	 * 
	 * @param dblCover The Cover
	 * 
	 * @return Function Class Population Covering Supremum Number Estimate Array, one for each dimension
	 */

	public double[] populationSupremumCoveringNumber (
		final double dblCover)
	{
		int iDimension = outputMetricVectorSpace().dimension();

		double[] adblCover = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblCover[i] = dblCover;

		return populationSupremumCoveringNumber (adblCover);
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param adblCover The Size of the Cover Array
	 * 
	 * @return The Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 */

	public double[] sampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double[] adblCover)
	{
		if (null == _aNormedRxToNormedRd || null == adblCover) return null;

		int iFunctionSpaceSize = _aNormedRxToNormedRd.length;

		if (iFunctionSpaceSize != adblCover.length) return null;

		double[] adblSampleCoveringNumber = _aNormedRxToNormedRd[0].sampleCoveringNumber (gvvi,
			adblCover[0]);

		if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionSampleCoveringNumber = _aNormedRxToNormedRd[i].sampleCoveringNumber (gvvi,
				adblCover[i]);

			if (!org.drip.quant.common.NumberUtil.IsValid (adblFunctionSampleCoveringNumber)) return null;

			int iDimension = adblFunctionSampleCoveringNumber.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblSampleCoveringNumber[j] < adblFunctionSampleCoveringNumber[j])
					adblSampleCoveringNumber[j] = adblFunctionSampleCoveringNumber[j];
			}
		}

		return adblSampleCoveringNumber;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblCover The Size of the Cover Array
	 * 
	 * @return The Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 */

	public double[] sampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblCover)
	{
		int iDimension = outputMetricVectorSpace().dimension();

		double[] adblCover = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblCover[i] = dblCover;

		return sampleCoveringNumber (gvvi, adblCover);
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param adblCover The Size of the Cover Array
	 * 
	 * @return The Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 */

	public double[] sampleSupremumCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double[] adblCover)
	{
		if (null == _aNormedRxToNormedRd || null == adblCover) return null;

		int iFunctionSpaceSize = _aNormedRxToNormedRd.length;

		if (iFunctionSpaceSize != adblCover.length) return null;

		double[] adblSampleSupremumCoveringNumber = _aNormedRxToNormedRd[0].sampleSupremumCoveringNumber
			(gvvi, adblCover[0]);

		if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleSupremumCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionSampleSupremumCoveringNumber =
				_aNormedRxToNormedRd[i].sampleSupremumCoveringNumber (gvvi, adblCover[i]);

			if (!org.drip.quant.common.NumberUtil.IsValid (adblFunctionSampleSupremumCoveringNumber))
				return null;

			int iDimension = adblFunctionSampleSupremumCoveringNumber.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblSampleSupremumCoveringNumber[j] < adblFunctionSampleSupremumCoveringNumber[j])
					adblSampleSupremumCoveringNumber[j] = adblFunctionSampleSupremumCoveringNumber[j];
			}
		}

		return adblSampleSupremumCoveringNumber;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblCover The Cover
	 * 
	 * @return The Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 */

	public double[] sampleSupremumCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblCover)
	{
		int iDimension = outputMetricVectorSpace().dimension();

		double[] adblCover = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblCover[i] = dblCover;

		return sampleSupremumCoveringNumber (gvvi, adblCover);
	}

	/**
	 * Compute the Population R^d Metric Norm
	 * 
	 * @return The Population R^d Metric Norm
	 */

	public double[] populationRdMetricNorm()
	{
		if (null == _aNormedRxToNormedRd) return null;

		int iNumFunction = _aNormedRxToNormedRd.length;

		double[] adblPopulationRdMetricNorm = _aNormedRxToNormedRd[0].populationMetricNorm();

		if (!org.drip.quant.common.NumberUtil.IsValid (adblPopulationRdMetricNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblPopulationMetricNorm = _aNormedRxToNormedRd[i].populationMetricNorm();

			if (!org.drip.quant.common.NumberUtil.IsValid (adblPopulationMetricNorm)) return null;

			int iDimension = adblPopulationMetricNorm.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblPopulationRdMetricNorm[j] < adblPopulationMetricNorm[j])
					adblPopulationRdMetricNorm[j] = adblPopulationMetricNorm[j];
			}
		}

		return adblPopulationRdMetricNorm;
	}

	/**
	 * Compute the Population R^d Supremum Norm
	 * 
	 * @return The Population R^d Supremum Norm
	 */

	public double[] populationRdSupremumNorm()
	{
		if (null == _aNormedRxToNormedRd) return null;

		int iNumFunction = _aNormedRxToNormedRd.length;

		double[] adblPopulationRdSupremumNorm = _aNormedRxToNormedRd[0].populationESS();

		if (!org.drip.quant.common.NumberUtil.IsValid (adblPopulationRdSupremumNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblPopulationSupremumNorm = _aNormedRxToNormedRd[i].populationESS();

			if (!org.drip.quant.common.NumberUtil.IsValid (adblPopulationSupremumNorm)) return null;

			int iDimension = adblPopulationSupremumNorm.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblPopulationRdSupremumNorm[j] < adblPopulationSupremumNorm[j])
					adblPopulationRdSupremumNorm[j] = adblPopulationSupremumNorm[j];
			}
		}

		return adblPopulationRdSupremumNorm;
	}

	/**
	 * Compute the Sample R^d Metric Norm
	 * 
	 * @param gvvi The Validated Vector Space Instance
	 * 
	 * @return The Sample R^d Metric Norm
	 */

	public double[] sampleRdMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		if (null == _aNormedRxToNormedRd) return null;

		int iNumFunction = _aNormedRxToNormedRd.length;

		double[] adblSampleRdMetricNorm = _aNormedRxToNormedRd[0].sampleMetricNorm (gvvi);

		if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleRdMetricNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblSampleMetricNorm = _aNormedRxToNormedRd[i].sampleMetricNorm (gvvi);

			if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleMetricNorm)) return null;

			int iDimension = adblSampleMetricNorm.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblSampleRdMetricNorm[j] < adblSampleMetricNorm[j])
					adblSampleRdMetricNorm[j] = adblSampleMetricNorm[j];
			}
		}

		return adblSampleRdMetricNorm;
	}

	/**
	 * Compute the Sample R^d Supremum Norm
	 * 
	 * @param gvvi The Validated Vector Space Instance
	 * 
	 * @return The Sample R^d Supremum Norm
	 */

	public double[] sampleRdSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		if (null == _aNormedRxToNormedRd) return null;

		int iNumFunction = _aNormedRxToNormedRd.length;

		double[] adblSampleRdSupremumNorm = _aNormedRxToNormedRd[0].sampleSupremumNorm (gvvi);

		if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleRdSupremumNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblSampleSupremumNorm = _aNormedRxToNormedRd[i].sampleSupremumNorm (gvvi);

			if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleSupremumNorm)) return null;

			int iDimension = adblSampleSupremumNorm.length;

			for (int j = 0; j < iDimension; ++j) {
				if (adblSampleRdSupremumNorm[j] < adblSampleSupremumNorm[j])
					adblSampleRdSupremumNorm[j] = adblSampleSupremumNorm[j];
			}
		}

		return adblSampleRdSupremumNorm;
	}

	@Override public double operatorPopulationMetricNorm()
		throws java.lang.Exception
	{
		double[] adblPopulationMetricNorm = populationRdMetricNorm();

		if (null == adblPopulationMetricNorm)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorPopulationMetricNorm => Invalid Inputs");

		int iDimension = adblPopulationMetricNorm.length;
		double dblOperatorPopulationMetricNorm = java.lang.Double.NaN;

		if (0 == iDimension)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorPopulationMetricNorm => Invalid Inputs");

		for (int j = 0; j < iDimension; ++j) {
			if (0 == j)
				dblOperatorPopulationMetricNorm = adblPopulationMetricNorm[j];
			else {
				if (dblOperatorPopulationMetricNorm < adblPopulationMetricNorm[j])
					dblOperatorPopulationMetricNorm = adblPopulationMetricNorm[j];
			}
		}

		return dblOperatorPopulationMetricNorm;
	}

	@Override public double operatorPopulationSupremumNorm()
		throws java.lang.Exception
	{
		double[] adblPopulationSupremumNorm = populationRdSupremumNorm();

		if (null == adblPopulationSupremumNorm)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorPopulationSupremumNorm => Invalid Inputs");

		int iDimension = adblPopulationSupremumNorm.length;
		double dblOperatorPopulationSupremumNorm = java.lang.Double.NaN;

		if (0 == iDimension)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorPopulationSupremumNorm => Invalid Inputs");

		for (int j = 0; j < iDimension; ++j) {
			if (0 == j)
				dblOperatorPopulationSupremumNorm = adblPopulationSupremumNorm[j];
			else {
				if (dblOperatorPopulationSupremumNorm < adblPopulationSupremumNorm[j])
					dblOperatorPopulationSupremumNorm = adblPopulationSupremumNorm[j];
			}
		}

		return dblOperatorPopulationSupremumNorm;
	}

	@Override public double operatorSampleMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		double[] adblSampleMetricNorm = sampleRdMetricNorm (gvvi);

		if (null == adblSampleMetricNorm)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorSampleMetricNorm => Invalid Inputs");

		int iDimension = adblSampleMetricNorm.length;
		double dblOperatorSampleMetricNorm = java.lang.Double.NaN;

		if (0 == iDimension)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorSampleMetricNorm => Invalid Inputs");

		for (int j = 0; j < iDimension; ++j) {
			if (0 == j)
				dblOperatorSampleMetricNorm = adblSampleMetricNorm[j];
			else {
				if (dblOperatorSampleMetricNorm < adblSampleMetricNorm[j])
					dblOperatorSampleMetricNorm = adblSampleMetricNorm[j];
			}
		}

		return dblOperatorSampleMetricNorm;
	}

	@Override public double operatorSampleSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		double[] adblSampleSupremumNorm = sampleRdSupremumNorm (gvvi);

		if (null == adblSampleSupremumNorm)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorSampleSupremumNorm => Invalid Inputs");

		int iDimension = adblSampleSupremumNorm.length;
		double dblOperatorSampleSupremumNorm = java.lang.Double.NaN;

		if (0 == iDimension)
			throw new java.lang.Exception
				("NormedRxToNormedRdFinite::operatorSampleSupremumNorm => Invalid Inputs");

		for (int j = 0; j < iDimension; ++j) {
			if (0 == j)
				dblOperatorSampleSupremumNorm = adblSampleSupremumNorm[j];
			else {
				if (dblOperatorSampleSupremumNorm < adblSampleSupremumNorm[j])
					dblOperatorSampleSupremumNorm = adblSampleSupremumNorm[j];
			}
		}

		return dblOperatorSampleSupremumNorm;
	}
}
