
package org.drip.sequence.functional;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>MultivariateRandom</i> contains the implementation of the objective Function dependent on Multivariate
 * Random Variables.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/functional">Functional</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class MultivariateRandom extends org.drip.function.definition.RdToR1 {

	protected MultivariateRandom()
	{
		super (null);
	}

	/**
	 * Compute the Target Variate Function Metrics conditional on the specified Input Non-Target Variate
	 *  Parameter Sequence Off of the Target Variate Ghost Sample Sequence
	 * 
	 * @param adblNonTargetVariate The Non-Target Variate Parameters
	 * @param iTargetVariateIndex Target Variate Index
	 * @param adblTargetVariateGhostSample Target Variate Ghost Sample
	 * 
	 * @return The Variate-specific Function Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ghostTargetVariateMetrics (
		final double[] adblNonTargetVariate,
		final int iTargetVariateIndex,
		final double[] adblTargetVariateGhostSample)
	{
		if (!org.drip.function.definition.RdToR1.ValidateInput (adblNonTargetVariate) ||
			null == adblTargetVariateGhostSample)
			return null;

		int iNumNonTargetVariate = adblNonTargetVariate.length;
		int iNumTargetVariateSample = adblTargetVariateGhostSample.length;

		if (0 > iTargetVariateIndex || iTargetVariateIndex > iNumNonTargetVariate || 0 ==
			iNumTargetVariateSample)
			return null;

		double[] adblFunctionArgs = new double[iNumNonTargetVariate + 1];
		double[] adblFunctionSequence = new double[iNumTargetVariateSample];

		for (int i = 0; i < iNumNonTargetVariate; ++i) {
			if (i < iTargetVariateIndex)
				adblFunctionArgs[i] = adblNonTargetVariate[i];
			else if (i >= iTargetVariateIndex)
				adblFunctionArgs[i + 1] = adblNonTargetVariate[i];
		}

		try {
			for (int i = 0; i < iNumTargetVariateSample; ++i) {
				adblFunctionArgs[iTargetVariateIndex] = adblTargetVariateGhostSample[i];

				adblFunctionSequence[i] = evaluate (adblFunctionArgs);
			}

			return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics (adblFunctionSequence, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Target Variate Function Metrics conditional on the specified Input Non-Target Variate
	 *  Parameter Sequence Off of the Target Variate Ghost Sample Sequence
	 * 
	 * @param aSSAM Array of Variate Sequence Metrics
	 * @param aiNonTargetVariateSequenceIndex Array of Non-Target Variate Sequence Indexes
	 * @param iTargetVariateIndex Target Variate Index
	 * @param adblTargetVariateGhostSample Target Variate Ghost Sample
	 * 
	 * @return The Variate-specific Function Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ghostTargetVariateMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM,
		final int[] aiNonTargetVariateSequenceIndex,
		final int iTargetVariateIndex,
		final double[] adblTargetVariateGhostSample)
	{
		if (null == aSSAM || null == aiNonTargetVariateSequenceIndex || 0 > iTargetVariateIndex) return null;

		int iNumNonTargetVariate = aSSAM.length - 1;
		double[] adblNonTargetVariate = new double[iNumNonTargetVariate];

		if (0 >= iNumNonTargetVariate || iNumNonTargetVariate != aiNonTargetVariateSequenceIndex.length ||
			iTargetVariateIndex > iNumNonTargetVariate)
			return null;

		for (int i = 0; i < iNumNonTargetVariate; ++i)
			adblNonTargetVariate[i] = aSSAM[i < iTargetVariateIndex ? i : i +
				1].sequence()[aiNonTargetVariateSequenceIndex[i]];

		return ghostTargetVariateMetrics (adblNonTargetVariate, iTargetVariateIndex,
			adblTargetVariateGhostSample);
	}

	/**
	 * Compute the Target Variate Function Metrics over the full Non-target Variate Empirical Distribution
	 *  Off of the Target Variate Ghost Sample Sequence
	 * 
	 * @param aSSAM Array of Variate Sequence Metrics
	 * @param iTargetVariateIndex Target Variate Index
	 * @param adblTargetVariateGhostSample Target Variate Ghost Sample
	 * 
	 * @return The Variate-specific Function Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ghostTargetVariateMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM,
		final int iTargetVariateIndex,
		final double[] adblTargetVariateGhostSample)
	{
		if (null == aSSAM || 0 > iTargetVariateIndex) return null;

		int iTargetVariateVarianceIndex = 0;
		int iNumNonTargetVariate = aSSAM.length - 1;

		if (0 >= iNumNonTargetVariate) return null;

		org.drip.spaces.iterator.SequenceIndexIterator sii =
			org.drip.spaces.iterator.SequenceIndexIterator.Standard (iNumNonTargetVariate,
				aSSAM[0].sequence().length);

		if (null == sii) return null;

		double[] adblTargetVariateVariance = new double[sii.size()];

		int[] aiNonTargetVariateSequenceIndex = sii.first();

		while (null != aiNonTargetVariateSequenceIndex && aiNonTargetVariateSequenceIndex.length ==
			iNumNonTargetVariate) {
			org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssamGhostConditional =
				ghostTargetVariateMetrics (aSSAM, aiNonTargetVariateSequenceIndex, iTargetVariateIndex,
					adblTargetVariateGhostSample);

			if (null == ssamGhostConditional) return null;

			adblTargetVariateVariance[iTargetVariateVarianceIndex++] =
				ssamGhostConditional.empiricalVariance();

			aiNonTargetVariateSequenceIndex = sii.next();
		}

		try {
			return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics (adblTargetVariateVariance,
				null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Target Variate Function Metrics Conditional on the specified Input Non-Target Variate
	 *  Parameter Sequence
	 * 
	 * @param adblNonTargetVariate The Non-Target Variate Parameters
	 * @param iTargetVariateIndex Target Variate Index
	 * @param ssamTarget Target Variate Metrics
	 * 
	 * @return The Variate-specific Function Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics conditionalTargetVariateMetrics (
		final double[] adblNonTargetVariate,
		final int iTargetVariateIndex,
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssamTarget)
	{
		return null == ssamTarget ? null : ghostTargetVariateMetrics (adblNonTargetVariate,
			iTargetVariateIndex, ssamTarget.sequence());
	}

	/**
	 * Compute the Target Variate Function Metrics Conditional on the specified Input Non-target Variate
	 *  Parameter Sequence
	 * 
	 * @param aSSAM Array of Variate Sequence Metrics
	 * @param aiNonTargetVariateSequenceIndex Array of Non-Target Variate Sequence Indexes
	 * @param iTargetVariateIndex Target Variate Index
	 * 
	 * @return The Variate-specific Function Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics conditionalTargetVariateMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM,
		final int[] aiNonTargetVariateSequenceIndex,
		final int iTargetVariateIndex)
	{
		if (null == aSSAM || null == aiNonTargetVariateSequenceIndex || 0 > iTargetVariateIndex) return null;

		int iNumNonTargetVariate = aSSAM.length - 1;
		double[] adblNonTargetVariate = new double[iNumNonTargetVariate];

		if (0 >= iNumNonTargetVariate || iNumNonTargetVariate != aiNonTargetVariateSequenceIndex.length ||
			iTargetVariateIndex > iNumNonTargetVariate)
			return null;

		for (int i = 0; i < iNumNonTargetVariate; ++i)
			adblNonTargetVariate[i] = aSSAM[i < iTargetVariateIndex ? i : i +
				1].sequence()[aiNonTargetVariateSequenceIndex[i]];

		return conditionalTargetVariateMetrics (adblNonTargetVariate, iTargetVariateIndex,
			aSSAM[iTargetVariateIndex]);
	}

	/**
	 * Compute the Target Variate Function Metrics over the full Non-target Variate Empirical Distribution
	 * 
	 * @param aSSAM Array of Variate Sequence Metrics
	 * @param iTargetVariateIndex Target Variate Index
	 * 
	 * @return The Variate-specific Function Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics unconditionalTargetVariateMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] aSSAM,
		final int iTargetVariateIndex)
	{
		if (null == aSSAM || 0 > iTargetVariateIndex) return null;

		int iTargetVariateVarianceIndex = 0;
		int iNumNonTargetVariate = aSSAM.length - 1;

		if (0 >= iNumNonTargetVariate) return null;

		org.drip.spaces.iterator.SequenceIndexIterator sii =
			org.drip.spaces.iterator.SequenceIndexIterator.Standard (iNumNonTargetVariate,
				aSSAM[0].sequence().length);

		if (null == sii) return null;

		double[] adblTargetVariateVariance = new double[sii.size()];

		int[] aiNonTargetVariateSequenceIndex = sii.first();

		while (null != aiNonTargetVariateSequenceIndex && aiNonTargetVariateSequenceIndex.length ==
			iNumNonTargetVariate) {
			org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssamConditional =
				conditionalTargetVariateMetrics (aSSAM, aiNonTargetVariateSequenceIndex,
					iTargetVariateIndex);

			if (null == ssamConditional) return null;

			adblTargetVariateVariance[iTargetVariateVarianceIndex++] = ssamConditional.empiricalVariance();

			aiNonTargetVariateSequenceIndex = sii.next();
		}

		try {
			return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics (adblTargetVariateVariance,
				null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
