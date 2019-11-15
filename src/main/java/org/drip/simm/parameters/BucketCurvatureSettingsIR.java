
package org.drip.simm.parameters;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>BucketCurvatureSettingsIR</i> holds the Curvature Risk Weights, Concentration Thresholds, and
 * Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor. The References are:
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

public class BucketCurvatureSettingsIR extends org.drip.simm.parameters.BucketVegaSettingsIR
{
	private java.util.Map<java.lang.String, java.lang.Double> _tenorScalingFactorMap = null;

	/**
	 * Generate the ISDA 2.0 Standard BucketCurvatureSettingsIR
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.0 Standard BucketCurvatureSettingsIR
	 */

	public static BucketCurvatureSettingsIR ISDA_20 (
		final java.lang.String currency)
	{
		org.drip.simm.parameters.BucketVegaSettingsIR bucketVegaSettingsIR =
			org.drip.simm.parameters.BucketVegaSettingsIR.ISDA_20 (currency);

		if (null == bucketVegaSettingsIR)
		{
			return null;
		}

		org.drip.function.definition.R1ToR1 r1ToR1CurvatureTenorScaler =
			org.drip.function.r1tor1.ISDABucketCurvatureTenorScaler.Standard();

		java.util.Map<java.lang.String, java.lang.Double> tenorScalingFactorMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		try
		{
			tenorScalingFactorMap.put (
				"2W",
				r1ToR1CurvatureTenorScaler.evaluate (14.)
			);

			tenorScalingFactorMap.put (
				"1M",
				r1ToR1CurvatureTenorScaler.evaluate (30.)
			);

			tenorScalingFactorMap.put (
				"3M",
				r1ToR1CurvatureTenorScaler.evaluate (91.)
			);

			tenorScalingFactorMap.put (
				"6M",
				r1ToR1CurvatureTenorScaler.evaluate (183.)
			);

			tenorScalingFactorMap.put (
				"1Y",
				r1ToR1CurvatureTenorScaler.evaluate (365.)
			);

			tenorScalingFactorMap.put (
				"2Y",
				r1ToR1CurvatureTenorScaler.evaluate (731.)
			);

			tenorScalingFactorMap.put (
				"3Y",
				r1ToR1CurvatureTenorScaler.evaluate (1096.)
			);

			tenorScalingFactorMap.put (
				"5Y",
				r1ToR1CurvatureTenorScaler.evaluate (1826.)
			);

			tenorScalingFactorMap.put (
				"10Y",
				r1ToR1CurvatureTenorScaler.evaluate (3652.)
			);

			tenorScalingFactorMap.put (
				"15Y",
				r1ToR1CurvatureTenorScaler.evaluate (5479.)
			);

			tenorScalingFactorMap.put (
				"20Y",
				r1ToR1CurvatureTenorScaler.evaluate (7305.)
			);

			tenorScalingFactorMap.put (
				"30Y",
				r1ToR1CurvatureTenorScaler.evaluate (10957.)
			);

			return new BucketCurvatureSettingsIR (
				bucketVegaSettingsIR.oisTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor1MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor3MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor6MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor12MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.primeTenorVegaRiskWeight(),
				bucketVegaSettingsIR.municipalTenorVegaRiskWeight(),
				bucketVegaSettingsIR.crossTenorCorrelation(),
				bucketVegaSettingsIR.crossCurveCorrelation(),
				bucketVegaSettingsIR.concentrationThreshold(),
				bucketVegaSettingsIR.vegaScaler(),
				bucketVegaSettingsIR.historicalVolatilityRatio(),
				bucketVegaSettingsIR.oisTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor1MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor3MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor6MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor12MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.primeTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.municipalTenorDeltaRiskWeight(),
				tenorScalingFactorMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the ISDA 2.1 Standard BucketCurvatureSettingsIR
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.1 Standard BucketCurvatureSettingsIR
	 */

	public static BucketCurvatureSettingsIR ISDA_21 (
		final java.lang.String currency)
	{
		org.drip.simm.parameters.BucketVegaSettingsIR bucketVegaSettingsIR =
			org.drip.simm.parameters.BucketVegaSettingsIR.ISDA_21 (currency);

		if (null == bucketVegaSettingsIR)
		{
			return null;
		}

		org.drip.function.definition.R1ToR1 r1ToR1CurvatureTenorScaler =
			org.drip.function.r1tor1.ISDABucketCurvatureTenorScaler.Standard();

		java.util.Map<java.lang.String, java.lang.Double> tenorScalingFactorMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		try
		{
			tenorScalingFactorMap.put (
				"2W",
				r1ToR1CurvatureTenorScaler.evaluate (14.)
			);

			tenorScalingFactorMap.put (
				"1M",
				r1ToR1CurvatureTenorScaler.evaluate (30.)
			);

			tenorScalingFactorMap.put (
				"3M",
				r1ToR1CurvatureTenorScaler.evaluate (91.)
			);

			tenorScalingFactorMap.put (
				"6M",
				r1ToR1CurvatureTenorScaler.evaluate (183.)
			);

			tenorScalingFactorMap.put (
				"1Y",
				r1ToR1CurvatureTenorScaler.evaluate (365.)
			);

			tenorScalingFactorMap.put (
				"2Y",
				r1ToR1CurvatureTenorScaler.evaluate (731.)
			);

			tenorScalingFactorMap.put (
				"3Y",
				r1ToR1CurvatureTenorScaler.evaluate (1096.)
			);

			tenorScalingFactorMap.put (
				"5Y",
				r1ToR1CurvatureTenorScaler.evaluate (1826.)
			);

			tenorScalingFactorMap.put (
				"10Y",
				r1ToR1CurvatureTenorScaler.evaluate (3652.)
			);

			tenorScalingFactorMap.put (
				"15Y",
				r1ToR1CurvatureTenorScaler.evaluate (5479.)
			);

			tenorScalingFactorMap.put (
				"20Y",
				r1ToR1CurvatureTenorScaler.evaluate (7305.)
			);

			tenorScalingFactorMap.put (
				"30Y",
				r1ToR1CurvatureTenorScaler.evaluate (10957.)
			);

			return new BucketCurvatureSettingsIR (
				bucketVegaSettingsIR.oisTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor1MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor3MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor6MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.libor12MTenorVegaRiskWeight(),
				bucketVegaSettingsIR.primeTenorVegaRiskWeight(),
				bucketVegaSettingsIR.municipalTenorVegaRiskWeight(),
				bucketVegaSettingsIR.crossTenorCorrelation(),
				bucketVegaSettingsIR.crossCurveCorrelation(),
				bucketVegaSettingsIR.concentrationThreshold(),
				bucketVegaSettingsIR.vegaScaler(),
				bucketVegaSettingsIR.historicalVolatilityRatio(),
				bucketVegaSettingsIR.oisTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor1MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor3MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor6MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.libor12MTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.primeTenorDeltaRiskWeight(),
				bucketVegaSettingsIR.municipalTenorDeltaRiskWeight(),
				tenorScalingFactorMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketCurvatureSettingsIR Constructor
	 * 
	 * @param oisTenorVegaRiskWeight The OIS Tenor Vega Risk Weight
	 * @param libor1MTenorVegaRiskWeight The LIBOR 1M Tenor Vega Risk Weight
	 * @param libor3MTenorVegaRiskWeight The LIBOR 3M Tenor Vega Risk Weight
	 * @param libor6MTenorVegaRiskWeight The LIBOR 6M Tenor Vega Risk Weight
	 * @param libor12MTenorVegaRiskWeight The LIBOR 12M Tenor Vega Risk Weight
	 * @param primeTenorVegaRiskWeight The PRIME Tenor Vega Risk Weight
	 * @param municipalTenorVegaRiskWeight The MUNICIPAL Tenor Vega Risk Weight
	 * @param crossTenorCorrelation Single Curve Cross-Tenor Correlation
	 * @param crossCurveCorrelation Cross Curve Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * @param vegaScaler The Vega Scaler
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * @param oisTenorDeltaRiskWeight The OIS Tenor Delta Risk Weight
	 * @param libor1MTenorDeltaRiskWeight The LIBOR 1M Tenor Delta Risk Weight
	 * @param libor3MTenorDeltaRiskWeight The LIBOR 3M Tenor Delta Risk Weight
	 * @param libor6MTenorDeltaRiskWeight The LIBOR 6M Tenor Delta Risk Weight
	 * @param libor12MTenorDeltaRiskWeight The LIBOR 12M Tenor Delta Risk Weight
	 * @param primeTenorDeltaRiskWeight The PRIME Tenor Delta Risk Weight
	 * @param municipalTenorDeltaRiskWeight The MUNICIPAL Tenor Delta Risk Weight
	 * @param tenorScalingFactorMap The Tenor Scaling Factor Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketCurvatureSettingsIR (
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeight,
		final org.drip.measure.stochastic.LabelCorrelation crossTenorCorrelation,
		final double crossCurveCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> tenorScalingFactorMap)
		throws java.lang.Exception
	{
		super (
			oisTenorVegaRiskWeight,
			libor1MTenorVegaRiskWeight,
			libor3MTenorVegaRiskWeight,
			libor6MTenorVegaRiskWeight,
			libor12MTenorVegaRiskWeight,
			primeTenorVegaRiskWeight,
			municipalTenorVegaRiskWeight,
			crossTenorCorrelation,
			crossCurveCorrelation,
			concentrationThreshold,
			vegaScaler,
			historicalVolatilityRatio,
			oisTenorDeltaRiskWeight,
			libor1MTenorDeltaRiskWeight,
			libor3MTenorDeltaRiskWeight,
			libor6MTenorDeltaRiskWeight,
			libor12MTenorDeltaRiskWeight,
			primeTenorDeltaRiskWeight,
			municipalTenorDeltaRiskWeight
		);

		if (null == (_tenorScalingFactorMap = tenorScalingFactorMap) || 0 == _tenorScalingFactorMap.size())
		{
			throw new java.lang.Exception ("BucketVegaSettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Tenor Scaling Factor Map
	 * 
	 * @return The Tenor Scaling Factor Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorScalingFactorMap()
	{
		return _tenorScalingFactorMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> oisTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> oisTenorVegaRiskWeight =
			super.oisTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> oisTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisTenorVegaRiskWeightEntry :
			oisTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = oisTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			oisTenorRiskWeight.put (
				tenor,
				oisTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return oisTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeight =
			super.libor1MTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeightEntry :
			libor1MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor1MTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			libor1MTenorRiskWeight.put (
				tenor,
				libor1MTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return libor1MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeight =
			super.libor3MTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeightEntry :
			libor3MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor3MTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			libor3MTenorRiskWeight.put (
				tenor,
				libor3MTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return libor3MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeight =
			super.libor6MTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeightEntry :
			libor6MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor6MTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			libor6MTenorRiskWeight.put (
				tenor,
				libor6MTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return libor6MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeight =
			super.libor12MTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeightEntry :
			libor12MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor12MTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			libor12MTenorRiskWeight.put (
				tenor,
				libor12MTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return libor12MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> primeTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> primeTenorVegaRiskWeight =
			super.primeTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> primeTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeTenorVegaRiskWeightEntry :
			primeTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = primeTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			primeTenorRiskWeight.put (
				tenor,
				primeTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return primeTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> municipalTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeight =
			super.municipalTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> municipalTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeightEntry :
			municipalTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = municipalTenorVegaRiskWeightEntry.getKey();

			if (!_tenorScalingFactorMap.containsKey (tenor))
			{
				return null;
			}

			municipalTenorRiskWeight.put (
				tenor,
				municipalTenorVegaRiskWeightEntry.getValue() * _tenorScalingFactorMap.get (tenor)
			);
		}

		return municipalTenorRiskWeight;
	}
}
