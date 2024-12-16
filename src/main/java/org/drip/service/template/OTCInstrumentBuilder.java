
package org.drip.service.template;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.analytics.support.Helper;
import org.drip.market.otc.FixedFloatSwapConvention;
import org.drip.market.otc.FloatFloatSwapConvention;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.market.otc.IBORFloatFloatContainer;
import org.drip.market.otc.OvernightFixedFloatContainer;
import org.drip.param.period.ComposableFloatingUnitSetting;
import org.drip.param.period.CompositePeriodSetting;
import org.drip.pricer.option.BlackScholesAlgorithm;
import org.drip.product.creator.CDSBuilder;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.product.fra.FRAStandardCapFloor;
import org.drip.product.fra.FRAStandardComponent;
import org.drip.product.fx.FXForwardComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.product.params.LastTradingDateSetting;
import org.drip.product.rates.FixFloatComponent;
import org.drip.product.rates.FloatFloatComponent;
import org.drip.product.rates.SingleStreamComponent;
import org.drip.product.rates.Stream;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.identifier.OvernightLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>OTCInstrumentBuilder</i> contains static Helper API to facilitate Construction of OTC Instruments. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct an OTC Funding Deposit Instrument from the Spot Date and the Maturity Tenor</li>
 * 		<li>Construct an OTC Forward Deposit Instrument from Spot Date and the Maturity Tenor</li>
 * 		<li>Construct an OTC Overnight Deposit Instrument from the Spot Date and the Maturity Tenor</li>
 * 		<li>Create a Standard FRA from the Spot Date, the Forward Label, and the Strike</li>
 * 		<li>Construct an OTC Standard Fix Float Swap using the specified Input Parameters</li>
 * 		<li>Construct a Standard Fix Float Swap Instances</li>
 * 		<li>Construct an Instance of OTC OIS Fix Float Swap</li>
 * 		<li>Construct an OTC Float-Float Swap Instance</li>
 * 		<li>Create an Instance of the OTC CDS</li>
 * 		<li>Create an OTC FX Forward Component</li>
 * 		<li>Construct an Array of OTC Funding Deposit Instruments from their corresponding Maturity Tenors</li>
 * 		<li>Construct an Array of OTC Funding Deposit and Futures Instruments</li>
 * 		<li>Construct an Array of OTC Forward Deposit Instruments from the corresponding Maturity Tenors</li>
 * 		<li>Construct an Array of OTC Overnight Deposit Instrument from their Maturity Tenors</li>
 * 		<li>Create an Array of Standard FRAs from the Spot Date, the Forward Label, and the Strike</li>
 * 		<li>Construct an Array of OTC Fix Float Swaps using the specified Input Parameters</li>
 * 		<li>Construct an Array of Custom Fix Float Swap Instances</li>
 * 		<li>Construct an Array of OTC Fix Float OIS Instances</li>
 * 		<li>Construct an Array of OTC OIS Fix-Float Futures</li>
 * 		<li>Construct an Array of OTC Float-Float Swap Instances</li>
 * 		<li>Create an Array of the OTC CDS Instance</li>
 * 		<li>Create an Array of OTC FX Forward Components</li>
 * 		<li>Construct an Instance of the Standard OTC FRA Cap/Floor</li>
 * 		<li>Construct the Array of Standard OTC FRA Cap/Floors</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/template/README.md">Curve Construction Product Builder Templates</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OTCInstrumentBuilder
{

	/**
	 * Construct an OTC Funding Deposit Instrument from the Spot Date and the Maturity Tenor
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param maturityTenor The Maturity Tenor
	 * 
	 * @return Funding Deposit Instrument Instance from the Spot Date and the corresponding Maturity Tenor
	 */

	public static final SingleStreamComponent FundingDeposit (
		final JulianDate spotDate,
		final String currency,
		final String maturityTenor)
	{
		if (null == spotDate ||
			null == currency || currency.isEmpty() ||
			null == maturityTenor || maturityTenor.isEmpty())
		{
			return null;
		}

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (
				currency,
				"ALL",
				maturityTenor,
				"MAIN"
			);

		if (null == fixedFloatSwapConvention) {
			return null;
		}

		ForwardLabel forwardLabel = fixedFloatSwapConvention.floatStreamConvention().floaterIndex();

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		String floaterTenor = forwardLabel.tenor();

		try {
			JulianDate maturityDate = maturityTenor.contains ("D") ? new JulianDate (
				Convention.AddBusinessDays (
					effectiveDate.julian(),
					Helper.TenorToDays (maturityTenor),
					currency
				)
			) : effectiveDate.addTenorAndAdjust (
				maturityTenor,
				currency
			);

			return new SingleStreamComponent (
				"DEPOSIT_" + maturityTenor,
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.EdgePair (effectiveDate, maturityDate),
						new CompositePeriodSetting (
							Helper.TenorToFreq (floaterTenor),
							floaterTenor,
							currency,
							null,
							1.,
							null,
							null,
							null,
							null
						),
						new ComposableFloatingUnitSetting (
							floaterTenor,
							CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
							null,
							ForwardLabel.Create (currency, floaterTenor),
							CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
							0.
						)
					)
				),
				null
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an OTC Forward Deposit Instrument from Spot Date and the Maturity Tenor
	 * 
	 * @param spotDate The Spot Date
	 * @param maturityTenor The Maturity Tenor
	 * @param forwardLabel The Forward Label
	 * 
	 * @return Forward Deposit Instrument Instance from the Spot Date and the corresponding Maturity Tenor
	 */

	public static final SingleStreamComponent ForwardRateDeposit (
		final JulianDate spotDate,
		final String maturityTenor,
		final ForwardLabel forwardLabel)
	{
		if (null == spotDate || null == forwardLabel) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, forwardLabel.currency());

		return null == effectiveDate? null : SingleStreamComponentBuilder.Deposit (
			effectiveDate,
			effectiveDate.addTenor (maturityTenor),
			forwardLabel
		);
	}

	/**
	 * Construct an OTC Overnight Deposit Instrument from the Spot Date and the Maturity Tenor
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param maturityTenor The Maturity Tenor
	 * 
	 * @return Overnight Deposit Instrument Instance from the Spot Date and the corresponding Maturity Tenor
	 */

	public static final SingleStreamComponent OvernightDeposit (
		final JulianDate spotDate,
		final String currency,
		final String maturityTenor)
	{
		if (null == spotDate) {
			return null;
		}

		OvernightLabel overnightLabel = OvernightLabel.Create (currency);

		if (null == overnightLabel) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		return null == effectiveDate ? null : SingleStreamComponentBuilder.Deposit (
			effectiveDate,
			effectiveDate.addTenorAndAdjust (maturityTenor, currency),
			overnightLabel
		);
	}

	/**
	 * Create a Standard FRA from the Spot Date, the Forward Label, and the Strike
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel The Forward Label
	 * @param maturityTenor Maturity Tenor
	 * @param strike Futures Strike
	 * 
	 * @return The Standard FRA Instance
	 */

	public static final FRAStandardComponent FRAStandard (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String maturityTenor,
		final double strike)
	{
		return null == spotDate || null == forwardLabel ? null : SingleStreamComponentBuilder.FRAStandard (
			spotDate.addBusDays (0, forwardLabel.currency()).addTenor (maturityTenor),
			forwardLabel,
			strike
		);
	}

	/**
	 * Construct an OTC Standard Fix Float Swap using the specified Input Parameters
	 * 
	 * @param spotDate The Spot Date
	 * @param currency The OTC Currency
	 * @param location Location
	 * @param maturityTenor Maturity Tenor
	 * @param index Index
	 * @param coupon Coupon
	 * 
	 * @return The OTC Standard Fix Float Swap constructed using the specified Input Parameters
	 */

	public static final FixFloatComponent FixFloatStandard (
		final JulianDate spotDate,
		final String currency,
		final String location,
		final String maturityTenor,
		final String index,
		final double coupon)
	{
		if (null == spotDate) {
			return null;
		}

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (
				currency,
				location,
				maturityTenor,
				index
			);

		return null == fixedFloatSwapConvention ? null :
			fixedFloatSwapConvention.createFixFloatComponent (
				spotDate.addBusDays (0, currency),
				maturityTenor,
				coupon,
				0.,
				1.
			);
	}

	/**
	 * Construct a Standard Fix Float Swap Instances
	 * 
	 * @param spotDate The Spot Date
	 * @param forwardLabel The Forward Label
	 * @param maturityTenor Maturity Tenor
	 * 
	 * @return A Standard Fix Float Swap Instances
	 */

	public static final FixFloatComponent FixFloatCustom (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String maturityTenor)
	{
		if (null == spotDate || null == forwardLabel) {
			return null;
		}

		String currency = forwardLabel.currency();

		String forwardTenor = forwardLabel.tenor();

		int tenorInMonths = Integer.parseInt (forwardTenor.split ("M")[0]);

		JulianDate effectiveDate = spotDate.addBusDays (0, currency).addDays (2);

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (
				currency,
				"ALL",
				maturityTenor,
				"MAIN"
			);

		if (null == fixedFloatSwapConvention) {
			return null;
		}

		try {
			FixFloatComponent fixFloatComponent = new FixFloatComponent (
				fixedFloatSwapConvention.fixedStreamConvention().createStream (
					effectiveDate,
					maturityTenor,
					0.,
					1.
				),
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.RegularEdgeDates (
							effectiveDate,
							forwardTenor,
							maturityTenor,
							null
						),
						new CompositePeriodSetting (
							12 / tenorInMonths,
							forwardTenor,
							currency,
							null,
							-1.,
							null,
							null,
							null,
							null
						),
						new
						ComposableFloatingUnitSetting (
							forwardTenor,
							CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
							null,
							forwardLabel,
							CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
							0.
						)
					)
				),
				null
			);

			fixFloatComponent.setPrimaryCode ("FixFloat:" + maturityTenor);

			return fixFloatComponent;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of OTC OIS Fix Float Swap
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param maturityTenor The OIS Maturity Tenor
	 * @param coupon The Fixed Coupon Rate
	 * @param useFundingCurve TRUE - Floater Based off of Fund
	 * 
	 * @return Instance of OIS Fix Float Swap
	 */

	public static final FixFloatComponent OISFixFloat (
		final JulianDate spotDate,
		final String currency,
		final String maturityTenor,
		final double coupon,
		final boolean useFundingCurve)
	{
		if (null == spotDate) {
			return null;
		}

		FixedFloatSwapConvention fixedFloatSwapConvention = useFundingCurve ?
			OvernightFixedFloatContainer.FundConventionFromJurisdiction (currency) :
			OvernightFixedFloatContainer.IndexConventionFromJurisdiction (currency, maturityTenor);

		return null == fixedFloatSwapConvention ? null : fixedFloatSwapConvention.createFixFloatComponent (
			spotDate.addBusDays (0, currency),
			maturityTenor,
			coupon,
			0.,
			1.
		);
	}

	/**
	 * Construct an OTC Float-Float Swap Instance
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency 
	 * @param derivedTenor Tenor of the Derived Leg
	 * @param maturityTenor Maturity Tenor of the Float-Float Swap
	 * @param basis The Float-Float Swap Basis
	 * 
	 * @return The OTC Float-Float Swap Instance
	 */

	public static final FloatFloatComponent FloatFloat (
		final JulianDate spotDate,
		final String currency,
		final String derivedTenor,
		final String maturityTenor,
		final double basis)
	{
		if (null == spotDate) {
			return null;
		}

		FloatFloatSwapConvention floatFloatSwapConvention =
			IBORFloatFloatContainer.ConventionFromJurisdiction (currency);

		return null == floatFloatSwapConvention ? null : floatFloatSwapConvention.createFloatFloatComponent (
			spotDate.addBusDays (0, currency),
			derivedTenor,
			maturityTenor,
			basis,
			1.
		);
	}

	/**
	 * Create an Instance of the OTC CDS
	 * 
	 * @param spotDate The Spot Date
	 * @param maturityTenor Maturity Tenor
	 * @param coupon Coupon
	 * @param currency Currency
	 * @param credit Credit Curve
	 * 
	 * @return The OTC CDS Instance
	 */

	public static final CreditDefaultSwap CDS (
		final JulianDate spotDate,
		final String maturityTenor,
		final double coupon,
		final String currency,
		final String credit)
	{
		if (null == spotDate || null == currency) {
			return null;
		}

		JulianDate firstCouponDate = spotDate.addBusDays (0, currency).nextCreditIMM (3);

		return null == firstCouponDate ? null : CDSBuilder.CreateCDS  (
			firstCouponDate.subtractTenor ("3M"),
			firstCouponDate.addTenor (maturityTenor),
			coupon,
			currency,
			"CAD".equalsIgnoreCase (currency) || "EUR".equalsIgnoreCase (currency) ||
				"GBP".equalsIgnoreCase (currency) || "HKD".equalsIgnoreCase (currency) ||
				"USD".equalsIgnoreCase (currency) ? 0.40 : 0.25,
			credit,
			currency,
			true
		);
	}

	/**
	 * Create an OTC FX Forward Component
	 * 
	 * @param spotDate Spot Date
	 * @param currencyPair Currency Pair
	 * @param maturityTenor Maturity Tenor
	 * 
	 * @return The OTC FX Forward Component Instance
	 */

	public static final FXForwardComponent FXForward (
		final JulianDate spotDate,
		final CurrencyPair currencyPair,
		final String maturityTenor)
	{
		if (null == spotDate || null == currencyPair) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, currencyPair.denomCcy());

		try {
			return new FXForwardComponent (
				"FXFWD::" + currencyPair.code() + "::" + maturityTenor,
				currencyPair,
				effectiveDate.julian(), effectiveDate.addTenor (maturityTenor).julian(),
				1.,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Array of OTC Funding Deposit Instruments from their corresponding Maturity Tenors
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param maturityTenorArray Array of Maturity Tenors
	 * 
	 * @return Array of OTC Funding Deposit Instruments from their corresponding Maturity Tenors
	 */

	public static final SingleStreamComponent[] FundingDeposit (
		final JulianDate spotDate,
		final String currency,
		final String[] maturityTenorArray)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		SingleStreamComponent[] depositComponentArray = new SingleStreamComponent[maturityTenorArray.length];

		for (int maturityTenorIndex = 0;
			maturityTenorIndex < maturityTenorArray.length;
			++maturityTenorIndex)
		{
			if (null == (
				depositComponentArray[maturityTenorIndex] = FundingDeposit (
					spotDate,
					currency,
					maturityTenorArray[maturityTenorIndex]
				)
			))
			{
				return null;
			}

			depositComponentArray[maturityTenorIndex].setPrimaryCode (maturityTenorArray[maturityTenorIndex]);
		}

		return depositComponentArray;
	}

	/**
	 * Construct an Array of OTC Funding Deposit and Futures Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param futuresCount Number of Serial Futures to be included
	 * 
	 * @return Array of OTC Funding Deposit Instruments from their corresponding Maturity Tenors
	 */

	public static final SingleStreamComponent[] FundingDepositFutures (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final int futuresCount)
	{
		if (null == depositMaturityTenorArray || 0 == depositMaturityTenorArray.length) {
			return null;
		}

		SingleStreamComponent[] depositFuturesComponentArray = new
			SingleStreamComponent[depositMaturityTenorArray.length + futuresCount];

		for (int depositIndex = 0; depositIndex < depositMaturityTenorArray.length; ++depositIndex) {
			if (null == (
				depositFuturesComponentArray[depositIndex] = FundingDeposit (
					spotDate,
					currency,
					depositMaturityTenorArray[depositIndex]
				)
			))
			{
				return null;
			}

			depositFuturesComponentArray[depositIndex].setPrimaryCode (
				depositMaturityTenorArray[depositIndex]
			);
		}

		if (0 == futuresCount) {
			return depositFuturesComponentArray;
		}

		SingleStreamComponent[] futuresComponentArray = ExchangeInstrumentBuilder.ForwardRateFuturesPack (
			spotDate,
			futuresCount,
			currency
		);

		if (null == futuresComponentArray) {
			return null;
		}

		for (int depositIndex = depositMaturityTenorArray.length;
			depositIndex < depositMaturityTenorArray.length + futuresCount;
			++depositIndex)
		{
			if (null == (
				depositFuturesComponentArray[depositIndex] =
					futuresComponentArray[depositIndex - depositMaturityTenorArray.length]
			))
			{
				return null;
			}
		}

		return depositFuturesComponentArray;
	}

	/**
	 * Construct an Array of OTC Forward Deposit Instruments from the corresponding Maturity Tenors
	 * 
	 * @param spotDate Spot Date
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param forwardLabel The Forward Label
	 * 
	 * @return Forward Deposit Instrument Instance from the corresponding Maturity Tenor
	 */

	public static final SingleStreamComponent[] ForwardRateDeposit (
		final JulianDate spotDate,
		final String[] maturityTenorArray,
		final ForwardLabel forwardLabel)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		SingleStreamComponent[] depositComponentArray = new SingleStreamComponent[maturityTenorArray.length];

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				depositComponentArray[maturityIndex] = ForwardRateDeposit (
					spotDate,
					maturityTenorArray[maturityIndex],
					forwardLabel
				)
			))
			{
				return null;
			}

			depositComponentArray[maturityIndex].setPrimaryCode (maturityTenorArray[maturityIndex]);
		}

		return depositComponentArray;
	}

	/**
	 * Construct an Array of OTC Overnight Deposit Instrument from their Maturity Tenors
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param maturityTenorArray Array of Maturity Tenor
	 * 
	 * @return Array of Overnight Deposit Instrument from their Maturity Tenors
	 */

	public static final SingleStreamComponent[] OvernightDeposit (
		final JulianDate spotDate,
		final String currency,
		final String[] maturityTenorArray)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		SingleStreamComponent[] depositComponentArray = new SingleStreamComponent[maturityTenorArray.length];

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				depositComponentArray[maturityIndex] = OvernightDeposit (
					spotDate,
					currency,
					maturityTenorArray[maturityIndex]
				)
			))
			{
				return null;
			}
		}

		return depositComponentArray;
	}

	/**
	 * Create an Array of Standard FRAs from the Spot Date, the Forward Label, and the Strike
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel The Forward Label
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param fraStrikeArray Array of FRA Strikes
	 * 
	 * @return Array of Standard FRA Instances
	 */

	public static final FRAStandardComponent[] FRAStandard (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String[] maturityTenorArray,
		final double[] fraStrikeArray)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length || null == fraStrikeArray) {
			return null;
		}

		FRAStandardComponent[] fraStandardComponentArray =
			new FRAStandardComponent[maturityTenorArray.length];

		if (maturityTenorArray.length != fraStrikeArray.length) {
			return null;
		}

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				fraStandardComponentArray[maturityIndex] = FRAStandard (
					spotDate,
					forwardLabel,
					maturityTenorArray[maturityIndex],
					fraStrikeArray[maturityIndex]
				)
			))
			{
				return null;
			}
		}

		return fraStandardComponentArray;
	}

	/**
	 * Construct an Array of OTC Fix Float Swaps using the specified Input Parameters
	 * 
	 * @param spotDate The Spot Date
	 * @param currency The OTC Currency
	 * @param location Location
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param index Index
	 * @param coupon Coupon
	 * 
	 * @return The Array of OTC Fix Float Swaps
	 */

	public static final FixFloatComponent[] FixFloatStandard (
		final JulianDate spotDate,
		final String currency,
		final String location,
		final String[] maturityTenorArray,
		final String index,
		final double coupon)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		FixFloatComponent[] fixFloatComponentArray = new FixFloatComponent[maturityTenorArray.length];

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				fixFloatComponentArray[maturityIndex] = FixFloatStandard (
					spotDate,
					currency,
					location,
					maturityTenorArray[maturityIndex],
					index,
					0.
				)
			))
			{
				return null;
			}
		}

		return fixFloatComponentArray;
	}

	/**
	 * Construct an Array of Custom Fix Float Swap Instances
	 * 
	 * @param spotDate The Spot Date
	 * @param forwardLabel The Forward Label
	 * @param maturityTenorArray Array of Maturity Tenors
	 * 
	 * @return Array of Custom Fix Float Swap Instances
	 */

	public static final FixFloatComponent[] FixFloatCustom (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String[] maturityTenorArray)
	{
		if (null == spotDate || null == forwardLabel ||
			null == maturityTenorArray || 0 == maturityTenorArray.length)
		{
			return null;
		}

		CompositePeriodSetting floatingCompositePeriodSetting = null;
		ComposableFloatingUnitSetting composableFloatingUnitSetting = null;
		FixFloatComponent[] fixFloatComponentArray = new FixFloatComponent[maturityTenorArray.length];

		String currency = forwardLabel.currency();

		String forwardTenor = forwardLabel.tenor();

		int tenorInMonths = Integer.parseInt (forwardTenor.split ("M")[0]);

		JulianDate effectiveDate = spotDate.addBusDays (0, currency).addDays (2);

		try {
			composableFloatingUnitSetting = new ComposableFloatingUnitSetting (
				forwardTenor,
				CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
				null,
				forwardLabel,
				CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
				0.
			);

			floatingCompositePeriodSetting = new CompositePeriodSetting (
				12 / tenorInMonths,
				forwardTenor,
				currency,
				null,
				-1.,
				null,
				null,
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			FixedFloatSwapConvention fixedFloatSwapConvention =
				IBORFixedFloatContainer.ConventionFromJurisdiction (
					currency,
					"ALL",
					maturityTenorArray[maturityIndex],
					"MAIN"
				);

			if (null == fixedFloatSwapConvention) {
				return null;
			}

			try {
				fixFloatComponentArray[maturityIndex] = new FixFloatComponent (
					fixedFloatSwapConvention.fixedStreamConvention().createStream (
						effectiveDate,
						maturityTenorArray[maturityIndex],
						0.,
						1.
					),
					new Stream (
						CompositePeriodBuilder.FloatingCompositeUnit (
							CompositePeriodBuilder.RegularEdgeDates (
								effectiveDate,
								forwardTenor,
								maturityTenorArray[maturityIndex],
								null
							),
							floatingCompositePeriodSetting,
							composableFloatingUnitSetting
						)
					),
					null
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			fixFloatComponentArray[maturityIndex].setPrimaryCode (
				"FixFloat:" + maturityTenorArray[maturityIndex]
			);
		}

		return fixFloatComponentArray;
	}

	/**
	 * Construct an Array of OTC Fix Float OIS Instances
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param maturityTenorArray Array of OIS Maturity Tenors
	 * @param couponArray OIS Fixed Rate Coupon
	 * @param useFundingCurve TRUE - Floater Based off of Fund
	 * 
	 * @return Array of Fix Float OIS Instances
	 */

	public static final FixFloatComponent[] OISFixFloat (
		final JulianDate spotDate,
		final String currency,
		final String[] maturityTenorArray,
		final double[] couponArray,
		final boolean useFundingCurve)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		FixFloatComponent[] fixFloatOISComponentArray = new FixFloatComponent[maturityTenorArray.length];

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				fixFloatOISComponentArray[maturityIndex] = OISFixFloat (
					spotDate,
					currency,
					maturityTenorArray[maturityIndex],
					couponArray[maturityIndex],
					useFundingCurve
				)
			))
			{
				return null;
			}
		}

		return fixFloatOISComponentArray;
	}

	/**
	 * Construct an Array of OTC OIS Fix-Float Futures
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param effectiveTenorArray Array of Effective Tenors
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param couponArray Array of Coupons
	 * @param useFundingCurve TRUE - Floater Based off of Fund
	 * 
	 * @return Array of OIS Fix-Float Futures
	 */

	public static final FixFloatComponent[] OISFixFloatFutures (
		final JulianDate spotDate,
		final String currency,
		final String[] effectiveTenorArray,
		final String[] maturityTenorArray,
		final double[] couponArray,
		final boolean useFundingCurve)
	{
		if (null == spotDate ||
			null == effectiveTenorArray || 0 == effectiveTenorArray.length ||
			null == maturityTenorArray ||
			null == couponArray)
		{
			return null;
		}

		FixFloatComponent[] fixFloatComponentArray = new FixFloatComponent[effectiveTenorArray.length];

		if (effectiveTenorArray.length != maturityTenorArray.length ||
			effectiveTenorArray.length != couponArray.length)
		{
			return null;
		}

		for (int effectiveTenorIndex = 0;
			effectiveTenorIndex < effectiveTenorArray.length;
			++effectiveTenorIndex)
		{
			if (null == (
				fixFloatComponentArray[effectiveTenorIndex] = OISFixFloat (
					spotDate.addTenor (effectiveTenorArray[effectiveTenorIndex]),
					currency,
					maturityTenorArray[effectiveTenorIndex],
					couponArray[effectiveTenorIndex],
					useFundingCurve
				)
			))
			{
				return null;
			}
		}

		return fixFloatComponentArray;
	}

	/**
	 * Construct an Array of OTC Float-Float Swap Instances
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency 
	 * @param derivedTenor Tenor of the Derived Leg
	 * @param maturityTenorArray Array of the Float-Float Swap Maturity Tenors
	 * @param basis The Float-Float Swap Basis
	 * 
	 * @return Array of OTC Float-Float Swap Instances
	 */

	public static final FloatFloatComponent[] FloatFloat (
		final JulianDate spotDate,
		final String currency,
		final String derivedTenor,
		final String[] maturityTenorArray,
		final double basis)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		FloatFloatSwapConvention floatFloatSwapConvention =
			IBORFloatFloatContainer.ConventionFromJurisdiction (currency);

		FloatFloatComponent[] floatFloatComponentArray = new FloatFloatComponent[maturityTenorArray.length];

		if (null == floatFloatSwapConvention) {
			return null;
		}

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				floatFloatComponentArray[maturityIndex] =
					floatFloatSwapConvention.createFloatFloatComponent (
						spotDate,
						derivedTenor,
						maturityTenorArray[maturityIndex],
						basis,
						1.
					)
				)
			)
			{
				return null;
			}
		}

		return floatFloatComponentArray;
	}

	/**
	 * Create an Array of the OTC CDS Instance
	 * 
	 * @param spotDate Spot Date
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param couponArray Array of Coupon
	 * @param currency Currency
	 * @param credit Credit Curve
	 * 
	 * @return Array of OTC CDS Instances
	 */

	public static final CreditDefaultSwap[] CDS (
		final JulianDate spotDate,
		final String[] maturityTenorArray,
		final double[] couponArray,
		final String currency,
		final String credit)
	{
		if (null == spotDate ||
			null == currency ||
			null == maturityTenorArray || 0 == maturityTenorArray.length ||
			null == couponArray)
		{
			return null;
		}

		String calendar = currency;
		CreditDefaultSwap[] cdsArray = new CreditDefaultSwap[maturityTenorArray.length];

		if (maturityTenorArray.length != couponArray.length) {
			return null;
		}

		JulianDate firstCouponDate = spotDate.addBusDays (0, calendar).nextCreditIMM (3);

		if (null == firstCouponDate) {
			return null;
		}

		JulianDate effectiveDate = firstCouponDate.subtractTenor ("3M");

		if (null == effectiveDate) {
			return null;
		}

		double recovery = "CAD".equalsIgnoreCase (currency) || "EUR".equalsIgnoreCase (currency) ||
			"GBP".equalsIgnoreCase (currency) || "HKD".equalsIgnoreCase (currency) ||
			"USD".equalsIgnoreCase (currency) ? 0.40 : 0.25;

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			cdsArray[maturityIndex] = CDSBuilder.CreateCDS (
				effectiveDate,
				firstCouponDate.addTenor (maturityTenorArray[maturityIndex]),
				couponArray[maturityIndex],
				currency,
				recovery,
				credit,
				calendar,
				true
			);
		}

		return cdsArray;
	}

	/**
	 * Create an Array of OTC FX Forward Components
	 * 
	 * @param spotDate Spot Date
	 * @param currencyPair Currency Pair
	 * @param maturityTenorArray Array of Maturity Tenors
	 * 
	 * @return Array of OTC FX Forward Component Instances
	 */

	public static final FXForwardComponent[] FXForward (
		final JulianDate spotDate,
		final CurrencyPair currencyPair,
		final String[] maturityTenorArray)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length) {
			return null;
		}

		FXForwardComponent[] fxForwardComponentArray = new FXForwardComponent[maturityTenorArray.length];

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			fxForwardComponentArray[maturityIndex] = FXForward (
				spotDate,
				currencyPair,
				maturityTenorArray[maturityIndex]
			);
		}

		return fxForwardComponentArray;
	}

	/**
	 * Construct an Instance of the Standard OTC FRA Cap/Floor
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel The Forward Label
	 * @param maturityTenor Cap/Floor Maturity Tenor
	 * @param strike Cap/Floor Strike
	 * @param isCap TRUE - Contract is a Cap
	 * 
	 * @return The Cap/Floor Instance
	 */

	public static final FRAStandardCapFloor CapFloor (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String maturityTenor,
		final double strike,
		final boolean isCap)
	{
		if (null == spotDate || null == forwardLabel) {
			return null;
		}

		String currency = forwardLabel.currency();

		String forwardTenor = forwardLabel.tenor();

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		try {
			return new FRAStandardCapFloor (
				forwardLabel.fullyQualifiedName() + (isCap ? "::CAP" : "::FLOOR"),
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.RegularEdgeDates (
							effectiveDate.julian(),
							forwardTenor,
							maturityTenor,
							null
						),
						new CompositePeriodSetting (
							Helper.TenorToFreq (forwardTenor),
							forwardTenor,
							currency,
							null,
							1.,
							null,
							null,
							null,
							null
						),
						new ComposableFloatingUnitSetting (
							forwardTenor,
							CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
							null,
							forwardLabel,
							CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
							0.
						)
					)
				),
				"ParForward",
				isCap,
				strike,
				new LastTradingDateSetting (
					LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY,
					"",
					Integer.MIN_VALUE
				),
				null,
				new BlackScholesAlgorithm()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Array of Standard OTC FRA Cap/Floors
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel The Forward Label
	 * @param maturityTenorArray Array of Cap/Floor Maturity Tenors
	 * @param strikeArray Array of Cap/Floor Strikes
	 * @param isCap TRUE - Contract is a Cap
	 * 
	 * @return The Cap/Floor Array
	 */

	public static final FRAStandardCapFloor[] CapFloor (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String[] maturityTenorArray,
		final double[] strikeArray,
		final boolean isCap)
	{
		if (null == maturityTenorArray || 0 == maturityTenorArray.length || null == strikeArray) {
			return null;
		}

		FRAStandardCapFloor[] fraStandardCapFloorArray = new FRAStandardCapFloor[maturityTenorArray.length];

		if (maturityTenorArray.length != strikeArray.length) {
			return null;
		}

		for (int maturityIndex = 0; maturityIndex < maturityTenorArray.length; ++maturityIndex) {
			if (null == (
				fraStandardCapFloorArray[maturityIndex] = OTCInstrumentBuilder.CapFloor (
					spotDate,
					forwardLabel,
					maturityTenorArray[maturityIndex],
					strikeArray[maturityIndex],
					isCap
				)
			))
			{
				return null;
			}
		}

		return fraStandardCapFloorArray;
	}
}
