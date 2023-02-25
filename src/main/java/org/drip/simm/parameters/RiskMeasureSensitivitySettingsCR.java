
package org.drip.simm.parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.simm.credit.CRNQBucketCorrelation20;
import org.drip.simm.credit.CRNQBucketCorrelation21;
import org.drip.simm.credit.CRNQBucketCorrelation24;
import org.drip.simm.credit.CRNQSettingsContainer20;
import org.drip.simm.credit.CRNQSettingsContainer21;
import org.drip.simm.credit.CRNQSettingsContainer24;
import org.drip.simm.credit.CRQSettingsContainer20;
import org.drip.simm.credit.CRQSettingsContainer21;
import org.drip.simm.credit.CRQSettingsContainer24;

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
 * <i>RiskMeasureSensitivitySettingsCR</i> holds the Settings that govern the Generation of the ISDA SIMM
 * Bucket Sensitivities across Individual CR Class Risk Measure Buckets. The References are:
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

public class RiskMeasureSensitivitySettingsCR
{
	private LabelCorrelation _crossBucketCorrelation = null;
	private Map<String, BucketSensitivitySettingsCR> _bucketSensitivitySettingsMap = null;

	/**
	 * Generate SIMM 2.0 Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_DELTA_20()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer20.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_20 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.1 Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_DELTA_21()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer21.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_21 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.4 Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.4 Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_DELTA_24()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer24.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketSensitivitySettingsCR.ISDA_CRQ_DELTA_24 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.0 Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_VEGA_20()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer20.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketVegaSettingsCR.ISDA_CRQ_20 (bucketIndex)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.1 Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_VEGA_21()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer21.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketVegaSettingsCR.ISDA_CRQ_21 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.4 Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.4 Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_VEGA_24()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer24.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketVegaSettingsCR.ISDA_CRQ_24 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.0 Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_CURVATURE_20()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer20.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketCurvatureSettingsCR.ISDA_CRQ_20 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer20.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.1 Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_CURVATURE_21()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer21.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketCurvatureSettingsCR.ISDA_CRQ_21 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer21.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.4 Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.4 Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRQ_CURVATURE_24()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		for (int bucketIndex : CRQSettingsContainer24.BucketSet())
		{
			bucketSensitivitySettingsMap.put (
				"" + bucketIndex,
				BucketCurvatureSettingsCR.ISDA_CRQ_24 (
					bucketIndex
				)
			);
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				CRQSettingsContainer24.CrossBucketCorrelation()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate SIMM 2.0 Non-Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Non-Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_DELTA_20()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer20.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter
		)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_20 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation20.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.1 Non-Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Non-Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_DELTA_21()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer21.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_21 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation21.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.4 Non-Credit Qualifying Delta Sensitivity Settings
	 * 
	 * @return The SIMM 2.4 Non-Credit Qualifying Delta Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_DELTA_24()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer24.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketSensitivitySettingsCR.ISDA_CRNQ_DELTA_24 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation24.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.0 Non-Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Non-Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_VEGA_20()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer20.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter
		)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketVegaSettingsCR.ISDA_CRNQ_20 (bucketIndexOuter)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation20.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.1 Non-Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Non-Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_VEGA_21()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer21.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter
		)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketVegaSettingsCR.ISDA_CRNQ_21 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation21.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.4 Non-Credit Qualifying Vega Sensitivity Settings
	 * 
	 * @return The SIMM 2.4 Non-Credit Qualifying Vega Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_VEGA_24()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer24.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter
		)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketVegaSettingsCR.ISDA_CRNQ_24 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation24.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.0 Non-Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.0 Non-Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_CURVATURE_20()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer20.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketCurvatureSettingsCR.ISDA_CRNQ_20 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation20.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.1 Non-Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.1 Non-Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_CURVATURE_21()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer21.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter
		)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketCurvatureSettingsCR.ISDA_CRNQ_21 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation21.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * Generate SIMM 2.4 Non-Credit Qualifying Curvature Sensitivity Settings
	 * 
	 * @return The SIMM 2.4 Non-Credit Qualifying Curvature Sensitivity Settings
	 */

	public static final RiskMeasureSensitivitySettingsCR ISDA_CRNQ_CURVATURE_24()
	{
		Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap =
			new CaseInsensitiveHashMap<BucketSensitivitySettingsCR>();

		int bucketCount = CRNQSettingsContainer24.BucketSet().size();

		List<String> bucketLabelList = new ArrayList<String>();

		double[][] crossBucketCorrelation = new double[bucketCount][bucketCount];

		for (int bucketIndexOuter = 0;
			bucketIndexOuter < bucketCount;
			++bucketIndexOuter
		)
		{
			String bucketIndexOuterKey = "" + bucketIndexOuter;

			bucketSensitivitySettingsMap.put (
				bucketIndexOuterKey,
				BucketCurvatureSettingsCR.ISDA_CRNQ_24 (
					bucketIndexOuter
				)
			);

			bucketLabelList.add (
				bucketIndexOuterKey
			);

			for (int bucketIndexInner = 0;
				bucketIndexInner < bucketCount;
				++bucketIndexInner
			)
			{
				crossBucketCorrelation[bucketIndexInner][bucketIndexOuter] =
					bucketIndexInner == bucketIndexOuter ? 1. :
					CRNQBucketCorrelation24.NON_RESIDUAL_TO_NON_RESIDUAL;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsCR (
				bucketSensitivitySettingsMap,
				new LabelCorrelation (
					bucketLabelList,
					crossBucketCorrelation
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
	 * RiskMeasureSensitivitySettingsCR Constructor
	 * 
	 * @param bucketSensitivitySettingsMap Credit Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation Cross Bucket Correlation
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettingsCR (
		final Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap,
		final LabelCorrelation crossBucketCorrelation)
		throws Exception
	{
		if (null == (_bucketSensitivitySettingsMap = bucketSensitivitySettingsMap) ||
				0 == _bucketSensitivitySettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation)
		)
		{
			throw new Exception (
				"RiskMeasureSensitivitySettingsCR Constructor => Invalid Inputs"
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
	 * Retrieve the Credit Bucket Sensitivity Settings Map
	 * 
	 * @return The Credit Bucket Sensitivity Settings Map
	 */

	public Map<String, BucketSensitivitySettingsCR> bucketSensitivitySettingsMap()
	{
		return _bucketSensitivitySettingsMap;
	}
}
