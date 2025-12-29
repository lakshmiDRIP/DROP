
package org.drip.measure.lebesgue;

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
 * <i>RdUniform</i> implements the R<sup>d</sup> Lebesgue Measure Distribution that corresponds to a Uniform
 * R<sup>d</sup> d-Volume Space.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/lebesgue/README.md">Uniform Piece-wise Lebesgue Measure</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdUniform extends org.drip.measure.continuous.RdDistribution {
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
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblX[i]) || adblX[i] > adblRightEdge[i])
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
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblXLeft[i]) || adblXLeft[i] < adblLeftEdge[i] ||
				!org.drip.numerical.common.NumberUtil.IsValid (adblXRight[i]) || adblXRight[i] >
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
