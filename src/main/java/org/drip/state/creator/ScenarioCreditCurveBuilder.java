
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.definition.CalibrationParams;
import org.drip.param.market.CreditCurveScenarioContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.boot.CreditCurveScenario;
import org.drip.state.credit.CreditCurve;
import org.drip.state.credit.ExplicitBootCreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.EntityCDSLabel;
import org.drip.state.nonlinear.ForwardHazardCreditCurve;

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

	public static final CreditCurve Custom (
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
	 * @param name Credit Curve Name
	 * @param spotDate Spot Date
	 * @param calibratableComponentArray Array of calibration instruments
	 * @param discountCurve Discount Curve
	 * @param calibrationQuoteArray Array of Instrument Quotes
	 * @param calibrationMeasureArray Array of calibration Measures
	 * @param recovery Recovery Rate
	 * @param flat Whether the Calibration is based off of a flat spread
	 * 
	 * @return The cooked Credit Curve
	 */

	public static final CreditCurve Custom (
		final String name,
		final JulianDate spotDate,
		final CalibratableComponent[] calibratableComponentArray,
		final MergedDiscountForwardCurve discountCurve,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double recovery,
		final boolean flat)
	{
		return Custom (
			name,
			spotDate,
			calibratableComponentArray,
			discountCurve,
			calibrationQuoteArray,
			calibrationMeasureArray,
			recovery,
			flat,
			null
		);
	}

	/**
	 * Create a <i>CreditCurve</i> instance from a single node hazard rate
	 * 
	 * @param startDate Curve epoch date
	 * @param name Credit Curve Name
	 * @param currency Currency
	 * @param hazardRate Curve hazard rate
	 * @param recovery Curve recovery
	 * 
	 * @return <i>CreditCurve</i> instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve FlatHazard (
		final int startDate,
		final String name,
		final String currency,
		final double hazardRate,
		final double recovery)
	{
		try {
			return new ForwardHazardCreditCurve (
				startDate,
				EntityCDSLabel.Standard (name, currency),
				currency,
				new double[] {hazardRate},
				new int[] {startDate},
				new double[] {recovery},
				new int[] {startDate},
				Integer.MIN_VALUE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a <i>CreditCurve</i> Instance from the Input Array of Survival Probabilities
	 * 
	 * @param startDate Curve epoch date
	 * @param name Credit Curve Name
	 * @param currency Currency
	 * @param survivalDateArray Array of Dates
	 * @param survivalProbabilityArray Array of Survival Probabilities
	 * @param recovery Curve recovery
	 * 
	 * @return The <i>CreditCurve</i> Instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Survival (
		final int startDate,
		final String name,
		final String currency,
		final int[] survivalDateArray,
		final double[] survivalProbabilityArray,
		final double recovery)
	{
		if (!NumberUtil.IsValid (recovery)) {
			return null;
		}

		try {
			double[] hazardRateArray = new double[survivalProbabilityArray.length];
			double[] recoveryArray = new double[1];
			int[] recoveryDateArray = new int[1];
			recoveryDateArray[0] = startDate;
			int periodBeginDate = startDate;
			recoveryArray[0] = recovery;
			double beginSurvival = 1.;

			for (int survivalProbabilityArrayIndex = 0;
				survivalProbabilityArrayIndex < survivalProbabilityArray.length;
				++survivalProbabilityArrayIndex) {
				if (!NumberUtil.IsValid (survivalProbabilityArray[survivalProbabilityArrayIndex]) ||
					survivalDateArray[survivalProbabilityArrayIndex] <= periodBeginDate ||
					beginSurvival < survivalProbabilityArray[survivalProbabilityArrayIndex]) {
					return null;
				}

				hazardRateArray[survivalProbabilityArrayIndex] = 365.25 /
					(survivalDateArray[survivalProbabilityArrayIndex] - periodBeginDate) * Math.log
					(beginSurvival / survivalProbabilityArray[survivalProbabilityArrayIndex]);

				periodBeginDate = survivalDateArray[survivalProbabilityArrayIndex];
				beginSurvival = survivalProbabilityArray[survivalProbabilityArrayIndex];
			}

			return new ForwardHazardCreditCurve (
				startDate,
				EntityCDSLabel.Standard (name, currency),
				currency,
				hazardRateArray,
				survivalDateArray,
				recoveryArray,
				recoveryDateArray,
				Integer.MIN_VALUE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a CreditCurve Instance from the Input Array of Survival Probabilities
	 * 
	 * @param startDate Curve epoch date
	 * @param name Credit Curve Name
	 * @param currency Currency
	 * @param survivalTenorArray Array of Survival Tenors
	 * @param survivalProbabilityArray Array of Survival Probabilities
	 * @param recovery Curve recovery
	 * 
	 * @return The CreditCurve Instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Survival (
		final int startDate,
		final String name,
		final String currency,
		final String[] survivalTenorArray,
		final double[] survivalProbabilityArray,
		final double recovery)
	{
		if (null == survivalTenorArray) {
			return null;
		}

		JulianDate spotDate = new JulianDate (startDate);

		int survivalTenorCount = survivalTenorArray.length;
		int[] survivalDateArray = new int[survivalTenorCount];

		for (int survivalTenorIndex = 0; survivalTenorIndex < survivalTenorCount; ++survivalTenorIndex) {
			JulianDate tenorDate = spotDate.addTenor (survivalTenorArray[survivalTenorIndex]);

			if (null == tenorDate) {
				return null;
			}

			survivalDateArray[survivalTenorIndex] = tenorDate.julian();
		}

		return Survival (startDate, name, currency, survivalDateArray, survivalProbabilityArray, recovery);
	}

	/**
	 * Create an instance of the CreditCurve object from a solitary hazard rate node
	 * 
	 * @param startDate Curve epoch date
	 * @param name Credit Curve Name
	 * @param currency Currency
	 * @param hazardRate The solo hazard rate
	 * @param hazardDate Date
	 * @param recovery Curve recovery
	 * 
	 * @return CreditCurve instance
	 */

	public static final org.drip.state.credit.ExplicitBootCreditCurve Hazard (
		final int startDate,
		final String name,
		final String currency,
		final double hazardRate,
		final int hazardDate,
		final double recovery)
	{
		if (!NumberUtil.IsValid (startDate) || !NumberUtil.IsValid (hazardRate) ||
			!NumberUtil.IsValid (recovery)) {
			return null;
		}

		double[] hazardRateArray = new double[1];
		double[] recoveryArray = new double[1];
		int[] recoveryDateArray = new int[1];
		int[] hazardDateArray = new int[1];
		hazardRateArray[0] = hazardRate;
		recoveryArray[0] = recovery;
		hazardDateArray[0] = hazardDate;
		recoveryDateArray[0] = startDate;

		try {
			return new ForwardHazardCreditCurve (
				startDate,
				EntityCDSLabel.Standard (name, currency),
				currency,
				hazardRateArray,
				hazardDateArray,
				recoveryArray,
				recoveryDateArray,
				Integer.MIN_VALUE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a credit curve from an array of dates and hazard rates
	 * 
	 * @param startDate Curve epoch date
	 * @param name Credit Curve Name
	 * @param currency Currency
	 * @param dateArray Array of dates
	 * @param hazardRateArray Array of hazard rates
	 * @param recovery Curve recovery
	 * 
	 * @return CreditCurve instance
	 */

	public static final ExplicitBootCreditCurve Hazard (
		final JulianDate startDate,
		final String name,
		final String currency,
		final int[] dateArray,
		final double[] hazardRateArray,
		final double recovery)
	{
		if (null == startDate || null == hazardRateArray || null == dateArray ||
			hazardRateArray.length != dateArray.length || !NumberUtil.IsValid (recovery)) {
			return null;
		}

		try {
			double[] recoveryRateArray = new double[1];
			int[] recoveryDateArray = new int[1];
			recoveryRateArray[0] = recovery;

			recoveryDateArray[0] = startDate.julian();

			return new ForwardHazardCreditCurve (
				startDate.julian(),
				EntityCDSLabel.Standard (name, currency),
				currency,
				hazardRateArray,
				dateArray,
				recoveryRateArray,
				recoveryDateArray,
				Integer.MIN_VALUE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a credit curve from hazard rate and recovery rate term structures
	 * 
	 * @param startDate Curve epoch date
	 * @param name Credit Curve Name
	 * @param currency Currency
	 * @param hazardRateArray Array of hazard rates
	 * @param hazardDateArray Matched array of hazard dates
	 * @param recoveryRateArray Matched array of recovery rates
	 * @param recoveryDateArray Matched array of recovery dates
	 * @param rpecificDefaultDate (Optional) Specific Default Date
	 * 
	 * @return <i>CreditCurve</i> instance
	 */

	public static final ExplicitBootCreditCurve Hazard (
		final int startDate,
		final String name,
		final String currency,
		final double[] hazardRateArray,
		final int[] hazardDateArray,
		final double[] recoveryRateArray,
		final int[] recoveryDateArray,
		final int specificDefaultDate)
	{
		try {
			return new ForwardHazardCreditCurve (
				startDate,
				EntityCDSLabel.Standard (name, currency),
				currency,
				hazardRateArray,
				hazardDateArray,
				recoveryRateArray,
				recoveryDateArray,
				specificDefaultDate
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
