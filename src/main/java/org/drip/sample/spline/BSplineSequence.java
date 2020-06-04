
package org.drip.sample.spline;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.bspline.*;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>BSplineSequence</i> implements Samples for the Construction and the usage of various monic basis B
 * 	Spline Sequences. It demonstrates the following:
 * 	- Construction and Usage of segment Monic B Spline Sequence.
 * 	- Construction and Usage of segment Multic B Spline Sequence.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/README.md">Basis Monic Multic Tension Spline</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BSplineSequence {

	/*
	 * This sample shows the computation of the response value, the normalized cumulative, and the ordered
	 * 	derivative of the specified Segment Basis Function.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void ComputeResponseMetric (
		final SegmentBasisFunction me,
		final String strComment)
		throws Exception
	{
		int iOrder = 1;
		double dblXIncrement = 0.25;

		double dblX = me.leading() - dblXIncrement;

		double dblXEnd = me.trailing() + dblXIncrement;

		System.out.println ("\n\t---------------------------------------------------------------");

		System.out.println ("\t-------------------------" + strComment + "---------------------------");

		System.out.println ("\t---------------------------------------------------------------\n");

		while (dblX <= dblXEnd) {
			System.out.println (
				"\t\tResponse[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (me.evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (me.normalizedCumulative (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (me.derivative (dblX, iOrder), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}
	}

	/*
	 * This sample demonstrates the construction and usage of the following monic/multic basis spline arrays:
	 * 	- Hyperbolic Rational Linear Monic.
	 * 	- Multic basis functions of 3rd degree (i.e., quadratic).
	 * 	- Multic basis functions of 4th degree (i.e., cubic).
	 * 	- Multic basis functions of 5th degree (i.e., quartic).
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BSplineSequenceSample()
		throws Exception
	{
		double[] adblPredictorOrdinate = new double[] {
			1., 2., 3., 4., 5., 6.
		};

		/*
		 * Construct the Array of Hyperbolic Rational Linear Monic Segment Basis Functions. 
		 */

		SegmentBasisFunction[] aMonic = SegmentBasisFunctionGenerator.MonicSequence (
			BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			adblPredictorOrdinate,
			0,
			1.
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Monic
		 * 	Segment Basis Function.
		 */

		for (int i = 0; i < aMonic.length; ++i)
			ComputeResponseMetric (
				aMonic[i],
				"   MONIC   "
			);

		/*
		 * Construct the array of multic basis functions of 3rd degree (i.e., quadratic).
		 */

		SegmentBasisFunction[] aQuadratic = SegmentBasisFunctionGenerator.MulticSequence (
			3,
			aMonic
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Quadratic
		 * 	Multic Segment Basis Function.
		 */

		for (int i = 0; i < aQuadratic.length; ++i)
			ComputeResponseMetric (
				aQuadratic[i],
				" QUADRATIC "
			);

		/*
		 * Construct the array of multic basis functions of 4th degree (i.e., cubic).
		 */

		SegmentBasisFunction[] aCubic = SegmentBasisFunctionGenerator.MulticSequence (
			4,
			aQuadratic
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Cubic
		 * 	Multic Segment Basis Function.
		 */

		for (int i = 0; i < aCubic.length; ++i)
			ComputeResponseMetric (
				aCubic[i],
				"   CUBIC   "
			);

		/*
		 * Construct the array of multic basis functions of 5th degree (i.e., quartic).
		 */

		SegmentBasisFunction[] aQuartic = SegmentBasisFunctionGenerator.MulticSequence (
			5,
			aCubic
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Quartic
		 * 	Multic Segment Basis Function.
		 */

		for (int i = 0; i < aQuartic.length; ++i)
			ComputeResponseMetric (
				aQuartic[i],
				"  QUARTIC  "
			);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		BSplineSequenceSample();

		EnvManager.TerminateEnv();
	}
}
