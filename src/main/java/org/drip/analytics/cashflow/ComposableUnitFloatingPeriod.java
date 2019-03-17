
package org.drip.analytics.cashflow;

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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * <i>ComposableUnitFloatingPeriod</i> contains the Floating Cash Flow Periods' Composable Period Details.
 * Currently it holds the Accrual Start Date, the Accrual End Date, the Fixing Date, the Spread over the
 * Index, and the corresponding Reference Index Period.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/README.md">Cash Flow</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableUnitFloatingPeriod extends org.drip.analytics.cashflow.ComposableUnitPeriod {
	private double _dblSpread = java.lang.Double.NaN;
	private org.drip.analytics.cashflow.ReferenceIndexPeriod _rip = null;

	private org.drip.analytics.date.JulianDate lookBackProjectionDate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.market.definition.OvernightIndex oi)
	{
		int iSkipBackDay = 0;
		org.drip.analytics.date.JulianDate dtFixing = null;

		org.drip.market.definition.FloaterIndex floaterIndex = forwardLabel.floaterIndex();

		int iLookBackProjectionWindow = oi.publicationLag();

		try {
			dtFixing = new org.drip.analytics.date.JulianDate (_rip.fixingDate());

			while (iSkipBackDay <= iLookBackProjectionWindow) {
				if (
					csqc.available (
						dtFixing,
						forwardLabel
					)
				)
				return dtFixing;

				if (null == (dtFixing = dtFixing.subtractBusDays (
					1,
					floaterIndex.calendar()
				)))
					return null;

				iSkipBackDay += 1;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double baseForwardRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
		throws java.lang.Exception
	{
		org.drip.market.definition.FloaterIndex floaterIndex = forwardLabel.floaterIndex();

		if (!(floaterIndex instanceof org.drip.market.definition.OvernightIndex)) {
			int iFixingDate = _rip.fixingDate();

			if (
				csqc.available (
					iFixingDate,
					forwardLabel
				)
			)
				return csqc.fixing (
					iFixingDate,
					forwardLabel
				);
		} else {
			org.drip.analytics.date.JulianDate dtValidFixing = lookBackProjectionDate (
				csqc,
				forwardLabel,
				(org.drip.market.definition.OvernightIndex) floaterIndex
			);

			if (null != dtValidFixing)
				return csqc.fixing (
					dtValidFixing,
					forwardLabel
				);
		}

		int iReferencePeriodEndDate = _rip.endDate();

		org.drip.state.forward.ForwardRateEstimator fre = csqc.forwardState (forwardLabel);

		if (null != fre) return fre.forward (iReferencePeriodEndDate);

		java.lang.String strForwardCurrency = forwardLabel.currency();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strForwardCurrency));

		if (null == dcFunding)
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseForwardRate => Cannot locate Funding Curve " +
					strForwardCurrency);

		int iEpochDate = dcFunding.epoch().julian();

		int iReferencePeriodStartDate = _rip.startDate();

		iReferencePeriodStartDate = iReferencePeriodStartDate > iEpochDate ? iReferencePeriodStartDate :
			iEpochDate;

		return dcFunding.libor (
			iReferencePeriodStartDate,
			iReferencePeriodEndDate,
			org.drip.analytics.daycount.Convention.YearFraction (
				iReferencePeriodStartDate,
				iReferencePeriodEndDate,
				floaterIndex.dayCount(),
				false,
				null,
				floaterIndex.calendar()
			)
		);
	}

	private double baseOTCFixFloatRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
		throws java.lang.Exception
	{
		int iFixingDate = _rip.fixingDate();

		if (csqc.available (
			iFixingDate,
			otcFixFloatLabel
		))
			return csqc.fixing (
				iFixingDate,
				otcFixFloatLabel
			);

		java.lang.String strCurrency = otcFixFloatLabel.currency();

		java.lang.String strOTCFixFloatMaturity = otcFixFloatLabel.fixFloatTenor();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strCurrency));

		if (null == dcFunding)
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseOTCFixFloatRate => Cannot locate Funding Curve " +
					strCurrency);

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (
				strCurrency,
				"ALL",
				strOTCFixFloatMaturity,
				"MAIN"
			);

		if (null == ffsc)
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseOTCFixFloatRate => Cannot locate Fix Float Convention!");

		int iReferencePeriodStartDate = _rip.startDate();

		org.drip.product.rates.FixFloatComponent ffc = ffsc.createFixFloatComponent (
			new org.drip.analytics.date.JulianDate (iReferencePeriodStartDate),
			strOTCFixFloatMaturity,
			0.,
			0.,
			1.
		);

		if (null == ffc)
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseOTCFixFloatRate => Cannot create Fix Float Component!");

		java.util.Map<java.lang.String, java.lang.Double> mapFFCOutput = ffc.value (
			org.drip.param.valuation.ValuationParams.Spot (iReferencePeriodStartDate),
			null,
			org.drip.param.creator.MarketParamsBuilder.Create (
				dcFunding,
				null,
				null,
				null,
				null,
				null,
				null
			),
			null
		);

		if (null == mapFFCOutput || !mapFFCOutput.containsKey ("SwapRate"))
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseOTCFixFloatRate => Cannot calculate Swap Rate!");

		return mapFFCOutput.get ("SwapRate");
	}

	/**
	 * The ComposableUnitFloatingPeriod Constructor
	 * 
	 * @param iStartDate Accrual Start Date
	 * @param iEndDate Accrual End Date
	 * @param strTenor The Composable Period Tenor
	 * @param rip The Reference Index Period
	 * @param dblSpread The Floater Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComposableUnitFloatingPeriod (
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strTenor,
		final org.drip.analytics.cashflow.ReferenceIndexPeriod rip,
		final double dblSpread)
		throws java.lang.Exception
	{
		super (
			iStartDate,
			iEndDate,
			strTenor,
			rip.floaterLabel().ucas()
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblSpread = dblSpread))
			throw new java.lang.Exception ("ComposableUnitFloatingPeriod Constructor => Invalid Inputs");

		_rip = rip;
	}

	/**
	 * Retrieve the Reference Rate for the Floating Period
	 * 
	 * @param csqc The Market Curve and Surface
	 * 
	 * @return The Reference Rate for the Floating Period
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	@Override public double baseRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		if (null == csqc) return java.lang.Double.NaN;

		org.drip.state.identifier.FloaterLabel floaterLabel = _rip.floaterLabel();

		if (floaterLabel instanceof org.drip.state.identifier.ForwardLabel)
			return baseForwardRate (
				csqc,
				(org.drip.state.identifier.ForwardLabel) _rip.floaterLabel()
			);

		if (floaterLabel instanceof org.drip.state.identifier.OTCFixFloatLabel)
			return baseOTCFixFloatRate (
				csqc,
				(org.drip.state.identifier.OTCFixFloatLabel) _rip.floaterLabel()
			);

		throw new java.lang.Exception
			("ComposableUnitFloatingPeriod::baseRate => Unknown Reference Period Index");
	}

	@Override public double basis()
	{
		return _dblSpread;
	}

	@Override public java.lang.String couponCurrency()
	{
		return _rip.floaterLabel().currency();
	}

	/**
	 * Retrieve the Reference Index Period
	 * 
	 * @return The Reference Index Period
	 */

	public org.drip.analytics.cashflow.ReferenceIndexPeriod referenceIndexPeriod()
	{
		return _rip;
	}
}
