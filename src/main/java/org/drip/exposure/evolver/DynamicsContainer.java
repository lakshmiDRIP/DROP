
package org.drip.exposure.evolver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>DynamicsContainer</i> holds the Dynamics of the Economy with the following Traded Assets - the
 * Numeraire Evolver Dynamics, the Terminal Latent State Evolver Dynamics, and the Primary Security Evolver
 * Dynamics. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
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

public class DynamicsContainer
{
	private org.drip.exposure.evolver.LatentStateDynamicsContainer _latentStateDynamicsContainer = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.PrimarySecurity> _mapPrimarySecurityDynamics
		= null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.ScalingNumeraire>
		_mapScalingNumeraireDynamics = null;

	/**
	 * Empty DynamicsContainer Constructor 
	 */

	public DynamicsContainer()
	{
		_mapScalingNumeraireDynamics = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.ScalingNumeraire>();

		_mapPrimarySecurityDynamics = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.PrimarySecurity>();

		_latentStateDynamicsContainer = new org.drip.exposure.evolver.LatentStateDynamicsContainer();
	}

	/**
	 * Retrieve the Scaling Numeraire Evolver Dynamics Settings Map
	 * 
	 * @return The Scaling Numeraire Evolver Dynamics Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.ScalingNumeraire> scalingNumeraireMap()
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
		final org.drip.exposure.evolver.ScalingNumeraire numeraire)
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

	public org.drip.exposure.evolver.ScalingNumeraire scalingNumeraire (
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

	public org.drip.exposure.evolver.LatentStateDynamicsContainer terminalLatentStateContainer()
	{
		return _latentStateDynamicsContainer;
	}

	/**
	 * Add the Terminal Latent State
	 * 
	 * @param terminalLatentState The Terminal Latent State
	 * 
	 * @return TRUE - The Terminal Latent State successfully added
	 */

	public boolean addTerminalLatentState (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		return _latentStateDynamicsContainer.addTerminalLatentState (terminalLatentState) &&
			addScalingNumeraire (
				terminalLatentState.label().fullyQualifiedName(),
				terminalLatentState
			);
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
		return _latentStateDynamicsContainer.labelExists (label);
	}

	/**
	 * Retrieve the Primary Security Evolver Dynamics Settings Map
	 * 
	 * @return The Primary Security Evolver Dynamics Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.PrimarySecurity> primarySecurityMap()
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
		final org.drip.exposure.evolver.PrimarySecurity primarySecurity)
	{
		if (null == primarySecurity)
		{
			return false;
		}

		_mapPrimarySecurityDynamics.put (
			primarySecurity.id(),
			primarySecurity
		);

		return addTerminalLatentState (primarySecurity) && addScalingNumeraire (
			primarySecurity.label().fullyQualifiedName(),
			primarySecurity
		);
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

	public org.drip.exposure.evolver.PrimarySecurity primarySecurity (
		final java.lang.String id)
	{
		return primarySecurityExists (id) ? _mapPrimarySecurityDynamics.get (id) : null;
	}
}
