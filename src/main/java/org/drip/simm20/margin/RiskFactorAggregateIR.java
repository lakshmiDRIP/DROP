
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
 * RiskFactorAggregateIR holds the Sensitivity Margin Aggregates for each of the IR Risk Factors - OIS, LIBOR
 *  1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL. The References are:
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

public class RiskFactorAggregateIR
{
	private double _sensitivityMargin = java.lang.Double.NaN;
	private double _concentrationRiskFactor = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _oisSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _primeSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor1MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor3MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor6MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor12MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _municipalSensitivityMargin = null;

	/**
	 * RiskFactorAggregateIR Constructor
	 * 
	 * @param oisSensitivityMargin The OIS Sensitivity Margin
	 * @param libor1MSensitivityMargin The LIBOR 1M Sensitivity Margin
	 * @param libor3MSensitivityMargin The LIBOR 3M Sensitivity Margin
	 * @param libor6MSensitivityMargin The LIBOR 6M Sensitivity Margin
	 * @param libor12MSensitivityMargin The LIBOR 12M Sensitivity Margin
	 * @param primeSensitivityMargin The PRIME Sensitivity Margin
	 * @param municipalSensitivityMargin The Municipal Sensitivity Margin
	 * @param sensitivityMargin The Bucket Sensitivity Margin
	 * @param concentrationRiskFactor The Currency's Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorAggregateIR (
		final java.util.Map<java.lang.String, java.lang.Double> oisSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> primeSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> municipalSensitivityMargin,
		final double sensitivityMargin,
		final double concentrationRiskFactor)
		throws java.lang.Exception
	{
		 if (!org.drip.quant.common.NumberUtil.IsValid (_sensitivityMargin = sensitivityMargin) ||
			null == (_oisSensitivityMargin = oisSensitivityMargin) || 0 == _oisSensitivityMargin.size() ||
			null == (_libor1MSensitivityMargin = libor1MSensitivityMargin) ||
				0 == _libor1MSensitivityMargin.size() ||
			null == (_libor3MSensitivityMargin = libor3MSensitivityMargin) ||
				0 == _libor3MSensitivityMargin.size() ||
			null == (_libor6MSensitivityMargin = libor6MSensitivityMargin) ||
				0 == _libor6MSensitivityMargin.size() ||
			null == (_libor12MSensitivityMargin = libor12MSensitivityMargin) ||
				0 == _libor12MSensitivityMargin.size() ||
			null == (_municipalSensitivityMargin = municipalSensitivityMargin) ||
				0 == _municipalSensitivityMargin.size() ||
			null == (_primeSensitivityMargin = primeSensitivityMargin) ||
				0 == _primeSensitivityMargin.size() ||
			!org.drip.quant.common.NumberUtil.IsValid (_concentrationRiskFactor = concentrationRiskFactor))
		 {
			 throw new java.lang.Exception ("RiskFactorAggregateIR Constructor => Invalid Inputs");
		 }
	}

	/**
	 * Retrieve the OIS Sensitivity Margin Map
	 * 
	 * @return The OIS Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisSensitivityMargin()
	{
		return _oisSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 1M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 1M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MSensitivityMargin()
	{
		return _libor1MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 3M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 3M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MSensitivityMargin()
	{
		return _libor3MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 6M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 6M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MSensitivityMargin()
	{
		return _libor6MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 12M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 12M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MSensitivityMargin()
	{
		return _libor12MSensitivityMargin;
	}

	/**
	 * Retrieve the PRIME Sensitivity Margin Map
	 * 
	 * @return The PRIME Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeSensitivityMargin()
	{
		return _primeSensitivityMargin;
	}

	/**
	 * Retrieve the MUNICIPAL Sensitivity Margin Map
	 * 
	 * @return The MUNICIPAL Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalSensitivityMargin()
	{
		return _municipalSensitivityMargin;
	}

	/**
	 * Retrieve the Bucket Sensitivity Margin
	 * 
	 * @return The Bucket Sensitivity Margin
	 */

	public double sensitivityMargin()
	{
		return _sensitivityMargin;
	}

	/**
	 * Retrieve the Bucket Concentration Risk Factor
	 * 
	 * @return The Bucket Concentration Risk Factor
	 */

	public double concentrationRiskFactor()
	{
		return _concentrationRiskFactor;
	}
}
