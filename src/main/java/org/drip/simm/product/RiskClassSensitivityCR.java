
package org.drip.simm.product;

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
 * RiskClassSensitivityCR holds the Risk Class Bucket Sensitivities for a single CR Class. The References
 *  are:
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

public class RiskClassSensitivityCR
{
	private org.drip.simm.product.RiskMeasureSensitivityCR _vega = null;
	private org.drip.simm.product.RiskMeasureSensitivityCR _delta = null;
	private org.drip.simm.product.RiskMeasureSensitivityCR _curvature = null;

	/**
	 * RiskClassSensitivityCR Constructor
	 * 
	 * @param delta The CR Delta Tenor Sensitivity
	 * @param vega The CR Vega Tenor Sensitivity
	 * @param curvature The CR Curvature Tenor Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskClassSensitivityCR (
		final org.drip.simm.product.RiskMeasureSensitivityCR delta,
		final org.drip.simm.product.RiskMeasureSensitivityCR vega,
		final org.drip.simm.product.RiskMeasureSensitivityCR curvature)
		throws java.lang.Exception
	{
		if (null == (_delta = delta) ||
			null == (_vega = vega) ||
			null == (_curvature = curvature))
		{
			throw new java.lang.Exception ("RiskClassSensitivityCR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the CR Delta Risk Measure Sensitivity
	 * 
	 * @return The CR Delta Risk Measure Sensitivity
	 */

	public org.drip.simm.product.RiskMeasureSensitivityCR delta()
	{
		return _delta;
	}

	/**
	 * Retrieve the CR Vega Risk Measure Sensitivity
	 * 
	 * @return The CR Vega Risk Measure Sensitivity
	 */

	public org.drip.simm.product.RiskMeasureSensitivityCR vega()
	{
		return _vega;
	}

	/**
	 * Retrieve the CR Curvature Risk Measure Sensitivity
	 * 
	 * @return The CR Curvature Risk Measure Sensitivity
	 */

	public org.drip.simm.product.RiskMeasureSensitivityCR curvature()
	{
		return _curvature;
	}

	/**
	 * Compute the Risk Class Sensitivity Aggregate
	 * 
	 * @param riskClassSensitivitySettingsCR The CR Risk Class Sensitivity Settings
	 * 
	 * @return The Risk Class Sensitivity Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateCR aggregate (
		final org.drip.simm.parameters.RiskClassSensitivitySettingsCR riskClassSensitivitySettingsCR)
	{
		if (null == riskClassSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.RiskClassAggregateCR (
				_delta.linearAggregate (riskClassSensitivitySettingsCR.delta()),
				_vega.linearAggregate (riskClassSensitivitySettingsCR.vega()),
				_curvature.curvatureAggregate (riskClassSensitivitySettingsCR.curvature())
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
