
package org.drip.regression.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>RegressionEngine</i> provides the control and frame-work functionality for the General Purpose
 * Regression Suite. It invokes the following steps as part of the execution:
 *  <ul>
 *  	<li>
 * 			Initialize the regression environment. This step sets up the regression sets, and adds individual
 * 				regressors to the set.
 *  	</li>
 *  	<li>
 * 			Invoke the regressors in each set one by one.
 *  	</li>
 *  	<li>
 * 			Collect the results and details of the regression runs.
 *  	</li>
 *  	<li>
 * 			Compile the regression statistics.
 *  	</li>
 *  	<li>
 * 			Optionally display the regression statistics.
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression">Regression</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core">Core</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RegressionEngine {

	/**
	 * Regression outputs decomposed at individual Module Units 
	 */

	public static final int REGRESSION_DETAIL_MODULE_UNIT_DECOMPOSED = 1;

	/**
	 * Regression outputs rolled up to Module Units
	 */

	public static final int REGRESSION_DETAIL_MODULE_UNIT_AGGREGATED = 2;

	/**
	 * Regression outputs rolled up to Modules 
	 */

	public static final int REGRESSION_DETAIL_MODULE_AGGREGATED = 4;

	/**
	 * Regression Output: Statistics
	 */

	public static final int REGRESSION_DETAIL_STATS = 8;

	private int _iNumRuns = 0;
	private int _iRegressionDetail = REGRESSION_DETAIL_MODULE_UNIT_DECOMPOSED;

	protected java.util.Set<org.drip.regression.core.RegressorSet> _setRS = new
		java.util.HashSet<org.drip.regression.core.RegressorSet>();

	org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.regression.core.UnitRegressionStat> _mapURS = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.regression.core.UnitRegressionStat>();

	private boolean executeRegressionSet (
		final org.drip.regression.core.RegressorSet rs)
	{
		if (null == rs || null == rs.getSetName() || rs.getSetName().isEmpty()) return false;

		java.util.List<org.drip.regression.core.UnitRegressor> lsRegressor = rs.getRegressorSet();

		if (null == lsRegressor || 0 == lsRegressor.size()) {
			System.out.println ("Cannot get the " + rs.getSetName() + " scenarios!");

			return false;
		}

		long lModuleTime = 0;
		int iNumRegressionsSucceeded = 0;
		boolean bRegressionDetail = 0 != (REGRESSION_DETAIL_MODULE_UNIT_DECOMPOSED & _iRegressionDetail) ?
			true : false;

		System.out.println ("\t" + rs.getSetName() + " starts at " + new java.util.Date());

		for (org.drip.regression.core.UnitRegressor r : lsRegressor) {
			if (null == r) continue;

			org.drip.regression.core.RegressionRunOutput ro = r.regress();

			if (null != ro && ro._bStatus) {
				++iNumRegressionsSucceeded;
				lModuleTime += ro._lExecTime;

				java.lang.String strScenarioQualifiedRegressor = rs.getSetName() + "." + r.getName();

				org.drip.regression.core.UnitRegressionStat urs = _mapURS.get
					(strScenarioQualifiedRegressor);

				if (null == urs) urs = new org.drip.regression.core.UnitRegressionStat();

				urs.addExecTime (ro._lExecTime);

				_mapURS.put (strScenarioQualifiedRegressor, urs);

				if (0 != (REGRESSION_DETAIL_MODULE_UNIT_DECOMPOSED & _iRegressionDetail) ||
					0 != (REGRESSION_DETAIL_MODULE_UNIT_AGGREGATED & _iRegressionDetail))
					System.out.println (ro.displayString (bRegressionDetail));
			}
		}

		System.out.println ("\t" + rs.getSetName() + "=> " + lModuleTime+ " (mu-s): " +
			iNumRegressionsSucceeded + " / " + lsRegressor.size() + " succeeded.");

		System.out.println ("\t" + rs.getSetName() + " ends at " + new java.util.Date() + "\n");

		return true;
	}

	protected RegressionEngine (
		final int iNumRuns,
		final int iRegressionDetail)
		throws java.lang.Exception
	{
		if (0 >= (_iNumRuns = iNumRuns))
			throw new java.lang.Exception ("RegressionEngine ctr: Invalid inputs");

		_iRegressionDetail = iRegressionDetail;
	}

	/**
	 * Add the regressor set to the framework
	 * 
	 * @param rs Regressor Set
	 * 
	 * @return TRUE - Regressor Set successfully added
	 */

	protected final boolean addRegressorSet (
		final org.drip.regression.core.RegressorSet rs)
	{
		if (null == rs || !rs.setupRegressors()) return false;

		_setRS.add (rs);

		return true;
	}

	/**
	 * One-time initialization of the regression engine environment
	 * 
	 * @return TRUE - Regression Environment initialized successfully
	 */

	public boolean initRegressionEnv()
	{
		return true;
	}

	/**
	 * Launch the Regression Engine and execute the regression sets
	 * 
	 * @return TRUE - Launch Successful
	 */

	protected final boolean launch()
	{
		if (0 == _setRS.size() || !initRegressionEnv()) return false;

		boolean bLaunchSuccessful = true;

		for (int i = 0; i < _iNumRuns; ++i) {
			for (org.drip.regression.core.RegressorSet rs : _setRS)
				bLaunchSuccessful = executeRegressionSet (rs);
		}

		if (0 != _mapURS.size() && null != _mapURS.entrySet()) {
			for (java.util.Map.Entry<java.lang.String, org.drip.regression.core.UnitRegressionStat>
				meURS : _mapURS.entrySet()) {
				if (null == meURS || null == meURS.getKey() || meURS.getKey().isEmpty()) continue;

				org.drip.regression.core.UnitRegressionStat urs = meURS.getValue();

				if (null == urs || !urs.generateStat()) continue;

				if (0 != (REGRESSION_DETAIL_STATS & _iRegressionDetail)) {
					System.out.println ("\n--------\nStats for " + meURS.getKey() + "\n--------");

					System.out.println (urs.displayString (meURS.getKey()));
				}
			}
		}

		return bLaunchSuccessful;
	}
}
