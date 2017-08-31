
package org.drip.measure.statistics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * UnivariateDiscreteThin analyzes and computes the "Thin" Statistics for the Realized Univariate Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateDiscreteThin {
	private double _dblError = java.lang.Double.NaN;
	private double _dblAverage = java.lang.Double.NaN;
	private double _dblMaximum = java.lang.Double.NaN;
	private double _dblMinimum = java.lang.Double.NaN;

	/**
	 * UnivariateDiscreteThin Constructor
	 * 
	 * @param adblSequence The Univariate Sequence
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public UnivariateDiscreteThin (
		final double[] adblSequence)
		throws java.lang.Exception
	{
		if (null == adblSequence)
			throw new java.lang.Exception ("UnivariateDiscreteThin Constructor => Invalid Inputs");

		_dblError = 0.;
		_dblAverage = 0.;
		_dblMaximum = 0.;
		_dblMinimum = 0.;
		int iSequenceSize = adblSequence.length;

		if (0 == iSequenceSize)
			throw new java.lang.Exception ("UnivariateDiscreteThin Constructor => Invalid Inputs");

		for (int i = 0; i < iSequenceSize; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblSequence[i]))
				throw new java.lang.Exception ("UnivariateDiscreteThin Constructor => Invalid Inputs");

			if (0 == i) {
				_dblMaximum = adblSequence[0];
				_dblMinimum = adblSequence[0];
			} else {
				if (_dblMaximum < adblSequence[i]) _dblMaximum = adblSequence[i];

				if (_dblMinimum > adblSequence[i]) _dblMinimum = adblSequence[i];
			}

			_dblAverage = _dblAverage + adblSequence[i];
		}

		_dblAverage /= iSequenceSize;

		for (int i = 0; i < iSequenceSize; ++i)
			_dblError = _dblError + java.lang.Math.abs (_dblAverage - adblSequence[i]);

		_dblError /= iSequenceSize;
	}

	/**
	 * Retrieve the Sequence Average
	 * 
	 * @return The Sequence Average
	 */

	public double average()
	{
		return _dblAverage;
	}

	/**
	 * Retrieve the Sequence Error
	 * 
	 * @return The Sequence Error
	 */

	public double error()
	{
		return _dblError;
	}

	/**
	 * Retrieve the Sequence Maximum
	 * 
	 * @return The Sequence Maximum
	 */

	public double maximum()
	{
		return _dblMaximum;
	}

	/**
	 * Retrieve the Sequence Minimum
	 * 
	 * @return The Sequence Minimum
	 */

	public double minimum()
	{
		return _dblMinimum;
	}
}
