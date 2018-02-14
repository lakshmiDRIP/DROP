
package org.drip.xva.evolver;

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
 * DynamicsContainer holds the Dynamics of the Economy with the following Traded Assets - the Numeraire
 *  Evolver Dynamics, the Terminal Latent State Evolver Dynamics, and the Primary Security Evolver Dynamics.
 *  The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies, Risk, 23 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DynamicsContainer
{
	private java.util.Map<java.lang.String, org.drip.xva.evolver.ScalingNumeraire> _mapScalingNumeraireDynamics =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.PrimarySecurity> _mapPrimarySecurityDynamics
		= null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState>
		_mapTerminalLatentStateDynamics = null;

	/**
	 * Empty DynamicsContainer Constructor 
	 */

	public DynamicsContainer()
	{
		_mapScalingNumeraireDynamics = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.ScalingNumeraire>();

		_mapPrimarySecurityDynamics = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.PrimarySecurity>();

		_mapTerminalLatentStateDynamics = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();
	}

	/**
	 * Retrieve the Scaling Numeraire Evolver Dynamics Settings Map
	 * 
	 * @return The Scaling Numeraire Evolver Dynamics Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.ScalingNumeraire> scalingNumeraireMap()
	{
		return _mapScalingNumeraireDynamics;
	}

	/**
	 * Add the Named Scaling Numeraire
	 * 
	 * @param numeraireName Name of the Scaling Numeraire
	 * @param numeraire Scaling Numeraire Instance
	 * 
	 * @return TRUE - The Scaling Numeraire Successfully added
	 */

	public boolean addScalingNumeraire (
		final java.lang.String numeraireName,
		final org.drip.xva.evolver.ScalingNumeraire numeraire)
	{
		if (null == numeraireName || numeraireName.isEmpty() ||
			null == numeraire)
		{
			return false;
		}

		_mapScalingNumeraireDynamics.put (
			numeraireName,
			numeraire
		);

		return true;
	}

	/**
	 * Indicate if the Scaling Numeraire Exists
	 * 
	 * @param numeraireName The Scaling Numeraire Name
	 * 
	 * @return TRUE - The Scaling Numeraire Exists
	 */

	public boolean scalingNumeraireExists (
		final java.lang.String numeraireName)
	{
		return null != numeraireName && !numeraireName.isEmpty() && _mapScalingNumeraireDynamics.containsKey
			(numeraireName);
	}

	/**
	 * Retrieve the Scaling Numeraire
	 * 
	 * @param numeraireName The Scaling Numeraire Name
	 * 
	 * @return The Scaling Numeraire
	 */

	public org.drip.xva.evolver.ScalingNumeraire scalingNumeraire (
		final java.lang.String numeraireName)
	{
		return !scalingNumeraireExists (numeraireName) ? null : _mapScalingNumeraireDynamics.get
			(numeraireName);
	}

	/**
	 * Retrieve the Terminal Latent State Evolver Dynamics Settings Map
	 * 
	 * @return The Terminal Latent State Evolver Dynamics Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> terminalLatentStateMap()
	{
		return _mapTerminalLatentStateDynamics;
	}

	/**
	 * Add the Terminal Latent State
	 * 
	 * @param terminalLatentState The Terminal Latent State
	 * 
	 * @return TRUE - The Terminal Latent State successfully added
	 */

	public boolean addTerminalLatentState (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		java.lang.String terminalLatentStateLabel = terminalLatentState.label().fullyQualifiedName();

		_mapTerminalLatentStateDynamics.put (
			terminalLatentStateLabel,
			terminalLatentState
		);

		_mapScalingNumeraireDynamics.put (
			terminalLatentStateLabel,
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Terminal Latent State Exists
	 * 
	 * @param label The Terminal Latent State Label
	 * 
	 * @return TRUE - The Terminal Latent State Exists
	 */

	public boolean terminalLatentStateExists (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		return null != label && _mapTerminalLatentStateDynamics.containsKey (label.fullyQualifiedName());
	}

	/**
	 * Retrieve the Terminal Latent State Evolver
	 * 
	 * @param label The Terminal Latent State Label
	 * 
	 * @return The Terminal Latent State Evolver
	 */

	public org.drip.xva.evolver.TerminalLatentState terminalLatentState (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		return terminalLatentStateExists (label) ? _mapTerminalLatentStateDynamics.get
			(label.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Primary Security Evolver Dynamics Settings Map
	 * 
	 * @return The Primary Security Evolver Dynamics Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.PrimarySecurity> primarySecurityMap()
	{
		return _mapPrimarySecurityDynamics;
	}

	/**
	 * Add the Specified Primary Security Instance
	 * 
	 * @param primarySecurity The Primary Security
	 * 
	 * @return TRUE - The Primary Security Successfully Added
	 */

	public boolean addPrimarySecurity (
		final org.drip.xva.evolver.PrimarySecurity primarySecurity)
	{
		if (null == primarySecurity)
		{
			return false;
		}

		java.lang.String primarySecurityLabel = primarySecurity.label().fullyQualifiedName();

		_mapPrimarySecurityDynamics.put (
			primarySecurity.id(),
			primarySecurity
		);

		_mapTerminalLatentStateDynamics.put (
			primarySecurityLabel,
			primarySecurity
		);

		_mapScalingNumeraireDynamics.put (
			primarySecurityLabel,
			primarySecurity
		);

		return true;
	}

	/**
	 * Indicate if the Primary Security Evolver exists in the Container
	 * 
	 * @param id The Primary Security ID
	 * 
	 * @return TRUE - The Primary Security Evolver exists in the Container
	 */

	public boolean primarySecurityExists (
		final java.lang.String id)
	{
		return null == id || id.isEmpty() ? false : _mapPrimarySecurityDynamics.containsKey (id);
	}

	/**
	 * Retrieve the Primary Security Evolver given the Label
	 * 
	 * @param id The Primary Security ID
	 * 
	 * @return The Primary Security Evolver
	 */

	public org.drip.xva.evolver.PrimarySecurity primarySecurity (
		final java.lang.String id)
	{
		return primarySecurityExists (id) ? _mapPrimarySecurityDynamics.get (id) : null;
	}
}
