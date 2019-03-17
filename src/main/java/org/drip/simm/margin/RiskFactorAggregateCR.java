
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
 * <i>RiskFactorAggregateCR</i> holds the Sensitivity Margin Aggregates for each of the CR Risk Factors -
 * both Qualifying and Non-qualifying. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin">Margin</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskFactorAggregateCR
{
	private double _concentrationRiskFactor = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>
		_componentSensitivityMarginMap = null;

	/**
	 * RiskFactorAggregateCR Constructor
	 * 
	 * @param componentSensitivityMarginMap The Component Sensitivity Margin Map
	 * @param concentrationRiskFactor The Bucket Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorAggregateCR (
		final java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>
			componentSensitivityMarginMap,
		final double concentrationRiskFactor)
		throws java.lang.Exception
	{
		if (null == (_componentSensitivityMarginMap = componentSensitivityMarginMap) ||
			0 == _componentSensitivityMarginMap.size() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_concentrationRiskFactor = concentrationRiskFactor))
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
	 * Retrieve the Component Tenor Sensitivity Margin Map
	 * 
	 * @return The Component Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>
		componentSensitivityMarginMap()
	{
		return _componentSensitivityMarginMap;
	}

	/**
	 * Retrieve the Component Tenor Sensitivity Margin
	 * 
	 * @param componentName The Component Name
	 * 
	 * @return The Component Tenor Sensitivity Margin
	 */

	public java.util.Map<java.lang.String, java.lang.Double> componentSensitivityMargin (
		final java.lang.String componentName)
	{
		return null != componentName || _componentSensitivityMarginMap.containsKey (componentName) ?
			_componentSensitivityMarginMap.get (componentName) : null;
	}

	/**
	 * Compute the Cumulative Sensitivity Margin for the specified Component
	 * 
	 * @param componentName The Component Name
	 * 
	 * @return The Cumulative Sensitivity Margin for the specified Component
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double cumulativeComponentSensitivityMargin (
		final java.lang.String componentName)
		throws java.lang.Exception
	{
		if (null == componentName || !_componentSensitivityMarginMap.containsKey (componentName))
		{
			throw new java.lang.Exception
				("RiskFactorAggregateCR::cumulativeComponentSensitivityMargin => Invalid Inputs");
		}

		double cumulativeComponentSensitivityMargin = 0.;

		java.util.Map<java.lang.String, java.lang.Double> componentTenorSensitivityMargin =
			_componentSensitivityMarginMap.get (componentName);

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> componentTenorSensitivityMarginEntry :
			componentTenorSensitivityMargin.entrySet())
		{
			cumulativeComponentSensitivityMargin = cumulativeComponentSensitivityMargin +
				componentTenorSensitivityMarginEntry.getValue();
		}

		return cumulativeComponentSensitivityMargin;
	}

	/**
	 * Compute the Cumulative Sensitivity Margin
	 * 
	 * @return The Cumulative Sensitivity Margin
	 */

	public double cumulativeSensitivityMargin()
	{
		double cumulativeSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>
			componentSensitivityMarginMapEntry : _componentSensitivityMarginMap.entrySet())
		{
			for (java.util.Map.Entry<java.lang.String, java.lang.Double> componentTenorSensitivityMapEntry :
				componentSensitivityMarginMapEntry.getValue().entrySet())
			{
				cumulativeSensitivityMargin = cumulativeSensitivityMargin +
					componentTenorSensitivityMapEntry.getValue();
			}
		}

		return cumulativeSensitivityMargin;
	}

	/**
	 * Compute the Component Pair Linear Margin Covariance
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * @param componentName1 Component #1 Name
	 * @param componentName2 Component #2 Name
	 * 
	 * @return The Component Pair Linear Margin Covariance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double componentLinearMarginCovariance (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR,
		final java.lang.String componentName1,
		final java.lang.String componentName2)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsCR ||
			!_componentSensitivityMarginMap.containsKey (componentName1) ||
			!_componentSensitivityMarginMap.containsKey (componentName2))
		{
			throw new java.lang.Exception
				("RiskFactorAggregateCR::componentLinearMarginCovariance => Invalid Inputs");
		}

		double crossTenorCorrelation = bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation();

		double componentLinearMarginCovariance = 0.;

		java.util.Map<java.lang.String, java.lang.Double> componentTenorSensitivityMargin1 =
			_componentSensitivityMarginMap.get (componentName1);

		java.util.Map<java.lang.String, java.lang.Double> componentTenorSensitivityMargin2 =
			_componentSensitivityMarginMap.get (componentName2);

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> componentTenorSensitivityMargin1Entry :
			componentTenorSensitivityMargin1.entrySet())
		{
			java.lang.String component1Tenor = componentTenorSensitivityMargin1Entry.getKey();

			double component1SensitivityMargin = componentTenorSensitivityMargin1Entry.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double>
				componentTenorSensitivityMargin2Entry : componentTenorSensitivityMargin2.entrySet())
			{
				componentLinearMarginCovariance = componentLinearMarginCovariance +
					component1SensitivityMargin * componentTenorSensitivityMargin2Entry.getValue() * (
						component1Tenor.equalsIgnoreCase (componentTenorSensitivityMargin2Entry.getKey()) ?
							1. : crossTenorCorrelation
					);
			}
		}

		return componentLinearMarginCovariance;
	}

	/**
	 * Compute the Component Pair Curvature Margin Covariance
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * @param componentName1 Component #1 Name
	 * @param componentName2 Component #2 Name
	 * 
	 * @return The Component Pair Curvature Margin Covariance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double componentCurvatureMarginCovariance (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR,
		final java.lang.String componentName1,
		final java.lang.String componentName2)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsCR ||
			!_componentSensitivityMarginMap.containsKey (componentName1) ||
			!_componentSensitivityMarginMap.containsKey (componentName2))
		{
			throw new java.lang.Exception
				("RiskFactorAggregateCR::componentCurvatureMarginCovariance => Invalid Inputs");
		}

		double crossTenorCorrelation = bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation();

		double componentCurvatureMarginCovariance = 0.;

		java.util.Map<java.lang.String, java.lang.Double> componentTenorSensitivityMargin1 =
			_componentSensitivityMarginMap.get (componentName1);

		java.util.Map<java.lang.String, java.lang.Double> componentTenorSensitivityMargin2 =
			_componentSensitivityMarginMap.get (componentName2);

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> componentTenorSensitivityMargin1Entry :
			componentTenorSensitivityMargin1.entrySet())
		{
			java.lang.String component1Tenor = componentTenorSensitivityMargin1Entry.getKey();

			double component1SensitivityMargin = componentTenorSensitivityMargin1Entry.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double>
				componentTenorSensitivityMargin2Entry : componentTenorSensitivityMargin2.entrySet())
			{
				componentCurvatureMarginCovariance = componentCurvatureMarginCovariance +
					component1SensitivityMargin * componentTenorSensitivityMargin2Entry.getValue() * (
						component1Tenor.equalsIgnoreCase (componentTenorSensitivityMargin2Entry.getKey()) ?
							1. : crossTenorCorrelation * crossTenorCorrelation
					);
			}
		}

		return componentCurvatureMarginCovariance;
	}

	/**
	 * Compute the Linear Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear Margin Co-variance
	 */

	public org.drip.simm.margin.SensitivityAggregateCR linearMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		java.util.Set<java.lang.String> componentNameSet = _componentSensitivityMarginMap.keySet();

		java.util.Map<java.lang.String, java.lang.Double> componentMarginCovarianceMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		double cumulativeMarginSensitivity = 0.;

		try
		{
			for (java.lang.String componentName1 : componentNameSet)
			{
				for (java.lang.String componentName2 : componentNameSet)
				{
					double componentLinearMarginCovariance = componentLinearMarginCovariance (
						bucketSensitivitySettingsCR,
						componentName1,
						componentName2
					);

					cumulativeMarginSensitivity = cumulativeMarginSensitivity +
						componentLinearMarginCovariance;

					componentMarginCovarianceMap.put (
						componentName1 + "_" + componentName2,
						componentLinearMarginCovariance
					);
				}
			}

			return new SensitivityAggregateCR (
				componentMarginCovarianceMap,
				cumulativeMarginSensitivity
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Curvature Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature Margin Co-variance
	 */

	public org.drip.simm.margin.SensitivityAggregateCR curvatureMargin (
		final org.drip.simm.parameters.BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		java.util.Set<java.lang.String> componentNameSet = _componentSensitivityMarginMap.keySet();

		java.util.Map<java.lang.String, java.lang.Double> componentMarginCovarianceMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		double cumulativeMarginSensitivity = 0.;

		try
		{
			for (java.lang.String componentName1 : componentNameSet)
			{
				for (java.lang.String componentName2 : componentNameSet)
				{
					double componentCurvatureMarginCovariance = componentCurvatureMarginCovariance (
						bucketSensitivitySettingsCR,
						componentName1,
						componentName2
					);

					cumulativeMarginSensitivity = cumulativeMarginSensitivity +
						componentCurvatureMarginCovariance;

					componentMarginCovarianceMap.put (
						componentName1 + "_" + componentName2,
						componentCurvatureMarginCovariance
					);
				}
			}

			return new SensitivityAggregateCR (
				componentMarginCovarianceMap,
				cumulativeMarginSensitivity
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
