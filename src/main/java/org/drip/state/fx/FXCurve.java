
package org.drip.state.fx;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.Curve;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.FXLabel;
import org.drip.state.identifier.LatentStateLabel;
import org.drip.state.representation.LatentState;

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
 * <i>FXCurve</i> is the Stub for the FX Curve for the specified Currency Pair. It implements the following
 *  Functionality.
 * 
 *  <ul>
 *		<li>Calculate the FX Forward to the given Date</li>
 *		<li>Calculate the set of Zero basis given the input discount curves</li>
 *		<li>Bootstrap the basis to the discount curve inputs</li>
 *		<li>Bootstrap the discount curve from the discount curve inputs</li>
 *		<li>Calculate the rates implied by the discount curve inputs</li>
 *		<li>Calculate the rate implied by the discount curve inputs to a specified date</li>
 *		<li>Return the Currency Pair</li>
 *		<li>Calculate the FX Forward to the given date</li>
 *		<li>Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date</li>
 *		<li>Retrieve the Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/fx/README.md">FX Latent State Curve Estimator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class FXCurve implements Curve
{
	private CurrencyPair _currencyPair = null;

	protected int _epochDate = Integer.MIN_VALUE;

	protected FXCurve (
		final int epochDate,
		final CurrencyPair currencyPair)
		throws java.lang.Exception
	{
		if (null == (_currencyPair = currencyPair)) {
			throw new Exception ("FXCurve ctr: Invalid Inputs");
		}

		_epochDate = epochDate;
	}

	/**
	 * Calculate the FX Forward to the given Date
	 * 
	 * @param date Date
	 * 
	 * @return The FX Forward
	 * 
	 * @throws Exception Thrown if the FX Forward cannot be calculated
	 */

	public abstract double fx (
		final int date)
		throws Exception;

	/**
	 * Calculate the set of Zero basis given the input discount curves
	 * 
	 * @param dateNodeArray Array of Date Nodes
	 * @param valuationParams Valuation Parameters
	 * @param numeratorDiscountCurve Discount Curve Numerator
	 * @param denominatorDiscountCurve Discount Curve Denominator
	 * @param basisOnDenominator True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed basis
	 */

	public abstract double[] zeroBasis (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator
	);

	/**
	 * Bootstrap the basis to the discount curve inputs
	 * 
	 * @param dateNodeArray Array of Date Nodes
	 * @param valuationParams Valuation Parameters
	 * @param numeratorDiscountCurve Discount Curve Numerator
	 * @param denominatorDiscountCurve Discount Curve Denominator
	 * @param basisOnDenominator True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed basis
	 */

	public abstract double[] bootstrapBasis (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator
	);

	/**
	 * Bootstrap the discount curve from the discount curve inputs
	 * 
	 * @param dateNodeArray Array of Date Nodes
	 * @param valuationParams Valuation Parameters
	 * @param numeratorDiscountCurve Discount Curve Numerator
	 * @param denominatorDiscountCurve Discount Curve Denominator
	 * @param basisOnDenominator True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed basis
	 */

	public abstract MergedDiscountForwardCurve bootstrapBasisDC (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator
	);

	/**
	 * Calculate the rates implied by the discount curve inputs
	 * 
	 * @param dateNodeArray Array of Date Nodes
	 * @param valuationParams Valuation Parameters
	 * @param numeratorDiscountCurve Discount Curve Numerator
	 * @param denominatorDiscountCurve Discount Curve Denominator
	 * @param basisOnDenominator True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed implied rates
	 */

	public abstract double[] impliedNodeRates (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final boolean basisOnDenominator
	);

	/**
	 * Calculate the rate implied by the discount curve inputs to a specified date
	 * 
	 * @param dateNodeArray Array of Date Nodes
	 * @param valuationParams ValuationParams
	 * @param numeratorDiscountCurve Discount Curve Numerator
	 * @param denominatorDiscountCurve Discount Curve Denominator
	 * @param date Date to which the implied rate is sought
	 * @param basisOnDenominator True if the implied rate is calculated on the denominator discount curve
	 * 
	 * @return Implied rate
	 * 
	 * @throws Exception Thrown if the implied rate cannot be calculated
	 */

	public abstract double rate (
		final int[] dateNodeArray,
		final ValuationParams valuationParams,
		final MergedDiscountForwardCurve numeratorDiscountCurve,
		final MergedDiscountForwardCurve denominatorDiscountCurve,
		final int date,
		final boolean basisOnDenominator)
		throws Exception;

	@Override public LatentStateLabel label()
	{
		return FXLabel.Standard (_currencyPair);
	}

	@Override public String currency()
	{
		return _currencyPair.quoteCcy();
	}

	@Override public JulianDate epoch()
	{
		return new JulianDate (_epochDate);
	}

	/**
	 * Return the Currency Pair
	 * 
	 * @return CurrencyPair
	 */

	public CurrencyPair currencyPair()
	{
		return _currencyPair;
	}

	/**
	 * Calculate the FX Forward to the given date
	 * 
	 * @param date Date
	 * 
	 * @return The FX Forward
	 * 
	 * @throws Exception Thrown if the FX Forward cannot be calculated
	 */

	public double fx (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("FXCurve::fx got null for date");
		}

		return fx (date.julian());
	}

	/**
	 * Calculate the FX Forward to the given date
	 * 
	 * @param tenor The Tenor
	 * 
	 * @return The FX Forward
	 * 
	 * @throws Exception Thrown if the FX Forward cannot be calculated
	 */

	public double fx (
		final String tenor)
		throws Exception
	{
		if (null == tenor || tenor.isEmpty()) {
			throw new Exception ("FXCurve::fx got bad tenor");
		}

		return fx (epoch().addTenor (tenor));
	}

	@Override public boolean setCCIS (
		final CurveConstructionInputSet curveConstructionInputSet)
	{
		return true;
	}

	@Override public CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public CaseInsensitiveTreeMap<Double> manifestMeasure (
		final String instrument)
	{
		return null;
	}

	@Override public LatentState parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public LatentState shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public LatentState customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public LatentState parallelShiftQuantificationMetric (
		final double shift)
	{
		return null;
	}

	@Override public LatentState customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param manifestMeasure Manifest Measure
	 * @param date Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public abstract WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final int date
	);

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param manifestMeasure Manifest Measure
	 * @param date Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final JulianDate date)
	{
		return null == date ? null : jackDForwardDManifestMeasure (manifestMeasure, date.julian());
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 * 
	 * @param manifestMeasure Manifest Measure
	 * @param tenor Tenor
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 */

	public WengertJacobian jackDForwardDManifestMeasure (
		final String manifestMeasure,
		final String tenor)
	{
		try {
			return null == tenor || tenor.isEmpty() ? null :
				jackDForwardDManifestMeasure (manifestMeasure, epoch().addTenor (tenor));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
