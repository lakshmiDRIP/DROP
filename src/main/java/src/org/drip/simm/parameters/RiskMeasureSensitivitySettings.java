
package org.drip.simm.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.simm.commodity.CTSettingsContainer20;
import org.drip.simm.commodity.CTSettingsContainer21;
import org.drip.simm.commodity.CTSettingsContainer24;
import org.drip.simm.equity.EQSettingsContainer20;
import org.drip.simm.equity.EQSettingsContainer21;
import org.drip.simm.equity.EQSettingsContainer24;
import org.drip.simm.fx.FXRiskThresholdContainer20;
import org.drip.simm.fx.FXRiskThresholdContainer21;
import org.drip.simm.fx.FXRiskThresholdContainer24;
import org.drip.simm.fx.FXSystemics20;
import org.drip.simm.fx.FXSystemics21;
import org.drip.simm.fx.FXSystemics24;

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
 * <i>RiskMeasureSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM
 * Bucket Sensitivities across Individual Risk Measure Buckets. The References are:
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

public class RiskMeasureSensitivitySettings
{
	private LabelCorrelation _crossBucketCorrelation = null;
	private Map<String, BucketSensitivitySettings> _bucketSettingsMap = null;

	/**
	 * Construct an ISDA 2.0 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_20()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer20.BucketMap().keySet())
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					BucketSensitivitySettings.ISDA_EQ_20 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_21()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer21.BucketMap().keySet())
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					BucketSensitivitySettings.ISDA_EQ_21 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.4 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.4 Equity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_DELTA_24()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer24.BucketMap().keySet())
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					BucketSensitivitySettings.ISDA_EQ_24 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				EQSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_20()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer20.BucketMap().keySet())
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					BucketVegaSettings.ISDA_EQ_20 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_21()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer21.BucketMap().keySet())
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					BucketVegaSettings.ISDA_EQ_21 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.4 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.4 Equity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_VEGA_24()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer24.BucketMap().keySet())
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					BucketVegaSettings.ISDA_EQ_24 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				EQSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_20 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer20.BucketMap().keySet())
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					BucketCurvatureSettings.ISDA_EQ_20 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				EQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_21 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer21.BucketMap().keySet())
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					BucketCurvatureSettings.ISDA_EQ_21 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.4 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.4 Equity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_EQ_CURVATURE_24 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : EQSettingsContainer24.BucketMap().keySet())
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					BucketCurvatureSettings.ISDA_EQ_24 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				EQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_20()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer20.BucketMap().keySet())
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					BucketSensitivitySettings.ISDA_CT_20 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_21()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer21.BucketMap().keySet())
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					BucketSensitivitySettings.ISDA_CT_21 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.4 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.4 Commodity DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_DELTA_24()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer24.BucketMap().keySet())
			{
				bucketDeltaSettingsMap.put (
					"" + bucketIndex,
					BucketSensitivitySettings.ISDA_CT_24 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				CTSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_20()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer20.BucketMap().keySet())
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					BucketVegaSettings.ISDA_CT_20 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_21()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer21.BucketMap().keySet())
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					BucketVegaSettings.ISDA_CT_21 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.4 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.4 Commodity VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_VEGA_24()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer24.BucketMap().keySet())
			{
				bucketVegaSettingsMap.put (
					"" + bucketIndex,
					BucketVegaSettings.ISDA_CT_24 (
						bucketIndex
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				CTSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_20 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer20.BucketMap().keySet())
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					BucketCurvatureSettings.ISDA_CT_20 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				CTSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.1 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_21 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer21.BucketMap().keySet())
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					BucketCurvatureSettings.ISDA_CT_21 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				CTSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.4 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.4 Commodity CURVATURE Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_CT_CURVATURE_24 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		try
		{
			for (int bucketIndex : CTSettingsContainer24.BucketMap().keySet())
			{
				bucketCurvatureSettingsMap.put (
					"" + bucketIndex,
					BucketCurvatureSettings.ISDA_CT_24 (
						bucketIndex,
						vegaDurationDays
					)
				);
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				CTSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ISDA 2.0 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_20()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<Integer, Double> fxConcentrationCategoryDeltaMap = FXRiskThresholdContainer20.CategoryDeltaMap();

		Set<Integer> fxConcentrationCategoryDeltaKey = fxConcentrationCategoryDeltaMap.keySet();

		List<String> deltaCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryDeltaCount = fxConcentrationCategoryDeltaKey.size();

		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryDeltaCount][fxConcentrationCategoryDeltaCount];

		try
		{
			for (int deltaCategoryIndex : fxConcentrationCategoryDeltaKey)
			{
				String deltaCategory = "" + deltaCategoryIndex;

				deltaCategoryList.add (
					deltaCategory
				);

				bucketDeltaSettingsMap.put (
					deltaCategory,
					BucketSensitivitySettings.ISDA_FX_20 (
						deltaCategoryIndex
					)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. : FXSystemics20.CORRELATION;
				}
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				new LabelCorrelation (
					deltaCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.1 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_21()
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<Integer, Double> fxConcentrationCategoryDeltaMap = FXRiskThresholdContainer21.CategoryDeltaMap();

		Set<Integer> fxConcentrationCategoryDeltaKey = fxConcentrationCategoryDeltaMap.keySet();

		List<String> deltaCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryDeltaCount = fxConcentrationCategoryDeltaKey.size();

		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryDeltaCount][fxConcentrationCategoryDeltaCount];

		try
		{
			for (int deltaCategoryIndex : fxConcentrationCategoryDeltaKey)
			{
				String deltaCategory = "" + deltaCategoryIndex;

				deltaCategoryList.add (
					deltaCategory
				);

				bucketDeltaSettingsMap.put (
					deltaCategory,
					BucketSensitivitySettings.ISDA_FX_21 (
						deltaCategoryIndex
					)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. : FXSystemics21.CORRELATION;
				}
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				new LabelCorrelation (
					deltaCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.4 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param givenCurrency Given Currency
	 * @param calculationCurrency Calculation Currency
	 * 
	 * @return ISDA 2.4 FX DELTA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_DELTA_24 (
		final String givenCurrency,
		final String calculationCurrency)
	{
		Map<String, BucketSensitivitySettings> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<Integer, Double> fxConcentrationCategoryDeltaMap = FXRiskThresholdContainer24.CategoryDeltaMap();

		Set<Integer> fxConcentrationCategoryDeltaKey = fxConcentrationCategoryDeltaMap.keySet();

		List<String> deltaCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryDeltaCount = fxConcentrationCategoryDeltaKey.size();

		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryDeltaCount][fxConcentrationCategoryDeltaCount];

		try
		{
			for (int deltaCategoryIndex : fxConcentrationCategoryDeltaKey)
			{
				String deltaCategory = "" + deltaCategoryIndex;

				deltaCategoryList.add (
					deltaCategory
				);

				bucketDeltaSettingsMap.put (
					deltaCategory,
					BucketSensitivitySettings.ISDA_FX_24 (
						deltaCategoryIndex,
						givenCurrency,
						calculationCurrency
					)
				);

				for (int categoryIndexInner : fxConcentrationCategoryDeltaKey)
				{
					crossBucketCorrelationMatrix[deltaCategoryIndex - 1][categoryIndexInner - 1] =
						deltaCategoryIndex == categoryIndexInner ? 1. :
						FXSystemics24.VOLATILITY_CURVATURE_CORRELATION;
				}
			}

			return new RiskMeasureSensitivitySettings (
				bucketDeltaSettingsMap,
				new LabelCorrelation (
					deltaCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.0 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.0 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_20()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<String, Double> fxConcentrationCategoryVegaMap = FXRiskThresholdContainer20.CategoryVegaMap();

		Set<String> fxConcentrationCategoryVegaKey = fxConcentrationCategoryVegaMap.keySet();

		List<String> vegaCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (
					vegaCategoryOuter
				);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					BucketVegaSettings.ISDA_FX_20 (
						vegaCategoryOuter
					)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. : FXSystemics20.CORRELATION;
				}

				++vegaCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				new LabelCorrelation (
					vegaCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.1 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @return ISDA 2.1 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_21()
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<String, Double> fxConcentrationCategoryVegaMap = FXRiskThresholdContainer21.CategoryVegaMap();

		Set<String> fxConcentrationCategoryVegaKey = fxConcentrationCategoryVegaMap.keySet();

		List<String> vegaCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (
					vegaCategoryOuter
				);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					BucketVegaSettings.ISDA_FX_21 (
						vegaCategoryOuter
					)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. : FXSystemics21.CORRELATION;
				}

				++vegaCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				new LabelCorrelation (
					vegaCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.4 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param givenCurrency Given Currency
	 * @param calculationCurrency Calculation Currency
	 * 
	 * @return ISDA 2.4 FX VEGA Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_VEGA_24 (
		final String givenCurrency,
		final String calculationCurrency)
	{
		Map<String, BucketSensitivitySettings> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<String, Double> fxConcentrationCategoryVegaMap = FXRiskThresholdContainer24.CategoryVegaMap();

		Set<String> fxConcentrationCategoryVegaKey = fxConcentrationCategoryVegaMap.keySet();

		List<String> vegaCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryVegaCount = fxConcentrationCategoryVegaKey.size();

		int vegaCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryVegaCount][fxConcentrationCategoryVegaCount];

		try
		{
			for (String vegaCategoryOuter : fxConcentrationCategoryVegaKey)
			{
				vegaCategoryList.add (
					vegaCategoryOuter
				);

				bucketVegaSettingsMap.put (
					vegaCategoryOuter,
					BucketVegaSettings.ISDA_FX_24 (
						vegaCategoryOuter,
						givenCurrency,
						calculationCurrency
					)
				);

				for (int vegaCategoryIndexInner = 0;
					vegaCategoryIndexInner < fxConcentrationCategoryVegaCount;
					++vegaCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[vegaCategoryIndexOuter][vegaCategoryIndexInner] =
						vegaCategoryIndexOuter == vegaCategoryIndexInner ? 1. :
						FXSystemics24.VOLATILITY_CURVATURE_CORRELATION;
				}

				++vegaCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketVegaSettingsMap,
				new LabelCorrelation (
					vegaCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.0 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.0 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_20 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<String, Double> fxConcentrationCategoryCurvatureMap =
			FXRiskThresholdContainer20.CategoryVegaMap();

		Set<String> fxConcentrationCategoryCurvatureKey = fxConcentrationCategoryCurvatureMap.keySet();

		List<String> curvatureCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (
					curvatureCategoryOuter
				);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					BucketCurvatureSettings.ISDA_FX_20 (
						curvatureCategoryOuter,
						vegaDurationDays
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						FXSystemics20.CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new LabelCorrelation (
					curvatureCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.1 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return ISDA 2.1 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_21 (
		final int vegaDurationDays)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<String, Double> fxConcentrationCategoryCurvatureMap =
			FXRiskThresholdContainer21.CategoryVegaMap();

		Set<String> fxConcentrationCategoryCurvatureKey = fxConcentrationCategoryCurvatureMap.keySet();

		List<String> curvatureCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (
					curvatureCategoryOuter
				);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					BucketCurvatureSettings.ISDA_FX_21 (
						curvatureCategoryOuter,
						vegaDurationDays
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						FXSystemics21.CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new LabelCorrelation (
					curvatureCategoryList,
					crossBucketCorrelationMatrix
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
	 * Construct an ISDA 2.4 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * @param givenCurrency Given Currency
	 * @param calculationCurrency Calculation Currency
	 * 
	 * @return ISDA 2.4 FX Curvature Standard Instance of RiskMeasureSensitivitySettings
	 */

	public static final RiskMeasureSensitivitySettings ISDA_FX_CURVATURE_24 (
		final int vegaDurationDays,
		final String givenCurrency,
		final String calculationCurrency)
	{
		Map<String, BucketSensitivitySettings> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettings>();

		Map<String, Double> fxConcentrationCategoryCurvatureMap =
			FXRiskThresholdContainer24.CategoryVegaMap();

		Set<String> fxConcentrationCategoryCurvatureKey = fxConcentrationCategoryCurvatureMap.keySet();

		List<String> curvatureCategoryList = new ArrayList<String>();

		int fxConcentrationCategoryCurvatureCount = fxConcentrationCategoryCurvatureKey.size();

		int curvatureCategoryIndexOuter = 0;
		double[][] crossBucketCorrelationMatrix = new
			double[fxConcentrationCategoryCurvatureCount][fxConcentrationCategoryCurvatureCount];

		try
		{
			for (String curvatureCategoryOuter : fxConcentrationCategoryCurvatureKey)
			{
				curvatureCategoryList.add (
					curvatureCategoryOuter
				);

				bucketCurvatureSettingsMap.put (
					curvatureCategoryOuter,
					BucketCurvatureSettings.ISDA_FX_24 (
						curvatureCategoryOuter,
						vegaDurationDays,
						givenCurrency,
						calculationCurrency
					)
				);

				for (int curvatureCategoryIndexInner = 0;
					curvatureCategoryIndexInner < fxConcentrationCategoryCurvatureCount;
					++curvatureCategoryIndexInner)
				{
					crossBucketCorrelationMatrix[curvatureCategoryIndexOuter][curvatureCategoryIndexInner] =
						curvatureCategoryIndexOuter == curvatureCategoryIndexInner ? 1. :
						FXSystemics24.VOLATILITY_CURVATURE_CORRELATION;
				}

				++curvatureCategoryIndexOuter;
			}

			return new RiskMeasureSensitivitySettings (
				bucketCurvatureSettingsMap,
				new LabelCorrelation (
					curvatureCategoryList,
					crossBucketCorrelationMatrix
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
	 * RiskMeasureSensitivitySettings Constructor
	 * 
	 * @param bucketSettingsMap The Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation The Cross Bucket Correlation
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettings (
		final Map<String, BucketSensitivitySettings> bucketSettingsMap,
		final LabelCorrelation crossBucketCorrelation)
		throws Exception
	{
		if (null == (_bucketSettingsMap = bucketSettingsMap) || 0 == _bucketSettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation)
		)
		{
			throw new Exception (
				"RiskMeasureSensitivitySettings Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public LabelCorrelation crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Sensitivity Settings Map
	 * 
	 * @return The Bucket Sensitivity Settings Map
	 */

	public Map<String, BucketSensitivitySettings> bucketSettingsMap()
	{
		return _bucketSettingsMap;
	}
}
