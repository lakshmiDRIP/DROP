
package org.drip.sample.fdmimplicit;

import org.drip.fdm.definition.EvolutionGrid1D;
import org.drip.fdm.definition.R1EvolutionSnapshot;
import org.drip.fdm.definition.R1StateResponseSnapshotDiagnostics;
import org.drip.fdm.implicit.CrankNicolsonDiffusion1D;
import org.drip.function.definition.RdToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>CrankNicolsonDiffusionEvolver1D</i> illustrates the construction and usage the Crank-Nicolson
 * 	Discretized State-Space Evolution Scheme for 1D Diffusion. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i>
 * 				<b>SIAM</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Cebeci, T. (2002): <i>Convective Heat Transfer</i> <b>Horizon Publishing</b> Hammond, IN
 * 		</li>
 * 		<li>
 * 			Crank, J., and P. Nicolson (1947): A Practical Method for Numerical Evaluation of Solutions of
 * 				Partial Differential Equations of the Heat Conduction Type <i>Proceedings of the Cambridge
 * 				Philosophical Society</i> <b>43 (1)</b> 50-67
 * 		</li>
 * 		<li>
 * 			Thomas, J. W. (1995): <i>Numerical Partial Differential Equations: Finite Difference Methods</i>
 * 				<b>Springer-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Alternating-direction implicit method
 * 				https://en.wikipedia.org/wiki/Alternating-direction_implicit_method
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Crank–Nicolson method
 * 				https://en.wikipedia.org/wiki/Crank%E2%80%93Nicolson_method
 * 		</li>
 * 	</ul>
 * 
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fdmimplicit/README.md">Multi-dimensional FDM Implicit Evolution Schemes</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CrankNicolsonDiffusionEvolver1D
{

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
		EnvManager.InitEnv ("");

		double diffusionCoefficient = 0.5;

		double[] timeArray = new double[] {
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
			5.0
		};

		double[] factorPredictorArray = new double[] {
			0.,
			1.,
			2.,
			3.,
			4.,
			5.,
			6.,
			7.,
			8.,
			9.
		};

		double[] startingStateResponseArray = new double[] {
			1.,
			0.,
			0.,
			0.,
			0.,
			0.,
			0.,
			0.,
			0.,
			0.
		};

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                                         INPUT PARAMETERS                                         ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t| Diffusion Coefficient         =>" +
				FormatUtil.FormatDouble (diffusionCoefficient, 1, 2, 1.)
		);

		System.out.println (
			"\t| Time Node Array               =>" + NumberUtil.ArrayRow (timeArray, 1, 1, false)
		);

		System.out.println (
			"\t| Factor Predictor Array        =>" + NumberUtil.ArrayRow (
				factorPredictorArray,
				1,
				1,
				false
			)
		);

		System.out.println (
			"\t| Starting State Response Array =>" + NumberUtil.ArrayRow (
				startingStateResponseArray,
				1,
				1,
				false
			)
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println();

		R1EvolutionSnapshot r1EvolutionSnapshot = new CrankNicolsonDiffusion1D (
			new EvolutionGrid1D (timeArray, factorPredictorArray),
			new RdToR1 (null)
			{
				@Override public int dimension()
				{
					return 2;
				}

				@Override public double evaluate (
					double[] variateArray)
					throws Exception
				{
					return diffusionCoefficient;
				}
			},
			true
		).evolve (startingStateResponseArray);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                    CRANK-NICOLSON 1D DIFFUSION REALIZED STATE RESPONSE ARRAY                     ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|  Columns: L -> R:                                                                                ||"
		);

		System.out.println (
			"\t|    - Time Node Value                                                                             ||"
		);

		System.out.println (
			"\t|    - <<State Response Realizations for the Factors Span>>                                        ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		for (double time : r1EvolutionSnapshot.timeStateResponseMap().keySet()) {
			R1StateResponseSnapshotDiagnostics r1StateResponseSnapshotDiagnostics =
				(R1StateResponseSnapshotDiagnostics) (r1EvolutionSnapshot.timeStateResponseMap().get (time));

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (time, 1, 1, 1.) + " => " + NumberUtil.ArrayRow (
					r1StateResponseSnapshotDiagnostics.realizationArray(),
					1,
					4,
					false
				) + " ||"
			);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println();

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                               VON-NEUMANN STABILITY METRIC ARRAY                                 ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|  Columns: L -> R:                                                                                ||"
		);

		System.out.println (
			"\t|    - Time Node Value                                                                             ||"
		);

		System.out.println (
			"\t|    - <<von-Neumann Stability Metric Realizations for the Factors Span>>                          ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		for (double time : r1EvolutionSnapshot.timeStateResponseMap().keySet()) {
			R1StateResponseSnapshotDiagnostics r1StateResponseSnapshotDiagnostics =
				(R1StateResponseSnapshotDiagnostics) (r1EvolutionSnapshot.timeStateResponseMap().get (time));

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (time, 1, 1, 1.) + " => " + NumberUtil.ArrayRow (
					r1StateResponseSnapshotDiagnostics.vonNeumannStabilityMetricArray(),
					1,
					4,
					false
				) + " ||"
			);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println();

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                               WEIGHTED NODE VALUE CONSTRAINT ARRAY                               ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|  Columns: L -> R:                                                                                ||"
		);

		System.out.println (
			"\t|    - Time Node Value                                                                             ||"
		);

		System.out.println (
			"\t|    - <<Weighted Node Value Constraint for the Factors Span>>                                     ||"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		for (double time : r1EvolutionSnapshot.timeStateResponseMap().keySet()) {
			R1StateResponseSnapshotDiagnostics r1StateResponseSnapshotDiagnostics =
				(R1StateResponseSnapshotDiagnostics) (r1EvolutionSnapshot.timeStateResponseMap().get (time));

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (time, 1, 1, 1.) + " => " + NumberUtil.ArrayRow (
					r1StateResponseSnapshotDiagnostics.weightedNodeValueConstraintArray(),
					1,
					4,
					false
				) + " ||"
			);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println();

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|                                STATE RESPONSE TIME SHIFT JACOBIAN                                  ||"
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|  Columns: L -> R:                                                                                  ||"
		);

		System.out.println (
			"\t|    - Time Node Value                                                                               ||"
		);

		System.out.println (
			"\t|    - <<State Response Time Shift Jacobian Matrix for the Factors Span>>                            ||"
		);

		for (double time : r1EvolutionSnapshot.timeStateResponseMap().keySet()) {
			System.out.println (
				"\t|----------------------------------------------------------------------------------------------------||"
			);

			R1StateResponseSnapshotDiagnostics r1StateResponseSnapshotDiagnostics =
				(R1StateResponseSnapshotDiagnostics) (r1EvolutionSnapshot.timeStateResponseMap().get (time));

			double[][] timeShiftJacobian = r1StateResponseSnapshotDiagnostics.timeShiftJacobian();

			for (int i = 0; i < timeShiftJacobian.length; ++i) {
				System.out.println (
					"\t| " + FormatUtil.FormatDouble (time, 1, 1, 1.) + " => [" + NumberUtil.ArrayRow (
						timeShiftJacobian[i],
						1,
						4,
						false
					) + "] ||"
				);
			}
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------------------------||"
		);

		EnvManager.TerminateEnv();
	}
}
