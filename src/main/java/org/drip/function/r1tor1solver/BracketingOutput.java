
package org.drip.function.r1tor1solver;

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
 * <i>BracketingOutput</i> carries the results of the bracketing initialization.
 * <br><br>
 * 	In addition to the fields of ExecutionInitializationOutput, BracketingOutput holds the left/right bracket
 *  	variates and the corresponding values for the objective function.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/r1tor1solver">R<sup>1</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BracketingOutput extends org.drip.function.r1tor1solver.ExecutionInitializationOutput {
	private double _dblOFLeft = java.lang.Double.NaN;
	private double _dblOFRight = java.lang.Double.NaN;
	private double _dblVariateLeft = java.lang.Double.NaN;
	private double _dblVariateRight = java.lang.Double.NaN;

	/**
	 * Default BracketingOutput constructor: Initializes the output
	 */

	public BracketingOutput()
	{
		super();
	}

	/**
	 * Return the left Variate
	 * 
	 * @return Left Variate
	 */

	public double getVariateLeft()
	{
		return _dblVariateLeft;
	}

	/**
	 * Return the Right Variate
	 * 
	 * @return Right Variate
	 */

	public double getVariateRight()
	{
		return _dblVariateRight;
	}

	/**
	 * Return the left OF
	 * 
	 * @return Left OF
	 */

	public double getOFLeft()
	{
		return _dblOFLeft;
	}

	/**
	 * Return the Right OF
	 * 
	 * @return Right OF
	 */

	public double getOFRight()
	{
		return _dblOFRight;
	}

	/**
	 * Set the brackets in the output object
	 * 
	 * @param dblVariateLeft Left Variate
	 * @param dblVariateRight Right Variate
	 * @param dblOFLeft Left OF
	 * @param dblOFRight Right OF
	 * @param dblStartingVariate Starting Variate
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean done (
		final double dblVariateLeft,
		final double dblVariateRight,
		final double dblOFLeft,
		final double dblOFRight,
		final double dblStartingVariate)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblVariateLeft = dblVariateLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblVariateRight = dblVariateRight) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblOFLeft = dblOFLeft) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblOFRight = dblOFRight) ||
						!setStartingVariate (dblStartingVariate))
			return false;

		return done();
	}

	/**
	 * Make a ConvergenceOutput for the Open Method from the bracketing output
	 * 
	 * @return The ConvergenceOutput object
	 */

	public org.drip.function.r1tor1solver.ConvergenceOutput makeConvergenceVariate()
	{
		org.drip.function.r1tor1solver.ConvergenceOutput cop = null;

		try {
			cop = new org.drip.function.r1tor1solver.ConvergenceOutput (this);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return cop.done (getStartingVariate()) ? cop : null;
	}

	@Override public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append (super.displayString());

		sb.append ("\n\t\tLeft Bracket: " + getVariateLeft());

		sb.append ("\n\t\tRight Bracket: " + getVariateRight());

		sb.append ("\n\t\tLeft OF: " + getOFLeft());

		sb.append ("\n\t\tRight OF: " + getOFRight());

		return sb.toString();
	}
}
