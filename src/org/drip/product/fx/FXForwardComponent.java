
package org.drip.product.fx;

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
 * FXForwardComponent contains the Standard FX forward Component contract details - the effective date, the
 *  maturity date, the currency pair and the product code. It also exports a calibrator that computes the
 *  forward points from the discount curve.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class FXForwardComponent extends org.drip.product.definition.CalibratableComponent {

	/**
	 * @author Lakshmi Krishnamurthy
	 *
	 * Calibrator for FXBasis - either bootstrapped or cumulative
	 */

	public class FXBasisCalibrator {
		private FXForwardComponent _fxfwd = null;

		// DC Basis Calibration Stochastic Control

		private int _iNumIterations = 100;
		private double _dblBasisIncr = 0.0001;
		private double _dblBasisDiffTol = 0.0001;

		private final double calcFXFwd (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
			final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
			final double dblFXSpot,
			final double dblBump,
			final boolean bBasisOnDenom)
			throws java.lang.Exception {
			if (bBasisOnDenom)
				return _fxfwd.fxForward (valParams, dcNum, (org.drip.state.discount.MergedDiscountForwardCurve)
					dcDenom.parallelShiftQuantificationMetric (dblBump), dblFXSpot, false);

			return _fxfwd.fxForward (valParams, (org.drip.state.discount.MergedDiscountForwardCurve)
				dcNum.parallelShiftQuantificationMetric (dblBump), dcDenom, dblFXSpot, false);
		}

		/**
		 * Constructor: Construct the basis calibrator from the FXForward parent
		 * 
		 * @param fxfwd FXForward parent
		 * 
		 * @throws java.lang.Exception Thrown if parent is invalid
		 */

		public FXBasisCalibrator (
			final FXForwardComponent fxfwd)
			throws java.lang.Exception
		{
			if (null == (_fxfwd = fxfwd))
				throw new java.lang.Exception ("FXForwardComponent::FXBasisCalibrator ctr: Invalid Inputs");
		}

		/**
		 * Calibrate the discount curve basis from FXForward using Newton-Raphson methodology
		 * 
		 * @param valParams ValuationParams
		 * @param dcNum Discount Curve for the Numerator
		 * @param dcDenom Discount Curve for the Denominator
		 * @param dblFXSpot FXSpot value
		 * @param dblMarketFXFwdPrice FXForward market value
		 * @param bBasisOnDenom True - Basis is set on the denominator
		 * 
		 * @return Calibrated DC basis
		 * 
		 * @throws java.lang.Exception Thrown if cannot calibrate
		 */

		public double calibrateDCBasisFromFwdPriceNR (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
			final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
			final double dblFXSpot,
			final double dblMarketFXFwdPrice,
			final boolean bBasisOnDenom)
			throws java.lang.Exception
		{
			if (null == valParams || null == dcNum || null == dcDenom ||
				!org.drip.quant.common.NumberUtil.IsValid (dblMarketFXFwdPrice) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblFXSpot))
				throw new java.lang.Exception
					("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => bad inputs");

			double dblFXFwdBase = _fxfwd.fxForward (valParams, dcNum, dcDenom, dblFXSpot, false);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFXFwdBase))
				throw new java.lang.Exception
					("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot imply FX Fwd Base!");

			double dblFXFwdBumped = calcFXFwd (valParams, dcNum, dcDenom, dblFXSpot, _dblBasisIncr,
				bBasisOnDenom);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFXFwdBumped))
				throw new java.lang.Exception
					("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot imply FX Fwd for " +
						_dblBasisIncr + " shift!");

			double dblDBasisDFXFwd = _dblBasisIncr / (dblFXFwdBumped - dblFXFwdBase);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblDBasisDFXFwd))
				throw new java.lang.Exception
					("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot calculate Fwd/Basis Slope for 0 basis!");

			double dblBasisPrev = 0.;
			double dblBasis = dblDBasisDFXFwd * (dblMarketFXFwdPrice - dblFXFwdBase);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblBasis))
				throw new java.lang.Exception ("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Got " +
					dblBasis + " for FlatSpread for " + _fxfwd.primaryCode() + " and price " + dblFXFwdBase);

			while (_dblBasisDiffTol < java.lang.Math.abs (dblBasis - dblBasisPrev)) {
				if (0 == --_iNumIterations)
					throw new java.lang.Exception
						("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot calib Basis for " +
							_fxfwd.primaryCode() + " and price " + dblMarketFXFwdPrice + " within limit!");

				if (!org.drip.quant.common.NumberUtil.IsValid (dblFXFwdBase = calcFXFwd (valParams, dcNum,
					dcDenom, dblFXSpot, dblBasisPrev = dblBasis, bBasisOnDenom)))
					throw new java.lang.Exception
						("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot imply FX Fwd for " +
							dblBasis + " shift!");

				if (!org.drip.quant.common.NumberUtil.IsValid (dblFXFwdBumped = calcFXFwd (valParams, dcNum,
					dcDenom, dblFXSpot, dblBasis + _dblBasisIncr, bBasisOnDenom)))
					throw new java.lang.Exception
						("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot imply FX Fwd for " +
							(dblBasis + _dblBasisIncr) + " shift!");

				if (!org.drip.quant.common.NumberUtil.IsValid (dblDBasisDFXFwd = _dblBasisIncr /
					(dblFXFwdBumped - dblFXFwdBase)))
					throw new java.lang.Exception
						("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Cannot calculate Fwd/Basis Slope for "
							+ (dblBasis + _dblBasisIncr) + " basis!");

				dblBasis = dblBasisPrev + dblDBasisDFXFwd * (dblMarketFXFwdPrice - dblFXFwdBase);

				if (!org.drip.quant.common.NumberUtil.IsValid (dblBasis))
					throw new java.lang.Exception
						("FXForwardComponent::calibrateDCBasisFromFwdPriceNR => Got " + dblBasis +
							" for FlatSpread for " + _fxfwd.primaryCode() + " and price " + dblFXFwdBase);
			}

			return dblBasis;
		}
	}

	private java.lang.String _strCode = "";
	private java.lang.String _strName = "";
	private double _dblNotional = java.lang.Double.NaN;
	private int _iMaturityDate = java.lang.Integer.MIN_VALUE;
	private int _iEffectiveDate = java.lang.Integer.MIN_VALUE;
	private org.drip.product.params.CurrencyPair _ccyPair = null;
	private org.drip.param.valuation.CashSettleParams _csp = null;

	/**
	 * Create an FXForwardComponent from the currency pair, the effective and the maturity dates
	 * 
	 * @param strName Name
	 * @param ccyPair Currency Pair
	 * @param iEffectiveDate Effective Date
	 * @param iMaturityDate Maturity Date
	 * @param dblNotional Notional
	 * @param csp Cash Settle Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public FXForwardComponent (
		final java.lang.String strName,
		final org.drip.product.params.CurrencyPair ccyPair,
		final int iEffectiveDate,
		final int iMaturityDate,
		final double dblNotional,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_ccyPair = ccyPair) ||
			(_iEffectiveDate = iEffectiveDate) >= (_iMaturityDate = iMaturityDate) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblNotional = dblNotional))
			throw new java.lang.Exception ("FXForwardComponent ctr: Invalid Inputs");

		_csp = csp;
	}

	/**
	 * Get the Currency Pair
	 * 
	 * @return CurrencyPair
	 */

	public org.drip.product.params.CurrencyPair currencyPair()
	{
		return _ccyPair;
	}

	/**
	 * Imply the FX Forward
	 * 
	 * @param valParams Valuation Parameters
	 * @param dcNum Discount Curve for the numerator
	 * @param dcDenom Discount Curve for the denominator
	 * @param dblFXSpot FXSpot
	 * @param bFwdAsPIP Calculate FXFwd as a PIP
	 * 
	 * @return Implied FXForward
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public double fxForward (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final double dblFXSpot,
		final boolean bFwdAsPIP)
		throws java.lang.Exception
	{
		if (null == valParams || null == dcNum || null == dcDenom ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFXSpot))
			throw new java.lang.Exception ("FXForwardComponent::fxForward => Invalid Inputs");

		int iCashPayDate = valParams.cashPayDate();

		double dblFXFwd = dblFXSpot * dcDenom.df (_iMaturityDate) * dcNum.df (iCashPayDate) / dcNum.df
			(_iMaturityDate) / dcDenom.df (iCashPayDate);

		return bFwdAsPIP ? (dblFXFwd - dblFXSpot) * _ccyPair.pipFactor() : dblFXFwd;
	}

	/**
	 * Calculate the basis to either the numerator or the denominator discount curve
	 * 
	 * @param valParams ValuationParams
	 * @param dcNum Discount Curve for the numerator
	 * @param dcDenom Discount Curve for the denominator
	 * @param dblFXSpot FXSpot
	 * @param dblMarketFXFwdPrice FXForward Market Value
	 * @param bBasisOnDenom Boolean indicating whether the basis is applied on the denominator (true) or
	 * 			denominator
	 * 
	 * @return Basis
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public double discountCurveBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final double dblFXSpot,
		final double dblMarketFXFwdPrice,
		final boolean bBasisOnDenom)
		throws java.lang.Exception
	{
		return new FXBasisCalibrator (this).calibrateDCBasisFromFwdPriceNR (valParams, dcNum, dcDenom,
			dblFXSpot, dblMarketFXFwdPrice, bBasisOnDenom);
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public java.lang.String primaryCode()
	{
		return _strCode;
	}

	@Override public void setPrimaryCode (
		final java.lang.String strCode)
	{
		_strCode = strCode;
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iEffectiveDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iMaturityDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		return maturityDate();
	}

	@Override public int freq()
	{
		return 1;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		return null;
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCouponCurrency = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		mapCouponCurrency.put (_strName, _ccyPair.denomCcy());

		return mapCouponCurrency;
	}

	@Override public java.lang.String payCurrency()
	{
		return _ccyPair.denomCcy();
	}

	@Override public java.lang.String principalCurrency()
	{
		return _ccyPair.denomCcy();
	}

	@Override public org.drip.state.identifier.CreditLabel creditLabel()
	{
		return null;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		return null;
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return org.drip.state.identifier.FundingLabel.Standard (payCurrency());
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return org.drip.state.identifier.GovvieLabel.Standard (payCurrency());
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel> mapFXLabel = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>();

		mapFXLabel.put ("DERIVED", org.drip.state.identifier.FXLabel.Standard (_ccyPair));

		return mapFXLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		return null;
	}

	@Override public double initialNotional()
		throws java.lang.Exception
	{
		return _dblNotional;
	}

	@Override public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		return _dblNotional;
	}

	@Override public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		return _dblNotional;
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return _csp;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || valParams.valueDate() > _iMaturityDate || null == csqs) return null;

		org.drip.state.identifier.FXLabel fxLabel = org.drip.state.identifier.FXLabel.Standard (_ccyPair);

		org.drip.state.fx.FXCurve fxCurve = csqs.fxState (fxLabel);

		if (null == fxCurve) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapRes = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		double dblFXSpot = java.lang.Double.NaN;
		double dblFXForward = java.lang.Double.NaN;

		try {
			dblFXSpot = fxCurve.fx (_iEffectiveDate);

			dblFXForward = fxCurve.fx (_iMaturityDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblPIP = (dblFXForward - dblFXSpot) * _ccyPair.pipFactor();

		mapRes.put ("FXForward", dblFXForward);

		mapRes.put ("FXForwardOutright", dblFXForward);

		mapRes.put ("FXForwardPIP", dblPIP);

		mapRes.put ("FXSpot", dblFXSpot);

		mapRes.put ("Outright", dblFXForward);

		mapRes.put ("PIP", dblPIP);

		mapRes.put ("PV", dblFXForward);

		org.drip.state.discount.MergedDiscountForwardCurve dcNum = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (_ccyPair.numCcy()));

		org.drip.state.discount.MergedDiscountForwardCurve dcDenom = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (_ccyPair.denomCcy()));

		if (null != dcNum && null != dcDenom) {
			try {
				double dblFXForwardOutright = fxForward (valParams, dcNum, dcDenom, dblFXSpot, false);

				double dblFXForwardPIP = fxForward (valParams, dcNum, dcDenom, dblFXSpot, true);

				mapRes.put ("DiscountCurveFXForward", dblFXForwardOutright);

				mapRes.put ("DiscountCurveFXForwardOutright", dblFXForwardOutright);

				mapRes.put ("DiscountCurveFXForwardPIP", dblFXForwardPIP);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return mapRes;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasures = new java.util.TreeSet<java.lang.String>();

		setstrMeasures.add ("DiscountCurveFXForward");

		setstrMeasures.add ("DiscountCurveFXForwardOutright");

		setstrMeasures.add ("DiscountCurveFXForwardPIP");

		setstrMeasures.add ("FXForward");

		setstrMeasures.add ("FXForwardOutright");

		setstrMeasures.add ("FXForwardPIP");

		setstrMeasures.add ("FXSpot");

		setstrMeasures.add ("Outright");

		setstrMeasures.add ("PIP");

		setstrMeasures.add ("PV");

		return setstrMeasures;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		org.drip.state.fx.FXCurve fxCurve = csqs.fxState (org.drip.state.identifier.FXLabel.Standard
			(_ccyPair));

		if (null == fxCurve) throw new java.lang.Exception ("FXForwardComponent::pv => Invalid Inputs");

		return fxCurve.fx (_iMaturityDate);
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> calibMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return new org.drip.product.calib.FXForwardQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs || !(pqs instanceof org.drip.product.calib.FXForwardQuoteSet))
			return null;

		int iMaturityDate = maturityDate().julian();

		if (valParams.valueDate() > iMaturityDate) return null;

		double dblOutright = java.lang.Double.NaN;
		org.drip.product.calib.FXForwardQuoteSet fxqs = (org.drip.product.calib.FXForwardQuoteSet) pqs;

		if (!fxqs.containsOutright()) return null;

		try {
			dblOutright = fxqs.outright();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (!prwc.addPredictorResponseWeight (iMaturityDate, 1.)) return null;

		if (!prwc.addDResponseWeightDManifestMeasure ("Outright", iMaturityDate, 1.)) return null;

		if (!prwc.updateValue (dblOutright)) return null;

		if (!prwc.updateDValueDManifestMeasure ("Outright", 1.)) return null;

		return prwc;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint govviePRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strManifestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}
}
