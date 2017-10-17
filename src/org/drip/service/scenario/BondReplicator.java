
package org.drip.service.scenario;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * BondReplicator generates a Target Set of Sensitivity and Relative Value Runs.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BondReplicator {
	private int _iSettleLag = -1;
	private double[] _adblGovvieQuote = null;
	private double[] _adblCreditQuote = null;
	private double[] _adblDepositQuote = null;
	private double[] _adblFuturesQuote = null;
	private double[] _adblFixFloatQuote = null;
	private double _dblFX = java.lang.Double.NaN;
	private java.lang.String _strGovvieCode = "";
	private boolean _bMarketPriceCreditMetrics = false;
	private java.lang.String[] _astrCreditTenor = null;
	private java.lang.String[] _astrGovvieTenor = null;
	private double _dblTenorBump = java.lang.Double.NaN;
	private java.lang.String[] _astrDepositTenor = null;
	private java.lang.String[] _astrFixFloatTenor = null;
	private double _dblIssuePrice = java.lang.Double.NaN;
	private double _dblIssueAmount = java.lang.Double.NaN;
	private double _dblZSpreadBump = java.lang.Double.NaN;
	private double _dblCurrentPrice = java.lang.Double.NaN;
	private double _dblCustomYieldBump = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtSpot = null;
	private org.drip.product.credit.BondComponent _bond = null;
	private double _dblCustomCreditBasisBump = java.lang.Double.NaN;
	private double _dblSpreadDurationMultiplier = java.lang.Double.NaN;

	private double _dblResetRate = java.lang.Double.NaN;
	private int _iResetDate = java.lang.Integer.MIN_VALUE;
	private org.drip.analytics.date.JulianDate _dtSettle = null;
	private org.drip.param.valuation.ValuationParams _valParams = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcCreditBase = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcCredit01Up = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcFundingBase = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcFunding01Up = null;
	private org.drip.param.market.CurveSurfaceQuoteContainer _csqcFundingEuroDollar = null;
	private java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		_mapCSQCCredit = null;

	private java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		_mapCSQCGovvieUp = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

	private java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		_mapCSQCGovvieDown = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

	private java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		_mapCSQCFundingUp = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

	private java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		_mapCSQCFundingDown = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

	/**
	 * Generate a Standard BondReplicator Instance
	 * 
	 * @param dblCurrentPrice Current Price
	 * @param dblIssuePrice Issue Price
	 * @param dblIssueAmount Issue Amount
	 * @param dtSpot Spot Date
	 * @param astrDepositTenor Array of Deposit Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param adblFuturesQuote Array of Futures Quotes
	 * @param astrFixFloatTenor Array of Fix-Float Tenors
	 * @param adblFixFloatQuote Array of Fix-Float Quotes
	 * @param dblSpreadBump Yield/Spread Bump
	 * @param dblSpreadDurationMultiplier Spread Duration Multiplier
	 * @param strGovvieCode Govvie Code
	 * @param astrGovvieTenor Array of Govvie Tenor
	 * @param adblGovvieQuote Array of Govvie Quotes
	 * @param astrCreditTenor Array of Credit Tenors
	 * @param adblCreditQuote Array of Credit Quotes
	 * @param dblFX FX Rate Applicable
	 * @param dblResetRate Reset Rate Applicable
	 * @param iSettleLag Settlement Lag
	 * @param bond Bond Component Instance
	 * 
	 * @return The BondReplicator Instance
	 */

	public static final BondReplicator Standard (
		final double dblCurrentPrice,
		final double dblIssuePrice,
		final double dblIssueAmount,
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrDepositTenor,
		final double[] adblDepositQuote,
		final double[] adblFuturesQuote,
		final java.lang.String[] astrFixFloatTenor,
		final double[] adblFixFloatQuote,
		final double dblSpreadBump,
		final double dblSpreadDurationMultiplier,
		final java.lang.String strGovvieCode,
		final java.lang.String[] astrGovvieTenor,
		final double[] adblGovvieQuote,
		final java.lang.String[] astrCreditTenor,
		final double[] adblCreditQuote,
		final double dblFX,
		final double dblResetRate,
		final int iSettleLag,
		final org.drip.product.credit.BondComponent bond)
		throws java.lang.Exception
	{
		try {
			return new BondReplicator (
				dblCurrentPrice,
				dblIssuePrice,
				dblIssueAmount,
				dtSpot,
				astrDepositTenor,
				adblDepositQuote,
				adblFuturesQuote,
				astrFixFloatTenor,
				adblFixFloatQuote,
				dblSpreadBump,
				dblSpreadBump,
				dblSpreadBump,
				dblSpreadBump,
				dblSpreadDurationMultiplier,
				strGovvieCode,
				astrGovvieTenor,
				adblGovvieQuote,
				true,
				astrCreditTenor,
				adblCreditQuote,
				dblFX,
				dblResetRate,
				iSettleLag,
				bond
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BondReplicator Constructor
	 * 
	 * @param dblCurrentPrice Current Price
	 * @param dblIssuePrice Issue Price
	 * @param dblIssueAmount Issue Amount
	 * @param dtSpot Spot Date
	 * @param astrDepositTenor Array of Deposit Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param adblFuturesQuote Array of Futures Quotes
	 * @param astrFixFloatTenor Array of Fix-Float Tenors
	 * @param adblFixFloatQuote Array of Fix-Float Quotes
	 * @param dblCustomYieldBump Custom Yield Bump
	 * @param dblCustomCreditBasisBump Custom Credit Basis Bump
	 * @param dblZSpreadBump Z Spread Bump
	 * @param dblTenorBump Tenor Bump
	 * @param dblSpreadDurationMultiplier Spread Duration Multiplier
	 * @param strGovvieCode Govvie Code
	 * @param astrGovvieTenor Array of Govvie Tenor
	 * @param adblGovvieQuote Array of Govvie Quotes
	 * @param bMarketPriceCreditMetrics Generate the Credit Metrics from the Market Price
	 * @param astrCreditTenor Array of Credit Tenors
	 * @param adblCreditQuote Array of Credit Quotes
	 * @param dblFX FX Rate Applicable
	 * @param dblResetRate Reset Rate Applicable
	 * @param iSettleLag Settlement Lag
	 * @param bond Bond Component Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BondReplicator (
		final double dblCurrentPrice,
		final double dblIssuePrice,
		final double dblIssueAmount,
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrDepositTenor,
		final double[] adblDepositQuote,
		final double[] adblFuturesQuote,
		final java.lang.String[] astrFixFloatTenor,
		final double[] adblFixFloatQuote,
		final double dblCustomYieldBump,
		final double dblCustomCreditBasisBump,
		final double dblZSpreadBump,
		final double dblTenorBump,
		final double dblSpreadDurationMultiplier,
		final java.lang.String strGovvieCode,
		final java.lang.String[] astrGovvieTenor,
		final double[] adblGovvieQuote,
		final boolean bMarketPriceCreditMetrics,
		final java.lang.String[] astrCreditTenor,
		final double[] adblCreditQuote,
		final double dblFX,
		final double dblResetRate,
		final int iSettleLag,
		final org.drip.product.credit.BondComponent bond)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCurrentPrice = dblCurrentPrice) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblIssuePrice = dblIssuePrice) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblIssueAmount = dblIssueAmount) || null ==
					(_dtSpot = dtSpot) || !org.drip.quant.common.NumberUtil.IsValid (_dblFX = dblFX) || 0. >=
						_dblFX || 0 > (_iSettleLag = iSettleLag) || null == (_bond = bond))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		_dblResetRate = dblResetRate;
		_dblTenorBump = dblTenorBump;
		_strGovvieCode = strGovvieCode;
		_dblZSpreadBump = dblZSpreadBump;
		_adblCreditQuote = adblCreditQuote;
		_astrCreditTenor = astrCreditTenor;
		_adblGovvieQuote = adblGovvieQuote;
		_astrGovvieTenor = astrGovvieTenor;
		_adblDepositQuote = adblDepositQuote;
		_astrDepositTenor = astrDepositTenor;
		_adblFuturesQuote = adblFuturesQuote;
		_adblFixFloatQuote = adblFixFloatQuote;
		_astrFixFloatTenor = astrFixFloatTenor;
		_dblCustomYieldBump = dblCustomYieldBump;
		_dblCustomCreditBasisBump = dblCustomCreditBasisBump;
		_bMarketPriceCreditMetrics = bMarketPriceCreditMetrics;
		_dblSpreadDurationMultiplier = dblSpreadDurationMultiplier;

		java.lang.String strCurrency = _bond.currency();

		if (null == (_dtSettle = _dtSpot.addBusDays (_iSettleLag, strCurrency)))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		_valParams = new org.drip.param.valuation.ValuationParams (_dtSpot, _dtSettle, strCurrency);

		org.drip.state.discount.MergedDiscountForwardCurve mdfc =
			org.drip.service.template.LatentMarketStateBuilder.ShapePreservingFundingCurve (_dtSpot,
				strCurrency, _astrDepositTenor, _adblDepositQuote, "ForwardRate", _adblFuturesQuote,
					"ForwardRate", _astrFixFloatTenor, _adblFixFloatQuote, "SwapRate");

		if (null == mdfc) throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		org.drip.analytics.date.JulianDate[] adtSpot = org.drip.analytics.support.Helper.SpotDateArray
			(_dtSpot, null == _astrGovvieTenor ? 0 : _astrGovvieTenor.length);

		org.drip.analytics.date.JulianDate[] adtMaturity = org.drip.analytics.support.Helper.FromTenor
			(_dtSpot, _astrGovvieTenor);

		org.drip.state.govvie.GovvieCurve gc = org.drip.service.template.LatentMarketStateBuilder.GovvieCurve
			(_strGovvieCode, _dtSpot, adtSpot, adtMaturity, _adblGovvieQuote, _adblGovvieQuote, "Yield",
				org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

		if (null == gc) throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		if (_bond.isFloater()) {
			org.drip.analytics.cashflow.CompositeFloatingPeriod cfp =
				(org.drip.analytics.cashflow.CompositeFloatingPeriod) _bond.stream().containingPeriod
					(_dtSpot.julian());

			_iResetDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod) (cfp.periods().get
				(0))).referenceIndexPeriod().fixingDate();
		}

		if (null == (_csqcFundingBase = org.drip.param.creator.MarketParamsBuilder.Create (mdfc, gc, null,
			null, null, null, null)))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		org.drip.state.identifier.ForwardLabel fl = _bond.isFloater() ? _bond.floaterSetting().fri() : null;

		if (_bond.isFloater() && !_csqcFundingBase.setFixing (_iResetDate, fl, _dblResetRate))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		if (null == (_csqcFunding01Up = org.drip.param.creator.MarketParamsBuilder.Create
			(org.drip.service.template.LatentMarketStateBuilder.ShapePreservingFundingCurve (_dtSpot,
				strCurrency, _astrDepositTenor, org.drip.analytics.support.Helper.ParallelNodeBump
					(_adblDepositQuote, 0.0001 * _dblTenorBump), "ForwardRate",
						org.drip.analytics.support.Helper.ParallelNodeBump (_adblFuturesQuote, 0.0001 *
							_dblTenorBump), "ForwardRate", _astrFixFloatTenor,
								org.drip.analytics.support.Helper.ParallelNodeBump (_adblFixFloatQuote,
									0.0001 * _dblTenorBump), "SwapRate"), gc, null, null, null, null, null)))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		if (_bond.isFloater() && !_csqcFunding01Up.setFixing (_iResetDate, fl, _dblResetRate + 0.0001 *
			_dblTenorBump))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		if (null == (_csqcFundingEuroDollar = org.drip.param.creator.MarketParamsBuilder.Create
			(org.drip.service.template.LatentMarketStateBuilder.ShapePreservingFundingCurve (_dtSpot,
				strCurrency, _astrDepositTenor, _adblDepositQuote, "ForwardRate", _adblFuturesQuote,
					"ForwardRate", null, null, "SwapRate"), gc, null, null, null, null, null)))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		if (_bond.isFloater() && !_csqcFundingEuroDollar.setFixing (_iResetDate, fl, _dblResetRate))
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		java.util.Map<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve> mapTenorFundingUp
			= org.drip.service.template.LatentMarketStateBuilder.BumpedFundingCurve (_dtSpot, strCurrency,
				_astrDepositTenor, _adblDepositQuote, "ForwardRate", _adblFuturesQuote, "ForwardRate",
					_astrFixFloatTenor, _adblFixFloatQuote, "SwapRate",
						org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING, 0.0001 *
							_dblTenorBump, false);

		java.util.Map<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve>
			mapTenorFundingDown = org.drip.service.template.LatentMarketStateBuilder.BumpedFundingCurve
				(_dtSpot, strCurrency, _astrDepositTenor, _adblDepositQuote, "ForwardRate",
					_adblFuturesQuote, "ForwardRate", _astrFixFloatTenor, _adblFixFloatQuote, "SwapRate",
						org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING, -0.0001 *
							_dblTenorBump, false);

		if (null == mapTenorFundingUp || null == mapTenorFundingDown)
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		for (java.util.Map.Entry<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve>
			meTenorFundingUp : mapTenorFundingUp.entrySet()) {
			java.lang.String strKey = meTenorFundingUp.getKey();

			org.drip.param.market.CurveSurfaceQuoteContainer csqcFundingTenorUp =
				org.drip.param.creator.MarketParamsBuilder.Create (meTenorFundingUp.getValue(), gc, null,
					null, null, null, null);

			org.drip.param.market.CurveSurfaceQuoteContainer csqcFundingTenorDown =
				org.drip.param.creator.MarketParamsBuilder.Create (mapTenorFundingDown.get (strKey), gc,
					null, null, null, null, null);

			if (null == csqcFundingTenorUp || null == csqcFundingTenorDown)
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			_mapCSQCFundingUp.put (strKey, csqcFundingTenorUp);

			_mapCSQCFundingDown.put (strKey, csqcFundingTenorDown);

			if (_bond.isFloater() && (!csqcFundingTenorUp.setFixing (_iResetDate, fl, _dblResetRate + 0.0001
				* _dblTenorBump) || !csqcFundingTenorDown.setFixing (_iResetDate, fl, _dblResetRate - 0.0001
					* _dblTenorBump)))
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		java.util.Map<java.lang.String, org.drip.state.govvie.GovvieCurve> mapTenorGovvieUp =
			org.drip.service.template.LatentMarketStateBuilder.BumpedGovvieCurve (_strGovvieCode, _dtSpot,
				adtSpot, adtMaturity,_adblGovvieQuote, _adblGovvieQuote, "Yield",
					org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING, 0.0001 *
						_dblTenorBump, false);

		java.util.Map<java.lang.String, org.drip.state.govvie.GovvieCurve> mapTenorGovvieDown =
			org.drip.service.template.LatentMarketStateBuilder.BumpedGovvieCurve (_strGovvieCode, _dtSpot,
				adtSpot, adtMaturity,_adblGovvieQuote, _adblGovvieQuote, "Yield",
					org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING, -0.0001 *
						_dblTenorBump, false);

		if (null == mapTenorGovvieUp || null == mapTenorGovvieDown)
			throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

		for (java.util.Map.Entry<java.lang.String, org.drip.state.govvie.GovvieCurve> meTenorGovvieUp :
			mapTenorGovvieUp.entrySet()) {
			java.lang.String strKey = meTenorGovvieUp.getKey();

			org.drip.param.market.CurveSurfaceQuoteContainer csqcGovvieTenorUp =
				org.drip.param.creator.MarketParamsBuilder.Create (mdfc, meTenorGovvieUp.getValue(), null,
					null, null, null, null);

			org.drip.param.market.CurveSurfaceQuoteContainer csqcGovvieTenorDown =
				org.drip.param.creator.MarketParamsBuilder.Create (mdfc, mapTenorGovvieDown.get (strKey),
					null, null, null, null, null);

			if (null == csqcGovvieTenorUp || null == csqcGovvieTenorDown)
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			_mapCSQCGovvieUp.put (strKey, csqcGovvieTenorUp);

			_mapCSQCGovvieDown.put (strKey, csqcGovvieTenorDown);

			if (_bond.isFloater() && (!csqcGovvieTenorUp.setFixing (_iResetDate, fl, _dblResetRate) ||
				!csqcGovvieTenorDown.setFixing (_iResetDate, fl, _dblResetRate)))
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		org.drip.state.identifier.CreditLabel cl = _bond.creditLabel();

		java.lang.String strReferenceEntity = null != cl ? cl.referenceEntity() : null;

		if (null == strReferenceEntity) return;

		if (!_bMarketPriceCreditMetrics) {
			if (null == (_csqcCreditBase = org.drip.param.creator.MarketParamsBuilder.Create (mdfc, gc,
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (_dtSpot, strReferenceEntity,
					_astrCreditTenor, _adblCreditQuote, _adblCreditQuote, "FairPremium", mdfc), null, null,
						null, null)))
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			if (_bond.isFloater() && !_csqcCreditBase.setFixing (_iResetDate, fl, _dblResetRate))
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			if (null == (_csqcCredit01Up = org.drip.param.creator.MarketParamsBuilder.Create (mdfc, gc,
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (_dtSpot, strReferenceEntity,
					_astrCreditTenor, _adblCreditQuote, org.drip.analytics.support.Helper.ParallelNodeBump
						(_adblCreditQuote, _dblTenorBump), "FairPremium", mdfc), null, null, null, null)))
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			if (_bond.isFloater() && !_csqcCredit01Up.setFixing (_iResetDate, fl, _dblResetRate))
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			java.util.Map<java.lang.String, org.drip.state.credit.CreditCurve> mapTenorCredit =
				org.drip.service.template.LatentMarketStateBuilder.BumpedCreditCurve (_dtSpot,
					strReferenceEntity, _astrCreditTenor, _adblCreditQuote, _adblCreditQuote, "FairPremium",
						mdfc, _dblTenorBump, false);

			if (null == mapTenorCredit)
				throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

			_mapCSQCCredit = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

			for (java.util.Map.Entry<java.lang.String, org.drip.state.credit.CreditCurve> meTenorCredit :
				mapTenorCredit.entrySet()) {
				org.drip.param.market.CurveSurfaceQuoteContainer csqcCreditTenor =
					org.drip.param.creator.MarketParamsBuilder.Create (mdfc, gc, meTenorCredit.getValue(),
						null, null, null, null);

				if (null == csqcCreditTenor)
					throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");

				_mapCSQCCredit.put (meTenorCredit.getKey(), csqcCreditTenor);

				if (_bond.isFloater() && !csqcCreditTenor.setFixing (_iResetDate, fl, _dblResetRate))
					throw new java.lang.Exception ("BondReplicator Constructor => Invalid Inputs");
			}
		} else {
			org.drip.state.credit.CreditCurve ccBase =
				org.drip.state.creator.ScenarioCreditCurveBuilder.Custom (strReferenceEntity, _dtSpot, new
					org.drip.product.definition.CalibratableComponent[] {bond}, mdfc, new double[]
						{_dblCurrentPrice}, new java.lang.String[] {"FairPrice"}, 0.40, false);

			if (null == ccBase || null == (_csqcCreditBase =
				org.drip.param.creator.MarketParamsBuilder.Create (mdfc, gc, ccBase, null, null, null,
					null)))
				return;

			_csqcCredit01Up = org.drip.param.creator.MarketParamsBuilder.Create (mdfc, gc,
				org.drip.state.creator.ScenarioCreditCurveBuilder.FlatHazard (_dtSpot.julian(),
					strReferenceEntity, strCurrency, ccBase.hazard (bond.maturityDate()) + 0.0001, 0.40),
						null, null, null, null);
		}
	}

	/**
	 * Retrieve the Bond Current Market Price
	 * 
	 * @return The Bond Current Market Price
	 */

	public double currentPrice()
	{
		return _dblCurrentPrice;
	}

	/**
	 * Retrieve the Bond Issue Price
	 * 
	 * @return The Bond Issue Price
	 */

	public double issuePrice()
	{
		return _dblIssuePrice;
	}

	/**
	 * Retrieve the Bond Issue Amount
	 * 
	 * @return The Bond Issue Amount
	 */

	public double issueAmount()
	{
		return _dblIssueAmount;
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public org.drip.analytics.date.JulianDate spotDate()
	{
		return _dtSpot;
	}

	/**
	 * Retrieve the Array of Deposit Instrument Maturity Tenors
	 * 
	 * @return The Array of Deposit Instrument Maturity Tenors
	 */

	public java.lang.String[] depositTenor()
	{
		return _astrDepositTenor;
	}

	/**
	 * Retrieve the Array of Deposit Instrument Quotes
	 * 
	 * @return The Array of Deposit Instrument Quotes
	 */

	public double[] depositQuote()
	{
		return _adblDepositQuote;
	}

	/**
	 * Retrieve the Array of Futures Instrument Quotes
	 * 
	 * @return The Array of Futures Instrument Quotes
	 */

	public double[] futuresQuote()
	{
		return _adblFuturesQuote;
	}

	/**
	 * Retrieve the Array of Fix-Float IRS Instrument Maturity Tenors
	 * 
	 * @return The Array of Fix-Float IRS Instrument Maturity Tenors
	 */

	public java.lang.String[] fixFloatTenor()
	{
		return _astrFixFloatTenor;
	}

	/**
	 * Retrieve the Array of Fix-Float IRS Instrument Quotes
	 * 
	 * @return The Array of Fix-Float IRS Instrument Quotes
	 */

	public double[] fixFloatQuote()
	{
		return _adblFixFloatQuote;
	}

	/**
	 * Retrieve the Custom Yield Bump
	 * 
	 * @return The Custom Yield Bump
	 */

	public double customYieldBump()
	{
		return _dblCustomYieldBump;
	}

	/**
	 * Retrieve the Custom Credit Basis Bump
	 * 
	 * @return The Custom Credit Basis Bump
	 */

	public double customCreditBasisBump()
	{
		return _dblCustomCreditBasisBump;
	}

	/**
	 * Retrieve the Z Spread Bump
	 * 
	 * @return The Z Spread Bump
	 */

	public double zSpreadBump()
	{
		return _dblZSpreadBump;
	}

	/**
	 * Retrieve the Tenor Quote Bump
	 * 
	 * @return The Tenor Quote Bump
	 */

	public double tenorBump()
	{
		return _dblTenorBump;
	}

	/**
	 * Retrieve the Spread Duration Multiplier
	 * 
	 * @return The Spread Duration Multiplier
	 */

	public double spreadDurationMultiplier()
	{
		return _dblSpreadDurationMultiplier;
	}

	/**
	 * Retrieve the Govvie Code
	 * 
	 * @return The Govvie Code
	 */

	public java.lang.String govvieCode()
	{
		return _strGovvieCode;
	}

	/**
	 * Retrieve the Array of Govvie Instrument Maturity Tenors
	 * 
	 * @return The Array of Govvie Instrument Maturity Tenors
	 */

	public java.lang.String[] govvieTenor()
	{
		return _astrGovvieTenor;
	}

	/**
	 * Retrieve the Array of Govvie Yield Quotes
	 * 
	 * @return The Array of Govvie Yield Quotes
	 */

	public double[] govvieQuote()
	{
		return _adblGovvieQuote;
	}

	/**
	 * Retrieve the Flag that indicates the Generation the Credit Metrics from the Market Price
	 * 
	 * @return TRUE - Generate the Credit Metrics from the Market Price
	 */

	public boolean creditMetricsFromMarketPrice()
	{
		return _bMarketPriceCreditMetrics;
	}

	/**
	 * Retrieve the Array of CDS Instrument Maturity Tenors
	 * 
	 * @return The Array of CDS Instrument Maturity Tenors
	 */

	public java.lang.String[] creditTenor()
	{
		return _astrCreditTenor;
	}

	/**
	 * Retrieve the Array of CDS Quotes
	 * 
	 * @return The Array of CDS Quotes
	 */

	public double[] creditQuote()
	{
		return _adblCreditQuote;
	}

	/**
	 * Retrieve the FX Rate
	 * 
	 * @return The FX Rate
	 */

	public double fx()
	{
		return _dblFX;
	}

	/**
	 * Retrieve the Settle Lag
	 * 
	 * @return The Settle Lag
	 */

	public double settleLag()
	{
		return _iSettleLag;
	}

	/**
	 * Retrieve the Bond Component Instance
	 * 
	 * @return The Bond Component Instance
	 */

	public org.drip.product.credit.BondComponent bond()
	{
		return _bond;
	}

	/**
	 * Retrieve the Settle Date
	 * 
	 * @return The Settle Date
	 */

	public org.drip.analytics.date.JulianDate settleDate()
	{
		return _dtSettle;
	}

	/**
	 * Retrieve the Valuation Parameters
	 * 
	 * @return The Valuation Parameters
	 */

	public org.drip.param.valuation.ValuationParams valuationParameters()
	{
		return _valParams;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Up Instances of the Funding Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Up Instances of the Funding Curve CSQC
	 */

	public java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		fundingTenorCSQCUp()
	{
		return _mapCSQCFundingUp;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Down Instances of the Funding Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Down Instances of the Funding Curve CSQC
	 */

	public java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		fundingTenorCSQCDown()
	{
		return _mapCSQCFundingDown;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Up Instances of the Govvie Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Up Instances of the Govvie Curve CSQC
	 */

	public java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		govvieTenorCSQCUp()
	{
		return _mapCSQCGovvieUp;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Down Instances of the Govvie Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Down Instances of the Govvie Curve CSQC
	 */

	public java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		govvieTenorCSQCDown()
	{
		return _mapCSQCGovvieDown;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Instances of the Credit Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Instances of the Credit Curve CSQC
	 */

	public java.util.Map<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
		creditTenorCSQC()
	{
		return _mapCSQCCredit;
	}

	/**
	 * Retrieve the CSQC built out of the Base Funding Curve
	 * 
	 * @return The CSQC built out of the Base Funding Curve
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer fundingBaseCSQC()
	{
		return _csqcFundingBase;
	}

	/**
	 * Retrieve the CSQC built out of the Base Euro Dollar Curve
	 * 
	 * @return The CSQC built out of the Base Euro Dollar Curve
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer fundingEuroDollarCSQC()
	{
		return _csqcFundingEuroDollar;
	}

	/**
	 * Retrieve the CSQC built out of the Base Credit Curve
	 * 
	 * @return The CSQC built out of the Base Credit Curve
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer creditBaseCSQC()
	{
		return _csqcCreditBase;
	}

	/**
	 * Retrieve the CSQC built out of the Funding Curve Flat Bumped 1 bp
	 * 
	 * @return The CSQC built out of the Funding Curve Flat Bumped 1 bp
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer funding01UpCSQC()
	{
		return _csqcFunding01Up;
	}

	/**
	 * Retrieve the CSQC built out of the Credit Curve Flat Bumped 1 bp
	 * 
	 * @return The CSQC built out of the Credit Curve Flat Bumped 1 bp
	 */

	public org.drip.param.market.CurveSurfaceQuoteContainer credit01UpCSQC()
	{
		return _csqcCredit01Up;
	}

	/**
	 * Retrieve the Reset Date
	 * 
	 * @return The Reset Date
	 */

	public int resetDate()
	{
		return _iResetDate;
	}

	/**
	 * Retrieve the Reset Rate
	 * 
	 * @return The Reset Rate
	 */

	public double resetRate()
	{
		return _dblResetRate;
	}

	/**
	 * Generate an Instance of a Replication Run
	 * 
	 * @return Instance of a Replication Run
	 */

	public org.drip.service.scenario.BondReplicationRun generateRun()
	{
		int iMaturityDate = _bond.maturityDate().julian();

		double dblNextPutFactor = 1.;
		double dblNextCallFactor = 1.;
		int iNextPutDate = iMaturityDate;
		int iNextCallDate = iMaturityDate;
		double dblCV01 = java.lang.Double.NaN;
		double dblAccrued = java.lang.Double.NaN;
		double dblYield01 = java.lang.Double.NaN;
		double dblNominalYield = java.lang.Double.NaN;
		double dblBEYToMaturity = java.lang.Double.NaN;
		double dblOASToExercise = java.lang.Double.NaN;
		double dblOASToMaturity = java.lang.Double.NaN;
		double dblWALToExercise = java.lang.Double.NaN;
		double dblSpreadDuration = java.lang.Double.NaN;
		double dblYieldToMaturity = java.lang.Double.NaN;
		double dblParOASToExercise = java.lang.Double.NaN;
		double dblESpreadToExercise = java.lang.Double.NaN;
		double dblISpreadToExercise = java.lang.Double.NaN;
		double dblJSpreadToExercise = java.lang.Double.NaN;
		double dblNSpreadToExercise = java.lang.Double.NaN;
		double dblZSpreadToExercise = java.lang.Double.NaN;
		double dblZSpreadToMaturity = java.lang.Double.NaN;
		double dblBondBasisToExercise = java.lang.Double.NaN;
		double dblBondBasisToMaturity = java.lang.Double.NaN;
		double dblConvexityToExercise = java.lang.Double.NaN;
		double dblWALCreditToExercise = java.lang.Double.NaN;
		double dblParZSpreadToExercise = java.lang.Double.NaN;
		double dblCreditBasisToExercise = java.lang.Double.NaN;
		double dblWALLossOnlyToExercise = java.lang.Double.NaN;
		double dblYieldFromPriceNextPut = java.lang.Double.NaN;
		double dblYieldFromPriceNextCall = java.lang.Double.NaN;
		double dblWALCouponOnlyToExercise = java.lang.Double.NaN;
		double dblDiscountMarginToExercise = java.lang.Double.NaN;
		double dblParCreditBasisToExercise = java.lang.Double.NaN;
		double dblYieldToMaturityFwdCoupon = java.lang.Double.NaN;
		double dblEffectiveDurationAdjusted = java.lang.Double.NaN;
		double dblMacaulayDurationToMaturity = java.lang.Double.NaN;
		double dblModifiedDurationToExercise = java.lang.Double.NaN;
		double dblModifiedDurationToMaturity = java.lang.Double.NaN;
		double dblWALPrincipalOnlyToExercise = java.lang.Double.NaN;

		int iSpotDate = _dtSpot.julian();

		java.lang.String strCurrency = _bond.currency();

		org.drip.product.params.EmbeddedOptionSchedule eosPut = _bond.putSchedule();

		org.drip.product.params.EmbeddedOptionSchedule eosCall = _bond.callSchedule();

		org.drip.service.scenario.BondReplicationRun arr = new
			org.drip.service.scenario.BondReplicationRun();

		org.drip.param.valuation.WorkoutInfo wi = _bond.exerciseYieldFromPrice (_valParams, _csqcFundingBase,
			null, _dblCurrentPrice);

		if (null == wi) return null;

		int iWorkoutDate = wi.date();

		double dblWorkoutFactor = wi.factor();

		double dblYieldToExercise = wi.yield();

		java.util.Map<java.lang.String, java.lang.Double> mapLIBORKRD = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> mapLIBORKPRD = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> mapGovvieKRD = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> mapGovvieKPRD = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> mapCreditKRD = null;
		java.util.Map<java.lang.String, java.lang.Double> mapCreditKPRD = null;

		try {
			if (null != eosCall) {
				iNextCallDate = eosCall.nextDate (iSpotDate);

				dblNextCallFactor = eosCall.nextFactor (iSpotDate);
			}

			if (null != eosPut) {
				iNextPutDate = eosPut.nextDate (iSpotDate);

				dblNextPutFactor = eosPut.nextFactor (iSpotDate);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblAccrued = _bond.accrued (_dtSettle.julian(), _csqcFundingBase);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldToMaturity = _bond.yieldFromPrice (_valParams, _csqcFundingBase, null, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblBEYToMaturity = _bond.yieldFromPrice (_valParams, _csqcFundingBase,
				org.drip.param.valuation.ValuationCustomizationParams.BondEquivalent (strCurrency),
					_dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldToMaturityFwdCoupon = _bond.yieldFromPrice (_valParams, _csqcFundingBase, new
				org.drip.param.valuation.ValuationCustomizationParams (_bond.couponDC(), _bond.freq(), false,
					null, strCurrency, false, true), _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldFromPriceNextCall = _bond.yieldFromPrice (_valParams, _csqcFundingBase, null,
				iNextCallDate, dblNextCallFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldFromPriceNextPut = _bond.yieldFromPrice (_valParams, _csqcFundingBase, null,
				iNextPutDate, dblNextPutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblNominalYield = _bond.yieldFromPrice (_valParams, _csqcFundingBase, null, _dblIssuePrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblOASToMaturity = _bond.oasFromPrice (_valParams, _csqcFundingBase, null, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblZSpreadToMaturity = _bond.isFloater() ? _bond.discountMarginFromPrice (_valParams,
				_csqcFundingBase, null, _dblCurrentPrice) : _bond.zSpreadFromPrice (_valParams,
					_csqcFundingBase, null, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			 dblZSpreadToExercise = _bond.isFloater() ? _bond.discountMarginFromPrice (_valParams,
				_csqcFundingBase, null, iWorkoutDate, dblWorkoutFactor, _dblCurrentPrice) :
					_bond.zSpreadFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
						dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			 dblParZSpreadToExercise = _bond.isFloater() ? _bond.discountMarginFromPrice (_valParams,
				_csqcFundingBase, null, iWorkoutDate, dblWorkoutFactor, _dblIssuePrice) :
					_bond.zSpreadFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
						dblWorkoutFactor, _dblIssuePrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblBondBasisToMaturity = _bond.bondBasisFromPrice (_valParams, _csqcFundingBase, null,
				_dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblBondBasisToExercise = _bond.bondBasisFromPrice (_valParams, _csqcFundingBase, null,
				iWorkoutDate, dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblModifiedDurationToMaturity = (_dblCurrentPrice - _bond.priceFromBondBasis (_valParams,
				_csqcFunding01Up, null, dblBondBasisToMaturity)) / _dblCurrentPrice;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblMacaulayDurationToMaturity = _bond.macaulayDurationFromPrice (_valParams, _csqcFundingBase,
				null, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblModifiedDurationToExercise = (_dblCurrentPrice - _bond.priceFromBondBasis (_valParams,
				_csqcFunding01Up, null, iWorkoutDate, dblWorkoutFactor, dblBondBasisToExercise)) /
					_dblCurrentPrice;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblYield01 = 0.5 * (_bond.priceFromYield (_valParams, _csqcFundingBase, null, iWorkoutDate,
				dblWorkoutFactor, dblYieldToExercise - 0.0001 * _dblCustomYieldBump) - _bond.priceFromYield
					(_valParams, _csqcFundingBase, null, iWorkoutDate, dblWorkoutFactor, dblYieldToExercise +
						0.0001 * _dblCustomYieldBump)) / _dblCustomYieldBump;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _csqcCreditBase)
				dblCreditBasisToExercise = _bMarketPriceCreditMetrics ? 0. : _bond.creditBasisFromPrice
					(_valParams, _csqcCreditBase, null, iWorkoutDate, dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _csqcCreditBase)
				dblParCreditBasisToExercise = _bond.creditBasisFromPrice (_valParams, _csqcCreditBase, null,
					iWorkoutDate, dblWorkoutFactor, _dblIssuePrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _csqcCreditBase) {
				if (!_bMarketPriceCreditMetrics)
					dblEffectiveDurationAdjusted = (_dblCurrentPrice - _bond.priceFromCreditBasis
						(_valParams, _csqcCreditBase, null, iWorkoutDate, dblWorkoutFactor,
							dblCreditBasisToExercise + 0.0001 * _dblCustomCreditBasisBump)) /
								_dblCurrentPrice / _dblCustomCreditBasisBump;
				else {
					org.drip.state.identifier.CreditLabel cl = _bond.creditLabel();

					org.drip.state.credit.CreditCurve ccBase = _csqcCreditBase.creditState (cl);

					org.drip.state.credit.CreditCurve ccAdj =
						org.drip.state.creator.ScenarioCreditCurveBuilder.FlatHazard (_dtSpot.julian(),
							cl.referenceEntity(), strCurrency, ccBase.hazard (_bond.maturityDate()) + 0.0001
								* _dblCustomCreditBasisBump, 0.40);

					if (null != ccAdj)
						dblEffectiveDurationAdjusted = (_dblCurrentPrice - _bond.priceFromCreditBasis
							(_valParams, org.drip.param.creator.MarketParamsBuilder.Create
								(_csqcCreditBase.fundingState (_bond.fundingLabel()),
									_csqcCreditBase.govvieState (_bond.govvieLabel()), ccAdj, "", null, null,
										_csqcCreditBase.fixings()), null, iWorkoutDate, dblWorkoutFactor,
											0.)) / _dblCurrentPrice / _dblCustomCreditBasisBump;
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblSpreadDuration = _dblSpreadDurationMultiplier * (_dblCurrentPrice - (_bond.isFloater() ?
				_bond.priceFromDiscountMargin (_valParams, _csqcFundingBase, null, iWorkoutDate,
					dblWorkoutFactor, dblZSpreadToExercise + 0.0001 * _dblZSpreadBump) :
						_bond.priceFromZSpread (_valParams, _csqcFundingBase, null, iWorkoutDate,
							dblWorkoutFactor, dblZSpreadToExercise + 0.0001 * _dblZSpreadBump))) /
								_dblCurrentPrice;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _csqcCredit01Up)
				dblCV01 = _dblCurrentPrice - _bond.priceFromCreditBasis (_valParams, _csqcCredit01Up, null,
					iWorkoutDate, dblWorkoutFactor, dblCreditBasisToExercise);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblConvexityToExercise = _bond.convexityFromPrice (_valParams, _csqcFundingBase, null,
				iWorkoutDate, dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblDiscountMarginToExercise = dblYieldToExercise - _csqcFundingBase.fundingState
				(_bond.fundingLabel()).libor (_valParams.valueDate(), "1M");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblESpreadToExercise = _bond.isFloater() ? _bond.discountMarginFromPrice (_valParams,
				_csqcFundingEuroDollar, null, iWorkoutDate, dblWorkoutFactor, _dblCurrentPrice) :
					_bond.zSpreadFromPrice (_valParams, _csqcFundingEuroDollar, null, iWorkoutDate,
						dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblISpreadToExercise = _bond.iSpreadFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
				dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblJSpreadToExercise = _bond.jSpreadFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
				dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblNSpreadToExercise = _bond.nSpreadFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
				dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblWALToExercise = _bond.weightedAverageLife (_valParams, _csqcFundingBase, iWorkoutDate,
				dblWorkoutFactor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblWALPrincipalOnlyToExercise = _bond.weightedAverageLifePrincipalOnly (_valParams,
				_csqcFundingBase, iWorkoutDate, dblWorkoutFactor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _csqcCreditBase)
				dblWALLossOnlyToExercise = _bond.weightedAverageLifeLossOnly (_valParams, _csqcCreditBase,
					iWorkoutDate, dblWorkoutFactor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblWALCouponOnlyToExercise = _bond.weightedAverageLifeCouponOnly (_valParams, _csqcFundingBase,
				iWorkoutDate, dblWorkoutFactor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _csqcCreditBase)
				dblWALCreditToExercise = _bond.weightedAverageLifeCredit (_valParams, _csqcCreditBase,
					iWorkoutDate, dblWorkoutFactor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblOASToExercise = _bond.oasFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
				dblWorkoutFactor, _dblCurrentPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			dblParOASToExercise = _bond.oasFromPrice (_valParams, _csqcFundingBase, null, iWorkoutDate,
				dblWorkoutFactor, _dblIssuePrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		double dblEffectiveDuration = dblYield01 / _dblCurrentPrice;

		try {
			for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
				meCSQCUp : _mapCSQCFundingUp.entrySet()) {
				java.lang.String strKey = meCSQCUp.getKey();

				org.drip.param.market.CurveSurfaceQuoteContainer csqcTenorUp = meCSQCUp.getValue();

				org.drip.param.market.CurveSurfaceQuoteContainer csqcTenorDown = _mapCSQCFundingDown.get
					(strKey);

				double dblTenorUpPrice = _bond.isFloater() ? _bond.priceFromDiscountMargin (_valParams,
					csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblZSpreadToExercise) :
						_bond.priceFromZSpread (_valParams, csqcTenorUp, null, iWorkoutDate,
							dblWorkoutFactor, dblZSpreadToExercise);

				double dblTenorUpParPrice = _bond.isFloater() ? _bond.priceFromDiscountMargin (_valParams,
					csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblParZSpreadToExercise) :
						_bond.priceFromZSpread (_valParams, csqcTenorUp, null, iWorkoutDate,
							dblWorkoutFactor, dblParZSpreadToExercise);

				double dblTenorDownPrice = _bond.isFloater() ? _bond.priceFromDiscountMargin (_valParams,
					csqcTenorDown, null, iWorkoutDate, dblWorkoutFactor, dblZSpreadToExercise) :
						_bond.priceFromZSpread (_valParams, csqcTenorDown, null, iWorkoutDate,
							dblWorkoutFactor, dblZSpreadToExercise);

				double dblTenorDownParPrice = _bond.isFloater() ? _bond.priceFromDiscountMargin (_valParams,
					csqcTenorDown, null, iWorkoutDate, dblWorkoutFactor, dblParZSpreadToExercise) :
						_bond.priceFromZSpread (_valParams, csqcTenorDown, null, iWorkoutDate,
							dblWorkoutFactor, dblParZSpreadToExercise);

				mapLIBORKRD.put (strKey, 0.5 * (dblTenorDownPrice - dblTenorUpPrice) / _dblCurrentPrice /
					_dblTenorBump);

				mapLIBORKPRD.put (strKey, 0.5 * (dblTenorDownParPrice - dblTenorUpParPrice) /
					_dblIssuePrice / _dblTenorBump);
			}

			for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
				meCSQCUp : _mapCSQCGovvieUp.entrySet()) {
				java.lang.String strKey = meCSQCUp.getKey();

				org.drip.param.market.CurveSurfaceQuoteContainer csqcTenorUp = meCSQCUp.getValue();

				org.drip.param.market.CurveSurfaceQuoteContainer csqcTenorDown = _mapCSQCGovvieDown.get
					(strKey);

				mapGovvieKRD.put (strKey, 0.5 * (_bond.priceFromOAS (_valParams, csqcTenorDown, null,
					iWorkoutDate, dblWorkoutFactor, dblOASToExercise) - _bond.priceFromOAS (_valParams,
						csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblOASToExercise)) /
							_dblCurrentPrice / _dblTenorBump);

				mapGovvieKPRD.put (strKey, 0.5 * (_bond.priceFromOAS (_valParams, csqcTenorDown, null,
					iWorkoutDate, dblWorkoutFactor, dblParOASToExercise) - _bond.priceFromOAS (_valParams,
						csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblParOASToExercise)) /
							_dblIssuePrice / _dblTenorBump);
			}

			if (null != _mapCSQCCredit) {
				mapCreditKRD = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

				mapCreditKPRD = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

				for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
					meCSQC : _mapCSQCCredit.entrySet()) {
					java.lang.String strKey = meCSQC.getKey();

					org.drip.param.market.CurveSurfaceQuoteContainer csqcTenor = meCSQC.getValue();

					mapCreditKRD.put (strKey, (_dblCurrentPrice - _bond.priceFromCreditBasis (_valParams,
						csqcTenor, null, iWorkoutDate, dblWorkoutFactor, dblCreditBasisToExercise)) /
							_dblCurrentPrice);

					mapCreditKPRD.put (strKey, (_dblIssuePrice - _bond.priceFromCreditBasis (_valParams,
						csqcTenor, null, iWorkoutDate, dblWorkoutFactor, dblParCreditBasisToExercise)) /
							_dblIssuePrice);
				}
			}

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Price", _dblCurrentPrice)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Market Value",
				_dblCurrentPrice * _dblIssueAmount)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Accrued", dblAccrued)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Accrued$", dblAccrued *
				_dblIssueAmount)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Accrued Interest Factor",
				dblAccrued * _dblFX)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Yield To Maturity",
				dblYieldToMaturity)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Yield To Maturity CBE",
				dblBEYToMaturity)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("YTM fwdCpn",
				dblYieldToMaturityFwdCoupon)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Yield To Worst",
				dblYieldToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("YIELD TO CALL",
				dblYieldFromPriceNextCall)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("YIELD TO PUT",
				dblYieldFromPriceNextPut)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Nominal Yield",
				dblNominalYield)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Z_Spread", dblOASToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Z_Vol_OAS",
				dblZSpreadToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("OAS", dblZSpreadToMaturity)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("TSY OAS", dblOASToMaturity)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("MOD DUR",
				dblModifiedDurationToMaturity)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("MACAULAY DURATION",
				dblMacaulayDurationToMaturity)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("MOD DUR TO WORST",
				dblModifiedDurationToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("LIBOR DURATION",
				mapLIBORKRD.get ("bump"))))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("TREASURY DURATION",
				mapGovvieKRD.get ("bump"))))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("EFFECTIVE DURATION",
				dblEffectiveDuration)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("EFFECTIVE DURATION ADJ",
				dblEffectiveDurationAdjusted)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("OAD MULT",
				dblEffectiveDurationAdjusted / dblEffectiveDuration)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Spread Dur",
				dblSpreadDuration)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Spread Dur $",
				dblSpreadDuration * _dblIssueAmount)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("DV01", dblYield01)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("CV01", dblCV01))) return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Convexity",
				dblConvexityToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("Modified Convexity",
				dblConvexityToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("DISCOUNT MARGIN",
				dblDiscountMarginToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("E-Spread",
				dblESpreadToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("I-Spread",
				dblISpreadToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("J-Spread",
				dblJSpreadToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("N-Spread",
				dblNSpreadToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("WAL To Worst",
				dblWALToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("WAL",
				dblWALPrincipalOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("WAL2",
				dblWALLossOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("WAL3",
				!org.drip.quant.common.NumberUtil.IsValid (dblWALCouponOnlyToExercise) ? 0. :
					dblWALCouponOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("WAL4",
				dblWALPrincipalOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new org.drip.service.scenario.NamedField ("WAL_Adj",
				dblWALCreditToExercise)))
				return null;

			if (!arr.addNamedFieldMap (new org.drip.service.scenario.NamedFieldMap ("LIBOR KRD",
				mapLIBORKRD)))
				return null;

			if (!arr.addNamedFieldMap (new org.drip.service.scenario.NamedFieldMap ("LIBOR KPRD",
				mapLIBORKPRD)))
				return null;

			if (!arr.addNamedFieldMap (new org.drip.service.scenario.NamedFieldMap ("Govvie KRD",
				mapGovvieKRD)))
				return null;

			if (!arr.addNamedFieldMap (new org.drip.service.scenario.NamedFieldMap ("Govvie KPRD",
				mapGovvieKPRD)))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			if (null != mapCreditKRD)
				arr.addNamedFieldMap (new org.drip.service.scenario.NamedFieldMap ("Credit KRD",
					mapCreditKRD));

			if (null != mapCreditKPRD)
				arr.addNamedFieldMap (new org.drip.service.scenario.NamedFieldMap ("Credit KPRD",
					mapCreditKPRD));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		System.out.println ("Workout        : " + new org.drip.analytics.date.JulianDate (iWorkoutDate));

		System.out.println ("Next Call Date : " + new org.drip.analytics.date.JulianDate (iNextCallDate) +
			" | " + dblNextCallFactor);

		return arr;
	}
}
