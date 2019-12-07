
package org.drip.xva.proto;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>CreditDebtGroupSpecification</i> contains the Specification of a Credit/Debt Netting Group. The
 * References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/proto/README.md">Collateral, Counter Party, Netting Groups</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditDebtGroupSpecification extends org.drip.xva.proto.ObjectSpecification
{
	private boolean _contractual = true;
	private boolean _enforceable = true;
	private org.drip.state.identifier.EntityHazardLabel _clientHazardLabel = null;
	private org.drip.state.identifier.EntityHazardLabel _dealerHazardLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _clientRecoveryLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _dealerSeniorRecoveryLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _dealerSubordinateRecoveryLabel = null;

	/**
	 * Generate a Standard Instance of CreditDebtGroupSpecification
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param dealerHazardLabel The Dealer Hazard Rate Latent State Label
	 * @param clientHazardLabel The Client Hazard Rate Latent State Label
	 * @param dealerSeniorRecoveryLabel The Dealer Senior Recovery Rate Latent State Label
	 * @param clientRecoveryLabel The Client Recovery Rate Latent State Label
	 * @param dealerSubordinateRecoveryLabel The Dealer Subordinate Recovery Rate Latent State Label
	 * 
	 * @return Standard Instance of NettingGroupSpecification
	 */

	public static final CreditDebtGroupSpecification Standard (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.state.identifier.EntityHazardLabel dealerHazardLabel,
		final org.drip.state.identifier.EntityHazardLabel clientHazardLabel,
		final org.drip.state.identifier.EntityRecoveryLabel dealerSeniorRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel clientRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel dealerSubordinateRecoveryLabel)
	{
		try {
			return new CreditDebtGroupSpecification (
				id,
				name,
				dealerHazardLabel,
				clientHazardLabel,
				dealerSeniorRecoveryLabel,
				clientRecoveryLabel,
				dealerSubordinateRecoveryLabel,
				true,
				true
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CreditDebtGroupSpecification Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param dealerHazardLabel The Dealer Hazard Rate Latent State Label
	 * @param clientHazardLabel The Client Hazard Rate Latent State Label
	 * @param dealerSeniorRecoveryLabel The Dealer Senior Recovery Rate Latent State Label
	 * @param clientRecoveryLabel The Client Recovery Rate Latent State Label
	 * @param dealerSubordinateRecoveryLabel The Dealer Subordinate Recovery Rate Latent State Label
	 * @param contractual TRUE - The Netting is Contractual
	 * @param enforceable TRUE - The Netting is Enforceable
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CreditDebtGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.state.identifier.EntityHazardLabel dealerHazardLabel,
		final org.drip.state.identifier.EntityHazardLabel clientHazardLabel,
		final org.drip.state.identifier.EntityRecoveryLabel dealerSeniorRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel clientRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel dealerSubordinateRecoveryLabel,
		final boolean contractual,
		final boolean enforceable)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_dealerHazardLabel = dealerHazardLabel) ||
			null == (_clientHazardLabel = clientHazardLabel) ||
			null == (_dealerSeniorRecoveryLabel = dealerSeniorRecoveryLabel) ||
			null == (_clientRecoveryLabel = clientRecoveryLabel))
		{
			throw new java.lang.Exception ("CreditDebtGroupSpecification Constructor");
		}

		_contractual = contractual;
		_enforceable = enforceable;
		_dealerSubordinateRecoveryLabel = dealerSubordinateRecoveryLabel;
	}

	/**
	 * Indicate if the Netting allowed is Contractual
	 * 
	 * @return TRUE - The Netting allowed is Contractual
	 */

	public boolean contractual()
	{
		return _contractual;
	}

	/**
	 * Indicate if the Netting is Enforceable
	 * 
	 * @return TRUE - The Netting is Enforceable
	 */

	public boolean enforceable()
	{
		return _enforceable;
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
	 * Retrieve the Client Hazard Label
	 * 
	 * @return The Client Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel clientHazardLabel()
	{
		return _clientHazardLabel;
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

	/**
	 * Retrieve the Client Recovery Label
	 * 
	 * @return The Client Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel clientRecoveryLabel()
	{
		return _clientRecoveryLabel;
	}
}
