
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
 * PathSimulatorScheme specifies the Hypothecation Vertex Generation Schemes for the Simulation. The
 *  References are:
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

public class PathSimulatorScheme
{
	private int _brokenDateScheme = -1;
	private int _adjustmentDigestScheme = -1;
	private int _positionReplicationScheme = -1;
	private double _hedgeError = java.lang.Double.NaN;
	private org.drip.xva.definition.CloseOutGeneral _closeOutScheme = null;
	private org.drip.measure.discrete.CorrelatedPathVertexDimension _correlatedPathVertexDimension = null;

	/**
	 * Construct an Albanese Andersen Vertex Generator Based Path Simulator Scheme
	 * 
	 * @return The Albanese Andersen Vertex Generator Based Path Simulator Scheme
	 */

	public static PathSimulatorScheme AlbaneseAndersenVertex()
	{
		try
		{
			return new PathSimulatorScheme (
				org.drip.xva.dynamics.PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX,
				org.drip.xva.dynamics.BrokenDateScheme.SQUARE_ROOT_OF_TIME,
				org.drip.xva.dynamics.AdjustmentDigestScheme.ALBANESE_ANDERSEN_METRICS_POINTER,
				0.,
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathSimulatorScheme Constructor
	 * 
	 * @param positionReplicationScheme Position Replication Scheme
	 * @param brokenDateScheme Broken Date Interpolation Scheme
	 * @param adjustmentDigestScheme Adjustment Digest Scheme
	 * @param hedgeError Hedge Error
	 * @param closeOutScheme Close Out Scheme
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathSimulatorScheme (
		final int positionReplicationScheme,
		final int brokenDateScheme,
		final int adjustmentDigestScheme,
		final double hedgeError,
		final org.drip.xva.definition.CloseOutGeneral closeOutScheme)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_hedgeError = hedgeError))
		{
			throw new java.lang.Exception ("PathSimulatorScheme Constructor => Invalid Inputs!");
		}

		_closeOutScheme = closeOutScheme;
		_brokenDateScheme = brokenDateScheme;
		_adjustmentDigestScheme = adjustmentDigestScheme;
		_positionReplicationScheme = positionReplicationScheme;
	}

	/**
	 * Retrieve the Position Replication Scheme
	 * 
	 * @return The Position Replication Scheme
	 */

	public int positionReplicationScheme()
	{
		return _positionReplicationScheme;
	}

	/**
	 * Retrieve the Broken Date Interpolation Scheme
	 * 
	 * @return The Broken Date Interpolation Scheme
	 */

	public int brokenDateScheme()
	{
		return _brokenDateScheme;
	}

	/**
	 * Retrieve the Adjustment Digest Scheme
	 * 
	 * @return The Adjustment Digest Scheme
	 */

	public int adjustmentDigestScheme()
	{
		return _adjustmentDigestScheme;
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _hedgeError;
	}

	/**
	 * Retrieve the Close Out Scheme
	 * 
	 * @return The Close Out Scheme
	 */

	public org.drip.xva.definition.CloseOutGeneral closeOutScheme()
	{
		return _closeOutScheme;
	}

	/**
	 * Retrieve the Path Vertex Dimension Generator
	 * 
	 * @return The Path Vertex Dimension Generator
	 */

	public org.drip.measure.discrete.CorrelatedPathVertexDimension correlatedPathVertexDimension()
	{
		return _correlatedPathVertexDimension;
	}
}
