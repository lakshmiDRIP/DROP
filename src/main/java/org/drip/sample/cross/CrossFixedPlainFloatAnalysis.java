
package org.drip.sample.cross;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.params.*;
import org.drip.product.rates.*;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>CrossFixedPlainFloatAnalysis</i> demonstrates the impact of Funding Volatility, Forward Volatility, and
 * Funding/Forward Correlation on the Valuation of a fix-float swap with a EUR Fixed leg that pays in USD,
 * and a USD Floating Leg. Comparison is done across MTM and non-MTM fixed Leg Counterparts.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/README.md">Cross Currency Stream Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CrossFixedPlainFloatAnalysis {

	private static final FixFloatComponent MakeFixFloatSwap (
		final JulianDate dtEffective,
		final boolean bFXMTM,
		final String strPayCurrency,
		final String strFixedCouponCurrency,
		final String strMaturityTenor,
		final int iTenorInMonths)
		throws Exception
	{
		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			strFixedCouponCurrency,
			false,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			iTenorInMonths + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strPayCurrency,
				iTenorInMonths + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.02,
			0.,
			strFixedCouponCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			12 / iTenorInMonths,
			iTenorInMonths + "M",
			strPayCurrency,
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
			strPayCurrency,
			null,
			1.,
			null,
			null,
			bFXMTM ? null : new FixingSetting (
				FixingSetting.FIXING_PRESET_STATIC,
				null,
				dtEffective.julian()
			),
			null
		);

		List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			iTenorInMonths + "M",
			strMaturityTenor,
			null
		);

		List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"6M",
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

		/*
		 * The fix-float swap instance
		 */

		FixFloatComponent fixFloat = new FixFloatComponent (
			fixedStream,
			floatingStream,
			new CashSettleParams (
				0,
				strPayCurrency,
				0
			)
		);

		return fixFloat;
	}

	private static final void SetMarketParams (
		final int iValueDate,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardLabel,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForwardVol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForwardFundingCorr,
		final double dblForwardFXCorr,
		final double dblFundingFXCorr)
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

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fxLabel),
				forwardLabel.currency(),
				dblFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			forwardLabel,
			fundingLabel,
			new FlatUnivariate (dblForwardFundingCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardLabel,
			fxLabel,
			new FlatUnivariate (dblForwardFXCorr)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabel,
			fxLabel,
			new FlatUnivariate (dblFundingFXCorr)
		);
	}

	private static final void VolCorrScenario (
		final FixFloatComponent[] aFixFloat,
		final ValuationParams valParams,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardLabel,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForwardVol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForwardFundingCorr,
		final double dblForwardFXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		SetMarketParams (
			valParams.valueDate(),
			mktParams,
			forwardLabel,
			fundingLabel,
			fxLabel,
			dblForwardVol,
			dblFundingVol,
			dblFXVol,
			dblForwardFundingCorr,
			dblForwardFXCorr,
			dblFundingFXCorr
		);

		String strDump = "\t[" +
			FormatUtil.FormatDouble (dblForwardVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFXVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardFundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardFXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingFXCorr, 2, 0, 100.) + "%] = ";

		for (int i = 0; i < aFixFloat.length; ++i) {
			CaseInsensitiveTreeMap<Double> mapOutput = aFixFloat[i].value (valParams, null, mktParams, null);

			if (0 != i) strDump += " || ";

			strDump +=
				FormatUtil.FormatDouble (mapOutput.get ("ReferenceCumulativeConvexityAdjustmentPremium"), 2, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (mapOutput.get ("DerivedCumulativeConvexityAdjustmentPremium"), 2, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (mapOutput.get ("CumulativeConvexityAdjustmentPremium"), 2, 0, 10000.);
		}

		System.out.println (strDump);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		double dblUSDCollateralRate = 0.02;
		double dblEURCollateralRate = 0.02;
		double dblUSD3MForwardRate = 0.02;
		double dblUSDEURFXRate = 1. / 1.35;

		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = org.drip.analytics.date.DateUtil.Today();

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			"USD"
		);

		ForwardLabel fri3M = ForwardLabel.Create (
			"USD",
			"3M"
		);

		MergedDiscountForwardCurve dcUSDCollatDomestic = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			"USD",
			dblUSDCollateralRate
		);

		MergedDiscountForwardCurve dcEURCollatDomestic = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			"EUR",
			dblEURCollateralRate
		);

		ForwardCurve fc3MUSD = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtToday,
			fri3M,
			dblUSD3MForwardRate
		);

		CurrencyPair cp = CurrencyPair.FromCode ("USD/EUR");

		FixFloatComponent fixMTMFloat = MakeFixFloatSwap (
			dtToday,
			true,
			"USD",
			"EUR",
			"2Y",
			3
		);

		FixFloatComponent fixNonMTMFloat = MakeFixFloatSwap (
			dtToday,
			false,
			"USD",
			"EUR",
			"2Y",
			3
		);

		FXLabel fxLabel = FXLabel.Standard (cp);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFundingState (dcUSDCollatDomestic);

		mktParams.setForwardState (fc3MUSD);

		mktParams.setFundingState (dcEURCollatDomestic);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				fxLabel.fullyQualifiedName(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblUSDEURFXRate},
				dblUSDEURFXRate
			)
		);

		mktParams.setFixing (
			dtToday,
			fxLabel,
			dblUSDEURFXRate
		);

		double[] adblForwardVol = new double[] {
			0.1, 0.35, 0.60
		};

		double[] adblFundingVol = new double[] {
			0.1, 0.35, 0.60
		};

		double[] adblFXVol = new double[] {
			0.1, 0.35, 0.60
		};

		double[] adblForwardFundingCorr = new double[] {
			-0.1, 0.35
		};

		double[] adblForwardFXCorr = new double[] {
			-0.1, 0.35
		};

		double[] adblFundingFXCorr = new double[] {
			-0.1, 0.35
		};

		for (double dblForwardVol : adblForwardVol) {
			for (double dblFundingVol : adblFundingVol) {
				for (double dblFXVol : adblFXVol) {
					for (double dblForwardFundingCorr : adblForwardFundingCorr) {
						for (double dblForwardFXCorr : adblForwardFXCorr) {
							for (double dblFundingFXCorr : adblFundingFXCorr)
								VolCorrScenario (
									new FixFloatComponent[] {
										fixMTMFloat,
										fixNonMTMFloat
									},
									valParams,
									mktParams,
									fri3M,
									FundingLabel.Standard ("USD"),
									fxLabel,
									dblForwardVol,
									dblFundingVol,
									dblFXVol,
									dblForwardFundingCorr,
									dblForwardFXCorr,
									dblFundingFXCorr
								);
						}
					}
				}
			}
		}

		EnvManager.TerminateEnv();
	}
}
