
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
 * BucketCurvatureSettingsIR holds the Curvature Risk Weights, Concentration Thresholds, and
 *  Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor. The References are:
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

public class BucketCurvatureSettingsIR extends org.drip.simm20.parameters.BucketVegaSettingsIR
{
	private java.util.Map<java.lang.String, java.lang.Double> _tenorScalingFactorMap = null;

	/**
	 * BucketCurvatureSettingsIR Constructor
	 * 
	 * @param oisTenorVegaRiskWeight The OIS Tenor Vega Risk Weight
	 * @param libor1MTenorVegaRiskWeight The LIBOR 1M Tenor Vega Risk Weight
	 * @param libor3MTenorVegaRiskWeight The LIBOR 3M Tenor Vega Risk Weight
	 * @param libor6MTenorVegaRiskWeight The LIBOR 6M Tenor Vega Risk Weight
	 * @param libor12MTenorVegaRiskWeight The LIBOR 12M Tenor Vega Risk Weight
	 * @param primeTenorVegaRiskWeight The PRIME Tenor Vega Risk Weight
	 * @param municipalTenorVegaRiskWeight The MUNICIPAL Tenor Vega Risk Weight
	 * @param crossTenorCorrelation Single Curve Cross-Tenor Correlation
	 * @param crossCurveCorrelation Cross Curve Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * @param vegaScaler The Vega Scaler
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * @param oisTenorDeltaRiskWeight The OIS Tenor Delta Risk Weight
	 * @param libor1MTenorDeltaRiskWeight The LIBOR 1M Tenor Delta Risk Weight
	 * @param libor3MTenorDeltaRiskWeight The LIBOR 3M Tenor Delta Risk Weight
	 * @param libor6MTenorDeltaRiskWeight The LIBOR 6M Tenor Delta Risk Weight
	 * @param libor12MTenorDeltaRiskWeight The LIBOR 12M Tenor Delta Risk Weight
	 * @param primeTenorDeltaRiskWeight The PRIME Tenor Delta Risk Weight
	 * @param municipalTenorDeltaRiskWeight The MUNICIPAL Tenor Delta Risk Weight
	 * @param tenorScalingFactorMap The Tenor Scaling Factor Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketCurvatureSettingsIR (
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeight,
		final org.drip.measure.stochastic.LabelCorrelation crossTenorCorrelation,
		final double crossCurveCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> tenorScalingFactorMap)
		throws java.lang.Exception
	{
		super (
			oisTenorVegaRiskWeight,
			libor1MTenorVegaRiskWeight,
			libor3MTenorVegaRiskWeight,
			libor6MTenorVegaRiskWeight,
			libor12MTenorVegaRiskWeight,
			primeTenorVegaRiskWeight,
			municipalTenorVegaRiskWeight,
			crossTenorCorrelation,
			crossCurveCorrelation,
			concentrationThreshold,
			vegaScaler,
			historicalVolatilityRatio,
			oisTenorDeltaRiskWeight,
			libor1MTenorDeltaRiskWeight,
			libor3MTenorDeltaRiskWeight,
			libor6MTenorDeltaRiskWeight,
			libor12MTenorDeltaRiskWeight,
			primeTenorDeltaRiskWeight,
			municipalTenorDeltaRiskWeight
		);

		if (null == (_tenorScalingFactorMap = tenorScalingFactorMap) || 0 == _tenorScalingFactorMap.size())
		{
			throw new java.lang.Exception ("BucketVegaSettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Tenor Scaling Factor Map
	 * 
	 * @return The Tenor Scaling Factor Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorScalingFactorMap()
	{
		return _tenorScalingFactorMap;
	}
}
