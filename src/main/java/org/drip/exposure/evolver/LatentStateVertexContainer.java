
package org.drip.exposure.evolver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>LatentStateVertexContainer</i> holds the Latent State Labels and their corresponding Vertex
 * Realizations. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk
 *  				Management, and Collateral Trading
 *  				https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301 <b>eSSRN</b>
 *  		</li>
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
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver">Evolver</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateVertexContainer
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

	private java.util.List<org.drip.state.identifier.LatentStateLabel> _loadedLabelList = new
		java.util.ArrayList<org.drip.state.identifier.LatentStateLabel>();

	/**
	 * Empty LatentStateVertexContainer Constructor
	 */

	public LatentStateVertexContainer()
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
	 * Add the Labeled CSA
	 * 
	 * @param csaLabel The CSA Label
	 * @param csa The CSA
	 * 
	 * @return The Labeled CSA successfully added
	 */

	public boolean add (
		final org.drip.state.identifier.CSALabel csaLabel,
		final double csa)
	{
		if (null == csaLabel || !org.drip.quant.common.NumberUtil.IsValid (csa))
		{
			return false;
		}

		_csa.put (
			csaLabel.fullyQualifiedName(),
			csa
		);

		_loadedLabelList.add (csaLabel);

		return true;
	}

	/**
	 * Check Presence of Labeled CSA
	 * 
	 * @param csaLabel The CSA Label
	 * 
	 * @return The Labeled CSA exists
	 */

	public boolean exists (
		final org.drip.state.identifier.CSALabel csaLabel)
	{
		return null != csaLabel && _csa.containsKey (csaLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve of Labeled CSA
	 * 
	 * @param csaLabel The CSA Label
	 * 
	 * @return The Labeled CSA
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double csa (
		final org.drip.state.identifier.CSALabel csaLabel)
		throws java.lang.Exception
	{
		if (!exists (csaLabel))
		{
			throw new java.lang.Exception ("LatentStateNodeContainer::csa => Invalid Inputs");
		}

		return _csa.get (csaLabel.fullyQualifiedName());
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

		_loadedLabelList.add (fxLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (repoLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (customLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (govvieLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (ratingLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (forwardLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (fundingLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (payDownLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (overnightLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (collateralLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (volatilityLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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
	 * @param otcFixFloat The OTC Fix Float Value
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

		_loadedLabelList.add (otcFixFloatLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (entityCreditLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (entityEquityLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (entityHazardLabel);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Hazard
	 * 
	 * @param entityHazardLabel The Entity Hazard Label
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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

		_loadedLabelList.add (entityFundingLabel);

		return true;
	}

	/**
	 * Check Presence of Labeled Entity Funding
	 * 
	 * @param entityFundingLabel The Entity Funding Label
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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double entityFunding (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel)
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

		_loadedLabelList.add (entityRecoveryLabel);

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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
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

	/**
	 * Add the Value Corresponding to the Specific Latent State
	 * 
	 * @param latentStateLabel The Latent State Label
	 * @param value The Latent State Value
	 * 
	 * @return TRUE - The Value Corresponding to the Specific Latent State successfully added
	 */

	public boolean addLatentStateValue (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel,
		final double value)
	{
		if (null == latentStateLabel || !org.drip.quant.common.NumberUtil.IsValid (value))
		{
			return false;
		}

		if (latentStateLabel instanceof org.drip.state.identifier.FXLabel)
		{
			return add (
				(org.drip.state.identifier.FXLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.CSALabel)
		{
			return add (
				(org.drip.state.identifier.CSALabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.RepoLabel)
		{
			return add (
				(org.drip.state.identifier.RepoLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.CustomLabel)
		{
			return add (
				(org.drip.state.identifier.CustomLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.GovvieLabel)
		{
			return add (
				(org.drip.state.identifier.GovvieLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.RatingLabel)
		{
			{
				return add (
					(org.drip.state.identifier.RatingLabel) latentStateLabel,
					value
				);
			}
		}

		if (latentStateLabel instanceof org.drip.state.identifier.ForwardLabel)
		{
			{
				return add (
					(org.drip.state.identifier.ForwardLabel) latentStateLabel,
					value
				);
			}
		}

		if (latentStateLabel instanceof org.drip.state.identifier.FundingLabel)
		{
			return add (
				(org.drip.state.identifier.FundingLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.PaydownLabel)
		{
			return add (
				(org.drip.state.identifier.PaydownLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.OvernightLabel)
		{
			return add (
				(org.drip.state.identifier.OvernightLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.CollateralLabel)
		{
			return add (
				(org.drip.state.identifier.CollateralLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.VolatilityLabel)
		{
			return add (
				(org.drip.state.identifier.VolatilityLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.OTCFixFloatLabel)
		{
			return add (
				(org.drip.state.identifier.OTCFixFloatLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityCreditLabel)
		{
			return add (
				(org.drip.state.identifier.EntityCreditLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityEquityLabel)
		{
			return add (
				(org.drip.state.identifier.EntityEquityLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityHazardLabel)
		{
			return add (
				(org.drip.state.identifier.EntityHazardLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityFundingLabel)
		{
			return add (
				(org.drip.state.identifier.EntityFundingLabel) latentStateLabel,
				value
			);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityRecoveryLabel)
		{
			return add (
				(org.drip.state.identifier.EntityRecoveryLabel) latentStateLabel,
				value
			);
		}

		return false;
	}

	/**
	 * Retrieve the Value Corresponding to the Specific Latent State
	 * 
	 * @param latentStateLabel The Latent State Label
	 * 
	 * @return The Value Corresponding to the Specific Latent State
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double value (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
		throws java.lang.Exception
	{
		if (null == latentStateLabel)
		{
			throw new java.lang.Exception ("LatentStateVertexContainer::value => Invalid State Label");
		}

		if (latentStateLabel instanceof org.drip.state.identifier.FXLabel)
		{
			return fx ((org.drip.state.identifier.FXLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.CSALabel)
		{
			return csa ((org.drip.state.identifier.CSALabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.RepoLabel)
		{
			return repo ((org.drip.state.identifier.RepoLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.CustomLabel)
		{
			return custom ((org.drip.state.identifier.CustomLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.GovvieLabel)
		{
			return govvie ((org.drip.state.identifier.GovvieLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.RatingLabel)
		{
			return rating ((org.drip.state.identifier.RatingLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.ForwardLabel)
		{
			return forward ((org.drip.state.identifier.ForwardLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.FundingLabel)
		{
			return funding ((org.drip.state.identifier.FundingLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.PaydownLabel)
		{
			return payDown ((org.drip.state.identifier.PaydownLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.OvernightLabel)
		{
			return overnight ((org.drip.state.identifier.OvernightLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.CollateralLabel)
		{
			return collateral ((org.drip.state.identifier.CollateralLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.VolatilityLabel)
		{
			return volatility ((org.drip.state.identifier.VolatilityLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.OTCFixFloatLabel)
		{
			return otcFixFloat ((org.drip.state.identifier.OTCFixFloatLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityCreditLabel)
		{
			return entityCredit ((org.drip.state.identifier.EntityCreditLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityEquityLabel)
		{
			return entityEquity ((org.drip.state.identifier.EntityEquityLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityHazardLabel)
		{
			return entityHazard ((org.drip.state.identifier.EntityHazardLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityFundingLabel)
		{
			return entityFunding ((org.drip.state.identifier.EntityFundingLabel) latentStateLabel);
		}

		if (latentStateLabel instanceof org.drip.state.identifier.EntityRecoveryLabel)
		{
			return entityRecovery ((org.drip.state.identifier.EntityRecoveryLabel) latentStateLabel);
		}

		throw new java.lang.Exception ("LatentStateVertexContainer::value => Invalid State Label");
	}

	/**
	 * Retrieve the List of all Loaded Labels
	 * 
	 * @return The List of all Loaded Labels
	 */

	public java.util.List<org.drip.state.identifier.LatentStateLabel> labelList()
	{
		return _loadedLabelList;
	}
}
