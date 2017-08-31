
package org.drip.historical.engine;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for fixed income analysts and developers -
 * 		http://www.credit-trader.org/Begin.html
 * 
 *  DRIP is a free, full featured, fixed income rates, credit, and FX analytics library with a focus towards
 *  	pricing/valuation, risk, and market making.
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
 * HorizonChangeExplainProcessor holds the Stubs associated with the Computation of the Horizon Position
 *  Change Components for the given Product.
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class HorizonChangeExplainProcessor {
	private int _iSettleLag = -1;
	private java.lang.String _strMarketMeasureName = "";
	private org.drip.product.definition.Component _comp = null;
	private org.drip.analytics.date.JulianDate _dtFirst = null;
	private org.drip.analytics.date.JulianDate _dtSecond = null;
	private double _dblMarketMeasureValue = java.lang.Double.NaN;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcFirst = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcSecond = null;
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			_mapCSQCRollDown = null;

	protected HorizonChangeExplainProcessor (
		final org.drip.product.definition.Component comp,
		final int iSettleLag,
		final java.lang.String strMarketMeasureName,
		final double dblMarketMeasureValue,
		final org.drip.analytics.date.JulianDate dtFirst,
		final org.drip.analytics.date.JulianDate dtSecond,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqcFirst,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqcSecond,
		final
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQCRollDown)
		throws java.lang.Exception
	{
		if (null == (_comp = comp) || 0 > (_iSettleLag = iSettleLag) || null == (_strMarketMeasureName =
			strMarketMeasureName) || _strMarketMeasureName.isEmpty() ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblMarketMeasureValue = dblMarketMeasureValue) ||
					null == (_dtFirst = dtFirst) || null == (_dtSecond = dtSecond) || _dtSecond.julian() <=
						_dtFirst.julian() || null == (_csqcFirst = csqcFirst) || null == (_csqcSecond =
							csqcSecond) || null == (_mapCSQCRollDown = mapCSQCRollDown))
			throw new java.lang.Exception ("HorizonChangeExplainProcessor Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Component
	 * 
	 * @return The Component
	 */

	public org.drip.product.definition.Component component()
	{
		return _comp;
	}

	/**
	 * Retrieve the Component Settle Lag
	 * 
	 * @return The Component Settle Lag
	 */

	public int settleLag()
	{
		return _iSettleLag;
	}

	/**
	 * Retrieve the Component Market Measure Name
	 * 
	 * @return The Component Market Measure Name
	 */

	public java.lang.String marketMeasureName()
	{
		return _strMarketMeasureName;
	}

	/**
	 * Retrieve the Component Market Measure Value
	 * 
	 * @return The Component Market Measure Value
	 */

	public double marketMeasureValue()
	{
		return _dblMarketMeasureValue;
	}

	/**
	 * Retrieve the First Date of the Horizon Change
	 * 
	 * @return The First Date of the Horizon Change
	 */

	public org.drip.analytics.date.JulianDate firstDate()
	{
		return _dtFirst;
	}

	/**
	 * Retrieve the First Date's Market Parameters
	 * 
	 * @return The First Date's Market Parameters
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer firstMarketParameters()
	{
		return _csqcFirst;
	}

	/**
	 * Retrieve the Second Date of the Horizon Change
	 * 
	 * @return The Second Date of the Horizon Change
	 */

	public org.drip.analytics.date.JulianDate secondDate()
	{
		return _dtSecond;
	}

	/**
	 * Retrieve the Second Date's Market Parameters
	 * 
	 * @return The Second Date's Market Parameters
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer secondMarketParameters()
	{
		return _csqcSecond;
	}

	/**
	 * Retrieve the Map of the Roll Down Market Parameters
	 * 
	 * @return Map of the Roll Down Market Parameters
	 */

	public
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			rollDownMarketParameters()
	{
		return _mapCSQCRollDown;
	}

	/**
	 * Generate the Map of the Roll Down Market Quote Metrics
	 * 
	 * @return Map of the Roll Down Market Quote Metrics
	 */

	public org.drip.historical.engine.MarketMeasureRollDown rollDownMeasureMap()
	{
		org.drip.param.valuation.ValuationParams valParamsRollDown =
			org.drip.param.valuation.ValuationParams.Spot (_dtSecond.addBusDays (_iSettleLag,
				_comp.payCurrency()).julian());

		java.util.Set<java.lang.String> setstrRollDownTenor = _mapCSQCRollDown.keySet(); 

		org.drip.historical.engine.MarketMeasureRollDown mmrd = null;

		for (java.lang.String strRollDownTenor : setstrRollDownTenor) {
			java.util.Map<java.lang.String, java.lang.Double> mapCompMeasures = _comp.value
				(valParamsRollDown, null, _mapCSQCRollDown.get (strRollDownTenor), null);

			if (null == mapCompMeasures || !mapCompMeasures.containsKey (_strMarketMeasureName)) return null;

			if ("Native".equalsIgnoreCase (strRollDownTenor)) {
				try {
					mmrd = new org.drip.historical.engine.MarketMeasureRollDown (mapCompMeasures.get
						(_strMarketMeasureName));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			} else
				mmrd.add (strRollDownTenor, mapCompMeasures.get (_strMarketMeasureName));
		}

		return mmrd;
	}

	/**
	 * Generate the Roll Up Version of the Quote Metric
	 * 
	 * @return The Roll Up Version of the Quote Metric
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double metricRollUp()
		throws java.lang.Exception
	{
		java.util.Map<java.lang.String, java.lang.Double> mapCompMeasures = _comp.value
			(org.drip.param.valuation.ValuationParams.Spot (_dtSecond.addBusDays (_iSettleLag,
				_comp.payCurrency()).julian()), null, _csqcFirst, null);

		if (null == mapCompMeasures || !mapCompMeasures.containsKey (_strMarketMeasureName))
			throw new java.lang.Exception ("HorizonChangeExplainProcessor::metricRollUp => Invalid Inputs");

		return mapCompMeasures.get (_strMarketMeasureName);
	}

	/**
	 * Generate and Snap Relevant Fields from the First Market Valuation Parameters
	 * 
	 * @return The First Market Parameters Valuation Snapshot
	 */

	public abstract org.drip.historical.attribution.PositionMarketSnap snapFirstMarketValue();

	/**
	 * Update the Fixings (if any) to the Second Market Parameters
	 * 
	 * @return TRUE - The Fixings were successfully updated to the Second Market Parameters
	 */

	public abstract boolean updateFixings();

	/**
	 * Generate and Snap Relevant Fields from the Second Market Valuation Parameters
	 * 
	 * @return The Second Market Parameters Valuation Snapshot
	 */

	public abstract org.drip.historical.attribution.PositionMarketSnap snapSecondMarketValue();

	/**
	 * Generate the Horizon Differential Metrics Map
	 * 
	 * @param pmsFirst The First Position Market Snap
	 * @param pmsSecond The Second Position Market Snap
	 * 
	 * @return The Horizon Differential Metrics Map
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>
		crossHorizonDifferentialMetrics (
			final org.drip.historical.attribution.PositionMarketSnap pmsFirst,
			final org.drip.historical.attribution.PositionMarketSnap pmsSecond);
}
