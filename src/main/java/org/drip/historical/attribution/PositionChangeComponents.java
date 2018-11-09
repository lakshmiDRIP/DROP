
package org.drip.historical.attribution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>PositionChangeComponents</i> contains the Decomposition of the Components of the Interval Change for a
 * given Position.
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical">Historical</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/attribution">Attribution</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PositionChangeComponents {
	private boolean _bChangeTypeReturn = false;
	private double _dblAccrualChange = java.lang.Double.NaN;
	private org.drip.historical.attribution.PositionMarketSnap _pmsFirst = null;
	private org.drip.historical.attribution.PositionMarketSnap _pmsSecond = null;
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapDifferenceMetric = null;

	/**
	 * PositionChangeComponents Constructor
	 * 
	 * @param bChangeTypeReturn TRUE - Change Type is Return (Relative)
	 * @param pmsFirst The First Position Market Snapshot Instance
	 * @param pmsSecond The Second Position Market Snapshot Instance
	 * @param dblAccrualChange The Accrual Change Component of Interval Return/Change
	 * @param mapDifferenceMetric The Map of Difference Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public PositionChangeComponents (
		final boolean bChangeTypeReturn,
		final org.drip.historical.attribution.PositionMarketSnap pmsFirst,
		final org.drip.historical.attribution.PositionMarketSnap pmsSecond,
		final double dblAccrualChange,
		final org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapDifferenceMetric)
		throws java.lang.Exception
	{
		_bChangeTypeReturn = bChangeTypeReturn;
		_mapDifferenceMetric = mapDifferenceMetric;

		if (null == (_pmsFirst = pmsFirst) || null == (_pmsSecond = pmsSecond) ||
			_pmsFirst.snapDate().julian() >= _pmsSecond.snapDate().julian() ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblAccrualChange = dblAccrualChange))
			throw new java.lang.Exception ("PositionChangeComponents Constructor => Invalid Inputs!");
	}

	/**
	 * Return the Position Change Type
	 * 
	 * @return TRUE - Change Type is Return (Relative)
	 */

	public boolean changeTypeReturn()
	{
		return _bChangeTypeReturn;
	}

	/**
	 * Retrieve the Set of Manifest Measures
	 * 
	 * @return The Set of Manifest Measures
	 */

	public java.util.Set<java.lang.String> manifestMeasures()
	{
		return _pmsFirst.manifestMeasures();
	}

	/**
	 * Retrieve the First Position Market Snapshot Instance
	 * 
	 * @return The First Position Market Snapshot Instance
	 */

	public org.drip.historical.attribution.PositionMarketSnap pmsFirst()
	{
		return _pmsFirst;
	}

	/**
	 * Retrieve the Second Position Market Snapshot Instance
	 * 
	 * @return The Second Position Market Snapshot Instance
	 */

	public org.drip.historical.attribution.PositionMarketSnap pmsSecond()
	{
		return _pmsSecond;
	}

	/**
	 * Retrieve the First Date
	 * 
	 * @return The First Date
	 */

	public org.drip.analytics.date.JulianDate firstDate()
	{
		return _pmsFirst.snapDate();
	}

	/**
	 * Retrieve the Second Date
	 * 
	 * @return The Second Date
	 */

	public org.drip.analytics.date.JulianDate secondDate()
	{
		return _pmsSecond.snapDate();
	}

	/**
	 * Retrieve the Gross Interval Clean Change
	 * 
	 * @return The Gross Interval Clean Change
	 */

	public double grossCleanChange()
	{
		return _pmsSecond.marketValue() - _pmsFirst.marketValue();
	}

	/**
	 * Retrieve the Gross Interval Change
	 * 
	 * @return The Gross Interval Change
	 */

	public double grossChange()
	{
		return grossCleanChange() + _dblAccrualChange;
	}

	/**
	 * Retrieve the Specific Manifest Measure Market Realization Position Change
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return The Specific Manifest Measure Market Realization Position Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double specificMarketRealizationChange (
		final java.lang.String strManifestMeasure)
		throws java.lang.Exception
	{
		org.drip.historical.attribution.PositionManifestMeasureSnap pmmsFirst = _pmsFirst.manifestMeasureSnap
			(strManifestMeasure);

		org.drip.historical.attribution.PositionManifestMeasureSnap pmmsSecond = _pmsSecond.manifestMeasureSnap
			(strManifestMeasure);

		if (null == pmmsFirst || null == pmmsSecond)
			throw new java.lang.Exception
				("PositionChangeComponents::specificMarketRealizationChange => Invalid Inputs");

		return 0.5 * (pmmsFirst.sensitivity() + pmmsSecond.sensitivity()) * (pmmsSecond.realization() -
			pmmsFirst.realization());
	}

	/**
	 * Retrieve the Full Manifest Measure Realization Position Change
	 * 
	 * @return The Full Manifest Measure Realization Position Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double marketRealizationChange()
		throws java.lang.Exception
	{
		java.util.Set<java.lang.String> setstrManiFestMeasure = _pmsFirst.manifestMeasures();

		if (null == setstrManiFestMeasure || 0 == setstrManiFestMeasure.size())
			throw new java.lang.Exception
				("PositionChangeComponents::marketRealizationChange => No Manifest Measures");

		double dblMarketRealizationChange = 0.;

		for (java.lang.String strManifestMeasure : setstrManiFestMeasure)
			dblMarketRealizationChange += specificMarketRealizationChange (strManifestMeasure);

		return dblMarketRealizationChange;
	}

	/**
	 * Retrieve the Specific Manifest Measure Market Sensitivity Position Change
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return The Specific Manifest Measure Market Sensitivity Position Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double specificMarketSensitivityChange (
		final java.lang.String strManifestMeasure)
		throws java.lang.Exception
	{
		org.drip.historical.attribution.PositionManifestMeasureSnap pmmsFirst = _pmsFirst.manifestMeasureSnap
			(strManifestMeasure);

		org.drip.historical.attribution.PositionManifestMeasureSnap pmmsSecond =
			_pmsSecond.manifestMeasureSnap (strManifestMeasure);

		if (null == pmmsFirst || null == pmmsSecond)
			throw new java.lang.Exception
				("PositionChangeComponents::specificMarketSensitivityChange => Invalid Inputs");

		return 0.5 * (pmmsFirst.realization() + pmmsSecond.realization()) * (pmmsSecond.sensitivity() -
			pmmsFirst.sensitivity());
	}

	/**
	 * Retrieve the Full Manifest Measure Market Sensitivity Position Change
	 * 
	 * @return The Full Manifest Measure Market Sensitivity Position Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double marketSensitivityChange()
		throws java.lang.Exception
	{
		java.util.Set<java.lang.String> setstrManiFestMeasure = _pmsFirst.manifestMeasures();

		if (null == setstrManiFestMeasure || 0 == setstrManiFestMeasure.size())
			throw new java.lang.Exception
				("PositionChangeComponents::marketSensitivityChange => No Manifest Measures");

		double dblMarketSensitivityChange = 0.;

		for (java.lang.String strManifestMeasure : setstrManiFestMeasure)
			dblMarketSensitivityChange += specificMarketSensitivityChange (strManifestMeasure);

		return dblMarketSensitivityChange;
	}

	/**
	 * Retrieve the Specific Manifest Measure Market Roll-down Position Change
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return The Specific Manifest Measure Market Roll-down Position Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double specificMarketRollDownChange (
		final java.lang.String strManifestMeasure)
		throws java.lang.Exception
	{
		org.drip.historical.attribution.PositionManifestMeasureSnap pmmsFirst = _pmsFirst.manifestMeasureSnap
			(strManifestMeasure);

		if (null == pmmsFirst)
			throw new java.lang.Exception
				("PositionChangeComponents::specificMarketRollDownChange => Invalid Inputs");

		return pmmsFirst.sensitivity() * (pmmsFirst.rollDown() - pmmsFirst.realization());
	}

	/**
	 * Retrieve the Full Manifest Measure Roll-down Position Change
	 * 
	 * @return The Full Manifest Measure Roll-down Position Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double marketRollDownChange()
		throws java.lang.Exception
	{
		java.util.Set<java.lang.String> setstrManiFestMeasure = _pmsFirst.manifestMeasures();

		if (null == setstrManiFestMeasure || 0 == setstrManiFestMeasure.size())
			throw new java.lang.Exception
				("PositionChangeComponents::marketRollDownChange => No Manifest Measures");

		double dblMarketRollDownChange = 0.;

		for (java.lang.String strManifestMeasure : setstrManiFestMeasure)
			dblMarketRollDownChange += specificMarketRollDownChange (strManifestMeasure);

		return dblMarketRollDownChange;
	}

	/**
	 * Retrieve the Accrual Interval Change
	 * 
	 * @return The Accrual Interval Change
	 */

	public double accrualChange()
	{
		return _dblAccrualChange;
	}

	/**
	 * Retrieve the Explained Interval Change
	 * 
	 * @return The Explained Interval Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double explainedChange()
		throws java.lang.Exception
	{
		return marketRealizationChange() + marketRollDownChange();
	}

	/**
	 * Retrieve the Unexplained Interval Change
	 * 
	 * @return The Unexplained Interval Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double unexplainedChange()
		throws java.lang.Exception
	{
		return grossChange() - explainedChange();
	}

	/**
	 * Retrieve the Map of Difference Metrics
	 * 
	 * @return The Map of Difference Metrics
	 */

	public org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> differenceMetric()
	{
		return _mapDifferenceMetric;
	}

	/**
	 * Retrieve the Row of Header Fields
	 * 
	 * @return The Row of Header Fields
	 */

	public java.lang.String header()
	{
		java.lang.String strHeader = "FirstDate,SecondDate,TotalPnL,TotalCleanPnL,MarketShiftPnL," +
			"RollDownPnL,AccrualPnL,ExplainedPnL,UnexplainedPnL," + _pmsFirst.header ("first") +
				_pmsSecond.header ("second");

		if (null == _mapDifferenceMetric) return strHeader;

		for (java.lang.String strKey : _mapDifferenceMetric.keySet())
			strHeader = strHeader + strKey + "change,";

		return strHeader;
	}

	/**
	 * Retrieve the Row of Content Fields
	 * 
	 * @return The Row of Content Fields
	 */

	public java.lang.String content()
	{
		java.lang.String strContent = firstDate().toString() + "," + secondDate().toString() + ",";

		strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (grossChange(), 1, 8, 1.) +
			",";

		strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (grossCleanChange(), 1, 8,
			1.) + ",";

		try {
			strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble
				(marketRealizationChange(), 1, 8, 1.) + ",";

			strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (marketRollDownChange(),
				1, 8, 1.) + ",";

			strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (_dblAccrualChange, 1, 8,
				1.) + ",";

			strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (explainedChange(), 1, 8,
				1.) + ",";

			strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (unexplainedChange(), 1,
				8, 1.) + ",";
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		strContent = strContent + _pmsFirst.content() + _pmsSecond.content();

		if (null == _mapDifferenceMetric) return strContent;

		for (java.lang.String strKey : _mapDifferenceMetric.keySet())
			strContent = strContent + org.drip.quant.common.FormatUtil.FormatDouble (_mapDifferenceMetric.get
				(strKey), 1, 8, 1.) + ",";

		return strContent;
	}
}
