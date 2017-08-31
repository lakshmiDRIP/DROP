
package org.drip.sample.fx;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.fx.FXForwardComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.fx.FXCurve;
import org.drip.state.inference.*;

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
 * CustomFXCurveBuilder illustrates the Construction and Usage of the FX Forward Curve.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CustomFXCurveBuilder {

	private static FXForwardComponent[] FXForwardCalibComponent (
		final CurrencyPair cp,
		final JulianDate dtSpot,
		final String[] astrMaturityTenor)
		throws Exception
	{
		FXForwardComponent[] aFXForward = new FXForwardComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aFXForward[i] = new FXForwardComponent (
				cp.code() + "::FXFWD::" + astrMaturityTenor[i],
				cp,
				dtSpot.julian(),
				dtSpot.addTenor (astrMaturityTenor[i]).julian(),
				1.,
				null
			);

		return aFXForward;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today().addTenor ("0D");

		CurrencyPair cp = CurrencyPair.FromCode ("USD/EUR");

		double dblSpot = 1.0993;

		String[] astrMaturityTenor = new String[] {
			"1W",
			"1M",
			"3M",
			"6M",
			"1Y",
			"2Y",
			"3Y"
		};

		FXForwardComponent[] aFXForward = FXForwardCalibComponent (
			cp,
			dtToday,
			astrMaturityTenor
		);

		double[] adblFXForward = new double[] {
			1.1000, //	"1W",
			1.1012,	// 	"1M",
			1.1039,	// 	"3M",
			1.1148,	// 	"6M",
			1.1232,	// 	"1Y",
			1.1497,	// 	"2Y",
			1.1865,	// 	"3Y"
		};

		LatentStateStretchSpec fxForwardStretch = LatentStateStretchBuilder.FXStretchSpec (
			"FXFORWARD",
			aFXForward,
			"Outright",
			adblFXForward
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			fxForwardStretch
		};

		LinearLatentStateCalibrator llsc = new LinearLatentStateCalibrator (
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				new ResponseScalingShapeControl (
					true,
					new QuadraticRationalShapeControl (0.)
				),
				null
			),
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE,
			null,
			null
		);

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			cp.denomCcy()
		);

		FXCurve fxCurve = ScenarioFXCurveBuilder.ShapePreservingFXCurve (
			llsc,
			aStretchSpec,
			cp,
			valParams,
			null,
			null,
			null,
			dblSpot
		);

		MergedDiscountForwardCurve dcUSD = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			"USD",
			0.02
		);

		MergedDiscountForwardCurve dcEUR = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			"EUR",
			0.01
		);

		CurveSurfaceQuoteContainer csqs = new CurveSurfaceQuoteContainer();

		csqs.setFXState (fxCurve);

		System.out.println ("\n\t|-------------------------------------------------------------------||");

		System.out.println ("\t|                                                                   ||");

		System.out.println ("\t|    Custom FX Curve Builder Metrics #1                             ||");

		System.out.println ("\t|    ------ -- ----- ------- ------- --                             ||");

		System.out.println ("\t|    L -> R:                                                        ||");

		System.out.println ("\t|        FX Forward Tenor                                           ||");

		System.out.println ("\t|        Input FX Forward Outright                                  ||");

		System.out.println ("\t|        Curve FX Forward Outright                                  ||");

		System.out.println ("\t|        Product FX Forward Outright                                ||");

		System.out.println ("\t|        Product FX Forward PIP                                     ||");

		System.out.println ("\t|        FX Forward Discount Curve Basis EUR Curve                  ||");

		System.out.println ("\t|        FX Forward Discount Curve Basis USD Curve                  ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			Map<String, Double> mapMeasures = aFXForward[i].value (
				valParams,
				null,
				csqs,
				null
			);

			System.out.println (
				"\t| [" + astrMaturityTenor[i] + "] => " +
				FormatUtil.FormatDouble (adblFXForward[i], 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (fxCurve.fx (astrMaturityTenor[i]), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (mapMeasures.get ("Outright"), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (mapMeasures.get ("PIP"), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (
					aFXForward[i].discountCurveBasis (
						valParams,
						dcEUR,
						dcUSD,
						dblSpot,
						adblFXForward[i],
						false
					), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (
					aFXForward[i].discountCurveBasis (
						valParams,
						dcEUR,
						dcUSD,
						dblSpot,
						adblFXForward[i],
						true
					), 1, 4, 1.) + " || "
			);
		}

		System.out.println ("\t|-------------------------------------------------------------------||");

		int[] aiDateNode = new int[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aiDateNode[i] = dtToday.addTenor (astrMaturityTenor[i]).julian();

		double[] adblZeroUSDBasis = fxCurve.zeroBasis (
			aiDateNode,
			valParams,
			dcEUR,
			dcUSD,
			true
		);

		double[] adblZeroEURBasis = fxCurve.zeroBasis (
			aiDateNode,
			valParams,
			dcEUR,
			dcUSD,
			false
		);

		double[] adblBootstrappedUSDBasis = fxCurve.bootstrapBasis (
			aiDateNode,
			valParams,
			dcEUR,
			dcUSD,
			true
		);

		double[] adblBootstrappedEURBasis = fxCurve.bootstrapBasis (
			aiDateNode,
			valParams,
			dcEUR,
			dcUSD,
			false
		);

		System.out.println ("\n\t|-------------------------------------------------------------------||");

		System.out.println ("\t|                                                                   ||");

		System.out.println ("\t|    Custom FX Curve Builder Metrics #2                             ||");

		System.out.println ("\t|    ------ -- ----- ------- ------- --                             ||");

		System.out.println ("\t|    L -> R:                                                        ||");

		System.out.println ("\t|        FX Forward Tenor                                           ||");

		System.out.println ("\t|        FX Forward Discount Curve Zero Basis USD Curve             ||");

		System.out.println ("\t|        FX Forward Discount Curve Zero Basis EUR Curve             ||");

		System.out.println ("\t|        FX Forward Discount Curve Bootstrapped USD Curve Basis     ||");

		System.out.println ("\t|        FX Forward Discount Curve Bootstrapped EUR Curve Basis     ||");

		System.out.println ("\t|-------------------------------------------------------------------||");

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			System.out.println (
				"\t| [" + astrMaturityTenor[i] + "] => " +
				FormatUtil.FormatDouble (adblZeroUSDBasis[i], 1, 4, 100.) + " | " +
				FormatUtil.FormatDouble (adblZeroEURBasis[i], 1, 4, 100.) + " | " +
				FormatUtil.FormatDouble (adblBootstrappedUSDBasis[i], 1, 4, 100.) + " | " +
				FormatUtil.FormatDouble (adblBootstrappedEURBasis[i], 1, 4, 100.) + " ||"
			);

		System.out.println ("\t|-------------------------------------------------------------------||");
	}
}
