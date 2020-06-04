
package org.drip.sample.lmm;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.MarketSurface;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.dynamics.lmm.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.rates.*;
import org.drip.sequence.random.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.creator.*;
import org.drip.state.curve.BasisSplineForwardRate;
import org.drip.state.discount.*;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.*;
import org.drip.state.inference.*;

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
 * <i>FixFloatMonteCarloEvolver</i> demonstrates the steps associated with a LMM-Based Monte-Carlo pricing of
 * 	a Standard Fix-Float Swap. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure
 *  			Models, The University of New South Wales.
 *  	</li>
 *  	<li>
 *  		Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of
 *  			New South Wales.
 *  	</li>
 *  	<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics,
 * 				Mathematical Finance 7 (2), 127-155.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lmm/README.md">LMM Multi-Factor Monte Carlo</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatMonteCarloEvolver {

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SingleStreamComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final String strCurrency,
		final int[] aiDay)
		throws Exception
	{
		SingleStreamComponent[] aDeposit = new SingleStreamComponent[aiDay.length];

		ComposableFloatingUnitSetting cfus = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				strCurrency,
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cps = new CompositePeriodSetting (
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

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		for (int i = 0; i < aiDay.length; ++i) {
			aDeposit[i] = new SingleStreamComponent (
				"DEPOSIT_" + aiDay[i],
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.EdgePair (
							dtEffective,
							dtEffective.addBusDays (
								aiDay[i],
								strCurrency
							)
						),
						cps,
						cfus
					)
				),
				csp
			);

			aDeposit[i].setPrimaryCode (aiDay[i] + "D");
		}

		return aDeposit;
	}

	/*
	 * Construct the Swap Instrument from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent SwapInstrumentFromMaturityTenor (
		final JulianDate dtEffective,
		final String strCurrency,
		final double dblFixedCoupon,
		final String strMaturityTenor)
		throws Exception
	{
		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			4,
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			true,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
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

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			dblFixedCoupon,
			0.,
			strCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
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

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
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

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"3M",
			strMaturityTenor,
			null
		);

		List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"3M",
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

		irs.setPrimaryCode ("IRS." + strMaturityTenor + "." + strCurrency);

		return irs;
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtEffective,
		final String strCurrency,
		final String[] astrMaturityTenor)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aIRS[i] = SwapInstrumentFromMaturityTenor (
				dtEffective,
				strCurrency,
				0.,
				astrMaturityTenor[i]
			);

		return aIRS;
	}

	/*
	 * This sample demonstrates discount curve calibration and input instrument calibration quote recovery.
	 * 	It shows the following:
	 * 	- Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
	 * 	- Construct the Cash/Swap Instrument Set Stretch Builder.
	 * 	- Set up the Linear Curve Calibrator using the following parameters:
	 * 		- Cubic Exponential Mixture Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
	 * 		of Cash and Swap Stretches.
	 * 	- Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
	 * 		construction methodologies.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MergedDiscountForwardCurve OTCInstrumentCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		/*
		 * Construct the Array of Deposit Instruments and their Quotes from the given set of parameters
		 */

		SingleStreamComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			strCurrency,
			new int[] {
				1, 2, 7, 14, 30, 60
			}
		);

		double[] adblDepositQuote = new double[] {
			0.0013, 0.0017, 0.0017, 0.0018, 0.0020, 0.0023
		};

		/*
		 * Construct the Deposit Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec depositStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"DEPOSIT",
			aDepositComp,
			"ForwardRate",
			adblDepositQuote
		);

		/*
		 * Construct the Array of EDF Instruments and their Quotes from the given set of parameters
		 */

		SingleStreamComponent[] aEDFComp = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtSpot,
			8,
			strCurrency
		);

		double[] adblEDFQuote = new double[] {
			0.0027, 0.0032, 0.0041, 0.0054, 0.0077, 0.0104, 0.0134, 0.0160
		};

		/*
		 * Construct the EDF Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec edfStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"EDF",
			aEDFComp,
			"ForwardRate",
			adblEDFQuote
		);

		/*
		 * Construct the Array of Swap Instruments and their Quotes from the given set of parameters
		 */

		FixFloatComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
			}
		);

		double[] adblSwapQuote = new double[] {
			0.0166, 0.0206, 0.0241, 0.0269, 0.0292, 0.0311, 0.0326, 0.0340, 0.0351, 0.0375, 0.0393, 0.0402, 0.0407, 0.0409, 0.0409
		};

		/*
		 * Construct the Swap Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec swapStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"SWAP",
			aSwapComp,
			"SwapRate",
			adblSwapQuote
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			depositStretch,
			edfStretch,
			swapStretch
		};

		/*
		 * Set up the Linear Curve Calibrator using the following parameters:
		 * 	- Cubic Exponential Mixture Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LinearLatentStateCalibrator lcc = new LinearLatentStateCalibrator (
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
			strCurrency
		);

		/*
		 * Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
		 *  of Deposit, Futures, and Swap Stretches.
		 */

		MergedDiscountForwardCurve dc = ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
			strCurrency,
			lcc,
			aStretchSpec,
			valParams,
			null,
			null,
			null,
			1.
		);

		CurveSurfaceQuoteContainer csqs = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		/*
		 * Cross-Comparison of the Deposit Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     DEPOSIT INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aDepositComp.length; ++i)
			System.out.println ("\t[" + aDepositComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (aDepositComp[i].measureValue (valParams, null, csqs,
					null, "Rate"), 1, 6, 1.) + " | " + FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.));

		/*
		 * Cross-Comparison of the EDF Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     EDF INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aEDFComp.length; ++i)
			System.out.println ("\t[" + aEDFComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (aEDFComp[i].measureValue (valParams, null, csqs, null, "Rate"), 1, 6, 1.)
					+ " | " + FormatUtil.FormatDouble (adblEDFQuote[i], 1, 6, 1.));

		/*
		 * Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     SWAP INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aSwapComp.length; ++i)
			System.out.println ("\t[" + aSwapComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (aSwapComp[i].measureValue (valParams, null, csqs, null, "CalibSwapRate"), 1, 6, 1.)
					+ " | " + FormatUtil.FormatDouble (adblSwapQuote[i], 1, 6, 1.) + " | " +
						FormatUtil.FormatDouble (aSwapComp[i].measureValue (valParams, null, csqs, null, "FairPremium"), 1, 6, 1.));

		return dc;
	}

	private static final ForwardCurve LIBORSpan (
		final MergedDiscountForwardCurve dc,
		final ForwardLabel forwardLabel,
		final SegmentCustomBuilderControl scbc,
		final JulianDate dtView,
		final int iNumForwardTenor)
		throws Exception
	{
		int[] aiDate = new int[iNumForwardTenor + 1];
		double[] adblLIBOR = new double[iNumForwardTenor + 1];
		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[iNumForwardTenor];

		JulianDate dtForward = dtView.subtractTenor (forwardLabel.tenor());

		for (int i = 0; i <= iNumForwardTenor; ++i) {
			if (iNumForwardTenor != i) aSCBC[i] = scbc;

			aiDate[i] = dtForward.julian();

			adblLIBOR[i] = dc.libor (dtForward, forwardLabel.tenor());

			dtForward = dtForward.addTenor (forwardLabel.tenor());
		}

		return new BasisSplineForwardRate (
			forwardLabel,
			new OverlappingStretchSpan (
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"SPOT_QM_LIBOR",
					aiDate,
					adblLIBOR,
					aSCBC,
					null,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE
				)
			)
		);
	}

	private static final MarketSurface FlatVolatilitySurface (
		final JulianDate dtStart,
		final String strCurrency,
		final double dblFlatVol)
		throws Exception
	{
		return ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (
			"VIEW_TARGET_VOLATILITY_SURFACE",
			dtStart,
			strCurrency,
			new double[] {
				dtStart.julian(),
				dtStart.addYears (2).julian(),
				dtStart.addYears (4).julian(),
				dtStart.addYears (6).julian(),
				dtStart.addYears (8).julian(),
				dtStart.addYears (10).julian()
			},
			new double[] {
				dtStart.julian(),
				dtStart.addYears (2).julian(),
				dtStart.addYears (4).julian(),
				dtStart.addYears (6).julian(),
				dtStart.addYears (8).julian(),
				dtStart.addYears (10).julian()
			},
			new double[][] {
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
			},
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				null,
				null
			),
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				null,
				null
			)
		);
	}

	private static final LognormalLIBORVolatility LLVInstance (
		final int iSpotDate,
		final ForwardLabel forwardLabel,
		final MarketSurface[] aMS,
		final double[][] aadblCorrelation,
		final int iNumFactor)
		throws Exception
	{
		UnivariateSequenceGenerator[] aUSG = new UnivariateSequenceGenerator[aMS.length];

		for (int i = 0; i < aUSG.length; ++i)
			aUSG[i] = new BoxMullerGaussian (
				0.,
				1.
			);

		return new LognormalLIBORVolatility (
			iSpotDate,
			forwardLabel,
			aMS,
			new PrincipalFactorSequenceGenerator (
				aUSG,
				aadblCorrelation,
				iNumFactor
			)
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strForwardTenor = "3M";
		String strViewTenor = "6M";
		String strSimulationTenor = "6M";
		String strSwapTenor = "5Y";
		String strCurrency = "USD";
		double dblFlatVol1 = 0.35;
		double dblFlatVol2 = 0.42;
		double dblFlatVol3 = 0.27;
		int iNumForwardTenor = 30;
		int iNumFactor = 2;
		int iNumRun = 100;

		double[][] aadblCorrelation = new double[][] {
			{1.0, 0.1, 0.2},
			{0.1, 1.0, 0.2},
			{0.2, 0.1, 1.0}
		};

		SegmentCustomBuilderControl scbc = new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (4),
			SegmentInelasticDesignControl.Create (
				2,
				2
			),
			new ResponseScalingShapeControl (
				true,
				new QuadraticRationalShapeControl (1.)
			),
			null
		);

		JulianDate dtSpot = org.drip.analytics.date.DateUtil.Today();

		MarketSurface[] aMS = new MarketSurface[] {
			FlatVolatilitySurface (
				dtSpot,
				strCurrency,
				dblFlatVol1
			),
			FlatVolatilitySurface (
				dtSpot,
				strCurrency,
				dblFlatVol2
			),
			FlatVolatilitySurface (
				dtSpot,
				strCurrency,
				dblFlatVol3
			)
		};

		FundingLabel fundingLabel = FundingLabel.Standard (
			strCurrency
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strForwardTenor
		);

		JulianDate dtView = dtSpot.addTenor (
			strViewTenor
		);

		JulianDate dtSimulationEnd = dtSpot.addTenor (
			strSimulationTenor
		);

		MergedDiscountForwardCurve dc = OTCInstrumentCurve (
			dtSpot,
			strCurrency
		);

		FixFloatComponent ffcFixFloat = SwapInstrumentFromMaturityTenor (
			dtView,
			strCurrency,
			0.,
			strSwapTenor
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		ForwardCurve fc = LIBORSpan (
			dc,
			forwardLabel,
			scbc,
			dtView,
			iNumForwardTenor
		);

		CurveSurfaceQuoteContainer csqs = MarketParamsBuilder.DiscountForward (
			dc,
			fc
		);

		double dblBaselineSwapRate = ffcFixFloat.value (
			valParams,
			null,
			csqs,
			null
		).get (
			"FairPremium"
		);

		ffcFixFloat = SwapInstrumentFromMaturityTenor (
			dtSpot,
			strCurrency,
			dblBaselineSwapRate,
			strSwapTenor
		);

		LognormalLIBORCurveEvolver llce = LognormalLIBORCurveEvolver.Create (
			fundingLabel,
			forwardLabel,
			iNumForwardTenor,
			scbc
		);

		BGMCurveUpdate bgmInitial = BGMCurveUpdate.Create (
			fundingLabel,
			forwardLabel,
			dtSpot.julian(),
			dtSpot.julian(),
			fc,
			null,
			dc,
			null,
			null,
			null,
			null,
			null,
			LLVInstance (
				dtSpot.julian(),
				forwardLabel,
				aMS,
				aadblCorrelation,
				iNumFactor
			)
		);

		ForwardCurve[] aFCLIBOR = llce.simulateTerminalLatentState (
			dtSpot.julian(),
			dtSimulationEnd.julian(),
			1,
			dtView.julian(),
			bgmInitial,
			iNumRun
		);

		System.out.println ("\n\t||---------------------------------||");

		System.out.println ("\t||  SIML  |  BASE  |   PV  |  ITM  ||");

		System.out.println ("\t||---------------------------------||");

		int iNumITM = 0;
		double dblOptionPayout = 0.;

		for (int i = 0; i < iNumRun; ++i) {
			Map<String, Double> mapScenOutput = ffcFixFloat.value (
				new ValuationParams (
					dtSimulationEnd,
					dtSimulationEnd,
					strCurrency
				),
				null,
				MarketParamsBuilder.DiscountForward (
					dc,
					aFCLIBOR[i]
				),
				null
			);

			double dblSimulatedPayout = mapScenOutput.get ("PV");

			double dblSimulatedSwapRate = mapScenOutput.get ("FairPremium");

			boolean bITM = dblSimulatedSwapRate < dblBaselineSwapRate;

			if (bITM) {
				++iNumITM;
				dblOptionPayout += dblSimulatedPayout;
			}

			System.out.println ("\t|| " +
				FormatUtil.FormatDouble (dblSimulatedSwapRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblBaselineSwapRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblSimulatedPayout, 1, 2, 1.) + " | " +
				(bITM ? " OUI  ||" : " NON  ||")
			);
		}

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\n\t||---------------------------||");

		System.out.println ("\t||  ITM Success Rate:" +
			FormatUtil.FormatDouble (((double) iNumITM) / iNumRun, 2, 2, 100.) + "% ||"
		);

		System.out.println ("\t||  Option Payout   :" +
			FormatUtil.FormatDouble (dblOptionPayout / iNumRun, 1, 4, 1.) + " ||"
		);

		System.out.println ("\t||  Option PV       :" +
			FormatUtil.FormatDouble (dblOptionPayout * dc.df (dtSimulationEnd) / iNumRun, 1, 4, 1.) + " ||"
		);

		System.out.println ("\t||---------------------------||");

		EnvManager.TerminateEnv();
	}
}
