
package org.drip.xva.dynamics;

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
 * DiffusionSettings contains Diffusion Evolvers for the various Latent States. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DiffusionSettings
{
	private double[][] _aadblCorrelation = null;
	private org.drip.measure.process.DiffusionEvolver _dePortfolio = null;
	private org.drip.measure.process.DiffusionEvolver _deCSANumeraire = null;
	private org.drip.measure.process.DiffusionEvolver _deBankHazardRate = null;
	private org.drip.measure.process.DiffusionEvolver _deBankRecoveryRate = null;
	private org.drip.measure.process.DiffusionEvolver _deOvernightNumeraire = null;

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		return _aadblCorrelation;
	}

	/**
	 * Retrieve the Portfolio Diffusion Evolver
	 * 
	 * @return The Portfolio Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver portfolioEvolver()
	{
		return _dePortfolio;
	}

	/**
	 * Retrieve the Overnight Numeraire Diffusion Evolver
	 * 
	 * @return The Overnight Numeraire Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver overnightNumeraireEvolver()
	{
		return _deOvernightNumeraire;
	}

	/**
	 * Retrieve the CSA Numeraire Diffusion Evolver
	 * 
	 * @return The CSA Numeraire Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver csaNumeraireEvolver()
	{
		return _deCSANumeraire;
	}

	/**
	 * Retrieve the Bank Hazard Rate Diffusion Evolver
	 * 
	 * @return The Bank Hazard Rate Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankHazardRateEvolver()
	{
		return _deBankHazardRate;
	}

	/**
	 * Retrieve the Bank Recovery Rate Diffusion Evolver
	 * 
	 * @return The Bank Recovery Rate Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankRecoveryRateEvolver()
	{
		return _deBankRecoveryRate;
	}
}
