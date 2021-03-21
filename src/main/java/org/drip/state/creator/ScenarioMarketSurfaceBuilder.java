
package org.drip.state.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * the input option instruments, their quotes, and a wide variety of custom builds.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioMarketSurfaceBuilder {

	/**
	 * Build an Instance of the Market Node Surface using Custom Wire Span and Surface Splines.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param adblY Array of Y Ordinates
	 * @param aadblNode Double Array of the Surface Nodes
	 * @param scbcWireSpan The Wire Span Segment Customizer
	 * @param scbcSurface The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface CustomSplineWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final double[] adblY,
		final double[][] aadblNode,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcSurface)
	{
		if (null == dtStart || null == strName || strName.isEmpty() || null == strCurrency ||
			strCurrency.isEmpty() || null == adblX || null == adblY || null == aadblNode || null ==
				scbcWireSpan || null == scbcSurface)
			return null;

		int iNumX = adblX.length;
		int iNumMaturity = adblY.length;
		int iNumOuterNode = aadblNode.length;

		if (0 == iNumX || 0 == iNumMaturity || iNumX != iNumOuterNode) return null;

		for (int i = 0; i < iNumX; ++i) {
			double[] adblInner = aadblNode[i];

			if (null == adblInner || iNumMaturity != adblInner.length) return null;
		}

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCWireSpan = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumX - 1];

		for (int i = 0; i < iNumX - 1; ++i)
			aSCBCWireSpan[i] = scbcWireSpan;

		java.util.TreeMap<java.lang.Double, org.drip.spline.grid.Span> mapWireSpan = new
			java.util.TreeMap<java.lang.Double, org.drip.spline.grid.Span>();

		for (int i = 0; i < iNumX; ++i) {
			org.drip.spline.stretch.MultiSegmentSequence mssWire =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					("Stretch@" + strName + "@" + org.drip.service.common.StringUtil.GUID(), adblY,
						aadblNode[i], aSCBCWireSpan, null,
							org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
								org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

			if (null == mssWire) return null;

			try {
				mapWireSpan.put (adblX[i], new org.drip.spline.grid.OverlappingStretchSpan (mssWire));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.state.curve.BasisSplineMarketSurface (dtStart.julian(),
				org.drip.state.identifier.CustomLabel.Standard (strName), strCurrency, new
					org.drip.spline.multidimensional.WireSurfaceStretch ("WireSurfaceStretch@" + strName +
						"@" + org.drip.service.common.StringUtil.GUID(), scbcSurface, mapWireSpan));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Scenario Market Surface off of Cubic Polynomial Wire Spline and Cubic Polynomial Surface
	 * 	Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface CubicPolynomialWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			scbcWireSpan = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (4),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

			scbcSurface = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (4),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of Quartic Polynomial Wire Spline and Quartic Polynomial
	 * 	Surface Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface QuarticPolynomialWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			scbcWireSpan = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (5),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

			scbcSurface = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (5),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of Kaklis-Pandelis Wire Spline and Kaklis-Pandelis Surface
	 * 	Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface KaklisPandelisWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			scbcWireSpan = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS, new
					org.drip.spline.basis.KaklisPandelisSetParams (2),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

			scbcSurface = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS, new
					org.drip.spline.basis.KaklisPandelisSetParams (2),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of KLK Hyperbolic Wire Spline and KLK Hyperbolic Surface
	 * 	Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * @param dblTension The Tension Parameter
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface KLKHyperbolicWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode,
		final double dblTension)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			scbcWireSpan = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION, new
					org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

			scbcSurface = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION, new
					org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of KLK Rational Linear Wire Spline and KLK Rational Linear
	 * 	Surface Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * @param dblTension The Tension Parameter
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface KLKRationalLinearWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode,
		final double dblTension)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			scbcWireSpan = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
					new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

			scbcSurface = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
					new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of KLK Rational Quadratic Wire Spline and KLK Rational
	 * 	Quadratic Surface Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * @param dblTension The Tension Parameter
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface KLKRationalQuadraticWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode,
		final double dblTension)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			scbcWireSpan = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
					new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);

			scbcSurface = new org.drip.spline.params.SegmentCustomBuilderControl
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
					new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of Custom Wire Spline and Custom Surface Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param adblX Array of X Ordinates
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * @param scbcWireSpan The Wire Span Segment Customizer
	 * @param scbcSurface The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface CustomWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblX,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcSurface)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblY = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblY[i] = dtStart.addTenor (astrTenor[i]).julian();

		return CustomSplineWireSurface (strName, dtStart, strCurrency, adblX, adblY, aadblNode, scbcWireSpan,
			scbcSurface);
	}

	/**
	 * Create a Price/Volatility Market Surface Based off of a Single Run using the Heston 1993 Model
	 * 
	 * @param strName Surface Name
	 * @param dtStart Epoch/Start Date
	 * @param strCurrency Currency
	 * @param dblRiskFreeRate Risk-Free Rate
	 * @param dblUnderlier The Underlier
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dblInitialVolatility Initial Volatility
	 * @param adblStrike Array of Strikes
	 * @param astrTenor Array of Maturity Tenors
	 * @param fphp The Heston Stochastic Volatility Generation Parameters
	 * @param bPriceSurface TRUE - Generate the Price Surface; FALSE - Generate the Vol Surface
	 * @param scbcWireSpan The Wire Span Segment Customizer
	 * @param scbcSurface The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface HestonRunMarketSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsForward,
		final double dblInitialVolatility,
		final double[] adblStrike,
		final java.lang.String[] astrTenor,
		final org.drip.param.pricer.HestonOptionPricerParams fphp,
		final boolean bPriceSurface,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcSurface)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblRiskFreeRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblInitialVolatility) || null == adblStrike ||
					null == astrTenor || null == fphp)
			return null;

		int iStrike = 0;
		int iNumTenor = astrTenor.length;
		int iNumStrike = adblStrike.length;
		double[][] aadblImpliedNode = new double[iNumStrike][iNumTenor];
		org.drip.pricer.option.HestonStochasticVolatilityAlgorithm hsva = null;

		try {
			hsva = new org.drip.pricer.option.HestonStochasticVolatilityAlgorithm (fphp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (0 == iNumTenor || 0 == iNumStrike) return null;

		for (double dblStrike : adblStrike) {
			int iTenor = 0;

			for (java.lang.String strTenor : astrTenor) {
				try {
					double dblTimeToExpiry = org.drip.analytics.support.Helper.TenorToYearFraction
						(strTenor);

					org.drip.pricer.option.Greeks callGreeks = hsva.greeks (dblStrike, dblTimeToExpiry,
						dblRiskFreeRate, dblUnderlier, false, bIsForward, dblInitialVolatility);

					if (null == callGreeks) return null;

					aadblImpliedNode[iStrike][iTenor++] = bPriceSurface ? callGreeks.price() : new
						org.drip.pricer.option.BlackScholesAlgorithm().impliedVolatilityFromPrice
							(dblStrike, dblTimeToExpiry, dblRiskFreeRate, dblUnderlier, false, false,
								callGreeks.price());
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			++iStrike;
		}

		return CustomWireSurface (strName, dtStart, strCurrency, adblStrike, astrTenor, aadblImpliedNode,
			scbcWireSpan, scbcSurface);
	}
}
