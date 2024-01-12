
package org.drip.spline.pchip;

import org.drip.numerical.common.NumberUtil;

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
 * <i>LocalMonotoneCkGenerator</i> generates customized Local Stretch by trading off C<sup>k</sup> for local
 * 	control. This class implements the following variants: Akima, Bessel, Harmonic, Hyman83, Hyman89, Kruger,
 *  Monotone Convex, as well as the Van Leer and the Huynh/LeFloch limiters. It also provides the following
 *  custom control on the resulting C<sup>1</sup>:
 *
 * <br>
 *  <ul>
	 * <li>Eliminate the Spurious Filter in the Input C<sup>1</sup> Entry</li>
	 * <li>Apply the Monotone Filter in the Input C<sup>1</sup> Entry</li>
	 * <li>Generate a Vanilla C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Bessel C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Hyman83 C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Hyman89 C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Harmonic C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a van Leer Limiter C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Huynh Le Floch Limiter C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Kruger C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Generate a Akima C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response Values</li>
	 * <li>Verify if the given Quintic Polynomial is Monotone using the Hyman89 Algorithm</li>
	 * <li>Generate C<sup>1</sup> Slope Quintic Polynomial is Monotone using the Hyman89 Algorithm</li>
	 * <li>Generate the Local Control Stretch in accordance with the desired Customization Parameters</li>
	 * <li>Retrieve the C<sup>1</sup> Array</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/README.md">Monotone Convex Themed PCHIP Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalMonotoneCkGenerator
{

	/**
	 * C<sup>1</sup> Type: Vanilla
	 */

	public static final String C1_VANILLA = "C1_VANILLA";

	/**
	 * C<sup>1</sup> Type: Akima
	 */

	public static final String C1_AKIMA = "C1_AKIMA";

	/**
	 * C<sup>1</sup> Type: Bessel
	 */

	public static final String C1_BESSEL = "C1_BESSEL";

	/**
	 * C<sup>1</sup> Type: Harmonic
	 */

	public static final String C1_HARMONIC = "C1_HARMONIC";

	/**
	 * C<sup>1</sup> Type: Huynh - Le Floch Limiter
	 */

	public static final String C1_HUYNH_LE_FLOCH = "C1_HUYNH_LE_FLOCH";

	/**
	 * C<sup>1</sup> Type: Hyman83
	 */

	public static final String C1_HYMAN83 = "C1_HYMAN83";

	/**
	 * C<sup>1</sup> Type: Hyman89
	 */

	public static final String C1_HYMAN89 = "C1_HYMAN89";

	/**
	 * C<sup>1</sup> Type: Kruger
	 */

	public static final String C1_KRUGER = "C1_KRUGER";

	/**
	 * C<sup>1</sup> Type: Monotone Convex
	 */

	public static final String C1_MONOTONE_CONVEX = "C1_MONOTONE_CONVEX";

	/**
	 * C<sup>1</sup> Type: Van Leer Limiter
	 */

	public static final String C1_VAN_LEER = "C1_VAN_LEER";

	private double[] _c1Array = null;
	private double[] _responseValueArray = null;
	private double[] _predictorOrdinateArray = null;

	/**
	 * Eliminate the Spurious Extrema in the Input C<sup>1</sup> Entry
	 * 
	 * @param c1Array The C<sup>1</sup> Array in which the Spurious Extrema is to be eliminated
	 * @param linearC1Array Array of the Linear C<sup>1</sup> Entries
	 * 
	 * @return The C<sup>1</sup> Array with the Spurious Extrema eliminated
	 */

	public static final double[] EliminateSpuriousExtrema (
		final double[] c1Array,
		final double[] linearC1Array)
	{
		if (null == c1Array || null == linearC1Array) {
			return null;
		}

		int entryCount = c1Array.length;
		double[] updatedC1Array = new double[entryCount];
		updatedC1Array[entryCount - 1] = c1Array[entryCount - 1];
		updatedC1Array[0] = c1Array[0];

		if (1 >= entryCount || entryCount != linearC1Array.length + 1) {
			return null;
		}

		for (int entryIndex = 1; entryIndex < entryCount - 1; ++entryIndex) {
			updatedC1Array[entryIndex] = 0. < linearC1Array[entryIndex] ?
				Math.min (
					Math.max (0., c1Array[entryIndex]),
					Math.min (linearC1Array[entryIndex], linearC1Array[entryIndex - 1])
				) : Math.max (
					Math.min (0., c1Array[entryIndex]),
					Math.max (linearC1Array[entryIndex], linearC1Array[entryIndex - 1])
				);
		}

		return updatedC1Array;
	}

	/**
	 * Apply the Monotone Filter in the Input C<sup>1</sup> Entry
	 * 
	 * @param c1Array The C<sup>1</sup> Array in which the Monotone Filter is to be applied
	 * @param linearC1Array Array of the Linear C<sup>1</sup> Entries
	 * 
	 * @return The C<sup>1</sup> Array with the Monotone Filter applied
	 */

	public static final double[] ApplyMonotoneFilter (
		final double[] c1Array,
		final double[] linearC1Array)
	{
		if (null == c1Array || null == linearC1Array) {
			return null;
		}

		int entryCount = c1Array.length;
		double[] updatedC1Array = new double[entryCount];
		updatedC1Array[0] = c1Array[0];

		if (1 >= entryCount || entryCount != linearC1Array.length + 1) {
			return null;
		}

		for (int entryIndex = 0; entryIndex < entryCount; ++entryIndex) {
			if (0 == entryIndex) {
				if (0. < c1Array[0] * linearC1Array[0] && 0. < linearC1Array[0] * linearC1Array[1] &&
					Math.abs (c1Array[0]) < 3. * Math.abs (linearC1Array[0])) {
					updatedC1Array[0] = 3. * linearC1Array[0];
				} else if (0. >= c1Array[0] * linearC1Array[0]) {
					updatedC1Array[0] = 0.;
				}
			} else if (entryCount == entryIndex) {
				if (0. < c1Array[entryIndex] * linearC1Array[entryIndex - 1] &&
					0. < linearC1Array[entryIndex - 1] * linearC1Array[entryIndex - 2] &&
					Math.abs (c1Array[entryIndex]) < 3. * Math.abs (linearC1Array[entryIndex - 1])) {
					updatedC1Array[entryIndex] = 3. * linearC1Array[entryIndex - 1];
				} else if (0. >= c1Array[entryIndex] * linearC1Array[entryIndex - 1]) {
					updatedC1Array[entryIndex] = 0.;
				}
			} else {
				updatedC1Array[entryIndex] = c1Array[entryIndex];
			}
		}

		return updatedC1Array;
	}

	/**
	 * Generate a Vanilla C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the
	 *  Response Values
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] LinearC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int segmentCount = responseValueArray.length - 1;
		double[] linearC1Array = new double[segmentCount];

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			linearC1Array[segmentIndex] = (
				responseValueArray[segmentIndex + 1] - responseValueArray[segmentIndex]
			) / (predictorOrdinateArray[segmentIndex + 1] - predictorOrdinateArray[segmentIndex]);
		}

		return linearC1Array;
	}

	/**
	 * Generate a Bessel C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response
	 *  Values
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] BesselC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int reponseValueCount = responseValueArray.length;
		double[] besselC1Array = new double[reponseValueCount];

		for (int reponseValueIndex = 0; reponseValueIndex < reponseValueCount; ++reponseValueIndex) {
			if (0 == reponseValueIndex) {
				besselC1Array[reponseValueIndex] = (
					predictorOrdinateArray[2] + predictorOrdinateArray[1] - 2. * predictorOrdinateArray[0]
				) * (
					responseValueArray[1] - responseValueArray[0]
				) / (
					predictorOrdinateArray[1] - predictorOrdinateArray[0]
				);
				besselC1Array[reponseValueIndex] -= (
					predictorOrdinateArray[1] - predictorOrdinateArray[0]
				) * (
					responseValueArray[2] - responseValueArray[1]
				) / (
					predictorOrdinateArray[2] - predictorOrdinateArray[1]
				);
				besselC1Array[reponseValueIndex] /= (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
			} else if (reponseValueCount - 1 == reponseValueIndex) {
				besselC1Array[reponseValueIndex] = (
					predictorOrdinateArray[reponseValueCount - 1] -
					predictorOrdinateArray[reponseValueCount - 2]
				) * (
					responseValueArray[reponseValueCount - 2] - responseValueArray[reponseValueCount - 3]
				) / (
					predictorOrdinateArray[reponseValueCount - 2] -
					predictorOrdinateArray[reponseValueCount - 3]
				);
				besselC1Array[reponseValueIndex] -= (
					2. * predictorOrdinateArray[reponseValueCount - 1] -
					predictorOrdinateArray[reponseValueCount - 2] -
					predictorOrdinateArray[reponseValueCount - 3]
				) * (
					responseValueArray[reponseValueCount - 1] - responseValueArray[reponseValueCount - 2]
				) / (
					predictorOrdinateArray[reponseValueCount - 1] -
					predictorOrdinateArray[reponseValueCount - 2]
				);
				besselC1Array[reponseValueIndex] /= (predictorOrdinateArray[reponseValueCount - 1] -
					predictorOrdinateArray[reponseValueCount - 3]);
			} else {
				besselC1Array[reponseValueIndex] = (
					predictorOrdinateArray[reponseValueIndex + 1] - predictorOrdinateArray[reponseValueIndex]
				) * (
					responseValueArray[reponseValueIndex] - responseValueArray[reponseValueIndex - 1]
				) / (
					predictorOrdinateArray[reponseValueIndex] - predictorOrdinateArray[reponseValueIndex - 1]
				);
				besselC1Array[reponseValueIndex] += (
					predictorOrdinateArray[reponseValueIndex] - predictorOrdinateArray[reponseValueIndex - 1]
				) * (
					responseValueArray[reponseValueIndex + 1] - responseValueArray[reponseValueIndex]
				) / (
					predictorOrdinateArray[reponseValueIndex + 1] - predictorOrdinateArray[reponseValueIndex]
				);
				besselC1Array[reponseValueIndex] /= (
					predictorOrdinateArray[reponseValueCount - 1] -
					predictorOrdinateArray[reponseValueCount - 3]
				);
			}
		}

		return besselC1Array;
	}

	/**
	 * Generate a Hyman83 C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the
	 *  Response Values
	 * 
	 * 	Hyman (1983) Accurate Monotonicity Preserving Cubic Interpolation -
	 *  	SIAM J on Numerical Analysis 4 (4), 645-654.
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] Hyman83C1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		double previousLinearSlope = Double.NaN;
		int responseValueCount = responseValueArray.length;
		double[] hyman83C1Array = new double[responseValueCount];

		for (int responseValueIndex = 0; responseValueIndex < responseValueCount; ++responseValueIndex) {
			hyman83C1Array[responseValueIndex] = 0.;
			double currentLinearSlope = responseValueCount - 1 != responseValueIndex ? (
				responseValueArray[responseValueIndex + 1] - responseValueArray[responseValueIndex]
			) / (
				predictorOrdinateArray[responseValueIndex + 1] - predictorOrdinateArray[responseValueIndex]
			) : Double.NaN;

			if (0 != responseValueIndex && responseValueCount - 1 != responseValueIndex) {
				double monotoneIndicator = previousLinearSlope * currentLinearSlope;

				if (0. <= monotoneIndicator) {
					hyman83C1Array[responseValueIndex] = 3. * monotoneIndicator / (
						Math.max (currentLinearSlope, previousLinearSlope) +
						2. * Math.min (currentLinearSlope, previousLinearSlope)
					);
				}
			}

			previousLinearSlope = currentLinearSlope;
		}

		return hyman83C1Array;
	}

	/**
	 * Generate a Hyman89 C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the
	 *  Response Values
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] Hyman89C1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int responseValueCount = responseValueArray.length;
		double[] hyman89C1Array = new double[responseValueCount];

		double[] nodeC1Array = LinearC1 (predictorOrdinateArray, responseValueArray);

		double[] besselC1Array = BesselC1 (predictorOrdinateArray, responseValueArray);

		for (int responseValueIndex = 0; responseValueIndex < responseValueCount; ++responseValueIndex) {
			if (responseValueIndex < 2 || responseValueIndex >= responseValueCount - 2) {
				hyman89C1Array[responseValueIndex] = besselC1Array[responseValueIndex];
			} else {
				double muMinus = (
					nodeC1Array[responseValueIndex - 1] * (
						2. * (
							predictorOrdinateArray[responseValueIndex] -
							predictorOrdinateArray[responseValueIndex - 1]
						) +
						predictorOrdinateArray[responseValueIndex - 1] -
						predictorOrdinateArray[responseValueIndex - 2]
					) - nodeC1Array[responseValueIndex - 2] * (
						predictorOrdinateArray[responseValueIndex] -
						predictorOrdinateArray[responseValueIndex - 1]
					)
				) / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
				double mu0 = (
					nodeC1Array[responseValueIndex - 1] * (
						predictorOrdinateArray[responseValueIndex + 1] -
						predictorOrdinateArray[responseValueIndex]
					) + nodeC1Array[responseValueIndex] * (
						predictorOrdinateArray[responseValueIndex] -
						predictorOrdinateArray[responseValueIndex - 1]
					)
				) / (
					predictorOrdinateArray[responseValueIndex + 1] -
					predictorOrdinateArray[responseValueIndex - 1]
				);
				double muPlus = (
					nodeC1Array[responseValueIndex] * (
						2. * (
							predictorOrdinateArray[responseValueIndex + 1] -
							predictorOrdinateArray[responseValueIndex]
						) +
						predictorOrdinateArray[responseValueIndex + 2] -
						predictorOrdinateArray[responseValueIndex + 1]
					) - nodeC1Array[responseValueIndex + 1] * (
						predictorOrdinateArray[responseValueIndex + 1] -
						predictorOrdinateArray[responseValueIndex]
					)
				) / (
					predictorOrdinateArray[responseValueIndex + 2] -
					predictorOrdinateArray[responseValueIndex]
				);

				try {
					double m = 3 * NumberUtil.Minimum (
						new double[] {
							Math.abs (nodeC1Array[responseValueIndex - 1]),
							Math.abs (nodeC1Array[responseValueIndex]),
							Math.abs (mu0),
							Math.abs (muPlus)
						}
					);

					if (!NumberUtil.SameSign (
						new double[] {
							mu0,
							muMinus,
							nodeC1Array[responseValueIndex - 1] - nodeC1Array[responseValueIndex - 2],
							nodeC1Array[responseValueIndex] - nodeC1Array[responseValueIndex - 1]
						}
					)) {
						m = Math.max (m, 1.5 * Math.min (Math.abs (mu0), Math.abs (muMinus)));
					} else if (!NumberUtil.SameSign (
						new double[] {
							-mu0,
							-muPlus,
							nodeC1Array[responseValueIndex] - nodeC1Array[responseValueIndex - 1],
							nodeC1Array[responseValueIndex + 1] - nodeC1Array[responseValueIndex]
						}
					)) {
						m = Math.max (m, 1.5 * Math.min (Math.abs (mu0), Math.abs (muPlus)));
					}

					hyman89C1Array[responseValueIndex] = 0.;

					if (0. < besselC1Array[responseValueIndex] * mu0) {
						hyman89C1Array[responseValueIndex] = besselC1Array[responseValueIndex] / Math.abs (
							besselC1Array[responseValueIndex]
						) * Math.min (Math.abs (besselC1Array[responseValueIndex]), m);
					}
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return hyman89C1Array;
	}

	/**
	 * Generate a Harmonic C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the
	 *  Response Values
	 * 
	 * 	Fritcsh and Butland (1984) A Method for constructing local monotonic piece-wise cubic interpolants -
	 *  	SIAM J on Scientific and Statistical Computing 5, 300-304.
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] HarmonicC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int responseValueCount = responseValueArray.length;
		double[] harmonicC1Array = new double[responseValueCount];

		double[] linearC1Array = LinearC1 (predictorOrdinateArray, responseValueArray);

		for (int responseValueIndex = 0; responseValueIndex < responseValueCount; ++responseValueIndex) {
			if (0 == responseValueIndex) {
				harmonicC1Array[responseValueIndex] = (
					predictorOrdinateArray[2] + predictorOrdinateArray[1] - 2. * predictorOrdinateArray[0]
				) * linearC1Array[0] / (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
				harmonicC1Array[responseValueIndex] -= (
					predictorOrdinateArray[1] - predictorOrdinateArray[0]
				) * linearC1Array[1] / (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
			} else if (responseValueCount - 1 == responseValueIndex) {
				harmonicC1Array[responseValueIndex] = -(
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 1]
				) * linearC1Array[responseValueIndex - 2] / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
				harmonicC1Array[responseValueIndex] += (
					2. * predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 1] -
					predictorOrdinateArray[responseValueIndex - 2]
				) * linearC1Array[responseValueIndex - 1] / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
			} else {
				if (0. >= linearC1Array[responseValueIndex - 1] * linearC1Array[responseValueIndex]) {
					harmonicC1Array[responseValueIndex] = 0.;
				} else {
					harmonicC1Array[responseValueIndex] = (
						predictorOrdinateArray[responseValueIndex] -
						predictorOrdinateArray[responseValueIndex - 1] + 2. * (
							predictorOrdinateArray[responseValueIndex + 1] -
							predictorOrdinateArray[responseValueIndex]
						)
					) / (
						3. * (
							predictorOrdinateArray[responseValueIndex + 1] -
							predictorOrdinateArray[responseValueIndex]
						)
					) / linearC1Array[responseValueIndex - 1];
					harmonicC1Array[responseValueIndex] += (
						predictorOrdinateArray[responseValueIndex + 1] -
						predictorOrdinateArray[responseValueIndex] + 2. * (
							predictorOrdinateArray[responseValueIndex] -
							predictorOrdinateArray[responseValueIndex - 1]
						)
					) / (
						3. * (
							predictorOrdinateArray[responseValueIndex + 1] -
							predictorOrdinateArray[responseValueIndex]
						)
					) / linearC1Array[responseValueIndex];
					harmonicC1Array[responseValueIndex] = 1. / harmonicC1Array[responseValueIndex];
				}
			}
		}

		return harmonicC1Array;
	}

	/**
	 * Generate a Van Leer Limiter C<sup>1</sup> Array from the specified Array of Predictor Ordinates and
	 *  the Response Values.
	 * 
	 * 	Van Leer (1974) Towards the Ultimate Conservative Difference Scheme. II - Monotonicity and
	 * 		Conservation combined in a Second-Order Scheme, Journal of Computational Physics 14 (4), 361-370.
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] VanLeerLimiterC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int responseValueCount = responseValueArray.length;
		double[] vanLeerLimiterC1Array = new double[responseValueCount];

		double[] nodeC1Array = LinearC1 (predictorOrdinateArray, responseValueArray);

		for (int responseValueIndex = 0; responseValueIndex < responseValueCount; ++responseValueIndex) {
			if (0 == responseValueIndex) {
				vanLeerLimiterC1Array[responseValueIndex] = (
					predictorOrdinateArray[2] + predictorOrdinateArray[1] - 2. * predictorOrdinateArray[0]
				) * nodeC1Array[0] / (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
				vanLeerLimiterC1Array[responseValueIndex] -= (
					predictorOrdinateArray[1] - predictorOrdinateArray[0]
				) * nodeC1Array[1] / (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
			} else if (responseValueCount - 1 == responseValueIndex) {
				vanLeerLimiterC1Array[responseValueIndex] = -(
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 1]
				) * nodeC1Array[responseValueIndex - 2] / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
				vanLeerLimiterC1Array[responseValueIndex] += (
					2. * predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 1] -
					predictorOrdinateArray[responseValueIndex - 2]
				) * nodeC1Array[responseValueIndex - 1] / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
			} else {
				if (0. != nodeC1Array[responseValueIndex - 1]) {
					double r = nodeC1Array[responseValueIndex] / nodeC1Array[responseValueIndex - 1];

					double rAbsolute = Math.abs (r);

					vanLeerLimiterC1Array[responseValueIndex] = nodeC1Array[responseValueIndex] *
						(r + rAbsolute) / (1. + rAbsolute);
				} else if (0. >= nodeC1Array[responseValueIndex]) {
					vanLeerLimiterC1Array[responseValueIndex] = 0.;
				} else if (0. < nodeC1Array[responseValueIndex]) {
					vanLeerLimiterC1Array[responseValueIndex] = 2. * nodeC1Array[responseValueIndex];
				}
			}
		}

		return vanLeerLimiterC1Array;
	}

	/**
	 * Generate a Huynh Le Floch Limiter C<sup>1</sup> Array from the specified Array of Predictor Ordinates
	 *  and the Response Values.
	 * 
	 * 	Huynh (1993) Accurate Monotone Cubic Interpolation, SIAM J on Numerical Analysis 30 (1), 57-100.
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] HuynhLeFlochLimiterC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int responseValueCount = responseValueArray.length;
		double[] huynhLeFlochLimiterC1Array = new double[responseValueCount];

		double[] nodeC1Array = LinearC1 (predictorOrdinateArray, responseValueArray);

		for (int responseValueIndex = 0; responseValueIndex < responseValueCount; ++responseValueIndex) {
			if (0 == responseValueIndex) {
				huynhLeFlochLimiterC1Array[responseValueIndex] = (
					predictorOrdinateArray[2] + predictorOrdinateArray[1] - 2. * predictorOrdinateArray[0]
				) * nodeC1Array[0] / (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
				huynhLeFlochLimiterC1Array[responseValueIndex] -= (
					predictorOrdinateArray[1] - predictorOrdinateArray[0]
				) * nodeC1Array[1] / (predictorOrdinateArray[2] - predictorOrdinateArray[0]);
			} else if (responseValueCount - 1 == responseValueIndex) {
				huynhLeFlochLimiterC1Array[responseValueIndex] = -(
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 1]
				) * nodeC1Array[responseValueIndex - 2] / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
				huynhLeFlochLimiterC1Array[responseValueIndex] += (
					2. * predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 1] -
					predictorOrdinateArray[responseValueIndex - 2]
				) * nodeC1Array[responseValueIndex - 1] / (
					predictorOrdinateArray[responseValueIndex] -
					predictorOrdinateArray[responseValueIndex - 2]
				);
			} else {
				double monotoneIndicator = nodeC1Array[responseValueIndex] *
					nodeC1Array[responseValueIndex - 1];

					huynhLeFlochLimiterC1Array[responseValueIndex] = 0. >= monotoneIndicator ? 0. :
						3. * monotoneIndicator * (
							nodeC1Array[responseValueIndex] +
							nodeC1Array[responseValueIndex - 1]
						) / (
							nodeC1Array[responseValueIndex] * nodeC1Array[responseValueIndex] +
							nodeC1Array[responseValueIndex - 1] * nodeC1Array[responseValueIndex - 1] * 4. *
								monotoneIndicator
						);
			}
		}

		return huynhLeFlochLimiterC1Array;
	}

	/**
	 * Generate a Kruger C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response
	 *  Values.
	 * 
	 * 	Kruger (2002) Constrained Cubic Spline Interpolations for Chemical Engineering Application,
	 *  	http://www.korf.co.uk/spline.pdf
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] KrugerC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		int responseValueCount = responseValueArray.length;
		double[] krugerSlopeArray = new double[responseValueCount];

		double[] slopeC1Array = LinearC1 (predictorOrdinateArray, responseValueArray);

		if (null == slopeC1Array || slopeC1Array.length != responseValueCount - 1) {
			return null;
		}

		for (int responseValueIndex = 0; responseValueIndex < responseValueCount; ++responseValueIndex) {
			if (0 != responseValueIndex && responseValueCount - 1 != responseValueIndex) {
				krugerSlopeArray[responseValueIndex] =
					0. >= slopeC1Array[responseValueIndex - 1] * slopeC1Array[responseValueIndex] ? 0. :
					2. / (
						(1. / slopeC1Array[responseValueIndex - 1]) + (1. / slopeC1Array[responseValueIndex])
					);
			}
		}

		krugerSlopeArray[0] = 3.5 * slopeC1Array[0] - 0.5 * krugerSlopeArray[1];
		krugerSlopeArray[responseValueCount - 1] = 3.5 * slopeC1Array[responseValueCount - 2] - 0.5 *
			krugerSlopeArray[responseValueCount - 2];
		return krugerSlopeArray;
	}

	/**
	 * Generate a Akima C<sup>1</sup> Array from the specified Array of Predictor Ordinates and the Response
	 *  Values.
	 * 
	 * 	Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures,
	 * 		Journal of the Association for the Computing Machinery 17 (4), 589-602.
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public static final double[] AkimaC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		AkimaLocalC1Generator akimaLocalC1Generator = AkimaLocalC1Generator.Create (
			predictorOrdinateArray,
			responseValueArray
		);

		return null == akimaLocalC1Generator ? null : akimaLocalC1Generator.C1();
	}

	/**
	 * Verify if the given Quintic Polynomial is Monotone using the Hyman89 Algorithm
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param firstDerivativeArray Array of First Derivatives
	 * @param secondDerivativeArray Array of Second Derivatives
	 * 
	 * @return TRUE - The given Quintic Polynomial is Monotone
	 * 
	 * @throws java.lang.Exception Thrown if the Monotonicity cannot be determined
	 */

	public static final boolean VerifyHyman89QuinticMonotonicity (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final double[] firstDerivativeArray,
		final double[] secondDerivativeArray)
		throws Exception
	{
		if (null == predictorOrdinateArray || null == responseValueArray || null == firstDerivativeArray ||
			null == secondDerivativeArray) {
			throw new Exception (
				"LocalMonotoneCkGenerator::VerifyHyman89QuinticMonotonicity => Invalid Inputs"
			);
		}

		int predictorOrdinateCount = predictorOrdinateArray.length;

		if (1 >= predictorOrdinateCount || predictorOrdinateCount != responseValueArray.length ||
			predictorOrdinateCount != responseValueArray.length ||
			predictorOrdinateCount != responseValueArray.length) {
			throw new Exception (
				"LocalMonotoneCkGenerator::VerifyHyman89QuinticMonotonicity => Invalid Inputs"
			);
		}

		for (int predictorOrdinateIndex = 1; predictorOrdinateIndex < predictorOrdinateCount - 1;
			++predictorOrdinateIndex) {
			double absoluteResponseValue = Math.abs (responseValueArray[predictorOrdinateIndex]);

			double responseValueSign = responseValueArray[predictorOrdinateIndex] > 0. ? 1. : -1.;
			double hMinus = (
				predictorOrdinateArray[predictorOrdinateIndex] -
				predictorOrdinateArray[predictorOrdinateIndex - 1]
			);
			double hPlus = (
				predictorOrdinateArray[predictorOrdinateIndex + 1] -
				predictorOrdinateArray[predictorOrdinateIndex]
			);

			if (-5. * absoluteResponseValue / hPlus >
					responseValueSign * firstDerivativeArray[predictorOrdinateIndex] ||
				5. * absoluteResponseValue / hMinus <
					responseValueSign * firstDerivativeArray[predictorOrdinateIndex]
			) {
				return false;
			}

			if (responseValueSign * secondDerivativeArray[predictorOrdinateIndex] <
				responseValueSign * Math.max (
					8. * firstDerivativeArray[predictorOrdinateIndex] / hMinus -
						20. * responseValueArray[predictorOrdinateIndex] / hMinus / hMinus,
					-8. * firstDerivativeArray[predictorOrdinateIndex] / hPlus -
						20. * responseValueArray[predictorOrdinateIndex] / hPlus / hPlus
				)
			) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Generate C<sup>1</sup> Slope Quintic Polynomial is Monotone using the Hyman89 Algorithm
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param firstDerivativeArray Array of First Derivatives
	 * @param secondDerivativeArray Array of Second Derivatives
	 * 
	 * @return The C<sup>1</sup> Slope Quintic Stretch
	 */

	public static final double[] Hyman89QuinticMonotoneC1 (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final double[] firstDerivativeArray,
		final double[] secondDerivativeArray)
	{
		if (null == predictorOrdinateArray || null == responseValueArray || null == firstDerivativeArray ||
			null == secondDerivativeArray) {
			return null;
		}

		int predictorCount = predictorOrdinateArray.length;

		if (1 >= predictorCount || predictorCount != responseValueArray.length ||
			predictorCount != responseValueArray.length || predictorCount != responseValueArray.length) {
			return null;
		}

		double[] adjustedFirstDerivativeArray = new double[predictorCount];

		double[] nodeC1Array = LinearC1 (predictorOrdinateArray, responseValueArray);

		double[] besselC1Array = BesselC1 (predictorOrdinateArray, responseValueArray);

		for (int predictorIndex = 0; predictorIndex < predictorCount; ++predictorIndex) {
			if (2 < predictorIndex || predictorIndex >= predictorCount - 2) {
				adjustedFirstDerivativeArray[predictorIndex] = besselC1Array[predictorIndex];
			} else {
				double sign = 0.;
				double hPlus = predictorOrdinateArray[predictorIndex + 1] -
					predictorOrdinateArray[predictorIndex];
				double hMinus = predictorOrdinateArray[predictorIndex] -
					predictorOrdinateArray[predictorIndex - 1];

				if (0. > firstDerivativeArray[predictorIndex - 1] * firstDerivativeArray[predictorIndex]) {
					sign = 0. < responseValueArray[predictorIndex] ? 1. : -1.;
				}

				adjustedFirstDerivativeArray[predictorIndex] = Math.max (
					Math.min (0., firstDerivativeArray[predictorIndex]),
					(0. <= sign ? 5. : -5.) * Math.min (
						Math.abs (firstDerivativeArray[predictorIndex - 1]),
						Math.abs (firstDerivativeArray[predictorIndex])
					)
				);

				double a = Math.max (
					0.,
					adjustedFirstDerivativeArray[predictorIndex] / nodeC1Array[predictorIndex - 1]
				);

				double b = Math.max (
					0.,
					adjustedFirstDerivativeArray[predictorIndex + 1] / nodeC1Array[predictorIndex]
				);

				double dPlus =
					0. < adjustedFirstDerivativeArray[predictorIndex] * nodeC1Array[predictorIndex] ?
					adjustedFirstDerivativeArray[predictorIndex] : 0.;
				double dMinus =
					0. < adjustedFirstDerivativeArray[predictorIndex] * nodeC1Array[predictorIndex - 1] ?
					adjustedFirstDerivativeArray[predictorIndex] : 0.;
				double aLeft = (-7.9 * dPlus - 0.26 * dPlus * b) / hPlus;
				double aRight = (
					(20. - 2. * b) * nodeC1Array[predictorIndex] - 8. * dPlus - 0.48 * dPlus * b
				) / hPlus;
				double bLeft = (
					(2 * a - 20) * nodeC1Array[predictorIndex - 1] + 8 * dMinus - 0.48 * dMinus * a
				) / hMinus;
				double bRight = (7.9 * dMinus + 0.26 * dMinus * a) / hMinus;

				if (aRight <= bLeft || aLeft >= bRight) {
					adjustedFirstDerivativeArray[predictorIndex] =
						(20. - 2. * b) * nodeC1Array[predictorIndex] / hPlus;
					adjustedFirstDerivativeArray[predictorIndex] +=
						(20. - 2. * a) * nodeC1Array[predictorIndex - 1] / hMinus;
					adjustedFirstDerivativeArray[predictorIndex] /= (
						(8. + 0.48 * b) / hMinus) + ((8. + 0.48 * a) / hMinus
					);
				}
			}
		}

		return adjustedFirstDerivativeArray;
	}

	/**
	 * Generate the Local Control Stretch in accordance with the desired Customization Parameters
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * @param generatorType The C<sup>1</sup> Generator Type
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Instance of the Local Control Stretch
	 */

	public static final LocalMonotoneCkGenerator Create (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final String generatorType,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		try {
			LocalMonotoneCkGenerator localMonotoneCkGenerator = new LocalMonotoneCkGenerator (
				predictorOrdinateArray,
				responseValueArray
			);

			if (!localMonotoneCkGenerator.generateC1 (generatorType)) {
				return null;
			}

			if (eliminateSpuriousExtrema && !localMonotoneCkGenerator.eliminateSpuriousExtrema()) {
				return null;
			}

			if (applyMonotoneFilter) {
				if (!localMonotoneCkGenerator.applyMonotoneFilter()) {
					return null;
				}
			}

			return localMonotoneCkGenerator;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Local Control Stretch in accordance with the desired Customization Parameters
	 * 
	 * @param predictorOrdinateArray The Predictor Ordinate Array
	 * @param responseValueArray The Response Value Array
	 * @param generatorType The C<sup>1</sup> Generator Type
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Instance of the Local Control Stretch
	 */

	public static final LocalMonotoneCkGenerator Create (
		final int[] predictorOrdinateArray,
		final double[] responseValueArray,
		final String generatorType,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		if (null == predictorOrdinateArray) {
			return null;
		}

		int predictorCount = predictorOrdinateArray.length;
		double[] clonedPredictorArray = new double[predictorCount];

		if (0 == predictorCount) {
			return null;
		}

		for (int predictorIndex = 0; predictorIndex < predictorCount; ++predictorIndex) {
			clonedPredictorArray[predictorIndex] = predictorOrdinateArray[predictorIndex];
		}

		return Create (
			clonedPredictorArray,
			responseValueArray,
			generatorType,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);
	}

	private LocalMonotoneCkGenerator (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
		throws Exception
	{
		if (null == (_predictorOrdinateArray = predictorOrdinateArray) ||
			null == (_responseValueArray = responseValueArray)) {
			throw new Exception ("LocalMonotoneCkGenerator ctr: Invalid Inputs!");
		}

		int predictorCount = _predictorOrdinateArray.length;

		if (0 == predictorCount || predictorCount != _responseValueArray.length) {
			throw new Exception ("LocalMonotoneCkGenerator ctr: Invalid Inputs!");
		}
	}

	private boolean generateC1 (
		final String generatorType)
	{
		if (null == generatorType || generatorType.isEmpty()) {
			return false;
		}

		if (C1_AKIMA.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = AkimaC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_BESSEL.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = BesselC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_HARMONIC.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = HarmonicC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_HUYNH_LE_FLOCH.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = HuynhLeFlochLimiterC1 (_predictorOrdinateArray, _responseValueArray))
				&& 0 != _c1Array.length;
		}

		if (C1_HYMAN83.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = Hyman83C1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_HYMAN89.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = Hyman89C1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_KRUGER.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = KrugerC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_MONOTONE_CONVEX.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = BesselC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_VANILLA.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = LinearC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		if (C1_VAN_LEER.equalsIgnoreCase (generatorType)) {
			return null != (_c1Array = VanLeerLimiterC1 (_predictorOrdinateArray, _responseValueArray)) &&
				0 != _c1Array.length;
		}

		return false;
	}

	private boolean eliminateSpuriousExtrema()
	{
		return null != (
			_c1Array = EliminateSpuriousExtrema (
				_c1Array,
				LinearC1 (_predictorOrdinateArray, _responseValueArray)
			)
		) && 0 != _c1Array.length; 
	}

	private boolean applyMonotoneFilter()
	{
		return null != (
			_c1Array = ApplyMonotoneFilter (
				_c1Array,
				LinearC1 (_predictorOrdinateArray, _responseValueArray)
			)
		) && 0 != _c1Array.length; 
	}

	/**
	 * Retrieve the C<sup>1</sup> Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public double[] C1()
	{
		return _c1Array;
	}
}
