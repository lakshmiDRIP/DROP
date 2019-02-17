
package org.drip.sample.anfuso2017;

import java.util.ArrayList;
import java.util.List;

import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.discrete.CorrelatedPathVertexDimension;
import org.drip.measure.statistics.MultivariateDiscrete;
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
 * <i>RollingWindowCorrelation8</i> demonstrates computing the Correlation on a Rolling Window Basis between
 * Two Correlated Series as illustrated in Table 8 of Anfuso, Karyampas, and Nawroth (2017).
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit
 *  			Pricing, Palgrave Macmillan
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationLibrary.md">Model Validation Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/anfuso2013">Anfuso, Karyampas, and Nawroth (2013) Replications</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RollingWindowCorrelation8
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int pathCount = 1;
		int vertexCount = 390;
		int rollingWindow = 26;
		double[][] correlation = 
		{
			{1.000, 0.161},	// SNP500
			{0.161, 1.000},	// CHFUSD
		};

		CorrelatedPathVertexDimension correlatedPathVertexDimension = new CorrelatedPathVertexDimension (
			new RandomNumberGenerator(),
			correlation,
			vertexCount,
			pathCount,
			false,
			null
		);

		double[][] correlatedSequence =
			correlatedPathVertexDimension.straightMultiPathVertexRd()[0].flatform();

		List<double[]> windowSequence = new ArrayList<double[]>();

		for (int rollingIndex = 0; rollingIndex < rollingWindow; ++rollingIndex)
		{
			windowSequence.add (correlatedSequence[rollingIndex]);
		}

		double[][] rollingWindowSequence = new double[rollingWindow][2];

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|   Time Series Rolling Window Correlation    ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|    L -> R:                                  ||");

		System.out.println ("\t|            - SNP500                         ||");

		System.out.println ("\t|            - CHFUSD                         ||");

		System.out.println ("\t|            - SNP500 vs. CHFUSD Correlation  ||");

		System.out.println ("\t|            - CHFUSD vs. SNP500 Correlation  ||");

		System.out.println ("\t|---------------------------------------------||");

		for (int index = rollingWindow; index < vertexCount; ++index)
		{
			windowSequence.toArray (rollingWindowSequence);

			MultivariateDiscrete multivariateDiscrete = new MultivariateDiscrete (rollingWindowSequence);

			double[][] rollingWindowCorrelation = multivariateDiscrete.correlation();

			System.out.println ("\t| " +
				FormatUtil.FormatDouble (correlatedSequence[index][0], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (correlatedSequence[index][1], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (rollingWindowCorrelation[0][1], 2, 1, 100.) + "% | " +
				FormatUtil.FormatDouble (rollingWindowCorrelation[1][0], 2, 1, 100.) + "% ||"
			);

			if (index < vertexCount - 1)
			{
				windowSequence.remove (0);

				windowSequence.add (correlatedSequence[index + 1]);
			}
		}

		System.out.println ("\t|---------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
