
package org.drip.execution.capture;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>TrajectoryShortfallAggregate</i> aggregates the  Execution Short-fall Distribution across each Interval
 * in the Trade. The References are:
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

public class TrajectoryShortfallAggregate {
	private java.util.List<org.drip.execution.discrete.ShortfallIncrementDistribution> _lsSID = null;

	/**
	 * TrajectoryShortfallAggregate Constructor
	 * 
	 * @param lsSID List of the Incremental Slice Short-fall Distributions
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrajectoryShortfallAggregate (
		final java.util.List<org.drip.execution.discrete.ShortfallIncrementDistribution> lsSID)
		throws java.lang.Exception
	{
		if (null == (_lsSID = lsSID))
			throw new java.lang.Exception ("TrajectoryShortfallAggregate Constructor => Invalid Inputs");

		int iNumInterval = _lsSID.size();

		if (0 == iNumInterval)
			throw new java.lang.Exception ("TrajectoryShortfallAggregate Constructor => Invalid Inputs");

		for (org.drip.execution.discrete.ShortfallIncrementDistribution sid : _lsSID) {
			if (null == sid)
				throw new java.lang.Exception ("TrajectoryShortfallAggregate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the List of the Interval Cost Distributions
	 * 
	 * @return The List of the Interval Cost Distributions
	 */

	public java.util.List<org.drip.execution.discrete.ShortfallIncrementDistribution> list()
	{
		return _lsSID;
	}

	/**
	 * Generate the Total Cost R^1 Normal Distribution
	 * 
	 * @return The Total Cost R^1 Normal Distribution
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal totalCostDistribution()
	{
		double dblTotalCostMean = 0.;
		double dblTotalCostVariance = 0.;

		for (org.drip.measure.gaussian.R1UnivariateNormal r1un : _lsSID) {
			dblTotalCostMean = dblTotalCostMean + r1un.mean();

			dblTotalCostVariance = dblTotalCostVariance + r1un.variance();
		}

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (dblTotalCostMean, java.lang.Math.sqrt
				(dblTotalCostVariance));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Array of Incremental Expectation Sequence
	 * 
	 * @return The Array of Incremental Expectation Sequence
	 */

	public double[] incrementalExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblIncrementalExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalExpectationSequence[i] = _lsSID.get (i).mean();

		return adblIncrementalExpectationSequence;
	}

	/**
	 * Generate the Array of Cumulative Expectation Sequence
	 * 
	 * @return The Array of Cumulative Expectation Sequence
	 */

	public double[] cumulativeExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblCumulativeExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeExpectationSequence[i] = 0 == i ? _lsSID.get (i).expectation() :
				adblCumulativeExpectationSequence[i - 1] + _lsSID.get (i).expectation();

		return adblCumulativeExpectationSequence;
	}

	/**
	 * Generate the Array of Incremental Variance Sequence
	 * 
	 * @return The Array of Incremental Variance Sequence
	 */

	public double[] incrementalVariance()
	{
		int iNumInterval = _lsSID.size();

		double[] adblIncrementalVarianceSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalVarianceSequence[i] = _lsSID.get (i).variance();

		return adblIncrementalVarianceSequence;
	}

	/**
	 * Generate the Array of Cumulative Variance Sequence
	 * 
	 * @return The Array of Cumulative Variance Sequence
	 */

	public double[] cumulativeVariance()
	{
		int iNumInterval = _lsSID.size();

		double[] adblCumulativeVarianceSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeVarianceSequence[i] = 0 == i ? _lsSID.get (i).variance() :
				adblCumulativeVarianceSequence[i - 1] + _lsSID.get (i).variance();

		return adblCumulativeVarianceSequence;
	}

	/**
	 * Generate the Array of Incremental Market Dynamic Expectation Sequence
	 * 
	 * @return The Array of Incremental Market Dynamic Expectation Sequence
	 */

	public double[] incrementalMarketDynamicExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblIncrementalMarketDynamicExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalMarketDynamicExpectationSequence[i] = _lsSID.get (i).marketDynamicExpectation();

		return adblIncrementalMarketDynamicExpectationSequence;
	}

	/**
	 * Generate the Array of Cumulative Market Dynamic Expectation Sequence
	 * 
	 * @return The Array of Cumulative Market Dynamic Expectation Sequence
	 */

	public double[] cumulativeMarketDynamicExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblCumulativeMarketDynamicExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeMarketDynamicExpectationSequence[i] = 0 == i ? _lsSID.get
				(i).marketDynamicExpectation() : adblCumulativeMarketDynamicExpectationSequence[i - 1] +
					_lsSID.get (i).marketDynamicExpectation();

		return adblCumulativeMarketDynamicExpectationSequence;
	}

	/**
	 * Generate the Array of Incremental Permanent Impact Expectation Sequence
	 * 
	 * @return The Array of Incremental Permanent Impact Expectation Sequence
	 */

	public double[] incrementalPermanentImpactExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblIncrementalPermanentImpactExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalPermanentImpactExpectationSequence[i] = _lsSID.get
				(i).permanentImpactExpectation();

		return adblIncrementalPermanentImpactExpectationSequence;
	}

	/**
	 * Generate the Array of Cumulative Permanent Impact Expectation Sequence
	 * 
	 * @return The Array of Cumulative Permanent Impact Expectation Sequence
	 */

	public double[] cumulativePermanentImpactExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblCumulativePermanentImpactExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativePermanentImpactExpectationSequence[i] = 0 == i ? _lsSID.get
				(i).permanentImpactExpectation() : adblCumulativePermanentImpactExpectationSequence[i - 1] +
					_lsSID.get (i).permanentImpactExpectation();

		return adblCumulativePermanentImpactExpectationSequence;
	}

	/**
	 * Generate the Array of Incremental Temporary Impact Expectation Sequence
	 * 
	 * @return The Array of Incremental Temporary Impact Expectation Sequence
	 */

	public double[] incrementalTemporaryImpactExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblIncrementalTemporaryImpactExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblIncrementalTemporaryImpactExpectationSequence[i] = _lsSID.get
				(i).temporaryImpactExpectation();

		return adblIncrementalTemporaryImpactExpectationSequence;
	}

	/**
	 * Generate the Array of Cumulative Temporary Impact Expectation Sequence
	 * 
	 * @return The Array of Cumulative Temporary Impact Expectation Sequence
	 */

	public double[] cumulativeTemporaryImpactExpectation()
	{
		int iNumInterval = _lsSID.size();

		double[] adblCumulativeTemporaryImpactExpectationSequence = new double[iNumInterval];

		for (int i = 0; i < iNumInterval; ++i)
			adblCumulativeTemporaryImpactExpectationSequence[i] = 0 == i ? _lsSID.get
				(i).temporaryImpactExpectation() : adblCumulativeTemporaryImpactExpectationSequence[i - 1] +
					_lsSID.get (i).temporaryImpactExpectation();

		return adblCumulativeTemporaryImpactExpectationSequence;
	}

	/**
	 * Generate the Expected Short-fall
	 * 
	 * @return The Expected Short-fall
	 */

	public double shortfallExpectation()
	{
		int iNumInterval = _lsSID.size();

		double dblExpectedShortfall = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblExpectedShortfall = dblExpectedShortfall + _lsSID.get (i).expectation();

		return dblExpectedShortfall;
	}

	/**
	 * Generate the Short-fall Variance
	 * 
	 * @return The Short-fall Variance
	 */

	public double shortfallVariance()
	{
		int iNumInterval = _lsSID.size();

		double dblShortfallVariance = 0.;

		for (int i = 0; i < iNumInterval; ++i)
			dblShortfallVariance = dblShortfallVariance + _lsSID.get (i).variance();

		return dblShortfallVariance;
	}
}
