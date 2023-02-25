
package org.drip.simm.product;

import java.util.Map;

import org.drip.numerical.common.NumberUtil;
import org.drip.simm.margin.BucketAggregateIR;
import org.drip.simm.margin.RiskFactorAggregateIR;
import org.drip.simm.margin.SensitivityAggregateIR;
import org.drip.simm.parameters.BucketCurvatureSettingsIR;
import org.drip.simm.parameters.BucketSensitivitySettingsIR;

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
 * <i>BucketSensitivityIR</i> holds the ISDA SIMM Risk Factor Tenor Bucket Sensitivities across IR Factor Sub
 * Curves. USD Exposures enhanced with the USD specific Sub-Curve Factors - PRIME and MUNICIPAL. The
 * References are:
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

public class BucketSensitivityIR
{
	private RiskFactorTenorSensitivity _oisTenorSensitivity = null;
	private RiskFactorTenorSensitivity _primeTenorSensitivity = null;
	private RiskFactorTenorSensitivity _libor1MTenorSensitivity = null;
	private RiskFactorTenorSensitivity _libor3MTenorSensitivity = null;
	private RiskFactorTenorSensitivity _libor6MTenorSensitivity = null;
	private RiskFactorTenorSensitivity _libor12MTenorSensitivity = null;
	private RiskFactorTenorSensitivity _municipalTenorSensitivity = null;

	private BucketAggregateIR linearAggregate (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		RiskFactorAggregateIR riskFactorAggregate = curveAggregate (
			bucketSensitivitySettings
		);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		SensitivityAggregateIR sensitivityAggregate = riskFactorAggregate.linearMargin (
			bucketSensitivitySettings
		);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new BucketAggregateIR (
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

	private BucketAggregateIR curvatureAggregate (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		RiskFactorAggregateIR riskFactorAggregate = curveAggregate (
			bucketSensitivitySettings
		);

		if (null == riskFactorAggregate)
		{
			return null;
		}

		SensitivityAggregateIR sensitivityAggregate = riskFactorAggregate.curvatureMargin (
			bucketSensitivitySettings
		);

		if (null == sensitivityAggregate)
		{
			return null;
		}

		try
		{
			return new BucketAggregateIR (
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
	 * Generate a Standard Instance of BucketSensitivityIR from the Tenor Sensitivity Maps
	 * 
	 * @param oisTenorSensitivity OIS Tenor Sensitivity Map
	 * @param libor1MTenorSensitivity LIBOR1M Tenor Sensitivity Map
	 * @param libor3MTenorSensitivity LIBOR3M Tenor Sensitivity Map
	 * @param libor6MTenorSensitivity LIBOR6M Tenor Sensitivity Map
	 * @param libor12MTenorSensitivity LIBOR 12M Tenor Sensitivity Map
	 * @param primeTenorSensitivity Prime Tenor Sensitivity Map
	 * @param municipalTenorSensitivity Municipal Tenor Sensitivity Map
	 * 
	 * @return Standard Instance of BucketSensitivityIR from the Tenor Sensitivity Maps
	 */

	public static final BucketSensitivityIR Standard (
		final Map<String, Double> oisTenorSensitivity,
		final Map<String, Double> libor1MTenorSensitivity,
		final Map<String, Double> libor3MTenorSensitivity,
		final Map<String, Double> libor6MTenorSensitivity,
		final Map<String, Double> libor12MTenorSensitivity,
		final Map<String, Double> primeTenorSensitivity,
		final Map<String, Double> municipalTenorSensitivity)
	{
		try
		{
			return new BucketSensitivityIR (
				new RiskFactorTenorSensitivity (
					oisTenorSensitivity
				),
				new RiskFactorTenorSensitivity (
					libor1MTenorSensitivity
				),
				new RiskFactorTenorSensitivity (
					libor3MTenorSensitivity
				),
				new RiskFactorTenorSensitivity (
					libor6MTenorSensitivity
				),
				new RiskFactorTenorSensitivity (
					libor12MTenorSensitivity
				),
				new RiskFactorTenorSensitivity (
					primeTenorSensitivity
				),
				new RiskFactorTenorSensitivity (
					municipalTenorSensitivity
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivityIR Constructor
	 * 
	 * @param oisTenorSensitivity The OIS Risk Factor Tenor Sensitivity
	 * @param libor1MTenorSensitivity The LIBOR1M Risk Factor Tenor Sensitivity
	 * @param libor3MTenorSensitivity The LIBOR3M Risk Factor Tenor Sensitivity
	 * @param libor6MTenorSensitivity The LIBOR6M Risk Factor Tenor Delta Sensitivity
	 * @param libor12MTenorSensitivity The LIBOR12M Risk Factor Tenor Sensitivity
	 * @param primeTenorSensitivity The PRIME Risk Factor Tenor Sensitivity
	 * @param municipalTenorSensitivity The MUNICIPAL Risk Factor Tenor Sensitivity
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivityIR (
		final RiskFactorTenorSensitivity oisTenorSensitivity,
		final RiskFactorTenorSensitivity libor1MTenorSensitivity,
		final RiskFactorTenorSensitivity libor3MTenorSensitivity,
		final RiskFactorTenorSensitivity libor6MTenorSensitivity,
		final RiskFactorTenorSensitivity libor12MTenorSensitivity,
		final RiskFactorTenorSensitivity primeTenorSensitivity,
		final RiskFactorTenorSensitivity municipalTenorSensitivity)
		throws Exception
	{
		if (null == (_oisTenorSensitivity = oisTenorSensitivity) ||
			null == (_libor1MTenorSensitivity = libor1MTenorSensitivity) ||
			null == (_libor3MTenorSensitivity = libor3MTenorSensitivity) ||
			null == (_libor6MTenorSensitivity = libor6MTenorSensitivity) ||
			null == (_libor12MTenorSensitivity = libor12MTenorSensitivity) ||
			null == (_primeTenorSensitivity = primeTenorSensitivity) ||
			null == (_municipalTenorSensitivity = municipalTenorSensitivity)
		)
		{
			throw new Exception (
				"BucketSensitivityIR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the OIS Risk Factor Tenor Sensitivity
	 * 
	 * @return The OIS Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity oisTenorSensitivity()
	{
		return _oisTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR1M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR1M Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity libor1MTenorSensitivity()
	{
		return _libor1MTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR3M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR3M Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity libor3MTenorSensitivity()
	{
		return _libor3MTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR6M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR6M Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity libor6MTenorSensitivity()
	{
		return _libor6MTenorSensitivity;
	}

	/**
	 * Retrieve the LIBOR12M Risk Factor Tenor Sensitivity
	 * 
	 * @return The LIBOR12M Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity libor12MTenorSensitivity()
	{
		return _libor12MTenorSensitivity;
	}

	/**
	 * Retrieve the PRIME Risk Factor Tenor Sensitivity
	 * 
	 * @return The PRIME Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity primeTenorSensitivity()
	{
		return _primeTenorSensitivity;
	}

	/**
	 * Retrieve the MUNICIPAL Risk Factor Tenor Sensitivity
	 * 
	 * @return The MUNICIPAL Risk Factor Tenor Sensitivity
	 */

	public RiskFactorTenorSensitivity municipalTenorSensitivity()
	{
		return _municipalTenorSensitivity;
	}

	/**
	 * Generate the Cumulative Tenor Sensitivity
	 * 
	 * @return The Cumulative Tenor Sensitivity
	 */

	public double cumulativeTenorSensitivity()
	{
		return _oisTenorSensitivity.cumulative() +
			_libor1MTenorSensitivity.cumulative() +
			_libor3MTenorSensitivity.cumulative() +
			_libor6MTenorSensitivity.cumulative() +
			_libor12MTenorSensitivity.cumulative() +
			_primeTenorSensitivity.cumulative() +
			_municipalTenorSensitivity.cumulative();
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
				"BucketSensitivityIR::sensitivityConcentrationRiskFactor => Invalid Inputs"
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
	 * Generate the OIS Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The OIS Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> oisTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> oisTenorMargin = _oisTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.oisTenorRiskWeight()
		);

		if (null == oisTenorMargin)
		{
			return oisTenorMargin;
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

		for (Map.Entry<String, Double> oisTenorMarginEntry : oisTenorMargin.entrySet())
		{
			String tenor = oisTenorMarginEntry.getKey();

			oisTenorMargin.put (
				tenor,
				oisTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return oisTenorMargin;
	}

	/**
	 * Generate the LIBOR1M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> libor1MTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> libor1MTenorMargin = _libor1MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor1MTenorRiskWeight()
		);

		if (null == libor1MTenorMargin)
		{
			return libor1MTenorMargin;
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

		for (Map.Entry<String, Double> libor1MTenorMarginEntry : libor1MTenorMargin.entrySet())
		{
			String tenor = libor1MTenorMarginEntry.getKey();

			libor1MTenorMargin.put (
				tenor,
				libor1MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return libor1MTenorMargin;
	}

	/**
	 * Generate the LIBOR3M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> libor3MTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> libor3MTenorMargin = _libor3MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor3MTenorRiskWeight()
		);

		if (null == libor3MTenorMargin)
		{
			return libor3MTenorMargin;
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

		for (Map.Entry<String, Double> libor3MTenorMarginEntry : libor3MTenorMargin.entrySet())
		{
			String tenor = libor3MTenorMarginEntry.getKey();

			libor3MTenorMargin.put (
				tenor,
				libor3MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return libor3MTenorMargin;
	}

	/**
	 * Generate the LIBOR6M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> libor6MTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> libor6MTenorMargin = _libor6MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor6MTenorRiskWeight()
		);

		if (null == libor6MTenorMargin)
		{
			return libor6MTenorMargin;
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

		for (Map.Entry<String, Double> libor6MTenorMarginEntry : libor6MTenorMargin.entrySet())
		{
			String tenor = libor6MTenorMarginEntry.getKey();

			libor6MTenorMargin.put (
				tenor,
				libor6MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return libor6MTenorMargin;
	}

	/**
	 * Generate the LIBOR12M Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR12M Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> libor12MTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> libor12MTenorMargin = _libor12MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor12MTenorRiskWeight()
		);

		if (null == libor12MTenorMargin)
		{
			return libor12MTenorMargin;
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

		for (Map.Entry<String, Double> libor12MTenorMarginEntry : libor12MTenorMargin.entrySet())
		{
			String tenor = libor12MTenorMarginEntry.getKey();

			libor12MTenorMargin.put (
				tenor,
				libor12MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return libor12MTenorMargin;
	}

	/**
	 * Generate the PRIME Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The PRIME Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> primeTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> primeTenorMargin = _primeTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.primeTenorRiskWeight()
		);

		if (null == primeTenorMargin)
		{
			return primeTenorMargin;
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

		for (Map.Entry<String, Double> primeTenorMarginEntry : primeTenorMargin.entrySet())
		{
			String tenor = primeTenorMarginEntry.getKey();

			primeTenorMargin.put (
				tenor,
				primeTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return primeTenorMargin;
	}

	/**
	 * Generate the MUNICIPAL Tenor Sensitivity Margin Map
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The MUNICIPAL Tenor Sensitivity Margin Map
	 */

	public Map<String, Double> municipalTenorMargin (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> municipalTenorMargin = _municipalTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.municipalTenorRiskWeight()
		);

		if (null == municipalTenorMargin)
		{
			return municipalTenorMargin;
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

		for (Map.Entry<String, Double> municipalTenorMarginEntry : municipalTenorMargin.entrySet())
		{
			String tenor = municipalTenorMarginEntry.getKey();

			municipalTenorMargin.put (
				tenor,
				municipalTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		return municipalTenorMargin;
	}

	/**
	 * Generate the IR Margin Factor Curve Tenor Aggregate
	 * 
	 * @param bucketSensitivitySettings The Bucket Tenor Sensitivity Settings
	 * 
	 * @return The IR Margin Factor Curve Tenor Aggregate
	 */

	public RiskFactorAggregateIR curveAggregate (
		final BucketSensitivitySettingsIR bucketSensitivitySettings)
	{
		if (null == bucketSensitivitySettings)
		{
			return null;
		}

		Map<String, Double> oisTenorMargin = _oisTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.oisTenorRiskWeight()
		);

		Map<String, Double> libor1MTenorMargin = _libor1MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor1MTenorRiskWeight()
		);

		Map<String, Double> libor3MTenorMargin = _libor3MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor3MTenorRiskWeight()
		);

		Map<String, Double> libor6MTenorMargin = _libor6MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor6MTenorRiskWeight()
		);

		Map<String, Double> libor12MTenorMargin = _libor12MTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.libor12MTenorRiskWeight()
		);

		Map<String, Double> primeTenorMargin = _primeTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.primeTenorRiskWeight()
		);

		Map<String, Double> municipalTenorMargin = _municipalTenorSensitivity.sensitivityMargin (
			bucketSensitivitySettings.municipalTenorRiskWeight()
		);

		if (null == oisTenorMargin ||
			null == libor1MTenorMargin ||
			null == libor3MTenorMargin ||
			null == libor6MTenorMargin ||
			null == libor12MTenorMargin ||
			null == primeTenorMargin ||
			null == municipalTenorMargin)
		{
			return null;
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

		for (Map.Entry<String, Double> municipalTenorMarginEntry : municipalTenorMargin.entrySet())
		{
			String tenor = municipalTenorMarginEntry.getKey();

			oisTenorMargin.put (
				tenor,
				oisTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);

			libor1MTenorMargin.put (
				tenor,
				libor1MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);

			libor3MTenorMargin.put (
				tenor,
				libor3MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);

			libor6MTenorMargin.put (
				tenor,
				libor6MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);

			libor12MTenorMargin.put (
				tenor,
				libor12MTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);

			primeTenorMargin.put (
				tenor,
				primeTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);

			municipalTenorMargin.put (
				tenor,
				municipalTenorMargin.get (
					tenor
				) * sensitivityConcentrationRiskFactor
			);
		}

		try
		{
			return new RiskFactorAggregateIR (
				oisTenorMargin,
				libor1MTenorMargin,
				libor3MTenorMargin,
				libor6MTenorMargin,
				libor12MTenorMargin,
				primeTenorMargin,
				municipalTenorMargin,
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
	 * Generate the Bucket IR Sensitivity Margin Aggregate
	 * 
	 * @param bucketSensitivitySettingsIR The IR Bucket Sensitivity Settings
	 * 
	 * @return The Bucket IR Sensitivity Margin Aggregate
	 */

	public BucketAggregateIR aggregate (
		final BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
	{
		if (null == bucketSensitivitySettingsIR)
		{
			return null;
		}

		return bucketSensitivitySettingsIR instanceof BucketCurvatureSettingsIR ? curvatureAggregate (
			bucketSensitivitySettingsIR
		) : linearAggregate (
			bucketSensitivitySettingsIR
		);
	}
}
