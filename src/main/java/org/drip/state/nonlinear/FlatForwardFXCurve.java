
package org.drip.state.nonlinear;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>FlatForwardFXCurve</i> manages the Volatility Latent State, using the Forward FX as the State Response
 * Representation.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardFXCurve extends org.drip.state.fx.ExplicitBootFXCurve {
	private int[] _aiPillarDate = null;
	private double[] _adblFXForward = null;
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
			org.drip.numerical.common.StringUtil.GUID(), currencyPair(), epoch().julian(), iNodeDate, 1.,
				null).discountCurveBasis (valParam, dcNum, dcDenom, _dblFXSpot, fx (iNodeDate),
					bBasisOnDenom);
	}

	/**
	 * FlatForwardVolatilityCurve Constructor
	 * 
	 * @param iEpochDate Epoch Date
	 * @param cp Currency Pair
	 * @param dblFXSpot FX Spot
	 * @param aiPillarDate Array of the Pillar Dates
	 * @param adblFXForward Array of the corresponding FX Forward Nodes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FlatForwardFXCurve (
		final int iEpochDate,
		final org.drip.product.params.CurrencyPair cp,
		final double dblFXSpot,
		final int[] aiPillarDate,
		final double[] adblFXForward)
		throws java.lang.Exception
	{
		super (iEpochDate, cp);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblFXSpot = dblFXSpot) || null == (_aiPillarDate =
			aiPillarDate) || null == (_adblFXForward = adblFXForward) || _aiPillarDate.length !=
				_adblFXForward.length)
			throw new java.lang.Exception ("FlatForwardFXCurve ctr => Invalid Inputs");

		int iNumPillar = _aiPillarDate.length;

		for (int i = 0; i < iNumPillar; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_adblFXForward[i]))
				throw new java.lang.Exception ("FlatForwardFXCurve ctr => Invalid Inputs");
		}
	}

	@Override public double fx (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _iEpochDate) return _adblFXForward[0];

		int iNumPillar = _adblFXForward.length;

		for (int i = 1; i < iNumPillar; ++i) {
			if (_aiPillarDate[i - 1] <= iDate && _aiPillarDate[i] > iDate) return _adblFXForward[i];
		}

		return _adblFXForward[iNumPillar - 1];
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

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
	{
		return null;
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblFXForward.length)
			return false;

		for (int i = iNodeIndex; i < _adblFXForward.length; ++i)
			_adblFXForward[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblFXForward.length)
			return false;

		for (int i = iNodeIndex; i < _adblFXForward.length; ++i)
			_adblFXForward[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblFXForward.length; ++i)
			_adblFXForward[i] = dblValue;

		return true;
	}
}
