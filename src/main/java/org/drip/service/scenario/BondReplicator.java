
package org.drip.service.scenario;

import java.util.Map;

import org.drip.analytics.cashflow.ComposableUnitFloatingPeriod;
import org.drip.analytics.cashflow.CompositeFloatingPeriod;
import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.analytics.support.Helper;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.definition.CalibrationParams;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.param.valuation.WorkoutInfo;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.CalibratableComponent;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.creator.ScenarioCreditCurveBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.EntityCDSLabel;
import org.drip.state.identifier.FloaterLabel;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.identifier.OTCFixFloatLabel;
import org.drip.state.sequence.GovvieBuilderSettings;

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
 * <i>BondReplicator</i> generates a Target Set of Sensitivity and Relative Value Runs. It provides the
 * 	following Functionality:
 *
 *  <ul>
 * 		<li>Empty <i>BondReplicationRun</i> Constructor</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/scenario/README.md">Custom Scenario Service Metric Generator</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BondReplicator
{

	/**
	 * Subordinate Corporate Recovery Rate
	 */

	public static final double CORPORATE_SUBORDINATE_RECOVERY_RATE = 0.20;

	/**
	 * Senior Corporate Recovery Rate
	 */

	public static final double CORPORATE_SENIOR_RECOVERY_RATE = 0.40;

	/**
	 * Loan Corporate Recovery Rate
	 */

	public static final double CORPORATE_LOAN_RECOVERY_RATE = 0.70;

	private int _settleLag = -1;
	private double _fx = Double.NaN;
	private String _govvieCode = "";
	private JulianDate _settleDate = null;
	private double _resetRate = Double.NaN;
	private double _tenorBump = Double.NaN;
	private double _issuePrice = Double.NaN;
	private double _issueAmount = Double.NaN;
	private double _zSpreadBump = Double.NaN;
	private JulianDate _valuationDate = null;
	private double _currentPrice = Double.NaN;
	private double _logNormalVolatility = 0.1;
	private double _recoveryRate = Double.NaN;
	private double[] _creditQuoteArray = null;
	private String[] _creditTenorArray = null;
	private double[] _govvieQuoteArray = null;
	private String[] _govvieTenorArray = null;
	private double[] _depositQuoteArray = null;
	private double[] _futuresQuoteArray = null;
	private int _resetDate = Integer.MIN_VALUE;
	private String[] _depositTenorArray = null;
	private BondComponent _bondComponent = null;
	private double[] _fixFloatQuoteArray = null;
	private String[] _fixFloatTenorArray = null;
	private double _customYieldBump = Double.NaN;
	private ValuationParams _valuationParams = null;
	private boolean _marketPriceCreditMetrics = false;
	private double _customCreditBasisBump = Double.NaN;
	private double _spreadDurationMultiplier = Double.NaN;
	private EOSMetricsReplicator _eosMetricsReplicator = null;
	private CurveSurfaceQuoteContainer _creditBaseCurveSurfaceQuoteContainer = null;
	private CurveSurfaceQuoteContainer _credit01UpCurveSurfaceQuoteContainer = null;
	private CurveSurfaceQuoteContainer _fundingBaseCurveSurfaceQuoteContainer = null;
	private CurveSurfaceQuoteContainer _funding01UpCurveSurfaceQuoteContainer = null;
	private Map<String, CurveSurfaceQuoteContainer> _curveSurfaceQuoteContainerMap = null;
	private CurveSurfaceQuoteContainer _euroDollarFundingCurveSurfaceQuoteContainer = null;

	private Map<String, CurveSurfaceQuoteContainer> _govvieUpCurveSurfaceQuoteContainerMap =
		new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

	private Map<String, CurveSurfaceQuoteContainer> _fundingUpCurveSurfaceQuoteContainerMap =
		new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

	private Map<String, CurveSurfaceQuoteContainer> _govvieDownCurveSurfaceQuoteContainerMap =
		new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

	private Map<String, CurveSurfaceQuoteContainer> _fundingDownCurveSurfaceQuoteContainerMap =
		new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

	private Map<String, CurveSurfaceQuoteContainer> _forwardFundingUpCurveSurfaceQuoteContainerMap =
		new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

	private Map<String, CurveSurfaceQuoteContainer> _forwardFundingDownCurveSurfaceQuoteContainerMap =
		new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

	/**
	 * Generate a Standard Subordinate Corporate BondReplicator Instance
	 * 
	 * @param currentPrice Current Price
	 * @param issuePrice Issue Price
	 * @param issueAmount Issue Amount
	 * @param spotDate Spot Date
	 * @param depositTenorArray Array of Deposit Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param fixFloatTenorArray Array of Fix-Float Tenors
	 * @param fixFloatQuoteArray Array of Fix-Float Quotes
	 * @param spreadBump Yield/Spread Bump
	 * @param spreadDurationMultiplier Spread Duration Multiplier
	 * @param govvieCode Govvie Code
	 * @param govvieTenorArray Array of Govvie Tenor
	 * @param govvieQuoteArray Array of Govvie Quotes
	 * @param creditTenorArray Array of Credit Tenors
	 * @param creditQuoteArray Array of Credit Quotes
	 * @param fx FX Rate Applicable
	 * @param resetRate Reset Rate Applicable
	 * @param settleLag Settlement Lag
	 * @param bondComponent Bond Component Instance
	 * 
	 * @return The Standard Subordinate BondReplicator Instance
	 */

	public static final BondReplicator CorporateSubordinate (
		final double currentPrice,
		final double issuePrice,
		final double issueAmount,
		final JulianDate spotDate,
		final String[] depositTenorArray,
		final double[] depositQuoteArray,
		final double[] futuresQuoteArray,
		final String[] fixFloatTenorArray,
		final double[] fixFloatQuoteArray,
		final double spreadBump,
		final double spreadDurationMultiplier,
		final String govvieCode,
		final String[] govvieTenorArray,
		final double[] govvieQuoteArray,
		final String[] creditTenorArray,
		final double[] creditQuoteArray,
		final double fx,
		final double resetRate,
		final int settleLag,
		final BondComponent bondComponent)
	{
		try {
			return new BondReplicator (
				currentPrice,
				issuePrice,
				issueAmount,
				spotDate,
				depositTenorArray,
				depositQuoteArray,
				futuresQuoteArray,
				fixFloatTenorArray,
				fixFloatQuoteArray,
				spreadBump,
				spreadBump,
				spreadBump,
				spreadBump,
				spreadDurationMultiplier,
				govvieCode,
				govvieTenorArray,
				govvieQuoteArray,
				true,
				creditTenorArray,
				creditQuoteArray,
				fx,
				resetRate,
				settleLag,
				CORPORATE_SUBORDINATE_RECOVERY_RATE,
				bondComponent
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Standard Senior Corporate BondReplicator Instance
	 * 
	 * @param currentPrice Current Price
	 * @param issuePrice Issue Price
	 * @param issueAmount Issue Amount
	 * @param spotDate Spot Date
	 * @param depositTenorArray Array of Deposit Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param fixFloatTenorArray Array of Fix-Float Tenors
	 * @param fixFloatQuoteArray Array of Fix-Float Quotes
	 * @param spreadBump Yield/Spread Bump
	 * @param spreadDurationMultiplier Spread Duration Multiplier
	 * @param govvieCode Govvie Code
	 * @param govvieTenorArray Array of Govvie Tenor
	 * @param govvieQuoteArray Array of Govvie Quotes
	 * @param creditTenorArray Array of Credit Tenors
	 * @param creditQuoteArray Array of Credit Quotes
	 * @param fx FX Rate Applicable
	 * @param resetRate Reset Rate Applicable
	 * @param settleLag Settlement Lag
	 * @param bondComponent Bond Component Instance
	 * 
	 * @return The Standard Senior BondReplicator Instance
	 */

	public static final BondReplicator CorporateSenior (
		final double currentPrice,
		final double issuePrice,
		final double issueAmount,
		final JulianDate spotDate,
		final String[] depositTenorArray,
		final double[] depositQuoteArray,
		final double[] futuresQuoteArray,
		final String[] fixFloatTenorArray,
		final double[] fixFloatQuoteArray,
		final double spreadBump,
		final double spreadDurationMultiplier,
		final String govvieCode,
		final String[] govvieTenorArray,
		final double[] govvieQuoteArray,
		final String[] creditTenorArray,
		final double[] creditQuoteArray,
		final double fx,
		final double resetRate,
		final int settleLag,
		final BondComponent bondComponent)
	{
		try {
			return new BondReplicator (
				currentPrice,
				issuePrice,
				issueAmount,
				spotDate,
				depositTenorArray,
				depositQuoteArray,
				futuresQuoteArray,
				fixFloatTenorArray,
				fixFloatQuoteArray,
				spreadBump,
				spreadBump,
				spreadBump,
				spreadBump,
				spreadDurationMultiplier,
				govvieCode,
				govvieTenorArray,
				govvieQuoteArray,
				true,
				creditTenorArray,
				creditQuoteArray,
				fx,
				resetRate,
				settleLag,
				CORPORATE_SENIOR_RECOVERY_RATE,
				bondComponent
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Standard Corporate Loan BondReplicator Instance
	 * 
	 * @param currentPrice Current Price
	 * @param issuePrice Issue Price
	 * @param issueAmount Issue Amount
	 * @param spotDate Spot Date
	 * @param depositTenorArray Array of Deposit Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param fixFloatTenorArray Array of Fix-Float Tenors
	 * @param fixFloatQuoteArray Array of Fix-Float Quotes
	 * @param spreadBump Yield/Spread Bump
	 * @param spreadDurationMultiplier Spread Duration Multiplier
	 * @param govvieCode Govvie Code
	 * @param govvieTenorArray Array of Govvie Tenor
	 * @param govvieQuoteArray Array of Govvie Quotes
	 * @param creditTenorArray Array of Credit Tenors
	 * @param creditQuoteArray Array of Credit Quotes
	 * @param fx FX Rate Applicable
	 * @param resetRate Reset Rate Applicable
	 * @param settleLag Settlement Lag
	 * @param bondComponent Bond Component Instance
	 * 
	 * @return The Standard Senior BondReplicator Instance
	 */

	public static final BondReplicator CorporateLoan (
		final double currentPrice,
		final double issuePrice,
		final double issueAmount,
		final JulianDate spotDate,
		final String[] depositTenorArray,
		final double[] depositQuoteArray,
		final double[] futuresQuoteArray,
		final String[] fixFloatTenorArray,
		final double[] fixFloatQuoteArray,
		final double spreadBump,
		final double spreadDurationMultiplier,
		final String govvieCode,
		final String[] govvieTenorArray,
		final double[] govvieQuoteArray,
		final String[] creditTenorArray,
		final double[] creditQuoteArray,
		final double fx,
		final double resetRate,
		final int settleLag,
		final BondComponent bondComponent)
	{
		try {
			return new BondReplicator (
				currentPrice,
				issuePrice,
				issueAmount,
				spotDate,
				depositTenorArray,
				depositQuoteArray,
				futuresQuoteArray,
				fixFloatTenorArray,
				fixFloatQuoteArray,
				spreadBump,
				spreadBump,
				spreadBump,
				spreadBump,
				spreadDurationMultiplier,
				govvieCode,
				govvieTenorArray,
				govvieQuoteArray,
				true,
				creditTenorArray,
				creditQuoteArray,
				fx,
				resetRate,
				settleLag,
				CORPORATE_LOAN_RECOVERY_RATE,
				bondComponent
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>BondReplicator</i> Constructor
	 * 
	 * @param currentPrice Current Price
	 * @param issuePrice Issue Price
	 * @param issueAmount Issue Amount
	 * @param valuationDate Value Date
	 * @param depositTenorArray Array of Deposit Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param fixFloatTenorArray Array of Fix-Float Tenors
	 * @param fixFloatQuoteArray Array of Fix-Float Quotes
	 * @param customYieldBump Custom Yield Bump
	 * @param customCreditBasisBump Custom Credit Basis Bump
	 * @param zSpreadBump Z Spread Bump
	 * @param tenorBump Tenor Bump
	 * @param spreadDurationMultiplier Spread Duration Multiplier
	 * @param govvieCode Govvie Code
	 * @param govvieTenorArray Array of Govvie Tenor
	 * @param govvieQuoteArray Array of Govvie Quotes
	 * @param marketPriceCreditMetrics Generate the Credit Metrics from the Market Price
	 * @param creditTenorArray Array of Credit Tenors
	 * @param creditQuoteArray Array of Credit Quotes
	 * @param fx FX Rate Applicable
	 * @param resetRate Reset Rate Applicable
	 * @param settleLag Settlement Lag
	 * @param recoveryRate Recovery Rate
	 * @param bondComponent Bond Component Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BondReplicator (
		final double currentPrice,
		final double issuePrice,
		final double issueAmount,
		final JulianDate valuationDate,
		final String[] depositTenorArray,
		final double[] depositQuoteArray,
		final double[] futuresQuoteArray,
		final String[] fixFloatTenorArray,
		final double[] fixFloatQuoteArray,
		final double customYieldBump,
		final double customCreditBasisBump,
		final double zSpreadBump,
		final double tenorBump,
		final double spreadDurationMultiplier,
		final String govvieCode,
		final String[] govvieTenorArray,
		final double[] govvieQuoteArray,
		final boolean marketPriceCreditMetrics,
		final String[] creditTenorArray,
		final double[] creditQuoteArray,
		final double fx,
		final double resetRate,
		final int settleLag,
		final double recoveryRate,
		final BondComponent bondComponent)
		throws Exception
	{
		if (!NumberUtil.IsValid (_currentPrice = currentPrice) ||
			!NumberUtil.IsValid (_issuePrice = issuePrice) ||
			!NumberUtil.IsValid (_issueAmount = issueAmount) ||
			null == (_valuationDate = valuationDate) ||
			!NumberUtil.IsValid (_fx = fx) || 0. >= _fx ||
			0 > (_settleLag = settleLag) ||
			!NumberUtil.IsValid (_recoveryRate = recoveryRate) || 0. >= _recoveryRate ||
			null == (_bondComponent = bondComponent))
		{
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		_resetRate = resetRate;
		_tenorBump = tenorBump;
		_govvieCode = govvieCode;
		_zSpreadBump = zSpreadBump;
		_customYieldBump = customYieldBump;
		JulianDate spotDate = valuationDate;
		_creditQuoteArray = creditQuoteArray;
		_creditTenorArray = creditTenorArray;
		_govvieQuoteArray = govvieQuoteArray;
		_govvieTenorArray = govvieTenorArray;
		_depositQuoteArray = depositQuoteArray;
		_depositTenorArray = depositTenorArray;
		_futuresQuoteArray = futuresQuoteArray;
		_fixFloatQuoteArray = fixFloatQuoteArray;
		_fixFloatTenorArray = fixFloatTenorArray;
		_customCreditBasisBump = customCreditBasisBump;
		_marketPriceCreditMetrics = marketPriceCreditMetrics;
		_spreadDurationMultiplier = spreadDurationMultiplier;

		String currency = _bondComponent.currency();

		if (null == (_settleDate = _valuationDate.addBusDays (_settleLag, currency))) {
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		_valuationParams = new ValuationParams (_valuationDate, _settleDate, currency);

		MergedDiscountForwardCurve discountCurve = LatentMarketStateBuilder.SmoothFundingCurve (
			spotDate,
			currency,
			_depositTenorArray,
			_depositQuoteArray,
			"ForwardRate",
			_futuresQuoteArray,
			"ForwardRate",
			_fixFloatTenorArray,
			_fixFloatQuoteArray,
			"SwapRate"
		);

		if (null == discountCurve) {
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		JulianDate[] spotDateArray = Helper.SpotDateArray (
			spotDate,
			null == _govvieTenorArray ? 0 : _govvieTenorArray.length
		);

		JulianDate[] maturityDateArray = Helper.FromTenor (spotDate, _govvieTenorArray);

		GovvieCurve govvieCurve = LatentMarketStateBuilder.GovvieCurve (
			_govvieCode,
			spotDate,
			spotDateArray,
			maturityDateArray,
			_govvieQuoteArray,
			_govvieQuoteArray,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);

		if (null == govvieCurve) {
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		boolean isBondFloater = _bondComponent.isFloater();

		if (isBondFloater) {
			CompositePeriod compositePeriod = _bondComponent.stream().containingPeriod (spotDate.julian());

			if (null != compositePeriod && compositePeriod instanceof CompositeFloatingPeriod) {
				_resetDate = ((ComposableUnitFloatingPeriod) (
					((CompositeFloatingPeriod) compositePeriod).periods().get (0)
				)).referenceIndexPeriod().fixingDate();
			}
		}

		if (null == (
			_fundingBaseCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
				discountCurve,
				govvieCurve,
				null,
				null,
				null,
				null,
				null
			)
		))
		{
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		FloaterLabel floaterLabel = isBondFloater ? _bondComponent.floaterSetting().fri() : null;

		if (isBondFloater && Integer.MIN_VALUE != _resetDate) {
			if (floaterLabel instanceof ForwardLabel) {
				if (!_fundingBaseCurveSurfaceQuoteContainer.setFixing (
					_resetDate,
					(ForwardLabel) floaterLabel,
					_resetRate
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			} else if (floaterLabel instanceof OTCFixFloatLabel) {
				if (!_fundingBaseCurveSurfaceQuoteContainer.setFixing (
					_resetDate,
					(OTCFixFloatLabel) floaterLabel,
					_resetRate
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			}
		}

		if (null == (
			_funding01UpCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
				LatentMarketStateBuilder.SmoothFundingCurve (
					spotDate,
					currency,
					_depositTenorArray,
					Helper.ParallelNodeBump (_depositQuoteArray, 0.0001),
					"ForwardRate",
					Helper.ParallelNodeBump (_futuresQuoteArray, 0.0001),
					"ForwardRate",
					_fixFloatTenorArray,
					Helper.ParallelNodeBump (_fixFloatQuoteArray, 0.0001),
					"SwapRate"
				),
				govvieCurve,
				null,
				null,
				null,
				null,
				null
			)
		))
		{
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		if (isBondFloater && Integer.MIN_VALUE != _resetDate) {
			if (floaterLabel instanceof ForwardLabel) {
				if (!_funding01UpCurveSurfaceQuoteContainer.setFixing (
					_resetDate,
					(ForwardLabel) floaterLabel,
					_resetRate /* + 0.0001 */
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			} else if (floaterLabel instanceof OTCFixFloatLabel) {
				if (!_funding01UpCurveSurfaceQuoteContainer.setFixing (
					_resetDate, (OTCFixFloatLabel) floaterLabel,
					_resetRate /* + 0.0001 */
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			}
		}

		if (null == (
			_euroDollarFundingCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
				LatentMarketStateBuilder.SmoothFundingCurve (
					spotDate,
					currency,
					_depositTenorArray,
					_depositQuoteArray,
					"ForwardRate",
					_futuresQuoteArray,
					"ForwardRate",
					null,
					null,
					"SwapRate"
				),
				govvieCurve,
				null,
				null,
				null,
				null,
				null
			)
		))
		{
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		if (isBondFloater && Integer.MIN_VALUE != _resetDate) {
			if (floaterLabel instanceof ForwardLabel) {
				if (!_euroDollarFundingCurveSurfaceQuoteContainer.setFixing (
					_resetDate,
					(ForwardLabel) floaterLabel,
					_resetRate
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			} else if (floaterLabel instanceof OTCFixFloatLabel) {
				if (!_euroDollarFundingCurveSurfaceQuoteContainer.setFixing (
					_resetDate,
					(OTCFixFloatLabel) floaterLabel,
					_resetRate
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			}
		}

		Map<String, MergedDiscountForwardCurve> tenorForwardFundingUpDiscountCurveMap =
			LatentMarketStateBuilder.BumpedForwardFundingCurve (
				spotDate,
				currency,
				_depositTenorArray,
				_depositQuoteArray,
				"ForwardRate",
				_futuresQuoteArray,
				"ForwardRate",
				_fixFloatTenorArray,
				_fixFloatQuoteArray,
				"SwapRate",
				LatentMarketStateBuilder.SHAPE_PRESERVING,
				0.0001 * _tenorBump,
				false
			);

		Map<String, MergedDiscountForwardCurve> tenorForwardFundingDownDiscountCurveMap =
			LatentMarketStateBuilder.BumpedForwardFundingCurve (
				spotDate,
				currency,
				_depositTenorArray,
				_depositQuoteArray,
				"ForwardRate",
				_futuresQuoteArray,
				"ForwardRate",
				_fixFloatTenorArray,
				_fixFloatQuoteArray,
				"SwapRate",
				LatentMarketStateBuilder.SHAPE_PRESERVING,
				-0.0001 * _tenorBump,
				false
			);

		if (null == tenorForwardFundingUpDiscountCurveMap || null == tenorForwardFundingDownDiscountCurveMap)
		{
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		for (Map.Entry<String, MergedDiscountForwardCurve> tenorForwardFundingUpDiscountCurveMapEntry :
			tenorForwardFundingUpDiscountCurveMap.entrySet())
		{
			String key = tenorForwardFundingUpDiscountCurveMapEntry.getKey();

			CurveSurfaceQuoteContainer forwardFundingTenorUpCurveSurfaceQuoteContainer =
				MarketParamsBuilder.Create (
					tenorForwardFundingUpDiscountCurveMapEntry.getValue(),
					govvieCurve,
					null,
					null,
					null,
					null,
					null
				);

			CurveSurfaceQuoteContainer forwardFundingTenorDownCurveSurfaceQuoteContainer =
				MarketParamsBuilder.Create (
					tenorForwardFundingDownDiscountCurveMap.get (key),
					govvieCurve,
					null,
					null,
					null,
					null,
					null
				);

			if (null == forwardFundingTenorUpCurveSurfaceQuoteContainer ||
				null == forwardFundingTenorDownCurveSurfaceQuoteContainer)
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			_forwardFundingUpCurveSurfaceQuoteContainerMap.put (
				key,
				forwardFundingTenorUpCurveSurfaceQuoteContainer
			);

			_forwardFundingDownCurveSurfaceQuoteContainerMap.put (
				key,
				forwardFundingTenorDownCurveSurfaceQuoteContainer
			);
		}

		Map<String, MergedDiscountForwardCurve> tenorFundingUpDiscountCurveMap =
			LatentMarketStateBuilder.BumpedFundingCurve (
				spotDate,
				currency,
				_depositTenorArray,
				_depositQuoteArray,
				"ForwardRate",
				_futuresQuoteArray,
				"ForwardRate",
				_fixFloatTenorArray,
				_fixFloatQuoteArray,
				"SwapRate",
				LatentMarketStateBuilder.SMOOTH,
				0.0001 * _tenorBump,
				false
			);

		Map<String, MergedDiscountForwardCurve> tenorFundingDownDiscountCurveMap =
			LatentMarketStateBuilder.BumpedFundingCurve (
				spotDate,
				currency,
				_depositTenorArray,
				_depositQuoteArray,
				"ForwardRate",
				_futuresQuoteArray,
				"ForwardRate",
				_fixFloatTenorArray,
				_fixFloatQuoteArray,
				"SwapRate",
				LatentMarketStateBuilder.SMOOTH,
				-0.0001 * _tenorBump,
				false
			);

		if (null == tenorFundingUpDiscountCurveMap || null == tenorFundingDownDiscountCurveMap) {
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		for (Map.Entry<String, MergedDiscountForwardCurve> tenorFundingUpDiscountCurveMapEntry :
			tenorFundingUpDiscountCurveMap.entrySet())
		{
			String key = tenorFundingUpDiscountCurveMapEntry.getKey();

			CurveSurfaceQuoteContainer fundingTenorUpCurveSurfaceQuoteContainer =
				MarketParamsBuilder.Create (
					tenorFundingUpDiscountCurveMapEntry.getValue(),
					govvieCurve,
					null,
					null,
					null,
					null,
					null
				);

			CurveSurfaceQuoteContainer fundingTenorDownCurveSurfaceQuoteContainer =
				MarketParamsBuilder.Create (
					tenorFundingDownDiscountCurveMap.get (key),
					govvieCurve,
					null,
					null,
					null,
					null,
					null
				);

			if (null == fundingTenorUpCurveSurfaceQuoteContainer ||
				null == fundingTenorDownCurveSurfaceQuoteContainer)
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			if (isBondFloater && Integer.MIN_VALUE != _resetDate) {
				if (floaterLabel instanceof ForwardLabel) {
					if (!fundingTenorUpCurveSurfaceQuoteContainer.setFixing (
						_resetDate,
						(ForwardLabel) floaterLabel,
						_resetRate /* + 0.0001 * _tenorBump */
					))
					{
						throw new Exception ("BondReplicator Constructor => Invalid Inputs");
					}

					if (!fundingTenorDownCurveSurfaceQuoteContainer.setFixing (
						_resetDate,
						(ForwardLabel) floaterLabel,
						_resetRate /* - 0.0001 * _tenorBump */
					))
					{
						throw new Exception ("BondReplicator Constructor => Invalid Inputs");
					}
				} else if (floaterLabel instanceof OTCFixFloatLabel) {
					if (!fundingTenorUpCurveSurfaceQuoteContainer.setFixing (
						_resetDate,
						(OTCFixFloatLabel) floaterLabel,
						_resetRate + 0.0001 * _tenorBump
					))
					{
						throw new Exception ("BondReplicator Constructor => Invalid Inputs");
					}

					if (!fundingTenorDownCurveSurfaceQuoteContainer.setFixing (
						_resetDate,
						(OTCFixFloatLabel) floaterLabel,
						_resetRate - 0.0001 * _tenorBump
					))
					{
						throw new Exception ("BondReplicator Constructor => Invalid Inputs");
					}
				}
			}

			_fundingUpCurveSurfaceQuoteContainerMap.put (key, fundingTenorUpCurveSurfaceQuoteContainer);

			_fundingDownCurveSurfaceQuoteContainerMap.put (key, fundingTenorDownCurveSurfaceQuoteContainer);
		}

		Map<String, GovvieCurve> tenorUpGovvieCurveMap = LatentMarketStateBuilder.BumpedGovvieCurve (
			_govvieCode,
			spotDate,
			spotDateArray,
			maturityDateArray,
			_govvieQuoteArray,
			_govvieQuoteArray,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING,
			0.0001 * _tenorBump,
			false
		);

		Map<String, GovvieCurve> tenorDownGovvieCurveMap = LatentMarketStateBuilder.BumpedGovvieCurve (
			_govvieCode,
			spotDate,
			spotDateArray,
			maturityDateArray,
			_govvieQuoteArray,
			_govvieQuoteArray, "Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING,
			-0.0001 * _tenorBump,
			false
		);

		if (null == tenorUpGovvieCurveMap || null == tenorDownGovvieCurveMap) {
			throw new Exception ("BondReplicator Constructor => Invalid Inputs");
		}

		for (Map.Entry<String, GovvieCurve> tenorUpGovvieCurveMapEntry : tenorUpGovvieCurveMap.entrySet()) {
			String key = tenorUpGovvieCurveMapEntry.getKey();

			CurveSurfaceQuoteContainer govvieTenorUpCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
				discountCurve,
				tenorUpGovvieCurveMapEntry.getValue(),
				null,
				null,
				null,
				null,
				null
			);

			CurveSurfaceQuoteContainer govvieTenorDownCurveSurfaceQuoteContainer =
				MarketParamsBuilder.Create (
					discountCurve,
					tenorDownGovvieCurveMap.get (key),
					null,
					null,
					null,
					null,
					null
				);

			if (null == govvieTenorUpCurveSurfaceQuoteContainer ||
				null == govvieTenorDownCurveSurfaceQuoteContainer)
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			_govvieUpCurveSurfaceQuoteContainerMap.put (key, govvieTenorUpCurveSurfaceQuoteContainer);

			_govvieDownCurveSurfaceQuoteContainerMap.put (key, govvieTenorDownCurveSurfaceQuoteContainer);

			if ((isBondFloater && Integer.MIN_VALUE != _resetDate) &&
				(
					!govvieTenorUpCurveSurfaceQuoteContainer.setFixing (
						_resetDate,
						floaterLabel,
						_resetRate
					) || !govvieTenorDownCurveSurfaceQuoteContainer.setFixing (
						_resetDate,
						floaterLabel,
						_resetRate
					)
				)
			)
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}
		}

		EntityCDSLabel entityCDSLabel = _bondComponent.creditLabel();

		String referenceEntity = null != entityCDSLabel ? entityCDSLabel.referenceEntity() : null;

		if (null == referenceEntity) {
			return;
		}

		if (!_marketPriceCreditMetrics) {
			if (null == (
				_creditBaseCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
					discountCurve,
					govvieCurve,
					LatentMarketStateBuilder.CreditCurve (
						spotDate,
						referenceEntity,
						_creditTenorArray,
						_creditQuoteArray,
						_creditQuoteArray,
						"FairPremium",
						discountCurve
					),
					null,
					null,
					null,
					null
				)
			))
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			if (isBondFloater && !_creditBaseCurveSurfaceQuoteContainer.setFixing (
				_resetDate,
				(ForwardLabel) floaterLabel,
				_resetRate
			))
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			if (null == (
				_credit01UpCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
					discountCurve,
					govvieCurve,
					LatentMarketStateBuilder.CreditCurve (
						spotDate,
						referenceEntity,
						_creditTenorArray,
						_creditQuoteArray,
						Helper.ParallelNodeBump (_creditQuoteArray, _tenorBump),
						"FairPremium",
						discountCurve
					),
					null,
					null,
					null,
					null
				)
			))
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			if (isBondFloater && !_credit01UpCurveSurfaceQuoteContainer.setFixing (
				_resetDate,
				floaterLabel,
				_resetRate
			))
			{
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			Map<String, CreditCurve> tenorCreditCurveMap = LatentMarketStateBuilder.BumpedCreditCurve (
				spotDate,
				referenceEntity,
				_creditTenorArray,
				_creditQuoteArray,
				_creditQuoteArray,
				"FairPremium",
				discountCurve,
				_tenorBump,
				false
			);

			if (null == tenorCreditCurveMap) {
				throw new Exception ("BondReplicator Constructor => Invalid Inputs");
			}

			_curveSurfaceQuoteContainerMap = new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

			for (Map.Entry<String, CreditCurve> tenorCreditCurveMapEntry : tenorCreditCurveMap.entrySet()) {
				CurveSurfaceQuoteContainer creditTenorCurveSurfaceQuoteContainer =
					MarketParamsBuilder.Create (
						discountCurve,
						govvieCurve,
						tenorCreditCurveMapEntry.getValue(),
						null,
						null,
						null,
						null
					);

				if (null == creditTenorCurveSurfaceQuoteContainer) {
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}

				_curveSurfaceQuoteContainerMap.put (
					tenorCreditCurveMapEntry.getKey(),
					creditTenorCurveSurfaceQuoteContainer
				);

				if (isBondFloater && !creditTenorCurveSurfaceQuoteContainer.setFixing (
					_resetDate,
					floaterLabel,
					_resetRate
				))
				{
					throw new Exception ("BondReplicator Constructor => Invalid Inputs");
				}
			}
		} else {
			CreditCurve baseCreditCurve = ScenarioCreditCurveBuilder.Custom (
				referenceEntity,
				spotDate,
				new CalibratableComponent[] {bondComponent},
				discountCurve,
				new double[] {_currentPrice},
				new String[] {"Price"},
				_recoveryRate,
				false,
				new CalibrationParams (
					"Price",
					0,
					_bondComponent.exerciseYieldFromPrice (
						_valuationParams,
						_fundingBaseCurveSurfaceQuoteContainer,
						null,
						_currentPrice
					)
				)
			);

			if (null == baseCreditCurve ||
				null == (
					_creditBaseCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
						discountCurve,
						govvieCurve,
						baseCreditCurve,
						null,
						null,
						null,
						null
					)
				)
			)
			{
				return;
			}

			if (isBondFloater) {
				if (null != floaterLabel && NumberUtil.IsValid (_resetRate)) {
					if (floaterLabel instanceof ForwardLabel) {
						if (!_creditBaseCurveSurfaceQuoteContainer.setFixing (
							_resetDate,
							(ForwardLabel) floaterLabel,
							_resetRate
						))
						{
							throw new Exception ("BondReplicator Constructor => Invalid Inputs");
						}
					} else if (floaterLabel instanceof OTCFixFloatLabel) {
						if (!_creditBaseCurveSurfaceQuoteContainer.setFixing (
							_resetDate,
							(OTCFixFloatLabel) floaterLabel,
							_resetRate
						))
						{
							throw new Exception ("BondReplicator Constructor => Invalid Inputs");
						}
					}
				}
			}

			_credit01UpCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
				discountCurve, govvieCurve,
				ScenarioCreditCurveBuilder.FlatHazard (
					spotDate.julian(),
					referenceEntity,
					currency,
					baseCreditCurve.hazard (bondComponent.maturityDate()) + 0.0001,
					_recoveryRate
				),
				null,
				null,
				null,
				null
			);
		}

		_eosMetricsReplicator = !_bondComponent.callable() && _bondComponent.putable() ? null :
			EOSMetricsReplicator.Standard (
				_bondComponent,
				_valuationParams,
				_fundingBaseCurveSurfaceQuoteContainer,
				new GovvieBuilderSettings (
					spotDate,
					_govvieCode,
					_govvieTenorArray,
					_govvieQuoteArray,
					_govvieQuoteArray
				),
				_logNormalVolatility,
				_currentPrice
			);
	}

	/**
	 * Retrieve the Bond Current Market Price
	 * 
	 * @return The Bond Current Market Price
	 */

	public double currentPrice()
	{
		return _currentPrice;
	}

	/**
	 * Retrieve the Bond Issue Price
	 * 
	 * @return The Bond Issue Price
	 */

	public double issuePrice()
	{
		return _issuePrice;
	}

	/**
	 * Retrieve the Bond Issue Amount
	 * 
	 * @return The Bond Issue Amount
	 */

	public double issueAmount()
	{
		return _issueAmount;
	}

	/**
	 * Retrieve the Value Date
	 * 
	 * @return The Value Date
	 */

	public JulianDate valueDate()
	{
		return _valuationDate;
	}

	/**
	 * Retrieve the Array of Deposit Instrument Maturity Tenors
	 * 
	 * @return The Array of Deposit Instrument Maturity Tenors
	 */

	public String[] depositTenor()
	{
		return _depositTenorArray;
	}

	/**
	 * Retrieve the Array of Deposit Instrument Quotes
	 * 
	 * @return The Array of Deposit Instrument Quotes
	 */

	public double[] depositQuote()
	{
		return _depositQuoteArray;
	}

	/**
	 * Retrieve the Array of Futures Instrument Quotes
	 * 
	 * @return The Array of Futures Instrument Quotes
	 */

	public double[] futuresQuote()
	{
		return _futuresQuoteArray;
	}

	/**
	 * Retrieve the Array of Fix-Float IRS Instrument Maturity Tenors
	 * 
	 * @return The Array of Fix-Float IRS Instrument Maturity Tenors
	 */

	public String[] fixFloatTenor()
	{
		return _fixFloatTenorArray;
	}

	/**
	 * Retrieve the Array of Fix-Float IRS Instrument Quotes
	 * 
	 * @return The Array of Fix-Float IRS Instrument Quotes
	 */

	public double[] fixFloatQuote()
	{
		return _fixFloatQuoteArray;
	}

	/**
	 * Retrieve the Recovery Rate
	 * 
	 * @return The Recovery Rate
	 */

	public double recoveryRate()
	{
		return _recoveryRate;
	}

	/**
	 * Retrieve the Custom Yield Bump
	 * 
	 * @return The Custom Yield Bump
	 */

	public double customYieldBump()
	{
		return _customYieldBump;
	}

	/**
	 * Retrieve the Custom Credit Basis Bump
	 * 
	 * @return The Custom Credit Basis Bump
	 */

	public double customCreditBasisBump()
	{
		return _customCreditBasisBump;
	}

	/**
	 * Retrieve the Z Spread Bump
	 * 
	 * @return The Z Spread Bump
	 */

	public double zSpreadBump()
	{
		return _zSpreadBump;
	}

	/**
	 * Retrieve the Tenor Quote Bump
	 * 
	 * @return The Tenor Quote Bump
	 */

	public double tenorBump()
	{
		return _tenorBump;
	}

	/**
	 * Retrieve the Spread Duration Multiplier
	 * 
	 * @return The Spread Duration Multiplier
	 */

	public double spreadDurationMultiplier()
	{
		return _spreadDurationMultiplier;
	}

	/**
	 * Retrieve the Govvie Code
	 * 
	 * @return The Govvie Code
	 */

	public String govvieCode()
	{
		return _govvieCode;
	}

	/**
	 * Retrieve the Array of Govvie Instrument Maturity Tenors
	 * 
	 * @return The Array of Govvie Instrument Maturity Tenors
	 */

	public String[] govvieTenor()
	{
		return _govvieTenorArray;
	}

	/**
	 * Retrieve the Array of Govvie Yield Quotes
	 * 
	 * @return The Array of Govvie Yield Quotes
	 */

	public double[] govvieQuote()
	{
		return _govvieQuoteArray;
	}

	/**
	 * Retrieve the Flag that indicates the Generation the Credit Metrics from the Market Price
	 * 
	 * @return TRUE - Generate the Credit Metrics from the Market Price
	 */

	public boolean creditMetricsFromMarketPrice()
	{
		return _marketPriceCreditMetrics;
	}

	/**
	 * Retrieve the Array of CDS Instrument Maturity Tenors
	 * 
	 * @return The Array of CDS Instrument Maturity Tenors
	 */

	public String[] creditTenor()
	{
		return _creditTenorArray;
	}

	/**
	 * Retrieve the Array of CDS Quotes
	 * 
	 * @return The Array of CDS Quotes
	 */

	public double[] creditQuote()
	{
		return _creditQuoteArray;
	}

	/**
	 * Retrieve the FX Rate
	 * 
	 * @return The FX Rate
	 */

	public double fx()
	{
		return _fx;
	}

	/**
	 * Retrieve the Settle Lag
	 * 
	 * @return The Settle Lag
	 */

	public double settleLag()
	{
		return _settleLag;
	}

	/**
	 * Retrieve the Bond Component Instance
	 * 
	 * @return The Bond Component Instance
	 */

	public BondComponent bond()
	{
		return _bondComponent;
	}

	/**
	 * Retrieve the Settle Date
	 * 
	 * @return The Settle Date
	 */

	public JulianDate settleDate()
	{
		return _settleDate;
	}

	/**
	 * Retrieve the Valuation Parameters
	 * 
	 * @return The Valuation Parameters
	 */

	public ValuationParams valuationParameters()
	{
		return _valuationParams;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Up Instances of the Funding Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Up Instances of the Funding Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> fundingTenorCSQCUp()
	{
		return _fundingUpCurveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Down Instances of the Funding Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Down Instances of the Funding Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> fundingTenorCSQCDown()
	{
		return _fundingDownCurveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Up Instances of the Forward Funding Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Up Instances of the Forward Funding Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> forwardFundingTenorCSQCUp()
	{
		return _forwardFundingUpCurveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Down Instances of the Forward Funding Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Down Instances of the Forward Funding Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> forwardFundingTenorCSQCDown()
	{
		return _forwardFundingDownCurveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Up Instances of the Govvie Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Up Instances of the Govvie Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> govvieTenorCSQCUp()
	{
		return _govvieUpCurveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Down Instances of the Govvie Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Down Instances of the Govvie Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> govvieTenorCSQCDown()
	{
		return _govvieDownCurveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the Map of the Tenor Bumped Instances of the Credit Curve CSQC
	 * 
	 * @return The Map of the Tenor Bumped Instances of the Credit Curve CSQC
	 */

	public Map<String, CurveSurfaceQuoteContainer> creditTenorCSQC()
	{
		return _curveSurfaceQuoteContainerMap;
	}

	/**
	 * Retrieve the CSQC built out of the Base Funding Curve
	 * 
	 * @return The CSQC built out of the Base Funding Curve
	 */

	public CurveSurfaceQuoteContainer fundingBaseCSQC()
	{
		return _fundingBaseCurveSurfaceQuoteContainer;
	}

	/**
	 * Retrieve the CSQC built out of the Base Euro Dollar Curve
	 * 
	 * @return The CSQC built out of the Base Euro Dollar Curve
	 */

	public CurveSurfaceQuoteContainer fundingEuroDollarCSQC()
	{
		return _euroDollarFundingCurveSurfaceQuoteContainer;
	}

	/**
	 * Retrieve the CSQC built out of the Base Credit Curve
	 * 
	 * @return The CSQC built out of the Base Credit Curve
	 */

	public CurveSurfaceQuoteContainer creditBaseCSQC()
	{
		return _creditBaseCurveSurfaceQuoteContainer;
	}

	/**
	 * Retrieve the CSQC built out of the Funding Curve Flat Bumped 1 bp
	 * 
	 * @return The CSQC built out of the Funding Curve Flat Bumped 1 bp
	 */

	public CurveSurfaceQuoteContainer funding01UpCSQC()
	{
		return _funding01UpCurveSurfaceQuoteContainer;
	}

	/**
	 * Retrieve the CSQC built out of the Credit Curve Flat Bumped 1 bp
	 * 
	 * @return The CSQC built out of the Credit Curve Flat Bumped 1 bp
	 */

	public CurveSurfaceQuoteContainer credit01UpCSQC()
	{
		return _credit01UpCurveSurfaceQuoteContainer;
	}

	/**
	 * Retrieve the Reset Date
	 * 
	 * @return The Reset Date
	 */

	public int resetDate()
	{
		return _resetDate;
	}

	/**
	 * Retrieve the Reset Rate
	 * 
	 * @return The Reset Rate
	 */

	public double resetRate()
	{
		return _resetRate;
	}

	/**
	 * Retrieve the EOS Metrics Replicator
	 * 
	 * @return The EOS Metrics Replicator
	 */

	public EOSMetricsReplicator eosMetricsReplicator()
	{
		return _eosMetricsReplicator;
	}

	/**
	 * Generate an Instance of a Replication Run
	 * 
	 * @return Instance of a Replication Run
	 */

	public BondReplicationRun generateRun()
	{
		int iMaturityDate = _bondComponent.maturityDate().julian();

		double dblNextPutFactor = 1.;
		double dblNextCallFactor = 1.;
		int iNextPutDate = iMaturityDate;
		int iNextCallDate = iMaturityDate;
		double dblCV01 = Double.NaN;
		double dblAccrued = Double.NaN;
		double dblYield01 = Double.NaN;
		double dblNominalYield = Double.NaN;
		double dblBEYToMaturity = Double.NaN;
		double dblOASToExercise = Double.NaN;
		double dblOASToMaturity = Double.NaN;
		double dblSpreadDuration = Double.NaN;
		double dblYieldToMaturity = Double.NaN;
		double dblParOASToExercise = Double.NaN;
		double dblESpreadToExercise = Double.NaN;
		double dblISpreadToExercise = Double.NaN;
		double dblJSpreadToExercise = Double.NaN;
		double dblNSpreadToExercise = Double.NaN;
		double dblZSpreadToExercise = Double.NaN;
		double dblZSpreadToMaturity = Double.NaN;
		double dblBondBasisToExercise = Double.NaN;
		double dblBondBasisToMaturity = Double.NaN;
		double dblConvexityToExercise = Double.NaN;
		double dblWALCreditToExercise = Double.NaN;
		double dblParZSpreadToExercise = Double.NaN;
		double dblCreditBasisToExercise = Double.NaN;
		double dblWALLossOnlyToExercise = Double.NaN;
		double dblYieldFromPriceNextPut = Double.NaN;
		double dblYieldFromPriceNextCall = Double.NaN;
		double dblWALCouponOnlyToExercise = Double.NaN;
		double dblDiscountMarginToExercise = Double.NaN;
		double dblParCreditBasisToExercise = Double.NaN;
		double dblYieldToMaturityFwdCoupon = Double.NaN;
		double dblEffectiveDurationAdjusted = Double.NaN;
		double dblMacaulayDurationToMaturity = Double.NaN;
		double dblModifiedDurationToExercise = Double.NaN;
		double dblModifiedDurationToMaturity = Double.NaN;
		double dblWALPrincipalOnlyToExercise = Double.NaN;
		double dblWALPrincipalOnlyToMaturity = Double.NaN;
		Map<String, Double> mapCreditKRD = null;
		Map<String, Double> mapCreditKPRD = null;

		int iValueDate = _valuationDate.julian();

		String currency = _bondComponent.currency();

		org.drip.product.params.EmbeddedOptionSchedule eosPut = _bondComponent.putSchedule();

		org.drip.product.params.EmbeddedOptionSchedule eosCall = _bondComponent.callSchedule();

		BondReplicationRun arr = new
			BondReplicationRun();

		WorkoutInfo wi = _bondComponent.exerciseYieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer,
			null, _currentPrice);

		if (null == wi) return null;

		int iWorkoutDate = wi.date();

		double dblWorkoutFactor = wi.factor();

		double dblYieldToExercise = wi.yield();

		Map<String, Double> mapLIBORKRD = new
			CaseInsensitiveHashMap<Double>();

		Map<String, Double> mapLIBORKPRD = new
			CaseInsensitiveHashMap<Double>();

		Map<String, Double> mapFundingKRD = new
			CaseInsensitiveHashMap<Double>();

		Map<String, Double> mapFundingKPRD = new
			CaseInsensitiveHashMap<Double>();

		Map<String, Double> mapGovvieKRD = new
			CaseInsensitiveHashMap<Double>();

		Map<String, Double> mapGovvieKPRD = new
			CaseInsensitiveHashMap<Double>();

		try {
			if (null != eosCall) {
				iNextCallDate = eosCall.nextDate (iValueDate);

				dblNextCallFactor = eosCall.nextFactor (iValueDate);
			}

			if (null != eosPut) {
				iNextPutDate = eosPut.nextDate (iValueDate);

				dblNextPutFactor = eosPut.nextFactor (iValueDate);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblAccrued = _bondComponent.accrued (_settleDate.julian(), _fundingBaseCurveSurfaceQuoteContainer);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldToMaturity = _bondComponent.yieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblBEYToMaturity = _bondComponent.yieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer,
				ValuationCustomizationParams.BondEquivalent (currency),
					_currentPrice);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldToMaturityFwdCoupon = _bondComponent.yieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, new
				ValuationCustomizationParams (_bondComponent.couponDC(), _bondComponent.freq(), false,
					null, currency, false, true), _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldFromPriceNextCall = _bondComponent.yieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null,
				iNextCallDate, dblNextCallFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblYieldFromPriceNextPut = _bondComponent.yieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null,
				iNextPutDate, dblNextPutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblNominalYield = _bondComponent.yieldFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, _issuePrice);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			dblOASToMaturity = _bondComponent.oasFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean isBondFloater = _bondComponent.isFloater();

		try {
			dblZSpreadToMaturity = isBondFloater ? _bondComponent.discountMarginFromPrice (_valuationParams,
				_fundingBaseCurveSurfaceQuoteContainer, null, _currentPrice) : _bondComponent.zSpreadFromPrice (_valuationParams,
					_fundingBaseCurveSurfaceQuoteContainer, null, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			 dblZSpreadToExercise = isBondFloater ? _bondComponent.discountMarginFromPrice (_valuationParams,
				_fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor, _currentPrice) :
					_bondComponent.zSpreadFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
						dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			 dblParZSpreadToExercise = isBondFloater ? _bondComponent.discountMarginFromPrice (_valuationParams,
				_fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor, _issuePrice) :
					_bondComponent.zSpreadFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
						dblWorkoutFactor, _issuePrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblBondBasisToMaturity = _bondComponent.bondBasisFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null,
				_currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblBondBasisToExercise = _bondComponent.bondBasisFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null,
				iWorkoutDate, dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblModifiedDurationToMaturity = (_currentPrice - _bondComponent.priceFromBondBasis (_valuationParams,
				_funding01UpCurveSurfaceQuoteContainer, null, dblBondBasisToMaturity)) / _currentPrice;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblMacaulayDurationToMaturity = _bondComponent.macaulayDurationFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer,
				null, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblModifiedDurationToExercise = (_currentPrice - _bondComponent.priceFromBondBasis (_valuationParams,
				_funding01UpCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor, dblBondBasisToExercise)) /
					_currentPrice;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblYield01 = 0.5 * (_bondComponent.priceFromYield (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
				dblWorkoutFactor, dblYieldToExercise - 0.0001 * _customYieldBump) - _bondComponent.priceFromYield
					(_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor, dblYieldToExercise +
						0.0001 * _customYieldBump)) / _customYieldBump;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _creditBaseCurveSurfaceQuoteContainer)
				dblCreditBasisToExercise = _marketPriceCreditMetrics ? 0. : _bondComponent.creditBasisFromPrice
					(_valuationParams, _creditBaseCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _creditBaseCurveSurfaceQuoteContainer)
				dblParCreditBasisToExercise = _bondComponent.creditBasisFromPrice (_valuationParams, _creditBaseCurveSurfaceQuoteContainer, null,
					iWorkoutDate, dblWorkoutFactor, _issuePrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _creditBaseCurveSurfaceQuoteContainer) {
				if (!_marketPriceCreditMetrics)
					dblEffectiveDurationAdjusted = (_currentPrice - _bondComponent.priceFromCreditBasis
						(_valuationParams, _creditBaseCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor,
							dblCreditBasisToExercise + 0.0001 * _customCreditBasisBump)) /
								_currentPrice / _customCreditBasisBump;
				else {
					EntityCDSLabel cl = _bondComponent.creditLabel();

					CreditCurve ccBase = _creditBaseCurveSurfaceQuoteContainer.creditState (cl);

					CreditCurve ccAdj =
						ScenarioCreditCurveBuilder.FlatHazard (_valuationDate.julian(),
							cl.referenceEntity(), currency, ccBase.hazard (_bondComponent.maturityDate()) + 0.0001
								* _customCreditBasisBump, _recoveryRate);

					if (null != ccAdj)
						dblEffectiveDurationAdjusted = (_currentPrice - _bondComponent.priceFromCreditBasis
							(_valuationParams, MarketParamsBuilder.Create
								(_creditBaseCurveSurfaceQuoteContainer.fundingState (_bondComponent.fundingLabel()),
									_creditBaseCurveSurfaceQuoteContainer.govvieState (_bondComponent.govvieLabel()), ccAdj, "", null, null,
										_creditBaseCurveSurfaceQuoteContainer.fixings()), null, iWorkoutDate, dblWorkoutFactor,
											0.)) / _currentPrice / _customCreditBasisBump;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblSpreadDuration = _spreadDurationMultiplier * (_currentPrice - (isBondFloater ?
				_bondComponent.priceFromDiscountMargin (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
					dblWorkoutFactor, dblZSpreadToExercise + 0.0001 * _zSpreadBump) :
						_bondComponent.priceFromZSpread (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
							dblWorkoutFactor, dblZSpreadToExercise + 0.0001 * _zSpreadBump))) /
								_currentPrice;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _credit01UpCurveSurfaceQuoteContainer)
				dblCV01 = _currentPrice - _bondComponent.priceFromCreditBasis (_valuationParams, _credit01UpCurveSurfaceQuoteContainer, null,
					iWorkoutDate, dblWorkoutFactor, dblCreditBasisToExercise);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblConvexityToExercise = _bondComponent.convexityFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null,
				iWorkoutDate, dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblDiscountMarginToExercise = dblYieldToExercise - _fundingBaseCurveSurfaceQuoteContainer.fundingState
				(_bondComponent.fundingLabel()).libor (_valuationParams.valueDate(), "1M");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblESpreadToExercise = isBondFloater ? _bondComponent.discountMarginFromPrice (_valuationParams,
				_euroDollarFundingCurveSurfaceQuoteContainer, null, iWorkoutDate, dblWorkoutFactor, _currentPrice) :
					_bondComponent.zSpreadFromPrice (_valuationParams, _euroDollarFundingCurveSurfaceQuoteContainer, null, iWorkoutDate,
						dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblISpreadToExercise = _bondComponent.iSpreadFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
				dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblJSpreadToExercise = _bondComponent.jSpreadFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
				dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblNSpreadToExercise = _bondComponent.nSpreadFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
				dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblWALPrincipalOnlyToExercise = _bondComponent.weightedAverageLifePrincipalOnly (_valuationParams,
				_fundingBaseCurveSurfaceQuoteContainer, iWorkoutDate, dblWorkoutFactor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblWALPrincipalOnlyToMaturity = _bondComponent.weightedAverageLifePrincipalOnly (_valuationParams,
				_fundingBaseCurveSurfaceQuoteContainer, iMaturityDate, 1.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _creditBaseCurveSurfaceQuoteContainer)
				dblWALLossOnlyToExercise = _bondComponent.weightedAverageLifeLossOnly (_valuationParams, _creditBaseCurveSurfaceQuoteContainer,
					iWorkoutDate, dblWorkoutFactor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblWALCouponOnlyToExercise = _bondComponent.weightedAverageLifeCouponOnly (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer,
				iWorkoutDate, dblWorkoutFactor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (null != _creditBaseCurveSurfaceQuoteContainer)
				dblWALCreditToExercise = _bondComponent.weightedAverageLifeCredit (_valuationParams, _creditBaseCurveSurfaceQuoteContainer,
					iWorkoutDate, dblWorkoutFactor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblOASToExercise = _bondComponent.oasFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
				dblWorkoutFactor, _currentPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dblParOASToExercise = _bondComponent.oasFromPrice (_valuationParams, _fundingBaseCurveSurfaceQuoteContainer, null, iWorkoutDate,
				dblWorkoutFactor, _issuePrice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		double dblEffectiveDuration = dblYield01 / _currentPrice;

		try {
			for (Map.Entry<String, CurveSurfaceQuoteContainer>
				meCSQCUp : _fundingUpCurveSurfaceQuoteContainerMap.entrySet()) {
				String key = meCSQCUp.getKey();

				CurveSurfaceQuoteContainer csqcTenorUp = meCSQCUp.getValue();

				CurveSurfaceQuoteContainer csqcTenorDown = _fundingDownCurveSurfaceQuoteContainerMap.get
					(key);

				double dblTenorFundingUpPrice = isBondFloater ? _bondComponent.priceFromFundingCurve (_valuationParams,
					csqcTenorUp, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread (_valuationParams,
						csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblZSpreadToExercise);

				double dblTenorFundingUpParPrice = isBondFloater ? _bondComponent.priceFromFundingCurve
					(_valuationParams, csqcTenorUp, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread
						(_valuationParams, csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor,
							dblParZSpreadToExercise);

				double dblTenorFundingDownPrice = isBondFloater ? _bondComponent.priceFromFundingCurve
					(_valuationParams, csqcTenorDown, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread
						(_valuationParams, csqcTenorDown, null, iWorkoutDate, dblWorkoutFactor,
							dblZSpreadToExercise);

				double dblTenorFundingDownParPrice = isBondFloater ? _bondComponent.priceFromFundingCurve
					(_valuationParams, csqcTenorDown, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread
						(_valuationParams, csqcTenorDown, null, iWorkoutDate, dblWorkoutFactor,
							dblParZSpreadToExercise);

				double dblBaseFloaterPrice = 0.5 * (dblTenorFundingDownPrice + dblTenorFundingUpPrice);

				mapFundingKRD.put (key, 0.5 * (dblTenorFundingDownPrice - dblTenorFundingUpPrice) /
					(isBondFloater ? dblBaseFloaterPrice : _currentPrice) / _tenorBump);

				mapFundingKPRD.put (key, 0.5 * (dblTenorFundingDownParPrice - dblTenorFundingUpParPrice) /
					(isBondFloater ? dblBaseFloaterPrice : _issuePrice) / _tenorBump);
			}

			for (Map.Entry<String, CurveSurfaceQuoteContainer>
				meCSQCUp : _forwardFundingUpCurveSurfaceQuoteContainerMap.entrySet()) {
				String key = meCSQCUp.getKey();

				CurveSurfaceQuoteContainer csqcTenorUp = meCSQCUp.getValue();

				CurveSurfaceQuoteContainer csqcTenorDown =
					_forwardFundingDownCurveSurfaceQuoteContainerMap.get (key);

				double dblTenorForwardUpPrice = isBondFloater ? _bondComponent.priceFromFundingCurve (_valuationParams,
					csqcTenorUp, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread (_valuationParams,
						csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblZSpreadToExercise);

				double dblTenorForwardUpParPrice = isBondFloater ? _bondComponent.priceFromFundingCurve
					(_valuationParams, csqcTenorUp, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread
						(_valuationParams, csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor,
							dblParZSpreadToExercise);

				double dblTenorForwardDownPrice = isBondFloater ? _bondComponent.priceFromFundingCurve
					(_valuationParams, csqcTenorDown, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread
						(_valuationParams, csqcTenorDown, null, iWorkoutDate, dblWorkoutFactor,
							dblZSpreadToExercise);

				double dblTenorForwardDownParPrice = isBondFloater ? _bondComponent.priceFromFundingCurve
					(_valuationParams, csqcTenorDown, iWorkoutDate, dblWorkoutFactor, 0.) : _bondComponent.priceFromZSpread
						(_valuationParams, csqcTenorDown, null, iWorkoutDate, dblWorkoutFactor,
							dblParZSpreadToExercise);

				double dblBaseFloaterPrice = 0.5 * (dblTenorForwardDownPrice + dblTenorForwardUpPrice);

				mapLIBORKRD.put (key, 0.5 * (dblTenorForwardDownPrice - dblTenorForwardUpPrice) /
					(isBondFloater ? dblBaseFloaterPrice : _currentPrice) / _tenorBump);

				mapLIBORKPRD.put (key, 0.5 * (dblTenorForwardDownParPrice - dblTenorForwardUpParPrice) /
					(isBondFloater ? dblBaseFloaterPrice : _issuePrice) / _tenorBump);
			}

			for (Map.Entry<String, CurveSurfaceQuoteContainer>
				meCSQCUp : _govvieUpCurveSurfaceQuoteContainerMap.entrySet()) {
				String key = meCSQCUp.getKey();

				CurveSurfaceQuoteContainer csqcTenorUp = meCSQCUp.getValue();

				CurveSurfaceQuoteContainer csqcTenorDown = _govvieDownCurveSurfaceQuoteContainerMap.get
					(key);

				mapGovvieKRD.put (key, 0.5 * (_bondComponent.priceFromOAS (_valuationParams, csqcTenorDown, null,
					iWorkoutDate, dblWorkoutFactor, dblOASToExercise) - _bondComponent.priceFromOAS (_valuationParams,
						csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblOASToExercise)) /
							_currentPrice / _tenorBump);

				mapGovvieKPRD.put (key, 0.5 * (_bondComponent.priceFromOAS (_valuationParams, csqcTenorDown, null,
					iWorkoutDate, dblWorkoutFactor, dblParOASToExercise) - _bondComponent.priceFromOAS (_valuationParams,
						csqcTenorUp, null, iWorkoutDate, dblWorkoutFactor, dblParOASToExercise)) /
							_issuePrice / _tenorBump);
			}

			if (null != _curveSurfaceQuoteContainerMap) {
				mapCreditKRD = new CaseInsensitiveHashMap<Double>();

				mapCreditKPRD = new CaseInsensitiveHashMap<Double>();

				for (Map.Entry<String, CurveSurfaceQuoteContainer>
					meCSQC : _curveSurfaceQuoteContainerMap.entrySet()) {
					String key = meCSQC.getKey();

					CurveSurfaceQuoteContainer csqcTenor = meCSQC.getValue();

					mapCreditKRD.put (key, (_currentPrice - _bondComponent.priceFromCreditBasis (_valuationParams,
						csqcTenor, null, iWorkoutDate, dblWorkoutFactor, dblCreditBasisToExercise)) /
							_currentPrice);

					mapCreditKPRD.put (key, (_issuePrice - _bondComponent.priceFromCreditBasis (_valuationParams,
						csqcTenor, null, iWorkoutDate, dblWorkoutFactor, dblParCreditBasisToExercise)) /
							_issuePrice);
				}
			}

			org.drip.analytics.output.BondEOSMetrics bem = null == _eosMetricsReplicator ? null : _eosMetricsReplicator.generateRun();

			if (null != bem)
			{
				if (!arr.addNamedField (new NamedField ("MCOAS", bem.oas())))
					return null;

				if (!arr.addNamedField (new NamedField ("MCDuration",
					bem.oasDuration())))
					return null;

				if (!arr.addNamedField (new NamedField ("MCConvexity",
					bem.oasConvexity())))
					return null;
			}

			if (!arr.addNamedField (new NamedField ("Price", _currentPrice)))
				return null;

			if (!arr.addNamedField (new NamedField ("Market Value",
				_currentPrice * _issueAmount)))
				return null;

			if (!arr.addNamedField (new NamedField ("Accrued", dblAccrued)))
				return null;

			if (!arr.addNamedField (new NamedField ("Accrued$", dblAccrued *
				_issueAmount)))
				return null;

			if (!arr.addNamedField (new NamedField ("Accrued Interest Factor",
				dblAccrued * _fx)))
				return null;

			if (!arr.addNamedField (new NamedField ("Yield To Maturity",
				dblYieldToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("Yield To Maturity CBE",
				dblBEYToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("YTM fwdCpn",
				dblYieldToMaturityFwdCoupon)))
				return null;

			if (!arr.addNamedField (new NamedField ("Yield To Worst",
				dblYieldToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("YIELD TO CALL",
				dblYieldFromPriceNextCall)))
				return null;

			if (!arr.addNamedField (new NamedField ("YIELD TO PUT",
				dblYieldFromPriceNextPut)))
				return null;

			if (!arr.addNamedField (new NamedField ("Nominal Yield",
				dblNominalYield)))
				return null;

			if (!arr.addNamedField (new NamedField ("Z_Spread", dblOASToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("Z_Vol_OAS",
				dblZSpreadToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("OAS", dblZSpreadToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("TSY OAS", dblOASToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("MOD DUR",
				dblModifiedDurationToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("MACAULAY DURATION",
				dblMacaulayDurationToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("MOD DUR TO WORST",
				dblModifiedDurationToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("Funding DURATION",
				mapFundingKRD.get ("bump"))))
				return null;

			if (!arr.addNamedField (new NamedField ("LIBOR DURATION",
				mapLIBORKRD.get ("bump"))))
				return null;

			if (!arr.addNamedField (new NamedField ("TREASURY DURATION",
				mapGovvieKRD.get ("bump"))))
				return null;

			if (!arr.addNamedField (new NamedField ("EFFECTIVE DURATION",
				dblEffectiveDuration)))
				return null;

			if (!arr.addNamedField (new NamedField ("EFFECTIVE DURATION ADJ",
				dblEffectiveDurationAdjusted)))
				return null;

			if (!arr.addNamedField (new NamedField ("OAD MULT",
				dblEffectiveDurationAdjusted / dblEffectiveDuration)))
				return null;

			if (!arr.addNamedField (new NamedField ("Spread Dur",
				dblSpreadDuration)))
				return null;

			if (!arr.addNamedField (new NamedField ("Spread Dur $",
				dblSpreadDuration * _issueAmount)))
				return null;

			if (!arr.addNamedField (new NamedField ("DV01", dblYield01)))
				return null;

			if (!arr.addNamedField (new NamedField ("CV01", dblCV01))) return null;

			if (!arr.addNamedField (new NamedField ("Convexity",
				dblConvexityToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("Modified Convexity",
				dblConvexityToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("DISCOUNT MARGIN",
				dblDiscountMarginToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("E-Spread",
				dblESpreadToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("I-Spread",
				dblISpreadToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("J-Spread",
				dblJSpreadToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("N-Spread",
				dblNSpreadToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("WAL To Worst",
				dblWALPrincipalOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("WAL",
				dblWALPrincipalOnlyToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("WAL2",
				dblWALLossOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("WAL3",
				!NumberUtil.IsValid (dblWALCouponOnlyToExercise) ? 0. :
					dblWALCouponOnlyToExercise)))
				return null;

			if (!arr.addNamedField (new NamedField ("WAL4",
				dblWALPrincipalOnlyToMaturity)))
				return null;

			if (!arr.addNamedField (new NamedField ("WAL_Adj",
				dblWALCreditToExercise)))
				return null;

			if (!arr.addNamedFieldMap (new NamedFieldMap ("Funding KRD",
				mapFundingKRD)))
				return null;

			if (!arr.addNamedFieldMap (new NamedFieldMap ("Funding KPRD",
				mapFundingKPRD)))
				return null;

			if (!arr.addNamedFieldMap (new NamedFieldMap ("LIBOR KRD",
				mapLIBORKRD)))
				return null;

			if (!arr.addNamedFieldMap (new NamedFieldMap ("LIBOR KPRD",
				mapLIBORKPRD)))
				return null;

			if (!arr.addNamedFieldMap (new NamedFieldMap ("Govvie KRD",
				mapGovvieKRD)))
				return null;

			if (!arr.addNamedFieldMap (new NamedFieldMap ("Govvie KPRD",
				mapGovvieKPRD)))
				return null;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			if (null != mapCreditKRD)
				arr.addNamedFieldMap (new NamedFieldMap ("Credit KRD",
					mapCreditKRD));

			if (null != mapCreditKPRD)
				arr.addNamedFieldMap (new NamedFieldMap ("Credit KPRD",
					mapCreditKPRD));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println ("Workout        : " + new JulianDate (iWorkoutDate));

		System.out.println ("Next Call Date : " + new JulianDate (iNextCallDate) +
			" | " + dblNextCallFactor);

		System.out.println ("Maturity Date  : " + new JulianDate (iMaturityDate) +
			" | 1.0");

		return arr;
	}
}
