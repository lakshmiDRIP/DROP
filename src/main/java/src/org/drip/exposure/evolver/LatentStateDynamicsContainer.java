
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
 * <i>LatentStateDynamicsContainer</i> holds the Latent State Labels for a variety of Latent States and their
 * Evolvers.  The References are:
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

public class LatentStateDynamicsContainer
{
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _fxEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _csaEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _repoEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _customEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _govvieEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _ratingEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _forwardEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _fundingEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _payDownEvolver = null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _overnightEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _collateralEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _volatilityEvolver = 
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _otcFixFloatEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _entityCreditEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _entityEquityEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _entityHazardEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _entityFundingEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> _entityRecoveryEvolver
		= null;

	/**
	 * Empty LatentStateDynamicsContainer Constructor
	 */

	public LatentStateDynamicsContainer()
	{
		_fxEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_csaEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_repoEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_customEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_govvieEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_ratingEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_forwardEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_fundingEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_payDownEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_overnightEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_collateralEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_volatilityEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_otcFixFloatEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_entityCreditEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_entityEquityEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_entityHazardEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_entityFundingEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();

		_entityRecoveryEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.evolver.TerminalLatentState>();
	}

	/**
	 * Retrieve the Equity Evolver Map
	 * 
	 * @return The Equity Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> entityEquityMap()
	{
		return _entityEquityEvolver;
	}

	/**
	 * Add the Entity Equity Latent State Evolver
	 * 
	 * @param terminalLatentState The Equity Entity Terminal Latent State
	 * 
	 * @return TRUE - The Entity Equity Latent Successfully added
	 */

	public boolean addEntityEquity (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityEquityEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Equity Latent State Exists
	 * 
	 * @param entityEquityLabel The Entity Equity Latent State Label
	 * 
	 * @return TRUE - The Entity Equity Latent State Exists
	 */

	public boolean entityEquityExists (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel)
	{
		return null == entityEquityLabel ? false : _entityEquityEvolver.containsKey
			(entityEquityLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Equity Latent State
	 * 
	 * @param entityEquityLabel The Entity Equity Latent State Label
	 * 
	 * @return The Entity Equity Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState entityEquity (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel)
	{
		return entityEquityExists (entityEquityLabel) ? _entityEquityEvolver.get
			(entityEquityLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Funding Evolver Map
	 * 
	 * @return The Funding Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> fundingMap()
	{
		return _fundingEvolver;
	}

	/**
	 * Add the Funding Latent State Evolver
	 * 
	 * @param terminalLatentState The Funding Terminal Latent State
	 * 
	 * @return TRUE - The Funding Latent State Successfully added
	 */

	public boolean addFunding (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_fundingEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Funding Latent State Exists
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return TRUE - The Funding Latent State Exists
	 */

	public boolean fundingExists (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		return null == fundingLabel ? false : _fundingEvolver.containsKey
			(fundingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Funding Latent State
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Funding Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState funding (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		return fundingExists (fundingLabel) ? _fundingEvolver.get (fundingLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Govvie Evolver Map
	 * 
	 * @return The Govvie Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> govvieMap()
	{
		return _govvieEvolver;
	}

	/**
	 * Add the Govvie Latent State Evolver
	 * 
	 * @param terminalLatentState The Govvie Terminal Latent State
	 * 
	 * @return TRUE - The Govvie Latent State Successfully added
	 */

	public boolean addGovvie (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_govvieEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Govvie Latent State Exists
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return TRUE - The Govvie Latent State Exists
	 */

	public boolean govvieExists (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		return null == govvieLabel ? false : _govvieEvolver.containsKey (govvieLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Govvie Latent State
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Govvie Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState govvie (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		return govvieExists (govvieLabel) ? _govvieEvolver.get (govvieLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the FX Evolver Map
	 * 
	 * @return The FX Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> fxMap()
	{
		return _fxEvolver;
	}

	/**
	 * Add the FX Latent State Evolver
	 * 
	 * @param terminalLatentState The FX Terminal Latent State
	 * 
	 * @return TRUE - The FX Latent State Successfully added
	 */

	public boolean addFX (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_fxEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the FX Latent State Exists
	 * 
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return TRUE - The FX Latent State Exists
	 */

	public boolean fxExists (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		return null == fxLabel ? false : _fxEvolver.containsKey (fxLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the FX Latent State
	 * 
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The FX Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState fx (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		return fxExists (fxLabel) ? _fxEvolver.get (fxLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Forward Evolver Map
	 * 
	 * @return The Forward Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> forwardMap()
	{
		return _forwardEvolver;
	}

	/**
	 * Add the Forward Latent State Evolver
	 * 
	 * @param terminalLatentState The Forward Terminal Latent State
	 * 
	 * @return TRUE - The Forward Latent State Successfully added
	 */

	public boolean addForward (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_forwardEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Forward Latent State Exists
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return TRUE - The Forward Latent State Exists
	 */

	public boolean forwardExists (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		return null == forwardLabel ? false : _forwardEvolver.containsKey
			(forwardLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Forward Latent State
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Forward Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState forward (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		return forwardExists (forwardLabel) ? _forwardEvolver.get (forwardLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the OTC Fix Float Evolver Map
	 * 
	 * @return The OTC Fix Float Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> otcFixFloatMap()
	{
		return _otcFixFloatEvolver;
	}

	/**
	 * Add the OTC Fix Float Latent State Evolver
	 * 
	 * @param terminalLatentState The OTC Fix Float Terminal Latent State
	 * 
	 * @return TRUE - The OTC Fix Float Latent State Successfully added
	 */

	public boolean addOTCFixFloat (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_otcFixFloatEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the OTC Fix Float Latent State Exists
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Latent State Label
	 * 
	 * @return TRUE - The OTC Fix Float Latent State Exists
	 */

	public boolean otcFixFloatExists (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
	{
		return null == otcFixFloatLabel ? false : _otcFixFloatEvolver.containsKey
			(otcFixFloatLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the OTC Fix Float Latent State
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Latent State Label
	 * 
	 * @return The OTC Fix Float Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState otcFixFloat (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
	{
		return otcFixFloatExists (otcFixFloatLabel) ? _otcFixFloatEvolver.get
			(otcFixFloatLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Overnight Evolver Map
	 * 
	 * @return The Overnight Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> overnightMap()
	{
		return _overnightEvolver;
	}

	/**
	 * Add the Overnight Latent State Evolver
	 * 
	 * @param terminalLatentState The Overnight Terminal Latent State
	 * 
	 * @return TRUE - The Overnight Latent State Successfully added
	 */

	public boolean addOvernight (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_overnightEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Overnight Latent State Exists
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return TRUE - The Overnight Latent State Exists
	 */

	public boolean overnightExists (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		return null == overnightLabel ? false : _overnightEvolver.containsKey
			(overnightLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Overnight Latent State
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Overnight Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState overnight (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		return overnightExists (overnightLabel) ? _overnightEvolver.get (overnightLabel.fullyQualifiedName())
			: null;
	}

	/**
	 * Retrieve the Collateral Evolver Map
	 * 
	 * @return The Collateral Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> collateralMap()
	{
		return _collateralEvolver;
	}

	/**
	 * Add the Collateral Latent State Evolver
	 * 
	 * @param terminalLatentState The Collateral Terminal Latent State
	 * 
	 * @return TRUE - The Collateral Latent State Successfully added
	 */

	public boolean addCollateral (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_collateralEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Collateral Latent State Exists
	 * 
	 * @param collateralLabel The Collateral Latent State Label
	 * 
	 * @return TRUE - The Collateral Latent State Exists
	 */

	public boolean collateralExists (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
	{
		return null == collateralLabel ? false : _collateralEvolver.containsKey
			(collateralLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Collateral Latent State
	 * 
	 * @param collateralLabel The Collateral Latent State Label
	 * 
	 * @return The Collateral Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState collateral (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
	{
		return collateralExists (collateralLabel) ? _collateralEvolver.get
			(collateralLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the CSA Evolver Map
	 * 
	 * @return The CSA Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> csaMap()
	{
		return _csaEvolver;
	}

	/**
	 * Add the CSA Latent State Evolver
	 * 
	 * @param terminalLatentState The CSA Terminal Latent State
	 * 
	 * @return TRUE - The CSA Latent State Successfully added
	 */

	public boolean addCSA (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_csaEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the CSA Latent State Exists
	 * 
	 * @param csaLabel The CSA Latent State Label
	 * 
	 * @return TRUE - The CSA Latent State Exists
	 */

	public boolean csaExists (
		final org.drip.state.identifier.CSALabel csaLabel)
	{
		return null == csaLabel ? false : _csaEvolver.containsKey (csaLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the CSA Latent State
	 * 
	 * @param csaLabel The CSA Latent State Label
	 * 
	 * @return The CSA Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState csa (
		final org.drip.state.identifier.CSALabel csaLabel)
	{
		return csaExists (csaLabel) ? _csaEvolver.get (csaLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Hazard Evolver Map
	 * 
	 * @return The Entity Hazard Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> entityHazardMap()
	{
		return _entityHazardEvolver;
	}

	/**
	 * Add the Entity Hazard Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Hazard Terminal Latent State
	 * 
	 * @return TRUE - The Entity Hazard Latent State Successfully added
	 */

	public boolean addEntityHazard (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityHazardEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Hazard Latent State Exists
	 * 
	 * @param entityHazardLabel The Entity Hazard Latent State Label
	 * 
	 * @return TRUE - The Entity Hazard Latent State Exists
	 */

	public boolean entityHazardExists (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel)
	{
		return null == entityHazardLabel ? false : _entityHazardEvolver.containsKey
			(entityHazardLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Hazard Latent State
	 * 
	 * @param entityHazardLabel The Entity Hazard Latent State Label
	 * 
	 * @return The Entity Hazard Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState entityHazard (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel)
	{
		return entityHazardExists (entityHazardLabel) ? _entityHazardEvolver.get
			(entityHazardLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Credit Evolver Map
	 * 
	 * @return The Entity Credit Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> entityCreditMap()
	{
		return _entityCreditEvolver;
	}

	/**
	 * Add the Entity Credit Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Credit Terminal Latent State
	 * 
	 * @return TRUE - The Entity Credit Latent State Successfully added
	 */

	public boolean addEntityCredit (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityCreditEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Credit Latent State Exists
	 * 
	 * @param entityCreditLabel The Entity Credit Latent State Label
	 * 
	 * @return TRUE - The Entity Credit Latent State Exists
	 */

	public boolean entityCreditExists (
		final org.drip.state.identifier.EntityCDSLabel entityCreditLabel)
	{
		return null == entityCreditLabel ? false : _entityCreditEvolver.containsKey
			(entityCreditLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Credit Latent State
	 * 
	 * @param entityCreditLabel The Entity Credit Latent State Label
	 * 
	 * @return The Entity Credit Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState entityCredit (
		final org.drip.state.identifier.EntityCDSLabel entityCreditLabel)
	{
		return entityCreditExists (entityCreditLabel) ? _entityCreditEvolver.get
			(entityCreditLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Recovery Evolver Map
	 * 
	 * @return The Entity Recovery Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> entityRecoveryMap()
	{
		return _entityRecoveryEvolver;
	}

	/**
	 * Add the Entity Recovery Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Recovery Terminal Latent State
	 * 
	 * @return TRUE - The Entity Recovery Latent State Successfully added
	 */

	public boolean addEntityRecovery (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityRecoveryEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Recovery Latent State Exists
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Latent State Label
	 * 
	 * @return TRUE - The Entity Recovery Latent State Exists
	 */

	public boolean entityRecoveryExists (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel)
	{
		return null == entityRecoveryLabel ? false : _entityRecoveryEvolver.containsKey
			(entityRecoveryLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Recovery Latent State
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Latent State Label
	 * 
	 * @return The Entity Recovery Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState entityRecovery (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel)
	{
		return entityRecoveryExists (entityRecoveryLabel) ? _entityRecoveryEvolver.get
			(entityRecoveryLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Funding Evolver Map
	 * 
	 * @return The Entity Funding Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> entityFundingMap()
	{
		return _entityFundingEvolver;
	}

	/**
	 * Add the Entity Funding Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Funding Terminal Latent State
	 * 
	 * @return TRUE - The Entity Funding Latent State Successfully added
	 */

	public boolean addEntityFunding (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityFundingEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Funding Latent State Exists
	 * 
	 * @param entityFundingLabel The Entity Funding Latent State Label
	 * 
	 * @return TRUE - The Entity Funding Latent State Exists
	 */

	public boolean entityFundingExists (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel)
	{
		return null == entityFundingLabel ? false : _entityFundingEvolver.containsKey
			(entityFundingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Funding Latent State
	 * 
	 * @param entityFundingLabel The Entity Funding Latent State Label
	 * 
	 * @return The Entity Funding Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState entityFunding (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel)
	{
		return entityFundingExists (entityFundingLabel) ? _entityFundingEvolver.get
			(entityFundingLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Volatility Evolver Map
	 * 
	 * @return The Volatility Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> volatilityMap()
	{
		return _volatilityEvolver;
	}

	/**
	 * Add the Volatility Latent State Evolver
	 * 
	 * @param terminalLatentState The Volatility Terminal Latent State
	 * 
	 * @return TRUE - The Volatility Latent State Successfully added
	 */

	public boolean addVolatility (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_volatilityEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Volatility Latent State Exists
	 * 
	 * @param volatilityLabel The Volatility Latent State Label
	 * 
	 * @return TRUE - The Volatility Latent State Exists
	 */

	public boolean volatilityExists (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel)
	{
		return null == volatilityLabel ? false : _volatilityEvolver.containsKey
			(volatilityLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Volatility Latent State
	 * 
	 * @param volatilityLabel The Volatility Latent State Label
	 * 
	 * @return The Volatility Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState volatility (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel)
	{
		return volatilityExists (volatilityLabel) ? _volatilityEvolver.get
			(volatilityLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Ratings Evolver Map
	 * 
	 * @return The Ratings Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> ratingMap()
	{
		return _ratingEvolver;
	}

	/**
	 * Add the Rating Latent State Evolver
	 * 
	 * @param terminalLatentState The Rating Terminal Latent State
	 * 
	 * @return TRUE - The Rating Latent State Successfully added
	 */

	public boolean addRating (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_ratingEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Rating Latent State Exists
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return TRUE - The Rating Latent State Exists
	 */

	public boolean ratingExists (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		return null == ratingLabel ? false : _ratingEvolver.containsKey (ratingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Rating Latent State
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Rating Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState rating (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		return ratingExists (ratingLabel) ? _ratingEvolver.get (ratingLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Repo Evolver Map
	 * 
	 * @return The Repo Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> repoMap()
	{
		return _repoEvolver;
	}

	/**
	 * Add the Repo Latent State Evolver
	 * 
	 * @param terminalLatentState The Repo Terminal Latent State
	 * 
	 * @return TRUE - The Repo Latent State Successfully added
	 */

	public boolean addRepo (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_repoEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Repo Latent State Exists
	 * 
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return TRUE - The Repo Latent State Exists
	 */

	public boolean repoExists (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		return null == repoLabel ? false : _repoEvolver.containsKey (repoLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Repo Latent State
	 * 
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Repo Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState repo (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		return repoExists (repoLabel) ? _repoEvolver.get (repoLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Pay Down Evolver Map
	 * 
	 * @return The Pay Down Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> payDownMap()
	{
		return _payDownEvolver;
	}

	/**
	 * Add the Pay Down Latent State Evolver
	 * 
	 * @param terminalLatentState The Pay Down Terminal Latent State
	 * 
	 * @return TRUE - The Repo Latent State Successfully added
	 */

	public boolean addPayDown (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_payDownEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Pay Down Latent State Exists
	 * 
	 * @param payDownLabel The Pay Down Latent State Label
	 * 
	 * @return TRUE - The Pay Down Latent State Exists
	 */

	public boolean payDownExists (
		final org.drip.state.identifier.PaydownLabel payDownLabel)
	{
		return null == payDownLabel ? false : _payDownEvolver.containsKey
			(payDownLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Pay Down Latent State
	 * 
	 * @param payDownLabel The Pay Down Latent State Label
	 * 
	 * @return The Pay Down Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState payDown (
		final org.drip.state.identifier.PaydownLabel payDownLabel)
	{
		return payDownExists (payDownLabel) ? _payDownEvolver.get (payDownLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Custom Evolver Map
	 * 
	 * @return The Custom Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.evolver.TerminalLatentState> customMap()
	{
		return _customEvolver;
	}

	/**
	 * Add the Custom Latent State Evolver
	 * 
	 * @param terminalLatentState The Custom Terminal Latent State
	 * 
	 * @return TRUE - The Custom Latent State Successfully added
	 */

	public boolean addCustom (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_customEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Custom Latent State Exists
	 * 
	 * @param customLabel The Custom Latent State Label
	 * 
	 * @return TRUE - The Custom Latent State Exists
	 */

	public boolean customExists (
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		return null == customLabel ? false : _customEvolver.containsKey (customLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Custom Latent State
	 * 
	 * @param customLabel The Custom Latent State Label
	 * 
	 * @return The Custom Latent State
	 */

	public org.drip.exposure.evolver.TerminalLatentState custom (
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		return customExists (customLabel) ? _customEvolver.get (customLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Add the Terminal Latent State
	 * 
	 * @param terminalLatentState The Terminal Latent State
	 * 
	 * @return TRUE - The Terminal Latent State Successfully added
	 */

	public boolean addTerminalLatentState (
		final org.drip.exposure.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		org.drip.state.identifier.LatentStateLabel label = terminalLatentState.label();

		if (label instanceof org.drip.state.identifier.EntityEquityLabel)
		{
			return addEntityEquity (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.FundingLabel)
		{
			return addFunding (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.GovvieLabel)
		{
			return addGovvie (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.FXLabel)
		{
			return addFX (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.ForwardLabel)
		{
			return addForward (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.OTCFixFloatLabel)
		{
			return addOTCFixFloat (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.OvernightLabel)
		{
			return addOvernight (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.CollateralLabel)
		{
			return addCollateral (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.CSALabel)
		{
			return addCSA (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityHazardLabel)
		{
			return addEntityHazard (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityRecoveryLabel)
		{
			return addEntityRecovery (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityFundingLabel)
		{
			return addEntityFunding (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityCDSLabel)
		{
			return addEntityCredit (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.VolatilityLabel)
		{
			return addVolatility (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.RatingLabel)
		{
			return addRating (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.RepoLabel)
		{
			return addRepo (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.PaydownLabel)
		{
			return addPayDown (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.CustomLabel)
		{
			return addCustom (terminalLatentState);
		}

		return false;
	}

	/**
	 * Indicate if the Label exists
	 * 
	 * @param label The Latent State Label
	 * 
	 * @return TRUE - The Latent State Label exists
	 */

	public boolean labelExists (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		if (null == label)
		{
			return false;
		}

		if (label instanceof org.drip.state.identifier.EntityEquityLabel)
		{
			return entityEquityExists ((org.drip.state.identifier.EntityEquityLabel) label);
		}

		if (label instanceof org.drip.state.identifier.FundingLabel)
		{
			return fundingExists ((org.drip.state.identifier.FundingLabel) label);
		}

		if (label instanceof org.drip.state.identifier.GovvieLabel)
		{
			return govvieExists ((org.drip.state.identifier.GovvieLabel) label);
		}

		if (label instanceof org.drip.state.identifier.FXLabel)
		{
			return fxExists((org.drip.state.identifier.FXLabel) label);
		}

		if (label instanceof org.drip.state.identifier.ForwardLabel)
		{
			return forwardExists ((org.drip.state.identifier.ForwardLabel) label);
		}

		if (label instanceof org.drip.state.identifier.OTCFixFloatLabel)
		{
			return otcFixFloatExists ((org.drip.state.identifier.OTCFixFloatLabel) label);
		}

		if (label instanceof org.drip.state.identifier.OvernightLabel)
		{
			return overnightExists ((org.drip.state.identifier.OvernightLabel) label);
		}

		if (label instanceof org.drip.state.identifier.CollateralLabel)
		{
			return collateralExists ((org.drip.state.identifier.CollateralLabel) label);
		}

		if (label instanceof org.drip.state.identifier.CSALabel)
		{
			return csaExists ((org.drip.state.identifier.CSALabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityHazardLabel)
		{
			return entityHazardExists ((org.drip.state.identifier.EntityHazardLabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityCDSLabel)
		{
			return entityCreditExists ((org.drip.state.identifier.EntityCDSLabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityRecoveryLabel)
		{
			return entityRecoveryExists ((org.drip.state.identifier.EntityRecoveryLabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityFundingLabel)
		{
			return entityFundingExists ((org.drip.state.identifier.EntityFundingLabel) label);
		}

		if (label instanceof org.drip.state.identifier.VolatilityLabel)
		{
			return volatilityExists ((org.drip.state.identifier.VolatilityLabel) label);
		}

		if (label instanceof org.drip.state.identifier.RatingLabel)
		{
			return ratingExists ((org.drip.state.identifier.RatingLabel) label);
		}

		if (label instanceof org.drip.state.identifier.RepoLabel)
		{
			return repoExists ((org.drip.state.identifier.RepoLabel) label);
		}

		if (label instanceof org.drip.state.identifier.PaydownLabel)
		{
			return payDownExists ((org.drip.state.identifier.PaydownLabel) label);
		}

		if (label instanceof org.drip.state.identifier.CustomLabel)
		{
			return customExists ((org.drip.state.identifier.CustomLabel) label);
		}

		return false;
	}

	/**
	 * Retrieve the Terminal Latent State corresponding to the Label
	 * 
	 * @param label The Latent State Label
	 * 
	 * @return The Terminal Latent State corresponding to the Label
	 */

	public org.drip.exposure.evolver.TerminalLatentState terminal (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		if (null == label)
		{
			return null;
		}

		if (label instanceof org.drip.state.identifier.EntityEquityLabel &&
			entityEquityExists ((org.drip.state.identifier.EntityEquityLabel) label))
		{
			return _entityEquityEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.FundingLabel &&
			fundingExists ((org.drip.state.identifier.FundingLabel) label))
		{
			return _fundingEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.GovvieLabel &&
			govvieExists ((org.drip.state.identifier.GovvieLabel) label))
		{
			return _govvieEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.FXLabel &&
			fxExists ((org.drip.state.identifier.FXLabel) label))
		{
			return _fxEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.ForwardLabel &&
			forwardExists ((org.drip.state.identifier.ForwardLabel) label))
		{
			return _forwardEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.OTCFixFloatLabel &&
			otcFixFloatExists ((org.drip.state.identifier.OTCFixFloatLabel) label))
		{
			return _otcFixFloatEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.OvernightLabel &&
			overnightExists ((org.drip.state.identifier.OvernightLabel) label))
		{
			return _overnightEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.CollateralLabel &&
			collateralExists ((org.drip.state.identifier.CollateralLabel) label))
		{
			return _collateralEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.CSALabel &&
			csaExists ((org.drip.state.identifier.CSALabel) label))
		{
			return _csaEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.EntityHazardLabel &&
			entityHazardExists ((org.drip.state.identifier.EntityHazardLabel) label))
		{
			return _entityHazardEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.EntityCDSLabel &&
			entityCreditExists ((org.drip.state.identifier.EntityCDSLabel) label))
		{
			return _entityCreditEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.EntityRecoveryLabel &&
			entityRecoveryExists ((org.drip.state.identifier.EntityRecoveryLabel) label))
		{
			return _entityRecoveryEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.EntityFundingLabel &&
			entityFundingExists ((org.drip.state.identifier.EntityFundingLabel) label))
		{
			return _entityFundingEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.VolatilityLabel &&
			volatilityExists ((org.drip.state.identifier.VolatilityLabel) label))
		{
			return _volatilityEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.RatingLabel &&
			ratingExists ((org.drip.state.identifier.RatingLabel) label))
		{
			return _ratingEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.RepoLabel &&
			repoExists ((org.drip.state.identifier.RepoLabel) label))
		{
			return _repoEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.PaydownLabel &&
			payDownExists ((org.drip.state.identifier.PaydownLabel) label))
		{
			return _payDownEvolver.get (label.fullyQualifiedName());
		}

		if (label instanceof org.drip.state.identifier.CustomLabel &&
			customExists ((org.drip.state.identifier.CustomLabel) label))
		{
			return _customEvolver.get (label.fullyQualifiedName());
		}

		return null;
	}
}
