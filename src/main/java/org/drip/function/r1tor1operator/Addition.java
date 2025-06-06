
package org.drip.function.r1tor1operator;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>Addition</i> implements the Univariate <code>x + a</code> Operator Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Belsley, D. A., E. Kuh, and R. E. Welsch (1980): <i>Regression Dynamics: Identifying Influential
 * 				Data and Sources of Collinearity</i> <b>John Wiley and Sons</b> New York NY
 * 		</li>
 * 		<li>
 * 			Cheney, K. (2008): <i>Numerical Mathematics and Computing</i> <b>Cengage Learning</b> New York NY
 * 		</li>
 * 		<li>
 * 			Pesaran, M. H. (2015): <i>Time Series and Panel Data Econometrics</i> <b>Oxford University
 * 				Press</b> New York NY
 * 		</li>
 * 		<li>
 * 			Trefethen, L. N., and D. Bau III (1997): <i>Numerical Linear Algebra</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia PA
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Condition Number https://en.wikipedia.org/wiki/Condition_number
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1operator/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Operator Functions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Addition extends R1ToR1
{
	private double _a = Double.NaN;

	/**
	 * <i>Addition</i> Constructor
	 * 
	 * @param a <i>a</i>
	 * 
	 * @throws Exception Thrown if the Input is Invalid
	 */

	public Addition (
		final double a)
		throws Exception
	{
		super (null);

		if (!NumberUtil.IsValid (_a = a)) {
			throw new Exception ("Addition Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve <i>a</i>
	 * 
	 * @return <i>a</i>
	 */

	public double a()
	{
		return _a;
	}

	@Override public double evaluate (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("Addition::evaluate => Invalid Inputs");
		}

		return x + _a;
	}

	@Override public double derivative (
		final double x,
		final int order)
		throws Exception
	{
		return 1 == order ? 1. : 0.;
	}

	@Override public double conditionNumber (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("Addition::conditionNumber => Invalid Inputs");
		}

		return Math.abs (x / (x + _a));
	}
}
