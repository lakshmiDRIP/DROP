
package org.drip.exposure.regression;

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
 * AndersenPykhtinSokolSegment generates the Segment Regression Based Exposures off of the corresponding
 *  Pillar Vertexes using the Pykhtin (2009) Scheme with the Andersen, Pykhtin, and Sokol (2017) Adjustments
 *  applied. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
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

public class AndersenPykhtinSokolSegment
{
	private int _epochDate = -1;
	private org.drip.exposure.regression.PillarVertex _leftPillar = null;
	private org.drip.exposure.regression.PillarVertex _rightPillar = null;
	private org.drip.function.definition.R1ToR1 _rightPillarLocalVolatility = null;

	/**
	 * AndersenPykhtinSokolSegment Constructor
	 * 
	 * @param epochDate The Path Epoch Date
	 * @param leftPillar The Left Pillar Vertex
	 * @param rightPillar The Right Pillar Vertex
	 * @param rightPillarLocalVolatility The Right Pillar Local Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolSegment (
		final int epochDate,
		final org.drip.exposure.regression.PillarVertex leftPillar,
		final org.drip.exposure.regression.PillarVertex rightPillar,
		final org.drip.function.definition.R1ToR1 rightPillarLocalVolatility)
		throws java.lang.Exception
	{
		if (null == (_leftPillar = leftPillar) ||
			null == (_rightPillar = rightPillar) ||
			_leftPillar.date() >= _rightPillar.date() ||
			null == (_rightPillarLocalVolatility = rightPillarLocalVolatility))
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolSegment Constructor => Invalid Inputs");
		}

		_epochDate = epochDate;
	}

	/**
	 * Retrieve the Epoch Date
	 * 
	 * @return The Epoch Date
	 */

	public int epochDate()
	{
		return _epochDate;
	}

	/**
	 * Retrieve the Left Pillar Vertex
	 * 
	 * @return The Left Pillar Vertex
	 */

	public org.drip.exposure.regression.PillarVertex leftPillar()
	{
		return _leftPillar;
	}

	/**
	 * Retrieve the Right Pillar Vertex
	 * 
	 * @return The Right Pillar Vertex
	 */

	public org.drip.exposure.regression.PillarVertex rightPillar()
	{
		return _rightPillar;
	}

	/**
	 * Retrieve the Right Pillar Local Volatility
	 * 
	 * @return The Right Pillar Local Volatility
	 */

	public org.drip.function.definition.R1ToR1 rightPillarLocalVolatility()
	{
		return _rightPillarLocalVolatility;
	}

	/**
	 * Generate the Dense (Complete) Segment Exposures
	 * 
	 * @param denseExposureTrajectory The Dense Exposure Trajectory
	 * @param wanderTrajectory The Wander Date Trajectory
	 * 
	 * @return The Dense (Complete) Segment Exposures
	 */

	public boolean denseExposureTrajectoryUpdate (
		final double[] denseExposureTrajectory,
		final double[] wanderTrajectory)
	{
		if (null == denseExposureTrajectory || null == wanderTrajectory)
		{
			return false;
		}

		int leftPillarDate = _leftPillar.date();

		int rightPillarDate = _rightPillar.date();

		double leftPillarExposure = _leftPillar.exposure();

		double rightPillarExposure = _rightPillar.exposure();

		int dateWidth = rightPillarDate - leftPillarDate;
		double urgency = 1. / dateWidth;
		double localVolatility = java.lang.Double.NaN;
		double localDrift = (rightPillarExposure - leftPillarExposure) * urgency;
		denseExposureTrajectory[leftPillarDate - _epochDate] = leftPillarExposure;
		denseExposureTrajectory[rightPillarDate - _epochDate] = rightPillarExposure;

		try
		{
			localVolatility = _rightPillarLocalVolatility.evaluate (rightPillarExposure);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		for (int dateIndex = dateWidth - 1; dateIndex > 0; --dateIndex)
		{
			int epochIndex = leftPillarDate + dateIndex - _epochDate;

			denseExposureTrajectory[epochIndex] = rightPillarExposure - localDrift * (dateWidth - dateIndex)
				+ localVolatility * urgency * wanderTrajectory[epochIndex] * java.lang.Math.sqrt (dateIndex *
					(dateWidth - dateIndex));
		}

		return true;
	}
}
