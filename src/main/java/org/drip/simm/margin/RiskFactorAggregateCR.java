
package org.drip.simm.margin;

import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.parameters.BucketSensitivitySettingsCR;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * 	both Qualifying and Non-qualifying. The References are:
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
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>RiskFactorAggregateCR</i> Constructor</li>
 * 		<li>Retrieve the Bucket Concentration Risk Factor</li>
 * 		<li>Retrieve the Component Tenor Sensitivity Margin Map</li>
 * 		<li>Retrieve the Component Tenor Sensitivity Margin</li>
 * 		<li>Compute the Cumulative Sensitivity Margin for the specified Component</li>
 * 		<li>Compute the Cumulative Sensitivity Margin</li>
 * 		<li>Compute the Component Pair Linear Margin Co-variance</li>
 * 		<li>Compute the Component Pair Curvature Margin Co-variance</li>
 * 		<li>Compute the Linear Margin Co-variance</li>
 * 		<li>Compute the Curvature Margin Co-variance</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin/README.md">ISDA SIMM Risk Factor Margin Metrics</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskFactorAggregateCR
{
	private double _concentrationRiskFactor = Double.NaN;
	private Map<String, Map<String, Double>> _componentSensitivityMarginMap = null;

	/**
	 * <i>RiskFactorAggregateCR</i> Constructor
	 * 
	 * @param componentSensitivityMarginMap The Component Sensitivity Margin Map
	 * @param concentrationRiskFactor The Bucket Concentration Risk Factor
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorAggregateCR (
		final Map<String, Map<String, Double>> componentSensitivityMarginMap,
		final double concentrationRiskFactor)
		throws Exception
	{
		if (null == (_componentSensitivityMarginMap = componentSensitivityMarginMap) ||
				0 == _componentSensitivityMarginMap.size() ||
			!NumberUtil.IsValid (_concentrationRiskFactor = concentrationRiskFactor))
		{
			 throw new Exception ("RiskFactorAggregateCR Constructor => Invalid Inputs");
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

	public Map<String, Map<String, Double>> componentSensitivityMarginMap()
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

	public Map<String, Double> componentSensitivityMargin (
		final String componentName)
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double cumulativeComponentSensitivityMargin (
		final String componentName)
		throws Exception
	{
		if (null == componentName || !_componentSensitivityMarginMap.containsKey (componentName)) {
			throw new Exception (
				"RiskFactorAggregateCR::cumulativeComponentSensitivityMargin => Invalid Inputs"
			);
		}

		double cumulativeComponentSensitivityMargin = 0.;

		for (Map.Entry<String, Double> componentTenorSensitivityMarginEntry :
			_componentSensitivityMarginMap.get (componentName).entrySet()) {
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

		for (Map.Entry<String, Map<String, Double>> componentSensitivityMarginMapEntry :
			_componentSensitivityMarginMap.entrySet())
		{
			for (Map.Entry<String, Double> componentTenorSensitivityMapEntry :
				componentSensitivityMarginMapEntry.getValue().entrySet())
			{
				cumulativeSensitivityMargin = cumulativeSensitivityMargin +
					componentTenorSensitivityMapEntry.getValue();
			}
		}

		return cumulativeSensitivityMargin;
	}

	/**
	 * Compute the Component Pair Linear Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * @param componentName1 Component #1 Name
	 * @param componentName2 Component #2 Name
	 * 
	 * @return The Component Pair Linear Margin Covariance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double componentLinearMarginCovariance (
		final BucketSensitivitySettingsCR bucketSensitivitySettingsCR,
		final String componentName1,
		final String componentName2)
		throws Exception
	{
		if (null == bucketSensitivitySettingsCR ||
			!_componentSensitivityMarginMap.containsKey (componentName1) ||
			!_componentSensitivityMarginMap.containsKey (componentName2))
		{
			throw new Exception (
				"RiskFactorAggregateCR::componentLinearMarginCovariance => Invalid Inputs"
			);
		}

		double crossTenorCorrelation = bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation();

		double componentLinearMarginCovariance = 0.;

		Map<String, Double> componentTenorSensitivityMargin1 =
			_componentSensitivityMarginMap.get (componentName1);

		Map<String, Double> componentTenorSensitivityMargin2 =
			_componentSensitivityMarginMap.get (componentName2);

		for (Map.Entry<String, Double> componentTenorSensitivityMargin1Entry :
			componentTenorSensitivityMargin1.entrySet())
		{
			String component1Tenor = componentTenorSensitivityMargin1Entry.getKey();

			double component1SensitivityMargin = componentTenorSensitivityMargin1Entry.getValue();

			for (Map.Entry<String, Double> componentTenorSensitivityMargin2Entry :
				componentTenorSensitivityMargin2.entrySet())
			{
				componentLinearMarginCovariance = componentLinearMarginCovariance +
					component1SensitivityMargin * componentTenorSensitivityMargin2Entry.getValue() * (
						component1Tenor.equalsIgnoreCase (
							componentTenorSensitivityMargin2Entry.getKey()
						) ? 1. : crossTenorCorrelation
					);
			}
		}

		return componentLinearMarginCovariance;
	}

	/**
	 * Compute the Component Pair Curvature Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * @param componentName1 Component #1 Name
	 * @param componentName2 Component #2 Name
	 * 
	 * @return The Component Pair Curvature Margin Covariance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double componentCurvatureMarginCovariance (
		final BucketSensitivitySettingsCR bucketSensitivitySettingsCR,
		final String componentName1,
		final String componentName2)
		throws Exception
	{
		if (null == bucketSensitivitySettingsCR ||
			!_componentSensitivityMarginMap.containsKey (componentName1) ||
			!_componentSensitivityMarginMap.containsKey (componentName2))
		{
			throw new Exception (
				"RiskFactorAggregateCR::componentCurvatureMarginCovariance => Invalid Inputs"
			);
		}

		double crossTenorCorrelation = bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation();

		double componentCurvatureMarginCovariance = 0.;

		Map<String, Double> componentTenorSensitivityMargin1 =
			_componentSensitivityMarginMap.get (componentName1);

		Map<String, Double> componentTenorSensitivityMargin2 =
			_componentSensitivityMarginMap.get (componentName2);

		for (Map.Entry<String, Double> componentTenorSensitivityMargin1Entry :
			componentTenorSensitivityMargin1.entrySet())
		{
			String component1Tenor = componentTenorSensitivityMargin1Entry.getKey();

			double component1SensitivityMargin = componentTenorSensitivityMargin1Entry.getValue();

			for (Map.Entry<String, Double> componentTenorSensitivityMargin2Entry :
				componentTenorSensitivityMargin2.entrySet())
			{
				componentCurvatureMarginCovariance = componentCurvatureMarginCovariance +
					component1SensitivityMargin * componentTenorSensitivityMargin2Entry.getValue() * (
						component1Tenor.equalsIgnoreCase (
							componentTenorSensitivityMargin2Entry.getKey()
						) ? 1. : crossTenorCorrelation * crossTenorCorrelation
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

	public SensitivityAggregateCR linearMargin (
		final BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		double cumulativeMarginSensitivity = 0.;

		Set<String> componentNameSet = _componentSensitivityMarginMap.keySet();

		Map<String, Double> componentMarginCovarianceMap = new CaseInsensitiveHashMap<Double>();

		try {
			for (String componentName1 : componentNameSet) {
				for (String componentName2 : componentNameSet) {
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

			return new SensitivityAggregateCR (componentMarginCovarianceMap, cumulativeMarginSensitivity);
		} catch (Exception e) {
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

	public SensitivityAggregateCR curvatureMargin (
		final BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		double cumulativeMarginSensitivity = 0.;

		Set<String> componentNameSet = _componentSensitivityMarginMap.keySet();

		Map<String, Double> componentMarginCovarianceMap = new CaseInsensitiveHashMap<Double>();

		try {
			for (String componentName1 : componentNameSet) {
				for (String componentName2 : componentNameSet) {
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

			return new SensitivityAggregateCR (componentMarginCovarianceMap, cumulativeMarginSensitivity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
