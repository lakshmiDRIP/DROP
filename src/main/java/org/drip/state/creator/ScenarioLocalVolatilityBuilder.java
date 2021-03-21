
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
 * <i>ScenarioLocalVolatilityBuilder</i> implements the construction of the Local Volatility surface using
 * the input option instruments, their Call Prices, and a wide variety of custom build schemes.
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

public class ScenarioLocalVolatilityBuilder {

	/**
	 * Create a Volatility Curve from the Calibration Instruments
	 * 
	 * @param strName Volatility Curve name
	 * @param dtSpot Spot Date
	 * @param lslUnderlying Underlying Latent State Label
	 * @param aFRACapFloor Array of the FRA Cap Floor Instruments
	 * @param adblCalibQuote Input Calibration Quotes
	 * @param astrCalibMeasure Input Calibration Measures
	 * @param dc Base Discount Curve
	 * @param fc Forward Curve
	 * @param lsfc Latent State Fixings Container
	 * 
	 * @return The Calibrated Volatility Curve
	 */

	public static final org.drip.state.volatility.VolatilityCurve NonlinearBuild (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.LatentStateLabel lslUnderlying,
		final org.drip.product.fra.FRAStandardCapFloor[] aFRACapFloor,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
	{
		return null == dtSpot ? null : org.drip.state.boot.VolatilityCurveScenario.Standard (strName,
			org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), lslUnderlying, aFRACapFloor,
				adblCalibQuote, astrCalibMeasure, false, dc, fc, lsfc, null);
	}

	/**
	 * Build an Instance of the Volatility Surface using custom wire span and surface splines
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param dblRiskFreeRate Risk Free Discounting Rate
	 * @param adblStrike Array of Strikes
	 * @param adblMaturity Array of Maturities
	 * @param aadblCallPrice Double Array of the Call Prices
	 * @param scbcWireSpan The Wire Span Segment Customizer
	 * @param scbcSurface The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface CustomSplineWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double dblRiskFreeRate,
		final double[] adblStrike,
		final double[] adblMaturity,
		final double[][] aadblCallPrice,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcSurface)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblRiskFreeRate)) return null;

		org.drip.analytics.definition.MarketSurface msCallPrice =
			org.drip.state.creator.ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (strName +
				"_CALL_PRICE_SURFACE", dtStart, strCurrency, adblStrike, adblMaturity, aadblCallPrice,
					scbcWireSpan, scbcSurface);

		if (null == msCallPrice) return null;

		int iNumStrike = adblStrike.length;
		int iNumMaturity = adblMaturity.length;
		double[][] aadblLocalVolatility = new double[iNumStrike][iNumMaturity];
		org.drip.analytics.definition.NodeStructure[] aTSMaturityAnchor = new
			org.drip.analytics.definition.NodeStructure[iNumMaturity];

		for (int j = 0; j < iNumMaturity; ++j) {
			if (null == (aTSMaturityAnchor[j] = msCallPrice.yAnchorTermStructure (adblMaturity[j])))
				return null;
		}

		for (int i = 0; i < iNumStrike; ++i) {
			org.drip.analytics.definition.NodeStructure tsStrikeAnchor = msCallPrice.xAnchorTermStructure
				(adblStrike[i]);

			if (null == tsStrikeAnchor) return null;

			for (int j = 0; j < iNumMaturity; ++j) {
				try {
					aadblLocalVolatility[i][j] = java.lang.Math.sqrt ((tsStrikeAnchor.nodeDerivative ((int)
						adblMaturity[j], 1) + dblRiskFreeRate * adblStrike[i] *
							aTSMaturityAnchor[j].nodeDerivative ((int) adblStrike[i], 1)) / (adblStrike[i] *
								adblStrike[i] * aTSMaturityAnchor[j].nodeDerivative ((int) adblStrike[i],
									2)));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return org.drip.state.creator.ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (strName, dtStart,
			strCurrency, adblStrike, adblMaturity, aadblLocalVolatility, scbcWireSpan, scbcSurface);
	}

	/**
	 * Construct a Scenario Market Surface off of cubic polynomial wire spline and cubic polynomial surface
	 * 	Spline.
	 * 
	 * @param strName Name of the Volatility Surface
	 * @param dtStart Start/Epoch Julian Date
	 * @param strCurrency Currency
	 * @param dblRiskFreeRate Risk Free Discounting Rate
	 * @param adblStrike Array of Strikes
	 * @param astrTenor Array of Maturity Tenors
	 * @param aadblNode Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final org.drip.analytics.definition.MarketSurface CubicPolynomialWireSurface (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double dblRiskFreeRate,
		final double[] adblStrike,
		final java.lang.String[] astrTenor,
		final double[][] aadblNode)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblMaturity = new double[iNumTenor];
		org.drip.spline.params.SegmentCustomBuilderControl scbcSurface = null;
		org.drip.spline.params.SegmentCustomBuilderControl scbcWireSpan = null;

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblMaturity[i] = dtStart.addTenor (astrTenor[i]).julian();

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

		return CustomSplineWireSurface (strName, dtStart, strCurrency, dblRiskFreeRate, adblStrike,
			adblMaturity, aadblNode, scbcWireSpan, scbcSurface);
	}
}
