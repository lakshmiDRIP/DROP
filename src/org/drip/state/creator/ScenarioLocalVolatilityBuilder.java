
package org.drip.state.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * ScenarioLocalVolatilityBuilder implements the construction of the Local Volatility surface using the input
 * 	option instruments, their Call Prices, and a wide variety of custom build schemes.
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblRiskFreeRate)) return null;

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
