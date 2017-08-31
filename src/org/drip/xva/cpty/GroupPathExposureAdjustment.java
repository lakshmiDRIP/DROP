
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
 * GroupPathExposureAdjustment cumulates the Exposures and the Adjustments across Multiple Netting/Funding
 *  Groups on a Single Path Projection Run across multiple Counter Party Groups the constitute a Book. The
 *  References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies, Risk, 23 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GroupPathExposureAdjustment implements org.drip.xva.cpty.PathExposureAdjustment {
	private org.drip.xva.cpty.MonoPathExposureAdjustment[] _aMPEA = null;

	/**
	 * GroupPathExposureAdjustment Constructor
	 * 
	 * @param aMPEA Array of Single Counter Party Path Exposure Adjustments
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GroupPathExposureAdjustment (
		final org.drip.xva.cpty.MonoPathExposureAdjustment[] aMPEA)
		throws java.lang.Exception
	{
		if (null == (_aMPEA = aMPEA) || 0 == _aMPEA.length)
			throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");

		int iNumCounterPartyGroup = _aMPEA.length;

		if (0 == iNumCounterPartyGroup)
			throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");

		for (int i = 0; i < iNumCounterPartyGroup; ++i) {
			if (null == _aMPEA[i])
				throw new java.lang.Exception ("GroupPathExposureAdjustment Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Counter Party Group Paths
	 * 
	 * @return Array of Counter Party Group Paths
	 */

	public org.drip.xva.cpty.MonoPathExposureAdjustment[] counterPartyGroupPaths()
	{
		return _aMPEA;
	}

	@Override public org.drip.analytics.date.JulianDate[] anchors()
	{
		return _aMPEA[0].anchors();
	}

	@Override public double[] collateralizedExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCollateralizedExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedExposure[j] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblCounterPartyGroupCollateralizedExposure =
				_aMPEA[iCounterPartyGroupIndex].collateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedExposure[iVertexIndex] +=
					adblCounterPartyGroupCollateralizedExposure[iVertexIndex];
		}

		return adblCollateralizedExposure;
	}

	@Override public double[] collateralizedExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCollateralizedExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedExposurePV[j] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblCounterPartyGroupCollateralizedExposurePV =
				_aMPEA[iCounterPartyGroupIndex].collateralizedExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedExposurePV[iVertexIndex] +=
					adblCounterPartyGroupCollateralizedExposurePV[iVertexIndex];
		}

		return adblCollateralizedExposurePV;
	}

	@Override public double[] uncollateralizedExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblUncollateralizedExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblUncollateralizedExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathUncollateralizedExposure =
				_aMPEA[iCounterPartyGroupIndex].uncollateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedExposure[iVertexIndex] += adblPathUncollateralizedExposure[iVertexIndex];
		}

		return adblUncollateralizedExposure;
	}

	@Override public double[] uncollateralizedExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblUncollateralizedExposurePV = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblUncollateralizedExposurePV[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathUncollateralizedExposurePV =
				_aMPEA[iCounterPartyGroupIndex].uncollateralizedExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedExposurePV[iVertexIndex] +=
					adblPathUncollateralizedExposurePV[iVertexIndex];
		}

		return adblUncollateralizedExposurePV;
	}

	@Override public double[] collateralizedPositiveExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCollateralizedPositiveExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblCollateralizedPositiveExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathCollateralizedPositiveExposure =
				_aMPEA[iCounterPartyGroupIndex].collateralizedPositiveExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedPositiveExposure[iVertexIndex] +=
					adblPathCollateralizedPositiveExposure[iVertexIndex];
		}

		return adblCollateralizedPositiveExposure;
	}

	@Override public double[] collateralizedPositiveExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCollateralizedPositiveExposurePV = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblCollateralizedPositiveExposurePV[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathCollateralizedPositiveExposurePV =
				_aMPEA[iCounterPartyGroupIndex].collateralizedPositiveExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedPositiveExposurePV[iVertexIndex] +=
					adblPathCollateralizedPositiveExposurePV[iVertexIndex];
		}

		return adblCollateralizedPositiveExposurePV;
	}

	@Override public double[] uncollateralizedPositiveExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblUncollateralizedPositiveExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblUncollateralizedPositiveExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathUncollateralizedPositiveExposure =
				_aMPEA[iCounterPartyGroupIndex].uncollateralizedPositiveExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedPositiveExposure[iVertexIndex] +=
					adblPathUncollateralizedPositiveExposure[iVertexIndex];
		}

		return adblUncollateralizedPositiveExposure;
	}

	@Override public double[] uncollateralizedPositiveExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblUncollateralizedPositiveExposurePV = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblUncollateralizedPositiveExposurePV[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathUncollateralizedPositiveExposurePV =
				_aMPEA[iCounterPartyGroupIndex].uncollateralizedPositiveExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedPositiveExposurePV[iVertexIndex] +=
					adblPathUncollateralizedPositiveExposurePV[iVertexIndex];
		}

		return adblUncollateralizedPositiveExposurePV;
	}

	@Override public double[] collateralizedNegativeExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCollateralizedNegativeExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblCollateralizedNegativeExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathCollateralizedNegativeExposure =
				_aMPEA[iCounterPartyGroupIndex].collateralizedNegativeExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedNegativeExposure[iVertexIndex] +=
					adblPathCollateralizedNegativeExposure[iVertexIndex];
		}

		return adblCollateralizedNegativeExposure;
	}

	@Override public double[] collateralizedNegativeExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCollateralizedNegativeExposurePV = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblCollateralizedNegativeExposurePV[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathCollateralizedNegativeExposurePV =
				_aMPEA[iCounterPartyGroupIndex].collateralizedNegativeExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedNegativeExposurePV[iVertexIndex] +=
					adblPathCollateralizedNegativeExposurePV[iVertexIndex];
		}

		return adblCollateralizedNegativeExposurePV;
	}

	@Override public double[] uncollateralizedNegativeExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblUncollateralizedNegativeExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblUncollateralizedNegativeExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathUncollateralizedNegativeExposure =
				_aMPEA[iCounterPartyGroupIndex].uncollateralizedNegativeExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedNegativeExposure[iVertexIndex] +=
					adblPathUncollateralizedNegativeExposure[iVertexIndex];
		}

		return adblUncollateralizedNegativeExposure;
	}

	@Override public double[] uncollateralizedNegativeExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblUncollateralizedNegativeExposurePV = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblUncollateralizedNegativeExposurePV[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathUncollateralizedNegativeExposurePV =
				_aMPEA[iCounterPartyGroupIndex].uncollateralizedNegativeExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedNegativeExposurePV[iVertexIndex] +=
					adblPathUncollateralizedNegativeExposurePV[iVertexIndex];
		}

		return adblUncollateralizedNegativeExposurePV;
	}

	@Override public double[] creditExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCreditExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblCreditExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathCreditExposure = _aMPEA[iCounterPartyGroupIndex].creditExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCreditExposure[iVertexIndex] += adblPathCreditExposure[iVertexIndex];
		}

		return adblCreditExposure;
	}

	@Override public double[] creditExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblCreditExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblCreditExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathCreditExposure = _aMPEA[iCounterPartyGroupIndex].creditExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCreditExposure[iVertexIndex] += adblPathCreditExposure[iVertexIndex];
		}

		return adblCreditExposure;
	}

	@Override public double[] debtExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblDebtExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblDebtExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathDebtExposure = _aMPEA[iCounterPartyGroupIndex].debtExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblDebtExposure[iVertexIndex] += adblPathDebtExposure[iVertexIndex];
		}

		return adblDebtExposure;
	}

	@Override public double[] debtExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblDebtExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblDebtExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathDebtExposure = _aMPEA[iCounterPartyGroupIndex].debtExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblDebtExposure[iVertexIndex] += adblPathDebtExposure[iVertexIndex];
		}

		return adblDebtExposure;
	}

	@Override public double[] fundingExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblFundingExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblFundingExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathFundingExposure = _aMPEA[iCounterPartyGroupIndex].fundingExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblFundingExposure[iVertexIndex] += adblPathFundingExposure[iVertexIndex];
		}

		return adblFundingExposure;
	}

	@Override public double[] fundingExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCounterPartyGroup = _aMPEA.length;
		double[] adblFundingExposure = new double[iNumVertex];

		for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
			adblFundingExposure[iVertexIndex] = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex) {
			double[] adblPathFundingExposure = _aMPEA[iCounterPartyGroupIndex].fundingExposurePV();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblFundingExposure[iVertexIndex] += adblPathFundingExposure[iVertexIndex];
		}

		return adblFundingExposure;
	}

	@Override public double unilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		double dblUnilateralCollateralAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblUnilateralCollateralAdjustment +=
				_aMPEA[iCounterPartyGroupIndex].unilateralCollateralAdjustment();

		return dblUnilateralCollateralAdjustment;
	}

	@Override public double bilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		double dblBilateralCollateralAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblBilateralCollateralAdjustment +=
				_aMPEA[iCounterPartyGroupIndex].bilateralCollateralAdjustment();

		return dblBilateralCollateralAdjustment;
	}

	@Override public double collateralAdjustment()
		throws java.lang.Exception
	{
		return bilateralCollateralAdjustment();
	}

	@Override public double unilateralCreditAdjustment()
		throws java.lang.Exception
	{
		double dblUnilateralCreditAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblUnilateralCreditAdjustment += _aMPEA[iCounterPartyGroupIndex].unilateralCreditAdjustment();

		return dblUnilateralCreditAdjustment;
	}

	@Override public double bilateralCreditAdjustment()
		throws java.lang.Exception
	{
		double dblBilateralCreditAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblBilateralCreditAdjustment += _aMPEA[iCounterPartyGroupIndex].bilateralCreditAdjustment();

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
		int iNumCounterPartyGroup = _aMPEA.length;
		double dblContraLiabilityCreditAdjustment = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblContraLiabilityCreditAdjustment +=
				_aMPEA[iCounterPartyGroupIndex].contraLiabilityCreditAdjustment();

		return dblContraLiabilityCreditAdjustment;
	}

	@Override public double debtAdjustment()
	{
		double dblDebtAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblDebtAdjustment += _aMPEA[iCounterPartyGroupIndex].debtAdjustment();

		return dblDebtAdjustment;
	}

	@Override public double fundingValueAdjustment()
		throws java.lang.Exception
	{
		double dblFundingValueAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblFundingValueAdjustment += _aMPEA[iCounterPartyGroupIndex].fundingValueAdjustment();

		return dblFundingValueAdjustment;
	}

	@Override public double fundingDebtAdjustment()
			throws java.lang.Exception
	{
		double dblFundingDebtAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblFundingDebtAdjustment += _aMPEA[iCounterPartyGroupIndex].fundingDebtAdjustment();

		return dblFundingDebtAdjustment;
	}

	@Override public double fundingCostAdjustment()
	{
		double dblFundingCostAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblFundingCostAdjustment += _aMPEA[iCounterPartyGroupIndex].fundingCostAdjustment();

		return dblFundingCostAdjustment;
	}

	@Override public double fundingBenefitAdjustment()
	{
		double dblFundingBenefitAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblFundingBenefitAdjustment += _aMPEA[iCounterPartyGroupIndex].fundingBenefitAdjustment();

		return dblFundingBenefitAdjustment;
	}

	@Override public double symmetricFundingValueAdjustment()
	{
		int iNumCounterPartyGroup = _aMPEA.length;
		double dblSymmetricFundingValueAdjustment = 0.;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblSymmetricFundingValueAdjustment +=
				_aMPEA[iCounterPartyGroupIndex].symmetricFundingValueAdjustment();

		return dblSymmetricFundingValueAdjustment;
	}

	@Override public double totalAdjustment()
		throws java.lang.Exception
	{
		double dblTotalAdjustment = 0.;
		int iNumCounterPartyGroup = _aMPEA.length;

		for (int iCounterPartyGroupIndex = 0; iCounterPartyGroupIndex < iNumCounterPartyGroup;
			++iCounterPartyGroupIndex)
			dblTotalAdjustment += _aMPEA[iCounterPartyGroupIndex].creditAdjustment() +
				_aMPEA[iCounterPartyGroupIndex].debtAdjustment() +
					_aMPEA[iCounterPartyGroupIndex].fundingValueAdjustment();

		return dblTotalAdjustment;
	}
}
