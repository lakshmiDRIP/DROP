
package org.drip.service.env;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.product.definition.BasketProduct;
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
		Map<JulianDate, Integer> dateSeriesMap = new
			TreeMap<JulianDate, Integer>();

		Map<Integer, JulianDate> seriesDateMap = new
			TreeMap<Integer, JulianDate>();

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2014, 6, 20), 21);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2013, 12, 20), 20);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2013, 6, 20), 19);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2012, 12, 20), 18);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2012, 6, 20), 17);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2011, 12, 20), 16);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2011, 6, 20), 15);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2010, 12, 20), 14);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2010, 6, 20), 13);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2009, 12, 20), 12);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2009, 6, 20), 11);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2008, 12, 20), 10);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2008, 6, 20), 9);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2007, 12, 20), 8);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2007, 6, 20), 7);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2006, 12, 20), 6);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2006, 6, 20), 5);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2005, 12, 20), 4);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2005, 6, 20), 3);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2004, 12, 20), 2);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2004, 6, 20), 1);

		_seriesFirstCouponDateToIndexSequence.put ("CDX.EM", dateSeriesMap);

		_seriesIndexSequenceToFirstCouponDate.put ("CDX.EM", seriesDateMap);

		return true;
	}

	private static final boolean PresetEUSeries()
	{
		Map<JulianDate, Integer> dateSeriesMap = new TreeMap<JulianDate, Integer>();

		Map<Integer, JulianDate> seriesDateMap = new TreeMap<Integer, JulianDate>();

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD (2014, 6, 20), 20);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2013, 12, 20), 19);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2013, 6, 20), 18);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2012, 12, 20), 17);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2012, 6, 20), 16);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2011, 12, 20), 15);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2011, 6, 20), 14);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2010, 12, 20), 13);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2010, 6, 20), 12);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2009, 12, 20), 11);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2009, 6, 20), 10);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2008, 12, 20), 9);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2008, 6, 20), 8);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2007, 12, 20), 7);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2007, 6, 20), 6);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2006, 12, 20), 5);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2006, 6, 20), 4);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2005, 12, 20), 3);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2005, 6, 20), 2);

		UpdateIndexMaps (dateSeriesMap, seriesDateMap, DateUtil.CreateFromYMD
			(2004, 12, 20), 1);

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

	/**
	 * Initialize the Standard CDX Series
	 * 
	 * @return TRUE - Initialization Succeeded
	 */

	public static final boolean InitStandardCDXSeries()
	{
		if (null != _indexMap) return true;

		_indexMap = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.product.definition.BasketProduct>();

		_seriesFirstCouponDateToIndexSequence = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<Map<JulianDate,
			Integer>>();

		_seriesIndexSequenceToFirstCouponDate = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<Map<Integer,
				JulianDate>>();

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

		return org.drip.product.creator.CDXRefDataHolder.InitFullCDXRefDataSet();
	}

	private static final org.drip.product.definition.BasketProduct ConstructCDX (
		final String strTenor,
		final JulianDate dtFirstCoupon,
		final double dblCoupon,
		final String strIR,
		final int iNumComponents,
		final String strCDXName)
	{
		String[] astrCC = new String[iNumComponents];

		for (int i = 0; i < iNumComponents; ++i)
			astrCC[i] = "CC" + (i + 1);

		return org.drip.product.creator.CDSBasketBuilder.MakeCDX (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, strIR, astrCC, strCDXName);
	}

	private static final org.drip.product.definition.BasketProduct ConstructCDXEM (
		final String strTenor,
		final JulianDate dtFirstCoupon,
		final String strCDXName)
	{
		return org.drip.product.creator.CDSBasketBuilder.MakeCDX (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), 0.05, "USD", new String[] {"ARG", "VEN", "BRA",
				"MAL", "COL", "HUN", "IND", "PAN", "PER", "SAF", "PHI", "TUR", "RUS", "UKR", "MEX"}, new
					double[] {0.06, 0.08, 0.13, 0.04, 0.08, 0.03, 0.05, 0.03, 0.05, 0.03, 0.06, 0.11, 0.13,
						0.03, 0.09}, strCDXName);
	}

	private static final org.drip.product.definition.BasketProduct MakePresetStandardCDX (
		final String strIndex,
		final int iSeries,
		final String strTenor)
	{
		if (null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.product.params.CDXIdentifier cdxID = null;

		try {
			cdxID = new org.drip.product.params.CDXIdentifier (iSeries, 1, strIndex, strTenor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == cdxID) return null;

		String strCDXCode = cdxID.getCode();

		if (null == strCDXCode || strCDXCode.isEmpty()) return null;

		org.drip.product.definition.BasketProduct cdx = _indexMap.get (strCDXCode);

		if (null != cdx) return cdx;

		Map<Integer, JulianDate> mapSeriesFirstCoupon =
			_seriesIndexSequenceToFirstCouponDate.get (strIndex);

		if (null == mapSeriesFirstCoupon) return null;

		JulianDate dtFirstCoupon = mapSeriesFirstCoupon.get (iSeries);

		if (null == dtFirstCoupon) return null;

		if ("CDX.EM".equalsIgnoreCase (strIndex))
			cdx = ConstructCDXEM (strTenor, dtFirstCoupon, strCDXCode);
		else {
			org.drip.product.params.StandardCDXParams cdxParams = _indexParamsMap.get (strIndex);

			if (null != cdxParams)
				cdx = ConstructCDX (strTenor, dtFirstCoupon, cdxParams._dblCoupon, cdxParams._strCurrency,
					cdxParams._iNumComponents, strCDXCode);
		}

		_indexMap.put (strCDXCode, cdx);

		return cdx;
	}

	private static final org.drip.product.definition.BasketProduct MakePreLoadedStandardCDX (
		final String strIndex,
		final int iSeries,
		final String strTenor)
	{
		if (null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.product.params.CDXIdentifier cdxID = null;

		try {
			cdxID = new org.drip.product.params.CDXIdentifier (iSeries, 1, strIndex, strTenor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == cdxID) return null;

		String strCDXCode = cdxID.getCode();

		if (null == strCDXCode || strCDXCode.isEmpty()) return null;

		org.drip.product.params.CDXRefDataParams cdxrdb =
			org.drip.product.creator.CDXRefDataHolder._mapCDXRefData.get (strCDXCode);

		if (null == cdxrdb) return null;

		String[] astrCC = new String[cdxrdb._iOriginalComponentCount];

		for (int i = 0; i < cdxrdb._iOriginalComponentCount; ++i)
			astrCC[i] = "CC" + (i + 1);

		return org.drip.product.creator.CDSBasketBuilder.MakeCDX (cdxrdb._dtMaturity.subtractTenor
			(cdxrdb._iIndexLifeSpan + "Y"), cdxrdb._dtMaturity, cdxrdb._dblCoupon, cdxrdb._strCurrency,
				astrCC, cdxrdb._strIndexClass + "." + cdxrdb._strIndexGroupName + "." +
					cdxrdb._iIndexLifeSpan + "Y." + cdxrdb._iIndexSeries + "." + cdxrdb._iIndexVersion);
	}

	private static final org.drip.product.definition.BasketProduct GetPresetOnTheRun (
		final String strIndex,
		final JulianDate dt,
		final String strTenor)
	{
		if (null == dt || null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty())
			return null;

		Map<JulianDate, Integer> mapFirstCouponSeries =
				_seriesFirstCouponDateToIndexSequence.get (strIndex);

		JulianDate dtFirstCoupon = dt.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries) return null;

		if (null == mapFirstCouponSeries.get (dtFirstCoupon))
			dtFirstCoupon = dtFirstCoupon.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries.get (dtFirstCoupon)) return null;

		return MakePresetStandardCDX (strIndex, mapFirstCouponSeries.get (dtFirstCoupon), strTenor);
	}

	private static final org.drip.product.definition.BasketProduct GetPreLoadedOnTheRun (
		final String strIndex,
		final JulianDate dt,
		final String strTenor)
	{
		if (null == dt || null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty())
			return null;

		Map<JulianDate, Integer> mapFirstCouponSeries =
			org.drip.product.creator.CDXRefDataHolder._mmCDXRDBFirstCouponSeries.get (strIndex);

		JulianDate dtFirstCoupon = dt.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries) return null;

		if (null == mapFirstCouponSeries.get (dtFirstCoupon))
			dtFirstCoupon = dtFirstCoupon.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries.get (dtFirstCoupon)) return null;

		return MakePreLoadedStandardCDX (strIndex, mapFirstCouponSeries.get (dtFirstCoupon), strTenor);
	}

	public static final boolean DumpIndexDetails (
		final String strCDXCoverageFile)
	{
		if (null == strCDXCoverageFile || strCDXCoverageFile.isEmpty()) return false;

		java.io.BufferedWriter bw = null;

		try {
			bw = new java.io.BufferedWriter (new java.io.FileWriter (strCDXCoverageFile));

			bw.write ("\n , Index Name, Description, Issue Date, Maturity Date, Frequency, Coupon\n");
		} catch (Exception e) {
			e.printStackTrace();

			try {
				bw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return false;
		}

		for (Map.Entry<String, org.drip.product.params.CDXRefDataParams> meCDXRefData :
			org.drip.product.creator.CDXRefDataHolder._mapCDXRefData.entrySet()) {
			org.drip.product.params.CDXRefDataParams cdxrdb = meCDXRefData.getValue();

			if (null == cdxrdb) continue;

			String strIndexDetails = " , " + meCDXRefData.getKey() + ", " + cdxrdb._strIndexName +
				", " + cdxrdb._dtIssue + ", " + cdxrdb._dtMaturity + ", " + cdxrdb._iFrequency + ", " + (int)
					(10000. * cdxrdb._dblCoupon) + "\n";

			try {
				bw.write (strIndexDetails);
			} catch (Exception e) {
				e.printStackTrace();

				try {
					bw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		try {
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Create a standard CDX from the index code, the index series, and the tenor.
	 * 
	 * @param strIndex The Index Code (CDX.NA.IG, CDX.NA.HY, etc)
	 * @param iSeries Index Series Number
	 * @param strTenor The specific tenor - typical common ones are 3Y, 5Y, 7Y, and 10Y
	 * 
	 * @return The CDX Basket Product
	 */

	public static final org.drip.product.definition.BasketProduct MakeStandardCDX (
		final String strIndex,
		final int iSeries,
		final String strTenor)
	{
		org.drip.product.definition.BasketProduct bpCDX = MakePresetStandardCDX (strIndex, iSeries,
			strTenor);

		if (null != bpCDX) return bpCDX;

		return MakePreLoadedStandardCDX (strIndex, iSeries, strTenor);
	}

	/**
	 * Retrieve the on-the-run for the index and tenor corresponding to the specified date
	 * 
	 * @param strIndex CDX/ITRAXX index
	 * @param dt Specified date
	 * @param strTenor Tenor
	 * 
	 * @return CDX/ITRAXX Basket Product
	 */

	public static final org.drip.product.definition.BasketProduct GetOnTheRun (
		final String strIndex,
		final JulianDate dt,
		final String strTenor)
	{
		org.drip.product.definition.BasketProduct bpCDX = GetPresetOnTheRun (strIndex, dt, strTenor);

		if (null != bpCDX) return bpCDX;

		return GetPreLoadedOnTheRun (strIndex, dt, strTenor);
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
		return org.drip.product.creator.CDXRefDataHolder._mmCDXRDBFirstCouponSeries.keySet();
	}

	/**
	 * Retrieve the comprehensive set of pre-set and pre-loaded CDX index names
	 * 
	 * @return Set of the pre-set and the pre-loaded CDX index names
	 */

	public static final Set<String> GetCDXNames()
	{
		Set<String> setstrIndex = new HashSet<String>();

		setstrIndex.addAll (GetPreLoadedIndexNames());

		setstrIndex.addAll (GetPresetIndexNames());

		return setstrIndex;
	}

	/**
	 * Return the full set of pre-set CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param strCDXName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final Map<JulianDate, Integer>
		GetPresetCDXSeriesMap (
			final String strCDXName)
	{
		if (null == strCDXName || strCDXName.isEmpty()) return null;

		return _seriesFirstCouponDateToIndexSequence.get (strCDXName);
	}

	/**
	 * Return the full set of pre-loaded CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param strCDXName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final Map<JulianDate, Integer>
		GetPreLoadedCDXSeriesMap (
			final String strCDXName)
	{
		if (null == strCDXName || strCDXName.isEmpty()) return null;

		return org.drip.product.creator.CDXRefDataHolder._mmCDXRDBFirstCouponSeries.get (strCDXName);
	}

	/**
	 * Return the full set of CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param strCDXName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final Map<JulianDate, Integer> GetCDXSeriesMap(
		final String strCDXName)
	{
		if (null == strCDXName || strCDXName.isEmpty()) return null;

		Map<JulianDate, Integer> mapFirstCouponSeries = new
			HashMap<JulianDate, Integer>();

		Map<JulianDate, Integer> mapPresetFirstCouponSeries =
			GetPresetCDXSeriesMap (strCDXName);

		if (null != mapPresetFirstCouponSeries) mapFirstCouponSeries.putAll (mapPresetFirstCouponSeries);

		Map<JulianDate, Integer> mapPreLoadedFirstCouponSeries =
			GetPreLoadedCDXSeriesMap (strCDXName);

		if (null != mapPreLoadedFirstCouponSeries)
			mapFirstCouponSeries.putAll (mapPreLoadedFirstCouponSeries);

		return mapFirstCouponSeries;
	}

	/**
	 * Retrieve the name/description map for all the pre-set CDS indices
	 * 
	 * @return Name/description map for all the pre-set CDS indices
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<String>
		GetPresetCDXDescriptions()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<String> mapCDXDescr = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<String>();

		for (Map.Entry<String, org.drip.product.params.StandardCDXParams> meCDXRefData :
			_indexParamsMap.entrySet())
			mapCDXDescr.put (meCDXRefData.getKey(), meCDXRefData.getKey());

		return mapCDXDescr;
	}

	/**
	 * Retrieve the name/description map for all the pre-loaded CDS indices
	 * 
	 * @return Name/description map for all the pre-loaded CDS indices
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<String>
		GetPreLoadedCDXDescriptions()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<String> mapCDXDescr = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<String>();

		for (Map.Entry<String, org.drip.product.params.CDXRefDataParams> meCDXRefData :
			org.drip.product.creator.CDXRefDataHolder._mapCDXRefData.entrySet())
			mapCDXDescr.put (meCDXRefData.getKey(), meCDXRefData.getValue()._strIndexName);

		return mapCDXDescr;
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

		mapCDXDescr.putAll (GetPreLoadedCDXDescriptions());

		mapCDXDescr.putAll (GetPresetCDXDescriptions());

		return mapCDXDescr;
	}
}
