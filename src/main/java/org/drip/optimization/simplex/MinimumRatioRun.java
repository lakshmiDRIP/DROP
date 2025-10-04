
package org.drip.optimization.simplex;

import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.FormatUtil;

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
 * <i>MinimumRatioRun</i> holds the results of a Minimum Ratio Test Run. It provides the following Functions:
 * 	<ul>
 * 		<li><i>MinimumRatioRun</i> Constructor</li>
 * 		<li>Retrieve the Entering Variable Pivot Column Index</li>
 * 		<li>Retrieve the Pivot Row Index</li>
 * 		<li>Retrieve the Optimal Row Minimum Implied Variate</li>
 * 		<li>Convert the State to a JSON-like String</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 *  	<li>
 * 			Dadush, D., and S. Huiberts (2020): A Friendly Smoothed Analysis of the Simplex Method <i>SIAM
 * 				Journal on Computing</i> <b>49 (5)</b> 449-499
 *  	</li>
 * 		<li>
 * 			Dantzig, G. B., and M. N. Thapa (1997): <i>Linear Programming 1: Introduction</i>
 * 				<b>Springer-Verlag</b> New York NY
 * 		</li>
 * 		<li>
 * 			Murty, K. G. (1983): <i>Linear Programming</i> <b>John Wiley and Sons</b> New York NY
 * 		</li>
 * 		<li>
 * 			Nering, E. D., and A. W. Tucker (1993): <i>Linear Programs and Related Problems</i>
 * 				<b>Academic Press</b> Cambridge MA
 * 		</li>
 * 		<li>
 * 			Padberg, M. (1999): <i> Linear Optimization and Extensions 2<sup>nd</sup> Edition</i>
 * 				<b>Springer-Verlag</b> New York NY
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/simplex/README.md">R<sup>d</sup> to R<sup>1</sup> Simplex Scheme</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MinimumRatioRun
{
	private double _impliedVariate = Double.NaN;
	private int _pivotRowIndex = Integer.MAX_VALUE;
	private int _enteringVariableColumnIndex = Integer.MAX_VALUE;

	/**
	 * <i>MinimumRatioRun</i> Constructor
	 * 
	 * @param enteringVariableColumnIndex Entering Variable Pivot Column Index
	 * @param pivotRowIndex Pivot Row Index
	 * @param impliedVariate Optimal Row Minimum Implied Variate
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MinimumRatioRun (
		final int enteringVariableColumnIndex,
		final int pivotRowIndex,
		final double impliedVariate)
		throws Exception
	{
		if (!NumberUtil.IsValid (_impliedVariate = impliedVariate)) {
			throw new Exception ("MinimumRatioRun Constructor => Invalid Inputs");
		}

		_pivotRowIndex = pivotRowIndex;
		_enteringVariableColumnIndex = enteringVariableColumnIndex;
	}

	/**
	 * Retrieve the Entering Variable Pivot Column Index
	 * 
	 * @return Entering Variable Pivot Column Index
	 */

	public int enteringVariableColumnIndex()
	{
		return _enteringVariableColumnIndex;
	}

	/**
	 * Retrieve the Pivot Row Index
	 * 
	 * @return Pivot Row Index
	 */

	public int pivotRowIndex()
	{
		return _pivotRowIndex;
	}

	/**
	 * Retrieve the Optimal Row Minimum Implied Variate
	 * 
	 * @return Optimal Row Minimum Implied Variate
	 */

	public double impliedVariate()
	{
		return _impliedVariate;
	}

	/**
	 * Convert the State to a JSON-like String
	 * 
	 * @return State to a JSON-like String
	 */

	@Override public String toString()
	{
		return "Minimum Ratio Run:" + 
			" Entering Variable Pivot Column Index => " + _enteringVariableColumnIndex +
			"; Pivot Row Index => " + _pivotRowIndex +
			"; Optimal Row Minimum Implied Variate =>" + FormatUtil.FormatDouble (_impliedVariate, 4, 3, 1.);
	}
}
