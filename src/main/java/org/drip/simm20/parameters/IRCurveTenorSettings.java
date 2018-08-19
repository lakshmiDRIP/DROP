
package org.drip.simm20.parameters;

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
 * IRCurveTenorSettings holds the Risk Weights and Concentration Thresholds for each Sub Curve Risk Factor
 *  and its Tenor. The References are:
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

public class IRCurveTenorSettings extends org.drip.simm20.parameters.LiquiditySettings
{
	private double _crossSubCurveCorrelation = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _ois = null;
	private java.util.Map<java.lang.String, java.lang.Double> _prime = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor1M = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor3M = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor6M = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor12M = null;
	private java.util.Map<java.lang.String, java.lang.Double> _municipal = null;
	private org.drip.measure.stochastic.LabelCorrelation _singleCurveTenorCorrelation = null;

	/**
	 * Construct the ISDA Standard IR Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA Standard IR Sensitivity Settings for the Currency
	 */

	public static final IRCurveTenorSettings ISDA (
		final java.lang.String currency)
	{
		org.drip.simm20.rates.IRThreshold irThreshold = org.drip.simm20.rates.IRThresholdContainer.Threshold
			(currency);

		org.drip.simm20.rates.IRWeight oisRiskWeight = org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
			currency,
			org.drip.simm20.rates.IRSystemics.SUB_CURVE_OIS
		);

		org.drip.simm20.rates.IRWeight libor1MRiskWeight =
			org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
				currency,
				org.drip.simm20.rates.IRSystemics.SUB_CURVE_LIBOR_1M
			);

		org.drip.simm20.rates.IRWeight libor3MRiskWeight =
			org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
				currency,
				org.drip.simm20.rates.IRSystemics.SUB_CURVE_LIBOR_3M
			);

		org.drip.simm20.rates.IRWeight libor6MRiskWeight =
			org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
				currency,
				org.drip.simm20.rates.IRSystemics.SUB_CURVE_LIBOR_6M
			);

		org.drip.simm20.rates.IRWeight libor12MRiskWeight =
			org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
				currency,
				org.drip.simm20.rates.IRSystemics.SUB_CURVE_LIBOR_12M
			);

		org.drip.simm20.rates.IRWeight primeRiskWeight =
			org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
				currency,
				org.drip.simm20.rates.IRSystemics.SUB_CURVE_PRIME
			);

		org.drip.simm20.rates.IRWeight municipalRiskWeight =
			org.drip.simm20.rates.IRSettingsContainer.RiskWeight (
				currency,
				org.drip.simm20.rates.IRSystemics.SUB_CURVE_MUNICIPAL
			);

		try
		{
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ? null : new IRCurveTenorSettings (
					oisRiskWeight.tenorWeightMap(),
					libor1MRiskWeight.tenorWeightMap(),
					libor3MRiskWeight.tenorWeightMap(),
					libor6MRiskWeight.tenorWeightMap(),
					libor12MRiskWeight.tenorWeightMap(),
					primeRiskWeight.tenorWeightMap(),
					municipalRiskWeight.tenorWeightMap(),
					org.drip.simm20.rates.IRSettingsContainer.SingleCurveTenorCorrelation(),
					org.drip.simm20.rates.IRSystemics.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().delta()
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * IRCurveTenorSettings Constructor
	 * 
	 * @param ois The OIS Sensitivity Margin Estimator Settings
	 * @param libor1M The LIBOR 1M Sensitivity Margin Estimator Settings
	 * @param libor3M The LIBOR 3M Sensitivity Margin Estimator Settings
	 * @param libor6M The LIBOR 6M Sensitivity Margin Estimator Settings
	 * @param libor12M The LIBOR 12M Sensitivity Margin Estimator Settings
	 * @param prime The PRIME Sensitivity Margin Estimator Settings
	 * @param municipal The MUNICIPAL 12M Sensitivity Margin Estimator Settings
	 * @param singleCurveTenorCorrelation Single Curve Tenor Correlation
	 * @param crossSubCurveCorrelation Cross Sub Curve Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IRCurveTenorSettings (
		final java.util.Map<java.lang.String, java.lang.Double> ois,
		final java.util.Map<java.lang.String, java.lang.Double> libor1M,
		final java.util.Map<java.lang.String, java.lang.Double> libor3M,
		final java.util.Map<java.lang.String, java.lang.Double> libor6M,
		final java.util.Map<java.lang.String, java.lang.Double> libor12M,
		final java.util.Map<java.lang.String, java.lang.Double> prime,
		final java.util.Map<java.lang.String, java.lang.Double> municipal,
		final org.drip.measure.stochastic.LabelCorrelation singleCurveTenorCorrelation,
		final double crossSubCurveCorrelation,
		final double concentrationThreshold)
		throws java.lang.Exception
	{
		super (concentrationThreshold);

		if (null == (_ois = ois) ||
			null == (_libor1M = libor1M) ||
			null == (_libor3M = libor3M) ||
			null == (_libor6M = libor6M) ||
			null == (_libor12M = libor12M) ||
			null == (_prime = prime) ||
			null == (_municipal = municipal) ||
			null == (_singleCurveTenorCorrelation = singleCurveTenorCorrelation) ||
			!org.drip.quant.common.NumberUtil.IsValid (_crossSubCurveCorrelation = crossSubCurveCorrelation) ||
				-1. > _crossSubCurveCorrelation || 1. < _crossSubCurveCorrelation)
		{
			throw new java.lang.Exception ("IRCurveTenorSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Sensitivity Margin Estimator Settings
	 * 
	 * @return The OIS Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> ois()
	{
		return _ois;
	}

	/**
	 * Retrieve the LIBOR 1M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 1M Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1M()
	{
		return _libor1M;
	}

	/**
	 * Retrieve the LIBOR 3M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 3M Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3M()
	{
		return _libor3M;
	}

	/**
	 * Retrieve the LIBOR 6M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 6M Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6M()
	{
		return _libor6M;
	}

	/**
	 * Retrieve the LIBOR 12M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 12M Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12M()
	{
		return _libor12M;
	}

	/**
	 * Retrieve the PRIME Sensitivity Margin Estimator Settings
	 * 
	 * @return The PRIME Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> prime()
	{
		return _prime;
	}

	/**
	 * Retrieve the MUNICIPAL Sensitivity Margin Estimator Settings
	 * 
	 * @return The MUNICIPAL Sensitivity Margin Estimator Settings
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipal()
	{
		return _municipal;
	}

	/**
	 * Retrieve the Cross Sub-Curve Correlation
	 * 
	 * @return The Cross Sub-Curve Correlation
	 */

	public double crossSubCurveCorrelation()
	{
		return _crossSubCurveCorrelation;
	}

	/**
	 * Retrieve the Single Curve Cross Tenor Correlation
	 * 
	 * @return The Single Curve Cross Tenor Correlation
	 */

	public org.drip.measure.stochastic.LabelCorrelation singleCurveTenorCorrelation()
	{
		return _singleCurveTenorCorrelation;
	}
}
