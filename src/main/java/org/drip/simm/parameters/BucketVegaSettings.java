
package org.drip.simm.parameters;

import java.util.Map;

import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.commodity.CTBucket;
import org.drip.simm.commodity.CTRiskThresholdContainer20;
import org.drip.simm.commodity.CTRiskThresholdContainer21;
import org.drip.simm.commodity.CTRiskThresholdContainer24;
import org.drip.simm.commodity.CTSettingsContainer20;
import org.drip.simm.commodity.CTSettingsContainer21;
import org.drip.simm.commodity.CTSettingsContainer24;
import org.drip.simm.commodity.CTSystemics20;
import org.drip.simm.commodity.CTSystemics21;
import org.drip.simm.commodity.CTSystemics24;
import org.drip.simm.equity.EQBucket;
import org.drip.simm.equity.EQRiskThresholdContainer20;
import org.drip.simm.equity.EQRiskThresholdContainer21;
import org.drip.simm.equity.EQRiskThresholdContainer24;
import org.drip.simm.equity.EQSettingsContainer20;
import org.drip.simm.equity.EQSettingsContainer21;
import org.drip.simm.equity.EQSettingsContainer24;
import org.drip.simm.equity.EQSystemics20;
import org.drip.simm.equity.EQSystemics21;
import org.drip.simm.equity.EQSystemics24;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.fx.FXRiskThresholdContainer21;
import org.drip.simm.fx.FXRiskThresholdContainer24;
import org.drip.simm.fx.FXSystemics20;
import org.drip.simm.fx.FXSystemics21;
import org.drip.simm.fx.FXSystemics24;
import org.drip.simm.fx.FXVolatilityGroupContainer24;

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
 * <i>BucketVegaSettings</i> holds the Settings that govern the Generation of the ISDA SIMM Single Bucket
 * Vega Sensitivities. The References are:
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
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
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

public class BucketVegaSettings
	extends BucketSensitivitySettings
{
	private double _impliedVolatility = Double.NaN;
	private double _historicalVolatilityRatio = Double.NaN;

	/**
	 * Retrieve the ISDA 2.0 Equity Vega Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The ISDA 2.0 Equity Vega Settings
	 */

	public static BucketVegaSettings ISDA_EQ_20 (
		final int bucketIndex)
	{
		EQBucket equityBucket = EQSettingsContainer20.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == equityBucket ? null : new BucketVegaSettings (
				equityBucket.vegaRiskWeight() * equityBucket.deltaRiskWeight(),
				EQRiskThresholdContainer20.DeltaVegaThresholdMap().get (
					bucketIndex
				).vega(),
				equityBucket.memberCorrelation(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				EQSystemics20.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.1 Equity Vega Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The ISDA 2.1 Equity Vega Settings
	 */

	public static BucketVegaSettings ISDA_EQ_21 (
		final int bucketIndex)
	{
		EQBucket equityBucket = EQSettingsContainer21.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == equityBucket ? null : new BucketVegaSettings (
				equityBucket.vegaRiskWeight() * equityBucket.deltaRiskWeight(),
				EQRiskThresholdContainer21.DeltaVegaThresholdMap().get (
					bucketIndex
				).vega(),
				equityBucket.memberCorrelation(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				EQSystemics21.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the ISDA 2.4 Equity Vega Settings
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The ISDA 2.4 Equity Vega Settings
	 */

	public static BucketVegaSettings ISDA_EQ_24 (
		final int bucketIndex)
	{
		EQBucket equityBucket = EQSettingsContainer24.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == equityBucket ? null : new BucketVegaSettings (
				equityBucket.vegaRiskWeight() * equityBucket.deltaRiskWeight(),
				EQRiskThresholdContainer24.DeltaVegaThresholdMap().get (
					bucketIndex
				).vega(),
				equityBucket.memberCorrelation(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				EQSystemics24.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 Commodity Vega Settings for the specified Bucket
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The Standard ISDA 2.0 Commodity Vega Settings for the specified Bucket
	 */

	public static BucketVegaSettings ISDA_CT_20 (
		final int bucketIndex)
	{
		CTBucket commodityBucket = CTSettingsContainer20.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == commodityBucket ? null : new BucketVegaSettings (
				CTSystemics20.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				CTRiskThresholdContainer20.DeltaVegaThresholdMap().get (
					bucketIndex
				).vega(),
				commodityBucket.memberCorrelation(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				CTSystemics20.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 Commodity Vega Settings for the specified Bucket
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The Standard ISDA 2.1 Commodity Vega Settings for the specified Bucket
	 */

	public static BucketVegaSettings ISDA_CT_21 (
		final int bucketIndex)
	{
		CTBucket commodityBucket = CTSettingsContainer21.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == commodityBucket ? null : new BucketVegaSettings (
				CTSystemics21.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				CTRiskThresholdContainer21.DeltaVegaThresholdMap().get (
					bucketIndex
				).vega(),
				commodityBucket.memberCorrelation(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				CTSystemics21.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.4 Commodity Vega Settings for the specified Bucket
	 * 
	 * @param bucketIndex The Bucket Index
	 * 
	 * @return The Standard ISDA 2.4 Commodity Vega Settings for the specified Bucket
	 */

	public static BucketVegaSettings ISDA_CT_24 (
		final int bucketIndex)
	{
		CTBucket commodityBucket = CTSettingsContainer24.BucketMap().get (
			bucketIndex
		);

		try
		{
			return null == commodityBucket ? null : new BucketVegaSettings (
				CTSystemics24.VEGA_RISK_WEIGHT * commodityBucket.deltaRiskWeight(),
				CTRiskThresholdContainer24.DeltaVegaThresholdMap().get (
					bucketIndex
				).vega(),
				commodityBucket.memberCorrelation(),
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				CTSystemics24.HISTORICAL_VOLATILITY_RATIO
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.0 Bucket FX Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * 
	 * @return The Standard ISDA 2.0 Bucket FX Settings
	 */

	public static BucketVegaSettings ISDA_FX_20 (
		final String vegaCategory)
	{
		Map<String, Double> fxConcentrationCategoryVegaMap = FXRiskThresholdContainer20.CategoryVegaMap();

		try
		{
			return fxConcentrationCategoryVegaMap.containsKey (
				vegaCategory
			) ? new BucketVegaSettings (
				FXSystemics20.VEGA_RISK_WEIGHT * FXSystemics20.DELTA_RISK_WEIGHT,
				fxConcentrationCategoryVegaMap.get (
					vegaCategory
				),
				FXSystemics20.CORRELATION,
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				FXSystemics20.HISTORICAL_VOLATILITY_RATIO
			) : null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.1 Bucket FX Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * 
	 * @return The Standard ISDA 2.1 Bucket FX Settings
	 */

	public static BucketVegaSettings ISDA_FX_21 (
		final String vegaCategory)
	{
		Map<String, Double> fxConcentrationCategoryVegaMap = FXRiskThresholdContainer21.CategoryVegaMap();

		try
		{
			return fxConcentrationCategoryVegaMap.containsKey (
				vegaCategory
			) ? new BucketVegaSettings (
				FXSystemics21.VEGA_RISK_WEIGHT * FXSystemics21.DELTA_RISK_WEIGHT,
				fxConcentrationCategoryVegaMap.get (
					vegaCategory
				),
				FXSystemics21.CORRELATION,
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				FXSystemics21.HISTORICAL_VOLATILITY_RATIO
			) : null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard ISDA 2.4 Bucket FX Settings
	 * 
	 * @param vegaCategory The Vega Category
	 * @param givenCurrency Given Currency
	 * @param calculationCurrency Calculation Currency
	 * 
	 * @return The Standard ISDA 2.4 Bucket FX Settings
	 */

	public static BucketVegaSettings ISDA_FX_24 (
		final String vegaCategory,
		final String givenCurrency,
		final String calculationCurrency)
	{
		Map<String, Double> fxConcentrationCategoryVegaMap = FXRiskThresholdContainer24.CategoryVegaMap();

		try
		{
			return fxConcentrationCategoryVegaMap.containsKey (
				vegaCategory
			) ? new BucketVegaSettings (
				FXSystemics24.VEGA_RISK_WEIGHT * FXVolatilityGroupContainer24.RiskWeight (
					givenCurrency,
					calculationCurrency
				),
				fxConcentrationCategoryVegaMap.get (
					vegaCategory
				),
				FXSystemics24.VOLATILITY_CURVATURE_CORRELATION,
				Math.sqrt (
					365. / 14.
				) / NormalQuadrature.InverseCDF (
					0.99
				),
				FXSystemics24.HISTORICAL_VOLATILITY_RATIO
			) : null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketVegaSettings Constructor
	 * 
	 * @param riskWeight The Vega Risk Weight
	 * @param concentrationFactor The Concentration Factor
	 * @param memberCorrelation The Member Correlation
	 * @param impliedVolatility The Implied Volatility
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettings (
		final double riskWeight,
		final double concentrationFactor,
		final double memberCorrelation,
		final double impliedVolatility,
		final double historicalVolatilityRatio)
		throws Exception
	{
		super (
			riskWeight,
			concentrationFactor,
			memberCorrelation
		);

		if (!NumberUtil.IsValid (
				_impliedVolatility = impliedVolatility
			) || 0. > _impliedVolatility ||
			!NumberUtil.IsValid (
				_historicalVolatilityRatio = historicalVolatilityRatio
			) || 0. > _historicalVolatilityRatio
		)
		{
			throw new Exception (
				"BucketVegaSettings Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Implied Volatility
	 * 
	 * @return The Implied Volatility
	 */

	public double impliedVolatility()
	{
		return _impliedVolatility;
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
	 * Retrieve the Raw Vega Risk Weight
	 * 
	 * @return The Raw Vega Risk Weight
	 */

	public double rawRiskWeight()
	{
		return super.riskWeight();
	}

	@Override public double riskWeight()
	{
		return super.riskWeight() * _impliedVolatility;
	}
}
