
package org.drip.dynamics.kolmogorov;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1FokkerPlanckCIR</i> exposes the R<sup>1</sup> Cox-Ingersoll-Ross Probability Density Function
 * 	Evolution Equation. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Bogoliubov, N. N., and D. P. Sankevich (1994): N. N. Bogoliubov and Statistical Mechanics
 * 				<i>Russian Mathematical Surveys</i> <b>49 (5)</b> 19-49
 * 		</li>
 * 		<li>
 * 			Holubec, V., K. Kroy, and S. Steffenoni (2019): Physically Consistent Numerical Solver for
 * 				Time-dependent Fokker-Planck Equations <i>Physical Review E</i> <b>99 (4)</b> 032117
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Ottinger, H. C. (1996): <i>Stochastic Processes in Polymeric Fluids</i> <b>Springer-Verlag</b>
 * 				Berlin-Heidelberg
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Fokker-Planck Equation
 * 				https://en.wikipedia.org/wiki/Fokker%E2%80%93Planck_equation
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/kolmogorov/README.md">Fokker Planck Kolmogorov Forward/Backward</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1FokkerPlanckCIR
	extends org.drip.dynamics.kolmogorov.R1FokkerPlanckCKLS
{

	/**
	 * R1FokkerPlanckCIR Constructor
	 *
	 * @param cklsParameters The CKLS Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1FokkerPlanckCIR (
		final org.drip.dynamics.meanreverting.CKLSParameters cklsParameters)
		throws java.lang.Exception
	{
		super (
			cklsParameters
		);
	}

	@Override public org.drip.function.definition.R1ToR1 steadyStatePDF()
	{
		return org.drip.function.r1tor1custom.CIRPDF.Standard (
			cklsParameters()
		);
	}

	@Override public org.drip.dynamics.process.R1ProbabilityDensityFunction deltaStartTemporalPDF (
		final double r0)
	{
		try
		{
			return new org.drip.dynamics.process.R1ProbabilityDensityFunctionCIR (
				r0,
				cklsParameters(),
				org.drip.specialfunction.bessel.ModifiedFirstFrobeniusSeriesEstimator.Standard (
					new org.drip.specialfunction.gamma.EulerIntegralSecondKind (
						null
					),
					20
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
