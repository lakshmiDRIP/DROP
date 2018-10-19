
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
 * ProductClassSensitivity holds the multiple Risk Class Sensitivities for a single Product Class. The
 *  References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting .Initial Margin Requirements,
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

public class ProductClassSensitivity
{
	private org.drip.simm.product.RiskClassSensitivity _fxRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivityIR _irRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivity _equityRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivity _commodityRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivityCR _creditQualifyingRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivityCR _creditNonQualifyingRiskClassSensitivity = null;

	/**
	 * ProductClassSensitivity Constructor
	 * 
	 * @param equityRiskClassSensitivity Equity Risk Class Sensitivity
	 * @param commodityRiskClassSensitivity Commodity Risk Class Sensitivity
	 * @param fxRiskClassSensitivity FX Risk Class Sensitivity
	 * @param irRiskClassSensitivity IR Risk Class Sensitivity
	 * @param creditQualifyingRiskClassSensitivity Credit Qualifying Risk Class Sensitivity
	 * @param creditNonQualifyingRiskClassSensitivity Credit Non-Qualifying Risk Class Sensitivity
	 * 
	 * @throws java.lang.Exception Thrownm if the Inputs are Invalid
	 */

	public ProductClassSensitivity (
		final org.drip.simm.product.RiskClassSensitivity equityRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivity commodityRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivity fxRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivityIR irRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivityCR creditQualifyingRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivityCR creditNonQualifyingRiskClassSensitivity)
		throws java.lang.Exception
	{
		_fxRiskClassSensitivity = fxRiskClassSensitivity;
		_irRiskClassSensitivity = irRiskClassSensitivity;
		_equityRiskClassSensitivity = equityRiskClassSensitivity;
		_commodityRiskClassSensitivity = commodityRiskClassSensitivity;
		_creditQualifyingRiskClassSensitivity = creditQualifyingRiskClassSensitivity;
		_creditNonQualifyingRiskClassSensitivity = creditNonQualifyingRiskClassSensitivity;

		if (null == _equityRiskClassSensitivity &&
			null == _commodityRiskClassSensitivity &&
			null == _fxRiskClassSensitivity &&
			null == _irRiskClassSensitivity &&
			null == _creditQualifyingRiskClassSensitivity &&
			null == _creditNonQualifyingRiskClassSensitivity)
		{
			throw new java.lang.Exception ("ProductClassSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Equity Risk Class Sensitivity
	 * 
	 * @return The Equity Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivity equityRiskClassSensitivity()
	{
		return _equityRiskClassSensitivity;
	}

	/**
	 * Retrieve the Commodity Risk Class Sensitivity
	 * 
	 * @return The Commodity Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivity commodityRiskClassSensitivity()
	{
		return _commodityRiskClassSensitivity;
	}

	/**
	 * Retrieve the FX Risk Class Sensitivity
	 * 
	 * @return The FX Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivity fxRiskClassSensitivity()
	{
		return _fxRiskClassSensitivity;
	}

	/**
	 * Retrieve the IR Risk Class Sensitivity
	 * 
	 * @return The IR Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivityIR irRiskClassSensitivity()
	{
		return _irRiskClassSensitivity;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Sensitivity
	 * 
	 * @return The Credit Qualifying Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivityCR creditQualifyingRiskClassSensitivity()
	{
		return _creditQualifyingRiskClassSensitivity;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Sensitivity
	 * 
	 * @return The Credit Non-Qualifying Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivityCR creditNonQualifyingRiskClassSensitivity()
	{
		return _creditNonQualifyingRiskClassSensitivity;
	}

	/**
	 * Generate the Margin for the Product Class
	 * 
	 * @param productClassSettings The Product Class Settings
	 * 
	 * @return The Margin for the Product Class
	 */

	public org.drip.simm.estimator.ProductClassMargin estimate (
		final org.drip.simm.estimator.ProductClassSettings productClassSettings)
	{
		if (null == productClassSettings)
		{
			return null;
		}

		try
		{
			return new ProductClassMargin (
				null == _irRiskClassSensitivity ? null : _irRiskClassSensitivity.aggregate
					(productClassSettings.irRiskClassSensitivitySettings()),
				null == _creditQualifyingRiskClassSensitivity ? null :
					_creditQualifyingRiskClassSensitivity.aggregate
						(productClassSettings.creditQualifyingRiskClassSensitivitySettings()),
				null == _creditNonQualifyingRiskClassSensitivity ? null :
					_creditNonQualifyingRiskClassSensitivity.aggregate
						(productClassSettings.creditNonQualifyingRiskClassSensitivitySettings()),
				null == _equityRiskClassSensitivity ? null : _equityRiskClassSensitivity.aggregate
					(productClassSettings.equityRiskClassSensitivitySettings()),
				null == _fxRiskClassSensitivity ? null : _fxRiskClassSensitivity.aggregate
					(productClassSettings.fxRiskClassSensitivitySettings()),
				null == _commodityRiskClassSensitivity ? null : _commodityRiskClassSensitivity.aggregate
					(productClassSettings.commodityRiskClassSensitivitySettings())
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
