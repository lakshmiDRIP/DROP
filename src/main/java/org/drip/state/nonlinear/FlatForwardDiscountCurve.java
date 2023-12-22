
package org.drip.state.nonlinear;

import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.definition.Curve;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.input.BootCurveConstructionInput;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.Helper;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.discount.ExplicitBootDiscountCurve;
import org.drip.state.forward.ForwardRateEstimator;
import org.drip.state.identifier.ForwardLabel;

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
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardDiscountCurve extends ExplicitBootDiscountCurve
{
	private int _dateArray[] = null;
	private int _compoundingFrequency = -1;
	private String _compoundingDayCount = "";
	private double _forwardRateArray[] = null;
	private boolean _discreteCompounding = false;

	protected double yearFraction (
		final int startDate,
		final int endDate)
		throws Exception
	{
		return _discreteCompounding ? Convention.YearFraction (
			startDate,
			endDate,
			_compoundingDayCount,
			false,
			null,
			currency()
		) : 1. * (endDate - startDate) / 365.25;
	}

	private FlatForwardDiscountCurve shiftManifestMeasure (
		final double[] shiftArray)
	{
		if (!NumberUtil.IsValid (shiftArray) || null == _ccis) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = _ccis.components();

		ValuationParams valuationParams = _ccis.valuationParameter();

		ValuationCustomizationParams valuationCustomizationParams = _ccis.quotingParameter();

		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> quoteDoubleMap = _ccis.quoteMap();

		CaseInsensitiveTreeMap<String[]> measureArrayMap = _ccis.measures();

		LatentStateFixingsContainer latentStateFixingsContainer = _ccis.fixing();

		int calibratableComponentCount = calibratableComponentArray.length;

		if (shiftArray.length != calibratableComponentCount) {
			return null;
		}

		try {
			FlatForwardDiscountCurve flatForwardDiscountCurve = new FlatForwardDiscountCurve (
				new JulianDate (_iEpochDate),
				_strCurrency,
				_dateArray,
				_forwardRateArray,
				_discreteCompounding,
				_compoundingDayCount,
				_compoundingFrequency
			);

			for (int i = 0; i < calibratableComponentCount; ++i) {
				CaseInsensitiveTreeMap<Double> instrumentQuoteMap = quoteDoubleMap.get
					(calibratableComponentArray[i].primaryCode());

				String calibrationMeasure = measureArrayMap.get
					(calibratableComponentArray[i].primaryCode())[0];

				if (null == instrumentQuoteMap || !instrumentQuoteMap.containsKey (calibrationMeasure)) {
					return null;
				}

				NonlinearCurveBuilder.DiscountCurveNode (
					valuationParams,
					calibratableComponentArray[i],
					instrumentQuoteMap.get (calibrationMeasure) + shiftArray[i],
					calibrationMeasure,
					false,
					i,
					flatForwardDiscountCurve,
					null,
					latentStateFixingsContainer,
					valuationCustomizationParams
				);
			}

			return flatForwardDiscountCurve.setCCIS (
				new BootCurveConstructionInput (
					valuationParams,
					valuationCustomizationParams,
					calibratableComponentArray,
					quoteDoubleMap,
					measureArrayMap,
					latentStateFixingsContainer
				)
			) ? flatForwardDiscountCurve : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Boot-strap a constant forward discount curve from an array of dates and discount rates
	 * 
	 * @param startDate Epoch Date
	 * @param currency Currency
	 * @param dateArray Array of Dates
	 * @param forwardRateArray Array of Forward Rates
	 * @param discreteCompounding TRUE - Compounding is Discrete
	 * @param compoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param compoundingFrequency Frequency to be used for Discrete Compounding
	 * 
	 * @throws Exception Thrown if the curve cannot be created
	 */

	public FlatForwardDiscountCurve (
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] forwardRateArray,
		final boolean discreteCompounding,
		final String compoundingDayCount,
		final int compoundingFrequency)
		throws Exception
	{
		super (
			startDate.julian(),
			currency
		);

		if (null == dateArray || null == forwardRateArray) {
			throw new Exception ("FlatForwardDiscountCurve ctr: Invalid inputs");
		}

		int dateArrayCount = dateArray.length;

		if (0 == dateArrayCount || dateArrayCount != forwardRateArray.length) {
			throw new Exception ("FlatForwardDiscountCurve ctr: Invalid inputs");
		}

		_dateArray = new int[dateArrayCount];
		_compoundingDayCount = compoundingDayCount;
		_discreteCompounding = discreteCompounding;
		_compoundingFrequency = compoundingFrequency;
		_forwardRateArray = new double[dateArrayCount];

		for (int i = 0; i < dateArrayCount; ++i) {
			_dateArray[i] = dateArray[i];
			_forwardRateArray[i] = forwardRateArray[i];
		}
	}

	protected FlatForwardDiscountCurve (
		final FlatForwardDiscountCurve flatForwardDiscountCurve)
		throws java.lang.Exception
	{
		super (flatForwardDiscountCurve.epoch().julian(), flatForwardDiscountCurve.currency());

		_dateArray = flatForwardDiscountCurve._dateArray;
		_iEpochDate = flatForwardDiscountCurve._iEpochDate;
		_strCurrency = flatForwardDiscountCurve._strCurrency;
		_forwardRateArray = flatForwardDiscountCurve._forwardRateArray;
		_compoundingDayCount = flatForwardDiscountCurve._compoundingDayCount;
		_discreteCompounding = flatForwardDiscountCurve._discreteCompounding;
		_compoundingFrequency = flatForwardDiscountCurve._compoundingFrequency;
	}

	/**
	 * Retrieve the Forward Node Dates
	 * 
	 * @return The Forward Node Dates
	 */

	public int[] dates()
	{
		return _dateArray;
	}

	/**
	 * Retrieve the Forward Node Values
	 * 
	 * @return The Forward Node Values
	 */

	public double[] nodeValues()
	{
		return _forwardRateArray;
	}

	/**
	 * Retrieve the Discrete Compounding Flag
	 * 
	 * @return TRUE - Discrete Compounding
	 */

	public boolean discreteCompounding()
	{
		return _discreteCompounding;
	}

	/**
	 * Retrieve the Compounding Frequency
	 * 
	 * @return The Compounding Frequency
	 */

	public int compoundingFrequency()
	{
		return _compoundingFrequency;
	}

	/**
	 * Retrieve the Compounding Day Count
	 * 
	 * @return The Compounding Day Count
	 */

	public String compoundingDayCount()
	{
		return _compoundingDayCount;
	}

	@Override public double df (
		final int date)
		throws Exception
	{
		if (date <= _iEpochDate) {
			return 1.;
		}

		int i = 0;
		double discountFactor = 1.;
		int startDate = _iEpochDate;
		double exponentArgument = 0.;
		int dateArrayCount = _dateArray.length;

		while (i < dateArrayCount && (int) date >= (int) _dateArray[i]) {
			if (_discreteCompounding) {
				discountFactor *= Math.pow (
					1. + (_forwardRateArray[i] / _compoundingFrequency),
					-1. * yearFraction (startDate, _dateArray[i]) * _compoundingFrequency
				);
			} else {
				exponentArgument -= _forwardRateArray[i] * yearFraction (startDate, _dateArray[i]);
			}

			startDate = _dateArray[i++];
		}

		if (i >= dateArrayCount) {
			i = dateArrayCount - 1;
		}

		if (_discreteCompounding) {
			discountFactor *= Math.pow (
				1. + (_forwardRateArray[i] / _compoundingFrequency),
				-1. * yearFraction (startDate, date) * _compoundingFrequency
			);
		} else {
			exponentArgument -= _forwardRateArray[i] * yearFraction (startDate, date);
		}

		return (_discreteCompounding ? discountFactor : Math.exp (exponentArgument)) * turnAdjust (
			_iEpochDate,
			date
		);
	}

	@Override public double forward (
		final int date1,
		final int date2)
		throws Exception
	{
		int startDate = epoch().julian();

		return date1 < startDate || date2 < startDate ? 0. :
			365.25 / (date2 - date1) * Math.log (df (date1) / df (date2));
	}

	@Override public double zero (
		final int date)
		throws Exception
	{
		double startDate = epoch().julian();

		return date < startDate ? 0. : -365.25 / (date - startDate) * Math.log (df (date));
	}

	@Override public ForwardRateEstimator forwardRateEstimator (
		final int date,
		final ForwardLabel forwardLabel)
	{
		return null;
	}

	@Override public Map<Integer, Double> canonicalTruthness (
		final String latentQuantificationMetric)
	{
		return null;
	}

	@Override public FlatForwardDiscountCurve parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		if (!NumberUtil.IsValid (shift) || null == _ccis) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = _ccis.components();

		int calibratableComponentCount = calibratableComponentArray.length;
		double[] shiftArray = new double[calibratableComponentCount];

		for (int i = 0; i < calibratableComponentCount; ++i) {
			shiftArray[i] = shift;
		}

		return shiftManifestMeasure (shiftArray);
	}

	@Override public FlatForwardDiscountCurve shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		if (!NumberUtil.IsValid (shift) || null == _ccis) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = _ccis.components();

		int calibratableComponentCount = calibratableComponentArray.length;
		double[] shiftArray = new double[calibratableComponentCount];

		if (spanIndex >= calibratableComponentCount) {
			return null;
		}

		for (int i = 0; i < calibratableComponentCount; ++i) {
			shiftArray[i] = i == spanIndex ? shift : 0.;
		}

		return shiftManifestMeasure (shiftArray);
	}

	@Override public ExplicitBootDiscountCurve customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return shiftManifestMeasure (Helper.TweakManifestMeasure (_forwardRateArray, manifestMeasureTweak));
	}

	@Override public FlatForwardDiscountCurve parallelShiftQuantificationMetric (
		final double shift)
	{
		if (!NumberUtil.IsValid (shift)) {
			return null;
		}

		int dateCount = _forwardRateArray.length;
		double[] forwardRateArray = new double[dateCount];

		for (int i = 0; i < dateCount; ++i) {
			forwardRateArray[i] = _forwardRateArray[i] + shift;
		}

		try {
			return new FlatForwardDiscountCurve (
				new JulianDate (_iEpochDate),
				_strCurrency,
				_dateArray,
				forwardRateArray,
				_discreteCompounding,
				_compoundingDayCount,
				_compoundingFrequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public Curve customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		try {
			return new FlatForwardDiscountCurve (
				new JulianDate (_iEpochDate),
				_strCurrency,
				_dateArray,
				Helper.TweakManifestMeasure (_forwardRateArray, manifestMeasureTweak),
				_discreteCompounding,
				_compoundingDayCount,
				_compoundingFrequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public FlatForwardDiscountCurve createBasisRateShiftedCurve (
		final int[] dateArray,
		final double[] basisArray)
	{
		if (null == dateArray || null == basisArray) {
			return null;
		}

		int dateArrayCount = dateArray.length;

		if (0 == dateArrayCount || dateArrayCount != basisArray.length) {
			return null;
		}

		double[] shiftedRateArray = new double[dateArrayCount];

		try {
			for (int i = 0; i < dateArray.length; ++i) {
				shiftedRateArray[i] = zero (dateArray[i]) + basisArray[i];
			}

			return new FlatForwardDiscountCurve (
				new JulianDate (_iEpochDate),
				_strCurrency,
				dateArray,
				shiftedRateArray,
				_discreteCompounding,
				_compoundingDayCount,
				_compoundingFrequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public String latentStateQuantificationMetric()
	{
		return LatentStateStatic.DISCOUNT_QM_ZERO_RATE;
	}

	@Override public WengertJacobian jackDDFDManifestMeasure (
		final int date,
		final String manifestMeasure)
	{
		if (!NumberUtil.IsValid (date)) {
			return null;
		}

		int i = 0;
		double startDate = _iEpochDate;
		double discountFactor = Double.NaN;
		WengertJacobian wengertJacobian = null;

		try {
			wengertJacobian = new WengertJacobian (1, _forwardRateArray.length);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (date <= _iEpochDate) {
			return wengertJacobian.setWengert (0, 0.) ? wengertJacobian : null;
		}

		try {
			if (!wengertJacobian.setWengert (0, discountFactor = df (date))) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		while (i < _forwardRateArray.length && (int) date >= (int) _dateArray[i]) {
			if (!wengertJacobian.accumulatePartialFirstDerivative (
				0,
				i,
				discountFactor * (startDate - _dateArray[i]) / 365.25
			)) {
				return null;
			}

			startDate = _dateArray[i++];
		}

		if (i >= _forwardRateArray.length) {
			i = _forwardRateArray.length - 1;
		}

		return wengertJacobian.accumulatePartialFirstDerivative (
			0,
			i,
			discountFactor * (startDate - date) / 365.25
		) ? wengertJacobian : null;
	}

	@Override public boolean setNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _forwardRateArray.length) {
			return false;
		}

		for (int i = nodeIndex; i < _forwardRateArray.length; ++i) {
			_forwardRateArray[i] = value;
		}

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _forwardRateArray.length) {
			return false;
		}

		for (int i = nodeIndex; i < _forwardRateArray.length; ++i) {
			_forwardRateArray[i] += value;
		}

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _forwardRateArray.length; ++i)
			_forwardRateArray[i] = dblValue;

		return true;
	}
}
