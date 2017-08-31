
package org.drip.regression.curve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * CreditCurveRegressor implements the regression set analysis for the Credit Curve. CreditCurveRegressor
 *  regresses 12 scenarios:
 * 	- #1: Create an SNAC CDS.
 * 	- #2: Create the credit curve from a set of CDS instruments.
 * 	- #3: Create the credit curve from a flat hazard rate.
 * 	- #4: Create the credit curve from a set of survival probabilities.
 * 	- #5: Create the credit curve from an array of hazard rates.
 * 	- #6: Extract the credit curve instruments and quotes.
 * 	- #7: Create a parallel hazard shifted credit curve.
 * 	- #8: Create a parallel quote shifted credit curve.
 * 	- #9: Create a node tweaked credit curve.
 * 	- #10: Set a specific default date on the credit curve.
 * 	- #11: Compute the effective survival probability between 2 dates.
 * 	- #12: Compute the effective hazard rate between 2 dates.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditCurveRegressor implements org.drip.regression.core.RegressorSet {
	private java.lang.String _strCurrency = "";
	private org.drip.state.credit.CreditCurve _cc = null;
	private org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
	private org.drip.analytics.date.JulianDate _dtStart = null;
	private java.lang.String _strRegressionScenario = "org.drip.analytics.curve.CreditCurve";

	private java.util.List<org.drip.regression.core.UnitRegressor> _setRegressors = new
		java.util.ArrayList<org.drip.regression.core.UnitRegressor>();

	/**
	 * Do Nothing CreditCurveRegressor constructor.
	 */

	public CreditCurveRegressor()
	{
	}

	/*
	 * Set up the unit functional regressors for the credit curve regression set
	 */
	
	@Override public boolean setupRegressors()
	{
		try {
			/*
			 * Testing creation of the Credit Curve from SNAC instruments - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("CreateSNAC",
				_strRegressionScenario)
			{
				private double[] _adblQuotes = new double[5];
				private java.lang.String[] _astrCalibMeasure = new java.lang.String[5];
				private org.drip.product.definition.CreditDefaultSwap[] _aCDS = new
					org.drip.product.definition.CreditDefaultSwap[5];

				@Override public boolean preRegression()
				{
					_strCurrency = "USD";

					if (null == (_dtStart = org.drip.analytics.date.DateUtil.CreateFromYMD (2010,
						org.drip.analytics.date.DateUtil.MAY, 12)))
						return false;

					if (null == (_dc =
						org.drip.state.creator.ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate
							(_dtStart, _strCurrency, 0.04)))
						return false;

					for (int i = 0; i < 5; ++i) {
						_adblQuotes[i] = 50. * (i + 1);
						_astrCalibMeasure[i] = "FairPremium";

						if (null == (_aCDS[i] = org.drip.product.creator.CDSBuilder.CreateSNAC (_dtStart, (i
							+ 1) + "Y", 0.01, "CORP")))
							return false;
			 		}

					return true;
				}

				@Override public boolean execRegression()
				{
					return null != (_cc = org.drip.state.creator.ScenarioCreditCurveBuilder.Custom ("CORP",
						_dtStart, _aCDS, _dc, _adblQuotes, _astrCalibMeasure, 0.4, false));
				}
			});

			/*
			 * Testing creation of the Credit Curve from flat hazard - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("FromFlatHazard",
				_strRegressionScenario)
			{
				private org.drip.state.credit.CreditCurve _ccFromFlatHazard = null;

				@Override public boolean execRegression()
				{
					return null != (_ccFromFlatHazard =
						org.drip.state.creator.ScenarioCreditCurveBuilder.FlatHazard (_dtStart.julian(),
							"CORP", "USD", 0.02, 0.4));
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					final int NUM_DC_INSTRUMENTS = 5;
					double adblHazard[] = new double[NUM_DC_INSTRUMENTS];
					org.drip.analytics.date.JulianDate adt[] = new
						org.drip.analytics.date.JulianDate[NUM_DC_INSTRUMENTS];

					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (adblHazard[i] =
								_ccFromFlatHazard.hazard (_dtStart, (adt[i] = _dtStart.addYears (i + 1)))))
								return false;

							rnvd.set ("HazardRateFromHazardCurve[" + adt[i] + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (adblHazard[i], 1, 4, 1));

							if (!org.drip.quant.common.NumberUtil.WithinTolerance (adblHazard[i], 0.02))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});

			/*
			 * Testing creation of the Credit Curve from flat survival - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("FromSurvival",
				_strRegressionScenario)
			{
				private static final int NUM_DC_INSTRUMENTS = 5;

				private int _aiDate[] = new int[NUM_DC_INSTRUMENTS];
				private double _adblSurvival[] = new double[NUM_DC_INSTRUMENTS];
				private org.drip.state.credit.CreditCurve _ccFromSurvival = null;

				@Override public boolean preRegression()
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						_aiDate[i] = _dtStart.addYears (i + 1).julian();

						_adblSurvival[i] = 1. - (i + 1) * 0.1;
					}

					return true;
				}

				@Override public boolean execRegression()
				{
					return null != (_ccFromSurvival =
						org.drip.state.creator.ScenarioCreditCurveBuilder.Survival (_dtStart.julian(),
							"CORP", "USD", _aiDate, _adblSurvival, 0.4));
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					double adblSurvivalCalc[] = new double[NUM_DC_INSTRUMENTS];

					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (adblSurvivalCalc[i] =
								_ccFromSurvival.survival (_aiDate[i])))
								return false;

							org.drip.analytics.date.JulianDate dt = new org.drip.analytics.date.JulianDate
								(_aiDate[i]);

							rnvd.set ("SurvivalFromOriginal[" + dt + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (_adblSurvival[i], 1, 4, 1));

							rnvd.set ("SurvivalFromSurvival[" + dt + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (adblSurvivalCalc[i], 1, 4, 1));

							if (!org.drip.quant.common.NumberUtil.WithinTolerance (adblSurvivalCalc[i],
								_adblSurvival[i]))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});

			/*
			 * Testing creation of the Credit Curve from hazard nodes - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("FromHazard",
				_strRegressionScenario)
			{
				private static final int NUM_DC_INSTRUMENTS = 5;

				private int _aiDate[] = new int[NUM_DC_INSTRUMENTS];
				private double _adblHazard[] = new double[NUM_DC_INSTRUMENTS];
				private org.drip.state.credit.CreditCurve _ccFromHazard = null;

				@Override public boolean preRegression()
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						_aiDate[i] = _dtStart.addYears (i + 1).julian();

						_adblHazard[i] = 0.01 * (1. - (i + 1) * 0.1);
					}

					return true;
				}

				@Override public boolean execRegression()
				{
					return null != (_ccFromHazard = org.drip.state.creator.ScenarioCreditCurveBuilder.Hazard
						(_dtStart, "CORP", "USD", _aiDate, _adblHazard, 0.4));
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					org.drip.analytics.date.JulianDate dt1 = _dtStart;
					double adblHazardCalc[] = new double[NUM_DC_INSTRUMENTS];

					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (adblHazardCalc[i] =
								_ccFromHazard.hazard (dt1, dt1.addYears (1))))
								return false;

							org.drip.analytics.date.JulianDate dt2 = dt1.addYears (1);

							rnvd.set ("HazardFromOriginal[" + dt1 + "-" + dt2 + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (_adblHazard[i], 1, 4, 1));

							rnvd.set ("HazardFromHazard[" + dt1 + "-" + dt2 + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (adblHazardCalc[i], 1, 4, 1));

							if (!org.drip.quant.common.NumberUtil.WithinTolerance (adblHazardCalc[i],
								_adblHazard[i]))
								return false;

							dt1 = dt1.addYears (1);
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});

			/*
			 * Testing extraction of the credit curve components and quotes - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("CompAndQuotes",
				_strRegressionScenario)
			{
				private org.drip.product.definition.CalibratableComponent[] _aCalibComp = null;

				@Override public boolean execRegression()
				{
					return null != (_aCalibComp = _cc.calibComp()) && 0 != _aCalibComp.length;
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					for (int i = 0; i < _aCalibComp.length; ++i) {
						org.drip.analytics.date.JulianDate dt = _aCalibComp[i].maturityDate();

						java.lang.String strCode = _aCalibComp[i].primaryCode();

						if (null == dt || null == strCode || strCode.isEmpty()) return false;

						try {
							rnvd.set ("CompQuote" + "_" + strCode + "[" + dt + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (_cc.manifestMeasure
									(strCode).get ("FairPremium"), 1, 4, 1));
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});

			/*
			 * Testing creation of a parallel hazard shifted Credit Curve - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor
				("ParallelHazardShiftedCurve", _strRegressionScenario)
			{
				private org.drip.state.credit.CreditCurve _ccParallelShifted = null;

				@Override public boolean execRegression()
				{
					if (null == (_ccParallelShifted = (org.drip.state.credit.CreditCurve)
						_cc.parallelShiftQuantificationMetric (0.0005)))
						return false;

					return true;
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					org.drip.product.definition.CalibratableComponent[] aCalibComp =
						_cc.calibComp();

					org.drip.analytics.date.JulianDate dt1 = _dtStart;

					for (int i = 0; i < aCalibComp.length; ++i) {
						org.drip.analytics.date.JulianDate dt = aCalibComp[i].maturityDate();

						double dblBaseHazard = java.lang.Double.NaN;
						double dblShiftedHazard = java.lang.Double.NaN;

						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (dblShiftedHazard =
								_ccParallelShifted.hazard (dt1, dt)) ||
									!org.drip.quant.common.NumberUtil.IsValid (dblBaseHazard = _cc.hazard
										(dt1, dt)))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}

						rnvd.set ("BaseCurveHazard[" + dt1 + "-" + dt + "]",
							org.drip.quant.common.FormatUtil.FormatDouble (dblBaseHazard, 1, 4, 1));

						rnvd.set ("ParallelShiftedCurveHazard[" + dt1 + "-" + dt + "]",
							org.drip.quant.common.FormatUtil.FormatDouble (dblShiftedHazard, 1, 4, 1));

						dt = dt1;

						if (!org.drip.quant.common.NumberUtil.WithinTolerance (dblBaseHazard + 0.0005,
							dblShiftedHazard))
							return false;
					}

					return true;
				}
			});

			/*
			 * Testing creation of the parallel quote shifted credit curve - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor
				("ParallelQuoteShiftedCurve", _strRegressionScenario)
			{
				private org.drip.state.credit.CreditCurve _ccParallelShifted = null;

				@Override public boolean execRegression()
				{
					return null != (_ccParallelShifted = (org.drip.state.credit.CreditCurve)
						_cc.parallelShiftManifestMeasure ("FairPremium", 5.));
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					org.drip.product.definition.CalibratableComponent[] aCalibComp =
						_cc.calibComp();

					org.drip.analytics.date.JulianDate dt1 = _dtStart;

					for (int i = 0; i < aCalibComp.length; ++i) {
						org.drip.analytics.date.JulianDate dt = aCalibComp[i].maturityDate();

						try {
							rnvd.set ("BaseCurveQuote[" + dt + "]",
								org.drip.quant.common.FormatUtil.FormatDouble (_cc.manifestMeasure
									(aCalibComp[i].primaryCode()).get ("FairPremium"), 1, 5, 1));

							rnvd.set ("ParallelShiftedCurveQuote[" + dt + "]",
								org.drip.quant.common.FormatUtil.FormatDouble
									(_ccParallelShifted.manifestMeasure (aCalibComp[i].primaryCode()).get
										("FairPremium"), 1, 5, 1));

							dt = dt1;

							if (!org.drip.quant.common.NumberUtil.WithinTolerance (_cc.manifestMeasure
								(aCalibComp[i].primaryCode()).get ("FairPremium") + 5.,
									_ccParallelShifted.manifestMeasure (aCalibComp[i].primaryCode()).get
										("FairPremium")))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});

			/*
			 * Testing creation of the node tweaked Credit Curve - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("NodeTweakedCurve",
				_strRegressionScenario)
			{
				private static final int TWEAKED_NODE = 0;

				private org.drip.param.definition.CreditManifestMeasureTweak _cntp = null;
				private org.drip.state.credit.CreditCurve _ccTweakedCurve = null;

				@Override public boolean preRegression()
				{
					try {
						_cntp = new org.drip.param.definition.CreditManifestMeasureTweak
							(org.drip.param.definition.CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_PARAM_QUOTE,
								org.drip.param.definition.CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_MEASURE_QUOTE,
							TWEAKED_NODE, true, 0.1, false);
					} catch (java.lang.Exception e) {
						e.printStackTrace();

						return false;
					}

					return true;
				}

				@Override public boolean execRegression()
				{
					return null != (_ccTweakedCurve = (org.drip.state.credit.CreditCurve)
						_cc.customTweakManifestMeasure ("FairPremium", _cntp));
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					org.drip.product.definition.CalibratableComponent[] aCalibComp =
						_cc.calibComp();

					org.drip.analytics.date.JulianDate dt1 = _dtStart;

					for (int i = 0; i < aCalibComp.length; ++i) {
						org.drip.analytics.date.JulianDate dt = aCalibComp[i].maturityDate();

						double dblBaseHazard = java.lang.Double.NaN;
						double dblShiftedHazard = java.lang.Double.NaN;

						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (dblShiftedHazard =
								_ccTweakedCurve.hazard (dt1, dt)) ||
									!org.drip.quant.common.NumberUtil.IsValid (dblBaseHazard = _cc.hazard
										(dt1, dt)))
								return false;
						} catch (Exception e) {
							e.printStackTrace();

							return false;
						}

						rnvd.set ("UntweakedHazard[" + dt + "]",
							org.drip.quant.common.FormatUtil.FormatDouble (dblBaseHazard, 1, 5, 1));

						rnvd.set ("TweakedHazard[" + dt + "]", org.drip.quant.common.FormatUtil.FormatDouble
							(dblShiftedHazard, 1, 5, 1));

						dt = dt1;
					}

					return true;
				}
			});

			/*
			 * Testing creation of the Credit Curve from flat/quoted spread - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("FlatCurve",
				_strRegressionScenario)
			{
				private org.drip.state.credit.CreditCurve _ccFlatCurve = null;

				@Override public boolean execRegression()
				{
					if (null == (_ccFlatCurve = _cc.flatCurve (90., false, 0.35))) return false;

					return true;
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					final int NUM_DC_INSTRUMENTS = 5;

					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						org.drip.analytics.date.JulianDate dt = _dtStart.addYears (i + 1);

						double dblHazard = java.lang.Double.NaN;

						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (dblHazard = _ccFlatCurve.hazard
								(dt)))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}

						rnvd.set ("FlatHazard[" + dt + "]", org.drip.quant.common.FormatUtil.FormatDouble
							(dblHazard, 1, 5, 1));
					}

					return true;
				}
			});

			/*
			 * Testing setting/removing specific default dates - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("SpecificDefault",
				_strRegressionScenario)
			{
				private int _iSpecificDefaultDate = java.lang.Integer.MIN_VALUE;

				@Override public boolean preRegression()
				{
					return !org.drip.quant.common.NumberUtil.IsValid (_iSpecificDefaultDate = _dtStart.addYears
						(2).julian());
				}

				@Override public boolean execRegression()
				{
					return _cc.setSpecificDefault (_iSpecificDefaultDate);
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					double dblSurvivalProb = java.lang.Double.NaN;

					org.drip.analytics.date.JulianDate dtSurvival = _dtStart.addYears (3);

					int iSurvivalDate = dtSurvival.julian();

					try {
						if (!org.drip.quant.common.NumberUtil.IsValid (dblSurvivalProb = _cc.survival
							(iSurvivalDate)))
							return false;
					} catch (Exception e) {
						e.printStackTrace();

						return false;
					}

					rnvd.set ("SpecificDefaultSetSurvival[" + dtSurvival + "]", "" + dblSurvivalProb);

					if (!_cc.unsetSpecificDefault()) return false;

					try {
						if (!org.drip.quant.common.NumberUtil.IsValid (dblSurvivalProb = _cc.survival
							(iSurvivalDate)))
							return false;
					} catch (java.lang.Exception e) {
						e.printStackTrace();

						return false;
					}

					rnvd.set ("SpecificDefaultUnsetSurvival[" + dtSurvival + "]", "" + dblSurvivalProb);

					return true;
				}
			});

			/*
			 * Testing calculation of effective survival between2 dates - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("EffectiveSurvival",
				_strRegressionScenario)
			{
				private static final int NUM_DC_INSTRUMENTS = 5;

				private int _aiDate[] = new int[NUM_DC_INSTRUMENTS];
				private double _adblSurvival[] = new double[NUM_DC_INSTRUMENTS];

				@Override public boolean preRegression()
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i)
						_aiDate[i] = _dtStart.addYears (i + 1).julian();

					return true;
				}

				@Override public boolean execRegression()
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (_adblSurvival[i] =
								_cc.effectiveSurvival ((i + 1) + "Y", (i + 2) + "Y")))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							rnvd.set ("EffectiveSurvival[" + new org.drip.analytics.date.JulianDate
								(_aiDate[i]) + "]", org.drip.quant.common.FormatUtil.FormatDouble
									(_adblSurvival[i], 1, 4, 1));
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});

			/*
			 * Testing calculation of effective recovery between2 dates - implements the pre-regression, the
			 * 	post-regression, and the actual regression functionality of the UnitRegressorExecutor class.
			 */

			_setRegressors.add (new org.drip.regression.core.UnitRegressionExecutor ("EffectiveRecovery",
				_strRegressionScenario)
			{
				private static final int NUM_DC_INSTRUMENTS = 5;

				private double _adblEffectiveRecovery[] = new double[NUM_DC_INSTRUMENTS];

				@Override public boolean execRegression()
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							if (!org.drip.quant.common.NumberUtil.IsValid (_adblEffectiveRecovery[i] =
								_cc.effectiveRecovery ((i + 1) + "Y", (i + 2) + "Y")))
								return false;
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}

				@Override public boolean postRegression (
					final org.drip.regression.core.RegressionRunDetail rnvd)
				{
					for (int i = 0; i < NUM_DC_INSTRUMENTS; ++i) {
						try {
							rnvd.set ("EffectiveRecovery[" + (i + 1) + "Y-" + (i + 2) + "Y]",
								org.drip.quant.common.FormatUtil.FormatDouble (_adblEffectiveRecovery[i], 1,
									4, 1));

							rnvd.set ("CurveRecovery[" + (i + 1) + "Y-" + (i + 2) + "Y]",
								org.drip.quant.common.FormatUtil.FormatDouble (_cc.recovery ((i + 1) + "Y"),
									1, 4, 1) + "-" + org.drip.quant.common.FormatUtil.FormatDouble
										(_cc.recovery ((i + 2) + "Y"), 1, 4, 1));
						} catch (java.lang.Exception e) {
							e.printStackTrace();

							return false;
						}
					}

					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	@Override public java.util.List<org.drip.regression.core.UnitRegressor> getRegressorSet()
	{
		return _setRegressors;
	}

	@Override public java.lang.String getSetName()
	{
		return _strRegressionScenario;
	}
}
