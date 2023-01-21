
package org.drip.state.discount;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DiscountCurve</i> Interface combines the Interfaces of Latent State Curve Representation and Discount
 * Factor Estimator.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/README.md">Discount Curve Spline Latent State</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class DiscountCurve implements org.drip.analytics.definition.Curve,
	org.drip.state.discount.DiscountFactorEstimator {

	/**
	 * Construct a Flat Forward Instance of the Curve at the specified Date Nodes
	 * 
	 * @param strDayCount Forward Curve Day Count
	 * @param iFreq Forward Curve Frequency
	 * @param aiDate Array of Date Nodes
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatForward (
		final java.lang.String strDayCount,
		final int iFreq,
		final int[] aiDate)
	{
		if (null == aiDate) return null;

		int iNumNode = aiDate.length;
		double[] adblForwardRate = 0 == iNumNode ? null : new double [iNumNode];

		if (0 == iNumNode) return null;

		java.lang.String strCurrency = currency();

		org.drip.analytics.date.JulianDate dtStart = epoch();

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		try {
			for (int i = 0; i < iNumNode; ++i) {
				int iStartDate = 0 == i ? dtStart.julian() : aiDate[i - 1];

				adblForwardRate[i] = ((df (iStartDate) / df (aiDate[i])) - 1.) /
					org.drip.analytics.daycount.Convention.YearFraction (iStartDate, aiDate[i], strDayCount,
						false, aap, strCurrency);
			}

			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, aiDate,
				adblForwardRate, true, strDayCount, iFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Flat Native Forward Instance of the Curve at the specified Date Nodes
	 * 
	 * @param aiDate Array of Date Nodes
	 * @param dblBump The Bump Amount
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatNativeForward (
		final int[] aiDate,
		final double dblBump)
	{
		if (null == aiDate || !org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		int iNumNode = aiDate.length;
		double[] adblForwardRate = 0 == iNumNode ? null : new double [iNumNode];

		if (0 == iNumNode) return null;

		java.lang.String strCurrency = currency();

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency);

		if (null == ffsc) return null;

		org.drip.param.period.UnitCouponAccrualSetting ucas =
			ffsc.floatStreamConvention().floaterIndex().ucas();

		org.drip.analytics.date.JulianDate dtStart = epoch();

		int iSpotDate = dtStart.julian();

		int iFreq = ucas.freq();

		java.lang.String strDayCount = ucas.couponDC();

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		org.drip.product.definition.CalibratableComponent[] aCalibComp = calibComp();

		int iNumComp = aCalibComp.length;
		double[] adblCompCalibValue = new double[iNumComp];
		java.lang.String[] astrCalibMeasure = new java.lang.String[iNumComp];

		org.drip.param.market.CurveSurfaceQuoteContainer csqcNative =
			org.drip.param.creator.MarketParamsBuilder.Create
				((org.drip.state.discount.MergedDiscountForwardCurve) this, null, null, null, null, null,
					null);

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSpotDate);

		for (int i = 0; i < iNumComp; ++i)
		{
			astrCalibMeasure[i] = "Rate";

			try {
				adblCompCalibValue[i] = aCalibComp[i].measureValue (valParams, null, csqcNative, null,
					astrCalibMeasure[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.state.discount.MergedDiscountForwardCurve mdfcNonlinear =
			org.drip.state.creator.ScenarioDiscountCurveBuilder.NonlinearBuild (
				dtStart,
				strCurrency,
				aCalibComp,
				adblCompCalibValue,
				astrCalibMeasure,
				null
			);

		try {
			for (int i = 0; i < iNumNode; ++i) {
				int iStartDate = 0 == i ? iSpotDate : aiDate[i - 1];

				adblForwardRate[i] = ((mdfcNonlinear.df (iStartDate) / mdfcNonlinear.df (aiDate[i])) - 1.) /
					org.drip.analytics.daycount.Convention.YearFraction (iStartDate, aiDate[i], strDayCount,
						false, aap, strCurrency) + dblBump;
			}

			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, aiDate,
				adblForwardRate, true, strDayCount, iFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Flat Native Forward Instance of the Curve at the specified Date Node Tenors
	 * 
	 * @param astrTenor Array of Date Tenors
	 * @param dblBump The Bump Amount
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatNativeForward (
		final java.lang.String[] astrTenor,
		final double dblBump)
	{
		if (null == astrTenor || !org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		int iNumNode = astrTenor.length;
		double[] adblForwardRate = 0 == iNumNode ? null : new double [iNumNode];

		if (0 == iNumNode) return null;

		java.lang.String strCurrency = currency();

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency);

		if (null == ffsc) return null;

		org.drip.param.period.UnitCouponAccrualSetting ucas =
			ffsc.floatStreamConvention().floaterIndex().ucas();

		org.drip.analytics.date.JulianDate dtStart = epoch();

		int iFreq = ucas.freq();

		java.lang.String strDayCount = ucas.couponDC();

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		int[] aiDate = new int[iNumNode];

		try {
			for (int i = 0; i < iNumNode; ++i) {
				org.drip.analytics.date.JulianDate dtTenor = dtStart.addTenor (astrTenor[i]);

				if (null == dtTenor) return null;

				aiDate[i] = dtTenor.julian();

				int iStartDate = 0 == i ? dtStart.julian() : aiDate[i - 1];

				adblForwardRate[i] = ((df (iStartDate) / df (aiDate[i])) - 1.) /
					org.drip.analytics.daycount.Convention.YearFraction (iStartDate, aiDate[i], strDayCount,
						false, aap, strCurrency) + dblBump;
			}

			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, aiDate,
				adblForwardRate, true, strDayCount, iFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Flat Native Forward Instance of the Curve at the specified Date Nodes with
	 * 	(Exclusive/Inclusive) Bumps applied within the Tenors
	 * 
	 * @param aiDate Array of Date Nodes
	 * @param iBumpNode The Node to be Bumped
	 * @param dblBump The Bump Amount
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatNativeForwardEI (
		final int[] aiDate,
		final int iBumpNode,
		final double dblBump)
	{
		if (null == aiDate || !org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		int iNumNode = aiDate.length;
		double[] adblForwardRate = 0 == iNumNode ? null : new double [iNumNode];

		if (0 == iNumNode) return null;

		java.lang.String strCurrency = currency();

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency);

		if (null == ffsc) return null;

		org.drip.param.period.UnitCouponAccrualSetting ucas =
			ffsc.floatStreamConvention().floaterIndex().ucas();

		org.drip.analytics.date.JulianDate dtStart = epoch();

		int iFreq = ucas.freq();

		java.lang.String strDayCount = ucas.couponDC();

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		try {
			for (int i = 0; i < iNumNode; ++i) {
				int iStartDate = 0 == i ? dtStart.julian() : aiDate[i - 1];

				adblForwardRate[i] = ((df (iStartDate) / df (aiDate[i])) - 1.) /
					org.drip.analytics.daycount.Convention.YearFraction (iStartDate, aiDate[i], strDayCount,
						false, aap, strCurrency) + (i == iBumpNode ? dblBump : 0.);
			}

			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, aiDate,
				adblForwardRate, true, strDayCount, iFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
