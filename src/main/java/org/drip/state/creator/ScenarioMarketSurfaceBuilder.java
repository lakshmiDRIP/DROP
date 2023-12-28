
package org.drip.state.creator;

import java.util.TreeMap;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.MarketSurface;
import org.drip.analytics.support.Helper;
import org.drip.param.pricer.HestonOptionPricerParams;
import org.drip.pricer.option.BlackScholesAlgorithm;
import org.drip.pricer.option.Greeks;
import org.drip.pricer.option.HestonStochasticVolatilityAlgorithm;
import org.drip.service.common.StringUtil;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.KaklisPandelisSetParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.grid.Span;
import org.drip.spline.multidimensional.WireSurfaceStretch;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.curve.BasisSplineMarketSurface;
import org.drip.state.identifier.CustomLabel;

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
 * <i>ScenarioMarketSurfaceBuilder</i> implements the construction of the scenario market Node surface using
 * 	the input option instruments, their quotes, and a wide variety of custom builds. It implements the
 *  following Functions:
 * 
 * <ul>
 * 		<li>Build an Instance of the Market Node Surface using Custom Wire Span and Surface Splines</li>
 * 		<li>Construct a Scenario Market Surface off of Cubic Polynomial Wire Spline and Cubic Polynomial Surface Spline</li>
 * 		<li>Construct a Scenario Market Surface off of Quartic Polynomial Wire Spline and Quartic Polynomial Surface Spline</li>
 * 		<li>Construct a Scenario Market Surface off of Kaklis-Pandelis Wire Spline and Kaklis-Pandelis Surface Spline</li>
 * 		<li>Construct a Scenario Market Surface off of KLK Hyperbolic Wire Spline and KLK Hyperbolic Surface Spline</li>
 * 		<li>Construct a Scenario Market Surface off of KLK Rational Linear Wire Spline and KLK Rational Linear Surface Spline</li>
 * 		<li>Construct a Scenario Market Surface off of KLK Rational Quadratic Wire Spline and KLK Rational Quadratic Surface Spline</li>
 * 		<li>Construct a Scenario Market Surface off of Custom Wire Spline and Custom Surface Spline</li>
 * 		<li>Create a Price/Volatility Market Surface Based off of a Single Run using the Heston 1993 Model</li>
 * </ul>
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

public class ScenarioMarketSurfaceBuilder {

	/**
	 * Build an Instance of the Market Node Surface using Custom Wire Span and Surface Splines.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param yArray Array of Y Ordinates
	 * @param nodeGrid Double Array of the Surface Nodes
	 * @param wireSpanSegmentCustomBuilderControl The Wire Span Segment Customizer
	 * @param surfaceSegmentCustomBuilderControl The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface CustomSplineWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final double[] yArray,
		final double[][] nodeGrid,
		final SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl,
		final SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl)
	{
		if (null == name || name.isEmpty() || null == startDate || null == currency || currency.isEmpty() ||
			null == xArray || null == yArray || null == nodeGrid ||
			null == wireSpanSegmentCustomBuilderControl || null == surfaceSegmentCustomBuilderControl)
			return null;

		int xCount = xArray.length;
		int maturityCount = yArray.length;
		int nodeGridOuterArrayCount = nodeGrid.length;

		if (0 == xCount || 0 == maturityCount || xCount != nodeGridOuterArrayCount) {
			return null;
		}

		for (int xIndex = 0; xIndex < xCount; ++xIndex) {
			double[] nodeGridInnerArray = nodeGrid[xIndex];

			if (null == nodeGridInnerArray || maturityCount != nodeGridInnerArray.length) {
				return null;
			}
		}

		SegmentCustomBuilderControl[] wireSpanSegmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[xCount - 1];

		for (int xIndex = 0; xIndex < xCount - 1; ++xIndex) {
			wireSpanSegmentCustomBuilderControlArray[xIndex] = wireSpanSegmentCustomBuilderControl;
		}

		TreeMap<Double, Span> wireSpanMap = new TreeMap<Double, Span>();

		for (int xIndex = 0; xIndex < xCount; ++xIndex) {
			MultiSegmentSequence wireMultiSegmentSequence =
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"Stretch@" + name + "@" + StringUtil.GUID(),
					yArray,
					nodeGrid[xIndex],
					wireSpanSegmentCustomBuilderControlArray,
					null,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE
				);

			if (null == wireMultiSegmentSequence) {
				return null;
			}

			try {
				wireSpanMap.put (xArray[xIndex], new OverlappingStretchSpan (wireMultiSegmentSequence));
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new BasisSplineMarketSurface (
				startDate.julian(),
				CustomLabel.Standard (name),
				currency,
				new WireSurfaceStretch (
					"WireSurfaceStretch@" + name + "@" + StringUtil.GUID(),
					surfaceSegmentCustomBuilderControl,
					wireSpanMap
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Scenario Market Surface off of Cubic Polynomial Wire Spline and Cubic Polynomial Surface
	 * 	Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface CubicPolynomialWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int yIndex = 0; yIndex < tenorCount; ++yIndex) {
			yArray[yIndex] = startDate.addTenor (tenorArray[yIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of Quartic Polynomial Wire Spline and Quartic Polynomial
	 * 	Surface Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface QuarticPolynomialWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int yIndex = 0; yIndex < tenorCount; ++yIndex) {
			yArray[yIndex] = startDate.addTenor (tenorArray[yIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (5),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (5),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of Kaklis-Pandelis Wire Spline and Kaklis-Pandelis Surface
	 * 	Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface KaklisPandelisWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int yIndex = 0; yIndex < tenorCount; ++yIndex) {
			yArray[yIndex] = startDate.addTenor (tenorArray[yIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
				new KaklisPandelisSetParams (2),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
				new KaklisPandelisSetParams (2),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of KLK Hyperbolic Wire Spline and KLK Hyperbolic Surface
	 * 	Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * @param tension The Tension Parameter
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface KLKHyperbolicWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid,
		final double tension)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int yIndex = 0; yIndex < tenorCount; ++yIndex) {
			yArray[yIndex] = startDate.addTenor (tenorArray[yIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
				new ExponentialTensionSetParams (tension),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
				new ExponentialTensionSetParams (tension),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of KLK Rational Linear Wire Spline and KLK Rational Linear
	 * 	Surface Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * @param tension The Tension Parameter
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface KLKRationalLinearWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid,
		final double tension)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int yIndex = 0; yIndex < tenorCount; ++yIndex) {
			yArray[yIndex] = startDate.addTenor (tenorArray[yIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
				new ExponentialTensionSetParams (tension),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
				new ExponentialTensionSetParams (tension),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of KLK Rational Quadratic Wire Spline and KLK Rational
	 * 	Quadratic Surface Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * @param tension The Tension Parameter
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface KLKRationalQuadraticWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid,
		final double tension)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int yIndex = 0; yIndex < tenorCount; ++yIndex) {
			yArray[yIndex] = startDate.addTenor (tenorArray[yIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
				new ExponentialTensionSetParams (tension),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
				new ExponentialTensionSetParams (tension),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of Custom Wire Spline and Custom Surface Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param xArray Array of X Ordinates
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * @param wireSpanSegmentCustomBuilderControl The Wire Span Segment Customizer
	 * @param surfaceSegmentCustomBuilderControl The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface CustomWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double[] xArray,
		final String[] tenorArray,
		final double[][] nodeGrid,
		final SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl,
		final SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] yArray = new double[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			yArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			xArray,
			yArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Create a Price/Volatility Market Surface Based off of a Single Run using the Heston 1993 Model
	 * 
	 * @param name Surface Name
	 * @param startDate Epoch/Start Date
	 * @param currency Currency
	 * @param riskFreeRate Risk-Free Rate
	 * @param underlier The Underlier
	 * @param isForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param initialVolatility Initial Volatility
	 * @param strikeArray Array of Strikes
	 * @param tenorArray Array of Maturity Tenors
	 * @param hestonOptionPricerParams The Heston Stochastic Volatility Generation Parameters
	 * @param generatePriceSurface TRUE - Generate the Price Surface; FALSE - Generate the Vol Surface
	 * @param wireSpanSegmentCustomBuilderControl The Wire Span Segment Customizer
	 * @param surfaceSpanSegmentCustomBuilderControl The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface HestonRunMarketSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double riskFreeRate,
		final double underlier,
		final boolean isForward,
		final double initialVolatility,
		final double[] strikeArray,
		final String[] tenorArray,
		final HestonOptionPricerParams hestonOptionPricerParams,
		final boolean generatePriceSurface,
		final SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl,
		final SegmentCustomBuilderControl surfaceSpanSegmentCustomBuilderControl)
	{
		if (null == strikeArray || null == tenorArray) {
			return null;
		}

		int strikeCounter = 0;
		int tenorCount = tenorArray.length;
		int strikeCount = strikeArray.length;
		double[][] impliedNodeGrid = new double[strikeCount][tenorCount];
		HestonStochasticVolatilityAlgorithm hestonStochasticVolatilityAlgorithm = null;

		try {
			hestonStochasticVolatilityAlgorithm = new HestonStochasticVolatilityAlgorithm
				(hestonOptionPricerParams);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (0 == tenorCount || 0 == strikeCount) {
			return null;
		}

		for (double strike : strikeArray) {
			int tenorCounter = 0;

			for (String tenor : tenorArray) {
				try {
					double timeToExpiry = Helper.TenorToYearFraction (tenor);

					Greeks callGreeks = hestonStochasticVolatilityAlgorithm.greeks (
						strike,
						timeToExpiry,
						riskFreeRate,
						underlier,
						false,
						isForward,
						initialVolatility
					);

					if (null == callGreeks) {
						return null;
					}

					impliedNodeGrid[strikeCounter][tenorCounter++] = generatePriceSurface ?
						callGreeks.price() : new BlackScholesAlgorithm().impliedVolatilityFromPrice (
							strike,
							timeToExpiry,
							riskFreeRate,
							underlier,
							false,
							false,
							callGreeks.price()
						);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			++strikeCounter;
		}

		return CustomWireSurface (
			name,
			startDate,
			currency,
			strikeArray,
			tenorArray,
			impliedNodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSpanSegmentCustomBuilderControl
		);
	}
}
