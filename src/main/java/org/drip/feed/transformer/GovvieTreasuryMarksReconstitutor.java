
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>GovvieTreasuryMarksReconstitutor</i> transforms the Treasury Marks (e.g., Yield) Feed Inputs into
 * Formats appropriate for Govvie Curve Construction and Measure Generation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Feed</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/README.md">Transformer</a></li>
 *  </ul>
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
