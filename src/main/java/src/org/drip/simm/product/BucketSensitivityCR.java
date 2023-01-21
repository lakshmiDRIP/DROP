
package org.drip.simm.product;

import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.margin.BucketAggregateCR;
import org.drip.simm.margin.RiskFactorAggregateCR;
import org.drip.simm.margin.SensitivityAggregateCR;
import org.drip.simm.parameters.BucketCurvatureSettingsCR;
import org.drip.simm.parameters.BucketSensitivitySettingsCR;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>BucketSensitivityCR</i> holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across CR Tenor
 * Factors. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/README.md">ISDA SIMM Risk Factor Sensitivities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketSensitivityCR
{
	private RiskFactorTenorSensitivity _cumulativeTenorSensitivityMap = null;
	private Map<String, RiskFactorTenorSensitivity> _tenorSensitivityMap = null;

	private BucketAggregateCR linearAggregate (
		final BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		RiskFactorAggregateCR riskFactorAggregate = curveAggregate (
			bucketSensitivitySettings
		);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		SensitivityAggregateCR sensitivityAggregate = riskFactorAggregate.linearMargin (
			bucketSensitivitySettings
		);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new BucketAggregateCR (
				riskFactorAggregate,
				sensitivityAggregate,
				sensitivityAggregate.cumulativeMarginCovariance(),
				riskFactorAggregate.cumulativeSensitivityMargin()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private BucketAggregateCR curvatureAggregate (
		final BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		RiskFactorAggregateCR riskFactorAggregate = curveAggregate (
			bucketSensitivitySettings
		);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		SensitivityAggregateCR sensitivityAggregate = riskFactorAggregate.curvatureMargin (
			bucketSensitivitySettings
		);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new BucketAggregateCR (
				riskFactorAggregate,
				sensitivityAggregate,
				sensitivityAggregate.cumulativeMarginCovariance(),
				riskFactorAggregate.cumulativeSensitivityMargin()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivityCR Constructor
	 * 
	 * @param tenorSensitivityMap The Risk Factor Tenor Sensitivity Map
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivityCR (
		final Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap)
		throws Exception
	{
		if (null == (_tenorSensitivityMap = tenorSensitivityMap) || 0 == _tenorSensitivityMap.size())
		{
			throw new Exception (
				"BucketSensitivityCR Constructor => Invalid Inputs"
			);
		}

		Map<String, Double> riskFactorTenorSensitivityMap = new CaseInsensitiveHashMap<Double>();

		for (Map.Entry<String, RiskFactorTenorSensitivity> tenorSensitivityMapEntry :
			_tenorSensitivityMap.entrySet()
		)
		{
			Map<String, Double> componentRiskFactorTenorSensitivityMap =
				tenorSensitivityMapEntry.getValue().sensitivityMap();

			for (Map.Entry<String, Double> componentRiskFactorTenorSensitivityMapEntry :
				componentRiskFactorTenorSensitivityMap.entrySet()
			)
			{
				String tenor = componentRiskFactorTenorSensitivityMapEntry.getKey();

				if (riskFactorTenorSensitivityMap.containsKey (
					tenor
				))
				{
					riskFactorTenorSensitivityMap.put (
						tenor,
						riskFactorTenorSensitivityMap.get (
							tenor
						) + componentRiskFactorTenorSensitivityMap.get (
							tenor
						)
					);
				}
				else
				{
					riskFactorTenorSensitivityMap.put (
						tenor,
						componentRiskFactorTenorSensitivityMap.get (
							tenor
						)
					);
				}
			}
		}

		_cumulativeTenorSensitivityMap = new RiskFactorTenorSensitivity (
			riskFactorTenorSensitivityMap
		);
	}

	/**
	 * Retrieve the Cumulative Risk Factor Tenor Sensitivity Map
	 * 
	 * @return The Cumulative Risk Factor Tenor Sensitivity Map
	 */

	public RiskFactorTenorSensitivity cumulativeTenorSensitivityMap()
	{
		return _cumulativeTenorSensitivityMap;
	}

	/**
	 * Retrieve the Risk Factor Tenor Sensitivity Map
	 * 
	 * @return The Risk Factor Tenor Sensitivity Map
	 */

	public Map<String, RiskFactorTenorSensitivity> tenorSensitivityMap()
	{
		return _tenorSensitivityMap;
	}

	/**
	 * Generate the Cumulative Tenor Sensitivity
	 * 
	 * @return The Cumulative Tenor Sensitivity
	 */

	public double cumulativeTenorSensitivity()
	{
		return _cumulativeTenorSensitivityMap.cumulative();
	}

	/**
	 * Compute the Sensitivity Concentration Risk Factor
	 * 
	 * @param sensitivityConcentrationThreshold The Sensitivity Concentration Threshold
	 * 
	 * @return The Sensitivity Concentration Risk Factor
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double sensitivityConcentrationRiskFactor (
		final double sensitivityConcentrationThreshold)
		throws Exception
	{
		if (!NumberUtil.IsValid (
			sensitivityConcentrationThreshold
		))
		{
			throw new Exception (
				"BucketSensitivityCR::sensitivityConcentrationRiskFactor => Invalid Inputs"
			);
		}

		return Math.max (
			Math.sqrt (
				Math.max (
					cumulativeTenorSensitivity(),
					0.
				) / sensitivityConcentrationThreshold
			),
			1.
		);
	}

	/**
	 * Generate the Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> tenorSensitivityMargin (
		final BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> tenorSensitivityMargin = _cumulativeTenorSensitivityMap.sensitivityMargin (
			bucketSensitivitySettings.tenorRiskWeight()
		);

		if (null == tenorSensitivityMargin)
		{
			return tenorSensitivityMargin;
		}

		double sensitivityConcentrationRiskFactor = Double.NaN;

		try
		{
			sensitivityConcentrationRiskFactor = sensitivityConcentrationRiskFactor (
				bucketSensitivitySettings.concentrationThreshold()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (Map.Entry<String, Double> tenorSensitivityMarginEntry : tenorSensitivityMargin.entrySet())
		{
			String tenor = tenorSensitivityMarginEntry.getKey();

			tenorSensitivityMargin.put (
				tenor,
				tenorSensitivityMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return tenorSensitivityMargin;
	}

	/**
	 * Generate the CR Margin Factor Curve Tenor Aggregate
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The CR Margin Factor Curve Tenor Aggregate
	 */

	public RiskFactorAggregateCR curveAggregate (
		final BucketSensitivitySettingsCR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Set<String> componentNameSet = _tenorSensitivityMap.keySet();

		Map<String, Map<String, Double>> componentTenorSensitivityMargin =
			new CaseInsensitiveHashMap<Map<String, Double>>();

		double sensitivityConcentrationRiskFactor = Double.NaN;

		try
		{
			sensitivityConcentrationRiskFactor = sensitivityConcentrationRiskFactor (
				bucketSensitivitySettings.concentrationThreshold()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (String componentName : componentNameSet)
		{
			Map<String, Double> tenorSensitivity = _tenorSensitivityMap.get (
				componentName
			).sensitivityMap();

			Map<String, Double> tenorSensitivityMargin = new CaseInsensitiveHashMap<Double>();

			for (Map.Entry<String, Double> tenorSensitivityEntry : tenorSensitivity.entrySet())
			{
				String tenor = tenorSensitivityEntry.getKey();

				tenorSensitivityMargin.put (
					tenor,
					tenorSensitivity.get (
						tenor
					) * sensitivityConcentrationRiskFactor
				);
			}

			componentTenorSensitivityMargin.put (
				componentName,
				tenorSensitivityMargin
			);
		}

		try
		{
			return new RiskFactorAggregateCR (
				componentTenorSensitivityMargin,
				sensitivityConcentrationRiskFactor
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Bucket CR Sensitivity Margin Aggregate
	 * 
	 * @param bucketSensitivitySettingsCR The CR Bucket Sensitivity Settings
	 * 
	 * @return The Bucket IR Sensitivity Margin Aggregate
	 */

	public BucketAggregateCR aggregate (
		final BucketSensitivitySettingsCR bucketSensitivitySettingsCR)
	{
		if (null == bucketSensitivitySettingsCR)
		{
			return null;
		}

		return bucketSensitivitySettingsCR instanceof BucketCurvatureSettingsCR ? curvatureAggregate (
			bucketSensitivitySettingsCR
		) : linearAggregate (
			bucketSensitivitySettingsCR
		);
	}
}
