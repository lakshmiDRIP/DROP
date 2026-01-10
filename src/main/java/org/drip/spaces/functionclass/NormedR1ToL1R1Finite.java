
package org.drip.spaces.functionclass;

import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.cover.FunctionClassCoveringBounds;
import org.drip.spaces.cover.L1R1CoveringBounds;
import org.drip.spaces.metric.R1Normed;
import org.drip.spaces.rxtor1.NormedR1ToNormedR1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>NormedR1ToL1R1Finite</i> implements the Class f E F : Normed R<sup>1</sup> To L<sub>1</sub>
 * 	R<sup>1</sup> Spaces of Finite Functions. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Create Bounded R<sup>1</sup> To Bounded L1 R<sup>1</sup> Function Class for the specified Bounded Class of Finite Functions</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass/README.md">Normed Finite Spaces Function Class</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedR1ToL1R1Finite extends NormedR1ToNormedR1Finite
{

	/**
	 * Create Bounded R<sup>1</sup> To Bounded L1 R<sup>1</sup> Function Class for the specified Bounded
	 * 	Class of Finite Functions
	 * 
	 * @param maureyConstant Maurey Constant
	 * @param aR1ToR1 The Bounded R<sup>1</sup> To Bounded R<sup>1</sup> Function Set
	 * @param dblPredictorSupport The Set Predictor Support
	 * @param dblResponseBound The Set Response Bound
	 * 
	 * @return The Bounded R<sup>1</sup> To Bounded R<sup>1</sup> Function Class for the specified Function
	 * 	Set
	 */

	public static final NormedR1ToL1R1Finite BoundedPredictorBoundedResponse (
		final double maureyConstant,
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
			org.drip.spaces.metric.R1Field r1ContinuousInput = new org.drip.spaces.metric.R1Field
				(-0.5 * dblPredictorSupport, 0.5 * dblPredictorSupport, null, 1);

			org.drip.spaces.metric.R1Field r1ContinuousOutput = new org.drip.spaces.metric.R1Field
				(-0.5 * dblResponseBound, 0.5 * dblResponseBound, null, 1);

			for (int i = 0; i < iNumFunction; ++i)
				aR1ToR1FunctionSpace[i] = new org.drip.spaces.rxtor1.NormedR1ContinuousToR1Continuous
					(r1ContinuousInput, r1ContinuousOutput, aR1ToR1[i]);

			return new NormedR1ToL1R1Finite (maureyConstant, aR1ToR1FunctionSpace);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected NormedR1ToL1R1Finite (
		final double maureyConstant,
		final NormedR1ToNormedR1[] normedR1ToNormedR1Array)
		throws java.lang.Exception
	{
		super (maureyConstant, normedR1ToNormedR1Array);
	}

	@Override public FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		NormedR1ToNormedR1[] normedR1ToNormedR1Array = (NormedR1ToNormedR1[]) functionSpaces();

		double responseLowerBound = Double.NaN;
		double responseUpperBound = Double.NaN;
		double predictorLowerBound = Double.NaN;
		double predictorUpperBound = Double.NaN;
		int functionCount = normedR1ToNormedR1Array.length;

		for (int i = 0; i < functionCount; ++i) {
			NormedR1ToNormedR1 normedR1ToNormedR1 = normedR1ToNormedR1Array[i];

			R1Normed inputR1Normed = normedR1ToNormedR1.inputMetricVectorSpace();

			R1Normed outputR1Normed = normedR1ToNormedR1.outputMetricVectorSpace();

			if (!inputR1Normed.isPredictorBounded() || !outputR1Normed.isPredictorBounded()) {
				return null;
			}

			double responseLeftBound = outputR1Normed.leftEdge();

			double predictorLeftBound = inputR1Normed.leftEdge();

			double responseRightBound = outputR1Normed.rightEdge();

			double predictorRightBound = inputR1Normed.rightEdge();

			if (!NumberUtil.IsValid (predictorLowerBound)) {
				predictorLowerBound = predictorLeftBound;
			} else {
				if (predictorLowerBound > predictorLeftBound) {
					predictorLowerBound = predictorLeftBound;
				}
			}

			if (!NumberUtil.IsValid (predictorUpperBound)) {
				predictorUpperBound = predictorRightBound;
			} else {
				if (predictorUpperBound < predictorRightBound) {
					predictorUpperBound = predictorRightBound;
				}
			}

			if (!NumberUtil.IsValid (responseLowerBound)) {
				responseLowerBound = responseLeftBound;
			} else {
				if (responseLowerBound > responseLeftBound) {
					responseLowerBound = responseLeftBound;
				}
			}

			if (!NumberUtil.IsValid (responseUpperBound)) {
				responseUpperBound = responseRightBound;
			} else {
				if (responseUpperBound < responseRightBound) {
					responseUpperBound = responseRightBound;
				}
			}
		}

		double variation = responseUpperBound - responseLowerBound;

		try {
			return new L1R1CoveringBounds (predictorUpperBound - predictorLowerBound, variation, variation);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
