
package org.drip.function.r1tor1solver;

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
 * <i>ExecutionInitializationOutput</i> holds the output of the root initializer calculation.
 * <br><br>
 * The following are the fields held by ExecutionInitializationOutput:
 * <br>
 * <ul>
 * 	<li>
 * 		Whether the initialization completed successfully
 * 	</li>
 * 	<li>
 * 		The number of iterations, the number of objective function calculations, and the time taken for the
 * 			initialization
 * 	</li>
 * 	<li>
 * 		The starting variate from the initialization
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Solvers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ExecutionInitializationOutput {
	private int _iNumOFCalcs = 0;
	private long _lStartTime = 0L;
	private boolean _bDone = false;
	private int _iNumIterations = 0;
	private int _iNumOFDerivCalcs = 0;
	private double _dblTime = java.lang.Double.NaN;
	private double _dblStartingVariate = java.lang.Double.NaN;

	protected ExecutionInitializationOutput()
	{
		_lStartTime = System.nanoTime();
	}

	protected ExecutionInitializationOutput (
		final ExecutionInitializationOutput eiopOther)
		throws java.lang.Exception
	{
		if (null == eiopOther)
			throw new java.lang.Exception ("ExecutionInitializationOutput constructor: Invalid inputs!");

		_iNumOFCalcs = eiopOther._iNumOFCalcs;
		_lStartTime = eiopOther._lStartTime;
		_bDone = eiopOther._bDone;
		_iNumIterations = eiopOther._iNumIterations;
		_iNumOFDerivCalcs = eiopOther._iNumOFDerivCalcs;
		_dblTime = eiopOther._dblTime;
		_dblStartingVariate = eiopOther._dblStartingVariate;
	}

	protected boolean done()
	{
		_dblTime = (System.nanoTime() - _lStartTime) * 0.000001;

		return _bDone = true;
	}

	/**
	 * Increment the Number of Iterations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public final boolean incrIterations()
	{
		++_iNumIterations;
		return true;
	}

	/**
	 * Return The number of Iterations consumed
	 * 
	 * @return Number of Iterations consumed
	 */

	public final int getNumIterations()
	{
		return _iNumIterations;
	}

	/**
	 * Increment the Number of Objective Function Evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public final boolean incrOFCalcs()
	{
		++_iNumOFCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function calculations needed
	 * 
	 * @return Number of objective function calculations needed
	 */

	public final int getNumOFCalcs()
	{
		return _iNumOFCalcs;
	}

	/**
	 * Increment the number of Objective Function Derivative evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public final boolean incrOFDerivCalcs()
	{
		++_iNumOFDerivCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function derivative calculations needed
	 * 
	 * @return Number of objective function derivative calculations needed
	 */

	public final int getNumOFDerivCalcs()
	{
		return _iNumOFDerivCalcs;
	}

	/**
	 * Indicate if the execution initialization is done
	 * 
	 * @return TRUE - Execution initialization is done
	 */

	public final boolean isDone()
	{
		return _bDone;
	}

	/**
	 * Return the time elapsed for the execution initialization operation
	 * 
	 * @return execution initialization time
	 */

	public final double time()
	{
		return _dblTime;
	}

	/**
	 * Set the Starting Variate
	 * 
	 * @param dblStartingVariate Starting Variate
	 * 
	 * @return TRUE - Starting Variate set successfully
	 */

	public boolean setStartingVariate (
		final double dblStartingVariate)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblStartingVariate)) return false;

		_dblStartingVariate = dblStartingVariate;
		return true;
	}

	/**
	 * Return the Starting Variate
	 * 
	 * @return Starting Variate
	 */

	public double getStartingVariate()
	{
		return _dblStartingVariate;
	}

	/**
	 * Return a string form of the Initializer output
	 * 
	 * @return String form of the Initializer output
	 */

	public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append ("\t\tInitialization Done? " + isDone() + " [" + time() + " msec]");

		sb.append ("\n\t\tNum Iterations: " + getNumIterations());

		sb.append ("\n\t\tNum OF Calculations: " + getNumOFCalcs());

		sb.append ("\n\t\tNum OF Derivative Calculations: " + getNumOFDerivCalcs());

		sb.append ("\n\t\tStarting Variate: " + getStartingVariate());

		return sb.toString();
	}
}
