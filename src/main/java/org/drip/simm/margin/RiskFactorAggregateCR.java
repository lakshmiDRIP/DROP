
package org.drip.simm.margin;

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
 * RiskFactorAggregateCR holds the Sensitivity Margin Aggregates for each of the CR Risk Factors - both
 *  Qualifying and Non-qualifying. The References are:
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

public class RiskFactorAggregateCR
{
	private double _concentrationRiskFactor = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _tenorSensitivityMargin = null;

	/**
	 * RiskFactorAggregateCR Constructor
	 * 
	 * @param tenorSensitivityMargin The Tenor Sensitivity Margin Map
	 * @param concentrationRiskFactor The Bucket Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorAggregateCR (
		final java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin,
		final double concentrationRiskFactor)
		throws java.lang.Exception
	{
		if (null == (_tenorSensitivityMargin = tenorSensitivityMargin) || 0 == _tenorSensitivityMargin.size() ||
			!org.drip.quant.common.NumberUtil.IsValid (_concentrationRiskFactor = concentrationRiskFactor))
		 {
			 throw new java.lang.Exception ("RiskFactorAggregateCR Constructor => Invalid Inputs");
		 }
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

	/**
	 * Retrieve the Tenor Sensitivity Margin Map
	 * 
	 * @return The Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorSensitivityMargin()
	{
		return _tenorSensitivityMargin;
	}

	/**
	 * Compute the Cumulative Tenor Sensitivity Margin
	 * 
	 * @return The Cumulative Tenor Sensitivity Margin
	 */

	public double cumulativeTenorSensitivityMargin()
	{
		double cumulativeTenorSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntry :
			_tenorSensitivityMargin.entrySet())
		{
			cumulativeTenorSensitivityMargin = cumulativeTenorSensitivityMargin +
				tenorSensitivityMarginEntry.getValue();
		}

		return cumulativeTenorSensitivityMargin;
	}

	/**
	 * Compute the Linear Margin Covariance
	 * 
	 * @param bucketSensitivitySettingsCR The Credit Bucket Sensitivity Settings
	 * 
	 * @return The Linear Margin Covariance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsCR)
		{
			throw new java.lang.Exception
				("RiskFactorAggregateCR::linearMarginCovariance => Invalid Inputs");
		}

		double linearMarginCovariance = 0.;

		double differentIssuerSeniorityCorrelation =
			bucketSensitivitySettingsCR.differentIssuerSeniorityCorrelation();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntryOuter :
			_tenorSensitivityMargin.entrySet())
		{
			java.lang.String outerTenor = tenorSensitivityMarginEntryOuter.getKey();

			double outerSensitivityMargin = tenorSensitivityMarginEntryOuter.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntryInner :
				_tenorSensitivityMargin.entrySet())
			{
				linearMarginCovariance = linearMarginCovariance +
					(outerTenor.equalsIgnoreCase (tenorSensitivityMarginEntryInner.getKey()) ? 1. :
					differentIssuerSeniorityCorrelation) * outerSensitivityMargin *
					tenorSensitivityMarginEntryInner.getValue();
			}
		}

		return linearMarginCovariance;
	}

	/**
	 * Compute the Curvature Margin Covariance
	 * 
	 * @param bucketSensitivitySettingsCR The Credit Bucket Sensitivity Settings
	 * 
	 * @return The Curvature Margin Covariance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsCR)
		{
			throw new java.lang.Exception
				("RiskFactorAggregateCR::curvatureMarginCovariance => Invalid Inputs");
		}

		double curvatureMarginCovariance = 0.;

		double differentIssuerSeniorityCorrelation =
			bucketSensitivitySettingsCR.differentIssuerSeniorityCorrelation();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntryOuter :
			_tenorSensitivityMargin.entrySet())
		{
			java.lang.String outerTenor = tenorSensitivityMarginEntryOuter.getKey();

			double outerSensitivityMargin = tenorSensitivityMarginEntryOuter.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorSensitivityMarginEntryInner :
				_tenorSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = tenorSensitivityMarginEntryInner.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					differentIssuerSeniorityCorrelation;

				curvatureMarginCovariance = curvatureMarginCovariance +
					outerSensitivityMargin * tenorSensitivityMarginEntryInner.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance;
	}
}
