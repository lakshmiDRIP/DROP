
package org.drip.state.creator;

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
 * ScenarioCreditCurveBuilder implements the construction of the custom Scenario based credit curves.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioCreditCurveBuilder {

	/**
	 * Create CreditScenarioCurve from the array of calibration instruments
	 * 
	 * @param aCalibInst Array of calibration instruments
	 * 
	 * @return CreditScenarioCurve object
	 */

	public static final org.drip.param.market.CreditCurveScenarioContainer CreateCCSC (
		final org.drip.product.definition.CalibratableComponent[] aCalibInst)
	{
		try {
			return new org.drip.param.market.CreditCurveScenarioContainer (aCalibInst, 0.0001, 0.01);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calibrate the base credit curve from the input credit instruments, measures, and the quotes
	 * 
	 * @param strName Credit Curve Name
	 * @param dtSpot Spot Date
	 * @param aCalibInst Array of calibration instruments
	 * @param dc Discount Curve
	 * @param adblCalibQuote Array of Instrument Quotes
	 * @param astrCalibMeasure Array of calibration Measures
	 * @param dblRecovery Recovery Rate
	 * @param bFlat Whether the Calibration is based off of a flat spread
	 * 
	 * @return The cooked Credit Curve
	 */

	public static final org.drip.state.credit.CreditCurve Custom (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final double dblRecovery,
		final boolean bFlat)
	{
		return null == dtSpot ? null : org.drip.state.boot.CreditCurveScenario.Standard (strName,
			org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), aCalibInst, adblCalibQuote,
				astrCalibMeasure, dblRecovery, bFlat, dc, null, null, null);
	}

	/**
	 * Create a CreditCurve instance from a single node hazard rate
	 * 
	 * @param iStartDate Curve epoch date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param dblHazardRate Curve hazard rate
	 * @param dblRecovery Curve recovery
	 * 
	 * @return CreditCurve instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve FlatHazard (
		final int iStartDate,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final double dblHazardRate,
		final double dblRecovery)
	{
		try {
			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (iStartDate,
				org.drip.state.identifier.CreditLabel.Standard (strName), strCurrency, new double[]
					{dblHazardRate}, new int[] {iStartDate}, new double[] {dblRecovery}, new int[]
						{iStartDate}, java.lang.Integer.MIN_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a CreditCurve Instance from the Input Array of Survival Probabilities
	 * 
	 * @param iStartDate Start Date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param aiSurvivalDate Array of Dates
	 * @param adblSurvivalProbability Array of Survival Probabilities
	 * @param dblRecovery Recovery Rate
	 * 
	 * @return The CreditCurve Instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Survival (
		final int iStartDate,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final int[] aiSurvivalDate,
		final double[] adblSurvivalProbability,
		final double dblRecovery)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblRecovery)) return null;

		try {
			double dblSurvivalBegin = 1.;
			int iPeriodBegin = iStartDate;
			double[] adblHazard = new double[adblSurvivalProbability.length];
			double[] adblRecovery = new double[1];
			int[] aiRecoveryDate = new int[1];
			adblRecovery[0] = dblRecovery;
			aiRecoveryDate[0] = iStartDate;

			for (int i = 0; i < adblSurvivalProbability.length; ++i) {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblSurvivalProbability[i]) ||
					aiSurvivalDate[i] <= iPeriodBegin || dblSurvivalBegin <= adblSurvivalProbability[i])
					return null;

				adblHazard[i] = 365.25 / (aiSurvivalDate[i] - iPeriodBegin) * java.lang.Math.log
					(dblSurvivalBegin / adblSurvivalProbability[i]);

				iPeriodBegin = aiSurvivalDate[i];
				dblSurvivalBegin = adblSurvivalProbability[i];
			}

			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (iStartDate,
				org.drip.state.identifier.CreditLabel.Standard (strName), strCurrency, adblHazard,
					aiSurvivalDate, adblRecovery, aiRecoveryDate, java.lang.Integer.MIN_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a CreditCurve Instance from the Input Array of Survival Probabilities
	 * 
	 * @param iStartDate Start Date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param astrSurvivalTenor Array of Survival Tenors
	 * @param adblSurvivalProbability Array of Survival Probabilities
	 * @param dblRecovery Recovery Rate
	 * 
	 * @return The CreditCurve Instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Survival (
		final int iStartDate,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String[] astrSurvivalTenor,
		final double[] adblSurvivalProbability,
		final double dblRecovery)
	{
		if (null == astrSurvivalTenor) return null;

		org.drip.analytics.date.JulianDate dtStart = new org.drip.analytics.date.JulianDate (iStartDate);

		int iNumSurvivalTenor = astrSurvivalTenor.length;
		int[] aiSurvivalDate = new int[iNumSurvivalTenor];

		for (int i = 0; i < iNumSurvivalTenor; ++i) {
			org.drip.analytics.date.JulianDate dtTenor = dtStart.addTenor (astrSurvivalTenor[i]);

			if (null == dtTenor) return null;

			aiSurvivalDate[i] = dtTenor.julian();
		}

		return Survival (iStartDate, strName, strCurrency, aiSurvivalDate, adblSurvivalProbability,
			dblRecovery);
	}

	/**
	 * Create an instance of the CreditCurve object from a solitary hazard rate node
	 * 
	 * @param iStartDate The Curve epoch date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param dblHazardRate The solo hazard rate
	 * @param iHazardDate Date
	 * @param dblRecovery Recovery
	 * 
	 * @return CreditCurve instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Hazard (
		final int iStartDate,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final double dblHazardRate,
		final int iHazardDate,
		final double dblRecovery)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (iStartDate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblHazardRate) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblRecovery))
			return null;

		double[] adblHazard = new double[1];
		double[] adblRecovery = new double[1];
		int[] aiHazardDate = new int[1];
		int[] aiRecoveryDate = new int[1];
		adblHazard[0] = dblHazardRate;
		adblRecovery[0] = dblRecovery;
		aiHazardDate[0] = iHazardDate;
		aiRecoveryDate[0] = iStartDate;

		try {
			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (iStartDate,
				org.drip.state.identifier.CreditLabel.Standard (strName), strCurrency, adblHazard,
					aiHazardDate, adblRecovery, aiRecoveryDate, java.lang.Integer.MIN_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a credit curve from an array of dates and hazard rates
	 * 
	 * @param dtStart Curve epoch date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param aiDate Array of dates
	 * @param adblHazardRate Array of hazard rates
	 * @param dblRecovery Recovery
	 * 
	 * @return CreditCurve instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Hazard (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblHazardRate,
		final double dblRecovery)
	{
		if (null == dtStart || null == adblHazardRate || null == aiDate || adblHazardRate.length !=
			aiDate.length || !org.drip.quant.common.NumberUtil.IsValid (dblRecovery))
			return null;

		try {
			double[] adblRecovery = new double[1];
			int[] aiRecoveryDate = new int[1];
			adblRecovery[0] = dblRecovery;

			aiRecoveryDate[0] = dtStart.julian();

			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (dtStart.julian(),
				org.drip.state.identifier.CreditLabel.Standard (strName), strCurrency, adblHazardRate,
					aiDate, adblRecovery, aiRecoveryDate, java.lang.Integer.MIN_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a credit curve from hazard rate and recovery rate term structures
	 * 
	 * @param iStartDate Curve Epoch date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param adblHazardRate Matched array of hazard rates
	 * @param aiHazardDate Matched array of hazard dates
	 * @param adblRecoveryRate Matched array of recovery rates
	 * @param aiRecoveryDate Matched array of recovery dates
	 * @param iSpecificDefaultDate (Optional) Specific Default Date
	 * 
	 * @return CreditCurve instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Hazard (
		final int iStartDate,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final double[] adblHazardRate,
		final int[] aiHazardDate,
		final double[] adblRecoveryRate,
		final int[] aiRecoveryDate,
		final int iSpecificDefaultDate)
	{
		try {
			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (iStartDate,
				org.drip.state.identifier.CreditLabel.Standard (strName), strCurrency, adblHazardRate,
					aiHazardDate, adblRecoveryRate, aiRecoveryDate, iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
