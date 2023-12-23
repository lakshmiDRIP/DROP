
package org.drip.state.nonlinear;

import org.drip.analytics.date.JulianDate;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.fx.FXForwardComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.service.common.StringUtil;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.fx.ExplicitBootFXCurve;

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
 * <i>FlatForwardFXCurve</i> manages the Volatility Latent State, using the Forward FX as the State Response
 * Representation.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardFXCurve extends ExplicitBootFXCurve
{
	private int[] _dateArray = null;
	private double _fxSpot = Double.NaN;
	private double[] _fxForwardArray = null;

	private double nodeBasis (
		final int nodeDate,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator)
		throws Exception
	{
		return new FXForwardComponent (
			"FXFWD_" + StringUtil.GUID(),
			currencyPair(),
			epoch().julian(),
			nodeDate,
			1.,
			null
		).discountCurveBasis (
			valuationParams,
			numeratorDiscountCurve,
			denominatorDiscountCurve,
			_fxSpot,
			fx (nodeDate),
			basisOnDenominator
		);
	}

	/**
	 * FlatForwardVolatilityCurve Constructor
	 * 
	 * @param epochDate Epoch Date
	 * @param currencyPair Currency Pair
	 * @param fxSpot FX Spot
	 * @param dateArray Array of the Pillar Dates
	 * @param fxForwardArray Array of the corresponding FX Forward Nodes
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FlatForwardFXCurve (
		final int epochDate,
		final CurrencyPair currencyPair,
		final double fxSpot,
		final int[] dateArray,
		final double[] fxForwardArray)
		throws Exception
	{
		super (epochDate, currencyPair);

		if (!NumberUtil.IsValid (_fxSpot = fxSpot) || null == (_dateArray = dateArray) ||
			null == (_fxForwardArray = fxForwardArray) || _dateArray.length != _fxForwardArray.length) {
			throw new Exception ("FlatForwardFXCurve ctr => Invalid Inputs");
		}

		int dateArrayCount = _dateArray.length;

		for (int i = 0; i < dateArrayCount; ++i) {
			if (!NumberUtil.IsValid (_fxForwardArray[i])) {
				throw new Exception ("FlatForwardFXCurve ctr => Invalid Inputs");
			}
		}
	}

	@Override public double fx (
		final int date)
		throws Exception
	{
		if (date <= _iEpochDate) {
			return _fxForwardArray[0];
		}

		int fxForwardArrayCount = _fxForwardArray.length;

		for (int i = 1; i < fxForwardArrayCount; ++i) {
			if (_dateArray[i - 1] <= date && _dateArray[i] > date) {
				return _fxForwardArray[i];
			}
		}

		return _fxForwardArray[fxForwardArrayCount - 1];
	}

	/**
	 * Retrieve the FX Spot
	 * 
	 * @return The FX Spot
	 */

	public double fxSpot()
	{
		return _fxSpot;
	}

	@Override public double[] zeroBasis (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator)
	{
		if (null == dateNodeArray) {
			return null;
		}

		int dateNodeCount = dateNodeArray.length;
		double[] basisArray = new double[dateNodeCount];

		if (0 == dateNodeCount) return null;

		for (int i = 0; i < dateNodeCount; ++i) {
			try {
				basisArray[i] = nodeBasis (
					dateNodeArray[i],
					valuationParams,
					numeratorDiscountCurve,
					denominatorDiscountCurve,
					basisOnDenominator
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return basisArray;
	}

	@Override public double[] impliedNodeRates (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator)
	{
		if (null == dateNodeArray) {
			return null;
		}

		int dateNodeCount = dateNodeArray.length;
		double[] impliedNodeRateArray = new double[dateNodeCount];

		if (0 == dateNodeCount) {
			return null;
		}

		for (int dateNodeIndex = 0; dateNodeIndex < dateNodeCount; ++dateNodeIndex) {
			try {
				impliedNodeRateArray[dateNodeIndex] = (
					basisOnDenominator ? numeratorDiscountCurve.zero (dateNodeArray[dateNodeIndex]) :
						denominatorDiscountCurve.zero (dateNodeArray[dateNodeIndex])
				) + nodeBasis (
					dateNodeIndex,
					valuationParams,
					numeratorDiscountCurve,
					denominatorDiscountCurve,
					basisOnDenominator
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return impliedNodeRateArray;
	}

	@Override public double[] bootstrapBasis (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator)
	{
		if (null == dateNodeArray) {
			return null;
		}

		int dateNodeCount = dateNodeArray.length;
		double[] basisArray = new double[dateNodeCount];
		MergedDiscountForwardCurve basisDiscountCurve = basisOnDenominator ?
			denominatorDiscountCurve : numeratorDiscountCurve;

		if (0 == dateNodeCount || null == basisDiscountCurve) {
			return null;
		}

		for (int dateNodeIndex = 0; dateNodeIndex < dateNodeCount; ++dateNodeIndex) {
			try {
				basisArray[dateNodeIndex] = nodeBasis (
					dateNodeArray[dateNodeIndex],
					valuationParams,
					basisOnDenominator ? numeratorDiscountCurve : basisDiscountCurve,
					basisOnDenominator ? basisDiscountCurve : denominatorDiscountCurve,
					true
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return basisArray;
	}

	@Override public MergedDiscountForwardCurve bootstrapBasisDC (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator)
	{
		double[] impliedRateArray = impliedNodeRates (
			dateNodeArray,
			valuationParams,
			numeratorDiscountCurve,
			denominatorDiscountCurve,
			basisOnDenominator
		);

		if (null == impliedRateArray) {
			return null;
		}

		int discountFactorCount = impliedRateArray.length;
		double[] discountFactorArray = new double[discountFactorCount];
		MergedDiscountForwardCurve discountCurve = basisOnDenominator ?
			denominatorDiscountCurve : numeratorDiscountCurve;

		if (0 == discountFactorCount) {
			return null;
		}

		int spotDate = valuationParams.valueDate();

		String currency = discountCurve.currency();

		for (int discountFactorIndex = 0; discountFactorIndex < discountFactorCount; ++discountFactorIndex) {
			discountFactorArray[discountFactorIndex] = Math.exp (
				-1. * impliedRateArray[discountFactorIndex] * (dateNodeArray[discountFactorIndex] - spotDate)
					/ 365.25
			);
		}

		try {
			return ScenarioDiscountCurveBuilder.CubicPolynomialDiscountCurve (
				currency + "::BASIS",
				new JulianDate (spotDate),
				currency,
				dateNodeArray,
				discountFactorArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double rate (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final int date,
		final boolean basisOnDenominator)
		throws Exception
	{
		MergedDiscountForwardCurve impliedDiscountCurve = bootstrapBasisDC (
			dateNodeArray,
			valuationParams,
			numeratorDiscountCurve,
			denominatorDiscountCurve,
			basisOnDenominator
		);

		if (null == impliedDiscountCurve) {
			throw new Exception ("BasisSplineFXForward::rate: Cannot imply basis DC!");
		}

		return impliedDiscountCurve.zero (date);
	}

	@Override public WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final int date)
	{
		return null;
	}

	@Override public boolean setNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _fxForwardArray.length) {
			return false;
		}

		for (int fxForwardIndex = nodeIndex; fxForwardIndex < _fxForwardArray.length; ++fxForwardIndex) {
			_fxForwardArray[fxForwardIndex] = value;
		}

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int nodeIndex,
		final double value)
	{
		if (!NumberUtil.IsValid (value) || nodeIndex > _fxForwardArray.length) {
			return false;
		}

		for (int fxForwardIndex = nodeIndex; fxForwardIndex < _fxForwardArray.length; ++fxForwardIndex) {
			_fxForwardArray[fxForwardIndex] += value;
		}

		return true;
	}

	@Override public boolean setFlatValue (
		final double value)
	{
		if (!NumberUtil.IsValid (value)) {
			return false;
		}

		for (int fxForwardIndex = 0; fxForwardIndex < _fxForwardArray.length; ++fxForwardIndex) {
			_fxForwardArray[fxForwardIndex] = value;
		}

		return true;
	}
}
