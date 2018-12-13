
package org.drip.sample.blacklitterman;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;

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
 * <i>OToole2013</i> reconciles the Outputs of the Black-Litterman Model Process. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model Portfolios</i>
 *  			<b>Goldman Sachs Asset Management</b>
 *  	</li>
 *  	<li>
 *  		O'Toole, R. (2013): The Black-Litterman Model: The Risk Budgeting Perspective <i>Journal of Asset
 *  			Management</i> <b>14 (1)</b> 2-13
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/blacklitterman/README.md">Black Litterman</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OToole2013 {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String[] astrG7 = new String[] {
			"Australia                ",
			"Canada                   ",
			"France                   ",
			"Germany                  ",
			"Japan                    ",
			"United Kingdom           ",
			"United States of America "
		};

		double[][] aadblG7ExcessReturnsCorrelation = new double[][] {
			{1.000, 0.488, 0.478, 0.515, 0.439, 0.512, 0.491},
			{0.488, 1.000, 0.664, 0.655, 0.310, 0.608, 0.779},
			{0.478, 0.664, 1.000, 0.861, 0.355, 0.783, 0.668},
			{0.515, 0.655, 0.861, 1.000, 0.354, 0.777, 0.653},
			{0.439, 0.310, 0.355, 0.354, 1.000, 0.405, 0.306},
			{0.512, 0.608, 0.783, 0.777, 0.405, 1.000, 0.652},
			{0.491, 0.779, 0.668, 0.653, 0.306, 0.652, 1.000}
		};

		double[] adblG7ExcessReturnsVolatility = new double[] {
			0.160,
			0.203,
			0.248,
			0.271,
			0.210,
			0.200,
			0.187
		};

		double[] adblG7BenchmarkWeight = new double[] {
			0.016,
			0.022,
			0.052,
			0.055,
			0.116,
			0.124,
			0.615
		};

		double[] adblG7ImpliedReturnsReconciler = new double[] {
			0.0394,
			0.0692,
			0.0836,
			0.0903,
			0.0430,
			0.0677,
			0.0756
		};

		double dblDelta = 2.5;
		double[][] aadblG7Covariance = new double[astrG7.length][astrG7.length];

		for (int i = 0; i < astrG7.length; ++i) {
			for (int j = 0; j < astrG7.length; ++j)
				aadblG7Covariance[i][j] = aadblG7ExcessReturnsCorrelation[i][j] * adblG7ExcessReturnsVolatility[i] * adblG7ExcessReturnsVolatility[j];
		}

		double[] adblG7ImpliedReturns = Matrix.Product (
			aadblG7Covariance,
			adblG7BenchmarkWeight
		);

		System.out.println ("\n\t|-----------------------------------------------------------------------------------||");

		System.out.println ("\t|                         G7 CORRELATION MATRIX INPUT                               ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		for (int i = 0; i < astrG7.length; ++i) {
			String strDump = "\t| " + astrG7[i] + " ";

			for (int j = 0; j < astrG7.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblG7ExcessReturnsCorrelation[i][j], 1, 3, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------||\n");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		System.out.println ("\t|                         G7 COVARIANCE MATRIX INPUT                                ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		for (int i = 0; i < astrG7.length; ++i) {
			String strDump = "\t| " + astrG7[i] + " ";

			for (int j = 0; j < astrG7.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblG7Covariance[i][j], 1, 3, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||     BENCHMARK WEIGHT AND RETURNS VOLATILITY  ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||        ASSET CLASS        => WEIGHT |   VOL  ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int i = 0; i < astrG7.length; ++i)
			System.out.println (
				"\t|| " + astrG7[i] + " => " +
				FormatUtil.FormatDouble (adblG7BenchmarkWeight[i], 2, 1, 100.) + "% | " +
				FormatUtil.FormatDouble (adblG7ExcessReturnsVolatility[i], 2, 1, 100.) + "% ||"
			);

		System.out.println ("\t||----------------------------------------------||\n");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||          RISK PREMIUM IMPLIED RETURNS        ||");

		System.out.println ("\t||----------------------------------------------||");

		System.out.println ("\t||         ASSET CLASS       => OUTPUT |  PAPER ||");

		System.out.println ("\t||----------------------------------------------||");

		for (int i = 0; i < adblG7ImpliedReturns.length; ++i)
			System.out.println (
				"\t|| " + astrG7[i] + " => " +
				FormatUtil.FormatDouble (dblDelta * adblG7ImpliedReturns[i], 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (adblG7ImpliedReturnsReconciler[i], 1, 2, 100.) + "% ||"
			);

		System.out.println ("\t||----------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
