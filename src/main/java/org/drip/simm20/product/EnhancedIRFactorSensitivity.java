
package org.drip.simm20.product;

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
 * EnhancedIRFactorSensitivity holds the ISDA SIMM 2.0 Risk Factor Tenor Bucket Sensitivities across IR
 *  Factor Sub Curves enhanced with the USD specific Sub-Curve Factors - PRIME and MUNICIPAL. The References
 *  are:
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

public class EnhancedIRFactorSensitivity extends org.drip.simm20.product.IRFactorSensitivity
{
	private org.drip.simm20.product.RiskFactorSensitivity _primeSensitivity = null;
	private org.drip.simm20.product.RiskFactorSensitivity _municipalSensitivity = null;

	/**
	 * EnhancedIRFactorSensitivity Constructor
	 * 
	 * @param oisSensitivity The OIS Risk Factor Sensitivity
	 * @param libor1MSensitivity The LIBOR-1M Risk Factor Sensitivity
	 * @param libor3MSensitivity The LIBOR-3M Risk Factor Sensitivity
	 * @param libor6MSensitivity The LIBOR-6M Risk Factor Sensitivity
	 * @param libor12MSensitivity The LIBOR-12M Risk Factor Sensitivity
	 * @param primeSensitivity The PRIME Risk Factor Sensitivity
	 * @param municipalSensitivity The MUNICIPAL Risk Factor Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EnhancedIRFactorSensitivity (
		final org.drip.simm20.product.RiskFactorSensitivity oisSensitivity,
		final org.drip.simm20.product.RiskFactorSensitivity libor1MSensitivity,
		final org.drip.simm20.product.RiskFactorSensitivity libor3MSensitivity,
		final org.drip.simm20.product.RiskFactorSensitivity libor6MSensitivity,
		final org.drip.simm20.product.RiskFactorSensitivity libor12MSensitivity,
		final org.drip.simm20.product.RiskFactorSensitivity primeSensitivity,
		final org.drip.simm20.product.RiskFactorSensitivity municipalSensitivity)
		throws java.lang.Exception
	{
		super (
			oisSensitivity,
			libor1MSensitivity,
			libor3MSensitivity,
			libor6MSensitivity,
			libor12MSensitivity
		);

		if (null == (_primeSensitivity = primeSensitivity) ||
			null == (_municipalSensitivity = municipalSensitivity))
		{
			throw new java.lang.Exception ("EnhancedIRFactorSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the PRIME Risk Factor Sensitivity
	 * 
	 * @return The PRIME Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorSensitivity primeSensitivity()
	{
		return _primeSensitivity;
	}

	/**
	 * Retrieve the MUNICIPAL Risk Factor Sensitivity
	 * 
	 * @return The MUNICIPAL Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorSensitivity municipalSensitivity()
	{
		return _municipalSensitivity;
	}

	/**
	 * Generate the Cumulative Delta
	 * 
	 * @return The Cumulative Delta
	 */

	public double cumulative()
	{
		return super.cumulative() +
			_primeSensitivity.cumulative() +
			_municipalSensitivity.cumulative();
	}
}
