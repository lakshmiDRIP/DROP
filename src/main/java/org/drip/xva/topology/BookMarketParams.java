
package org.drip.xva.topology;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * BookMarketParams contains the Market Parameters that correspond to a given Book. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 *  	Collateral Trading <b>https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301</b><br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BookMarketParams
{
	private java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> _csaLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> _overnightLabelMap =
		null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> _bankHazardLabelMap
		= null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		_counterPartyHazardLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		_bankSeniorRecoveryLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		_counterPartyRecoveryLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		_bankSubordinateRecoveryLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		_bankSeniorFundingLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		_counterPartyFundingLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		_bankSubordinateFundingLabelMap = null;

	/**
	 * BookMarketParams Constructor
	 * 
	 * @param overnightLabelMap Map of Overnight Labels
	 * @param csaLabelMap Map of CSA Labels
	 * @param bankHazardLabelMap Map of Bank Hazard Labels
	 * @param counterPartyHazardLabelMap Map of Counter Party Hazard Labels
	 * @param bankSeniorRecoveryLabelMap Map of Bank Senior Recovery Labels
	 * @param counterPartyRecoveryLabelMap Map of Counter Party Recovery Labels
	 * @param bankSubordinateRecoveryLabelMap Map of Bank Subordinate Recovery Labels
	 * @param bankSeniorFundingLabelMap Map of Bank Senior Funding Labels
	 * @param counterPartyFundingLabelMap Map of Counter Party Funding Labels
	 * @param bankSubordinateFundingLabelMap Map of Bank Subordinate Funding Labels
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BookMarketParams (
		final java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
			bankHazardLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
			counterPartyHazardLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			bankSeniorRecoveryLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			counterPartyRecoveryLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			bankSubordinateRecoveryLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			bankSeniorFundingLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			counterPartyFundingLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			bankSubordinateFundingLabelMap)
		throws java.lang.Exception
	{
		if (null == (_overnightLabelMap = overnightLabelMap) || 0 == _overnightLabelMap.size() ||
			null == (_csaLabelMap = csaLabelMap) || 0 == _csaLabelMap.size() ||
			null == (_bankHazardLabelMap = bankHazardLabelMap) || 0 == _bankHazardLabelMap.size() ||
			null == (_counterPartyHazardLabelMap = counterPartyHazardLabelMap) || 0 ==
				_counterPartyHazardLabelMap.size() ||
			null == (_bankSeniorRecoveryLabelMap = bankSeniorRecoveryLabelMap) || 0 ==
				_bankSeniorRecoveryLabelMap.size() ||
			null == (_counterPartyRecoveryLabelMap = counterPartyRecoveryLabelMap) || 0 ==
				_counterPartyRecoveryLabelMap.size() ||
			null == (_bankSeniorFundingLabelMap = bankSeniorFundingLabelMap) || 0 ==
				_bankSeniorFundingLabelMap.size() ||
			null == (_counterPartyFundingLabelMap = counterPartyFundingLabelMap) || 0 ==
				_counterPartyFundingLabelMap.size())
		{
			throw new java.lang.Exception ("BookMarketParams Constructor => Invalid Inputs");
		}

		_bankSubordinateFundingLabelMap = bankSubordinateFundingLabelMap;
		_bankSubordinateRecoveryLabelMap = bankSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Overnight Labels
	 * 
	 * @return The Map of Overnight Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap()
	{
		return _overnightLabelMap;
	}

	/**
	 * Retrieve the Map of CSA Labels
	 * 
	 * @return The Map of CSA Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap()
	{
		return _csaLabelMap;
	}

	/**
	 * Retrieve the Map of Bank Hazard Labels
	 * 
	 * @return The Map of Bank Hazard Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> bankHazardLabelMap()
	{
		return _bankHazardLabelMap;
	}

	/**
	 * Retrieve the Map of Counter Party Hazard Labels
	 * 
	 * @return The Map of Counter Party Hazard Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		counterPartyHazardLabelMap()
	{
		return _counterPartyHazardLabelMap;
	}

	/**
	 * Retrieve the Map of Bank Senior Recovery Labels
	 * 
	 * @return The Map of Bank Senior Recovery Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		bankSeniorRecoveryLabelMap()
	{
		return _bankSeniorRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Counter Party Recovery Labels
	 * 
	 * @return The Map of Counter Party Recovery Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		counterPartyRecoveryLabelMap()
	{
		return _counterPartyRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Bank Subordinate Recovery Labels
	 * 
	 * @return The Map of Bank Subordinate Recovery Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		bankSubordinateRecoveryLabelMap()
	{
		return _bankSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Bank Senior Funding Labels
	 * 
	 * @return The Map of Bank Senior Funding Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		bankSeniorFundingLabelMap()
	{
		return _bankSeniorFundingLabelMap;
	}

	/**
	 * Retrieve the Map of Counter Party Funding Labels
	 * 
	 * @return The Map of Counter Party Funding Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		counterPartyFundingLabelMap()
	{
		return _counterPartyFundingLabelMap;
	}

	/**
	 * Retrieve the Map of Bank Subordinate Funding Labels
	 * 
	 * @return The Map of Bank Subordinate Funding Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		bankSubordinateFundingLabelMap()
	{
		return _bankSubordinateFundingLabelMap;
	}
}
