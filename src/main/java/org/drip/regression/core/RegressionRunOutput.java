
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
 * <i>RegressionRunOutput</i> contains the output of a single regression activity. It holds the following:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			The execution time
 *  	</li>
 *  	<li>
 * 			The Success/failure status of the run
 *  	</li>
 *  	<li>
 * 			The regression scenario that was executed
 *  	</li>
 *  	<li>
 * 			The Completion time for the regression module
 *  	</li>
 *  	<li>
 * 			The Regression Run Detail for the regression run
 *  	</li>
 *  </ul>
 * <br><br>
 * 
 * Another function displays the contents of this RegressionRunOutput instance.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression">Regression</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/core">Core</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
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
