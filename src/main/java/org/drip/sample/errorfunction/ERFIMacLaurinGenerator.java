
package org.drip.sample.errorfunction;

import java.util.Map;

import org.drip.function.erf.MacLaurinSeriesGenerator;
import org.drip.quant.common.FormatUtil;
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
 * <i>ERFIMacLaurinGenerator</i> illustrates the MacLaurin Series Coefficient Generation for the Error
 * Function Inverse. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN – A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann’s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/errorfunction/README.md">Error Function Variants Numerical Estimate</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ERFIMacLaurinGenerator
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int seriesTermCount = 5;
		int coefficientTermCount = 15;

		double[] termWeightCount =
		{
			Math.pow (Math.PI, 0.5) / 2.,
			-Math.pow (Math.PI, 1.5) / 24.,
			7. * Math.pow (Math.PI, 2.5) / 960.,
			-127. * Math.pow (Math.PI, 3.5) / 80640.,
			0.5 * 4369. * Math.pow (Math.PI, 4.5) / 5806080.,
			-0.5 * 34807. * Math.pow (Math.PI, 5.5) / 182476800.
		};

		System.out.println ("\t|---------------------||");

		System.out.println ("\t|  ERFI COEFFICIENTS  ||");

		System.out.println ("\t|---------------------||");

		System.out.println ("\t|    L -> R:          ||");

		System.out.println ("\t|        - Index      ||");

		System.out.println ("\t|        - Value      ||");

		System.out.println ("\t|---------------------||");

		for (int coefficientTermIndex = 0; coefficientTermIndex <= coefficientTermCount; ++coefficientTermIndex)
		{
			System.out.println (
				"\t|" + FormatUtil.FormatDouble (coefficientTermIndex, 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (MacLaurinSeriesGenerator.ERFICoefficient (coefficientTermIndex), 2, 9, 1.) + " ||"
			);
		}

		System.out.println ("\t|---------------------||");

		System.out.println();

		Map<Integer, Double> termWeightMap = MacLaurinSeriesGenerator.ERFI (seriesTermCount).termWeightMap();

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t|   MacLaurin Series Coefficients    ||");

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t|    L -> R:                         ||");

		System.out.println ("\t|        - Index                     ||");

		System.out.println ("\t|        - Value                     ||");

		System.out.println ("\t|        - Reconciler                ||");

		System.out.println ("\t|------------------------------------||");

		for (int seriesTermIndex = 0; seriesTermIndex <= seriesTermCount; ++seriesTermIndex)
		{
			System.out.println (
				"\t|" + FormatUtil.FormatDouble (seriesTermIndex, 1, 0, 1.) + " => " +
				FormatUtil.FormatDouble (termWeightMap.get (seriesTermIndex), 1, 10, 1.) + " | " +
				FormatUtil.FormatDouble (termWeightCount[seriesTermIndex], 1, 10, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
