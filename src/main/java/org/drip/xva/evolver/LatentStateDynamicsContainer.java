
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
 * LatentStateDynamicsContainer holds the Latent State Labels for a variety of Latent States and their
 * 	Evolvers.  The References are:<br><br>
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

public class LatentStateDynamicsContainer
{
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _fxEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _csaEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _repoEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _hazardEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _customEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _equityEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _govvieEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _ratingEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _forwardEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _fundingEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _payDownEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _recoveryEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _overnightEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _collateralEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _volatilityEvolver = 
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _otcFixFloatEvolver =
		null;

	/**
	 * Retrieve the Equity Evolver Map
	 * 
	 * @return The Equity Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> equityEvolver()
	{
		return _equityEvolver;
	}

	/**
	 * Retrieve the Funding Evolver Map
	 * 
	 * @return The Funding Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> fundingEvolver()
	{
		return _fundingEvolver;
	}

	/**
	 * Retrieve the Govvie Evolver Map
	 * 
	 * @return The Govvie Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> govvieEvolver()
	{
		return _govvieEvolver;
	}

	/**
	 * Retrieve the FX Evolver Map
	 * 
	 * @return The FX Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> fxEvolver()
	{
		return _fxEvolver;
	}

	/**
	 * Retrieve the Forward Evolver Map
	 * 
	 * @return The Forward Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> forwardEvolver()
	{
		return _forwardEvolver;
	}

	/**
	 * Retrieve the OTC Fix Float Evolver Map
	 * 
	 * @return The OTC Fix Float Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> otcFixFloatEvolver()
	{
		return _otcFixFloatEvolver;
	}

	/**
	 * Retrieve the Overnight Evolver Map
	 * 
	 * @return The Overnight Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> overnightEvolver()
	{
		return _overnightEvolver;
	}

	/**
	 * Retrieve the Collateral Evolver Map
	 * 
	 * @return The Collateral Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> collateralEvolver()
	{
		return _collateralEvolver;
	}

	/**
	 * Retrieve the CSA Evolver Map
	 * 
	 * @return The CSA Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> csaEvolver()
	{
		return _csaEvolver;
	}

	/**
	 * Retrieve the Hazard Evolver Map
	 * 
	 * @return The Hazard Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> hazardEvolver()
	{
		return _hazardEvolver;
	}

	/**
	 * Retrieve the Recovery Evolver Map
	 * 
	 * @return The Recovery Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> recoveryEvolver()
	{
		return _recoveryEvolver;
	}

	/**
	 * Retrieve the Volatility Evolver Map
	 * 
	 * @return The Volatility Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> volatilityEvolver()
	{
		return _volatilityEvolver;
	}

	/**
	 * Retrieve the Ratings Evolver Map
	 * 
	 * @return The Ratings Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> ratingEvolver()
	{
		return _ratingEvolver;
	}

	/**
	 * Retrieve the Repo Evolver Map
	 * 
	 * @return The Repo Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> repoEvolver()
	{
		return _repoEvolver;
	}

	/**
	 * Retrieve the Pay Down Evolver Map
	 * 
	 * @return The Pay Down Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> payDownEvolver()
	{
		return _payDownEvolver;
	}

	/**
	 * Retrieve the Custom Evolver Map
	 * 
	 * @return The Custom Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> customEvolver()
	{
		return _customEvolver;
	}
}
