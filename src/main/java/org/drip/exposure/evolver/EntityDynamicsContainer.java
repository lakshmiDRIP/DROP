
package org.drip.exposure.evolver;

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
 * EntityDynamicsContainer contains the Dealer and the Client Hazard and Recovery Latent State Evolvers. The
 *  References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EntityDynamicsContainer extends org.drip.exposure.evolver.DynamicsContainer
{
	private org.drip.state.identifier.EntityHazardLabel _clientHazardLabel = null;
	private org.drip.state.identifier.EntityHazardLabel _dealerHazardLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _clientRecoveryLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _dealerSeniorRecoveryLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _dealerSubordinateRecoveryLabel = null;

	/**
	 * EntityDynamicsContainer Constructor
	 * 
	 * @param dealerHazardLatentState The Dealer Hazard Rate Latent State
	 * @param dealerSeniorRecoveryLatentState The Dealer Senior Recovery Rate Latent State
	 * @param dealerSubordinateRecoveryLatentState The Dealer Subordinate Rate Latent State
	 * @param clientHazardLatentState The Client Hazard Rate Latent State
	 * @param clientRecoveryLatentState The Client Recovery Rate Latent State
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityDynamicsContainer (
		final org.drip.exposure.evolver.TerminalLatentState dealerHazardLatentState,
		final org.drip.exposure.evolver.TerminalLatentState dealerSeniorRecoveryLatentState,
		final org.drip.exposure.evolver.TerminalLatentState dealerSubordinateRecoveryLatentState,
		final org.drip.exposure.evolver.TerminalLatentState clientHazardLatentState,
		final org.drip.exposure.evolver.TerminalLatentState clientRecoveryLatentState)
		throws java.lang.Exception
	{
		if (null == dealerHazardLatentState ||
			null == dealerSeniorRecoveryLatentState ||
			null == clientHazardLatentState ||
			null == clientRecoveryLatentState)
		{
			throw new java.lang.Exception ("EntityDynamicsContainer Constructor => Invalid Inputs");
		}

		org.drip.state.identifier.LatentStateLabel dealerHazardLabel = dealerHazardLatentState.label();

		org.drip.state.identifier.LatentStateLabel dealerSeniorRecoveryLabel =
			dealerSeniorRecoveryLatentState.label();

		org.drip.state.identifier.LatentStateLabel clientHazardLabel = clientHazardLatentState.label();

		org.drip.state.identifier.LatentStateLabel clientRecoveryLabel = clientRecoveryLatentState.label();

		if (!(dealerHazardLabel instanceof org.drip.state.identifier.EntityHazardLabel) ||
			!(dealerSeniorRecoveryLabel instanceof org.drip.state.identifier.EntityRecoveryLabel) ||
			!(clientHazardLabel instanceof org.drip.state.identifier.EntityHazardLabel) ||
			!(clientRecoveryLabel instanceof org.drip.state.identifier.EntityRecoveryLabel) ||
			!addTerminalLatentState (dealerHazardLatentState) ||
			!addTerminalLatentState (dealerSeniorRecoveryLatentState) ||
			!addTerminalLatentState (clientHazardLatentState) ||
			!addTerminalLatentState (clientRecoveryLatentState))
		{
			throw new java.lang.Exception ("EntityDynamicsContainer Constructor => Invalid Inputs");
		}

		_dealerHazardLabel = (org.drip.state.identifier.EntityHazardLabel) dealerHazardLabel;
		_clientHazardLabel = (org.drip.state.identifier.EntityHazardLabel) clientHazardLabel;
		_dealerSeniorRecoveryLabel = (org.drip.state.identifier.EntityRecoveryLabel)
			dealerSeniorRecoveryLabel;
		_clientRecoveryLabel = (org.drip.state.identifier.EntityRecoveryLabel) clientRecoveryLabel;

		if (null != dealerSubordinateRecoveryLatentState)
		{
			org.drip.state.identifier.LatentStateLabel dealerSubordinateRecoveryLabel =
				dealerSubordinateRecoveryLatentState.label();

			if (!(dealerSubordinateRecoveryLabel instanceof org.drip.state.identifier.EntityRecoveryLabel) ||
				!addTerminalLatentState (dealerSubordinateRecoveryLatentState))
			{
				throw new java.lang.Exception ("EntityDynamicsContainer Constructor => Invalid Inputs");
			}

			_dealerSubordinateRecoveryLabel = (org.drip.state.identifier.EntityRecoveryLabel)
				dealerSubordinateRecoveryLabel;
		}
	}

	/**
	 * Retrieve the Dealer Hazard Rate Evolver
	 * 
	 * @return The Dealer Hazard Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver dealerHazardRateEvolver()
	{
		return terminalLatentStateContainer().entityHazard (_dealerHazardLabel).evolver();
	}

	/**
	 * Retrieve the Dealer Senior Recovery Rate Evolver
	 * 
	 * @return The Dealer Senior Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver dealerSeniorRecoveryRateEvolver()
	{
		return terminalLatentStateContainer().entityRecovery (_dealerSeniorRecoveryLabel).evolver();
	}

	/**
	 * Retrieve the Dealer Subordinate Recovery Rate Evolver
	 * 
	 * @return The Dealer Subordinate Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver dealerSubordinateRecoveryRateEvolver()
	{
		org.drip.exposure.evolver.TerminalLatentState terminalLatentStateDealerSubordinateRecovery =
			terminalLatentStateContainer().entityRecovery (_dealerSubordinateRecoveryLabel);

		return null == terminalLatentStateDealerSubordinateRecovery ? null :
			terminalLatentStateDealerSubordinateRecovery.evolver();
	}

	/**
	 * Retrieve the Client Hazard Rate Evolver
	 * 
	 * @return The Client Hazard Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver clientHazardRateEvolver()
	{
		return terminalLatentStateContainer().entityHazard (_clientHazardLabel).evolver();
	}

	/**
	 * Retrieve the Client Recovery Rate Evolver
	 * 
	 * @return The Client Recovery Rate Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver clientRecoveryRateEvolver()
	{
		return terminalLatentStateContainer().entityRecovery (_clientRecoveryLabel).evolver();
	}
}
