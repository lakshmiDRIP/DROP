
package org.drip.function.r1tor1solver;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>FixedPointFinderOutput</i> holds the result of the fixed point search.
 *
 * FixedPointFinderOutput contains the following fields:
 * <br>
 * <ul>
 * 	<li>
 * 		Whether the search completed successfully
 * 	</li>
 * 	<li>
 * 		The number of iterations, the number of objective function base/derivative calculations, and the time
 * 			taken for the search
 * 	</li>
 * 	<li>
 * 		The output from initialization
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

public class FixedPointFinderOutput {
	private int _iNumOFCalcs = 0;
	private long _lStartTime = 0L;
	private int _iNumIterations = 0;
	private int _iNumOFDerivCalcs = 0;
	private boolean _bHasRoot = false;
	private double _dblRoot = java.lang.Double.NaN;
	private double _dblRootFindingTime = java.lang.Double.NaN;
	private org.drip.function.r1tor1solver.ExecutionInitializationOutput _eiop = null;

	/**
	 * FixedPointFinderOutput constructor
	 * 
	 * @param eiop Execution Initialization Output 1D
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public FixedPointFinderOutput (
		final org.drip.function.r1tor1solver.ExecutionInitializationOutput eiop)
		throws java.lang.Exception
	{
		if (null == (_eiop = eiop))
			throw new java.lang.Exception ("FixedPointFinderOutput constructor: Invalid inputs!");

		_lStartTime = System.nanoTime();
	}

	/**
	 * Set the Root
	 * 
	 * @param dblRoot Root
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRoot (
		final double dblRoot)
	{
		_dblRootFindingTime = (System.nanoTime() - _lStartTime) * 0.000001;

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblRoot = dblRoot)) return false;

		return _bHasRoot = true;
	}

	/**
	 * Indicate whether the root is present in the output, i.e., if the finder has successfully completed.
	 * 
	 * @return TRUE - Root exists in the output
	 */

	public boolean containsRoot()
	{
		return _bHasRoot;
	}

	/**
	 * Return the time elapsed for the the full root finding operation
	 * 
	 * @return Time taken for root finding
	 */

	public double time()
	{
		return _dblRootFindingTime;
	}

	/**
	 * Return the root
	 * 
	 * @return Root
	 */

	public double getRoot()
	{
		return _dblRoot;
	}

	/**
	 * Increment the number of Iterations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public boolean incrIterations()
	{
		++_iNumIterations;
		return true;
	}

	/**
	 * Return The number of iterations taken
	 * 
	 * @return Number of iterations taken
	 */

	public int getNumIterations()
	{
		return _iNumIterations;
	}

	/**
	 * Increment the number of Objective Function evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public boolean incrOFCalcs()
	{
		++_iNumOFCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function calculations needed
	 * 
	 * @return Number of objective function calculations needed
	 */

	public int getNumOFCalcs()
	{
		return _iNumOFCalcs;
	}

	/**
	 * Increment the number of Objective Function Derivative evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public boolean incrOFDerivCalcs()
	{
		++_iNumOFDerivCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function derivative calculations needed
	 * 
	 * @return Number of objective function derivative calculations needed
	 */

	public int getNumOFDerivCalcs()
	{
		return _iNumOFDerivCalcs;
	}

	/**
	 * Retrieve the Execution Initialization Output
	 * 
	 * @return Execution Initialization Output
	 */

	public org.drip.function.r1tor1solver.ExecutionInitializationOutput getEIOP()
	{
		return _eiop;
	}

	/**
	 * Return a string form of the root finder output
	 * 
	 * @return String form of the root finder output
	 */

	public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append (_eiop.displayString());

		sb.append ("\n\tRoot finding Done? " + _bHasRoot + " [" + _dblRootFindingTime + " msec]");

		sb.append ("\n\tRoot: " + _dblRoot);

		sb.append ("\n\tNum Iterations: " + _iNumIterations);

		sb.append ("\n\tNum OF Calculations: " + _iNumOFCalcs);

		sb.append ("\n\tNum OF Derivative Calculations: " + _iNumOFDerivCalcs);

		return sb.toString();
	}
}
