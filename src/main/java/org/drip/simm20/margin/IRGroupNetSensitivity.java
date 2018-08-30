
package org.drip.simm20.margin;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * IRGroupNetSensitivity holds the Weighted Net Sensitivities for each of the IR Risk Factors - OIS, LIBOR
 *  1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL - across a Set of Currencies. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IRGroupNetSensitivity
{
	private java.util.Map<java.lang.String, org.drip.simm20.margin.IRFactorAggregate> _netSensitivityMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.simm20.margin.IRFactorAggregate>();

	/**
	 * Hollow IRGroupNetSensitivity Constructor
	 */

	public IRGroupNetSensitivity()
	{
	}

	/**
	 * Add the Net Sensitivity for the specified Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @param netSensitivity The Net Sensitivity Instance
	 * 
	 * @return TRUE - The Net Sensitivity successfully added
	 */

	public boolean addNetSensitivity (
		final java.lang.String currency,
		final org.drip.simm20.margin.IRFactorAggregate netSensitivity)
	{
		if (null == currency || currency.isEmpty() || null == netSensitivity)
		{
			return false;
		}

		_netSensitivityMap.put (
			currency,
			netSensitivity
		);

		return true;
	}

	/**
	 * Retrieve the Matrix of gBC Values
	 * 
	 * @return The Matrix of gBC Values
	 */

	public java.util.Map<java.lang.String, java.lang.Double> gBC()
	{
		java.util.Map<java.lang.String, java.lang.Double> gBCMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.IRFactorAggregate>
			netSensitivityMapEntryOuter : _netSensitivityMap.entrySet())
		{
			java.lang.String currencyOuter = netSensitivityMapEntryOuter.getKey();

			org.drip.simm20.margin.IRFactorAggregate irNetSensitivityOuter =
				netSensitivityMapEntryOuter.getValue();

			double concentrationRiskFactorOuter = irNetSensitivityOuter.concentrationRiskFactor();

			for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.IRFactorAggregate>
				netSensitivityMapEntryInner : _netSensitivityMap.entrySet())
			{
				java.lang.String currencyInner = netSensitivityMapEntryInner.getKey();

				org.drip.simm20.margin.IRFactorAggregate irNetSensitivityInner =
					netSensitivityMapEntryInner.getValue();

				double concentrationRiskFactorInner = irNetSensitivityInner.concentrationRiskFactor();

				double gBC = java.lang.Math.min (
					concentrationRiskFactorInner,
					concentrationRiskFactorOuter
				) / java.lang.Math.max (
					concentrationRiskFactorInner,
					concentrationRiskFactorOuter
				);

				gBCMap.put (
					currencyInner + "_" + currencyOuter,
					gBC
				);

				gBCMap.put (
					currencyOuter + "_" + currencyInner,
					gBC
				);
			}
		}

		return gBCMap;
	}

	/**
	 * Estimate the Delta Margin
	 * 
	 * @param groupSettings The Group Settings
	 * 
	 * @return The Delta Margin
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double deltaMargin (
		final org.drip.simm20.parameters.IRClassSensitivitySettings groupSettings)
		throws java.lang.Exception
	{
		if (null == groupSettings)
		{
			throw new java.lang.Exception ("IRGroupNetSensitivity::deltaMargin => Invalid Inputs");
		}

		java.util.Map<java.lang.String, java.lang.Double> marginVarianceMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> adjustedNetThresholdMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.lang.String currency : _netSensitivityMap.keySet())
		{
			if (!groupSettings.containsCurrency (currency))
			{
				throw new java.lang.Exception ("IRGroupNetSensitivity::deltaMargin => Invalid Inputs");
			}

			org.drip.simm20.margin.IRClassDeltaAggregate marginCovariance = _netSensitivityMap.get
				(currency).marginCovariance (groupSettings.curveTenorSettings (currency));

			if (null == marginCovariance)
			{
				throw new java.lang.Exception ("IRGroupNetSensitivity::deltaMargin => Invalid Inputs");
			}

			marginVarianceMap.put (
				currency,
				marginCovariance.cumulative()
			);

			adjustedNetThresholdMap.put (
				currency,
				marginCovariance.adjustedNetThresholded()
			);
		}

		double deltaMarginVariance = 0.;

		java.util.Map<java.lang.String, java.lang.Double> gBC = gBC();

		double crossCurrencyCorrelation = groupSettings.crossBucketCorrelation();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.IRFactorAggregate>
			netSensitivityMapEntryOuter : _netSensitivityMap.entrySet())
		{
			java.lang.String currencyOuter = netSensitivityMapEntryOuter.getKey();

			deltaMarginVariance = deltaMarginVariance + marginVarianceMap.get (currencyOuter);

			double adjustedNetThresholdedOuter = adjustedNetThresholdMap.get (currencyOuter);

			for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.IRFactorAggregate>
				netSensitivityMapEntryInner : _netSensitivityMap.entrySet())
			{
				java.lang.String currencyInner = netSensitivityMapEntryInner.getKey();

				deltaMarginVariance = deltaMarginVariance +
					crossCurrencyCorrelation *
					adjustedNetThresholdedOuter *
					adjustedNetThresholdMap.get (currencyInner) *
					gBC.get (currencyOuter + "_" + currencyInner);
			}
		}

		return java.lang.Math.sqrt (deltaMarginVariance);
	}

	/**
	 * Indicate if the Currency has Net Sensitivity available
	 * 
	 * @param currency The Currency
	 * 
	 * @return TRUE - The Currency has Net Sensitivity available
	 */

	public boolean ContainsNetSensitivity (
		final java.lang.String currency)
	{
		return null != currency || !_netSensitivityMap.containsKey (currency);
	}

	/**
	 * Retrieve the IR Net Sensitivity Group for the specified Currency
	 * 
	 * @param currency The Currency
	 * 
	 * @return The IR Net Sensitivity Group for the specified Currency
	 */

	public org.drip.simm20.margin.IRFactorAggregate netSensitivity (
		final java.lang.String currency)
	{
		return !ContainsNetSensitivity (currency) ? null : _netSensitivityMap.get (currency);
	}

	/**
	 * Retrieve the IR Net Sensitivity Currency Map
	 * 
	 * @return The IR Net Sensitivity Currency Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm20.margin.IRFactorAggregate> netSensitivityMap()
	{
		return _netSensitivityMap;
	}
}
