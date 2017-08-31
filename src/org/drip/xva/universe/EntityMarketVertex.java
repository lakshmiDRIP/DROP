
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * EntityMarketVertex holds the Realizations at a Market Trajectory Vertex of the given Entity (i.e.,
 *  Bank/Counter Party). The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b> 82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing <i>Risk</i>
 *  	<b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EntityMarketVertex {
	private double _dblHazardRate = java.lang.Double.NaN;
	private double _dblSeniorRecoveryRate = java.lang.Double.NaN;
	private double _dblSeniorFundingSpread = java.lang.Double.NaN;
	private double _dblSurvivalProbability = java.lang.Double.NaN;
	private double _dblSubordinateRecoveryRate = java.lang.Double.NaN;
	private double _dblSubordinateFundingSpread = java.lang.Double.NaN;
	private org.drip.xva.universe.NumeraireMarketVertex _nmvSeniorFunding = null;
	private org.drip.xva.universe.NumeraireMarketVertex _nmvSubordinateFunding = null;

	/**
	 * EntityMarketVertex Constructor
	 * 
	 * @param dblSurvivalProbability The Realized Entity Survival Probability
	 * @param dblHazardRate The Realized Entity Hazard Rate
	 * @param dblSeniorRecoveryRate The Entity Senior Recovery Rate
	 * @param dblSeniorFundingSpread The Entity Senior Funding Spread
	 * @param nmvSeniorFunding The Entity Senior Funding Numeraire Vertex
	 * @param dblSubordinateRecoveryRate The Entity Subordinate Recovery Rate
	 * @param dblSubordinateFundingSpread The Entity Subordinate Funding Spread
	 * @param nmvSubordinateFunding The Entity Subordinate Funding Numeraire Vertex
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityMarketVertex (
		final double dblSurvivalProbability,
		final double dblHazardRate,
		final double dblSeniorRecoveryRate,
		final double dblSeniorFundingSpread,
		final org.drip.xva.universe.NumeraireMarketVertex nmvSeniorFunding,
		final double dblSubordinateRecoveryRate,
		final double dblSubordinateFundingSpread,
		final org.drip.xva.universe.NumeraireMarketVertex nmvSubordinateFunding)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblSurvivalProbability = dblSurvivalProbability) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblSeniorRecoveryRate = dblSeniorRecoveryRate) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblHazardRate = dblHazardRate) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblSeniorFundingSpread =
						dblSeniorFundingSpread) || null == (_nmvSeniorFunding = nmvSeniorFunding))
			throw new java.lang.Exception ("EntityMarketVertex Constructor => Invalid Inputs");

		_nmvSubordinateFunding = nmvSubordinateFunding;
		_dblSubordinateRecoveryRate = dblSubordinateRecoveryRate;
		_dblSubordinateFundingSpread = dblSubordinateFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Hazard Rate
	 * 
	 * @return The Realized Entity Hazard Rate
	 */

	public double hazardRate()
	{
		return _dblHazardRate;
	}

	/**
	 * Retrieve the Realized Entity Survival Probability
	 * 
	 * @return The Realized Entity Survival Probability
	 */

	public double survivalProbability()
	{
		return _dblSurvivalProbability;
	}

	/**
	 * Retrieve the Realized Entity Senior Recovery Rate
	 * 
	 * @return The Realized Entity Senior Recovery Rate
	 */

	public double seniorRecoveryRate()
	{
		return _dblSeniorRecoveryRate;
	}

	/**
	 * Retrieve the Realized Entity Senior Funding Spread
	 * 
	 * @return The Realized Entity Senior Funding Spread
	 */

	public double seniorFundingSpread()
	{
		return _dblSeniorFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Senior Funding Numeraire Vertex
	 * 
	 * @return The Realized Entity Senior Funding Numeraire Vertex
	 */

	public org.drip.xva.universe.NumeraireMarketVertex seniorFundingNumeraire()
	{
		return _nmvSeniorFunding;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Recovery Rate
	 * 
	 * @return The Realized Entity Subordinate Recovery Rate
	 */

	public double subordinateRecoveryRate()
	{
		return _dblSubordinateRecoveryRate;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Funding Spread
	 * 
	 * @return The Realized Entity Subordinate Funding Spread
	 */

	public double subordinateFundingSpread()
	{
		return _dblSubordinateFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Funding Numeraire Vertex
	 * 
	 * @return The Realized Entity Subordinate Funding Numeraire Vertex
	 */

	public org.drip.xva.universe.NumeraireMarketVertex subordinateFundingNumeraire()
	{
		return _nmvSubordinateFunding;
	}
}
