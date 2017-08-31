
package org.drip.sample.classifier;

import org.drip.learning.rxtor1.*;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.functional.FlatMultivariateRandom;
import org.drip.sequence.metrics.SingleSequenceAgnosticMetrics;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * BinaryClassifierSupremumBound demonstrates the Computation of the Probabilistic Bounds for the Supremum
 * 	among the Class of Binary Classifier Functions for an Empirical Sample from its Population Mean using
 *  Variants of the Efron-Stein Methodology.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BinaryClassifierSupremumBound {

	private static final double[] EmpiricalOutcome (
		final int iNumOutcome)
		throws Exception
	{
		double[] adblEmpiricalOutcome = new double[iNumOutcome];

		for (int i = 0; i < iNumOutcome; ++i)
			adblEmpiricalOutcome[i] = Math.random() + 0.5;

		return adblEmpiricalOutcome;
	}

	private static final SingleSequenceAgnosticMetrics[] IIDDraw (
		final UnivariateSequenceGenerator rsg,
		final int iNumSample)
		throws Exception
	{
		SingleSequenceAgnosticMetrics[] aSSAM = new SingleSequenceAgnosticMetrics[iNumSample];

		for (int i = 0; i < iNumSample; ++i)
			aSSAM[i] = rsg.sequence (iNumSample, null);

		return aSSAM;
	}

	private static final EmpiricalPenaltySupremumEstimator EmpiricalLossSupremumFunction (
		final double[] asEmpiricalOutcome)
		throws Exception
	{
		// AbstractBinaryClassifier[] aClassifier = null;

		return null;

		/* return new EmpiricalLossSupremum (
			new GeneralizedClassifierFunctionClass (
				aClassifier,
				new ExpectedSupremumLossAsymptote (
					0.01,
					-1.5
				)
			),
			asEmpiricalOutcome
		); */
	}

	private static final void MartingaleDifferencesRun (
		final Binary bsg,
		final double[] adblEmpiricalOutcome,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (adblEmpiricalOutcome.length, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			EmpiricalPenaltySupremumMetrics eslm = new EmpiricalPenaltySupremumMetrics (
				EmpiricalLossSupremumFunction (
					adblEmpiricalOutcome
				),
				aSSAM,
				null
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (eslm.martingaleVarianceUpperBound(), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void GhostVariateVarianceRun (
		final Binary bsg,
		final double[] adblEmpiricalOutcome,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (adblEmpiricalOutcome.length, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			EmpiricalPenaltySupremumMetrics eslm = new EmpiricalPenaltySupremumMetrics (
				EmpiricalLossSupremumFunction (
					adblEmpiricalOutcome
				),
				aSSAM,
				null
			);

			SingleSequenceAgnosticMetrics[] aSSAMGhost = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (eslm.ghostVarianceUpperBound (aSSAMGhost), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void EfronSteinSteeleRun (
		final Binary bsg,
		final double[] adblEmpiricalOutcome,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (adblEmpiricalOutcome.length, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			EmpiricalPenaltySupremumMetrics eslm = new EmpiricalPenaltySupremumMetrics (
				EmpiricalLossSupremumFunction (
					adblEmpiricalOutcome
				),
				aSSAM,
				null
			);

			SingleSequenceAgnosticMetrics[] aSSAMGhost = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (eslm.efronSteinSteeleBound (aSSAMGhost), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void PivotDifferencesRun (
		final Binary bsg,
		final double[] adblEmpiricalOutcome,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (adblEmpiricalOutcome.length, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			EmpiricalPenaltySupremumMetrics eslm = new EmpiricalPenaltySupremumMetrics (
				EmpiricalLossSupremumFunction (
					adblEmpiricalOutcome
				),
				aSSAM,
				null
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (eslm.pivotVarianceUpperBound (new FlatMultivariateRandom (0.)), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void LugosiVarianceRun (
		final Binary bsg,
		final double[] adblEmpiricalOutcome,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (adblEmpiricalOutcome.length, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				bsg,
				adblEmpiricalOutcome.length
			);

			EmpiricalPenaltySupremumMetrics eslm = new EmpiricalPenaltySupremumMetrics (
				EmpiricalLossSupremumFunction (
					adblEmpiricalOutcome
				),
				aSSAM,
				null
			);

			SingleSequenceAgnosticMetrics[] aSSAMGhost = IIDDraw (
				bsg,
				1
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (eslm.lugosiVarianceBound (aSSAMGhost[0].sequence()), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumSet = 5;

		int[] aiSampleSize = new int[] {
			3, 10, 25, 50
		};

		Binary bin = new Binary (0.7);

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|  Martingale Differences Variance Upper Bound  |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			MartingaleDifferencesRun (
				bin,
				EmpiricalOutcome (iSampleSize),
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|   Symmetrized Variate Variance Upper Bound    |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			GhostVariateVarianceRun (
				bin,
				EmpiricalOutcome (iSampleSize),
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		aiSampleSize = new int[] {
			3, 10, 25, 50, 75, 99
		};

		System.out.println ("\t|    Efron-Stein-Steele Variance Upper Bound    |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			EfronSteinSteeleRun (
				bin,
				EmpiricalOutcome (iSampleSize),
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|    Pivoted Differences Variance Upper Bound   |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			PivotDifferencesRun (
				bin,
				EmpiricalOutcome (iSampleSize),
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|       Lugosi Bounded Variance Upper Bound       |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			LugosiVarianceRun (
				bin,
				EmpiricalOutcome (iSampleSize),
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");
	}
}
