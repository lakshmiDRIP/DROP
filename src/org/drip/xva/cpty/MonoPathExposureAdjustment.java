
package org.drip.xva.cpty;

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
 * MonoPathExposureAdjustment aggregates the Exposures and the Adjustments across Multiple Netting/Funding
 *  Groups on a Single Path Projection Run along the Granularity of a Counter Party Group. The References
 *  are:
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

public class MonoPathExposureAdjustment implements org.drip.xva.cpty.PathExposureAdjustment {
	private org.drip.xva.netting.FundingGroupPath[] _aFGP = null;
	private org.drip.xva.netting.CreditDebtGroupPath[] _aCDGP = null;

	/**
	 * MonoPathExposureAdjustment Constructor
	 * 
	 * @param aCDGP The Array of Credit/Debt Netting Group Paths
	 * @param aFGP The Array of Funding Group Paths
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MonoPathExposureAdjustment (
		final org.drip.xva.netting.CreditDebtGroupPath[] aCDGP,
		final org.drip.xva.netting.FundingGroupPath[] aFGP)
		throws java.lang.Exception
	{
		if (null == (_aCDGP = aCDGP) || 0 == _aCDGP.length || null == (_aFGP = aFGP) || 0 == _aFGP.length)
			throw new java.lang.Exception ("MonoPathExposureAdjustment Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Funding Group Trajectory Paths
	 * 
	 * @return The Array of the Funding Group Trajectory Paths
	 */

	public org.drip.xva.netting.FundingGroupPath[] fundingGroupTrajectoryPaths()
	{
		return _aFGP;
	}

	/**
	 * Retrieve the Array of Credit/Debt Netting Group Trajectory Paths
	 * 
	 * @return The Array of Credit/Debt Netting Group Trajectory Paths
	 */

	public org.drip.xva.netting.CreditDebtGroupPath[] nettingGroupTrajectoryPaths()
	{
		return _aCDGP;
	}

	@Override public org.drip.analytics.date.JulianDate[] anchors()
	{
		return _aCDGP[0].anchors();
	}

	@Override public double[] collateralizedExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCollateralizedExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupCollateralizedExposure =
				_aCDGP[iCreditDebtGroupIndex].collateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedExposure[iVertexIndex] +=
					adblCreditDebtGroupCollateralizedExposure[iVertexIndex];
		}

		return adblCollateralizedExposure;
	}

	@Override public double[] collateralizedExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCollateralizedExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupCollateralizedExposurePV =
				_aCDGP[iCreditDebtGroupIndex].collateralizedExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedExposurePV[iVertexIndex] +=
					adblCreditDebtGroupCollateralizedExposurePV[iVertexIndex];
		}

		return adblCollateralizedExposurePV;
	}

	@Override public double[] collateralizedPositiveExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCollateralizedPositiveExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedPositiveExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupCollateralizedPositiveExposure =
				_aCDGP[iCreditDebtGroupIndex].collateralizedPositiveExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedPositiveExposure[iVertexIndex] +=
					adblCreditDebtGroupCollateralizedPositiveExposure[iVertexIndex];
		}

		return adblCollateralizedPositiveExposure;
	}

	@Override public double[] collateralizedPositiveExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCollateralizedPositiveExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedPositiveExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupCollateralizedPositiveExposurePV =
				_aCDGP[iCreditDebtGroupIndex].collateralizedPositiveExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedPositiveExposurePV[iVertexIndex] +=
					adblCreditDebtGroupCollateralizedPositiveExposurePV[iVertexIndex];
		}

		return adblCollateralizedPositiveExposurePV;
	}

	@Override public double[] collateralizedNegativeExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCollateralizedNegativeExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedNegativeExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupCollateralizedNegativeExposure =
				_aCDGP[iCreditDebtGroupIndex].collateralizedNegativeExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedNegativeExposure[iVertexIndex] +=
					adblCreditDebtGroupCollateralizedNegativeExposure[iVertexIndex];
		}

		return adblCollateralizedNegativeExposure;
	}

	@Override public double[] collateralizedNegativeExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCollateralizedNegativeExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedNegativeExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupCollateralizedNegativeExposurePV =
				_aCDGP[iCreditDebtGroupIndex].collateralizedNegativeExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedNegativeExposurePV[iVertexIndex] +=
					adblCreditDebtGroupCollateralizedNegativeExposurePV[iVertexIndex];
		}

		return adblCollateralizedNegativeExposurePV;
	}

	@Override public double[] uncollateralizedExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblUncollateralizedExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupUncollateralizedExposure =
				_aCDGP[iCreditDebtGroupIndex].uncollateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedExposure[iVertexIndex] +=
					adblCreditDebtGroupUncollateralizedExposure[iVertexIndex];
		}

		return adblUncollateralizedExposure;
	}

	@Override public double[] uncollateralizedExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblUncollateralizedExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupUncollateralizedExposurePV =
				_aCDGP[iCreditDebtGroupIndex].uncollateralizedExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedExposurePV[iVertexIndex] +=
					adblCreditDebtGroupUncollateralizedExposurePV[iVertexIndex];
		}

		return adblUncollateralizedExposurePV;
	}

	@Override public double[] uncollateralizedPositiveExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblUncollateralizedPositiveExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedPositiveExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupUncollateralizedPositiveExposure =
				_aCDGP[iCreditDebtGroupIndex].uncollateralizedPositiveExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedPositiveExposure[iVertexIndex] +=
					adblCreditDebtGroupUncollateralizedPositiveExposure[iVertexIndex];
		}

		return adblUncollateralizedPositiveExposure;
	}

	@Override public double[] uncollateralizedPositiveExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblUncollateralizedPositiveExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedPositiveExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupUncollateralizedPositiveExposurePV =
				_aCDGP[iCreditDebtGroupIndex].uncollateralizedPositiveExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedPositiveExposurePV[iVertexIndex] +=
					adblCreditDebtGroupUncollateralizedPositiveExposurePV[iVertexIndex];
		}

		return adblUncollateralizedPositiveExposurePV;
	}

	@Override public double[] uncollateralizedNegativeExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblUncollateralizedNegativeExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedNegativeExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupUncollateralizedNegativeExposure =
				_aCDGP[iCreditDebtGroupIndex].uncollateralizedNegativeExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedNegativeExposure[iVertexIndex] +=
					adblCreditDebtGroupUncollateralizedNegativeExposure[iVertexIndex];
		}

		return adblUncollateralizedNegativeExposure;
	}

	@Override public double[] uncollateralizedNegativeExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblUncollateralizedNegativeExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedNegativeExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupUncollateralizedNegativeExposurePV =
				_aCDGP[iCreditDebtGroupIndex].uncollateralizedNegativeExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedNegativeExposurePV[iVertexIndex] +=
					adblCreditDebtGroupUncollateralizedNegativeExposurePV[iVertexIndex];
		}

		return adblUncollateralizedNegativeExposurePV;
	}

	@Override public double[] creditExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCreditExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCreditExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupExposure = _aCDGP[iCreditDebtGroupIndex].creditExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCreditExposure[iVertexIndex] += adblCreditDebtGroupExposure[iVertexIndex];
		}

		return adblCreditExposure;
	}

	@Override public double[] creditExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblCreditExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCreditExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupExposurePV = _aCDGP[iCreditDebtGroupIndex].creditExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCreditExposurePV[iVertexIndex] += adblCreditDebtGroupExposurePV[iVertexIndex];
		}

		return adblCreditExposurePV;
	}

	@Override public double[] debtExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblDebtExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblDebtExposure[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupExposure = _aCDGP[iCreditDebtGroupIndex].debtExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblDebtExposure[iVertexIndex] += adblCreditDebtGroupExposure[iVertexIndex];
		}

		return adblDebtExposure;
	}

	@Override public double[] debtExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCreditDebtGroup = _aCDGP.length;
		double[] adblDebtExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblDebtExposurePV[j] = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex) {
			double[] adblCreditDebtGroupExposurePV = _aCDGP[iCreditDebtGroupIndex].debtExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblDebtExposurePV[iVertexIndex] += adblCreditDebtGroupExposurePV[iVertexIndex];
		}

		return adblDebtExposurePV;
	}

	@Override public double[] fundingExposure()
	{
		int iNumVertex = anchors().length;

		int iNumFundingGroup = _aFGP.length;
		double[] adblFundingExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblFundingExposure[j] = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex) {
			double[] adblFundingGroupExposure = _aFGP[iFundingGroupIndex].fundingExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblFundingExposure[iVertexIndex] += adblFundingGroupExposure[iVertexIndex];
		}

		return adblFundingExposure;
	}

	@Override public double[] fundingExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumFundingGroup = _aFGP.length;
		double[] adblFundingExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblFundingExposurePV[j] = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex) {
			double[] adblFundingGroupExposurePV = _aFGP[iFundingGroupIndex].fundingExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblFundingExposurePV[iVertexIndex] += adblFundingGroupExposurePV[iVertexIndex];
		}

		return adblFundingExposurePV;
	}

	@Override public double unilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		int iNumCreditDebtGroup = _aCDGP.length;
		double dbUnilateralCollateralAdjustment = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dbUnilateralCollateralAdjustment +=
				_aCDGP[iCreditDebtGroupIndex].unilateralCollateralAdjustment();

		return dbUnilateralCollateralAdjustment;
	}

	@Override public double bilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		int iNumCreditDebtGroup = _aCDGP.length;
		double dbBilateralCollateralAdjustment = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dbBilateralCollateralAdjustment += _aCDGP[iCreditDebtGroupIndex].bilateralCollateralAdjustment();

		return dbBilateralCollateralAdjustment;
	}

	@Override public double collateralAdjustment()
		throws java.lang.Exception
	{
		return unilateralCollateralAdjustment();
	}

	@Override public double unilateralCreditAdjustment()
		throws java.lang.Exception
	{
		int iNumCreditDebtGroup = _aCDGP.length;
		double dbUnilateralCreditAdjustment = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dbUnilateralCreditAdjustment += _aCDGP[iCreditDebtGroupIndex].unilateralCreditAdjustment();

		return dbUnilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
		throws java.lang.Exception
	{
		int iNumCreditDebtGroup = _aCDGP.length;
		double dblBilateralCreditAdjustment = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dblBilateralCreditAdjustment += _aCDGP[iCreditDebtGroupIndex].bilateralCreditAdjustment();

		return dblBilateralCreditAdjustment;
	}

	@Override public double creditAdjustment()
		throws java.lang.Exception
	{
		return bilateralCreditAdjustment();
	}

	@Override public double contraLiabilityCreditAdjustment()
		throws java.lang.Exception
	{
		int iNumCreditDebtGroup = _aCDGP.length;
		double dblContraLiabilityCreditAdjustment = 0.;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dblContraLiabilityCreditAdjustment +=
				_aCDGP[iCreditDebtGroupIndex].contraLiabilityCreditAdjustment();

		return dblContraLiabilityCreditAdjustment;
	}

	@Override public double debtAdjustment()
	{
		double dblDebtAdjustment = 0.;
		int iNumCreditDebtGroup = _aCDGP.length;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dblDebtAdjustment += _aCDGP[iCreditDebtGroupIndex].debtAdjustment();

		return dblDebtAdjustment;
	}

	@Override public double fundingValueAdjustment()
		throws java.lang.Exception
	{
		int iNumFundingGroup = _aFGP.length;
		double dblFundingValueAdjustment = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex)
			dblFundingValueAdjustment += _aFGP[iFundingGroupIndex].fundingValueAdjustment();

		return dblFundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
		throws java.lang.Exception
	{
		int iNumFundingGroup = _aFGP.length;
		double dblFundingDebtAdjustment = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex)
			dblFundingDebtAdjustment += _aFGP[iFundingGroupIndex].fundingDebtAdjustment();

		return dblFundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		int iNumFundingGroup = _aFGP.length;
		double dblFundingCostAdjustment = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex)
			dblFundingCostAdjustment += _aFGP[iFundingGroupIndex].fundingCostAdjustment();

		return dblFundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		int iNumFundingGroup = _aFGP.length;
		double dblFundingBenefitAdjustment = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex)
			dblFundingBenefitAdjustment += _aFGP[iFundingGroupIndex].fundingBenefitAdjustment();

		return dblFundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		int iNumFundingGroup = _aFGP.length;
		double dblSymmetricFundingValueAdjustment = 0.;

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex)
			dblSymmetricFundingValueAdjustment +=
				_aFGP[iFundingGroupIndex].symmetricFundingValueAdjustment();

		return dblSymmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
		throws java.lang.Exception
	{
		double dblTotalAdjustment = 0.;
		int iNumFundingGroup = _aFGP.length;
		int iNumCreditDebtGroup = _aCDGP.length;

		for (int iCreditDebtGroupIndex = 0; iCreditDebtGroupIndex < iNumCreditDebtGroup;
			++iCreditDebtGroupIndex)
			dblTotalAdjustment += _aCDGP[iCreditDebtGroupIndex].creditAdjustment() +
				_aCDGP[iCreditDebtGroupIndex].debtAdjustment();

		for (int iFundingGroupIndex = 0; iFundingGroupIndex < iNumFundingGroup; ++iFundingGroupIndex)
			dblTotalAdjustment += _aFGP[iFundingGroupIndex].fundingValueAdjustment();

		return dblTotalAdjustment;
	}
}
