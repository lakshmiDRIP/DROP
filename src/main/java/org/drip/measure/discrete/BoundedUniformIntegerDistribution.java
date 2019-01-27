
package org.drip.measure.discrete;

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
 * <i>BoundedUniformIntegerDistribution</i> implements the Univariate Bounded Uniform Integer Distribution,
 * with the Integer being generated between a (n inclusive) lower and an upper Bound.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete">Discrete</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedUniformIntegerDistribution extends org.drip.measure.continuous.R1Univariate {
	private int _iStart = -1;
	private int _iFinish = -1;

	/**
	 * Construct a Univariate Bounded Uniform Integer Distribution
	 * 
	 * @param iStart The Starting Integer
	 * @param iFinish The Finishing Integer
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public BoundedUniformIntegerDistribution (
		final int iStart,
		final int iFinish)
		throws java.lang.Exception
	{
		if ((_iFinish = iFinish) <= (_iStart = iStart))
			throw new java.lang.Exception ("BoundedUniformIntegerDistribution constructor: Invalid inputs");
	}

	/**
	 * Retrieve the Start
	 * 
	 * @return The Start
	 */

	public int start()
	{
		return _iStart;
	}

	/**
	 * Retrieve the Finish
	 * 
	 * @return The Finish
	 */

	public int finish()
	{
		return _iFinish;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception
				("BoundedUniformIntegerDistribution::cumulative => Invalid inputs");

		if (dblX <= _iStart) return 0.;

		if (dblX >= _iFinish) return 1.;

		return (dblX - _iStart) / (_iFinish - _iStart);
	}

	@Override public double incremental (
		final double dblXLeft,
		final double dblXRight)
		throws java.lang.Exception
	{
		return cumulative (dblXRight) - cumulative (dblXLeft);
	}

	@Override public double invCumulative (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || dblY < 0. || dblY > 1.)
			throw new java.lang.Exception
				("BoundedUniformIntegerDistribution::invCumulative => Invalid inputs");

	    return dblY * (_iFinish - _iStart) + _iStart;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		throw new java.lang.Exception
			("BoundedUniformIntegerDistribution::density => Not available for discrete distributions");
	}

	@Override public double mean()
	{
	    return 0.5 * (_iFinish + _iStart);
	}

	@Override public double variance()
	{
	    return (_iFinish - _iStart) * (_iFinish - _iStart) / 12.;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		int iGridWidth = _iFinish - _iStart;
		double[] adblX = new double[iGridWidth];
		double[] adblY = new double[iGridWidth];

		for (int i = 0; i < iGridWidth; ++i) {
			adblY[i] = 1. / iGridWidth;
			adblX[i] = _iStart + (i + 1);
		}

		try {
			
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
