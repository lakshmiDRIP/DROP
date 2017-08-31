
package org.drip.state.nonlinear;

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
 * FlatForwardRepoCurve manages the Repo Latent State, using the Forward Repo Rate as the State Response
 *  Representation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardRepoCurve extends org.drip.state.repo.ExplicitBootRepoCurve {
	private int[] _aiPillarDate = null;
	private double[] _adblRepoForward = null;

	/**
	 * FlatForwardRepoCurve Constructor
	 * 
	 * @param iEpochDate Epoch Date
	 * @param comp The Repo Component
	 * @param aiPillarDate Array of Pillar Dates
	 * @param adblRepoForward Array of Repo Forward Rates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FlatForwardRepoCurve (
		final int iEpochDate,
		final org.drip.product.definition.Component comp,
		final int[] aiPillarDate,
		final double[] adblRepoForward)
		throws java.lang.Exception
	{
		super (iEpochDate, comp);

		if (null == (_aiPillarDate = aiPillarDate) || null == (_adblRepoForward = adblRepoForward) ||
			_aiPillarDate.length != _adblRepoForward.length)
			throw new java.lang.Exception ("FlatForwardRepoCurve ctr => Invalid Inputs");

		int iNumPillar = _aiPillarDate.length;

		for (int i = 0; i < iNumPillar; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_aiPillarDate[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (_adblRepoForward[i]))
				throw new java.lang.Exception ("FlatForwardRepoCurve ctr => Invalid Inputs");
		}
	}

	@Override public double repo (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate >= component().maturityDate().julian())
			throw new java.lang.Exception ("FlatForwardRepoCurve::repo => Invalid Input");

		if (iDate <= epoch().julian()) return _adblRepoForward[0];

		int iNumPillar = _adblRepoForward.length;

		for (int i = 1; i < iNumPillar; ++i) {
			if (_aiPillarDate[i - 1] <= iDate && _aiPillarDate[i] > iDate)
				return _adblRepoForward[i];
		}

		return _adblRepoForward[iNumPillar - 1];
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblRepoForward.length)
			return false;

		for (int i = iNodeIndex; i < _adblRepoForward.length; ++i)
			_adblRepoForward[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblRepoForward.length)
			return false;

		for (int i = iNodeIndex; i < _adblRepoForward.length; ++i)
			_adblRepoForward[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblRepoForward.length; ++i)
			_adblRepoForward[i] = dblValue;

		return true;
	}
}
