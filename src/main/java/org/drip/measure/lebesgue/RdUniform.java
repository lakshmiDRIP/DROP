
package org.drip.measure.lebesgue;

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
 * <i>RdUniform</i> implements the R<sup>d</sup> Lebesgue Measure Distribution that corresponds to a Uniform
 * R<sup>d</sup> d-Volume Space.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/lebesgue">Lebesgue</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdUniform extends org.drip.measure.continuous.Rd {
	private org.drip.spaces.tensor.RdGeneralizedVector _gmvs = null;

	/**
	 * RdUniform Constructor
	 * 
	 * @param gmvs The Vector Space Underlying the Measure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdUniform (
		final org.drip.spaces.tensor.RdGeneralizedVector gmvs)
		throws java.lang.Exception
	{
		if (null == (_gmvs = gmvs)) throw new java.lang.Exception ("RdUniform ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Vector Space Underlying the Measure
	 * 
	 * @return The Vector Space Underlying the Measure
	 */

	public org.drip.spaces.tensor.RdGeneralizedVector measureSpace()
	{
		return _gmvs;
	}

	@Override public double cumulative (
		final double[] adblX)
		throws java.lang.Exception
	{
		double[] adblLeftEdge = _gmvs.leftDimensionEdge();

		double dblCumulative = 1.;
		int iDimension = adblLeftEdge.length;

		if (null == adblX || iDimension != adblX.length)
			throw new java.lang.Exception ("RdLebesgue::cumulative => Invalid Inputs");

		double[] adblRightEdge = _gmvs.rightDimensionEdge();

		for (int i = 0; i < iDimension; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblX[i]) || adblX[i] > adblRightEdge[i])
				throw new java.lang.Exception ("RdUniform::cumulative => Invalid Inputs");

			dblCumulative *= (adblX[i] - adblLeftEdge[i]) / (adblRightEdge[i] - adblLeftEdge[i]);
		}

		return dblCumulative;
	}

	@Override public double incremental (
		final double[] adblXLeft,
		final double[] adblXRight)
		throws java.lang.Exception
	{
		if (null == adblXLeft || null == adblXRight)
			throw new java.lang.Exception ("RdUniform::incremental => Invalid Inputs");

		double[] adblLeftEdge = _gmvs.leftDimensionEdge();

		double dblIncremental = 1.;
		int iDimension = adblLeftEdge.length;

		if (iDimension != adblXLeft.length || iDimension != adblXRight.length)
			throw new java.lang.Exception ("RdUniform::incremental => Invalid Inputs");

		double[] adblRightEdge = _gmvs.rightDimensionEdge();

		for (int i = 0; i < iDimension; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblXLeft[i]) || adblXLeft[i] < adblLeftEdge[i] ||
				!org.drip.quant.common.NumberUtil.IsValid (adblXRight[i]) || adblXRight[i] >
					adblRightEdge[i])
				throw new java.lang.Exception ("RdUniform::incremental => Invalid Inputs");

			dblIncremental *= (adblXRight[i] - adblXLeft[i]) / (adblRightEdge[i] - adblLeftEdge[i]);
		}

		return dblIncremental;
	}

	@Override public double density (
		final double[] adblX)
		throws java.lang.Exception
	{
		return 1. / _gmvs.hyperVolume();
	}
}
