
package org.drip.sample.sequence;

import org.drip.measure.continuous.R1Univariate;
import org.drip.measure.lebesgue.R1Uniform;
import org.drip.numerical.common.FormatUtil;
import org.drip.sequence.metrics.*;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;

/*

 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>IIDSequenceSumBound</i> demonstrates the Computation of the Different Probabilistic Bounds for Sums of
 * 	i.i.d. Random Sequences.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/sequence/README.md">IID Dual Poisson Sequence Bound</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IIDSequenceSumBound {

	private static final void Head (
		final String strHeader)
	{
		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		System.out.println (strHeader);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		System.out.println ("\t|   SAMPLE  ||               <-               TOLERANCES               ->               |");

		System.out.println ("\t|---------------------------------------------------------------------------------------|");
	}

	private static final void WeakLawBounds (
		final UnivariateSequenceGenerator iidsg,
		final R1Univariate dist,
		final int[] aiSampleSize,
		final double[] adblTolerance)
		throws Exception
	{
		for (int iSampleSize : aiSampleSize) {
			SingleSequenceAgnosticMetrics ssamDist = iidsg.sequence (
				iSampleSize,
				dist
			);

			String strDump = "\t| " + FormatUtil.FormatDouble (iSampleSize, 8, 0, 1) + " => ";

			for (double dblTolerance : adblTolerance)
				strDump += FormatUtil.FormatDouble (ssamDist.weakLawAverageBounds (dblTolerance).upper(), 1, 9, 1.) + " | ";

			System.out.println (strDump);
		}
	}

	private static final void ChernoffHoeffdingBounds (
		final UnivariateSequenceGenerator iidsg,
		final R1Univariate dist,
		final double dblSupport,
		final int[] aiSampleSize,
		final double[] adblTolerance)
		throws Exception
	{
		for (int iSampleSize : aiSampleSize) {
			BoundedSequenceAgnosticMetrics ssamDist = (BoundedSequenceAgnosticMetrics) iidsg.sequence (
				iSampleSize,
				dist
			);

			String strDump = "\t| " + FormatUtil.FormatDouble (iSampleSize, 8, 0, 1) + " => ";

			for (double dblTolerance : adblTolerance)
				strDump += FormatUtil.FormatDouble (ssamDist.chernoffHoeffdingAverageBounds (dblTolerance).upper(), 1, 9, 1.) + " | ";

			System.out.println (strDump);
		}
	}

	private static final void BennettBounds (
		final UnivariateSequenceGenerator iidsg,
		final R1Univariate dist,
		final double dblSupport,
		final int[] aiSampleSize,
		final double[] adblTolerance)
		throws Exception
	{
		for (int iSampleSize : aiSampleSize) {
			BoundedSequenceAgnosticMetrics ssamDist = (BoundedSequenceAgnosticMetrics) iidsg.sequence (
				iSampleSize,
				dist
			);

			String strDump = "\t| " + FormatUtil.FormatDouble (iSampleSize, 8, 0, 1) + " => ";

			for (double dblTolerance : adblTolerance)
				strDump += FormatUtil.FormatDouble (ssamDist.bennettAverageBounds (dblTolerance).upper(), 1, 9, 1.) + " | ";

			System.out.println (strDump);
		}
	}

	private static final void BernsteinBounds (
		final UnivariateSequenceGenerator iidsg,
		final R1Univariate dist,
		final double dblSupport,
		final int[] aiSampleSize,
		final double[] adblTolerance)
		throws Exception
	{
		for (int iSampleSize : aiSampleSize) {
			BoundedSequenceAgnosticMetrics ssamDist = (BoundedSequenceAgnosticMetrics) iidsg.sequence (
				iSampleSize,
				dist
			);

			String strDump = "\t| " + FormatUtil.FormatDouble (iSampleSize, 8, 0, 1) + " => ";

			for (double dblTolerance : adblTolerance)
				strDump += FormatUtil.FormatDouble (ssamDist.bernsteinAverageBounds (dblTolerance).upper(), 1, 9, 1.) + " | ";

			System.out.println (strDump);
		}
	}

	public static void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		BoundedUniform uniformRandom = new BoundedUniform (
			0.,
			1.
		);

		R1Uniform uniformDistribution = new R1Uniform (
			0.,
			1.
		);

		int[] aiSampleSize = new int[] {
			50, 500, 5000, 50000, 500000, 5000000, 50000000
		};

		double[] adblTolerance = new double[] {
			0.01, 0.03, 0.05, 0.07, 0.10
		};

		Head ("\t|         WEAK LAW OF LARGE NUMBERS     -      METRICS FROM UNDERLYING GENERATOR        |");

		WeakLawBounds (
			uniformRandom,
			uniformDistribution,
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|        WEAK LAW OF LARGE NUMBERS      -     METRICS FROM EMPIRICAL DISTRIBUTION       |");

		WeakLawBounds (
			uniformRandom,
			null,
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|         CHERNOFF-HOEFFDING BOUNDS      -     METRICS FROM UNDERLYING GENERATOR        |");

		ChernoffHoeffdingBounds (
			uniformRandom,
			uniformDistribution,
			uniformRandom.upperBound() - uniformRandom.lowerBound(),
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|         CHERNOFF-HOEFFDING BOUNDS      -     METRICS FROM EMPIRICAL DISTRIBUTION      |");

		ChernoffHoeffdingBounds (
			uniformRandom,
			null,
			uniformRandom.upperBound() - uniformRandom.lowerBound(),
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|              BENNETT BOUNDS      -      METRICS FROM UNDERLYING GENERATOR             |");

		BennettBounds (
			uniformRandom,
			uniformDistribution,
			uniformRandom.upperBound() - uniformRandom.lowerBound(),
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|              BENNETT BOUNDS      -      METRICS FROM EMPIRICAL DISTRIBUTION           |");

		BennettBounds (
			uniformRandom,
			null,
			uniformRandom.upperBound() - uniformRandom.lowerBound(),
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|             BERNSTEIN BOUNDS      -      METRICS FROM UNDERLYING GENERATOR            |");

		BernsteinBounds (
			uniformRandom,
			uniformDistribution,
			uniformRandom.upperBound() - uniformRandom.lowerBound(),
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		Head ("\t|            BERNSTEIN BOUNDS      -      METRICS FROM EMPIRICAL DISTRIBUTION           |");

		BernsteinBounds (
			uniformRandom,
			uniformDistribution,
			uniformRandom.upperBound() - uniformRandom.lowerBound(),
			aiSampleSize,
			adblTolerance
		);

		System.out.println ("\t|---------------------------------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
