
package org.drip.xva.hypothecation;

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
 * CollateralGroupPath accumulates the Vertex Realizations of the Sequence in a Single Path Projection Run
 *  along the Granularity of a Regular Collateral Hypothecation Group. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroupPath {
	private org.drip.xva.hypothecation.CollateralGroupVertex[] _aCGV = null;

	/**
	 * CollateralGroupPath Constructor
	 * 
	 * @param aCGV The Array of Collateral Hypothecation Group Trajectory Vertexes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupVertex[] aCGV)
		throws java.lang.Exception
	{
		if (null == (_aCGV = aCGV))
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");

		int iNumPath = _aCGV.length;

		if (1 >= iNumPath)
			throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");

		for (int i = 0; i < iNumPath; ++i) {
			if (null == _aCGV[i])
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");

			if (0 != i && _aCGV[i - 1].anchor().julian() >= _aCGV[i].anchor().julian())
				throw new java.lang.Exception ("CollateralGroupPath Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Netting Group Trajectory Vertexes
	 * 
	 * @return The Array of Netting Group Trajectory Vertexes
	 */

	public org.drip.xva.hypothecation.CollateralGroupVertex[] vertexes()
	{
		return _aCGV;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchors()
	{
		int iNumVertex = _aCGV.length;
		org.drip.analytics.date.JulianDate[] adtVertex = new org.drip.analytics.date.JulianDate[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adtVertex[i] = _aCGV[i].anchor();

		return adtVertex;
	}

	/**
	 * Retrieve the Array of Collateralized Exposures
	 * 
	 * @return The Array of Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int iNumVertex = _aCGV.length;
		double[] adblCollateralizedExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblCollateralizedExposure[i] = _aCGV[i].collateralized();

		return adblCollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposures
	 * 
	 * @return The Array of Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int iNumVertex = _aCGV.length;
		double[] adblUncollateralizedExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblUncollateralizedExposure[i] = _aCGV[i].uncollateralized();

		return adblUncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Credit Exposures
	 * 
	 * @return The Array of Credit Exposures
	 */

	public double[] creditExposure()
	{
		int iNumVertex = _aCGV.length;
		double[] adblCreditExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblCreditExposure[i] = _aCGV[i].credit();

		return adblCreditExposure;
	}

	/**
	 * Retrieve the Array of Debt Exposures
	 * 
	 * @return The Array of Debt Exposures
	 */

	public double[] debtExposure()
	{
		int iNumVertex = _aCGV.length;
		double[] adblDebtExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblDebtExposure[i] = _aCGV[i].debt();

		return adblDebtExposure;
	}

	/**
	 * Retrieve the Array of Funding Exposures
	 * 
	 * @return The Array of Funding Exposures
	 */

	public double[] fundingExposure()
	{
		int iNumVertex = _aCGV.length;
		double[] adblFundingExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblFundingExposure[i] = _aCGV[i].funding();

		return adblFundingExposure;
	}

	/**
	 * Retrieve the Array of Collateral Balances
	 * 
	 * @return The Array of Collateral Balances
	 */

	public double[] collateralBalance()
	{
		int iNumVertex = _aCGV.length;
		double[] adblCollateralizedBalance = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblCollateralizedBalance[i] = _aCGV[i].collateralBalance();

		return adblCollateralizedBalance;
	}
}
