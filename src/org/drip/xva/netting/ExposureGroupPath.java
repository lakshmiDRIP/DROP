
package org.drip.xva.netting;

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
 * ExposureGroupPath rolls up the Path Realizations of the Sequence in a Single Path Projection Run over
 *  Multiple Collateral Groups onto a Single Exposure Group. The References are:
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

public class ExposureGroupPath {
	private org.drip.xva.universe.MarketPath _mp = null;
	private org.drip.xva.hypothecation.CollateralGroupPath[] _aHGP = null;

	/**
	 * ExposureGroupPath Constructor
	 * 
	 * @param aHGP Array of the Collateral Group Trajectory Paths
	 * @param mp The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ExposureGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupPath[] aHGP,
		final org.drip.xva.universe.MarketPath mp)
		throws java.lang.Exception
	{
		if (null == (_aHGP = aHGP) || null == (_mp = mp))
			throw new java.lang.Exception ("ExposureGroupPath Constructor => Invalid Inputs");

		int iNumHypothecationGroup = _aHGP.length;

		if (0 == iNumHypothecationGroup)
			throw new java.lang.Exception ("ExposureGroupPath Constructor => Invalid Inputs");

		for (int i = 0; i < iNumHypothecationGroup; ++i) {
			if (null == _aHGP[i])
				throw new java.lang.Exception ("ExposureGroupPath Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Collateral Hypothecation Group Trajectory Paths
	 * 
	 * @return Array of the Collateral Hypothecation Group Trajectory Paths
	 */

	public org.drip.xva.hypothecation.CollateralGroupPath[] hypothecationGroupPaths()
	{
		return _aHGP;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public org.drip.xva.universe.MarketPath marketPath()
	{
		return _mp;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchors()
	{
		return _aHGP[0].anchors();
	}

	/**
	 * Retrieve the Array of Collateralized Exposures
	 * 
	 * @return The Array of Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralizedExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].collateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedExposure[iVertexIndex] +=
					adblCollateralGroupCollateralizedExposure[iVertexIndex];
		}

		return adblCollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Collateralized Exposure PV
	 * 
	 * @return The Array of Collateralized Exposure PV
	 */

	public double[] collateralizedExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralizedExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralizedExposurePV[iVertexIndex] +=
					adblCollateralGroupCollateralizedExposure[iVertexIndex] *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
		}

		return adblCollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Collateralized Positive Exposures
	 * 
	 * @return The Array of Collateralized Positive Exposures
	 */

	public double[] collateralizedPositiveExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralizedPositiveExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedPositiveExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].collateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblCollateralizedExposure = adblCollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. < dblCollateralizedExposure)
					adblCollateralizedPositiveExposure[iVertexIndex] += dblCollateralizedExposure;
			}
		}

		return adblCollateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Collateralized Positive Exposure PV
	 * 
	 * @return The Array of Collateralized Positive Exposures PV
	 */

	public double[] collateralizedPositiveExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralizedPositiveExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedPositiveExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblCollateralizedExposure = adblCollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. < dblCollateralizedExposure)
					adblCollateralizedPositiveExposurePV[iVertexIndex] += dblCollateralizedExposure *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
			}
		}

		return adblCollateralizedPositiveExposurePV;
	}

	/**
	 * Retrieve the Array of Collateralized Negative Exposures
	 * 
	 * @return The Array of Collateralized Negative Exposures
	 */

	public double[] collateralizedNegativeExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralizedNegativeExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedNegativeExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].collateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblCollateralizedExposure = adblCollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. > dblCollateralizedExposure)
					adblCollateralizedNegativeExposure[iVertexIndex] += dblCollateralizedExposure;
			}
		}

		return adblCollateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Collateralized Negative Exposure PV
	 * 
	 * @return The Array of Collateralized Negative Exposure PV
	 */

	public double[] collateralizedNegativeExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralizedNegativeExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralizedNegativeExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].collateralizedExposure();

			org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblCollateralizedExposure = adblCollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. > dblCollateralizedExposure)
					adblCollateralizedNegativeExposurePV[iVertexIndex] += dblCollateralizedExposure *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
			}
		}

		return adblCollateralizedNegativeExposurePV;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposures
	 * 
	 * @return The Array of Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblUncollateralizedExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupUncollateralizedExposure =
				_aHGP[iCollateralGroupIndex].uncollateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedExposure[iVertexIndex] +=
					adblCollateralGroupUncollateralizedExposure[iVertexIndex];
		}

		return adblUncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposure PV
	 * 
	 * @return The Array of Uncollateralized Exposure PV
	 */

	public double[] uncollateralizedExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblUncollateralizedExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupUncollateralizedExposure =
				_aHGP[iCollateralGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblUncollateralizedExposurePV[iVertexIndex] +=
					adblCollateralGroupUncollateralizedExposure[iVertexIndex] *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
		}

		return adblUncollateralizedExposurePV;
	}

	/**
	 * Retrieve the Array of Uncollateralized Positive Exposures
	 * 
	 * @return The Array of Uncollateralized Positive Exposures
	 */

	public double[] uncollateralizedPositiveExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblUncollateralizedPositiveExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedPositiveExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblUncollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].uncollateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblUncollateralizedExposure =
					adblUncollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. < dblUncollateralizedExposure)
					adblUncollateralizedPositiveExposure[iVertexIndex] += dblUncollateralizedExposure;
			}
		}

		return adblUncollateralizedPositiveExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Positive Exposure PV
	 * 
	 * @return The Array of Uncollateralized Positive Exposure PV
	 */

	public double[] uncollateralizedPositiveExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblUncollateralizedPositiveExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedPositiveExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblUncollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblUncollateralizedExposure =
					adblUncollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. < dblUncollateralizedExposure)
					adblUncollateralizedPositiveExposurePV[iVertexIndex] += dblUncollateralizedExposure *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
			}
		}

		return adblUncollateralizedPositiveExposurePV;
	}

	/**
	 * Retrieve the Array of Uncollateralized Negative Exposures
	 * 
	 * @return The Array of Uncollateralized Negative Exposures
	 */

	public double[] uncollateralizedNegativeExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblUncollateralizedNegativeExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedNegativeExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupUncollateralizedExposure =
				_aHGP[iCollateralGroupIndex].uncollateralizedExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblUncollateralizedExposure =
					adblCollateralGroupUncollateralizedExposure[iVertexIndex];

				if (0. > dblUncollateralizedExposure)
					adblUncollateralizedNegativeExposure[iVertexIndex] += dblUncollateralizedExposure;
			}
		}

		return adblUncollateralizedNegativeExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Negative Exposure PV
	 * 
	 * @return The Array of Uncollateralized Negative Exposure PV
	 */

	public double[] uncollateralizedNegativeExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblUncollateralizedNegativeExposurePV = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblUncollateralizedNegativeExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblUncollateralGroupCollateralizedExposure =
				_aHGP[iCollateralGroupIndex].uncollateralizedExposure();

			org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex) {
				double dblUncollateralizedExposure =
					adblUncollateralGroupCollateralizedExposure[iVertexIndex];

				if (0. > dblUncollateralizedExposure)
					adblUncollateralizedNegativeExposurePV[iVertexIndex] += dblUncollateralizedExposure *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
			}
		}

		return adblUncollateralizedNegativeExposurePV;
	}

	/**
	 * Retrieve the Array of Collateral Balances
	 * 
	 * @return The Array of Collateral Balances
	 */

	public double[] collateralBalance()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCollateralBalance = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCollateralBalance[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCollateralBalance = _aHGP[iCollateralGroupIndex].collateralBalance();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCollateralBalance[iVertexIndex] += adblCollateralGroupCollateralBalance[iVertexIndex];
		}

		return adblCollateralBalance;
	}

	/**
	 * Compute Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Path Bilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		double[] adblCollateralBalance = collateralBalance();

		double dblBilateralCollateralValueAdjustment = 0.;
		int iNumVertex = adblCollateralBalance.length;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCollateralBalance[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].counterParty().survivalProbability() *
						aMV[iVertexIndex - 1].collateralSchemeSpread() *
							aMV[iVertexIndex - 1].overnightIndexNumeraire().epochalForwardScale();

			double dblPeriodIntegrandEnd = adblCollateralBalance[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].counterParty().survivalProbability() *
						aMV[iVertexIndex].collateralSchemeSpread() *
							aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();

			dblBilateralCollateralValueAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd)
				* (aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblBilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Unilateral Collateral Value Adjustment
	 * 
	 * @return The Path Unilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double unilateralCollateralAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		double[] adblCollateralBalance = collateralBalance();

		double dblUnilateralCollateralValueAdjustment = 0.;
		int iNumVertex = adblCollateralBalance.length;

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCollateralBalance[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].counterParty().survivalProbability() *
					aMV[iVertexIndex - 1].collateralSchemeSpread() *
						aMV[iVertexIndex - 1].overnightIndexNumeraire().epochalForwardScale();

			double dblPeriodIntegrandEnd = adblCollateralBalance[iVertexIndex] *
				aMV[iVertexIndex].counterParty().survivalProbability() *
					aMV[iVertexIndex].collateralSchemeSpread() *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();

			dblUnilateralCollateralValueAdjustment -= 0.5 * (dblPeriodIntegrandStart + dblPeriodIntegrandEnd)
				* (aMV[iVertexIndex].anchor().julian() - aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return dblUnilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Path Collateral Value Adjustment
	 * 
	 * @return The Path Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double collateralAdjustment()
		throws java.lang.Exception
	{
		return bilateralCollateralAdjustment();
	}

	/**
	 * Compute Period-wise Path Unilateral Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Unilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodUnilateralCollateralValueAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		double[] adblCollateralBalance = collateralBalance();

		int iNumVertex = adblCollateralBalance.length;
		double[] adblUnilateralCollateralValueAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCollateralBalance[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].counterParty().survivalProbability() *
					aMV[iVertexIndex - 1].collateralSchemeSpread() *
						aMV[iVertexIndex - 1].overnightIndexNumeraire().epochalForwardScale();

			double dblPeriodIntegrandEnd = adblCollateralBalance[iVertexIndex] *
				aMV[iVertexIndex].counterParty().survivalProbability() *
					aMV[iVertexIndex].collateralSchemeSpread() *
						aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();

			adblUnilateralCollateralValueAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblUnilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Bilateral Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Bilateral Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodBilateralCollateralValueAdjustment()
		throws java.lang.Exception
	{
		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		double[] adblCollateralBalance = collateralBalance();

		int iNumVertex = adblCollateralBalance.length;
		double[] adblBilateralCollateralValueAdjustment = new double[iNumVertex - 1];

		for (int iVertexIndex = 1; iVertexIndex < iNumVertex; ++iVertexIndex) {
			double dblPeriodIntegrandStart = adblCollateralBalance[iVertexIndex - 1] *
				aMV[iVertexIndex - 1].bank().survivalProbability() *
					aMV[iVertexIndex - 1].counterParty().survivalProbability() *
						aMV[iVertexIndex - 1].collateralSchemeSpread() *
							aMV[iVertexIndex - 1].overnightIndexNumeraire().epochalForwardScale();

			double dblPeriodIntegrandEnd = adblCollateralBalance[iVertexIndex] *
				aMV[iVertexIndex].bank().survivalProbability() *
					aMV[iVertexIndex].counterParty().survivalProbability() *
						aMV[iVertexIndex].collateralSchemeSpread() *
							aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();

			adblBilateralCollateralValueAdjustment[iVertexIndex - 1] = -0.5 * (dblPeriodIntegrandStart +
				dblPeriodIntegrandEnd) * (aMV[iVertexIndex].anchor().julian() -
					aMV[iVertexIndex - 1].anchor().julian()) / 365.25;
		}

		return adblBilateralCollateralValueAdjustment;
	}

	/**
	 * Compute Period-wise Path Collateral Value Adjustment
	 * 
	 * @return The Period-wise Path Collateral Value Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double[] periodCollateralValueAdjustment()
		throws java.lang.Exception
	{
		return periodBilateralCollateralValueAdjustment();
	}

	/**
	 * Retrieve the Array of Credit Exposures
	 * 
	 * @return The Array of Credit Exposures
	 */

	public double[] creditExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCreditExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblCreditExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCreditExposure = _aHGP[iCollateralGroupIndex].creditExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCreditExposure[iVertexIndex] += adblCollateralGroupCreditExposure[iVertexIndex];
		}

		return adblCreditExposure;
	}

	/**
	 * Retrieve the Array of Credit Exposure PV
	 * 
	 * @return The Array of Credit Exposure PV
	 */

	public double[] creditExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblCreditExposurePV = new double[iNumVertex];

		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		for (int j = 0; j < iNumVertex; ++j)
			adblCreditExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupCreditExposure = _aHGP[iCollateralGroupIndex].creditExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblCreditExposurePV[iVertexIndex] += adblCollateralGroupCreditExposure[iVertexIndex] *
					aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
		}

		return adblCreditExposurePV;
	}

	/**
	 * Retrieve the Array of Debt Exposures
	 * 
	 * @return The Array of Debt Exposures
	 */

	public double[] debtExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblDebtExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblDebtExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupDebtExposure = _aHGP[iCollateralGroupIndex].debtExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblDebtExposure[iVertexIndex] += adblCollateralGroupDebtExposure[iVertexIndex];
		}

		return adblDebtExposure;
	}

	/**
	 * Retrieve the Array of Debt Exposure PV
	 * 
	 * @return The Array of Debt Exposure PV
	 */

	public double[] debtExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblDebtExposurePV = new double[iNumVertex];

		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		for (int j = 0; j < iNumVertex; ++j)
			adblDebtExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupDebtExposure = _aHGP[iCollateralGroupIndex].debtExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblDebtExposurePV[iVertexIndex] += adblCollateralGroupDebtExposure[iVertexIndex] *
					aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
		}

		return adblDebtExposurePV;
	}

	/**
	 * Retrieve the Array of Funding Exposures
	 * 
	 * @return The Array of Funding Exposures
	 */

	public double[] fundingExposure()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblFundingExposure = new double[iNumVertex];

		for (int j = 0; j < iNumVertex; ++j)
			adblFundingExposure[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupFundingExposure = _aHGP[iCollateralGroupIndex].fundingExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblFundingExposure[iVertexIndex] += adblCollateralGroupFundingExposure[iVertexIndex];
		}

		return adblFundingExposure;
	}

	/**
	 * Retrieve the Array of Funding Exposure PV
	 * 
	 * @return The Array of Funding Exposure PV
	 */

	public double[] fundingExposurePV()
	{
		int iNumVertex = anchors().length;

		int iNumCollateralGroup = _aHGP.length;
		double[] adblFundingExposurePV = new double[iNumVertex];

		org.drip.xva.universe.MarketVertex[] aMV = _mp.vertexes();

		for (int j = 0; j < iNumVertex; ++j)
			adblFundingExposurePV[j] = 0.;

		for (int iCollateralGroupIndex = 0; iCollateralGroupIndex < iNumCollateralGroup;
			++iCollateralGroupIndex) {
			double[] adblCollateralGroupFundingExposure = _aHGP[iCollateralGroupIndex].fundingExposure();

			for (int iVertexIndex = 0; iVertexIndex < iNumVertex; ++iVertexIndex)
				adblFundingExposurePV[iVertexIndex] += adblCollateralGroupFundingExposure[iVertexIndex] *
					aMV[iVertexIndex].overnightIndexNumeraire().epochalForwardScale();
		}

		return adblFundingExposurePV;
	}
}
