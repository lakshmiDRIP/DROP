
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.param.definition.CalibrationParams;
import org.drip.param.market.CreditCurveScenarioContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.boot.CreditCurveScenario;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * <i>ScenarioCreditCurveBuilder</i> implements the construction of the custom Scenario based credit curves.
 *  It exposes the following Functions:
 *
 *  <ul>
 * 		<li>Create <i>CreditScenarioCurve</i> from the array of calibration instruments</li>
 * 		<li>Calibrate the base credit curve from the input credit instruments, measures, and the quotes</li>
 * 		<li>Create a <i>CreditCurve</i> instance from a single node hazard rate</li>
 * 		<li>Create a <i>CreditCurve</i> Instance from the Input Array of Survival Probabilities</li>
 * 		<li>Create an instance of the CreditCurve object from a solitary hazard rate node</li>
 * 		<li>Create a credit curve from an array of dates and hazard rates</li>
 * 		<li>Create a credit curve from hazard rate and recovery rate term structures</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioCreditCurveBuilder {

	/**
	 * Create <i>CreditScenarioCurve</i> from the array of calibration instruments
	 * 
	 * @param calibratableComponentArray Array of calibration instruments
	 * 
	 * @return <i>CreditScenarioCurve</i> object
	 */

	public static final CreditCurveScenarioContainer CreateCCSC (
		final CalibratableComponent[] calibratableComponentArray)
	{
		try {
			return new CreditCurveScenarioContainer (calibratableComponentArray, 0.0001, 0.01);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calibrate the base credit curve from the input credit instruments, measures, and the quotes
	 * 
	 * @param name Credit Curve Name
	 * @param spotDate Spot Date
	 * @param calibratableComponentArray Array of calibration instruments
	 * @param discountCurve Discount Curve
	 * @param calibrationQuoteArray Array of Instrument Quotes
	 * @param calibrationMeasureArray Array of calibration Measures
	 * @param recovery Recovery Rate
	 * @param flat Whether the Calibration is based off of a flat spread
	 * @param calibrationParams The Calibration Parameters
	 * 
	 * @return The cooked Credit Curve
	 */

	public static final org.drip.state.credit.CreditCurve Custom (
		final String name,
		final JulianDate spotDate,
		final CalibratableComponent[] calibratableComponentArray,
		final MergedDiscountForwardCurve discountCurve,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double recovery,
		final boolean flat,
		final CalibrationParams calibrationParams)
	{
		return null == spotDate ? null : CreditCurveScenario.Standard (
			name,
			ValuationParams.Spot (spotDate.julian()),
			calibratableComponentArray,
			calibrationQuoteArray,
			calibrationMeasureArray,
			recovery,
			flat,
			discountCurve,
			null,
			null,
			null,
			calibrationParams
		);
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
		return Custom (strName, dtSpot, aCalibInst, dc, adblCalibQuote, astrCalibMeasure, dblRecovery, bFlat,
			null);
	}

	/**
	 * Create a <i>CreditCurve</i> instance from a single node hazard rate
	 * 
	 * @param iStartDate Curve epoch date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param dblHazardRate Curve hazard rate
	 * @param dblRecovery Curve recovery
	 * 
	 * @return <i>CreditCurve</i> instance
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
				org.drip.state.identifier.EntityCDSLabel.Standard (strName, strCurrency), strCurrency, new
					double[] {dblHazardRate}, new int[] {iStartDate}, new double[] {dblRecovery}, new int[]
						{iStartDate}, java.lang.Integer.MIN_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a <i>CreditCurve</i> Instance from the Input Array of Survival Probabilities
	 * 
	 * @param iStartDate Start Date
	 * @param strName Credit Curve Name
	 * @param strCurrency Currency
	 * @param aiSurvivalDate Array of Dates
	 * @param adblSurvivalProbability Array of Survival Probabilities
	 * @param dblRecovery Recovery Rate
	 * 
	 * @return The <i>CreditCurve</i> Instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Survival (
		final int iStartDate,
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final int[] aiSurvivalDate,
		final double[] adblSurvivalProbability,
		final double dblRecovery)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblRecovery)) return null;

		try {
			double dblSurvivalBegin = 1.;
			int iPeriodBegin = iStartDate;
			double[] adblHazard = new double[adblSurvivalProbability.length];
			double[] adblRecovery = new double[1];
			int[] aiRecoveryDate = new int[1];
			adblRecovery[0] = dblRecovery;
			aiRecoveryDate[0] = iStartDate;

			for (int i = 0; i < adblSurvivalProbability.length; ++i) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (adblSurvivalProbability[i]) ||
					aiSurvivalDate[i] <= iPeriodBegin || dblSurvivalBegin < adblSurvivalProbability[i])
					return null;

				adblHazard[i] = 365.25 / (aiSurvivalDate[i] - iPeriodBegin) * java.lang.Math.log
					(dblSurvivalBegin / adblSurvivalProbability[i]);

				iPeriodBegin = aiSurvivalDate[i];
				dblSurvivalBegin = adblSurvivalProbability[i];
			}

			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (iStartDate,
				org.drip.state.identifier.EntityCDSLabel.Standard (strName, strCurrency), strCurrency,
					adblHazard, aiSurvivalDate, adblRecovery, aiRecoveryDate, java.lang.Integer.MIN_VALUE);
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (iStartDate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblHazardRate) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblRecovery))
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
				org.drip.state.identifier.EntityCDSLabel.Standard (strName, strCurrency), strCurrency,
					adblHazard, aiHazardDate, adblRecovery, aiRecoveryDate, java.lang.Integer.MIN_VALUE);
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
			aiDate.length || !org.drip.numerical.common.NumberUtil.IsValid (dblRecovery))
			return null;

		try {
			double[] adblRecovery = new double[1];
			int[] aiRecoveryDate = new int[1];
			adblRecovery[0] = dblRecovery;

			aiRecoveryDate[0] = dtStart.julian();

			return new org.drip.state.nonlinear.ForwardHazardCreditCurve (dtStart.julian(),
				org.drip.state.identifier.EntityCDSLabel.Standard (strName, strCurrency), strCurrency,
					adblHazardRate, aiDate, adblRecovery, aiRecoveryDate, java.lang.Integer.MIN_VALUE);
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
				org.drip.state.identifier.EntityCDSLabel.Standard (strName, strCurrency), strCurrency,
					adblHazardRate, aiHazardDate, adblRecoveryRate, aiRecoveryDate, iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
