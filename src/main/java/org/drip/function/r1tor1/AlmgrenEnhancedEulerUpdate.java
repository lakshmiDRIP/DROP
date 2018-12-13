
package org.drip.function.r1tor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>AlmgrenEnhancedEulerUpdate</i> is a R<sup>1</sup> To R<sup>1</sup> Function that is used in Almgren
 * (2009, 2012) to illustrate the Construction of the Enhanced Euler Update Scheme. The References are:
 * 
 * <br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 				of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/r1tor1/README.md">R<sup>1</sup> To R<sup>1</sup></a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AlmgrenEnhancedEulerUpdate extends org.drip.function.definition.R1ToR1 {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;

	/**
	 * AlmgrenEnhancedEulerUpdate Constructor
	 * 
	 * @param dblA The "A" Parameter
	 * @param dblB The "B" Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AlmgrenEnhancedEulerUpdate (
		final double dblA,
		final double dblB)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblA = dblA) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblB = dblB) || _dblA == _dblB)
			throw new java.lang.Exception ("AlmgrenEnhancedEulerUpdate Constructor => Inbalid Inputs");
	}

	/**
	 * Retrieve the "A" Parameter
	 * 
	 * @return The "A" Parameter
	 */

	public double a()
	{
		return _dblA;
	}

	/**
	 * Retrieve the "B" Parameter
	 * 
	 * @return The "B" Parameter
	 */

	public double b()
	{
		return _dblB;
	}

	@Override public double evaluate (
		final double dblT)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblT))
			throw new java.lang.Exception ("AlmgrenEnhancedEulerUpdate::evaluate => Invalid Inputs");

		double dblInvExpAT = java.lang.Math.exp (-1. * _dblA * dblT);

		double dblInvExpBT = java.lang.Math.exp (-1. * _dblB * dblT);

		return (_dblA * dblInvExpBT - _dblB * dblInvExpAT) / (dblInvExpBT - dblInvExpAT);
	}
}
