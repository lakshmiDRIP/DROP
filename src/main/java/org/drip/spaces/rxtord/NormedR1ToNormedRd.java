
package org.drip.spaces.rxtord;

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
 * <i>NormedR1ToNormedRd</i> is the Abstract Class underlying the f : Validated Normed R<sup>1</sup> To
 * Validated Normed R<sup>d</sup> Function Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtord">R<sup>x</sup> To R<sup>d</sup></a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedR1ToNormedRd extends org.drip.spaces.rxtord.NormedRxToNormedRd {
	private org.drip.spaces.metric.R1Normed _r1Input = null;
	private org.drip.spaces.metric.RdNormed _rdOutput = null;
	private org.drip.function.definition.R1ToRd _funcR1ToRd = null;

	protected NormedR1ToNormedRd (
		final org.drip.spaces.metric.R1Normed r1Input,
		final org.drip.spaces.metric.RdNormed rdOutput,
		final org.drip.function.definition.R1ToRd funcR1ToRd)
		throws java.lang.Exception
	{
		if (null == (_r1Input = r1Input) || null == (_rdOutput = rdOutput))
			throw new java.lang.Exception ("NormedR1ToNormedRd ctr: Invalid Inputs");

		_funcR1ToRd = funcR1ToRd;
	}

	@Override public org.drip.spaces.metric.R1Normed inputMetricVectorSpace()
	{
		return _r1Input;
	}

	@Override public org.drip.spaces.metric.RdNormed outputMetricVectorSpace()
	{
		return _rdOutput;
	}

	/**
	 * Retrieve the Underlying R1ToRd Function
	 * 
	 * @return The Underlying R1ToR1 Function
	 */

	public org.drip.function.definition.R1ToRd function()
	{
		return _funcR1ToRd;
	}

	@Override public double[] sampleSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		if (null == _funcR1ToRd || null == gvvi || !gvvi.tensorSpaceType().match (_r1Input) || !(gvvi
			instanceof org.drip.spaces.instance.ValidatedR1))
			return null;

		org.drip.spaces.instance.ValidatedR1 vr1 = (org.drip.spaces.instance.ValidatedR1) gvvi;

		double[] adblInstance = vr1.instance();

		int iNumSample = adblInstance.length;

		int iOutputDimension = _rdOutput.dimension();

		double[] adblSupremumNorm = _funcR1ToRd.evaluate (adblInstance[0]);

		if (null == adblSupremumNorm || iOutputDimension != adblSupremumNorm.length ||
			!org.drip.quant.common.NumberUtil.IsValid (adblSupremumNorm))
			return null;

		for (int i = 0; i < iOutputDimension; ++i)
			adblSupremumNorm[i] = java.lang.Math.abs (adblSupremumNorm[i]);

		for (int i = 1; i < iNumSample; ++i) {
			double[] adblSampleNorm = _funcR1ToRd.evaluate (adblInstance[i]);

			if (null == adblSampleNorm || iOutputDimension != adblSampleNorm.length) return null;

			for (int j = 0; j < iOutputDimension; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblSampleNorm[j])) return null;

				if (adblSampleNorm[j] > adblSupremumNorm[j]) adblSupremumNorm[j] = adblSampleNorm[j];
			}
		}

		return adblSupremumNorm;
	}

	@Override public double[] sampleMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		int iPNorm = outputMetricVectorSpace().pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) sampleSupremumNorm (gvvi);

		if (null == _funcR1ToRd || null == gvvi || !gvvi.tensorSpaceType().match (_r1Input) || !(gvvi
			instanceof org.drip.spaces.instance.ValidatedR1))
			return null;

		int iOutputDimension = _rdOutput.dimension();

		double[] adblInstance = ((org.drip.spaces.instance.ValidatedR1) gvvi).instance();

		double[] adblMetricNorm = new double[iOutputDimension];
		int iNumSample = adblInstance.length;

		for (int i = 0; i < iNumSample; ++i)
			adblMetricNorm[i] = 0.;

		for (int i = 0; i < iNumSample; ++i) {
			double[] adblPointValue = _funcR1ToRd.evaluate (adblInstance[i]);

			if (null == adblPointValue || iOutputDimension != adblPointValue.length) return null;

			for (int j = 0; j < iOutputDimension; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblPointValue[j])) return null;

				adblMetricNorm[j] += java.lang.Math.pow (java.lang.Math.abs (adblPointValue[j]), iPNorm);
			}
		}

		for (int i = 0; i < iNumSample; ++i)
			adblMetricNorm[i] = java.lang.Math.pow (adblMetricNorm[i], 1. / iPNorm);

		return adblMetricNorm;
	}

	@Override public double[] populationESS()
	{
		try {
			return null == _funcR1ToRd ? null : _funcR1ToRd.evaluate (_r1Input.populationMode());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
