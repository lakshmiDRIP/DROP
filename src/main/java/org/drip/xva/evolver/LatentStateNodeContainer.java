
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
 * LatentStateNodeContainer holds the Latent State Labels and their corresponding Nodal Realizations. The
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
 *  - Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 *  	Collateral Trading <b>https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301</b><br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateNodeContainer
{
	private java.util.Map<java.lang.String, java.lang.Double> _fx = null;
	private java.util.Map<java.lang.String, java.lang.Double> _csa = null;
	private java.util.Map<java.lang.String, java.lang.Double> _repo = null;
	private java.util.Map<java.lang.String, java.lang.Double> _custom = null;
	private java.util.Map<java.lang.String, java.lang.Double> _govvie = null;
	private java.util.Map<java.lang.String, java.lang.Double> _rating = null;
	private java.util.Map<java.lang.String, java.lang.Double> _forward = null;
	private java.util.Map<java.lang.String, java.lang.Double> _funding = null;
	private java.util.Map<java.lang.String, java.lang.Double> _payDown = null;
	private java.util.Map<java.lang.String, java.lang.Double> _overnight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _collateral = null;
	private java.util.Map<java.lang.String, java.lang.Double> _volatility = null;
	private java.util.Map<java.lang.String, java.lang.Double> _otcFixFloat = null;
	private java.util.Map<java.lang.String, java.lang.Double> _entityCredit = null;
	private java.util.Map<java.lang.String, java.lang.Double> _entityEquity = null;
	private java.util.Map<java.lang.String, java.lang.Double> _entityHazard = null;
	private java.util.Map<java.lang.String, java.lang.Double> _entityFunding = null;
	private java.util.Map<java.lang.String, java.lang.Double> _entityRecovery = null;

	/**
	 * Empty LatentStateNodeContainer Constructor
	 */

	public LatentStateNodeContainer()
	{
		_fx = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_csa = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_repo = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_custom = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_govvie = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_rating = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_forward = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_funding = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_payDown = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_overnight = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_collateral = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_volatility = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_otcFixFloat = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_entityCredit = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_entityEquity = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_entityHazard = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_entityFunding = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_entityRecovery = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
	}

	/**
	 * Retrieve the FX Latent State Node Container
	 * 
	 * @return The FX Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fx()
	{
		return _fx;
	}

	/**
	 * Retrieve the CSA Latent State Node Container
	 * 
	 * @return The CSA Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> csa()
	{
		return _csa;
	}

	/**
	 * Retrieve the Repo Latent State Node Container
	 * 
	 * @return The Repo Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> repo()
	{
		return _repo;
	}

	/**
	 * Retrieve the Custom Latent State Node Container
	 * 
	 * @return The Custom Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> custom()
	{
		return _custom;
	}

	/**
	 * Retrieve the Govvie Latent State Node Container
	 * 
	 * @return The Govvie Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> govvie()
	{
		return _govvie;
	}

	/**
	 * Retrieve the Govvie Latent State Node Container
	 * 
	 * @return The Govvie Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> rating()
	{
		return _rating;
	}

	/**
	 * Retrieve the Forward Latent State Node Container
	 * 
	 * @return The Forward Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> forward()
	{
		return _forward;
	}

	/**
	 * Retrieve the Funding Latent State Node Container
	 * 
	 * @return The Funding Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> funding()
	{
		return _funding;
	}

	/**
	 * Retrieve the Pay Down Latent State Node Container
	 * 
	 * @return The Pay Down Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> payDown()
	{
		return _payDown;
	}

	/**
	 * Retrieve the Overnight Latent State Node Container
	 * 
	 * @return The Overnight Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> overnight()
	{
		return _overnight;
	}

	/**
	 * Retrieve the Collateral Latent State Node Container
	 * 
	 * @return The Collateral Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> collateral()
	{
		return _collateral;
	}

	/**
	 * Retrieve the Volatility Latent State Node Container
	 * 
	 * @return The Volatility Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> volatility()
	{
		return _volatility;
	}

	/**
	 * Retrieve the OTC Fix Float Latent State Node Container
	 * 
	 * @return The OTC Fix Float Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> otcFixFloat()
	{
		return _otcFixFloat;
	}

	/**
	 * Retrieve the Entity Credit Latent State Node Container
	 * 
	 * @return The Entity Credit Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> entityCredit()
	{
		return _entityCredit;
	}

	/**
	 * Retrieve the Entity Equity Latent State Node Container
	 * 
	 * @return The Entity Equity Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> entityEquity()
	{
		return _entityEquity;
	}

	/**
	 * Retrieve the Entity Hazard Latent State Node Container
	 * 
	 * @return The Entity Hazard Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> entityHazard()
	{
		return _entityHazard;
	}

	/**
	 * Retrieve the Entity Funding Latent State Node Container
	 * 
	 * @return The Entity Funding Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> entityFunding()
	{
		return _entityFunding;
	}

	/**
	 * Retrieve the Entity Recovery Latent State Node Container
	 * 
	 * @return The Entity Recovery Latent State Node Container
	 */

	public java.util.Map<java.lang.String, java.lang.Double> entityRecovery()
	{
		return _entityRecovery;
	}

	/**
	 * Add the Labeled FX
	 * 
	 * @param fxLabel The FX Label
	 * @param fx The FX
	 * 
	 * @return The Labeled FX successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.FXLabel fxLabel,
		final double fx)
	{
		if (null == fxLabel || !org.drip.quant.common.NumberUtil.IsValid (fx))
		{
			return false;
		}

		_fx.put (
			fxLabel.fullyQualifiedName(),
			fx
		);

		return true;
	}

	/**
	 * Check Presence of Labeled FX
	 * 
	 * @param fxLabel The FX Label
	 * 
	 * @return The Labeled FX exists
	 */

	public boolean exists (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		return null != fxLabel && _fx.containsKey (fxLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled FX
	 * 
	 * @param fxLabel The FX Label
	 * 
	 * @return The Labeled FX
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double fx (
		final org.drip.state.identifier.FXLabel fxLabel)
		throws java.lang.Exception
	{
		if (!exists (fxLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::fx => Invalid Inputs");
		}

		return _fx.get (fxLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Repo
	 * 
	 * @param repoLabel The Repo Label
	 * @param repo The Repo
	 * 
	 * @return The Labeled Repo successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.RepoLabel repoLabel,
		final double repo)
	{
		if (null == repoLabel || !org.drip.quant.common.NumberUtil.IsValid (repo))
		{
			return false;
		}

		_repo.put (
			repoLabel.fullyQualifiedName(),
			repo
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Repo
	 * 
	 * @param repoLabel The Repo Label
	 * 
	 * @return The Labeled Repo exists
	 */

	public boolean exists (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		return null != repoLabel && _repo.containsKey (repoLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Repo
	 * 
	 * @param repoLabel The Repo Label
	 * 
	 * @return The Labeled Repo
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double repo (
		final org.drip.state.identifier.RepoLabel repoLabel)
		throws java.lang.Exception
	{
		if (!exists (repoLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::repo => Invalid Inputs");
		}

		return _repo.get (repoLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Custom
	 * 
	 * @param customLabel The Custom Label
	 * @param custom The Custom
	 * 
	 * @return The Labeled Custom successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.CustomLabel customLabel,
		final double custom)
	{
		if (null == customLabel || !org.drip.quant.common.NumberUtil.IsValid (custom))
		{
			return false;
		}

		_custom.put (
			customLabel.fullyQualifiedName(),
			custom
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Custom
	 * 
	 * @param customLabel The Custom Label
	 * 
	 * @return The Labeled Custom exists
	 */

	public boolean exists (
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		return null != customLabel && _custom.containsKey (customLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Custom
	 * 
	 * @param customLabel The Custom Label
	 * 
	 * @return The Labeled Custom
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double custom (
		final org.drip.state.identifier.CustomLabel customLabel)
		throws java.lang.Exception
	{
		if (!exists (customLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::custom => Invalid Inputs");
		}

		return _custom.get (customLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Govvie
	 * 
	 * @param govvieLabel The Govvie Label
	 * @param govvie The Govvie
	 * 
	 * @return The Labeled Govvie successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.GovvieLabel govvieLabel,
		final double govvie)
	{
		if (null == govvieLabel || !org.drip.quant.common.NumberUtil.IsValid (govvie))
		{
			return false;
		}

		_govvie.put (
			govvieLabel.fullyQualifiedName(),
			govvie
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Govvie
	 * 
	 * @param govvieLabel The Govvie Label
	 * 
	 * @return The Labeled Govvie exists
	 */

	public boolean exists (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		return null != govvieLabel && _govvie.containsKey (govvieLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Govvie
	 * 
	 * @param govvieLabel The Govvie Label
	 * 
	 * @return The Labeled Govvie
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double govvie (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
		throws java.lang.Exception
	{
		if (!exists (govvieLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::govvie => Invalid Inputs");
		}

		return _govvie.get (govvieLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Rating
	 * 
	 * @param ratingLabel The Rating Label
	 * @param rating The Rating
	 * 
	 * @return The Labeled Rating successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.RatingLabel ratingLabel,
		final double rating)
	{
		if (null == ratingLabel || !org.drip.quant.common.NumberUtil.IsValid (rating))
		{
			return false;
		}

		_rating.put (
			ratingLabel.fullyQualifiedName(),
			rating
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Rating
	 * 
	 * @param ratingLabel The Rating Label
	 * 
	 * @return The Labeled Rating exists
	 */

	public boolean exists (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		return null != ratingLabel && _rating.containsKey (ratingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Rating
	 * 
	 * @param ratingLabel The Rating Label
	 * 
	 * @return The Labeled Rating
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double rating (
		final org.drip.state.identifier.RatingLabel ratingLabel)
		throws java.lang.Exception
	{
		if (!exists (ratingLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::rating => Invalid Inputs");
		}

		return _rating.get (ratingLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Forward
	 * 
	 * @param forwardLabel The Forward Label
	 * @param forward The Forward
	 * 
	 * @return The Labeled Forward successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double forward)
	{
		if (null == forwardLabel || !org.drip.quant.common.NumberUtil.IsValid (forward))
		{
			return false;
		}

		_forward.put (
			forwardLabel.fullyQualifiedName(),
			forward
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Forward
	 * 
	 * @param forwardLabel The Forward Label
	 * 
	 * @return The Labeled Forward exists
	 */

	public boolean exists (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		return null != forwardLabel && _forward.containsKey (forwardLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Forward
	 * 
	 * @param forwardLabel The Forward Label
	 * 
	 * @return The Labeled Forward
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double forward (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
		throws java.lang.Exception
	{
		if (!exists (forwardLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::forward => Invalid Inputs");
		}

		return _forward.get (forwardLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Funding
	 * 
	 * @param fundingLabel The Funding Label
	 * @param funding The Funding
	 * 
	 * @return The Labeled Funding successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.FundingLabel fundingLabel,
		final double funding)
	{
		if (null == fundingLabel || !org.drip.quant.common.NumberUtil.IsValid (funding))
		{
			return false;
		}

		_funding.put (
			fundingLabel.fullyQualifiedName(),
			funding
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Funding
	 * 
	 * @param fundingLabel The Funding Label
	 * 
	 * @return The Labeled Funding exists
	 */

	public boolean exists (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		return null != fundingLabel && _funding.containsKey (fundingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Funding
	 * 
	 * @param fundingLabel The Funding Label
	 * 
	 * @return The Labeled Funding
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double funding (
		final org.drip.state.identifier.FundingLabel fundingLabel)
		throws java.lang.Exception
	{
		if (!exists (fundingLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::funding => Invalid Inputs");
		}

		return _funding.get (fundingLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Pay Down
	 * 
	 * @param payDownLabel The Pay Down Label
	 * @param payDown The Pay Down
	 * 
	 * @return The Labeled Pay Down successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.PaydownLabel payDownLabel,
		final double payDown)
	{
		if (null == payDownLabel || !org.drip.quant.common.NumberUtil.IsValid (payDown))
		{
			return false;
		}

		_payDown.put (
			payDownLabel.fullyQualifiedName(),
			payDown
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Pay Down
	 * 
	 * @param payDownLabel The Pay Down Label
	 * 
	 * @return The Labeled Pay Down exists
	 */

	public boolean exists (
		final org.drip.state.identifier.PaydownLabel payDownLabel)
	{
		return null != payDownLabel && _payDown.containsKey (payDownLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Pay Down
	 * 
	 * @param payDownLabel The Pay Down Label
	 * 
	 * @return The Labeled Pay Down
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double payDown (
		final org.drip.state.identifier.PaydownLabel payDownLabel)
		throws java.lang.Exception
	{
		if (!exists (payDownLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::payDown => Invalid Inputs");
		}

		return _payDown.get (payDownLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Overnight
	 * 
	 * @param overnightLabel The Overnight Label
	 * @param overnight The Overnight
	 * 
	 * @return The Labeled Overnight successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final double overnight)
	{
		if (null == overnightLabel || !org.drip.quant.common.NumberUtil.IsValid (overnight))
		{
			return false;
		}

		_overnight.put (
			overnightLabel.fullyQualifiedName(),
			overnight
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Overnight
	 * 
	 * @param overnightLabel The Overnight Label
	 * 
	 * @return The Labeled Overnight exists
	 */

	public boolean exists (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		return null != overnightLabel && _overnight.containsKey (overnightLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Overnight
	 * 
	 * @param overnightLabel The Overnight Label
	 * 
	 * @return The Labeled Overnight
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double overnight (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
		throws java.lang.Exception
	{
		if (!exists (overnightLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::overnight => Invalid Inputs");
		}

		return _overnight.get (overnightLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Collateral
	 * 
	 * @param collateralLabel The Collateral Label
	 * @param collateral The Collateral
	 * 
	 * @return The Labeled Collateral successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.CollateralLabel collateralLabel,
		final double collateral)
	{
		if (null == collateralLabel || !org.drip.quant.common.NumberUtil.IsValid (collateral))
		{
			return false;
		}

		_collateral.put (
			collateralLabel.fullyQualifiedName(),
			collateral
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Collateral
	 * 
	 * @param collateralLabel The Collateral Label
	 * 
	 * @return The Labeled Collateral exists
	 */

	public boolean exists (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
	{
		return null != collateralLabel && _collateral.containsKey (collateralLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Collateral
	 * 
	 * @param collateralLabel The Collateral Label
	 * 
	 * @return The Labeled Collateral
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double collateral (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
		throws java.lang.Exception
	{
		if (!exists (collateralLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::collateral => Invalid Inputs");
		}

		return _collateral.get (collateralLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Volatility
	 * 
	 * @param volatilityLabel The Volatility Label
	 * @param volatility The Volatility
	 * 
	 * @return The Labeled Volatility successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel,
		final double volatility)
	{
		if (null == volatilityLabel || !org.drip.quant.common.NumberUtil.IsValid (volatility))
		{
			return false;
		}

		_volatility.put (
			volatilityLabel.fullyQualifiedName(),
			volatility
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Volatility
	 * 
	 * @param volatilityLabel The Volatility Label
	 * 
	 * @return The Labeled Volatility exists
	 */

	public boolean exists (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel)
	{
		return null != volatilityLabel && _volatility.containsKey (volatilityLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Volatility
	 * 
	 * @param volatilityLabel The Volatility Label
	 * 
	 * @return The Labeled Volatility
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double volatility (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel)
		throws java.lang.Exception
	{
		if (!exists (volatilityLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::volatility => Invalid Inputs");
		}

		return _volatility.get (volatilityLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled OTC Fix Float
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Label
	 * @param otcfixFloat The OTC Fix Float Value
	 * 
	 * @return The Labeled OTC Fix Float successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel,
		final double otcFixFloat)
	{
		if (null == otcFixFloatLabel || !org.drip.quant.common.NumberUtil.IsValid (otcFixFloat))
		{
			return false;
		}

		_otcFixFloat.put (
			otcFixFloatLabel.fullyQualifiedName(),
			otcFixFloat
		);

		return true;
	}

	/**
	 * Check Presence of Labeled OTC Fix Float
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Label
	 * 
	 * @return The Labeled OTC Fix Float exists
	 */

	public boolean exists (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
	{
		return null != otcFixFloatLabel && _otcFixFloat.containsKey (otcFixFloatLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled OTC Fix Float
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Label
	 * 
	 * @return The Labeled OTC Fix Float
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double otcFixFloat (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
		throws java.lang.Exception
	{
		if (!exists (otcFixFloatLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::otcFixFloat => Invalid Inputs");
		}

		return _otcFixFloat.get (otcFixFloatLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Entity Credit
	 * 
	 * @param entityCreditLabel The Entity Credit Label
	 * @param entityCredit The Entity Credit
	 * 
	 * @return The Labeled Entity Credit successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.EntityCreditLabel entityCreditLabel,
		final double entityCredit)
	{
		if (null == entityCreditLabel || !org.drip.quant.common.NumberUtil.IsValid (entityCredit))
		{
			return false;
		}

		_entityCredit.put (
			entityCreditLabel.fullyQualifiedName(),
			entityCredit
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Credit
	 * 
	 * @param entityCreditLabel The Entity Credit Label
	 * 
	 * @return The Labeled Entity Credit exists
	 */

	public boolean exists (
		final org.drip.state.identifier.EntityCreditLabel entityCreditLabel)
	{
		return null != entityCreditLabel && _entityCredit.containsKey
			(entityCreditLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Entity Credit
	 * 
	 * @param entityCreditLabel The Entity Credit Label
	 * 
	 * @return The Labeled Entity Credit
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double entityCredit (
		final org.drip.state.identifier.EntityCreditLabel entityCreditLabel)
		throws java.lang.Exception
	{
		if (!exists (entityCreditLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::entityCredit => Invalid Inputs");
		}

		return _entityCredit.get (entityCreditLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Entity Equity
	 * 
	 * @param entityEquityLabel The Entity Equity Label
	 * @param entityEquity The Entity Equity
	 * 
	 * @return The Labeled Entity Equity successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel,
		final double entityEquity)
	{
		if (null == entityEquityLabel || !org.drip.quant.common.NumberUtil.IsValid (entityEquity))
		{
			return false;
		}

		_entityEquity.put (
			entityEquityLabel.fullyQualifiedName(),
			entityEquity
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Equity
	 * 
	 * @param entityEquityLabel The Entity Equity Label
	 * 
	 * @return The Labeled Entity Equity exists
	 */

	public boolean exists (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel)
	{
		return null != entityEquityLabel && _entityEquity.containsKey
			(entityEquityLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Entity Equity
	 * 
	 * @param entityEquityLabel The Entity Equity Label
	 * 
	 * @return The Labeled Entity Equity
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double entityEquity (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel)
		throws java.lang.Exception
	{
		if (!exists (entityEquityLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::entityEquity => Invalid Inputs");
		}

		return _entityEquity.get (entityEquityLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Entity Hazard
	 * 
	 * @param entityHazardLabel The Entity Hazard Label
	 * @param entityHazard The Entity Hazard
	 * 
	 * @return The Labeled Entity Hazard successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel,
		final double entityHazard)
	{
		if (null == entityHazardLabel || !org.drip.quant.common.NumberUtil.IsValid (entityHazard))
		{
			return false;
		}

		_entityHazard.put (
			entityHazardLabel.fullyQualifiedName(),
			entityHazard
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Hazard
	 * 
	 * @param entityRecoveryLabel The Entity Hazard Label
	 * 
	 * @return The Labeled Entity Hazard exists
	 */

	public boolean exists (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel)
	{
		return null != entityHazardLabel && _entityHazard.containsKey
			(entityHazardLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Entity Hazard
	 * 
	 * @param entityHazardLabel The Entity Hazard Label
	 * 
	 * @return The Labeled Entity Hazard
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double entityHazard (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel)
		throws java.lang.Exception
	{
		if (!exists (entityHazardLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::entityHazard => Invalid Inputs");
		}

		return _entityHazard.get (entityHazardLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Entity Funding
	 * 
	 * @param entityFundingLabel The Entity Funding Label
	 * @param entityFunding The Entity Funding
	 * 
	 * @return The Labeled Entity Funding successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel,
		final double entityFunding)
	{
		if (null == entityFundingLabel || !org.drip.quant.common.NumberUtil.IsValid (entityFunding))
		{
			return false;
		}

		_entityFunding.put (
			entityFundingLabel.fullyQualifiedName(),
			entityFunding
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Funding
	 * 
	 * @param entityRecoveryLabel The Entity Funding Label
	 * 
	 * @return The Labeled Entity Funding Exists
	 */

	public boolean exists (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel)
	{
		return null != entityFundingLabel && _entityFunding.containsKey
			(entityFundingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Entity Funding
	 * 
	 * @param entityFundingLabel The Entity Funding Label
	 * 
	 * @return The Labeled Entity Funding
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double entityFunding (
		final org.drip.state.identifier.EntityRecoveryLabel entityFundingLabel)
		throws java.lang.Exception
	{
		if (!exists (entityFundingLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::entityFunding => Invalid Inputs");
		}

		return _entityFunding.get (entityFundingLabel.fullyQualifiedName());
	}

	/**
	 * Add the Labeled Entity Recovery
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Label
	 * @param entityRecovery The Entity Recovery
	 * 
	 * @return The Labeled Entity Recovery successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel,
		final double entityRecovery)
	{
		if (null == entityRecoveryLabel || !org.drip.quant.common.NumberUtil.IsValid (entityRecovery))
		{
			return false;
		}

		_entityRecovery.put (
			entityRecoveryLabel.fullyQualifiedName(),
			entityRecovery
		);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Recovery
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Label
	 * 
	 * @return The Labeled Entity Recovery exists
	 */

	public boolean exists (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel)
	{
		return null != entityRecoveryLabel && _entityRecovery.containsKey
			(entityRecoveryLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled Entity Recovery
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Label
	 * 
	 * @return The Labeled Entity Recovery
	 * 
	 * @throws Thrown if Inputs are Invalid
	 */

	public double entityRecovery (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel)
		throws java.lang.Exception
	{
		if (!exists (entityRecoveryLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::entityRecovery => Invalid Inputs");
		}

		return _entityRecovery.get (entityRecoveryLabel.fullyQualifiedName());
	}
}
