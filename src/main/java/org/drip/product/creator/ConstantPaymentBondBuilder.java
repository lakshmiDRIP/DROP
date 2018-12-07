
package org.drip.product.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>ConstantPaymentBondBuilder</i> contains the Suite of Helper Functions for creating Constant Payments
 * Based Bonds.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator">Creator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ConstantPaymentBondBuilder {

	/**
	 * Construct an Instance of the Constant Payment Bond
	 * 
	 * @param strName Mortgage Bond Instance Name
	 * @param dtEffective Effective Date
	 * @param strCurrency Currency
	 * @param iNumPayment The (Maximum) Number of Payments
	 * @param strDayCount Coupon/Accrual Day Count
	 * @param iPayFrequency Pay Frequency
	 * @param dblCouponRate The Coupon Rate
	 * @param dblFeeRate The Fee Rate
	 * @param dblConstantAmount The Fixed Monthly Amount
	 * @param dblInitialNotional The Initial Bond Notional
	 * 
	 * @return Instance of the Fixed Mortgage Product
	 */

	public static final org.drip.product.credit.BondComponent Standard (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strCurrency,
		final int iNumPayment,
		final java.lang.String strDayCount,
		final int iPayFrequency,
		final double dblCouponRate,
		final double dblFeeRate,
		final double dblConstantAmount,
		final double dblInitialNotional)
	{
		if (null == dtEffective || !org.drip.quant.common.NumberUtil.IsValid (dblCouponRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFeeRate) || dblFeeRate > dblCouponRate ||
				!org.drip.quant.common.NumberUtil.IsValid (dblConstantAmount) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblInitialNotional))
			return null;

		double dblOutstandingPrincipal = dblInitialNotional;

		java.util.List<java.lang.Double> lsCouponPayment = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> lsOutstandingPrincipal = new
			java.util.ArrayList<java.lang.Double>();

		java.util.List<org.drip.analytics.date.JulianDate> lsPaymentDate = new
			java.util.ArrayList<org.drip.analytics.date.JulianDate>();

		for (int i = 0; i < iNumPayment; ++i) {
			double dblCouponPayment = java.lang.Double.NaN;

			org.drip.analytics.date.JulianDate dtPayment = dtEffective.addMonths (i + 1);

			org.drip.analytics.date.JulianDate dtPrev = 0 == i ? dtEffective : lsPaymentDate.get (i - 1);

			try {
				dblCouponPayment = dblOutstandingPrincipal * (dblCouponRate - dblFeeRate) *
					org.drip.analytics.daycount.Convention.YearFraction (dtPrev.julian(), dtPayment.julian(),
						strDayCount, false, null, "");
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			lsPaymentDate.add (dtPayment);

			lsCouponPayment.add (dblCouponPayment);

			double dblPrincipalPayment = dblConstantAmount - dblCouponPayment;

			if (dblPrincipalPayment > dblOutstandingPrincipal) {
				lsOutstandingPrincipal.add (0.);

				break;
			}

			dblOutstandingPrincipal -= dblPrincipalPayment;

			lsOutstandingPrincipal.add (dblOutstandingPrincipal);
		}

		int iNumValidPayment = lsOutstandingPrincipal.size();

		double[] adblCouponPayment = new double[iNumValidPayment];
		double[] adblOutstandingPrincipal = new double[iNumValidPayment];
		org.drip.analytics.date.JulianDate[] adtPayment = new
			org.drip.analytics.date.JulianDate[iNumValidPayment];

		for (int i = 0; i < iNumValidPayment; ++i) {
			adtPayment[i] = lsPaymentDate.get (i);

			adblCouponPayment[i] = lsCouponPayment.get (i);

			adblOutstandingPrincipal[i] = lsOutstandingPrincipal.get (i);
		}

		return org.drip.product.creator.BondBuilder.CreateBondFromCF (strName, dtEffective, strCurrency, "",
			strDayCount, dblInitialNotional, dblCouponRate - dblFeeRate, iPayFrequency, adtPayment,
				adblCouponPayment, adblOutstandingPrincipal, false);
	}

	/**
	 * Construct an Instance of the Constant Payment Bond with a Deterministic Pre-payment Rate
	 * 
	 * @param strName Mortgage Bond Instance Name
	 * @param dtEffective Effective Date
	 * @param strCurrency Currency
	 * @param iNumPayment The (Maximum) Number of Payments
	 * @param strDayCount Coupon/Accrual Day Count
	 * @param iPayFrequency Pay Frequency
	 * @param dblCouponRate The Coupon Rate
	 * @param dblFeeRate The Fee Rate
	 * @param dblCPR the Constant Pre-payment Rate
	 * @param dblConstantAmount The Fixed Monthly Amount
	 * @param dblInitialNotional The Initial Bond Notional
	 * 
	 * @return Instance of the Fixed Mortgage Product
	 */

	public static final org.drip.product.credit.BondComponent Prepay (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strCurrency,
		final int iNumPayment,
		final java.lang.String strDayCount,
		final int iPayFrequency,
		final double dblCouponRate,
		final double dblFeeRate,
		final double dblCPR,
		final double dblConstantAmount,
		final double dblInitialNotional)
	{
		if (null == dtEffective || !org.drip.quant.common.NumberUtil.IsValid (dblCouponRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFeeRate) || dblFeeRate > dblCouponRate ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCPR) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblConstantAmount) ||
						!org.drip.quant.common.NumberUtil.IsValid (dblInitialNotional))
			return null;

		double dblOutstandingPrincipal = dblInitialNotional;

		java.util.List<java.lang.Double> lsCouponPayment = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> lsOutstandingPrincipal = new
			java.util.ArrayList<java.lang.Double>();

		java.util.List<org.drip.analytics.date.JulianDate> lsPaymentDate = new
			java.util.ArrayList<org.drip.analytics.date.JulianDate>();

		for (int i = 0; i < iNumPayment; ++i) {
			double dblCouponPayment = java.lang.Double.NaN;

			org.drip.analytics.date.JulianDate dtPayment = dtEffective.addMonths (i + 1);

			org.drip.analytics.date.JulianDate dtPrev = 0 == i ? dtEffective : lsPaymentDate.get (i - 1);

			try {
				dblCouponPayment = dblOutstandingPrincipal * (dblCouponRate - dblFeeRate) *
					org.drip.analytics.daycount.Convention.YearFraction (dtPrev.julian(), dtPayment.julian(),
						strDayCount, false, null, "");
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			lsPaymentDate.add (dtPayment);

			lsCouponPayment.add (dblCouponPayment);

			double dblPrincipalPayment = dblConstantAmount - dblCouponPayment + dblCPR *
				dblOutstandingPrincipal;

			if (dblPrincipalPayment > dblOutstandingPrincipal) {
				lsOutstandingPrincipal.add (0.);

				break;
			}

			dblOutstandingPrincipal -= dblPrincipalPayment;

			lsOutstandingPrincipal.add (dblOutstandingPrincipal);
		}

		int iNumValidPayment = lsOutstandingPrincipal.size();

		double[] adblCouponPayment = new double[iNumValidPayment];
		double[] adblOutstandingPrincipal = new double[iNumValidPayment];
		org.drip.analytics.date.JulianDate[] adtPayment = new
			org.drip.analytics.date.JulianDate[iNumValidPayment];

		for (int i = 0; i < iNumValidPayment; ++i) {
			adtPayment[i] = lsPaymentDate.get (i);

			adblCouponPayment[i] = lsCouponPayment.get (i);

			adblOutstandingPrincipal[i] = lsOutstandingPrincipal.get (i);
		}

		return org.drip.product.creator.BondBuilder.CreateBondFromCF (strName, dtEffective, strCurrency, "",
			strDayCount, dblInitialNotional, dblCouponRate - dblFeeRate, iPayFrequency, adtPayment,
				adblCouponPayment, adblOutstandingPrincipal, false);
	}

	/**
	 * Compute the Constant Uniform Payment Amount for the Parameters of the Specified Mortgage Bond
	 * 
	 * @param dblBondNotional The Current Notional
	 * @param dblCouponRate The Coupon Rate
	 * @param iTenorInYears Tenor in Years
	 * 
	 * @return The Constant Uniform Payment Amount
	 * 
	 * @throws java.lang.Exception Thrown if the Constant Uniform Payment Amount cannot be computed
	 */

	public static final double ConstantUniformPaymentAmount (
		final double dblBondNotional,
		final double dblCouponRate,
		final int iTenorInYears)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBondNotional) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCouponRate))
				throw new java.lang.Exception
					("ConstantPaymentBondBuilder::ConstantUniformPaymentAmount => Invalid Inputs");

		int iNumPeriod = iTenorInYears * 12;
		double dblPeriodRate = dblCouponRate / 12.;

		return dblPeriodRate * dblBondNotional * java.lang.Math.pow (1. + dblPeriodRate, iNumPeriod - 1) /
			(java.lang.Math.pow (1. + dblPeriodRate, iNumPeriod) - 1.);
	}
}
