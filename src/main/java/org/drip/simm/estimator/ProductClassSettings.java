
package org.drip.simm.estimator;

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
 * ProductClassSensitivitySettings holds the Settings that govern the Generation of the ISDA SIMM Bucket
 *  Sensitivities across Individual Product Classes. The References are:
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

public class ProductClassSettings
{
	private org.drip.measure.stochastic.LabelCorrelation _labelCorrelation = null;
	private org.drip.simm.parameters.RiskClassSensitivitySettings _fxRiskClassSensitivitySettings = null;
	private org.drip.simm.parameters.RiskClassSensitivitySettingsIR _irRiskClassSensitivitySettings = null;
	private org.drip.simm.parameters.RiskClassSensitivitySettings _equityRiskClassSensitivitySettings = null;
	private org.drip.simm.parameters.RiskClassSensitivitySettings _commodityRiskClassSensitivitySettings =
		null;
	private org.drip.simm.parameters.RiskClassSensitivitySettingsCR
		_creditQualifyingRiskClassSensitivitySettings = null;
	private org.drip.simm.parameters.RiskClassSensitivitySettingsCR
		_creditNonQualifyingRiskClassSensitivitySettings = null;

	/**
	 * Construct an ISDA SIMM 2.0 Version of ProductClassSettings
	 * 
	 * @param currencyList Currency List
	 * @param vegaDurationDays The Volatility Duration in Days
	 * 
	 * @return ISDA SIMM 2.0 Version of ProductClassSettings
	 */

	public static final ProductClassSettings ISDA_20 (
		final java.util.List<java.lang.String> currencyList,
		final int vegaDurationDays)
	{
		try
		{
			return new ProductClassSettings (
				org.drip.simm.parameters.RiskClassSensitivitySettings.ISDA_EQ_20 (vegaDurationDays),
				org.drip.simm.parameters.RiskClassSensitivitySettings.ISDA_CT_20 (vegaDurationDays),
				org.drip.simm.parameters.RiskClassSensitivitySettings.ISDA_FX_20 (vegaDurationDays),
				org.drip.simm.parameters.RiskClassSensitivitySettingsIR.ISDA_20 (currencyList),
				org.drip.simm.parameters.RiskClassSensitivitySettingsCR.ISDA_CRQ_20(),
				org.drip.simm.parameters.RiskClassSensitivitySettingsCR.ISDA_CRNQ_20(),
				org.drip.simm.common.CrossRiskClassCorrelation20.Matrix()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA SIMM 2.1 Version of ProductClassSettings
	 * 
	 * @param currencyList Currency List
	 * @param vegaDurationDays The Volatility Duration in Days
	 * 
	 * @return ISDA SIMM 2.1 Version of ProductClassSettings
	 */

	public static final ProductClassSettings ISDA_21 (
		final java.util.List<java.lang.String> currencyList,
		final int vegaDurationDays)
	{
		try
		{
			return new ProductClassSettings (
				org.drip.simm.parameters.RiskClassSensitivitySettings.ISDA_EQ_21 (vegaDurationDays),
				org.drip.simm.parameters.RiskClassSensitivitySettings.ISDA_CT_21 (vegaDurationDays),
				org.drip.simm.parameters.RiskClassSensitivitySettings.ISDA_FX_21 (vegaDurationDays),
				org.drip.simm.parameters.RiskClassSensitivitySettingsIR.ISDA_21 (currencyList),
				org.drip.simm.parameters.RiskClassSensitivitySettingsCR.ISDA_CRQ_21(),
				org.drip.simm.parameters.RiskClassSensitivitySettingsCR.ISDA_CRNQ_21(),
				org.drip.simm.common.CrossRiskClassCorrelation21.Matrix()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ProductClassSettings Constructor
	 * 
	 * @param equityRiskClassSensitivitySettings Equity Risk Class Sensitivity Settings
	 * @param commodityRiskClassSensitivitySettings Commodity Risk Class Sensitivity Settings
	 * @param fxRiskClassSensitivitySettings FX Risk Class Sensitivity Settings
	 * @param irRiskClassSensitivitySettings IR Risk Class Sensitivity Settings
	 * @param creditQualifyingRiskClassSensitivitySettings Credit Qualifying Risk Class Sensitivity Settings
	 * @param creditNonQualifyingRiskClassSensitivitySettings Credit Non-Qualifying Risk Class Sensitivity
	 * 	Settings
	 * @param labelCorrelation Cross Risk Class Label Correlation
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public ProductClassSettings (
		final org.drip.simm.parameters.RiskClassSensitivitySettings equityRiskClassSensitivitySettings,
		final org.drip.simm.parameters.RiskClassSensitivitySettings commodityRiskClassSensitivitySettings,
		final org.drip.simm.parameters.RiskClassSensitivitySettings fxRiskClassSensitivitySettings,
		final org.drip.simm.parameters.RiskClassSensitivitySettingsIR irRiskClassSensitivitySettings,
		final org.drip.simm.parameters.RiskClassSensitivitySettingsCR
			creditQualifyingRiskClassSensitivitySettings,
		final org.drip.simm.parameters.RiskClassSensitivitySettingsCR
			creditNonQualifyingRiskClassSensitivitySettings,
		final org.drip.measure.stochastic.LabelCorrelation labelCorrelation)
		throws java.lang.Exception
	{
		_fxRiskClassSensitivitySettings = fxRiskClassSensitivitySettings;
		_irRiskClassSensitivitySettings = irRiskClassSensitivitySettings;
		_equityRiskClassSensitivitySettings = equityRiskClassSensitivitySettings;
		_commodityRiskClassSensitivitySettings = commodityRiskClassSensitivitySettings;
		_creditQualifyingRiskClassSensitivitySettings = creditQualifyingRiskClassSensitivitySettings;
		_creditNonQualifyingRiskClassSensitivitySettings = creditNonQualifyingRiskClassSensitivitySettings;

		if ((null == _equityRiskClassSensitivitySettings &&
			null == _commodityRiskClassSensitivitySettings &&
			null == _fxRiskClassSensitivitySettings &&
			null == _irRiskClassSensitivitySettings &&
			null == _creditQualifyingRiskClassSensitivitySettings &&
			null == _creditNonQualifyingRiskClassSensitivitySettings) ||
			null == (_labelCorrelation = labelCorrelation))
		{
			throw new java.lang.Exception ("ProductClassSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Equity Risk Class Sensitivity Settings
	 * 
	 * @return The Equity Risk Class Sensitivity Settings
	 */

	public org.drip.simm.parameters.RiskClassSensitivitySettings equityRiskClassSensitivitySettings()
	{
		return _equityRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Commodity Risk Class Sensitivity Settings
	 * 
	 * @return The Commodity Risk Class Sensitivity Settings
	 */

	public org.drip.simm.parameters.RiskClassSensitivitySettings commodityRiskClassSensitivitySettings()
	{
		return _commodityRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the FX Risk Class Sensitivity Settings
	 * 
	 * @return The FX Risk Class Sensitivity Settings
	 */

	public org.drip.simm.parameters.RiskClassSensitivitySettings fxRiskClassSensitivitySettings()
	{
		return _fxRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the IR Risk Class Sensitivity Settings
	 * 
	 * @return The IR Risk Class Sensitivity Settings
	 */

	public org.drip.simm.parameters.RiskClassSensitivitySettingsIR irRiskClassSensitivitySettings()
	{
		return _irRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Sensitivity Settings
	 * 
	 * @return The Credit Qualifying Risk Class Sensitivity Settings
	 */

	public org.drip.simm.parameters.RiskClassSensitivitySettingsCR
		creditQualifyingRiskClassSensitivitySettings()
	{
		return _creditQualifyingRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Sensitivity Settings
	 * 
	 * @return The Credit Non-Qualifying Risk Class Sensitivity Settings
	 */

	public org.drip.simm.parameters.RiskClassSensitivitySettingsCR
		creditNonQualifyingRiskClassSensitivitySettings()
	{
		return _creditNonQualifyingRiskClassSensitivitySettings;
	}

	/**
	 * Retrieve the Cross Risk Class Label Correlation
	 * 
	 * @return The Cross Risk Class Label Correlation
	 */

	public org.drip.measure.stochastic.LabelCorrelation labelCorrelation()
	{
		return _labelCorrelation;
	}
}
