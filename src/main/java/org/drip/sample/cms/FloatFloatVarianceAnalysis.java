
package org.drip.sample.cms;

import java.util.List;

import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.market.otc.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>FloatFloatVarianceAnalysis</i> demonstrates the Construction and Valuation Impact of Volatility and
 * Correlation on the CMS Float-Float Swap.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cms/README.md">Dual Stream Constant Maturity Swap</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FloatFloatVarianceAnalysis {

	private static final FixFloatComponent OTCIRS (
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

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			FixFloatComponent irs = OTCIRS (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

			irs.setPrimaryCode ("IRS." + astrMaturityTenor[i] + "." + strCurrency);

			aIRS[i] = irs;
		}

		return aIRS;
	}

	private static final MergedDiscountForwardCurve MakeDC (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{

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
			"ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate"
		};

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

		return ScenarioDiscountCurveBuilder.CubicKLKHyperbolicDFRateShapePreserver (
			"KLK_HYPERBOLIC_SHAPE_TEMPLATE",
			new ValuationParams (
				dtSpot,
				dtSpot,
				strCurrency
			),
			aDepositComp,
			adblDepositQuote,
			astrDepositManifestMeasure,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			true
		);
	}

	private static final FloatFloatComponent MakeFloatFloatSwap (
		final JulianDate dtEffective,
		final String strCurrency,
		final ForwardLabel forwardLabel,
		final String strMaturityTenor,
		final boolean bInArrears)
		throws Exception
	{
		ComposableFloatingUnitSetting cfusReference = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCurrency,
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFloatingUnitSetting cfusDerived = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			forwardLabel,
			bInArrears ? CompositePeriodBuilder.REFERENCE_PERIOD_IN_ARREARS : CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cpsReference = new CompositePeriodSetting (
			4,
			"3M",
			strCurrency,
			null,
			1.,
			null,
			null,
			null,
			null
		);

		CompositePeriodSetting cpsDerived = new CompositePeriodSetting (
			4,
			"3M",
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		List<Integer> lsReferenceStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"3M",
			strMaturityTenor,
			null
		);

		List<Integer> lsDerivedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"3M",
			strMaturityTenor,
			null
		);

		Stream referenceStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsReferenceStreamEdgeDate,
				cpsReference,
				cfusReference
			)
		);

		Stream derivedStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsDerivedStreamEdgeDate,
				cpsDerived,
				cfusDerived
			)
		);

		FloatFloatComponent floatFloat = new FloatFloatComponent (
			referenceStream,
			derivedStream,
			new CashSettleParams (0, strCurrency, 0)
		);

		return floatFloat;
	}

	private static final void SetMarketParams (
		final int iValueDate,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardLabel,
		final FundingLabel fundingLabel,
		final double dblFundingVol,
		final double dblForwardVol,
		final double dblForwardFundingCorr)
		throws Exception
	{
		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardLabel),
				forwardLabel.currency(),
				dblForwardVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fundingLabel),
				forwardLabel.currency(),
				dblFundingVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			forwardLabel,
			fundingLabel,
			new FlatUnivariate (dblForwardFundingCorr)
		);
	}

	private static final void VolCorrScenario (
		final FloatFloatComponent[] aCMSFloatFloat,
		final ValuationParams valParams,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardLabel,
		final FundingLabel fundingLabel,
		final double dblForwardVol,
		final double dblFundingVol,
		final double dblForwardFundingCorr,
		final double dblBaseReferenceParBasisSpread)
		throws Exception
	{
		SetMarketParams (
			valParams.valueDate(),
			mktParams,
			forwardLabel,
			fundingLabel,
			dblForwardVol,
			dblFundingVol,
			dblForwardFundingCorr
		);

		String strDump = "\t[" +
			FormatUtil.FormatDouble (dblForwardVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardFundingCorr, 2, 0, 100.) + "%] = ";

		for (int i = 0; i < aCMSFloatFloat.length; ++i) {
			CaseInsensitiveTreeMap<Double> mapOutput = aCMSFloatFloat[i].value (
				valParams,
				null,
				mktParams,
				null
			);

			if (0 != i) strDump += " || ";

			double dblReferenceParBasisSpread = mapOutput.get ("ReferenceParBasisSpread");

			strDump +=
				FormatUtil.FormatDouble (dblReferenceParBasisSpread, 2, 1, 1.) + " | " +
				FormatUtil.FormatDouble (dblReferenceParBasisSpread - dblBaseReferenceParBasisSpread, 2, 1, 1.);
		}

		System.out.println (strDump + "  |");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strTenor = "6M";
		String strCurrency = "USD";
		String strMaturityTenor = "5Y";

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2012,
			DateUtil.DECEMBER,
			11
		);

		MergedDiscountForwardCurve dc = MakeDC (
			dtSpot,
			strCurrency
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFundingState (dc);

		FloatFloatComponent cmsInAdvance = MakeFloatFloatSwap (
			dtSpot,
			strCurrency,
			forwardLabel,
			strMaturityTenor,
			false
		);

		FloatFloatComponent cmsInArrears = MakeFloatFloatSwap (
			dtSpot,
			strCurrency,
			forwardLabel,
			strMaturityTenor,
			true
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		double dblBaseReferenceParBasisSpread = cmsInAdvance.value (
			valParams,
			null,
			mktParams,
			null
		).get ("ReferenceParBasisSpread");

		double[] adblForwardVol = new double[] {
			0.10, 0.30, 0.50
		};

		double[] adblFundingVol = new double[] {
			0.10, 0.30, 0.50
		};

		double[] adblForwardFundingCorr = new double[] {
			-0.10, 0.25
		};

		System.out.println ("\n\n\t|--------------------------------------------------|");

		System.out.println ("\t| CMS FLOAT-FLOAT IN-ADVANCE & IN-ARREARS ANALYSIS |");

		System.out.println ("\t|--------------------------------------------------|");

		System.out.println ("\t| INPUTS: L -> R                                   |");

		System.out.println ("\t|                                                  |");

		System.out.println ("\t|   Forward State Volatility                       |");

		System.out.println ("\t|   Funding State Volatility                       |");

		System.out.println ("\t|   Forward-Funding Correlation                    |");

		System.out.println ("\t|                                                  |");

		System.out.println ("\t|--------------------------------------------------|");

		System.out.println ("\t| OUTPUTS: L -> R                                  |");

		System.out.println ("\t|                                                  |");

		System.out.println ("\t|   In Advance Reference Par Basis Spread          |");

		System.out.println ("\t|   In Advance Reference Par Basis Spread Change   |");

		System.out.println ("\t|   In Arrears Reference Par Basis Spread          |");

		System.out.println ("\t|   In Arrears Reference Par Basis Spread Change   |");

		System.out.println ("\t|                                                  |");

		System.out.println ("\t|--------------------------------------------------|");

		for (double dblForwardVol : adblForwardVol) {
			for (double dblFundingVol : adblFundingVol) {
				for (double dblForwardFundingCorr : adblForwardFundingCorr) {
					VolCorrScenario (
						new FloatFloatComponent[] {
							cmsInAdvance,
							cmsInArrears
						},
						valParams,
						mktParams,
						forwardLabel,
						FundingLabel.Standard (strCurrency),
						dblForwardVol,
						dblFundingVol,
						dblForwardFundingCorr,
						dblBaseReferenceParBasisSpread
					);
				}
			}
		}

		System.out.println ("\t|--------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
