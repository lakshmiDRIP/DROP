
package org.drip.simm.foundation;

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
 * MarginEstimationSettings exposes the Customization Settings used in the Margin Estimation. The References
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

public class MarginEstimationSettings
{

	/**
	 * FRTB Based Position - Principal Component Estimator
	 */

	public static final java.lang.String POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_FRTB = "FRTB";

	/**
	 * ISDA Based Position - Principal Component Estimator
	 */

	public static final java.lang.String POSITION_PRINCIPAL_COMPONENT_COVARIANCE_ESTIMATOR_ISDA = "ISDA";

	private java.lang.String _positionPrincipalComponentScheme = "";
	private org.drip.simm.foundation.CurvatureEstimator _curvatureEstimator = null;

	/**
	 * Generate a Cornish-Fischer Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return Cornish-Fischer Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings CornishFischer (
		final java.lang.String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				org.drip.simm.foundation.CurvatureEstimatorResponseFunction.CornishFischer()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a ISDA Delta Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return ISDA Delta Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings ISDADelta (
		final java.lang.String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				org.drip.simm.foundation.CurvatureEstimatorISDADelta.Standard()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate an FRTB Instance of MarginEstimationSettings
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * 
	 * @return FRTB Instance of MarginEstimationSettings
	 */

	public static final MarginEstimationSettings FRTB (
		final java.lang.String positionPrincipalComponentScheme)
	{
		try
		{
			return new MarginEstimationSettings (
				positionPrincipalComponentScheme,
				org.drip.simm.foundation.CurvatureEstimatorFRTB.Standard()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarginEstimationSettings Constructor
	 * 
	 * @param positionPrincipalComponentScheme The Position Principal Component Scheme
	 * @param curvatureEstimator The Curvature Estimator Function
	 * 
	 * @throws java.lang.Exception Throwm if the Inputs are Invalid
	 */

	public MarginEstimationSettings (
		final java.lang.String positionPrincipalComponentScheme,
		final org.drip.simm.foundation.CurvatureEstimator curvatureEstimator)
		throws java.lang.Exception
	{
		if (null == (_positionPrincipalComponentScheme = positionPrincipalComponentScheme) ||
			_positionPrincipalComponentScheme.isEmpty() ||
			null == (_curvatureEstimator = curvatureEstimator))
		{
			throw new java.lang.Exception ("MarginEstimationSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Position Principal Component Scheme
	 * 
	 * @return The Position Principal Component Scheme
	 */

	public java.lang.String positionPrincipalComponentScheme()
	{
		return _positionPrincipalComponentScheme;
	}

	/**
	 * Retrieve the Curvature Estimator Function
	 * 
	 * @return The Curvature Estimator Function
	 */

	public org.drip.simm.foundation.CurvatureEstimator curvatureEstimator()
	{
		return _curvatureEstimator;
	}
}
