
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * GovvieTreasuryMarksReconstitutor transforms the Treasury Marks (e.g., Yield) Feed Inputs into Formats
 *  appropriate for Govvie Curve Construction and Measure Generation.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GovvieTreasuryMarksReconstitutor {

	/**
	 * The Standard Treasury Input Calibration Manifest Measure
	 */

	public static final java.lang.String s_strCalibrationMeasure = "Yield";

	/**
	 * The Standard Treasury Input Calibration Manifest Measure Scaler
	 */

	public static final double s_dblScaler = 0.01;

	/**
	 * The Standard Treasury Market Yield Re-constitution Benchmark Tenors
	 */

	public static final java.lang.String[] s_astrOutputBenchmarkTenor = new java.lang.String[] {
		"2Y", "3Y", "4Y", "5Y", "7Y", "10Y", "20Y", "30Y"
	};

	/**
	 * Re-constitute the Horizon Benchmark Marks
	 * 
	 * @param strTreasuryType The Treasury Type
	 * @param mapClosingMarks Map of the Dates Closing Marks
	 * @param strManifestMeasure The Govvie Curve Calibration Manifest Measure
	 * @param astrOutputBenchmarkTenor Tenors of the Output Benchmark Desired
	 * 
	 * @return The Transformed Horizon Benchmark Yield
	 */

	public static final boolean RegularizeBenchmarkMarks (
		final java.lang.String strTreasuryType,
		final java.util.Map<org.drip.analytics.date.JulianDate, java.util.Map<java.lang.Double,
			java.lang.Double>> mapClosingMarks,
		final java.lang.String strManifestMeasure,
		final java.lang.String[] astrOutputBenchmarkTenor)
	{
		if (null == mapClosingMarks || 0 == mapClosingMarks.size() || null == astrOutputBenchmarkTenor)
			return false;

		java.lang.String strHeader = "Date";
		int iNumOutputBenchmark = astrOutputBenchmarkTenor.length;

		if (0 == iNumOutputBenchmark) return false;

		for (int i = 0; i < iNumOutputBenchmark; ++i)
			strHeader += "," + astrOutputBenchmarkTenor[i];

		for (int i = 0; i < iNumOutputBenchmark; ++i)
			strHeader += ",<<INPUT::" + astrOutputBenchmarkTenor[i] + ">>";

		System.out.println (strHeader);

		for (java.util.Map.Entry<org.drip.analytics.date.JulianDate, java.util.Map<java.lang.Double,
			java.lang.Double>> meClosingMarks : mapClosingMarks.entrySet()) {
			org.drip.analytics.date.JulianDate dtSpot = meClosingMarks.getKey();

			java.util.Map<java.lang.Double, java.lang.Double> mapKeyMarks = meClosingMarks.getValue();

			int iNumInputBenchmark = mapKeyMarks.size();

			int i = 0;
			double[] adblInputTreasuryYield = new double[iNumInputBenchmark];
			org.drip.analytics.date.JulianDate[] adtInputTreasuryMaturity = new
				org.drip.analytics.date.JulianDate[iNumInputBenchmark];
			org.drip.analytics.date.JulianDate[] adtInputTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumInputBenchmark];
			org.drip.product.credit.BondComponent[] aInputBenchmarkTreasury = new
				org.drip.product.credit.BondComponent[iNumInputBenchmark];
			org.drip.product.credit.BondComponent[] aOutputBenchmarkTreasury = new
				org.drip.product.credit.BondComponent[iNumOutputBenchmark];

			for (java.util.Map.Entry<java.lang.Double, java.lang.Double> meKeyMarks : mapKeyMarks.entrySet())
			{
				double dblYear = meKeyMarks.getKey();

				if (null == (aInputBenchmarkTreasury[i] = org.drip.service.template.TreasuryBuilder.FromCode
					(strTreasuryType, adtInputTreasuryEffective[i] = dtSpot, adtInputTreasuryMaturity[i] =
						dtSpot.addYears ((int) dblYear), adblInputTreasuryYield[i] = meKeyMarks.getValue())))
					return false;

				++i;
			}

			for (int j = 0; j < iNumOutputBenchmark; ++j) {
				if (null == (aOutputBenchmarkTreasury[j] = org.drip.service.template.TreasuryBuilder.FromCode
					(strTreasuryType, dtSpot, dtSpot.addTenor (astrOutputBenchmarkTenor[j]), 0.01)))
					return false;
			}

			org.drip.state.govvie.GovvieCurve gc =
				org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve
					(strTreasuryType, dtSpot, adtInputTreasuryEffective, adtInputTreasuryMaturity,
						adblInputTreasuryYield, adblInputTreasuryYield, strManifestMeasure);

			java.lang.String strCleansedInput = "" + dtSpot;

			org.drip.param.valuation.ValuationParams valParamsSpot =
				org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian());

			for (int j = 0; j < iNumOutputBenchmark; ++j) {
				try {
					strCleansedInput += "," + org.drip.quant.common.FormatUtil.FormatDouble
						(aOutputBenchmarkTreasury[j].yieldFromPrice (valParamsSpot, null, null,
							aOutputBenchmarkTreasury[j].priceFromYield (valParamsSpot, null, null, gc.yield
								(aOutputBenchmarkTreasury[j].maturityDate()))), 1, 6, 1.);
				} catch (java.lang.Exception e) {
					// e.printStackTrace();

					continue;
				}
			}

			for (int j = 0; j < iNumInputBenchmark; ++j)
				strCleansedInput += "," + org.drip.quant.common.FormatUtil.FormatDouble
					(adblInputTreasuryYield[j], 1, 6, 1.);

			System.out.println (strCleansedInput);
		}

		return true;
	}

	/**
	 * Re-constitute the Horizon Benchmark Marks
	 * 
	 * @param strTreasuryType The Treasury Type
	 * @param mapClosingMarks Map of the Dates Closing Marks
	 * 
	 * @return The Transformed Horizon Benchmark Yield
	 */

	public static final boolean RegularizeBenchmarkMarks (
		final java.lang.String strTreasuryType,
		final java.util.Map<org.drip.analytics.date.JulianDate, java.util.Map<java.lang.Double,
			java.lang.Double>> mapClosingMarks)
	{
		return RegularizeBenchmarkMarks (strTreasuryType, mapClosingMarks, s_strCalibrationMeasure,
			s_astrOutputBenchmarkTenor);
	}

	/**
	 * Re-constitute the Horizon Benchmark Marks
	 * 
	 * @param strMarksLocation The Location of the CSV Marks File
	 * @param strTreasuryType The Treasury Type
	 * 
	 * @return The Transformed Horizon Benchmark Yield
	 */

	public static final boolean RegularizeBenchmarkMarks (
		final java.lang.String strMarksLocation,
		final java.lang.String strTreasuryType)
	{
		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid (strMarksLocation,
			false);

		return null == csvGrid ? false : RegularizeBenchmarkMarks (strTreasuryType, csvGrid.doubleMap
			(s_dblScaler), s_strCalibrationMeasure, s_astrOutputBenchmarkTenor);
	}
}
