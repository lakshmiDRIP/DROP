
package org.drip.sample.ckls;

import org.drip.dynamics.ito.R1WienerDriver;
import org.drip.dynamics.physical.LangevinEvolver;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>LangevinEvolver</i> implements the Noisy Elastic Relaxation Process in a Friction-Thermal Background.
 * 	The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Doob, J. L. (1942): The Brownian Movement and Stochastic Equations <i>Annals of Mathematics</i>
 * 				<b>43 (2)</b> 351-369
 * 		</li>
 * 		<li>
 * 			Gardiner, C. W. (2009): <i>Stochastic Methods: A Handbook for the Natural and Social Sciences
 * 				4<sup>th</sup> Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Karatzas, I., and S. E. Shreve (1991): <i>Brownian Motion and Stochastic Calculus 2<sup>nd</sup>
 * 				Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Risken, H., and F. Till (1996): <i>The Fokker-Planck Equation – Methods of Solution and
 * 				Applications</i> <b>Springer</b>
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/ckls/README.md">Analysis of CKLS Process Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LangevinEvolution
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double timeWidth = 1.;
		double temperature = 300.;
		double restLength = 1.e-10;
		double initialLength = 3.e-10;
		double dampingCoefficient = 0.5;
		double elasticityCoefficient = 1.0;
		double[] timeArray =
		{
			0.0,
			0.5,
			1.0,
			1.5,
			2.0,
			2.5,
			3.0,
			3.5,
			4.0,
			4.5,
			5.0,
			5.5,
			6.0,
			6.5,
			7.0,
			7.5,
			8.0,
			8.5,
			9.0,
			9.5
		};

		System.out.println (
			"\t|-----------------------------------------------------------||"
		);

		System.out.println (
			"\t|                 LANGEVIN SYSTEM EVOLUTION                 ||"
		);

		System.out.println (
			"\t|-----------------------------------------------------------||"
		);

		System.out.println (
			"\t| Temperature             => " + temperature
		);

		System.out.println (
			"\t| Rest Length             => " + restLength
		);

		System.out.println (
			"\t| Damping Coefficient     => " + dampingCoefficient
		);

		System.out.println (
			"\t| Elasticity Coefficient  => " + elasticityCoefficient
		);

		System.out.println (
			"\t|-----------------------------------------------------------||"
		);

		R1WienerDriver wienerDriver = new R1WienerDriver (
			timeWidth
		);

		LangevinEvolver langevinEvolver = new LangevinEvolver (
			elasticityCoefficient,
			dampingCoefficient,
			restLength,
			temperature,
			wienerDriver
		);

		System.out.println (
			"\t| Correlation Time        => " + langevinEvolver.correlationTime()
		);

		System.out.println (
			"\t| Diffusion Coefficient   => " + langevinEvolver.stokesEinsteinEffectiveDiffusionCoefficient()
		);

		System.out.println (
			"\t| Equi-Partition Energy   => " + langevinEvolver.equiPartitionEnergy()
		);

		System.out.println (
			"\t| Volatility              => " + langevinEvolver.cklsParameters().volatilityCoefficient()
		);

		System.out.println (
			"\t| Mean-Reversion Level    => " + langevinEvolver.cklsParameters().meanReversionLevel()
		);

		System.out.println (
			"\t| Mean-Reversion Speed    => " + langevinEvolver.cklsParameters().meanReversionSpeed()
		);

		System.out.println (
			"\t|-----------------------------------------------------------||"
		);

		System.out.println();

		System.out.println (
			"\t|-----------------------------------------------------------||"
		);

		for (double time : timeArray)
		{
			System.out.println (
				"\t| " + FormatUtil.FormatDouble (time, 1, 1, 1.) + " => " +
				FormatUtil.FormatDouble (
					langevinEvolver.fluctuationCorrelation (
						time
					), 1, 5, 1.
				) + " | " + FormatUtil.FormatDouble (
					langevinEvolver.fluctuationCovariance (
						time
					), 1, 25, 1.
				) + " | " + FormatUtil.FormatDouble (
					langevinEvolver.mean (
						initialLength,
						time
					), 1, 15, 1.
				)
			);
		}

		System.out.println (
			"\t|-----------------------------------------------------------||"
		);

		System.out.println();

		double[][] aitSahaliaMLEAsymptote = langevinEvolver.aitSahaliaMLEAsymptote (
			1.
		);

		System.out.println (
			"\t|---------------------------------||"
		);

		System.out.println (
			"\t| AIT SAHALIA MLE ASYMPTOTE ERROR ||"
		);

		System.out.println (
			"\t|---------------------------------||"
		);

		org.drip.numerical.common.NumberUtil.Print2DArray (
			"\t|\t",
			aitSahaliaMLEAsymptote,
			false
		);

		System.out.println (
			"\t|---------------------------------||"
		);

		EnvManager.TerminateEnv();
	}
}
