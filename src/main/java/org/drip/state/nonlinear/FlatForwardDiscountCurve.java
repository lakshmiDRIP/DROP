
package org.drip.state.nonlinear;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>FlatForwardDiscountCurve</i> manages the Discounting Latent State, using the Forward Rate as the State
 * Response Representation. It exports the following functionality:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Boot Methods - Set/Bump Specific Node Quantification Metric, or Set Flat Value
 *  	</li>
 *  	<li>
 *  		Boot Calibration - Initialize Run, Compute Calibration Metric
 *  	</li>
 *  	<li>
 *  		Compute the discount factor, forward rate, or the zero rate from the Forward Rate Latent State
 *  	</li>
 *  	<li>
 *  		Create a ForwardRateEstimator instance for the given Index
 *  	</li>
 *  	<li>
 *  		Retrieve Array of the Calibration Components
 *  	</li>
 *  	<li>
 *  		Retrieve the Curve Construction Input Set
 *  	</li>
 *  	<li>
 *  		Compute the Jacobian of the Discount Factor Latent State to the input Quote
 *  	</li>
 *  	<li>
 *  		Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 *  	</li>
 *  	<li>
 *  		Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear">Non-Linear</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardDiscountCurve extends org.drip.state.discount.ExplicitBootDiscountCurve {
	private int _aiDate[] = null;
	private int _iCompoundingFreq = -1;
	private double _adblForwardRate[] = null;
	private boolean _bDiscreteCompounding = false;
	private java.lang.String _strCompoundingDayCount = "";

	protected double yearFract (
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		return _bDiscreteCompounding ? org.drip.analytics.daycount.Convention.YearFraction (iStartDate,
			iEndDate, _strCompoundingDayCount, false, null, currency()) : 1. * (iEndDate - iStartDate) /
				365.25;
	}

	private FlatForwardDiscountCurve shiftManifestMeasure (
		final double[] adblShift)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (adblShift) || null == _ccis) return null;

		org.drip.product.definition.CalibratableComponent[] aCalibInst = _ccis.components();

		org.drip.param.valuation.ValuationParams valParam = _ccis.valuationParameter();

		org.drip.param.valuation.ValuationCustomizationParams quotingParam = _ccis.quotingParameter();

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapQuote = _ccis.quoteMap();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> mapMeasures =
			_ccis.measures();

		org.drip.param.market.LatentStateFixingsContainer lsfc = _ccis.fixing();

		int iNumComp = aCalibInst.length;

		if (adblShift.length != iNumComp) return null;

		try {
			FlatForwardDiscountCurve ffdc = new FlatForwardDiscountCurve (new
				org.drip.analytics.date.JulianDate (_iEpochDate), _strCurrency, _aiDate, _adblForwardRate,
					_bDiscreteCompounding, _strCompoundingDayCount, _iCompoundingFreq);

			for (int i = 0; i < iNumComp; ++i) {
				java.lang.String strInstrumentCode = aCalibInst[i].primaryCode();

				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapInstrumentQuote =
					mapQuote.get (aCalibInst[i].primaryCode());

				java.lang.String strCalibMeasure = mapMeasures.get (strInstrumentCode)[0];

				if (null == mapInstrumentQuote || !mapInstrumentQuote.containsKey (strCalibMeasure))
					return null;

				org.drip.state.nonlinear.NonlinearCurveBuilder.DiscountCurveNode (valParam, aCalibInst[i],
					mapInstrumentQuote.get (strCalibMeasure) + adblShift[i], strCalibMeasure, false, i, ffdc,
						null, lsfc, quotingParam);
			}

			return ffdc.setCCIS (new org.drip.analytics.input.BootCurveConstructionInput (valParam,
				quotingParam, aCalibInst, mapQuote, mapMeasures, lsfc)) ? ffdc : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Boot-strap a constant forward discount curve from an array of dates and discount rates
	 * 
	 * @param dtStart Epoch Date
	 * @param strCurrency Currency
	 * @param aiDate Array of Dates
	 * @param adblForwardRate Array of Forward Rates
	 * @param bDiscreteCompounding TRUE - Compounding is Discrete
	 * @param strCompoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param iCompoundingFreq Frequency to be used for Discrete Compounding
	 * 
	 * @throws java.lang.Exception Thrown if the curve cannot be created
	 */

	public FlatForwardDiscountCurve (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblForwardRate,
		final boolean bDiscreteCompounding,
		final java.lang.String strCompoundingDayCount,
		final int iCompoundingFreq)
		throws java.lang.Exception
	{
		super (
			dtStart.julian(),
			strCurrency
		);

		if (null == aiDate || null == adblForwardRate)
			throw new java.lang.Exception ("FlatForwardDiscountCurve ctr: Invalid inputs");

		int iNumDate = aiDate.length;

		if (0 == iNumDate || iNumDate != adblForwardRate.length)
			throw new java.lang.Exception ("FlatForwardDiscountCurve ctr: Invalid inputs");

		_aiDate = new int[iNumDate];
		_iCompoundingFreq = iCompoundingFreq;
		_adblForwardRate = new double[iNumDate];
		_bDiscreteCompounding = bDiscreteCompounding;
		_strCompoundingDayCount = strCompoundingDayCount;

		for (int i = 0; i < iNumDate; ++i) {
			_aiDate[i] = aiDate[i];
			_adblForwardRate[i] = adblForwardRate[i];
		}
	}

	protected FlatForwardDiscountCurve (
		final FlatForwardDiscountCurve dc)
		throws java.lang.Exception
	{
		super (dc.epoch().julian(), dc.currency());

		_aiDate = dc._aiDate;
		_strCurrency = dc._strCurrency;
		_iEpochDate = dc._iEpochDate;
		_adblForwardRate = dc._adblForwardRate;
		_iCompoundingFreq = dc._iCompoundingFreq;
		_bDiscreteCompounding = dc._bDiscreteCompounding;
		_strCompoundingDayCount = dc._strCompoundingDayCount;
	}

	/**
	 * Retrieve the Forward Node Dates
	 * 
	 * @return The Forward Node Dates
	 */

	public int[] dates()
	{
		return _aiDate;
	}

	/**
	 * Retrieve the Forward Node Values
	 * 
	 * @return The Forward Node Values
	 */

	public double[] nodeValues()
	{
		return _adblForwardRate;
	}

	/**
	 * Retrieve the Discrete Compounding Flag
	 * 
	 * @return TRUE - Discrete Compounding
	 */

	public boolean discreteCompounding()
	{
		return _bDiscreteCompounding;
	}

	/**
	 * Retrieve the Compounding Frequency
	 * 
	 * @return The Compounding Frequency
	 */

	public int compoundingFrequency()
	{
		return _iCompoundingFreq;
	}

	/**
	 * Retrieve the Compounding Day Count
	 * 
	 * @return The Compounding Day Count
	 */

	public java.lang.String compoundingDayCount()
	{
		return _strCompoundingDayCount;
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _iEpochDate) return 1.;

		int i = 0;
		double dblDF = 1.;
		double dblExpArg = 0.;
		int iStartDate = _iEpochDate;
		int iNumDate = _aiDate.length;

		while (i < iNumDate && (int) iDate >= (int) _aiDate[i]) {
			if (_bDiscreteCompounding)
				dblDF *= java.lang.Math.pow (1. + (_adblForwardRate[i] / _iCompoundingFreq), -1. * yearFract
					(iStartDate, _aiDate[i]) * _iCompoundingFreq);
				// dblDF /= (1. + (_adblForwardRate[i] * yearFract (iStartDate, _aiDate[i])));
			else
				dblExpArg -= _adblForwardRate[i] * yearFract (iStartDate, _aiDate[i]);

			iStartDate = _aiDate[i++];
		}

		if (i >= iNumDate) i = iNumDate - 1;

		if (_bDiscreteCompounding)
			dblDF *= java.lang.Math.pow (1. + (_adblForwardRate[i] / _iCompoundingFreq), -1. * yearFract
				(iStartDate, iDate) * _iCompoundingFreq);
			// dblDF /= (1. + (_adblForwardRate[i] * yearFract (iStartDate, iDate)));
		else
			dblExpArg -= _adblForwardRate[i] * yearFract (iStartDate, iDate);

		return (_bDiscreteCompounding ? dblDF : java.lang.Math.exp (dblExpArg)) * turnAdjust (_iEpochDate,
			iDate);
	}

	@Override public double forward (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		int iStartDate = epoch().julian();

		if (iDate1 < iStartDate || iDate2 < iStartDate) return 0.;

		return 365.25 / (iDate2 - iDate1) * java.lang.Math.log (df (iDate1) / df (iDate2));
	}

	@Override public double zero (
		final int iDate)
		throws java.lang.Exception
	{
		double iStartDate = epoch().julian();

		if (iDate < iStartDate) return 0.;

		return -365.25 / (iDate - iStartDate) * java.lang.Math.log (df (iDate));
	}

	@Override public org.drip.state.forward.ForwardRateEstimator forwardRateEstimator (
		final int iDate,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		return null;
	}

	@Override public java.util.Map<java.lang.Integer, java.lang.Double> canonicalTruthness (
		final java.lang.String strLatentQuantificationMetric)
	{
		return null;
	}

	@Override public FlatForwardDiscountCurve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblShift) || null == _ccis) return null;

		org.drip.product.definition.CalibratableComponent[] aCalibInst = _ccis.components();

		int iNumComp = aCalibInst.length;
		double[] adblShift = new double[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			adblShift[i] = dblShift;

		return shiftManifestMeasure (adblShift);
	}

	@Override public FlatForwardDiscountCurve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblShift) || null == _ccis) return null;

		org.drip.product.definition.CalibratableComponent[] aCalibInst = _ccis.components();

		int iNumComp = aCalibInst.length;
		double[] adblShift = new double[iNumComp];

		if (iSpanIndex >= iNumComp) return null;

		for (int i = 0; i < iNumComp; ++i)
			adblShift[i] = i == iSpanIndex ? dblShift : 0.;

		return shiftManifestMeasure (adblShift);
	}

	@Override public org.drip.state.discount.ExplicitBootDiscountCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return shiftManifestMeasure (org.drip.analytics.support.Helper.TweakManifestMeasure
			(_adblForwardRate, rvtp));
	}

	@Override public FlatForwardDiscountCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblShift)) return null;

		int iNumDate = _adblForwardRate.length;
		double[] adblForwardRate = new double[iNumDate];

		for (int i = 0; i < iNumDate; ++i)
			adblForwardRate[i] = _adblForwardRate[i] + dblShift;

		try {
			return new FlatForwardDiscountCurve (new org.drip.analytics.date.JulianDate (_iEpochDate),
				_strCurrency, _aiDate, adblForwardRate, _bDiscreteCompounding, _strCompoundingDayCount,
					_iCompoundingFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		try {
			return new FlatForwardDiscountCurve (new org.drip.analytics.date.JulianDate (_iEpochDate),
				_strCurrency, _aiDate, org.drip.analytics.support.Helper.TweakManifestMeasure
					(_adblForwardRate, rvtp), _bDiscreteCompounding, _strCompoundingDayCount,
						_iCompoundingFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public FlatForwardDiscountCurve createBasisRateShiftedCurve (
		final int[] aiDate,
		final double[] adblBasis)
	{
		if (null == aiDate || null == adblBasis) return null;

		int iNumDate = aiDate.length;

		if (0 == iNumDate || iNumDate != adblBasis.length) return null;

		double[] adblShiftedRate = new double[iNumDate];

		try {
			for (int i = 0; i < aiDate.length; ++i)
				adblShiftedRate[i] = zero (aiDate[i]) + adblBasis[i];

			return new FlatForwardDiscountCurve (new org.drip.analytics.date.JulianDate (_iEpochDate),
				_strCurrency, aiDate, adblShiftedRate, _bDiscreteCompounding, _strCompoundingDayCount,
					_iCompoundingFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String latentStateQuantificationMetric()
	{
		return org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (iDate)) return null;

		int i = 0;
		double dblDF = java.lang.Double.NaN;
		double iStartDate = _iEpochDate;
		org.drip.numerical.differentiation.WengertJacobian wj = null;

		try {
			wj = new org.drip.numerical.differentiation.WengertJacobian (1, _adblForwardRate.length);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (iDate <= _iEpochDate) {
			if (!wj.setWengert (0, 0.)) return null;

			return wj;
		}

		try {
			if (!wj.setWengert (0, dblDF = df (iDate))) return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		while (i < _adblForwardRate.length && (int) iDate >= (int) _aiDate[i]) {
			if (!wj.accumulatePartialFirstDerivative (0, i, dblDF * (iStartDate - _aiDate[i]) / 365.25))
				return null;

			iStartDate = _aiDate[i++];
		}

		if (i >= _adblForwardRate.length) i = _adblForwardRate.length - 1;

		return wj.accumulatePartialFirstDerivative (0, i, dblDF * (iStartDate - iDate) / 365.25) ? wj :
			null;
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblForwardRate.length)
			return false;

		for (int i = iNodeIndex; i < _adblForwardRate.length; ++i)
			_adblForwardRate[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblForwardRate.length)
			return false;

		for (int i = iNodeIndex; i < _adblForwardRate.length; ++i)
			_adblForwardRate[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblForwardRate.length; ++i)
			_adblForwardRate[i] = dblValue;

		return true;
	}
}
