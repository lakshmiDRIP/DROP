
package org.drip.state.curve;

import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardRateEstimator;
import org.drip.state.identifier.ForwardLabel;

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
 * <i>DeterministicCollateralChoiceDiscountCurve</i> implements the Dynamically Switchable Collateral Choice
 * 	Discount Curve among the choice of provided "deterministic" collateral curves.
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

public class DeterministicCollateralChoiceDiscountCurve extends MergedDiscountForwardCurve
{
	private int _discreteCollateralizationIncrement = -1;
	private MergedDiscountForwardCurve _domesticCollateralizedDiscountCurve = null;
	private ForeignCollateralizedDiscountCurve[] _foreignCollateralizedDiscountCurveArray = null;

	/**
	 * <i>DeterministicCollateralChoiceDiscountCurve</i> constructor
	 * 
	 * @param domesticCollateralizedDiscountCurve The Domestic Collateralized Curve
	 * @param foreignCollateralizedDiscountCurveArray Array of The Foreign Collateralized Curves
	 * @param discreteCollateralizationIncrement The Discrete Collateralization Increment
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public DeterministicCollateralChoiceDiscountCurve (
		final MergedDiscountForwardCurve domesticCollateralizedDiscountCurve,
		final ForeignCollateralizedDiscountCurve[] foreignCollateralizedDiscountCurveArray,
		final int discreteCollateralizationIncrement)
		throws Exception
	{
		super (
			domesticCollateralizedDiscountCurve.epoch().julian(),
			domesticCollateralizedDiscountCurve.currency(),
			null
		);

		if (0 >= (_discreteCollateralizationIncrement = discreteCollateralizationIncrement)) {
			throw new Exception (
				"DeterministicCollateralChoiceDiscountCurve ctr: Invalid Collateralization Increment!"
			);
		}

		_domesticCollateralizedDiscountCurve = domesticCollateralizedDiscountCurve;
		_foreignCollateralizedDiscountCurveArray = foreignCollateralizedDiscountCurveArray;
	}

	@Override public double df (
		final int date)
		throws Exception
	{
		if (null == _foreignCollateralizedDiscountCurveArray) {
			return _domesticCollateralizedDiscountCurve.df (date);
		}

		int collateralizerCount = _foreignCollateralizedDiscountCurveArray.length;

		if (0 == collateralizerCount) {
			return _domesticCollateralizedDiscountCurve.df (date);
		}

		if (date <= _epochDate) {
			return 1.;
		}

		double discountFactor = 1.;
		int workoutDate = _epochDate;

		while (Math.abs (date - workoutDate) > _discreteCollateralizationIncrement) {
			int workoutEndDate = workoutDate + _discreteCollateralizationIncrement;

			double discountFactorIncrement = _domesticCollateralizedDiscountCurve.df (workoutEndDate) /
				_domesticCollateralizedDiscountCurve.df (workoutDate);

			for (int collateralizerIndex = 0; collateralizerIndex < collateralizerCount;
				++collateralizerIndex) {
				double dblCollateralizerDFIncrement =
					_foreignCollateralizedDiscountCurveArray[collateralizerIndex].df (workoutEndDate) /
					_foreignCollateralizedDiscountCurveArray[collateralizerIndex].df (workoutDate);

				if (dblCollateralizerDFIncrement < discountFactorIncrement) {
					discountFactorIncrement = dblCollateralizerDFIncrement;
				}
			}

			discountFactor *= discountFactorIncrement;
			workoutDate = workoutEndDate;
		}

		if (date > workoutDate) {
			double discountFactorIncrement = _domesticCollateralizedDiscountCurve.df (date) /
				_domesticCollateralizedDiscountCurve.df (workoutDate);

			for (int collateralizerIndex = 0; collateralizerIndex < collateralizerCount;
				++collateralizerIndex) {
				double dblCollateralizerDFIncrement =
					_foreignCollateralizedDiscountCurveArray[collateralizerIndex].df (date) /
					_foreignCollateralizedDiscountCurveArray[collateralizerIndex].df (workoutDate);

				if (dblCollateralizerDFIncrement < discountFactorIncrement) {
					discountFactorIncrement = dblCollateralizerDFIncrement;
				}
			}

			discountFactor *= discountFactorIncrement;
		}

		return discountFactor;
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
		if (date < _epochDate) return 0.;

		return -365.25 / (date - _epochDate) * Math.log (df (date));
	}

	@Override public ForwardRateEstimator forwardRateEstimator (
		final int date,
		final ForwardLabel forwardLabel)
	{
		return null;
	}

	@Override public String latentStateQuantificationMetric()
	{
		return null;
	}

	@Override public DiscountFactorDiscountCurve parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public DiscountFactorDiscountCurve shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public org.drip.state.discount.MergedDiscountForwardCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public DiscountFactorDiscountCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		return null;
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return false;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		return null;
	}
}
