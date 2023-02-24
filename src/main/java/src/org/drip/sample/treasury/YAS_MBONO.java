
package org.drip.sample.treasury;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.*;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.FixFloatComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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

/*
 * <i>YAS_MBONO</i> contains the sample demonstrating the replication of Bloomberg's Mexican MBONO MXN Bond
 * 	YAS Functionality.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/treasury/README.md">G20 Govvie Bond Definitions YAS</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

/**
 * <i>YAS_MBONO</i> contains the sample demonstrating the replication of Bloomberg's Mexican MBONO MXN Bond
 * 	YAS Functionality.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/treasury/README.md">G20 Govvie Bond Definitions YAS</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class YAS_MBONO {

	private static BondComponent TSYBond (
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final int iFreq,
		final String strDayCount,
		final String strCurrency,
		final double dblCoupon)
		throws Exception
	{
		return BondBuilder.CreateSimpleFixed (
			"MBONO " + FormatUtil.FormatDouble (dblCoupon, 1, 2, 100.) + " " + dtMaturity,
			strCurrency,
			"",
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);
	}

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

	/*
	 * Sample demonstrating building of rates curve from cash/future/swaps
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static MergedDiscountForwardCurve BuildRatesCurveFromInstruments (
		final JulianDate dtStart,
		final String[] astrCashTenor,
		final double[] adblCashRate,
		final String[] astrIRSTenor,
		final double[] adblIRSRate,
		final double dblBump,
		final String strCurrency)
		throws Exception
	{
		int iNumDCInstruments = astrCashTenor.length + adblIRSRate.length;
		int aiDate[] = new int[iNumDCInstruments];
		double adblRate[] = new double[iNumDCInstruments];
		String astrCalibMeasure[] = new String[iNumDCInstruments];
		double adblCompCalibValue[] = new double[iNumDCInstruments];
		CalibratableComponent aCompCalib[] = new CalibratableComponent[iNumDCInstruments];

		// Cash Calibration

		JulianDate dtCashEffective = dtStart.addBusDays (
			1,
			strCurrency
		);

		for (int i = 0; i < astrCashTenor.length; ++i) {
			astrCalibMeasure[i] = "Rate";
			adblRate[i] = java.lang.Double.NaN;
			adblCompCalibValue[i] = adblCashRate[i] + dblBump;

			aCompCalib[i] = SingleStreamComponentBuilder.Deposit (
				dtCashEffective,
				new JulianDate (aiDate[i] = dtCashEffective.addTenor (astrCashTenor[i]).julian()),
				ForwardLabel.Create (
					strCurrency,
					astrCashTenor[i]
				)
			);
		}

		// IRS Calibration

		JulianDate dtIRSEffective = dtStart.addBusDays (2, strCurrency);

		for (int i = 0; i < astrIRSTenor.length; ++i) {
			astrCalibMeasure[i + astrCashTenor.length] = "Rate";
			adblRate[i + astrCashTenor.length] = java.lang.Double.NaN;
			adblCompCalibValue[i + astrCashTenor.length] = adblIRSRate[i] + dblBump;

			aiDate[i + astrCashTenor.length] = dtIRSEffective.addTenor (astrIRSTenor[i]).julian();

			aCompCalib[i + astrCashTenor.length] = OTCIRS (
				dtIRSEffective,
				strCurrency,
				astrIRSTenor[i],
				0.
			);
		}

		/*
		 * Build the IR curve from the components, their calibration measures, and their calibration quotes.
		 */

		return ScenarioDiscountCurveBuilder.NonlinearBuild (
			dtStart,
			strCurrency,
			aCompCalib,
			adblCompCalibValue,
			astrCalibMeasure,
			null
		);
	}

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrCashTenor = new String[] {"3M"};
		double[] adblCashRate = new double[] {0.00276};
		String[] astrIRSTenor = new String[] {   "1Y",    "2Y",    "3Y",    "4Y",    "5Y",    "6Y",    "7Y",
			   "8Y",    "9Y",   "10Y",   "11Y",   "12Y",   "15Y",   "20Y",   "25Y",   "30Y",   "40Y",   "50Y"};
		double[] adblIRSRate = new double[]  {0.00367, 0.00533, 0.00843, 0.01238, 0.01609, 0.01926, 0.02191,
			0.02406, 0.02588, 0.02741, 0.02870, 0.02982, 0.03208, 0.03372, 0.03445, 0.03484, 0.03501, 0.03484};

		return BuildRatesCurveFromInstruments (
			dtSpot,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.,
			strCurrency
		);
	}

	private static final void TSYMetrics (
		final BondComponent tsyBond,
		final double dblNotional,
		final JulianDate dtSettle,
		final CurveSurfaceQuoteContainer mktParams,
		final double dblCleanPrice)
		throws Exception
	{
		double dblAccrued = tsyBond.accrued (
			dtSettle.julian(),
			null
		);

		ValuationParams valParams = ValuationParams.Spot (dtSettle.julian());

		double dblYield = tsyBond.yieldFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		double dblModifiedDuration = tsyBond.modifiedDurationFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		double dblRisk = tsyBond.yield01FromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		double dblConvexity = tsyBond.convexityFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		JulianDate dtPreviousCouponDate = tsyBond.previousCouponDate (dtSettle);

		System.out.println();

		System.out.println ("\t\t" + tsyBond.name());

		System.out.println ("\tPrice             : " + FormatUtil.FormatDouble (dblCleanPrice, 1, 4, 100.));

		System.out.println ("\tYield             : " + FormatUtil.FormatDouble (dblYield, 1, 4, 100.) + "%");

		System.out.println ("\tSettle            :  " + dtSettle);

		System.out.println();

		System.out.println ("\tModified Duration : " + FormatUtil.FormatDouble (dblModifiedDuration, 1, 4, 10000.));

		System.out.println ("\tRisk              : " + FormatUtil.FormatDouble (dblRisk, 1, 4, 10000.));

		System.out.println ("\tConvexity         : " + FormatUtil.FormatDouble (dblConvexity * dblNotional, 1, 4, 1.));

		System.out.println ("\tDV01              : " + FormatUtil.FormatDouble (dblRisk * dblNotional, 1, 0, 1.));

		System.out.println();

		System.out.println ("\tPrevious Coupon Date :  " + dtPreviousCouponDate);

		System.out.println ("\tFace                 : " + FormatUtil.FormatDouble (dblNotional, 1, 2, 1.));

		System.out.println ("\tPrincipal            : " + FormatUtil.FormatDouble (dblCleanPrice * dblNotional, 1, 2, 1.));

		System.out.println ("\tAccruedDCF           : " + FormatUtil.FormatDouble (dblAccrued, 1, 6, 1.));

		System.out.println ("\tAccrued              : " + FormatUtil.FormatDouble (dblAccrued * dblNotional, 1, 2, 1.));

		System.out.println ("\tTotal                : " + FormatUtil.FormatDouble ((dblCleanPrice + dblAccrued) * dblNotional, 1, 2, 1.));

		System.out.println ("\tAccrual Days         : " + (dtSettle.julian() - dtPreviousCouponDate.julian()));
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2016,
			DateUtil.MARCH,
			30
		);

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2011,
			DateUtil.NOVEMBER,
			20
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2036,
			DateUtil.NOVEMBER,
			20
		);

		int iFreq = 2;
		String strDayCount = "Act/364";
		String strCurrency = "MXN";
		double dblCoupon = 0.10;
		double dblNotional = 100000000.;
		double dblCleanPrice = 1.35213;

		BondComponent tsyBond = TSYBond (
			dtEffective,
			dtMaturity,
			iFreq,
			strDayCount,
			strCurrency,
			dblCoupon
		);

		System.out.println();

		System.out.println ("\tEffective : " + tsyBond.effectiveDate());

		System.out.println ("\tMaturity  : " + tsyBond.maturityDate());

		System.out.println();

		MergedDiscountForwardCurve dc = FundingCurve (
			dtSpot,
			strCurrency
		);

		TSYMetrics (
			tsyBond,
			dblNotional,
			dtSpot,
			MarketParamsBuilder.Create (
				dc,
				null,
				null,
				null,
				null,
				null,
				null
			),
			dblCleanPrice
		);

		System.out.println ("\n\tCashflow\n\t--------");

		for (CompositePeriod p : tsyBond.couponPeriods())
			System.out.println ("\t\t" +
				DateUtil.YYYYMMDD (p.startDate()) + " | " +
				DateUtil.YYYYMMDD (p.endDate()) + " | " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 4, 1.) + " ||"
			);

		EnvManager.TerminateEnv();
	}
}
