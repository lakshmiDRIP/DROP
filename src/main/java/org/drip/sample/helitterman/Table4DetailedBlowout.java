
package org.drip.sample.helitterman;

import org.drip.numerical.linearalgebra.MatrixUtil;
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
 * <i>Table4DetailedBlowout</i> replicates the detailed Steps involved in the Black-Litterman Model Process
 * as illustrated in Table #4 the Following Paper:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios,
 *  			Goldman Sachs Asset Management
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/helitterman/README.md">He Litterman (1999) Projection Loadings</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Table4DetailedBlowout
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] sovereignArray = new String[]
		{
			"AUS",
			"CAD",
			"FRA",
			"GER",
			"JPN",
			"UK ",
			"USA"
		};
		double[][] inputCorrelationMatrix = new double[][]
		{
			{1.000, 0.488, 0.478, 0.515, 0.439, 0.512, 0.491},
			{0.488, 1.000, 0.664, 0.655, 0.310, 0.608, 0.779},
			{0.478, 0.664, 1.000, 0.861, 0.355, 0.783, 0.668},
			{0.515, 0.655, 0.861, 1.000, 0.354, 0.777, 0.653},
			{0.439, 0.310, 0.355, 0.354, 1.000, 0.405, 0.306},
			{0.512, 0.608, 0.783, 0.777, 0.405, 1.000, 0.652},
			{0.491, 0.779, 0.668, 0.653, 0.306, 0.652, 1.000}
		};
		double[] inputVolatilityArray = new double[]
		{
			0.160,
			0.203,
			0.248,
			0.271,
			0.210,
			0.200,
			0.187
		};
		double[] inputWArray = new double[]
		{
			0.016,
			0.022,
			0.052,
			0.055,
			0.116,
			0.124,
			0.615
		};
		double inputTAU = 0.05;
		double inputDELTA = 2.5;
		double[][] inputPMatrix = new double[][]
		{
			{ 0.000,  0.000, -0.295,  1.000,  0.000, -0.705,  0.000}
		};
		double[][] inputOmegaMatrix = new double[][]
		{
			{0.021}
		};
		double[] inputQArray = new double[]
		{
			0.05
		};

		int sovereignCount = sovereignArray.length;
		double[][] sigmaMatrix = new double[sovereignCount][sovereignCount];

		for (int sovereignIndexI = 0; sovereignIndexI < sovereignCount; ++sovereignIndexI)
		{
			for (int sovereignIndexJ = 0; sovereignIndexJ < sovereignCount; ++sovereignIndexJ)
			{
				sigmaMatrix[sovereignIndexI][sovereignIndexJ] = inputVolatilityArray[sovereignIndexI] *
					inputVolatilityArray[sovereignIndexJ] *
					inputCorrelationMatrix[sovereignIndexI][sovereignIndexJ];
			}
		}

		double[] piArray = MatrixUtil.Product (
			sigmaMatrix,
			inputWArray
		);

		for (int sovereignIndex = 0; sovereignIndex < sovereignCount; ++sovereignIndex)
		{
			piArray[sovereignIndex] *= inputDELTA;
		}

		System.out.println();

		for (int sovereignIndex = 0; sovereignIndex < sovereignCount; ++sovereignIndex)
		{
			System.out.println (
				"\t{PI}[" + sovereignArray[sovereignIndex] + "] =>" +
				FormatUtil.FormatDouble (piArray[sovereignIndex], 1, 1, 100.) + "%"
			);
		}

		System.out.println();

		double[] pDotPIArray = MatrixUtil.Product (
			inputPMatrix,
			piArray
		);

		for (int pDotPIIndex = 0; pDotPIIndex < pDotPIArray.length; ++pDotPIIndex)
		{
			System.out.println (
				"\t{P.PI}[" + pDotPIIndex + "] =>" +
				FormatUtil.FormatDouble (pDotPIArray[pDotPIIndex], 1, 6, 1.)
			);
		}

		System.out.println();

		double[] qMinus_PdotPI_Array = new double[inputQArray.length];

		for (int qIndex = 0; qIndex < inputQArray.length; ++qIndex)
		{
			qMinus_PdotPI_Array[qIndex] = inputQArray[qIndex] - pDotPIArray[qIndex];
		}

		for (int qMinus_PdotPI_ArrayIndex = 0;
			qMinus_PdotPI_ArrayIndex < qMinus_PdotPI_Array.length;
			++qMinus_PdotPI_ArrayIndex)
		{
			System.out.println (
				"\t{Q-P.PI}[" + qMinus_PdotPI_ArrayIndex + "] =>" +
				FormatUtil.FormatDouble (qMinus_PdotPI_Array[qMinus_PdotPI_ArrayIndex], 1, 6, 1.)
			);
		}

		System.out.println();

		double[][] pTransposeMatrix = MatrixUtil.Transpose (
			inputPMatrix
		);

		double[][] sigmaDot_PTranspose_Matrix = MatrixUtil.Product (
			sigmaMatrix,
			pTransposeMatrix
		);

		for (int sigmaDot_PTranspose_MatrixRowIndex = 0;
			sigmaDot_PTranspose_MatrixRowIndex < sigmaDot_PTranspose_Matrix.length;
			++sigmaDot_PTranspose_MatrixRowIndex)
		{
			for (int sigmaDot_PTranspose_MatrixColumnIndex = 0;
				sigmaDot_PTranspose_MatrixColumnIndex < sigmaDot_PTranspose_Matrix[0].length;
				++sigmaDot_PTranspose_MatrixColumnIndex)
			{
				System.out.println (
					"\t{SIGMA.PTRANSPOSE}[" + sigmaDot_PTranspose_MatrixRowIndex + "][" + sigmaDot_PTranspose_MatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (sigmaDot_PTranspose_Matrix[sigmaDot_PTranspose_MatrixRowIndex][sigmaDot_PTranspose_MatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] pDotSIGMAdot_PTranspose_Matrix = MatrixUtil.Product (
			inputPMatrix,
			sigmaDot_PTranspose_Matrix
		);

		for (int pDotSIGMAdot_PTranspose_MatrixRowIndex = 0;
			pDotSIGMAdot_PTranspose_MatrixRowIndex < pDotSIGMAdot_PTranspose_Matrix.length;
			++pDotSIGMAdot_PTranspose_MatrixRowIndex)
		{
			for (int pDotSIGMAdot_PTranspose_MatrixColumnIndex = 0;
				pDotSIGMAdot_PTranspose_MatrixColumnIndex < pDotSIGMAdot_PTranspose_Matrix[0].length;
				++pDotSIGMAdot_PTranspose_MatrixColumnIndex)
			{
				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE}[" + pDotSIGMAdot_PTranspose_MatrixRowIndex + "][" + pDotSIGMAdot_PTranspose_MatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (pDotSIGMAdot_PTranspose_Matrix[pDotSIGMAdot_PTranspose_MatrixRowIndex][pDotSIGMAdot_PTranspose_MatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] pDotSIGMAdot_PTranspose_PlusOMEGAMatrix =
			new double[pDotSIGMAdot_PTranspose_Matrix.length][pDotSIGMAdot_PTranspose_Matrix[0].length];

		for (int pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex = 0;
			pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex < pDotSIGMAdot_PTranspose_PlusOMEGAMatrix.length;
			++pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex)
		{
			for (int pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex = 0;
				pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex <
					pDotSIGMAdot_PTranspose_PlusOMEGAMatrix[0].length;
				++pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex)
			{
				pDotSIGMAdot_PTranspose_PlusOMEGAMatrix[pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex][pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex] =
					pDotSIGMAdot_PTranspose_Matrix[pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex][pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex] +
					inputOmegaMatrix[pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex][pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex];

				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE+OMEGA}[" + pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex + "][" + pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (
						pDotSIGMAdot_PTranspose_PlusOMEGAMatrix[pDotSIGMAdot_PTranspose_PlusOMEGAMatrixRowIndex][pDotSIGMAdot_PTranspose_PlusOMEGAMatrixColumnIndex],
						1, 6, 1.
					)
				);
			}
		}

		System.out.println();

		double[][] $PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrix = MatrixUtil.InvertUsingGaussianElimination (
			pDotSIGMAdot_PTranspose_PlusOMEGAMatrix
		);

		for (int $PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixRowIndex = 0;
			$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixRowIndex <
				$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrix.length;
			++$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixRowIndex)
		{
			for (int $PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixColumnIndex = 0;
				$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixColumnIndex <
					$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrix[0].length;
				++$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixColumnIndex)
			{
				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE+OMEGA}^(-1)[" + $PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixRowIndex + "][" + $PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble ($PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrix[$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixRowIndex][$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[] __$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__Array = MatrixUtil.Product (
			$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrix,
			qMinus_PdotPI_Array
		);

		for (int __$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__ArrayIndex = 0;
			__$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__ArrayIndex <
				__$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__Array.length;
			++__$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__ArrayIndex)
		{
			System.out.println (
				"\t{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{Q-P.PI}[" + __$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__ArrayIndex + "] =>" +
				FormatUtil.FormatDouble (__$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__Array[__$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__ArrayIndex], 1, 6, 1.)
			);
		}

		System.out.println();

		double[] sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__Array
			= MatrixUtil.Product (
				sigmaDot_PTranspose_Matrix,
				__$PDotSIGMADot_PTranspose_plusOMEGA$Inverse__Dot_QMinus__PDotPI__Array
			);

		for (int sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__ArrayIndex = 0;
			sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__ArrayIndex <
				sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__Array.length;
			++sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__ArrayIndex)
		{
			System.out.println (
				"\t{SIGMA.PTRANSPOSE}{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{Q-P.PI}[" + sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__ArrayIndex + "] =>" +
				FormatUtil.FormatDouble (sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__Array[sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__ArrayIndex], 1, 6, 1.)
			);
		}

		System.out.println();

		double[] piHatArray = new double[piArray.length];

		for (int piHatArrayIndex = 0; piHatArrayIndex < piHatArray.length; ++piHatArrayIndex)
		{
			piHatArray[piHatArrayIndex] = piArray[piHatArrayIndex] +
				sigmaDot$PTranspose$__$PDotSIGMADot_PTranspose_PlusOmega$Inverse__Dot_QMinus__PDotPI__Array[piHatArrayIndex];

			System.out.println (
				"\tPIHAT=PI+{SIGMA.PTRANSPOSE}{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{Q-P.PI}[" + piHatArrayIndex + "] =>" +
				FormatUtil.FormatDouble (piHatArray[piHatArrayIndex], 1, 1, 100.) + "%"
			);
		}

		System.out.println();

		double[][] pDotSigmaMatrix = MatrixUtil.Product (
			inputPMatrix,
			sigmaMatrix
		);

		for (int pDotSigmaMatrixRowIndex = 0;
			pDotSigmaMatrixRowIndex < pDotSigmaMatrix.length;
			++pDotSigmaMatrixRowIndex)
		{
			for (int pDotSigmaMatrixColumnIndex = 0;
				pDotSigmaMatrixColumnIndex < pDotSigmaMatrix[0].length;
				++pDotSigmaMatrixColumnIndex)
			{
				System.out.println (
					"\tP.SIGMA[" + pDotSigmaMatrixRowIndex + "][" + pDotSigmaMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (pDotSigmaMatrix[pDotSigmaMatrixRowIndex][pDotSigmaMatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] __$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix = MatrixUtil.Product (
			$PDotSIGMAdot_PTranspose_PlusOMEGA$InverseMatrix,
			pDotSigmaMatrix
		);

		for (int __$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex = 0;
			__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex <
				__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix.length;
			++__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex)
		{
			for (int __$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex = 0;
				__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex <
					__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix[0].length;
				++__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex)
			{
				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA}[" + __$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex + "][" + __$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix[__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex][__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrix =
			MatrixUtil.Product (
				sigmaDot_PTranspose_Matrix,
				__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix
			);

		for (int sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixRowIndex = 0;
			sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixRowIndex <
				sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrix.length;
			++sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixRowIndex)
		{
			for (int sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixColumnIndex = 0;
				sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixColumnIndex <
					sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrix[0].length;
				++sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixColumnIndex)
			{
				System.out.println (
					"\t{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA}[" + sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixRowIndex + "][" + sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (
						sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrix[sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixRowIndex][sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrixColumnIndex],
						1, 6, 1.
					)
				);
			}
		}

		System.out.println();

		double[][]
			sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix
				= new double[sovereignCount][sovereignCount];

		for (int sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex = 0;
			sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex <
				sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix.length;
			++sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex)
		{
			for (int sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex = 0;
				sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex <
					sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix[0].length;
				++sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex)
			{
				sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix[sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex][sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex] =
					sigmaMatrix[sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex][sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex] -
					sigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmegaPDotSigmaMatrix[sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex][sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex];

				System.out.println (
					"\tSIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA}[" + sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex + "][" + sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix[sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixRowIndex][sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix =
			new double[sovereignCount][sovereignCount];

		for (int tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex = 0;
			tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex <
				tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix.length;
			++tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex)
		{
			for (int tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex = 0;
				tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex <
					tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[0].length;
				++tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex)
			{
				tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex] =
					inputTAU * sigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigmaMatrix[tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex];

				System.out.println (
					"\tTAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})[" + tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex + "][" + tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix =
			new double[sovereignCount][sovereignCount];

		for (int sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex = 0;
				sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex <
				sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix.length;
			++sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex)
		{
			for (int sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex = 0;
				sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex <
					sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[0].length;
				++sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex)
			{
				sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex] =
					sigmaMatrix[sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex] +
					tauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex];

				System.out.println (
					"\tSIGMA+TAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})[" + sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex + "][" + sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix[sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixRowIndex][sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$MatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[][] sigmaPInverseMatrix = MatrixUtil.InvertUsingGaussianElimination (
			sigmaPlusTauDot$$SigmaMinusSigmaDot_PTranspose_Dot__$PDotSigmaDot_PTranspose_PlusOmega$Inverse__DotPDotSigma$$Matrix
		);

		for (int sigmaPInverseMatrixRowIndex = 0;
			sigmaPInverseMatrixRowIndex< sigmaPInverseMatrix.length;
			++sigmaPInverseMatrixRowIndex)
		{
			for (int sigmaPInverseMatrixColumnIndex = 0;
				sigmaPInverseMatrixColumnIndex < sigmaPInverseMatrix[0].length;
				++sigmaPInverseMatrixColumnIndex)
			{
				System.out.println (
					"\t[SIGMA+TAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})]^(-1)[" + sigmaPInverseMatrixRowIndex + "][" + sigmaPInverseMatrixColumnIndex + "] =>" +
					FormatUtil.FormatDouble (sigmaPInverseMatrix[sigmaPInverseMatrixRowIndex][sigmaPInverseMatrixColumnIndex], 1, 6, 1.)
				);
			}
		}

		System.out.println();

		double[] sigmaPInverseDotPiHatArray = MatrixUtil.Product (
			sigmaPInverseMatrix,
			piHatArray
		);

		for (int sigmaPInverseDotPiHatArrayIndex = 0;
			sigmaPInverseDotPiHatArrayIndex < sigmaPInverseDotPiHatArray.length;
			++sigmaPInverseDotPiHatArrayIndex)
		{
			sigmaPInverseDotPiHatArray[sigmaPInverseDotPiHatArrayIndex] /= inputDELTA;

			System.out.println (
				"\t[SIGMA+TAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})]^(-1).PIHAT[" + sigmaPInverseDotPiHatArrayIndex + "] =>" +
				FormatUtil.FormatDouble (sigmaPInverseDotPiHatArray[sigmaPInverseDotPiHatArrayIndex], 1, 1, 100.) + "%"
			);
		}

		System.out.println ("\n\n\t|----------------||");

		System.out.println ("\t|  EQUILIBRIUM   ||");

		System.out.println ("\t|    RETURNS     ||");

		System.out.println ("\t|----------------||");

		for (int piArrayIndex = 0; piArrayIndex < piArray.length; ++piArrayIndex)
		{
			System.out.println (
				"\t| [" + sovereignArray[piArrayIndex] + "] =>" +
				FormatUtil.FormatDouble (piArray[piArrayIndex], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\n\n\t|----------------||");

		System.out.println ("\t| BLACK LITERMAN ||");

		System.out.println ("\t|    RETURNS     ||");

		System.out.println ("\t|----------------||");

		for (int piArrayIndex = 0; piArrayIndex < piHatArray.length; ++piArrayIndex)
		{
			System.out.println (
				"\t| [" + sovereignArray[piArrayIndex] + "] =>" +
				FormatUtil.FormatDouble (piHatArray[piArrayIndex], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------||");

		System.out.println ("\t|----------------||");

		System.out.println ("\n\n\t|-----------------||");

		System.out.println ("\t|   EQUILIBRIUM   ||");

		System.out.println ("\t|   ALLOCATION    ||");

		System.out.println ("\t|-----------------||");

		for (int inputWArrayIndex = 0; inputWArrayIndex < inputWArray.length; ++inputWArrayIndex)
		{
			System.out.println (
				"\t| [" + sovereignArray[inputWArrayIndex] + "] => " +
				FormatUtil.FormatDouble (inputWArray[inputWArrayIndex] / (1. + inputTAU), 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-----------------||");

		System.out.println ("\n\n\t|-----------------||");

		System.out.println ("\t| BLACK LITERMAN  ||");

		System.out.println ("\t|    ALLOCATION   ||");

		System.out.println ("\t|-----------------||");

		for (int sigmaPInverseDotPiHatArrayIndex = 0;
			sigmaPInverseDotPiHatArrayIndex < sigmaPInverseDotPiHatArray.length;
			++sigmaPInverseDotPiHatArrayIndex)
		{
			System.out.println (
				"\t| [" + sovereignArray[sigmaPInverseDotPiHatArrayIndex] + "] => " +
				FormatUtil.FormatDouble (sigmaPInverseDotPiHatArray[sigmaPInverseDotPiHatArrayIndex], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-----------------||");

		System.out.println ("\n\n\t|------------------||");

		System.out.println ("\t|  BLACK LITERMAN  ||");

		System.out.println ("\t| ALLOCATION SHIFT ||");

		System.out.println ("\t|------------------||");

		for (int sigmaPInverseDotPiHatArrayIndex = 0;
			sigmaPInverseDotPiHatArrayIndex < sigmaPInverseDotPiHatArray.length;
			++sigmaPInverseDotPiHatArrayIndex)
		{
			System.out.println (
				"\t| [" + sovereignArray[sigmaPInverseDotPiHatArrayIndex] + "] => " +
				FormatUtil.FormatDouble (sigmaPInverseDotPiHatArray[sigmaPInverseDotPiHatArrayIndex] - (inputWArray[sigmaPInverseDotPiHatArrayIndex] / (1. + inputTAU)), 2, 1, 100.) + "%  ||"
			);
		}

		System.out.println ("\t|------------------||");

		EnvManager.TerminateEnv();
	}
}
