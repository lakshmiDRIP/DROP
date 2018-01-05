
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
	private org.drip.measure.process.DiffusionEvolver _deCSANumeraire = null;
	private org.drip.measure.process.DiffusionEvolver _deBankHazardRate = null;
	private org.drip.measure.process.DiffusionEvolver _dePortfolioValue = null;
	private org.drip.measure.process.DiffusionEvolver _deBankRecoveryRate = null;
	private org.drip.measure.process.DiffusionEvolver _deBankFundingSpread = null;
	private org.drip.measure.process.DiffusionEvolver _deOvernightNumeraire = null;
	private org.drip.measure.process.DiffusionEvolver _deCounterPartyHazardRate = null;
	private org.drip.measure.process.DiffusionEvolver _deCounterPartyRecoveryRate = null;
	private org.drip.measure.process.DiffusionEvolver _deCounterPartyFundingSpread = null;

	/**
	 * DiffusionSettings Constructor
	 * 
	 * @param dePortfolioValue Portfolio Value Diffusion Evolver
	 * @param deOvernightNumeraire Overnight Numeraire Diffusion Evolver
	 * @param deCSANumeraire CSA Numeraire Diffusion Evolver
	 * @param deBankHazardRate Bank Hazard Rate Diffusion Evolver
	 * @param deBankRecoveryRate Bank Recovery Rate Diffusion Evolver
	 * @param deBankFundingSpread Bank Funding Spread Diffusion Evolver
	 * @param deCounterPartyHazardRate Counter Party Hazard Rate Diffusion Evolver
	 * @param deCounterPartyRecoveryRate Counter Party Recovery Rate Diffusion Evolver
	 * @param deCounterPartyFundingSpread Counter Party Funding Spread Diffusion Evolver
	 * @param aadblCorrelation Latent State Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiffusionSettings (
		final org.drip.measure.process.DiffusionEvolver dePortfolioValue,
		final org.drip.measure.process.DiffusionEvolver deOvernightNumeraire,
		final org.drip.measure.process.DiffusionEvolver deCSANumeraire,
		final org.drip.measure.process.DiffusionEvolver deBankHazardRate,
		final org.drip.measure.process.DiffusionEvolver deBankRecoveryRate,
		final org.drip.measure.process.DiffusionEvolver deBankFundingSpread,
		final org.drip.measure.process.DiffusionEvolver deCounterPartyHazardRate,
		final org.drip.measure.process.DiffusionEvolver deCounterPartyRecoveryRate,
		final org.drip.measure.process.DiffusionEvolver deCounterPartyFundingSpread,
		final double[][] aadblCorrelation)
		throws java.lang.Exception
	{
		if (null == (_dePortfolioValue = dePortfolioValue) ||
			null == (_deOvernightNumeraire = deOvernightNumeraire) ||
			null == (_deCSANumeraire = deCSANumeraire) ||
			null == (_deBankHazardRate = deBankHazardRate) ||
			null == (_deBankRecoveryRate = deBankRecoveryRate) ||
			null == (_deBankFundingSpread = deBankFundingSpread) ||
			null == (_deCounterPartyHazardRate = deCounterPartyHazardRate) ||
			null == (_deCounterPartyRecoveryRate = deCounterPartyRecoveryRate) ||
			null == (_deCounterPartyFundingSpread = deCounterPartyFundingSpread) ||
			null == (_aadblCorrelation = aadblCorrelation))
			throw new java.lang.Exception ("DiffusionSettings Constructor => Invalid Inputs");

		int iNumEvolver = _aadblCorrelation.length;

		if (9 != iNumEvolver)
			throw new java.lang.Exception ("DiffusionSettings Constructor => Invalid Inputs");

		for (int i = 0; i < iNumEvolver; ++i)
		{
			if (null == _aadblCorrelation[i] || iNumEvolver != _aadblCorrelation[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblCorrelation[i]))
				throw new java.lang.Exception ("DiffusionSettings Constructor => Invalid Inputs");
		}
	}

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
	 * Retrieve the Portfolio Value Diffusion Evolver
	 * 
	 * @return The Portfolio Value Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver portfolioValueEvolver()
	{
		return _dePortfolioValue;
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

	/**
	 * Retrieve the Bank Funding Spread Diffusion Evolver
	 * 
	 * @return The Bank Funding Spread Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver bankFundingSpreadEvolver()
	{
		return _deBankFundingSpread;
	}

	/**
	 * Retrieve the Counter Party Hazard Rate Diffusion Evolver
	 * 
	 * @return The Counter Party Hazard Rate Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyHazardRateEvolver()
	{
		return _deCounterPartyHazardRate;
	}

	/**
	 * Retrieve the Counter Party Recovery Rate Diffusion Evolver
	 * 
	 * @return The Counter Party Recovery Rate Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyRecoveryRateEvolver()
	{
		return _deCounterPartyRecoveryRate;
	}

	/**
	 * Retrieve the Counter Party Funding Spread Diffusion Evolver
	 * 
	 * @return The Counter Party Funding Spread Diffusion Evolver
	 */

	public org.drip.measure.process.DiffusionEvolver counterPartyFundingSpreadEvolver()
	{
		return _deCounterPartyFundingSpread;
	}

	/**
	 * Retrieve the Count of the Latent States Evolved
	 * 
	 * @return Count of the Latent States Evolved
	 */

	public int latentStateCount()
	{
		return 9;
	}
}
