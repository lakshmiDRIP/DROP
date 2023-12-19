
package org.drip.state.nonlinear;

import org.drip.analytics.definition.Curve;
import org.drip.analytics.support.Helper;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.definition.CreditManifestMeasureTweak;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.creator.ScenarioCreditCurveBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.credit.ExplicitBootCreditCurve;
import org.drip.state.identifier.EntityCDSLabel;

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
 * <i>ForwardHazardCreditCurve</i> manages the Survival Latent State, using the Hazard Rate as the State
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
 *  		Compute the survival probability, recovery rate, or the hazard rate from the Hazard Rate Latent
 *  			State
 *  	</li>
 *  	<li>
 *  		Retrieve Array of the Calibration Components
 *  	</li>
 *  	<li>
 *  		Retrieve the Curve Construction Input Set
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

public class ForwardHazardCreditCurve extends ExplicitBootCreditCurve
{
	private int[] _hazardDateArray = null;
	private int[] _recoveryDateArray = null;
	private double[] _hazardRateArray = null;
	private double[] _recoveryRateArray = null;

	private CreditCurve createFromBaseMMTP (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		double[] bumpedHazardRate = Helper.TweakManifestMeasure (_hazardRateArray, manifestMeasureTweak);

		if (null == bumpedHazardRate || _hazardRateArray.length != bumpedHazardRate.length) {
			return null;
		}

		try {
			return new ForwardHazardCreditCurve (
				_iEpochDate,
				_label,
				_strCurrency,
				bumpedHazardRate,
				_hazardDateArray,
				_recoveryRateArray,
				_recoveryDateArray,
				_iSpecificDefaultDate
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a credit curve from hazard rate and recovery rate term structures
	 * 
	 * @param startDate Curve Epoch date
	 * @param entityCDSLabel Credit Curve Label
	 * @param currency Currency
	 * @param hazardRateArray Matched array of hazard rates
	 * @param hazardDateArray Matched array of hazard dates
	 * @param recoveryRateArray Matched array of recovery rates
	 * @param recoveryDateArray Matched array of recovery dates
	 * @param specificDefaultDate (Optional) Specific Default Date
	 * 
	 * @throws Exception Thrown if inputs are invalid
	 */

	public ForwardHazardCreditCurve (
		final int startDate,
		final EntityCDSLabel entityCDSLabel,
		final String currency,
		final double[] hazardRateArray,
		final int[] hazardDateArray,
		final double[] recoveryRateArray,
		final int[] recoveryDateArray,
		final int specificDefaultDate)
		throws Exception
	{
		super (startDate, entityCDSLabel, currency);

		if (null == hazardRateArray || 0 == hazardRateArray.length ||
			null == hazardDateArray || 0 == hazardDateArray.length ||
			hazardRateArray.length != hazardDateArray.length ||
			null == recoveryRateArray || 0 == recoveryRateArray.length ||
			null == recoveryDateArray || 0 == recoveryDateArray.length ||
			recoveryRateArray.length != recoveryDateArray.length) {
			throw new Exception ("ForwardHazardCreditCurve ctr: Invalid Params!");
		}

		_iSpecificDefaultDate = specificDefaultDate;
		_hazardDateArray = new int[hazardDateArray.length];
		_hazardRateArray = new double[hazardRateArray.length];
		_recoveryDateArray = new int[recoveryDateArray.length];
		_recoveryRateArray = new double[recoveryRateArray.length];

		for (int i = 0; i < hazardRateArray.length; ++i) {
			_hazardRateArray[i] = hazardRateArray[i];
		}

		for (int i = 0; i < _hazardDateArray.length; ++i) {
			_hazardDateArray[i] = hazardDateArray[i];
		}

		for (int i = 0; i < recoveryRateArray.length; ++i) {
			_recoveryRateArray[i] = recoveryRateArray[i];
		}

		for (int i = 0; i < recoveryDateArray.length; ++i) {
			_recoveryDateArray[i] = recoveryDateArray[i];
		}
	}

	@Override public double survival (
		final int date)
		throws Exception
	{
		if (date <= _iEpochDate) return 1.;

		if (Integer.MIN_VALUE != _iSpecificDefaultDate && date >= _iSpecificDefaultDate) {
			return 0.;
		}

		int i = 0;
		int startDate = _iEpochDate;
		double exponentialArgument = 0.;

		while (i < _hazardRateArray.length && date > _hazardDateArray[i]) {
			exponentialArgument -= _hazardRateArray[i] * (_hazardDateArray[i] - startDate);
			startDate = _hazardDateArray[i++];
		}

		if (i >= _hazardRateArray.length) {
			i = _hazardRateArray.length - 1;
		}

		exponentialArgument -= _hazardRateArray[i] * (date - startDate);

		return Math.exp (exponentialArgument / 365.25);
	}

	@Override public double recovery (
		final int date)
		throws Exception
	{
		for (int i = 0; i < _recoveryDateArray.length; ++i) {
			if (date < _recoveryDateArray[i]) {
				return _recoveryRateArray[i];
			}
		}

		return _recoveryRateArray[_recoveryDateArray.length - 1];
	}

	@Override public ForwardHazardCreditCurve parallelShiftQuantificationMetric (
		final double shift)
	{
		if (!NumberUtil.IsValid (shift)) {
			return null;
		}

		double[] hazardRateArray = new double[_hazardRateArray.length];

		for (int i = 0; i < _hazardRateArray.length; ++i) {
			hazardRateArray[i] = _hazardRateArray[i] + shift;
		}

		try {
			return new ForwardHazardCreditCurve (
				_iEpochDate,
				_label,
				_strCurrency,
				hazardRateArray,
				_hazardDateArray,
				_recoveryRateArray,
				_recoveryDateArray,
				_iSpecificDefaultDate
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public Curve customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public ForwardHazardCreditCurve parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		if (!NumberUtil.IsValid (shift)) {
			return null;
		}

		if (null == _valParam || null == _aCalibInst || 0 == _aCalibInst.length ||
			null == _adblCalibQuote || 0 == _adblCalibQuote.length ||
			null == _astrCalibMeasure || 0 == _astrCalibMeasure.length ||
			_astrCalibMeasure.length != _adblCalibQuote.length ||
			_adblCalibQuote.length != _aCalibInst.length) {
			return parallelShiftQuantificationMetric (shift);
		}

		ForwardHazardCreditCurve creditCurve = null;
		double[] calibrationQuoteArray = new double[_adblCalibQuote.length];

		try {
			creditCurve = new ForwardHazardCreditCurve (
				_iEpochDate,
				_label,
				_strCurrency,
				_hazardRateArray,
				_hazardDateArray,
				_recoveryRateArray,
				_recoveryDateArray,
				_iSpecificDefaultDate
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < _adblCalibQuote.length; ++i) {
			try {
				NonlinearCurveBuilder.CreditCurve (
					_valParam,
					_aCalibInst[i],
					calibrationQuoteArray[i] = _adblCalibQuote[i] + shift,
					_astrCalibMeasure[i],
					_bFlat,
					i,
					creditCurve,
					_dc,
					_gc,
					_pricerParam,
					_lsfc,
					_quotingParams,
					null
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		creditCurve.setInstrCalibInputs (
			_valParam,
			_bFlat,
			_dc,
			_gc,
			_pricerParam,
			_aCalibInst,
			calibrationQuoteArray,
			_astrCalibMeasure,
			_lsfc,
			_quotingParams
		);

		return creditCurve;
	}

	@Override public ForwardHazardCreditCurve shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		if (!NumberUtil.IsValid (shift)) {
			return null;
		}

		if (null == _valParam || null == _aCalibInst || 0 == _aCalibInst.length ||
			null == _adblCalibQuote || 0 == _adblCalibQuote.length ||
			null == _astrCalibMeasure || 0 == _astrCalibMeasure.length ||
			_astrCalibMeasure.length != _adblCalibQuote.length ||
			_adblCalibQuote.length != _aCalibInst.length) {
			return parallelShiftQuantificationMetric (shift);
		}

		ForwardHazardCreditCurve creditCurve = null;
		double[] calibrationQuoteArray = new double[_adblCalibQuote.length];

		if (spanIndex >= _adblCalibQuote.length) {
			return null;
		}

		try {
			creditCurve = new ForwardHazardCreditCurve (
				_iEpochDate,
				_label,
				_strCurrency,
				_hazardRateArray,
				_hazardDateArray,
				_recoveryRateArray,
				_recoveryDateArray,
				_iSpecificDefaultDate
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < _adblCalibQuote.length; ++i) {
			try {
				NonlinearCurveBuilder.CreditCurve (
					_valParam,
					_aCalibInst[i],
					calibrationQuoteArray[i] = _adblCalibQuote[i] + (i == spanIndex ? shift : 0.),
					_astrCalibMeasure[i],
					_bFlat,
					i,
					creditCurve,
					_dc,
					_gc,
					_pricerParam,
					_lsfc,
					_quotingParams,
					null
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		creditCurve.setInstrCalibInputs (
			_valParam,
			_bFlat,
			_dc,
			_gc,
			_pricerParam,
			_aCalibInst,
			calibrationQuoteArray,
			_astrCalibMeasure,
			_lsfc,
			_quotingParams
		);

		return creditCurve;
	}

	@Override public CreditCurve flatCurve (
		final double flatNodeValue,
		final boolean singleNode,
		final double recovery)
	{
		if (!NumberUtil.IsValid (flatNodeValue) || 0. >= flatNodeValue || null == _valParam ||
			null == _aCalibInst || 0 == _aCalibInst.length ||
			null == _adblCalibQuote || 0 == _adblCalibQuote.length ||
			null == _astrCalibMeasure || 0 == _astrCalibMeasure.length ||
			_astrCalibMeasure.length != _adblCalibQuote.length ||
			_adblCalibQuote.length != _aCalibInst.length) {
			return null;
		}

		ExplicitBootCreditCurve creditCurve = null;

		try {
			if (singleNode)
				creditCurve = ScenarioCreditCurveBuilder.Hazard (
					_iEpochDate,
					_label.fullyQualifiedName(),
					_strCurrency,
					_hazardRateArray[0],
					_hazardDateArray[0],
					!NumberUtil.IsValid (recovery) ? _recoveryRateArray[0] : recovery
				);
			else
				creditCurve = new ForwardHazardCreditCurve (
					_iEpochDate,
					_label,
					_strCurrency,
					_hazardRateArray,
					_hazardDateArray,
					_recoveryRateArray,
					_recoveryDateArray,
					_iSpecificDefaultDate
				);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < _adblCalibQuote.length; ++i) {
			try {
				NonlinearCurveBuilder.CreditCurve (
					_valParam,
					_aCalibInst[i],
					flatNodeValue,
					_astrCalibMeasure[i],
					true,
					i,
					creditCurve,
					_dc,
					_gc,
					_pricerParam,
					_lsfc,
					_quotingParams,
					null
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (singleNode)
			creditCurve.setInstrCalibInputs (
				_valParam,
				true,
				_dc,
				_gc,
				_pricerParam,
				new CalibratableComponent[] {_aCalibInst[0]},
				new double[] {flatNodeValue},
				_astrCalibMeasure,
				_lsfc,
				_quotingParams
			);
		else {
			double[] calibrationValueArray = new double[_adblCalibQuote.length];

			for (int i = 0; i < _adblCalibQuote.length; ++i) {
				calibrationValueArray[i] = flatNodeValue;
			}

			creditCurve.setInstrCalibInputs (
				_valParam,
				true,
				_dc,
				_gc,
				_pricerParam,
				_aCalibInst,
				calibrationValueArray,
				_astrCalibMeasure,
				_lsfc,
				_quotingParams
			);
		}

		return creditCurve;
	}

	@Override public CreditCurve customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		if (null == manifestMeasureTweak) {
			return null;
		}

		if (!(manifestMeasureTweak instanceof CreditManifestMeasureTweak)) {
			return createFromBaseMMTP (manifestMeasureTweak);
		}

		CreditManifestMeasureTweak creditManifestMeasureTweak =
			(CreditManifestMeasureTweak) manifestMeasureTweak;

		if (CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_PARAM_RECOVERY.equalsIgnoreCase
			(creditManifestMeasureTweak.paramType())) {
			double[] bumpedRecoveryRateArray = null;

			if (null == (
				bumpedRecoveryRateArray = Helper.TweakManifestMeasure (
					_recoveryRateArray,
					creditManifestMeasureTweak
				)
			) || bumpedRecoveryRateArray.length != _recoveryRateArray.length) {
				return null;
			}

			try {
				return new ForwardHazardCreditCurve (
					_iEpochDate,
					_label,
					_strCurrency,
					_hazardRateArray,
					_hazardDateArray,
					bumpedRecoveryRateArray,
					_recoveryDateArray,
					_iSpecificDefaultDate
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_PARAM_QUOTE.equalsIgnoreCase (
			creditManifestMeasureTweak.paramType()
		)) {
			if (CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_MEASURE_HAZARD.equalsIgnoreCase (
				creditManifestMeasureTweak.measureType()
			)) {
				double[] bumpedHazardRateArray = null;

				if (null == (
					bumpedHazardRateArray = Helper.TweakManifestMeasure (
						_hazardRateArray,
						creditManifestMeasureTweak
					)) || bumpedHazardRateArray.length != _hazardRateArray.length
				) {
					return null;
				}

				try {
					return new ForwardHazardCreditCurve (
						_iEpochDate,
						_label,
						_strCurrency,
						bumpedHazardRateArray,
						_hazardDateArray,
						_recoveryRateArray,
						_recoveryDateArray,
						_iSpecificDefaultDate
					);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_MEASURE_QUOTE.equalsIgnoreCase (
				creditManifestMeasureTweak.measureType()
			)) {
				double[] bumpedQuoteArray = null;

				if (null == (
					bumpedQuoteArray = Helper.TweakManifestMeasure (
						_hazardRateArray,
						creditManifestMeasureTweak
					)) || bumpedQuoteArray.length != _hazardRateArray.length
				) {
					return null;
				}

				ExplicitBootCreditCurve creditCurve = null;

				try {
					if (creditManifestMeasureTweak.singleNodeCalib()) {
						creditCurve = ScenarioCreditCurveBuilder.Hazard (
							_iEpochDate,
							_strCurrency,
							_label.fullyQualifiedName(),
							_hazardRateArray[0],
							_hazardDateArray[0],
							_recoveryRateArray[0]
						);
					} else {
						creditCurve = new ForwardHazardCreditCurve (
							_iEpochDate,
							_label,
							_strCurrency,
							_hazardRateArray,
							_hazardDateArray,
							_recoveryRateArray,
							_recoveryDateArray,
							_iSpecificDefaultDate
						);
					}
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}

				for (int i = 0; i < bumpedQuoteArray.length; ++i) {
					try {
						NonlinearCurveBuilder.CreditCurve (
							_valParam,
							_aCalibInst[i],
							bumpedQuoteArray[i],
							_astrCalibMeasure[i],
							_bFlat,
							i,
							creditCurve,
							_dc,
							_gc,
							_pricerParam,
							_lsfc,
							_quotingParams,
							null
						);
					} catch (Exception e) {
						e.printStackTrace();

						return null;
					}
				}

				creditCurve.setInstrCalibInputs (
					_valParam,
					_bFlat,
					_dc,
					_gc,
					_pricerParam,
					_aCalibInst,
					bumpedQuoteArray,
					_astrCalibMeasure,
					_lsfc,
					_quotingParams
				);

				return creditCurve;
			}
		}

		return null;
	}

	@Override public boolean setNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _hazardRateArray.length) {
			return false;
		}

		for (int i = nodeIndex; i < _hazardRateArray.length; ++i) {
			_hazardRateArray[i] = value;
		}

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _hazardRateArray.length) {
			return false;
		}

		for (int i = nodeIndex; i < _hazardRateArray.length; ++i) {
			_hazardRateArray[i] += value;
		}

		return true;
	}

	@Override public boolean setFlatValue (
		final double value)
	{
		if (!NumberUtil.IsValid (value)) {
			return false;
		}

		for (int i = 0; i < _hazardRateArray.length; ++i) {
			_hazardRateArray[i] = value;
		}

		return true;
	}
}
