
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * StandardCDXManager implements the creation and the static details of the all the NA, EU, SovX, EMEA, and
 *  ASIA standardized CDS indices. It exposes the following functionality:
 *  - Retrieve the full set of pre-set/pre-loaded CDX names/descriptions.
 *  - Retrieve all the CDX's given an index name.
 *  - Get the index, index series, and the effective/maturity dates for a given CDX.
 *  - Get all the on-the-runs for an index, date, and tenor.
 *  - Retrieve the full basket product corresponding to NA/EU/ASIA IG/HY/EM and other available standard CDX.
 *  - Build a custom CDX product.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class StandardCDXManager {
	private static org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.product.definition.BasketProduct>
		_mapStandardCDX = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.product.definition.BasketProduct>();

	private static org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.product.params.StandardCDXParams>
		_mapStandardCDXParams = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.product.params.StandardCDXParams>();

	private static
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.util.Map<org.drip.analytics.date.JulianDate,
			java.lang.Integer>> _mmIndexFirstCouponSeries = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.util.Map<org.drip.analytics.date.JulianDate,
					java.lang.Integer>>();

	private static org.drip.analytics.support.CaseInsensitiveTreeMap<java.util.Map<java.lang.Integer,
		org.drip.analytics.date.JulianDate>> _mmIndexSeriesFirstCoupon = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.util.Map<java.lang.Integer,
				org.drip.analytics.date.JulianDate>>();

	private static final boolean SetupStandardCDXParams()
	{
		try {
			_mapStandardCDXParams.put ("CDX.NA.IG", new org.drip.product.params.StandardCDXParams (125,
				"USD", 0.01));

			_mapStandardCDXParams.put ("CDX.NA.HY", new org.drip.product.params.StandardCDXParams (100,
				"USD", 0.05));

			_mapStandardCDXParams.put ("CDX.NA.HVOL", new org.drip.product.params.StandardCDXParams (30,
				"USD", 0.01));

			_mapStandardCDXParams.put ("CDX.NA.HIVOL", new org.drip.product.params.StandardCDXParams (30,
				"USD", 0.01));

			_mapStandardCDXParams.put ("CDX.NA.XO", new org.drip.product.params.StandardCDXParams (35, "USD",
				0.034));

			_mapStandardCDXParams.put ("CDX.NA.HY.BB", new org.drip.product.params.StandardCDXParams (40,
				"USD", 0.05));

			_mapStandardCDXParams.put ("CDX.NA.HY.B", new org.drip.product.params.StandardCDXParams (37,
				"USD", 0.05));

			_mapStandardCDXParams.put ("ITRX.EU.IG", new org.drip.product.params.StandardCDXParams (125,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRAXX.EU.IG", new org.drip.product.params.StandardCDXParams (125,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRX.EU.HVOL", new org.drip.product.params.StandardCDXParams (30,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRAXX.EU.HVOL", new org.drip.product.params.StandardCDXParams (30,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRX.EU.XO", new org.drip.product.params.StandardCDXParams (50,
				"EUR", 0.05));

			_mapStandardCDXParams.put ("ITRAXX.EU.XO", new org.drip.product.params.StandardCDXParams (50,
				"EUR", 0.05));

			_mapStandardCDXParams.put ("ITRX.EU.NONFIN", new org.drip.product.params.StandardCDXParams (50,
				"EUR", 0.05));

			_mapStandardCDXParams.put ("ITRAXX.EU.NONFIN", new org.drip.product.params.StandardCDXParams (50,
				"EUR", 0.05));

			_mapStandardCDXParams.put ("ITRX.EU.FINSNR", new org.drip.product.params.StandardCDXParams (20,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRAXX.EU.FINSNR", new org.drip.product.params.StandardCDXParams (20,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRX.EU.FINSUB", new org.drip.product.params.StandardCDXParams (20,
				"EUR", 0.01));

			_mapStandardCDXParams.put ("ITRAXX.EU.FINSUB", new org.drip.product.params.StandardCDXParams (20,
				"EUR", 0.01));

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static final boolean UpdateIndexMaps (
		final java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapDateSeries,
		final java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> mapSeriesDate,
		final org.drip.analytics.date.JulianDate dt,
		final int iSeries)
	{
		mapDateSeries.put (dt, iSeries);

		mapSeriesDate.put (iSeries, dt);

		return true;
	}

	private static final boolean PresetNA_IG_HY_HVOL_HYBB_HYBSeries()
	{
		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapDateSeries = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Integer>();

		java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> mapSeriesDate = new
			java.util.TreeMap<java.lang.Integer, org.drip.analytics.date.JulianDate>();

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2014, 6, 20), 22);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 12, 20), 21);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 6, 20), 20);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 12, 20), 19);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 6, 20), 18);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 12, 20), 17);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 6, 20), 16);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 12, 20), 15);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 6, 20), 14);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 12, 20), 13);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 6, 20), 12);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 12, 20), 11);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 6, 20), 10);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 12, 20), 9);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 6, 20), 8);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 12, 20), 7);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 6, 20), 6);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2005, 12, 20), 5);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2005, 6, 20), 4);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2004, 12, 20), 3);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2004, 6, 20), 2);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2003, 12, 20), 1);

		_mmIndexFirstCouponSeries.put ("CDX.NA.IG", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.IG", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("CDX.NA.HY", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.HY", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("CDX.NA.HVOL", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.HVOL", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("CDX.NA.HIVOL", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.HIVOL", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("CDX.NA.HY.B", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.HY.B", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("CDX.NA.HY.BB", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.HY.BB", mapSeriesDate);

		return true;
	}

	private static final boolean PresetNAXOSeries()
	{
		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapDateSeries = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Integer>();

		java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> mapSeriesDate = new
			java.util.TreeMap<java.lang.Integer, org.drip.analytics.date.JulianDate>();

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2014, 6, 20), 16);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 12, 20), 15);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 6, 20), 14);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 12, 20), 13);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 6, 20), 12);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 12, 20), 11);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 6, 20), 10);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 12, 20), 9);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 6, 20), 8);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 12, 20), 7);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 6, 20), 6);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 12, 20), 5);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 6, 20), 4);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 12, 20), 3);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 6, 20), 2);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 12, 20), 1);

		_mmIndexFirstCouponSeries.put ("CDX.NA.XO", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.NA.XO", mapSeriesDate);

		return true;
	}

	private static final boolean PresetEMSeries()
	{
		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapDateSeries = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Integer>();

		java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> mapSeriesDate = new
			java.util.TreeMap<java.lang.Integer, org.drip.analytics.date.JulianDate>();

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2014, 6, 20), 21);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 12, 20), 20);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 6, 20), 19);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 12, 20), 18);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 6, 20), 17);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 12, 20), 16);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 6, 20), 15);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 12, 20), 14);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 6, 20), 13);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 12, 20), 12);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 6, 20), 11);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 12, 20), 10);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 6, 20), 9);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 12, 20), 8);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 6, 20), 7);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 12, 20), 6);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 6, 20), 5);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2005, 12, 20), 4);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2005, 6, 20), 3);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2004, 12, 20), 2);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2004, 6, 20), 1);

		_mmIndexFirstCouponSeries.put ("CDX.EM", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("CDX.EM", mapSeriesDate);

		return true;
	}

	private static final boolean PresetEUSeries()
	{
		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapDateSeries = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Integer>();

		java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> mapSeriesDate = new
			java.util.TreeMap<java.lang.Integer, org.drip.analytics.date.JulianDate>();

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2014, 6, 20), 20);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 12, 20), 19);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2013, 6, 20), 18);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 12, 20), 17);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2012, 6, 20), 16);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 12, 20), 15);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2011, 6, 20), 14);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 12, 20), 13);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2010, 6, 20), 12);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 12, 20), 11);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2009, 6, 20), 10);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 12, 20), 9);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2008, 6, 20), 8);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 12, 20), 7);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2007, 6, 20), 6);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 12, 20), 5);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2006, 6, 20), 4);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2005, 12, 20), 3);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2005, 6, 20), 2);

		UpdateIndexMaps (mapDateSeries, mapSeriesDate, org.drip.analytics.date.DateUtil.CreateFromYMD
			(2004, 12, 20), 1);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.IG", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.IG", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.HVOL", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.HVOL", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.HIVOL", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.HIVOL", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.XO", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.XO", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.FINSNR", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.FINSNR", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.FINSUB", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.FINSUB", mapSeriesDate);

		_mmIndexFirstCouponSeries.put ("ITRX.EU.NONFIN", mapDateSeries);

		_mmIndexSeriesFirstCoupon.put ("ITRX.EU.NONFIN", mapSeriesDate);

		return true;
	}

	public static final boolean InitStandardCDXSeries()
	{
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

		if (!SetupStandardCDXParams()) {
			System.out.println ("Cannot setup standard CDX Params!");

			return false;
		}

		return org.drip.product.creator.CDXRefDataHolder.InitFullCDXRefDataSet();
	}

	private static final org.drip.product.definition.BasketProduct ConstructCDX (
		final java.lang.String strTenor,
		final org.drip.analytics.date.JulianDate dtFirstCoupon,
		final double dblCoupon,
		final java.lang.String strIR,
		final int iNumComponents,
		final java.lang.String strCDXName)
	{
		java.lang.String[] astrCC = new java.lang.String[iNumComponents];

		for (int i = 0; i < iNumComponents; ++i)
			astrCC[i] = "CC" + (i + 1);

		return org.drip.product.creator.CDSBasketBuilder.MakeCDX (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, strIR, astrCC, strCDXName);
	}

	private static final org.drip.product.definition.BasketProduct ConstructCDXEM (
		final java.lang.String strTenor,
		final org.drip.analytics.date.JulianDate dtFirstCoupon,
		final java.lang.String strCDXName)
	{
		return org.drip.product.creator.CDSBasketBuilder.MakeCDX (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), 0.05, "USD", new java.lang.String[] {"ARG", "VEN", "BRA",
				"MAL", "COL", "HUN", "IND", "PAN", "PER", "SAF", "PHI", "TUR", "RUS", "UKR", "MEX"}, new
					double[] {0.06, 0.08, 0.13, 0.04, 0.08, 0.03, 0.05, 0.03, 0.05, 0.03, 0.06, 0.11, 0.13,
						0.03, 0.09}, strCDXName);
	}

	private static final org.drip.product.definition.BasketProduct MakePresetStandardCDX (
		final java.lang.String strIndex,
		final int iSeries,
		final java.lang.String strTenor)
	{
		if (null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.product.params.CDXIdentifier cdxID = null;

		try {
			cdxID = new org.drip.product.params.CDXIdentifier (iSeries, 1, strIndex, strTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		if (null == cdxID) return null;

		java.lang.String strCDXCode = cdxID.getCode();

		if (null == strCDXCode || strCDXCode.isEmpty()) return null;

		org.drip.product.definition.BasketProduct cdx = _mapStandardCDX.get (strCDXCode);

		if (null != cdx) return cdx;

		java.util.Map<java.lang.Integer, org.drip.analytics.date.JulianDate> mapSeriesFirstCoupon =
			_mmIndexSeriesFirstCoupon.get (strIndex);

		if (null == mapSeriesFirstCoupon) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = mapSeriesFirstCoupon.get (iSeries);

		if (null == dtFirstCoupon) return null;

		if ("CDX.EM".equalsIgnoreCase (strIndex))
			cdx = ConstructCDXEM (strTenor, dtFirstCoupon, strCDXCode);
		else {
			org.drip.product.params.StandardCDXParams cdxParams = _mapStandardCDXParams.get (strIndex);

			if (null != cdxParams)
				cdx = ConstructCDX (strTenor, dtFirstCoupon, cdxParams._dblCoupon, cdxParams._strCurrency,
					cdxParams._iNumComponents, strCDXCode);
		}

		_mapStandardCDX.put (strCDXCode, cdx);

		return cdx;
	}

	private static final org.drip.product.definition.BasketProduct MakePreLoadedStandardCDX (
		final java.lang.String strIndex,
		final int iSeries,
		final java.lang.String strTenor)
	{
		if (null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.product.params.CDXIdentifier cdxID = null;

		try {
			cdxID = new org.drip.product.params.CDXIdentifier (iSeries, 1, strIndex, strTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		if (null == cdxID) return null;

		java.lang.String strCDXCode = cdxID.getCode();

		if (null == strCDXCode || strCDXCode.isEmpty()) return null;

		org.drip.product.params.CDXRefDataParams cdxrdb =
			org.drip.product.creator.CDXRefDataHolder._mapCDXRefData.get (strCDXCode);

		if (null == cdxrdb) return null;

		java.lang.String[] astrCC = new java.lang.String[cdxrdb._iOriginalComponentCount];

		for (int i = 0; i < cdxrdb._iOriginalComponentCount; ++i)
			astrCC[i] = "CC" + (i + 1);

		return org.drip.product.creator.CDSBasketBuilder.MakeCDX (cdxrdb._dtMaturity.subtractTenor
			(cdxrdb._iIndexLifeSpan + "Y"), cdxrdb._dtMaturity, cdxrdb._dblCoupon, cdxrdb._strCurrency,
				astrCC, cdxrdb._strIndexClass + "." + cdxrdb._strIndexGroupName + "." +
					cdxrdb._iIndexLifeSpan + "Y." + cdxrdb._iIndexSeries + "." + cdxrdb._iIndexVersion);
	}

	private static final org.drip.product.definition.BasketProduct GetPresetOnTheRun (
		final java.lang.String strIndex,
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strTenor)
	{
		if (null == dt || null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty())
			return null;

		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapFirstCouponSeries =
			_mmIndexFirstCouponSeries.get (strIndex);

		org.drip.analytics.date.JulianDate dtFirstCoupon = dt.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries) return null;

		if (null == mapFirstCouponSeries.get (dtFirstCoupon))
			dtFirstCoupon = dtFirstCoupon.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries.get (dtFirstCoupon)) return null;

		return MakePresetStandardCDX (strIndex, mapFirstCouponSeries.get (dtFirstCoupon), strTenor);
	}

	private static final org.drip.product.definition.BasketProduct GetPreLoadedOnTheRun (
		final java.lang.String strIndex,
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strTenor)
	{
		if (null == dt || null == strIndex || strIndex.isEmpty() || null == strTenor || strTenor.isEmpty())
			return null;

		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapFirstCouponSeries =
			org.drip.product.creator.CDXRefDataHolder._mmCDXRDBFirstCouponSeries.get (strIndex);

		org.drip.analytics.date.JulianDate dtFirstCoupon = dt.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries) return null;

		if (null == mapFirstCouponSeries.get (dtFirstCoupon))
			dtFirstCoupon = dtFirstCoupon.nextCreditIMM (3);

		if (null == dtFirstCoupon || null == mapFirstCouponSeries.get (dtFirstCoupon)) return null;

		return MakePreLoadedStandardCDX (strIndex, mapFirstCouponSeries.get (dtFirstCoupon), strTenor);
	}

	private static final boolean DumpIndexDetails (
		final java.lang.String strCDXCoverageFile)
	{
		if (null == strCDXCoverageFile || strCDXCoverageFile.isEmpty()) return false;

		java.io.BufferedWriter bw = null;

		try {
			bw = new java.io.BufferedWriter (new java.io.FileWriter (strCDXCoverageFile));

			bw.write ("\n , Index Name, Description, Issue Date, Maturity Date, Frequency, Coupon\n");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			try {
				bw.close();
			} catch (java.lang.Exception e1) {
				e1.printStackTrace();
			}

			return false;
		}

		for (java.util.Map.Entry<java.lang.String, org.drip.product.params.CDXRefDataParams> meCDXRefData :
			org.drip.product.creator.CDXRefDataHolder._mapCDXRefData.entrySet()) {
			org.drip.product.params.CDXRefDataParams cdxrdb = meCDXRefData.getValue();

			if (null == cdxrdb) continue;

			java.lang.String strIndexDetails = " , " + meCDXRefData.getKey() + ", " + cdxrdb._strIndexName +
				", " + cdxrdb._dtIssue + ", " + cdxrdb._dtMaturity + ", " + cdxrdb._iFrequency + ", " + (int)
					(10000. * cdxrdb._dblCoupon) + "\n";

			try {
				bw.write (strIndexDetails);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				try {
					bw.close();
				} catch (java.lang.Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		try {
			bw.close();
		} catch (java.lang.Exception e) {
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
		final java.lang.String strIndex,
		final int iSeries,
		final java.lang.String strTenor)
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
		final java.lang.String strIndex,
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strTenor)
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

	public static final java.util.Set<java.lang.String> GetPresetIndexNames()
	{
		return _mmIndexFirstCouponSeries.keySet();
	}

	/**
	 * Retrieve a set of all the pre-loaded CDX index names
	 * 
	 * @return Set of the pre-loaded CDX index names
	 */

	public static final java.util.Set<java.lang.String> GetPreLoadedIndexNames()
	{
		return org.drip.product.creator.CDXRefDataHolder._mmCDXRDBFirstCouponSeries.keySet();
	}

	/**
	 * Retrieve the comprehensive set of pre-set and pre-loaded CDX index names
	 * 
	 * @return Set of the pre-set and the pre-loaded CDX index names
	 */

	public static final java.util.Set<java.lang.String> GetCDXNames()
	{
		java.util.Set<java.lang.String> setstrIndex = new java.util.HashSet<java.lang.String>();

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

	public static final java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer>
		GetPresetCDXSeriesMap (
			final java.lang.String strCDXName)
	{
		if (null == strCDXName || strCDXName.isEmpty()) return null;

		return _mmIndexFirstCouponSeries.get (strCDXName);
	}

	/**
	 * Return the full set of pre-loaded CDX series/first coupon date pairs for the given CDX
	 * 
	 * @param strCDXName CDX Name
	 * 
	 * @return Map of the CDX series/first coupon dates
	 */

	public static final java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer>
		GetPreLoadedCDXSeriesMap (
			final java.lang.String strCDXName)
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

	public static final java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> GetCDXSeriesMap(
		final java.lang.String strCDXName)
	{
		if (null == strCDXName || strCDXName.isEmpty()) return null;

		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapFirstCouponSeries = new
			java.util.HashMap<org.drip.analytics.date.JulianDate, java.lang.Integer>();

		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapPresetFirstCouponSeries =
			GetPresetCDXSeriesMap (strCDXName);

		if (null != mapPresetFirstCouponSeries) mapFirstCouponSeries.putAll (mapPresetFirstCouponSeries);

		java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Integer> mapPreLoadedFirstCouponSeries =
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

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>
		GetPresetCDXDescriptions()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCDXDescr = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, org.drip.product.params.StandardCDXParams> meCDXRefData :
			_mapStandardCDXParams.entrySet())
			mapCDXDescr.put (meCDXRefData.getKey(), meCDXRefData.getKey());

		return mapCDXDescr;
	}

	/**
	 * Retrieve the name/description map for all the pre-loaded CDS indices
	 * 
	 * @return Name/description map for all the pre-loaded CDS indices
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>
		GetPreLoadedCDXDescriptions()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCDXDescr = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, org.drip.product.params.CDXRefDataParams> meCDXRefData :
			org.drip.product.creator.CDXRefDataHolder._mapCDXRefData.entrySet())
			mapCDXDescr.put (meCDXRefData.getKey(), meCDXRefData.getValue()._strIndexName);

		return mapCDXDescr;
	}

	/**
	 * Retrieve the name/description map for all the CDS indices
	 * 
	 * @return Name/description map for all the CDS indices
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>
		GetCDXDescriptions()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCDXDescr = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		mapCDXDescr.putAll (GetPreLoadedCDXDescriptions());

		mapCDXDescr.putAll (GetPresetCDXDescriptions());

		return mapCDXDescr;
	}

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		if (!InitStandardCDXSeries()) System.out.println ("Cannot initialize InitStandardCDXSeries!");

		DumpIndexDetails ("C:\\CreditAnalytics\\docs\\CDXCoverage.csv");
	}
}
