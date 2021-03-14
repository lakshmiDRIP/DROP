
package org.drip.spaces.functionclass;

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
 * <i>NormedRxToNormedRdFinite</i> implements the Class F with f E f : Normed R<sup>x</sup> To Normed
 * R<sup>d</sup> Space of Finite Functions. The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of
 *  			Operators in Banach Spaces <i>Annals of the Fourier Institute</i> <b>35 (3)</b> 79-118
 *  	</li>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  	<li>
 *  		Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function
 *  			Classes, in: <i>Proceedings of the 13th Annual Conference on Computational Learning
 *  				Theory</i> <b>ACM</b> New York
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass/README.md">Normed Finite Spaces Function Class</a></li>
 *  </ul>
 * <br><br>
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblPopulationCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionPopulationCoveringNumber = _aNormedRxToNormedRd[i].populationCoveringNumber
				(adblCover[i]);

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblFunctionPopulationCoveringNumber))
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblPopulationSupremumCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionPopulationSupremumCoveringNumber =
				_aNormedRxToNormedRd[i].populationSupremumCoveringNumber (adblCover[i]);

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblFunctionPopulationSupremumCoveringNumber))
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionSampleCoveringNumber = _aNormedRxToNormedRd[i].sampleCoveringNumber (gvvi,
				adblCover[i]);

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblFunctionSampleCoveringNumber)) return null;

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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleSupremumCoveringNumber)) return null;

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double[] adblFunctionSampleSupremumCoveringNumber =
				_aNormedRxToNormedRd[i].sampleSupremumCoveringNumber (gvvi, adblCover[i]);

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblFunctionSampleSupremumCoveringNumber))
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblPopulationRdMetricNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblPopulationMetricNorm = _aNormedRxToNormedRd[i].populationMetricNorm();

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblPopulationMetricNorm)) return null;

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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblPopulationRdSupremumNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblPopulationSupremumNorm = _aNormedRxToNormedRd[i].populationESS();

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblPopulationSupremumNorm)) return null;

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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleRdMetricNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblSampleMetricNorm = _aNormedRxToNormedRd[i].sampleMetricNorm (gvvi);

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleMetricNorm)) return null;

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

		if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleRdSupremumNorm)) return null;

		for (int i = 1; i < iNumFunction; ++i) {
			double[] adblSampleSupremumNorm = _aNormedRxToNormedRd[i].sampleSupremumNorm (gvvi);

			if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleSupremumNorm)) return null;

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
