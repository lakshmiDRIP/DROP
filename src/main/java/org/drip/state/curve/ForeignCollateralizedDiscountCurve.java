
package org.drip.state.curve;

import java.util.Map;

import org.drip.analytics.definition.Curve;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.support.OptionHelper;
import org.drip.function.definition.R1ToR1;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.state.discount.ExplicitBootDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardRateEstimator;
import org.drip.state.fx.FXCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.nonlinear.FlatForwardDiscountCurve;
import org.drip.state.volatility.VolatilityCurve;

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
 * <i>ForeignCollateralizedDiscountCurve</i> computes the discount factor corresponding to one unit of
 * 	domestic currency collateralized by a foreign collateral.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/README.md">Basis Spline Based Latent States</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ForeignCollateralizedDiscountCurve extends ExplicitBootDiscountCurve
{
	private String _currency = null;
	private FXCurve _fxCurve = null;
	private VolatilityCurve _fxVolatilityCurve = null;
	private R1ToR1 _collateralForeignFXCorrelationFunction = null;
	private VolatilityCurve _foreignCollateralizedVolatilityCurve = null;
	private MergedDiscountForwardCurve _foreignCollateralizedDiscountCurve = null;

	/**
	 * <i>ForeignCollateralizedDiscountCurve</i> constructor
	 * 
	 * @param currency The Currency
	 * @param foreignCollateralizedDiscountCurve The Collateralized Foreign Discount Curve
	 * @param fxCurve The FX Forward Curve
	 * @param foreignCollateralizedVolatilityCurve The Foreign Collateral Volatility Curve
	 * @param fxVolatilityCurve The FX Volatility Curve
	 * @param collateralForeignFXCorrelationFunction The FX Foreign Collateral Correlation Curve
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public ForeignCollateralizedDiscountCurve (
		final String currency,
		final MergedDiscountForwardCurve foreignCollateralizedDiscountCurve,
		final FXCurve fxCurve,
		final VolatilityCurve foreignCollateralizedVolatilityCurve,
		final VolatilityCurve fxVolatilityCurve,
		final R1ToR1 collateralForeignFXCorrelationFunction)
		throws Exception
	{
		super (foreignCollateralizedDiscountCurve.epoch().julian(), currency);

		if (null == (_currency = currency) || _currency.isEmpty() ||
			null == (_foreignCollateralizedVolatilityCurve = foreignCollateralizedVolatilityCurve) ||
			null == (_fxVolatilityCurve = fxVolatilityCurve) ||
			null == (_collateralForeignFXCorrelationFunction = collateralForeignFXCorrelationFunction) ||
			null == (_foreignCollateralizedDiscountCurve = foreignCollateralizedDiscountCurve) ||
			null == (_fxCurve = fxCurve)) {
			throw new Exception ("ForeignCollateralizedDiscountCurve ctr: Invalid Inputs");
		}
	}

	@Override public double df (
		final int date)
		throws Exception
	{
		return date <= _epochDate ? 1. :
			_foreignCollateralizedDiscountCurve.df (date) * _fxCurve.fx (date) * Math.exp (
				-1. * OptionHelper.IntegratedCrossVolQuanto (
					_fxVolatilityCurve,
					_foreignCollateralizedVolatilityCurve,
					_collateralForeignFXCorrelationFunction,
					_epochDate,
					date
				)
			);
	}

	@Override public double forward (
		final int date1,
		final int date2)
		throws Exception
	{
		return date1 < _epochDate || date2 < _epochDate ? 0. :
			365.25 / (date2 - date1) * Math.log (df (date1) / df (date2));
	}

	@Override public double zero (
		final int date)
		throws Exception
	{
		return date < _epochDate ? 0. : -365.25 / (date - _epochDate) * Math.log (df (date));
	}

	@Override public ForwardRateEstimator forwardRateEstimator (
		final int date,
		final ForwardLabel forwardLabel)
	{
		return null;
	}

	@Override public Map<Integer, Double> canonicalTruthness (
		final String latentQuantificationMetric)
	{
		return null;
	}

	@Override public FlatForwardDiscountCurve parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public FlatForwardDiscountCurve shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public ExplicitBootDiscountCurve customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public FlatForwardDiscountCurve parallelShiftQuantificationMetric (
		final double shift)
	{
		return null;
	}

	@Override public Curve customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public FlatForwardDiscountCurve createBasisRateShiftedCurve (
		final int[] dateArray,
		final double[] basisArray)
	{
		return null;
	}

	@Override public String latentStateQuantificationMetric()
	{
		return LatentStateStatic.DISCOUNT_QM_ZERO_RATE;
	}

	@Override public WengertJacobian jackDDFDManifestMeasure (
		final int date,
		final String manifestMeasure)
	{
		return null;
	}

	@Override public boolean setNodeValue (
		final int nodeIndex,
		final double value)
	{
		return true;
	}

	@Override public boolean bumpNodeValue (
		final int nodeIndex,
		final double value)
	{
		return true;
	}

	@Override public boolean setFlatValue (
		final double value)
	{
		return true;
	}
}
