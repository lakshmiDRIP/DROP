
package org.drip.regression.core;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>UnitRegressionStat</i> creates the statistical details for the Unit Regressor. It holds the following:
 *  <ul>
 *  	<li>
 * 			Execution Initialization Delay
 *  	</li>
 *  	<li>
 * 			Execution time mean, variance, maximum, and minimum
 *  	</li>
 *  	<li>
 * 			The full list of individual execution times
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md">Regression Engine Core and the Unit Regressors</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core/README.md">Regression Engine Core - Unit Regressors</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnitRegressionStat {
	private long _lExecTimeMean = 0L;
	private long _lExecTimeMaximum = 0L;
	private long _lExecTimeMinimum = 0L;
	private long _lExecTimeVariance = 0L;
	private long _lInitializationDelay = 0L;
	private java.util.List<java.lang.Long> _llExecTime = null;

	/**
	 * Empty Constructor
	 */

	public UnitRegressionStat()
	{
		_llExecTime = new java.util.ArrayList<java.lang.Long>();
	}

	/**
	 * Add another run execution time
	 * 
	 * @param lExecTime Execution Time
	 * 
	 * @return TRUE - Executed time run successfully added
	 */

	public boolean addExecTime (
		final long lExecTime)
	{
		_llExecTime.add (lExecTime);

		return true;
	}

	/**
	 * Generate the statistics across all the execution times generated
	 * 
	 * @return TRUE - Statistics successfully generated
	 */

	public boolean generateStat()
	{
		boolean bFirstRun = true;

		int iNumRuns = _llExecTime.size();

		if (0 == iNumRuns) return false;

		for (long lExecTime : _llExecTime) {
			if (bFirstRun) {
				_lExecTimeMaximum = lExecTime;
				_lExecTimeMinimum = lExecTime;
				_lInitializationDelay = lExecTime;
			} else {
				_lExecTimeMean += lExecTime;

				if (_lExecTimeMaximum < lExecTime) _lExecTimeMaximum = lExecTime;

				if (_lExecTimeMinimum > lExecTime) _lExecTimeMinimum = lExecTime;
			}

			bFirstRun = false;
		}

		_lExecTimeMean /= (iNumRuns - 1);
		bFirstRun = true;

		for (long lExecTime : _llExecTime) {
			if (!bFirstRun)
				_lExecTimeVariance += (lExecTime - _lExecTimeMean) * (lExecTime - _lExecTimeMean);

			bFirstRun = false;
		}

		_lExecTimeVariance = (long) java.lang.Math.sqrt (_lExecTimeVariance / (iNumRuns - 1));

		_lInitializationDelay -= _lExecTimeMean;
		return true;
	}

	/**
	 * Get the number of runs for the statistics
	 * 
	 * @return Number of runs
	 */

	public int getRuns()
	{
		return _llExecTime.size();
	}

	/**
	 * Get the Mean in the execution time
	 * 
	 * @return Execution Time Mean
	 */

	public long getMean()
	{
		return _lExecTimeMean;
	}

	/**
	 * Get the Minimum in the execution time
	 * 
	 * @return Execution Time Minimum
	 */

	public long getMin()
	{
		return _lExecTimeMinimum;
	}

	/**
	 * Get the Maximum in the execution time
	 * 
	 * @return Execution Time Maximum
	 */

	public long getMax()
	{
		return _lExecTimeMaximum;
	}

	/**
	 * Get the variance in the execution time
	 * 
	 * @return Execution Time Variance
	 */

	public long getVariance()
	{
		return _lExecTimeVariance;
	}

	/**
	 * Get the delay when the regressor is invoked for the first time
	 * 
	 * @return Initialization Delay
	 */

	public long getInitializationDelay()
	{
		return _lInitializationDelay;
	}

	/**
	 * Return the string version of the statistics
	 * 
	 * @param strRegressionUnit Name the unit for which the regression run was done
	 * 
	 * @return String holding the content of the unit regression statistics
	 */

	public java.lang.String displayString (
		final java.lang.String strRegressionUnit)
	{
		if (null == strRegressionUnit || strRegressionUnit.isEmpty()) return null;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append ("\t" + strRegressionUnit + ".Stat.NumRuns=" + _llExecTime.size() + "\n");

		sb.append ("\t" + strRegressionUnit + ".Stat.ExecTimeMean=" + _lExecTimeMean + "\n");

		sb.append ("\t" + strRegressionUnit + ".Stat.ExecTimeMaximum=" + _lExecTimeMaximum + "\n");

		sb.append ("\t" + strRegressionUnit + ".Stat.ExecTimeMinimum=" + _lExecTimeMinimum + "\n");

		sb.append ("\t" + strRegressionUnit + ".Stat.ExecTimeVariance=" + _lExecTimeVariance + "\n");

		sb.append ("\t" + strRegressionUnit + ".Stat.InitializationDelay=" + _lInitializationDelay + "\n");

		sb.append ("\t" + strRegressionUnit + ".Stat.ExecTimeList=" + _llExecTime.toString() + "\n");

		return sb.toString();
	}
}
