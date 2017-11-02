
package org.drip.sample.secsuite1;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.fx.FXForwardComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.creator.ScenarioFXCurveBuilder;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.fx.FXCurve;
import org.drip.state.inference.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * FXSwap demonstrates the Analytics Calculation/Reconciliation for an FX Swap.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXSwap {

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

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.AUGUST,
			25
		);

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
			dtSpot,
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
			dtSpot,
			dtSpot,
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

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setFXState (fxCurve);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2028,
			DateUtil.NOVEMBER,
			27
		);

		FXForwardComponent fxfc = new FXForwardComponent (
			cp.code() + "::FXFWD::" + dtMaturity,
			cp,
			dtSpot.julian(),
			dtMaturity.julian(),
			1.,
			null
		);

		Map<String, Double> mapOutput = fxfc.value (
			valParams,
			null,
			csqc,
			null
		);

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t[" + me.getKey() + "] => " + me.getValue());
	}
}
