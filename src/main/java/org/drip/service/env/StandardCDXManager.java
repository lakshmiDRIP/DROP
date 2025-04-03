
package org.drip.service.env;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.product.creator.CDSBasketBuilder;
import org.drip.product.creator.CDXRefDataHolder;
import org.drip.product.definition.BasketProduct;
import org.drip.product.params.CDXIdentifier;
import org.drip.product.params.CDXRefDataParams;
import org.drip.product.params.StandardCDXParams;

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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>StandardCDXManager</i> implements the creation and the static details of the all the NA, EU, SovX,
 * 	EMEA, and ASIA standardized CDS indices. It exposes the following functionality:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Retrieve the full set of pre-set/pre-loaded CDX names/descriptions.
 *  	</li>
 *  	<li>
 *  		Retrieve all the CDX's given an index name.
 *  	</li>
 *  	<li>
 *  		Get the index, index series, and the effective/maturity dates for a given CDX.
 *  	</li>
 *  	<li>
 *  		Get all the on-the-runs for an index, date, and tenor.
 *  	</li>
 *  	<li>
 *  		Retrieve the full basket product corresponding to NA/EU/ASIA IG/HY/EM and other available
 *  			standard CDX.
 *  	</li>
 *  	<li>
 *  		Build a custom CDX product.
 *  	</li>
 *  </ul>
 * 
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></td></tr>
 *  </table>
 *	<br>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class StandardCDXManager
{
	private static CaseInsensitiveTreeMap<BasketProduct> _indexMap = null;
	private static CaseInsensitiveTreeMap<StandardCDXParams> _indexParamsMap = null;
	private static CaseInsensitiveTreeMap<Map<JulianDate, Integer>> _seriesFirstCouponDateToIndexSequence =
		null;
	private static CaseInsensitiveTreeMap<Map<Integer, JulianDate>> _seriesIndexSequenceToFirstCouponDate =
		null;

	private static final boolean SetupParams()
	{
		_indexParamsMap = new CaseInsensitiveTreeMap<StandardCDXParams>();

		try {
			_indexParamsMap.put ("CDX.NA.IG", new StandardCDXParams (125, "USD", 0.01));

			_indexParamsMap.put ("CDX.NA.HY", new StandardCDXParams (100, "USD", 0.05));

			_indexParamsMap.put ("CDX.NA.HVOL", new StandardCDXParams (30, "USD", 0.01));

			_indexParamsMap.put ("CDX.NA.HIVOL", new StandardCDXParams (30, "USD", 0.01));

			_indexParamsMap.put ("CDX.NA.XO", new StandardCDXParams (35, "USD", 0.034));

			_indexParamsMap.put ("CDX.NA.HY.BB", new StandardCDXParams (40, "USD", 0.05));

			_indexParamsMap.put ("CDX.NA.HY.B", new StandardCDXParams (37, "USD", 0.05));

			_indexParamsMap.put ("ITRX.EU.IG", new StandardCDXParams (125, "EUR", 0.01));

			_indexParamsMap.put ("ITRAXX.EU.IG", new StandardCDXParams (125, "EUR", 0.01));

			_indexParamsMap.put ("ITRX.EU.HVOL", new StandardCDXParams (30, "EUR", 0.01));

			_indexParamsMap.put ("ITRAXX.EU.HVOL", new StandardCDXParams (30, "EUR", 0.01));

			_indexParamsMap.put ("ITRX.EU.XO", new StandardCDXParams (50, "EUR", 0.05));

			_indexParamsMap.put ("ITRAXX.EU.XO", new StandardCDXParams (50, "EUR", 0.05));

			_indexParamsMap.put ("ITRX.EU.NONFIN", new StandardCDXParams (50, "EUR", 0.05));

			_indexParamsMap.put ("ITRAXX.EU.NONFIN", new StandardCDXParams (50, "EUR", 0.05));

			_indexParamsMap.put ("ITRX.EU.FINSNR", new StandardCDXParams (20, "EUR", 0.01));

			_indexParamsMap.put ("ITRAXX.EU.FINSNR", new StandardCDXParams (20, "EUR", 0.01));

			_indexParamsMap.put ("ITRX.EU.FINSUB", new StandardCDXParams (20, "EUR", 0.01));

			_indexParamsMap.put ("ITRAXX.EU.FINSUB", new StandardCDXParams (20, "EUR", 0.01));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static final boolean UpdateIndexMaps (
		final Map<JulianDate, Integer> dateSeriesMap,
		final Map<Integer, JulianDate> seriesDateMap,
		final JulianDate date,
		final int series)
	{
		dateSeriesMap.put (date, series);

		seriesDateMap.put (series, date);

		return true;
	}

	private static final boolean PresetNA_IG_HY_HVOL_HYBB_HYBSeries()
	{
		Map<JulianDate, Integer> dateSeriesMap = new TreeMap<JulianDate, Integer>();

		Map<Integer, JulianDate> seriesDateMap = new TreeMap<Integer, JulianDate>();

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2014, 6, 20), 22);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 12, 20), 21);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 6, 20), 20);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 12, 20), 19);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 6, 20), 18);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 12, 20), 17);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 6, 20), 16);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 12, 20), 15);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 6, 20), 14);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 12, 20), 13);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 6, 20), 12);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 12, 20), 11);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 6, 20), 10);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 12, 20), 9);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 6, 20), 8);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 12, 20), 7);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 6, 20), 6);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2005, 12, 20), 5);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2005, 6, 20), 4);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2004, 12, 20), 3);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2004, 6, 20), 2);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2003, 12, 20), 1);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.IG", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.IG", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.HY", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.HY", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.HVOL", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.HVOL", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.HIVOL", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.HIVOL", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.HY.B", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.HY.B", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.HY.BB", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.HY.BB", seriesDateMap);

		return true;
	}

	private static final boolean PresetNAXOSeries()
	{
		Map<JulianDate, Integer> dateSeriesMap = new TreeMap<JulianDate, Integer>();

		Map<Integer, JulianDate> seriesDateMap = new TreeMap<Integer, JulianDate>();

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2014, 6, 20), 16);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 12, 20), 15);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 6, 20), 14);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 12, 20), 13);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 6, 20), 12);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 12, 20), 11);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 6, 20), 10);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 12, 20), 9);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 6, 20), 8);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 12, 20), 7);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 6, 20), 6);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 12, 20), 5);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 6, 20), 4);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 12, 20), 3);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 6, 20), 2);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 12, 20), 1);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.NA.XO", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.NA.XO", seriesDateMap);

		return true;
	}

	private static final boolean PresetEMSeries()
	{
		Map<JulianDate, Integer> dateSeriesMap = new TreeMap<JulianDate, Integer>();

		Map<Integer, JulianDate> seriesDateMap = new TreeMap<Integer, JulianDate>();

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2014, 6, 20), 21);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 12, 20), 20);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 6, 20), 19);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 12, 20), 18);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 6, 20), 17);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 12, 20), 16);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 6, 20), 15);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 12, 20), 14);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 6, 20), 13);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 12, 20), 12);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 6, 20), 11);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 12, 20), 10);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 6, 20), 9);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 12, 20), 8);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 6, 20), 7);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 12, 20), 6);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 6, 20), 5);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2005, 12, 20), 4);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2005, 6, 20), 3);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2004, 12, 20), 2);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2004, 6, 20), 1);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.EM", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.EM", seriesDateMap);

		return true;
	}

	private static final boolean PresetEUSeries()
	{
		Map<JulianDate, Integer> dateSeriesMap = new TreeMap<JulianDate, Integer>();

		Map<Integer, JulianDate> seriesDateMap = new TreeMap<Integer, JulianDate>();

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2014, 6, 20), 20);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 12, 20), 19);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2013, 6, 20), 18);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 12, 20), 17);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2012, 6, 20), 16);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 12, 20), 15);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2011, 6, 20), 14);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 12, 20), 13);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2010, 6, 20), 12);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 12, 20), 11);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2009, 6, 20), 10);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 12, 20), 9);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2008, 6, 20), 8);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 12, 20), 7);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2007, 6, 20), 6);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 12, 20), 5);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2006, 6, 20), 4);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2005, 12, 20), 3);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2005, 6, 20), 2);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2004, 12, 20), 1);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.IG", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.IG", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.HVOL", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.HVOL", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.HIVOL", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.HIVOL", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.XO", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.XO", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.FINSNR", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.FINSNR", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.FINSUB", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.FINSUB", seriesDateMap);

		_seriesFirstCouponDateToIndexSequence.put ("ITRX.EU.NONFIN", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("ITRX.EU.NONFIN", seriesDateMap);

		return true;
	}

	private static final BasketProduct ConstructIndex (
		final String tenor,
		final JulianDate firstCouponDate,
		final double coupon,
		final String fundingCurveName,
		final int componentCount,
		final String cdxName)
	{
		String[] creditCurveNameArray = new String[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			creditCurveNameArray[componentIndex] = "CC" + (componentIndex + 1);
		}

		return CDSBasketBuilder.MakeCDX (
			firstCouponDate.subtractTenor ("3M"),
			firstCouponDate.addTenor (tenor),
			coupon,
			fundingCurveName,
			creditCurveNameArray,
			cdxName
		);
	}

	private static final BasketProduct ConstructEMIndex (
		final String tenor,
		final JulianDate firstCouponDate,
		final String cdxName)
	{
		return CDSBasketBuilder.MakeCDX (
			firstCouponDate.subtractTenor ("3M"),
			firstCouponDate.addTenor (tenor),
			0.05,
			"USD",
			new String[] {
				"ARG",
				"VEN",
				"BRA",
				"MAL",
				"COL",
				"HUN",
				"IND",
				"PAN",
				"PER",
				"SAF",
				"PHI",
				"TUR",
				"RUS",
				"UKR",
				"MEX"
			},
			new double[] {
				0.06,
				0.08,
				0.13,
				0.04,
				0.08,
				0.03,
				0.05,
				0.03,
				0.05,
				0.03,
				0.06,
				0.11,
				0.13,
				0.03,
				0.09
			},
			cdxName
		);
	}

	private static final BasketProduct MakePresetIndex (
		final String indexName,
		final int series,
		final String tenor)
	{
		if (null == indexName || indexName.isEmpty() || null == tenor || tenor.isEmpty()) {
			return null;
		}

		CDXIdentifier cdxIdentifier = null;

		try {
			cdxIdentifier = new CDXIdentifier (series, 1, indexName, tenor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == cdxIdentifier) {
			return null;
		}

		String cdxCode = cdxIdentifier.getCode();

		if (null == cdxCode || cdxCode.isEmpty()) {
			return null;
		}

		BasketProduct cdxBasketProduct = _indexMap.get (cdxCode);

		if (null != cdxBasketProduct) {
			return cdxBasketProduct;
		}

		Map<Integer, JulianDate> seriesFirstCouponDateMap =
			_seriesIndexSequenceToFirstCouponDate.get (indexName);

		if (null == seriesFirstCouponDateMap) {
			return null;
		}

		JulianDate firstCouponDate = seriesFirstCouponDateMap.get (series);

		if (null == firstCouponDate) {
			return null;
		}

		if ("CDX.EM".equalsIgnoreCase (indexName)) {
			cdxBasketProduct = ConstructEMIndex (tenor, firstCouponDate, cdxCode);
		} else {
			StandardCDXParams cdxParams = _indexParamsMap.get (indexName);

			if (null != cdxParams) {
				cdxBasketProduct = ConstructIndex (
					tenor, firstCouponDate,
					cdxParams._dblCoupon,
					cdxParams._strCurrency,
					cdxParams._iNumComponents,
					cdxCode
				);
			}
		}

		_indexMap.put (cdxCode, cdxBasketProduct);

		return cdxBasketProduct;
	}

	private static final BasketProduct MakePreLoadedIndex (
		final String index,
		final int series,
		final String tenor)
	{
		if (null == index || index.isEmpty() || null == tenor || tenor.isEmpty()) {
			return null;
		}

		CDXIdentifier cdxIdentifier = null;

		try {
			cdxIdentifier = new CDXIdentifier (series, 1, index, tenor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == cdxIdentifier) {
			return null;
		}

		String cdxCode = cdxIdentifier.getCode();

		if (null == cdxCode || cdxCode.isEmpty()) {
			return null;
		}

		CDXRefDataParams cdxRefDataParams = CDXRefDataHolder._mapCDXRefData.get (cdxCode);

		if (null == cdxRefDataParams) {
			return null;
		}

		String[] creditCurveNameArray = new String[cdxRefDataParams._iOriginalComponentCount];

		for (int creditCurveIndex = 0;
			creditCurveIndex < cdxRefDataParams._iOriginalComponentCount;
			++creditCurveIndex)
		{
			creditCurveNameArray[creditCurveIndex] = "CC" + (creditCurveIndex + 1);
		}

		return CDSBasketBuilder.MakeCDX (
			cdxRefDataParams._dtMaturity.subtractTenor (cdxRefDataParams._iIndexLifeSpan + "Y"),
			cdxRefDataParams._dtMaturity,
			cdxRefDataParams._dblCoupon,
			cdxRefDataParams._strCurrency,
			creditCurveNameArray,
			cdxRefDataParams._strIndexClass + "." + cdxRefDataParams._strIndexGroupName + "." +
				cdxRefDataParams._iIndexLifeSpan + "Y." + cdxRefDataParams._iIndexSeries + "." +
				cdxRefDataParams._iIndexVersion
		);
	}

	private static final BasketProduct GetPresetOnTheRun (
		final String index,
		final JulianDate date,
		final String tenor)
	{
		if (null == date || null == index || index.isEmpty() || null == tenor || tenor.isEmpty()) {
			return null;
		}

		Map<JulianDate, Integer> firstCouponDateSeriesMap =
			_seriesFirstCouponDateToIndexSequence.get (index);

		JulianDate firstCouponDate = date.nextCreditIMM (3);

		if (null == firstCouponDate || null == firstCouponDateSeriesMap) {
			return null;
		}

		if (null == firstCouponDateSeriesMap.get (firstCouponDate)) {
			firstCouponDate = firstCouponDate.nextCreditIMM (3);
		}

		return null == firstCouponDate || null == firstCouponDateSeriesMap.get (firstCouponDate) ? null :
			MakePresetIndex (index, firstCouponDateSeriesMap.get (firstCouponDate), tenor);
	}

	private static final BasketProduct GetPreLoadedOnTheRun (
		final String index,
		final JulianDate date,
		final String tenor)
	{
		if (null == date || null == index || index.isEmpty() || null == tenor || tenor.isEmpty()) {
			return null;
		}

		Map<JulianDate, Integer> firstCouponDateSeriesMap =
			CDXRefDataHolder._mmCDXRDBFirstCouponSeries.get (index);

		JulianDate firstCouponDate = date.nextCreditIMM (3);

		if (null == firstCouponDate || null == firstCouponDateSeriesMap) {
			return null;
		}

		if (null == firstCouponDateSeriesMap.get (firstCouponDate)) {
			firstCouponDate = firstCouponDate.nextCreditIMM (3);
		}

		Integer series = null == firstCouponDate ? null : firstCouponDateSeriesMap.get (firstCouponDate);

		return null == series ? null : MakePreLoadedIndex (index, series, tenor);
	}

	/**
	 * Initialize the Standard CDX Series
	 * 
	 * @return TRUE - Initialization Succeeded
	 */

	public static final boolean InitializeSeries()
	{
		if (null != _indexMap) {
			return true;
		}

		_indexMap = new CaseInsensitiveTreeMap<BasketProduct>();

		_seriesFirstCouponDateToIndexSequence = new CaseInsensitiveTreeMap<Map<JulianDate, Integer>>();

		_seriesIndexSequenceToFirstCouponDate = new CaseInsensitiveTreeMap<Map<Integer, JulianDate>>();

		if (!PresetNA_IG_HY_HVOL_HYBB_HYBSeries()) {
			System.out.println ("Cannot initialize NA_IG_HY_HVOL_HYBB_HYB");

			return false;
		}

		if (!PresetNAXOSeries()) {
			System.out.println ("Cannot initialize NA_XO");

			return false;
		}

		if (!PresetEMSeries()) {
			System.out.println ("Cannot initialize EM");

			return false;
		}

		if (!PresetEUSeries()) {
			System.out.println ("Cannot initialize EU");

			return false;
		}

		if (!SetupParams()) {
			System.out.println ("Cannot setup standard CDX Params!");

			return false;
		}

		return CDXRefDataHolder.InitFullCDXRefDataSet();
	}

	/**
	 * Write the Index Details to a File
	 * 
	 * @param coverageFile Coverage File
	 * 
	 * @return TRUE - Index Details successfully written to a File
	 */

	public static final boolean DumpIndexDetails (
		final String coverageFile)
	{
		if (null == coverageFile || coverageFile.isEmpty()) {
			return false;
		}

		BufferedWriter bufferedWriter = null;

		try {
			(
				bufferedWriter = new BufferedWriter (new FileWriter (coverageFile))
			).write (
				"\n , Index Name, Description, Issue Date, Maturity Date, Frequency, Coupon\n"
			);
		} catch (Exception e) {
			e.printStackTrace();

			try {
				bufferedWriter.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		}

		for (Map.Entry<String, CDXRefDataParams> cdxRefDataParamsMapEntry :
			CDXRefDataHolder._mapCDXRefData.entrySet())
		{
			CDXRefDataParams cdxRefDataParams = cdxRefDataParamsMapEntry.getValue();

			if (null == cdxRefDataParams) {
				continue;
			}

			try {
				bufferedWriter.write (
					" , " + cdxRefDataParamsMapEntry.getKey() + ", " + cdxRefDataParams._strIndexName +
						", " + cdxRefDataParams._dtIssue + ", " + cdxRefDataParams._dtMaturity + ", " +
						cdxRefDataParams._iFrequency + ", " + (int) (10000. * cdxRefDataParams._dblCoupon) +
						"\n"
				);
			} catch (Exception e) {
				e.printStackTrace();

				try {
					bufferedWriter.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		try {
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Create a standard CDX from the index code, the index series, and the tenor.
	 * 
	 * @param index The Index Code (CDX.NA.IG, CDX.NA.HY, etc)
	 * @param series Index Series Number
	 * @param tenor The specific tenor - typical common ones are 3Y, 5Y, 7Y, and 10Y
	 * 
	 * @return The CDX Basket Product
	 */

	public static final BasketProduct MakeIndex (
		final String index,
		final int series,
		final String tenor)
	{
		BasketProduct cdxBasketProduct = MakePresetIndex (index, series, tenor);

		return null != cdxBasketProduct ? cdxBasketProduct : MakePreLoadedIndex (index, series, tenor);
	}

	/**
	 * Retrieve the on-the-run for the index and tenor corresponding to the specified date
	 * 
	 * @param index CDX/ITRAXX index
	 * @param date Specified date
	 * @param tenor Tenor
	 * 
	 * @return CDX/ITRAXX Basket Product
	 */

	public static final BasketProduct GetOnTheRun (
		final String index,
		final JulianDate date,
		final String tenor)
	{
		BasketProduct cdxBasketProduct = GetPresetOnTheRun (index, date, tenor);

		return null != cdxBasketProduct ? cdxBasketProduct : GetPreLoadedOnTheRun (index, date, tenor);
	}

	/**
	 * Retrieve a set of all the pre-set CDX index names
	 * 
	 * @return Set of the pre-set CDX index names
	 */

	public static final Set<String> GetPresetIndexNames()
	{
		return _seriesFirstCouponDateToIndexSequence.keySet();
	}

	/**
	 * Retrieve a set of all the pre-loaded CDX index names
	 * 
	 * @return Set of the pre-loaded CDX index names
	 */

	public static final Set<String> GetPreLoadedIndexNames()
	{
		return CDXRefDataHolder._mmCDXRDBFirstCouponSeries.keySet();
	}

	/**
	 * Retrieve the comprehensive set of pre-set and pre-loaded CDX index names
	 * 
	 * @return Set of the pre-set and the pre-loaded CDX index names
	 */

	public static final Set<String> GetIndexNames()
	{
		Set<String> indexNameSet = new HashSet<String>();

		indexNameSet.addAll (GetPreLoadedIndexNames());

		indexNameSet.addAll (GetPresetIndexNames());

		return indexNameSet;
	}

	/**
	 * Return the full set of pre-set CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param indexName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final Map<JulianDate, Integer> GetPresetIndexSeriesMap (
		final String indexName)
	{
		return null == indexName || indexName.isEmpty() ? null :
			_seriesFirstCouponDateToIndexSequence.get (indexName);
	}

	/**
	 * Return the full set of pre-loaded CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param indexName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final Map<JulianDate, Integer> GetPreLoadedCDXSeriesMap (
		final String indexName)
	{
		return null == indexName || indexName.isEmpty() ? null :
			CDXRefDataHolder._mmCDXRDBFirstCouponSeries.get (indexName);
	}

	/**
	 * Return the full set of CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param indexName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final Map<JulianDate, Integer> GetIndexSeriesMap (
		final String indexName)
	{
		if (null == indexName || indexName.isEmpty()) {
			return null;
		}

		Map<JulianDate, Integer> firstCouponDateSeriesMap = new HashMap<JulianDate, Integer>();

		Map<JulianDate, Integer> presetFirstCouponDateSeriesMap = GetPresetIndexSeriesMap (indexName);

		if (null != presetFirstCouponDateSeriesMap) {
			firstCouponDateSeriesMap.putAll (presetFirstCouponDateSeriesMap);
		}

		Map<JulianDate, Integer> preLoadedFirstCouponDateSeriesMap = GetPreLoadedCDXSeriesMap (indexName);

		if (null != preLoadedFirstCouponDateSeriesMap) {
			firstCouponDateSeriesMap.putAll (preLoadedFirstCouponDateSeriesMap);
		}

		return firstCouponDateSeriesMap;
	}

	/**
	 * Retrieve the name/description map for all the pre-set CDS indices
	 * 
	 * @return Name/description map for all the pre-set CDS indices
	 */

	public static final CaseInsensitiveTreeMap<String> GetPresetIndexDescriptions()
	{
		CaseInsensitiveTreeMap<String> indexDescriptionMap = new CaseInsensitiveTreeMap<String>();

		for (Map.Entry<String, StandardCDXParams> standardCDXParamsMapEntry : _indexParamsMap.entrySet()) {
			indexDescriptionMap.put (standardCDXParamsMapEntry.getKey(), standardCDXParamsMapEntry.getKey());
		}

		return indexDescriptionMap;
	}

	/**
	 * Retrieve the name/description map for all the pre-loaded CDS indices
	 * 
	 * @return Name/description map for all the pre-loaded CDS indices
	 */

	public static final CaseInsensitiveTreeMap<String> GetPreLoadedIndexDescriptions()
	{
		CaseInsensitiveTreeMap<String> indexDescriptionMap = new CaseInsensitiveTreeMap<String>();

		for (Map.Entry<String, CDXRefDataParams> cdxRefDataParamsMapEntry :
			CDXRefDataHolder._mapCDXRefData.entrySet())
		{
			indexDescriptionMap.put (
				cdxRefDataParamsMapEntry.getKey(),
				cdxRefDataParamsMapEntry.getValue()._strIndexName
			);
		}

		return indexDescriptionMap;
	}

	/**
	 * Retrieve the name/description map for all the CDS indices
	 * 
	 * @return Name/description map for all the CDS indices
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<String>
		GetCDXDescriptions()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<String> mapCDXDescr = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<String>();

		mapCDXDescr.putAll (GetPreLoadedIndexDescriptions());

		mapCDXDescr.putAll (GetPresetIndexDescriptions());

		return mapCDXDescr;
	}
}
