
package org.drip.sample.forwardratefutures;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.creator.SingleStreamOptionBuilder;
import org.drip.product.fra.FRAStandardCapFloorlet;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.sample.forward.OvernightIndexCurve;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;

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
 * JurisdictionVenueOptionValuation contains the Demonstration of the Construction and the Valuation of the
 *  Options on Standardized LIBOR Futures Contract across Jurisdictions and Venues.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JurisdictionVenueOptionValuation {

	private static final FloatFloatComponent OTCFloatFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strDerivedTenor,
		final String strMaturityTenor,
		final double dblBasis)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		return ffConv.createFloatFloatComponent (
			dtSpot,
			strDerivedTenor,
			strMaturityTenor,
			dblBasis,
			1.
		);
	}

	/*
	 * Construct an array of float-float swaps from the corresponding reference (6M) and the derived legs.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FloatFloatComponent[] MakexM6MBasisSwap (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final int iTenorInMonths)
		throws Exception
	{
		FloatFloatComponent[] aFFC = new FloatFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aFFC[i] = OTCFloatFloat (
				dtSpot,
				strCurrency,
				iTenorInMonths + "M",
				astrMaturityTenor[i],
				0.
			);

		return aFFC;
	}

	private static final ForwardCurve MakeFC (
		final JulianDate dtSpot,
		final String strCurrency,
		final MergedDiscountForwardCurve dc,
		final int iTenorInMonths,
		final String[] astrxM6MFwdTenor,
		final double[] adblxM6MBasisSwapQuote)
		throws Exception
	{
		/*
		 * Construct the 6M-xM float-float basis swap.
		 */

		FloatFloatComponent[] aFFC = MakexM6MBasisSwap (
			dtSpot,
			strCurrency,
			astrxM6MFwdTenor,
			iTenorInMonths
		);

		String strBasisTenor = iTenorInMonths + "M";

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		/*
		 * Calculate the starting forward rate off of the discount curve.
		 */

		double dblStartingFwd = dc.forward (
			dtSpot.julian(),
			dtSpot.addTenor (strBasisTenor).julian()
		);

		/*
		 * Set the discount curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		/*
		 * Construct the shape preserving forward curve off of Quartic Polynomial Basis Spline.
		 */

		return ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			"QUARTIC_FWD" + strBasisTenor,
			ForwardLabel.Create (
				strCurrency,
				strBasisTenor
			),
			valParams,
			null,
			mktParams,
			null,
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (5),
			aFFC,
			"DerivedParBasisSpread",
			adblxM6MBasisSwapQuote,
			dblStartingFwd
		);
	}

	private static final Map<String, ForwardCurve> MakeFC (
		final JulianDate dt,
		final String strCurrency,
		final MergedDiscountForwardCurve dc)
		throws Exception
	{
		Map<String, ForwardCurve> mapFC = new HashMap<String, ForwardCurve>();

		/*
		 * Build and run the sampling for the 1M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		ForwardCurve fc1M = MakeFC (
			dt,
			strCurrency,
			dc,
			1,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
			new double[] {
				0.00551,    //  1Y
				0.00387,    //  2Y
				0.00298,    //  3Y
				0.00247,    //  4Y
				0.00211,    //  5Y
				0.00185,    //  6Y
				0.00165,    //  7Y
				0.00150,    //  8Y
				0.00137,    //  9Y
				0.00127,    // 10Y
				0.00119,    // 11Y
				0.00112,    // 12Y
				0.00096,    // 15Y
				0.00079,    // 20Y
				0.00069,    // 25Y
				0.00062     // 30Y
				}
			);

		mapFC.put (
			"1M",
			fc1M
		);

		/*
		 * Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		ForwardCurve fc3M = MakeFC (
			dt,
			strCurrency,
			dc,
			3,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
			new double[] {
				0.00186,    //  1Y
				0.00127,    //  2Y
				0.00097,    //  3Y
				0.00080,    //  4Y
				0.00067,    //  5Y
				0.00058,    //  6Y
				0.00051,    //  7Y
				0.00046,    //  8Y
				0.00042,    //  9Y
				0.00038,    // 10Y
				0.00035,    // 11Y
				0.00033,    // 12Y
				0.00028,    // 15Y
				0.00022,    // 20Y
				0.00020,    // 25Y
				0.00018     // 30Y
				}
			);

		mapFC.put (
			"3M",
			fc3M
		);

		/*
		 * Build and run the sampling for the 12M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		ForwardCurve fc12M = MakeFC (
			dt,
			strCurrency,
			dc,
			12,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y",
				"35Y", "40Y" // Extrapolated
			},
			new double[] {
				-0.00212,    //  1Y
				-0.00152,    //  2Y
				-0.00117,    //  3Y
				-0.00097,    //  4Y
				-0.00082,    //  5Y
				-0.00072,    //  6Y
				-0.00063,    //  7Y
				-0.00057,    //  8Y
				-0.00051,    //  9Y
				-0.00047,    // 10Y
				-0.00044,    // 11Y
				-0.00041,    // 12Y
				-0.00035,    // 15Y
				-0.00028,    // 20Y
				-0.00025,    // 25Y
				-0.00022,    // 30Y
				-0.00022,    // 35Y Extrapolated
				-0.00022,    // 40Y Extrapolated
				}
			);

		mapFC.put (
			"12M",
			fc12M
		);

		return mapFC;
	}

	private static final void SetVolCorrelation (
		final int iValueDate,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel fri,
		final double dblForwardVol,
		final double dblFundingVol,
		final double dblForwardFundingCorr)
		throws Exception
	{
		FundingLabel fundingLabel = FundingLabel.Standard (fri.currency());

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fri),
				fri.currency(),
				dblForwardVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fundingLabel),
				fri.currency(),
				dblFundingVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			fri,
			fundingLabel,
			new FlatUnivariate (dblForwardFundingCorr)
		);
	}

	private static final void FuturesOptionMetrics (
		final String strCurrency,
		final String strTenor,
		final JulianDate dtSpot,
		final String strOptionType,
		final String strExchange)
		throws Exception
	{
		MergedDiscountForwardCurve dcOIS = OvernightIndexCurve.MakeDC (
			dtSpot,
			strCurrency
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		Map<String, ForwardCurve> mapFC = MakeFC (
			dtSpot,
			strCurrency,
			dcOIS
		);

		ForwardCurve fc = mapFC.get (strTenor);

		JulianDate dtEffective = dtSpot.addTenor ("3M");

		FRAStandardCapFloorlet liborFuturesOption = SingleStreamOptionBuilder.ExchangeTradedFuturesOption (
			dtEffective,
			forwardLabel,
			fc.forward (dtEffective.addTenor (fc.tenor())),
			"ParForward",
			false,
			strOptionType,
			strExchange
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dcOIS,
			fc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		double dblForwardVol = 0.50;
		double dblFundingVol = 0.50;
		double dblForwardFundingCorr = 0.50;

		SetVolCorrelation (
			dtSpot.julian(),
			mktParams,
			forwardLabel,
			dblForwardVol,
			dblFundingVol,
			dblForwardFundingCorr
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		Map<String, Double> mapOutput = liborFuturesOption.value (
			valParams,
			null,
			mktParams,
			null
		);

		System.out.println ("\t\t" + strExchange + " | " +
			FormatUtil.FormatDouble (mapOutput.get ("ATMFRA"), 1, 4, 100.) + " % | " +
			FormatUtil.FormatDouble (mapOutput.get ("Upfront"), 1, 1, 10000.) + " bp | " +
			forwardLabel.fullyQualifiedName()
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today();

		System.out.println ("\tOutput Order - L -> R:");

		System.out.println ("\t\tExchange\n\t\tATM Par FRA Level (%)\n\t\tOption Upfront (bp)\n\t\tFRA Label");

		System.out.println ("\n\t-----------------------------------------------------");

		System.out.println ("\t--------------- MARGIN TYPE OPTION ------------------");

		System.out.println ("\t-----------------------------------------------------");

		FuturesOptionMetrics (
			"CHF",
			"3M",
			dtToday,
			"MARGIN",
			"LIFFE"
		);

		FuturesOptionMetrics (
			"GBP",
			"3M",
			dtToday,
			"MARGIN",
			"LIFFE"
		);

		/* FuturesOptionMetrics (
			"EUR",
			"3M",
			dtToday,
			"MARGIN",
			"LIFFE"
		); */

		FuturesOptionMetrics (
			"USD",
			"3M",
			dtToday,
			"MARGIN",
			"LIFFE"
		);

		System.out.println ("\t-----------------------------------------------------");

		System.out.println ("\t-------------- PREMIUM TYPE OPTION ------------------");

		System.out.println ("\t-----------------------------------------------------");

		FuturesOptionMetrics (
			"JPY",
			"3M",
			dtToday,
			"PREMIUM",
			"SGX"
		);

		FuturesOptionMetrics (
			"USD",
			"1M",
			dtToday,
			"PREMIUM",
			"CME"
		);

		FuturesOptionMetrics (
			"USD",
			"3M",
			dtToday,
			"PREMIUM",
			"CME"
		);

		FuturesOptionMetrics (
			"USD",
			"3M",
			dtToday,
			"PREMIUM",
			"SGX"
		);

		System.out.println ("\t-----------------------------------------------------");

		System.out.println ("\t-----------------------------------------------------");
	}
}
