
package org.drip.simm.parameters;

import java.util.HashMap;
import java.util.Map;

import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.credit.CRNQSystemics20;
import org.drip.simm.credit.CRNQSystemics21;
import org.drip.simm.credit.CRNQSystemics24;
import org.drip.simm.credit.CRQSystemics20;
import org.drip.simm.credit.CRQSystemics21;
import org.drip.simm.credit.CRQSystemics24;
import org.drip.simm.credit.CRThresholdContainer20;
import org.drip.simm.credit.CRThresholdContainer21;
import org.drip.simm.credit.CRThresholdContainer24;

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
 * <i>BucketVegaSettingsCR</i> holds the Vega Risk Weights, Concentration Thresholds, and Cross-Tenor
 * Correlations for each Credit Curve and its Tenor. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/README.md">ISDA SIMM Risk Factor Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketVegaSettingsCR
	extends BucketSensitivitySettingsCR
{
	private double _vegaScaler = Double.NaN;
	private double _historicalVolatilityRatio = Double.NaN;
	private Map<String, Double> _tenorDeltaRiskWeight = null;

	/**
	 * Retrieve the ISDA 2.0 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_20 (
		final int bucketNumber)
	{
		BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_20 (
				bucketNumber
			);

		try
		{
			return null == bucketSensitivitySettingsCR ? null : new BucketVegaSettingsCR (
				TenorRiskWeightMap (
					CRQSystemics20.VEGA_RISK_WEIGHT
				),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				CRThresholdContainer20.QualifyingThreshold (
					bucketNumber
				).vega(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.0 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.0 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_20 (
		final int bucketNumber)
	{
		BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_20 (
				bucketNumber
			);

		try
		{
			return null == bucketSensitivitySettingsCR ? null : new BucketVegaSettingsCR (
				TenorRiskWeightMap (
					CRNQSystemics20.VEGA_RISK_WEIGHT
				),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				CRThresholdContainer20.NonQualifyingThreshold (
					bucketNumber
				).vega(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_21 (
		final int bucketNumber)
	{
		BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_21 (
				bucketNumber
			);

		try
		{
			return null == bucketSensitivitySettingsCR ? null : new BucketVegaSettingsCR (
				TenorRiskWeightMap (
					CRQSystemics21.VEGA_RISK_WEIGHT
				),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				CRThresholdContainer21.QualifyingThreshold (
					bucketNumber
				).vega(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.1 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_21 (
		final int bucketNumber)
	{
		BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_21 (
				bucketNumber
			);

		try
		{
			return null == bucketSensitivitySettingsCR ? null : new BucketVegaSettingsCR (
				TenorRiskWeightMap (
					CRNQSystemics21.VEGA_RISK_WEIGHT
				),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				CRThresholdContainer21.NonQualifyingThreshold (
					bucketNumber
				).vega(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.4 Credit Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.4 Credit Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRQ_24 (
		final int bucketNumber)
	{
		BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_24 (
				bucketNumber
			);

		try
		{
			return null == bucketSensitivitySettingsCR ? null : new BucketVegaSettingsCR (
				TenorRiskWeightMap (
					CRQSystemics24.VEGA_RISK_WEIGHT
				),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				CRThresholdContainer24.QualifyingThreshold (
					bucketNumber
				).vega(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.4 Credit Non-Qualifying Bucket Vega Settings
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The ISDA 2.4 Credit Non-Qualifying Bucket Vega Settings
	 */

	public static BucketVegaSettingsCR ISDA_CRNQ_24 (
		final int bucketNumber)
	{
		BucketSensitivitySettingsCR bucketSensitivitySettingsCR =
			BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_24 (
				bucketNumber
			);

		try
		{
			return null == bucketSensitivitySettingsCR ? null : new BucketVegaSettingsCR (
				TenorRiskWeightMap (
					CRNQSystemics24.VEGA_RISK_WEIGHT
				),
				bucketSensitivitySettingsCR.intraFamilyCrossTenorCorrelation(),
				bucketSensitivitySettingsCR.extraFamilyCrossTenorCorrelation(),
				CRThresholdContainer24.NonQualifyingThreshold (
					bucketNumber
				).vega(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				1.,
				bucketSensitivitySettingsCR.tenorRiskWeight()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketVegaSettingsCR Constructor
	 * 
	 * @param tenorVegaRiskWeight The Tenor Vega Risk Weight Map
	 * @param sameIssuerSeniorityCorrelation Same Issuer/Seniority Correlation
	 * @param differentIssuerSeniorityCorrelation Different Issuer/Seniority Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * @param vegaScaler The Vega Scaler
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * @param tenorDeltaRiskWeight The Credit Tenor Delta Risk Weight
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettingsCR (
		final Map<String, Double> tenorVegaRiskWeight,
		final double sameIssuerSeniorityCorrelation,
		final double differentIssuerSeniorityCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final Map<String, Double> tenorDeltaRiskWeight)
		throws Exception
	{
		super (
			tenorVegaRiskWeight,
			sameIssuerSeniorityCorrelation,
			differentIssuerSeniorityCorrelation,
			concentrationThreshold
		);

		if (!NumberUtil.IsValid (
				_vegaScaler = vegaScaler
			) ||
			!NumberUtil.IsValid (
				_historicalVolatilityRatio = historicalVolatilityRatio
			) ||
			null == (_tenorDeltaRiskWeight = tenorDeltaRiskWeight)
		)
		{
			throw new Exception (
				"BucketVegaSettingsIR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Vega Scaler
	 * 
	 * @return The Vega Scaler
	 */

	public double vegaScaler()
	{
		return _vegaScaler;
	}

	/**
	 * Retrieve the Historical Volatility Ratio
	 * 
	 * @return The Historical Volatility Ratio
	 */

	public double historicalVolatilityRatio()
	{
		return _historicalVolatilityRatio;
	}

	/**
	 * Retrieve the Tenor Delta Risk Weight
	 * 
	 * @return The Tenor Delta Risk Weight
	 */

	public Map<String, Double> tenorDeltaRiskWeight()
	{
		return _tenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the Tenor Vega Risk Weight
	 * 
	 * @return The Tenor Vega Risk Weight
	 */

	public Map<String, Double> tenorVegaRiskWeight()
	{
		return super.tenorRiskWeight();
	}

	@Override public Map<String, Double> tenorRiskWeight()
	{
		Map<String, Double> tenorVegaRiskWeight = tenorVegaRiskWeight();

		Map<String, Double> tenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> tenorVegaRiskWeightEntry : tenorVegaRiskWeight.entrySet())
		{
			String tenor = tenorVegaRiskWeightEntry.getKey();

			if (!tenorVegaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			tenorRiskWeight.put (
				tenor,
				tenorVegaRiskWeightEntry.getValue() * _tenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler * _historicalVolatilityRatio
			);
		}

		return tenorRiskWeight;
	}
}
