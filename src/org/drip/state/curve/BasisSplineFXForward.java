
package org.drip.state.curve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * BasisSplineFXForward manages the Basis Latent State, using the Basis as the State Response
 *  Representation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisSplineFXForward extends org.drip.state.fx.FXCurve {
	private org.drip.spline.grid.Span _span = null;
	private double _dblFXSpot = java.lang.Double.NaN;

	private double nodeBasis (
		final int iNodeDate,
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom)
		throws java.lang.Exception
	{
		return new org.drip.product.fx.FXForwardComponent ("FXFWD_" +
			org.drip.quant.common.StringUtil.GUID(), currencyPair(), epoch().julian(), iNodeDate, 1.,
				null).discountCurveBasis (valParam, dcNum, dcDenom, _dblFXSpot, fx (iNodeDate),
					bBasisOnDenom);
	}

	/**
	 * BasisSplineFXForward constructor
	 * 
	 * @param cp The Currency Pair
	 * @param span The Span over which the Basis Representation is valid
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BasisSplineFXForward (
		final org.drip.product.params.CurrencyPair cp,
		final org.drip.spline.grid.Span span)
		throws java.lang.Exception
	{
		super ((int) span.left(), cp);

		_span = span;

		_dblFXSpot = _span.calcResponseValue (_span.left());
	}

	@Override public double fx (
		final int iDate)
		throws java.lang.Exception
	{
		double dblSpanLeft = _span.left();

		if (iDate <= dblSpanLeft) return _span.calcResponseValue (dblSpanLeft);

		double dblSpanRight = _span.right();

		if (iDate >= dblSpanRight) return _span.calcResponseValue (dblSpanRight);

		return _span.calcResponseValue (iDate);
	}

	/**
	 * Retrieve the FX Spot
	 * 
	 * @return The FX Spot
	 */

	public double fxSpot()
	{
		return _dblFXSpot;
	}

	@Override public double[] zeroBasis (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom)
	{
		if (null == aiDateNode) return null;

		int iNumBasis = aiDateNode.length;
		double[] adblBasis = new double[iNumBasis];

		if (0 == iNumBasis) return null;

		for (int i = 0; i < iNumBasis; ++i) {
			try {
				adblBasis[i] = nodeBasis (aiDateNode[i], valParams, dcNum, dcDenom, bBasisOnDenom);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblBasis;
	}

	@Override public double[] bootstrapBasis (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom)
	{
		if (null == aiDateNode) return null;

		int iNumBasis = aiDateNode.length;
		double[] adblBasis = new double[iNumBasis];
		org.drip.state.discount.MergedDiscountForwardCurve dcBasis = bBasisOnDenom ? dcDenom : dcNum;

		if (0 == iNumBasis || null == dcBasis) return null;

		for (int i = 0; i < iNumBasis; ++i) {
			try {
				if (bBasisOnDenom)
					adblBasis[i] = nodeBasis (aiDateNode[i], valParams, dcNum, dcBasis, true);
				else
					adblBasis[i] = nodeBasis (aiDateNode[i], valParams, dcBasis, dcDenom, false);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblBasis;
	}

	@Override public double[] impliedNodeRates (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom)
	{
		if (null == aiDateNode) return null;

		int iNumBasis = aiDateNode.length;
		double[] adblImpliedNodeRate = new double[iNumBasis];

		if (0 == iNumBasis) return null;

		for (int i = 0; i < iNumBasis; ++i) {
			try {
				double dblBaseImpliedRate = java.lang.Double.NaN;

				if (bBasisOnDenom)
					dblBaseImpliedRate = dcNum.zero (aiDateNode[i]);
				else
					dblBaseImpliedRate = dcDenom.zero (aiDateNode[i]);

				adblImpliedNodeRate[i] = dblBaseImpliedRate + nodeBasis (i,	valParams, dcNum, dcDenom,
					bBasisOnDenom);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return adblImpliedNodeRate;
	}

	@Override public org.drip.state.discount.MergedDiscountForwardCurve bootstrapBasisDC (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom)
	{
		double[] adblImpliedRate = impliedNodeRates (aiDateNode, valParams, dcNum, dcDenom, bBasisOnDenom);

		if (null == adblImpliedRate) return null;

		int iNumDF = adblImpliedRate.length;
		double[] adblDF = new double[iNumDF];
		org.drip.state.discount.MergedDiscountForwardCurve dc = bBasisOnDenom ? dcDenom : dcNum;

		if (0 == iNumDF) return null;

		int iSpotDate = valParams.valueDate();

		java.lang.String strCurrency = dc.currency();

		for (int i = 0; i < iNumDF; ++i)
			adblDF[i] = java.lang.Math.exp (-1. * adblImpliedRate[i] * (aiDateNode[i] - iSpotDate) /
				365.25);

		try {
			return org.drip.state.creator.ScenarioDiscountCurveBuilder.CubicPolynomialDiscountCurve
				(strCurrency + "::BASIS", new org.drip.analytics.date.JulianDate (iSpotDate), strCurrency,
					aiDateNode, adblDF);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double rate (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final int iDate,
		final boolean bBasisOnDenom)
		throws java.lang.Exception
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcImplied = bootstrapBasisDC (aiDateNode, valParams, dcNum,
			dcDenom, bBasisOnDenom);

		if (null == dcImplied)
			throw new java.lang.Exception ("BasisSplineFXForward::rate: Cannot imply basis DC!");

		return dcImplied.zero (iDate);
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
	{
		return _span.jackDResponseDManifestMeasure (strManifestMeasure, iDate, 1);
	}
}
