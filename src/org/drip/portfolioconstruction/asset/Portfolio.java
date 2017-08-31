
package org.drip.portfolioconstruction.asset;

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
 * Portfolio implements an Instance of the Portfolio of Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Portfolio {
	private org.drip.portfolioconstruction.asset.AssetComponent[] _aAC = null;

	/**
	 * Construct a Portfolio Instance from the Array of Asset ID's and their Amounts
	 * 
	 * @param astrAssetID Array of Asset IDs
	 * @param adblAmount Array of Amounts
	 * 
	 * @return The Portfolio Instance
	 */

	public static final Portfolio Standard (
		final java.lang.String[] astrAssetID,
		final double[] adblAmount)
	{
		if (null == astrAssetID || null == adblAmount) return null;

		int iNumAsset = astrAssetID.length;
		org.drip.portfolioconstruction.asset.AssetComponent[] aAC = 0 == iNumAsset ? null : new
			org.drip.portfolioconstruction.asset.AssetComponent[iNumAsset];

		if (0 == iNumAsset || iNumAsset != adblAmount.length) return null;

		try {
			for (int i = 0; i < iNumAsset; ++i)
				aAC[i] = new org.drip.portfolioconstruction.asset.AssetComponent (astrAssetID[i],
					adblAmount[i]);

			return new Portfolio (aAC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Portfolio Constructor
	 * 
	 * @param aAC Array of the Asset Components
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Portfolio (
		final org.drip.portfolioconstruction.asset.AssetComponent[] aAC)
		throws java.lang.Exception
	{
		if (null == (_aAC = aAC)) throw new java.lang.Exception ("Portfolio Constructor => Invalid Inputs");

		int iNumAsset = _aAC.length;

		if (0 == iNumAsset) throw new java.lang.Exception ("Portfolio Constructor => Invalid Inputs");

		for (int i = 0; i < iNumAsset; ++i) {
			if (null == _aAC[i]) throw new java.lang.Exception ("Portfolio Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Asset Components
	 * 
	 * @return Array of the Asset Components
	 */

	public org.drip.portfolioconstruction.asset.AssetComponent[] assets()
	{
		return _aAC;
	}

	/**
	 * Retrieve the Notional of the Portfolio
	 * 
	 * @return Notional of the Portfolio
	 */

	public double notional()
	{
		double dblTotalWeight = 0.;
		int iNumAsset = _aAC.length;

		for (int i = 0; i < iNumAsset; ++i)
			dblTotalWeight += _aAC[i].amount();

		return dblTotalWeight;
	}

	/**
	 * Retrieve the Array of Asset IDs
	 * 
	 * @return The Array of Asset IDs
	 */

	public java.lang.String[] id()
	{
		int iNumAsset = _aAC.length;
		java.lang.String[] astrAssetID = new java.lang.String[iNumAsset];

		for (int i = 0; i < iNumAsset; ++i)
			astrAssetID[i] = _aAC[i].id();

		return astrAssetID;
	}

	/**
	 * Retrieve the Multivariate Meta Instance around the Assets
	 * 
	 * @return The Multivariate Meta Instance around the Assets
	 */

	public org.drip.measure.continuous.MultivariateMeta meta()
	{
		try {
			return new org.drip.measure.continuous.MultivariateMeta (id());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Array of Asset Weights
	 * 
	 * @return The Array of Asset Weights
	 */

	public double[] weights()
	{
		int iNumAsset = _aAC.length;
		double[] adblWeight = new double[iNumAsset];

		for (int i = 0; i < iNumAsset; ++i)
			adblWeight[i] = _aAC[i].amount();

		return adblWeight;
	}

	/**
	 * Retrieve the Asset Component with the Minimal Weight
	 * 
	 * @return The Asset Component with the Minimal Weight
	 */

	public org.drip.portfolioconstruction.asset.AssetComponent minWeight()
	{
		int iNumAsset = _aAC.length;
		org.drip.portfolioconstruction.asset.AssetComponent acMinWeight = _aAC[0];

		double dblMinWeight = _aAC[0].amount();

		for (int i = 0; i < iNumAsset; ++i) {
			double dblAmount = _aAC[i].amount();

			if (dblAmount < dblMinWeight) {
				acMinWeight = _aAC[i];
				dblMinWeight = dblAmount;
			}
		}

		return acMinWeight;
	}

	/**
	 * Retrieve the Asset Component with the Maximal Weight
	 * 
	 * @return The Asset Component with the Maximal Weight
	 */

	public org.drip.portfolioconstruction.asset.AssetComponent maxWeight()
	{
		int iNumAsset = _aAC.length;
		org.drip.portfolioconstruction.asset.AssetComponent acMaxWeight = _aAC[0];

		double dblMaxWeight = _aAC[0].amount();

		for (int i = 0; i < iNumAsset; ++i) {
			double dblAmount = _aAC[i].amount();

			if (dblAmount > dblMaxWeight) {
				acMaxWeight = _aAC[i];
				dblMaxWeight = dblAmount;
			}
		}

		return acMaxWeight;
	}

	/**
	 * Retrieve the Expected Returns of the Portfolio
	 * 
	 * @param apsp The Asset Pool Statistical Properties Instance
	 * 
	 * @return Expected Returns of the Portfolio
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Returns cannot be calculated
	 */

	public double expectedReturn (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties apsp)
		throws java.lang.Exception
	{
		int iNumAsset = _aAC.length;
		double dblExpectedReturn = 0.;

		for (int i = 0; i < iNumAsset; ++i) {
			org.drip.portfolioconstruction.params.AssetStatisticalProperties asp = apsp.asp (_aAC[i].id());

			if (null == asp) throw new java.lang.Exception ("Portfolio::expectedReturn => Invalid Inputs");

			dblExpectedReturn += _aAC[i].amount() * asp.expectedReturn();
		}

		return dblExpectedReturn;
	}

	/**
	 * Retrieve the Variance of the Portfolio
	 * 
	 * @param apsp The Asset Pool Statistical Properties Instance
	 * 
	 * @return Variance of the Portfolio
	 * 
	 * @throws java.lang.Exception Thrown if the Variance cannot be calculated
	 */

	public double variance (
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties apsp)
		throws java.lang.Exception
	{
		double dblVariance = 0.;
		int iNumAsset = _aAC.length;

		for (int i = 0; i < iNumAsset; ++i) {
			double dblAmountI = _aAC[i].amount();

			java.lang.String strIDI = _aAC[i].id();

			org.drip.portfolioconstruction.params.AssetStatisticalProperties aspI = apsp.asp (strIDI);

			if (null == aspI) throw new java.lang.Exception ("Portfolio::variance => Invalid Inputs");

			double dblVarianceI = aspI.variance();

			for (int j = 0; j < iNumAsset; ++j) {
				java.lang.String strIDJ = _aAC[j].id();

				org.drip.portfolioconstruction.params.AssetStatisticalProperties aspJ = apsp.asp (strIDJ);

				if (null == aspJ) throw new java.lang.Exception ("Portfolio::variance => Invalid Inputs");

				dblVariance += dblAmountI * _aAC[j].amount() * java.lang.Math.sqrt (dblVarianceI *
					aspJ.variance()) * (i == j ? 1. : apsp.correlation (strIDI, strIDJ));
			}
		}

		return dblVariance;
	}
}
