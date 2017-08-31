
package org.drip.param.market;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * CurveSurfaceQuoteContainer provides implementation of the set of the market curve parameters. It serves as
 *  a place holder for the market parameters needed to value the product – discount curve, forward curve,
 *  treasury curve, credit curve, product quote, treasury quote map, and fixings map.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurveSurfaceQuoteContainer {
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>>
			_mapPayCurrencyForeignCollateralDC = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		_mapCreditState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.forward.ForwardCurve>
		_mapForwardState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.forward.ForwardCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		_mapFundingState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.fx.FXCurve>
		_mapFXState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.fx.FXCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>
		_mapGovvieState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		_mapOvernightState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapPaydownState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		_mapRatingState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		_mapRecoveryState = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.repo.RepoCurve> _mapRepoState =
		new org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.repo.RepoCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapCollateralVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapCreditVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapCustomVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapEquityVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapForwardVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapFundingVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapFXVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapGovvieVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapOvernightVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapPaydownVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapRatingVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapRecoveryVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		_mapRepoVolatility = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralCollateralCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralCreditCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralCustomCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralEquityCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralForwardCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralFundingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCollateralRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditCreditCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditCustomCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditEquityCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditForwardCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditFundingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCreditRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomCustomCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomEquityCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomForwardCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomFundingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapCustomRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityEquityCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityForwardCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityFundingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapEquityRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardForwardCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardFundingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapForwardRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingFundingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFundingRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXFXCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapFXRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapGovvieGovvieCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapGovvieOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapGovviePaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapGovvieRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapGovvieRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapGovvieRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapOvernightOvernightCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapOvernightPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapOvernightRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapOvernightRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapOvernightRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapPaydownPaydownCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapPaydownRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapPaydownRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapPaydownRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapRatingRatingCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapRatingRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapRatingRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapRecoveryRecoveryCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapRecoveryRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>
		_mapRepoRepoCorrelation = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.function.definition.R1ToR1>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
		_mapProductQuote = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>();

	private org.drip.param.market.LatentStateFixingsContainer _lsfc = new
		org.drip.param.market.LatentStateFixingsContainer();

	/**
	 * Empty CurveSurfaceQuoteSet Constructor
	 */

	public CurveSurfaceQuoteContainer()
	{
	}

	/**
	 * Retrieve the Discount Curve associated with the Pay Cash-flow Collateralized using a different
	 * 	Collateral Currency Numeraire
	 * 
	 * @param strPayCurrency The Pay Currency
	 * @param strCollateralCurrency The Collateral Currency
	 * 
	 * @return The Discount Curve associated with the Pay Cash-flow Collateralized using a different
	 * 	Collateral Currency Numeraire
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve payCurrencyCollateralCurrencyCurve (
		final java.lang.String strPayCurrency,
		final java.lang.String strCollateralCurrency)
	{
		if (null == strPayCurrency || !_mapPayCurrencyForeignCollateralDC.containsKey (strPayCurrency) ||
			null == strCollateralCurrency)
			return null;

		return _mapPayCurrencyForeignCollateralDC.get (strPayCurrency).get (strCollateralCurrency);
	}

	/**
	 * Set the Discount Curve associated with the Pay Cash-flow Collateralized using a different
	 * 	Collateral Currency Numeraire
	 * 
	 * @param strPayCurrency The Pay Currency
	 * @param strCollateralCurrency The Collateral Currency
	 * @param dcPayCurrencyCollateralCurrency The Discount Curve associated with the Pay Cash-flow
	 *  Collateralized using a different Collateral Currency Numeraire
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPayCurrencyCollateralCurrencyCurve (
		final java.lang.String strPayCurrency,
		final java.lang.String strCollateralCurrency,
		final org.drip.state.discount.MergedDiscountForwardCurve dcPayCurrencyCollateralCurrency)
	{
		if (null == strPayCurrency || strPayCurrency.isEmpty() || null == strCollateralCurrency ||
			strCollateralCurrency.isEmpty() || null == dcPayCurrencyCollateralCurrency)
			return false;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapCollateralCurrencyDC = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

		mapCollateralCurrencyDC.put (strCollateralCurrency, dcPayCurrencyCollateralCurrency);

		_mapPayCurrencyForeignCollateralDC.put (strPayCurrency, mapCollateralCurrencyDC);

		return true;
	}

	/**
	 * Retrieve the Collateral Choice Discount Curve for the specified Pay Currency
	 * 
	 * @param strPayCurrency The Pay Currency
	 * 
	 * @return Collateral Choice Discount Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve collateralChoiceDiscountCurve (
		final java.lang.String strPayCurrency)
	{
		if (null == strPayCurrency || !_mapPayCurrencyForeignCollateralDC.containsKey (strPayCurrency))
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapCollateralCurrencyDC = _mapPayCurrencyForeignCollateralDC.get (strPayCurrency);

		int iNumCollateralizer = mapCollateralCurrencyDC.size();

		org.drip.state.curve.ForeignCollateralizedDiscountCurve[] aFCDC = new
			org.drip.state.curve.ForeignCollateralizedDiscountCurve[iNumCollateralizer];

		int i = 0;

		for (java.util.Map.Entry<java.lang.String, org.drip.state.discount.MergedDiscountForwardCurve> me :
			mapCollateralCurrencyDC.entrySet()) {
			org.drip.state.discount.MergedDiscountForwardCurve fcdc = me.getValue();

			if (!(fcdc instanceof org.drip.state.curve.ForeignCollateralizedDiscountCurve)) return null;

			aFCDC[i++] = (org.drip.state.curve.ForeignCollateralizedDiscountCurve) fcdc;
		}

		try {
			return new org.drip.state.curve.DeterministicCollateralChoiceDiscountCurve
				(mapCollateralCurrencyDC.get (strPayCurrency), aFCDC, 30);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Credit Latent State from the Label
	 * 
	 * @param creditLabel The Credit Latent State Label
	 * 
	 * @return The Credit Latent State from the Label
	 */

	public org.drip.state.credit.CreditCurve creditState (
		final org.drip.state.identifier.CreditLabel creditLabel)
	{
		if (null == creditLabel) return null;

		java.lang.String strCreditLabel = creditLabel.fullyQualifiedName();

		return !_mapCreditState.containsKey (strCreditLabel) ? null : _mapCreditState.get (strCreditLabel);
	}

	/**
	 * (Re)-set the Credit State
	 * 
	 * @param cc The Credit State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditState (
		final org.drip.state.credit.CreditCurve cc)
	{
		if (null == cc) return false;

		_mapCreditState.put (cc.label().fullyQualifiedName(), cc);

		return true;
	}

	/**
	 * Retrieve the Equity State for the specified Equity Latent State Label
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * 
	 * @return Equity Curve
	 */

	public org.drip.function.definition.R1ToR1 equityState (
		final org.drip.state.identifier.EquityLabel equityLabel)
	{
		if (null == equityLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName();

		return _mapEquityState.containsKey (strCode) ? _mapEquityState.get (strCode) : null;
	}

	/**
	 * (Re)-set the Equity State for the specified Equity Latent State Label
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param auEquity The Equity State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityState (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.function.definition.R1ToR1 auEquity)
	{
		if (null == equityLabel || null == auEquity) return false;

		_mapEquityState.put (equityLabel.fullyQualifiedName(), auEquity);

		return true;
	}

	/**
	 * Retrieve the Forward State corresponding to the Label
	 * 
	 * @param forwardLabel Forward Latent State Label
	 * 
	 * @return Forward Curve
	 */

	public org.drip.state.forward.ForwardCurve forwardState (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == forwardLabel) return null;

		java.lang.String strForwardLabel = forwardLabel.fullyQualifiedName();

		return _mapForwardState.containsKey (strForwardLabel) ? _mapForwardState.get (strForwardLabel) :
			null;
	}

	/**
	 * (Re)-set the Forward State
	 * 
	 * @param fc Forward State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardState (
		final org.drip.state.forward.ForwardCurve fc)
	{
		if (null == fc) return false;

		_mapForwardState.put (fc.label().fullyQualifiedName(), fc);

		return true;
	}

	/**
	 * Retrieve the Funding Latent State Corresponding to the Label
	 * 
	 * @param fundingLabel Funding Latent State Label
	 * 
	 * @return The Funding Latent State
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve fundingState (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == fundingLabel) return null;

		java.lang.String strFundingLabel = fundingLabel.fullyQualifiedName();

		return _mapFundingState.containsKey (strFundingLabel) ? _mapFundingState.get (strFundingLabel) :
			null;
	}

	/**
	 * (Re)-set the Funding State
	 * 
	 * @param dc Funding State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingState (
		final org.drip.state.discount.MergedDiscountForwardCurve dc)
	{
		if (null == dc) return false;

		_mapFundingState.put (dc.label().fullyQualifiedName(), dc);

		return true;
	}

	/**
	 * Retrieve the FX State for the specified FX Latent State Label
	 * 
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return FX Curve
	 */

	public org.drip.state.fx.FXCurve fxState (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == fxLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName();

		return _mapFXState.containsKey (strCode) ? _mapFXState.get (strCode) : null;
	}

	/**
	 * (Re)-set the FX State for the specified FX Latent State Label
	 * 
	 * @param fxfc The FX State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXState (
		final org.drip.state.fx.FXCurve fxfc)
	{
		if (null == fxfc) return false;

		org.drip.state.identifier.FXLabel fxLabel = (org.drip.state.identifier.FXLabel) fxfc.label();

		_mapFXState.put (fxLabel.fullyQualifiedName(), fxfc);

		return true;
	}

	/**
	 * Retrieve the Government State for the specified Label
	 * 
	 * @param govvieLabel Govvie Latent State Label
	 * 
	 * @return Government Curve for the specified Label
	 */

	public org.drip.state.govvie.GovvieCurve govvieState (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == govvieLabel) return null;

		java.lang.String strGovvieLabel = govvieLabel.fullyQualifiedName();

		return !_mapGovvieState.containsKey (strGovvieLabel) ? null : _mapGovvieState.get (strGovvieLabel);
	}

	/**
	 * (Re)-set the Govvie State Curve
	 * 
	 * @param gc Govvie State Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieState (
		final org.drip.state.govvie.GovvieCurve gc)
	{
		if (null == gc) return false;

		_mapGovvieState.put (gc.label().fullyQualifiedName(), gc);

		return true;
	}

	/**
	 * Retrieve the Overnight Latent State Corresponding to the Label
	 * 
	 * @param overnightLabel Overnight Latent State Label
	 * 
	 * @return The Overnight Latent State
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve overnightState (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == overnightLabel) return null;

		java.lang.String strOvernightLabel = overnightLabel.fullyQualifiedName();

		return _mapOvernightState.containsKey (strOvernightLabel) ? _mapOvernightState.get
			(strOvernightLabel) : null;
	}

	/**
	 * (Re)-set the Overnight State
	 * 
	 * @param dcOvernight Overnight State Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightState (
		final org.drip.state.discount.MergedDiscountForwardCurve dcOvernight)
	{
		if (null == dcOvernight) return false;

		_mapOvernightState.put (dcOvernight.label().fullyQualifiedName(), dcOvernight);

		return true;
	}

	/**
	 * Retrieve the Pay-down State for the specified Pay-down Latent State Label
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return Pay-down State
	 */

	public org.drip.function.definition.R1ToR1 paydownState (
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == paydownLabel) return null;

		java.lang.String strCode = paydownLabel.fullyQualifiedName();

		return _mapPaydownState.containsKey (strCode) ? _mapPaydownState.get (strCode) : null;
	}

	/**
	 * (Re)-set the Pay-down State for the specified Pay-down Latent State Label
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auPaydown The Pay-down State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPaydownCurve (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auPaydown)
	{
		if (null == paydownLabel || null == auPaydown) return false;

		_mapPaydownState.put (paydownLabel.fullyQualifiedName(), auPaydown);

		return true;
	}

	/**
	 * Retrieve the Rating State for the specified Rating Latent State Label
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return Rating State
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve ratingState (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == ratingLabel) return null;

		java.lang.String strCode = ratingLabel.fullyQualifiedName();

		return _mapRatingState.containsKey (strCode) ? _mapRatingState.get (strCode) : null;
	}

	/**
	 * (Re)-set the Rating State for the specified Rating Latent State Label
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * @param dcRating The Rating State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRatingCurve (
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.state.discount.MergedDiscountForwardCurve dcRating)
	{
		if (null == ratingLabel || null == dcRating) return false;

		_mapRatingState.put (ratingLabel.fullyQualifiedName(), dcRating);

		return true;
	}

	/**
	 * Retrieve the Recovery Latent State from the Label
	 * 
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Recovery Latent State from the Label
	 */

	public org.drip.state.credit.CreditCurve recoveryState (
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == recoveryLabel) return null;

		java.lang.String strRecoveryLabel = recoveryLabel.fullyQualifiedName();

		return !_mapRecoveryState.containsKey (strRecoveryLabel) ? null : _mapRecoveryState.get
			(strRecoveryLabel);
	}

	/**
	 * (Re)-set the Recovery State for the specified Recovery Latent State Label
	 * 
	 * @param rc The Recovery State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRecoveryState (
		final org.drip.state.credit.CreditCurve rc)
	{
		if (null == rc) return false;

		_mapRecoveryState.put (rc.label().fullyQualifiedName(), rc);

		return true;
	}

	/**
	 * Retrieve the Repo Latent State Corresponding to the Label
	 * 
	 * @param repoLabel Repo Latent State Label
	 * 
	 * @return The Repo Latent State
	 */

	public org.drip.state.repo.RepoCurve repoState (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == repoLabel) return null;

		java.lang.String strRepoLabel = repoLabel.fullyQualifiedName();

		return _mapRepoState.containsKey (strRepoLabel) ? _mapRepoState.get (strRepoLabel) : null;
	}

	/**
	 * (Re)-set the Repo State
	 * 
	 * @param repoState Repo State
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRepoState (
		final org.drip.state.repo.RepoCurve repoState)
	{
		if (null == repoState) return false;

		_mapRepoState.put (repoState.label().fullyQualifiedName(), repoState);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified Collateral Label
	 * 
	 * @param collateralLabel The Collateral Currency
	 * 
	 * @return The Volatility Curve for the Collateral Label
	 */

	public org.drip.state.volatility.VolatilityCurve collateralVolatility (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
	{
		if (null == collateralLabel) return null;

		java.lang.String strCollateralLabel = collateralLabel.fullyQualifiedName();

		return !_mapCollateralVolatility.containsKey (strCollateralLabel) ? null :
			_mapCollateralVolatility.get (strCollateralLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the specified Collateral Label
	 * 
	 * @param vcCollateral The Collateral Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralVolatility (
		final org.drip.state.volatility.VolatilityCurve vcCollateral)
	{
		if (null == vcCollateral) return false;

		_mapCollateralVolatility.put (vcCollateral.label().fullyQualifiedName(), vcCollateral);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the Credit Latent State
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * 
	 * @return The Volatility Curve for the Credit Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve creditVolatility (
		final org.drip.state.identifier.CreditLabel creditLabel)
	{
		if (null == creditLabel) return null;

		java.lang.String strCreditLabel = creditLabel.fullyQualifiedName();

		return  !_mapCreditVolatility.containsKey (strCreditLabel) ? null : _mapCreditVolatility.get
			(strCreditLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the Credit Latent State
	 * 
	 * @param vcCredit The Credit Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditVolatility (
		final org.drip.state.volatility.VolatilityCurve vcCredit)
	{
		if (null == vcCredit) return false;

		_mapCreditVolatility.put (vcCredit.label().fullyQualifiedName(), vcCredit);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the Custom Metric Latent State
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * 
	 * @return The Volatility Curve for the Custom Metric Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve customVolatility (
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		if (null == customLabel) return null;

		java.lang.String strCustomLabel = customLabel.fullyQualifiedName();

		return _mapCustomVolatility.containsKey (strCustomLabel) ? _mapCustomVolatility.get (strCustomLabel)
			: null;
	}

	/**
	 * (Re)-set the Custom Metric Volatility Curve
	 * 
	 * @param vcCustom The Custom Metric Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomVolatility (
		final org.drip.state.volatility.VolatilityCurve vcCustom)
	{
		if (null == vcCustom) return false;

		_mapCustomVolatility.put (vcCustom.label().fullyQualifiedName(), vcCustom);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the Equity Latent State
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * 
	 * @return The Volatility Curve for the Equity Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve equityVolatility (
		final org.drip.state.identifier.EquityLabel equityLabel)
	{
		if (null == equityLabel) return null;

		java.lang.String strEquityLabel = equityLabel.fullyQualifiedName();

		return  !_mapEquityVolatility.containsKey (strEquityLabel) ? null : _mapEquityVolatility.get
			(strEquityLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the Equity Latent State
	 * 
	 * @param vcEquity The Equity Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityVolatility (
		final org.drip.state.volatility.VolatilityCurve vcEquity)
	{
		if (null == vcEquity) return false;

		_mapEquityVolatility.put (vcEquity.label().fullyQualifiedName(), vcEquity);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified Forward Latent State Label
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Volatility Curve for the Forward Label
	 */

	public org.drip.state.volatility.VolatilityCurve forwardVolatility (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == forwardLabel) return null;

		java.lang.String strForwardLabel = forwardLabel.fullyQualifiedName();

		return _mapForwardVolatility.containsKey (strForwardLabel) ? _mapForwardVolatility.get
			(strForwardLabel) : null;
	}

	/**
	 * (Re)-set the Volatility Curve for the specified Forward Latent State Label
	 * 
	 * @param vcForward The Forward Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardVolatility (
		final org.drip.state.volatility.VolatilityCurve vcForward)
	{
		if (null == vcForward) return false;

		_mapForwardVolatility.put (vcForward.label().fullyQualifiedName(), vcForward);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the Funding Latent State Label
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Volatility Curve for the Funding Label
	 */

	public org.drip.state.volatility.VolatilityCurve fundingVolatility (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == fundingLabel) return null;

		java.lang.String strFundingLabel = fundingLabel.fullyQualifiedName();

		return _mapFundingVolatility.containsKey (strFundingLabel) ? _mapFundingVolatility.get
			(strFundingLabel) : null;
	}

	/**
	 * (Re)-set the Volatility Curve for the Funding Latent State Label
	 * 
	 * @param vcFunding The Funding Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingVolatility (
		final org.drip.state.volatility.VolatilityCurve vcFunding)
	{
		if (null == vcFunding) return false;

		_mapFundingVolatility.put (vcFunding.label().fullyQualifiedName(), vcFunding);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified FX Latent State Label
	 * 
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Volatility Curve for the FX Latent State Label
	 */

	public org.drip.state.volatility.VolatilityCurve fxVolatility (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == fxLabel) return null;

		java.lang.String strFXLabel = fxLabel.fullyQualifiedName();

		return !_mapFXVolatility.containsKey (strFXLabel) ? null : _mapFXVolatility.get (strFXLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the specified FX Latent State
	 * 
	 * @param vcFX The FX Volatility Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXVolatility (
		final org.drip.state.volatility.VolatilityCurve vcFX)
	{
		if (null == vcFX) return false;

		_mapFXVolatility.put (vcFX.label().fullyQualifiedName(), vcFX);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified Govvie Latent State
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Volatility Curve for the Govvie Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve govvieVolatility (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == govvieLabel) return null;

		java.lang.String strGovvieLabel = govvieLabel.fullyQualifiedName();

		return !_mapGovvieVolatility.containsKey (strGovvieLabel) ? null : _mapGovvieVolatility.get
			(strGovvieLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the Govvie Latent State
	 * 
	 * @param vcGovvie The Govvie Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieVolatility (
		final org.drip.state.volatility.VolatilityCurve vcGovvie)
	{
		if (null == vcGovvie) return false;

		_mapGovvieVolatility.put (vcGovvie.label().fullyQualifiedName(), vcGovvie);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the Overnight Latent State Label
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Volatility Curve for the Overnight Label
	 */

	public org.drip.state.volatility.VolatilityCurve overnightVolatility (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == overnightLabel) return null;

		java.lang.String strOvernightLabel = overnightLabel.fullyQualifiedName();

		return _mapOvernightVolatility.containsKey (strOvernightLabel) ? _mapOvernightVolatility.get
			(strOvernightLabel) : null;
	}

	/**
	 * (Re)-set the Volatility Curve for the Overnight Latent State Label
	 * 
	 * @param vcOvernight The Overnight Volatility Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightVolatility (
		final org.drip.state.volatility.VolatilityCurve vcOvernight)
	{
		if (null == vcOvernight) return false;

		_mapOvernightVolatility.put (vcOvernight.label().fullyQualifiedName(), vcOvernight);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified Pay-down Latent State
	 * 
	 * @param paydownLabel The Pay Down Latent State Label
	 * 
	 * @return The Volatility Curve for the Pay-down Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve paydownVolaitlity (
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == paydownLabel) return null;

		java.lang.String strPaydownLabel = paydownLabel.fullyQualifiedName();

		return !_mapPaydownVolatility.containsKey (strPaydownLabel) ? null : _mapPaydownVolatility.get
			(strPaydownLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the Pay-down Latent State
	 * 
	 * @param vcPaydown The Pay down Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPaydownVolatility (
		final org.drip.state.volatility.VolatilityCurve vcPaydown)
	{
		if (null == vcPaydown) return false;

		_mapPaydownVolatility.put (vcPaydown.label().fullyQualifiedName(), vcPaydown);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified Rating Latent State
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Volatility Curve for the Rating Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve ratingVolaitlity (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == ratingLabel) return null;

		java.lang.String strRatingLabel = ratingLabel.fullyQualifiedName();

		return !_mapRatingVolatility.containsKey (strRatingLabel) ? null : _mapRatingVolatility.get
			(strRatingLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the Rating Latent State
	 * 
	 * @param vcRating The Rating Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRatingVolatility (
		final org.drip.state.volatility.VolatilityCurve vcRating)
	{
		if (null == vcRating) return false;

		_mapRatingVolatility.put (vcRating.label().fullyQualifiedName(), vcRating);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the specified Recovery Latent State
	 * 
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Volatility Curve for the Recovery Latent State
	 */

	public org.drip.state.volatility.VolatilityCurve recoveryVolatility (
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == recoveryLabel) return null;

		java.lang.String strRecoveryLabel = recoveryLabel.fullyQualifiedName();

		return !_mapRecoveryVolatility.containsKey (strRecoveryLabel) ? null : _mapRecoveryVolatility.get
			(strRecoveryLabel);
	}

	/**
	 * (Re)-set the Volatility Curve for the Recovery Latent State
	 * 
	 * @param vcRecovery The Recovery Volatility Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRecoveryVolatility (
		final org.drip.state.volatility.VolatilityCurve vcRecovery)
	{
		if (null == vcRecovery) return false;

		_mapRecoveryVolatility.put (vcRecovery.label().fullyQualifiedName(), vcRecovery);

		return true;
	}

	/**
	 * Retrieve the Volatility Curve for the Repo Latent State Label
	 * 
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Volatility Curve for the Repo Label
	 */

	public org.drip.state.volatility.VolatilityCurve repoVolatility (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == repoLabel) return null;

		java.lang.String strRepoLabel = repoLabel.fullyQualifiedName();

		return _mapRepoVolatility.containsKey (strRepoLabel) ? _mapRepoVolatility.get (strRepoLabel) : null;
	}

	/**
	 * (Re)-set the Volatility Curve for the Repo Latent State Label
	 * 
	 * @param vcRepo The Repo Volatility Curve
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRepoVolatility (
		final org.drip.state.volatility.VolatilityCurve vcRepo)
	{
		if (null == vcRepo) return false;

		_mapRepoVolatility.put (vcRepo.label().fullyQualifiedName(), vcRepo);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral Currency Pair
	 * 
	 * @param strCurrency1 Collateral Currency #1
	 * @param strCurrency2 Collateral Currency #2
	 * 
	 * @return The Correlation Surface for the specified Collateral Currency Pair
	 */

	public org.drip.function.definition.R1ToR1 collateralCollateralCorrelation (
		final java.lang.String strCurrency1,
		final java.lang.String strCurrency2)
	{
		if (null == strCurrency1 || strCurrency1.isEmpty() || null == strCurrency2 || strCurrency2.isEmpty())
			return null;

		java.lang.String strCode = strCurrency1 + "@#" + strCurrency2;

		if (!_mapCollateralCollateralCorrelation.containsKey (strCode)) return null;

		return _mapCollateralCollateralCorrelation.get (strCode);
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral Currency Pair
	 * 
	 * @param strCurrency1 Collateral Currency #1
	 * @param strCurrency2 Collateral Currency #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralCollateralCorrelation (
		final java.lang.String strCurrency1,
		final java.lang.String strCurrency2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCurrency1 || strCurrency1.isEmpty() || null == strCurrency2 || strCurrency2.isEmpty()
			|| null == auCorrelation)
			return false;

		_mapCollateralCollateralCorrelation.put (strCurrency1 + "@#" + strCurrency2, auCorrelation);

		_mapCollateralCollateralCorrelation.put (strCurrency2 + "@#" + strCurrency1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Pair of Credit Latent States
	 * 
	 * @param creditLabel1 The Credit Curve Latent State Label #1
	 * @param creditLabel2 The Credit Curve Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Pair of Credit Latent States
	 */

	public org.drip.function.definition.R1ToR1 creditCreditCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel1,
		final org.drip.state.identifier.CreditLabel creditLabel2)
	{
		if (null == creditLabel1 || null == creditLabel2) return null;

		java.lang.String strCode12 = creditLabel1.fullyQualifiedName() + "@#" +
			creditLabel2.fullyQualifiedName();

		if (_mapCreditCreditCorrelation.containsKey (strCode12))
			return _mapCreditCreditCorrelation.get (strCode12);

		java.lang.String strCode21 = creditLabel2.fullyQualifiedName() + "@#" +
			creditLabel1.fullyQualifiedName();

		return !_mapCreditCreditCorrelation.containsKey (strCode21) ? null :
			_mapCreditCreditCorrelation.get (strCode21);
	}

	/**
	 * (Re)-set the Correlation Surface between the Pair of Credit Latent States
	 * 
	 * @param creditLabel1 The Credit Curve Latent State Label #1
	 * @param creditLabel2 The Credit Curve Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditCreditCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel1,
		final org.drip.state.identifier.CreditLabel creditLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel1 || null == creditLabel2 || null == auCorrelation) return false;

		java.lang.String strCreditLabel1 = creditLabel1.fullyQualifiedName();

		java.lang.String strCreditLabel2 = creditLabel2.fullyQualifiedName();

		_mapCreditCreditCorrelation.put (strCreditLabel1 + "@#" + strCreditLabel2, auCorrelation);

		_mapCreditCreditCorrelation.put (strCreditLabel2 + "@#" + strCreditLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric Latent State Pair
	 * 
	 * @param customLabel1 The Custom Metric Latent State Label #1
	 * @param customLabel2 The Custom Metric Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Custom Metric Latent State Pair
	 */

	public org.drip.function.definition.R1ToR1 customCustomCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel1,
		final org.drip.state.identifier.CustomLabel customLabel2)
	{
		if (null == customLabel1 || null == customLabel2) return null;

		java.lang.String strCode12 = customLabel1.fullyQualifiedName() + "@#" +
			customLabel2.fullyQualifiedName();

		if (_mapCustomCustomCorrelation.containsKey (strCode12))
			return _mapCustomCustomCorrelation.get (strCode12);

		java.lang.String strCode21 = customLabel2.fullyQualifiedName() + "@#" +
			customLabel1.fullyQualifiedName();

		return _mapCustomCustomCorrelation.containsKey (strCode21) ?
			_mapCustomCustomCorrelation.get (strCode21) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric Latent State Pair
	 * 
	 * @param customLabel1 The Custom Metric Latent State Label #1
	 * @param customLabel2 The Custom Metric Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomCustomCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel1,
		final org.drip.state.identifier.CustomLabel customLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel1 || null == customLabel2 || customLabel1.match (customLabel2) || null ==
			auCorrelation)
			return false;

		_mapCustomCustomCorrelation.put (customLabel1.fullyQualifiedName() + "@#" +
			customLabel2.fullyQualifiedName(), auCorrelation);

		_mapCustomCustomCorrelation.put (customLabel2.fullyQualifiedName() + "@#" +
			customLabel1.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Pair of Equity Latent States
	 * 
	 * @param equityLabel1 Equity Curve Latent State Label #1
	 * @param equityLabel2 EquityCurve Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Pair of Equity Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityEquityCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel1,
		final org.drip.state.identifier.EquityLabel equityLabel2)
	{
		if (null == equityLabel1 || null == equityLabel2) return null;

		java.lang.String strCode = equityLabel1.fullyQualifiedName() + "@#" +
			equityLabel2.fullyQualifiedName();

		return _mapEquityEquityCorrelation.containsKey (strCode) ?
			_mapEquityEquityCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Pair of Equity Latent States
	 * 
	 * @param equityLabel1 EquityCurve Latent State Label #1
	 * @param equityLabel2 EquityCurve Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityEquityCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel1,
		final org.drip.state.identifier.EquityLabel equityLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel1 || null == equityLabel2 || equityLabel1.match (equityLabel2) || null ==
			auCorrelation)
			return false;

		java.lang.String strEquityLabel1 = equityLabel1.fullyQualifiedName();

		java.lang.String strEquityLabel2 = equityLabel2.fullyQualifiedName();

		_mapEquityEquityCorrelation.put (strEquityLabel1 + "@#" + strEquityLabel2, auCorrelation);

		_mapEquityEquityCorrelation.put (strEquityLabel2 + "@#" + strEquityLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Pair of Forward Latent States
	 * 
	 * @param forwardLabel1 Forward Curve Latent State Label #1
	 * @param forwardLabel2 Forward Curve Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Pair of Forward Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardForwardCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel1,
		final org.drip.state.identifier.ForwardLabel forwardLabel2)
	{
		if (null == forwardLabel1 || null == forwardLabel2) return null;

		java.lang.String strCode = forwardLabel1.fullyQualifiedName() + "@#" +
			forwardLabel2.fullyQualifiedName();

		return _mapForwardForwardCorrelation.containsKey (strCode) ?
			_mapForwardForwardCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Pair of Forward Latent States
	 * 
	 * @param forwardLabel1 Forward Curve Latent State Label #1
	 * @param forwardLabel2 Forward Curve Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardForwardCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel1,
		final org.drip.state.identifier.ForwardLabel forwardLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel1 || null == forwardLabel2 || forwardLabel1.match (forwardLabel2) || null ==
			auCorrelation)
			return false;

		java.lang.String strForwardLabel1 = forwardLabel1.fullyQualifiedName();

		java.lang.String strForwardLabel2 = forwardLabel2.fullyQualifiedName();

		_mapForwardForwardCorrelation.put (strForwardLabel1 + "@#" + strForwardLabel2, auCorrelation);

		_mapForwardForwardCorrelation.put (strForwardLabel2 + "@#" + strForwardLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Pair of Funding Latent States
	 * 
	 * @param fundingLabel1 Funding Latent State Label #1
	 * @param fundingLabel2 Funding Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Pair of Funding Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingFundingCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel1,
		final org.drip.state.identifier.FundingLabel fundingLabel2)
	{
		if (null == fundingLabel1 || null == fundingLabel2 || fundingLabel1.match (fundingLabel2))
			return null;

		java.lang.String strCode = fundingLabel1.fullyQualifiedName() + "@#" +
			fundingLabel2.fullyQualifiedName();

		return _mapFundingFundingCorrelation.containsKey (strCode) ?
			_mapFundingFundingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Pair of Funding Latent States
	 * 
	 * @param fundingLabel1 Funding Latent State Label #1
	 * @param fundingLabel2 Funding Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingFundingCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel1,
		final org.drip.state.identifier.FundingLabel fundingLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel1 || null == fundingLabel2 || fundingLabel1.match (fundingLabel2) || null ==
			auCorrelation)
			return false;

		java.lang.String strFundingLabel1 = fundingLabel1.fullyQualifiedName();

		java.lang.String strFundingLabel2 = fundingLabel2.fullyQualifiedName();

		_mapFundingFundingCorrelation.put (strFundingLabel1 + "@#" + strFundingLabel2, auCorrelation);

		_mapFundingFundingCorrelation.put (strFundingLabel2 + "@#" + strFundingLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX Latent State Label Set
	 * 
	 * @param fxLabel1 The FX Latent State Label #1
	 * @param fxLabel2 The FX Latent State Label #2
	 * 
	 * @return The Correlation Surface for the specified FX Latent State Label Set
	 */

	public org.drip.function.definition.R1ToR1 fxFXCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel1,
		final org.drip.state.identifier.FXLabel fxLabel2)
	{
		if (null == fxLabel1 || null == fxLabel2 || fxLabel1.match (fxLabel2)) return null;

		java.lang.String strCode = fxLabel1.fullyQualifiedName() + "@#" + fxLabel2.fullyQualifiedName();

		return !_mapFXFXCorrelation.containsKey (strCode) ? null : _mapFXFXCorrelation.get
			(strCode);
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX Latent State Label Set
	 * 
	 * @param fxLabel1 The FX Latent State Label #1
	 * @param fxLabel2 The FX Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXFXCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel1,
		final org.drip.state.identifier.FXLabel fxLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel1 || null == fxLabel2 || fxLabel1.match (fxLabel2) || null == auCorrelation)
			return false;

		java.lang.String strCode1 = fxLabel1.fullyQualifiedName();

		java.lang.String strCode2 = fxLabel2.fullyQualifiedName();

		_mapFXFXCorrelation.put (strCode1 + "@#" + strCode2, auCorrelation);

		_mapFXFXCorrelation.put (strCode2 + "@#" + strCode1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Govvie Latent State Pair
	 * 
	 * @param govvieLabel1 The Govvie Curve Latent State Label #1
	 * @param govvieLabel2 The Govvie Curve Latent State Label #2
	 * 
	 * @return The Correlation Surface for the specified Govvie Latent State Pair
	 */

	public org.drip.function.definition.R1ToR1 govvieGovvieCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel1,
		final org.drip.state.identifier.GovvieLabel govvieLabel2)
	{
		if (null == govvieLabel1 || null == govvieLabel2 || govvieLabel1.match (govvieLabel2)) return null;

		java.lang.String strCode12 = govvieLabel1.fullyQualifiedName() + "@#" +
			govvieLabel2.fullyQualifiedName();

		if (_mapGovvieGovvieCorrelation.containsKey (strCode12))
			return _mapGovvieGovvieCorrelation.get (strCode12);

		java.lang.String strCode21 = govvieLabel2.fullyQualifiedName() + "@#" +
			govvieLabel1.fullyQualifiedName();

		return _mapGovvieGovvieCorrelation.containsKey (strCode21) ?
			_mapGovvieGovvieCorrelation.get (strCode21) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the Govvie Latent State Pair
	 * 
	 * @param govvieLabel1 The Govvie Curve Latent State Label #1
	 * @param govvieLabel2 The Govvie Curve Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieGovvieCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel1,
		final org.drip.state.identifier.GovvieLabel govvieLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == govvieLabel1 || null == govvieLabel2 || govvieLabel1.match (govvieLabel2) || null ==
			auCorrelation)
			return false;

		java.lang.String strGovvieLabel1 = govvieLabel1.fullyQualifiedName();

		java.lang.String strGovvieLabel2 = govvieLabel2.fullyQualifiedName();

		_mapGovvieGovvieCorrelation.put (strGovvieLabel1 + "@#" + strGovvieLabel2, auCorrelation);

		_mapGovvieGovvieCorrelation.put (strGovvieLabel2 + "@#" + strGovvieLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Pair of Overnight Latent States
	 * 
	 * @param overnightLabel1 Overnight Latent State Label #1
	 * @param overnightLabel2 Overnight Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Pair of Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 overnightOvernightCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel1,
		final org.drip.state.identifier.OvernightLabel overnightLabel2)
	{
		if (null == overnightLabel1 || null == overnightLabel2 || overnightLabel1.match (overnightLabel2))
			return null;

		java.lang.String strCode = overnightLabel1.fullyQualifiedName() + "@#" +
			overnightLabel2.fullyQualifiedName();

		return _mapOvernightOvernightCorrelation.containsKey (strCode) ?
			_mapOvernightOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Pair of Overnight Latent States
	 * 
	 * @param overnightLabel1 Overnight Latent State Label #1
	 * @param overnightLabel2 Overnight Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightOvernightCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel1,
		final org.drip.state.identifier.OvernightLabel overnightLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == overnightLabel1 || null == overnightLabel2 || overnightLabel1.match (overnightLabel2) ||
			null == auCorrelation)
			return false;

		java.lang.String strOvernightLabel1 = overnightLabel1.fullyQualifiedName();

		java.lang.String strOvernightLabel2 = overnightLabel2.fullyQualifiedName();

		_mapOvernightOvernightCorrelation.put (strOvernightLabel1 + "@#" + strOvernightLabel2,
			auCorrelation);

		_mapOvernightOvernightCorrelation.put (strOvernightLabel2 + "@#" + strOvernightLabel1,
			auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Pay-down Latent State Pair
	 * 
	 * @param paydownLabel1 The Pay-down Curve Latent State Label #1
	 * @param paydownLabel2 The Pay-down Curve Latent State Label #2
	 * 
	 * @return The Correlation Surface for the specified Pay-down Latent State Pair
	 */

	public org.drip.function.definition.R1ToR1 paydownPaydownCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel1,
		final org.drip.state.identifier.PaydownLabel paydownLabel2)
	{
		if (null == paydownLabel1 || null == paydownLabel2 || paydownLabel1.match (paydownLabel2))
			return null;

		java.lang.String strCode12 = paydownLabel1.fullyQualifiedName() + "@#" +
			paydownLabel2.fullyQualifiedName();

		if (_mapPaydownPaydownCorrelation.containsKey (strCode12))
			return _mapPaydownPaydownCorrelation.get (strCode12);

		java.lang.String strCode21 = paydownLabel2.fullyQualifiedName() + "@#" +
			paydownLabel1.fullyQualifiedName();

		return _mapPaydownPaydownCorrelation.containsKey (strCode21) ?
			_mapPaydownPaydownCorrelation.get (strCode21) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the Pay-down Latent State Pair
	 * 
	 * @param paydownLabel1 The Pay-down Curve Latent State Label #1
	 * @param paydownLabel2 The Pay-down Curve Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPaydownPaydownCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel1,
		final org.drip.state.identifier.PaydownLabel paydownLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == paydownLabel1 || null == paydownLabel2 || paydownLabel1.match (paydownLabel2) || null ==
			auCorrelation)
			return false;

		java.lang.String strPaydownLabel1 = paydownLabel1.fullyQualifiedName();

		java.lang.String strPaydownLabel2 = paydownLabel2.fullyQualifiedName();

		_mapPaydownPaydownCorrelation.put (strPaydownLabel1 + "@#" + strPaydownLabel2, auCorrelation);

		_mapPaydownPaydownCorrelation.put (strPaydownLabel2 + "@#" + strPaydownLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Recovery Latent State Pair
	 * 
	 * @param recoveryLabel1 The Recovery Curve Latent State Label #1
	 * @param recoveryLabel2 The Recovery Curve Latent State Label #2
	 * 
	 * @return The Correlation Surface for the specified Recovery Latent State Pair
	 */

	public org.drip.function.definition.R1ToR1 recoveryRecoveryCorrelation (
		final org.drip.state.identifier.RecoveryLabel recoveryLabel1,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel2)
	{
		if (null == recoveryLabel1 || null == recoveryLabel2 || recoveryLabel1.match (recoveryLabel2))
			return null;

		java.lang.String strCode12 = recoveryLabel1.fullyQualifiedName() + "@#" +
			recoveryLabel2.fullyQualifiedName();

		if (_mapRecoveryRecoveryCorrelation.containsKey (strCode12))
			return _mapRecoveryRecoveryCorrelation.get (strCode12);

		java.lang.String strCode21 = recoveryLabel2.fullyQualifiedName() + "@#" +
			recoveryLabel1.fullyQualifiedName();

		return _mapRecoveryRecoveryCorrelation.containsKey (strCode21) ?
			_mapRecoveryRecoveryCorrelation.get (strCode21) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the Recovery Latent State Pair
	 * 
	 * @param recoveryLabel1 The Recovery Curve Latent State Label #1
	 * @param recoveryLabel2 The Recovery Curve Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRecoveryRecoveryCorrelation (
		final org.drip.state.identifier.RecoveryLabel recoveryLabel1,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == recoveryLabel1 || null == recoveryLabel2 || recoveryLabel1.match (recoveryLabel2) || null
			== auCorrelation)
			return false;

		java.lang.String strRecoveryLabel1 = recoveryLabel1.fullyQualifiedName();

		java.lang.String strRecoveryLabel2 = recoveryLabel2.fullyQualifiedName();

		_mapRecoveryRecoveryCorrelation.put (strRecoveryLabel1 + "@#" + strRecoveryLabel2,
			auCorrelation);

		_mapRecoveryRecoveryCorrelation.put (strRecoveryLabel2 + "@#" + strRecoveryLabel1,
			auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Pair of Repo Latent States
	 * 
	 * @param repoLabel1 Repo Latent State Label #1
	 * @param repoLabel2 Repo Latent State Label #2
	 * 
	 * @return The Correlation Surface between the Pair of Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 repoRepoCorrelation (
		final org.drip.state.identifier.RepoLabel repoLabel1,
		final org.drip.state.identifier.RepoLabel repoLabel2)
	{
		if (null == repoLabel1 || null == repoLabel2 || repoLabel1.match (repoLabel2)) return null;

		java.lang.String strCode = repoLabel1.fullyQualifiedName() + "@#" + repoLabel2.fullyQualifiedName();

		return _mapRepoRepoCorrelation.containsKey (strCode) ? _mapRepoRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Pair of Repo Latent States
	 * 
	 * @param repoLabel1 Repo Latent State Label #1
	 * @param repoLabel2 Repo Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRepoRepoCorrelation (
		final org.drip.state.identifier.RepoLabel repoLabel1,
		final org.drip.state.identifier.RepoLabel repoLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == repoLabel1 || null == repoLabel2 || repoLabel1.match (repoLabel2) || null ==
			auCorrelation)
			return false;

		java.lang.String strRepoLabel1 = repoLabel1.fullyQualifiedName();

		java.lang.String strRepoLabel2 = repoLabel2.fullyQualifiedName();

		_mapRepoRepoCorrelation.put (strRepoLabel1 + "@#" + strRepoLabel2, auCorrelation);

		_mapRepoRepoCorrelation.put (strRepoLabel2 + "@#" + strRepoLabel1, auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Collateral and the Credit Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param creditLabel The Credit Curve Latent State Label
	 * 
	 * @return The Correlation Surface between the Collateral and the Credit Latent States
	 */

	public org.drip.function.definition.R1ToR1 collateralCreditCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.CreditLabel creditLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == creditLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + creditLabel.fullyQualifiedName();

		return _mapCollateralCreditCorrelation.containsKey (strCode) ? null :
			_mapCollateralCreditCorrelation.get (strCode);
	}

	/**
	 * (Re)-set the Correlation Surface between the Collateral and the Credit Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralCreditCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == creditLabel)
			return false;

		_mapCollateralCreditCorrelation.put (strCollateralCurrency + "@#" +
			creditLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Collateral and the Custom Metric Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param customLabel The Custom Metric Latent State Label
	 * 
	 * @return The Correlation Surface between the Collateral and the Custom Metric Latent States
	 */

	public org.drip.function.definition.R1ToR1 collateralCustomCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == customLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + customLabel.fullyQualifiedName();

		return _mapCollateralCustomCorrelation.containsKey (strCode) ?
			_mapCollateralCustomCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Collateral and the Custom Metric Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param customLabel The Custom Metric Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralCustomCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == customLabel)
			return false;

		_mapCollateralCustomCorrelation.put (strCollateralCurrency + "@#" +
			customLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Collateral and the Forward Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Correlation Surface between the Collateral and the Forward Latent States
	 */

	public org.drip.function.definition.R1ToR1 collateralForwardCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == forwardLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + forwardLabel.fullyQualifiedName();

		return _mapCollateralForwardCorrelation.containsKey (strCode) ?
			_mapCollateralForwardCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Collateral and the Equity Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param equityLabel The Equity Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralEquityCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == equityLabel || null
			== auCorrelation)
			return false;

		_mapCollateralEquityCorrelation.put (strCollateralCurrency + "@#" +
			equityLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Collateral and the Equity Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param equityLabel The Equity Latent State Label
	 * 
	 * @return The Correlation Surface between the Collateral and the Equity Latent States
	 */

	public org.drip.function.definition.R1ToR1 collateralEquityCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.EquityLabel equityLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == equityLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + equityLabel.fullyQualifiedName();

		return _mapCollateralEquityCorrelation.containsKey (strCode) ?
			_mapCollateralEquityCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Collateral and the Forward Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param forwardLabel The Forward Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralForwardCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == forwardLabel || null
			== auCorrelation)
			return false;

		_mapCollateralForwardCorrelation.put (strCollateralCurrency + "@#" +
			forwardLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Collateral and the Funding Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Correlation Surface between the Collateral and the Funding Latent States
	 */

	public org.drip.function.definition.R1ToR1 collateralFundingCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == fundingLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + fundingLabel.fullyQualifiedName();

		return _mapCollateralFundingCorrelation.containsKey (strCode) ?
			_mapCollateralFundingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Collateral and the Funding Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param fundingLabel The Funding Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralFundingCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == fundingLabel || null
			== auCorrelation)
			return false;

		_mapCollateralFundingCorrelation.put (strCollateralCurrency + "@#" +
			fundingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral and the FX Latent State Label
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Collateral and the FX Latent State Label
	 */

	public org.drip.function.definition.R1ToR1 collateralFXCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == fxLabel) return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + fxLabel.fullyQualifiedName();

		return _mapCollateralFXCorrelation.containsKey (strCode) ?
			_mapCollateralFXCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral and FX Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param fxLabel The FX Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralFXCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == fxLabel || null ==
			auCorrelation)
			return false;

		_mapCollateralFXCorrelation.put (strCollateralCurrency + "@#" + fxLabel.fullyQualifiedName(),
			auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral and Govvie Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Collateral and Govvie Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 collateralGovvieCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == govvieLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + govvieLabel.fullyQualifiedName();

		return _mapCollateralGovvieCorrelation.containsKey (strCode) ?
			_mapCollateralGovvieCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral and Govvie Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralGovvieCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == govvieLabel || null
			== auCorrelation)
			return false;

		_mapCollateralGovvieCorrelation.put (strCollateralCurrency + "@#" +
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Collateral and the Overnight Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface between the Collateral and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 collateralOvernightCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == overnightLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + overnightLabel.fullyQualifiedName();

		return _mapCollateralOvernightCorrelation.containsKey (strCode) ?
			_mapCollateralOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Collateral and the Overnight Latent States
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralOvernightCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == overnightLabel ||
			null == auCorrelation)
			return false;

		_mapCollateralOvernightCorrelation.put (strCollateralCurrency + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral and Pay-down Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Collateral and Pay-down Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 collateralPaydownCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == paydownLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + paydownLabel.fullyQualifiedName();

		return _mapCollateralPaydownCorrelation.containsKey (strCode) ?
			_mapCollateralPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral and Pay-down Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralPaydownCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == paydownLabel || null
			== auCorrelation)
			return false;

		_mapCollateralPaydownCorrelation.put (strCollateralCurrency + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral and Rating Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Collateral and Rating Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 collateralRatingCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == ratingLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + ratingLabel.fullyQualifiedName();

		return _mapCollateralRatingCorrelation.containsKey (strCode) ?
			_mapCollateralRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral and Rating Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralRatingCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == ratingLabel || null
			== auCorrelation)
			return false;

		_mapCollateralRatingCorrelation.put (strCollateralCurrency + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral and Recovery Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Collateral and Recovery Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 collateralRecoveryCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == recoveryLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + recoveryLabel.fullyQualifiedName();

		return _mapCollateralRecoveryCorrelation.containsKey (strCode) ?
			_mapCollateralRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral and Recovery Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralRecoveryCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == recoveryLabel || null
			== auCorrelation)
			return false;

		_mapCollateralRecoveryCorrelation.put (strCollateralCurrency + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Collateral and Repo Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Collateral and Repo Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 collateralRepoCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == repoLabel)
			return null;

		java.lang.String strCode = strCollateralCurrency + "@#" + repoLabel.fullyQualifiedName();

		return _mapCollateralRepoCorrelation.containsKey (strCode) ?
			_mapCollateralRepoCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Collateral and Repo Latent State Labels
	 * 
	 * @param strCollateralCurrency The Collateral Currency
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCollateralRepoCorrelation (
		final java.lang.String strCollateralCurrency,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == strCollateralCurrency || strCollateralCurrency.isEmpty() || null == repoLabel || null ==
			auCorrelation)
			return false;

		_mapCollateralRepoCorrelation.put (strCollateralCurrency + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Custom Metric Latent States
	 * 
	 * @param creditLabel The Credit Latent State Label
	 * @param customLabel The Custom Metric Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Custom Metric Latent States
	 */

	public org.drip.function.definition.R1ToR1 creditCustomMetricCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		if (null == creditLabel || null == customLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			customLabel.fullyQualifiedName();

		return _mapCreditCustomCorrelation.containsKey (strCode) ?
			_mapCreditCustomCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Custom Metric Latent States
	 * 
	 * @param creditLabel The Credit Latent State Label
	 * @param customLabel The Custom Metric Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditCustomCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == customLabel || null == auCorrelation) return false;

		_mapCreditCustomCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			customLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Equity Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param equityLabel The Equity Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Equity Latent States
	 */

	public org.drip.function.definition.R1ToR1 creditEquityCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.EquityLabel equityLabel)
	{
		if (null == creditLabel || null == equityLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			equityLabel.fullyQualifiedName();

		return _mapCreditEquityCorrelation.containsKey (strCode) ?
			_mapCreditEquityCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Equity Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param equityLabel The Equity Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditEquityCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == equityLabel || null == auCorrelation) return false;

		_mapCreditEquityCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			equityLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Forward Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Forward Latent States
	 */

	public org.drip.function.definition.R1ToR1 creditForwardCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == creditLabel || null == forwardLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			forwardLabel.fullyQualifiedName();

		return _mapCreditForwardCorrelation.containsKey (strCode) ?
			_mapCreditForwardCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Forward Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param forwardLabel The Forward Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditForwardCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == forwardLabel || null == auCorrelation) return false;

		_mapCreditForwardCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			forwardLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Funding Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Funding Latent States
	 */

	public org.drip.function.definition.R1ToR1 creditFundingCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == creditLabel || null == fundingLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName();

		return _mapCreditFundingCorrelation.containsKey (strCode) ?
			_mapCreditFundingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Funding Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param fundingLabel The Funding Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditFundingCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == fundingLabel || null == auCorrelation) return false;

		_mapCreditFundingCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the FX Latent State Labels
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the FX Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 creditFXCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == creditLabel || null == fxLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" + fxLabel.fullyQualifiedName();

		return _mapCreditFXCorrelation.containsKey (strCode) ? _mapCreditFXCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the FX Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param fxLabel The FX Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditFXCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == fxLabel || null == auCorrelation) return false;

		_mapCreditFXCorrelation.get (creditLabel.fullyQualifiedName() + "@#" +
			fxLabel.fullyQualifiedName());

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Govvie Latent State Labels
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Govvie Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 creditGovvieCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == creditLabel || null == govvieLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName();

		return _mapCreditGovvieCorrelation.containsKey (strCode) ?
			_mapCreditGovvieCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Govvie Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditGovvieCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == govvieLabel || null == auCorrelation) return false;

		_mapCreditGovvieCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Overnight Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 creditOvernightCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == creditLabel || null == overnightLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName();

		return _mapCreditOvernightCorrelation.containsKey (strCode) ?
			_mapCreditOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Overnight Latent States
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditOvernightCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == overnightLabel || null == auCorrelation) return false;

		_mapCreditOvernightCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Pay-down Latent State Labels
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Pay-down Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 creditPaydownCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == creditLabel || null == paydownLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapCreditPaydownCorrelation.containsKey (strCode) ?
			_mapCreditPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Pay-down Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditPaydownCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == paydownLabel || null == auCorrelation) return false;

		_mapCreditPaydownCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Rating Latent State Labels
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Rating Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 creditRatingCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == creditLabel || null == ratingLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapCreditRatingCorrelation.containsKey (strCode) ?
			_mapCreditRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Rating Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditRatingCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapCreditRatingCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Repo Latent State Labels
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Repo Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 creditRepoCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == creditLabel || null == repoLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapCreditRepoCorrelation.containsKey (strCode) ? _mapCreditRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Repo Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditRepoCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == repoLabel || null == auCorrelation) return false;

		_mapCreditRepoCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Credit and the Recovery Latent State Labels
	 * 
	 * @param creditLabel The Credit Curve Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface between the Credit and the Recovery Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 creditRecoveryCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == creditLabel || null == recoveryLabel) return null;

		java.lang.String strCode = creditLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapCreditRecoveryCorrelation.containsKey (strCode) ?
			_mapCreditRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Credit and the Recovery Latent States
	 * 
	 * @param creditLabel The Credit Curve Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCreditRecoveryCorrelation (
		final org.drip.state.identifier.CreditLabel creditLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == creditLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapCreditRecoveryCorrelation.put (creditLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Equity Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param equityLabel The Equity Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Equity Latent States
	 */

	public org.drip.function.definition.R1ToR1 customEquityCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.EquityLabel equityLabel)
	{
		if (null == customLabel || null == equityLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			equityLabel.fullyQualifiedName();

		return _mapCustomEquityCorrelation.containsKey (strCode) ?
			_mapCustomEquityCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Equity Latent States
	 * 
	 * @param customLabel The Custom Metric Label
	 * @param equityLabel The Equity Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomEquityCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == equityLabel || null == auCorrelation) return false;

		_mapCustomEquityCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			equityLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Forward Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Forward Latent States
	 */

	public org.drip.function.definition.R1ToR1 customForwardCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == customLabel || null == forwardLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			forwardLabel.fullyQualifiedName();

		return _mapCustomForwardCorrelation.containsKey (strCode) ?
			_mapCustomForwardCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Forward Latent States
	 * 
	 * @param customLabel The Custom Metric Label
	 * @param forwardLabel The Forward Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomForwardCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == forwardLabel || null == auCorrelation) return false;

		_mapCustomForwardCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			forwardLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between Custom Metric and the Funding Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Funding Latent States
	 */

	public org.drip.function.definition.R1ToR1 customMetricFundingCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == customLabel || null == fundingLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName();

		return _mapCustomFundingCorrelation.containsKey (strCode) ?
			_mapCustomFundingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Funding Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomFundingCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == fundingLabel) return false;

		_mapCustomFundingCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the FX Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the FX Latent States
	 */

	public org.drip.function.definition.R1ToR1 customFXCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == customLabel || null == fxLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" + fxLabel.fullyQualifiedName();

		return _mapCustomFXCorrelation.containsKey (strCode) ? _mapCustomFXCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the FX Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomFXCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == fxLabel || null == auCorrelation) return false;

		_mapCustomFXCorrelation.get (customLabel.fullyQualifiedName() + "@#" +
			fxLabel.fullyQualifiedName());

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Govvie Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Govvie Latent States
	 */

	public org.drip.function.definition.R1ToR1 customGovvieCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == customLabel || null == govvieLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName();

		return _mapCustomGovvieCorrelation.containsKey (strCode) ?
			_mapCustomGovvieCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Govvie Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomGovvieCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == govvieLabel) return false;

		_mapCustomGovvieCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Overnight Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 customOvernightCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == customLabel || null == overnightLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName();

		return _mapCustomOvernightCorrelation.containsKey (strCode) ?
			_mapCustomOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Overnight Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomOvernightCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == overnightLabel) return false;

		_mapCustomOvernightCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Pay-down Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 customPaydownCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == customLabel || null == paydownLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapCustomPaydownCorrelation.containsKey (strCode) ?
			_mapCustomPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Pay-down Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomPaydownCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == paydownLabel) return false;

		_mapCustomPaydownCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Rating Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 customRatingCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == customLabel || null == ratingLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapCustomRatingCorrelation.containsKey (strCode) ?
			_mapCustomRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Rating Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomRatingCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == ratingLabel) return false;

		_mapCustomRatingCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Recovery Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 customRecoveryCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == customLabel || null == recoveryLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapCustomRecoveryCorrelation.containsKey (strCode) ?
			_mapCustomRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Recovery Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomRecoveryCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == recoveryLabel) return false;

		_mapCustomRecoveryCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Custom Metric and the Repo Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface between the Custom Metric and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 customRepoCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == customLabel || null == repoLabel) return null;

		java.lang.String strCode = customLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapCustomRepoCorrelation.containsKey (strCode) ? _mapCustomRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Custom Metric and the Repo Latent States
	 * 
	 * @param customLabel The Custom Metric Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setCustomRepoCorrelation (
		final org.drip.state.identifier.CustomLabel customLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == customLabel || null == repoLabel) return false;

		_mapCustomRepoCorrelation.put (customLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the Forward Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Forward Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityForwardCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == equityLabel || null == forwardLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			forwardLabel.fullyQualifiedName();

		return _mapEquityForwardCorrelation.containsKey (strCode) ?
			_mapEquityForwardCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Forward Latent States
	 * 
	 * @param equityLabel The Equity Label
	 * @param forwardLabel The Forward Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityForwardCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == forwardLabel || null == auCorrelation) return false;

		_mapEquityForwardCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			forwardLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between Equity and the Funding Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Funding Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityFundingCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == equityLabel || null == fundingLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName();

		return _mapEquityFundingCorrelation.containsKey (strCode) ?
			_mapEquityFundingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Funding Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityFundingCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == fundingLabel) return false;

		_mapEquityFundingCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the FX Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the FX Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityFXCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == equityLabel || null == fxLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" + fxLabel.fullyQualifiedName();

		return _mapEquityFXCorrelation.containsKey (strCode) ? _mapEquityFXCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the FX Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityFXCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == fxLabel || null == auCorrelation) return false;

		_mapEquityFXCorrelation.get (equityLabel.fullyQualifiedName() + "@#" +
			fxLabel.fullyQualifiedName());

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the Govvie Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Govvie Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityGovvieCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == equityLabel || null == govvieLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName();

		return _mapEquityGovvieCorrelation.containsKey (strCode) ?
			_mapEquityGovvieCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Govvie Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityGovvieCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == govvieLabel) return false;

		_mapEquityGovvieCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between Equity and the Overnight Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityOvernightCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == equityLabel || null == overnightLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName();

		return _mapEquityOvernightCorrelation.containsKey (strCode) ?
			_mapEquityOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Overnight Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityOvernightCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == overnightLabel) return false;

		_mapEquityOvernightCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the Pay-down Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityPaydownCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == equityLabel || null == paydownLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapEquityPaydownCorrelation.containsKey (strCode) ?
			_mapEquityPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Pay-down Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityPaydownCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == paydownLabel) return false;

		_mapEquityPaydownCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the Recovery Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityRecoveryCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == equityLabel || null == recoveryLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapEquityRecoveryCorrelation.containsKey (strCode) ?
			_mapEquityRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Recovery Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityRecoveryCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == recoveryLabel) return false;

		_mapEquityRecoveryCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the Rating Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityRatingCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == equityLabel || null == ratingLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapEquityRatingCorrelation.containsKey (strCode) ?
			_mapEquityRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Rating Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityRatingCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == ratingLabel) return false;

		_mapEquityRatingCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Equity and the Repo Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface between the Equity and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 equityRepoCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == equityLabel || null == repoLabel) return null;

		java.lang.String strCode = equityLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapEquityRepoCorrelation.containsKey (strCode) ? _mapEquityRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Equity and the Repo Latent States
	 * 
	 * @param equityLabel The Equity Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setEquityRepoCorrelation (
		final org.drip.state.identifier.EquityLabel equityLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == equityLabel || null == repoLabel) return false;

		_mapEquityRepoCorrelation.put (equityLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Funding Latent States
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Funding Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardFundingCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		if (null == forwardLabel || null == fundingLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName();

		return _mapForwardFundingCorrelation.containsKey (strCode) ?
			_mapForwardFundingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Funding Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param fundingLabel The Funding Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardFundingCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == fundingLabel || null == auCorrelation) return false;

		_mapForwardFundingCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" +
			fundingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the FX Latent State Labels
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the FX Latent State Labels
	 */

	public org.drip.function.definition.R1ToR1 forwardFXCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == forwardLabel || null == fxLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" + fxLabel.fullyQualifiedName();

		return _mapForwardFXCorrelation.containsKey (strCode) ? _mapForwardFXCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the FX Latent State Labels
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardFXCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == fxLabel || null == auCorrelation) return false;

		_mapForwardFXCorrelation.get (forwardLabel.fullyQualifiedName() + "@#" +
			fxLabel.fullyQualifiedName());

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Govvie Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Govvie Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardGovvieCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == forwardLabel || null == govvieLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName();

		return _mapForwardGovvieCorrelation.containsKey (strCode) ?
			_mapForwardGovvieCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Govvie Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardGovvieCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == govvieLabel || null == auCorrelation) return false;

		_mapForwardGovvieCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" + 
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Overnight Latent States
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardOvernightCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == forwardLabel || null == overnightLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName();

		return _mapForwardOvernightCorrelation.containsKey (strCode) ?
			_mapForwardOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Overnight Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardOvernightCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == overnightLabel || null == auCorrelation) return false;

		_mapForwardOvernightCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Pay-down Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardPaydownCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == forwardLabel || null == paydownLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapForwardPaydownCorrelation.containsKey (strCode) ?
			_mapForwardPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Pay-down Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardPaydownCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == paydownLabel || null == auCorrelation) return false;

		_mapForwardPaydownCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" + 
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Rating Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardRatingCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == forwardLabel || null == ratingLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapForwardRatingCorrelation.containsKey (strCode) ?
			_mapForwardRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Rating Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardRatingCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapForwardRatingCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" + 
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Recovery Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardRecoveryCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == forwardLabel || null == recoveryLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapForwardRecoveryCorrelation.containsKey (strCode) ?
			_mapForwardRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Recovery Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardRecoveryCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapForwardRecoveryCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" + 
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Forward and the Repo Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface between the Forward and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 forwardRepoCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == forwardLabel || null == repoLabel) return null;

		java.lang.String strCode = forwardLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapForwardRepoCorrelation.containsKey (strCode) ?
			_mapForwardRepoCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Forward and the Repo Latent States
	 * 
	 * @param forwardLabel The Forward Curve Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setForwardRepoCorrelation (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == forwardLabel || null == repoLabel || null == auCorrelation) return false;

		_mapForwardRepoCorrelation.put (forwardLabel.fullyQualifiedName() + "@#" + 
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the FX Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the FX Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingFXCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		if (null == fundingLabel || null == fxLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" + fxLabel.fullyQualifiedName();

		return _mapFundingFXCorrelation.containsKey (strCode) ? _mapFundingFXCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the FX Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param fxLabel The FX Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingFXCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == fxLabel || null == auCorrelation) return false;

		_mapFundingFXCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			fxLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the Govvie Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the Govvie Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingGovvieCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == fundingLabel || null == govvieLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName();

		return _mapFundingGovvieCorrelation.containsKey (strCode) ?
			_mapFundingGovvieCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the Govvie Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingGovvieCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == govvieLabel || null == auCorrelation) return false;

		_mapFundingGovvieCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the Overnight Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingOvernightCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == fundingLabel || null == overnightLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName();

		return _mapFundingOvernightCorrelation.containsKey (strCode) ?
			_mapFundingOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the Overnight Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingOvernightCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == overnightLabel || null == auCorrelation) return false;

		_mapFundingOvernightCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the Pay-down Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingPaydownCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == fundingLabel || null == paydownLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapFundingPaydownCorrelation.containsKey (strCode) ?
			_mapFundingPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the Pay-down Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingPaydownCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == paydownLabel || null == auCorrelation) return false;

		_mapFundingPaydownCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the Rating Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingRatingCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == fundingLabel || null == ratingLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapFundingRatingCorrelation.containsKey (strCode) ?
			_mapFundingRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the Rating Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingRecoveryCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapFundingRatingCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the Recovery Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingRecoveryCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == fundingLabel || null == recoveryLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapFundingRecoveryCorrelation.containsKey (strCode) ?
			_mapFundingRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the Recovery Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingRecoveryCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapFundingRecoveryCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface between the Funding and the Repo Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface between the Funding and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 fundingRepoCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == fundingLabel || null == repoLabel) return null;

		java.lang.String strCode = fundingLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapFundingRepoCorrelation.containsKey (strCode) ?
			_mapFundingRepoCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface between the Funding and the Repo Latent States
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFundingRepoCorrelation (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fundingLabel || null == repoLabel || null == auCorrelation) return false;

		_mapFundingRepoCorrelation.put (fundingLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX and the Govvie Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Correlation Surface for the specified FX and the Govvie Latent States
	 */

	public org.drip.function.definition.R1ToR1 fxGovvieCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		if (null == fxLabel || null == govvieLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName() + "@#" + govvieLabel.fullyQualifiedName();

		return _mapFXGovvieCorrelation.containsKey (strCode) ? _mapFXGovvieCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX and the Govvie Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param govvieLabel The Govvie Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXGovvieCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel || null == govvieLabel || null == auCorrelation) return false;

		_mapFXGovvieCorrelation.put (fxLabel.fullyQualifiedName() + "@#" +
			govvieLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX and the Overnight Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface for the specified FX and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 fxOvernightCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == fxLabel || null == overnightLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName() + "@#" + overnightLabel.fullyQualifiedName();

		return _mapFXOvernightCorrelation.containsKey (strCode) ?
			_mapFXOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX and the Overnight Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXOvernightCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel || null == overnightLabel || null == auCorrelation) return false;

		_mapFXOvernightCorrelation.put (fxLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX and the Pay-down Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface for the specified FX and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 fxPaydownCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == fxLabel || null == paydownLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName() + "@#" + paydownLabel.fullyQualifiedName();

		return _mapFXPaydownCorrelation.containsKey (strCode) ? _mapFXPaydownCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX and the Pay-down Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXPaydownCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel || null == paydownLabel || null == auCorrelation) return false;

		_mapFXPaydownCorrelation.put (fxLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX and the Rating Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface for the specified FX and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 fxRatingCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == fxLabel || null == ratingLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName() + "@#" + ratingLabel.fullyQualifiedName();

		return _mapFXRatingCorrelation.containsKey (strCode) ? _mapFXRatingCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX and the Rating Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXRatingCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapFXRatingCorrelation.put (fxLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX and the Recovery Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface for the specified FX and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 fxRecoveryCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == fxLabel || null == recoveryLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName() + "@#" + recoveryLabel.fullyQualifiedName();

		return _mapFXRecoveryCorrelation.containsKey (strCode) ? _mapFXRecoveryCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX and the Recovery Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXRecoveryCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapFXRecoveryCorrelation.put (fxLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified FX and the Repo Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified FX and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 fxRepoCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == fxLabel || null == repoLabel) return null;

		java.lang.String strCode = fxLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapFXRepoCorrelation.containsKey (strCode) ? _mapFXRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified FX and the Repo Latent States
	 * 
	 * @param fxLabel The FX Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setFXRepoCorrelation (
		final org.drip.state.identifier.FXLabel fxLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == fxLabel || null == repoLabel || null == auCorrelation) return false;

		_mapFXRepoCorrelation.put (fxLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Govvie and the Overnight Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieOvernightCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == govvieLabel || null == overnightLabel || null == auCorrelation) return false;

		_mapGovvieOvernightCorrelation.put (govvieLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Govvie and the Overnight Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Govvie and the Overnight Latent States
	 */

	public org.drip.function.definition.R1ToR1 govvieOvernightCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		if (null == govvieLabel || null == overnightLabel) return null;

		java.lang.String strCode = govvieLabel.fullyQualifiedName() + "@#" +
			overnightLabel.fullyQualifiedName();

		return _mapGovvieOvernightCorrelation.containsKey (strCode) ?
			_mapGovvieOvernightCorrelation.get (strCode) : null;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Govvie and the Pay-down Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Govvie and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 govviePaydownCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == govvieLabel || null == paydownLabel) return null;

		java.lang.String strCode = govvieLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapGovviePaydownCorrelation.containsKey (strCode) ?
			_mapGovviePaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Govvie and the Pay-down Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovviePaydownCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == govvieLabel || null == paydownLabel || null == auCorrelation) return false;

		_mapGovviePaydownCorrelation.put (govvieLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Govvie and the Rating Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Govvie and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 govvieRecoveryCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == govvieLabel || null == ratingLabel) return null;

		java.lang.String strCode = govvieLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapGovvieRecoveryCorrelation.containsKey (strCode) ?
			_mapGovvieRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Govvie and the Rating Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieRatingCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == govvieLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapGovvieRatingCorrelation.put (govvieLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Govvie and the Recovery Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Govvie and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 govvieRecoveryCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == govvieLabel || null == recoveryLabel) return null;

		java.lang.String strCode = govvieLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapGovvieRecoveryCorrelation.containsKey (strCode) ?
			_mapGovvieRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Govvie and the Recovery Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieRecoveryCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == govvieLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapGovvieRecoveryCorrelation.put (govvieLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Govvie and the Repo Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Govvie and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 govvieRepoCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == govvieLabel || null == repoLabel) return null;

		java.lang.String strCode = govvieLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapGovvieRepoCorrelation.containsKey (strCode) ? _mapGovvieRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Govvie and the Repo Latent States
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setGovvieRepoCorrelation (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == govvieLabel || null == repoLabel || null == auCorrelation) return false;

		_mapGovvieRepoCorrelation.put (govvieLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Overnight and the Pay-down Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Overnight and the Pay-down Latent States
	 */

	public org.drip.function.definition.R1ToR1 overnightPaydownCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel)
	{
		if (null == overnightLabel || null == paydownLabel) return null;

		java.lang.String strCode = overnightLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName();

		return _mapOvernightPaydownCorrelation.containsKey (strCode) ?
			_mapOvernightPaydownCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Overnight and the Pay-down Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightPaydownCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == overnightLabel || null == paydownLabel || null == auCorrelation) return false;

		_mapOvernightPaydownCorrelation.put (overnightLabel.fullyQualifiedName() + "@#" +
			paydownLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Overnight and the Rating Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Overnight and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 overnightRatingCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == overnightLabel || null == ratingLabel) return null;

		java.lang.String strCode = overnightLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapOvernightRatingCorrelation.containsKey (strCode) ?
			_mapOvernightRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Overnight and the Rating Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightRatingCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == overnightLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapOvernightRatingCorrelation.put (overnightLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Overnight and the Recovery Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Overnight and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 overnightRecoveryCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == overnightLabel || null == recoveryLabel) return null;

		java.lang.String strCode = overnightLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapOvernightRecoveryCorrelation.containsKey (strCode) ?
			_mapOvernightRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Overnight and the Recovery Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightRecoveryCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == overnightLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapOvernightRecoveryCorrelation.put (overnightLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Overnight and the Repo Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Overnight and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 overnightRepoCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == overnightLabel || null == repoLabel) return null;

		java.lang.String strCode = overnightLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName();

		return _mapOvernightRepoCorrelation.containsKey (strCode) ?
			_mapOvernightRepoCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Overnight and the Repo Latent States
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setOvernightRepoCorrelation (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == overnightLabel || null == repoLabel || null == auCorrelation) return false;

		_mapOvernightRepoCorrelation.put (overnightLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Pay-down and the Rating Latent States
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Pay-down and the Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 paydownRatingCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		if (null == paydownLabel || null == ratingLabel) return null;

		java.lang.String strCode = paydownLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName();

		return _mapPaydownRatingCorrelation.containsKey (strCode) ?
			_mapPaydownRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Pay-down and the Rating Latent States
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param ratingLabel The Rating Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPaydownRatingCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == paydownLabel || null == ratingLabel || null == auCorrelation) return false;

		_mapPaydownRatingCorrelation.put (paydownLabel.fullyQualifiedName() + "@#" +
			ratingLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Pay-down and the Recovery Latent States
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Pay-down and the Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 paydownRecoveryCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == paydownLabel || null == recoveryLabel) return null;

		java.lang.String strCode = paydownLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapPaydownRecoveryCorrelation.containsKey (strCode) ?
			_mapPaydownRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Pay-down and the Recovery Latent States
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPaydownRecoveryCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == paydownLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapPaydownRecoveryCorrelation.put (paydownLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Pay-down and the Repo Latent States
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Pay-down and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 paydownRepoCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == paydownLabel || null == repoLabel) return null;

		java.lang.String strCode = paydownLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapPaydownRepoCorrelation.containsKey (strCode) ?
			_mapPaydownRepoCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Pay-down and the Repo Latent States
	 * 
	 * @param paydownLabel The Pay-down Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setPaydownRepoCorrelation (
		final org.drip.state.identifier.PaydownLabel paydownLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == paydownLabel || null == repoLabel || null == auCorrelation) return false;

		_mapPaydownRepoCorrelation.put (paydownLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Rating and the Rating Latent States
	 * 
	 * @param ratingLabel1 The Rating Latent State Label #1
	 * @param ratingLabel2 The Rating Latent State Label #2
	 * 
	 * @return The Correlation Surface for the specified Pair of Rating Latent States
	 */

	public org.drip.function.definition.R1ToR1 ratingRatingCorrelation (
		final org.drip.state.identifier.RatingLabel ratingLabel1,
		final org.drip.state.identifier.RatingLabel ratingLabel2)
	{
		if (null == ratingLabel1 || null == ratingLabel2) return null;

		java.lang.String strCode = ratingLabel1.fullyQualifiedName() + "@#" +
			ratingLabel2.fullyQualifiedName();

		return _mapRatingRatingCorrelation.containsKey (strCode) ?
			_mapRatingRatingCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Pair of Rating Latent States
	 * 
	 * @param ratingLabel1 The Rating Latent State Label #1
	 * @param ratingLabel2 The Rating Latent State Label #2
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRatingRatingCorrelation (
		final org.drip.state.identifier.RatingLabel ratingLabel1,
		final org.drip.state.identifier.RatingLabel ratingLabel2,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == ratingLabel1 || null == ratingLabel2 || null == auCorrelation) return false;

		_mapRatingRatingCorrelation.put (ratingLabel1.fullyQualifiedName() + "@#" +
			ratingLabel2.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Rating and Recovery Latent States
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Rating and Recovery Latent States
	 */

	public org.drip.function.definition.R1ToR1 ratingRecoveryCorrelation (
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel)
	{
		if (null == ratingLabel || null == recoveryLabel) return null;

		java.lang.String strCode = ratingLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName();

		return _mapRatingRecoveryCorrelation.containsKey (strCode) ?
			_mapRatingRecoveryCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Rating and Recovery Latent States
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRatingRecoveryCorrelation (
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == ratingLabel || null == recoveryLabel || null == auCorrelation) return false;

		_mapRatingRecoveryCorrelation.put (ratingLabel.fullyQualifiedName() + "@#" +
			recoveryLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Rating and Repo Latent States
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Rating and Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 ratingRepoCorrelation (
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == ratingLabel || null == repoLabel) return null;

		java.lang.String strCode = ratingLabel.fullyQualifiedName() + "@#" + repoLabel.fullyQualifiedName();

		return _mapRatingRepoCorrelation.containsKey (strCode) ? _mapRatingRepoCorrelation.get
			(strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Rating and Repo Latent States
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRatingRepoCorrelation (
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == ratingLabel || null == repoLabel || null == auCorrelation) return false;

		_mapRatingRepoCorrelation.put (ratingLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Correlation Surface for the specified Recovery and the Repo Latent States
	 * 
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Correlation Surface for the specified Recovery and the Repo Latent States
	 */

	public org.drip.function.definition.R1ToR1 recoveryRepoCorrelation (
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		if (null == recoveryLabel || null == repoLabel) return null;

		java.lang.String strCode = recoveryLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName();

		return _mapRecoveryRepoCorrelation.containsKey (strCode) ?
			_mapRecoveryRepoCorrelation.get (strCode) : null;
	}

	/**
	 * (Re)-set the Correlation Surface for the specified Recovery and the Repo Latent States
	 * 
	 * @param recoveryLabel The Recovery Latent State Label
	 * @param repoLabel The Repo Latent State Label
	 * @param auCorrelation The Correlation Surface
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRecoveryRepoCorrelation (
		final org.drip.state.identifier.RecoveryLabel recoveryLabel,
		final org.drip.state.identifier.RepoLabel repoLabel,
		final org.drip.function.definition.R1ToR1 auCorrelation)
	{
		if (null == recoveryLabel || null == repoLabel || null == auCorrelation) return false;

		_mapRecoveryRepoCorrelation.put (recoveryLabel.fullyQualifiedName() + "@#" +
			repoLabel.fullyQualifiedName(), auCorrelation);

		return true;
	}

	/**
	 * Retrieve the Product Quote
	 * 
	 * @param strProductCode Product Code
	 * 
	 * @return Product Quote
	 */

	public org.drip.param.definition.ProductQuote productQuote (
		final java.lang.String strProductCode)
	{
		if (null == strProductCode || strProductCode.isEmpty() || !_mapProductQuote.containsKey
			(strProductCode))
			return null;

		return _mapProductQuote.get (strProductCode);
	}

	/**
	 * (Re)-set the Product Quote
	 * 
	 * @param strProductCode Product Code
	 * @param pq Product Quote
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setProductQuote (
		final java.lang.String strProductCode,
		final org.drip.param.definition.ProductQuote pq)
	{
		if (null == strProductCode || strProductCode.isEmpty() || null == pq) return false;

		_mapProductQuote.put (strProductCode, pq);

		return true;
	}

	/**
	 * Retrieve the Full Set of Quotes
	 * 
	 * @return The Full Set of Quotes
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			quoteMap()
	{
		return _mapProductQuote;
	}

	/**
	 * (Re)-set the Map of Quote
	 * 
	 * @param mapQuote Map of Quotes
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setQuoteMap (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			mapQuote)
	{
		if (null == mapQuote || 0 == mapQuote.size()) return false;

		for (java.util.Map.Entry<java.lang.String, org.drip.param.definition.ProductQuote> meCQ :
			mapQuote.entrySet()) {
			if (null == meCQ) continue;

			java.lang.String strKey = meCQ.getKey();

			org.drip.param.definition.ProductQuote cq = meCQ.getValue();

			if (null == strKey || strKey.isEmpty() || null == cq) continue;

			_mapProductQuote.put (strKey, cq);
		}

		return true;
	}

	/**
	 * Set the Fixing corresponding to the Date/Label Pair
	 * 
	 * @param dt The Fixing Date
	 * @param lsl The Fixing Label
	 * @param dblFixing The Fixing Amount
	 * 
	 * @return TRUE - Entry successfully added
	 */

	public boolean setFixing (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		return _lsfc.add (dt, lsl, dblFixing);
	}

	/**
	 * Set the Fixing corresponding to the Date/Label Pair
	 * 
	 * @param iDate The Fixing Date
	 * @param lsl The Fixing Label
	 * @param dblFixing The Fixing Amount
	 * 
	 * @return TRUE - Entry successfully added
	 */

	public boolean setFixing (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		return _lsfc.add (iDate, lsl, dblFixing);
	}

	/**
	 * Remove the Fixing corresponding to the Date/Label Pair it if exists
	 * 
	 * @param dt The Fixing Date
	 * @param lsl The Fixing Label
	 * 
	 * @return TRUE - Entry successfully removed if it existed
	 */

	public boolean removeFixing (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return _lsfc.remove (dt, lsl);
	}

	/**
	 * Remove the Fixing corresponding to the Date/Label Pair it if exists
	 * 
	 * @param iDate The Fixing Date
	 * @param lsl The Fixing Label
	 * 
	 * @return TRUE - Entry successfully removed if it existed
	 */

	public boolean removeFixing (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return _lsfc.remove (iDate, lsl);
	}

	/**
	 * Retrieve the Fixing for the Specified Date/LSL Combination
	 * 
	 * @param dt Date
	 * @param lsl The Latent State Label
	 * 
	 * @return The Fixing for the Specified Date/LSL Combination
	 * 
	 * @throws java.lang.Exception Thrown if the Fixing cannot be found
	 */

	public double fixing (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		return _lsfc.fixing (dt, lsl);
	}

	/**
	 * Retrieve the Fixing for the Specified Date/LSL Combination
	 * 
	 * @param iDate Date
	 * @param lsl The Latent State Label
	 * 
	 * @return The Fixing for the Specified Date/LSL Combination
	 * 
	 * @throws java.lang.Exception Thrown if the Fixing cannot be found
	 */

	public double fixing (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		return _lsfc.fixing (iDate, lsl);
	}

	/**
	 * Indicates the Availability of the Fixing for the Specified LSL Label on the specified Date
	 * 
	 * @param dt The Date
	 * @param lsl The Label
	 * 
	 * @return TRUE - The Fixing for the Specified LSL Label on the specified Date 
	 */

	public boolean available (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return _lsfc.available (dt, lsl);
	}

	/**
	 * Indicates the Availability of the Fixing for the Specified LSL Label on the specified Date
	 * 
	 * @param iDate The Date
	 * @param lsl The Label
	 * 
	 * @return TRUE - The Fixing for the Specified LSL Label on the specified Date 
	 */

	public boolean available (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return _lsfc.available (iDate, lsl);
	}

	/**
	 * Retrieve the Latent State Fixings
	 * 
	 * @return The Latent State Fixings
	 */

	public org.drip.param.market.LatentStateFixingsContainer fixings()
	{
		return _lsfc;
	}

	/**
	 * Set the Latent State Fixings Container Instance
	 * 
	 * @param lsfc The Latent State Fixings Container Instance
	 * 
	 * @return The Latent State Fixings Container Instance successfully set
	 */

	public boolean setFixings (
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
	{
		_lsfc = lsfc;
		return true;
	}
}
