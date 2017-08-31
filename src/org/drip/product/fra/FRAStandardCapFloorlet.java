
package org.drip.product.fra;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * FRAStandardCapFloorlet implements the Standard FRA Caplet and Floorlet.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FRAStandardCapFloorlet extends org.drip.product.option.OptionComponent {
	private boolean _bIsCaplet = false;
	private org.drip.product.fra.FRAStandardComponent _fra = null;
	private org.drip.pricer.option.FokkerPlanckGenerator _fpg = null;

	/**
	 * FRAStandardCapFloorlet constructor
	 * 
	 * @param strName Name
	 * @param fra The Underlying FRA Standard Component
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCaplet Is the FRA Option a Caplet? TRUE - YES
	 * @param dblStrike Strike of the Underlying Component's Measure
	 * @param dblNotional Option Notional
	 * @param ltds Last Trading Date Setting
	 * @param fpg The Fokker Planck Pricer Instance
	 * @param csp Cash Settle Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FRAStandardCapFloorlet (
		final java.lang.String strName,
		final org.drip.product.fra.FRAStandardComponent fra,
		final java.lang.String strManifestMeasure,
		final boolean bIsCaplet,
		final double dblStrike,
		final double dblNotional,
		final org.drip.product.params.LastTradingDateSetting ltds,
		final org.drip.pricer.option.FokkerPlanckGenerator fpg,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		super (strName, fra, strManifestMeasure, dblStrike, dblNotional, ltds, csp);

		if (null == (_fpg = fpg))
			throw new java.lang.Exception ("FRAStandardCapFloorlet ctr: Invalid Option Pricer");

		_fra = fra;
		_bIsCaplet = bIsCaplet;
	}

	/**
	 * Retrieve the Underlying FRA Instance
	 * 
	 * @return The FRA Instance
	 */

	public org.drip.product.fra.FRAStandardComponent fra()
	{
		return _fra;
	}

	/**
	 * Indicate whether this a Caplet/Floorlet
	 * 
	 * @return TRUE - This is a Caplet
	 */

	public boolean isCaplet()
	{
		return _bIsCaplet;
	}

	/**
	 * Retrieve the Underlying Pricer Instance
	 * 
	 * @return The Pricer Instance
	 */

	public org.drip.pricer.option.FokkerPlanckGenerator pricer()
	{
		return _fpg;
	}

	/**
	 * Generate the Standard FRA Caplet/Floorlet Measures from the Integrated Surface Variance
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblIntegratedSurfaceVariance The Integrated Surface Variance
	 * 
	 * @return The Standard FRA Caplet/Floorlet Measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> valueFromSurfaceVariance (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblIntegratedSurfaceVariance)
	{
		if (null == valParams || null == csqs || !org.drip.quant.common.NumberUtil.IsValid
			(dblIntegratedSurfaceVariance))
			return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (_fra.payCurrency()));

		if (null == dcFunding) return null;

		int iValueDate = valParams.valueDate();

		org.drip.product.params.LastTradingDateSetting ltds = lastTradingDateSetting();

		try {
			if (null != ltds && iValueDate >= ltds.lastTradingDate (_fra.effectiveDate().julian(),
				_fra.stream().calendar()))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		long lStart = System.nanoTime();

		int iExerciseDate = exerciseDate().julian();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFRAOutput = _fra.value
			(valParams, pricerParams, csqs, vcp);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapFRAOutput || !mapFRAOutput.containsKey (strManifestMeasure)) return null;

		double dblFRADV01 = mapFRAOutput.get ("DV01");

		double dblATMManifestMeasure = mapFRAOutput.get (strManifestMeasure);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblATMManifestMeasure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFRADV01))
			return null;

		try {
			double dblStrike = strike();

			double dblNotional = notional();

			double dblMoneynessFactor = dblATMManifestMeasure / dblStrike;
			double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;
			double dblManifestMeasureIntrinsic = _bIsCaplet ? dblATMManifestMeasure - dblStrike : dblStrike -
				dblATMManifestMeasure;

			if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
				dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate);
			else if (strManifestMeasure.equalsIgnoreCase ("ForwardRate") ||
				strManifestMeasure.equalsIgnoreCase ("ParForward") || strManifestMeasure.equalsIgnoreCase
					("ParForwardRate") || strManifestMeasure.equalsIgnoreCase ("QuantoAdjustedParForward") ||
						strManifestMeasure.equalsIgnoreCase ("Rate"))
				dblManifestMeasurePriceTransformer = 10000. * dblFRADV01;

			if (!org.drip.quant.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer)) return null;

			org.drip.pricer.option.Greeks optGreek = _fpg.greeks (iValueDate, iExerciseDate, dblStrike,
				dcFunding, dblATMManifestMeasure, !_bIsCaplet, true, dblIntegratedSurfaceVariance);

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			double dblForwardIntrinsic = optGreek.expectedPayoff();

			double dblForwardATMIntrinsic = optGreek.expectedATMPayoff();

			double dblSpotPrice = dblForwardIntrinsic * dblManifestMeasurePriceTransformer;

			mapResult.put ("ATMFRA", dblATMManifestMeasure);

			mapResult.put ("ATMPrice", dblForwardATMIntrinsic * dblManifestMeasurePriceTransformer);

			mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

			mapResult.put ("Charm", optGreek.charm() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Color", optGreek.color() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Delta", optGreek.delta() * dblManifestMeasurePriceTransformer);

			mapResult.put ("EffectiveVolatility", optGreek.effectiveVolatility());

			mapResult.put ("ExpectedATMPayoff", optGreek.expectedATMPayoff());

			mapResult.put ("ExpectedPayoff", optGreek.expectedPayoff());

			mapResult.put ("ForwardATMIntrinsic", dblForwardATMIntrinsic);

			mapResult.put ("ForwardIntrinsic", dblForwardIntrinsic);

			mapResult.put ("Gamma", optGreek.gamma() * dblManifestMeasurePriceTransformer);

			mapResult.put ("IntegratedSurfaceVariance", dblIntegratedSurfaceVariance);

			mapResult.put ("ManifestMeasureIntrinsic", dblManifestMeasureIntrinsic);

			mapResult.put ("ManifestMeasureIntrinsicValue", dblManifestMeasureIntrinsic *
				dblManifestMeasurePriceTransformer);

			mapResult.put ("ManifestMeasureTransformer", dblManifestMeasurePriceTransformer);

			mapResult.put ("MoneynessFactor", dblMoneynessFactor);

			mapResult.put ("Price", dblSpotPrice);

			mapResult.put ("Prob1", optGreek.prob1());

			mapResult.put ("Prob2", optGreek.prob2());

			mapResult.put ("PV", dblSpotPrice * dblNotional);

			mapResult.put ("Rho", optGreek.rho() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Speed", optGreek.speed() * dblManifestMeasurePriceTransformer);

			mapResult.put ("SpotPrice", dblSpotPrice);

			mapResult.put ("Theta", optGreek.theta() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Ultima", optGreek.ultima() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Upfront", dblSpotPrice);

			mapResult.put ("Vanna", optGreek.vanna() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Vega", optGreek.vega() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Veta", optGreek.veta() * dblManifestMeasurePriceTransformer);

			mapResult.put ("Vomma", optGreek.vomma() * dblManifestMeasurePriceTransformer);

			return mapResult;
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Caplet/Floorlet Price from the Inputs
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblVolatility The FRA Volatility
	 * 
	 * @return The Caplet/Floorlet Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double price (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblVolatility)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqs || !org.drip.quant.common.NumberUtil.IsValid (dblVolatility))
			throw new java.lang.Exception ("FRAStandardCapFloorlet::price => Invalid Inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (_fra.payCurrency()));

		if (null == dcFunding)
			throw new java.lang.Exception ("FRAStandardCapFloorlet::price => Invalid Inputs");

		int iExerciseDate = exerciseDate().julian();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFRAOutput = _fra.value
			(valParams, pricerParams, csqs, vcp);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapFRAOutput || !mapFRAOutput.containsKey (strManifestMeasure))
			throw new java.lang.Exception ("FRAStandardCapFloorlet::price => No ATM Metric");

		double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;

		if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
			dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate);
		else if (strManifestMeasure.equalsIgnoreCase ("ForwardRate") ||
			strManifestMeasure.equalsIgnoreCase ("ParForward") || strManifestMeasure.equalsIgnoreCase
				("ParForwardRate") || strManifestMeasure.equalsIgnoreCase ("QuantoAdjustedParForward") ||
					strManifestMeasure.equalsIgnoreCase ("Rate")) {
			if (!mapFRAOutput.containsKey ("DV01"))
				throw new java.lang.Exception ("FRAStandardCapFloorlet::price => No FRA DV01");

			dblManifestMeasurePriceTransformer = 10000. * mapFRAOutput.get ("DV01");
		}

		if (!org.drip.quant.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer))
			throw new java.lang.Exception
				("FRAStandardCapFloorlet::price => No Manifest Measure Price Transformer");

		return dblManifestMeasurePriceTransformer * _fpg.payoff (valParams.valueDate(), iExerciseDate,
			strike(), dcFunding, mapFRAOutput.get (strManifestMeasure), !_bIsCaplet, true, dblVolatility,
				false);
	}

	/**
	 * Imply the Flat Caplet/Floorlet Volatility from the Market Manifest Measure
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param strCalibMeasure The Calibration Measure
	 * @param dblCalibValue The Calibration Value
	 * 
	 * @return The Implied Caplet/Floorlet Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double implyVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final java.lang.String strCalibMeasure,
		final double dblCalibValue)
		throws java.lang.Exception
	{
		if (null == valParams || null == strCalibMeasure || strCalibMeasure.isEmpty() || null == csqs ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCalibValue))
			throw new java.lang.Exception ("FRAStandardCapFloorlet::implyVolatility => Invalid Inputs");

		final double dblStrike = strike();

		final int iValueDate = valParams.valueDate();

		final int iExerciseDate = exerciseDate().julian();

		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (_fra.payCurrency()));

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFRAOutput = _fra.value
			(valParams, pricerParams, csqs, vcp);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapFRAOutput || !mapFRAOutput.containsKey (strManifestMeasure))
			throw new java.lang.Exception ("FRAStandardCapFloorlet::implyVolatility => No ATM Metric");

		final double dblATMManifestMeasure = mapFRAOutput.get (strManifestMeasure);

		double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;

		if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
			dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate);
		else if (strManifestMeasure.equalsIgnoreCase ("ForwardRate") ||
			strManifestMeasure.equalsIgnoreCase ("ParForward") || strManifestMeasure.equalsIgnoreCase
				("ParForwardRate") || strManifestMeasure.equalsIgnoreCase ("QuantoAdjustedParForward") ||
					strManifestMeasure.equalsIgnoreCase ("Rate")) {
			if (!mapFRAOutput.containsKey ("DV01"))
				throw new java.lang.Exception ("FRAStandardCapFloorlet::implyVolatility => No DV01");

			dblManifestMeasurePriceTransformer = 10000. * mapFRAOutput.get ("DV01");
		}

		final double dblManifestMeasurePriceTransformerCalib = dblManifestMeasurePriceTransformer;

		if (!org.drip.quant.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer))
			throw new java.lang.Exception ("FRAStandardCapFloorlet::implyVolatility => No Transformer");

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				if ("Price".equals (strCalibMeasure))
					return dblManifestMeasurePriceTransformerCalib * _fpg.payoff (iValueDate, iExerciseDate,
						dblStrike, dcFunding, dblATMManifestMeasure, !_bIsCaplet, true, dblVolatility,
							false);

				if ("ATMPrice".equals (strCalibMeasure))
					return dblManifestMeasurePriceTransformerCalib * _fpg.payoff (iValueDate, iExerciseDate,
						dblStrike, dcFunding, dblStrike, !_bIsCaplet, true, dblVolatility, false);

				java.util.Map<java.lang.String, java.lang.Double> mapOutput = valueFromSurfaceVariance 
					(valParams, pricerParams, csqs, vcp, dblVolatility * dblVolatility * (iExerciseDate -
						iValueDate) / 365.25);

				if (null == mapOutput || !mapOutput.containsKey (strCalibMeasure))
					throw new java.lang.Exception
						("FRAStandardCapFloorlet::implyVolatility => Cannot generate Calibration Measure");

				return mapOutput.get (strCalibMeasure);

			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = (new
			org.drip.function.r1tor1solver.FixedPointFinderBrent (dblCalibValue, funcVolPricer,
				false)).findRoot();

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("FRAStandardCapFloorlet::implyVolatility => Cannot calibrate the Vol");

		return fpfo.getRoot();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == csqs) return null;

		try {
			return valueFromSurfaceVariance (valParams, pricerParams, csqs, vcp,
				org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.forwardVolatility
					(_fra.forwardLabel().get ("DERIVED")), valParams.valueDate(), exerciseDate().julian()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("ATMFRA");

		setstrMeasureNames.add ("ATMPrice");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("Charm");

		setstrMeasureNames.add ("Color");

		setstrMeasureNames.add ("Delta");

		setstrMeasureNames.add ("EffectiveVolatility");

		setstrMeasureNames.add ("ExpectedATMPayoff");

		setstrMeasureNames.add ("ExpectedPayoff");

		setstrMeasureNames.add ("ForwardATMIntrinsic");

		setstrMeasureNames.add ("ForwardIntrinsic");

		setstrMeasureNames.add ("Gamma");

		setstrMeasureNames.add ("IntegratedSurfaceVariance");

		setstrMeasureNames.add ("ManifestMeasureIntrinsic");

		setstrMeasureNames.add ("ManifestMeasureIntrinsicValue");

		setstrMeasureNames.add ("MoneynessFactor");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("Prob1");

		setstrMeasureNames.add ("Prob2");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("Rho");

		setstrMeasureNames.add ("Speed");

		setstrMeasureNames.add ("SpotPrice");

		setstrMeasureNames.add ("Theta");

		setstrMeasureNames.add ("Ultima");

		setstrMeasureNames.add ("Upfront");

		setstrMeasureNames.add ("Vanna");

		setstrMeasureNames.add ("Vega");

		setstrMeasureNames.add ("Veta");

		setstrMeasureNames.add ("Vomma");

		return setstrMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		if (null == csqc) throw new java.lang.Exception ("FRAStandardCapFloorlet::pv => Invalid Inputs");

		org.drip.state.volatility.VolatilityCurve vc = csqc.forwardVolatility (_fra.forwardLabel().get
			("DERIVED"));

		if (null == vc) throw new java.lang.Exception ("FRAStandardCapFloorlet::pv => Invalid Inputs");

		return price (valParams, pricerParams, csqc, vcp, vc.impliedVol (exerciseDate().julian()));
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs || null == valParams || !(pqs instanceof
			org.drip.product.calib.VolatilityProductQuoteSet))
			return null;

		double dblEndDate = maturityDate().julian();

		if (valParams.valueDate() > dblEndDate) return null;

		double dblOptionPV = 0.;
		org.drip.product.calib.VolatilityProductQuoteSet vpqs =
			(org.drip.product.calib.VolatilityProductQuoteSet) pqs;

		if (!vpqs.containsOptionPV()) return null;

		try {
			dblOptionPV = vpqs.optionPV();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = _fra.stream().volatilityPRWC
			(valParams, pricerParams, csqs, vcp, pqs);

		if (null == prwc) return null;

		if (!prwc.addPredictorResponseWeight (dblEndDate, 1.)) return null;

		if (!prwc.addDResponseWeightDManifestMeasure ("OptionPV", dblEndDate, 1.)) return null;

		return !prwc.updateValue (dblOptionPV) || !prwc.updateDValueDManifestMeasure ("OptionPV", 1.) ? null
			: prwc;
	}
}
