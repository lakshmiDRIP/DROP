
package org.drip.state.credit;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.Curve;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.EntityCDSLabel;
import org.drip.state.identifier.LatentStateLabel;

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
 * <i>CreditCurve</i> is the stub for the survival curve functionality. It extends the Curve object by
 * exposing the following functions:
 *
 *  <ul>
 *  	<li>Set of curve and market identifiers</li>
 *  	<li>Recovery to a specific date/tenor, and effective recovery between a date interval</li>
 *  	<li>Hazard Rate to a specific date/tenor, and effective hazard rate between a date interval</li>
 *  	<li>Survival to a specific date/tenor, and effective survival between a date interval</li>
 *  	<li>Set/unset date of specific default</li>
 *  	<li>Generate scenario curves from the base credit curve (flat/parallel/custom)</li>
 *  	<li>Set/unset the Curve Construction Inputs, Latent State, and the Manifest Metrics</li>
 *  	<li>Serialization/De-serialization to and from Byte Arrays</li>
 *    	<li>Set the Specific Default Date</li>
 * 		<li>Remove the Specific Default Date</li>
 * 		<li>Calculate the survival to the given date</li>
 * 		<li>Calculate the survival to the given tenor</li>
 * 		<li>Calculate the time-weighted survival between a pair of 2 dates</li>
 * 		<li>Calculate the time-weighted survival between a pair of 2 tenors</li>
 * 		<li>Calculate the recovery rate to the given date</li>
 * 		<li>Calculate the recovery rate to the given tenor</li>
 * 		<li>Calculate the time-weighted recovery between a pair of dates</li>
 * 		<li>Calculate the time-weighted recovery between a pair of tenors</li>
 * 		<li>Calculate the hazard rate between a pair of forward dates</li>
 * 		<li>Calculate the hazard rate to the given date</li>
 * 		<li>Calculate the hazard rate to the given tenor</li>
 * 		<li>Create a flat hazard curve from the inputs</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/credit/README.md">Credit Latent State Curve Representation</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class CreditCurve implements Curve
{
	private static final int NUM_DF_QUADRATURES = 5;

	protected boolean _flat = false;
	protected String _currency = "";
	protected GovvieCurve _govvieCurve = null;
	protected int _epochDate = Integer.MIN_VALUE;
	protected EntityCDSLabel _entityCDSLabel = null;
	protected double[] _calibrationQuoteArray = null;
	protected ValuationParams _valuationParams = null;
	protected String[] _calibrationMeasureArray = null;
	protected int _specificDefaultDate = Integer.MIN_VALUE;
	protected CreditPricerParams _creditPricerParams = null;
	protected MergedDiscountForwardCurve _discountCurve = null;
	protected CaseInsensitiveTreeMap<String> _measureMap = null;
	protected CalibratableComponent[] _calibratableComponentArray = null;
	protected LatentStateFixingsContainer _latentStateFixingsContainer = null;
	protected ValuationCustomizationParams _valuationCustomizationParams = null;
	protected CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> _doubleQuoteMap = null;

	protected CreditCurve (
		final int epochDate,
		final EntityCDSLabel entityCDSLabel,
		final String currency)
		throws Exception
	{
		if (!NumberUtil.IsValid (_epochDate = epochDate) || null == (_entityCDSLabel = entityCDSLabel) ||
			null == (_currency = currency) || _currency.isEmpty()) {
			throw new Exception ("CreditCurve ctr: Invalid Inputs");
		}
	}

	@Override public LatentStateLabel label()
	{
		return _entityCDSLabel;
	}

	@Override public String currency()
	{
		return _currency;
	}

	@Override public JulianDate epoch()
	{
		try {
			return new JulianDate (_epochDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Set the Specific Default Date
	 * 
	 * @param specificDefaultDate Date of Specific Default
	 * 
	 * @return TRUE if successful
	 */

	public boolean setSpecificDefault (
		final int specificDefaultDate)
	{
		_specificDefaultDate = specificDefaultDate;
		return true;
	}

	/**
	 * Remove the Specific Default Date
	 * 
	 * @return TRUE if successful
	 */

	public boolean unsetSpecificDefault()
	{
		_specificDefaultDate = Integer.MIN_VALUE;
		return true;
	}

	/**
	 * Calculate the survival to the given date
	 * 
	 * @param date Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws Exception Thrown if the survival probability cannot be calculated
	 */

	public abstract double survival (
		final int date)
		throws Exception;

	/**
	 * Calculate the survival to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double survival (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("CreditCurve::survival => Invalid Date");

		return survival (dt.julian());
	}

	/**
	 * Calculate the survival to the given tenor
	 * 
	 * @param tenor Tenor
	 * 
	 * @return Survival Probability
	 * 
	 * @throws Exception Thrown if the survival probability cannot be calculated
	 */

	public double survival (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("CreditCurve::survival => Bad tenor");
		}

		return survival (new JulianDate (_epochDate).addTenor (tenor));
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final int date1,
		final int date2)
		throws Exception
	{
		if (date1 == date2) {
			return survival (date1);
		}

		int quadratureCount = 0;
		double effectiveSurvival = 0.;
		int quadratureWidth = (date2 - date1) / NUM_DF_QUADRATURES;

		for (int date = date1; date <= date2; date += quadratureWidth) {
			++quadratureCount;

			effectiveSurvival += (survival (date) + survival (date + quadratureWidth));
		}

		return effectiveSurvival / (2. * quadratureCount);
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final JulianDate date1,
		final JulianDate date2)
		throws Exception
	{
		if (null == date1 || null == date2) {
			throw new Exception ("CreditCurve::effectiveSurvival => Invalid date");
		}

		return effectiveSurvival (date1.julian(), date2.julian());
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 tenors
	 * 
	 * @param tenor1 First tenor
	 * @param tenor2 Second tenor
	 * 
	 * @return Survival Probability
	 * 
	 * @throws Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		if (null == tenor1 || tenor1.isEmpty() || null == tenor2 || tenor2.isEmpty()) {
			throw new Exception ("CreditCurve::effectiveSurvival => bad tenor");
		}

		return effectiveSurvival (
			new JulianDate (_epochDate).addTenor (tenor1),
			new JulianDate (_epochDate).addTenor (tenor2)
		);
	}

	/**
	 * Calculate the recovery rate to the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws Exception Thrown if the Recovery rate cannot be calculated
	 */

	public abstract double recovery (
		final int date)
		throws Exception;

	/**
	 * Calculate the recovery rate to the given date
	 * 
	 * @param date Date
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws Exception Thrown if the Recovery rate cannot be calculated
	 */

	public double recovery (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("CreditCurve::recovery => Invalid Date");
		}

		return recovery (date.julian());
	}

	/**
	 * Calculate the recovery rate to the given tenor
	 * 
	 * @param tenor Tenor
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws Exception Thrown if the Recovery rate cannot be calculated
	 */

	public double recovery (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("CreditCurve::recovery => Invalid Tenor");
		}

		return recovery (new JulianDate (_epochDate).addTenor (tenor));
	}

	/**
	 * Calculate the time-weighted recovery between a pair of dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final int date1,
		final int date2)
		throws Exception
	{
		if (date1 == date2) {
			return recovery (date1);
		}

		int quadratureCount = 0;
		double effectiveRecovery = 0.;
		int quadratureWidth = (date2 - date1) / NUM_DF_QUADRATURES;

		if (0 == quadratureWidth) {
			quadratureWidth = 1;
		}

		for (int date = date1; date <= date2; date += quadratureWidth) {
			++quadratureCount;

			effectiveRecovery += (recovery (date) + recovery (date + quadratureWidth));
		}

		return effectiveRecovery / (2. * quadratureCount);
	}

	/**
	 * Calculate the time-weighted recovery between a pair of dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final JulianDate date1,
		final JulianDate date2)
		throws Exception
	{
		if (null == date1 || null == date2) {
			throw new Exception ("CreditCurve::effectiveRecovery => Invalid date");
		}

		return effectiveRecovery (date1.julian(), date2.julian());
	}

	/**
	 * Calculate the time-weighted recovery between a pair of tenors
	 * 
	 * @param tenor1 First Tenor
	 * @param tenor2 Second Tenor
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		if (null == tenor1 || tenor1.isEmpty() || null == tenor2 || tenor2.isEmpty()) {
			throw new Exception ("CreditCurve::effectiveRecovery => Invalid tenor");
		}

		return effectiveRecovery (
			new JulianDate (_epochDate).addTenor (tenor1),
			new JulianDate (_epochDate).addTenor (tenor2)
		);
	}

	/**
	 * Calculate the hazard rate between a pair of forward dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final JulianDate date1,
		final JulianDate date2)
		throws Exception
	{
		if (null == date1 || null == date2) {
			throw new Exception ("CreditCurve::hazard => Invalid dates");
		}

		if (date1.julian() < _epochDate || date2.julian() < _epochDate) return 0.;

		return date1.julian() < _epochDate || date2.julian() < _epochDate ? 0. :
			365.25 / (date2.julian() - date1.julian()) * Math.log (survival (date1) / survival (date2));
	}

	/**
	 * Calculate the hazard rate to the given date
	 * 
	 * @param date Date
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final JulianDate date)
		throws Exception
	{
		return hazard (date, new JulianDate (_epochDate));
	}

	/**
	 * Calculate the hazard rate to the given tenor
	 * 
	 * @param tenor Tenor
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("CreditCurve::hazard => Bad Tenor");
		}

		return hazard (new JulianDate (_epochDate).addTenor (tenor));
	}

	/**
	 * Create a flat hazard curve from the inputs
	 * 
	 * @param flatNodeValue Flat hazard node value
	 * @param singleNode Uses a single node for Calibration (True)
	 * @param recovery (Optional) Recovery to be used in creation of the flat curve
	 * 
	 * @return New CreditCurve instance
	 */

	public abstract CreditCurve flatCurve (
		final double flatNodeValue,
		final boolean singleNode,
		final double recovery
	);

	/**
	 * Set the calibration inputs for the CreditCurve
	 * 
	 * @param valuationParams Valuation Params
	 * @param flat Flat calibration desired (True)
	 * @param discountCurve Base Discount Curve
	 * @param govvieCurve Govvie Curve
	 * @param creditPricerParams PricerParams
	 * @param calibratableComponentArray Array of calibration instruments
	 * @param calibrationQuoteArray Array of calibration quotes
	 * @param calibrationMeasureArray Array of calibration measures
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Quoting Parameters
	 */

	public void setInstrCalibInputs (
		final ValuationParams valuationParams,
		final boolean flat,
		final MergedDiscountForwardCurve discountCurve,
		final GovvieCurve govvieCurve,
		final CreditPricerParams creditPricerParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final org.drip.param.valuation.ValuationCustomizationParams valuationCustomizationParams)
	{
		_flat = flat;
		_govvieCurve = govvieCurve;
		_discountCurve = discountCurve;
		_valuationParams = valuationParams;
		_creditPricerParams = creditPricerParams;
		_calibrationQuoteArray = calibrationQuoteArray;
		_calibrationMeasureArray = calibrationMeasureArray;
		_calibratableComponentArray = calibratableComponentArray;
		_latentStateFixingsContainer = latentStateFixingsContainer;
		_valuationCustomizationParams = valuationCustomizationParams;

		_measureMap = new CaseInsensitiveTreeMap<String>();

		_doubleQuoteMap = new CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>();

		for (int componentIndex = 0; componentIndex < calibratableComponentArray.length; ++componentIndex) {
			_measureMap.put (
				_calibratableComponentArray[componentIndex].primaryCode(),
				calibrationMeasureArray[componentIndex]
			);

			CaseInsensitiveTreeMap<Double> manifestMeasureCalibrationQuoteMap =
				new CaseInsensitiveTreeMap<Double>();

			manifestMeasureCalibrationQuoteMap.put (
				_calibrationMeasureArray[componentIndex],
				calibrationQuoteArray[componentIndex]
			);

			_doubleQuoteMap.put (
				_calibratableComponentArray[componentIndex].primaryCode(),
				manifestMeasureCalibrationQuoteMap
			);

			String[] secondaryCodeArray = _calibratableComponentArray[componentIndex].secondaryCode();

			if (null != secondaryCodeArray) {
				for (int secondaryCodeIndex = 0; secondaryCodeIndex < secondaryCodeArray.length;
					++secondaryCodeIndex) {
					_doubleQuoteMap.put (
						secondaryCodeArray[secondaryCodeIndex],
						manifestMeasureCalibrationQuoteMap
					);
				}
			}
		}
	}

	@Override public boolean setCCIS (
		final CurveConstructionInputSet curveConstructionInputSet)
	{
		return false;
	}

	@Override public CalibratableComponent[] calibComp()
	{
		return _calibratableComponentArray;
	}

	@Override public CaseInsensitiveTreeMap<Double> manifestMeasure (
		final String instrument)
	{
		return null == _doubleQuoteMap || 0 == _doubleQuoteMap.size() ||
			null == instrument || instrument.isEmpty() || !_doubleQuoteMap.containsKey (instrument) ?
			null : _doubleQuoteMap.get (instrument);
	}
}
