
package org.drip.dynamics.hullwhite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>TrinomialTreeTransitionMetrics</i> records the Transition Metrics associated with Node-to-Node
 * Evolution of the Instantaneous Short Rate using the Hull-White Model Trinomial Tree.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/README.md">Hull White Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TrinomialTreeTransitionMetrics {
	private long _lTreeTimeIndex = -1L;
	private long _lTreeStochasticBaseIndex = -1L;
	private long _lTreeStochasticDisplacementIndex = -1L;
	private double _dblXVariance = java.lang.Double.NaN;
	private double _dblTerminalAlpha = java.lang.Double.NaN;
	private double _dblProbabilityUp = java.lang.Double.NaN;
	private int _iInitialDate = java.lang.Integer.MIN_VALUE;
	private int _iTerminalDate = java.lang.Integer.MIN_VALUE;
	private double _dblProbabilityDown = java.lang.Double.NaN;
	private double _dblProbabilityStay = java.lang.Double.NaN;
	private double _dblXStochasticShift = java.lang.Double.NaN;
	private double _dblExpectedTerminalX = java.lang.Double.NaN;

	/**
	 * TrinomialTreeTransitionMetrics Constructor
	 * 
	 * @param iInitialDate The Initial Date
	 * @param iTerminalDate The Terminal/Final Date
	 * @param lTreeTimeIndex The Tree Time Index
	 * @param lTreeStochasticBaseIndex The Tree Stochastic Base Index
	 * @param dblExpectedTerminalX Expectation of the Final/Terminal Value for X
	 * @param dblXVariance Variance of X
	 * @param dblTerminalAlpha The Final/Terminal Alpha
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrinomialTreeTransitionMetrics (
		final int iInitialDate,
		final int iTerminalDate,
		final long lTreeTimeIndex,
		final long lTreeStochasticBaseIndex,
		final double dblExpectedTerminalX,
		final double dblXVariance,
		final double dblTerminalAlpha)
		throws java.lang.Exception
	{
		if (0 > (_lTreeTimeIndex = lTreeTimeIndex) || !org.drip.numerical.common.NumberUtil.IsValid
			(_dblExpectedTerminalX = dblExpectedTerminalX) || !org.drip.numerical.common.NumberUtil.IsValid
				(_dblXVariance = dblXVariance) || !org.drip.numerical.common.NumberUtil.IsValid
					(_dblTerminalAlpha = dblTerminalAlpha))
			throw new java.lang.Exception ("TrinomialTreeTransitionMetrics ctr: Invalid Inputs");

		_dblXStochasticShift = java.lang.Math.sqrt (_dblXVariance * 3.);

		_lTreeStochasticDisplacementIndex = java.lang.Math.round (_dblExpectedTerminalX /
			_dblXStochasticShift);

		_iInitialDate = iInitialDate;
		_iTerminalDate = iTerminalDate;
		_lTreeStochasticBaseIndex = lTreeStochasticBaseIndex;
		double dblEta = _dblExpectedTerminalX - _lTreeStochasticDisplacementIndex * _dblXStochasticShift;
		_dblProbabilityStay = (2. / 3.) - (dblEta * dblEta / (3. * _dblXVariance));
		_dblProbabilityDown = (1. / 6.) + (dblEta * dblEta / (6. * _dblXVariance)) - (0.5 * dblEta /
			_dblXStochasticShift);
		_dblProbabilityUp = (1. / 6.) + (dblEta * dblEta / (6. * _dblXVariance)) + (0.5 * dblEta /
			_dblXStochasticShift);
	}

	/**
	 * Retrieve the Initial Date
	 * 
	 * @return The Initial Date
	 */

	public int initialDate()
	{
		return _iInitialDate;
	}

	/**
	 * Retrieve the Terminal Date
	 * 
	 * @return The Terminal Date
	 */

	public int terminalDate()
	{
		return _iTerminalDate;
	}

	/**
	 * Retrieve the Tree Time Index
	 * 
	 * @return The Tree Time Index
	 */

	public long treeTimeIndex()
	{
		return _lTreeTimeIndex;
	}

	/**
	 * Retrieve the Expected Final/Terminal Value for X
	 * 
	 * @return The Expected Final/Terminal Value for X
	 */

	public double expectedTerminalX()
	{
		return _dblExpectedTerminalX;
	}

	/**
	 * Retrieve the Variance in the Final Value of X
	 * 
	 * @return The Variance in the Final Value of X
	 */

	public double xVariance()
	{
		return _dblXVariance;
	}

	/**
	 * Retrieve the Stochastic Shift of X
	 * 
	 * @return The Stochastic Shift of X
	 */

	public double xStochasticShift()
	{
		return _dblXStochasticShift;
	}

	/**
	 * Retrieve the Tree Stochastic Displacement Index
	 * 
	 * @return The Tree Stochastic Displacement Index
	 */

	public long treeStochasticDisplacementIndex()
	{
		return _lTreeStochasticDisplacementIndex;
	}

	/**
	 * Retrieve the Probability of the Up Stochastic Shift
	 * 
	 * @return Probability of the Up Stochastic Shift
	 */

	public double probabilityUp()
	{
		return _dblProbabilityUp;
	}

	/**
	 * Retrieve the Probability of the Down Stochastic Shift
	 * 
	 * @return Probability of the Down Stochastic Shift
	 */

	public double probabilityDown()
	{
		return _dblProbabilityDown;
	}

	/**
	 * Retrieve the Probability of the No Shift
	 * 
	 * @return Probability of the No Shift
	 */

	public double probabilityStay()
	{
		return _dblProbabilityStay;
	}

	/**
	 * Retrieve the "Up" Value for X
	 * 
	 * @return The "Up" Value for X
	 */

	public double xUp()
	{
		return (_lTreeStochasticDisplacementIndex + 1) * _dblXStochasticShift;
	}

	/**
	 * Retrieve the "Down" Value for X
	 * 
	 * @return The "Down" Value for X
	 */

	public double xDown()
	{
		return (_lTreeStochasticDisplacementIndex - 1) * _dblXStochasticShift;
	}

	/**
	 * Retrieve the Final/Terminal Alpha
	 * 
	 * @return The Final/Terminal Alpha
	 */

	public double terminalAlpha()
	{
		return _dblTerminalAlpha;
	}

	/**
	 * Retrieve the "Up" Node Metrics
	 * 
	 * @return The "Up" Node Metrics
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics upNodeMetrics()
	{
		try {
			return new org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics (_lTreeTimeIndex,
				_lTreeStochasticBaseIndex + 1, (_lTreeStochasticDisplacementIndex + 1) *
					_dblXStochasticShift, _dblTerminalAlpha);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the "Down" Node Metrics
	 * 
	 * @return The "Down" Node Metrics
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics downNodeMetrics()
	{
		try {
			return new org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics (_lTreeTimeIndex,
				_lTreeStochasticBaseIndex - 1, (_lTreeStochasticDisplacementIndex - 1) *
					_dblXStochasticShift, _dblTerminalAlpha);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the "Stay" Node Metrics
	 * 
	 * @return The "Stay" Node Metrics
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics stayNodeMetrics()
	{
		try {
			return new org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics (_lTreeTimeIndex,
				_lTreeStochasticBaseIndex, _lTreeStochasticDisplacementIndex * _dblXStochasticShift,
					_dblTerminalAlpha);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
