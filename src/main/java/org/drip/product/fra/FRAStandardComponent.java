
package org.drip.product.fra;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>FRAStandardComponent</i> contains the implementation of the Standard Multi-Curve FRA Component.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fra">FRA</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FRAStandardComponent extends org.drip.product.rates.SingleStreamComponent {
	private double _dblStrike = java.lang.Double.NaN;

	/**
	 * FRAStandardComponent constructor
	 * 
	 * @param strName Futures Component Name
	 * @param stream Futures Stream
	 * @param dblStrike Futures Strike
	 * @param csp Cash Settle Parameters Instance
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public FRAStandardComponent (
		final java.lang.String strName,
		final org.drip.product.rates.Stream stream,
		final double dblStrike,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		super (strName, stream, csp);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStrike = dblStrike))
			throw new java.lang.Exception ("FRAStandardComponent ctr => Invalid Inputs!");
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == csqs) return null;

		org.drip.state.identifier.FundingLabel fundingLabel =
			org.drip.state.identifier.FundingLabel.Standard (payCurrency());

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState (fundingLabel);

		if (null == dcFunding) return null;

		long lStart = System.nanoTime();

		double dblParForward = java.lang.Double.NaN;

		int iValueDate = valParams.valueDate();

		int iEffectiveDate = effectiveDate().julian();

		if (iValueDate > iEffectiveDate) return null;

		org.drip.analytics.date.JulianDate dtMaturity = maturityDate();

		int iMaturityDate = dtMaturity.julian();

		org.drip.state.identifier.ForwardLabel forwardLabel = forwardLabel().get ("DERIVED");

		org.drip.state.forward.ForwardRateEstimator fc = csqs.forwardState (forwardLabel);

		if (null == fc || !forwardLabel.match (fc.index())) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		org.drip.param.valuation.CashSettleParams settleParams = cashSettleParams();

		try {
			int dblCashSettle = null == settleParams ? valParams.cashPayDate() : settleParams.cashSettleDate
				(iValueDate);

			dblParForward = csqs.available (dtMaturity, forwardLabel) ? csqs.fixing (dtMaturity,
				forwardLabel) : fc.forward (dtMaturity);

			double dblMultiplicativeQuantoAdjustment = java.lang.Math.exp
				(org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (csqs.forwardVolatility
					(forwardLabel), csqs.fundingVolatility (fundingLabel), csqs.forwardFundingCorrelation
						(forwardLabel, fundingLabel), iValueDate, iEffectiveDate));

			double dblDCF = org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate,
				iMaturityDate, stream().couponDC(), false, null, stream().calendar());

			double dblQuantoAdjustedParForward = dblParForward * dblMultiplicativeQuantoAdjustment;

			double dblDV01 = 0.0001 * dblDCF * dcFunding.df (iMaturityDate) / dcFunding.df (dblCashSettle)
				* notional (iValueDate);

			double dblLevelLift = dblQuantoAdjustedParForward - _dblStrike;
			double dblPV = dblDV01 * dblLevelLift;
			double dblLevelCapLift = dblLevelLift < 0. ? -1. * dblLevelLift : 0.;
			double dblLevelFloorLift = dblLevelLift > 0. ? dblLevelLift : 0.;

			double dblDCParForward = dcFunding.libor (iEffectiveDate, iMaturityDate);

			mapResult.put ("additivequantoadjustment", dblQuantoAdjustedParForward - dblParForward);

			mapResult.put ("caplift", dblLevelCapLift);

			mapResult.put ("discountcurveadditivebasis", dblQuantoAdjustedParForward - dblDCParForward);

			mapResult.put ("discountcurvemultiplicativebasis", dblQuantoAdjustedParForward /
				dblDCParForward);

			mapResult.put ("discountcurveparforward", dblDCParForward);

			mapResult.put ("dv01", dblDV01);

			mapResult.put ("floorlift", dblLevelFloorLift);

			mapResult.put ("forward", dblParForward);

			mapResult.put ("forwardrate", dblParForward);

			mapResult.put ("mercuriorfactor", (dblDCF * dblDCParForward + 1.) / (dblDCF *
				dblQuantoAdjustedParForward + 1.));

			mapResult.put ("multiplicativequantoadjustment", dblMultiplicativeQuantoAdjustment);

			mapResult.put ("parforward", dblParForward);

			mapResult.put ("parforwardrate", dblParForward);

			mapResult.put ("price", dblPV);

			mapResult.put ("pv", dblPV);

			mapResult.put ("quantoadjustedparforward", dblQuantoAdjustedParForward);

			mapResult.put ("upfront", dblPV);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		mapResult.put ("calctime", (System.nanoTime() - lStart) * 1.e-09);

		return mapResult;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("AdditiveQuantoAdjustment");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("CapLift");

		setstrMeasureNames.add ("DiscountCurveAdditiveBasis");

		setstrMeasureNames.add ("DiscountCurveMultiplicativeBasis");

		setstrMeasureNames.add ("DiscountCurveParForward");

		setstrMeasureNames.add ("DV01");

		setstrMeasureNames.add ("FloorLift");

		setstrMeasureNames.add ("Forward");

		setstrMeasureNames.add ("ForwardRate");

		setstrMeasureNames.add ("MercurioRFactor");

		setstrMeasureNames.add ("MultiplicativeQuantoAdjustment");

		setstrMeasureNames.add ("ParForward");

		setstrMeasureNames.add ("ParForwardRate");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("QuantoAdjustedParForward");

		setstrMeasureNames.add ("Upfront");

		return setstrMeasureNames;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strManifestMeasure,
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
			return new org.drip.product.calib.FRAComponentQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

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

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs || !(pqs instanceof
			org.drip.product.calib.FRAComponentQuoteSet))
			return null;

		if (valParams.valueDate() > effectiveDate().julian()) return null;

		org.drip.product.calib.FRAComponentQuoteSet fcqs = (org.drip.product.calib.FRAComponentQuoteSet) pqs;

		if (!fcqs.containsFRARate() && !fcqs.containsParForwardRate()) return null;

		double dblForwardRate = java.lang.Double.NaN;

		try {
			if (fcqs.containsParForwardRate())
				dblForwardRate = fcqs.parForwardRate();
			else if (fcqs.containsFRARate())
				dblForwardRate = fcqs.fraRate();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		double dblMaturity = maturityDate().julian();

		if (!prwc.addPredictorResponseWeight (dblMaturity, 1.)) return null;

		if (!prwc.addDResponseWeightDManifestMeasure ("Rate", dblMaturity, 1.)) return null;

		if (!prwc.updateValue (dblForwardRate)) return null;

		if (!prwc.updateDValueDManifestMeasure ("Rate", 1.)) return null;

		return prwc;
	}

	/**
	 * Retrieve the FRA Strike
	 * 
	 * @return The FRA Strike
	 */

	public double strike()
	{
		return _dblStrike;
	}
}
