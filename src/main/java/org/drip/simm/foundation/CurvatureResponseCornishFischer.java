
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
 * CurvatureResponseCornishFischer computes the Curvature Co-variance Scaling Factor using the Cumulative
 * 	Curvature Sensitivities. The References are:
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

public class CurvatureResponseCornishFischer implements org.drip.simm.foundation.CurvatureResponse
{

	/**
	 * ISDA SIMM VaR Curvature Cut-off
	 */

	public static final double CURVATURE_VAR_CUT_OFF = 0.995;

	private double _varCutoff = java.lang.Double.NaN;
	private double _lambdaPlateauPeak = java.lang.Double.NaN;

	/**
	 * Construct the Standard Instance of CurvatureResponseCornishFischer
	 * 
	 * @return The Standard Instance of CurvatureResponseCornishFischer
	 */

	public static final CurvatureResponseCornishFischer Standard()
	{
		try
		{
			return new CurvatureResponseCornishFischer (CURVATURE_VAR_CUT_OFF);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CurvatureResponseCornishFischer Constructor
	 * 
	 * @param varCutoff VaR Cut-off
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CurvatureResponseCornishFischer (
		final double varCutoff)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_varCutoff = varCutoff) ||
				0. > _varCutoff || 1. < _varCutoff)
		{
			throw new java.lang.Exception ("CurvatureResponseCornishFischer Constructor => Invalid Inputs");
		}

		double tailVariate = org.drip.measure.gaussian.NormalQuadrature.InverseCDF (_varCutoff);

		_lambdaPlateauPeak = tailVariate * tailVariate - 1.;
	}

	/**
	 * Retrieve the VaR Cut-off
	 * 
	 * @return The VaR Cut-off
	 */

	public double varCutoff()
	{
		return _varCutoff;
	}

	/**
	 * Retrieve the Lambda Plateau Peak
	 * 
	 * @return The Lambda Plateau Peak
	 */

	public double lambdaPlateauPeak()
	{
		return _lambdaPlateauPeak;
	}

	/**
	 * Compute the Lambda from the Curvature Sensitivities
	 * 
	 * @param cumulativeRiskFactorSensitivity Cumulative Risk Factor Sensitivity
	 * @param cumulativeRiskFactorSensitivityPositive Cumulative Risk Factor Sensitivity Positive
	 * 
	 * @return The Lambda
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double lambda (
		final double cumulativeRiskFactorSensitivity,
		final double cumulativeRiskFactorSensitivityPositive)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (cumulativeRiskFactorSensitivity) ||
			!org.drip.quant.common.NumberUtil.IsValid (cumulativeRiskFactorSensitivityPositive) ||
				0. > cumulativeRiskFactorSensitivityPositive)
		{
			throw new java.lang.Exception ("CurvatureResponseCornishFischer::lambda => Invalid Inputs");
		}

		double theta = java.lang.Math.min (
			0. == cumulativeRiskFactorSensitivityPositive ? 0. :
				cumulativeRiskFactorSensitivity / cumulativeRiskFactorSensitivityPositive,
			0.
		);

		return _lambdaPlateauPeak * (1. + theta) - theta;
	}
}
