
package org.drip.sample.optimizer;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.LagrangianMultivariate;
import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>NSphereSurfaceExtremization</i> computes the Equality-Constrained Extrema of the Specified Function
 * 	along the Surface of an N-Sphere using Lagrange Multipliers.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/optimizer/README.md">Lagrangian/KKT Necessary Sufficient Conditions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NSphereSurfaceExtremization
{

	private static final void Solve (
		final NewtonFixedPointFinder newtonFixedPointFinder,
		final double[] startingVariateArray)
		throws Exception
	{
		System.out.println ("\n\t|------------------------------------||");

		String strDump = "\t| STARTER: [";

		strDump += FormatUtil.FormatDouble (startingVariateArray[0], 1, 4, 1.) + ",";

		strDump += FormatUtil.FormatDouble (startingVariateArray[1], 1, 4, 1.) + ",";

		strDump += FormatUtil.FormatDouble (startingVariateArray[2], 1, 4, 1.);

		System.out.println (strDump + "] ||");

		System.out.println ("\t|------------------------------------||");

		double[] variateArray = newtonFixedPointFinder.convergeVariate (
			new VariateInequalityConstraintMultiplier (
				false,
				startingVariateArray,
				null
			)
		).variateArray();

		System.out.println (
			"\t| Optimal X      : " + FormatUtil.FormatDouble (variateArray[0], 1, 4, 1.) + "           ||"
		);

		System.out.println (
			"\t| Optimal Y      : " + FormatUtil.FormatDouble (variateArray[1], 1, 4, 1.) + "           ||"
		);

		System.out.println (
			"\t| Optimal Lambda : " + FormatUtil.FormatDouble (variateArray[2], 1, 4, 1.) + "           ||"
		);

		System.out.println ("\t|------------------------------------||");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		RdToR1 variateSumObjectiveFunction = new RdToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] * variateArray[0] * variateArray[1];
			}

			@Override public int dimension()
			{
				return 2;
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				double[] jacobian = new double[2];
				jacobian[0] = 2. * variateArray[0] * variateArray[1];
				jacobian[1] = variateArray[0] * variateArray[0];
				return jacobian;
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				double[][] hessian = new double[2][2];
				hessian[0][0] = 2. * variateArray[1];
				hessian[0][1] = 2. * variateArray[0];
				hessian[1][0] = 2. * variateArray[0];
				hessian[1][1] = 0.;
				return hessian;
			}
		};

		RdToR1 sphereSurfaceConstraintFunction = new RdToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] * variateArray[0] + variateArray[1] * variateArray[1] - 3.;
			}

			@Override public int dimension()
			{
				return 2;
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				double[] jacobian = new double[2];
				jacobian[0] = 2. * variateArray[0];
				jacobian[1] = 2. * variateArray[1];
				return jacobian;
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				double[][] hessian = new double[2][2];
				hessian[0][0] = 2.;
				hessian[0][1] = 0.;
				hessian[1][0] = 0.;
				hessian[1][1] = 2.;
				return hessian;
			}
		};

		NewtonFixedPointFinder newtonFixedPointFinder = new NewtonFixedPointFinder (
			new LagrangianMultivariate (
				variateSumObjectiveFunction,
				new RdToR1[]
				{
					sphereSurfaceConstraintFunction
				}
			),
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			),
			ConvergenceControl.Standard()
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				2.,
				1.,
				1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				-2.,
				 1.,
				 1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[] {
				 2.,
				-1.,
				 1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				-2.,
				-1.,
				 1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				0.,
				1.,
				0.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				 0.,
				-1.,
				 0.
			}
		);

		EnvManager.TerminateEnv();
	}
}
