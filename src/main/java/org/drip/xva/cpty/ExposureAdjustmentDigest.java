
package org.drip.xva.cpty;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  Thin file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
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
 * ExposureAdjustmentDigest holds the "thin" Statistics of the Aggregations across Multiple Path Projection
 *  Runs along the Granularity of a Counter Party Group (i.e., across multiple Funding and Credit/Debt
 *  Netting groups). The References are:
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

public class ExposureAdjustmentDigest
{
	private org.drip.measure.statistics.UnivariateDiscreteThin _cvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _dvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _fbaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _fcaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _fdaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _fvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _ucvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _sfvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _cvaclThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _ftdcvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _ucolvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _totalvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _ftdcolvaThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_collateralizedExposureThinStatistics = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_uncollateralizedExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_collateralizedExposureThinStatisticsPV = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_uncollateralizedExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_collateralizedPositiveExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_collateralizedNegativeExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_uncollateralizedPositiveExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_uncollateralizedNegativeExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_collateralizedPositiveExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_collateralizedNegativeExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_uncollateralizedPositiveExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[]
		_uncollateralizedNegativeExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[] _creditExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[] _creditExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[] _debtExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[] _debtExposurePVThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[] _fundingExposureThinStatisticsArray = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin[] _fundingExposurePVThinStatisticsArray = null;

	/**
	 * ExposureAdjustmentDigest Constructor
	 * 
	 * @param ucolva The Array of Unilateral Collateral VA
	 * @param ftdcolva The Array of Bilateral Collateral VA
	 * @param ucva The Array of UCVA
	 * @param ftdcva The Array of FTD CVA
	 * @param cva The Array of CVA
	 * @param cvacl The Array of CVA Contra-Liabilities
	 * @param dva The Array of DVA
	 * @param fva The Array of FVA
	 * @param fda The Array of FDA
	 * @param fca The Array of FCA
	 * @param fba The Array of FBA
	 * @param sfva The Array of SFVA
	 * @param totalVA The Array of Total VA
	 * @param collateralizedExposure Double Array of Collateralized Exposure
	 * @param collateralizedExposurePV Double Array of Collateralized Exposure PV
	 * @param collateralizedPositiveExposure Double Array of Collateralized Positive Exposure
	 * @param collateralizedPositiveExposurePV Double Array of Collateralized Positive Exposure PV
	 * @param collateralizedNegativeExposure Double Array of Collateralized Negative Exposure
	 * @param collateralizedNegativeExposurePV Double Array of Collateralized Negative Exposure PV
	 * @param uncollateralizedExposure Double Array of Uncollateralized Exposure
	 * @param uncollateralizedExposurePV Double Array of Uncollateralized Exposure PV
	 * @param uncollateralizedPositiveExposure Double Array of Uncollateralized Positive Exposure
	 * @param uncollateralizedPositiveExposurePV Double Array of Uncollateralized Positive Exposure PV
	 * @param uncollateralizedNegativeExposure Double Array of Uncollateralized Negative Exposure
	 * @param uncollateralizedNegativeExposurePV Double Array of Uncollateralized Negative Exposure PV
	 * @param creditExposure Double Array of Credit Exposure
	 * @param creditExposurePV Double Array of Credit Exposure PV
	 * @param debtExposure Double Array of Debt Exposure
	 * @param debtExposurePV Double Array of Debt Exposure PV
	 * @param fundingExposure Double Array of Funding Exposure
	 * @param fundingExposurePV Double Array of Funding Exposure PV
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ExposureAdjustmentDigest (
		final double[] ucolva,
		final double[] ftdcolva,
		final double[] ucva,
		final double[] ftdcva,
		final double[] cva,
		final double[] cvacl,
		final double[] dva,
		final double[] fva,
		final double[] fda,
		final double[] fca,
		final double[] fba,
		final double[] sfva,
		final double[] totalVA,
		final double[][] collateralizedExposure,
		final double[][] collateralizedExposurePV,
		final double[][] collateralizedPositiveExposure,
		final double[][] collateralizedPositiveExposurePV,
		final double[][] collateralizedNegativeExposure,
		final double[][] collateralizedNegativeExposurePV,
		final double[][] uncollateralizedExposure,
		final double[][] uncollateralizedExposurePV,
		final double[][] uncollateralizedPositiveExposure,
		final double[][] uncollateralizedPositiveExposurePV,
		final double[][] uncollateralizedNegativeExposure,
		final double[][] uncollateralizedNegativeExposurePV,
		final double[][] creditExposure,
		final double[][] creditExposurePV,
		final double[][] debtExposure,
		final double[][] debtExposurePV,
		final double[][] fundingExposure,
		final double[][] fundingExposurePV)
		throws java.lang.Exception
	{
		if (null == ucolva ||
			null == ftdcolva ||
			null == ucva ||
			null == ftdcva ||
			null == cva ||
			null == cvacl ||
			null == dva ||
			null == fva ||
			null == fca ||
			null == fba ||
			null == sfva ||
			null == totalVA ||
			null == collateralizedExposure ||
			null == collateralizedExposurePV ||
			null == collateralizedPositiveExposure ||
			null == collateralizedPositiveExposurePV ||
			null == collateralizedNegativeExposure ||
			null == collateralizedNegativeExposurePV ||
			null == uncollateralizedExposure ||
			null == uncollateralizedExposurePV ||
			null == uncollateralizedPositiveExposure ||
			null == uncollateralizedPositiveExposurePV ||
			null == uncollateralizedNegativeExposure ||
			null == uncollateralizedNegativeExposurePV ||
			null == creditExposure ||
			null == creditExposurePV ||
			null == debtExposure ||
			null == debtExposurePV ||
			null == fundingExposure ||
			null == fundingExposurePV)
		{
			throw new java.lang.Exception ("ExposureAdjustmentDigest Constructor => Invalid Inputs");
		}

		_ucolvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (ucolva);

		_ftdcolvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (ftdcolva);

		_ucvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (ucva);

		_ftdcvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (ftdcva);

		_cvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (cva);

		_cvaclThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (cvacl);

		_dvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (dva);

		_fvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (fva);

		_fdaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (fda);

		_fcaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (fca);

		_fbaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (fba);

		_sfvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (sfva);

		_totalvaThinStatistics = new org.drip.measure.statistics.UnivariateDiscreteThin (totalVA);

		int vertexCount = collateralizedExposure.length;
		_collateralizedExposureThinStatistics = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_uncollateralizedExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_collateralizedExposureThinStatisticsPV = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_uncollateralizedExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_collateralizedPositiveExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_collateralizedPositiveExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_collateralizedNegativeExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_collateralizedNegativeExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_uncollateralizedPositiveExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_uncollateralizedPositiveExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_uncollateralizedNegativeExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_uncollateralizedNegativeExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_creditExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_creditExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_debtExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_debtExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_fundingExposureThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];
		_fundingExposurePVThinStatisticsArray = new
			org.drip.measure.statistics.UnivariateDiscreteThin[vertexCount];

		if (0 == vertexCount ||
			vertexCount != collateralizedExposurePV.length ||
			vertexCount != collateralizedPositiveExposure.length ||
			vertexCount != collateralizedPositiveExposurePV.length ||
			vertexCount != collateralizedNegativeExposure.length ||
			vertexCount != collateralizedNegativeExposurePV.length ||
			vertexCount != uncollateralizedExposure.length ||
			vertexCount != uncollateralizedExposurePV.length ||
			vertexCount != uncollateralizedPositiveExposure.length ||
			vertexCount != collateralizedPositiveExposurePV.length ||
			vertexCount != collateralizedNegativeExposurePV.length ||
			vertexCount != collateralizedNegativeExposurePV.length ||
			vertexCount != creditExposure.length ||
			vertexCount != creditExposurePV.length ||
			vertexCount != debtExposure.length ||
			vertexCount != debtExposurePV.length ||
			vertexCount != fundingExposure.length ||
			vertexCount != fundingExposurePV.length)
		{
			throw new java.lang.Exception ("ExposureAdjustmentDigest Constructor => Invalid Inputs");
		}

		for (int i = 0 ; i < vertexCount; ++i)
		{
			_collateralizedExposureThinStatistics[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (collateralizedExposure[i]);

			_collateralizedExposureThinStatisticsPV[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (collateralizedExposurePV[i]);

			_collateralizedPositiveExposureThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (collateralizedPositiveExposure[i]);

			_collateralizedPositiveExposurePVThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (collateralizedPositiveExposurePV[i]);

			_collateralizedNegativeExposureThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (collateralizedNegativeExposure[i]);

			_collateralizedNegativeExposurePVThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (collateralizedNegativeExposurePV[i]);

			_uncollateralizedExposureThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (uncollateralizedExposure[i]);

			_uncollateralizedExposurePVThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (uncollateralizedExposurePV[i]);

			_uncollateralizedPositiveExposureThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (uncollateralizedPositiveExposure[i]);

			_uncollateralizedPositiveExposurePVThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (uncollateralizedPositiveExposurePV[i]);

			_uncollateralizedNegativeExposureThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (uncollateralizedNegativeExposure[i]);

			_uncollateralizedNegativeExposurePVThinStatisticsArray[i] = new
				org.drip.measure.statistics.UnivariateDiscreteThin (uncollateralizedNegativeExposurePV[i]);
		}
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Exposure
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] collateralizedExposure()
	{
		return _collateralizedExposureThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] collateralizedExposurePV()
	{
		return _collateralizedExposureThinStatisticsPV;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Positive Exposure
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Positive Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] collateralizedPositiveExposure()
	{
		return _collateralizedPositiveExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Positive Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Positive Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] collateralizedPositiveExposurePV()
	{
		return _collateralizedPositiveExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Negative Exposure
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Negative Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] collateralizedNegativeExposure()
	{
		return _collateralizedNegativeExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Collateralized Negative Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Collateralized Negative Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] collateralizedNegativeExposurePV()
	{
		return _collateralizedNegativeExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Exposure
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] uncollateralizedExposure()
	{
		return _uncollateralizedExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] uncollateralizedExposurePV()
	{
		return _uncollateralizedExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Positive Exposure
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Positive Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] uncollateralizedPositiveExposure()
	{
		return _uncollateralizedPositiveExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Positive Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Positive Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] uncollateralizedPositiveExposurePV()
	{
		return _uncollateralizedPositiveExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Negative Exposure
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Negative Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] uncollateralizedNegativeExposure()
	{
		return _uncollateralizedNegativeExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Uncollateralized Negative Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Uncollateralized Negative Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] uncollateralizedNegativeExposurePV()
	{
		return _uncollateralizedNegativeExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Credit Exposure
	 * 
	 * @return Univariate Thin Statistics for the Credit Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] creditExposure()
	{
		return _creditExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Credit Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Credit Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] creditExposurePV()
	{
		return _creditExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Debt Exposure
	 * 
	 * @return Univariate Thin Statistics for the Debt Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] debtExposure()
	{
		return _debtExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Debt Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Debt Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] debtExposurePV()
	{
		return _debtExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Funding Exposure
	 * 
	 * @return Univariate Thin Statistics for the Funding Exposure
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] fundingExposure()
	{
		return _fundingExposureThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for the Funding Exposure PV
	 * 
	 * @return Univariate Thin Statistics for the Funding Exposure PV
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] fundingExposurePV()
	{
		return _fundingExposurePVThinStatisticsArray;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for Unilateral Collateral VA
	 * 
	 * @return Univariate Thin Statistics for Unilateral Collateral VA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin ucolva()
	{
		return _ucolvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for Bilateral Collateral VA
	 * 
	 * @return Univariate Thin Statistics for Bilateral Collateral VA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin ftdcolva()
	{
		return _ftdcolvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for UCVA
	 * 
	 * @return Univariate Thin Statistics for UCVA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin ucva()
	{
		return _ucvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FTD CVA
	 * 
	 * @return Univariate Thin Statistics for FTD CVA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin ftdcva()
	{
		return _ftdcvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for CVA
	 * 
	 * @return Univariate Thin Statistics for CVA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin cva()
	{
		return _cvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for CVA Contra-Liabilities
	 * 
	 * @return Univariate Thin Statistics for CVA Contra-Liabilities
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin cvacl()
	{
		return _cvaclThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for DVA
	 * 
	 * @return Univariate Thin Statistics for DVA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin dva()
	{
		return _dvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FVA
	 * 
	 * @return Univariate Thin Statistics for FVA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin fva()
	{
		return _fvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FDA
	 * 
	 * @return Univariate Thin Statistics for FDA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin fda()
	{
		return _fdaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FCA
	 * 
	 * @return Univariate Thin Statistics for FCA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin fca()
	{
		return _fcaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for FBA
	 * 
	 * @return Univariate Thin Statistics for FBA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin fba()
	{
		return _fbaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for SFVA
	 * 
	 * @return Univariate Thin Statistics for SFVA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin sfva()
	{
		return _sfvaThinStatistics;
	}

	/**
	 * Retrieve the Univariate Thin Statistics for Total VA
	 * 
	 * @return Univariate Thin Statistics for Total VA
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin totalVA()
	{
		return _totalvaThinStatistics;
	}
}
