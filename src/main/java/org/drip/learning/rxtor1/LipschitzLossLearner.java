
package org.drip.learning.rxtor1;

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
 * <i>LipschitzLossLearner</i> implements the Learner Class that holds the Space of Normed R<sup>1</sup> To
 * Normed R<sup>1</sup> Learning Functions for the Family of Loss Functions that are Lipschitz, i.e.,
 * 
 * 				loss (ep) - loss (ep') Less Than C * |ep-ep'|
 *  
 * <br><br>
 * The References are:
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1">Statistical Learning Empirical Loss Penalizer</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LipschitzLossLearner extends org.drip.learning.rxtor1.GeneralizedLearner {
	private double _dblLipschitzSlope = java.lang.Double.NaN;

	/**
	 * LipschitzLossLearner Constructor
	 * 
	 * @param funcClassRxToR1 R^x To R^1 Function Class
	 * @param cdpb The Covering Number based Deviation Upper Probability Bound Generator
	 * @param regularizerFunc The Regularizer Function
	 * @param dblLipschitzSlope The Lipschitz Slope Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LipschitzLossLearner (
		final org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1,
		final org.drip.learning.bound.CoveringNumberLossBound cdpb,
		final org.drip.learning.regularization.RegularizationFunction regularizerFunc,
		final double dblLipschitzSlope)
		throws java.lang.Exception
	{
		super (funcClassRxToR1, cdpb, regularizerFunc);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblLipschitzSlope = dblLipschitzSlope))
			throw new java.lang.Exception ("LipschitzLossLearner ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Lipschitz Slope Bound
	 * 
	 * @return The Lipschitz Slope Bound
	 */

	public double lipschitzSlope()
	{
		return _dblLipschitzSlope;
	}

	@Override public double lossSampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		if (null == gvvi || !org.drip.numerical.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon)
			throw new java.lang.Exception
				("LipschitzLossLearner::lossSampleCoveringNumber => Invalid Inputs");

		double dblLipschitzCover = dblEpsilon / _dblLipschitzSlope;

		org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1 = functionClass();

		org.drip.learning.bound.LipschitzCoveringNumberBound llcn = new
			org.drip.learning.bound.LipschitzCoveringNumberBound (funcClassRxToR1.sampleSupremumCoveringNumber
				(gvvi, dblLipschitzCover), funcClassRxToR1.sampleCoveringNumber (gvvi, gvvi.sampleSize() *
					dblLipschitzCover));

		return bSupremum ? llcn.supremumUpperBound() : llcn.lpUpperBound();
	}

	@Override public double empiricalLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("LipschitzLossLearner::empiricalLoss => No Generic Implementation");
	}

	@Override public double empiricalLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("LipschitzLossLearner::empiricalLoss => No Generic Implementation");
	}

	@Override public double empiricalRisk (
		final org.drip.measure.continuous.R1R1Distribution distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("LipschitzLossLearner::empiricalRisk => No Generic Implementation");
	}

	@Override public double empiricalRisk (
		final org.drip.measure.continuous.RdR1Distribution distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("LipschitzLossLearner::empiricalRisk => No Generic Implementation");
	}
}
