
package org.drip.exposure.evolver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>EntityDynamicsContainer</i> contains the Dealer and the Client Hazard and Recovery Latent State
 * Evolvers. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/README.md">Securities and Exposure States Evolvers</a></li>
 *  </ul>
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

	/**
	 * Retrieve the Client Hazard Label
	 * 
	 * @return The Client Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel clientHazardLabel()
	{
		return _clientHazardLabel;
	}

	/**
	 * Retrieve the Client Recovery Label
	 * 
	 * @return The Client Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel clientRecoveryLabel()
	{
		return _clientRecoveryLabel;
	}

	/**
	 * Retrieve the Dealer Hazard Label
	 * 
	 * @return The Dealer Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel dealerHazardLabel()
	{
		return _dealerHazardLabel;
	}

	/**
	 * Retrieve the Dealer Senior Recovery Label
	 * 
	 * @return The Dealer Senior Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel dealerSeniorRecoveryLabel()
	{
		return _dealerSeniorRecoveryLabel;
	}

	/**
	 * Retrieve the Dealer Subordinate Recovery Label
	 * 
	 * @return The Dealer Subordinate Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel dealerSubordinateRecoveryLabel()
	{
		return _dealerSubordinateRecoveryLabel;
	}
}
