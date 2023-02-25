
package org.drip.sample.selection;

import org.drip.graph.selection.IntroselectControl;
import org.drip.graph.selection.Introselector;
import org.drip.graph.selection.OrderStatisticSelector;
import org.drip.graph.selection.QuickSelector;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>MusserSelect</i> illustrates the Construction and Usage of Musser's Introselect Algorithm. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322
 *  	</li>
 *  	<li>
 *  		Knuth, D. (1997): <i>The Art of Computer Programming 3<sup>rd</sup> Edition</i>
 *  			<b>Addison-Wesley</b>
 *  	</li>
 *  	<li>
 *  		Musser, D. R. (1997): Introselect Sorting and Selection Algorithms <i>Software: Practice and
 *  			Experience</i> <b>27 (8)</b> 983-993
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Introselect https://en.wikipedia.org/wiki/Introselect
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/selection/README.md">k<sup>th</sup> Extremum Element Selection Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MusserSelect
{

	private static final void RunIntroselect (
		final int groupElementCount,
		final int subPartitionReductionLimit,
		final double subPartitionCumulativeFactor,
		final Double[] numberArrayIn)
		throws Exception
	{
		Double[] numberArray = new Double[numberArrayIn.length];

		for (int i = 0;
			i < numberArrayIn.length;
			++i)
		{
			numberArray[i] = numberArrayIn[i];
		}

		IntroselectControl introselectControl = new IntroselectControl (
			subPartitionReductionLimit,
			subPartitionCumulativeFactor
		);

		OrderStatisticSelector<Double> introselectRecursive = new Introselector<Double> (
			numberArray,
			true,
			introselectControl,
			groupElementCount
		);

		System.out.println (
			"\t|---------------|"
		);

		System.out.println (
			"\t|     INPUT     |"
		);

		System.out.println (
			"\t|---------------|"
		);

		for (int i = 0;
			i < numberArray.length;
			++i)
		{
			System.out.println (
				"\t| " + i + " => " + FormatUtil.FormatDouble (
					numberArray[i], 1, 4, 1.
				)
			);
		}

		System.out.println (
			"\t|---------------|"
		);

		System.out.println();

		System.out.println (
			"\t|---------------|"
		);

		System.out.println (
			"\t|   RECURSIVE   |"
		);

		System.out.println (
			"\t|---------------|"
		);

		for (int i = 0;
			i < numberArray.length;
			++i)
		{
			System.out.println (
				"\t| " + i + " => " + FormatUtil.FormatDouble (
					introselectRecursive.select (
						i
					), 1, 4, 1.
				)
			);
		}

		System.out.println (
			"\t|---------------|"
		);

		System.out.println();

		QuickSelector<Double> introselectIterative = new Introselector<Double> (
			numberArray,
			true,
			introselectControl,
			groupElementCount
		);

		System.out.println (
			"\t|---------------|"
		);

		System.out.println (
			"\t|   ITERATIVE   |"
		);

		System.out.println (
			"\t|---------------|"
		);

		for (int i = 0;
			i < numberArray.length;
			++i)
		{
			System.out.println (
				"\t| " + i + " => " + FormatUtil.FormatDouble (
					introselectIterative.select (
						i
					), 1, 4, 1.
				)
			);
		}

		System.out.println (
			"\t|---------------|"
		);
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		int groupElementCount = 5;
		Double[] numberArray =
		{
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
			Math.random(),
		};

		int subPartitionReductionLimit = 100;
		double subPartitionCumulativeFactor = 50.;

		RunIntroselect (
			groupElementCount,
			subPartitionReductionLimit,
			subPartitionCumulativeFactor,
			numberArray
		);

		subPartitionReductionLimit = 2;
		subPartitionCumulativeFactor = 1.1;

		RunIntroselect (
			groupElementCount,
			subPartitionReductionLimit,
			subPartitionCumulativeFactor,
			numberArray
		);

		EnvManager.TerminateEnv();
	}
}
