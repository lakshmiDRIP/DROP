
package org.drip.param.market;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>CurveSurfaceScenarioContainer</i> extends MarketParams abstract class, and is the place holder for the
 * comprehensive suite of the market set of curves for the given date. It exports the following
 * functionality:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Add/remove/retrieve scenario discount curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario Forward curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario zero curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario credit curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario recovery curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario FXForward curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario FXBasis curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario fixings
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve Treasury/component quotes
 * 		</li>
 * 		<li>
 * 			Retrieve scenario Market Parameters
 * 		</li>
 * 		<li>
 * 			Retrieve map of flat rates/credit/recovery Market Parameters
 * 		</li>
 * 		<li>
 * 			Retrieve double map of tenor rates/credit/recovery Market Parameters
 * 		</li>
 * 		<li>
 *  		Retrieve rates/credit scenario generator
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/market/README.md">Curves Surfaces Quotes Fixings Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurveSurfaceScenarioContainer extends org.drip.param.definition.ScenarioMarketParams {
	private static final int BASE = 0;
	private static final int BUMP_UP = 1;
	private static final int BUMP_DN = 2;
	private static final int RR_BUMP_UP = 4;
	private static final int RR_BUMP_DN = 8;

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
		_mapTSYQuote = null;

	private org.drip.param.market.LatentStateFixingsContainer _lsfc = new
		org.drip.param.market.LatentStateFixingsContainer();

	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.DiscountCurveScenarioContainer>
		_mapScenarioDiscountCurve = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.DiscountCurveScenarioContainer>();

	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CreditCurveScenarioContainer>
		_mapScenarioCreditCurve = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CreditCurveScenarioContainer>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
		_mapQuote = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
		_mapScenarioMarketParams = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve> dcSet (
		final int iBumpType)
	{
		if (null == _mapScenarioDiscountCurve.entrySet()) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve> mapDC =
			new org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.DiscountCurveScenarioContainer>
			meSDC : _mapScenarioDiscountCurve.entrySet()) {
			java.lang.String strKey = meSDC.getKey();

			org.drip.param.market.DiscountCurveScenarioContainer sdc = meSDC.getValue();

			if (null == strKey || strKey.isEmpty() || null == sdc) continue;

			if (BASE == iBumpType)
				mapDC.put (strKey, sdc.base());
			else if (BUMP_UP == iBumpType)
				mapDC.put (strKey, sdc.bumpUp());
			else if (BUMP_DN == iBumpType)
				mapDC.put (strKey, sdc.bumpDown());
			}

		return mapDC;
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		ccSet (
			final int iBumpType)
	{
		if (null == _mapScenarioCreditCurve.entrySet()) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve> mapCC =
			new org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CreditCurveScenarioContainer> meSCC
			: _mapScenarioCreditCurve.entrySet()) {
			java.lang.String strKey = meSCC.getKey();

			org.drip.param.market.CreditCurveScenarioContainer scc = meSCC.getValue();

			if (null == strKey || strKey.isEmpty() || null == scc) continue;

			if (BASE == iBumpType)
				mapCC.put (strKey, scc.base());
			else if (BUMP_UP == iBumpType)
				mapCC.put (strKey, scc.bumpUp());
			else if (BUMP_DN == iBumpType)
				mapCC.put (strKey, scc.bumpDown());
			}

		return mapCC;
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		specificIRFlatBumpDCSet (
			final java.lang.String strIRCurve,
			final boolean bBumpUp)
	{
		if (null == strIRCurve || strIRCurve.isEmpty() || null == _mapScenarioDiscountCurve.get (strIRCurve))
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve> mapDC =
			dcSet (BASE);

		if (null == mapDC) return null;

		org.drip.param.market.DiscountCurveScenarioContainer dcsc = _mapScenarioDiscountCurve.get (strIRCurve);

		if (null == dcsc) return null;

		mapDC.put (strIRCurve, bBumpUp ? dcsc.bumpUp() : dcsc.bumpDown());

		return mapDC;
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		specificCreditFlatBumpCCSet (
			final java.lang.String strCreditCurve,
			final boolean bBumpUp)
	{
		if (null == strCreditCurve || strCreditCurve.isEmpty() || null == _mapScenarioCreditCurve.get
			(strCreditCurve))
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve> mapCC =
			ccSet (BASE);

		if (null == mapCC) return null;

		org.drip.param.market.CreditCurveScenarioContainer scc = _mapScenarioCreditCurve.get
			(strCreditCurve);

		if (null == scc) return null;

		mapCC.put (strCreditCurve, bBumpUp ? scc.bumpUp() : scc.bumpDown());

		return mapCC;
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		specificCreditFlatBumpRRSet (
			final java.lang.String strCreditCurve,
			final boolean bBumpUp)
	{
		if (null == strCreditCurve || strCreditCurve.isEmpty() || null == _mapScenarioCreditCurve.get
			(strCreditCurve))
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve> mapCC =
			ccSet (BASE);

		org.drip.param.market.CreditCurveScenarioContainer scc = _mapScenarioCreditCurve.get
			(strCreditCurve);

		if (null == scc) return null;

		mapCC.put (strCreditCurve, bBumpUp ? scc.bumpRecoveryUp() : scc.bumpRecoveryDown());

		return mapCC;
	}

	private org.drip.param.market.CurveSurfaceQuoteContainer customMarketParams (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapDC,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.forward.ForwardCurve> mapFC,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
			mapCC)
	{
		org.drip.param.market.CurveSurfaceQuoteContainer csqs = new org.drip.param.market.CurveSurfaceQuoteContainer();

		if (null != mapDC && 0 != mapDC.size()) {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve>
				meDC : mapDC.entrySet()) {
				if (null == meDC) continue;

				org.drip.state.discount.MergedDiscountForwardCurve dcFunding = meDC.getValue();

				if (null != dcFunding && !csqs.setFundingState (dcFunding)) return null;
			}
		}

		if (null != mapFC && 0 != mapFC.size()) {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.forward.ForwardCurve>
				meFC : mapFC.entrySet()) {
				if (null == meFC) continue;

				org.drip.state.forward.ForwardCurve fc = meFC.getValue();

				if (null != fc && !csqs.setForwardState (fc)) return null;
			}
		}

		if (null != mapCC && 0 != mapCC.size()) {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.credit.CreditCurve>
				meCC : mapCC.entrySet()) {
				if (null == meCC) continue;

				org.drip.state.credit.CreditCurve cc = meCC.getValue();

				if (null != cc && !csqs.setCreditState (cc)) return null;
			}
		}

		return csqs.setFixings (_lsfc) ? csqs : null;
	}

	/**
	 * Construct an empty MarketParamsContainer instance
	 */

	public CurveSurfaceScenarioContainer()
	{
	}

	@Override public boolean addScenarioDiscountCurve (
		final java.lang.String strName,
		final org.drip.param.market.DiscountCurveScenarioContainer sdc)
	{
		if (null != strName && !strName.isEmpty() && null != sdc) {
			_mapScenarioDiscountCurve.put (strName, sdc);

			return true;
		}

		return false;
	}

	@Override public boolean removeScenarioDiscountCurve (
		final java.lang.String strName)
	{
		if (null != strName && !strName.isEmpty()) {
			_mapScenarioDiscountCurve.remove (strName);

			return true;
		}

		return false;
	}

	@Override public boolean addScenarioCreditCurve (
		final java.lang.String strName,
		final org.drip.param.market.CreditCurveScenarioContainer scc)
	{
		if (null != strName && !strName.isEmpty() && null != scc) {
			_mapScenarioCreditCurve.put (strName, scc);

			return true;
		}

		return false;
	}

	@Override public boolean removeScenarioCreditCurve (
		final java.lang.String strName)
	{
		if (null != strName && !strName.isEmpty()) {
			_mapScenarioCreditCurve.remove (strName);

			return true;
		}

		return false;
	}

	@Override public boolean addTSYQuote (
		final java.lang.String strBenchmark,
		final org.drip.param.definition.ProductQuote pqTSY)
	{
		if (null == strBenchmark || strBenchmark.isEmpty() || null == pqTSY) return false;

		if (null == _mapTSYQuote)
			_mapTSYQuote = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>();

		_mapTSYQuote.put (strBenchmark, pqTSY);

		return true;
	}

	@Override public boolean removeTSYQuote (
		final java.lang.String strBenchmark)
	{
		if (null == strBenchmark || strBenchmark.isEmpty()) return false;

		if (null == _mapTSYQuote) return true;

		_mapTSYQuote.remove (strBenchmark);

		return true;
	}

	@Override public boolean setTSYQuotes (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote> mapCQTSY)
	{
		_mapTSYQuote = mapCQTSY;
		return true;
	}

	@Override public org.drip.param.definition.ProductQuote tsyQuote (
		final java.lang.String strBenchmark)
	{
		if (null == _mapTSYQuote || null == strBenchmark || strBenchmark.isEmpty()) return null;

		return _mapTSYQuote.get (strBenchmark);
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			tsyQuotes()
	{
		return _mapTSYQuote;
	}

	@Override public boolean addFixing (
		final org.drip.analytics.date.JulianDate dtFix,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		return _lsfc.add (dtFix, lsl, dblFixing);
	}

	@Override public boolean removeFixing (
		final org.drip.analytics.date.JulianDate dtFix,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return _lsfc.remove (dtFix, lsl);
	}

	@Override public org.drip.param.market.LatentStateFixingsContainer fixings()
	{
		return _lsfc;
	}

	@Override public boolean addComponentQuote (
		final java.lang.String strComponentID,
		final org.drip.param.definition.ProductQuote cqComponent)
	{
		if (null == strComponentID || strComponentID.isEmpty() || null == cqComponent) return false;

		if (null == _mapQuote)
			_mapQuote = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>();

		_mapQuote.put (strComponentID, cqComponent);

		return true;
	}

	@Override public boolean removeComponentQuote (
		final java.lang.String strComponentID)
	{
		if (null == strComponentID || strComponentID.isEmpty()) return false;

		if (null == _mapQuote) return true;

		_mapQuote.remove (strComponentID);

		return true;
	}

	@Override public boolean addComponentQuote (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			mapComponentQuote)
	{
		_mapQuote = mapComponentQuote;
		return true;
	}

	@Override public org.drip.param.definition.ProductQuote componentQuote (
		final java.lang.String strComponentID)
	{
		if (null == _mapQuote || null == strComponentID || strComponentID.isEmpty()) return null;

		return _mapQuote.get (strComponentID);
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			componentQuotes()
	{
		return _mapQuote;
	}

	@Override public boolean addScenarioMarketParams (
		final java.lang.String strScenarioName,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		if (null == strScenarioName || strScenarioName.isEmpty() || null == csqs) return false;

		_mapScenarioMarketParams.put (strScenarioName, csqs);

		return true;
	}

	@Override public org.drip.param.market.CurveSurfaceQuoteContainer scenarioMarketParams (
		final java.lang.String strScenarioName)
	{
		return null == strScenarioName || strScenarioName.isEmpty() ? null : _mapScenarioMarketParams.get
			(strScenarioName);
	}

	@Override public org.drip.param.market.CurveSurfaceQuoteContainer scenarioMarketParams (
		final org.drip.product.definition.Component comp,
		final java.lang.String strScenario)
	{
		if (null == comp || null == strScenario || strScenario.isEmpty()) return null;

		org.drip.state.forward.ForwardCurve fc = null;
		org.drip.state.discount.MergedDiscountForwardCurve dc = null;
		org.drip.state.credit.CreditCurve cc = null;

		java.lang.String strPayCurrency = comp.payCurrency();

		org.drip.param.market.DiscountCurveScenarioContainer sdc = _mapScenarioDiscountCurve.get
			(strPayCurrency);

		if (null != sdc) {
			dc = sdc.base();

			if ("FlatIRBumpUp".equalsIgnoreCase (strScenario))
				dc = sdc.bumpUp();
			else if ("FlatIRBumpDn".equalsIgnoreCase (strScenario))
				dc = sdc.bumpDown();
		}

		org.drip.state.identifier.EntityCDSLabel creditLabel = comp.creditLabel();

		org.drip.param.market.CreditCurveScenarioContainer scc = null == creditLabel ? null :
			_mapScenarioCreditCurve.get (creditLabel.fullyQualifiedName());

		if (null != scc) {
			if ("FlatCreditBumpUp".equalsIgnoreCase (strScenario))
				cc = scc.bumpUp();
			else if ("FlatCreditBumpDn".equalsIgnoreCase (strScenario))
				cc = scc.bumpDown();
			else
				cc = scc.base();
		}

		return org.drip.param.creator.MarketParamsBuilder.Create (dc, fc, null, cc, comp.name(),
			_mapQuote.get (comp.name()), _mapTSYQuote, _lsfc);
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			fundingTenorMarketParams (
				final org.drip.product.definition.Component comp,
				final boolean bBumpUp)
	{
		if (null == comp) return null;

		java.lang.String strPayCurrency = comp.payCurrency();

		org.drip.param.market.DiscountCurveScenarioContainer sdc = _mapScenarioDiscountCurve.get
			(strPayCurrency);

		if (null == sdc) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve> mapDCBumpUp
			= sdc.tenorBumpUp();

		if (bBumpUp && (null == mapDCBumpUp || 0 == mapDCBumpUp.size())) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapDCBumpDown = sdc.tenorBumpDown();

		if (!bBumpUp && (null == mapDCBumpDown || 0 == mapDCBumpDown.size())) return null;

		org.drip.state.identifier.EntityCDSLabel creditLabel = comp.creditLabel();

		org.drip.param.market.CreditCurveScenarioContainer scc = null == creditLabel ? null :
			_mapScenarioCreditCurve.get (creditLabel.fullyQualifiedName());

		org.drip.state.credit.CreditCurve cc = null == scc ? null : scc.base();

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer> mapCSQS
			= new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

		java.lang.String strComponentName = comp.name();

		if (bBumpUp) {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve> meDC :
				mapDCBumpUp.entrySet()) {
				if (null == meDC) continue;

				java.lang.String strKey = meDC.getKey();

				org.drip.state.discount.MergedDiscountForwardCurve dc = meDC.getValue();

				if (null == dc || null == strKey || strKey.isEmpty()) continue;

				mapCSQS.put (strKey, org.drip.param.creator.MarketParamsBuilder.Create (dc, null, null, cc,
					strComponentName, _mapQuote.get (strComponentName), _mapTSYQuote, _lsfc));
			}
		} else {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve> meDC :
				mapDCBumpDown.entrySet()) {
				if (null == meDC) continue;

				java.lang.String strKey = meDC.getKey();

				org.drip.state.discount.MergedDiscountForwardCurve dc = meDC.getValue();

				if (null == dc || null == strKey || strKey.isEmpty()) continue;

				mapCSQS.put (strKey, org.drip.param.creator.MarketParamsBuilder.Create (dc, null, null, cc,
					strComponentName, _mapQuote.get (strComponentName), _mapTSYQuote, _lsfc));
			}
		}

		return mapCSQS;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			creditTenorMarketParams (
				final org.drip.product.definition.Component comp,
				final boolean bBumpUp)
	{
		if (null == comp) return null;

		org.drip.state.identifier.EntityCDSLabel creditLabel = comp.creditLabel();

		if (null == creditLabel) return null;

		org.drip.param.market.CreditCurveScenarioContainer scc = _mapScenarioCreditCurve.get
			(creditLabel.fullyQualifiedName());

		if (null == scc) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
			mapCCBumpUp = scc.tenorBumpUp();

		if (bBumpUp && (null == mapCCBumpUp || 0 == mapCCBumpUp.size())) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
			mapCCBumpDown = scc.tenorBumpDown();

		if (!bBumpUp && (null == mapCCBumpDown || 0 == mapCCBumpDown.size())) return null;

		java.lang.String strPayCurrency = comp.payCurrency();

		org.drip.param.market.DiscountCurveScenarioContainer sdc = _mapScenarioDiscountCurve.get
			(strPayCurrency);

		if (null == sdc) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dc = sdc.base();

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQS = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

		java.lang.String strComponentName = comp.name();

		if (bBumpUp) {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.credit.CreditCurve> meCC :
				mapCCBumpUp.entrySet()) {
				if (null == meCC) continue;

				java.lang.String strKey = meCC.getKey();

				org.drip.state.credit.CreditCurve cc = meCC.getValue();

				if (null == strKey || strKey.isEmpty()) continue;

				mapCSQS.put (strKey, org.drip.param.creator.MarketParamsBuilder.Create (dc, null, null, cc,
					strComponentName, _mapQuote.get (strComponentName), _mapTSYQuote, _lsfc));
			}
		} else {
			for (java.util.Map.Entry<java.lang.String, org.drip.state.credit.CreditCurve> meCC :
				mapCCBumpDown.entrySet()) {
				if (null == meCC) continue;

				java.lang.String strKey = meCC.getKey();

				org.drip.state.credit.CreditCurve cc = meCC.getValue();

				if (null == strKey || strKey.isEmpty()) continue;

				mapCSQS.put (strKey, org.drip.param.creator.MarketParamsBuilder.Create (dc, null, null, cc,
					strComponentName, _mapQuote.get (strComponentName), _mapTSYQuote, _lsfc));
			}
		}

		return mapCSQS;
	}

	@Override public org.drip.param.market.CurveSurfaceQuoteContainer scenarioMarketParams (
		final org.drip.product.definition.BasketProduct bp,
		final java.lang.String strScenario)
	{
		if (null == strScenario || strScenario.isEmpty()) return null;

		if ("Base".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (BASE));

		if ("FlatIRBumpUp".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BUMP_UP), null, ccSet (BASE));

		if ("FlatIRBumpDn".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BUMP_DN), null, ccSet (BASE));

		if ("FlatForwardBumpUp".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (BASE));

		if ("FlatForwardBumpDn".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (BASE));

		if ("FlatCreditBumpUp".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (BUMP_UP));

		if ("FlatCreditBumpDn".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (BUMP_DN));

		if ("FlatRRBumpUp".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (RR_BUMP_UP));

		if ("FlatRRBumpDn".equalsIgnoreCase (strScenario))
			return customMarketParams (dcSet (BASE), null, ccSet (RR_BUMP_DN));

		return null;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			fundingFlatBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQS = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.DiscountCurveScenarioContainer>
			meSDC : _mapScenarioDiscountCurve.entrySet()) {
			if (null != meSDC) {
				java.lang.String strKey = meSDC.getKey();

				if (null != strKey && !strKey.isEmpty())
					mapCSQS.put (strKey, customMarketParams (specificIRFlatBumpDCSet (strKey, bBump), null,
						ccSet (BASE)));
			}
		}

		return mapCSQS;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			creditFlatBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQS = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CreditCurveScenarioContainer> meSCC
			: _mapScenarioCreditCurve.entrySet()) {
			if (null != meSCC) {
				java.lang.String strKey = meSCC.getKey();

				if (null != strKey && !strKey.isEmpty())
					mapCSQS.put (strKey, customMarketParams (dcSet (BASE), null, specificCreditFlatBumpCCSet
						(strKey, bBump)));
			}
		}

		return mapCSQS;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			recoveryFlatBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQS = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CreditCurveScenarioContainer> meSCC
			: _mapScenarioCreditCurve.entrySet()) {
			if (null != meSCC) {
				java.lang.String strKey = meSCC.getKey();

				if (null != strKey && !strKey.isEmpty())
					mapCSQS.put (strKey, customMarketParams (dcSet (BASE), null, specificCreditFlatBumpRRSet
						(strKey, bBump)));
			}
		}

		return mapCSQS;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>
			fundingTenorBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump)
	{
		if (null == bp) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>
			mmFundingTenorCSQS = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.DiscountCurveScenarioContainer>
			meSDC : _mapScenarioDiscountCurve.entrySet()) {
			if (null == meSDC) continue;

			java.lang.String strOuterKey = meSDC.getKey();

			org.drip.param.market.DiscountCurveScenarioContainer sdc = meSDC.getValue();

			if (null == sdc || null == strOuterKey || strOuterKey.isEmpty()) continue;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
				mapDCBumpUp = sdc.tenorBumpUp();

			if (bBump && (null == mapDCBumpUp || 0 == mapDCBumpUp.size())) return null;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
				mapDCBumpDown = sdc.tenorBumpDown();

			if (!bBump && (null == mapDCBumpDown || 0 == mapDCBumpDown.size())) return null;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
				mapTenorCSQS = new
					org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

			for (java.util.Map.Entry<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve> meDC : (bBump
				? mapDCBumpUp.entrySet() : mapDCBumpDown.entrySet())) {
				if (null == meDC) continue;

				java.lang.String strInnerKey = meDC.getKey();

				org.drip.state.discount.MergedDiscountForwardCurve dc = meDC.getValue();

				if (null == dc || null == strInnerKey || strInnerKey.isEmpty()) continue;

				org.drip.param.market.CurveSurfaceQuoteContainer csqs = scenarioMarketParams (bp, "Base");

				if (null == csqs || !csqs.setFundingState (dc)) continue;

				mapTenorCSQS.put (strInnerKey, csqs);
			}

			mmFundingTenorCSQS.put (strOuterKey, mapTenorCSQS);
		}

		return mmFundingTenorCSQS;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>
			creditTenorBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump)
	{
		if (null == bp) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>
			mmCreditTenorCSQS = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>();

		for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CreditCurveScenarioContainer> meSCC
			: _mapScenarioCreditCurve.entrySet()) {
			if (null == meSCC) continue;

			java.lang.String strOuterKey = meSCC.getKey();

			org.drip.param.market.CreditCurveScenarioContainer scc = meSCC.getValue();

			if (null == scc || null == strOuterKey || strOuterKey.isEmpty()) continue;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
				mapCCBumpUp = scc.tenorBumpUp();

			if (bBump && (null == mapCCBumpUp || 0 == mapCCBumpUp.size())) return null;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
				mapCCBumpDown = scc.tenorBumpDown();

			if (!bBump && (null == mapCCBumpDown || 0 == mapCCBumpDown.size())) return null;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
				mapTenorCSQS = new
					org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

			for (java.util.Map.Entry<java.lang.String, org.drip.state.credit.CreditCurve> meCC :
				(bBump ? mapCCBumpUp.entrySet() : mapCCBumpDown.entrySet())) {
				if (null == meCC) continue;

				java.lang.String strInnerKey = meCC.getKey();

				org.drip.state.credit.CreditCurve cc = meCC.getValue();

				if (null == cc || null == strInnerKey || strInnerKey.isEmpty()) continue;

				org.drip.param.market.CurveSurfaceQuoteContainer csqs = scenarioMarketParams (bp, "Base");

				if (null == csqs || !csqs.setCreditState (cc)) continue;

				mapTenorCSQS.put (strInnerKey, csqs);
			}

			mmCreditTenorCSQS.put (strOuterKey, mapTenorCSQS);
		}

		return mmCreditTenorCSQS;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.DiscountCurveScenarioContainer>
			scenarioDiscountCurveMap()
	{
		return _mapScenarioDiscountCurve;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CreditCurveScenarioContainer>
			scenarioCreditCurveMap()
	{
		return _mapScenarioCreditCurve;
	}
}
