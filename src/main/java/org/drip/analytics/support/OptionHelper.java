
package org.drip.analytics.support;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>OptionHelper</i> contains the collection of the option valuation related utility functions used by the
 * modules.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/README.md">Assorted Support and Helper Utilities</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptionHelper {

	static class CrossVolatilityQuantoProduct extends org.drip.function.definition.R1ToR1 {
		org.drip.state.volatility.VolatilityCurve _vcForward = null;
		org.drip.state.volatility.VolatilityCurve _vcForwardToDiscount = null;
		org.drip.function.definition.R1ToR1 _r1r1ForwardForwardToDiscountCorrelation = null;

		CrossVolatilityQuantoProduct (
			final org.drip.state.volatility.VolatilityCurve vcForward,
			final org.drip.state.volatility.VolatilityCurve vcForwardToDiscount,
			final org.drip.function.definition.R1ToR1 r1r1ForwardForwardToDiscountCorrelation)
		{
			super (null);

			_vcForward = vcForward;
			_vcForwardToDiscount = vcForwardToDiscount;
			_r1r1ForwardForwardToDiscountCorrelation = r1r1ForwardForwardToDiscountCorrelation;
		}

		@Override public double evaluate (
			final double dblVariate)
			throws java.lang.Exception
		{
			return _vcForward.impliedVol ((int) dblVariate) * _vcForwardToDiscount.impliedVol ((int)
				dblVariate) * _r1r1ForwardForwardToDiscountCorrelation.evaluate (dblVariate);
		}
	}

	static class CrossVolatilityConvexityExponent extends org.drip.function.definition.R1ToR1 {
		org.drip.state.volatility.VolatilityCurve _vcForward = null;
		org.drip.state.volatility.VolatilityCurve _vcFunding = null;
		double _dblForwardShiftedLogNormalScaler = java.lang.Double.NaN;
		double _dblFundingShiftedLogNormalScaler = java.lang.Double.NaN;
		org.drip.function.definition.R1ToR1 _r1r1ForwardFundingCorrelation = null;

		CrossVolatilityConvexityExponent (
			final org.drip.state.volatility.VolatilityCurve vcForward,
			final double dblForwardShiftedLogNormalScaler,
			final org.drip.state.volatility.VolatilityCurve vcFunding,
			final double dblFundingShiftedLogNormalScaler,
			final org.drip.function.definition.R1ToR1 r1r1ForwardFundingCorrelation)
		{
			super (null);

			_vcForward = vcForward;
			_vcFunding = vcFunding;
			_r1r1ForwardFundingCorrelation = r1r1ForwardFundingCorrelation;
			_dblForwardShiftedLogNormalScaler = dblForwardShiftedLogNormalScaler;
			_dblFundingShiftedLogNormalScaler = dblFundingShiftedLogNormalScaler;
		}

		@Override public double evaluate (
			final double dblVariate)
			throws java.lang.Exception
		{
			double dblForwardShiftedLogNormalScaler = java.lang.Double.isNaN
				(_dblForwardShiftedLogNormalScaler) ? 1. : _dblForwardShiftedLogNormalScaler;
			double dblFundingShiftedLogNormalScaler = java.lang.Double.isNaN
				(_dblFundingShiftedLogNormalScaler) ? 1. : _dblFundingShiftedLogNormalScaler;

			return _r1r1ForwardFundingCorrelation.evaluate (dblVariate) * _vcFunding.impliedVol ((int)
				dblVariate) * _vcFunding.impliedVol ((int) dblVariate) * dblFundingShiftedLogNormalScaler *
					dblForwardShiftedLogNormalScaler - _vcForward.impliedVol ((int) dblVariate) *
						_vcForward.impliedVol ((int) dblVariate) * dblForwardShiftedLogNormalScaler *
							dblForwardShiftedLogNormalScaler;
		}
	}

	static class PeriodVariance extends org.drip.function.definition.R1ToR1 {
		org.drip.state.volatility.VolatilityCurve _vc = null;

		PeriodVariance (
			final org.drip.state.volatility.VolatilityCurve vc)
		{
			super (null);

			_vc = vc;
		}

		@Override public double evaluate (
			final double dblVariate)
			throws java.lang.Exception
		{
			return _vc.impliedVol ((int) dblVariate) * _vc.impliedVol ((int) dblVariate);
		}
	}

	/**
	 * Compute the Integrated Surface Variance given the corresponding volatility and the date spans
	 * 
	 * @param csqs Market Parameters
	 * @param strCustomMetricLabel Custom Metric Label
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Integrated Volatility Surface
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double IntegratedSurfaceVariance (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final java.lang.String strCustomMetricLabel,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (iEndDate < iStartDate)
			throw new java.lang.Exception ("OptionHelper::IntegratedSurfaceVariance => Invalid Inputs");

		if (null == csqs || null == strCustomMetricLabel || strCustomMetricLabel.isEmpty() || iEndDate ==
			iStartDate)
			return 0.;

		org.drip.state.volatility.VolatilityCurve vc = csqs.customVolatility
			(org.drip.state.identifier.CustomLabel.Standard (strCustomMetricLabel));

		return null != vc ? new PeriodVariance (vc).integrate (iStartDate, iEndDate) / 365.25 : 0.;
	}

	/**
	 * Compute the Integrated Surface Variance given the corresponding volatility and the date spans
	 * 
	 * @param vc The Volatility Curve
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Integrated Volatility Surface
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double IntegratedSurfaceVariance (
		final org.drip.state.volatility.VolatilityCurve vc,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (null == vc || iEndDate < iStartDate)
			throw new java.lang.Exception ("OptionHelper::IntegratedSurfaceVariance => Invalid Inputs");

		return null != vc ? new PeriodVariance (vc).integrate (iStartDate, iEndDate) / 365.25 : 0.;
	}

	/**
	 * Compute the Integrated Cross Volatility Quanto Product given the corresponding volatility and the
	 * 	correlation curves, and the date spans
	 * 
	 * @param vc1 Volatility Curve #1
	 * @param vc2 Volatility Curve #2
	 * @param r1r1Correlation Correlation Curve
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Integrated Cross Volatility Quanto Product
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double IntegratedCrossVolQuanto (
		final org.drip.state.volatility.VolatilityCurve vc1,
		final org.drip.state.volatility.VolatilityCurve vc2,
		final org.drip.function.definition.R1ToR1 r1r1Correlation,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (iEndDate < iStartDate)
			throw new java.lang.Exception ("OptionHelper::IntegratedCrossVolQuanto => Invalid Inputs");

		return null == vc1 || null == vc2 || null == r1r1Correlation ? 0. : new CrossVolatilityQuantoProduct
			(vc1, vc2, r1r1Correlation).integrate (iStartDate, iEndDate) / 365.25;
	}

	/**
	 * Compute the Integrated FRA Cross Volatility Convexity Exponent given the corresponding volatility and
	 * 	the correlation Curves, and the date spans
	 * 
	 * @param vcForward Volatility Term Structure of the Funding Rate
	 * @param vcFunding Volatility Term Structure of the Forward Rate
	 * @param r1r1ForwardFundingCorrelation Correlation Term Structure between the Forward and the Funding
	 *  States
	 * @param dblForwardShiftedLogNormalScaler Scaling for the Forward Log Normal Volatility
	 * @param dblFundingShiftedLogNormalScaler Scaling for the Funding Log Normal Volatility
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Integrated FRA Cross Volatility Convexity Exponent
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double IntegratedFRACrossVolConvexityExponent (
		final org.drip.state.volatility.VolatilityCurve vcForward,
		final org.drip.state.volatility.VolatilityCurve vcFunding,
		final org.drip.function.definition.R1ToR1 r1r1ForwardFundingCorrelation,
		final double dblForwardShiftedLogNormalScaler,
		final double dblFundingShiftedLogNormalScaler,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (iEndDate < iStartDate)
			throw new java.lang.Exception
				("OptionHelper::IntegratedFRACrossVolConvexityExponent => Invalid Inputs");

		return null == vcFunding || null == vcForward || null == r1r1ForwardFundingCorrelation ? 0. : new
			CrossVolatilityConvexityExponent (vcForward, dblForwardShiftedLogNormalScaler, vcFunding,
				dblFundingShiftedLogNormalScaler, r1r1ForwardFundingCorrelation).integrate (iStartDate,
					iEndDate) / 365.25;
	}

	/**
	 * Compute the Integrated Cross Volatility Quanto Product given the corresponding volatility and the
	 * 	correlation Curves and the date spans
	 * 
	 * @param csqs Market Parameters
	 * @param strCustomMetricLabel1 Custom Metric Label #1
	 * @param strCustomMetricLabel2 Custom Metric Label #2
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Integrated Cross Volatility Quanto Product
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double IntegratedCrossVolQuanto (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final java.lang.String strCustomMetricLabel1,
		final java.lang.String strCustomMetricLabel2,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (iEndDate < iStartDate)
			throw new java.lang.Exception ("OptionHelper::IntegratedCrossVolQuanto => Invalid Inputs");

		if (null == csqs || null == strCustomMetricLabel1 || strCustomMetricLabel1.isEmpty() || null ==
			strCustomMetricLabel2 || strCustomMetricLabel2.isEmpty() || iEndDate == iStartDate)
			return 0.;

		org.drip.state.identifier.CustomLabel cml1 =
			org.drip.state.identifier.CustomLabel.Standard (strCustomMetricLabel1);

		org.drip.state.identifier.CustomLabel cml2 =
			org.drip.state.identifier.CustomLabel.Standard (strCustomMetricLabel2);

		return null == cml1 || null == cml2 ? 0. : IntegratedCrossVolQuanto (csqs.customVolatility (cml1),
			csqs.customVolatility (cml2), csqs.customCustomCorrelation (cml1, cml2), iStartDate, iEndDate);
	}

	/**
	 * Compute the Multiplicative Cross Volatility Quanto Product given the corresponding volatility and the
	 * 	correlation Curves, and the date spans
	 * 
	 * @param csqs Market Parameters
	 * @param strCustomMetricLabel1 Custom Metric Label #1
	 * @param strCustomMetricLabel2 Custom Metric Label #2
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Multiplicative Cross Volatility Quanto Product
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double MultiplicativeCrossVolQuanto (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final java.lang.String strCustomMetricLabel1,
		final java.lang.String strCustomMetricLabel2,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		return java.lang.Math.exp (-1. * IntegratedCrossVolQuanto (csqs, strCustomMetricLabel1,
			strCustomMetricLabel2, iStartDate, iEndDate));
	}

	/**
	 * Compute the Integrated FRA Cross Volatility Convexity Adjuster given the corresponding volatility and
	 * 	the correlation Curves and the date spans
	 * 
	 * @param csqs Market Parameters
	 * @param forwardLabel Forward Latent State Label
	 * @param fundingLabel Funding Latent State Label
	 * @param dblForwardShiftedLogNormalScaler Scaling for the Forward Log Normal Volatility
	 * @param dblFundingShiftedLogNormalScaler Scaling for the Funding Log Normal Volatility
	 * @param iStartDate Evolution Start Date
	 * @param iEndDate Evolution End Date
	 * 
	 * @return The Integrated FRA Cross Volatility Convexity Adjuster
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public static final double IntegratedFRACrossVolConvexityAdjuster (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final double dblForwardShiftedLogNormalScaler,
		final double dblFundingShiftedLogNormalScaler,
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (iEndDate < iStartDate)
			throw new java.lang.Exception
				("OptionHelper::IntegratedFRACrossVolConvexityAdjuster => Invalid Inputs");

		return null == csqs || null == forwardLabel || null == fundingLabel || iEndDate == iStartDate ? 0. :
			IntegratedFRACrossVolConvexityExponent (csqs.fundingVolatility (fundingLabel),
				csqs.forwardVolatility (forwardLabel), csqs.forwardFundingCorrelation (forwardLabel,
					fundingLabel), dblFundingShiftedLogNormalScaler, dblForwardShiftedLogNormalScaler,
						iStartDate, iEndDate);
	}
}
