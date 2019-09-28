
package org.drip.sample.randomdiscrete;

import org.drip.measure.discrete.SequenceGenerator;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>ScaledGamma</i> demonstrates Generation of Scaled Gamma R<sup>1</sup> Random Numbers with different
 * Degrees of Freedom and Scale Parameters.
 *
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Backstrom, T., and J. Fischer (2018): Fast Randomization for Distributed Low Bit-rate Coding of
 * 				Speech and Audio <i>IEEE/ACM Transactions on Audio, Speech, and Language Processing</i> <b>26
 * 				(1)</b> 19-30
 * 		</li>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Johnson, N. L., S. Klotz, and N. Balakrishnan (1994): <i>Continuous Univariate Distributions
 * 				<b>1</b> 2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			Lancaster, H, O. (1969): <i>The Chi-Squared Distribution</i> <b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Pillai, N. S. (1026): An Unexpected Encounter with Cauchy and Levy <i>Annals of Statistics</i>
 * 				<b>44 (5)</b> 2089-2097
 * 		</li>
 * 	</ul>
 * 
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discrete">Discrete</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScaledGamma
{

	private static final void DisplayStream (
		final int degreesOfFreedom,
		final double scale)
		throws Exception
	{
		double[] randomArray = SequenceGenerator.ScaledGamma (
			200,
			degreesOfFreedom,
			scale
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|  Degrees of Freedom => " + degreesOfFreedom + "; Scale => " + scale);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------||");

		for (int row = 0; row < 20; ++row)
		{
			String rowDump = "\t|";

			for (int column = 0; column < 10; ++column)
			{
				rowDump = rowDump + FormatUtil.FormatDouble (
					randomArray[row * 10 + column], 2, 4, 1.
				) + " |";
			}

			System.out.println (rowDump + "|");
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		DisplayStream (
			2,
			0.5
		);

		DisplayStream (
			2,
			2.0
		);

		DisplayStream (
			2,
			5.0
		);

		DisplayStream (
			5,
			0.5
		);

		DisplayStream (
			5,
			2.0
		);

		DisplayStream (
			5,
			5.0
		);

		DisplayStream (
			10,
			0.5
		);

		DisplayStream (
			10,
			2.0
		);

		DisplayStream (
			10,
			5.0
		);

		EnvManager.TerminateEnv();
	}
}
