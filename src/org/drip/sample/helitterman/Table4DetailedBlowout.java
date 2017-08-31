
package org.drip.sample.helitterman;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.linearalgebra.Matrix;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * Table4DetailedBlowout replicates the detailed Steps involved in the Black-Litterman Model Process as
 *  illustrated in Table #4 the Following Paper:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *
 * @author Lakshmi Krishnamurthy
 */

public class Table4DetailedBlowout {

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		String[] astrInputSovereign = new String[] {
			"AUS",
			"CAD",
			"FRA",
			"GER",
			"JPN",
			"UK ",
			"USA"
		};

		double[][] aadblInputCorrelation = new double[][] {
			{1.000, 0.488, 0.478, 0.515, 0.439, 0.512, 0.491},
			{0.488, 1.000, 0.664, 0.655, 0.310, 0.608, 0.779},
			{0.478, 0.664, 1.000, 0.861, 0.355, 0.783, 0.668},
			{0.515, 0.655, 0.861, 1.000, 0.354, 0.777, 0.653},
			{0.439, 0.310, 0.355, 0.354, 1.000, 0.405, 0.306},
			{0.512, 0.608, 0.783, 0.777, 0.405, 1.000, 0.652},
			{0.491, 0.779, 0.668, 0.653, 0.306, 0.652, 1.000}
		};

		double[] adblInputVolatility = new double[] {
			0.160,
			0.203,
			0.248,
			0.271,
			0.210,
			0.200,
			0.187
		};

		double[] adblInputW = new double[] {
			0.016,
			0.022,
			0.052,
			0.055,
			0.116,
			0.124,
			0.615
		};

		double dblInputDELTA = 2.5;
		double dblInputTAU = 0.05;

		double[][] aadblInputP = new double[][] {
			{ 0.000,  0.000, -0.295,  1.000,  0.000, -0.705,  0.000}
		};

		double[][] aadblInputOmega = new double[][] {
			{0.021}
		};

		double[] adblInputQ = new double[] {
			0.05
		};

		int iNumSovereign = astrInputSovereign.length;
		double[][] aadblSIGMA = new double[iNumSovereign][iNumSovereign];

		for (int i = 0; i < iNumSovereign; ++i) {
			for (int j = 0; j < iNumSovereign; ++j)
				aadblSIGMA[i][j] = adblInputVolatility[i] * adblInputVolatility[j] * aadblInputCorrelation[i][j];
		}

		double[] adblPI = Matrix.Product (
			aadblSIGMA,
			adblInputW
		);

		for (int i = 0; i < iNumSovereign; ++i)
			adblPI[i] *= dblInputDELTA;

		System.out.println();

		for (int i = 0; i < iNumSovereign; ++i)
			System.out.println (
				"\t{PI}[" + astrInputSovereign[i] + "] =>" +
				FormatUtil.FormatDouble (adblPI[i], 1, 1, 100.) + "%"
			);

		System.out.println();

		double[] adblPdotPI = Matrix.Product (
			aadblInputP,
			adblPI
		);

		for (int i = 0; i < adblPdotPI.length; ++i)
			System.out.println (
				"\t{P.PI}[" + i + "] =>" +
				FormatUtil.FormatDouble (adblPdotPI[i], 1, 6, 1.)
			);

		System.out.println();

		double[] adblQMinus_PdotPI_ = new double[adblInputQ.length];

		for (int i = 0; i < adblInputQ.length; ++i)
			adblQMinus_PdotPI_[i] = adblInputQ[i] - adblPdotPI[i];

		for (int i = 0; i < adblQMinus_PdotPI_.length; ++i)
			System.out.println (
				"\t{Q-P.PI}[" + i + "] =>" +
				FormatUtil.FormatDouble (adblQMinus_PdotPI_[i], 1, 6, 1.)
			);

		System.out.println();

		double[][] aadblPTranspose = Matrix.Transpose (aadblInputP);

		double[][] aadblSIGMAdot_Ptranspose_ = Matrix.Product (
			aadblSIGMA,
			aadblPTranspose
		);

		for (int i = 0; i < aadblSIGMAdot_Ptranspose_.length; ++i)
			for (int j = 0; j < aadblSIGMAdot_Ptranspose_[0].length; ++j) {
				System.out.println (
					"\t{SIGMA.PTRANSPOSE}[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblSIGMAdot_Ptranspose_[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[][] aadblPdotSIGMAdot_PTranspose_ = Matrix.Product (
			aadblInputP,
			aadblSIGMAdot_Ptranspose_
		);

		for (int i = 0; i < aadblPdotSIGMAdot_PTranspose_.length; ++i)
			for (int j = 0; j < aadblPdotSIGMAdot_PTranspose_[0].length; ++j) {
				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE}[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblPdotSIGMAdot_PTranspose_[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[][] aadblPdotSIGMAdot_PTranspose_plusOMEGA = new double[aadblPdotSIGMAdot_PTranspose_.length][aadblPdotSIGMAdot_PTranspose_[0].length];

		for (int i = 0; i < aadblPdotSIGMAdot_PTranspose_plusOMEGA.length; ++i)
			for (int j = 0; j < aadblPdotSIGMAdot_PTranspose_plusOMEGA[0].length; ++j) {
				aadblPdotSIGMAdot_PTranspose_plusOMEGA[i][j] = aadblPdotSIGMAdot_PTranspose_[i][j] + aadblInputOmega[i][j];

				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE+OMEGA}[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblPdotSIGMAdot_PTranspose_plusOMEGA[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[][] aadbl$PdotSIGMAdot_PTranspose_plusOMEGA$inverse = Matrix.InvertUsingGaussianElimination (aadblPdotSIGMAdot_PTranspose_plusOMEGA);

		for (int i = 0; i < aadbl$PdotSIGMAdot_PTranspose_plusOMEGA$inverse.length; ++i)
			for (int j = 0; j < aadbl$PdotSIGMAdot_PTranspose_plusOMEGA$inverse[0].length; ++j) {
				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE+OMEGA}^(-1)[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadbl$PdotSIGMAdot_PTranspose_plusOMEGA$inverse[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[] aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__ = Matrix.Product (
			aadbl$PdotSIGMAdot_PTranspose_plusOMEGA$inverse,
			adblQMinus_PdotPI_
		);

		for (int i = 0; i < aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__.length; ++i)
			System.out.println (
				"\t{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{Q-P.PI}[" + i + "] =>" +
				FormatUtil.FormatDouble (aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__[i], 1, 6, 1.)
			);

		System.out.println();

		double[] aadblSIGMAdot$Ptranspose$__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__ = Matrix.Product (
			aadblSIGMAdot_Ptranspose_,
			aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__
		);

		for (int i = 0; i < aadblSIGMAdot$Ptranspose$__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__.length; ++i)
			System.out.println (
				"\t{SIGMA.PTRANSPOSE}{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{Q-P.PI}[" + i + "] =>" +
				FormatUtil.FormatDouble (aadblSIGMAdot$Ptranspose$__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__[i], 1, 6, 1.)
			);

		System.out.println();

		double[] adblPIHAT = new double[adblPI.length];

		for (int i = 0; i < adblPIHAT.length; ++i) {
			adblPIHAT[i] = adblPI[i] + aadblSIGMAdot$Ptranspose$__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dot_Qminus__PdotPI__[i];

			System.out.println (
				"\tPIHAT=PI+{SIGMA.PTRANSPOSE}{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{Q-P.PI}[" + i + "] =>" +
				FormatUtil.FormatDouble (adblPIHAT[i], 1, 1, 100.) + "%"
			);
		}

		System.out.println();

		double[][] aadblPdotSIGMA = Matrix.Product (
			aadblInputP,
			aadblSIGMA
		);

		for (int i = 0; i < aadblPdotSIGMA.length; ++i)
			for (int j = 0; j < aadblPdotSIGMA[0].length; ++j)
				System.out.println (
					"\tP.SIGMA[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblPdotSIGMA[i][j], 1, 6, 1.)
				);

		System.out.println();

		double[][] aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA = Matrix.Product (
			aadbl$PdotSIGMAdot_PTranspose_plusOMEGA$inverse,
			aadblPdotSIGMA
		);

		for (int i = 0; i < aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA.length; ++i)
			for (int j = 0; j < aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[0].length; ++j)
				System.out.println (
					"\t{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA}[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[i][j], 1, 6, 1.)
				);

		System.out.println();

		double[][] aadblSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA = Matrix.Product (
			aadblSIGMAdot_Ptranspose_,
			aadbl__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA
		);

		for (int i = 0; i < aadblSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA.length; ++i)
			for (int j = 0; j < aadblSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[0].length; ++j)
				System.out.println (
					"\t{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA}[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[i][j], 1, 6, 1.)
				);

		System.out.println();

		double[][] aadblSIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA = new double[iNumSovereign][iNumSovereign];

		for (int i = 0; i < aadblSIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA.length; ++i)
			for (int j = 0; j < aadblSIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[0].length; ++j) {
				aadblSIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[i][j] =
					aadblSIGMA[i][j] - aadblSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[i][j];

				System.out.println (
					"\tSIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA}[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblSIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[][] aadblTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$ =
			new double[iNumSovereign][iNumSovereign];

		for (int i = 0; i < aadblTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$.length; ++i)
			for (int j = 0; j < aadblTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[0].length; ++j) {
				aadblTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[i][j] =
					dblInputTAU * aadblSIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA[i][j];

				System.out.println (
					"\tTAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[][] aadblSIGMAplusTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$ =
			new double[iNumSovereign][iNumSovereign];

		for (int i = 0; i < aadblSIGMAplusTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$.length; ++i)
			for (int j = 0; j < aadblSIGMAplusTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[0].length; ++j) {
				aadblSIGMAplusTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[i][j] =
					aadblSIGMA[i][j] +
						aadblTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[i][j];

				System.out.println (
					"\tSIGMA+TAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblSIGMAplusTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[][] aadblSIGMAPInverse = Matrix.InvertUsingGaussianElimination (aadblSIGMAplusTAUdot$$SIGMAminusSIGMAdot_Ptranspose_dot__$PdotSIGMAdot_PTranspose_plusOMEGA$inverse__dotPdotSIGMA$$);

		for (int i = 0; i < aadblSIGMAPInverse.length; ++i)
			for (int j = 0; j < aadblSIGMAPInverse[0].length; ++j) {
				System.out.println (
					"\t[SIGMA+TAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})]^(-1)[" + i + "][" + j + "] =>" +
					FormatUtil.FormatDouble (aadblSIGMAPInverse[i][j], 1, 6, 1.)
				);
			}

		System.out.println();

		double[] adblSIGMAPInversedotPIHAT = Matrix.Product (
			aadblSIGMAPInverse,
			adblPIHAT
		);

		for (int i = 0; i < adblSIGMAPInversedotPIHAT.length; ++i) {
			adblSIGMAPInversedotPIHAT[i] /= dblInputDELTA;

			System.out.println (
				"\t[SIGMA+TAU.(SIGMA-{SIGMA.PTRANSPOSE}.{P.SIGMA.PTRANSPOSE+OMEGA}^(-1).{P.SIGMA})]^(-1).PIHAT[" + i + "] =>" +
				FormatUtil.FormatDouble (adblSIGMAPInversedotPIHAT[i], 1, 1, 100.) + "%"
			);
		}

		System.out.println ("\n\n\t|----------------||");

		System.out.println ("\t|  EQUILIBRIUM   ||");

		System.out.println ("\t|    RETURNS     ||");

		System.out.println ("\t|----------------||");

		for (int i = 0; i < adblPI.length; ++i) {
			System.out.println (
				"\t| [" + astrInputSovereign[i] + "] =>" +
				FormatUtil.FormatDouble (adblPI[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\n\n\t|----------------||");

		System.out.println ("\t| BLACK LITERMAN ||");

		System.out.println ("\t|    RETURNS     ||");

		System.out.println ("\t|----------------||");

		for (int i = 0; i < adblPIHAT.length; ++i) {
			System.out.println (
				"\t| [" + astrInputSovereign[i] + "] =>" +
				FormatUtil.FormatDouble (adblPIHAT[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|----------------||");

		System.out.println ("\t|----------------||");

		System.out.println ("\n\n\t|-----------------||");

		System.out.println ("\t|   EQUILIBRIUM   ||");

		System.out.println ("\t|   ALLOCATION    ||");

		System.out.println ("\t|-----------------||");

		for (int i = 0; i < adblInputW.length; ++i) {
			System.out.println (
				"\t| [" + astrInputSovereign[i] + "] => " +
				FormatUtil.FormatDouble (adblInputW[i] / (1. + dblInputTAU), 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-----------------||");

		System.out.println ("\n\n\t|-----------------||");

		System.out.println ("\t| BLACK LITERMAN  ||");

		System.out.println ("\t|    ALLOCATION   ||");

		System.out.println ("\t|-----------------||");

		for (int i = 0; i < adblSIGMAPInversedotPIHAT.length; ++i) {
			System.out.println (
				"\t| [" + astrInputSovereign[i] + "] => " +
				FormatUtil.FormatDouble (adblSIGMAPInversedotPIHAT[i], 2, 1, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-----------------||");

		System.out.println ("\n\n\t|------------------||");

		System.out.println ("\t|  BLACK LITERMAN  ||");

		System.out.println ("\t| ALLOCATION SHIFT ||");

		System.out.println ("\t|------------------||");

		for (int i = 0; i < adblSIGMAPInversedotPIHAT.length; ++i) {
			System.out.println (
				"\t| [" + astrInputSovereign[i] + "] => " +
				FormatUtil.FormatDouble (adblSIGMAPInversedotPIHAT[i] - (adblInputW[i] / (1. + dblInputTAU)), 2, 1, 100.) + "%  ||"
			);
		}

		System.out.println ("\t|------------------||");
	}
}
