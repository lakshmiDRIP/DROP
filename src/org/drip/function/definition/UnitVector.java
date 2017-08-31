
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * UnitVector implements the Normalized R^d Unit Vector.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnitVector {
	private double[] _adblComponent;

	/**
	 * Construct an Instance of the Unit Vector from the Input Vector
	 * 
	 * @param adbl The Input Double Vector
	 * 
	 * @return The Unit Vector Instance
	 */

	public static final UnitVector Standard (
		final double[] adbl)
	{
		if (null == adbl) return null;

		int iDimension = adbl.length;
		double dblGradientModulus = 0.;
		double[] adblComponent = new double[iDimension];

		if (0 == iDimension) return null;

		for (int i = 0; i < iDimension; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adbl[i])) return null;

			dblGradientModulus += adbl[i] * adbl[i];
		}

		if (0. == dblGradientModulus) return null;

		dblGradientModulus = java.lang.Math.sqrt (dblGradientModulus);

		for (int i = 0; i < iDimension; ++i)
			adblComponent[i] = adbl[i] / dblGradientModulus;

		return new UnitVector (adblComponent);
	}

	protected UnitVector (
		final double[] adblComponent)
	{
		_adblComponent = adblComponent;
	}

	/**
	 * Retrieve the Unit Vector's Component Array
	 * 
	 * @return The Unit Vector's Component Array
	 */

	public double[] component()
	{
		return _adblComponent;
	}

	/**
	 * Compute the Directional Increment along the Vector
	 * 
	 * @param adblVariate The Starting R^d Variate
	 * @param dblStepLength The Step Length
	 * 
	 * @return The Directionally Incremented Vector
	 */

	public double[] directionalIncrement (
		final double[] adblVariate,
		final double dblStepLength)
	{
		if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (dblStepLength)) return null;

		int iVariateDimension = adblVariate.length;
		double[] adblIncrementedVariate = new double[iVariateDimension];

		if (iVariateDimension != _adblComponent.length) return null;

		for (int i = 0; i < iVariateDimension; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblVariate[i])) return null;

			adblIncrementedVariate[i] = adblVariate[i] + dblStepLength * _adblComponent[i];
		}

		return adblIncrementedVariate;
	}
}
