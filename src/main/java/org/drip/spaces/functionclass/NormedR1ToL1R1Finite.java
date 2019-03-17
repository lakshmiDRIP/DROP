
package org.drip.spaces.functionclass;

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
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>NormedR1ToL1R1Finite</i> implements the Class f E F : Normed R<sup>1</sup> To L<sub>1</sub>
 * R<sup>1</sup> Spaces of Finite Functions. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass">Function Class</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedR1ToL1R1Finite extends org.drip.spaces.functionclass.NormedR1ToNormedR1Finite {

	/**
	 * Create Bounded R^1 To Bounded L1 R^1 Function Class for the specified Bounded Class of Finite
	 *  Functions
	 * 
	 * @param dblMaureyConstant Maurey Constant
	 * @param aR1ToR1 The Bounded R^1 To Bounded R^1 Function Set
	 * @param dblPredictorSupport The Set Predictor Support
	 * @param dblResponseBound The Set Response Bound
	 * 
	 * @return The Bounded R^1 To Bounded R^1 Function Class for the specified Function Set
	 */

	public static final NormedR1ToL1R1Finite BoundedPredictorBoundedResponse (
		final double dblMaureyConstant,
		final org.drip.function.definition.R1ToR1[] aR1ToR1,
		final double dblPredictorSupport,
		final double dblResponseBound)
	{
		if (null == aR1ToR1) return null;

		int iNumFunction = aR1ToR1.length;
		org.drip.spaces.rxtor1.NormedR1ToNormedR1[] aR1ToR1FunctionSpace = new
			org.drip.spaces.rxtor1.NormedR1ToNormedR1[iNumFunction];

		if (0 == iNumFunction) return null;

		try {
			org.drip.spaces.metric.R1Continuous r1ContinuousInput = new org.drip.spaces.metric.R1Continuous
				(-0.5 * dblPredictorSupport, 0.5 * dblPredictorSupport, null, 1);

			org.drip.spaces.metric.R1Continuous r1ContinuousOutput = new org.drip.spaces.metric.R1Continuous
				(-0.5 * dblResponseBound, 0.5 * dblResponseBound, null, 1);

			for (int i = 0; i < iNumFunction; ++i)
				aR1ToR1FunctionSpace[i] = new org.drip.spaces.rxtor1.NormedR1ContinuousToR1Continuous
					(r1ContinuousInput, r1ContinuousOutput, aR1ToR1[i]);

			return new NormedR1ToL1R1Finite (dblMaureyConstant, aR1ToR1FunctionSpace);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected NormedR1ToL1R1Finite (
		final double dblMaureyConstant,
		final org.drip.spaces.rxtor1.NormedR1ToNormedR1[] aR1ToR1FunctionSpace)
		throws java.lang.Exception
	{
		super (dblMaureyConstant, aR1ToR1FunctionSpace);
	}

	@Override public org.drip.spaces.cover.FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		org.drip.spaces.rxtor1.NormedR1ToNormedR1[] aNormedR1ToNormedR1 =
			(org.drip.spaces.rxtor1.NormedR1ToNormedR1[]) functionSpaces();

		int iNumFunction = aNormedR1ToNormedR1.length;
		double dblResponseLowerBound = java.lang.Double.NaN;
		double dblResponseUpperBound = java.lang.Double.NaN;
		double dblPredictorLowerBound = java.lang.Double.NaN;
		double dblPredictorUpperBound = java.lang.Double.NaN;

		for (int i = 0; i < iNumFunction; ++i) {
			org.drip.spaces.rxtor1.NormedR1ToNormedR1 r1Tor1 = aNormedR1ToNormedR1[i];

			org.drip.spaces.metric.R1Normed runsInput = r1Tor1.inputMetricVectorSpace();

			org.drip.spaces.metric.R1Normed runsOutput = r1Tor1.outputMetricVectorSpace();

			if (!runsInput.isPredictorBounded() || !runsOutput.isPredictorBounded()) return null;

			double dblResponseLeftBound = runsOutput.leftEdge();

			double dblPredictorLeftBound = runsInput.leftEdge();

			double dblResponseRightBound = runsOutput.rightEdge();

			double dblPredictorRightBound = runsInput.rightEdge();

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorLowerBound))
				dblPredictorLowerBound = dblPredictorLeftBound;
			else {
				if (dblPredictorLowerBound > dblPredictorLeftBound)
					dblPredictorLowerBound = dblPredictorLeftBound;
			}

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorUpperBound))
				dblPredictorUpperBound = dblPredictorRightBound;
			else {
				if (dblPredictorUpperBound < dblPredictorRightBound)
					dblPredictorUpperBound = dblPredictorRightBound;
			}

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblResponseLowerBound))
				dblResponseLowerBound = dblResponseLeftBound;
			else {
				if (dblResponseLowerBound > dblResponseLeftBound)
					dblResponseLowerBound = dblResponseLeftBound;
			}

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblResponseUpperBound))
				dblResponseUpperBound = dblResponseRightBound;
			else {
				if (dblResponseUpperBound < dblResponseRightBound)
					dblResponseUpperBound = dblResponseRightBound;
			}
		}

		double dblVariation = dblResponseUpperBound - dblResponseLowerBound;

		try {
			return new org.drip.spaces.cover.L1R1CoveringBounds (dblPredictorUpperBound -
				dblPredictorLowerBound, dblVariation, dblVariation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
