
package org.drip.sample.fra;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.fra.FRAMarketComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.sample.forward.*;
import org.drip.service.env.EnvManager;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;
import org.drip.state.nonlinear.FlatForwardVolatilityCurve;
import org.drip.state.volatility.VolatilityCurve;

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
 * MultiCurveFRAMarketAnalysis contains an analysis of the correlation and volatility impact on the Market
 *  FRA.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MultiCurveFRAMarketAnalysis {
	static class FRAMktConvexityCorrection {
		double _dblParMktFwd = Double.NaN;
		double _dblParStdFwd = Double.NaN;
		double _dblConvexityCorrection = Double.NaN;

		FRAMktConvexityCorrection (
			final double dblParMktFwd,
			final double dblParStdFwd,
			final double dblConvexityCorrection)
		{
			_dblParMktFwd = dblParMktFwd;
			_dblParStdFwd = dblParStdFwd;
			_dblConvexityCorrection = dblConvexityCorrection;
		}
	}

	private static final VolatilityCurve ATMVolatilityCurve (
		final JulianDate dtEpoch,
		final VolatilityLabel label,
		final String strCurrency,
		final String[] astrTenor,
		final double[] adblVolatility)
		throws Exception
	{
		int[] iPillarDate = new int[astrTenor.length];

		for (int i = 0; i < iPillarDate.length; ++i)
			iPillarDate[i] = dtEpoch.addTenor (astrTenor[i]).julian();

		return new FlatForwardVolatilityCurve (
			dtEpoch.julian(),
			label,
			strCurrency,
			iPillarDate,
			adblVolatility
		);
	}

	public static final FRAMktConvexityCorrection FRAMktMetric (
		final JulianDate dtValue,
		final MergedDiscountForwardCurve dcEONIA,
		final ForwardCurve fcEURIBOR6M,
		final String strForwardStartTenor,
		final VolatilityCurve vcEONIA,
		final VolatilityCurve vcEURIBOR6M,
		final double dblEONIAEURIBOR6MCorrelation)
		throws Exception
	{
		String strTenor = "6M";
		String strCurrency = "USD";

		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		FundingLabel fundingLabel = FundingLabel.Standard (strCurrency);

		JulianDate dtForwardStart = dtValue.addTenor (strForwardStartTenor);

		FRAMarketComponent fra = SingleStreamComponentBuilder.FRAMarket (
			dtForwardStart,
			fri,
			0.006
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dcEONIA,
			fcEURIBOR6M,
			null,
			null,
			null,
			null,
			null,
			null
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtValue,
			strCurrency
		);

		mktParams.setForwardVolatility (vcEURIBOR6M);

		mktParams.setFundingVolatility (vcEONIA);

		mktParams.setForwardFundingCorrelation (
			fri,
			fundingLabel,
			new FlatUnivariate (dblEONIAEURIBOR6MCorrelation)
		);

		Map<String, Double> mapFRAOutput = fra.value (
			valParams,
			null,
			mktParams,
			null
		);

		return new FRAMktConvexityCorrection (
			mapFRAOutput.get ("shiftedlognormalparmarketfra"),
			mapFRAOutput.get ("parstandardfra"),
			mapFRAOutput.get ("shiftedlognormalconvexitycorrection")
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

		String strTenor = "6M";
		String strCurrency = "USD";

		JulianDate dtToday = DateUtil.Today().addTenor ("0D");

		MergedDiscountForwardCurve dcEONIA = OvernightIndexCurve.MakeDC (
			dtToday,
			strCurrency
		);

		ForwardCurve fcEURIBOR6M = IBOR6MQuarticPolyVanilla.Make6MForward (
			dtToday,
			strCurrency,
			strTenor
		);

		String[] astrForwardStartTenor = {
			"6M",
			"1Y",
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y"
		};

		double[] adblVolatility = new double[] {
			0.5946, // 6M
			0.5311,	// 1Y
			0.3307,	// 2Y
			0.2929,	// 3Y
			0.2433,	// 4Y
			0.2013,	// 5Y
			0.1855,	// 6Y
			0.1789,	// 7Y
			0.1655,	// 8Y
			0.1574	// 9Y
		};

		double dblEONIAEURIBOR6MCorrelation = 0.8;

		VolatilityCurve vcEONIA = ATMVolatilityCurve (
			dtToday,
			VolatilityLabel.Standard (FundingLabel.Standard (strCurrency)),
			strCurrency,
			astrForwardStartTenor,
			adblVolatility
		);

		VolatilityCurve vEURIBOR6M = ATMVolatilityCurve (
			dtToday,
			VolatilityLabel.Standard (
				ForwardLabel.Create (
					strCurrency,
					strTenor
				)
			),
			strCurrency,
			astrForwardStartTenor,
			adblVolatility
		);

		System.out.println ("\t---------------------------------");

		System.out.println ("\t---------------------------------");

		System.out.println ("\t---------------------------------");

		System.out.println ("\t---------------------------------");

		System.out.println ("\tTNR =>   MKT   |   STD   |  CONV ");

		System.out.println ("\t---------------------------------");

		for (String strForwardStartTenor : astrForwardStartTenor) {
			FRAMktConvexityCorrection fraMktMetric = FRAMktMetric (
				dtToday,
				dcEONIA,
				fcEURIBOR6M,
				strForwardStartTenor,
				vcEONIA,
				vEURIBOR6M,
				dblEONIAEURIBOR6MCorrelation
			);

			System.out.println (
				"\t " + strForwardStartTenor + " => " +
				FormatUtil.FormatDouble (fraMktMetric._dblParMktFwd, 1, 3, 100.) + "% | " +
				FormatUtil.FormatDouble (fraMktMetric._dblParStdFwd, 1, 3, 100.) + "% | " +
				FormatUtil.FormatDouble (fraMktMetric._dblConvexityCorrection, 1, 2, 10000.)
			);
		}
	}
}
