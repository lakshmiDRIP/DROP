
package org.drip.sample.multicurve;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1operator.Flat;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
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
 * <i>FixFloatSwap</i> contains a full valuation run on the Multi-Curve Fix-Float IRS Product.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/README.md">Multi-Curve Construction and Valuation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatSwap {

	private static final FixFloatComponent OTCFixFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

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
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final int[] aiDay,
		final int iNumFutures,
		final String strCurrency)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[aiDay.length + iNumFutures];

		for (int i = 0; i < aiDay.length; ++i)
			aCalibComp[i] = SingleStreamComponentBuilder.Deposit (
				dtEffective,
				dtEffective.addBusDays (
					aiDay[i],
					strCurrency
				),
				ForwardLabel.Create (
					strCurrency,
					"3M"
				)
			);

		CalibratableComponent[] aEDF = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtEffective,
			iNumFutures,
			strCurrency
		);

		for (int i = aiDay.length; i < aiDay.length + iNumFutures; ++i)
			aCalibComp[i] = aEDF[i - aiDay.length];

		return aCalibComp;
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aIRS[i] = OTCFixFloat (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

		return aIRS;
	}

	/*
	 * Construct the discount curve using the following steps:
	 * 	- Construct the array of cash instruments and their quotes.
	 * 	- Construct the array of swap instruments and their quotes.
	 * 	- Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MergedDiscountForwardCurve MakeDC (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		/*
		 * Construct the array of Deposit instruments and their quotes.
		 */

		CalibratableComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			new int[] {
				1, 2, 3, 7, 14, 21, 30, 60
			},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {
			0.01200, 0.01200, 0.01200, 0.01450, 0.01550, 0.01600, 0.01660, 0.01850
		};

		String[] astrDepositManifestMeasure = new String[] {
			"ForwardRate",
			"ForwardRate",
			"ForwardRate",
			"ForwardRate",
			"ForwardRate",
			"ForwardRate",
			"ForwardRate",
			"ForwardRate"
		};

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			0.02604,    //  4Y
			0.02808,    //  5Y
			0.02983,    //  6Y
			0.03136,    //  7Y
			0.03268,    //  8Y
			0.03383,    //  9Y
			0.03488,    // 10Y
			0.03583,    // 11Y
			0.03668,    // 12Y
			0.03833,    // 15Y
			0.03854,    // 20Y
			0.03672,    // 25Y
			0.03510,    // 30Y
			0.03266,    // 40Y
			0.03145     // 50Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			"SwapRate",    //  4Y
			"SwapRate",    //  5Y
			"SwapRate",    //  6Y
			"SwapRate",    //  7Y
			"SwapRate",    //  8Y
			"SwapRate",    //  9Y
			"SwapRate",    // 10Y
			"SwapRate",    // 11Y
			"SwapRate",    // 12Y
			"SwapRate",    // 15Y
			"SwapRate",    // 20Y
			"SwapRate",    // 25Y
			"SwapRate",    // 30Y
			"SwapRate",    // 40Y
			"SwapRate"     // 50Y
		};

		CalibratableComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
			},
			adblSwapQuote
		);

		/*
		 * Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
		 */

		return ScenarioDiscountCurveBuilder.CubicKLKHyperbolicDFRateShapePreserver (
			"KLK_HYPERBOLIC_SHAPE_TEMPLATE",
			new ValuationParams (
				dtSpot,
				dtSpot,
				"USD"
			),
			aDepositComp,
			adblDepositQuote,
			astrDepositManifestMeasure,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			false
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

	private static final FixFloatComponent CreateSTIR (
		final JulianDate dtEffective,
		final String strMaturityTenor,
		final ForwardLabel fri,
		final double dblCoupon,
		final String strCurrency)
		throws Exception
	{
		JulianDate dtMaturity = dtEffective.addTenor (strMaturityTenor);

		int iTenorInMonths = Helper.TenorToMonths (fri.tenor());

		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			true,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			fri.tenor(),
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			fri,
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			dblCoupon,
			0.,
			strCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			12 / iTenorInMonths,
			fri.tenor(),
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
			2,
			"6M",
			strCurrency,
			null,
			1.,
			null,
			null,
			null,
			null
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"6M",
			strMaturityTenor,
			null
		);

		List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			fri.tenor(),
			strMaturityTenor,
			null
		);

		Stream floatingStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsFloatingStreamEdgeDate,
				cpsFloating,
				cfusFloating
			)
		);

		Stream fixedStream = new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				lsFixedStreamEdgeDate,
				cpsFixed,
				ucasFixed,
				cfusFixed
			)
		);

		FixFloatComponent irs = new FixFloatComponent (
			fixedStream,
			floatingStream,
			csp
		);

		irs.setPrimaryCode ("IRS." + dtMaturity.toString() + "." + strCurrency);

		return irs;
	}

	private static final void RunWithVolCorrelation (
		final FixFloatComponent stir,
		final ValuationParams valParams,
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
				valParams.valueDate(),
				VolatilityLabel.Standard (fri),
				fri.currency(),
				dblForwardVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				valParams.valueDate(),
				VolatilityLabel.Standard (fundingLabel),
				fri.currency(),
				dblFundingVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			fri,
			fundingLabel,
			new Flat (dblForwardFundingCorr)
		);

		Map<String, Double> mapSTIROutput = stir.value (
			valParams,
			null,
			mktParams,
			null
		);

		for (Map.Entry<String, Double> me : mapSTIROutput.entrySet())
			System.out.println ("\t" + me.getKey() + " => " + me.getValue());
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		String strTenor = "3M";
		String strCurrency = "USD";
		double dblForwardVolatility = 0.3;
		double dblFundingVolatility = 0.1;
		double dblForwardFundingCorr = 0.2;

		JulianDate dtToday = DateUtil.Today().addTenor ("0D");

		/*
		 * Construct the Discount Curve using its instruments and quotes
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtToday,
			strCurrency
		);

		Map<String, ForwardCurve> mapFC = MakeFC (
			dtToday,
			strCurrency,
			dc
		);

		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		FixFloatComponent stir = CreateSTIR (
			dtToday.addTenor (strTenor),
			"5Y",
			fri,
			0.05,
			strCurrency
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			mapFC.get (strTenor),
			null,
			null,
			null,
			null,
			null,
			null
		);

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			strCurrency
		);

		RunWithVolCorrelation (
			stir,
			valParams,
			mktParams,
			fri,
			dblForwardVolatility,
			dblFundingVolatility,
			dblForwardFundingCorr
		);

		EnvManager.TerminateEnv();
	}
}
