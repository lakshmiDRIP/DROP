
package org.drip.regression.core;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * RegressionRunOutput contains the output of a single regression activity. It holds the following:
 * 	- The execution time
 * 	- The Success/failure status of the run
 * 	- The regression scenario that was executed
 * 	- The Completion time for the regression module
 * 	- The Regression Run Detail for the regression run
 * 
 * Another function displays the contents of this RegressionRunOutput instance.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RegressionRunOutput {

	/**
	 * Execution time for the Regression Module
	 */

	public long _lExecTime = 0L;

	/**
	 * Completion Status for the Regression Module
	 */

	public boolean _bStatus = false;

	/**
	 * Completion Status for the Regression Module
	 */

	public java.lang.String _strRegressionScenarioName = "";

	/**
	 * Completion Time for the Regression Module
	 */

	public java.util.Date _dtCompletion = null;

	private org.drip.regression.core.RegressionRunDetail _rnvd = new
		org.drip.regression.core.RegressionRunDetail();

	/**
	 * Regression Run Output Constructor
	 * 
	 * @param strRegressionScenarioName Regression Scenario Name
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RegressionRunOutput (
		final java.lang.String strRegressionScenarioName)
		throws java.lang.Exception
	{
		if (null == (_strRegressionScenarioName = strRegressionScenarioName) ||
			_strRegressionScenarioName.isEmpty())
			throw new java.lang.Exception ("RegressionRunOutput ctr: Invalid Regression Scenario Name!");
	}

	/**
	 * Set the termination status for the regression output
	 * 
	 * @param bSuccess TRUE - Regression Run succeeded
	 * 
	 * @return TRUE - Termination status successfully set
	 */

	public boolean setTerminationStatus (
		final boolean bSuccess)
	{
		_dtCompletion = new java.util.Date();

		_bStatus = bSuccess;
		return true;
	}

	/**
	 * Retrieve the regression details object
	 * 
	 * @return The regression details object
	 */

	public org.drip.regression.core.RegressionRunDetail getRegressionDetail()
	{
		return _rnvd;
	}

	/**
	 * Print the contents of the regression output
	 * 
	 * @param bDetailed Display detailed output
	 * 
	 * @return String representing the Regression output
	 */

	public java.lang.String displayString (
		final boolean bDetailed)
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append ("\n");

		sb.append ("\t\t" + _strRegressionScenarioName + ".Success=").append (_bStatus).append ("\n");

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapDetails = _rnvd.getFieldMap();

		if (null != mapDetails && 0 != mapDetails.size() && null != mapDetails.entrySet()) {
			for (java.util.Map.Entry<java.lang.String, java.lang.String> me : mapDetails.entrySet()) {
				if (null != me && null != me.getKey() && !me.getKey().isEmpty() && null != me.getValue() &&
					!me.getValue().isEmpty())
					sb.append ("\t\t" + _strRegressionScenarioName + "." + me.getKey() + "=").append
						(me.getValue()).append ("\n");
			}
		}

		sb.append ("\t\t" + _strRegressionScenarioName + ".FinishTime=").append (_dtCompletion).append
			("\n");

		sb.append ("\t\t" + _strRegressionScenarioName + ".ExecTime=").append (_lExecTime);

		return sb.toString();
	}
}
