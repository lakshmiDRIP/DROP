
package org.drip.portfolioconstruction.params;

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
 * AssetUniverseStatisticalProperties holds the Statistical Properties of a Pool of Assets.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetUniverseStatisticalProperties {
	private double _dblRiskFreeRate = java.lang.Double.NaN;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.params.AssetStatisticalProperties>
		_mapASP = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.params.AssetStatisticalProperties>();

	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapCorrelation = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Construct an Instance of AUSP from the corresponding MultivariateMetrics Instance
	 * 
	 * @param mvm The MultivariateMetrics Instance
	 * 
	 * @return The AUSP Instance
	 */

	public static final AssetUniverseStatisticalProperties FromMultivariateMetrics (
		final org.drip.measure.statistics.MultivariateMoments mvm)
	{
		if (null == mvm) return null;

		java.util.Set<java.lang.String> setstrAsset = mvm.variateList();

		if (null == setstrAsset|| 0 == setstrAsset.size()) return null;

		try {
			AssetUniverseStatisticalProperties ausp = new AssetUniverseStatisticalProperties (0.);

			for (java.lang.String strAsset : setstrAsset) {
				if (!ausp.setASP (new org.drip.portfolioconstruction.params.AssetStatisticalProperties
					(strAsset, strAsset, mvm.mean (strAsset), mvm.variance (strAsset))))
					return null;
			}

			for (java.lang.String strAsset1 : setstrAsset) {
				for (java.lang.String strAsset2 : setstrAsset) {
					if (!ausp.setCorrelation (strAsset1, strAsset2, mvm.correlation (strAsset1, strAsset2)))
						return null;
				}
			}

			return ausp;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AssetUniverseStatisticalProperties Constructor
	 * 
	 * @param dblRiskFreeRate The Risk Free Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetUniverseStatisticalProperties (
		final double dblRiskFreeRate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRiskFreeRate = dblRiskFreeRate))
			throw new java.lang.Exception
				("AssetUniverseStatisticalProperties Constructor => Invalid Inputs");
	}

	/**
	 * Set the ASP Instance
	 * 
	 * @param asp ASP Instance
	 * 
	 * @return TRUE - ASP Instance Successfully added
	 */

	public boolean setASP (
		final org.drip.portfolioconstruction.params.AssetStatisticalProperties asp)
	{
		if (null == asp) return false;

		_mapASP.put (asp.id(), asp);

		return true;
	}

	/**
	 * Set the Correlation Between the Specified Assets
	 * 
	 * @param strID1 Asset #1
	 * @param strID2 Asset #2
	 * @param dblCorrelation Cross-asset Correlation
	 * 
	 * @return Correlation Between the Specified Assets
	 */

	public boolean setCorrelation (
		final java.lang.String strID1,
		final java.lang.String strID2,
		final double dblCorrelation)
	{
		if (null == strID1 || strID1.isEmpty() || null == strID2 || strID2.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCorrelation) || 1. < dblCorrelation || -1. >
				dblCorrelation)
			return false;

		_mapCorrelation.put (strID1 + "@#" + strID2, dblCorrelation);

		_mapCorrelation.put (strID2 + "@#" + strID1, dblCorrelation);

		return true;
	}

	/**
	 * Retrieve the Risk Free Rate
	 * 
	 * @return The Risk Free Rate
	 */

	public double riskFreeRate()
	{
		return _dblRiskFreeRate;
	}

	/**
	 * Retrieve the ASP Instance corresponding to the specified ID
	 * 
	 * @param strID The ASP ID
	 * 
	 * @return The ASP Instance
	 */

	public org.drip.portfolioconstruction.params.AssetStatisticalProperties asp (
		final java.lang.String strID)
	{
		return null == strID || strID.isEmpty() || !_mapASP.containsKey (strID) ? null : _mapASP.get (strID);
	}

	/**
	 * Retrieve the Correlation between the Specified Assets
	 * 
	 * @param strID1 Asset #1
	 * @param strID2 Asset #2
	 * 
	 * @return Correlation between the Specified Assets
	 * 
	 * @throws java.lang.Exception Thtrown if the Inputs are Invalid
	 */

	public double correlation (
		final java.lang.String strID1,
		final java.lang.String strID2)
		throws java.lang.Exception
	{
		if (null == strID1 || strID1.isEmpty() || null == strID2 || strID2.isEmpty())
			throw new java.lang.Exception
				("AssetUniverseStatisticalProperties::correlation => Invalid Inputs");

		if (strID1.equalsIgnoreCase (strID2)) return 1.;

		java.lang.String strCorrelationSlot = strID1 + "@#" + strID2;

		if (!_mapCorrelation.containsKey (strCorrelationSlot))
			throw new java.lang.Exception
				("AssetUniverseStatisticalProperties::correlation => Invalid Inputs");

		return _mapCorrelation.get (strCorrelationSlot);
	}

	/**
	 * Retrieve the Asset Expected Returns Array
	 * 
	 * @param astrAssetID Array of Asset IDs
	 * 
	 * @return The Asset Covariance Matrix
	 */

	public double[] expectedReturns (
		final java.lang.String[] astrAssetID)
	{
		if (null == astrAssetID) return null;

		int iNumAsset = astrAssetID.length;
		double[] adblExpectedReturns = new double[iNumAsset];

		if (0 == iNumAsset) return null;

		for (int i = 0; i < iNumAsset; ++i) {
			org.drip.portfolioconstruction.params.AssetStatisticalProperties asp = asp (astrAssetID[i]);

			if (null == asp) return null;

			adblExpectedReturns[i] = asp.expectedReturn();
		}

		return adblExpectedReturns;
	}

	/**
	 * Retrieve the Asset Covariance Matrix
	 * 
	 * @param astrID Array of Asset ID
	 * 
	 * @return The Asset Covariance Matrix
	 */

	public double[][] covariance (
		final java.lang.String[] astrID)
	{
		if (null == astrID) return null;

		int iNumAsset = astrID.length;
		double[][] aadblCovariance = new double[iNumAsset][iNumAsset];

		if (0 == iNumAsset) return null;

		for (int i = 0; i < iNumAsset; ++i) {
			org.drip.portfolioconstruction.params.AssetStatisticalProperties asp1 = asp (astrID[i]);

			if (null == asp1) return null;

			double dblVarianceI = asp1.variance();

			for (int j = 0; j < iNumAsset; ++j) {
				org.drip.portfolioconstruction.params.AssetStatisticalProperties asp2 = asp (astrID[j]);

				if (null == asp2) return null;

				try {
					aadblCovariance[i][j] = java.lang.Math.sqrt (dblVarianceI * asp2.variance()) *
						correlation (astrID[i], astrID[j]);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return aadblCovariance;
	}
}
