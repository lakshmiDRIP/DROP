
package org.drip.execution.capture;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>TrajectoryShortfallRealization</i> holds Execution Cost Realization across each Interval in the Trade
 * during a Single Simulation Run. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/README.md">Execution Trajectory Transaction Cost Capture</a></li>
 *  </ul>
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
