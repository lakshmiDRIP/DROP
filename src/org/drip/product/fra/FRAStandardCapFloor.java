
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
- */

/**
 * FRAStandardCapFloor implements the Caps and Floors on the Standard FRA.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FRAStandardCapFloor extends org.drip.product.option.OptionComponent {
	private boolean _bIsCap = false;
	private double _dblStrike = java.lang.Double.NaN;
	private org.drip.product.rates.Stream _stream = null;

	private java.util.List<org.drip.product.fra.FRAStandardCapFloorlet> _lsFRACapFloorlet = new
		java.util.ArrayList<org.drip.product.fra.FRAStandardCapFloorlet>();

	/**
	 * FRAStandardCapFloor constructor
	 * 
	 * @param strName Name of the Cap/Floor Instance
	 * @param stream The Underlying Stream
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCap Is the FRA Option a Cap? TRUE - YES
	 * @param dblStrike Strike of the Underlying Component's Measure
	 * @param ltds Last Trading Date Setting
	 * @param csp Cash Settle Parameters
	 * @param fpg The Fokker Planck Pricer Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FRAStandardCapFloor (
		final java.lang.String strName,
		final org.drip.product.rates.Stream stream,
		final java.lang.String strManifestMeasure,
		final boolean bIsCap,
		final double dblStrike,
		final org.drip.product.params.LastTradingDateSetting ltds,
		final org.drip.param.valuation.CashSettleParams csp,
		final org.drip.pricer.option.FokkerPlanckGenerator fpg)
		throws java.lang.Exception
	{
		super (strName, org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard
			(stream.effective(), stream.forwardLabel(), dblStrike), strManifestMeasure, dblStrike,
				stream.initialNotional(), ltds, csp);

		if (null == (_stream = stream) || !org.drip.quant.common.NumberUtil.IsValid (_dblStrike = dblStrike))
			throw new java.lang.Exception ("FRAStandardCapFloor Constructor => Invalid Inputs");

		_bIsCap = bIsCap;

		org.drip.state.identifier.ForwardLabel fri = _stream.forwardLabel();

		if (null == fri)
			throw new java.lang.Exception ("FRAStandardCapFloor Constructor => Invalid Floater Index");

		for (org.drip.analytics.cashflow.CompositePeriod period : _stream.periods()) {
			org.drip.product.fra.FRAStandardComponent fra =
				org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard (new
					org.drip.analytics.date.JulianDate (period.startDate()), fri, _dblStrike);

			_lsFRACapFloorlet.add (new org.drip.product.fra.FRAStandardCapFloorlet (fra.name() + "::LET",
				fra, strManifestMeasure, _bIsCap, _dblStrike, _stream.notional (period.startDate()), ltds,
					fpg, csp));
		}
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		double dblPV = 0.;
		double dblPrice = 0.;
		double dblUpfront = 0.;
		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = null;

		long lStart = System.nanoTime();

		final int iValueDate = valParams.valueDate();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapStreamResult = _stream.value
			(valParams, pricerParams, csqs, vcp);

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFRAResult = fracfl.value
				(valParams, pricerParams, csqs, vcp);

			if (null == mapFRAResult) continue;

			if (mapFRAResult.containsKey ("Price")) dblPrice += mapFRAResult.get ("Price");

			if (mapFRAResult.containsKey ("PV")) dblPV += mapFRAResult.get ("PV");

			if (mapFRAResult.containsKey ("Upfront")) dblUpfront += mapFRAResult.get ("Upfront");
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapResult.put ("ATMFairPremium", mapStreamResult.get ("FairPremium"));

		mapResult.put ("Price", dblPrice);

		mapResult.put ("PV", dblPV);

		mapResult.put ("Upfront", dblUpfront);

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				double dblCapFloorletPrice = 0.;

				for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
					int iExerciseDate = fracfl.exerciseDate().julian();

					if (iExerciseDate <= iValueDate) continue;

					dblCapFloorletPrice += fracfl.price (valParams, pricerParams, csqs, vcp, dblVolatility);
				}

				return dblCapFloorletPrice;
			}
		};

		try {
			fpfo = (new org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblPrice, funcVolPricer,
				null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
						5.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return mapResult;
		}

		mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		if (null != fpfo && fpfo.containsRoot())
			mapResult.put ("FlatVolatility", fpfo.getRoot());
		else
			mapResult.put ("FlatVolatility", java.lang.Double.NaN);

		return mapResult;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("ATMFairPremium");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("FlatVolatility");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("Upfront");

		return setstrMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		double dblPV = 0.;

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet)
			dblPV += fracfl.pv (valParams, pricerParams, csqc, vcp);

		return dblPV;
	}

	/**
	 * Retrieve the Stream Instance Underlying the Cap
	 * 
	 * @return The Stream Instance Underlying the Cap
	 */

	public org.drip.product.rates.Stream stream()
	{
		return _stream;
	}

	/**
	 * Indicate if this is a Cap or Floor
	 * 
	 * @return TRUE - The Product is a Cap
	 */

	public boolean isCap()
	{
		return _bIsCap;
	}

	/**
	 * Retrieve the List of the Underlying Caplets/Floorlets
	 * 
	 * @return The List of the Underlying Caplets/Floorlets
	 */

	public java.util.List<org.drip.product.fra.FRAStandardCapFloorlet> capFloorlets()
	{
		return _lsFRACapFloorlet;
	}

	/**
	 * Compute the ATM Cap/Floor Price from the Flat Volatility
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblFlatVolatility The Flat Volatility
	 * 
	 * @return The Cap/Floor ATM Price
	 * 
	 * @throws java.lang.Exception Thrown if the ATM Price cannot be calculated
	 */

	public double atmPriceFromVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblFlatVolatility)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblFlatVolatility))
			throw new java.lang.Exception ("FRAStandardCapFloor::atmPriceFromVolatility => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		double dblPrice = 0.;

		org.drip.product.fra.FRAStandardCapFloorlet fraLeading = _lsFRACapFloorlet.get (0);

		java.lang.String strManifestMeasure = fraLeading.manifestMeasure();

		org.drip.pricer.option.FokkerPlanckGenerator fpg = fraLeading.pricer();

		org.drip.product.params.LastTradingDateSetting ltds = fraLeading.lastTradingDateSetting();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapStreamResult = _stream.value
			(valParams, pricerParams, csqs, vcp);

		if (null == mapStreamResult || !mapStreamResult.containsKey ("FairPremium"))
			throw new java.lang.Exception
				("FRAStandardCapFloor::atmPriceFromVolatility => Cannot calculate Fair Premium");

		double dblCapATMFairPremium = mapStreamResult.get ("FairPremium");

		org.drip.state.identifier.ForwardLabel forwardLabel = _stream.forwardLabel();

		java.util.List<org.drip.product.fra.FRAStandardCapFloorlet> lsATMFRACapFloorlet = new
			java.util.ArrayList<org.drip.product.fra.FRAStandardCapFloorlet>();

		for (org.drip.analytics.cashflow.CompositePeriod period : _stream.periods()) {
			org.drip.product.fra.FRAStandardComponent fra =
				org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard (new
					org.drip.analytics.date.JulianDate (period.startDate()), forwardLabel,
						dblCapATMFairPremium);

			lsATMFRACapFloorlet.add (new org.drip.product.fra.FRAStandardCapFloorlet (fra.name() + "::LET",
				fra, strManifestMeasure, _bIsCap, dblCapATMFairPremium, _stream.notional
					(period.startDate()), ltds, fpg, cashSettleParams()));
		}

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : lsATMFRACapFloorlet) {
			org.drip.analytics.date.JulianDate dtExercise = fracfl.exerciseDate();

			int iExerciseDate = dtExercise.julian();

			if (iExerciseDate <= iValueDate) continue;

			dblPrice += fracfl.price (valParams, pricerParams, csqs, vcp, dblFlatVolatility);
		}

		return dblPrice;
	}

	/**
	 * Imply the Flat Cap/Floor Volatility from the Calibration ATM Price
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCalibPrice The Calibration Price
	 * 
	 * @return The Cap/Floor Flat Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Flat Volatility cannot be calculated
	 */

	public double volatilityFromATMPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCalibPrice)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblCalibPrice))
			throw new java.lang.Exception ("FRAStandardCapFloor::volatilityFromATMPrice => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				return atmPriceFromVolatility (valParams, pricerParams, csqs, vcp, dblVolatility);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = (new
			org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblCalibPrice, funcVolPricer, null,
				org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
						5.));

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("FRAStandardCapFloor::volatilityFromATMPrice => Cannot imply Flat Vol");

		return fpfo.getRoot();
	}

	/**
	 * Compute the Cap/Floor Price from the Flat Volatility
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblFlatVolatility The Flat Volatility
	 * 
	 * @return The Cap/Floor Price
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public double priceFromFlatVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblFlatVolatility)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblFlatVolatility))
			throw new java.lang.Exception ("FRAStandardCapFloor::priceFromFlatVolatility => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		double dblPrice = 0.;

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			org.drip.analytics.date.JulianDate dtExercise = fracfl.exerciseDate();

			int iExerciseDate = dtExercise.julian();

			if (iExerciseDate <= iValueDate) continue;

			dblPrice += fracfl.price (valParams, pricerParams, csqs, vcp, dblFlatVolatility);
		}

		return dblPrice;
	}

	/**
	 * Imply the Flat Cap/Floor Volatility from the Calibration Price
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCalibPrice The Calibration Price
	 * 
	 * @return The Cap/Floor Flat Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public double flatVolatilityFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCalibPrice)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblCalibPrice))
			throw new java.lang.Exception ("FRAStandardCapFloor::flatVolatilityFromPrice => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				return priceFromFlatVolatility (valParams, pricerParams, csqs, vcp, dblVolatility);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = (new
			org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblCalibPrice, funcVolPricer, null,
				org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
						5.));

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("FRAStandardCapFloor::flatVolatilityFromPrice => Cannot imply Flat Vol");

		return fpfo.getRoot();
	}

	/**
	 * Strip the Piece-wise Constant Forward Rate Volatility of the Unmarked Segment of the Volatility Term
	 *  Structure
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The pricer Parameters
	 * @param csqs The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCapVolatility The Flat Cap Volatility
	 * @param mapDateVol The Date/Volatility Map
	 * 
	 * @return TRUE - The Forward Rate Volatility of the Unmarked Segment of the Volatility Term Structure
	 * 	successfully implied
	 */

	public boolean stripPiecewiseForwardVolatility (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCapVolatility,
		final java.util.Map<org.drip.analytics.date.JulianDate, java.lang.Double> mapDateVol)
	{
		if (null == valParams || null == mapDateVol) return false;

		int iIndex = 0;
		double dblPreceedingCapFloorletPV = 0.;
		double dblCapPrice = java.lang.Double.NaN;
		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = null;

		try {
			dblCapPrice = priceFromFlatVolatility (valParams, pricerParams, csqs, vcp, dblCapVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		final int iValueDate = valParams.valueDate();

		final java.util.List<java.lang.Integer> lsCalibCapFloorletIndex = new
			java.util.ArrayList<java.lang.Integer>();

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			org.drip.analytics.date.JulianDate dtExercise = fracfl.exerciseDate();

			int iExerciseDate = dtExercise.julian();

			if (iExerciseDate <= iValueDate) continue;

			if (mapDateVol.containsKey (dtExercise)) {
				double dblExerciseVolatility = mapDateVol.get (dtExercise);

				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCapFloorlet =
					fracfl.valueFromSurfaceVariance (valParams, pricerParams, csqs, vcp,
						dblExerciseVolatility * dblExerciseVolatility * (iExerciseDate - iValueDate) /
							365.25);

				if (null == mapCapFloorlet || !mapCapFloorlet.containsKey ("Price")) return false;

				dblPreceedingCapFloorletPV += mapCapFloorlet.get ("Price");
			} else
				lsCalibCapFloorletIndex.add (iIndex);

			++iIndex;
		}

		org.drip.function.definition.R1ToR1 funcVolPricer = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblVolatility)
				throws java.lang.Exception
			{
				int iIndex = 0;
				double dblSucceedingCapFloorletPV = 0.;

				for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
					int iExerciseDate = fracfl.exerciseDate().julian();

					if (iExerciseDate <= iValueDate) continue;

					if (lsCalibCapFloorletIndex.contains (iIndex)) {
						java.util.Map<java.lang.String, java.lang.Double> mapOutput =
							fracfl.valueFromSurfaceVariance (valParams, pricerParams, csqs, vcp,
								dblVolatility * dblVolatility * (iExerciseDate - iValueDate) / 365.25);
	
						if (null == mapOutput || !mapOutput.containsKey ("Price"))
							throw new java.lang.Exception
								("FRAStandardCapFloor::implyVolatility => Cannot generate Calibration Measure");
	
						dblSucceedingCapFloorletPV += mapOutput.get ("Price");
					}

					++iIndex;
				}

				return dblSucceedingCapFloorletPV;
			}
		};

		try {
			fpfo = (new org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblCapPrice -
				dblPreceedingCapFloorletPV, funcVolPricer, null,
					org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, false)).findRoot
						(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.0001,
							5.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		if (null == fpfo || !fpfo.containsRoot()) return false;

		double dblVolatility = fpfo.getRoot();

		iIndex = 0;

		for (org.drip.product.fra.FRAStandardCapFloorlet fracfl : _lsFRACapFloorlet) {
			if (lsCalibCapFloorletIndex.contains (iIndex))
				mapDateVol.put (fracfl.exerciseDate(), dblVolatility);

			++iIndex;
		}

		return true;
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

		if (valParams.valueDate() > maturityDate().julian()) return null;

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

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.product.fra.FRAStandardCapFloorlet frascf : _lsFRACapFloorlet) {
			org.drip.state.estimator.PredictorResponseWeightConstraint prwcFRASCF = frascf.volatilityPRWC
				(valParams, pricerParams, csqs, vcp, pqs);

			if (null == prwcFRASCF || !prwc.absorb (prwcFRASCF)) return null;
		}

		return !prwc.updateValue (dblOptionPV) || !prwc.updateDValueDManifestMeasure ("OptionPV", 1.) ? null
			: prwc;
	}
}
