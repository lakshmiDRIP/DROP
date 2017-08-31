
package org.drip.measure.lebesgue;

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
 * RdUniform implements the R^d Lebesgue Measure Distribution that corresponds to a Uniform R^d d-Volume
 *  Space.
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
