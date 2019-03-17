
package org.drip.sample.efronstein;

import org.drip.numerical.common.FormatUtil;
import org.drip.sequence.custom.GlivenkoCantelliUniformDeviation;
import org.drip.sequence.functional.*;
import org.drip.sequence.metrics.SingleSequenceAgnosticMetrics;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>GlivenkoCantelliUniformBound</i> demonstrates the Computation of the Probabilistic Bounds for the
 * Uniform Deviation of an Empirical Sample from its Population Mean using Variants of the Efron-Stein
 * Methodology.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/efronstein/README.md">Efron-Stein Semi-Agnostic Sequence Bounds</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GlivenkoCantelliUniformBound {

	private static final SingleSequenceAgnosticMetrics[] IIDDraw (
		final UnivariateSequenceGenerator rsg,
		final int iNumSample)
		throws Exception
	{
		SingleSequenceAgnosticMetrics[] aSSAM = new SingleSequenceAgnosticMetrics[iNumSample];

		for (int i = 0; i < iNumSample; ++i)
			aSSAM[i] = rsg.sequence (
				iNumSample,
				null
			);

		return aSSAM;
	}

	private static final GlivenkoCantelliUniformDeviation GlivenkoCantelliFunction (
		final Binary binarySequenceGenerator,
		final int iNumVariate)
		throws Exception
	{
		return GlivenkoCantelliUniformDeviation.Create (
			new BoundedIdempotentUnivariateRandom (
				binarySequenceGenerator.positiveProbability(),
				null,
				1.
			),
			iNumVariate
		);
	}

	private static final void MartingaleDifferencesRun (
		final Binary binarySequenceGenerator,
		final int iNumSample,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (iNumSample, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			EfronSteinMetrics esam = new EfronSteinMetrics (
				GlivenkoCantelliFunction (
					binarySequenceGenerator,
					iNumSample
				),
				aSSAM
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (esam.martingaleVarianceUpperBound(), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void GhostVariateVarianceRun (
		final Binary binarySequenceGenerator,
		final int iNumSample,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (iNumSample, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			EfronSteinMetrics esam = new EfronSteinMetrics (
				GlivenkoCantelliFunction (
					binarySequenceGenerator,
					iNumSample
				),
				aSSAM
			);

			SingleSequenceAgnosticMetrics[] aSSAMGhost = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (esam.ghostVarianceUpperBound (aSSAMGhost), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void EfronSteinSteeleRun (
		final Binary binarySequenceGenerator,
		final int iNumSample,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (iNumSample, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			EfronSteinMetrics esam = new EfronSteinMetrics (
				GlivenkoCantelliFunction (
					binarySequenceGenerator,
					iNumSample
				),
				aSSAM
			);

			SingleSequenceAgnosticMetrics[] aSSAMGhost = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (esam.efronSteinSteeleBound (aSSAMGhost), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void PivotDifferencesRun (
		final Binary binarySequenceGenerator,
		final int iNumSample,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (iNumSample, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			MultivariateRandom func = GlivenkoCantelliFunction (
				binarySequenceGenerator,
				iNumSample
			);

			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			EfronSteinMetrics esam = new EfronSteinMetrics (
				func,
				aSSAM
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (esam.pivotVarianceUpperBound (func), 1, 3, 1.);
		}

		System.out.println (strDump + " |");
	}

	private static final void BoundedDifferencesRun (
		final Binary binarySequenceGenerator,
		final int iNumSample,
		final int iNumSet)
		throws Exception
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (iNumSample, 2, 0, 1.) + " => ";

		for (int j = 0; j < iNumSet; ++j) {
			SingleSequenceAgnosticMetrics[] aSSAM = IIDDraw (
				binarySequenceGenerator,
				iNumSample
			);

			EfronSteinMetrics esam = new EfronSteinMetrics (
				GlivenkoCantelliFunction (
					binarySequenceGenerator,
					iNumSample
				),
				aSSAM
			);

			if (0 != j) strDump += " |";

			strDump += FormatUtil.FormatDouble (esam.boundedVarianceUpperBound(), 1, 3, 1.);
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
				iSampleSize,
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|   Symmetrized Variate Variance Upper Bound    |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			GhostVariateVarianceRun (
				bin,
				iSampleSize,
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		aiSampleSize = new int[] {
			3, 10, 25, 50, 75, 99
		};

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|    Efron-Stein-Steele Variance Upper Bound    |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			EfronSteinSteeleRun (
				bin,
				iSampleSize,
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|    Pivoted Differences Variance Upper Bound   |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			PivotDifferencesRun (
				bin,
				iSampleSize,
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|   Bounded Differences Variance Upper Bound    |");

		System.out.println ("\t|-----------------------------------------------|");

		for (int iSampleSize : aiSampleSize)
			BoundedDifferencesRun (
				bin,
				iSampleSize,
				iNumSet
			);

		System.out.println ("\t|-----------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
