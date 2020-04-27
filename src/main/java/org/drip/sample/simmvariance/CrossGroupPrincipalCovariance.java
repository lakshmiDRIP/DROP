
package org.drip.sample.simmvariance;

import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.eigen.EigenComponent;
import org.drip.numerical.eigen.PowerIterationComponentExtractor;
import org.drip.numerical.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>CrossGroupPrincipalCovariance</i> demonstrates the Computation of the Cross Risk Group Principal
 * 	Component Co-variance using the Actual Risk Group Principal Component. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simmvariance/README.md">Position Bucket Co-variance - ISDA SIMM vs. FRTB SBA-C vs. Actual</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CrossGroupPrincipalCovariance
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[][] correlationMatrix =
		{
			{1.00, 0.99, 0.79, 0.67, 0.53, 0.42, 0.37, 0.30, 0.22, 0.18, 0.16, 0.12},
			{0.99, 1.00, 0.79, 0.67, 0.53, 0.42, 0.37, 0.30, 0.22, 0.18, 0.16, 0.12},
			{0.79, 0.79, 1.00, 0.85, 0.69, 0.57, 0.50, 0.42, 0.32, 0.25, 0.23, 0.20},
			{0.67, 0.67, 0.85, 1.00, 0.86, 0.76, 0.69, 0.59, 0.47, 0.40, 0.37, 0.32},
			{0.53, 0.53, 0.69, 0.86, 1.00, 0.93, 0.87, 0.77, 0.63, 0.57, 0.54, 0.50},
			{0.42, 0.42, 0.57, 0.76, 0.93, 1.00, 0.98, 0.90, 0.77, 0.70, 0.67, 0.63},
			{0.37, 0.37, 0.50, 0.69, 0.87, 0.98, 1.00, 0.96, 0.84, 0.78, 0.75, 0.71},
			{0.30, 0.30, 0.42, 0.59, 0.77, 0.90, 0.96, 1.00, 0.93, 0.89, 0.86, 0.82},
			{0.22, 0.22, 0.32, 0.47, 0.63, 0.77, 0.84, 0.93, 1.00, 0.98, 0.96, 0.94},
			{0.18, 0.18, 0.25, 0.40, 0.57, 0.70, 0.78, 0.89, 0.98, 1.00, 0.99, 0.98},
			{0.16, 0.16, 0.23, 0.37, 0.54, 0.67, 0.75, 0.86, 0.96, 0.99, 1.00, 0.99},
			{0.12, 0.12, 0.20, 0.32, 0.50, 0.63, 0.71, 0.82, 0.94, 0.98, 0.99, 1.00}
		};
		double crossBucketCorrelation = 0.27;

		System.out.println
			("\t||-----------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println
			("\t||                                                       TENOR CORRELATION MATRIX FOR GIRR                                                       |");

		System.out.println
			("\t||-----------------------------------------------------------------------------------------------------------------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t|| GIRR2.0",
			correlationMatrix
		);

		System.out.println
			("\t||-----------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println();

		PowerIterationComponentExtractor pice = new PowerIterationComponentExtractor (
			30,
			0.000001,
			false
		);

		EigenComponent principalComponent = pice.principalComponent (correlationMatrix);

		double[] rawEigenvector = principalComponent.eigenVector();

		double rawEigenvalue = principalComponent.eigenValue();

		double scaledEigenvalue = Math.sqrt (rawEigenvalue);

		double[] scaledEigenvector = new double[rawEigenvector.length];

		String rawEigenDump    = "\t||   RAW    || " +
			"[" + FormatUtil.FormatDouble (rawEigenvalue, 1, 4, 1.) + "] => ";

		String scaledEigenDump = "\t||  SCALED  || " +
			"[" + FormatUtil.FormatDouble (scaledEigenvalue, 1, 4, 1.) + "] => ";

		for (int i = 0; i < rawEigenvector.length; ++i)
		{
			rawEigenDump += FormatUtil.FormatDouble (rawEigenvector[i], 1, 4, 1.) + " | ";

			scaledEigenDump += FormatUtil.FormatDouble (
				scaledEigenvector[i] = scaledEigenvalue * rawEigenvector[i], 1, 4, 1.
			) + " | ";
		}

		System.out.println
			("\t||------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (rawEigenDump);

		System.out.println
			("\t||------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (scaledEigenDump);

		System.out.println
			("\t||------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println();

		double[][] unadjustedOffDiagonalBlockMatrix = Matrix.CrossProduct (
			scaledEigenvector,
			scaledEigenvector
		);

		System.out.println
			("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println
			("\t||                                                   GIRR 2.0 UNADJUSTED OFF-DIAGONAL COVARIANCE ENTRIES                                                    |");

		System.out.println
			("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t|| OFF-DIAGONAL UNADJ",
			unadjustedOffDiagonalBlockMatrix
		);

		System.out.println
			("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println();

		double[][] adjustedOffDiagonalBlockMatrix = Matrix.Scale2D (
			unadjustedOffDiagonalBlockMatrix,
			crossBucketCorrelation
		);

		System.out.println
			("\t||--------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println
			("\t||                                                   GIRR 2.0 ADJUSTED OFF-DIAGONAL COVARIANCE ENTRIES                                                    |");

		System.out.println
			("\t||--------------------------------------------------------------------------------------------------------------------------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t|| OFF-DIAGONAL ADJ",
			adjustedOffDiagonalBlockMatrix
		);

		System.out.println
			("\t||--------------------------------------------------------------------------------------------------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
