
package org.drip.execution.capture;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * TrajectoryShortfallRealization holds Execution Cost Realization across each Interval in the Trade during a
 * 	Single Simulation Run. The  References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * 	- Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades,
 * 		Journal of Finance, 50, 1147-1174.
 *
 * 	- Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 		Analysis of Institutional Equity Trades, Journal of Financial Economics, 46, 265-292.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TrajectoryShortfallRealization {
	private java.util.List<org.drip.execution.discrete.ShortfallIncrement> _lsSI = null;

	/**
	 * TrajectoryShortfallRealization Constructor
	 * 
	 * @param lsSI List of the Composite Slice Short-fall Increments
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrajectoryShortfallRealization (
		final java.util.List<org.drip.execution.discrete.ShortfallIncrement> lsSI)
		throws java.lang.Exception
	{
		if (null == (_lsSI = lsSI))
			throw new java.lang.Exception ("TrajectoryShortfallRealization Constructor => Invalid Inputs");

		int iNumSlice = _lsSI.size();

		if (0 == iNumSlice)
			throw new java.lang.Exception ("TrajectoryShortfallRealization Constructor => Invalid Inputs");

		for (org.drip.execution.discrete.ShortfallIncrement si : _lsSI) {
			if (null == si)
				throw new java.lang.Exception
					("TrajectoryShortfallRealization Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the List of the Realized Composite Cost Increments
	 * 
	 * @return The List of the Realized Composite Cost Increments
	 */

	public java.util.List<org.drip.execution.discrete.ShortfallIncrement> list()
	{
		return _lsSI;
	}

	/**
	 * Generate the Array of Incremental Market Dynamic Cost Drift
	 * 
	 * @return The Array of Incremental Market Dynamic Cost Drift
	 */

	public double[] incrementalMarketDynamicDrift()
	{
		int iNumInterval = _lsSI.size();

		double[] adblIncrementalMarketDynamicDrift = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalMarketDynamicDrift[i] = _lsSI.get (i).marketDynamicDrift();

		return adblIncrementalMarketDynamicDrift;
	}

	/**
	 * Generate the Array of Cumulative Market Dynamic Cost Drift
	 * 
	 * @return The Array of Cumulative Market Dynamic Cost Drift
	 */

	public double[] cumulativeMarketDynamicDrift()
	{
		int iNumInterval = _lsSI.size();

		double[] adblCumulativeMarketDynamicDrift = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeMarketDynamicDrift[i] = 0 == i ? _lsSI.get (i).marketDynamicDrift() : _lsSI.get
				(i).marketDynamicDrift() + adblCumulativeMarketDynamicDrift[i - 1];

		return adblCumulativeMarketDynamicDrift;
	}

	/**
	 * Generate the Total Market Dynamic Cost Drift
	 * 
	 * @return The Total Market Dynamic Cost Drift
	 */

	public double totalMarketDynamicDrift()
	{
		int iNumInterval = _lsSI.size();

		double dblTotalMarketDynamicDrift = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblTotalMarketDynamicDrift = dblTotalMarketDynamicDrift + _lsSI.get (i).marketDynamicDrift();

		return dblTotalMarketDynamicDrift;
	}

	/**
	 * Generate the Array of Incremental Market Dynamic Cost Wander
	 * 
	 * @return The Array of Incremental Market Dynamic Cost Wander
	 */

	public double[] incrementalMarketDynamicWander()
	{
		int iNumInterval = _lsSI.size();

		double[] adblIncrementalMarketDynamicWander = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalMarketDynamicWander[i] = _lsSI.get (i).marketDynamicWander();

		return adblIncrementalMarketDynamicWander;
	}

	/**
	 * Generate the Array of Cumulative Market Dynamic Cost Wander
	 * 
	 * @return The Array of Cumulative Market Dynamic Cost Wander
	 */

	public double[] cumulativeMarketDynamicWander()
	{
		int iNumInterval = _lsSI.size();

		double[] adblCumulativeMarketDynamicWander = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeMarketDynamicWander[i] = 0 == i ? _lsSI.get (i).marketDynamicWander() : _lsSI.get
				(i).marketDynamicWander() + adblCumulativeMarketDynamicWander[i - 1];

		return adblCumulativeMarketDynamicWander;
	}

	/**
	 * Generate the Total Market Dynamic Cost Wander
	 * 
	 * @return The Total Market Dynamic Cost Wander
	 */

	public double totalMarketDynamicWander()
	{
		int iNumInterval = _lsSI.size();

		double dblTotalMarketDynamicWander = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblTotalMarketDynamicWander = dblTotalMarketDynamicWander + _lsSI.get (i).marketDynamicWander();

		return dblTotalMarketDynamicWander;
	}

	/**
	 * Generate the Array of Incremental Permanent Cost Drift
	 * 
	 * @return The Array of Incremental Permanent Cost Drift
	 */

	public double[] incrementalPermanentDrift()
	{
		int iNumInterval = _lsSI.size();

		double[] adblIncrementalPermanentDrift = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalPermanentDrift[i] = _lsSI.get (i).permanentImpactDrift();

		return adblIncrementalPermanentDrift;
	}

	/**
	 * Generate the Array of Cumulative Permanent Cost Drift
	 * 
	 * @return The Array of Cumulative Permanent Cost Drift
	 */

	public double[] cumulativePermanentDrift()
	{
		int iNumInterval = _lsSI.size();

		double[] adblCumulativePermanentDrift = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativePermanentDrift[i] = 0 == i ? _lsSI.get (i).permanentImpactDrift() : _lsSI.get
				(i).permanentImpactDrift() + adblCumulativePermanentDrift[i - 1];

		return adblCumulativePermanentDrift;
	}

	/**
	 * Generate the Total Permanent Cost Drift
	 * 
	 * @return The Total Permanent Cost Drift
	 */

	public double totalPermanentDrift()
	{
		int iNumInterval = _lsSI.size();

		double dblTotalPermanentDrift = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblTotalPermanentDrift = dblTotalPermanentDrift + _lsSI.get (i).permanentImpactDrift();

		return dblTotalPermanentDrift;
	}

	/**
	 * Generate the Array of Incremental Permanent Cost Wander
	 * 
	 * @return The Array of Incremental Permanent Cost Wander
	 */

	public double[] incrementalPermanentWander()
	{
		int iNumInterval = _lsSI.size();

		double[] adblIncrementalPermanentWander = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalPermanentWander[i] = _lsSI.get (i).permanentImpactWander();

		return adblIncrementalPermanentWander;
	}

	/**
	 * Generate the Array of Cumulative Permanent Cost Wander
	 * 
	 * @return The Array of Cumulative Permanent Cost Wander
	 */

	public double[] cumulativePermanentWander()
	{
		int iNumInterval = _lsSI.size();

		double[] adblCumulativePermanentWander = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativePermanentWander[i] = 0 == i ? _lsSI.get (i).permanentImpactWander() : _lsSI.get
				(i).permanentImpactWander() + adblCumulativePermanentWander[i - 1];

		return adblCumulativePermanentWander;
	}

	/**
	 * Generate the Total Permanent Cost Wander
	 * 
	 * @return The Total Permanent Cost Wander
	 */

	public double totalPermanentWander()
	{
		int iNumInterval = _lsSI.size();

		double dblTotalPermanentWander = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblTotalPermanentWander = dblTotalPermanentWander + _lsSI.get (i).permanentImpactWander();

		return dblTotalPermanentWander;
	}

	/**
	 * Generate the Array of Incremental Temporary Cost Drift
	 * 
	 * @return The Array of Incremental Temporary Cost Drift
	 */

	public double[] incrementalTemporaryDrift()
	{
		int iNumInterval = _lsSI.size();

		double[] adblIncrementalTemporaryDrift = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalTemporaryDrift[i] = _lsSI.get (i).temporaryImpactDrift();

		return adblIncrementalTemporaryDrift;
	}

	/**
	 * Generate the Array of Cumulative Temporary Cost Drift
	 * 
	 * @return The Array of Cumulative Temporary Cost Drift
	 */

	public double[] cumulativeTemporaryDrift()
	{
		int iNumInterval = _lsSI.size();

		double[] adblCumulativeTemporaryDrift = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeTemporaryDrift[i] = 0 == i ? _lsSI.get (i).temporaryImpactDrift() : _lsSI.get
				(i).temporaryImpactDrift() + adblCumulativeTemporaryDrift[i - 1];

		return adblCumulativeTemporaryDrift;
	}

	/**
	 * Generate the Total Temporary Cost Drift
	 * 
	 * @return The Total Temporary Cost Drift
	 */

	public double totalTemporaryDrift()
	{
		int iNumInterval = _lsSI.size();

		double dblTotalTemporaryDrift = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblTotalTemporaryDrift = dblTotalTemporaryDrift + _lsSI.get (i).temporaryImpactDrift();

		return dblTotalTemporaryDrift;
	}

	/**
	 * Generate the Array of Incremental Temporary Cost Wander
	 * 
	 * @return The Array of Incremental Temporary Cost Wander
	 */

	public double[] incrementalTemporaryWander()
	{
		int iNumInterval = _lsSI.size();

		double[] adblIncrementalTemporaryWander = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalTemporaryWander[i] = _lsSI.get (i).temporaryImpactWander();

		return adblIncrementalTemporaryWander;
	}

	/**
	 * Generate the Array of Cumulative Temporary Cost Wander
	 * 
	 * @return The Array of Cumulative Temporary Cost Wander
	 */

	public double[] cumulativeTemporaryWander()
	{
		int iNumInterval = _lsSI.size();

		double[] adblCumulativeTemporaryWander = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeTemporaryWander[i] = 0 == i ? _lsSI.get (i).temporaryImpactWander() : _lsSI.get
				(i).temporaryImpactWander() + adblCumulativeTemporaryWander[i - 1];

		return adblCumulativeTemporaryWander;
	}

	/**
	 * Generate the Total Temporary Cost Wander
	 * 
	 * @return The Total Temporary Cost Wander
	 */

	public double totalTemporaryWander()
	{
		int iNumInterval = _lsSI.size();

		double dblTotalTemporaryWander = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblTotalTemporaryWander = dblTotalTemporaryWander + _lsSI.get (i).temporaryImpactWander();

		return dblTotalTemporaryWander;
	}
}
