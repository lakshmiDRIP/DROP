
package org.drip.function.r1tor1custom;

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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1custom/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Custom Functions</a></li>
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblA = dblA) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblB = dblB) || _dblA == _dblB)
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblT))
			throw new java.lang.Exception ("AlmgrenEnhancedEulerUpdate::evaluate => Invalid Inputs");

		double dblInvExpAT = java.lang.Math.exp (-1. * _dblA * dblT);

		double dblInvExpBT = java.lang.Math.exp (-1. * _dblB * dblT);

		return (_dblA * dblInvExpBT - _dblB * dblInvExpAT) / (dblInvExpBT - dblInvExpAT);
	}
}
