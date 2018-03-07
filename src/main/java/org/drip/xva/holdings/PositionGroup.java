
package org.drip.xva.holdings;

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
 * PositionGroup holds the Settings that correspond to a Position/Collateral Group. The References are:
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

public class PositionGroup
{
	private org.drip.xva.netting.PositionGroupPath _collateralGroupPath = null;
	private org.drip.xva.holdings.PositionGroupNumeraire _positionGroupNumeraire = null;
	private org.drip.xva.proto.PositionSchemaSpecification _positionGroupSpecification = null;

	/**
	 * PositionGroup Constructor
	 * 
	 * @param positionGroupSpecification The Position Group Specification
	 * @param positionGroupNumeraire The Position Group Numeraire
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public PositionGroup (
		final org.drip.xva.proto.PositionSchemaSpecification positionGroupSpecification,
		final org.drip.xva.holdings.PositionGroupNumeraire positionGroupNumeraire)
		throws java.lang.Exception
	{
		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_positionGroupNumeraire = positionGroupNumeraire))
		{
			throw  new java.lang.Exception ("PositionGroup Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Position Group Specification
	 * 
	 * @return The Position Group Specification
	 */

	public org.drip.xva.proto.PositionSchemaSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Position Group Numeraire
	 * 
	 * @return The Position Group Numeraire
	 */

	public org.drip.xva.holdings.PositionGroupNumeraire positionGroupNumeraire()
	{
		return _positionGroupNumeraire;
	}

	/**
	 * Set the Collateral Group Path
	 * 
	 * @param collateralGroupPath The Collateral Group Path
	 * 
	 * @return TRUE - The Collateral Group Path Successfully Set
	 */

	public boolean setCollateralGroupPath (
		final org.drip.xva.netting.PositionGroupPath collateralGroupPath)
	{
		if (null == collateralGroupPath)
		{
			return false;
		}

		_collateralGroupPath = collateralGroupPath;
		return true;
	}

	/**
	 * Retrieve the Collateral Group Path
	 * 
	 * @return The Collateral Group Path
	 */

	public org.drip.xva.netting.PositionGroupPath collateralGroupPath()
	{
		return _collateralGroupPath;
	}

	/**
	 * Generate the Position Group Value Array at the specified Vertexes
	 * 
	 * @param marketVertexArray The Vertex Market Parameter Array
	 * 
	 * @return The Position Group Value Array
	 */

	public double[] valueArray (
		final org.drip.xva.universe.MarketVertex[] marketVertexArray)
	{
		if (null == marketVertexArray)
		{
			return null;
		}

		int vertexCount = marketVertexArray.length;
		double[] positionGroupValueArray = 0 == vertexCount ? null : new double[vertexCount];

		if (0 == vertexCount)
		{
			return null;
		}
		for (int i = 0; i < vertexCount; ++i)
		{
			try {
				positionGroupValueArray[i] = marketVertexArray[i].positionManifestValue() *
					_positionGroupNumeraire.value (marketVertexArray[i]);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return positionGroupValueArray;
	}
}
