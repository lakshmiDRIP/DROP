
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
 * IRWeightedSensitivitySettings holds the ISDA SIMM 2.0 Risk Factor Tenor/Sub-Curve Level Weighted Net
 * 	Sensitivity Settings. The References are:
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

public class IRWeightedSensitivitySettings
{
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _ois = null;
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _prime = null;
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _libor1M = null;
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _libor3M = null;
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _libor6M = null;
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _libor12M = null;
	private org.drip.simm20.product.WeightedNetTenorSensitivitySettings _municipal = null;

	/**
	 * IRWeightedSensitivitySettings Constructor
	 * 
	 * @param ois The OIS Sensitivity Margin Estimator Settings
	 * @param libor1M The LIBOR 1M Sensitivity Margin Estimator Settings
	 * @param libor3M The LIBOR 3M Sensitivity Margin Estimator Settings
	 * @param libor6M The LIBOR 6M Sensitivity Margin Estimator Settings
	 * @param libor12M The LIBOR 12M Sensitivity Margin Estimator Settings
	 * @param prime The PRIME Sensitivity Margin Estimator Settings
	 * @param municipal The MUNICIPAL 12M Sensitivity Margin Estimator Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IRWeightedSensitivitySettings (
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings ois,
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor1M,
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor3M,
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor6M,
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor12M,
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings prime,
		final org.drip.simm20.product.WeightedNetTenorSensitivitySettings municipal)
		throws java.lang.Exception
	{
		if (null == (_ois = ois) ||
			null == (_libor1M = libor1M) ||
			null == (_libor3M = libor3M) ||
			null == (_libor6M = libor6M) ||
			null == (_libor12M = libor12M) ||
			null == (_prime = prime) ||
			null == (_municipal = municipal))
		{
			throw new java.lang.Exception ("IRWeightedSensitivitySettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Sensitivity Margin Estimator Settings
	 * 
	 * @return The OIS Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings ois()
	{
		return _ois;
	}

	/**
	 * Retrieve the LIBOR 1M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 1M Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor1M()
	{
		return _libor1M;
	}

	/**
	 * Retrieve the LIBOR 3M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 3M Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor3M()
	{
		return _libor3M;
	}

	/**
	 * Retrieve the LIBOR 6M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 6M Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor6M()
	{
		return _libor6M;
	}

	/**
	 * Retrieve the LIBOR 12M Sensitivity Margin Estimator Settings
	 * 
	 * @return The LIBOR 12M Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings libor12M()
	{
		return _libor12M;
	}

	/**
	 * Retrieve the PRIME Sensitivity Margin Estimator Settings
	 * 
	 * @return The PRIME Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings prime()
	{
		return _prime;
	}

	/**
	 * Retrieve the MUNICIPAL Sensitivity Margin Estimator Settings
	 * 
	 * @return The MUNICIPAL Sensitivity Margin Estimator Settings
	 */

	public org.drip.simm20.product.WeightedNetTenorSensitivitySettings municipal()
	{
		return _municipal;
	}
}
