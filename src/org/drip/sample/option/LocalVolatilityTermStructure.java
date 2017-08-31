
package org.drip.sample.option;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.*;
import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.ScenarioMarketSurfaceBuilder;

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
 * LocalVolatilityTermStructure contains an illustration of the Calibration and Extraction of the Implied and
 * 	the Local Volatility Surfaces and their eventual Strike and Maturity Anchor Term Structures.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LocalVolatilityTermStructure {
	private static final SegmentCustomBuilderControl scbc()
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (4),
			SegmentInelasticDesignControl.Create (
				2,
				2
			),
			null,
			null
		);
	}

	private static final void EvaluateLocalVolSurface (
		final MarketSurface volSurface,
		final double[] adblStrikeATMFactor,
		final String[] astrMaturityTenor)
		throws Exception
	{
		System.out.println ("\n\t  " + volSurface.label());

		System.out.println ("\t|------------------------------------------------------------|");

		System.out.print ("\t|------------------------------------------------------------|\n\t|  ATM/TTE  =>");

		NodeStructure[] aTSMaturityAnchor = new NodeStructure[astrMaturityTenor.length];

		for (int j = 0; j < astrMaturityTenor.length; ++j) {
			aTSMaturityAnchor[j] = volSurface.maturityAnchorTermStructure (astrMaturityTenor[j]);

			System.out.print ("    " + astrMaturityTenor[j] + "  ");
		}

		System.out.println ("  |\n\t|------------------------------------------------------------|");

		for (int i = 0; i < adblStrikeATMFactor.length; ++i) {
			System.out.print ("\t|  " + FormatUtil.FormatDouble (adblStrikeATMFactor[i], 1, 2, 1.) + "    =>");

			NodeStructure tsStrikeAnchor = volSurface.xAnchorTermStructure (adblStrikeATMFactor[i]);

			for (int j = 0; j < astrMaturityTenor.length; ++j) {
				double dblLocalVol = Math.sqrt (2. * (tsStrikeAnchor.nodeDerivative (astrMaturityTenor[j], 1) +
					0.0 * adblStrikeATMFactor[i] * aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 1)) /
						(adblStrikeATMFactor[i] * adblStrikeATMFactor[i] *
							aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 2)));

				System.out.print ("  " + FormatUtil.FormatDouble (dblLocalVol, 2, 2, 100.) + "%");
			}

			System.out.print ("  |\n");
		}

		System.out.println ("\t|------------------------------------------------------------|");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		JulianDate dtStart = DateUtil.Today();

		double[] adblStrikeATMFactorCalib = new double[] {
			0.8, 0.9, 1.0, 1.1, 1.2
		};
		String[] astrMaturityTenorCalib = new String[] {
			"12M", "24M", "36M", "48M", "60M"
		};
		double[][] aadblVol = new double[][] {
			{0.171, 0.169, 0.168, 0.168, 0.168},
			{0.159, 0.161, 0.161, 0.162, 0.164},
			{0.138, 0.145, 0.149, 0.152, 0.154},
			{0.115, 0.130, 0.137, 0.143, 0.148},
			{0.103, 0.119, 0.128, 0.135, 0.140}
		};

		MarketSurface priceSurfCubicPoly = ScenarioMarketSurfaceBuilder.CustomWireSurface (
			"HESTON1993_CUBICPOLY_CALLPRICE_SURFACE",
			dtStart,
			"USD",
			adblStrikeATMFactorCalib,
			astrMaturityTenorCalib,
			aadblVol,
			scbc(),
			scbc()
		);

		double[] adblStrikeATMFactor = new double[] {
			0.850, 0.925, 1.000, 1.075, 1.150
		};
		String[] astrMaturityTenor = new String[] {
			"18M", "27M", "36M", "45M", "54M"
		};

		NodeStructure[] aTSMaturityAnchor = new NodeStructure[astrMaturityTenor.length];

		for (int j = 0; j < astrMaturityTenor.length; ++j)
			aTSMaturityAnchor[j] = priceSurfCubicPoly.maturityAnchorTermStructure (astrMaturityTenor[j]);

		for (int i = 0; i < adblStrikeATMFactor.length; ++i) {
			NodeStructure tsStrikeAnchor = priceSurfCubicPoly.xAnchorTermStructure (adblStrikeATMFactor[i]);

			for (int j = 0; j < astrMaturityTenor.length; ++j) {
				System.out.println (Math.sqrt (2. * (tsStrikeAnchor.nodeDerivative (astrMaturityTenor[j], 1) +
					0.0 * adblStrikeATMFactor[i] * aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 1)) /
						(adblStrikeATMFactor[i] * adblStrikeATMFactor[i] *
							aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 2))) + " | " +
								aTSMaturityAnchor[j].nodeDerivative ((int) adblStrikeATMFactor[i], 2));
			}
		}

		EvaluateLocalVolSurface (
			priceSurfCubicPoly,
			adblStrikeATMFactor,
			astrMaturityTenor
		);
	}
}
