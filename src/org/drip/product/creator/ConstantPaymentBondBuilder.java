
package org.drip.product.creator;

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
 * ConstantPaymentBondBuilder contains the Suite of Helper Functions for creating Constant Payments Based
 * 	Bonds.
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
